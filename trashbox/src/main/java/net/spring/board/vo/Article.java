package net.spring.board.vo;

import java.io.Serializable;
import java.util.List;

public class Article implements Serializable {
  private static final long serialVersionUID = 1L;
  
  protected int       listNo;
  protected int       articleNo;
  protected int       userNo;
  protected int       boardNo;
  protected int       views;
  protected int       likeCount;
  protected boolean   likeCheck;
  protected String    title;
  protected String    content;
  protected String    createDate;
  protected String    nicName;
  protected String    email;
  protected String    originalFilePath;
  protected String    renameFilePath;
  
  
  protected int           titleCommentCnt;
  protected int           likeCnt;
  protected List<String>  arrayImgName;
  protected String        userLevel;
  
  
  public int getTitleCommentCnt() {
    return titleCommentCnt;
  }
  public void setTitleCommentCnt(int titleCommentCnt) {
    this.titleCommentCnt = titleCommentCnt;
  }
  public int getUserNo() {
    return userNo;
  }
  public void setUserNo(int userNo) {
    this.userNo = userNo;
  }
  public int getBoardNo() {
    return boardNo;
  }
  public void setBoardNo(int boardNo) {
    this.boardNo = boardNo;
  }
  public int getArticleNo() {
    return articleNo;
  }
  public void setArticleNo(int articleNo) {
    this.articleNo = articleNo;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getCreateDate() {
    return createDate;
  }
  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }
  public int getViews() {
    return views;
  }
  public void setViews(int views) {
    this.views = views;
  } 

  public int getListNo() {
    return listNo;
  }
  public void setListNo(int listNo) {
    this.listNo = listNo;
  } 
  public String getNicName() {
    return nicName;
  }
  public void setNicName(String nicName) {
    this.nicName = nicName;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public int getLikeCount() {
    return likeCount;
  }
  public void setLikeCount(int likeCount) {
    this.likeCount = likeCount;
  }
  public boolean isLikeCheck() {
    return likeCheck;
  }
  public void setLikeCheck(boolean likeCheck) {
    this.likeCheck = likeCheck;
  }
  public int getLikeCnt() {
    return likeCnt;
  }
  public void setLikeCnt(int likeCnt) {
    this.likeCnt = likeCnt;
  }
  public String getOriginalFilePath() {
    return originalFilePath;
  }
  public void setOriginalFilePath(String originalFilePath) {
    this.originalFilePath = originalFilePath;
  }
  public String getRenameFilePath() {
    return renameFilePath;
  }
  public void setRenameFilePath(String renameFilePath) {
    this.renameFilePath = renameFilePath;
  }
  public List<String> getArrayImgName() {
    return arrayImgName;
  }
  public void setArrayImgName(List<String> arrayImgName) {
    this.arrayImgName = arrayImgName;
  }
  
  public String getUserLevel() {
    return userLevel;
  }
  public void setUserLevel(String userLevel) {
    this.userLevel = userLevel;
  }
  @Override
  public String toString() {
    return "Article [listNo=" + listNo + ", articleNo=" + articleNo + ", userNo=" + userNo + ", boardNo=" + boardNo
        + ", views=" + views + ", likeCount=" + likeCount + ", likeCheck=" + likeCheck + ", title=" + title
        + ", content=" + content + ", createDate=" + createDate + ", nicName=" + nicName + ", email=" + email
        + ", originalFilePath=" + originalFilePath + ", renameFilePath=" + renameFilePath + ", titleCommentCnt="
        + titleCommentCnt + ", likeCnt=" + likeCnt + ", arrayImgName=" + arrayImgName + ", userLevel=" + userLevel
        + "]";
  }
  
  
 

 

}
