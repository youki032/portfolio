<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
  
  
    <!-- Standard Meta -->
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    
    
    <!-- csrf Meta -->
    <sec:csrfMetaTags/>
    <meta name="_csrf_header" content="${_csrf.headerName}" />
    <meta name="_csrf" content="${_csrf.token}" />
    
    
    <!-- Site Properties -->
    <title> - UserEdit_TrashBox - </title> 
    
    
    <!-- jQuery Library -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery-1.11.3.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery-ui-1.11.4.min.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/javascript/css/jquery-ui-1.11.4.css" />" >
    
    
    <!-- sweetalert -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/sweetalert.min.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/javascript/css/sweetalert.css"/>">
        
        
    <!-- shortcut icon -->
    <link rel="shortcut icon" href="<c:url value="/resources/trashbox/icons/tricolor.ico"/>" type="image/x-icon">
    
    
    <!-- daum post search Library -->
    <!-- https -->
<!--     <script src="https://spi.maps.daum.net/imap/map_js_init/postcode.v2.js"></script> -->
    <!-- http -->
    <script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
    
    
    <!-- trashbox css, js -->    
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/trashbox/trashbox.userEdit.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-userEdit.css"/>">  
    

    <!-- Semantic-UI Components -->
    <jsp:include page="../include/library.jsp" flush="true" />
      
  
    <!-- security -->
    <sec:authorize access="isAuthenticated()"> 
        <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/security/trashbox-authenticated.js"/>"></script> 
    </sec:authorize> 
    
    <sec:authorize access="isAnonymous()">
        <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/security/trashbox-anonymous.js"/>"></script> 
    </sec:authorize>


<script type="text/javascript">
window.name = "parentUserEdit";

//닉네임 변경일 가져오기
var loadNicDate = "${userInfo.nicChangeDate}";
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

	   
</script>




 </head>
 
<body id="trashbox" onload="javascript:do_vaildChangeNic();">

<!--       <div class="ui text container"> -->

<!--       </div> -->
        
        
      <div id="banner" class="ui inverted verticalmasthead center aligned segment">
         <div class="ui text container">
              <h2 class="ui header" id="normal_header">
                  <i class="circular write icon"></i>정보수정
              </h2>
                
            

    
<form class="ui small form" name="editFrm" id="editFrm" method="post" action="userEdit" enctype="multipart/form-data"">    
  <sec:csrfInput/>
<table class="ui celled table" border="2">
<tbody>
    <tr>
      <td>이메일</td>
      <td><p>${myUserDetails.username }&nbsp;&nbsp;&nbsp;&nbsp;(가입일: ${myUserDetails.signDate })</p></td>
    </tr>
    
    <tr>
      <td>password변경</td>
      <th>
          <div class="two fields">
            <div class="field">
              <div class="ui mini icon input">
                <i class="lock icon"></i>
                  <input type="password" name="newPassword" id="newPassword" placeholder="변경하실 password를 입력하세요." />
              </div>
            </div>
            
            <div class="field">
              <div class="ui mini icon input">
                <i class="lock icon"></i>
                  <input type="password" id="newPasswordCfm" placeholder="다시한번 password를 입력해주세요.">
              </div>
            </div>
          </div>
     </th>
    </tr>
    
   
    <tr>
      <td>닉네임 변경</td>
      <th>
          <div class="two fields">
            <div class="field">
              <div class="ui tiny icon input">
                <i class="lock icon"></i>
                  <input type="text" name="newNicName" id="newNicName" placeholder="현재 닉네임 : ${myUserDetails.nicName}" readonly="readonly"/>
              </div>
            </div>
            
            
            <div class="field">
              <div class="ui mini icon input">
                  <a href="javascript:do_vaildChangeNicPopUp();">
                    <i class="ui mini gray button">닉네임변경</i>
                  </a><p id="nicDisable"></p>
              </div>
            </div>
              
          </div>
     </th>
    </tr>
    
    <tr>
      <td>실명</td>
      <td>${myUserDetails.userName }</td>
    </tr>
    
    <tr>
      <td>생년월일</td>
      <td>${userInfo.birthDate}</td>
    </tr>
    
    <tr>
      <td>주소</td>
      <th>
          <div class="wide two fields">
            <div class="field">
              <div class="ui tiny icon input">
                <i class="building outline icon"></i>
                <input type="text" name="address1" id="address1" placeholder="${userInfo.address1 }" />
              </div>
            </div>
            
            <div class="field">
              <div class="ui tiny icon input">
                <a href="javascript:postSearch();"><i class="ui mini gray button">우편번호 검색</i></a>
<!--                   <input class="ui mini gray button" onclick="postSearch()" value="우편번호 검색"/> -->
                  
              </div>
            </div>
          </div>
          
           <div class="wide two fields">
            <div class="field">
              <div class="ui tiny icon input">
                <i class="building outline icon"></i>
                <input type="text" name="address2" id="address2" placeholder="${userInfo.address2 }" />
              </div>
            </div>
            
            <div class="field">
              <div class="ui tiny icon input">
                <i class="building outline icon"></i>
                <input type="text" name="address3" id="address3" placeholder="${userInfo.address3 }" />
              </div>
            </div>
          </div>
     </th>
     
    </tr>
    
    <tr>
      <td>핸드폰</td>
      <th>
          <div class="wide two fields">
            <div class="field">
              <div class="ui tiny icon input">
              <i class="phone icon"></i>
                <input type="text" name="phone" id="phone" placeholder="${userInfo.phone }"/>
              </div>
<!--       <td><input type="text" name="phone" id="phone"/></td> -->
            </div>
            <div class="field">
            <p>※ " - "를 뺀 숫자만 입력해주세요. </p>
            </div>
         </div>
      </th>
    </tr>
  
    <tr>
      <td>자기소개</td>
      <td>
      
      <textarea name="selfIntroduction" id="selfIntroduction" placeholder="${userInfo.selfIntroduction}">${userInfo.selfIntroduction}</textarea>
      <span id="type_num">0</span>/250bytes
    </tr>
    
    <tr>
      <td>프로필사진</td>
      <td><input type="file" name="file" /> </td>
      
    </tr>
    
  </tbody>

</table> 
<!-- <input type="hidden" onsubmit="javascript:do_close();"> -->
<input class="ui inverted tiny yellow button" type="button" value="변경" onclick="javascript:do_submit();"/>
<input class="ui inverted tiny yellow button" type="button" value="취소" onclick="javascript:do_cancel();"/>
<input class="ui inverted tiny yellow button" type="button" value="회원탈퇴" id="userBye" onclick="javascript:do_userBye();"/>

</form>


 
    
    </div>
        </div>   
    
    
    
</body>
</html>