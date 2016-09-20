package net.spring.board.controller;

import java.security.Principal;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.vertx.java.core.json.JsonObject;

import net.spring.board.dao.UserClassDao;
import net.spring.board.security.MyUserDetails;
import net.spring.board.server.VertxServer;
import net.spring.board.service.LogService;
import net.spring.board.service.UserService;
import net.spring.board.vo.User;
import net.spring.board.vo.UserClass;
import net.spring.board.vo.UserInfo;
import net.spring.board.vo.UserLog;


@Controller
@RequestMapping(value={"/", "/home"})
public class HomeController {
  private Logger log = LogManager.getLogger(HomeController.class);
  
  @Autowired private UserService userService;
  @Autowired private LogService logService;
  @Autowired private JavaMailSender mailSender;
  @Autowired private VertxServer vertxServer;
  
  
  //메인페이지
  @RequestMapping(value="/main", method=RequestMethod.GET)
  public String main(Principal principal, Model model) {
    
    if (principal != null){
        
      MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      UserInfo profileUserInfo = userService.getUserInfo(myUserDetails.getUserNo());
//      log.debug("/////////////login mydetails session = " + myUserDetails.getID());
      model.addAttribute("profileUserInfo", profileUserInfo);   //menubar 프로필사진 load
    }
    
    log.debug("welcome main");
//    log.debug("main session = " + session.getId());
    
    return "home/main";
   }
  
  
  //로그인 페이지 view
  @RequestMapping(value="/login", method={RequestMethod.GET,RequestMethod.POST})
  public String login(Principal principal, Model model, HttpServletRequest request, HttpSession session) {
    
    String referrer = request.getHeader("Referer");
    request.getSession().setAttribute("loginRedirect", referrer);
    log.debug("뒤로가기 테스트 login controller = " + referrer);
   
//    log.debug("login session = " + session.getId());
    
    if (principal != null){
      
      MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      UserInfo profileUserInfo = userService.getUserInfo(myUserDetails.getUserNo());
      
      model.addAttribute("profileUserInfo", profileUserInfo);   //menubar 프로필사진 load
    
    } 
   
    
    
    return "home/login";
  }

  
  //회원가입 성공 view
  @RequestMapping(value="/welcome", method=RequestMethod.GET)
  public String welcome(Principal principal, Model model) {
    
    if (principal != null){
      
      MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      UserInfo profileUserInfo = userService.getUserInfo(myUserDetails.getUserNo());
      
      model.addAttribute("profileUserInfo", profileUserInfo);   //menubar 프로필사진 load
    
    }
    
    return "home/welcome";
  }
  
  
  //회원가입 view
  @RequestMapping(value="/signUp", method=RequestMethod.GET)
  public String signUp() {
    
    
    return "home/signUp";
  }
  
  
  
  @RequestMapping(value="/signUp", method=RequestMethod.POST)
  public String signUp(User user, HttpServletRequest request) throws Exception {

    int count = 0;
    
    count = userService.addUser(user);   
    
    log.debug("SignUp userlog count = " + count);
    
    if(count == 1) { //회원가입 정보가 성공적으로 들어갔는지 유무를 확인하여 1을 반환받으면 signUp log작성
      
      UserLog userLog =  new UserLog(UserLog.CMD_SIGNUP, request);
      log.debug("SignUp userlog = " + userLog);
      logService.addUserLog(userLog, "add");
      
    }
    
    
    
    return "home/welcome";
    
  }
  
  
  //회원정보 찾기
  @RequestMapping(value="/userFind", method=RequestMethod.GET)
  public String userFind(Model model, User user, Principal principal) {
    log.debug("user = " + user);
    
    if (principal != null){
     
      MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      UserInfo profileUserInfo = userService.getUserInfo(myUserDetails.getUserNo());
      
      model.addAttribute("profileUserInfo", profileUserInfo);   //menubar 프로필사진 load
    
    }
    
    model.addAttribute("user", new User());
    
    return "home/userFind";
  }
  
  
  @RequestMapping(value="/userFindResult", method= {RequestMethod.POST, RequestMethod.GET})
  public String userFind(User user, Model model, @RequestParam(value="findStr")String findStr) throws Exception {    
    
    List<User> findEmail = null;
    log.debug("findStr = " + findStr);
    
    if (findStr.equals("email")) {
      
      findEmail = userService.getFindEmail(user);
      log.debug("getFindEmail findEmail = " + findEmail);
      
      if (findEmail.size() <= 0) {
        model.addAttribute("nullEmail", "nullEmail");
      } else {
        model.addAttribute("findEmail", findEmail);
      }
      
      
      
    } else if (findStr.equals("password")) {
    
      user = userService.getFindPassword(user);
    
    try {
      
      if (!user.getPassword().equals("") || user.getPassword() != null) {
        
          String userNicName = user.getNicName();
          String from = "youki032@naver.com";
          String subject = userNicName + "님의 찾으신 비밀번호 입니다.";  
    
          MimeMessage message = mailSender.createMimeMessage();
          MimeMessageHelper messageHelper = new MimeMessageHelper(message);
          messageHelper.setTo(user.getEmail());
          messageHelper.setText("찾으신 비밀번호는 " + user.getOriginPassword() + "입니다.", true);
          messageHelper.setFrom(from);
          messageHelper.setSubject(subject);  // 메일제목은 생략이 가능하다
    
          mailSender.send(message);
          
          }
    
      } catch(NullPointerException e2) {
        
    
      } catch(Exception e) {
        System.out.println(e);
      
      }

    model.addAttribute("user", user);
    
    }
      
    
    return "home/userFindResult";
                         
  }

  
  //vertx server
  @RequestMapping(value="/strSend", method=RequestMethod.POST)
  @ResponseBody
  public String strSend(@RequestParam(value="str")String str) throws Exception {

    vertxServer.getIo().sockets().emit("echo", new JsonObject().putString("data", str));
    
    return "ok";
  }
  


}
