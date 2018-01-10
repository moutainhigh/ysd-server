<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-充值结果信息</title>
	<#include "/content/common/meta.ftl">
	
	<link rel="stylesheet" href="${base}/css/common.css">
<script src="${base}/js/jquery-1.11.3.min.js"></script>
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
<style>
body{ margin:0 ; padding:0;font-family:"微软雅黑";}
ul,li{ list-style:none; margin:0 ; padding:0;}
#header { background: #fff;}
</style>

</head>
<body>
<#include "/content/common/header.ftl">

	<link rel="stylesheet" href="${base}/css/payment.css">
<input type='hidden' id='init_value' value="3" />
  <div class="find operation">
  <div class='operationCont findCont'>
      <!--<div class="top">
          <p><span>系统通知</span></p>
      </div>
      <div style="margin:85px auto 0;font-size:16px;">
         <div style="color:#666;text-align:center;">
            <span style="width:34px;height:34px;display:inline-block;line-height:34px;text-align:center;color:#fff;font-weight:bold;border-radius:50%;background:#fd7c1a;margin-right:8px;font-size:24px;">!</span>
           <#if (actionMessages?? && actionMessages?size > 0)>
     		<#list actionMessages as actionMessage>${actionMessage}&nbsp;</#list>
     	<#else>
     		您的操作已成功!
     	</#if>
     		系统将<span id="showSecond">3</span>秒后跳转...
          </div>
         <div style="text-align:center;margin-top:130px;">
           <a href="javascript:void(0);" onclick="redirectUrl();" style="width:108px;height:36px;border-radius:5px;line-height:36px;background-color:#fd7c1a;color:#fff;display:inline-block;text-align:center;cursor:pointer;">请点击这里</a>
         </div>
      </div>-->
      <p class='title'>
      	<!--<#if (actionMessages?? && actionMessages?size > 0)>
     		<#list actionMessages as actionMessage>${actionMessage}&nbsp;</#list>
     	<#else>-->
     		您的操作已成功!
     	<!--</#if>-->
      </p>
      <p>系统将<span id="showSecond">3</span>秒后跳转...</p>
      <div class='qybtn'>
      	<a href="javascript:void(0);" onclick="redirectUrl();" class='red'>点击这里</a>
      </div>
  </div>
</div><!-- end -->
<#include "/content/common/footer.ftl">

</body>
</html>
