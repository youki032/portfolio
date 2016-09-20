package net.spring.board.controller;

import java.net.URLEncoder;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.spring.board.security.MyUserDetails;
import net.spring.board.service.ArticleService;
import net.spring.board.service.LogService;
import net.spring.board.service.MsgService;
import net.spring.board.service.UserClassService;
import net.spring.board.service.UserService;
import net.spring.board.vo.Article;
import net.spring.board.vo.ArticleLog;
import net.spring.board.vo.Board;
import net.spring.board.vo.Comment;
import net.spring.board.vo.CommentLog;
import net.spring.board.vo.CommentPaging;
import net.spring.board.vo.ImageBean;
import net.spring.board.vo.Msg;
import net.spring.board.vo.Paging;
import net.spring.board.vo.User;
import net.spring.board.vo.UserActivity;
import net.spring.board.vo.UserClass;
import net.spring.board.vo.UserInfo;


@Controller
@RequestMapping("/article")
public class ArticleController {
  protected Logger log = LogManager.getLogger(ArticleController.class);
  
  @Autowired 
  private ArticleService articleService;
  
  @Autowired 
  private UserService userService;
  
  @Autowired 
  private MsgService msgService;
  
  @Autowired 
  private LogService logService;

  @Autowired 
  private UserClassService userClassService;
  
  //게시판 목록
  @RequestMapping(value="/list", method=RequestMethod.GET) 
  public String list(Model model, Principal principal, Article article, int boardNo, HttpServletRequest request,
                      @RequestParam(required=false, value="keyFiled")String keyFiled,
                      @RequestParam(required=false, value="keyWord")String keyWord) throws Exception {
    
    Board board = userService.getBoard(boardNo);
    
    if(principal != null) {
      
      MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      UserInfo profileUserInfo = userService.getUserInfo(myUserDetails.getUserNo());
      
      model.addAttribute("profileUserInfo", profileUserInfo);   //menubar 프로필사진 load
    
    }

    
    //======================= 총 게시물 갯수를 가져온다 ==========================
    
    Map<String, Object> totalListMap = new HashMap<String, Object>();
    int vaildListCnt = 0;   //보통리스트인지 검색키워드가 들어간 리스트인지 검증
    
    if (keyFiled == null & keyWord == null) {
       
       keyFiled = "";
       totalListMap.put("keyFiled", keyFiled);
       totalListMap.put("boardNo", boardNo);
       vaildListCnt = 1; //보통 리스트면 1을 넣어줘서 반환
    
    } else if (keyFiled != null & keyWord != null) {
       
       totalListMap.put("keyFiled", keyFiled);
       totalListMap.put("keyWord", keyWord);
       totalListMap.put("boardNo", boardNo);
       vaildListCnt = 2; //검색키워드가 들어간 리스트면 2을 넣어줘서 반환
    }
    
    int totalCount = articleService.getArticleListTotalNum(totalListMap);
    int pageNo = Integer.parseInt(request.getParameter("pageNo"));
    int pageNoCal = pageCalculation(keyFiled, keyWord, pageNo);

    
    //=================== 게시판 내용을 가져오기 위한 map ======================
    
    Map<String, Object> listMap = new HashMap<String, Object>();
    listMap.put("boardNo", boardNo);
    listMap.put("pageNo", pageNoCal);
    listMap.put("keyFiled", keyFiled);
    listMap.put("keyWord", keyWord);
    
    //=========================== 페이징처리 ==============================
    
    Paging paging = new Paging();
    paging.setPageNo(pageNo);        //페이지번호
    paging.setPageSize(10);     //페이지당 뿌려지는 게시글수
    paging.setBoardNo(boardNo);
    paging.setTotalCount(totalCount);
    
    
    model.addAttribute("vaildListCnt", vaildListCnt);
    model.addAttribute("keyFiled", keyFiled);
    model.addAttribute("keyWord", keyWord);
    model.addAttribute("paging", paging);
    model.addAttribute("board", board);
    model.addAttribute("list", articleService.articleList(listMap));
    
    return "article/list";
    
  }
  
  
  
  
  
