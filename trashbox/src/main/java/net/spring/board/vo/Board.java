package net.spring.board.vo;

import java.io.Serializable;

public class Board implements Serializable {
  private static final long serialVersionUID = 1L;
  
  protected int     boardNo;
  protected String  boardName;
  
  
  public int getBoardNo() {
    return boardNo;
  }
  public void setBoardNo(int boardNo) {
    this.boardNo = boardNo;
  }
  public String getBoardName() {
    return boardName;
  }
  public void setBoardName(String boardName) {
    this.boardName = boardName;
  }
  
  @Override
  public String toString() {
    return "Board [boardNo=" + boardNo + ", boardName=" + boardName + "]";
  }
  
  
  

}
