package net.spring.board.dao;

import java.util.List;

import net.spring.board.vo.Role;

public interface RoleDao {
  
  
  /**==========================  select(get)  ===============================*/

  //MyUserDetails - 로그인시 유저No로 권한 가져오기.
  public List<Role> selectRole(int userNo);

}
