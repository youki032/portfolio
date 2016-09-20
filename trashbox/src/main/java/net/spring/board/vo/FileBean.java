package net.spring.board.vo;

import java.io.Serializable;

public class FileBean implements Serializable {
  private static final long serialVersionUID = 1L;

  protected int files_no;
  protected int listNo;
  protected int articleNo;
  protected int boardNo;
  protected String originalFilePath;
  protected String renameFilePath;
  public int getFiles_no() {
    return files_no;
  }
  public void setFiles_no(int files_no) {
    this.files_no = files_no;
  }
  public int getListNo() {
    return listNo;
  }
  public void setListNo(int listNo) {
    this.listNo = listNo;
  }
  public int getArticleNo() {
    return articleNo;
  }
  public void setArticleNo(int articleNo) {
    this.articleNo = articleNo;
  }
  public int getBoardNo() {
    return boardNo;
  }
  public void setBoardNo(int boardNo) {
    this.boardNo = boardNo;
  }
  public String getOriginalFilePath() {
    return originalFilePath;
  }
  public void setOriginalFilePath(String originalFilePath) {
    this.originalFilePath = originalFilePath;
  }
  public String getRenameFilePath() {
    return renameFilePath;
  }
  public void setRenameFilePath(String renameFilePath) {
    this.renameFilePath = renameFilePath;
  }
  
  @Override
  public String toString() {
    return "FileBean [files_no=" + files_no + ", listNo=" + listNo + ", articleNo=" + articleNo + ", boardNo=" + boardNo
        + ", originalFilePath=" + originalFilePath + ", renameFilePath=" + renameFilePath + "]";
  }
  
}
