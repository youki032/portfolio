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
    <title>SignUp - TrashBox</title>


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
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-welcome.css"/>">
    

    <!-- Semantic-UI Components -->
    <jsp:include page="../include/library.jsp" flush="true" />
  
  
    <!-- security -->
    <sec:authorize access="isAuthenticated()"> 
        <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/security/trashbox-authenticated.js"/>"></script> 
    </sec:authorize> 
    
    <sec:authorize access="isAnonymous()">
        <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/security/trashbox-anonymous.js"/>"></script> 
    </sec:authorize>

</head>

<body id="trashbox">
<jsp:include page="../include/menubar.jsp" flush="true" />
<div class="pusher">

  
<div class="ui segment" id="banner">
  <div class="ui text container">
	   <div class="ui middle aligned center aligned grid">
		    <div class="column">
			     <div class="two column row">
               <div class="column" id="grid_column">
                  <h2 class="ui header" id="normal_header">
                     <i class="empty star icon"></i>환영합니다!!
                  </h2>
                  
                </div>
            </div>
			
			

			<div class="ui piled segment">
				<h4 class="ui header" >메인으로 돌아가기.</h4>
				<p>
				<a href="<c:url value="/home/main"/>">
				<i class="reply large icon"></i></a></p>
			</div>

		</div>
	</div>

</div>
</div>

 <div id="footer">       
    <jsp:include page="../include/footer.jsp" flush="true" />
 </div>

</div>
</body>
</html>