

$(document).ready(function(){

  $('#userByePassword').on('click',function(){

       $.ajax({
         url : "/ajax/byePassword.do",
         type : "POST",
         data : {password : $("#password").val()},
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
                 
                 if ( data.result == "success") {
                     nicCount = 1;
                 
                 $("userByeFrm").ready(function() {
                    	 
                  var f = document.getElementById("userByeFrm");
                  f.target = "parentUserEdit";
                  f.submit();
                  self.close();
                  
                  window.onunload = function() {
                    
                  opener.window.close();
                  opener.opener.window.location.href = "/home/main";
                  }
              })
                 
                 
                 } else if (data.result == "failure") {
                     nicCount = 2;
                  }

                 }
              }]
           });
           
           $(".ui-dialog-titlebar").hide();
  
          }
      })
  });

});


function sendChangeNicName() {
  if (nicCount == 1) {
  
  var val1 = document.getElementById("changeNicName").value;
  window.opener.document.getElementById("newNicname").value = val1;
  self.close();
  
  } alert("이미 사용중인 닉네임이거나 닉네임중복 체크를 확인해주세요.");
  
  
}