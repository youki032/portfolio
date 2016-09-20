package net.spring.board.vo;

import java.io.Serializable;

public class Comment implements Serializable {
  private static final long serialVersionUID = 1L; 
  
  protected int     commentNo;
  protected int     listNo;
  protected int     userNo;
  protected int     articleNo;
  protected int     boardNo;
  protected String  nicName;
  protected String  contents;
  protected String  createDate;
  
  
  //join용 변수
  protected String  profilePath;
  
  //userLevel 불러오기 변수
  protected String  userLevel; 
  
  
  
  public String getProfilePath() {
    return profilePath;
  }
  public void setProfilePath(String profilePath) {
    this.profilePath = profilePath;
  }
  
  public int getCommentNo() {
    return commentNo;
  }
  public void setCommentNo(int commentNo) {
    this.commentNo = commentNo;
  }
  public int getListNo() {
    return listNo;
  }
  public void setListNo(int listNo) {
    this.listNo = listNo;
  }
  public int getArticleNo() {
    return articleNo;
  }
  public void setArticleNo(int articleNo) {
    this.articleNo = articleNo;
  }
  public int getUserNo() {
    return userNo;
  }
  public void setUserNo(int userNo) {
    this.userNo = userNo;
  }

  public String getContents() {
    return contents;
  }
  public void setContents(String contents) {
    this.contents = contents;
  }
  public String getCreateDate() {
    return createDate;
  }
  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }
  
  public int getBoardNo() {
    return boardNo;
  }
  public void setBoardNo(int boardNo) {
    this.boardNo = boardNo;
  }
  public String getNicName() {
    return nicName;
  }
  public void setNicName(String nicName) {
    this.nicName = nicName;
  }
  public String getUserLevel() {
    return userLevel;
  }
  public void setUserLevel(String userLevel) {
    this.userLevel = userLevel;
  }
  
  @Override
  public String toString() {
    return "Comment [commentNo=" + commentNo + ", listNo=" + listNo + ", userNo=" + userNo + ", articleNo=" + articleNo
        + ", boardNo=" + boardNo + ", nicName=" + nicName + ", contents=" + contents + ", createDate=" + createDate
        + ", profilePath=" + profilePath + ", userLevel=" + userLevel + "]";
  }
  
  
}
