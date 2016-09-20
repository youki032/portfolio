package net.spring.board.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

public class MyUserDetailsChecker implements UserDetailsChecker {
  protected Logger log = LogManager.getLogger(MyUserDetailsChecker.class);
  protected final MessageSourceAccessor messages = SpringSecurityMessageSource
      .getAccessor();

  public void check(UserDetails user) {
    log.debug("checkError" + user);
    
    if (!user.isCredentialsNonExpired()) {
      throw new CredentialsExpiredException(messages.getMessage(
          "AccountStatusUserDetailsChecker.credentialsExpired",
          "User credentials have expired"));
    }
  }
}
