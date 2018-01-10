<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>${Application ["qmd.setting.name"]}-手机号验证</title>
<#include "/content/common/meta.ftl">
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>

</head>
<body>
<#include "/content/common/header.ftl">

<div style="background:#fff; width:998px; height:525px; margin:20px auto; border:1px solid #b5b5b6; border-radius:20px;">
   <div style="width:963px; padding-left:35px;">
       <h3 style="color:#595757; font-size:16px; height:69px; line-height:69px;">账户登录</h3>
       <div style=""><img src="${base}/static/img/v3.png" /></div>
	      <ul style=" color:#3e3a39; padding:50px 0px 30px 15px; position:relative;width:585px;">
	       
		         <li style="padding:40px 0px 10px 155px; background:url(${base}/static/img/email.png) 45px 10px no-repeat;">
		         	  <font style="color:#727171;font-size:16px;">我们给您的邮箱<a href="${mailUrl}" style="color:#5b8ea3;">${(user.email)!}</a>发送了一封验证邮件，请您按照邮件提示在24小时内激活你的帐号</font>
		         </li>
		         <li style="padding:10px 0px 0px 202px;">
		         	<a href="${mailUrl}" target="_blank" style="background:#e7414f;width:100px;height:35px;line-height:35px;text-align:center;color:#fff;font-size:14px;display:inline-block;border-radius:5px;">去邮箱查看</a>
		           <span>&nbsp;&nbsp;没收到？&nbsp;&nbsp;&nbsp;<a href="${base}/user/validateEmail.do" style="color:#5b8ea3;">再次验证</a></span>
		         </li>
         
        
         <li style="position:absolute; right:-270px; bottom:20px;"><a href=""><img src="${base}/static/img/v5.png" /></a></li>
       </ul>
   </div>
  <div style="clear:both;"></div>
</div>


<#include "/content/common/footer.ftl">
<script>
jQuery(function(){
	jQuery(".informashion").mouseover(function(){
	  jQuery(this).children('.sky').css("display","block");
		});
	jQuery(".informashion").mouseleave(function(){
		jQuery(this).children('.sky').css("display","none");
		});
	});
</script>
</body>
</html>
