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
    <title> - Login_TrashBox - </title>  
    
    
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
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-login.css"/>">  

    
    <!-- Semantic-UI Components -->
    <jsp:include page="../include/library.jsp" flush="true" />
  
  
    <!-- security -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/security/trashbox-anonymous.js"/>"></script> 
    
    
    

<script>
  
  $(document).ready(function() {
      $('.ui.form')
        .form({
          inline : true,
          on     : 'blur',
          fields: {
            Email: {
              identifier  : 'email',
              rules: [
                {
                  type   : 'empty',
                  prompt : '이메일을 입력해 주세요.'
                },
                {
                    type   : 'email',
                    prompt : '이메일형식이 아닙니다.'
                }
              ]
            },
            Password: {
              identifier  : 'password',
              rules: [
                {
                  type   : 'empty',
                  prompt : '비밀번호를 입력해 주세요.'
                },
                {
                    type   : 'regExp[/^[A-Za-z0-9]{6,30}$/]',
                    prompt : '문자와 숫자포함 6~30 이내의 비밀번호를 입력해주세요.'
                    
                }
              ]
            }
          }
        });
      
      
    $('.message .close')
      .on('click', function() {
        $(this)
          .closest('.message')
          .transition('fade')
        ;
      })
    ;
      
   
});
  
  

  </script>




</head>


<body id="trashbox">

    <!-- Anonymous right Sidebar Menu  -->
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
        
        <a class="header item"><span id="menu_headLine">Q & A</span></a>
        <a class="violet item" href="<c:url value="/article/list?boardNo=7&pageNo=1"/>"><span id="qna_text">-&nbsp;&nbsp;질문게시판</span></a>
        
        <a class="header item"><span id="menu_headLine">etc.</span></a>
        <a class="purple item" href="<c:url value="/article/list?boardNo=8&pageNo=1"/>"><span id="etc_text">-&nbsp;&nbsp;장터게시판</span></a>
        
    </div>
  
  
    <!-- Following Menu -->
    <div class="ui fixed menu">
        <a id="left-sidebar-button" class="item">
            <i class="sidebar icon"></i>
        </a>
        <div class="header inverted blue item active">
        <a href="<c:url value="/home/main"/>">Trash Box</a>
            
        </div>
        <div class="right menu">
            <a class="item">
            
            <span id="MsgIcon" class='ui long msg image'></span>
            
          
            </a>
            <div id="state"></div>

        
            <a class="item" href="#search">
                <i class="search icon"></i>
            </a>
            <a id="right-sidebar-button" class="item">
                <i class="user icon"></i>
            </a>
        </div>
    </div>


<div class="pusher">


<div class="ui middle aligned center aligned grid">
  <div class="column">
      <h2 class="ui header" id="login_header">
        <i class="sign in icon"></i>
                  로그인
      </h2>
    
    
    
    
    <form class="ui large form" name="loginFormUrl" id="loginForm" method="POST" action="<c:url value='/home/login'/>">

      <div class="ui stacked segment">
        <div class="field">
          <div class="ui left icon input">
            <i class="user icon"></i>
            <input type="text" name="email" id="email" placeholder="ID" value="${loginId}"/>
          </div>
        </div>
        <div class="field">
          <div class="ui left icon input">
            <i class="lock icon"></i>
            <input type="password" name="password" id="password" placeholder="Password" value="${loginPass}"/>
          </div>
        </div>
        <div class="ui fluid large submit button" id="loginBtn">Login</div>
      </div>
   <sec:csrfInput />
   
   <c:if test="${securityExceptionMsg eq 'Bad credentials'}">
      <div class="ui red message">
        <i class="close icon"></i>
        <div class="header">
                  로그인 실패!
        </div>
        <p>아이디나 비밀번호가 틀립니다! 확인해주세요.</p>
      </div>
   </c:if>
   
   <c:if test="${securityExceptionMsg eq '사용자 계정이 잠겨 있습니다.'}">
      <div class="ui red message">
        <i class="close icon"></i>
        <div class="header">
                  로그인 실패!
        </div>
        <p>탈퇴하셨거나 정지된 계정입니다,.</p>
      </div>
   </c:if>
   
   <input type="hidden" name="loginRedirect" id="loginRedirect" value="${loginRedirect}" />

   </form>
     
    <div class="ui message">
     <a href="<c:url value='/home/signUp'/>" id="msgText">회원가입</a>
     <span>ㅣ</span>
     <a href="<c:url value='/home/userFind' />" id="msgText">아이디/비밀번호 찾기</a>  
      

    </div>
    
    
  </div>
</div>


<div id="footer">       
<jsp:include page="../include/footer.jsp" flush="true" />
</div>


</div>
</body>
</html>