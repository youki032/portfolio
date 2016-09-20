package net.spring.board.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.spring.board.security.MyUserDetails;
import net.spring.board.service.MsgService;
import net.spring.board.service.UserClassService;
import net.spring.board.service.UserService;
import net.spring.board.vo.UserClass;
import net.spring.board.vo.UserInfo;

@Controller
@RequestMapping("/include")
public class IncludeController {
  protected Logger log = LogManager.getLogger(IncludeController.class);

  @Autowired 
  private UserService userService;
  
  @Autowired 
  private MsgService msgService;

  @Autowired 
  private UserClassService userClassService;
  
  
  @RequestMapping(value="/menubar", method=RequestMethod.POST)
  @ResponseBody
  public Map<String, Integer> menubar(Principal principal, Model model) throws Exception {
    
    Map<String, Integer> userClassMap = new HashMap<String, Integer>();
    
    if (principal != null) {
      
    log.debug("===== menubar start =====");
    log.debug("===== menubar start =====");
    log.debug("===== menubar start =====");
    log.debug("===== menubar start =====");
    log.debug("===== menubar start =====");
    log.debug("===== menubar start =====");
    log.debug("===== menubar start =====");
    
    MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
//    UserInfo userInfo = userService.getUserInfo(myUserDetails.getUserNo()); //회원정보 load
    UserClass userClass = userClassService.getUserClass(myUserDetails.getUserNo()); //회원point정보 load
    
    userClassMap.put("userLevel", userClass.getUserLevel());
    userClassMap.put("nowExp", userClass.getNowExp());
    userClassMap.put("nextExp", userClass.getNextExp());
    
    userClassMap.put("progressExp", (int)userClass.getProgressExp());
    log.debug("progress = " + userClass.getProgressExp());
    
//    model.addAttribute("userInfo", userInfo);
//    model.addAttribute("userNo", myUserDetails.getUserNo());
//    model.addAttribute("userClass", userClass);
    
    }
    
    return userClassMap;
  }
  
  @RequestMapping(value="/msgCheck", method=RequestMethod.POST)
  @ResponseBody
  public Map<String, Integer> msgCheck(String start, Principal principal) throws Exception {
    Map<String, Integer> msgCheckMap = new HashMap<String, Integer>();
    
    if (principal != null){
    MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    int msgCheck = msgService.isReadMsg(myUserDetails.getUserNo());
    msgCheckMap.put("msgCheck", msgCheck);
    
    log.debug("msgCheck start = " + start);
    log.debug("msgCheck = " + msgCheck);
        
    }
    
    return msgCheckMap;
  
  }

}
