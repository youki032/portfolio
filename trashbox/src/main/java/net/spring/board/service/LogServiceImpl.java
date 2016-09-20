package net.spring.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spring.board.dao.LogDao;
import net.spring.board.vo.ArticleLog;
import net.spring.board.vo.CommentLog;
import net.spring.board.vo.UserLog;

@Service("LogService")
public class LogServiceImpl implements LogService {

  @Autowired 
  private LogDao logDao;
  
  //유저 login, logout, signUp log
  public void addUserLog(UserLog userLog, String selecter) throws Exception {
    
    if(selecter.equals("add")) {
      logDao.insertUserLog(userLog);
      
    } else {
      logDao.etcUserLog(userLog);
      
    }
    
  }
  
  
  public void addArticleLog(ArticleLog articleLog, String selecter) throws Exception {
    
    if(selecter.equals("add")) {
      logDao.insertArticleLog(articleLog);
      
    } else {
      logDao.etcArticleLog(articleLog);
      
    }
        
  }
  
  
  public void addCommentLog(CommentLog commentLog, String selecter) throws Exception {
    
    if(selecter.equals("add")) {
      logDao.insertCommentLog(commentLog);
      
    } else {
      logDao.etcCommentLog(commentLog);
      
    }
        
  }
  
  
 
}
