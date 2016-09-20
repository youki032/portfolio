package net.spring.board.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.spring.board.dao.RoleDao;
import net.spring.board.dao.UserDao;
import net.spring.board.vo.Role;
import net.spring.board.vo.User;

@Service
public class MyUserDetailsService implements UserDetailsService {
  protected final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
  protected Logger log = LogManager.getLogger(MyUserDetailsService.class);
  
  @Autowired 
  private UserDao userDao;
  @Autowired 
  private RoleDao roleDao;
  

  
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = null;
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    log.debug("email = " + email);
    try {
      
      int userNo = userDao.selectUserNo(email);
      
      if (userNo != -1) {
        user = userDao.selectUser(userNo);
        authorities = buildUserAuthorities(roleDao.selectRole(userNo));

        log.debug("user = " + user + "authorities = " + authorities);
      }
    
    
    } catch(Exception e) {
      throw new UsernameNotFoundException(messages.getMessage(
          "user notFound", new Object[] { email },
          "User {0} notFound"));
    }
    
    return buildUserForAuthentication(user, authorities);
  }
  
  
  
  private MyUserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
    MyUserDetails myUserDetails = new MyUserDetails(user.getUserNo(), user.getEmail(), user.getPassword(), 
        user.getNicName(), user.getUserName(), user.isEnabled(), user.getSignDate(), user.isUnlocked() , authorities);
    
    log.debug("myUserDetails = " + myUserDetails);
   return myUserDetails;
   
  }
  
  private List<GrantedAuthority> buildUserAuthorities(List<Role> roles) {
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    
    for(Role role : roles) {
      authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
    }
    
    log.debug("authorities = " + authorities);
    return authorities;
  } 
 
  
}
