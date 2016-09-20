package net.spring.board.security;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import net.spring.board.vo.User;

public class MyUserDetails implements UserDetails {
  private static final long serialVersionUID = 1L;
  
  protected int       userNo;
  protected String    email;
  protected String    password;
  protected String    nicName;
  protected String    userName;
  protected boolean   enabled;
  protected String    signDate;
  
//  private Integer     ID;
  
  private boolean     accountNonExpired;
  private boolean     accountNonLocked;
  private boolean     credentialsNonExpired;
  private Collection<? extends GrantedAuthority> authorities;
  
  
  public MyUserDetails(int userNo, String email, String password, String nicName,
      String userName, boolean enabled, Date signDate, boolean unlocked,
      Collection<? extends GrantedAuthority> authorities) {
    this.userNo = userNo;
    this.email = email;
    this.password = password;
    this.nicName = nicName;
    this.userName = userName;
    this.enabled = enabled;
    this.signDate = formatDate(signDate);
    this.accountNonExpired = true;
    this.accountNonLocked = unlocked;
    this.credentialsNonExpired = true;
    this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }
  
  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }


  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  
  

  public void setPassword(String password) {
    this.password = password;
  }


  public int getUserNo() {
    return userNo;
  }
  
  
  public void setUserNo(int userNo) {
    this.userNo = userNo;
  }



  public String getNicName() {
    return nicName;
  }


  public void setNicName(String nicName) {
    this.nicName = nicName;
  }


  public String getUserName() {
    return userName;
  }


  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getSignDate() {
    return signDate;
  }


  public void setSignDate(String signDate) {
    this.signDate = signDate;
  }

  
//  public Integer getID() {
//    return ID;
//  }
//
//  public void setID(Integer iD) {
//    ID = iD;
//  }


  @Override
  public String toString() {
    return "MyUserDetails [userNo=" + userNo + ", email=" + email + ", password=" + password + ", nicName=" + nicName
        + ", userName=" + userName + ", enabled=" + enabled + ", signDate=" + signDate + ", accountNonExpired="
        + accountNonExpired + ", accountNonLocked=" + accountNonLocked + ", credentialsNonExpired="
        + credentialsNonExpired + ", authorities=" + authorities + "]";
  }


  private String formatDate(Date date) {
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
    return dateFormat.format(date);
  }
  
  
  private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
    Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
    
    SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(new AuthorityComparator());
    
    for(GrantedAuthority grantedAuthority : authorities) {
      Assert.notNull(grantedAuthority,"GrantedAuthority list cannot contain any null elements");
      sortedAuthorities.add(grantedAuthority);
    }
    
    return sortedAuthorities;
  }
  
  private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    
    public int compare(GrantedAuthority g1, GrantedAuthority g2) {
      
      if(g2.getAuthority() == null) {
        return -1;
      }
      
      if(g1.getAuthority() == null) {
        return 1;
      }
      
      return g1.getAuthority().compareTo(g2.getAuthority());
    }
  }
  
  
  
//  @Override
//  public boolean equals(Object obj) {
//    System.out.println("equalsequalsequalsequalsequalsequalsequalsequalsequalsequalsequalsequalsequalsequalsequals");
//    if (!(obj instanceof MyUserDetails)) {
//    
//      return false;
//    }
//    
//    MyUserDetails other = (MyUserDetails) obj;
//    
//    if ((this.getID() == null && other.getID() != null) || 
//        (this.getID() != null && !this.getID().equals(other.getID()))) {
//      
//      return false;
//      
//    }
//    
//    return true;
//  }
//  
//  @Override
//  public int hashCode() {
//    System.out.println("hashCodehashCodehashCodehashCodehashCodehashCodehashCode");
//    return (ID != null ? ID.hashCode() : 0);
//  }
  

}
