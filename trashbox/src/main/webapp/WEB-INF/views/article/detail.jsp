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
    <title> - detail_TrashBox - </title> 
  
  
    <!-- jQuery Library -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery-1.11.3.min.js"/>"></script>
<%--     <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery-3.0.0.min.js"/>"></script> --%>
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
    
    
    <!-- scroll up -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery.scrollUp.min.js"/>"></script>    
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/javascript/css/jquery.scrollUp.image.css"/>">
        
        
    <!-- trashbox css, js -->    
    <script type="text/javascript" charset="UTF-8" src="<c:url value="/resources/trashbox/javascript/js/trashbox/trashbox.detail.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-detail.css"/>">  
    
    
    <!-- Semantic-UI Components -->
    <jsp:include page="../include/library.jsp" flush="true" />
  
  
    <!-- security -->
    <sec:authorize access="isAuthenticated()"> 
        <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/security/trashbox-authenticated.js"/>"></script> 
    </sec:authorize> 
    
    <sec:authorize access="isAnonymous()">
        <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/security/trashbox-anonymous.js"/>"></script> 
    </sec:authorize>


<script type="text/javascript" charset="UTF-8">

var articleNoVal = ${article.articleNo};  //추천 aJax컨트롤러로 넘겨줄 게시글값
var boardNoVal = ${article.boardNo};  //추천 aJax컨트롤러로 넘겨줄 보드값
var listNoVal = ${article.listNo};
var pageNoVal = ${paging.pageNo};
var commentPageNoVal = ${commentPaging.cPageNo};

var nicNameVal = '${article.nicName}';

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");


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
                     <i class="table icon"></i>${board.boardName}
                  </h2>
          
                  <h2 class="ui small header" id="mini_header">
                     <i class="table icon"></i>${board.boardName}
                  </h2>
              </div>
            </div>
        </div>
       
            
<div class="ui text container" style="margin-top: 10px;">
<input type="hidden" id="titleArticleNo" value="${article.articleNo}">
  <div class="ui left aligned red segment">
  <p class="detail_title_name">${article.title}</p> 
  <span class="detail_title_etc">*작성일: &nbsp; ${article.createDate}&nbsp;&nbsp;*조회수:&nbsp;${article.views}</span>
  </div>
</div>



<div class="ui text container segment" id="content" style="margin-top: 10px;">
      <p id="details_attachFile">
        <c:choose>
           <c:when test="${empty article.renameFilePath}"></c:when>

           <c:when test="${!empty article.renameFilePath}">
           
               <i class="download icon"></i>&nbsp;<a href='fileDownload?renameFilePath=${article.renameFilePath}'>${article.originalFilePath}</a>
           
           </c:when>
           
        </c:choose>
      </p>
      
      <br/><br/>
      
      <p id="detail_content">${article.content}</p>
      
      <br/><br/><br/><br/>

 
<!-- class="ui autumn leaf image visible" -->
<div class="ui labeled button" tabindex="0">
    <sec:authorize access="isAuthenticated()">
        <div class="ui red button" id="redButton" onclick="do_like();" style="left: 5px;">
          <i class="ui autumn large heart icon visible" style="padding-left:10px"></i>
        </div>
    </sec:authorize>
    
    <sec:authorize access="isAnonymous()">
        <div class="ui red button" id="redButton" onclick="do_vaild_like();" style="left: 5px;">
          <i class="ui autumn large heart icon visible" style="padding-left:10px"></i>
        </div>
    </sec:authorize>
    
      <a class="ui basic red left pointing label" id="likeCountArea">
         ${article.likeCnt}
      </a>
</div>

</div>