  //키워드 검색
  @RequestMapping(value="/searchList", method=RequestMethod.POST)
  public String searchList(Model model, 
                            @RequestParam(required=false)int boardNo,
                            @RequestParam(required=false)int pageNo,
                            @RequestParam(required=false)String keyFiled,
                            @RequestParam(required=false)String keyWord) throws Exception {

    String keyWordEncode = URLEncoder.encode(keyWord, "UTF-8");
    
    log.debug("keyFiled = " + keyFiled);
    log.debug("keyword = " + keyWord);
    log.debug("boardNo = " + boardNo);

    //검색한 키워드를 받아서 list가져오는 메소드로 넘겨준다.
    return "redirect:/article/list?boardNo="+boardNo+"&pageNo="+pageNo+
        "&keyFiled="+keyFiled+"&keyWord="+keyWordEncode;
  }
  
  
  

  
  //게시물 상세페이지
  @RequestMapping(value="/detail", method=RequestMethod.GET)
  public String detail(Article article, Comment comment, Principal principal, Model model, 
                       HttpServletRequest request, int boardNo, Authentication authentication,
                       @RequestParam(required=false, value="keyFiled")String keyFiled,
                       @RequestParam(required=false, value="keyWord")String keyWord) throws Exception {
   
    
    //로그인 유무를 확인해 슬라이드바 프로필 사진을 불러온다.
    if (principal != null) { 
      MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      UserInfo profileUserInfo = userService.getUserInfo(myUserDetails.getUserNo());
      
      ArticleLog articleLog =  new ArticleLog(ArticleLog.CMD_SELECT, request, authentication);
      articleLog.setListNo(article.getListNo());
      logService.addArticleLog(articleLog, "etc");
      
      model.addAttribute("profileUserInfo", profileUserInfo);   //menubar 프로필사진 load
      model.addAttribute("user", myUserDetails);
    }
  
    
    article = articleService.getArticle(article);
    Board board = userService.getBoard(boardNo);
    UserInfo userInfo = userService.getUserInfo(article.getUserNo());
    
    
    
    //=============== detail 아래 뿌려지는 list 페이징 =================
    
    Paging paging = new Paging();
    int vaildListCnt = 0;   //보통리스트인지 검색키워드가 들어간 리스트인지 검증
    
    Map<String, Object> totalListMap = new HashMap<String, Object>();
    if(keyFiled == null & keyWord == null) {
      keyFiled = "";
      totalListMap.put("keyFiled", keyFiled);
      totalListMap.put("boardNo", boardNo);
      vaildListCnt = 1;   // 보통 리스트면 1을 넣어주고 반환.
      
    } else if(keyFiled != null & keyWord != null) {
      totalListMap.put("keyFiled", keyFiled);
      totalListMap.put("keyWord", keyWord);
      totalListMap.put("boardNo", boardNo);
      vaildListCnt = 2;   // 검색키워드가 들어간 리스트면 2을 넣어주고 반환.
    }
    
    
    int totalCount = articleService.getArticleListTotalNum(totalListMap);
    int pageNo = Integer.parseInt(request.getParameter("pageNo"));
    int pageNoCal = pageCalculation(keyFiled, keyWord, pageNo);
    
   
    
    //=================== 게시판 내용을 가져오기 위한 map ======================
    
    Map<String, Object> listMap = new HashMap<String, Object>();
    listMap.put("boardNo", boardNo);
    listMap.put("pageNo", pageNoCal);
    listMap.put("keyFiled", keyFiled);
    listMap.put("keyWord", keyWord);
 

    paging.setPageNo(pageNo);        //페이지번호
    paging.setPageSize(10);     //페이지당 뿌려지는 게시글수
    paging.setBoardNo(boardNo);
    paging.setTotalCount(totalCount);
    
    
    //=============== detail 댓글 페이징 =================
    
    CommentPaging commentPaging = new CommentPaging();
    
    Map<String, Integer> commentMap = new HashMap<String, Integer>();
    commentMap.put("articleNo", article.getArticleNo());
    commentMap.put("boardNo", article.getBoardNo());
    
    int commentTotalCnt = articleService.getCommentListTotalNum(commentMap);
    int commentPageNo = Integer.parseInt(request.getParameter("cPageNo"));
    int commentPageCal = ((commentPageNo-1)*10);
    
    
    commentMap.put("cPageNo", commentPageCal);
    
    
    commentPaging.setTotalCount(commentTotalCnt);
    commentPaging.setcPageNo(commentPageNo);        //페이지번호
    commentPaging.setPageSize(10);     //페이지당 뿌려지는 게시글수
    commentPaging.setBoardNo(boardNo);
    commentPaging.setArticleNo(article.getArticleNo());
    commentPaging.setPageNo(paging.getPageNo());

    
//    model.addAttribute("artocleNo", article.getArticleNo());
    model.addAttribute("vaildListCnt", vaildListCnt);
    model.addAttribute("keyFiled", keyFiled);
    model.addAttribute("keyWord", keyWord);
    model.addAttribute("commentPaging", commentPaging);
    model.addAttribute("paging", paging);
    model.addAttribute("userInfo", userInfo); //sidebar 프로필사진 load
    model.addAttribute("board", board);
    model.addAttribute("article", article);
    model.addAttribute("list", articleService.articleList(listMap));
    model.addAttribute("comment", articleService.commentList(commentMap));
    
    
    return "article/detail";
  }
  
  
  
  
  
