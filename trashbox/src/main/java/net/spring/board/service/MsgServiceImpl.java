package net.spring.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.vertx.java.core.json.JsonObject;

import net.spring.board.dao.MsgDao;
import net.spring.board.security.MyUserDetails;
import net.spring.board.server.VertxServer;
import net.spring.board.vo.Msg;

@Service("MsgService")
public class MsgServiceImpl implements MsgService {
  protected Logger log = LogManager.getLogger(MsgServiceImpl.class);
  
  @Autowired 
  private MsgDao msgDao;
  
  @Autowired
  private VertxServer vertxServer;
  
  
  
  /**==========================  insert(add)  ===============================*/
  //쪽지 보내기
  public int addMsg(Msg msg) throws Exception {
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Map<String, Object> msgMap = new HashMap<String, Object>();
    int count = 0;
    

    //받는사람의 회원번호랑 이메일을 가져온다.
    msgMap = msgDao.selectToUserInfo(msg);
    String loadEmail = (String)msgMap.get("email");
    int loadUserNo = (Integer)msgMap.get("user_no");
    log.debug("msgMap = " + msgMap);
    
    
    //받은쪽지함으로 insert
    msg.setToUserEmail(loadEmail);
    msg.setToUserNo(loadUserNo);
    msg.setFromUserNicName(myUserDetails.getNicName());
    msg.setFromUserNo(myUserDetails.getUserNo());
    msg.setFromUserEmail(myUserDetails.getUsername());
//    msg.setMsgContent(toTEXT(msg.getMsgContent())); //입력된 특수기호를 변환
    log.debug("msg = " + msg);
    
    
    //toList
    msgDao.insertToMsg(msg);
    
    //fromList
    msgDao.insertFromMsg(msg);
    
    
    //쪽지를 보내면 받는사람쪽에서 msg알림 on. vertx server로 데이터 전송
    int msgCheck = isReadMsg(loadUserNo);
    if(msgCheck > 0) {
      vertxServer.getIo().sockets().emit("echo", new JsonObject().putNumber("data", msgCheck));
      log.debug("===== addmsg server ===== " + msgCheck);
    }
    
    return count;
  }
  
  
  
  
  
  //답장보내기
  public int addRePlayMsg(Msg msg) throws Exception {
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    log.debug("addReplayMsg_myUserDetails = " + myUserDetails);
    msg.setFromUserNo(myUserDetails.getUserNo());
    msg.setFromUserNicName(myUserDetails.getNicName());
//    msg.setMsgContent(toTEXT(msg.getMsgContent())); //입력된 특수기호를 변환
    log.debug("addReplayMsg_msg = " + msg);
    
    //받은쪽지 테이블로 insert
    msgDao.insertToMsg(msg);
    
    
    //보낸쪽지 테이블로 insert
    int count = msgDao.insertFromMsg(msg);
    
    
    //쪽지를 보내면 받는사람쪽에서 msg알림 on. vertx server로 데이터 전송
    int msgCheck = isReadMsg(msg.getToUserNo());
    if(msgCheck > 0) {
      vertxServer.getIo().sockets().emit("echo", new JsonObject().putNumber("data", msgCheck));
      log.debug("===== addReplayMsg userNo ===== " + msg.getToUserNo());
      log.debug("===== addReplayMsg server ===== " + msgCheck);
    }
    
    return count;
  }
  
  
  
  
  
  //체크된 메시지항목 저장
  public int addSaveMsg(List<String> checkedMsgs, int msgListNo) throws Exception {
    Map<String, Integer> msgNosMap = new HashMap<String, Integer>();
    Msg msg = null;
    int count = 0;
    log.debug("checkMsgNos = " + checkedMsgs);
    
    for(String msgNo : checkedMsgs) {
      msgNosMap.put("msgNo", Integer.parseInt(msgNo)); //리스트의 값들을 key:value형태로 만들어준다.
      
      if(msgListNo == 1) {
        msg = msgDao.selectToMsg(msgNosMap);
        msgDao.insertSaveMsg(msg);
        msgDao.deleteToMsg(msgNosMap);
        log.debug("removeToMsg = " + msgNo);
        count = msgListNo;
        
      } 
    }
    
    return count;
  }
  
  
  
  
  /**==========================  select(get)  ===============================*/
  
