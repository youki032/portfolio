package net.spring.board.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

import net.spring.board.vo.Board;
import net.spring.board.vo.ChangeEdit;
import net.spring.board.vo.User;
import net.spring.board.vo.UserActivity;
import net.spring.board.vo.UserClass;
import net.spring.board.vo.UserInfo;

public interface UserService {
  
  
  
  /**===========================  add  ===================================*/
  
  //시큐리티 로그인 회원가입정보, 회원권한추가
  public int addUser(User user);
  
  //패스워드, 닉네임없이 기본회원정보만 변경시
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public int addUserEdit(UserInfo userInfo);
  
  //기본정보외 패스워드, 닉네임까지 변경시
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public int addUserEdit(UserInfo userInfo, ChangeEdit changeEdit) throws ParseException;
  
  //출석 업데이트
  public void attendance() throws Exception;
  
  
  /**===========================  get  ===================================*/
  //게시판제목의 번호와 이름 가져오기
  public Board getBoard(int boardNo);
  
  //회원 추가정보 가져오기
  //  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
  public UserInfo getUserInfo(int userNo);
  
  //회원가입 id중복체크
  public int getEmailCheck(String email) throws Exception;
  
  //닉네임중복 체크(user테이블에서 찾기)
  public int getUserNicNameCheck(String nicName) throws Exception;
  
  //넥네임중복 체크(article테이블에서 찾기)
  public int getArticleNicNameCheck(String nicName) throws Exception;
  
  
  //회원정보찾기 - email
  public List<User> getFindEmail(User user) throws Exception;
  
  //회원정보찾기 - password
  public User getFindPassword(User user) throws Exception;
  
  
  //회원탈퇴 - 회원탈퇴시 유저번호 가져오기
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
  public User getUser(int userNo);
  
  
  //마이페이지 활동내역 
  public UserActivity getUserActivity(int userNo) throws Exception;
  
  
  /**===========================  remove  =================================*/
  //회원탈퇴, 탈퇴진행을 위해 password를 확인한다.
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MASTER')")
  public int getByePassword(String password, int userNo) throws Exception;
  
  
}
