<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


<div class="ui text container left aligned segment" style="margin-top: 10px;">
<div class="ui comments">
  <h3 class="ui dividing header">Comments
      <a class="ui blue circular label">댓글수:&nbsp;${article.titleCommentCnt}</a>
  </h3>
  <div class="comment">
    <c:forEach items="${comments}" var="comments">
    <a class="avatar">
      <img src="<c:url value="/files/profile/${comments.profilePath}"/>" />
    </a>
    <div class="content">
      <a class="author">${comments.nicName}</a>
      <div class="metadata">
        <span class="date">${comments.createDate}</span>
      
        <sec:authorize access="isAuthenticated()">
          <div class="actions">
            <c:if test="${principal.userNo eq comments.userNo}">
              <a class="reply" onclick="javascript:do_removeComments(${comments.commentsNo});">삭제</a>      
            </c:if>
          </div>
        </sec:authorize>
      
      </div>
      
      <div class="text">
        ${comments.commentContent}
      </div><br/>
        
    </div>
    </c:forEach>
    <div id="paging">
        <input class="max" value="${commentTotalCnt}" />
    </div>
  </div>
  
  <sec:authorize access="isAuthenticated()">
    <form name="commentsFrm" action="comments" method="post" onsubmit="return do_comment()">
      <div class="ui small fluid action input">
        <input type="text" id="commentContent" name="commentContent" placeholder="코멘트를 입력해주세요.."/>
        <button class="ui submit button">쓰기</button>
      </div>
      <sec:csrfInput/>
        <input type="hidden" name="nicName" value="${userInfo.nicName}" />
        <input type="hidden" name="listNo" value="${article.listNo}"/>
        <input type="hidden" name="articleNo" value="${article.articleNo}" />
        <input type="hidden" name="boardNo" value="${article.boardNo}" />
        <input type="hidden" name="pageNo" value="${paging.pageNo}" />
    </form>
  </sec:authorize>
  
</div>
  
</div>