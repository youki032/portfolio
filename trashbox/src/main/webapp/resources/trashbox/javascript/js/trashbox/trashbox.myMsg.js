

$(document).ready(function() {
	
	  //페이지 로드시 받은쪽지 리스트 로드.
	  to_list(1); 
	  
	  //화면 web, 모바일 화면 전환시 체크박스 해제
	  $(document).load($(window).bind("resize", checkPosition));
	  
	//받은쪽지리스트
    $("#one").click(function(){
        to_list(1);
        $("#fromList").html("");//리스트탭 변경시 다른 탭을 초기화 시킨다.
        $("#saveList").html("");
      });
     
    //보낸쪽지리스트
    $("#two").click(function(){
        from_list(1);
        $("#toList").html("");//리스트탭 변경시 다른 탭을 초기화 시킨다.
        $("#saveList").html("");
        
      });
    
    //저장리스트        
    $("#three").click(function(){
        save_list(1);
        $("#toList").html("");//리스트탭 변경시 다른 탭을 초기화 시킨다.
        $("#fromList").html("");
        
      });

    $('.ui.menu .item').tab();

    
    //신규쪽지 메시지 아이콘 이미지 효과
    setInterval(function(){   //msg이미지 효과
            $(".blink_img").css({color: 'skyblue'}).toggle();
            }, 600);
 
	});





function checkPosition() {   //화면 web, 모바일 화면 전환시 체크박스 해제
	  
	if($(window).width() < 460) {
		  $(":checkbox[name='msgNo']").prop("checked",false);
		  $("#allCheck").prop('checked',false);
		  console.log("작은화면");
		  
	  } else {
			  $(":checkbox[name='resizing_msgNo']").prop("checked",false);
			  $("#resizing_allCheck").prop('checked',false);
			  console.log("큰화면");
			  }  
	  }



function msgNoAllCheck() {    //리스트 전체 체크 함수
	   if($("#allCheck").prop('checked')) {
       $(":checkbox[name='msgNo']").prop("checked",true);
     
     } else {
       $(":checkbox[name='msgNo']").prop("checked",false);
       
     }
	   }


function resizing_msgNoAllCheck() {   //모바일 화면 리스트 전체 체크 함수
	  
	 if($("#resizing_allCheck").prop('checked')) {
       $("input:checkbox[name='resizing_msgNo']").prop("checked",true);
     
     } else {
       $("input:checkbox[name='resizing_msgNo']").prop("checked",false);
       
     }
}


function btn_choose(str, value) {     //msg삭제 함수
	var btnVal = str;
	console.log("btnVal = " + btnVal + "value = " + value);
	
	swal({
      title : btnVal + "하시겠습니까?",
      showCancelButton: true
      },
      function() {
	
	var checkMsgNos = [];
	
	if($(window).width() < 460) { //모바일화면
		  $("input:checkbox[name='resizing_msgNo']:checked").each(function(){  
			console.log(this.value);
			checkMsgNos.push(this.value); //체크박스에 담겨진 msgNo를 리스트에 담는다.
		  });
		
	} else { //web화면
    $("input:checkbox[name='msgNo']:checked").each(function(){
    console.log(this.value);
    checkMsgNos.push(this.value); //체크박스에 담겨진 msgNo를 리스트에 담는다.
    });
	} 
	 
	//namingCheckMsgNo안에 체크한 박스의 no값들과 list의 고유 no값을 key, value형태로 묶어준다.
	//checkMsgNos=체크된no값, msgListNo=실행중인list번호, btnChoose= 저장,삭제중 실행될 기능
	var namingCheckMsgNo = {"msgNo" : checkMsgNos, "msgListNo" : value, "btnVal" : btnVal};
		  console.log("namingCheckMsgNo = " + namingCheckMsgNo);
		  console.log("msgListNo = " + value);
  
	$.ajax({
      type: "POST",
      url : "/ajax/btnFunction.do",
      data: namingCheckMsgNo,
      datatype: "json",
      beforeSend: function(xhr){
          xhr.setRequestHeader(header, token);
      },
      success: function(data) {
      	//sql문을 정상적으로 실행한후 다시 list 넘버값을 받아와서 리스트함수를 재실행해준다.
      	if(data.msgListNo == 1) {
      		  to_list(1);
      		  console.log("data.msgListNo = " + data.msgListNo);
      		  
      	} else if(data.msgListNo == 2) {
      		  from_list(1);	
      		  console.log("data.msgListNo = " + data.msgListNo);
      		  
      	} else if(data.msgListNo == 3) {
      		  save_list(1);	
      		  console.log("data.msgListNo = " + data.msgListNo);
      		  
      	}
      	
      }
 });
});
}


