



$(document).ready(function(){

	$('#checkBtn').on('click',function(){
		  
		var validformat = /[~!@\#$%^&*\()\-=+_']/gi;  
		  var checkInput = $("#changeNicName").val();
		  
		  if(validformat.test(checkInput)) {  //특수문자가 포함되면 삭제하여 값으로 다시셋팅
			  swal("특수기호 사용x");
			  $("#changeNicName").val("");
		  			  
		  } else {
			 
		
		   $.ajax({
			   url : "/ajax/checkNicName.do",
			   type : "POST",
			   data : {nicName : $("#changeNicName").val()},
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

			});
			
		  }
	});
	
	
	$("#submitBtn").click(function() {
		sendChangeNicName();
	});
	
	
});


function sendChangeNicName() {
	if (nicCount == 1) {
	
	var val1 = document.getElementById("changeNicName").value;
	window.opener.document.getElementById("newNicName").value = val1;
	self.close();
	
	} alert("이미 사용중인 닉네임이거나 닉네임중복 체크를 확인해주세요.");
	
	
}
