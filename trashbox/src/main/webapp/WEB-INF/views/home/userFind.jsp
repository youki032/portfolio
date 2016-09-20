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
    <title> - UserFind_TrashBox - </title>  
    
    
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
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/trashbox/trashbox.userFind.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-userFind.css"/>">  
    
    
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
         <div class="two column row">
              <h2 class="ui header" id="normal_header">
                  <i class="help icon"></i>아이디 / 비밀번호 찾기
              </h2>
                  
              <h2 class="ui medium header" id="mini_header">
                  <i class="help icon"></i>아이디 / 비밀번호 찾기
              </h2>
         </div>
       </div>
         
            
<div class="ui segment">
<div class="ui styled accordion">
  <div class="title active">
    <i class="dropdown icon"></i>
       아이디 찾기
  </div>
  
  <div class="content active">
    <div class="two fields">
    <form class="ui large form" name="emailFindFrm" action="userFindResult" method="post">
      <sec:csrfInput />
      <div class="field">
        <div class="ui left icon input">
          <i class="user icon"></i>
            <input type="text" name="userName" placeholder="이름을 입력해주세요.">
        </div>
      </div>
    
      <div class="field">
        <div class="ui left icon input">
          <i class="birthday icon"></i>
<!--             <input type="text" name="birthDate" placeholder="ex:19xx-01-01"> -->
            <input type="text" name="birthDate" id="birthDate" maxlength="10" placeholder="birthDate (ex:19xx-01-01)"
                       value="" onkeyup="javascript:date_hyphen(this.form.name, this.name);" />
        </div>
      </div>
      <input type="hidden" name="findStr" value="email" />
      <button class="ui green tiny button" id="submitBtn">찾기</button>
    </form>
    </div>
  </div>
  
  <div class="title">
    <i class="dropdown icon"></i>
   비밀번호 찾기
  </div>
  
  <div class="content">
    <div class="two fields">
    <form class="ui large form" name="paswordFindFrm" action="userFindResult" method="post">
      <sec:csrfInput />
        <div class="field">
          <div class="ui left icon input">
            <i class="user icon"></i>
              <input type="text" name="userName" placeholder="이름을 입력해주세요.">
          </div>
        </div>

        <div class="field">
          <div class="ui left icon input">
            <i class="at icon"></i>
              <input type="text" name="email" placeholder="이메일 아이디를 입력해주세요.">
          </div>
        </div>
        <input type="hidden" name="findStr" value="password" />
        <button class="ui green tiny right aligned button" id="submitBtn">찾기</button>
    </form>
    </div>
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