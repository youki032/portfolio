package net.spring.board.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
  protected Logger log = LogManager.getLogger(ErrorController.class);
  
  @RequestMapping("/denied")
  public String denied() {
    return "error/denied";
  }
  
  @RequestMapping("/isNotLogin")
  public String isNotLogin() {
    return "error/isNotLogin";
  }
  
  
  @RequestMapping("/test")
  public String test() {
    return "error/test";
  }
  
  @RequestMapping("/nullPointerError")
  public String nullPage(Model model, HttpServletRequest request, NullPointerException nullPointerException) {
    
//    model.addAttribute("errormsg", nullPointerException);
    return "error/nullPointerError";
  }
  
  @RequestMapping("/databaseError")
  public String databaseError(Model model, HttpServletRequest request, NullPointerException nullPointerException) {
    
//    model.addAttribute("errormsg", nullPointerException);
    return "error/databaseError";
  }
  
  
  
 
}
