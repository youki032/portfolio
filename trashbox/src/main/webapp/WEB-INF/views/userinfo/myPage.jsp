<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>


    <!-- Standard Meta -->
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    
    
    <!-- Site Properties -->
    <title> - MyPage_TrashBox - </title>
    
    
    <!-- jQuery Library -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery-1.11.3.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery-ui-1.11.4.min.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/javascript/css/jquery-ui-1.11.4.css" />" >
    
    
    <!-- sweetalert -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/sweetalert.min.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/javascript/css/sweetalert.css"/>">
        
        
    <!-- shortcut icon -->
    <link rel="shortcut icon" href="<c:url value="/resources/trashbox/icons/tricolor.ico"/>" type="image/x-icon">
    
    
    <!-- Semantic-UI Components -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/semantic-ui/dist/semantic.min.css"/>">
    <script type="text/javascript" src="<c:url value="/resources/semantic-ui/dist/semantic.min.js"/>"></script>
    
    
    <!-- trashbox css, js -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/trashbox/trashbox.userClass.js"/>" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-mypage.css"/>">  
    
      
    <!-- security -->
    <sec:authorize access="isAuthenticated()"> 
        <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/security/trashbox-authenticated.js"/>"></script> 
    </sec:authorize> 
    
    <sec:authorize access="isAnonymous()">
        <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/security/trashbox-anonymous.js"/>"></script> 
    </sec:authorize>

  
<script type="text/javascript">
// 	userEdit창을 여는 부모창임을 명명.
var openName = window.name = "parentMyPage";  

var loadUserLevel = ${userClass.userLevel};
var loadNowExp = ${userClass.nowExp};
var loadNextExp = ${userClass.nextExp};

 
	function editpop() {

	 var url = "${pageContext.request.contextPath}/userinfo/userEdit";
	 var title = "UserEdit";
	 var status = 'location=no,toolbar=no,directories=no,scrollbars=no,resizable=no,status=no,menubar=no,width=800, height=700, top=0,left=20'; 
	 window.open(url, title, status, openName, "");
	
	}
	
	

</script>
</head>

<body id="trashbox">
<jsp:include page="../include/menubar.jsp" flush="true" />

<div class="pusher">

<!-- MainPage Contents -->

        <div id="banner" class="ui inverted vertical masthead center aligned segment">
            <div class="ui text container">
            <h2 class="ui header" id="header">
              <i class="circular user icon"></i>MyPage
              </h2>
              
     
            

<div id="mypageImg" class="ui segment">
        <h4 class="ui horizontal divider header">
          <i class="tag icon"></i>
                       회원 정보
        </h4>

<div class="ui divided items">
  <div class="item">
    <div class="image">
      <c:choose>
        <c:when test="${empty userInfo.profilePath}">
          <img id="imgResizeing" src="<c:url value="/files/profile/square-image.png"/>" >
        </c:when>

        <c:when test="${!empty userInfo.profilePath}">
          <img id="imgResizeing" src="<c:url value="/files/profile/${userInfo.profilePath}"/>" >
        </c:when>
      </c:choose>
    </div>
    
    <div class="content">
      <div class="nic_header">
        <h3>
          <span id="level_icon"></span>&nbsp;&nbsp;
          ${myUserDetails.nicName}&nbsp;(${myUserDetails.username})</h3>
      </div>
      
        <span class="mypage_nicname">
          <c:if test="${!empty userInfo.prevNicName }">
            <p id="nic_text">(변경전 닉네임:&nbsp;&nbsp;${userInfo.prevNicName})</p>
          </c:if>
        </span>
        
        <div class="ui indicating small progress" id="progress">
           <div class="bar">
              <div class="progress"></div>
              <label>Exp</label>
           </div>
        </div>
      
      <div class="description">
        <ul>
          <li>등   급  :&nbsp;&nbsp;${userInfo.userClass}</li>
          <li>출   석  :&nbsp;&nbsp;${userInfo.attendance}회&nbsp;&nbsp;<span id="attendance_inLine"></span>
          (마지막 출석일: ${userInfo.attendanceDate})</li>
          <li>가입일  :&nbsp;&nbsp; ${userInfo.signDate}</li>
        </ul>
      </div>
      
      <div class="extra">
        <div class="ui label"><a href="javascript:editpop();"><i class="settings icon"></i>정보수정</a></div>
      </div>
      
    </div>
  </div>
</div>


<h4 class="ui horizontal divider header">
  <i class="bar chart icon"></i>
  활동 내역
</h4>
<table class="ui celled center aligned table">
  <thead>
  <tr>
      <th>작성 게시글</th>
      <th>삭제 게시글</th>
<!--       <th>추천수</th> -->
      <th>작성 댓글수</th>
      <th>삭제 덧글수</th>
    </tr>
  </thead>  
  <tbody>
    <tr>
      <td class="write_article">${userActivity.articleWriteCnt}</td>
      <td class="remove_article">${userActivity.articleRemoveCnt}</td>
<!--       <td class="like_All">5</td> -->
      <td class="write_comment">${userActivity.commentWriteCnt}</td>
      <td class="remove_comment">${userActivity.commentRemoveCnt}</td>
      <td class="resizing_column">
      <div class="mini_column">
        <ul> 
         <li>작성게시글 :&nbsp;&nbsp;&nbsp;${userActivity.articleWriteCnt}개</li>
         <li>삭제게시글 :&nbsp;&nbsp;&nbsp;${userActivity.articleRemoveCnt}개</li>
         <li>작성코멘트 :&nbsp;&nbsp;&nbsp;${userActivity.commentWriteCnt}개</li>
         <li>삭제코멘트 :&nbsp;&nbsp;&nbsp;${userActivity.commentRemoveCnt}개</li>
        </ul>
      </div>
      
      </td>
    </tr>
  </tbody>
</table>
</div>







            
            
           </div> 
        </div>


      
      

<div id="footer">       
<jsp:include page="../include/footer.jsp" flush="true" />
</div>
     
<!-- </div> -->


</div>
</body>
</html>