<div class="ui text container" style="margin-top: 10px; padding:0; ">
<div class="ui celled grid segment">
  <div class="row">
        <div class="three wide column">
           <c:choose>
             <c:when test="${empty userInfo.profilePath}">
                <img src="<c:url value="/files/profile/square-image.png"/>" class="ui circular small image">
             </c:when>

             <c:when test="${!empty userInfo.profilePath}">
                <img src="<c:url value="/files/profile/${userInfo.profilePath}"/>" class="ui circular small image">
             </c:when>
           </c:choose>
        </div>
        
    
    
    <div class="twelve wide left aligned column">
    <sec:authorize access="isAuthenticated()">
       <div class="ui dropdown">
          <div class="text">
            <h3>
              <span id="level_icon">
                <img id='levelIcon' src='/resources/trashbox/icons/level/${article.userLevel }.gif'>
              </span>&nbsp;${article.nicName }
            </h3>
          </div>
          
          <div class="menu">
            <div class="item" onclick="userHistoryPopUp('${article.nicName }', '${article.listNo}');">회원정보</div>
            <div class="item" onclick="sendMsgPopUp('${article.nicName }', '${article.articleNo}');">쪽지보내기</div>
          </div>
       </div>
     </sec:authorize>
     
     <sec:authorize access="isAnonymous()">
        <div class="ui dropdown">
           <div class="text">
             <h3>
               <span id="level_icon">
                 <img id='levelIcon' src='/resources/trashbox/icons/level/${article.userLevel }.gif'>
               </span>&nbsp;${article.nicName }
             </h3>  
           </div>
              
           <div class="menu">
              <div class="item" onclick="anonymous_menuPopUp();">회원정보</div>
              <div class="item" onclick="anonymous_menuPopUp();">쪽지보내기</div>
           </div>
        </div>
     </sec:authorize>
     
     
      <br/>
      
      <c:if test="${!empty userInfo.prevNicName }">
      <span>(변경전 닉네임:&nbsp;&nbsp;${userInfo.prevNicName})</span>
      </c:if>
      
      <br/>
      <textarea id="detail_history" readonly="readonly" cols="30" rows="3">${userInfo.selfIntroduction }</textarea>
    </div>
    
  </div>
</div>
</div>

<!-- 로그인유저 기능 -->
<sec:authorize access="isAuthenticated()">
    <c:if test="${user.userNo eq article.userNo}">
      <div class="ui right aligned text container" style="margin-top: 10px;">
        <div class="ui tiny buttons">
<%--             <button class="ui tiny button" onclick='location.href="change?articleNo=${article.articleNo}&boardNo=${article.boardNo}&pageNo=${paging.pageNo}&cPageNo=${commentPaging.cPageNo}"'>수정</button> --%>
            <button class="ui tiny button" onclick='location.href="<c:url value='/article/change?articleNo=${article.articleNo}&boardNo=${article.boardNo}&pageNo=${paging.pageNo}&cPageNo=${commentPaging.cPageNo}'/>"'>수정</button>
            <button class="ui tiny button" onclick="javascript:do_removeArticle();">삭제</button>
        </div>
      </div>
   </c:if>
</sec:authorize>

<!-- 관리자 기능 -->
<sec:authorize access="hasAnyRole('ROLE_MASTER')">
      <div class="ui right aligned text container" style="margin-top: 10px;">
        <div class="ui tiny buttons">
          <button class="ui tiny button" onclick="javascript:do_removeArticle();">관리자 삭제</button>
        </div>
      </div>
</sec:authorize>


