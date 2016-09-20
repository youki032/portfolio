package net.spring.board.vo;

import java.io.Serializable;

public class UserActivity implements Serializable {
  private static final long serialVersionUID = 1L;
  
  protected int userNo; 
  protected int articleWriteCnt; 
  protected int articleRemoveCnt;
  protected int commentWriteCnt;
  protected int commentRemoveCnt;
  
  
  public int getUserNo() {
    return userNo;
  }
  public void setUserNo(int userNo) {
    this.userNo = userNo;
  }
  public int getArticleWriteCnt() {
    return articleWriteCnt;
  }
  public void setArticleWriteCnt(int articleWriteCnt) {
    this.articleWriteCnt = articleWriteCnt;
  }
  public int getArticleRemoveCnt() {
    return articleRemoveCnt;
  }
  public void setArticleRemoveCnt(int articleRemoveCnt) {
    this.articleRemoveCnt = articleRemoveCnt;
  }
  public int getCommentWriteCnt() {
    return commentWriteCnt;
  }
  public void setCommentWriteCnt(int commentWriteCnt) {
    this.commentWriteCnt = commentWriteCnt;
  }
  public int getCommentRemoveCnt() {
    return commentRemoveCnt;
  }
  public void setCommentRemoveCnt(int commentRemoveCnt) {
    this.commentRemoveCnt = commentRemoveCnt;
  }
  
  @Override
  public String toString() {
    return "UserActivity [userNo=" + userNo + ", articleWriteCnt=" + articleWriteCnt + ", articleRemoveCnt="
        + articleRemoveCnt + ", commentWriteCnt=" + commentWriteCnt + ", commentRemoveCnt=" + commentRemoveCnt + "]";
  }
 
}
