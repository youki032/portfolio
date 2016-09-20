<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


<!DOCTYPE html>
<html lang="ko">
<head>

  
    <!-- Standard Meta -->
    <meta charset='UTF-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    
    
    <!-- csrf Meta -->
    <sec:csrfMetaTags/>
    <meta name="_csrf_header" content="${_csrf.headerName}" />
    <meta name="_csrf" content="${_csrf.token}" />
    
    
    <!-- Site Properties -->
    <title> - MyMessage - </title>  
    
    
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
    
    
    <!-- socket io -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/socket.io.js"/>"></script>
    
    
    <!-- browser js -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery.browser.min.js"/>"></script>
    
    
    <!-- trashbox css, js -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/trashbox/trashbox.myMsg.js"/>" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-myMsg.css"/>">
 
    
    <!-- Semantic-UI Components -->
    <jsp:include page="../include/library.jsp" flush="true" />
    
    
    <!-- security -->
    <sec:authorize access="isAuthenticated()"> 
        <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/security/trashbox-authenticated.js"/>"></script> 
    </sec:authorize> 
    
    <sec:authorize access="isAnonymous()">
        <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/security/trashbox-anonymous.js"/>"></script> 
    </sec:authorize>
      
      
<script type="text/javascript" >

//to, from, save 리스트번호
var toListVal = 1;
var fromListVal = 2;
var saveListVal = 3;

//paging변수
var prevPageNo;
var nextPageNo;
var finalPageNo;
var startPageNo;
var firstPageNo;
var endPageNo;

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");


</script>


</head>

<body id="trashbox">

    <div class="header">
      <h2 class="ui center aligned small icon header">
        <i class="mail outline icon" id="myMsg_header"></i>
          내 쪽지함
      </h2>

    </div>
  

      <div class="ui top pointing secondary menu">
        <a class="item active" id="one" data-tab="first">받은쪽지</a>
        <a class="item" id="two" data-tab="second">보낸쪽지</a>
        <a class="item" id="three" data-tab="third">보관쪽지</a>
      </div>
      
      
      
      <div class="ui tab segment active" id="oneTab" data-tab="first">
            <div id="toList"></div>
            <input type="hidden" name="toUserNo" id="toUserNo" value="" />
            <input type="hidden" name="toUserNicName" id="toUserNicName" value="" />
            
            <div id="toList_paging" class="pagination"></div>
            
            
            <div id="myMsg_btns">
              <input class="ui mini button" type="button" value="삭제" onclick="btn_choose(this.value,1);"/>
              <input class="ui mini button" type="button" value="저장" onclick="btn_choose(this.value,1);" />
            </div>
            
            
      </div>
      
      
      <div class="ui tab segment" id="twoTab" data-tab="second">
            <div id="fromList"></div>
            
            <div id="fromList_paging" class="pagination"></div>
            
            <div id="myMsg_btns">
              <input class="ui mini button" type="button" value="삭제" onclick="btn_choose(this.value,2);"/>
            </div>
      </div>

  
      <div class="ui tab segment" id="threeTab" data-tab="third">
            <div id="saveList"></div>
            
            <div id="saveList_paging" class="pagination"></div>
            
            <div id="myMsg_btns">
              <input class="ui mini button" type="button" value="삭제" onclick="btn_choose(this.value,3);"/>
            </div>
      </div>
  
    
      

</body>
</html>