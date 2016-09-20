package net.spring.board.security;

import java.io.Serializable;

public class MyUserRole implements Serializable {
  private static final long serialVersionUID = 1L;

  protected String    authorityName;
  protected String    email;
  protected int       userNo;
  protected int       authNo;
  
  
  public String getAuthorityName() {
    return authorityName;
  }
  public void setAuthorityName(String authorityName) {
    this.authorityName = authorityName;
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
    return "MyUserRole [authorityName=" + authorityName + ", email=" + email + ", userNo=" + userNo + ", authNo="
        + authNo + "]";
  }
  
  
  

}
