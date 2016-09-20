<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>


    <!-- Standard Meta -->
    <meta charset='UTF-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    
    
    <!-- Site Properties -->
    <title> - Denied_TrashBox - </title>  
    
    
    <!-- jQuery Library -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery-1.11.3.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery-ui-1.11.4.min.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/javascript/css/jquery-ui-1.11.4.css" />" >
    
    
    <!-- sweetalert -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/sweetalert.min.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/javascript/css/sweetalert.css"/>">
        
        
    <!-- shortcut icon -->
    <link rel="shortcut icon" href="<c:url value="/resources/trashbox/icons/tricolor.ico"/>" type="image/x-icon">
    
    
    <!-- trashbox css -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-error.css"/>">  
    
    
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
$(document).ready(function () {
	
	
});

var ctx = "";   // context path
/*
현재 보는 페이지의 URL을 로그인 페이지에 인자값으로 던져서 로그인 페이지를 보여주도록 한다
 */
function showLogin(){
  var showPath = location.pathname;
  var showSearch = location.search;
  var showUrl = "";
  
  if(showSearch == ""){
    showUrl = showPath;
  }else{
    showUrl = showPath = "?" + showSearch;
  }
  
  showUrl = encodeURIComponent(showUrl);
  
  location.href = ctx + "/home/login"; 
}

/*
현재 보는 페이지의 URL을 로그인 페이지에 인자값으로 던져서 로그인 페이지를 보여주도록 한다
 */
function popupLogin(){
  var popUrl = ctx + "/loginPopup.do";   //팝업창에 출력될 페이지 URL
  var popOption = "width=400, height=400, resizable=no, scrollbars=no, status=no;";    //팝업창 옵션(optoin)
  window.open(popUrl,"",popOption);
}


</script>

</head>
<body id="trashbox">

<jsp:include page="../include/menubar.jsp" flush="true" />

<div class="pusher">


<div class="ui segment" id="banner">
        
    <div class="ui text container">
       <div class="ui middle aligned center aligned grid">
           <div class="two column row">
                
               <h2 class="ui header" id="normal_header">
                 <i class="help icon"></i>접근 권한이 없습니다.
               </h2>
                  
                  
               <h2 class="ui small header" id="mini_header">
                 <i class="help icon"></i>접근 권한이 없습니다.
               </h2>
                
           </div>
       </div>
       
       <div class="ui center aligned segment">
          <br/>
          <p>관리자에게 문의하여 주십시오.</p>
          <p>${errormsg}</p>
          
          <c:if test="${not empty email}">
            ${email}<br/>
          </c:if>
          
          <br/>
          
          <a href="<c:url value='/home/main'/>">메인 페이지로 가기</a>
          
          <br/><br/>
          
          <sec:authorize access="isAnonymous()">
            <a href="<c:url value='/home/login'/>">로그인 페이지로 가기</a>  
          </sec:authorize>
       </div>
       
       
    </div>
  
</div>




 <div id="footer">       
    <jsp:include page="../include/footer.jsp" flush="true" />
 </div>



</div><!-- pusher -->
</body>
</html>