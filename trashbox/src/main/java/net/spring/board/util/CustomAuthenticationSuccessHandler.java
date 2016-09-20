package net.spring.board.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import net.spring.board.service.LogService;
import net.spring.board.service.UserService;
import net.spring.board.vo.UserLog;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  
  protected Logger log = LogManager.getLogger(CustomAuthenticationSuccessHandler.class);
  
  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
  
  private RequestCache requestCache = new HttpSessionRequestCache();
  
  private String targetUrlParameter;
  
  private String defaultUrl;
  
  private boolean useReferer;

  @Autowired 
  private LogService logService;
  
  @Autowired 
  private UserService userService;
  
  
  //생성자
  public CustomAuthenticationSuccessHandler() {
    targetUrlParameter = "";
    defaultUrl = "/";
    useReferer = false;
  }

  
  //getter and getter
  public String getTargetUrlParameter() {
    return targetUrlParameter;
  }

  public void setTargetUrlParameter(String targetUrlParameter) {
    this.targetUrlParameter = targetUrlParameter;
  }

  public String getDefaultUrl() {
    return defaultUrl;
  }

  public void setDefaultUrl(String defaultUrl) {
    this.defaultUrl = defaultUrl;
  }

  public boolean isUseReferer() {
    return useReferer;
  }

  public void setUseReferer(boolean useReferer) {
    this.useReferer = useReferer;
  } 

  @Override
  public String toString() {
    return "CustomAuthenticationSuccessHandler [redirectStrategy=" + redirectStrategy + ", requestCache=" + requestCache
        + ", targetUrlParameter=" + targetUrlParameter + ", defaultUrl=" + defaultUrl + ", useReferer=" + useReferer
        + "]";
  }


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
      Authentication authentication) throws IOException, ServletException {
    
    log.debug("뒤로가기 테스트 onAuthenticationSuccess = " + request.getRequestURI());
    
    try {
      //유저 login, logout, signUp log를 db에 저장
      UserLog userLog =  new UserLog(UserLog.CMD_LOGIN, request, authentication);
      logService.addUserLog(userLog, "etc");
      userService.attendance();
      
      
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
   
    
    clearAuthenticationAttributes(request);
    
    int inRedirectStrategy = decideRedirectStrategy(request, response);
    log.debug("뒤로가기 테스트 inRedirectStrategy = " + inRedirectStrategy);
    
    switch(inRedirectStrategy) {
    case 1:
      useTargetUrl(request, response);
      break;
    
    case 2:
      useSessionUrl(request, response);
      break;
      
    case 3:
      useRefererUrl(request, response);
      break;
      
    default:
      useDefaultUrl(request, response);
    }
  }
  
  
  
  
  private void clearAuthenticationAttributes(HttpServletRequest request) {
    log.debug("clearAuthenticationAttributes = " + request.toString());
    
    HttpSession session = request.getSession(false);
    
    if (session == null) {
      return;
    }
    
    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
  }
  
  
  
  
  
  private void useTargetUrl(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
    log.debug("뒤로가기 테스트 useTargetUrl = " + request.getRequestURI());
    
    SavedRequest savedRequest = requestCache.getRequest(request, response);
    
    if (savedRequest != null) {
      requestCache.removeRequest(request, response);
    
    }
    
    String targetUrl = (String)request.getSession().getAttribute(targetUrlParameter);
    log.debug("뒤로가기 테스트 targetUrlParameter = " + targetUrl.toString());
    
    if (targetUrl.matches(".*/login.*")) {
      targetUrl = "/main";
      
    } else if (targetUrl.matches(".*/signUp.*")) {
      targetUrl = "/main";
      
    } else if (targetUrl.matches(".*/userFind.*")) {
      targetUrl = "/main";
      
    } else if (targetUrl.matches(".*/userFindResult.*")) {
      targetUrl = "/main";
      
    } else if (targetUrl.matches(".*/welcome.*")) {
      targetUrl = "/main";
      
    }
    
    redirectStrategy.sendRedirect(request, response, targetUrl);
    
  }
  
  private void useSessionUrl(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
    log.debug("뒤로가기 테스트 useSessionUrl = " + request.getRequestURI());
    
    SavedRequest savedRequest = requestCache.getRequest(request, response);
    String targetUrl = savedRequest.getRedirectUrl();
    redirectStrategy.sendRedirect(request, response, targetUrl);
  }
  
  private void useRefererUrl(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
    log.debug("뒤로가기 테스트 useRefererUrl = " + request.getRequestURI());
    
    String targetUrl = request.getHeader("REFERER");
    redirectStrategy.sendRedirect(request, response, targetUrl);
  }
  
  private void useDefaultUrl(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
    log.debug("뒤로가기 테스트 useDefaultUrl = " + request.getRequestURI());
    
    redirectStrategy.sendRedirect(request, response, defaultUrl);
  }
  

  
  
  /**
   - 인증 성공후 어떤 URL로 redirect할지 결정한다.
   - 판단 기준은 targetURLParameter 값을 읽은 URL이 존재할 경우 그것을 1순위.
   - 1순위 URL이 없을 경우 Spring Security가 세션에 저장한 URL을 2순위.
   - 2순위 URL이 없을 경우 Request의 REFERER를 사용하고 그 REFERER URL이 존재할 경우 그  URL을 3순위.
   - 3순위 URL이 없을 경우 Default URL을 4순위로 한다.
   
   @param request
   
   @param response
   
   @return 1: targetUrlParameter 값을 읽은 URL.
           2: Session에 저장되어 있는 URL
           3: referer 헤더에 있는 url
           0: default url
   */
  
  private int decideRedirectStrategy(HttpServletRequest request, HttpServletResponse response) {
    log.debug("decideRedirectStrategy = " + request.toString());
    
    int result = 0;
    
    SavedRequest savedRequest = requestCache.getRequest(request, response);
    
    if (!"".equals(targetUrlParameter)) {
      String targetUrl = request.getParameter(targetUrlParameter);
        if (StringUtils.hasText(targetUrl)) {
          result = 1;
          log.debug("========================= result = " + result);
          
        } else {
          if (savedRequest != null) {
            result = 2;
            log.debug("========================= result = " + result);
            
          } else {
            String refererUrl = request.getHeader("REFERER");
            if (useReferer && StringUtils.hasText(refererUrl)) {
              result = 3;
              log.debug("========================= result = " + result);
              
            } else {
              result = 0;
              log.debug("========================= result = " + result);
              
            }
          }
        }
        
        return result;
    }
    
    if (savedRequest != null) {
      result = 2;
      return result;
    }
    
    String refererUrl = request.getHeader("REFERER");
    
    if (useReferer && StringUtils.hasText(refererUrl)) {
    result = 3;
    
  } else {
    result = 0;
    
  }
  return result;

}
  

  
}

