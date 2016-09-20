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
    <title> - UserFindResult_TrashBox - </title>
    
    
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
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-userFindResult.css"/>">  
    
    
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
  
  $(document)
    .ready(function() {
      $('.ui.form')
        .form({
          inline : true,
          on     : 'blur',
          fields: {
            ID: {
              identifier  : 'username',
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
                    type   : 'regExp[/^[A-Za-z0-9]{6,16}$/]',
                    prompt : '문자와 숫자포함 6~16 이내의 비밀번호를 입력해주세요.'
                    
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
      
      
    $('.ui.accordion')
    .accordion({
      exclusive: true,
      animateChildren: true
    });
  
});



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
                    <i class="help icon"></i>아이디 / 비밀번호 찾기
                 </h2>
                  
                 <h2 class="ui small header" id="mini_header">
                    <i class="help icon"></i>아이디 / 비밀번호 찾기
                 </h2>
              </div>
          </div>
         
            
          <div class="ui segment">
            <c:choose>
              <c:when test="${findEmail != null}">
                  <h3>아이디 찾기</h3>
                    <c:forEach items="${findEmail}" var="user" varStatus="status">
                      <ul>
                        <li>${user.email}</li>
                      </ul>
                    </c:forEach>
              </c:when>
              
              <c:when test="${nullEmail == 'nullEmail'}">
                <h3>일치하는 정보가 없습니다.</h3>
              </c:when>

  
              <c:when test="${user.password != null}">
                <h3>비밀번호 찾기</h3>
                  <p>가입하신 이메일주소로 변경된 패스워드가 전송되었습니다.</p>
              </c:when>
  
              <c:when test="${empty user.birthDate or empty user.password}">
                <h3>일치하는 정보가 없습니다.</h3>
              </c:when>
              
            </c:choose>  
          </div> 
      
        </div>
    
    </div>
  
  
  <div id="footer">       
    <jsp:include page="../include/footer.jsp" flush="true" />
  </div>
    

  </div>
 


</body>
</html>