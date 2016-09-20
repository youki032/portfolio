package net.spring.board.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.vertx.java.core.json.JsonObject;

import net.spring.board.dao.ArticleDao;
import net.spring.board.dao.CommentDao;
import net.spring.board.dao.LikeDao;
import net.spring.board.dao.MsgDao;
import net.spring.board.security.MyUserDetails;
import net.spring.board.server.VertxServer;
import net.spring.board.vo.Article;
import net.spring.board.vo.Comment;
import net.spring.board.vo.ImageBean;
import net.spring.board.vo.User;
import net.spring.board.vo.UserClass;
import net.spring.board.vo.UserClassPoint;
import net.spring.board.vo.UserInfo;

@Service("ArticleService")
public class ArticleServiceImpl implements ArticleService {
  protected Logger log = LogManager.getLogger(ArticleServiceImpl.class);
  
  @Autowired 
  private ArticleDao articleDao;
  
  @Autowired 
  private CommentDao commentDao;
  
  @Autowired 
  private LikeDao likeDao;
  
  @Autowired 
  private VertxServer vertxServer;
  
  @Autowired 
  private ServletContext sc;
  
  @Autowired 
  MsgDao toMsgDao;
  
  @Autowired 
  PlatformTransactionManager txManager;
  
  @Autowired 
  UserClassService userClassService;
  
  
  /**===========================  add  ===================================*/
  
  //글작성
  public int addArticle(Article article) throws Exception {
    log.debug("serviceImplInsert = " + article);
      
    article = copyUploadFile(article);  //임시폴더에 업로드된 파일을 원본 폴더로 이동.
    imgTempDelete();    //원본폴더에 업로드되지않은 임시폴더안의 img를 삭제
    fileTempDelete();   //원본폴더에 업로드되지않은 임시폴더안의 file을 삭제
    
    int count = articleDao.insertArticle(article); //ArticleController로 리턴되는 count.
    
    
      int selectPoint = 0;
      
      switch (article.getBoardNo()) { //게시판 종류에 따라 다른 포인트를 넣는다.
      
      case 1: //공지사항
        break;
        
      case 2: //자유
        selectPoint = UserClassPoint.FREE_ARTICLE_POINT;
        break;
        
      case 3: //유머
        selectPoint = UserClassPoint.HUMOR_ARTICLE_POINT;
        break;
        
      case 4: //창작
        selectPoint = UserClassPoint.CREATE_ARTICLE_POINT;
        break;
        
      case 5: //목공
        selectPoint = UserClassPoint.WOOD_ARTICLE_POINT;
        break;
        
      case 6: //전자
        selectPoint = UserClassPoint.ELECT_ARTICLE_POINT;
        break;
        
      case 7: //금속
        selectPoint = UserClassPoint.METAL_ARTICLE_POINT;
        break;
        
      case 8: //질문
        selectPoint = UserClassPoint.QnA_POINT;
        break;
        
      case 9: //장터
        break;
      
      }
      
      //댓글쓰기가 완료되면 활동 포인트를 넣는다.
      userClassService.addExp(selectPoint, article.getUserNo());
    
    
    
    
    return count;
   }
 
  
  
