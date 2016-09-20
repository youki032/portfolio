<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
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
    
    <meta http-equiv="Expires" content="0">
    
    <!-- Site Properties -->
    <title> - TrashBox - </title>  
    
    
    <!-- jQuery Library -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery-1.11.3.min.js"/>"></script>
<%--     <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery-ui-1.11.4.min.js"/>"></script> --%>
<%--     <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/javascript/css/jquery-ui-1.11.4.css" />" > --%>
    
    
    <!-- sweetalert -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/sweetalert.min.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/javascript/css/sweetalert.css"/>">
        
        
    <!-- image slider -->
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/jquery.flexslider-min.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/javascript/css/jquery.flexslider-min.css"/>">
        
        
    <!-- shortcut icon -->
    <link rel="shortcut icon" href="<c:url value="/resources/trashbox/icons/tricolor.ico"/>" type="image/x-icon">


    <!-- trashbox css, js -->    
    <script type="text/javascript" src="<c:url value="/resources/trashbox/javascript/js/trashbox/trashbox.main.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/trashbox/stylesheets/trashbox-main.css"/>">  
    
    
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
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

//main게시판 테이블 번호
var notice_num = 1;
var newest_num = 2;
var week_num = 3;
var month_num = 4;

//fast image테이블 이미지 개수 전역변수
var imageSize;

 
</script>
</head>
  
<body id="trashbox">
   <jsp:include page="../include/menubar.jsp" flush="true" />
   
<div class="pusher">
 

    <!-- MainPage Contents -->


        <div class="slider_container">
          <div class="flexslider">
            <ul class="slides">
            
                <li id="slider_image1">
                  <div id="black_img">
                  </div>
                  <div class="flex-caption">
                    <div class="caption_title_line">
                      <div id="headers">
                        <h1 class="ui inverted header" id="slides_h1">Trash Box.</h1>
                        <h2 class="ui inverted header">Do it yourself</h2>
                      </div>
                    </div>
                  </div>
                </li>
                
                <li id="slider_image2">
                  <div id="black_img">
                  </div>
                  <div class="flex-caption">
                    <div class="caption_title_line">
                      <div id="headers">
                        <h1 class="ui inverted header" id="slides_h1">Trash Box.</h1>
                        <h2 class="ui inverted header">Do it yourself</h2>
                      </div>  
                    </div>
                  </div>
                </li>
                
                <li id="slider_image3">
                  <div id="black_img">
                  </div>
                  <div class="flex-caption">
                    <div class="caption_title_line">
                      <div id="headers">
                        <h1 class="ui inverted header" id="slides_h1">Trash Box.</h1>
                        <h2 class="ui inverted header">Do it yourself</h2>
                      </div>  
                    </div>
                  </div>
                </li>
            
            </ul>
          </div>

          <div id="resizing_slider_container">
            <div id="resizing_headers">
              <h1 class="ui inverted header">Trash Box.</h1>
              <h2 class="ui inverted header">Do it yourself</h2>
            </div>
          </div>
        
        </div>  


		<div class="ui vertical stripe segment">
			<div class="ui internally grid" id="fast_table">
				<div class="row">
          
          <div class="ui segment" id="notice_segment">
					 <div class="eight wide column">
            <span>공지사항</span>
						  <table class="ui small celled center aligned table" id="notice_table">
                <tbody>
                </tbody>
              </table>
					 </div>
					</div>
          
          <div class="ui segment" id="newest_segment">
					 <div class="eight wide column">
					   <span>최신 게시물</span>
						  <table class="ui small celled center aligned table" id="newest_table">
                <tbody>
                </tbody>
              </table>
					 </div>
				  </div>
				</div>
				
				
				<div class="row">
				  <div class="ui segment" id="week_segment">
					 <div class="eight wide column">
					   <span>주간 베스트 게시물</span>
						    <table class="ui small celled center aligned table" id="week_best_table">
                  <tbody>
                  </tbody>
              </table>
					 </div>
					</div>
					
          <div class="ui segment">
					 <div class="eight wide column" id="month_segment">
					   <span>월간 베스트 게시물</span>
						  <table class="ui small celled center aligned table" id="month_best_table">
                <tbody>
                </tbody>
              </table>
					 </div>
				</div>
			</div>
			
		</div>
		</div>


		<div class="ui vertical stripe segment">
		  <div class="ui segment" id="image_segment">
		    <div>
		      <h3>이미지</h3>
		    </div>

          <div class='ui four cards' id="fast_image">    

           </div>

			
			<div id="fast_image_btn">
			   <input type="hidden" id="pageStatus" value="0"/>
			   <input class="ui mini button" type="button" id="prevImg" value="이전 이미지" />
			   <input class="ui mini button" type="button" id="nextImg" value="다음 이미지" />
			</div>
			
	  </div>
	  </div>
	   

<!--     <div class="ui vertical stripe segment"> -->
<!--        <div class="ui text container"> -->
<!--           <h3 class="ui header">Breaking The Grid, Grabs Your Attention</h3> -->
<!--           <p>Instead of focusing on content creation and hard work, we have learned how to master the art of doing nothing by providing massive amounts of whitespace and generic content that can seem massive, monolithic and worth your attention.</p> -->
<!--           <a class="ui large button">Read More</a> -->
<!--           <h4 class="ui horizontal header divider"> -->
<!--             <a href="#">Case Studies</a> -->
<!--           </h4> -->
<!--           <h3 class="ui header">Did We Tell You About Our Bananas?</h3> -->
<!--           <p>Yes I know you probably disregarded the earlier boasts as non-sequitor filler content, but its really true. It took years of gene splicing and combinatory DNA research, but our bananas can really dance.</p> -->
<!--           <a class="ui large button">I'm Still Quite Interested</a> -->
<!--         </div> -->
<!--      </div> -->



  <jsp:include page="../include/footer.jsp" flush="true" />

    
    
</div>
</body>
</html>