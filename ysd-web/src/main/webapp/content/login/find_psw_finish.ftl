<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-密码重置</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/reset_pwd.css" />
</head>
<body>
   <!-- 头部 -->
   <#include "/content/common/header.ftl">
    <!-- 主内容区 -->
    <div class="content">
        <div class="hint">
             <span>找回密码&nbsp;&nbsp;&nbsp;</span>
            若您无法找回密码，请联系客服400-057-7820
        </div>
        <div class="progress">
            <ul>
                <li class="current progress_bz">1</li>
                <li></li>
                <li class="current progress_bz">2</li>
                <li></li>
                <li class="current progress_bz">3</li>
                <li></li>
                <li class="current progress_bz">4</li>
            </ul>
            <div class="progress_name">
                <span class="one">确认账号</span>
                <span class="two">安全验证</span>
                <span class="three">重置密码</span>
                <span class="four">完成</span>
            </div>
        </div>
        <!--完成-->
        <div class="complete" >
            <div class="reset_success">密码重置成功</div>
            <div class="auto"><span id="showSecond">4</span>秒后自动调转至登录页</a></div>
            <a href="${base}/user/login.do"><div class="login">立即登录</div></a>
            <input type='hidden' id='init_value' value="4" />
        </div>
    </div>
 	<!-- 尾部 -->
	<#include "/content/common/footer.ftl">
    <script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
    <script src="${base}/js/common.js"></script>
    <script src="${base}/js/reset_pwd.js"></script>
    <script type="text/javascript" src="${base}/js/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/js/jquery/jquery.validate.methods.js"></script>
    <script type="text/javascript">
		//我的账号
		$(function(){
			$('#header_wdzh').addClass('current');
			$('#header_gywm a').css('border',0);
		});
		function auto_jump() {  
		    secs = $("#init_value").val() - 1;  
		    $("#init_value").val(secs);  
		        if(secs < 0){return false;}  
		    if(secs == 0){  
		        clearInterval(time);  
		        window.location.href='${base}/user/login.do';  
		    }else{  
		        $("#showSecond").html(secs);  
		    }  
		}  
		$(function(){  
		    time = setInterval('auto_jump()',1000);  
		}) 
	</script>
</body>
</html>