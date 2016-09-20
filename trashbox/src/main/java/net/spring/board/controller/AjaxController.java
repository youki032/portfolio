package net.spring.board.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.spring.board.dao.UserClassDao;
import net.spring.board.security.MyUserDetails;
import net.spring.board.service.ArticleService;
import net.spring.board.service.LogService;
import net.spring.board.service.MainTableService;
import net.spring.board.service.MsgService;
import net.spring.board.service.UserService;
import net.spring.board.vo.MainTable;
import net.spring.board.vo.Msg;
import net.spring.board.vo.Paging;
import net.spring.board.vo.UserClass;
import net.spring.board.vo.UserLog;

@Controller
@RequestMapping("/ajax")
public class AjaxController {
  protected Logger log = LogManager.getLogger(AjaxController.class);
  
  @Autowired private MainTableService mainTableService;
  @Autowired private ArticleService articleService;
  @Autowired private UserService userService;
  @Autowired private MsgService msgService;
  @Autowired private LogService logService;

  
  /**컨트롤러에서 ResponseBody를 사용하면 스프링에 선언된 ViewResolver를 사용하지않고,
  HTTP Response Body 에 직접 쓰여지게 된다. 보통 JSON이나 XML 형식의 문자열로 변환할 때 사용된다.*/
  @RequestMapping(value="/emailCheck.do", method=RequestMethod.POST)
  @ResponseBody
  public Map<String, String> emailCheck(String email)  throws Exception { 
  Map<String, String> checkMap = new HashMap<String, String>();
  String result = "";
  String resultMessage = "";
  
  
  if(email.equals("")){
    result = "failure";
    resultMessage = "중복확인할 이메일을 입력해주세요.";
    
  } else {
  
    int resultCnt = userService.getEmailCheck(email);
   
    switch(resultCnt) {
    
    case 0:
      result = "success";
      resultMessage = "사용가능한 이메일 입니다.";
      break;
      
    case 1:
      result = "failure";
      resultMessage = "이미 사용중인 이메일 입니다.";
      break;
    
      }
  }
 
  
  checkMap.put("result", result);
  checkMap.put("resultMessage", resultMessage);
  
  return checkMap;
  }
  
  
  @RequestMapping(value="/checkNicName.do", method=RequestMethod.POST)
  @ResponseBody
  public Map<String, String> checkNicName(String nicName)  throws Exception { 
  
    Map<String, String> checkMap = new HashMap<String, String>();
    String result = "";
    String resultMessage = "";
    
    String blindStrResult = "";
    
    blindStrResult = isBlindNicName(nicName);
    
    
  if (nicName.equals("")) {
    
    result = "failure";
    resultMessage = "중복확인할 닉네임을 입력해주세요.";
  
  } else if (blindStrResult.equals("failure")) {
    
    result = "failure";
    resultMessage = "사용불가능한 닉네임 입니다.";

  } else if (nicName.length() <= 2) {
    
    result = "failure";
    resultMessage = "닉네임은 2글자 이상 가능합니다.";
    
  } else {
   
    
  int userResultCnt = userService.getUserNicNameCheck(nicName); //user테이블에서 닉네임검색
  int articleResultCnt = userService.getArticleNicNameCheck(nicName); //article테이블에서 닉네임검색
  
  
    if (userResultCnt == 0 & articleResultCnt == 0) {
      result = "success";
      resultMessage = "사용가능한 별명입니다.";
    
    } else {
      result = "failure";
      resultMessage = "이미 사용중인 별명 입니다.";
    }
  }

  
  checkMap.put("result", result);
  checkMap.put("resultMessage", resultMessage);
  
  return checkMap;
  }
  
  
  @RequestMapping(value="/byePassword.do", method=RequestMethod.POST)
  @ResponseBody
  public Map<String, String> byePassword(String password, Principal principal, HttpServletRequest request,
      Authentication authentication) throws Exception {
    Map<String, String> checkMap = new HashMap<String, String>();
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    int userNo = myUserDetails.getUserNo();
    String result = "";
    String resultMessage = "";
    
    log.debug("password = " + password);
    
    if(password.equals("")){
      result = "failure";
      resultMessage = "패스워드를 입력해주세요.";
      
    } else {
    
      
    int resultCnt = userService.getByePassword(password, userNo);
    
    log.debug("resultCnt = " + resultCnt);
    
    switch(resultCnt) {
    
    case 1:
      result = "success";
      resultMessage = "정상적으로 탈퇴되셨습니다.";
      UserLog userLog =  new UserLog(UserLog.CMD_BYE, request, authentication);
      logService.addUserLog(userLog, "etc");
      break;
      
    case 2:
      result = "failure";
      resultMessage = "패스워드가 틀립니다. ";
      break;
    
      }
    }

    
    checkMap.put("result", result);
    checkMap.put("resultMessage", resultMessage);
    
    return checkMap;
  }
 
  
  