function do_cancel() {  //답장보내기->취소
location.reload(true);
}


function do_submit() {  //답장보내기 전송함수   
var replayFrm = $("#msgFrm").serialize();
   
   $.ajax({
     type: "POST",
     url : "/ajax/replayMsg.do",
     data: replayFrm,
     contentType: 'application/x-www-form-urlencoded; charset=UTF-8', 
     datatype: "json",
     beforeSend: function(xhr){
         xhr.setRequestHeader(header, token);
     },
     success: function(data) {
         if(data.resultStr == "Success") {
           swal({
             title : data.resultMsg
             },
             function() {
               location.reload(true);
           });
           
           return true;
         
       } else if(data.resultStr == "Failuer") {
           swal(data.resultMsg);
           
           return false;
       }
     }
});
}



function replayMsg(value1, value2) {  //답장보내기 form 만드는 함수
console.log(value1 + value2);

var createTab = "<a class='item' id='replay' data-tab='first'>답장</a>";
var createSeg = "<div class='ui tab segment active' id='replayMsg' data-tab='first'></div>"; 
var toNicName = value1;
var toUserNo = value2;

              $(".ui.top.pointing.secondary.menu").html("");
              $("body").html("");
              
              var replayContent = "<div class='frame'><div class='header'><h2 class='ui center aligned small icon header'>"
                    +  "<i class='send icon'></i>메시지 보내기</h2></div><div class='ui piled segment'>"
                    +  "<p id='userNicName'>받는사람:&nbsp;&nbsp;" + toNicName + "</p>"
                    +  "<form class='ui form' name='msgFrm' id='msgFrm' action='sendMsg' method='post'>"
                    +  "<input type='hidden' id='toUserNicName' name='toUserNicName' value='" + toNicName + "' />"
                    +  "<input type='hidden' id='toUserNo' name='toUserNo' value='" + toUserNo + "' />"
                    +  "<input type='hidden' id='msgFlag' name='msgFlag' value='1' />"
                    +  "<textarea class='ui textarea' name='msgContent' cols='80' rows='12' placeholder='보낼내용...'></textarea>"
                    +  "<div id='replayMsg_btns'><input class='ui right aligned mini button' id='replayMsg_submitBtn' type='button' value='전송' onclick='do_submit();' />"
                    +  "<input class='ui right aligned mini button' id='replayMsg_canceltBtn' type='button' value='취소' onclick='do_cancel();'/>"
                    +   "</div></div></form></div>";
              
                    $(".ui.top.pointing.secondary.menu").append(createTab);
                    $("body").append(createSeg);
                    $(".ui.tab.segment.active").append(replayContent);

              } 




function flag_count(value1, value2) {  //리스트에서 읽지않은 쪽지 표시확인후 제거 함수
var msgNo = value1;
 console.log(value1, value2);

$.ajax({
     type: "POST",
     url : "/ajax/flagCount.do",
     data: {msgNo : value1, msgListNo : value2},
     datatype: "json",
     beforeSend: function(xhr){
         xhr.setRequestHeader(header, token);
     },
     success: function(data) {
       
       if(data.flagCheck == 1) { //1은 실제 테이블의 flag값이 아닌 update된 count.
      		 $("#title_img_"+ msgNo).remove();
      	 
        
       } else {
         
       }
     }
});
}



