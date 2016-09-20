package net.spring.board.dao;

import java.util.Map;

public interface LikeDao {
  
  
  
  /**==========================  insert(add)  ===============================*/
  
  //추천테이블에 중복여부 정보주입
  int insertLikeFlag(Map<String, Integer> likeMap) throws Exception;
  
  
  
  /**==========================  select(get)  ===============================*/
  
  //추천수 가져오기
  int selectLikeToTalNum(Map<String, Integer> likeMap) throws Exception;
  
  //추천수 검증
  int selectLikeFlag(Map<String, Integer> likeMap) throws Exception;
  
  
  
  /**==========================  update(change)  ============================*/
  
  //추천수 증가
  int updateArticleLikeCnt(Map<String, Integer> likeMap) throws Exception;
  
 
  
}
