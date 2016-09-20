package net.spring.board.vo;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class ImageBean implements Serializable {
  private static final long serialVersionUID = 1L;

  protected String attach_path;
  protected MultipartFile upload;
  protected String filename;
  protected String CKEditorFuncNum;  //ckeditor 대상을 기억하기위한 값을 저장
  protected String generateFilename;
  
  
  public String getAttach_path() {
    return attach_path;
  }
  public void setAttach_path(String attach_path) {
    this.attach_path = attach_path;
  }
  public MultipartFile getUpload() {
    return upload;
  }
  public void setUpload(MultipartFile upload) {
    this.upload = upload;
  }
  public String getFilename() {
    return filename;
  }
  public void setFilename(String filename) {
    this.filename = filename;
  }
  public String getCKEditorFuncNum() {
    return CKEditorFuncNum;
  }
  public void setCKEditorFuncNum(String cKEditorFuncNum) {
    CKEditorFuncNum = cKEditorFuncNum;
  }
  public String getGenerateFilename() {
    return generateFilename;
  }
  public void setGenerateFilename(String generateFilename) {
    this.generateFilename = generateFilename;
  }
  
  
}