  //쪽지함 List 전체 글 개수 가져오기
  public int getListToTalNum(int toUserNo, int msgListNo) throws Exception {
    int count = 0;
    
    switch(msgListNo) {
    case 1:
      count = msgDao.selectToMsgListToTalCnt(toUserNo);
      break;
      
    case 2:
      count = msgDao.selectFromMsgListToTalCnt(toUserNo);
      break;
      
    case 3:
      count = msgDao.selectSaveMsgListToTalCnt(toUserNo);
      break;
    
    }
    
    
    return count;
  }
  
  
  
  //메시지 열람 체크
  public int isFlagNum(int msgNo, int msgListNo) throws Exception {

    int count = 0;
    
    switch(msgListNo) {
    case 1://toList
      count = msgDao.selectToMsgFlag(msgNo);
      break;
      
    case 3://saveList
      count = msgDao.selectSaveMsgFlag(msgNo);
      break;
    }
    
    vertxServer.getIo().sockets().emit("echo", new JsonObject().putString("isMsgCount", "isMsgCount"));

    return count;
  }
  
  
  
  
  //총 읽은 쪽지확인
  public int isReadMsg(int userNo) throws Exception {
    int sum = 0;

    int count1 = msgDao.selectToListMsg(userNo);
    int count2 = msgDao.selectSaveListMsg(userNo);
    
    sum = count1 + count2;
    
    return sum;
  }
  
  
  
  /**==========================  list(list)   ===============================*/
  
  //받은쪽지 리스트 가져오기 ajax용
  public List<Msg> msgList(Map<String, Integer> pageMap) throws Exception {
    
    MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<Msg> list = null;
    
    int pageNoCal = ((pageMap.get("pageNo")-1)*10);
    log.debug("getMsgList = " + pageMap);
    log.debug("selectNo = " + pageMap.get("msgListNo"));
    
    
    switch(pageMap.get("msgListNo")) {
      
      case 1: //msgList
        pageMap.put("pageNo", pageNoCal);
        pageMap.put("toUserNo", myUserDetails.getUserNo());
        
        list = msgDao.toMsgList(pageMap);
        log.debug("msgDao(msgList)" + list);
      break;
    
      case 2: //fromMsgList
        pageMap.put("pageNo", pageNoCal);
        pageMap.put("toUserNo", myUserDetails.getUserNo());
        
        list = msgDao.fromMsgList(pageMap);
        log.debug("fromMsgDao(fromMsgList)" + list);
      break;
    
      case 3: //saveMsgList
        pageMap.put("pageNo", pageNoCal);
        pageMap.put("toUserNo", myUserDetails.getUserNo());
        
        list = msgDao.saveMsgList(pageMap);
        log.debug("saveMsgDao(saveMsgList)" + list);
      break;
  }
    
    return list; 
  }
  
  
  
  
  /**==========================  delete(remove)  ============================*/
  
  //체크된 메시지항목 삭제
  public int removeMsg(List<String> checkedMsgs, int msgListNo) throws Exception {
    Map<String, Integer> msgNosMap = new HashMap<String, Integer>();
    int count = 0;
    log.debug("checkMsgNos = " + checkedMsgs);
    
    for(String msgNo : checkedMsgs) {
      msgNosMap.put("msgNo", Integer.parseInt(msgNo)); //리스트의 값들을 key:value형태로 만들어준다.
      
      if(msgListNo == 1) {
        msgDao.deleteToMsg(msgNosMap);
        log.debug("removeToMsg = " + msgNo);
        count = msgListNo;
        
      } else if(msgListNo == 2) {
        msgDao.deleteFromMsg(msgNosMap);
        log.debug("removeFromMsg = " + msgNo);
        count = msgListNo;
        
      } else if(msgListNo == 3) {
        msgDao.deleteSaveMsg(msgNosMap);
        log.debug("removeSaveMsg = " + msgNo);
        count = msgListNo;
      }
      
    }
    
    return count;
  }


  

  
  
  
  
  
  
//  ======================= 특수문자 치환 ==================================
  
//  public String toTEXT(String srcString) {
//    String rtnStr = null;
//    try{
//    StringBuffer strTxt = new StringBuffer("");
//    char chrBuff;
//    int len = srcString.length();
//
//    for(int i = 0; i < len; i++) {
//    chrBuff = (char)srcString.charAt(i);
//
//    switch(chrBuff) {
//    case '<':
//    strTxt.append("&lt;");
//    break;
//    case '>':
//    strTxt.append("&gt;");
//    break;
//    case '&':
//    strTxt.append("&amp;");
//    break;
//    default:
//    strTxt.append(chrBuff);
//    }
//    }
//
//    rtnStr = strTxt.toString();
//
//    }catch(Exception e) {
//    e.printStackTrace();
//    }
//
//    return rtnStr;
//    }
  
  
}