  @RequestMapping(value="/removeAttachFile.do", method=RequestMethod.POST)
  @ResponseBody
  public Map<String, String> removeAttachFile(Principal principal, String fileValue) throws Exception {
    Map<String, String> checkMap = new HashMap<String, String>();

    log.debug("fileValue = " + fileValue);
    String result = "";
    String resultMessage = "";
    
    int resultCnt = articleService.removeAttachFile(fileValue);


    checkMap.put("result", result);
    checkMap.put("resultMessage", resultMessage);
    log.debug("resultCnt = " + resultCnt);
    
    return checkMap;
  }
  
  
  
  
  
  @RequestMapping(value="/likeArticle.do", method=RequestMethod.POST)
  @ResponseBody
  public Map<String, Integer> likeArticle(@RequestParam(value="articleNo")int articleNo, 
                                          @RequestParam(value="boardNo")int boardNo, 
                                          @RequestParam(value="listNo")int listNo, 
                                          @RequestParam(value="nicName")String nicName) throws Exception {
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    Map<String, Integer> likeMap = new HashMap<String, Integer>();
    likeMap.put("articleNo", articleNo);
    likeMap.put("boardNo", boardNo);
    likeMap.put("listNo", listNo);
    likeMap.put("userNo", myUserDetails.getUserNo());
    
    int likeAllCount = 0;
    int likeCheckVaild = articleService.getLikeFlag(likeMap);
    log.debug("likeCheckVaild = " + likeCheckVaild);
    
    Map<String, Integer> checkMap = new HashMap<String, Integer>();
    
    if (likeCheckVaild == 0) {
        
      likeAllCount = articleService.addArticleLike(likeMap, nicName);
      log.debug("likeAllCount = " + likeAllCount);
        
      checkMap.put("likeAllCount", likeAllCount);
      log.debug("likeAllCountCheckMap = " + checkMap);
                
    } else {
      
      int failLike = 2;
      checkMap.put("failLike", failLike);
      log.debug("failLikeCheckMap = " + checkMap);
    
    }   
    return checkMap;
  }
  
  
  
  
  
 @RequestMapping(value="/changeNicPopUp.do", method=RequestMethod.POST)
 @ResponseBody
 public Map<String, String> changeNicPopUp(String checkNicDate) {
   Map<String, String> checkMap = new HashMap<String, String>();
   log.debug("checkNicDate = " + checkNicDate);
   String result = "";
   String resultMessage = "";
   String nowDate = "";
   String afterDate = checkNicDate;
   String differentDate = "";
//   String rerutnResult = "";
   
   if(checkNicDate.equals("") | checkNicDate == null) {
     
     result = "success";
     
     
   } else {
     
   
   nowDate = doNowDate();
   
   differentDate = doDiffOfDate(nowDate, afterDate);
   
   
   if(differentDate.equals("success")) {
     
     result = "success";
     resultMessage = "";
     
   } else {
     
     result = "failure";
     resultMessage = differentDate;
     log.debug("differentDate = " + resultMessage);
   
   }
   
   
   }
   
   checkMap.put("result", result);
   checkMap.put("resultMessage", resultMessage);
   return checkMap;
   
 }
 
 
 
 
 
