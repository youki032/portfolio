package net.spring.board.dao;

import net.spring.board.vo.Board;

public interface BoardDao {
  
  
  /**==========================  select(get)  ===============================*/

  //게시판번호를 가져옴
  public Board selectBoard(int boardNo);

}
