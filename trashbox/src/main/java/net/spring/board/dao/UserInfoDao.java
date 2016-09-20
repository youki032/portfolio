package net.spring.board.dao;

import net.spring.board.vo.UserInfo;

public interface UserInfoDao {
  
  
  /**==========================  select(get)  ===============================*/
  
  //유저No로 MyPage 회원정보 가져오기
  public UserInfo selectUserInfo(int userNo);

  
  //출석날짜 컬럼이 비었을때 출석한 날짜를 넣어줌
  public void updateAttendanceDate(UserInfo userInfo);
}
