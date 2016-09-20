package net.spring.board.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import net.spring.board.service.LogService;
import net.spring.board.vo.UserLog;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler, LogoutHandler {
  private Logger log = LogManager.getLogger(CustomLogoutSuccessHandler.class);

  private String redirectUrl;
  
  @Autowired 
  private LogService logService;
  
   
  String getRedirectUrl() {
    return redirectUrl;
  }
  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }
  
  
  @Override
  public String toString() {
    return "CustomLogoutSuccessHandler [redirectUrl=" + redirectUrl + "]";
  }
  
  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    
  }
  
  
  
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    log.debug("==== logout handler start =====");
    
    try {
      //logout log수집
      UserLog userLog =  new UserLog(UserLog.CMD_LOGOUT, request, authentication);
      logService.addUserLog(userLog, "etc");
    
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
   
    response.sendRedirect(request.getContextPath() + redirectUrl);
    
  }
  
}
