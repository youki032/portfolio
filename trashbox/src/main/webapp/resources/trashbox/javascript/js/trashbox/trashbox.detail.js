

$(function() { //모바일 화면에서 검색블록 사이즈 조정
    
    if ($(window).width() < 400) {
      
      $("#simple_buttons").attr("class", "ui mini simple buttons");
      $("#icon_input").attr("class", "ui icon mini input");
      
    } 
});


$(document).ready(function(){
  
    $('#select')
      .dropdown({
        transition:'scale',
        direction: 'upward',
        action:'combo'
      });
    
    
    $('.ui.dropdown')
      .dropdown({
        transition:'scale',
        action:'combo'
      });
    
    
    $('.ui.search')
      .search({
        minCharacters: 2,
        type: 'button'
      });
    
    
    $("#redButton").click(function(){   //추천 button
      $('.ui.autumn.heart.icon.visible')
       .transition({
        animation: 'jiggle'
       });
    });
  
     
    
    //scroll up function
    $(function() {  
      $.scrollUp({
            scrollName: 'scrollUp',
            animation: 'fade',
            activeOverlay: false,
            scrollImg: {
                active: true
            }
        });
    });
    
    
    
   var timer; //지속적인 스크롤 이동시 setTimeout 딜레이 중복을 clearTimeout으로 초기화 시켜주기위한 변수   
   $(window).scroll(function(event) { //scroll up 버튼 숨기기(타이머)
       clearTimeout(timer);
       
       timer = setTimeout(function() {
          $("#scrollUp").css({display:"none"});
       }, 2000);
       
       $("#scrollUp").css({display:"block"});

     });   

    
});



//vertx server 실시간 추천갯수 load
$(function(){
    var socket;

//      socket = io.connect('https://localhost:19999' , {secure: true}); //localhost 환경
      socket = io.connect('https://www.trashbox.loan:19999' , {secure: true}); //aws 환경
      socket.on('connect', function(){
      console.log('connected');
        
      load_msg_checker();
        
      });
   
      
      socket.on('echo', function(msg) {
        //articleService에서 추천 카운트가 올라가면 추천수를 vertx서버를통해 push해준다.
        console.log("msg.likeCount = " + msg.likeCount);
        
        var isLikeAllCount = Number(msg.likeCount);
        
        if(isLikeAllCount > 0) {
          $("#likeCountArea").html(isLikeAllCount);                         

        } else {

        }
        
        
      });
  
  });

  
function do_removeArticle() {
  
  swal({   
        title:"",
        text:"현재 게시물을 삭제하시겠습니까?",
        type: "warning",
        confirmButtonText: "Yes",   
        showCancelButton: true,   
        confirmButtonColor: "#DD6B55",   
        closeOnConfirm: false,
        showLoaderOnConfirm: true,
        },
        
        function() {   
          location.href= "removeArticle?articleNo=" + articleNoVal + "&boardNo=" + boardNoVal 
          + "&pageNo=" + pageNoVal + "&listNo=" + listNoVal;

        }); 
     }


function do_removeComment(value) {
  
  swal({   
        title:"",
        text:"선택하신 댓글을 삭제하시겠습니까?",
        type: "warning",
        confirmButtonText: "Yes",   
        showCancelButton: true,   
        confirmButtonColor: "#DD6B55",   
        closeOnConfirm: false,
        showLoaderOnConfirm: true,
        },
        function() {   
          location.href = "removeComment?articleNo=" + articleNoVal + "&boardNo=" + boardNoVal 
                        + "&pageNo=" + pageNoVal + "&cPageNo="+ commentPageNoVal + "&commentNo=" + value;
        });
     }


function do_like() {
          $.ajax({
            type: "POST",
            url : "/ajax/likeArticle.do",
            data: {articleNo : articleNoVal, boardNo : boardNoVal, listNo : listNoVal, nicName : nicNameVal},
            datatype: "json",
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(data) {
            
                 if(data.likeAllCount != null) {
                   $("#likeCountArea").html(data.likeAllCount);
        
                  } else if(data.failLike != true) {
                     swal({
                        title:"",
                        text: "게시물당 추천은 한번밖에 할 수 없습니다.",   
                        type: "error",   
                        confirmButtonText: "Yes." 
                        });
       
                   return false;
     
                   }
                 }
            });
          } 
         
       
       
 function do_vaild_like() {
	 swal({
	    	title:"",
	        text: "로그인후 사용가능합니다.",
	        type: "error"
	      },
	      function() {
	        location.href = "/home/login";
	      });
     }
 
 function do_vaild_cmt() {
	 swal({
	    	title:"",
	        text: "로그인후 사용가능합니다.",
	        type: "error"
	      },
	      function() {
	        location.href = "/home/login";
	      });
 }   
    

function do_comment() {
  var vaildComment = document.getElementById("contents");
  if(vaildComment.value == "") {
    swal("글을 입력해주세요!");
    return false;
  }
  return true;
}

 
function userHistoryPopUp(value1, value2) {
    var url = "/article/userHistory?nicName="+value1+"&listNo="+value2;
    var title = "userHistory";
    var status = 'location=no, toolbar=no, directories=no, scrollbars=no, resizable=no, status=no, menubar=no, width=470, height=520, top=0, left=20'; 
    window.open(url, title, status, ""); 
}

function sendMsgPopUp(value1, value2) {
    var url = "/article/sendMsg?nicName="+value1+"&articleNo="+value2;
    var title = "sendMsg";
    var status = 'location=no,toolbar=no,directories=no,scrollbars=no,resizable=no,status=no,menubar=no,width=430, height=550, top=0,left=20'; 
    window.open(url, title, status, "");
}

function anonymous_menuPopUp() {
    swal({
    	title:"",
        text: "로그인후 사용가능합니다.",
        type: "error"
      },
      function() {
        location.href = "/home/login";
      });
  }

function search_btn() {
    var searchFrm = document.getElementById("searchFrm");
    searchFrm.submit();
  }