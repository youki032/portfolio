

$(document).ready(function() {
      $('.ui.form')
        .form({
        	inline : true,
          on     : 'blur',
        	fields: {
        	  email: {
              identifier  : 'email',
              rules: [
                {
                  type   : 'empty',
                  prompt : '이메일을 입력해주세요'
                },
                {
                	type   : 'email',
                	prompt : '이메일형식이 아닙니다.'
                }
              ]
            },
            nicName: {
                identifier  : 'nicName',
                rules: [
                  {
                    type   : 'empty',
                    prompt : '닉네임을 입력해주세요.'
                  },
                  {
                	  type   : 'regExp[/^[가-힣a-zA-Z0-9_-]{3,30}$/]',
                	  prompt : '닉네임은 특수기호를 제외한 3~30 영문과 한글로 입력해주세요.'
                  }
                ]
              },
            password: {
                identifier  : 'password',
                rules: [
                  {
                    type   : 'empty',
                    prompt : '비밀번호를 입력해 주세요.'
                  },
                  {
                	  type   : 'regExp[/^[A-Za-z0-9]{6,16}$/]',
                	  prompt : '문자와 숫자포함 6~16 이내의 비밀번호를 입력해주세요.'
                	  
                  }
                ]
              },
                passwordCfm: {
                    identifier  : 'passwordCfm',
                    rules: [
                      {
                        type   : 'match[password]',
                        prompt : '비밀번호가 일치하지않습니다.'
                      }
                    ]
                  },
                  birthDate: {
                      identifier  : 'birthDate',
                      rules: [
                        {
                          type   : 'empty',
                          prompt : '셍년월일을 입력해 주세요.'
                        },
                        {
                          type   : 'regExp[/^[0-9]{4}\-[0-9]{2}\-[0-9]{2}$/]',
                          prompt : '날짜 형식이 올바르지 않습니다. YYYY-MM-DD'
                        }
                      ]
                    } 
        	}
     });
      
  
      $("#birthDate").on("focus", function() {  //onkeyup
    	  $("#birthDate").val("");
      });


 $('#signBtn').on('click', function(){
     var nullCount = 1;    
     
     if(emailCount == 1 & nicCount == 1){
    	 $("#signUpForm").submit();
    	   
     } else if(emailCount == 2 && nicCount == 1){
    	 $("<div style='text-align:center;'>이메일주소 중복!</div>").dialog({
             modal : true,
             resizable : false,
             buttons : [{
               text : "확인",
               click : function() {
                $(this).dialog("close");
               }
             }]
           });
    	   return;
    	   
     } else if(emailCount == 1 && nicCount == 2) {
    	 $("<div style='text-align:center;'>닉네임 중복!</div>").dialog({
             modal : true,
             resizable : false,
             buttons : [{
               text : "확인",
               click : function() {
                $(this).dialog("close");
               }
             }]
           });
         return;
         
     } else if(emailCount == 2 && nicCount == 2) {
    	 $("<div style='text-align:center;'>이메일, 닉네임중복!</div>").dialog({
             modal : true,
             resizable : false,
             buttons : [{
               text : "확인",
               click : function() {
                $(this).dialog("close");
               }
             }]
           });
         return;
         
     } else if(emailCount || nicCount < nullCount) {
       $("<div style='text-align:center;'>이메일, 닉네임 체크를 확인하세요.</div>").dialog({
             modal : true,
             resizable : false,
             buttons : [{
               text : "확인",
               click : function() {
                $(this).dialog("close");
               }
             }]
           });
         return;
         
     } else if(nicCount || emailCount < nullCount) {
       $("<div style='text-align:center;'>이메일, 닉네임 체크를 확인하세요.</div>").dialog({
             modal : true,
             resizable : false,
             buttons : [{
               text : "확인",
               click : function() {
                $(this).dialog("close");
               }
             }]
           });
         return;
         
     }
    });

  $("#emailCfm").on('click', check_Email);
  $("#nicNameCfm").on('click', check_nicName);
  $("#resizing_emailCfm").on('click', check_Email);
  $("#resizing_nicNameCfm").on('click', check_nicName);
 
  
  function check_nicName() {

	    $.ajax({
	     url : "/ajax/checkNicName.do",
	     type : "POST",
	     data : {nicName : $("#nicName").val()},
	     dataType : "json",
	     beforeSend: function(xhr){
	         xhr.setRequestHeader(header, token);
	     },
	    
	     success : function(data){     
	      $("<div style='text-align:center;'>"+data.resultMessage+"</div>").dialog({
	       modal : true,
	       resizable : false,
	       buttons : [{
	         text : "확인",
	         click : function() {
	          $(this).dialog("close");
	         }
	       }]
	     });
	      $(".ui-dialog-titlebar").hide();
	     
	      if ( data.result == "success") {
	        nicCount = 1;
	        
	     } else if (data.result == "failure") {
	       nicCount = 2;
	     }
	    }
	  })
	}
  
  
  function check_Email() {
	  
	  $.ajax({
	     url : "/ajax/emailCheck.do",
	     type : "POST",
	     data : {email : $("#email").val()},
	     dataType : "json",
	     beforeSend: function(xhr){
	         xhr.setRequestHeader(header, token);
	     },
       headers: {
        'X-CSRF-Token': $('meta[name="token"]').attr('content')
       },
	     
	     success : function(data){        
	      $("<div style='text-align:center;'>"+data.resultMessage+"</div>").dialog({
	       modal : true,
	       resizable : false,
	       buttons : [{
	         text : "확인",
	         click : function() {
	          $(this).dialog("close");
	         }
	       }]
	      });
	      $(".ui-dialog-titlebar").hide();
	      
	      if ( data.result == "success") {
	        emailCount = 1;
	      } else if (data.result == "failure") {
	        emailCount = 2;
	      }

	     }    
	   })
	 }

}); 
  
  
//생년월일 - 자동생성
  function date_hyphen(formd, textid) {
    /*
    input onkeyup에서
    formd == this.form.name
    textid == this.name
    */
    
    var form = eval("document."+formd);
    var text = eval("form."+textid);
    var textlength = text.value.length;
    if (textlength == 4) {
    text.value = text.value + "-";
    } else if (textlength == 7) {
    text.value = text.value + "-";
    } else if (textlength > 9) {
    //날짜 수동 입력 Validation 체크
    var chk_date = checkdate(text);
      if (chk_date == false) {
      
        return;
      }
    
    } else if (focus()) {
      
    }
  }
     
     
  //생년월일 유효성검사   
  function checkdate(input) {
       var validformat = /^\d{4}\-\d{2}\-\d{2}$/; //Basic check for format validity 
       var returnval = false;
       if (!validformat.test(input.value)) {
        swal({
            title:"",
            text: "날짜 형식이 올바르지 않습니다. YYYY-MM-DD",
            type: "warning"
            });  
        input.clear();
       } else { //Detailed check for valid date ranges 
        var yearfield = input.value.split("-")[0];
        var monthfield = input.value.split("-")[1];
        var dayfield = input.value.split("-")[2];
        var dayobj = new Date(yearfield, monthfield - 1, dayfield);
       }
       if ((dayobj.getMonth() + 1 != monthfield)
         || (dayobj.getDate() != dayfield)
         || (dayobj.getFullYear() != yearfield)) {
    	   swal({
               title:"",
               text: "날짜 형식이 올바르지 않습니다. YYYY-MM-DD",
               type: "warning"
               }); 
         input.clear.value();

       } else {
        returnval = true;
       }
       if (returnval == false) {
        input.select();
       }
       return returnval;
     }