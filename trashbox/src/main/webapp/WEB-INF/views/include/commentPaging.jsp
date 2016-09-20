<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


<div class="pagination">
  <c:choose>
    <c:when test="${param.firstPageNo eq param.startPageNo}">
    </c:when>
    <c:when test="${i > param.nextPageNo} ">
        <a href="detail?articleNo=${param.articleNo}&boardNo=${param.boardNo}&pageNo=${param.pageNo}&cPageNo=${param.firstPageNo}" class="direction prev"><span></span><span></span>${param.firstPageNo}...</a>
    </c:when>
    <c:otherwise>
        <a href="detail?articleNo=${param.articleNo}&boardNo=${param.boardNo}&pageNo=${param.pageNo}&cPageNo=${param.firstPageNo}" class="direction prev"><span></span><span></span>${param.firstPageNo}...</a>
        <a href="detail?articleNo=${param.articleNo}&boardNo=${param.boardNo}&pageNo=${param.pageNo}&cPageNo=${param.prevPageNo}" class="direction prev"><span></span>이전페이지</a>
    </c:otherwise>
  </c:choose>
    
        <c:forEach var="i" begin="${param.startPageNo}" end="${param.endPageNo}" step="1">
            <c:choose>
                <c:when test="${i eq param.cPageNo}"><a href="javascript:goPage(${i})" class="choice"><strong>${i}</strong></a></c:when>
                <c:when test="${i <= 0}"><strong>${i}</strong></c:when>
                <c:otherwise><a href="detail?articleNo=${param.articleNo}&boardNo=${param.boardNo}&pageNo=${param.pageNo}&cPageNo=${i}">[${i}]</a></c:otherwise>
            </c:choose>
        </c:forEach>
  
  <c:choose>
    <c:when test="${param.finalPageNo eq param.endPageNo}">
    </c:when>
    
    <c:when test="${i < param.prevPageNo}">
        <a href="detail?articleNo=${param.articleNo}&boardNo=${param.boardNo}&pageNo=${param.pageNo}&cPageNo=${param.finalPageNo}" class="direction next">...${param.totalCount}<span></span><span></span></a>
    </c:when>
    
    <c:otherwise>
       <a href="detail?articleNo=${param.articleNo}&boardNo=${param.boardNo}&pageNo=${param.pageNo}&cPageNo=${param.nextPageNo}" class="direction next">다음페이지 <span></span></a>
       <a href="detail?articleNo=${param.articleNo}&boardNo=${param.boardNo}&pageNo=${param.pageNo}&cPageNo=${param.finalPageNo}" class="direction next">...${param.totalCount}<span></span><span></span></a>
    </c:otherwise>
  </c:choose>
</div>



