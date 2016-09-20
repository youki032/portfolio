package net.spring.board.service;

import java.util.Map;

import net.spring.board.vo.UserClass;

public interface UserClassService {
  
  public UserClass getUserClass(int userNo) throws Exception;
  
  public int addExp(int addExp, int userNo) throws Exception;
  
}
