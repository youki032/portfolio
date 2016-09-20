package net.spring.board.vo;

import java.io.Serializable;

public class UserClass implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected int classNo;
    protected int userNo;
    protected int userLevel;
    protected int nowExp;
    protected int nextExp;
    
    protected double progressExp;
    
    public int getClassNo() {
      return classNo;
    }
    public void setClassNo(int classNo) {
      this.classNo = classNo;
    }
    public int getUserNo() {
      return userNo;
    }
    public void setUserNo(int userNo) {
      this.userNo = userNo;
    }
    public int getUserLevel() {
      return userLevel;
    }
    public void setUserLevel(int userLevel) {
      this.userLevel = userLevel;
    }
    public int getNowExp() {
      return nowExp;
    }
    public void setNowExp(int nowExp) {
      this.nowExp = nowExp;
    }
    public int getNextExp() {
      return nextExp;
    }
    public void setNextExp(int nextExp) {
      this.nextExp = nextExp;
    }
    
    public double getProgressExp() {
      this.progressExp = (int)(((double)this.nowExp/this.nextExp)*100);
      
      return progressExp;
    }
    
    public void setProgressExp(double progressExp) {
      this.progressExp = progressExp;
    }
    
    @Override
    public String toString() {
      return "UserClass [classNo=" + classNo + ", userNo=" + userNo + ", userLevel=" + userLevel + ", nowExp=" + nowExp
          + ", nextExp=" + nextExp + ", progressExp=" + progressExp + "]";
    }
  
    
}
