package net.spring.board.dao;

import java.util.List;
import java.util.Map;

import net.spring.board.vo.Msg;

public interface MsgDao {
  
  
  
  /************************************************************************
   ****************************** To List *********************************
   ************************************************************************/  
  //받은 쪽지함에 insert
  int insertToMsg(Msg msg) throws Exception;
  
  //받은쪽지함 list 가져오기
  List<Msg> toMsgList(Map<String, Integer> pageMap) throws Exception;
  
  //toList 쪽지list 총 개수 가져오기
  int selectToMsgListToTalCnt(int toUserNo) throws Exception;
  
  //toList 쪽지삭제
  int deleteToMsg(Map<String, Integer> msgNosMap) throws Exception;
  
  //toList메시지 열람체크
  int selectToMsgFlag(int msgNo) throws Exception;
  
  
  
  
  
  
  /************************************************************************
   ****************************** From List *******************************
   ************************************************************************/
  
  //보낸 쪽지함에 insert
  int insertFromMsg(Msg msg) throws Exception;
  
  //보낸쪽지함 list 가져오기
  List<Msg> fromMsgList(Map<String, Integer> pageMap) throws Exception;

  //fromList 쪽지list 총 개수 가져오기
  int selectFromMsgListToTalCnt(int toUserNo) throws Exception;
  
  //답장보내기
//  int insertRePlayMsg(Msg msg) throws Exception;
  
  //fromList 쪽지삭제
  int deleteFromMsg(Map<String, Integer> msgNosMap) throws Exception;
  
  
  
  
  
  /************************************************************************
   ****************************** Save List *******************************
   ************************************************************************/  
  //쪽지저장
  int insertSaveMsg(Msg msg) throws Exception;
  
  //쪽지보관리스트 불러오기
  List<Msg> saveMsgList(Map<String, Integer> pageMap) throws Exception;
  
  //saveList 쪽지list 총 개수 가져오기
  int selectSaveMsgListToTalCnt(int toUserNo) throws Exception;
  
  //fromList 쪽지삭제
  int deleteSaveMsg(Map<String, Integer> msgNosMap) throws Exception;
  
  //저장할 toList의 내용을 불러온다
  Msg selectToMsg(Map<String, Integer> msgNosMap) throws Exception;
  
  //저장할 fromList의 내용을 불러온다
  Msg selectFromMsg(Map<String, Integer> msgNosMap) throws Exception;
  
  //saveList메시지 열람체크
  int selectSaveMsgFlag(int msgNo) throws Exception;
  
  
  /************************************************************************
   ****************************** Msg Check *******************************
   ************************************************************************/

  
  //받는사람의 나머지정보(userNo, email)를 가져온다
  Map<String, Object> selectToUserInfo(Msg msg) throws Exception;
  
  //쪽지확인
  int selectToListMsg(int userNo) throws Exception;
  
  int selectSaveListMsg(int userNo) throws Exception;
}
