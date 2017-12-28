<!DOCTYPE html>
<html>
  <head>
    <title>${Application ["qmd.setting.name"]} 系统信息</title>
	
    <#include "/content/common/meta.ftl">
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
			<#assign flag=true>
			<#if (errorMessages?? && errorMessages?size gt 0)>
				<#list errorMessages as actionMessage>${actionMessage}&nbsp;</#list>
			<#elseif fieldErrors?? && fieldErrors?size gt 0>
				<#list fieldErrors.keySet() as fieldErrorKey>
					<#list fieldErrors[fieldErrorKey] as fieldErrorValue>
										${fieldErrorValue}&nbsp;
					</#list>
				</#list>
			<#else>
				<#assign flag=false>
								页面不存在或者出现系统错误！
			</#if>
          </div>
         <div style="text-align:center;margin-top:130px;">
         <#if flag>
           <a  href="javascript:void(0);" style="width:108px;height:36px;border-radius:5px;line-height:36px;background-color:#f3485a;color:#fff;display:inline-block;text-align:center;cursor:pointer;">系统将<span id="showSecond">3</span>秒后跳转...</a>
         <#else>
           <a href="${base}/userCenter/index.do"style="width:108px;height:36px;border-radius:5px;line-height:36px;background-color:#f3485a;color:#fff;display:inline-block;text-align:center;cursor:pointer;">首页</a>
         </#if>
         </div>
      </div>
  </div>
</div><!-- 找回密码 end -->
<#include "/content/common/footer.ftl">

</body>
</html>