function to_list(value) { //받은쪽지 리스트
var pageNo = value;
console.log("pageNo = " + pageNo);
console.log("value = " + value);
$.ajax({
     type: "POST",
     url : "/ajax/loadMsgList.do",
     data: {msgListNo : toListVal, pageNo : pageNo},
     datatype: "object",
     beforeSend: function(xhr){
         xhr.setRequestHeader(header, token);
     },
     success: function(data) {
            $("#toList").html(""); //공백으로 일단 초기화
            $("#fromList").html("");
            $("#saveList").html("");
           
            
            $("<table/>").css({
                width: '100%',
                height: 'auto',
                margin: '5px',
                padding: '0;',
              
              }).appendTo("#toList"); // 테이블을 생성하고 그 테이블을 div에 추가함
              
                $("<tr>" , {
                
                html :  "<th class='checkbox_th'>"
              	       +"<input type='checkbox' id='allCheck' onclick='msgNoAllCheck();'>"
              	       +"<input type='checkbox' id='resizing_allCheck' onclick='resizing_msgNoAllCheck();'></th>"
              	       +"<th class='content_th'>" + "content" + "</th>"  // 컬럼명들
              	       +"<th class='from_th'>" + "from" + "</th>"
                       +"<th class='date_th'>" + "date" + "</th>"
                    
                
              }).css({
                 width: '10px'
                
              }).appendTo("table") // 이것을 테이블에붙임
            
            
            $.each(data.listMap, function(index, listMap) {
              
              var items = [];
              
              var msg_img_zone = "";  //msg이미지 저장변수
              var title_cut = "";     //title길이 저장변수
              var title = listMap.msgContent; 
              
              //미확인 쪽지 이미지
              if(listMap.msgFlag == 1) {
                  msg_img_zone = "<img class='blink_img' id='title_img_" + listMap.msgNo + "' src='/resources/trashbox/images/msg_12px.png' style='width:10px; height:10px;'/>";
              
              };
             
                //title길이 자르기(msg아이콘 삽입간격 조정)
               if(title.length > 10) {
              	  title_cut = title.substring(0, 10) + "...";
              	 
               } else {
              	 title_cut = listMap.msgContent;
               }
               
               

                  items.push("<td id='checkbox_row'><div class='ui checkbox'><input type='checkbox' id='input_checkbox' name='msgNo' value='" + listMap.msgNo + "' >"
                           + "<label></label></div></td><td id='content_row'>"
                           + "<div class='ui accordion'><div class='title' onclick='flag_count("+ listMap.msgNo +"," + toListVal + "); this.onclick=null;'>" //this.onclick=null; 함수 한번만 실행하게함.
                           + "<i class='dropdown icon'></i><span id='content_cut' class='content_cut'>" + title_cut + "</span>"
                           + "<span id='msg_img_zone' class='msg_img_zone'>" + msg_img_zone + "</span></div>"
                           + "<div id='content' class='content'><p class='transition hidden'>" + listMap.msgContent + "</p></div></div>"
                           + "<span id='mini_row'><div class='ui checkbox'><input type='checkbox' id='input_checkbox' name='resizing_msgNo' value='" + listMap.msgNo + "' >"
                           + "<label></label></div><input type='button' class='nicNameBtn' name='fromUserNo' id='" + listMap.fromUserNo + "' value='" + listMap.fromUserNicName +"' onclick='replayMsg(this.value, this.id);'/>"
                           + "ㅣ" + listMap.createDate +"</span></td>"
                           );
                  
                  items.push("<td id='from_row'>" + "<input type='button' class='nicNameBtn' name='fromUserNo' id='" + listMap.fromUserNo + "' value='" + listMap.fromUserNicName +"' onclick='replayMsg(this.value, this.id);'/></td>");
                  items.push("<td id='date_row'>" + listMap.createDate +"</td>");
                  
                  
                  $("<tr/>", {
                         html : items // 티알에 붙임,
                   }).appendTo("table"); 
                  

                  $('.ui.accordion').accordion({
                      exclusive: true,
                      closenested: true,
                      animateChildren: true
                    });
                  
                  $('.ui.checkbox').checkbox();
                  
                  
                });
              
                $.each(data.pagingMap, function(index, pagingMap) {
                  var items = [];
                   
                  items.push($("#pageNo").val(pagingMap.PageNo));
                  items.push($("#prevPageNo").val(pagingMap.prevPageNo));
                  items.push($("#nextPageNo").val(pagingMap.nextPageNo));
                  items.push(pageNo = parseInt(pagingMap.pageNo));
                  items.push(prevPageNo = parseInt(pagingMap.prevPageNo));
                  items.push(nextPageNo = parseInt(pagingMap.nextPageNo));
                  items.push(finalPageNo = parseInt(pagingMap.finalPageNo));
                  items.push(startPageNo = parseInt(pagingMap.startPageNo));
                  items.push(firstPageNo = parseInt(pagingMap.firstPageNo));
                  items.push(endPageNo = parseInt(pagingMap.endPageNo));
                  
                 
                  var prevStep = "<a href='#' onClick='javascript:to_list(" + prevPageNo + ");return false;'><span></span>이전</a>";
                  var nextStep = "<a href='#' onClick='javascript:to_list(" + nextPageNo + ");return false;'><span></span>다음</a>";

                  
                  $("#toList_paging").html("");
                  
                  $("#toList_paging").append(prevStep);
                    
                    for(var i=startPageNo; i<=endPageNo; i++) {
                      if(i == pageNo) {
                      page = "<strong>" + i + "</strong>";
                      $("#toList_paging").append(page);
                      
                      } else {
                      page = "<a href='#' onClick='javascript:to_list(" + i + ");return false;'>" + "[" + i + "]" + "</a>";
                      $("#toList_paging").append(page);   
                      
                      }
                    }
                    
                    $("#toList_paging").append(nextStep);
                  
                });
                  
                },
                error : function(xhr, status, error) {
              	  location.reload();
//                     $("#toList").html("받으신 쪽지가 없습니다.");
                    
                }
            });
     }



