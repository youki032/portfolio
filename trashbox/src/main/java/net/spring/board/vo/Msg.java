package net.spring.board.vo;

import java.io.Serializable;
import java.sql.Date;

public class Msg implements Serializable {
  private static final long serialVersionUID = 1L;
  
  protected int       msgNo; 
  protected int       toUserNo;
  protected String    toUserEmail; 
  protected String    toUserNicName;
  protected Date      createDate;
  protected boolean   msgFlag;
  protected String    msgContent; 
  protected int       fromUserNo;
  protected String    fromUserEmail;
  protected String    fromUserNicName;
  
  
  protected int       articleNo;
  protected int       boardNo;
  
  
  
  public int getArticleNo() {
    return articleNo;
  }
  public void setArticleNo(int articleNo) {
    this.articleNo = articleNo;
  }
  public int getMsgNo() {
    return msgNo;
  }
  public void setMsgNo(int msgNo) {
    this.msgNo = msgNo;
  }
  public boolean isMsgFlag() {
    return msgFlag;
  }
  public void setMsgFlag(boolean msgFlag) {
    this.msgFlag = msgFlag;
  }
 
  public String getMsgContent() {
    return msgContent;
  }
  public void setMsgContent(String msgContent) {
    this.msgContent = msgContent;
  }

  public int getBoardNo() {
    return boardNo;
  }
  public void setBoardNo(int boardNo) {
    this.boardNo = boardNo;
  }
  public String getFromUserNicName() {
    return fromUserNicName;
  }
  public void setFromUserNicName(String fromUserNicName) {
    this.fromUserNicName = fromUserNicName;
  }
  public String getToUserNicName() {
    return toUserNicName;
  }
  public void setToUserNicName(String toUserNicName) {
    this.toUserNicName = toUserNicName;
  }
  public int getToUserNo() {
    return toUserNo;
  }
  public void setToUserNo(int toUserNo) {
    this.toUserNo = toUserNo;
  }
  public int getFromUserNo() {
    return fromUserNo;
  }
  public void setFromUserNo(int fromUserNo) {
    this.fromUserNo = fromUserNo;
  }
  public String getToUserEmail() {
    return toUserEmail;
  }
  public void setToUserEmail(String toUserEmail) {
    this.toUserEmail = toUserEmail;
  }
  public String getFromUserEmail() {
    return fromUserEmail;
  }
  public void setFromUserEmail(String fromUserEmail) {
    this.fromUserEmail = fromUserEmail;
  }
  public Date getCreateDate() {    
    return createDate;
  }
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
  @Override
  public String toString() {
    return "ToMsg [msgNo=" + msgNo + ", toUserNo=" + toUserNo + ", toUserEmail=" + toUserEmail + ", toUserNicName="
        + toUserNicName + ", createDate=" + createDate + ", msgFlag=" + msgFlag + ", msgContent=" + msgContent
        + ", fromUserNo=" + fromUserNo + ", fromUserEmail=" + fromUserEmail + ", fromUserNicName=" + fromUserNicName
        + ", articleNo=" + articleNo + ", boardNo=" + boardNo + "]";
  }

}