  //글쓰기 view
  @RequestMapping(value="/writeForm/{boardNoVal}/{pageNoVal}", method=RequestMethod.GET)
  public String writeForm(Model model, Principal principal,
                          @PathVariable String boardNoVal, @PathVariable String pageNoVal) throws Exception {
    
    //글쓰기, 글수정에서 back space시 업로드했던 임시파일을 삭제한다.
    articleService.removeTempFile();
    
    if (principal != null) {
      MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      model.addAttribute("principal", myUserDetails);
      UserInfo profileUserInfo = userService.getUserInfo(myUserDetails.getUserNo());
      model.addAttribute("profileUserInfo", profileUserInfo);   //menubar 프로필사진 load
    }
     
    int boardNo = Integer.parseInt(boardNoVal);
    int pageNo = Integer.parseInt(pageNoVal);
    
    Board board = userService.getBoard(boardNo);
    
    log.debug("writeFrmBoardNo = " + boardNo);
    
    Map<String, Integer> writeMap = new HashMap<String, Integer>();
    writeMap.put("pageNo", pageNo);
    
    model.addAttribute("board", board);
    model.addAttribute("pageNo", writeMap);
    
    return "article/writeForm";
  }
  
  
  
  
  
  //게시글 쓰기
  @RequestMapping(value="/addArticle", method=RequestMethod.POST)
  public String addArticle(Principal principal, Article article, HttpServletRequest request, Authentication authentication) throws Exception {
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
    UserClass userClass = userClassService.getUserClass(myUserDetails.getUserNo()); //회원 level정보 load
    
    String userLevel = Integer.toString(userClass.getUserLevel());
    
    article.setUserNo(myUserDetails.getUserNo());
    article.setEmail(principal.getName());
    article.setUserLevel(userLevel);
    
    log.debug("article = " + article);
    
    int count = articleService.addArticle(article);
    
    if(count == 1) {
      
      ArticleLog articleLog =  new ArticleLog(ArticleLog.CMD_INSERT, request, authentication);
      logService.addArticleLog(articleLog, "add");
      
    }
    
    
    return "redirect:/article/list?boardNo=" + article.getBoardNo() + "&pageNo=" + 1;
  
  }
  
  
  
  
  //게시물 수정 view
  @RequestMapping(value="/change", method=RequestMethod.GET)
  public String changeArticle(Article article, Model model, HttpServletRequest request, Principal principal) throws Exception {
     
    //글쓰기, 글수정에서 back space시 업로드했던 임시파일을 삭제한다.
    articleService.removeTempFile();
    
    int pageNo = Integer.parseInt(request.getParameter("pageNo"));
    int cPageNo = Integer.parseInt(request.getParameter("cPageNo"));
    
    if(principal != null) {
      MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      UserInfo profileUserInfo = userService.getUserInfo(myUserDetails.getUserNo());
      model.addAttribute("profileUserInfo", profileUserInfo);   //menubar 프로필사진 load
      
    }
    
    //url접근제어를 위해 article정보를 가져와서 비교한다.
    article = articleService.getAuthorizeArticle(article, principal);
    
    Board board = userService.getBoard(article.getBoardNo());
    
    model.addAttribute("boardName", board.getBoardName());
    model.addAttribute("pageNo", pageNo);
    model.addAttribute("cPageNo", cPageNo);
    model.addAttribute("article", article);
    
    return "article/change";
  }

  
  
  
  
