package net.spring.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import net.spring.board.security.MyUserDetails;
import net.spring.board.service.UserClassService;
import net.spring.board.vo.UserClass;

//@Controller
public class UserClassController {
  protected Logger log = LogManager.getLogger(UserClassController.class);
  
  @Autowired private UserClassService userClassService;
  
  
//  @RequestMapping(value="/loadUserClass", method=RequestMethod.GET)
//  public String loadUserClass(Model model) {
//    
//    MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    
//    UserClass userClass = userClassService.getUserClass(myUserDetails.getUserNo());
//        
//    model.addAttribute("userClass", userClass);
//    
//    return "home/test";
//    
//   }
  
  
  
//  @RequestMapping(value="/sendUserClass.do", method=RequestMethod.POST)
//  @ResponseBody
//  public Map<String, Integer> sendUserClass(int addExp) {
//    MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//    Map<String, Integer> expMap = new HashMap<String, Integer>();
//    expMap.put("userNo", myUserDetails.getUserNo());
//    expMap.put("nowExp", addExp);
//    
//    userClassService.addExp(expMap);
//    
//    
//    Map<String, Integer> resultMap = new HashMap<String, Integer>();
//    UserClass userClass = userClassService.getUserClass(myUserDetails.getUserNo());
//    
//    resultMap.put("nowExp", userClass.getNowExp());
//    resultMap.put("nextExp", userClass.getNextExp());
//    resultMap.put("userLevel", userClass.getUserLevel());
//    
//    return resultMap;
//   }
  
  
  public Map<String, Integer> sendUserClass(int addExp) throws Exception {
    MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//    userClassService.addExp(addExp);
    
    
    Map<String, Integer> resultMap = new HashMap<String, Integer>();
    UserClass userClass = userClassService.getUserClass(myUserDetails.getUserNo());
    
    resultMap.put("nowExp", userClass.getNowExp());
    resultMap.put("nextExp", userClass.getNextExp());
    resultMap.put("userLevel", userClass.getUserLevel());
    
    return resultMap;
   }
  
}