  //댓글쓰기
  public int addComment(Comment comment) throws Exception {
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    comment.setNicName(myUserDetails.getNicName());
     
    //유저레벨 불러오기
    UserClass userClass = userClassService.getUserClass(myUserDetails.getUserNo());
    comment.setUserLevel(Integer.toString(userClass.getUserLevel()));
    
    int count = commentDao.insertComment(comment);
                commentDao.updateTitleCommentCnt(comment);//글쓴후 총댓글수 update
    
   
    log.debug("UserClassPoint.COMMENT_POINT = " + UserClassPoint.COMMENT_POINT);            
    //댓글쓰기가 완료되면 활동 포인트를 넣는다.
    userClassService.addExp(UserClassPoint.COMMENT_POINT, myUserDetails.getUserNo());
    
    return count;    
  }
  
  
  
  
  //article테이블에 추천개수 추가
  public int addArticleLike(Map<String, Integer> likeMap, String nicName) throws Exception {
    
    likeDao.updateArticleLikeCnt(likeMap);  //추천수 넣기
    log.debug("likeArticleCount = " + likeMap);
    
    likeDao.insertLikeFlag(likeMap);  //추천테이블에 중복여부 정보주입
    log.debug("insertLikeStemp = " + likeMap);
    
    int count = likeDao.selectLikeToTalNum(likeMap);  //추천수 가져오기
    
    
    // 추천시 포인트 insert
    Map<String, Object> otherUserMap = new HashMap<String, Object>();
    int getListNo = likeMap.get("listNo");
    otherUserMap.put("nicName", nicName);
    otherUserMap.put("listNo", getListNo);
    
    int otherUserNo = articleDao.selectHistoryUserNo(otherUserMap);
    userClassService.addExp(UserClassPoint.LIKE_POINT, otherUserNo);
    
    
    if(count > 0) {
      //실시간 추천수 가져오기
      vertxServer.getIo().sockets().emit("echo", new JsonObject().putNumber("likeCount", count));
      
    }
    
    return count;
  }
  
  
 
  
  
  
  /**===========================  get  ===================================*/
  
  //게시판 정보 가져오기
  public Article getArticle(Article article) throws Exception {
    article = articleDao.selectArticle(article);//게시글내용(detail) 불러오기
              articleDao.updateArticleView(article);//조회수 update
    log.debug("service articleno, boardno = " + article + ",");
        
    return article;
  }
  
  
  
  //게시글 수정용 게시판 정보 가져오기
  public Article getAuthorizeArticle(Article article, Principal principal) throws Exception {
    article = articleDao.selectArticle(article);//게시글내용(detail) 불러오기
    
    return article;
  }
  
  
  
  //댓글 수정용 코멘트 정보 갸져오기
  public Comment getAuthorizeComment(Comment comment, Principal principal) throws Exception {
    comment = commentDao.selectComment(comment);  //댓글내용 가져오기.
    log.debug("ArticleService comment = " + comment);
    
    return comment;
  }
  
  
  
  
  //리스트 총개수 가져오기
  public int getArticleListTotalNum(Map<String, Object> totalListMap) throws Exception {
    int totalCount = articleDao.selectArticleListToTalCnt(totalListMap);//게시글전체개수
    log.debug("getTotalListCntService = " + totalListMap);
    log.debug("getTotalListCntServiceReturn = " + totalCount);
    return totalCount;
  }
  
  
  //user가 해당 게시글에 중복추천하였는지 체크
  public int getLikeFlag(Map<String, Integer> likeMap) throws Exception {
    log.debug("getLikeCheckCount = " + likeMap);
    int count = likeDao.selectLikeFlag(likeMap);//추천수 flag가져오기
    
    return count;
  }
  
  
  //댓글 전체갯수 가져오기
  public int getCommentListTotalNum(Map<String, Integer> commentMap) throws Exception {
    int count = commentDao.selectCommentToTalListCnt(commentMap);
    
    return count;
  }
  
  
  //userHistory의 정보를 나타내기 위해 해당 게시글을 쓴 유저의 번호를 가져온다.
  public int getHistoryUserNo(HttpServletRequest request) throws Exception {
    Map<String, Object> userNoMap = new HashMap<String, Object>();
    
    int result = 0;
    int listNo = Integer.parseInt(request.getParameter("listNo"));
    String nicName = request.getParameter("nicName");
    
    userNoMap.put("listNo", listNo);
    userNoMap.put("nicName", nicName);
    
    result = articleDao.selectHistoryUserNo(userNoMap);
   
    return result;
  }
  
  //가져온 userNo값에 해당하는 User 정보를 가져온다
  public User getHistoryUser(int userNo) throws Exception {
    
    return articleDao.selectHistoryUser(userNo);
  }
  
  
  //가져온 userNo값에 해당하는 UserInfo 정보를 가져온다
  public UserInfo getHistoryUserInfo(int userNo) throws Exception {
    
    return articleDao.selectHistoryUserInfo(userNo);
  }
  
  

  
  
  
  /**===========================  list  ===================================*/
  
