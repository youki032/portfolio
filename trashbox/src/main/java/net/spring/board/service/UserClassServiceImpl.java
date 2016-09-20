package net.spring.board.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import net.spring.board.dao.UserClassDao;
import net.spring.board.security.MyUserDetails;
import net.spring.board.vo.UserClass;


@Service("UserClassService")
public class UserClassServiceImpl implements UserClassService {
  protected Logger log = LogManager.getLogger(UserClassServiceImpl.class);
  
  @Autowired private UserClassDao userClassDao;
  
  
  
  public UserClass getUserClass(int userNo) throws Exception {
    UserClass userClass = userClassDao.selectUserClass(userNo);
    
    int userLevel = userClass.getUserLevel();
    int nowExp = userClass.getNowExp();
    int nextExp = userClass.getNextExp();
    int calculation = 0;
    int restExp = 0;
    
    if (nowExp >= nextExp) {
      
      while (true) {
        
        userLevel = userLevel + 1;
        calculation = ((userLevel * 22) * 100) / 10;
        
        log.debug("== userLevel == " + userLevel);
        log.debug("== nextExp == " + calculation);
        
        if (calculation >= nowExp) {
          restExp = calculation - nowExp; // 레벨업되고 남은경험치
          break;
          }
      } 
      
      Map<String, Integer> levelUpMap = new HashMap<String, Integer>();
      levelUpMap.put("userLevel", userLevel);
      levelUpMap.put("nextExp", calculation);
      levelUpMap.put("userNo", userNo);
      levelUpMap.put("nowExp", restExp);
      
      userClassDao.updateLevelUp(levelUpMap);  //레벨업 적용
      
      userClass = userClassDao.selectUserClass(userNo);  //적용된 정보 다시 가져오기
      
    }
    
    return userClass;
    
  }
  
  
  public int addExp(int addExp, int userNo) throws Exception {
//    MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Map<String, Integer> expMap = new HashMap<String, Integer>();
    expMap.put("userNo", userNo);
    expMap.put("nowExp", addExp);
    
    return userClassDao.updateExp(expMap);
  }

}
