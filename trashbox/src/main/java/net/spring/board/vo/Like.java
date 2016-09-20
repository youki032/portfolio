package net.spring.board.vo;

import java.io.Serializable;

public class Like implements Serializable {
  private static final long serialVersionUID = 1L;
  
  protected int      likeNo;
  protected int      userNo;
  protected int      boardNo;
  protected int      articleNo;
  protected int      likeCnt;
  protected boolean  checkLike;
  public int getLikeNo() {
    return likeNo;
  }
  public void setLikeNo(int likeNo) {
    this.likeNo = likeNo;
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
  public int getLikeCnt() {
    return likeCnt;
  }
  public void setLikeCnt(int likeCnt) {
    this.likeCnt = likeCnt;
  }
  public boolean isCheckLike() {
    return checkLike;
  }
  public void setCheckLike(boolean checkLike) {
    this.checkLike = checkLike;
  }
  
  @Override
  public String toString() {
    return "Like [likeNo=" + likeNo + ", userNo=" + userNo + ", boardNo=" + boardNo + ", articleNo=" + articleNo
        + ", likeCnt=" + likeCnt + ", checkLike=" + checkLike + "]";
  }

  
  
}