  //게시판정보(목록) 가져오기
  public List<Article> articleList(Map<String, Object> listMap) throws Exception {
    return articleDao.articleList(listMap);
  }
    
  
  //댓글정보(목록) 가져오기
  public List<Comment> commentList(Map<String, Integer> commentMap) throws Exception {    
    return commentDao.commentList(commentMap);
  }
  
  
  
  
  
  
  
  /**===========================  change  ================================*/
  
  //게시판 수정
  public int changeArticle(Article article) throws Exception {
       
    article = copyUploadFile(article);  //임시폴더에 업로드된 파일을 원본 폴더로 이동.
    imgTempDelete();    //원본폴더에 업로드되지않은 임시폴더안의 img를 삭제
    fileTempDelete();   //원본폴더에 업로드되지않은 임시폴더안의 file을 삭제
    
    int count = articleDao.updateArticle(article);; //ArticleController로 리턴되는 count.
        
    return count;
  }
  
  
  
  
  /**===========================  remove  =================================*/
  
  //글 삭제
  public int removeArticle(Article article) throws Exception {
    
    int count = 0;
    String deleteFileResult = "";
    String deleteImageResult = "";
    
    Article selectArticle = articleDao.selectArticle(article);  //파일 이름을 불어온다.
    
    log.debug("!!selectArticle!! = " + selectArticle);
    
    deleteFileResult = realPathFileDelete(selectArticle.getRenameFilePath()); //경로를 찾아서 파일삭제
    deleteImageResult = realPathImageDelete(selectArticle.getContent());  //경로를 찾아서 이미지삭제
    
    log.debug("deleteFileResult = " + deleteFileResult);
    log.debug("deleteImageResult = " + deleteImageResult);
    
    if (deleteFileResult == "DeleteFile!" | deleteImageResult  == "DeleteImage!") {
            
      count = articleDao.deleteArticle(article);
            
    }
    
    return count;
    
  }
  
  
  //댓글삭제
  public int removeComment(Comment comment) throws Exception {
        
    int count = commentDao.deleteComment(comment);
                commentDao.updateTitleCommentCnt(comment);//삭제후 총댓글수 update
    
    return count;
  }
  
  
  
  //글수정 - 첨부파일삭제
  public int removeAttachFile(String fileValue) throws Exception {    
    
    int count = 0;
    String deleteResult = "";
    
    deleteResult = realPathFileDelete(fileValue);  //경로를 찾아서 파일삭제
    
    if(deleteResult == "DeleteFile!") {
      
      count = articleDao.deleteAttachFile(fileValue);
    
    }
    
    return count;
  }
  
  
  
  
  //업로드된 파일 삭제
  @ResponseBody
  public String realPathFileDelete(String fileName) { 
    
    if(fileName != null) {
      
      File file = new File(sc.getRealPath("/files/article/file/")
          + "/" + fileName);
      
      if (file.exists() == true) {
        file.delete();
      } 
     
    }
    
    
    return "DeleteFile!";
  }
  
  //업로드된 이미지 삭제
  @ResponseBody
  public String realPathImageDelete(String content) {  
    //content내 img태그에 들어있는 이미지 path를 추출한다.
    if(content.matches(".*<img alt.*") | content.matches(".*&lt;img alt.*")) {
      
      String extractContent = content.substring(content.lastIndexOf("image/") + 6, content.lastIndexOf("\" style"));
      
      File image = new File(sc.getRealPath("/files/article/image/")
          + "/" + extractContent);
      
      if (image.exists() == true) {
        image.delete();
      }
    }
    
    return "DeleteImage!";
  }
  
  
  
  
  /**========================  file up & down  =============================*/
  
  
  //file & multi image 업로드. 
  public Map<String, String> addFileUpload(MultipartFile file, String inputType) throws Exception {
    Map<String, String> fileUploadMap = new HashMap<String, String>();
    
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    String originalName = file.getOriginalFilename();
    String newName = this.generateFilename(file.getOriginalFilename(), myUserDetails.getUsername()); 
    String file_path = "/files/article/temps/file";   //파일 업로드 경로
    String multiImage_path = "/files/article/temps/image";  //multi image업로드 경로
    
//    int split = originalName.lastIndexOf(".");
//    String result = originalName.substring(split).toLowerCase();
//    log.debug("result = " + result);
    

    log.debug("inputType = " + inputType);
    
    if (inputType.equals("multi")) {
      
          file.transferTo(new File(
              sc.getRealPath(multiImage_path)
              + "/" + newName));
          log.debug("multiImage_path = " + multiImage_path);
      
    
      } else if (inputType.equals("single")) {
  
          file.transferTo(new File(
              sc.getRealPath(file_path)
              + "/" + newName));
          log.debug("file_path = " + file_path);
   
      } else if(!file.isEmpty()) {
    
      }
    
      log.debug("select_path = " + file_path);   
      fileUploadMap.put("originalName", originalName);
      fileUploadMap.put("newName", newName);        
    
    
    return fileUploadMap;
  }
  
  
  
