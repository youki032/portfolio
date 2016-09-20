package net.spring.board.vo;

import java.io.Serializable;

public class UserClassPoint implements Serializable {
  private static final long serialVersionUID = 1L;
  
  //활동 적립 포인트
  public static final int ATTENDANCE_POINT     = 50;  //출석
  public static final int CREATE_ARTICLE_POINT = 30;  //창작게시판
  public static final int WOOD_ARTICLE_POINT   = 30;  //목공게시판
  public static final int ELECT_ARTICLE_POINT  = 30;  //전자게시판
  public static final int METAL_ARTICLE_POINT  = 30;  //금속게시판
  public static final int FREE_ARTICLE_POINT   = 10;  //자유게시판
  public static final int HUMOR_ARTICLE_POINT  = 10;  //유머게시판
  public static final int COMMENT_POINT        = 5;   //댓글포인트
  public static final int QnA_POINT            = 5;   //질문게시판
  public static final int LIKE_POINT           = 1;   //추천포인트
  

}
