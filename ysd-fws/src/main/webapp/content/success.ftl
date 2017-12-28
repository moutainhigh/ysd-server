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
			 <#if (actionMessages?? && actionMessages?size > 0)>
	             		<#list actionMessages as actionMessage>${actionMessage}&nbsp;</#list>
	             	<#else>您的操作已成功!
	             	</#if>系统将<span id="showSecond">3</span>秒后跳转...<br/><br/><br/>
          </div>
         <div style="text-align:center;margin-top:130px;">
           <a href="javascript:void(0);" onclick="redirectUrl();" style="width:108px;height:36px;border-radius:5px;line-height:36px;background-color:#f3485a;color:#fff;display:inline-block;text-align:center;cursor:pointer;">返回</a>
         </div>
      </div>
  </div>
</div><!-- 找回密码 end -->
<#include "/content/common/footer.ftl">

</body>
</html>
