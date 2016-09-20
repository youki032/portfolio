package net.spring.board.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
  protected Logger log = LogManager.getLogger(CustomAuthenticationFailureHandler.class);
  
  private String loginIdName;            //로그인 id값이 들어오는 input태그
  private String loginPasswordName;      //로그인 pass값이 들어오는 input태그
  private String loginRedirectName;      //로그인 성공시 redirect할 url이 지정되어있는 input태그
  
  private String exceptionMesegeName;    //예외 메시지를 request의 Attribute에 저장할 input태그
  private String defaultFailureUrl;      //화면에 보여줄 url(로그인화면)
  
  public CustomAuthenticationFailureHandler() {
    this.loginIdName = "email";
    this.loginPasswordName = "password";
    this.loginRedirectName = "loginRedirect";
    this.exceptionMesegeName = "securityExceptionMsg";
    this.defaultFailureUrl = "/home/login";
  }

  public String getLoginIdName() {
    return loginIdName;
  }

  public void setLoginIdName(String loginIdName) {
    this.loginIdName = loginIdName;
  }

  public String getLoginPasswordName() {
    return loginPasswordName;
  }

  public void setLoginPasswordName(String loginPasswordName) {
    this.loginPasswordName = loginPasswordName;
  }

  public String getLoginRedirectName() {
    return loginRedirectName;
  }

  public void setLoginRedirectName(String loginRedirectName) {
    this.loginRedirectName = loginRedirectName;
  }

  public String getExceptionMesegeName() {
    return exceptionMesegeName;
  }

  public void setExceptionMesegeName(String exceptionMesegeName) {
    this.exceptionMesegeName = exceptionMesegeName;
  }

  public String getDefaultFailureUrl() {
    return defaultFailureUrl;
  }

  public void setDefaultFailureUrl(String defaultFailureUrl) {
    this.defaultFailureUrl = defaultFailureUrl;
  }

  
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException e) throws IOException, ServletException {
    
    //request 객체에 사용자가 실패시 입력했던 로그인 ID와 비밀번호를
    //저장해두어 로그인 페이지에서 이를 접근하도록 한다.
    System.out.println("failur 실행");
    
    String loginId = request.getParameter(loginIdName);
    String loginPass = request.getParameter(loginPasswordName);
    String loginRedirect = request.getHeader("REFERER");
//    String loginRedirect = request.getParameter(loginRedirectName);
    
    log.debug("loginRedirect = " + loginRedirect);
    
    request.setAttribute(loginIdName, loginId);
    request.setAttribute(loginPasswordName, loginPass);
    request.setAttribute(loginRedirectName, loginRedirect);
//    request.setAttribute(loginRedirectName, loginRedirect);
    
    
    //request에 예외 메시지 저장
   
    request.setAttribute(exceptionMesegeName, e.getMessage());
    request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
    log.debug("errorMsg = " + e.getMessage());
    log.debug("defaultFailureUrl = " + defaultFailureUrl);
    log.debug("request = " + request);
  }


}
