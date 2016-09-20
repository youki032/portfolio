



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
    	  title:"로그인 후 이용하실 수 있습니다.",
    	  type: "warning"
    	  },
    	  function() {
    		  location.href = "/home/login";
    	  });
    }
    
    function search_btn() {
    	var searchFrm = document.getElementById("searchFrm");
    	searchFrm.submit();
    }





$(function() { //모바일 화면에서 검색블록 사이즈 조정
    
    if ($(window).width() < 450) {
      
      $("#simple_buttons").attr("class", "ui mini simple buttons");
      $("#searchInput").attr("class", "ui icon mini input");
      
    } 
});


$(document).ready(function() {
	  $('#select').dropdown({
	      transition:'scale',
	      direction: 'upward',
	      action:'combo'
	    });
	  
	   
	  $('.ui.dropdown').dropdown({
	      transition:'scale',
	      action:'select'
	    });
	  
	  
	  $('.ui.search').search({
	      minCharacters: 2,
	      type: 'button'
	    });
	 
	});