 //리스트 불러오기
 @RequestMapping(value="/loadMsgList.do", method=RequestMethod.POST)
 @ResponseBody
 public Object msgList(int msgListNo, int pageNo) throws Exception {
   MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   
   Map<String, Object> listMap = new HashMap<String, Object>();//클라이언트로 리턴되는 map
   Map<String, Integer> getPageList = new HashMap<String, Integer>();//list불러올 인자값 넣어주는 map
   
   List<Paging> pagingList = new ArrayList<Paging>();
   List<Msg> msgList;
   
   Paging msgPaging = new Paging();
   
   //메시지 총갯수 가져오기
   int toAllListCount = 0;
   log.debug("toAllListCount = " + toAllListCount);
   log.debug("pageNo = " + pageNo);
   log.debug("msgListNo = " + msgListNo);
   
   //어떤 msg리스트인지 선택
   switch(msgListNo) {
     case 1: //msgList
       msgPaging.setMsgListNo(1);
       toAllListCount = msgService.getListToTalNum(myUserDetails.getUserNo(), msgListNo);
       break;
     
     case 2: //fromMsgList
       msgPaging.setMsgListNo(2);
       toAllListCount = msgService.getListToTalNum(myUserDetails.getUserNo(), msgListNo);
       break;
     
     case 3: //saveMsgList
       msgPaging.setMsgListNo(3);
       toAllListCount = msgService.getListToTalNum(myUserDetails.getUserNo(), msgListNo);
       break;
   }
   
   //msgList 불러올 인자값 map에 넣어줌
//   getPageList.put("toUserNo", myUserDetails.getUserNo());
   getPageList.put("pageNo", pageNo);
   getPageList.put("msgListNo", msgListNo);
   //msgList불러오기
   msgList = msgService.msgList(getPageList);
   
   msgPaging.setPageNo(pageNo);
   msgPaging.setPageSize(10);// 페이지당 뿌려지는 게시글수
   msgPaging.setTotalCount(toAllListCount);
   log.debug("msgPaging = " + msgPaging);
   
   
   
   pagingList.add(msgPaging);
   //view로 리턴되는값 설정
   listMap.put("listMap", msgList);
   listMap.put("pagingMap", pagingList);
   
   log.debug("listMap = " + listMap);
   return listMap;
 }
 
 
 
 
 
 //답장보내기
 @RequestMapping(value="/replayMsg.do", method=RequestMethod.POST)
 @ResponseBody
 public Map<String, String> replayMsg(Msg msg) throws Exception {
   Map<String, String> reMsgMap = new HashMap<String, String>();
   log.debug("Msg = " + msg);
   
   
   int count = msgService.addRePlayMsg(msg);
   String resultStr = "";
   String resultMsg = "";
   log.debug("rePlayMsgCount = " + count);
   
   switch(count) {
     case 1:
       resultStr = "Success";
       resultMsg = "답장을 보냈습니다.";
       
       reMsgMap.put("resultStr", resultStr);
       reMsgMap.put("resultMsg", resultMsg);
       log.debug("SuccessMap = " + reMsgMap);
       break;
       
     case 0:
       resultStr = "Failuer";
       resultMsg = "답장을 보내는데 실패하였습니다.";
       
       reMsgMap.put("resultStr", resultStr);
       reMsgMap.put("resultMsg", resultMsg);
       log.debug("FaluerMap = " + reMsgMap);
       break;
       
   }
   
   
   return reMsgMap;
 }
 
 
 
 
 
 
 //쪽지 읽기확인
 @RequestMapping(value="/flagCount.do", method=RequestMethod.POST)
 @ResponseBody
 public Map<String, Integer> flagCount(int msgNo, int msgListNo, Principal principal) throws Exception {
   MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   Map<String, Integer> flagMap = new HashMap<String, Integer>();
   log.debug("toUserNo = " + msgNo);
   log.debug("msgListNo = " + msgListNo);
   
   int flagCheck = msgService.isFlagNum(msgNo, msgListNo);
   log.debug("isFlagCount = " + flagCheck);
   
   int isTotalFlag = msgService.isReadMsg(myUserDetails.getUserNo());
   log.debug("isTotalFlag = " + isTotalFlag);
   
   
   flagMap.put("flagCheck", flagCheck);
   flagMap.put("isTotalFlag", isTotalFlag);
   
   return flagMap;
 }
 
 
 
 
 
 
 