  //image 단일 업로드
  public Model addImageUpload(Model model, HttpServletRequest request,
                              ImageBean imageBean) throws Exception {
    
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    String CKEditorFuncNum = "";
    String filename = "";
    String generateFilename = "";
    String attach_path = "/files/article/temps/image";
    
    MultipartFile upload = imageBean.getUpload();
    
    if (!upload.isEmpty()) {
        log.debug("upload = " + upload);
        filename = upload.getOriginalFilename(); 
        imageBean.setFilename(filename);
        CKEditorFuncNum = imageBean.getCKEditorFuncNum();
      
        generateFilename = this.generateFilename(upload.getOriginalFilename(), myUserDetails.getUsername()); 
        upload.transferTo(new File(
                            sc.getRealPath(attach_path)
                            + "/" + generateFilename));
        
        log.debug("@@@@@@@@@@ getRealPath @@@@@@@@@@ = " + sc.getRealPath(attach_path));
        
        imageBean.setGenerateFilename(generateFilename);
        log.debug("attach_path = " + attach_path);
        log.debug("generateFilename = " + generateFilename);
        log.debug("CKEditorFuncNum = " + CKEditorFuncNum);
        log.debug("filename = " + filename);
        
        }
      
      String file_path = attach_path + "/" + generateFilename;
      log.debug("file_path = " + file_path);

      model.addAttribute("file_path", file_path);
      model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);
      
      return model;
  }
  
  
  
  
  
  //file DownLoad
  public void getFileDownLoad(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    String renameFilePath = request.getParameter("renameFilePath");
    log.debug("filedownload renameFilePath" + renameFilePath);
    
    File file = null;
    file = new File(sc.getRealPath("/files/article/file" + "/" + renameFilePath));
        
    response.setContentType("application/octet-stream");
    response.setContentLength((int)file.length());
    response.setHeader("Content-Disposition", String.format("inline; filename=\"" + renameFilePath +"\""));
    
    InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
    FileCopyUtils.copy(inputStream, response.getOutputStream());
    
  }
  
  
  
  
