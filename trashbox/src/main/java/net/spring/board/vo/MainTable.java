package net.spring.board.vo;

public class MainTable {
  
  private int listNo;
  private int articleNo;
  private int boardNo;
  private int likeCnt;
  private int views;
  private String title;
  private String createDate;
  private String content;
  
  
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
  public int getBoardNo() {
    return boardNo;
  }
  public void setBoardNo(int boardNo) {
    this.boardNo = boardNo;
  }
  public int getLikeCnt() {
    return likeCnt;
  }
  public void setLikeCnt(int likeCnt) {
    this.likeCnt = likeCnt;
  }
  public int getViews() {
    return views;
  }
  public void setViews(int views) {
    this.views = views;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  
  
  @Override
  public String toString() {
    return "MainTable [listNo=" + listNo + ", articleNo=" + articleNo + ", boardNo=" + boardNo + ", likeCnt=" + likeCnt
        + ", views=" + views + ", title=" + title + ", createDate=" + createDate + ", content=" + content + "]";
  }
  
}
