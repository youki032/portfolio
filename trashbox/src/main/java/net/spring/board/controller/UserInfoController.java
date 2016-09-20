package net.spring.board.controller;

import java.io.File;
import java.security.Principal;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.spring.board.security.MyUserDetails;
import net.spring.board.service.UserClassService;
import net.spring.board.service.UserService;
import net.spring.board.vo.ChangeEdit;
import net.spring.board.vo.UserActivity;
import net.spring.board.vo.UserClass;
import net.spring.board.vo.UserInfo;

@Controller
@RequestMapping("/userinfo")
public class UserInfoController {
  protected Logger log = LogManager.getLogger(UserInfoController.class); 
  
  @Autowired 
  private UserService userService;
  
  @Autowired 
  private ServletContext sc;
  
  @Autowired 
  private UserClassService userClassService;
  
  
  @RequestMapping(value="/myPage", method=RequestMethod.GET)
  public String myPage(Principal principal, Model model) throws Exception {
    
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    UserInfo profileUserInfo = userService.getUserInfo(myUserDetails.getUserNo());
    UserInfo userInfo = userService.getUserInfo(myUserDetails.getUserNo());
    UserActivity userActivity = userService.getUserActivity(myUserDetails.getUserNo());
    UserClass userClass = userClassService.getUserClass(myUserDetails.getUserNo()); //회원point정보 load

    log.debug("myUserDetails = " + myUserDetails);

    model.addAttribute("userActivity", userActivity);
    model.addAttribute("profileUserInfo", profileUserInfo);   //menubar 프로필사진 load
    model.addAttribute("myUserDetails", myUserDetails);
    model.addAttribute("userInfo", userInfo);
    model.addAttribute("userClass", userClass);

    
    return "userinfo/myPage";
    
  }
  
  @RequestMapping(value="/checkPopUp", method=RequestMethod.GET)
  public String checkPopUp(Model model) {
//    model.addAttribute("user", new User());
    return "userinfo/checkPopUp";
  }
  
  
  
  @RequestMapping(value="/zipcode", method=RequestMethod.GET)
  public String zipcode() throws Exception {
    return "userinfo/zipcode";
  }
  
  
  
  
  @RequestMapping(value="/userEdit", method=RequestMethod.GET)
  public String userEdit(Principal principal, Model model) {
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserInfo userInfo = userService.getUserInfo(myUserDetails.getUserNo());

    //    User user = userService.getUser(principal.getName());
    //    UserInfo userInfo = new UserInfo();
    log.debug("userInfo = " + userInfo);
    model.addAttribute("userInfo", userInfo);
    model.addAttribute("myUserDetails", myUserDetails);
    
    return "userinfo/userEdit";
  }
  
  

  @RequestMapping(value="/userEdit", method=RequestMethod.POST)
  public String userEdit(Principal principal, UserInfo userInfo, ChangeEdit changeEdit,
      @RequestParam(value="file",required=false)MultipartFile file) throws Exception {
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    log.debug("myUserDetails = "+myUserDetails);
    
    userInfo.setUserNo(myUserDetails.getUserNo());
    changeEdit.setChangeEditEmail(principal.getName());
    
    
    String newPass = changeEdit.getNewPassword();
    String newNic = changeEdit.getNewNicName();
    
    int checkCount = check(newNic, newPass);
    

    if (!file.isEmpty()) {
    String fileName = this.generateFilename(file.getOriginalFilename()); 
    file.transferTo(new File(
        sc.getRealPath("/files/profile")
        + "/" + fileName));
    userInfo.setProfilePath(fileName);
    
    
    } else {
    
    userInfo.setProfilePath(""); 

    }
    
    
    log.debug("user = " + myUserDetails.getUsername());
    log.debug("userInfo = " + userInfo);
    log.debug("newNicname = " + newNic);
    log.debug("newPassword = " + newPass);
    log.debug("checkCount = " + checkCount);
    log.debug("changeEmail = " + changeEdit.getChangeEditEmail());
    
//    1=둘다null, 2=닉값0 패스x, 3=닉값x 패스0, 4=둘다값0  
    
    
    
    switch(checkCount) {
    case 1: 
        userService.addUserEdit(userInfo);
        log.debug("case1: userInfo = " + userInfo);
        break;
        
    case 2:
        myUserDetails.setNicName(changeEdit.getNewNicName());
      
      
        changeEdit.setNewNicName(newNic);
        changeEdit.setUserNo(myUserDetails.getUserNo());
        
        log.debug("case2: newNicname = " + newNic);
        
        userService.addUserEdit(userInfo, changeEdit);
        break;
        
    case 3:
        myUserDetails.setPassword(changeEdit.getNewPassword());
        myUserDetails.setNicName(myUserDetails.getNicName());

      
        changeEdit.setNewPassword(newPass);
        changeEdit.setUserNo(myUserDetails.getUserNo());
        log.debug("case3: newPassword = " + newPass);
        
        userService.addUserEdit(userInfo, changeEdit);
        break;
        
    case 4:
      
      myUserDetails.setNicName(changeEdit.getNewNicName());
      myUserDetails.setPassword(changeEdit.getNewPassword());
      
      
        changeEdit.setNewNicName(newNic);
        changeEdit.setNewPassword(newPass);
        changeEdit.setUserNo(myUserDetails.getUserNo());
        log.debug("case4: newNicname = " + newNic 
            + "NewPassword = " + newPass);

        userService.addUserEdit(userInfo, changeEdit);
        break;  
    }

    
    return "redirect:/userinfo/myPage";
  }
  
  
  
  @RequestMapping(value="/userBye", method=RequestMethod.GET)
  public String userBye(Principal principal, Model model) {
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
    model.addAttribute("email", myUserDetails.getUsername());
    
    return "userinfo/userBye";
  }
  
  
  
  
  

  
  private String generateFilename(String originFilename) {
    // 원래 파일명을 사용하지 않는다. => 다른 사람이 올린 파일명과 같을 수 있다.
    // 랜덤하게 생성한 파일명을 사용한다.
    int lastIndex = originFilename.lastIndexOf("."); // 파일명에서 마지막 점의 위치
    return System.currentTimeMillis() + originFilename.substring(lastIndex);
  }

  
  
  public int check(String nicArg1, String passArg2) {
    int checkCount = 0;
    
    if (nicArg1.equals("") & passArg2.equals("")) {       //닉=null, 비번=null
      checkCount = 1;
      
    } else if (!nicArg1.equals("") & passArg2.equals("")) {    //닉=0, 비번=null
        checkCount = 2;
      
      } else if (nicArg1.equals("") & !passArg2.equals("")) {   //닉=null, 비번=0
          checkCount = 3;
          
        } else if (!nicArg1.equals("") & !passArg2.equals("")) { //닉=0, 비번=0 
            checkCount = 4;
          }
   
    return checkCount;
  }
  
  

}