//======================= 기능함수  ==================================

  //업로드 파일명 변환
  private String generateFilename(String originFilename, String email) {
    // 원래 파일명을 사용하지 않는다. => 다른 사람이 올린 파일명과 같을 수 있다.
    // 랜덤하게 생성한 파일명을 사용한다.
    int lastIndex = originFilename.lastIndexOf("."); // 파일명에서 마지막 점의 위치
    return email + "_" + System.currentTimeMillis() + originFilename.substring(lastIndex);
  }
  
  
  
  /** 
   * 업로드시 임시폴더의 내용물을 원본 업로드 폴더로 이동시킨후 
   * 업로드되지않은 임시폴더의 파일은 삭제시킨다.
   * */
  public Article copyUploadFile(Article article) {
    log.debug("=== copyUploadFile start ===");    
    log.debug("=== Arraylength ===" + article.getArrayImgName().size());
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    String beforeImgPath = "/files/article/temps/image";  //복사할 img경로
    String afterImgPath = "/files/article/image";        //복사될 img경로
    
    String beforeFilePath = "/files/article/temps/file";  //복사할 file경로
    String afterFilePath = "/files/article/file";        //복사될 file경로
    
    if (!article.getArrayImgName().equals("")) {
    
      for (int i = 0;  i < article.getArrayImgName().size(); i++) {
        log.debug("=== Array Img ===" + article.getArrayImgName().get(i));
     
        String multiImgTempName = article.getArrayImgName().get(i); // 멀티업로드된 파일을 하나씩 담는다.
        int    multiImgIndexCheck = multiImgTempName.indexOf("_");    //임시파일에 붙힌 @의 위치를 알아낸다.
        String multiImgIndexFind = multiImgTempName.substring(0, multiImgIndexCheck);  //0번째부터 @의 위치안에 담긴 유저번호를 알아낸다.
        
        if (multiImgTempName.matches(".*" + myUserDetails.getUsername() + ".*")) { //임시파일에 붙힌 유저넘버랑 나의 유저넘버랑 맞는지 확인
          
          String multiImgIndexReplace = article.getArrayImgName().get(i).replace(multiImgIndexFind+"_", ""); //원본폴더로 이동시킬대 유저번호를 삭제후 이동한다.
        
            
          File moveMultiImg = new File(sc.getRealPath(beforeImgPath)  ////임시폴더에서 img로드
              + "/" + multiImgTempName);
          
          moveMultiImg.renameTo(new File(    //Img이동
              sc.getRealPath(afterImgPath)
              + "/" + multiImgIndexReplace));
      
            log.debug("=== moveMultiImg ===" + moveMultiImg);
            article.setContent(article.getContent().replace("temps/", ""));
            article.setContent(article.getContent().replace(myUserDetails.getUsername()+"_", ""));
            log.debug("=== content replace ===" + article.getContent());
          
        }
        
      }
      
    } if (article.getRenameFilePath() != "") {
      
        String fileTempName = article.getRenameFilePath();
        int    fileIndexCheck = fileTempName.indexOf("_");
        String fileIndexFind = fileTempName.substring(0, fileIndexCheck);
      
        if (fileTempName.matches(".*" + myUserDetails.getUsername() + ".*")) {
      
          String fileIndexReplace = fileTempName.replace(fileIndexFind+"_", "");
          log.debug("fileIndexReplace = " + fileIndexReplace);
          File moveFile = new File(sc.getRealPath(beforeFilePath) //임시폴더에서 file로드
              + "/" + fileTempName);
      
          moveFile.renameTo(new File(     //파일이동
              sc.getRealPath(afterFilePath)
              + "/" + fileIndexReplace));
          
          article.setRenameFilePath(fileIndexReplace);
                    
        }
        
    } 
 
    
    return article;
    
  }
  
  
  
  
  //글쓰기, 글수정에서 남아있는 imgae or file 임시파일을 삭제한다.
  public void removeTempFile() throws Exception {
    
    imgTempDelete();
    fileTempDelete();
    
  }
  
  
  
  public void imgTempDelete () {  //img임시폴더내 파일제거
    String imgTempPath = "/files/article/temps/image";
    
    File tempPath = new File(sc.getRealPath(imgTempPath));
    
    File[] removeTemp = tempPath.listFiles(new FilenameFilter() {
      
      @Override
      public boolean accept(File dir, String name) {  //FilenameFilter 인터페이스로 특정 파일 이름만 리스트에 넣는다
        MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return name.toLowerCase().matches(".*"+ myUserDetails.getUsername() +".*");
      }
    });
    
    if (removeTemp.length > 0) {
      
      for (int i = 0; i < removeTemp.length; i++) {
        
        removeTemp[i].delete();
      }
      
    }
    
  }
  
  public void fileTempDelete () {  //file임시폴더내 파일제거
    
    String fileTempPath = "/files/article/temps/file";
    
    File tempPath = new File(sc.getRealPath(fileTempPath));
    
    File[] removeTemp = tempPath.listFiles(new FilenameFilter() { //FilenameFilter 인터페이스로 특정 파일 이름만 리스트에 넣는다
      
      @Override
      public boolean accept(File dir, String name) {
        MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return name.toLowerCase().matches(".*"+ myUserDetails.getUsername() +".*");
      }
    });
    
    if (removeTemp.length > 0) {
      
      for (int i = 0; i < removeTemp.length; i++) {
        removeTemp[i].delete();
      }
      
    }
    
  }
  
  
}

