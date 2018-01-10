<!DOCTYPE html>
<html>
  <head>
    <title>${Application ["qmd.setting.name"]} 系统信息</title>
	
    <#include "/content/common/meta.ftl">
    
	<script>  
		function auto_jump() {  
		    secs = $("#init_value").val() - 1;  
		    $("#init_value").val(secs);  
		        if(secs < 0){return false;}  
		    if(secs == 0){  
		        clearInterval(time);  
		        window.location.href='${base}/userCenter/index.do';  
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
<#include "/content/common/header.ftl">

<input type='hidden' id='init_value' value="3" />
<!-- content -->
    <div style="padding:100px 50px 150px 50px; background:#efebdf; width:800px; margin:0 auto; text-align:center;">
      <div style="color:#595757; font-size:27px; padding:50px 0px 0 0;">您目前账户类型不是借款者，请进入个人中心申请。系统将<span id="showSecond">3</span>秒后跳转...</div>
      <div><a href="${base}${msgUrl}" style="color:#f74405;font-family:'宋体'; margin-top:20px;">进入用户中心</a></div>
  </div>
<#include "/content/common/footer.ftl">

</body>
</html>
