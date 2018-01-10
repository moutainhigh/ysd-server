<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-找回密码</title>
<#include "/content/common/meta.ftl">
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">
<div class="find">
  <div style="width:1200px;height:600px; margin:30px auto 90px;background:#fff;">
      <div class="top">
          <p><span>找回密码</span>若您无法使用上述方法找回，请联系客服400-057-7820或重新注册</p>
      </div>
      <div>
          <ul class="step">
            <li>
              <p class="circle">1</p>
              <p class="info" style="left:-20px;">填写账户名</p>
            </li>
            <li class="line"></li>
            <li>
              <p class="circle">2</p>
              <p class="info">验证身份</p>
            </li>
            <li class="line"></li>
            <li>
              <p class="circle">3</p>
              <p class="info">重置密码</p>
            </li>
            <li class="line"></li>
            <li class="cur">
              <p class="circle">4</p>
              <p class="info" style="left:0px;">完成</p>
            </li>
          </ul>
      </div>
      <div style=" width:400px; margin:110px auto 150px;">
         <div style="float:left;width:72px;height:72px;background:url(/img/dui.png) no-repeat;"></div>
         <div style="float:left;color:#666;margin-left:6px;margin-top:11px;">
           <p style="font-size:20px;">设置成功，请牢记新的登录密码</p>
           <p style="font-size:14px;"><span style="color:#fd7c1a;" id="showSecond">４</span>秒自动跳转到<a href="${base}/user/login.do" style="color:#006dc1;">登录</a>页</p>
           <input type='hidden' id='init_value' value="4" />
         </div>
      </div>
  </div>
</div><!-- 找回密码 end -->
<#include "/content/common/footer.ftl">
</body>
<script>  
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
</html>
