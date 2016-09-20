

$(document)
    .ready(function() {
      $('.ui.form')
        .form({
          inline : true,
          on     : 'blur',
          fields: {
            Email: {
              identifier  : 'email',
              rules: [
                {
                  type   : 'empty',
                  prompt : '이메일을 입력해 주세요.'
                },
                {
                    type   : 'email',
                    prompt : '이메일형식이 아닙니다.'
                }
              ]
            },
            BirthDate: {
              identifier  : 'birthDate',
              rules: [
                {
                  type   : 'empty',
                  prompt : '생년월일을 입력해 주세요.'
                },
              ]
            },
            UserName: {
                identifier  : 'userName',
                rules: [
                  {
                    type   : 'empty',
                    prompt : '성함을 입력해 주세요.'
                  },
                ]
              }  
            
          }
        });
      
      
    $('.message .close')
      .on('click', function() {
        $(this)
          .closest('.message')
          .transition('fade')
        ;
      })
    ;
      
      
    $('.ui.accordion')
    .accordion({
    	exclusive: true,
    	animateChildren: true
    });
   
    
    $("#birthDate").on("focus", function() {  //onkeyup
        $("#birthDate").val("");
      });
    
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
        //alert ('Correct date'); 
        returnval = true;
       }
       if (returnval == false) {
        input.select();
       }
       return returnval;
     }