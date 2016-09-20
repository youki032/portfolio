



$(document).ready(function() {
	   
	 //패스워드 유효성검사  
	 $('.ui.form')
    .form({
      inline : true,
      on     : 'blur',
      fields: {
    	  newPassword: {
            identifier  : 'newPassword',
            rules: [
              {
            	  type   : 'regExp[/^[A-Za-z0-9]{6,16}$/]',
                  prompt : '문자와 숫자포함 6~16 이내의 비밀번호를 입력해주세요.'
                
              }
            ]
          },
            
          newPasswordCfm: {
                identifier  : 'newPasswordCfm',
                rules: [
                  {
                    type   : 'match[newPassword]',
                    prompt : '비밀번호가 일치하지않습니다.'
                  }
                ]
              }
      }
    });
	
});



function do_vaildChangeNicPopUp() {	 //실제 닉네임변경 버튼실행할때 
	  
	  $.ajax({
	    type :   "POST",
	    url  :   "/ajax/changeNicPopUp.do",
	    data :   {checkNicDate : loadNicDate},
	    datatype :"json",
	    beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
      },
	    success  : function(data){
	    	
	      if(data.result == "success") {
	    	    var url = "/userinfo/checkPopUp";
	          var specs = "width=468, height=200, left=250, top=250";
	          winObject = window.open(url,"checkPopUp", specs);
	    	  
	    	  return true;
	        
	      } else if(data.result == "failure"){
	        swal(data.resultMessage);
	        jQuery("#nicDisable").html(data.resultMessage);
	        
	        return false;  
	      }
	     }
	  });
	 }


//닉네임변경 팝업
function do_vaildChangeNic() { //회원정보 수정창 열었을대 onload로 닉네임 변경가능한지 text보여주기용.
	
	$.ajax({
	      type :   "POST",
	      url  :   "/ajax/changeNicPopUp.do",
	      data :   {checkNicDate : loadNicDate},
	      datatype :"json",
	      beforeSend: function(xhr){
	            xhr.setRequestHeader(header, token);
	      },
	      success  : function(data){
	        
	        if(data.result == "success") {
	        	jQuery("#nicDisable").html("닉네임 변경이 가능합니다.");
	          
	          return true;
	          
	        } else if(data.result == "failure"){
	          jQuery("#nicDisable").html(data.resultMessage);
	          
	          return false;  
	        }
	       }
	    });
	  }


//회원탈퇴 팝업
function do_userBye() {
	  var url = "/userinfo/userBye";
	  var specs = "width=468, height=300, left=250, top=250";
	  winObject = window.open(url,"userBye", specs);
	    
	}


//우편번호 팝업	
function postSearch() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = ''; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    fullAddr = data.roadAddress;

                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    fullAddr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                if(data.userSelectedType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('address1').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('address2').value = fullAddr;

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('address3').focus();
            }
        }).open();
    }

	 

//textarea함수
var limit_length = 250;
var msg_length = 0;

//String에 bytes() 함수 만들기
String.prototype.bytes = function() {
  var msg = this;
  var cnt = 0;
  
//한글이면 2, 아니면 1 count 증가
for (var i = 0; i < msg.length; i++)
  cnt += (msg.charCodeAt(i) > 128) ? 2 : 1;
return cnt;
}


$(document).ready(function() {

//textarea에서 키를 입력할 때 마다 동작
$("#selfIntroduction").keyup(function(e){
  msg_length = $(this).val().bytes();
  
  if (msg_length <= limit_length) {
    $("#type_num").css("color", "#646464");
    $("#type_num").html(msg_length);
  
  } else {
    $("#type_num").css("color", "#E55451");
    $("#type_num").html(msg_length);
    swal({
        title:"",
        text: "입력수 초과! 입력 가능한 최대 길이는 " + limit_length + "입니다.",
        type: "warning"
        }); 
     return;
  }
  
  });

});

// /^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$/;
function do_submit() {
  var valid_phone = /^[0-9]{0,11}$/;
  var phone = document.getElementById("phone").value;
  
  if (msg_length > limit_length) {
    swal({
          title:"",
          text: "자기소개 최대 입력수를 초과하였습니다. 확인후 재시도 해주세요.",
          type: "warning"
          }); 
    
    return;
  
  } else if (!valid_phone.test(phone)) {  
	  swal({
	        title:"",
	        text: "전화번호 입력오류! 유효한 전화번호를 입력해 주세요.",
	        type: "warning"
	        });     
    return;
    
  } else {
    
    swal({   
        title:"",
        text:"회원정보를 변경하였습니다 !",
        type: "info",
        confirmButtonText: "Yes",   
        closeOnConfirm: false,
        showLoaderOnConfirm: true,
        },
        
        function() {  
        	  var form = document.getElementById("editFrm");
            
            form.target = "parentMyPage";
            form.submit(); 
            self.close();
          }); 
      }
  }


function do_cancel() {

	swal({   
      title:"",
      text:"회원정보 수정창을 닫으시겠습니까?",
      type: "info",
      confirmButtonText: "Yes",   
      showCancelButton: true,   
      closeOnConfirm: false,
      showLoaderOnConfirm: true,
      },
      
      function() {   
    	   window.close();
      }); 
  
}