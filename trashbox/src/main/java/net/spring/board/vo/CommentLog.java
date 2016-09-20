package net.spring.board.vo;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import net.spring.board.security.MyUserDetails;

public class CommentLog implements Serializable {

  private static final long serialVersionUID = 1L;
  
  public static final String CMD_INSERT = "INSERT";
  public static final String CMD_SELECT = "SELECT";
  public static final String CMD_UPDATE = "UPDATE";
  public static final String CMD_DELETE = "DELETE";
  
  
  protected int     logNo;
  protected int     userNo;
  protected int     commentNo;
  protected String  ipAddress;
  protected String  command;
  protected String  commandDate;
  
  
  public CommentLog(String command, HttpServletRequest request, Authentication authentication) throws Exception {
    
    MyUserDetails myUserDetails = (MyUserDetails)authentication.getPrincipal();
    this.userNo = myUserDetails.getUserNo();
    this.ipAddress = getClientIP(request);
    this.command = command;
    
  }
  
  
  public int getLogNo() {
    return logNo;
  }
  public void setLogNo(int logNo) {
    this.logNo = logNo;
  }
  
  public int getCommentNo() {
    return commentNo;
  }
  public void setCommentNo(int commentNo) {
    this.commentNo = commentNo;
  }
  public String getIpAddress() {
    return ipAddress;
  }
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }
  public String getCommand() {
    return command;
  }
  public void setCommand(String command) {
    this.command = command;
  }
  public int getUserNo() {
    return userNo;
  }
  public void setUserNo(int userNo) {
    this.userNo = userNo;
  }
  public String getCommandDate() {
    return commandDate;
  }
  public void setCommandDate(String commandDate) {
    this.commandDate = commandDate;
  }
  
  @Override
  public String toString() {
    return "CommentLog [logNo=" + logNo + ", commentNo=" + commentNo + ", userNo=" + userNo + ", ipAddress=" + ipAddress
        + ", command=" + command + ", commandDate=" + commandDate + "]";
  }

  
//접속 클라이언트 ip가져오기
public String getClientIP(HttpServletRequest request) {
  String ipStr = request.getHeader("X-FORWARDED-FOR");
  
  if (ipStr == null || ipStr.length() == 0) {
    ipStr = request.getHeader("Proxy-Client-IP");
  }
  
  if (ipStr == null || ipStr.length() == 0) {
    ipStr = request.getHeader("WL-Proxy-Client-IP");//웹로직
  }
  
  if (ipStr == null || ipStr.length() == 0) {
    ipStr = request.getRemoteAddr();
  }
  
  
  return ipStr;
}
  
  
}
