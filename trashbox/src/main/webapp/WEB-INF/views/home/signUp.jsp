<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
    <title> - SignUp_TrashBox - </title> 
    
    
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
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/trashbox/trashbox.signUp.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-signUp.css"/>">  
    

    <!-- Semantic-UI Components -->
    <jsp:include page="../include/library.jsp" flush="true" />

  
    <!-- security -->
    <sec:authorize access="isAuthenticated()"> 
        <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/security/trashbox-authenticated.js"/>"></script> 
    </sec:authorize> 
    
    <sec:authorize access="isAnonymous()">
        <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/security/trashbox-anonymous.js"/>"></script> 
    </sec:authorize>
    
    
<script>
	  var emailCount = 0;
	  var nicCount = 0;
	  var header = $("meta[name='_csrf_header']").attr("content");
	  var token = $("meta[name='_csrf']").attr("content");
  
</script>

 
</head>
<body id="trashbox">
<jsp:include page="../include/menubar.jsp" flush="true" />
<div class="pusher">


<div class="ui middle aligned center aligned grid">
  <div class="column">
    <h2 class="ui header" id="login_header">
        <i class="add user icon"></i>
                회원가입
    </h2>

<!--     ===================  pc사이즈 ===================== -->

    <form class="ui large form" name="signUpForm" id="signUpForm" method="post" action="signUp" >
      <sec:csrfInput/>
      
      <div class="ui stacked segment">
        <div class="field">
          <div class="ui left icon input">
            <i class="user icon"></i>
              <input name="email" id="email" placeholder="Email"/>
              <input class="ui small button" type="button" id="emailCfm" value="중복 체크"/>
            </div>
              <input class="ui small button" type="button" id="resizing_emailCfm" value="중복 체크"/>
        </div>
        
        <div class="field">
          <div class="ui left icon input">
            <i class="user icon"></i>
              <input name="nicName" id="nicName" placeholder="NicName"/>
              <input class="ui small button" type="button" id="nicNameCfm" value="중복 체크"/>
           </div>
              <input class="ui small button" type="button" id="resizing_nicNameCfm" value="중복 체크"/>
        </div>
        
        
        
        <div class="field">
          <div class="ui left icon input">
            <i class="lock icon"></i>
              <input type="password" name="password" placeholder="Password"/>
          </div>
        </div>
        
        <div class="field">
          <div class="ui left icon input">
            <i class="lock icon"></i>
              <input type="password" id="passwordCfm" placeholder="passwordCfm"/>
          </div>
        </div>
        
        <div class="field">
          <div class="ui left icon input">
            <i class="user icon"></i>
              <input type="text" name="userName" placeholder="userName"/>
          </div>
              <p style="font-size: x-small;">※ 추후 회원정보를 찾을시에 필요합니다.</p>
        </div>
        
        <div class="field">
           <div class="field">
              <div class="ui left icon input">
                <i class="birthday icon"></i>
                <input type="text" name="birthDate" id="birthDate" maxlength="10" placeholder="birthDate"
                       value="" onkeyup="javascript:date_hyphen(this.form.name, this.name);" />
              </div>
           </div>
              <p style="font-size: x-small;">※ YYYY-MM-DD 형식으로 입력해주세요.<br/>
                                                            ※ 추후 회원정보를 찾을시에 필요합니다.</p>
         </div>
            
<!--             <div class="field"> -->
<!-- <!--                 <p>※ 추후 아이디,비밀번호 찾을때 필요합니다.</p> -->
<!--             </div> -->
          
        <input type="hidden" name="enabled" value="1"/>
        <input type="hidden" name="unlocked" value="1"/>
      
        <div class="ui fluid large button" id="signBtn">회원가입!</div>
      </div>
        
    </form>
    
    
    
</div>
</div>



<div id="footer">       
<jsp:include page="../include/footer.jsp" flush="true" />
</div>

</div>
</body>
</html>




