package net.spring.board.vo;

import java.io.Serializable;

public class Role implements Serializable {
  private static final long serialVersionUID = 1L;

  protected String    authority;
  protected String    email;
  protected int       userNo;
  protected int       authNo;
  
  
  public String getAuthority() {
    return authority;
  }
  public void setAuthority(String authority) {
    this.authority = authority;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public int getUserNo() {
    return userNo;
  }
  public void setUserNo(int userNo) {
    this.userNo = userNo;
  }
  public int getAuthNo() {
    return authNo;
  }
  public void setAuthNo(int authNo) {
    this.authNo = authNo;
  }
  @Override
  public String toString() {
    return "MyUserRole [authority=" + authority + ", email=" + email + ", userNo=" + userNo + ", authNo="
        + authNo + "]";
  }
  
  
  

}
