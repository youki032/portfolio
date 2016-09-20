package net.spring.board.dao;

import net.spring.board.vo.ArticleLog;
import net.spring.board.vo.CommentLog;
import net.spring.board.vo.UserLog;

public interface LogDao {

  /** ===== UserLog =====  */
  //로그인, 로그아웃, 회원가입, 탈퇴 로그  
  int insertUserLog(UserLog userLog) throws Exception;
  
  int etcUserLog(UserLog userLog) throws Exception;
  
  
  /** ===== ArticleLog =====  */
  //게시판 글쓰기 로그
  int insertArticleLog(ArticleLog articleLog) throws Exception;
  
  //게시판 상세페이지보기, 수정, 삭제 로그
  int etcArticleLog(ArticleLog articleLog) throws Exception;
  
  //마이 페이지 활동내역 작성게시글 가져오기
  int selectArticleWriteCnt(int userNo) throws Exception;
  
  //마이 페이지 활동내역 삭제게시글 가져오기
  int selectArticleRemoveCnt(int userNo) throws Exception;
  
  
  
  /** ===== CommentLog =====  */
  
  //댓글쓰기 로그
  int insertCommentLog(CommentLog commentsLog) throws Exception;
  
  //댓글 쓰기, 삭제 로그
  int etcCommentLog(CommentLog commentsLog) throws Exception;
  
  //마이페이지 활동내역 작성댓글 가져오기
  int selectCommentWriteCnt(int userNo) throws Exception;
  
  //마이페이지 활동내역 삭제댓글 가져오기
  int selectCommentRemoveCnt(int userNo) throws Exception;
  
}
