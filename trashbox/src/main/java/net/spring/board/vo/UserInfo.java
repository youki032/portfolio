package net.spring.board.vo;

import java.io.Serializable;


public class UserInfo implements Serializable {
  private static final long serialVersionUID = 1L;
  
  protected int     userNo;
  
  protected String  birthDate;           //생년월일
  protected String  userClass;           //회원등급
  protected int     attendance;             //출석
  protected String  attendanceDate;
  protected String  signDate;
  protected String  phone;               //휴대전화
  protected String  address1;            //우편번호
  protected String  address2;            //앞 주소
  protected String  address3;            //상제주소
  protected String  selfIntroduction;    //자기소개
  protected String  profilePath;         //프로필파일
  protected String  temp;
  
  protected String  prevNicName;
  protected String  nicName;
  protected String  userName;
  protected String  nicChangeDate;
  
  
  

  public String getAttendanceDate() {
    return attendanceDate;
  }


  public void setAttendanceDate(String attendanceDate) {
    this.attendanceDate = attendanceDate;
  }


  public String getSignDate() {
    return signDate;
  }


  public void setSignDate(String signDate) {
    this.signDate = signDate;
  }


  public String getNicChangeDate() {    
    return nicChangeDate;
  }


  public void setNicChangeDate(String nicChangeDate) {
    this.nicChangeDate = nicChangeDate;
  }


  public String getPrevNicName() {
    return prevNicName;
  }


  public void setPrevNicName(String prevNicName) {
    this.prevNicName = prevNicName;
  }
  
  public int getUserNo() {
    return userNo;
  }

  public void setUserNo(int userNo) {
    this.userNo = userNo;
  }
  

  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public String getUserClass() {
    return userClass;
  }

  public void setUserClass(String userClass) {
    this.userClass = userClass;
  }

  public int getAttendance() {
    return attendance;
  }

  public void setAttendance(int attendance) {
    this.attendance = attendance;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getAddress3() {
    return address3;
  }

  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  public String getSelfIntroduction() {
    return selfIntroduction;
  }

  public void setSelfIntroduction(String selfIntroduction) {
    this.selfIntroduction = selfIntroduction;
  }

  public String getProfilePath() {
    return profilePath;
  }

  public void setProfilePath(String profilePath) {
    this.profilePath = profilePath;
  }

  
  
  public String getTemp() {
    return temp;
  }

  public void setTemp(String temp) {
    this.temp = temp;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getNicName() {
    return nicName;
  }
  
  public void setNicName(String nicName) {
    this.nicName = nicName;
  }


  @Override
  public String toString() {
    return "UserInfo [userNo=" + userNo + ", birthDate=" + birthDate + ", userClass=" + userClass + ", attendance="
        + attendance + ", attendanceDate=" + attendanceDate + ", signDate=" + signDate + ", phone=" + phone
        + ", address1=" + address1 + ", address2=" + address2 + ", address3=" + address3 + ", selfIntroduction="
        + selfIntroduction + ", profilePath=" + profilePath + ", temp=" + temp + ", prevNicName=" + prevNicName
        + ", nicName=" + nicName + ", userName=" + userName + ", nicChangeDate=" + nicChangeDate + "]";
  }



 
}