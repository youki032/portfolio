
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");



//web 화면일때 ckeditor설정
var ckeditor_config = {
           resize_enabled : false, // 에디터 크기를 조절하지 않음
           enterMode : CKEDITOR.ENTER_BR , // 엔터키를 <br> 로 적용함.
           shiftEnterMode : CKEDITOR.ENTER_P ,  // 쉬프트 +  엔터를 <p> 로 적용함.
           toolbarCanCollapse : false , 
           removePlugins : "elementspath", // DOM 출력하지 않음
//            filebrowserUploadUrl: '/article/image_upload?${_csrf.parameterName}=${_csrf.token}', // 파일 업로드를 처리 할 경로 설정.
           height : "400px",
           // 에디터에 사용할 기능들 정의
           toolbar : [
             [ 'Source', '-' , 'NewPage', 'Preview' ],
             [ 'Cut', 'Copy', 'Paste', 'PasteText', '-', 'Undo', 'Redo' ],
             [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript'],
             [ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ],
             '/',
             [ 'Styles', 'Format', 'Font', 'FontSize' ],
             [ 'TextColor', 'BGColor' ],
             [  '#Image', 'CustomUpload', 'Table' , 'SpecialChar']
           ],
           allowedContent: true
         };

//mobile 화면일때 ckeditor설정
var resizing_ckeditor_config = {
        resize_enabled : false, // 에디터 크기를 조절하지 않음
        enterMode : CKEDITOR.ENTER_BR , // 엔터키를 <br> 로 적용함.
        shiftEnterMode : CKEDITOR.ENTER_P ,  // 쉬프트 +  엔터를 <p> 로 적용함.
        toolbarCanCollapse : false , 
        removePlugins : "elementspath", // DOM 출력하지 않음
//         filebrowserUploadUrl: '/article/image_upload?${_csrf.parameterName}=${_csrf.token}', // 파일 업로드를 처리 할 경로 설정.
        height : "200px",
        // 에디터에 사용할 기능들 정의
        toolbar : [
          [ 'Bold', 'Italic', 'Underline', 'Strike'],
          [ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ],
          '/',
          [ 'Styles', 'Font', 'FontSize' ],
          [ 'TextColor', 'BGColor' ],
          [ '#Image', 'CustomUpload', 'Table' , 'SpecialChar', ],
        ],
        allowedContent: true
      };



/* =========== 페이지 로딩 ckeditor 적용(custom파일업로드 버튼, width에 따른 버튼개수조정)  =========== */
$(function() {
    function custom_image_btn(obj) {
      editor = obj;
      editor.addCommand("customUploadCommand", { // create named command
            exec: function(edt) {
               $("#uploadModal").modal('setting', 'closable', false).modal('show');  //업로드 modal열기
               $('.menu .item').tab("change tab", "first"); //modal시작탭 설정
               $("#uploadTabStr").attr("value", "");
               clear_status();
            }
        });
      
      editor.ui.addButton('CustomUpload', { // custom create button
            label: "Click me",
            command: 'customUploadCommand',
            toolbar: 'insert',
//            icon: '<c:url value="/resources/trashbox/images/mini_upload_icon.png"/>'
            icon: '/resources/trashbox/images/mini_upload_icon.png'
        });
    }  
    
    // pc, 모바일 화면에 따라 에디터 버튼 개수 변경
    if($(window).width() < 760) {
      
  	  editor = CKEDITOR.replace( "content" , resizing_ckeditor_config);
      $(".ui.fluid.input").prependTo(".resizing_tr th"); //모바일창 toolBar 보여주기
      custom_image_btn(editor);
      
      } else {
      
      editor = CKEDITOR.replace( "content" , ckeditor_config);
      custom_image_btn(editor);
    
      }
 
  });

	


/* =========== ckeditor 전송체크  =========== */
      function form_save(form) {
           editor.updateElement();
      };
   

      function insert_arrayImg () { //form submit전에 arrayImg에 담긴 파일명을 input에 넣어서 submit한다.
     	 document.getElementById("arrayImg").value = arrayImg;
     	 console.log(arrayImg);
     }
      
   
 /* =========== form 전송  =========== */
   function do_submit(value) {
     var title = document.getElementById("title").value;
     var swalTitle = "";
     
     if (title.length < 3) {
       swal("최소 3자 이내의 제목을 입력해주세요!");
       return;
       
     } else {
    	 
    	 if (value == "changeForm") {
    		 swalTitle = "글을 수정하시겠습니까?"; 
    	 } else if (value == "writeForm") {
    		 swalTitle = "글을 등록하시겠습니까?"; 
    	 }
    	 
       swal({   
             title: swalTitle,   
             showCancelButton: true,   
             confirmButtonColor: "#DD6B55",   
             confirmButtonText: "Yes",   
             closeOnConfirm: false,
             showLoaderOnConfirm: true,
             },
             function() {   
             setTimeout(function() {
            	 insert_arrayImg();
            	 var form = document.getElementById("form");
            	 form.submit();
             	}, 1000); 
             });   
     }
   };
   
   
/* =========== 첨부파일 삭제  =========== */
   function do_remove_attachFile(value) {
	   var removeFileName = String(value);
	   
	   if (value == null || value == "") {
		   swal("","첨부된 파일이 없습니다.");
	   
	   } else {
	   
        $.ajax({
          type: "POST",
          url : "/ajax/removeAttachFile.do",
          data: {fileValue : removeFileName},
          datatype: "String",
          beforeSend: function(xhr){
		         xhr.setRequestHeader(header, token);
		     },
          success: function(msg) {
            $("#fileLink").html("");
            swal("삭제완료");
          }
        });
	   }
     };
   
     
/* =========== 파일 사이즈 변환  =========== */
     function format_fileSize(value){
          if      (value >= 1048576)    {value=(value/1048576).toFixed(2)+' MB';}
          else if (value >= 1024)       {value=(value/1024).toFixed(2)+' KB';}
          else if (value > 1)           {value=value+' bytes';}
          else if (value == 1)          {value=value+' byte';}
          else                          {value='0 byte';}
          return value;
          
     }
     
     
/* =========== 파일 이름, progress등 초기화 =========== */
     function clear_status() {
         $("#multiFile").val(""); 	//multi탭 초기화
         $("#singleFile").val("");	//single탭 초기화
         $("#upFileText").html(""); //upload한 textarea 초기화.
         $("#disabled_auto").val("320"); //automatic size 초기화
         $("#disabled_menu").val("");	//menual size 초기화
         
         
         $("#progress").progress({
                 text : {
                   active : 'ready'
                 },
                 percent : progress
             });
         }
     
     
 /* =========== img 사이즈 변경: menual input 자리수체크 =========== */     
     function check_maxLangth(object) {
    	 
    	 if (object.value.length > object.maxLength) {
    		  object.value = object.value.slice(0, object.maxLength);
    	}  
     }
     
     
     
     
 $(document).ready(function() {
    	var fileSize;     //파일길이 담을 변수선언.
    	var originName;   //파일원본이름
    	var newName;      //파일변경이름
    	var multiImgList = [];  //img 태그로 담을 변수.
    	var imageSize = 0;
    	
    	/* =========== img 사이즈 변경 종류 선택 =========== */     
        function select_editType() {
          
   		   //radio 체크박스 클래스이름을 가져온다.
   		   var automatic_checkbox = $("#automatic_checkbox").attr("class");
   		   var menual_checkbox = $("#menual_checkbox").attr("class");
   		   
   		   
   		   //checked 이벤트가  붙었는지 확인한다.
   		   if (automatic_checkbox.match(".*checked.*")) {
   			   
   			   $("#menual").removeAttr("checked");      //다른 선택지 초기화
   			   $("#automatic").attr("checked", "checked"); //체크된 input에 checked 옵션부여
   			   
   			   imageSize = $("#disabled_auto").val(); 	//automatic input값을 전역변수에 저장
   			   
   			   console.log("auto체크!");
   			   console.log($("#automatic").val() + " = " + $("#automatic").attr("checked"));
   			   console.log("imageSize 값 = " + imageSize);
   			   
   		   } else if (menual_checkbox.match(".*checked.*")) {
   			   
   			   $("#automatic").removeAttr("checked");      //다른 선택지 초기화
   			   $("#menual").attr("checked", "checked"); //체크된 input에 checked 옵션부여
   			   
   			   imageSize = $("#disabled_menu").val(); 	//menual input값을 전역변수에 저장
   			   
   			   console.log("menu체크!");
   			   console.log($("#menual").val() + " = " + $("#menual").attr("checked"));
   			   console.log("imageSize 값 = " + imageSize);
          
   		   }
   		   
        };
        
    	
        
    	  $("#progress").progress('increment');  //progress 애니메이션
    	    
    	  $('#multi_upload_area .menu .item').tab({  //tab
    			   });
    	  
    	  $("#image_tab").click(function() {  //image_tab이동시 input정보 초기화
    		   clear_status();

    	  });
    	  
    	  $("#file_tab").click(function() {  //file_tab이동시 input정보 초기화
    		   clear_status();

    	  });
    	  
    	  $("#removeFile").click(function() {  //file취소
    		   clear_status();
    	   });
    	  
    	  
    	  $('.ui.radio.checkbox').checkbox();  //checkbox
    	  
    	  
    	  
    	  $("#multiFile").fileupload({
    	        url : "/article/fileUpload?type=multi",
    	        type : "POST",
    	        dataType: "json",
    	        replaceFileInput: false,
    	        beforeSend: function(xhr){
    		         xhr.setRequestHeader(header, token);
    		     },
    	        add: function(e, data) {
    	             var uploadFile = data.files[0];  //넘어온 파일들을 담는다.
    	             var fileName = uploadFile.name;  //input에 들어온 파일이름을 추출.
    	             var isValid = true;
    	             //파일의 확장자명을 알기위해 추출.
    	             var isValidName = fileName.slice(fileName.indexOf(".") + 1).toLowerCase();             
    	             
    	             //업로드한 파일 사이즈를 포멧변환후 body에 append시켜준다.
    	             fileSize = format_fileSize(uploadFile.size);
    	                          
    	               if (uploadFile.size > 104857600) { // 100mb
    	               swal('','파일 용량은 10mb를 초과할 수 없습니다.','warning');
    	               $("#multiFile").val("");
    	               isValid = false;
    	               
    	               } else if (isValidName != "jpg" && isValidName != "jpeg" &&  isValidName != "gif" &&  isValidName != "png") {
    	            	   
    	            	   swal('','jpg, jpeg, png, gif 파일만 등록 가능합니다.','warning');
    	            	   clear_status();
    	            	   isValid = false;
    	            	   
    	               } else {

    	            	   data.submit();
    	              
    	            	   }
    	            
    	        }, success: function(data) {
    	           originName = data.originalName; //업로드된 원본파일명을 불러온다.
    	           newName = data.newName;		   //업로드된 새파일명을 불러온다.
    	          
    	           multiImgList.push(newName);
    	           console.log("each = " + multiImgList);
    	           console.log("each length = " + multiImgList.length);
    	           //업로드한 파일 리스트
    	           $("#upFileText").append("&nbsp; - &nbsp;");
    	           $("#upFileText").append(originName + "&nbsp;&nbsp; : &nbsp;&nbsp;" + "(" + fileSize + ")" + "\r\n");
    	           $("#uploadTabStr").attr("value", "multi");
    	           
    	        }, progressall: function(e, data) {
    	            var progress = parseInt(data.loaded / data.total * 100, 10);
    	            
    	            $("#progress").progress({
    	                text : {
    	                  active  : progress + "%",
    	                  success : 'Success!'
    	                },
    	                
    	                percent : progress
    	            });
    	            
    	        }
    	    });

    	$("#singleFile").fileupload({
    		url : "/article/fileUpload?type=single",
  	        type : "POST",
  	        dataType: "json",
  	        replaceFileInput: false,
  	        beforeSend: function(xhr){
  		         xhr.setRequestHeader(header, token);
  		     },
  	        add: function(e, data) {
  	             var uploadFile = data.files[0];  //넘어온 파일들을 담는다.
  	             var fileName = uploadFile.name;  //input에 들어온 파일이름을 추출.
  	             var isValid = true;
  	             //파일의 확장자명을 알기위해 추출.
  	             var isValidName = fileName.slice(fileName.indexOf(".") + 1).toLowerCase();             
  	             
  	             //업로드한 파일 사이즈를 포멧변환후 body에 append시켜준다.
  	             fileSize = format_fileSize(uploadFile.size);
  	                          
  	               if (uploadFile.size > 104857600) { // 100mb
  	               swal('','파일 용량은 10mb를 초과할 수 없습니다.','warning');
  	               clear_status();
  	               isValid = false;
  	               
  	            	   
  	               } else {

  	            	   data.submit();
  	              
  	            	   }
  	            
  	        }, success: function(data) {
  	           originName = data.originalName;
  	           newName = data.newName;
  	           $("#renameFilePath").attr("value", newName);
  	           $("#originalFilePath").attr("value", originName);
  	          
  	           //업로드한 파일 리스트
  	           $("#upFileText").append("&nbsp; - &nbsp;");
  	           $("#upFileText").append(originName + "&nbsp;&nbsp; : &nbsp;&nbsp;" + "(" + fileSize + ")" + "\r\n");
  	           $("#uploadTabStr").attr("value", "single");
  	           
  	        }, progressall: function(e, data) {
  	            var progress = parseInt(data.loaded / data.total * 100, 10);
  	            
  	            $("#progress").progress({
  	                text : {
  	                  active  : progress + "%",
  	                  success : 'Success!'
  	                },
  	                
  	                percent : progress
  	            });
  	            
  	        }
  	    });
    	
    	
    	$("#uploadSubmit").click(function() {
    		select_editType();  //img 사이즈 변경 종류를 알기위한 함수호출
    		var renameFilePath = $("#renameFilePath").val();
    		var originalFilePath = $("#originalFilePath").val();
    		console.log(renameFilePath);
    		var addSingleFileLink = "<a id='fileLink' href='../files/article/file/" + renameFilePath 
    		+ "'>" + originalFilePath + "</a>";
    		var removeFileBtn = "<input class='ui mini button' type='button' value='삭제' onclick='javascript:do_remove_attachFile(" + 'renameFilePath' + ");'/>";
    		var uploadTabStr = $("#uploadTabStr").attr("value"); //어떤 탭을 선택했는지 str을 가져온다.
    		
    		if (uploadTabStr == "single") {
    			console.log(" = single go = ");
    				$(".file_tr td").html(addSingleFileLink);
    				$(".file_tr td").append("&nbsp;&nbsp;&nbsp;&nbsp;" + removeFileBtn);
    				$("#uploadModal").modal("hide"); 
    		
    				
    		} if (uploadTabStr == "multi") {
    			console.log(" = multi go = ");
    			
    			$.each(multiImgList, function(index, newName) {
    				
    				var sumEl = [];
    				var editWidth = imageSize;
    				
    				sumEl.push("<a href='/files/article/image/" + newName + "'>" 
    							+ "<img alt='' src='/files/article/temps/image/" + newName 
    							+ "' style='width:" + editWidth + "px;' 'height:auto;' /></a>");
    				
    				console.log("elements each = " + sumEl);
    				
    				CKEDITOR.instances.content.insertHtml("" + sumEl + "<br/>");
//    				editor.insertHtml("" + sumEl + "<br/>");
    				
    				arrayImg.push(multiImgList);
    				console.log("multiImgList = " +multiImgList);
    				console.log("arrayImg.push = " + arrayImg);
    				    				
    				$("#uploadModal").modal("hide"); 
     	           
    			});
    			
    			multiImgList = []; //사진 담은 변수 초기화
    			
    		}
    		
    	});
    	
    	
    	$("#uploadCancle").click(function() { //업로드 취소시 multi업로드 파일명 초기화
    		console.log("취소")
    		multiImgList = [];
    		clear_status();
    		$("#uploadModal").modal("hide"); 
    	});
    	  
    	  
 });        
 
     
     