<br/>
<div class="ui text container left aligned segment" style="margin-top: 10px;">
<div class="ui comments">
  <h3 class="ui dividing header"><i class="comments outline icon"></i>Comments</h3>
  
  <div class="comment">
    <c:forEach items="${comment}" var="comment">
    <a class="avatar">
      <c:choose>
         <c:when test="${empty comment.profilePath}">
            <img src="<c:url value="/files/profile/square-image.png"/>" >
         </c:when>

         <c:when test="${!empty comment.profilePath}">
            <img src="<c:url value="/files/profile/${comment.profilePath}"/>" >
         </c:when>
       </c:choose>
      
      
    </a>
    <div class="content">
      <label class="author">
        <img id='levelIcon' src='/resources/trashbox/icons/level/${comment.userLevel }.gif'>
        &nbsp;${comment.nicName}
      </label>
      
        <div class="metadata">
          <span class="date">${comment.createDate}</span>
          
          <!-- 로그인유저 기능 -->
          <sec:authorize access="isAuthenticated()">
            <div class="actions">
              <c:if test="${user.userNo eq comment.userNo}">
                <a class="reply" onclick="javascript:do_removeComment(${comment.commentNo});">삭제</a>      
              </c:if>
            </div>
          </sec:authorize>
          
          <!-- 관리자 기능 -->
          <sec:authorize access="hasAnyRole('ROLE_MASTER')">
            <div class="actions">
                <a class="reply" onclick="javascript:do_removeComment(${comment.commentNo});">관리자 삭제</a>      
            </div>
          </sec:authorize>
          
      
        </div>
        
        <div class="text">
          ${comment.contents}
        </div>
        
        <br/>
    </div>
    </c:forEach>
    
    <!-- comment paging start -->
    <jsp:include page="../include/commentPaging.jsp" flush="true">
      
      <jsp:param name="firstPageNo" value="${commentPaging.firstPageNo}"/>  

      <jsp:param name="nextPageNo" value="${commentPaging.nextPageNo}"/>    
      
      <jsp:param name="prevPageNo" value="${commentPaging.prevPageNo}"/>    
      
      <jsp:param name="startPageNo" value="${commentPaging.startPageNo}"/>
      
      <jsp:param name="cPageNo" value="${commentPaging.cPageNo}"/>
      
      <jsp:param name="endPageNo" value="${commentPaging.endPageNo}"/>
      
      <jsp:param name="finalPageNo" value="${commentPaging.finalPageNo}"/>
      
      <jsp:param name="totalCount" value="${commentPaging.totalCount}"/>
      
      <jsp:param name="boardNo" value="${commentPaging.boardNo}"/>
      
      <jsp:param name="articleNo" value="${commentPaging.articleNo}"/>  
      
      <jsp:param name="pageNo" value="${commentPaging.pageNo}"/> 
    </jsp:include>

  </div>
  
  
  <c:choose>
    <c:when test="${board.boardName eq '공지사항'}">
      <sec:authorize access="hasAnyRole('ROLE_MASTER')">
        <form name="commentFrm" action="comment" method="post" onsubmit="return do_comment()">
          <div class="ui fluid action small input">
            <input class="ui small input" type="text" id="contents" name="contents" placeholder="코멘트를 입력해주세요.."/>
              <sec:authorize access="isAuthenticated()">
                <button class="ui small submit button" id="cmtBtn">쓰기</button>
              </sec:authorize>
        
              <sec:authorize access="isAnonymous()">
                <button class="ui small submit button" id="cmtBtn" onclick="do_vaild_cmt();">쓰기</button>
              </sec:authorize>
          </div>
            <sec:csrfInput/>
            <input type="hidden" name="nicName" value="${userInfo.nicName}" />
            <input type="hidden" name="listNo" value="${article.listNo}"/>
            <input type="hidden" name="articleNo" value="${article.articleNo}" />
            <input type="hidden" name="boardNo" value="${article.boardNo}" />
            <input type="hidden" name="pageNo" value="${paging.pageNo}" />
            <input type="hidden" name="cPageNo" value="${commentPaging.cPageNo}" />
         </form>
      </sec:authorize>
    </c:when>
  
    <c:otherwise>
      <sec:authorize access="hasAnyRole('ROLE_USER','ROLE_MASTER')">
        <form name="commentFrm" action="comment" method="post" onsubmit="return do_comment()">
          <div class="ui fluid action small input">
            <input class="ui small input" type="text" id="contents" name="contents" placeholder="코멘트를 입력해주세요.."/>
              <sec:authorize access="isAuthenticated()">
                <button class="ui small submit button" id="cmtBtn">쓰기</button>
              </sec:authorize>
        
              <sec:authorize access="isAnonymous()">
                <button class="ui small submit button" id="cmtBtn" onclick="do_vaild_cmt();">쓰기</button>
              </sec:authorize>
          </div>
            <sec:csrfInput/>
            <input type="hidden" name="nicName" value="${userInfo.nicName}" />
            <input type="hidden" name="listNo" value="${article.listNo}"/>
            <input type="hidden" name="articleNo" value="${article.articleNo}" />
            <input type="hidden" name="boardNo" value="${article.boardNo}" />
            <input type="hidden" name="pageNo" value="${paging.pageNo}" />
            <input type="hidden" name="cPageNo" value="${commentPaging.cPageNo}" />
        </form>
      </sec:authorize>
    </c:otherwise>
  </c:choose>
  
  
</div>
  
</div>


