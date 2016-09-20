package net.spring.board.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Paging implements Serializable {
  private static final long serialVersionUID = 1L;
  
  protected int pageSize;       //pagination 블록사이즈
  protected int firstPageNo;    //첫번쨰 페이지번호
  protected int prevPageNo;     //이전 페이지 번호
  protected int startPageNo;    //시작 페이지(페이징 네비기준)
  
  protected int pageNo;         //페이지 번호
  protected int endPageNo;      //끝 페이지(페이징 네비기준)
  
  protected int nextPageNo;     //다음페이지 번호
  protected int finalPageNo;    //마지막 페이지 번호
  protected int totalCount;     //전체 게시글 수
  
  protected int boardNo;
  protected int articleNo;
  protected int msgListNo;
  
  
  
  
  public int getMsgListNo() {
    return msgListNo;
  }
  public void setMsgListNo(int msgListNo) {
    this.msgListNo = msgListNo;
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
  public int getPageSize() {
    return pageSize;
  }
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
  public int getFirstPageNo() {
    return firstPageNo;
  }
  public void setFirstPageNo(int firstPageNo) {
    this.firstPageNo = firstPageNo;
  }
  public int getPrevPageNo() {
    return prevPageNo;
  }
  public void setPrevPageNo(int prevPageNo) {
    this.prevPageNo = prevPageNo;
  }
  public int getStartPageNo() {
    return startPageNo;
  }
  public void setStartPageNo(int startPageNo) {
    this.startPageNo = startPageNo;
  }
  public int getPageNo() {
    return pageNo;
  }
  public void setPageNo(int pageNo) {
    this.pageNo = pageNo;
  }
  public int getEndPageNo() {
    return endPageNo;
  }
  public void setEndPageNo(int endPageNo) {
    this.endPageNo = endPageNo;
  }
  public int getNextPageNo() {
    return nextPageNo;
  }
  public void setNextPageNo(int nextPageNo) {
    this.nextPageNo = nextPageNo;
  }
  public int getFinalPageNo() {
    return finalPageNo;
  }
  public void setFinalPageNo(int finalPageNo) {
    this.finalPageNo = finalPageNo;
  }
  public int getTotalCount() {
    return totalCount;
  }
  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
    this.makePaging();
  }
  
  private void makePaging() {
    if(this.totalCount == 0) {  //게시글 전체 수가 없는 경우.
      return; 
    }
      
    if(this.pageNo == 0) {      //기본값 설정
      this.setPageNo(1);
    }
    
    if(this.pageSize == 0) {    //기본값 설정
      this.setPageSize(10);
    }
    
    int finalPage = (totalCount + (pageSize - 1)) / pageSize; //마지막 페이지
    
    if(this.pageNo > finalPage) {
      this.setPageNo(finalPage); //기본값 설정
    }
      
    if(this.pageNo < 0 || this.pageNo > finalPage) {
      this.pageNo = 1;  //현재 페이지 유효성 체크
    }
    
    boolean isNowFirst = pageNo == 1? true : false;  //시작 페이지(전체)
    
    boolean isNowFinal = pageNo == finalPage ? true : false; //마지막 페이지(전체)
  
    int startPage = ((pageNo -1)/ 10) * 10 + 1; //시작 페이지(페이징 네비기준)
    
    int endPage = startPage + 10 - 1;  //끝 페이지 (페이징네비 기준)
  
    
    if(endPage > finalPage) {  //[마지막페이지 (페이징네비기준) > 마지막페이지] 보다 큰 경우
      endPage = finalPage;
    }
    
    this.setFirstPageNo(1);  //첫 번쨰 페이지 번호
    
    if(isNowFirst) {
      this.setPrevPageNo(1);  //이전 페이지 번호
    } else {
      this.setPrevPageNo(((pageNo - 1) < 1? 1 : (pageNo - 1))); //이전 페이지 번호
    }
    
    this.setStartPageNo(startPage); //시작 페이지(페이징네비 기준)
    
    this.setEndPageNo(endPage);     //끝 페이지(페이징네비 기준)
    
    if(isNowFinal) {
      this.setNextPageNo(finalPage); //다음페이지 번호
    } else {
      this.setNextPageNo(((pageNo + 1) > finalPage? finalPage : (pageNo + 1))); 
      //다음 페이지 번호
    }
    
    this.setFinalPageNo(finalPage); //마지막 페이지 번호
    
  }
  
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
  

}
