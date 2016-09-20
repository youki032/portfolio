<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


<!DOCTYPE html>
<html lang="ko">
<head>
    
    
    <!-- Standard Meta -->
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    
    
    <!-- Site Properties -->
    <title> - ${board.boardName}_TrashBox - </title>  
    
    
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
    
    
    <!-- trashbox css, js -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/trashbox/trashbox.list.js"/>" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-list.css"/>">  
 
    
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

var openName = window.name = "parentList";

	
</script>

</head>

<body id="trashbox">
<jsp:include page="../include/menubar.jsp" flush="true" />

<div class="pusher">


<div id="banner" class="ui vertical masthead center aligned segment">
<div class="ui text container">



<div class="ui four column grid">
     <div class="two column row">
        <div class="column" id="grid_column">
          <h2 class="ui header" id="header">
            <i class="circular user icon"></i>${board.boardName}
          </h2>
          
          <h2 class="ui small header" id="header">
            <i class="circular user icon"></i>${board.boardName}
          </h2>
        </div>

     </div>
</div>


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
          </a>&nbsp;&nbsp;<c:if test="${article.originalFilePath != null and article.originalFilePath != ''}"> <i class="download icon"></i></c:if>             
        </span>
      
        <span class="resizing_mini_title">
          ${article.articleNo}<span>ㅣ</span><a href='detail?&boardNo=${board.boardNo}&articleNo=${article.articleNo}&pageNo=${paging.pageNo}&cPageNo=1' title="${article.title}">
          ${article.title} [${article.titleCommentCnt}]</a>
          <c:if test="${article.originalFilePath != null and article.originalFilePath != ''}"> 
            <i class="download icon"></i>
          </c:if><br/>
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
    
  <div class="ui icon input" id="searchInput">
    <input type="hidden" name="boardNo" value="${article.boardNo}" />
    <input type="hidden" name="pageNo" value="${paging.pageNo}" />
    <input type="text" name="keyWord" placeholder="Search..." />
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

<!-- =======================  회원 쪽지기능 전달값  =========================  -->
<input type="hidden" name="boardNo" id="throwBoardNo" value="${board.boardNo}" />
<input type="hidden" name="pageNo" id="throwPageNo" value="${paging.pageNo}" />


<div id="footer"> 
<jsp:include page="../include/footer.jsp" flush="true" />
</div>


</div>
</body>

</html>
    
    
    
    