


qmd = {
	base:"http://www.web.com",
	img:"http://www.web.com/images"
};

var name="cookieUsername";//Cookies名字
this.GetCookie = function(name) {
	  var ck = document.cookie;
	  var exp1 = new RegExp(name + "=.*?(?=;|$)");
	  var mch = ck.match(exp1);
	  return mch ? unescape(mch[0].substring(name.length + 1)) : null;
}


function delCookie(){//为cookie name 

   var date = new Date(); 

   date.setTime(date.getTime() - 10000); 

   document.cookie = name + "=a; expires=" + date.toGMTString(); 
   $.ajax({
		
		 type: "GET",
		 url:'/rest/logout',
		 dataType: "json",
		 success: function(bean) {
		 	if(bean.rcd=='R0001') {
		 		window.location ='index.html';
		 	
		 	} 
		 }
	});
} 

/*----------------------top.ftl Header------------------*/
$().ready( function() { 

	$(":text").focusout(function() {
	  $(this).removeClass("txt_focus");
	});
	$(":text").focusin(function() {
	  $(this).addClass("txt_focus");
	});

	if(GetCookie('cookieUsertoken') !=''){
		$(this).find("[name='token']").val(GetCookie('cookieUsertoken'));
	}
	//返回
	$('.am-header-left').click(function(){
		history.back(-1);return false;
	});
/*
var $header=$("#header");
  if($header.size() > 0 ){ 
	  var $headerLoginUsername = $("#headerLoginUsername");
	  var $headerShowLoginWindow = $("#headerShowLoginWindow");
	  var $headerShowRegisterWindow = $("#headerShowRegisterWindow");
	  var $headerUserCenter = $("#headerUserCenter");
	  var $headerLogout = $("#headerLogout");
	  var $headerRecharge = $("#rechargeHeader");
	  var $headerNihao = $("#headerNihao");
	  var $rechargeHeader = $("#rechargeHeader");
	  
	  var $typeId0=$("#typeId0");
	  var $typeId1Realstatus1 = $("#typeId1Realstatus1");
	  var $typeId1Realstatus0 = $("#typeId1Realstatus0");

	  var $span_haslogin=$(".span_haslogin");
	  var $span_nologin=$(".span_nologin");
	  
	  $.flushHeaderInfo = function(){
		  if(getCookie("cookieUsername") != null){
			  $headerLoginUsername.html("您好：<a href='"+qmd.base+"/userCenter/index.do'>"+getCookie("cookieUsername")+"</a>");
			  $headerUserCenter.show();
			  $headerLogout.show();
			  $headerShowLoginWindow.hide();
			  $rechargeHeader.hide();
			  $headerShowRegisterWindow.hide();
			  $headerRecharge.show();
			  $span_haslogin.show();
			  $span_nologin.hide();
			  if(getCookie("userTypeId")!= null ){
				  if(getCookie("userTypeId")=='0'){
					  $typeId0.show();
					  $typeId1Realstatus1.hide();
					  $typeId1Realstatus0.hide();
				  }else if(getCookie("userTypeId")=='1'){
					  $typeId0.hide();
					  if(getCookie("typeId1Realstatus")!= null){
						  if(getCookie("typeId1Realstatus")=='1'){
							  $typeId1Realstatus1.show();
							  $typeId1Realstatus0.hide();
						  }else if(getCookie("typeId1Realstatus")=='2'){
							  $typeId1Realstatus0.show();
							  $typeId1Realstatus1.hide();
						  }else if(getCookie("typeId1Realstatus")=='0'){
							  $typeId1Realstatus0.show();
							  $typeId1Realstatus1.hide();
						  }
					  }
				  }
			  }
			  
		  } else{
			  $headerLoginUsername.html("");
			  $headerShowLoginWindow.show();
			  $headerShowRegisterWindow.show();
			  $rechargeHeader.show();
			  $headerUserCenter.hide();
			  $headerLogout.hide();
			  $headerRecharge.hide();
			  $headerNihao.hide();
			  $span_haslogin.hide();
			  $span_nologin.show();
		  }
		  
	  };
	  
	  $.flushHeaderInfo();
  }
*/
  var $header=$("#header");
  if($header.size() > 0 ){ 
	  var $header_top_login_out = $("#header_top_login_out");
	  var $header_menu_login_out = $("#header_menu_login_out");
	  var $header_top_login_in = $("#header_top_login_in");
	  var $header_menu_login_in = $("#header_menu_login_in");
	  var $header_top_login_in_username = $("#header_top_login_in_username");
	  
	  $.flushHeaderInfo = function(){
		  if(GetCookie("cookieUsername") != null){
			  $header_top_login_in_username.html(GetCookie("cookieUsername"));
			  $header_top_login_out.hide();
			  $header_menu_login_out.hide();
			  $header_top_login_in.show();
			  $header_menu_login_in.show();
		  } else{
			  /*
			  $header_top_login_out.show();
			  $header_menu_login_out.show();
			  $header_top_login_in.hide();
			  $header_menu_login_in.hide();
			  */
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