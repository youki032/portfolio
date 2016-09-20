package net.spring.board.service;

import net.spring.board.vo.ArticleLog;
import net.spring.board.vo.CommentLog;
import net.spring.board.vo.UserLog;

public interface LogService {
  
  /** ===== UserLog =====  */
  //유저 login, logout, signUp log
  public void addUserLog(UserLog userLog, String selecter) throws Exception;
  
  
  /** ===== ArticleLog =====  */
  public void addArticleLog(ArticleLog articleLog, String selecter) throws Exception;
  
  
  /** ===== CommentLog =====  */
  public void addCommentLog(CommentLog commentLog, String selecter) throws Exception;
  
}
