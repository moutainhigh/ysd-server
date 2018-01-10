


qmd = {
	base: "https://www.yueshanggroup.cn/fws",
	img:"https://www.yueshanggroup.cn/fws/img"
};


/*----------------------top.ftl Header------------------*/
$().ready( function() { 

	$(":text").focusout(function() {
	  $(this).removeClass("txt_focus");
	});
	$(":text").focusin(function() {
	  $(this).addClass("txt_focus");
	});
	
var $header=$("#header");
  if($header.size() > 0 ){ 
	  var $headerLoginUsername = $("#headerLoginUsername");
	  var $headerLoginOff = $("#headerLoginOff");
	  var $headerLoginOn = $("#headerLoginOn");
	  var $headerLoginCenter = $("#headerLoginCenter");
	  var $headerLoginJiHuo = $("#headerLoginJiHuo");
	  $.flushHeaderInfo = function(){
		  if(getCookie("cookieUsername") != null){
			  $headerLoginUsername.html(""+getCookie("cookieUsername")+"");
			  $headerLoginOff.hide();
			  $headerLoginOn.show();
		  } else{
			  $headerLoginUsername.html("");
			  $headerLoginOff.show();
			  $headerLoginOn.hide();
			
		  }
		 
		  if(getCookie("cookieIsJiHuo") != null && getCookie("cookieIsJiHuo")!='' ){
			  $headerLoginCenter.show();
			  $headerLoginJiHuo.hide();
		  }else{
			  $headerLoginCenter.hide();
			  $headerLoginJiHuo.show();
		  }
		  
	  };
	  
	  $.flushHeaderInfo();
  }
  
  
  var $listForm = $("#listForm");// 列表表单
  if ($listForm.size() > 0) {
	  var $searchButton = $("#searchButton");// 查找按钮
	  var $pageNumber = $("#pageNumber");// 当前页码
	  var $pageSize = $("#pageSize");// 每页显示数
	  var $minDate = $("#minDate");
	  var $maxDate = $("#maxDate");
		// 查找
		$searchButton.click( function() {
			if (Date.parse(TimeFormatToSQL($minDate.val()).replace("-", "/")) > Date.parse(TimeFormatToSQL($maxDate.val()).replace("-", "/"))) { 
				$.dialog({type: "warn", content: "开始时间不能大于结束时间!", ok: "确 定",  modal: true});
				return false;
			}	
			$pageNumber.val("1");
			$listForm.submit();
		});
  }
  
});
      
//设置Cookie
function setCookie(name, value) {
	var expires = (arguments.length > 2) ? arguments[2] : null;
	document.cookie = name + "=" + encodeURIComponent(value) + ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + ";path=" + qmd.base;
}

// 获取Cookie
function getCookie(name) {
	var value = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (value != null) {
		return decodeURIComponent(value[2]);
    } else {
		return null;
	}
}

// 删除cookie
function removeCookie(name) {
	var expires = new Date();
	expires.setTime(expires.getTime() - 1000 * 60);
	setCookie(name, "", expires);
}

function TimeFormatToSQL(strTime) { 
	var strResult = ""; 
	var strTemp = ""; 
	for (var i = 0; i < strTime.length; i++) { 
	strTemp = strTime.substr(i, 1); 
	if (strTemp == "年" || strTemp == "月") 
	strResult += "-"; 
	else 
	if (strTemp == "日" || strTemp == "秒") { 
	if (strTemp == "日") 
	strResult += "|"; 
	else 
	strResult += ""; 
	} 
	else 
	if (strTemp == "时" || strTemp == "分") 
	strResult += ":"; 
	else 
	strResult += strTemp; 
	} 
	var strArgDateTime = strResult.split('|'); //此时的时间格式可能为2010-11-11 11: 或2010-11-11 11等格式 
	if (strArgDateTime.length == 1) { 
	//日期部分进行处理 
	var strArgDate = strArgDateTime[0].split('-'); //此时对时间部分进行处理,可能为11: 11 或11:00等格式 
	if (strArgDate.length == 2) { 
	if (strArgDate[1].length < 1) 
	strResult = strArgDate[0]; 
	else 
	strResult = strArgDateTime[0] + "-01"; 
	} else 
	if (strArgDate.length == 3) { 
	if (strArgDate[2].length < 1) 
	strResult = strArgDate[0] + "-" + strArgDate[1] + "-01"; 
	} 
	} 
	else 
	if (strArgDateTime.length == 2) { 
	//时间部分进行处理 
	var strArgTime = strArgDateTime[1].split(':'); //此时对时间部分进行处理,可能为11: 11 或11:00等格式 
	if (strArgTime.length == 1) { 
	strResult = strArgDateTime[0] + " " + strArgDateTime[1] + ":00:00" 
	} else 
	if (strArgTime.length == 2) { 
	if (strArgTime[1].length < 1) 
	strResult = strArgDateTime[0] + " " + strArgDateTime[1] + "00" 
	else 
	strResult = strArgDateTime[0] + " " + strArgDateTime[1] + ":00" 
	} else 
	if (strArgTime.length == 3) { 
	if (strArgTime[2].length < 1) 
	strResult = strArgDateTime[0] + " " + strArgDateTime[1] + "00" 
	} 
	} 
	return strResult; 
	} 