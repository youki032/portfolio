package net.spring.board.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import net.spring.board.vo.Article;
import net.spring.board.vo.Comment;
import net.spring.board.vo.ImageBean;
import net.spring.board.vo.User;
import net.spring.board.vo.UserInfo;

public interface ArticleService {
  
  
  
  /**===========================  add  ===================================*/
  
  //글쓰기
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public int addArticle(Article article) throws Exception;
  
  //댓글쓰기
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public int addComment(Comment comments) throws Exception;
  
  //article테이블에 추천개수 추가
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public int addArticleLike(Map<String, Integer> likeMap, String nicName) throws Exception;
  
 
  
  
  /**===========================  get  ===================================*/
  
  //게시판 상세정보 가져오기
  public Article getArticle(Article article) throws Exception;
  
  
  //게시판수정용 article정보 가져오기, 권한과 article 테이블에 기록된 userNodhk 로그인한 userNo를 비교하여 접근유무를 설정한다.
  @PostAuthorize("isAuthenticated() and returnObject.userNo == principal.userNo or hasAnyRole('ROLE_MASTER')")
  public Article getAuthorizeArticle(Article article, Principal principal) throws Exception;
  
  
  
  //게시판수정용 comment정보 가져오기, 권한과 comment 테이블에 기록된 userNo와 로그인한 userNo를 비교하여 접근유무를 설정한다.
  @PostAuthorize("isAuthenticated() and returnObject.userNo == principal.userNo or hasAnyRole('ROLE_MASTER')")
  public Comment getAuthorizeComment(Comment comment, Principal principal) throws Exception;
 
  
  //게시판 총 글수 가져오기
  public int getArticleListTotalNum(Map<String, Object> totalListMap) throws Exception;
  
  
  //user가 해당 게시글에 중복추천하였는지 체크
  public int getLikeFlag(Map<String, Integer> likeMap) throws Exception;
  
  
  //댓글 총 글수 가져오기
  public int getCommentListTotalNum(Map<String, Integer> commentMap) throws Exception;
  
  
  //userHistory의 정보를 나타내기 위해 해당 게시글을 쓴 유저의 번호를 가져온다.
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public int getHistoryUserNo(HttpServletRequest request) throws Exception;
  
  //가져온 userNo값에 해당하는 User 정보를 가져온다
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public User getHistoryUser(int userNo) throws Exception;
  
  //가져온 userNo값에 해당하는 UserInfo 정보를 가져온다
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public UserInfo getHistoryUserInfo(int userNo) throws Exception;

  
  /**===========================  list  ===================================*/
  
  //게시판목록 가져오기
  public List<Article> articleList(Map<String, Object> listMap) throws Exception;
  
  //댓글리스트 가져오기
  public List<Comment> commentList(Map<String, Integer> commentMap) throws Exception;
  
  
  
  
  
  
  /**===========================  change  ================================*/
  
  //글수정
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public int changeArticle(Article article) throws Exception;
  
  
  
  
  
  
  
  /**===========================  remove  =================================*/
  
  //게시판 글 지우기
//  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
//  @PreAuthorize("#article.userNo == principal.userNo")
  public int removeArticle(Article article) throws Exception;

  //게시판 댓글 지우기
//  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
//  @PreAuthorize("isAuthenticated() and #article.userNo == principal.userNo or hasAnyRole('ROLE_MASTER')")
  public int removeComment(Comment comments) throws Exception;
  
  //게시판 첨부파일 지우기
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public int removeAttachFile(String fileValue) throws Exception;

 
  
  //글쓰기, 글수정에서 back space시 업로드했던 임시파일을 삭제한다.
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public void removeTempFile() throws Exception;
  
  
  
  
  /**========================  file up & down  =============================*/
  
  //file & multi image 업로드. 
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public Map<String, String> addFileUpload(MultipartFile file, String inputType) throws Exception;
  
  
  //image 단일 업로드. 
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public Model addImageUpload(Model model, HttpServletRequest request, ImageBean imageBean) throws Exception;
  
  
  //file 다운로드. 
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public void getFileDownLoad(HttpServletRequest request, HttpServletResponse response) throws Exception;
  
}