  //게시물 수정
  @RequestMapping(value="/doChange", method=RequestMethod.POST)
  public String changeArticle(Article article, HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws Exception {
    log.debug("doChange request Title = " + request.getParameter("title"));
    log.debug("doChange request Content = " + request.getParameter("content"));
    
    int pageNo = Integer.parseInt(request.getParameter("pageNo"));
    int cPageNo = Integer.parseInt(request.getParameter("cPageNo"));
    log.debug("change article = " + article);
    
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    article.setUserNo(myUserDetails.getUserNo());
  
    int count = articleService.changeArticle(article);
    
    if(count == 1) {
      
      ArticleLog articleLog =  new ArticleLog(ArticleLog.CMD_UPDATE, request, authentication);
      articleLog.setListNo(article.getListNo());
      logService.addArticleLog(articleLog, "etc");
    }
   
    
    return "redirect:/article/detail?boardNo=" + article.getBoardNo() + "&articleNo=" + article.getArticleNo() + "&pageNo=" + pageNo + "&cPageNo=" + cPageNo;
  }
  
  
  
  
  //댓글쓰기
  @RequestMapping(value="/comment", method=RequestMethod.POST)
  public String addComment(Principal principal, Comment comment, HttpServletRequest request, Authentication authentication) throws Exception {
    
    int pageNo = Integer.parseInt(request.getParameter("pageNo"));
    int cPageNo = Integer.parseInt(request.getParameter("cPageNo"));
    
    if (principal != null) {
      
      MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      comment.setUserNo(myUserDetails.getUserNo());
            
      int count = articleService.addComment(comment);
      
      if(count == 1) {
        
        CommentLog commentLog =  new CommentLog(CommentLog.CMD_INSERT, request, authentication);
        logService.addCommentLog(commentLog, "add");
        
      }
      
    }
    
    return "redirect:/article/detail?boardNo=" + comment.getBoardNo() + "&articleNo=" + comment.getArticleNo() + "&pageNo=" + pageNo + "&cPageNo=" + cPageNo;
  }
  

  
  
  
  
  //게시물삭제 
  @RequestMapping(value="/removeArticle", method=RequestMethod.GET)
  public String removeArticle(Article article, HttpServletRequest request, Principal principal, Authentication authentication) throws Exception {
    
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    int pageNo = Integer.parseInt(request.getParameter("pageNo"));
    
    article.setUserNo(myUserDetails.getUserNo());
    
    
    //url로 접근시 내 게시글인지 확인후 접근권한을 부여
    articleService.getAuthorizeArticle(article, principal);
//    Article authorizeArticle = null;
//    authorizeArticle = 
    
    log.debug("remove article = " + article);
    log.debug("remove principal = " + principal);
    
    //내 게시글이 맞으면 삭제진행
    int count = articleService.removeArticle(article);
    
      if(count == 1) {
      
      ArticleLog articleLog =  new ArticleLog(ArticleLog.CMD_DELETE, request, authentication);
      articleLog.setListNo(article.getListNo());
      logService.addArticleLog(articleLog, "etc");
      
      }
    
    return "redirect:/article/list?boardNo="+article.getBoardNo() + "&pageNo=" + pageNo;
  }
  
 
  
  
  
  
  //댓글삭제
  @RequestMapping(value="/removeComment", method=RequestMethod.GET)
  public String removeComment(Comment comment, HttpServletRequest request, Principal principal, Authentication authentication) throws Exception {
    
    int pageNo = Integer.parseInt(request.getParameter("pageNo"));
    int cPageNo = Integer.parseInt(request.getParameter("cPageNo"));
    
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    comment.setUserNo(myUserDetails.getUserNo());
    log.debug("remove article = " + comment);
    
    
    //url로 접근시 내 댓글인지 확인후 접근권한을 부여
    articleService.getAuthorizeComment(comment, principal);
//    Comment authorizeComment = null;
//    authorizeComment = 
    
    //내 댓글이 맞으면 삭제 진행
    int count = articleService.removeComment(comment);
    
    if(count == 1) {
      
      CommentLog commentLog =  new CommentLog(CommentLog.CMD_DELETE, request, authentication);
      commentLog.setCommentNo(comment.getCommentNo());
      logService.addCommentLog(commentLog, "etc");
      
    }
    
    return "redirect:/article/detail?boardNo=" + comment.getBoardNo() + "&articleNo=" + comment.getArticleNo() + "&pageNo=" + pageNo + "&cPageNo=" + cPageNo;
  }
  
  
  

  
  //파일 업로드, 멀티이미지 업로드
  @RequestMapping(value="/fileUpload", method=RequestMethod.POST)
  @ResponseBody
  public Map<String, String> procFileUpload(@RequestParam(required=false, value="file") MultipartFile file,
                                            @RequestParam(value="type") String inputType) throws Exception {
      Map<String, String> fileUploadMap = null;
    
      fileUploadMap = articleService.addFileUpload(file, inputType);
      
    return fileUploadMap;
  }
  
  
  
  
  //파일다운로드
  @RequestMapping(value="/fileDownload", method=RequestMethod.GET)
  public void procFileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    articleService.getFileDownLoad(request, response);
    
  }
  
  
  
  
  //이미지 업로드
  @RequestMapping(value="/image_upload", method=RequestMethod.POST)
  public String procImageUpload(Model model, HttpServletRequest request,
                                ImageBean imageBean) throws Exception {
    
      model = articleService.addImageUpload(model, request, imageBean);
    
      return "article/imageUpload";
  }
  
  
  
