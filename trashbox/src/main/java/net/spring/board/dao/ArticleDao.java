package net.spring.board.dao;

import java.util.List;
import java.util.Map;

import net.spring.board.vo.Article;
import net.spring.board.vo.User;
import net.spring.board.vo.UserInfo;

/* mybatis의 MapperScannerConfigurer에서 사용할 인터페이스
 * => SQL 문을 찾을 때, 인터페이스 이름과 메서드 이름을 사용하여 찾는다.
 * => SQL 파일에서 namespace 이름과 SQL문의 id 값이 위의 이름과 일치해야 한다.
 * => 메서드의 파라미터 개수는 반드시 한 개여야 한다.
 */

public interface ArticleDao {  
  
  
  
  /**==========================  insert(add)  ===============================*/
  //글쓰기
  int insertArticle(Article article) throws Exception;
   
  
  
  
  /**==========================  select(get)  ===============================*/
  //게시글내용 불러오기
  Article selectArticle(Article article) throws Exception;
  
  //게시글전체개수
  int selectArticleListToTalCnt(Map<String, Object> totalListMap) throws Exception;
  
  //userHistory의 정보를 나타내기 위해 해당 게시글을 쓴 유저의 번호를 가져온다.
  int selectHistoryUserNo(Map<String, Object> userNoMap) throws Exception;
  
  
  //가져온 userNo값에 해당하는 User 정보를 가져온다
  User selectHistoryUser(int userNo) throws Exception;
  //가져온 userNo값에 해당하는 UserInfo 정보를 가져온다
  UserInfo selectHistoryUserInfo(int userNo) throws Exception;
  
  
  /**==========================  list(list)   ===============================*/
  //게시글목록 불러오기
  List<Article> articleList(Map<String, Object> listMap) throws Exception;
  
  
  
  
  
  /**==========================  update(change)  ============================*/
  
  //게시글 수정
  int updateArticle(Article article) throws Exception;
  
  //조회수 증가
  int updateArticleView(Article article) throws Exception;
  
  
//  int updateFileUpload(FileBean fileBean) throws Exception;
  
  
  
  /**==========================  delete(remove)  ============================*/
  //게시글 수정에서 파일삭제
  int deleteAttachFile(String fileValue) throws Exception;
  
  //게시글 삭제
  int deleteArticle(Article article) throws Exception;
  
 
  
}





