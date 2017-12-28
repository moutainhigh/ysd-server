<!DOCTYPE html>
<html>
  <head>
    <title>${Application ["qmd.setting.name"]} 系统信息</title>
	
    <meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="application-name" content="${name}" />
    <meta name="msapplication-tooltip" content="${name}" />
	<meta name="keywords" content="${metaKeywords}" />
	<meta name="description" content="${metaDescription}" />
	<meta name="robots" content="all" />
	<link rel="shortcut icon" href="${base}/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="${base}/static/base/css/base.css" type="text/css" />
	<link rel="stylesheet" href="${base}/static/css/style.css" />
	<link rel="stylesheet" href="${base}/static/css/kefu.css" />
	
	<script src="${base}/static/js/jquery.min.js"></script>
	<script src="${base}/static/js/base.js"></script>
	<script src="${base}/static/base/js/base.js"></script>
	<script src="${base}/static/js/common/qmd.js"></script>	
	<script src="${base}/static/js/kefu.js"></script>
    <script>  
		
		function redirectUrl() {
				window.history.back();
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
<#include "/content/common/header.ftl">
<input type='hidden' id='init_value' value="3" />


 <div style=" width:960px; margin:20px auto; background:#fff;">
     <div style="background:#ede3cb; height:40px; line-height:40px; text-indent:20px; color:#000; font-size:16px;">系统消息</div>
     <div style=" width:800px; height:350px; border:1px solid #ccc; margin:20px auto;">
       <table width="450" style=" margin:140px auto">
         <tr>
           <td rowspan="3" width="58"><img src="/static/img/buchenggong.png" /></td>
         </tr>
         <tr><td style="color:#646464; font-size:27px;">
         	
								页面不存在或者出现系统错误！

		</td></tr>
		<#if flag>
         	<tr><td><a href="javascript:void(0);" style="font-size:14px; color:#F00; padding-left:5px;">系统将<span id="showSecond">3</span>秒后跳转...</a></td></tr>
        <#else>
        	<tr><td><a href="${base}/index.do" style="font-size:12px; color:#F00; padding-left:5px;">进入首页</a></td></tr>
        </#if>
       </table>
     
     </div>
  </div>
<#include "/content/common/footer.ftl">

</body>
</html>