 //저장 & 삭제
 @RequestMapping(value="/btnFunction.do", method=RequestMethod.POST)
 @ResponseBody
 public Map<String, Integer> btnFunction(@RequestParam(value="msgNo[]")List<String> checkedMsgs,
                                       @RequestParam(value="btnVal")String btnVal,
                                       @RequestParam(value="msgListNo")int msgListNo) throws Exception {
   Map<String, Integer> flagMap = new HashMap<String, Integer>();
   log.debug("checkMsgNosController = " + checkedMsgs);
   log.debug("msgListNoController = " + msgListNo);
   log.debug("btnChooseController = " + btnVal);
   
   if(btnVal.equals("삭제")) {
     int count = msgService.removeMsg(checkedMsgs, msgListNo);
     log.debug("removeMsg = " + count);
     
     flagMap.put("msgListNo", count);
     
     
   } else if(btnVal.equals("저장")) {
     int count = msgService.addSaveMsg(checkedMsgs, msgListNo);
     log.debug("saveMsg = " + count);
     
     flagMap.put("msgListNo", count);
     
   }

   
   return flagMap;
 }
 
 
 
 @RequestMapping(value="/loadNoticeTable.do", method=RequestMethod.POST)
 @ResponseBody
 public Object loadMainTable() throws Exception {
   log.debug("--------------loadNoticeTable------------- start");
   
   List<MainTable> noticeList;
   
   noticeList = mainTableService.getNoticeList();
   
   return noticeList;

 }
 
 
 
 @RequestMapping(value="/loadNewestTable.do", method=RequestMethod.POST)
 @ResponseBody
 public Object loadNewestTable() throws Exception {
   log.debug("-------------loadNewestTable------------- start");
   
   List<MainTable> newestList;
   
   newestList = mainTableService.getNewestList();
   
   return newestList;

 }
 
 
 
 @RequestMapping(value="/loadWeekTable.do", method=RequestMethod.POST)
 @ResponseBody
 public Object loadWeekTable() throws Exception {
   log.debug("-------------loadWeekTable------------- start");
   
   List<MainTable> weekList;
   
   weekList = mainTableService.getWeekList();
   
   return weekList;

 }
 
 
 
 @RequestMapping(value="/loadMonthTable.do", method=RequestMethod.POST)
 @ResponseBody
 public Object loadMonthTable() throws Exception {
   log.debug("-------------loadMonthTable------------- start");
   
   List<MainTable> monthList;
   
   monthList = mainTableService.getMonthList();
   
   return monthList;

 }
 
 
 
 
 
 @RequestMapping(value="/loadIamgeTable.do", method=RequestMethod.POST)
 @ResponseBody
 public Object loadIamgeTable(int imgPage) throws Exception {
   log.debug("-------------loadIamgeTable------------- start");
   log.debug("-------------imgPage-------------" + imgPage);
   
   List<MainTable> imageList;
   
   imageList = mainTableService.getImageList(imgPage);
      
   log.debug("imageList = " + imageList);
   
   return imageList;

 }
 
 
 
 
// ======================= 기능함수 ==================================
 
 
 public String doNowDate() {//현재날짜 구하기
   String result = "";
   SimpleDateFormat fm1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   result = fm1.format(new Date());

   log.debug("doNowDate = " + result);
   
   return result;
 }
 
 

 public String doDiffOfDate(String str1, String str2) {//현재와 한달후 날짜 차이 구하기
   log.debug("str1 = " + str1 + ", str2 = " + str2);
   
   String returnStr = "";
   
   try {
     
   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   Date beginDate = formatter.parse(str1);
   Date afterDate = formatter.parse(str2);
   
   log.debug("afterDate = " + afterDate);
    
   // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
   long diff = afterDate.getTime() - beginDate.getTime();
   long diffDays = diff / (24 * 60 * 60 * 1000);
     
   log.debug("diff = " + diff + ", diffDays = " + diffDays);
   
   if(beginDate.compareTo(afterDate) >= 0) {
     
     returnStr = "success";
     log.debug("doDiffOfDate success = " + returnStr);
   
   } else {
     
     returnStr = String.valueOf(diffDays) + "일후에 다시 변경 가능합니다.";
     log.debug("doDiffOfDate fail = " + returnStr);
     
   }
   
   
   } catch(Exception e) {
     System.out.println(e);
   }
   
   return returnStr;
 }
 
 
 
 
   public String isBlindNicName(String nicName) {
     
   //사용불가 닉네임 Role
     String[] blindNicName = { "관리자", "관리쟈", "매니저", "매니져",
                               "GM운영자", "GM관리자", "GM매니저",
                               "마스터", "운영자", "운영쟈" };
     String result = "";
     
     for (String str : blindNicName) { // 사용불가 닉네임 Role
             
       if (str.equals(nicName)) {
         
         result = "failure";
       
       } 
       
     }
     
     return result;
     
   }
 
}
