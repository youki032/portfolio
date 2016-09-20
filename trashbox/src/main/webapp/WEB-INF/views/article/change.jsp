<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" session="false" %>

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
    <title> - Change_TrashBox - </title> 
  
  
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


    <!-- CKEditor -->
    <script type="text/javascript" src="<c:url value="/resources/ckeditor/ckeditor.js"/>" charset="utf-8"></script>
    <script type="text/javascript" src="<c:url value="/resources/ckeditor/config.js"/>" charset="utf-8"></script>
    
  
    <!-- fileUpload -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery.fileupload.js"/>" charset="utf-8"></script>
    
    
    <!-- trashbox css, js -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/trashbox/trashbox.myUpload.js"/>" charset="utf-8"></script>    
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-change.css"/>">  
    
    
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
//    history.go(1);
var editor = null;

// 업로드한 파일명을 배열 변수안에 쌓아 넣는다.   
var arrayImg = [];

</script>

</head>


<body id="trashbox">
<jsp:include page="../include/menubar.jsp" flush="true" />

<sec:authentication property="principal" var="user"/> 
<div class="pusher">


<div id="banner" class="ui inverted vertical masthead center aligned segment">
        <div class="ui text container">
           <div class="two column row">
               <div class="column" id="grid_column">
                  <h2 class="ui header" id="normal_header">
                     <i class="write icon"></i>${boardName}&nbsp;&nbsp;-&nbsp;&nbsp;게시물 수정
                  </h2>
              </div>
           </div>
      </div>

<div class="ui raised very padded segment">
<form name="changeFrom" id="form" action="<c:url value='/article/doChange'/>" method="post">
<table class="ui small celled table" border="1">
<tr class="title_tr">
  <th>제목</th>
  <td>
    <div class="ui fluid input">
      <input name="title" id="title" type="text" maxlength="37" value="${article.title}" min="3"/>
    </div>
  </td>
</tr>
<tr class="nicname_tr">
  <th>닉네임</th>
  <td><p>${user.nicName}</p></td>
</tr>
<tr class="resizing_tr">
  <th>
    <span>닉네임: ${user.nicName}</span>
  </th>
</tr>
<tr class="content_tr">
  <th colspan="2"><textarea name="content" id="content" rows="5" cols="50">${article.content}</textarea>
</tr>

<tr class="file_tr">
  <th>첨부된 파일</th>
    <td>
    <c:if test="${not empty article.renameFilePath or not empty article.originalFilePath}" >
      <a id="fileLink" href='../files/article/file/${article.renameFilePath}'>${article.originalFilePath}</a>
      <input type="button" value="삭제" onclick="javascript:do_remove_attachFile('${article.renameFilePath}');" />
    </c:if>
    &nbsp;&nbsp;
    </td>
</tr>

</table>
<input type="hidden" name='listNo'  value="${article.listNo}">
<input type="hidden" name='articleNo'  value="${article.articleNo}">
<input type="hidden" name='boardNo'  value="${article.boardNo}">
<input type="hidden" name="nicName" value="${user.nicName}" />
<input type="hidden" name="pageNo" value="${pageNo}" />
<input type="hidden" name="cPageNo" value="${cPageNo}" />
<input type="hidden" id="originalFilePath" name="originalFilePath" value="" />
<input type="hidden" id="renameFilePath" name="renameFilePath" value="" />
<input type="hidden" id="arrayImg" name="arrayImgName" value="" />
<sec:csrfInput/>

</form>
<div class="ui small modal" id="uploadModal">
  <div class="header">Multi Upload</div>
  <div class="ui divider"></div>
  
  <div id="multi_upload_area">
    <div class="ui secondary menu">
      <a class="item active" data-tab="first" id="image_tab">Image</a>
      <a class="item" data-tab="second" id="file_tab">File</a>
    </div>
    
    <div class="ui tab segment active" data-tab="first" id="multi_image_seg">
      <div class="content">
        <div class="ui small segments">
          <div class="ui segment" id="multi_input_segment">
            <input type="file" name="file" id="multiFile" multiple="multiple" />
            <input type="button" id="removeFile" value="취소" />
          </div>
          <div class="ui two column middle aligned very relaxed stackable grid" id="file_text_list">
            <div class="center aligned column">
              <textarea name="upFileText" id="upFileText" rows="5" readonly="readonly"></textarea>
            </div>
            <div class="left aligned column">
               <div class="form">
                  <div class="inline fields">
                  <label style="padding-left:26px">※가로길이</label>
                    <div class="field">
                      <div class="ui radio checkbox checked" id="automatic_checkbox">
                        <input type="radio" id="automatic" name="type" value="automatic" checked="checked" />
                      </div>
                      <select id="disabled_auto">
                        <option value="320">320px</option>
                        <option value="400">400px</option>
                        <option value="480">480px</option>
                        <option value="550">550px</option>
                        <option value="650">650px</option>
                        <option value="740">740px</option>
                        <option value="960">960px</option>
                      </select>
                    </div>
                  <div class="field">
                    <div class="ui radio checkbox" id="menual_checkbox">
                      <input type="radio" id="menual" name="type" value="menual" />
                    </div>
                      <input type="number" id="disabled_menu" value="" max="9999" maxlength="4" style="width:63px;" oninput="check_maxLangth(this);" />&nbsp;px
                  </div>
                  </div>
                </div>
              
              
            </div>
            <div id="file_notice">
              <p>*다중 업로드는 이미지 파일만 지원합니다.</p>
              <p>*jpg, jpeg, png, gif 확장자만 업로드 가능합니다.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  
    <div class="ui tab segment" data-tab="second" id="file_seg">
     <div class="content">
       <div class="ui small segments">
         <div class="ui segment" id="single_input_segment">
           <input type="file" name="file" id="singleFile" />
           <input type="button" id="removeFile" value="취소" />
         </div>
         <div id="file_text_list">
           <textarea name="upFileText" id="upFileText" rows="5" readonly="readonly"></textarea>
           <p>*파일 업로드는 단일 업로드만 지원합니다.</p>
         </div>
       </div>
     </div>
    </div>
    
  </div>
  
   
  
      <div class="ui segment" id="progress_segment">
        <div class="ui indicating small progress" id="progress">
          <div class="bar"></div>
          <div class="label">ready</div>
        </div>
      </div>
  
  <div class="actions">
    <input type="hidden" id="uploadTabStr" value="" />
    <input class="ui mini green button" type="button" id="uploadSubmit" value="확인" />
    <input class="ui mini red button" type="button" id="uploadCancle" value="취소" />
  </div>
  
</div>

  

  <div class="submit_btns">
    <input class="ui tiny green button" type="button" value="변경" onclick="javascript:do_submit('changeForm');"/>
    <input class="ui tiny red button" type="button" value="취소" onclick="history.back()" />
  </div>


</div>
</div>



<div id="footer">       
<jsp:include page="../include/footer.jsp" flush="true" />
</div>


</div>
</body>
</html>