package net.spring.board.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.spring.board.dao.BoardDao;
import net.spring.board.dao.LogDao;
import net.spring.board.dao.UserClassDao;
import net.spring.board.dao.UserDao;
import net.spring.board.dao.UserInfoDao;
import net.spring.board.security.MyUserDetails;
import net.spring.board.vo.Board;
import net.spring.board.vo.ChangeEdit;
import net.spring.board.vo.Msg;
import net.spring.board.vo.User;
import net.spring.board.vo.UserActivity;
import net.spring.board.vo.UserClass;
import net.spring.board.vo.UserClassPoint;
import net.spring.board.vo.UserInfo;

@Service("UserService")
public class UserServiceImpl implements UserService {
  protected Logger log = LogManager.getLogger(UserServiceImpl.class);
  
  @Autowired
  private BCryptPasswordEncoder bcryptPasswordEncoder;
 
  @Autowired 
  private UserDao userDao;
  
  @Autowired 
  private BoardDao boardDao;
  
  @Autowired
  private UserInfoDao userInfoDao;

  @Autowired
  private LogDao logDao;
  
  
  @Autowired 
  UserClassService userClassService;
 
  /**==========================  insert(add)  ===============================*/
  //시큐리티 로그인 회원가입정보, 회원권한추가
  public int addUser(User user) {
    log.debug("add user = " + user);
    
    //password encoding
    user.setOriginPassword(user.getPassword());
    user.setPassword(this.bcryptPasswordEncoder.encode(user.getPassword()));
    
    //auth테이블 권한부여
    String authName = "ROLE_USER";
    String authEmail = user.getEmail();
    
    Map<String, String> authMap = new HashMap<String, String>();
    authMap.put("authority", authName);
    authMap.put("email", authEmail);
    
    
    int count = 0;
    user.setUserNo(userDao.insertSignUp(user));//회원정보  user DB테이블에 user정보를 전달
    userDao.insertSignUpDate(user.getBirthDate());//회원정보와 같이 user_info DB테이블에 가입일전달.
    userDao.insertAuthority(authMap);//회원권한 DB테이블에 정보전달
    userDao.insertUserClassTable();
    
    //msg 테이블 가입멘트 넣기
    Msg welcomeMsg = new Msg();
    welcomeMsg.setToUserNicName(user.getNicName());
    welcomeMsg.setToUserEmail(user.getEmail());
    welcomeMsg.setFromUserNo(1);
    welcomeMsg.setFromUserEmail("trashbox.naver.com");
    welcomeMsg.setFromUserNicName("관리자");
    welcomeMsg.setMsgContent("가입을 환영합니다! <br /> 이용수칙은 공지사항 게시판을 확인해주세요.");
    welcomeMsg.setMsgFlag(true);
    welcomeMsg.setToUserNo(user.getUserNo());
    userDao.insertWelComeMsg(welcomeMsg); //신규유저 msg테이블 생성및 환영쪽지
    
    
    return count;
  }
  
  
  
  //패스워드, 닉네임없이 기본회원정보만 변경시
  public int addUserEdit(UserInfo userInfo) {
    
    int count = userDao.updateBasicInfo(userInfo);
    
    return count;
  }
  
  
  