function from_list(value) {  //보낸쪽지 리스트
 var pageNo = value;

   console.log("pageNo = " + pageNo);
   console.log("value = " + value);
   $.ajax({
         type: "POST",
         url : "/ajax/loadMsgList.do",
         data: {msgListNo : fromListVal, pageNo : pageNo},
         datatype: "object",
         beforeSend: function(xhr){
             xhr.setRequestHeader(header, token);
         },
         success: function(data) {
                $("#fromList").html(""); //공백으로 일단 초기화
                $("#toList").html(""); //공백으로 일단 초기화
                $("#saveList").html("");
                
                $("<table/>").css({
                    width: '100%',
                    height: 'auto',
                    margin: '5px',
                    padding: '0;',
                  
                  }).appendTo("#fromList"); // 테이블을 생성하고 그 테이블을 div에 추가함
                  
                    $("<tr>" , {
                    
                  	  html :  "<th class='checkbox_th'>"
                            +"<input type='checkbox' id='allCheck' onclick='msgNoAllCheck();'>"
                            +"<input type='checkbox' id='resizing_allCheck' onclick='resizing_msgNoAllCheck();'></th>"
                            +"<th class='content_th'>" + "content" + "</th>"  // 컬럼명들
                            +"<th class='from_th'>" + "from" + "</th>"
                            +"<th class='date_th'>" + "date" + "</th>"
                    
                  }).css({
                     width: '10px'
                    
                  }).appendTo("table") // 이것을 테이블에붙임
                
                
                $.each(data.listMap, function(index, listMap) {
                  
                  var items = [];
                  
                  var title_cut = "";     //title길이 저장변수
                  var title = listMap.msgContent; 
                    //title길이 자르기(msg아이콘 삽입간격 조정)
                   if(title.length > 10) {
                      title_cut = title.substring(0, 10) + "...";
                     
                   } else {
                     title_cut = listMap.msgContent;
                   }
                  
                   items.push("<td id='checkbox_row'><div class='ui checkbox'><input type='checkbox' id='input_checkbox' name='msgNo' value='" + listMap.msgNo + "' >"
                           + "<label></label></div></td><td id='content_row'>"
                           + "<div class='ui accordion'><div class='title' onclick='flag_count("+ listMap.msgNo +"); this.onclick=null;'>" //this.onclick=null; 함수 한번만 실행하게함.
                           + "<i class='dropdown icon'></i><span id='content_cut' class='content_cut'>" + title_cut + "</span></div>"
                           + "<div id='content' class='content'><p class='transition hidden'>" + listMap.msgContent + "</p></div></div>"
                           + "<span id='mini_row'><div class='ui checkbox'><input type='checkbox' id='input_checkbox' name='resizing_msgNo' value='" + listMap.msgNo + "' >"
                           + "<label></label></div><input type='button' class='nicNameBtn' name='fromUserNo' id='" + listMap.fromUserNo + "' value='" + listMap.fromUserNicName +"' onclick='replayMsg(this.value, this.id);'/>"
                           + "ㅣ" + listMap.createDate +"</span></td>"
                           );
                      
                      items.push("<td id='from_row'>" + "<input type='button' class='nicNameBtn' value='" + listMap.toUserNicName +"' disabled='disabled' />");
                      items.push("<td id='date_row'>" + listMap.createDate +"</td>");
                      items.push($("#fromUserNo").val(listMap.fromUserNo));
                      items.push($("#toUserNo").val(listMap.toUserNo));
                      items.push($("#toUserNicName").val(listMap.toUserNicName));
                      
                      
                      $("<tr/>", {
                             html : items // 티알에 붙임,
                       }).appendTo("table"); 
                
                      
                      $('.ui.accordion').accordion({
                          exclusive: true,
                          animateChildren: true
                        });
                      
                      $('.ui.checkbox').checkbox();
                      
                      
                    });
                  
                    $.each(data.pagingMap, function(index, pagingMap) {
                      var items = [];
                       
                      items.push($("#pageNo").val(pagingMap.PageNo));
                      items.push($("#prevPageNo").val(pagingMap.prevPageNo));
                      items.push($("#nextPageNo").val(pagingMap.nextPageNo));
                      items.push(pageNo = parseInt(pagingMap.pageNo));
                      items.push(prevPageNo = parseInt(pagingMap.prevPageNo));
                      items.push(nextPageNo = parseInt(pagingMap.nextPageNo));
                      items.push(finalPageNo = parseInt(pagingMap.finalPageNo));
                      items.push(startPageNo = parseInt(pagingMap.startPageNo));
                      items.push(firstPageNo = parseInt(pagingMap.firstPageNo));
                      items.push(endPageNo = parseInt(pagingMap.endPageNo));
                      
                     
                      var prevStep = "<a href='#' onClick='javascript:from_list(" + prevPageNo + ");return false;'><span></span>이전</a>";
                      var nextStep = "<a href='#' onClick='javascript:from_list(" + nextPageNo + ");return false;'><span></span>다음</a>";
                      
                      
                      $("#fromList_paging").html("");
                      
                      $("#fromList_paging").append(prevStep);
                        
                        for(var i=startPageNo; i<=endPageNo; i++) {
                          if(i == pageNo) {
                          page = "<strong>" + i + "</strong>";
                          $("#fromList_paging").append(page);
                          
                          } else {
                          page = "<a href='#' onClick='javascript:from_list(" + i + ");return false;'>" + "[" + i + "]" + "</a>";
                          $("#fromList_paging").append(page);   
                          
                          }
                        }
                        
                        $("#fromList_paging").append(nextStep);
                      
                    });
                    },
                    error : function(xhr, status, error) {
//                         $("#fromList").html("보내신 쪽지가 없습니다.");
                  	  location.reload();
                    }
                });
          }