<br/><br/><br/><br/><br/><br/><br/><br/>

                  
<div class="ui text container" style="margin-top: 10px;">
<table class="ui small celled center aligned table">
  <thead>
    <tr>
      <th>No</th>
      <th class="seven wide">제목 </th>
      <th>닉네임</th>
      <th>등록일</th>
      <th>조회수</th>
      <th>추천수</th>
    </tr>
  </thead>
  
  <tbody>
  <c:choose>
    <c:when test="${empty list}">
          <tr>
            <td colspan="5" class="seven wide">등록된 게시글이 없습니다</td>
          </tr>
    </c:when>
   
  <c:when test="${vaildListCnt == 1}">
    <c:forEach items="${list}" var="article" varStatus="status">

    <tr class="table_cell">
      <td class="num_row">${article.articleNo}</td>
      <td class="title_row" nowrap="nowrap">
        <span class="resizing_normal_title">
          <a href='detail?&boardNo=${board.boardNo}&articleNo=${article.articleNo}&pageNo=${paging.pageNo}&cPageNo=1' title="${article.title}">
          ${article.title} [${article.titleCommentCnt}]
          </a>&nbsp;&nbsp;<c:if test="${article.originalFilePath != null and article.originalFilePath != ''}"> <i class="download icon"></i></c:if>
        </span>
      
        <span class="resizing_mini_title">
          ${article.articleNo}<span>ㅣ</span><a href='detail?&boardNo=${board.boardNo}&articleNo=${article.articleNo}&pageNo=${paging.pageNo}&cPageNo=1' title="${article.title}">
          ${article.title} [${article.titleCommentCnt}]</a><c:if test="${article.originalFilePath != null and article.originalFilePath != ''}"> <i class="download icon"></i></c:if><br/>
          <sec:authorize access="isAuthenticated()">
            <div class="ui dropdown">
              <div class="text">
                <h4>
                  <span id="level_icon">
                    <img id='levelIcon' src='/resources/trashbox/icons/level/${article.userLevel }.gif'>
                  </span>&nbsp;${article.nicName}
                </h4>  
              </div>
              <div class="menu">
                <div class="item" onclick="userHistoryPopUp('${article.nicName }', '${article.listNo}');">회원정보</div>
                <div class="item" onclick="sendMsgPopUp('${article.nicName }', '${article.articleNo}');">쪽지보내기</div>
              </div>
            </div>     
          </sec:authorize>
          <sec:authorize access="isAnonymous()">
            <div class="ui dropdown">
              <div class="text">
                <h4>
                  <span id="level_icon">
                    <img id='levelIcon' src='/resources/trashbox/icons/level/${article.userLevel }.gif'>
                  </span>&nbsp;${article.nicName }
                </h4>  
              </div>
              
              <div class="menu">
                <div class="item" onclick="anonymous_menuPopUp();">회원정보</div>
                <div class="item" onclick="anonymous_menuPopUp();">쪽지보내기</div>
              </div>
            </div>
          </sec:authorize>
            
          <span>ㅣ</span><img id="view_Icon" src="../resources/trashbox/images/look4.png">${article.views}<span>ㅣ</span>
          <img id="miniHeart_icon" src="../resources/trashbox/images/mini_red_heart.png">${article.likeCnt}<span>ㅣ</span>${article.createDate}
      
        </span>
      
      </td>
      <td class="nic_row">
          <sec:authorize access="isAuthenticated()">
            <div class="ui dropdown">
              <div class="text">
                <h4>
                  <span id="level_icon">
                    <img id='levelIcon' src='/resources/trashbox/icons/level/${article.userLevel }.gif'>
                  </span>&nbsp;${article.nicName }
                </h4>  
              </div>
              <div class="menu">
                <div class="item" onclick="userHistoryPopUp('${article.nicName }', '${article.listNo}');">회원정보</div>
                <div class="item" onclick="sendMsgPopUp('${article.nicName }', '${article.articleNo}');">쪽지보내기</div>
              </div>
            </div>
          </sec:authorize>
          <sec:authorize access="isAnonymous()">
            <div class="ui dropdown">
              <div class="text">
                <h4>
                  <span id="level_icon">
                    <img id='levelIcon' src='/resources/trashbox/icons/level/${article.userLevel }.gif'>
                  </span>&nbsp;${article.nicName }
                </h4>  
              </div>
              <div class="menu">
                <div class="item" onclick="anonymous_menuPopUp();">회원정보</div>
                <div class="item" onclick="anonymous_menuPopUp();">쪽지보내기</div>
              </div>
            </div>
          </sec:authorize>  
      </td>
      <td class="date_row">${article.createDate}</td>
      <td class="view_row">${article.views}</td>
      <td class="like_row">${article.likeCnt}</td>
    </tr>
    </c:forEach>
   </c:when> 
   
  <c:when test="${vaildListCnt == 2}">
    <c:forEach items="${list}" var="article" varStatus="status">    
    <tr class="table_cell">
      <td class="num_row">${article.articleNo}</td>
      <td class="title_row" nowrap="nowrap">
        <span class="resizing_normal_title">
          <a href='detail?&boardNo=${board.boardNo}&articleNo=${article.articleNo}&pageNo=${paging.pageNo}&cPageNo=1' title="${article.title}">
            ${article.title} [${article.titleCommentCnt}]
          </a>&nbsp;&nbsp;
          <c:if test="${article.originalFilePath != null and article.originalFilePath != ''}"> 
            <i class="download icon"></i>
          </c:if>             
        </span>
      
        <span class="resizing_mini_title">
          ${article.articleNo}<span>ㅣ</span>
          <a href='detail?&boardNo=${board.boardNo}&articleNo=${article.articleNo}&pageNo=${paging.pageNo}&cPageNo=1' title="${article.title}">
            ${article.title} [${article.titleCommentCnt}]</a>
          <c:if test="${article.originalFilePath != null and article.originalFilePath != ''}"> 
            <i class="download icon"></i>
          </c:if>
          <br/>
          <sec:authorize access="isAuthenticated()">
            <div class="ui dropdown">
              <div class="text">
                <span id="level_icon">
                  <img id='levelIcon' src='/resources/trashbox/icons/level/${article.userLevel }.gif'>
                </span>&nbsp;${article.nicName }
              </div>
              <div class="menu">
                <div class="item" onclick="userHistoryPopUp('${article.nicName }', '${article.listNo}');">회원정보</div>
                <div class="item" onclick="sendMsgPopUp('${article.nicName }', '${article.articleNo}');">쪽지보내기</div>
              </div>
            </div>
          </sec:authorize>
          <sec:authorize access="isAnonymous()">
            <div class="ui dropdown">
              <div class="text">
                <h4>
                  <span id="level_icon">
                    <img id='levelIcon' src='/resources/trashbox/icons/level/${article.userLevel }.gif'>
                  </span>&nbsp;${article.nicName }
                </h4>  
              </div>
              <div class="menu">
                <div class="item" onclick="anonymous_menuPopUp();">회원정보</div>
                <div class="item" onclick="anonymous_menuPopUp();">쪽지보내기</div>
              </div>
            </div>
          </sec:authorize>
        </span>
        
      </td>
      <td class="nic_row">
        <sec:authorize access="isAuthenticated()">
            <div class="ui dropdown">
              <div class="text">
                <span id="level_icon">
                  <img id='levelIcon' src='/resources/trashbox/icons/level/${article.userLevel }.gif'>
                </span>&nbsp;${article.nicName }
              </div>
              <div class="menu">
                <div class="item" onclick="userHistoryPopUp('${article.nicName }', '${article.listNo}');">회원정보</div>
                <div class="item" onclick="sendMsgPopUp('${article.nicName }', '${article.articleNo}');">쪽지보내기</div>
              </div>
            </div>
          </sec:authorize>
          <sec:authorize access="isAnonymous()">
            <div class="ui dropdown">
              <div class="text">
                <h4>
                  <span id="level_icon">
                    <img id='levelIcon' src='/resources/trashbox/icons/level/${article.userLevel }.gif'>
                  </span>&nbsp;${article.nicName }
                </h4>  
              </div>
              <div class="menu">
                <div class="item" onclick="anonymous_menuPopUp();">회원정보</div>
                <div class="item" onclick="anonymous_menuPopUp();">쪽지보내기</div>
              </div>
            </div>
          </sec:authorize>
      </td>
      <td class="date_row">${article.createDate}</td>
      <td class="view_row">${article.views}</td>
      <td class="like_row">${article.likeCnt}</td>
    </tr>
    </c:forEach>
   </c:when> 
   
  
   </c:choose> 
  </tbody>
  
  <tfoot>
  <tr>
  <th colspan="8">
 
    <jsp:include page="../include/paging.jsp" flush="true">
      
      <jsp:param name="firstPageNo" value="${paging.firstPageNo}"/>  
      
      <jsp:param name="nextPageNo" value="${paging.nextPageNo}"/>    
      
      <jsp:param name="prevPageNo" value="${paging.prevPageNo}"/>    
      
      <jsp:param name="startPageNo" value="${paging.startPageNo}"/>
      
      <jsp:param name="pageNo" value="${paging.pageNo}"/>
      
      <jsp:param name="endPageNo" value="${paging.endPageNo}"/>
      
      <jsp:param name="finalPageNo" value="${paging.finalPageNo}"/>
      
      <jsp:param name="totalCount" value="${paging.totalCount}"/>
      
      <jsp:param name="boardNo" value="${paging.boardNo}"/>  
     
    </jsp:include>
   
 <form name="searchFrm" id="searchFrm" action="searchList" method="post">
