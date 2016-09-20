package net.spring.board.dao;

import java.util.List;
import java.util.Map;

import net.spring.board.vo.Comment;

public interface CommentDao {
  
  
  
  /**==========================  insert(add)  ===============================*/
  //댓글쓰기
  public int insertComment(Comment comments) throws Exception;
  
    
  
  /**==========================  select(get)  ===============================*/
  //comment 전체 갯수 가져오기
  int selectCommentToTalListCnt(Map<String, Integer> commentMap) throws Exception;
  
  
  //comment내용 가져오기
  Comment selectComment(Comment comment) throws Exception;
  
  
  
  
  /**==========================  list(list)   ===============================*/
  //댓글목록 불러오기
  public List<Comment> commentList(Map<String, Integer> commentMap) throws Exception;
  
  

  /**==========================  update(change)  ============================*/
  //게시글타이틀 옆 총 댓글수
  public int updateTitleCommentCnt(Comment comments) throws Exception;
  
  

  /**==========================  delete(remove)  ============================*/
  //댓글 삭제
  int deleteComment(Comment comments) throws Exception;
  
  

}
