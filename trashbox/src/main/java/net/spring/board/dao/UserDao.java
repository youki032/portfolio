package net.spring.board.dao;

import java.util.List;
import java.util.Map;

import net.spring.board.vo.ChangeEdit;
import net.spring.board.vo.Msg;
import net.spring.board.vo.User;
import net.spring.board.vo.UserInfo;

public interface UserDao {
  
  
  
  
  /**==========================  insert(add)  ===============================*/
  //회원가입 - user테이블에 회원정보 insert
  public int insertSignUp(User user);

  //회원가입 - userinfo테이블에 가입일도 같이 넣어줌. 
  public int insertSignUpDate(String birthDate);

  //회원가입 - 신규유저 msg테이블 생성및 환영쪽지
  public int insertWelComeMsg(Msg welcomeMsg);

  //회원가입 - security 권한 insert
  public int insertAuthority(Map<String, String> authMap);
  
  //유저클래스 테이블 생성
  public int insertUserClassTable();
  
  
  
  
  /**==========================  select(get)  ===============================*/
  //회원가입 - 아이디 중복체크
  public int selectEmailCheck(String email);
  
  //회원가입 - 닉네임중복 체크(user테이블)
  public int selectUserNicNameCheck(String nicName);
  
  //회원가입 - 닉네임중복 체크(article테이블)
  public int selectArticleNicNameCheck(String nicName);
  
  
  //회원정보 찾기 - 입력한 값으로 email 찾기
  public List<User> selectFindEmail(User user) throws Exception;
  
  //회원정보 찾기 - 입력한 값으로 password 찾기
  public User selectFindPassword(User user) throws Exception;
  
  
  
  //MyUserDetails - 회원번호를 가져와 가입유무 확인
  public int selectUserNo(String email);
  
  //MyUserDetails - no값으로 user테이블의 회원정보를 가져와 MyUserDetails에 넣어줌
  public User selectUser(int userNo);
  
  
  
  
  
  /**==========================  update(change)  ============================*/
  
  //회원정보 수정 - 기본 회원정보 변경
  public int updateBasicInfo(UserInfo userInfo);
  
  //회원정보 수정 - 기본 회원정보외 닉네임또는 비번변경.
  public int updateUniqueInfo(ChangeEdit changeEdit);
  
  
  //회원정보찾기 - 패스워드 수정
  int updateFindPassword(User user) throws Exception;
  
  
  
  
  
  /**==========================  delete(remove)  ============================*/
  
  //회원탈퇴
  public int deleteUser(int userNo);

  

  
}