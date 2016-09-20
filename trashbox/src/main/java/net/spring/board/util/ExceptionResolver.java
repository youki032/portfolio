package net.spring.board.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class ExceptionResolver extends SimpleMappingExceptionResolver {
  protected Logger log = LogManager.getLogger(ExceptionResolver.class);
  
  /**
   * SimpleMappingExceptionResolver를 bean으로 등록해 바로 사용하지않고
   * 상속받는 클래스를 만들어서 로그를 남기게 만듬. 
   * */
  
  
  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) {
    
    request.setAttribute("errormsg", ex);
    
    log.error("#resolveException#ERROR::resolveException::", ex);
    log.debug("#resolveException#ERROR::resolveException::" + ex + "::####::"+request.getRequestURI());
    
    return super.resolveException(request, response, handler, ex);
  }

}
