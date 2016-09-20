package net.spring.board.dao;

import java.util.Map;

import net.spring.board.vo.UserClass;

public interface UserClassDao {
    
   //유저의 레벨, 경험치 정보를 가져온다. 
   UserClass selectUserClass(int userNo);
   
   
   //글쓰기, 추천등으로 인한 경험치를 업데이트 해준다.
   int updateExp(Map<String, Integer> expMap);
   
   
   //경험치가 쌓이면 레벨업을 해준다.
   int updateLevelUp(Map<String, Integer> levelUpMap);
   
}
