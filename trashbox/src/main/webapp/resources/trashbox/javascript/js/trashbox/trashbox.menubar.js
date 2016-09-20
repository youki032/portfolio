$(function() {  
    	 
	//시작시 메시지가 있는지 한번 체크한다.
	load_msg_checker();
	
	//시작시 menubar 정보를 한번 불러온다.
	load_menubar();
	
	
	// 여기에 시작시 menubar 불러오는 코드 작성하기
	
	
	
	
	
    	 
    	 return false;
     });


      function do_myMsg() {  //메시지함 들어가기
          var url = "/article/myMsg";
          var title = "myMsg";
          var status = 'location=no,toolbar=no,directories=no,scrollbars=no,resizable=no,status=no,menubar=no,width=680, height=580, top=0,left=20'; 
          window.open(url, title, status, ""); 
          
      }
     
      
      
      function load_menubar() {
    	  $.ajax({
              type: "POST",
              url : "/include/menubar",
              datatype: "Object",
              beforeSend: function(xhr){
                  xhr.setRequestHeader(header, token);
              },
              success: function(data) {
            	
            	var fomatText = String(data.userLevel); //integer형태의 숫자값을 String으로 형변환.
            	var levelIcon = "<img id='levelIcon' src='/resources/trashbox/icons/level/" + fomatText + ".gif'>";
            	$("#menubar_level").html(levelIcon);  //level icon 삽입
            	
            	//menubar progress 조절
            	$('#exp_progress_bar')
            	  .progress({
            	    value : data.nowExp,
            	    total    : data.nextExp
            	  });

              }
          });
      }
      
      
        
      function load_msg_checker() {
        var startVal = "start";
        
        $.ajax({
              type: "POST",
              url : "/include/msgCheck",
              data: {start : startVal},
              datatype: "Object",
              beforeSend: function(xhr){
                  xhr.setRequestHeader(header, token);
              },
              success: function(data) {
                
                if(data.msgCheck == 0) {
                    $('.ui.mail.outline.icon').css({
                     color: "black"  
                         }).transition('remove looping');
                                    
                  } else if(data.msgCheck > 0) {
                    
                   $('.ui.mail.outline.icon').css({
                   color: "red"  
                 })
                      .transition('set looping')
                         .transition('tada', '3000ms');
                    
                  }
              }
          });
      }
          
    
      
      
      $(function() { //소켓연결
    	
        var socket;
//          socket = io.connect('https://localhost:19999' , {secure: true}); //localhost 환경
          socket = io.connect('https://www.trashbox.loan:19999' , {secure: true}); //aws 환경
          socket.on('connect', function(){
          console.log('connected');
          
          });
        
          
          socket.on('echo', function(msg){
            console.log("msg.data = " + msg.data);
            console.log("msg.data = " + msg.isMsgCount);
            
            
            //msg flag개수 확인하기위해 불러옴
            var flag_notSame = Number(msg.data);
            var isMsgCount = msg.isMsgCount;
            
            if(flag_notSame > 0) {
              //받는사람의 msg 아이콘 알림 on
              console.log("if flag_notSame = " + flag_notSame);
              load_msg_checker();                         

            } else if(isMsgCount != null) {
              
              load_msg_checker();

            }
            
       });
    });
      
      
      
     
      