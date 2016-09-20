package net.spring.board.vo;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
  private static final long serialVersionUID = 1L;
  
  protected int       userNo;                //회원번호
  protected String    email;          //아이디
  protected String    password;       //비밀번호
  protected boolean   enabled;       //계정활성화여부
  protected String    nicName;        //닉네임
  protected String    userName;       //실명
  protected Date      signDate;         //가입일
  protected boolean   unlocked;
  
  protected String birthDate;
  protected String originPassword;
  
  
  public int getUserNo() {
    return userNo;
  }
  public void setUserNo(int userNo) {
    this.userNo = userNo;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public boolean isEnabled() {
    return enabled;
  }
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
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
  public Date getSignDate() {
    return signDate;
  }
  public void setSignDate(Date signDate) {
    this.signDate = signDate;
  }
  public boolean isUnlocked() {
    return unlocked;
  }
  public void setUnlocked(boolean unlocked) {
    this.unlocked = unlocked;
  }
  public String getBirthDate() {
    return birthDate;
  }
  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }
  public String getOriginPassword() {
    return originPassword;
  }
  public void setOriginPassword(String originPassword) {
    this.originPassword = originPassword;
  }
  
  @Override
  public String toString() {
    return "User [userNo=" + userNo + ", email=" + email + ", password=" + password + ", enabled=" + enabled
        + ", nicName=" + nicName + ", userName=" + userName + ", signDate=" + signDate + ", unlocked=" + unlocked
        + ", birthDate=" + birthDate + ", originPassword=" + originPassword + "]";
  }
  
  


}
