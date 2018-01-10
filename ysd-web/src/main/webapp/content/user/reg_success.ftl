<!DOCTYPE html>
<html>
  <head>
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台—系统信息</title>
    <#include "/content/common/meta.ftl">
<script>  
	function auto_jump() {  
	    secs = $("#init_value").val() - 1;  
	    $("#init_value").val(secs);  
	        if(secs < 0){return false;}  
	    if(secs == 0){  
	        clearInterval(time);  
	        window.location.href='${base}/userCenter/realname.do';  
	    }else{  
	        $("#showSecond").html(secs);  
	    }  
	}  
	$(function(){  
	    time = setInterval('auto_jump()',1000);  
	}) 
</script> 
</script> 
  </head>  <!-- header -->
  
  <body>
  <#include "/content/common/header.ftl">
  <input type='hidden' id='init_value' value="3" />
  <div style=" width:960px; margin:20px auto; background:#fff;">
     <div style="background:#ede3cb; height:40px; line-height:40px; text-indent:20px; color:#000; font-size:16px;">系统消息</div>
     <div style=" width:800px; height:350px; border:1px solid #ccc; margin:20px auto;">
       <table width="450" style=" margin:140px auto">
         <tr>
           <td rowspan="3" width="58"><img src="/static/img/chenggong.jpg" /></td>
         </tr>
         <tr><td><span style="color:#646464; font-size:27px;">注册成功，系统将<span id="showSecond">3</span>秒后跳转...</span></td></tr>
         <tr><td><a href="我的帐号.html" style="font-size:12px; color:#F00; padding-left:5px;"></a></td></tr>
       </table>
     
     </div>
  </div>
  <#include "/content/common/footer.ftl">
</body>
<#--
  <body>
<#include "/content/common/header.ftl">

<input type='hidden' id='init_value' value="3" />

<div class="main"><div style=" margin:0 auto; height:160px; width:1000px; background:url(${base}/static/img/kaqu_v.png) no-repeat;"></div></div>

<div class="wrap">
	<div class="content">
		<div style="background:url(${base}/static/img/yjiao.png) no-repeat 0 top; width:1000px; height:20px;"></div>
		<div class="kaqu_name">
			<div class="kaqu_name0">
				<h2 class="kaqu_youxi">系统消息</h2>
				<div class="kaqu_name1">
					<div class="kaqu_name2_2">
						<style>
						</style>
						<div class="kaqu_gongxinin">
							<div class="kaqu_chenggong">
									注册成功!
							</div>
						</div>

						<div class="kaqu_chakanjilu">
							<a href="javascript:void(0);" class="kaqu_jiluxinxi">系统将<span id="showSecond">3</span>秒钟后自动跳转</a>
						</div>
					</div>
					<div style=" float:left; width:360px; height:420px; background:url(${base}/static/img/suanpan.png) no-repeat 0 0;"></div>
				</div>
			</div>
			<div style=" clear:both"></div>
		</div>
		<div style=" clear:both"></div>
		<div style=" background-image:url(${base}/static/img/3_bg.png); background-position:0 bottom; background-repeat:repeat-x; height:9px;"></div>
	</div>
	<!-- middle end --`>
</div>
<!-- content end --`>
<div style="clear:both"></div>
</div><!--wrap end--`>

</div>

<#include "/content/common/footer.ftl">

</body>-->
</html>
