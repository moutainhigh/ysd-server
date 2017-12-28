<!DOCTYPE html>
<html>
  <head>
    <title>乐商贷-—国资控股|专业、安全、透明的互联网金融服务平台-系统信息</title>
	<#include "/content/common/meta.ftl">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" type="text/css" href="${base}/static/css/reset.css" />
   <script src="${base}/static/js/jquery.min.js"></script>
	<script src="${base}/static/js/base.js"></script>
	<script src="${base}/static/base/js/base.js"></script>
	<script src="${base}/static/js/common/qmd.js"></script>	
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
 <input type='hidden' id='init_value' value="3" />
  <div class="zhuce05">
        <header>
            <img src="${base}/static/img/moblie/zc01.png">
        </header>
        <!--header--结束-->
        <section>
          <div class="tuijian">
                    <span></span>
                    <p class="p1">
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
                    </p>
                    <p class="p2"><a href="javascript:void(0);" onclick="redirectUrl();">系统将3秒后跳转</a></p>
            </div>
            <!--<div class="phone">
                <a class="android" href="#"><span></span><p>Android</p></a>
                <a class="ios" href="#"><span></span><p>IOS</p></a>
            </div>-->
           
        </section>
        <!--section--结束-->
<!--        <footer>
            <img src="${base}/static/img/moblie/zc03.png">
        </footer>-->
        <!--footer--结束-->
    </div>
</body>
</html>