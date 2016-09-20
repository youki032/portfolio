
$(document).ready(function () {
	  
	  var fomatText = String(loadUserLevel); //integer형태의 숫자값을 String으로 형변환.
	  var levelIcon = "<img id='levelIcon' src='/resources/trashbox/icons/level/" + fomatText + ".gif'>";
	  $("#level_icon").html(levelIcon);
	  
	  $('.ui.progress')
      .progress({
        value : loadNowExp,
        total    : loadNextExp
      });
	  
	});

