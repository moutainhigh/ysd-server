<!DOCTYPE html>
<html>
  <head>
    <title>${Application ["qmd.setting.name"]} 系统信息</title>
	
    <#include "/content/common/meta.ftl">
<style>
.registerform{
	padding: 1em 1.5em;
	background: #FFF;
	border: 1px solid #e1e1e1;
-webkit-box-shadow: 0 2px 6px 0 #CCC;
	box-shadow: 0 2px 6px 0 #CCC;
}
.registerform h1{
	border-bottom: 2px solid #dcdcdc;
	padding: 0 0 .5em 0;
	font-size: 22px;
	font-weight: normal;
	margin: 0 0 15px 0;
}
.registerform .row {
	display: table;
	width: 100%;
	clear: both;
	padding: 6px 0;
	position: relative;
}
.registerform label {
	float: none;
	display: block;
	width: auto;
	font-size: 1.1em;
	padding: 0 .3em .3em .2em;
	text-align: left;
	color: #555;
	font-weight: bold;
	font-size:14px;
}
.registerform input.text.user-icon-bg {
	background: #FFF url(images/user-icon.png) no-repeat 3px center;
	padding:7px 7px 7px 28px;
	width: 204px;
	border: 1px solid #CCC;
	-webkit-border-radius: 4px;
	border-radius: 4px;
}
.registerform input.text.lock-icon-bg {
	background: #FFF url(images/lock-icon.png) no-repeat 4px center;
	padding:7px 7px 7px 28px;
	width: 204px;
	border: 1px solid #CCC;
	-webkit-border-radius: 4px;
	border-radius: 4px;
}
.registerform .forgot-password {
	display: inline-block;
	padding: .3em .4em;
	text-align: left;
	font-size: 14px;
	color: #36abf6;
}
.registerform .controls {
	text-align: right;
	padding-top: .4em;
}
.registerform .remember-me {
	float: right;
	display: block;
	font-weight: normal;
	font-size: 14px;
	text-align: right;
	padding: .4em 0;
}
.registerform .not-a-member {
	padding: 30px 0 0 0;
	text-align: left;
	color: #555;
	font-size: 14px;
}
</style>
		<script>  
		
		function redirectUrl() {
			<#if redirectUrl??>
				window.location.href = "${redirectUrl}"
			<#else>
				window.history.back();
			</#if>
		}
		function auto_jump() {  
		    secs = $("#init_value").val() - 1;  
		    $("#init_value").val(secs);  
		        if(secs < 0){return false;}  
		    if(secs == 0){  
		        clearInterval(time);  
		        redirectUrl();
		    }else{
		        $("#showSecond").html(secs);  
		    }
		}
		$(function(){  
		    time = setInterval('auto_jump()',1000);  
		})
</script> 
  </head>
  
  <body>
 <!-- header -->
	<#include "/content/common/user_center_header.ftl">

<input type='hidden' id='init_value' value="3" />

<div class="find">
  <div style="width:1200px;height:530px; margin:30px auto 90px;background:#fff;">
      <div class="top">
          <p><span>系统通知</span></p>
      </div>
      <div style="margin:85px auto 0;font-size:16px;">
         <div style="color:#666;text-align:center;">
            <span style="width:34px;height:34px;display:inline-block;line-height:34px;text-align:center;color:#fff;font-weight:bold;border-radius:50%;background:#f3485a;margin-right:8px;font-size:24px;">!</span>
			<#if (errorMessages?? && errorMessages?size gt 0)>
         				<#list errorMessages as actionMessage>
         					<#if actionMessage?index_of('the request was rejected because its size') != -1 >
         						上传文件大小超出限制，最多500K
         					<#else>
         						${actionMessage}
         					</#if>&nbsp;
         				</#list>
         			<#elseif fieldErrors?? && fieldErrors?size gt 0>
         				<#list fieldErrors.keySet() as fieldErrorKey>
							<#list fieldErrors[fieldErrorKey] as fieldErrorValue>
								${fieldErrorValue}&nbsp;
							</#list>
						</#list>
	          		<#else>
	          			您的操作已成功!
	          		</#if>
         				系统将<span id="showSecond">3</span>秒后跳转...
          </div>
         <div style="text-align:center;margin-top:130px;">
           <a <#if msgUrl != ''>href="${msgUrl}" <#else>onclick="javascript:history.back();"</#if> style="width:108px;height:36px;border-radius:5px;line-height:36px;background-color:#f3485a;color:#fff;display:inline-block;text-align:center;cursor:pointer;">请点击这里</a>
         </div>
      </div>
  </div>
</div><!-- 找回密码 end -->
<#include "/content/common/footer.ftl">

</body>
</html>