<sec:csrfInput/>   
<div class="ui center aligned small search">
 <div class="ui simple buttons" id="simple_buttons">
   <div class="ui button">Toggle</div>
   <div class="ui floating dropdown icon button">
     <i class="dropdown icon"></i>
     <select name="keyFiled" id="select" size="1">
       <option value="title">제목</option>
       <option value="title_content">제목+내용</option>
       <option value="nicname">닉네임</option>
     </select>
   </div>
 </div>
    

  <div class="ui icon input" id="icon_input">
    <input type="hidden" name="boardNo" value="${article.boardNo}" />
    <input type="hidden" name="pageNo" value="${paging.pageNo}" />
    <input type="text"  name="keyWord" type="text" placeholder="Search..." />
    <i class="inverted circular search link icon" onclick="search_btn();"></i>
  </div>
  
  <c:choose>
    <c:when test="${board.boardName eq '공지사항'}">
        <sec:authorize access="hasAnyRole('ROLE_MASTER')">
          <div class="ui floated right image" id="imageWrite"> 
            <a href="<c:url value="/article/writeForm/${board.boardNo}/${paging.pageNo}"/>" >
            <img src="<c:url value='/resources/trashbox/images/write_icon.png' />" title="글쓰기"></a>
          </div>
          
          <div id="resizeWriteInput">
            <input class="ui button" type="button" value="글쓰기" onclick="location.href='${pagecontext.request.contextpath}/article/writeForm/${board.boardNo}/${paging.pageNo}'" />
          </div>
        </sec:authorize>
    </c:when>
  
    <c:otherwise>
      <sec:authorize access="hasAnyRole('ROLE_USER','ROLE_MASTER')">
          <div class="ui floated right image" id="imageWrite"> 
            <a href="<c:url value="/article/writeForm/${board.boardNo}/${paging.pageNo}"/>" >
            <img src="<c:url value='/resources/trashbox/images/write_icon.png' />" title="글쓰기"></a>
          </div>
          
          <div id="resizeWriteInput">
            <input class="ui button" type="button" value="글쓰기" onclick="location.href='${pagecontext.request.contextpath}/article/writeForm/${board.boardNo}/${paging.pageNo}'" />
          </div>
        </sec:authorize>
    </c:otherwise>
  </c:choose>
  
</div>
</form>
    </th>
  </tr>
  </tfoot>
</table>


</div> 
<br/><br/><br/><br/>
 
</div>

<div class="ui modal" id="imgModal">
  <div class="header">Header</div>
</div>

<!-- =======================  회원 쪽지기능 전달값  =========================  -->
<input type="hidden" name="boardNo" id="throwBoardNo" value="${board.boardNo}" />
<input type="hidden" name="pageNo" id="throwPageNo" value="${paging.pageNo}" />

<div id="footer">       
<jsp:include page="../include/footer.jsp" flush="true" />
</div>




</div>

 
<%--  <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/trashbox.detail.js"/>"></script> --%>

</body>
</html>







    