package net.spring.board.error;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class MyAccessDeniedHandler implements AccessDeniedHandler {
  protected Logger log = LogManager.getLogger(MyAccessDeniedHandler.class);
  private String errorPage;
  
  public void setErrorPage(String errorPage) {
    this.errorPage = errorPage;
  }
  
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException e) throws IOException, ServletException {
    request.getRequestDispatcher(errorPage).forward(request, response);
  }

}
