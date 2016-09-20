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
    
    
    <!-- csrf Meta -->
    <sec:csrfMetaTags/>
    <meta name="_csrf_header" content="${_csrf.headerName}" />
    <meta name="_csrf" content="${_csrf.token}" />
    
    
    <!-- Site Properties -->
    <title> - UserBye_TrashBox - </title>
    
    
    <!-- jQuery Library -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery-1.11.3.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery-ui-1.11.4.min.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/javascript/css/jquery-ui-1.11.4.css" />" >
    
    
    <!-- sweetalert -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/sweetalert.min.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/javascript/css/sweetalert.css"/>">
        
        
    <!-- shortcut icon -->
    <link rel="shortcut icon" href="<c:url value="/resources/trashbox/icons/tricolor.ico"/>" type="image/x-icon">
 
 
    <!-- trashbox css, js -->    
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/trashbox/trashbox.userBye.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-checkPopUp.css"/>">  

    
    <!-- Semantic-UI Components -->
    <jsp:include page="../include/library.jsp" flush="true" />
  
  
    <!-- security -->
    <sec:authorize access="isAuthenticated()"> 
        <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/security/trashbox-authenticated.js"/>"></script> 
    </sec:authorize> 
    
    <sec:authorize access="isAnonymous()">
        <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/security/trashbox-anonymous.js"/>"></script> 
    </sec:authorize>
    
    
<style type="text/css">
#nicNameCfm {
color: black;
}
#sendCheckPopUp {
color: black;
}
</style>    
    
    
<script>
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");
  var nicCount = 0;

</script>    
    
    
</head>


<sec:authorize access="isAuthenticated()">
<body>
            
      <div id="banner" class="ui inverted vertical masthead center aligned segment">      
      <h5 class="ui center aligned tiny icon header">
          <i class="write icon"></i>
                 회원탈퇴
      </h5>
      <div class="ui text container">
       
       
   <c:url var="logoutUrl" value="/logout"/>
   <form name="userByeFrm" id="userByeFrm" action="${logoutUrl}" method="post">
      <sec:csrfInput />
   </form>
   
      <table class="ui small celled table">
        <thead>
          <tr>
            <th>※재확인을 위해 패스워드를 입력해주세요.</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>Email :&nbsp;&nbsp;${email}</td>
          </tr>
          <tr>
            <td><input type="password" id="password"/>
            <input class="ui mini submit button" type="button" value="확인" id="userByePassword"/></td>
          </tr>
        </tbody>
      
      </table>  
         
      </div>
      </div>


  </sec:authorize>   

</body>
</html>