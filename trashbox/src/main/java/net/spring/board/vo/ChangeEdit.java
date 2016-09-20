package net.spring.board.vo;

import java.io.Serializable;

public class ChangeEdit implements Serializable {
  private static final long serialVersionUID = 1L;  
  protected int userNo;
 
  protected String newPassword;
  protected String newNicName;
  protected String changeEditEmail;
  protected String nicChangeDate;
  
  
  
  
  
  
  
  public String getNicChangeDate() {
    return nicChangeDate;
  }

  public void setNicChangeDate(String nicChangeDate) {
    this.nicChangeDate = nicChangeDate;
  }

  public int getUserNo() {
    return userNo;
  }

  public void setUserNo(int userNo) {
    this.userNo = userNo;
  }

  public String getNewPassword() {
    return newPassword;
  }
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  
  public String getNewNicName() {
    return newNicName;
  }

  public void setNewNicName(String newNicName) {
    this.newNicName = newNicName;
  }

  public String getChangeEditEmail() {
    return changeEditEmail;
  }


  public void setChangeEditEmail(String changeEditEmail) {
    this.changeEditEmail = changeEditEmail;
  }

  @Override
  public String toString() {
    return "ChangeEdit [userNo=" + userNo + ", newPassword=" + newPassword + ", newNicName=" + newNicName
        + ", changeEditEmail=" + changeEditEmail + ", nicChangeDate=" + nicChangeDate + "]";
  }


  
  
}
