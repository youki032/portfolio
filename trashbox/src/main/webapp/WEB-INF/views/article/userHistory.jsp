<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html lang="ko">
<head>
  
  
    <!-- Standard Meta -->
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    
    
    <!-- Site Properties -->
    <title> - 회원정보 - </title>  
    
    
    <!-- jQuery Library -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery-1.11.3.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery-ui-1.11.4.min.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/javascript/css/jquery-ui-1.11.4.css" />" >
    
    
    <!-- jQuery Paging -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/paging.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/javascript/css/paging.css"/>">
    
    
    <!-- sweetalert -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/sweetalert.min.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/javascript/css/sweetalert.css"/>">
        
        
    <!-- shortcut icon -->
    <link rel="shortcut icon" href="<c:url value="/resources/trashbox/icons/tricolor.ico"/>" type="image/x-icon">
    
    
    <!-- trashbox css, js -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/trashbox/trashbox.userClass.js"/>" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-userHistory.css"/>">
    
    
    <!-- Semantic-UI Components -->
    <jsp:include page="../include/library.jsp" flush="true" />


<script type="text/javascript">
var loadUserLevel = ${userClass.userLevel};
var loadNowExp = ${userClass.nowExp};
var loadNextExp = ${userClass.nextExp};

</script>


</head>

<body id="trashbox">
  
    <div class="ui center aligned header">
      <h2 class="ui small header">
            회원정보
      </h2>
      <c:choose>
        <c:when test="${empty historyUserInfo.profilePath}">
          <img id="profileImage" src="<c:url value="/files/profile/square-image.png"/>" >
        </c:when>

        <c:when test="${!empty historyUserInfo.profilePath}">
          <img id="profileImage" src="<c:url value="/files/profile/${historyUserInfo.profilePath}"/>" >
        </c:when>
      </c:choose>
     </div>

  
<div class="ui red container segment">
  <ul>
    <li>아이디 :&nbsp;&nbsp;${historyUser.email}</li>
    <li>닉네임 :&nbsp;&nbsp;<span id="level_icon"></span>&nbsp;&nbsp;${historyUser.nicName }
       <div class="ui indicating small progress" id="progress">
         <div class="bar">
            <div class="progress"></div>
         </div>
       </div>
    </li>
    <li>생년월일 :&nbsp;&nbsp;${historyUserInfo.birthDate }</li>
    <li>등급 :&nbsp;&nbsp;${historyUserInfo.userClass }</li>
    <li>가입일 :&nbsp;&nbsp;${historyUserInfo.signDate }</li>
    <li>활동내역 :&nbsp;&nbsp;작성 게시글 ${userActivity.articleWriteCnt}회, <br /> 
              삭제 게시글 ${userActivity.articleRemoveCnt}회, <br /> 
              작성 댓글수 ${userActivity.commentWriteCnt}회, <br /> 
              삭제 덧글수 ${userActivity.commentRemoveCnt}회</li>
    <li>자기소개 :&nbsp;&nbsp;<br />${historyUserInfo.selfIntroduction }</li>
  </ul>
</div>

<div id="historyBtn">
  <button class="ui tiny green button" onclick="javascript:self.close();">확인</button>
</div>

</body>
</html>