function save_list(value) {  //저장한 쪽지 리스트
	var pageNo = value;
   console.log("pageNo = " + pageNo);
   console.log("value = " + value);
   $.ajax({
         type: "POST",
         url : "/ajax/loadMsgList.do",
         data: {msgListNo : saveListVal, pageNo : pageNo},
         datatype: "object",
         beforeSend: function(xhr){
             xhr.setRequestHeader(header, token);
         },
         success: function(data) {
                $("#saveList").html(""); //공백으로 일단 초기화
                $("#toList").html(""); //공백으로 일단 초기화
                $("#fromList").html("");
                
                $("<table/>").css({
                    width: '100%',
                    height: 'auto',
                    margin: '5px',
                    padding: '0;',
                  
                  }).appendTo("#saveList"); // 테이블을 생성하고 그 테이블을 div에 추가함
                  
                    $("<tr>" , {
                    
                  	  html :  "<th class='checkbox_th'>"
                            +"<input type='checkbox' id='allCheck' onclick='msgNoAllCheck();'>"
                            +"<input type='checkbox' id='resizing_allCheck' onclick='resizing_msgNoAllCheck();'></th>"
                            +"<th class='content_th'>" + "content" + "</th>"  // 컬럼명들
                            +"<th class='from_th'>" + "from" + "</th>"
                            +"<th class='date_th'>" + "date" + "</th>"
                    
                  }).css({
                     width: '10px'
                    
                  }).appendTo("table") // 이것을 테이블에붙임
                
                
                $.each(data.listMap, function(index, listMap) {
                  
              	  var items = [];
                    
                    var msg_img_zone = "";  //msg이미지 저장변수
                    var title_cut = "";     //title길이 저장변수
                    var title = listMap.msgContent; 
                    
                    //미확인 쪽지 이미지
                    if(listMap.msgFlag == 1) {
                        msg_img_zone = "<img class='blink_img' id='title_img_" + listMap.msgNo + "' src='/resources/trashbox/images/msg_12px.png' style='width:10px; height:10px;'/>";
                    
                    };
                   
                      //title길이 자르기(msg아이콘 삽입간격 조정)
                     if(title.length > 10) {
                        title_cut = title.substring(0, 10) + "...";
                       
                     } else {
                       title_cut = listMap.msgContent;
                     }
                     

                        items.push("<td id='checkbox_row'><div class='ui checkbox'><input type='checkbox' id='input_checkbox' name='msgNo' value='" + listMap.msgNo + "' >"
                                 + "<label></label></div></td><td id='content_row'>"
                                 + "<div class='ui accordion'><div class='title' onclick='flag_count("+ listMap.msgNo +"," + saveListVal + "); this.onclick=null;'>" //this.onclick=null; 함수 한번만 실행하게함.
                                 + "<i class='dropdown icon'></i><span id='content_cut' class='content_cut'>" + title_cut + "</span>"
                                 + "<span id='msg_img_zone' class='msg_img_zone'>" + msg_img_zone + "</span></div>"
                                 + "<div id='content' class='content'><p class='transition hidden'>" + listMap.msgContent + "</p></div></div>"
                                 + "<span id='mini_row'><div class='ui checkbox'><input type='checkbox' id='input_checkbox' name='resizing_msgNo' value='" + listMap.msgNo + "' >"
                                 + "<label></label></div><input type='button' class='nicNameBtn' name='fromUserNo' id='" + listMap.fromUserNo + "' value='" + listMap.fromUserNicName +"' onclick='replayMsg(this.value, this.id);'/>"
                                 + "ㅣ" + listMap.createDate +"</span></td>"
                                 );
                      
                      items.push("<td id='from_row'>" + "<input type='button' class='nicNameBtn' value='" + listMap.fromUserNicName +"' disabled='disabled' />");
                      items.push("<td id='date_row'>" + listMap.createDate +"</td>");
                      items.push($("#fromUserNo").val(listMap.fromUserNo));
                      items.push($("#toUserNo").val(listMap.toUserNo));
                      items.push($("#toUserNicName").val(listMap.toUserNicName));
                      
                      
                      $("<tr/>", {
                             html : items // 티알에 붙임,
                       }).appendTo("table"); 
                
                      
                      $('.ui.accordion').accordion({
                          exclusive: true,
                          animateChildren: true
                        });
                      
                      $('.ui.checkbox').checkbox();
                                                  
                      
                    });
                  
                    $.each(data.pagingMap, function(index, pagingMap) {
                      var items = [];
                       
                      items.push($("#pageNo").val(pagingMap.PageNo));
                      items.push($("#prevPageNo").val(pagingMap.prevPageNo));
                      items.push($("#nextPageNo").val(pagingMap.nextPageNo));
                      items.push(pageNo = parseInt(pagingMap.pageNo));
                      items.push(prevPageNo = parseInt(pagingMap.prevPageNo));
                      items.push(nextPageNo = parseInt(pagingMap.nextPageNo));
                      items.push(finalPageNo = parseInt(pagingMap.finalPageNo));
                      items.push(startPageNo = parseInt(pagingMap.startPageNo));
                      items.push(firstPageNo = parseInt(pagingMap.firstPageNo));
                      items.push(endPageNo = parseInt(pagingMap.endPageNo));
                      
                     
                      var prevStep = "<a href='#' onClick='javascript:save_list(" + prevPageNo + ");return false;'><span></span>이전</a>";
                      var nextStep = "<a href='#' onClick='javascript:save_list(" + nextPageNo + ");return false;'><span></span>다음</a>";

                      
                      $("#saveList_paging").html("");
                      
                      $("#saveList_paging").append(prevStep);
                        
                        for(var i=startPageNo; i<=endPageNo; i++) {
                          if(i == pageNo) {
                          page = "<strong>" + i + "</strong>";
                          $("#saveList_paging").append(page);
                          
                          } else {
                          page = "<a href='#' onClick='javascript:save_list(" + i + ");return false;'>" + "[" + i + "]" + "</a>";
                          $("#saveList_paging").append(page);   
                          
                          }
                        }
                        
                        $("#saveList_paging").append(nextStep);
                      
                    });
                      
                    },
                    
                    error : function(xhr, status, error) {
                  	  location.reload();
                  }
              });  
          }