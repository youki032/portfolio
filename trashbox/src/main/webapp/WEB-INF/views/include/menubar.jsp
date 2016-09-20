<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


<meta name="_csrf_header" content="${_csrf.headerName}" />
<meta name="_csrf" content="${_csrf.token}" />

  
  <!-- logout modal -->
  <sec:authorize access="isAuthenticated()">
    <div class="ui small logout modal">
        <div class="header">
                      로그아웃
        </div>
        <div class="content">
            <p>로그아웃 하시겠습니까?</p>
        </div>
        <div class="actions">
            <div class="ui negative button">
                CANCEL
            </div>
            <div class="ui positive right labeled icon button">
                OK
                <i class="checkmark icon"></i>
            </div>
        </div>
    </div>
    <c:url var="logoutUrl" value="/logout"/>
  <form id="logout-form" action="${logoutUrl}" method="post">
   <sec:csrfInput />
  </form>
  
  
  <!-- left Sidebar Menu -->
  <div class="ui sidebar vertical small left menu">
        <a class="header item"><span id="menu_headLine">Trash_Box</span></a>
        <a class="red item" href="<c:url value="/article/list?boardNo=1&pageNo=1"/>"><span id="notice_text">-&nbsp;&nbsp;공지사항</span></a>
        
        <a class="header item"><span id="menu_headLine">커뮤니티</span></a>
        <a class="orange item" href="<c:url value="/article/list?boardNo=2&pageNo=1"/>"><span id="free_text">-&nbsp;&nbsp;자유게시판</span></a>
        <a class="purple item" href="<c:url value="/article/list?boardNo=3&pageNo=1"/>"><span id="humor_text">-&nbsp;&nbsp;유머게시판</span></a>
        
        <a class="header item"><span id="menu_headLine">정보</span></a>
        <a class="yellow item" href="<c:url value="/article/list?boardNo=4&pageNo=1"/>"><span id="create_text">-&nbsp;&nbsp;창작게시판</span></a>
        <a class="green item" href="<c:url value="/article/list?boardNo=5&pageNo=1"/>"><span id="wood_text">-&nbsp;&nbsp;목공게시판</span></a>
        <a class="blue item" href="<c:url value="/article/list?boardNo=6&pageNo=1"/>"><span id="elect_text">-&nbsp;&nbsp;전자게시판</span></a>
        <a class="blue item" href="<c:url value="/article/list?boardNo=7&pageNo=1"/>"><span id="metal_text">-&nbsp;&nbsp;금속게시판</span></a>
        
        <a class="header item"><span id="menu_headLine">Q & A</span></a>
        <a class="violet item" href="<c:url value="/article/list?boardNo=8&pageNo=1"/>"><span id="qna_text">-&nbsp;&nbsp;질문게시판</span></a>
        
        <a class="header item"><span id="menu_headLine">etc.</span></a>
        <a class="purple item" href="<c:url value="/article/list?boardNo=9&pageNo=1"/>"><span id="etc_text">-&nbsp;&nbsp;장터게시판</span></a>
  </div>
    </sec:authorize>


    <!-- Anonymous right Sidebar Menu  -->
    <sec:authorize access="isAnonymous()">
    <div class="ui sidebar vertical right menu">
       <div id="user-info-box" class="item">
            <div class="ui tiny circular image">
                <img src="<c:url value="/resources/semantic-ui/assets/images/wireframe/image.png"/>">
            </div>

            <div class="ui center aligned container">
                <h2>Anonymous</h2>
            </div>
        </div>
 
        <a id="login-button" class="green item" href="<c:url value="/home/login"/>">
          <span id="login_text">LogIn</span>
        </a>
        
        <a id="signUp-button" class="blue item" href="<c:url value="/home/signUp"/>">
          <span id="signUp_text">SignUp</span>
        </a>
        
     </div>
     
     
     
     <!-- left Sidebar Menu -->
    <div class="ui sidebar vertical small left menu">
        <a class="header item"><span id="menu_headLine">Trash_Box</span></a>
        <a class="red item" href="<c:url value="/article/list?boardNo=1&pageNo=1"/>"><span id="notice_text">-&nbsp;&nbsp;공지사항</span></a>
        
        <a class="header item"><span id="menu_headLine">커뮤니티</span></a>
        <a class="orange item" href="<c:url value="/article/list?boardNo=2&pageNo=1"/>"><span id="free_text">-&nbsp;&nbsp;자유게시판</span></a>
        <a class="purple item" href="<c:url value="/article/list?boardNo=3&pageNo=1"/>"><span id="humor_text">-&nbsp;&nbsp;유머게시판</span></a>
        
        <a class="header item"><span id="menu_headLine">정보</span></a>
        <a class="yellow item" href="<c:url value="/article/list?boardNo=4&pageNo=1"/>"><span id="create_text">-&nbsp;&nbsp;창작게시판</span></a>
        <a class="green item" href="<c:url value="/article/list?boardNo=5&pageNo=1"/>"><span id="wood_text">-&nbsp;&nbsp;목공게시판</span></a>
        <a class="blue item" href="<c:url value="/article/list?boardNo=6&pageNo=1"/>"><span id="elect_text">-&nbsp;&nbsp;전자게시판</span></a>
        <a class="blue item" href="<c:url value="/article/list?boardNo=7&pageNo=1"/>"><span id="metal_text">-&nbsp;&nbsp;금속게시판</span></a>
        
        <a class="header item"><span id="menu_headLine">Q & A</span></a>
        <a class="violet item" href="<c:url value="/article/list?boardNo=8&pageNo=1"/>"><span id="qna_text">-&nbsp;&nbsp;질문게시판</span></a>
        
        <a class="header item"><span id="menu_headLine">etc.</span></a>
        <a class="purple item" href="<c:url value="/article/list?boardNo=9&pageNo=1"/>"><span id="etc_text">-&nbsp;&nbsp;장터게시판</span></a>
        
    </div>
     </sec:authorize>
  
  
   <!-- Authenticated right Sidebar Menu  -->
     <sec:authorize access="isAuthenticated()">
     <div class="ui sidebar vertical right menu">
       <div id="user-info-box" class="item">
            <div class="ui circular tiny image">
               <c:choose>
                  <c:when test="${empty profileUserInfo.profilePath}">
                    <img src="<c:url value="/files/profile/square-image.png"/>" class="ui circular tiny image">
                  </c:when>

                  <c:when test="${!empty profileUserInfo.profilePath}">
                    <img src="<c:url value="/files/profile/${profileUserInfo.profilePath}"/>" class="ui circular tiny image">
                  </c:when>

                </c:choose>
            </div>

            <div class="ui center aligned container">
                <h3><sec:authentication property="principal.username" /></h3>
                <h4><span id="menubar_level"></span>&nbsp;&nbsp;&nbsp;<sec:authentication property="principal.nicName" /></h4>
            </div>
            <br/>

            
            <div class="ui indicating small progress" id="exp_progress_bar">
              <div class="bar">
                <div class="progress"></div>
              </div>
            </div>

              
        </div>
        
        <a class="blue item" href="<c:url value="/userinfo/myPage"/>">
          <span id="userInfo_text">회원정보</span>
        </a>

        <a id="logout-button" class="red item">
          <span id="logout_text">Logout</span>
        </a>
        
    </div>
    </sec:authorize>
    
    
    
    <!-- Following Menu -->
    <div class="ui fixed menu">
        <a id="left-sidebar-button" class="item">
            <i class="sidebar icon"></i>
        </a>
        
        <div class="header inverted blue item active">
            <a href="<c:url value="/main"/>">Trash Box</a>
        </div>
        
        <div class="right menu">
           <div class="item">
               <i class="user icon"></i>

                  <sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
                                    일반회원
                  </sec:authorize>
                  
                  <sec:authorize access="hasAnyRole('ROLE_MASTER')">
                                    관리자
                  </sec:authorize>
                  
                  <sec:authorize access="isAnonymous()">
                                    비회원
                  </sec:authorize>
            </div>
           
           <sec:authorize access="isAuthenticated()">
              <a class="item">
                <i class='ui mail outline icon' onclick="do_myMsg();"></i>
              </a>
           </sec:authorize>

            <a id="right-sidebar-button" class="item">
               <i class="privacy icon"></i>
            </a>
        </div>
    </div>
    
    
    
<!-- User Custom -->
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-menubar.css"/>">

<!-- trashbox js -->    
<script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/trashbox/trashbox.menubar.js"/>"></script>
    

<script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery.browser.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/socket.io.js"/>"></script>

<script type="text/javascript">
   var header = $("meta[name='_csrf_header']").attr("content");
   var token = $("meta[name='_csrf']").attr("content");
   
   $("#exp_progress").progress('increment');
   
 </script>