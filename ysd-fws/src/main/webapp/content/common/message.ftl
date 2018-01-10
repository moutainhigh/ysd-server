<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${Application ["qmd.setting.name"]} 系统信息</title>
	
    <#include "/content/common/meta.ftl">

<#if redirectUrl?if_exists>
<script>  
	function redirectUrl() {
		window.location.href = "${redirectUrl}"
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
</#if>
   
  <body>
 <!-- header -->
<#include "/content/common/header.html">
<#if redirectUrl?if_exists>
<input type='hidden' id='init_value' value="3" />
</#if>
<div class="main"><div style=" margin:0 auto; height:160px; width:1000px; background:url(${base}/static/img/kaqu_v.png) no-repeat;"></div></div><!--main end -->

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
<#if actionMessages?if_exists>
						<div class="kaqu_gongxinin">
							<div class="kaqu_chenggong">
	<#list actionMessages as actionMessage>
									${actionMessage}
	</#list>
							</div>
						</div>
</#if>
<#if actionErrors?if_exists>
						<div class="kaqu_gongxinin">
							<div class="kaqu_cuowu">
	<#list actionErrors as actionError>
									${actionError}
	</#list>
							</div>
						</div>
</#if>
<#if redirectUrl?if_exists>
						<div class="kaqu_chakanjilu">
							<a href="javascript:void(0);" class="kaqu_jiluxinxi"><span id="showSecond">3</span>秒钟后自动跳转</a>
						</div>
</#if>
					</div>
					<div style=" float:left; width:360px; height:420px; background:url(${base}/static/img/suanpan.png) no-repeat 0 0;"></div>
				</div>
			</div>
			<div style=" clear:both"></div>
		</div>
		<div style=" clear:both"></div>
		<div style=" background-image:url(${base}/static/img/3_bg.png); background-position:0 bottom; background-repeat:repeat-x; height:9px;"></div>
	</div>
	<!-- middle end -->
</div>
<!-- content end -->
<div style="clear:both"></div>
</div><!--wrap end-->

<#include "/content/common/footer.html">

</body>
</html>
