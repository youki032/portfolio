package net.spring.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

import net.spring.board.vo.Msg;

public interface MsgService {
  
  
  
  /**===========================  add  ===================================*/
  
  //쪽지보내기(from)
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public int addMsg(Msg msg) throws Exception;
  
  //답장보내기
  public int addRePlayMsg(Msg msg) throws Exception;
  
  //체크된 메시지 항목 저장
  public int addSaveMsg(List<String> checkedMsgs, int msgListNo) throws Exception;
  
  
  
  /**===========================  get  ===================================*/
  
  //읽은 쪽지 확인
  public int isReadMsg(int userNo) throws Exception;
  
  //메시지 열람 체크
  public int isFlagNum(int msgNo, int msgListNo) throws Exception;
  
  
  //쪽지함 목록들 쪽지 총 개수 가져오기
  public int getListToTalNum(int toUserNo, int msgListNo) throws Exception;
  
  
  
  
  /**===========================  list  ===================================*/
  
  //받은쪽지 리스트 가져오기 
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public List<Msg> msgList(Map<String, Integer> pageMap) throws Exception;
  
  
  

  
  
  
  /**===========================  remove  =================================*/
  //체크된 메시지 항목 삭제
  public int removeMsg(List<String> checkedMsgs, int msgListNo) throws Exception;
  
    
  


}