  //메시지함 view
  @RequestMapping(value="/myMsg", method=RequestMethod.GET)
  public String myMsg(Model model, Principal principal, HttpServletRequest request) throws Exception {
    
  
    return "article/myMsg";
  }
  
  
  
  
  //회원정보 view
  @RequestMapping(value="/userHistory", method=RequestMethod.GET)
  public String userHistory(Model model, HttpServletRequest request) throws Exception {

    int getUserNo = articleService.getHistoryUserNo(request);
    
    User historyUser = articleService.getHistoryUser(getUserNo);
    UserInfo historyUserInfo = articleService.getHistoryUserInfo(getUserNo);
    UserActivity userActivity = userService.getUserActivity(getUserNo);
    UserClass userClass = userClassService.getUserClass(getUserNo); //회원point정보 load

    //회원정보의 가입일에서 년/월/일만 출력되게 시간은 없애준다.
    historyUserInfo.setSignDate(historyUserInfo.getSignDate().substring(0,10));
    
    model.addAttribute("historyUser", historyUser);
    model.addAttribute("historyUserInfo", historyUserInfo);
    model.addAttribute("userActivity", userActivity);
    model.addAttribute("userClass", userClass);

    return "article/userHistory";
  }
  
  
  
  
  //메시지 보내기 view
  @RequestMapping(value="/sendMsg", method=RequestMethod.GET)
  public String sendMsg(Model model, HttpServletRequest request) {
    
    String nicName = request.getParameter("nicName");
    int articleNo = Integer.parseInt(request.getParameter("articleNo"));
    log.debug("nicName = " + nicName + "articleNo = " + articleNo);
    
    model.addAttribute("toNicName", nicName);
    model.addAttribute("articleNo", articleNo);
    return "article/sendMsg";
  }
  
  
  //메시지 보내기 
  @RequestMapping(value="/sendMsg", method=RequestMethod.POST)
  public String sendMsg(Msg msg, Principal principal, HttpServletRequest request) throws Exception {
    
    int boardNo = Integer.parseInt(request.getParameter("boardNo"));
    int pageNo = Integer.parseInt(request.getParameter("pageNo"));
    log.debug("ToMsg = " + msg);
    
    msgService.addMsg(msg);
    
    return "redirect:/article/list?boardNo=" + boardNo + "&pageNo=" + pageNo;
  }
  
  
  
  
//  //글쓰기, 글수정에서 back space시 업로드했던 임시파일을 삭제한다.
//  @RequestMapping(value="/do_backSpace", method=RequestMethod.POST)
//  public void removeTempFile() throws Exception {
//    
//    articleService.removeTempFile();
//    
//  }
  
  
  
  
  //접속 클라이언트 ip가져오기
  public String getClientIP(HttpServletRequest request) {
    String ipStr = request.getHeader("X-FORWARDED-FOR");
    
    if (ipStr == null || ipStr.length() == 0) {
      ipStr = request.getHeader("Proxy-Client-IP");
    }
    
    if (ipStr == null || ipStr.length() == 0) {
      ipStr = request.getHeader("WL-Proxy-Client-IP");//웹로직
    }
    
    if (ipStr == null || ipStr.length() == 0) {
      ipStr = request.getRemoteAddr();
    }
    
    log.debug("getClientIP = " + ipStr);
    
    return ipStr;
  }
  
  
  
  //페이징 계산함수
  public int pageCalculation(String keyFiledStr, String keyWordStr, int pageNo) {
    log.debug("keyFiledStr = " + keyFiledStr + ", keyWordStr = " + keyWordStr + ", pageNo = " + pageNo);

    int pageNoCal = 0;
    
    if(keyFiledStr.equals("") & keyWordStr == null) {
      pageNoCal = ((pageNo-1)*10);
      
    } else if(!keyFiledStr.equals("") & keyWordStr != null) {
      pageNoCal = ((1-1)*10);

    }
    log.debug("pageNoCal = " + pageNoCal);
    return pageNoCal;
  
  }
  
  
  public String nullPointerException(Article article) throws NullPointerException {
    log.debug("nullpoint");
    
    return "redirect:/article/list?boardNo=" + article.getBoardNo() + "&pageNo=1";
  }


}


