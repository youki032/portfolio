



$(document).ready(function(){
	 
	  var nextPosition = $("nextImg").offset();
	  var prevPosition = $("prevImg").offset();
	 
	  
	  /* =========== banner slide효과  =========== */
	  $(window).load(function () {
		  
		  $('.slider_container').transition({
			  animation : 'horizontal flip in',
			  duration   : '1.5s',
		  });
		  
		  
		  $('.flexslider').flexslider({
		        animation: "fade"
		    });
		  
		  
		  console.log("시작!");
		  //main로딩과 함께 테이블 실행함수
		  notice_table();
		  newest_table();
		  week_table();
		  month_table();
		  
		  //image테이블 페이징 기본값으로 0
		  image_table(0);
		  
	  });
	  
	  
	  
	  
	  //mina 로딩시 image prev버튼 숨기기
	  $("#prevImg").hide();
	 
	  
	  /* =========== image next 페이징  =========== */
	  $("#nextImg").click(function(e) {
		  e.preventDefault();
		  
		  var stat = $("#pageStatus").val();
		  var num = parseInt(stat);
		  num++;
		  
		  
		  //next버튼 클릭시 count되는 int값을 body에 있는 input의 스택값을 더한다.
		  $("#pageStatus").val(num);
		  //next로 넘어가면 prev버튼을 다시 생성.
		  $("#prevImg").show();
		  		  
		  
		  if ((imageSize-1) == 1) {
			  
			  swal("마지막 이미지 입니다.");
			  
			  //다음 보여줄 이미지가 없으면 next버튼을 감춘다
			  $("#nextImg").hide();
			  
		  } 
		  
		  //input에 넣은 페이지값을 다시 가져와서 넣어준다.
		  image_table($("#pageStatus").val());
		  
		  });
	  
	  
	  /* =========== image prev페이징  =========== */
	  $("#prevImg").click(function(e) {  
	      e.preventDefault();
	      	      
	      var stat = $("#pageStatus").val();
	      var num = parseInt(stat);
	      num--;
	      
	      if (num-1 <= -1) {
	    	  swal("첫 페이지 입니다.");
	    	  num = 0;
	    	  
	    	  //첫페이지므로 prev버튼을 다시 숨긴다.
	    	  $("#prevImg").hide();
	    	  //next버튼 다시 보여준다
	    	  $("#nextImg").show();
	    	  
	      } 
// 	      else if(imageSize-1 != 1) {
// 	      }
	      
	      //prev버튼 클릭시 count되는 int값을 body에 있는 input의 스택값을 뺀다.
	      $("#pageStatus").val(num);
	      
	      //input에 넣은 페이지값을 다시 가져와서 넣어준다.
	      image_table($("#pageStatus").val());
	      
	      });
	  
	  
  });
  

  
  function notice_table() {
	  $("#notice_table tbody").html("");
	  
	  $.ajax({
          type : "POST",
          url : "/ajax/loadNoticeTable.do",
          datatype : "object",
          beforeSend: function(xhr){
              xhr.setRequestHeader(header, token);
          },
          success : function(noticeList) {
        	    var listNoEa = [];  //메인 테이블당 보여지는 갯수가 10인데 가져오는 글 개수가 10개 미만일때 빈 컬럼을 만들어주기 위한 배열 변수.
        	  
            $.each(noticeList, function(index, noticeList) {
            	  
              var items = [],

                  viewIcon = "<img id='miniHeart_icon' src='/resources/trashbox/images/look4.png'>",
                  likeIcon = "<img id='miniHeart_icon' src='/resources/trashbox/images/mini_red_heart.png'>";
                  
              listNoEa.push(noticeList.listNo); // 불러온 게시글 수가 10개 미만인지 확인하기위해 게시글 갯수를 넣어준다.
              
              items.push("<tr><td id='title_column'><a href='/article/detail?&boardNo=" 
            		       + noticeList.boardNo + "&articleNo="+ noticeList.articleNo + "&pageNo=1&cPageNo=1' title='" 
            		       + noticeList.title + "'>" + noticeList.title + "</a></td><td id='view_column'><span id='view_left_align'>" + viewIcon + "ㅣ</span>" 
            		       + "<span id='view_right_align'>" + noticeList.views + "</span></td>"+ "<td id='like_column'><span id='like_left_align'>" + likeIcon + "ㅣ</span>" 
            		       + "<span id='like_right_align'>" + noticeList.likeCnt + "</span></td>"
            		         + "<td id='resizing_column'><div id='resizing_title'><a href='/article/detail?&boardNo=" 
                         + noticeList.boardNo + "&articleNo="+ noticeList.articleNo + "&pageNo=1&cPageNo=1' title='" + noticeList.title + "'>" 
                         + noticeList.title + "</a></div><br/>" + "ㅣ<span id='resizing_view'>" + viewIcon + "&nbsp;&nbsp;" + noticeList.views + "</span>"
                         + "ㅣ<span id='resizing_like'>" + likeIcon + "&nbsp;&nbsp;" + noticeList.likeCnt + "</span>ㅣ"
                         + "</span></td></tr>");
            	              
              $("#notice_table tbody").append(items);
            
            });
            
              
        	    // 게시글 개수가 10개 미만이면 모자라는 갯수만큼 빈 컬럼을 만들어준다.
              if(listNoEa.length != 10) {
            	  
            	  var restListNo = 10 - listNoEa.length;
            	  
                  for(var i = 0; i < restListNo; i++) {
                	  
                	  $("#notice_table tbody").append(
                              "<tr id='temp_column' style='height: 38px;'><td id='title_column'></td><td id='view_column'></td>"
                            + "<td id='like_column'></td></tr>");
                	  
                   }
                }
      
            
          },
          error : function(xhr, status, error) {
           location.reload();
          
          }
      });
		           
  }

  
  
  
	function newest_table() {
		$("#newest_table tbody").html("");  
		
		$.ajax({
            type : "POST",
            url : "/ajax/loadNewestTable.do",
            datatype : "object",
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success : function(newestList) {
            	 
            	  var listNoEa = [];  //메인 테이블당 보여지는 갯수가 10인데 가져오는 글 개수가 10개 미만일때 빈 컬럼을 만들어주기 위한 배열 변수.
            	
            	  $.each(newestList, function(index, newestList) {
              
                var items = [],
                
                  viewIcon = "<img id='miniHeart_icon' src='/resources/trashbox/images/look4.png' >",
                  likeIcon = "<img id='miniHeart_icon' src='/resources/trashbox/images/mini_red_heart.png' >";
                  
                  
                listNoEa.push(newestList.listNo); // 불러온 게시글 수가 10개 미만인지 확인하기위해 게시글 갯수를 넣어준다.
                
                items.push("<tr><td id='title_column'><a href='/article/detail?&boardNo=" 
                        + newestList.boardNo + "&articleNo="+ newestList.articleNo + "&pageNo=1&cPageNo=1' title='" 
                        + newestList.title + "'>" + newestList.title + "</a></td><td id='view_column'><span id='view_left_align'>" + viewIcon + "ㅣ</span>" 
                        + "<span id='view_right_align'>" + newestList.views + "</span></td>"+ "<td id='like_column'><span id='like_left_align'>" + likeIcon + "ㅣ</span>" 
                        + "<span id='like_right_align'>" + newestList.likeCnt + "</span></td>"
                          + "<td id='resizing_column'><div id='resizing_title'><a href='/article/detail?&boardNo=" 
                          + newestList.boardNo + "&articleNo="+ newestList.articleNo + "&pageNo=1&cPageNo=1' title='" + newestList.title + "'>" 
                          + newestList.title + "</a></div><br/>" + "ㅣ<span id='resizing_view'>" + viewIcon + "&nbsp;&nbsp;" + newestList.views + "</span>"
                          + "ㅣ<span id='resizing_like'>" + likeIcon + "&nbsp;&nbsp;" + newestList.likeCnt + "</span>ㅣ"
                          + "</span></td></tr>");
                
                $("#newest_table tbody").append(items);
              
              });
              
              // 게시글 개수가 10개 미만이면 모자라는 갯수만큼 빈 컬럼을 만들어준다.
              if(listNoEa.length != 10) {
                  
                  var restListNo = 10 - listNoEa.length;
                  
                    for(var i = 0; i < restListNo; i++) {
                    	
                    	$("#newest_table tbody").append(
                                "<tr id='temp_column' style='height: 38px;'><td id='title_column'></td><td id='view_column'></td>"
                              + "<td id='like_column'></td></tr>");
                    	
                     }
                  }
              
              
            },
            error : function(xhr, status, error) {
             location.reload();
            
            }
          });
		
	 }

	
	
	
	function week_table() {
		$("#week_best_table tbody").html("");
		
		$.ajax({
            type : "POST",
            url : "/ajax/loadWeekTable.do",
            datatype : "object",
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success : function(weekList) {
            	
            	var listNoEa = [];  //메인 테이블당 보여지는 갯수가 10인데 가져오는 글 개수가 10개 미만일때 빈 컬럼을 만들어주기 위한 배열 변수.
            	
              $.each(weekList, function(index, weekList) {
              
                var items = [],

                  viewIcon = "<img id='miniHeart_icon' src='/resources/trashbox/images/look4.png' >",
                  likeIcon = "<img id='miniHeart_icon' src='/resources/trashbox/images/mini_red_heart.png' >";
                  
                  
                listNoEa.push(weekList.listNo); // 불러온 게시글 수가 10개 미만인지 확인하기위해 게시글 갯수를 넣어준다.
                
                items.push("<tr><td id='title_column'><a href='/article/detail?&boardNo=" 
                        + weekList.boardNo + "&articleNo="+ weekList.articleNo + "&pageNo=1&cPageNo=1' title='" 
                        + weekList.title + "'>" + weekList.title + "</a></td><td id='view_column'><span id='view_left_align'>" + viewIcon + "ㅣ</span>" 
                        + "<span id='view_right_align'>" + weekList.views + "</span></td>"+ "<td id='like_column'><span id='like_left_align'>" + likeIcon + "ㅣ</span>" 
                        + "<span id='like_right_align'>" + weekList.likeCnt + "</span></td>"
                          + "<td id='resizing_column'><div id='resizing_title'><a href='/article/detail?&boardNo=" 
                          + weekList.boardNo + "&articleNo="+ weekList.articleNo + "&pageNo=1&cPageNo=1' title='" + weekList.title + "'>" 
                          + weekList.title + "</a></div><br/>" + "ㅣ<span id='resizing_view'>" + viewIcon + "&nbsp;&nbsp;" + weekList.views + "</span>"
                          + "ㅣ<span id='resizing_like'>" + likeIcon + "&nbsp;&nbsp;" + weekList.likeCnt + "</span>ㅣ"
                          + "</span></td></tr>");
                
                $("#week_best_table tbody").append(items);
              
              });
             
              // 게시글 개수가 10개 미만이면 모자라는 갯수만큼 빈 컬럼을 만들어준다.
              if(listNoEa.length != 10) {
                  
                  var restListNo = 10 - listNoEa.length;
                  
                    for(var i = 0; i < restListNo; i++) {
                    	
                    	$("#week_best_table tbody").append(
                                "<tr id='temp_column' style='height: 38px;'><td id='title_column'></td><td id='view_column'></td>"
                              + "<td id='like_column'></td></tr>");
                    	
                     }
                  }
              
            },
            error : function(xhr, status, error) {
             location.reload();
            
            }
          });
		
	}

	
	
	function month_table() {
		$("#month_best_table tbody").html("");
		
		$.ajax({
            type : "POST",
            url : "/ajax/loadMonthTable.do",
            datatype : "object",
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success : function(monthList) {
              
            	var listNoEa = [];  //메인 테이블당 보여지는 갯수가 10인데 가져오는 글 개수가 10개 미만일때 빈 컬럼을 만들어주기 위한 배열 변수.
            	
              $.each(monthList, function(index, monthList) {
              
                var items = [],

                  viewIcon = "<img id='miniHeart_icon' src='/resources/trashbox/images/look4.png' >",
                  likeIcon = "<img id='miniHeart_icon' src='/resources/trashbox/images/mini_red_heart.png' >";
                  
                  
                listNoEa.push(monthList.listNo); // 불러온 게시글 수가 10개 미만인지 확인하기위해 게시글 갯수를 넣어준다.
                
                items.push("<tr><td id='title_column'><a href='/article/detail?&boardNo=" 
                        + monthList.boardNo + "&articleNo="+ monthList.articleNo + "&pageNo=1&cPageNo=1' title='" 
                        + monthList.title + "'>" + monthList.title + "</a></td><td id='view_column'><span id='view_left_align'>" + viewIcon + "ㅣ</span>" 
                        + "<span id='view_right_align'>" + monthList.views + "</span></td>"+ "<td id='like_column'><span id='like_left_align'>" + likeIcon + "ㅣ</span>" 
                        + "<span id='like_right_align'>" + monthList.likeCnt + "</span></td>"
                          + "<td id='resizing_column'><div id='resizing_title'><a href='/article/detail?&boardNo=" 
                          + monthList.boardNo + "&articleNo="+ monthList.articleNo + "&pageNo=1&cPageNo=1' title='" + monthList.title + "'>" 
                          + monthList.title + "</a></div><br/>" + "ㅣ<span id='resizing_view'>" + viewIcon + "&nbsp;&nbsp;" + monthList.views + "</span>"
                          + "ㅣ<span id='resizing_like'>" + likeIcon + "&nbsp;&nbsp;" + monthList.likeCnt + "</span>ㅣ"
                          + "</span></td></tr>");
                
                $("#month_best_table tbody").append(items);
              
              });
              
              // 게시글 개수가 10개 미만이면 모자라는 갯수만큼 빈 컬럼을 만들어준다.
              if(listNoEa.length != 10) {
                  
                  var restListNo = 10 - listNoEa.length;
                  
                    for(var i = 0; i < restListNo; i++) {
                        
                    	$("#month_best_table tbody").append(
                    			"<tr id='temp_column' style='height: 38px;'><td id='title_column'></td><td id='view_column'></td>"
                    		+	"<td id='like_column'></td></tr>");
                    	
                    }
                  }
              
            },
            error : function(xhr, status, error) {
             location.reload();
            
            }
          });
		  	}
	
	
	  function image_table(value) {
		  //페이징 값
		  var pageNum = value;
		  
		  $.ajax({
			    type : "POST",
	            url : "/ajax/loadIamgeTable.do",
	            data : {imgPage : pageNum},
	            datatype : "object",
	            beforeSend: function(xhr){
	                xhr.setRequestHeader(header, token);
	            },
	            success : function(imageList) {
	            	$("#fast_image").html("");
	            	imageSize = imageList.length;
	            	
	            	$.each(imageList, function(index, imageList) {
	            		
	            		var items = [];
	            		
	            		//한 게시글에 여러개의 img태그가 있으면 첫번째 img태그만 잘라서 보여준다.
	            		var imageSplit = imageList.content.split("/>", 1);
	            		var imageResult = imageSplit+"/>";
	            		
                    items.push("<a class='red card' id='redCard' href='"
                             + "/article/detail?&boardNo=" 
                             + imageList.boardNo + "&articleNo="+ imageList.articleNo + "&pageNo=1&cPageNo=1'>" 
                             + "<div class='image' id='createArticle_img' style='margin:auto;'>" + imageResult + "</div></a>");
	            		
                    $("#fast_image").append(items);
	            		 
	            	});

	            	//fast_image 이미지 리사이징
	            	$("#createArticle_img img").each(function() {
	            		   $(this).removeAttr("style");
	            		   $(this).css("max-width", "200px");
	            		   $(this).css("max-height", "260px");
	                  }); 
	            	
	            },
	            
	            error : function(xhr, status, error) {
	            	
	            }
			  
		  });
	  }