  //기본정보외 패스워드, 닉네임까지 변경시
  public int addUserEdit(UserInfo userInfo, ChangeEdit changeEdit) throws ParseException {
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    log.debug("changeEdit = " + changeEdit + "userInfo" + userInfo);
    int count = 0;
    
    if(!changeEdit.getNewPassword().equals("")) {
    changeEdit.setNewPassword(this.bcryptPasswordEncoder.encode(changeEdit.getNewPassword()));
    
    myUserDetails.setPassword(changeEdit.getNewPassword());
    Authentication authentication = new UsernamePasswordAuthenticationToken(myUserDetails, myUserDetails.getPassword(), myUserDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    
    count = userDao.updateUniqueInfo(changeEdit);
    userDao.updateBasicInfo(userInfo);
    
    } else if(changeEdit.getNewPassword().equals("")) {
      
        String result = "";
        String setStr;
        setStr = doAfterDate(changeEdit.getNicChangeDate(), result);
        log.debug("result = " + result);
        
        changeEdit.setNicChangeDate(setStr);
        log.debug("닉네임변경" + changeEdit.getNicChangeDate());
        
        count = userDao.updateUniqueInfo(changeEdit);
        userDao.updateBasicInfo(userInfo);
    
    }
    
    return count;
    
  }
  
  
  
  public void attendance() throws Exception {
    log.debug("== attendance start ==");
    
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserInfo userInfo = userInfoDao.selectUserInfo(myUserDetails.getUserNo());
    
    if (userInfo.getAttendanceDate() == null) {
      
      //처음 로그인(첫 출석)이면 로그인한 시각을 출석날짜 컬럼으로 넣어준다.
      SimpleDateFormat fm1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      userInfo.setAttendanceDate(fm1.format(new Date()));
      userInfo.setAttendance(1);
      userInfoDao.updateAttendanceDate(userInfo);
      log.debug("== 출석날짜 없을때 ==");
      //출석 포인트 넣기.
      userClassService.addExp(UserClassPoint.ATTENDANCE_POINT, myUserDetails.getUserNo());
    
    } else if(userInfo.getAttendanceDate() != null) {
      log.debug("== 출석날짜 존재 ==");
      
      //출석날짜가 존재하면 현재시각과 출석날짜를 비교하여 하루가 지났는지 체크하여 처리한다.
      
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      
      String befor = userInfo.getAttendanceDate();
      String after = formatter.format(new Date());
      
      Date beginDate = formatter.parse(befor);
      Date afterDate = formatter.parse(after);
       
      // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
      long diff = afterDate.getTime() - beginDate.getTime();
      long diffDays = diff / (24 * 60 * 60 * 1000);
      log.debug("@@@@@@@@@@@@@@@@@@@@@@ = " + diffDays);
      
      if (diffDays > 0) { //0이 아닌 count가 출력되면 출석 conut 진행. 출석한 날짜 update
       
        SimpleDateFormat fm1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        userInfo.setAttendanceDate(fm1.format(new Date()));
        userInfo.setAttendance(1);
        
        userInfoDao.updateAttendanceDate(userInfo);
        
        //출석 포인트 넣기.
        userClassService.addExp(UserClassPoint.ATTENDANCE_POINT, myUserDetails.getUserNo());
        
        log.debug("== 출석 진행 ==");
        
      } else if(diffDays <= 0) { //0이 출력되면 출석한 날짜로 부터 하루가 안지남으로 진행x
        
      
      } 
    }
  }
  
  
  
  
  
  /**==========================  select(get)  ===============================*/
    
  
  //회원탈퇴 - 회원탈퇴시 유저번호 가져오기
  public User getUser(int userNo) {
  
    return userDao.selectUser(userNo);
  }
  
  
  
  //게시판제목의 번호와 이름 가져오기
  public Board getBoard(int boardNo) {
    
    return boardDao.selectBoard(boardNo);
  }
  

  
  //회원 추가정보 가져오기
  public UserInfo getUserInfo(int userNo) {
    
    UserInfo userInfo = userInfoDao.selectUserInfo(userNo);
    
    //출석날짜가 있는지 확인한다.
    if (userInfo.getAttendanceDate() == null) {
      //출석날짜가 없으면 건너뛴다.
      
    } else if(userInfo.getAttendanceDate() != null) {
      
      userInfo.setAttendanceDate(userInfo.getAttendanceDate().substring(0,10));
      
    }
    
    userInfo.setSignDate(userInfo.getSignDate().substring(0,10));
   
    return userInfo;
  }
  
  
  
  //회원가입 id중복체크
  public int getEmailCheck(String email) throws Exception{
    
    int count = userDao.selectEmailCheck(email);
    
    return count;
  }
  
  
  
  //닉네임중복 체크(user테이블에서 찾기)
  public int getUserNicNameCheck(String nicName) throws Exception{
    
    int count = userDao.selectUserNicNameCheck(nicName);
    
    return count;
  }
  
  //넥네임중복 체크(article테이블에서 찾기)
  public int getArticleNicNameCheck(String nicName) throws Exception{
    
    int count = userDao.selectArticleNicNameCheck(nicName);
    
    return count;
  }
  
 
  
  //회원정보 찾기 - email
  public List<User> getFindEmail(User user) throws Exception {
    log.debug(" = getFindEmail start = ");
    
    List<User> findUser = null;
    
    try {
 
      findUser = userDao.selectFindEmail(user);
      
      } catch(NullPointerException e) {
        return findUser;
      
      } catch (Exception e) {
        System.out.println(e);
      }
    
    return findUser;
  }
  
  
  
  //회원정보 찾기 - password
  public User getFindPassword(User user) throws Exception {
    log.debug(" = getFindPassword start = ");
    
    try {
    
    if (user.getBirthDate() != null) {    //eamil찾기
      user = userDao.selectFindPassword(user);
      log.debug("emailFindUser = " + user);
      
      
    } else if(user.getEmail() != null) { //password찾기
      user = userDao.selectFindPassword(user);
      log.debug("emailFindUser = " + user);
      Random ran = new Random();
      StringBuffer buf = new StringBuffer();
      
      for(int i=0; i<20; i++) {  //임시비밀번호 만들기
        if(ran.nextBoolean()) {
          buf.append((char)((int)(ran.nextInt(26))+97));
          
        } else {
          buf.append((ran.nextInt(10)));
        }
      }
      log.debug("buf = " + buf);
      
      user.setPassword(this.bcryptPasswordEncoder.encode(buf.toString()));
      log.debug("randomUser = " + user);
      log.debug("passwordFindUser = " + user);
      
      
      userDao.updateFindPassword(user);
      userDao.selectFindPassword(user);
      user.setOriginPassword(buf.toString());
      
      log.debug("returnFindUser = " + user);
      
    }
    
    } catch(NullPointerException e) {
      return user;
    
    } catch (Exception e) {
      System.out.println(e);
    }

   return user;
  }
  
  
  //마이페이지 활동내역 불러오기
  public UserActivity getUserActivity(int userNo) throws Exception {
    UserActivity userActivity = new UserActivity();
    
    userActivity.setArticleWriteCnt(logDao.selectArticleWriteCnt(userNo));
    userActivity.setArticleRemoveCnt(logDao.selectArticleRemoveCnt(userNo));
    userActivity.setCommentWriteCnt(logDao.selectCommentWriteCnt(userNo));
    userActivity.setCommentRemoveCnt(logDao.selectCommentRemoveCnt(userNo));

    
    return userActivity;
  }
  
  
  
  /**==========================  delete(remove)  ============================*/
  
  public int getByePassword(String password, int userNo) {
    
    String encodedPassword = this.getUser(userNo).getPassword();
    log.debug("encodedPassword = " + encodedPassword);
    
    boolean checkPassword = this.bcryptPasswordEncoder.matches(password, encodedPassword);
    log.debug("checkPassword = " + checkPassword);
    
    int checkCount = 0;
    
    
    if(checkPassword == false) {
      checkCount = 2;   //비밀번호 미일치
      log.debug("checkPasswordFalse = " + checkPassword);
    
    } else if(checkPassword == true){
      userDao.deleteUser(userNo);
      
      checkCount = 1;   //비밀번호 일치, 탈퇴진행
      log.debug("checkPasswordSucess = " + checkPassword);
      log.debug("checkPasswordSucess = " + userNo);
      
    }
    log.debug("servicePass = " + password);
    
    return checkCount;
  }
  
  
  
  
  
  
  /**==========================  기능함수  ============================*/
  //한달후날짜 구하기(닉네임 날짜변경 함수)
  public String doAfterDate(String str, String result) throws ParseException {   
    log.debug("str = " + str);
    
    if(str == null) {
      SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          
      Calendar cal = new GregorianCalendar(Locale.KOREA);
      cal.setTime(new Date());
      cal.add(Calendar.MONTH, 1); // 한달을 더한다. 
      result = fm.format(cal.getTime());
      log.debug("result = " + result + "날짜없을때");
      
      return result;
      
    } else {
    
    
    SimpleDateFormat fm2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date resultDate = fm2.parse(str);
    log.debug("resultDate = " + resultDate + "날짜있을때");
    Calendar cal = new GregorianCalendar(Locale.KOREA);
    cal.setTime(resultDate);
    cal.add(Calendar.MONTH, 1); // 한달을 더한다. 
    result = fm2.format(cal.getTime());
    
    log.debug("result = " + result);
    
    return result;
    
    }
  }
  
  
  
}
