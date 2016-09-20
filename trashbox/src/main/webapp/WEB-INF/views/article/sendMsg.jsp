<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


<!DOCTYPE html>
<html lang="ko">
<head>


    <!-- Standard Meta -->
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    
    
    <!-- Site Properties -->
    <title> - Message - </title>  
    
    
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
    
    
    <!-- trashbox css -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-sendMsg.css"/>">
    
    
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

$(document).ready(function(){
	  $('.menu .item').tab()
});

    function call_opner_value() {
    	  
    	  var opener_boardNo_val = window.opener.document.getElementById("throwBoardNo").value;
        document.getElementById("boardNo").value = opener_boardNo_val;
          
        var opener_pageNo_val = window.opener.document.getElementById("throwPageNo").value;
        document.getElementById("pageNo").value = opener_pageNo_val;
    }
    
    
    function do_submit() {
      
      swal({   
          title:"쪽지를 전송하였습니다",   
          confirmButtonColor: "#DD6B55",   
          confirmButtonText: "Yes",   
          closeOnConfirm: false,
          showLoaderOnConfirm: true,
          },
          function(){   
        	  var form = document.getElementById("msgFrm");
        	  
        	  form.target = "parentList";
        	  form.submit();
            self.close();
        	  
          });
      }
    

</script>



</head>

<body id="trashbox" onload="call_opner_value();">
<div class="frame">
    <div class="header">
      <h2 class="ui center aligned small icon header">
        <i class="send icon"></i>
          메시지 보내기
      </h2>

    </div>

 
      
      <div class="ui piled segment">
        <p id="userNicName">받는사람:&nbsp;&nbsp;${toNicName}</p>
        <form name="msgFrm" id="msgFrm" action="sendMsg" method="post">
            <input type="hidden" id="articleNo" name="articleNo" value="${articleNo}" />
            <input type="hidden" id="boardNo" name="boardNo" value="" />
            <input type="hidden" id="pageNo" name="pageNo" value="" />
            <input type="hidden" id="toUserNicName" name="toUserNicName" value="${toNicName}" />
            <input type="hidden" id="msgFlag" name="msgFlag" value="1" />
            
           <textarea class="ui textarea" name="msgContent" cols="60" rows="12" style="resize:none;" placeholder="보낼내용..."></textarea>
            
            <div id="btn">
              <sec:csrfInput/>
              <input class="ui right aligned mini button" type="button" value="전송" onclick="do_submit();"/>
            </div>
        </form>
    
      
      </div>

    
  
  
  
  
  </div>
</body>
</html>