<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-邮箱验证</title>
<#include "/content/common/meta.ftl">
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript">

	//发送邮件
	function sendEmailCode(o){
		var email=$("#email").val();
		
		var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if(email==""){
			alert("邮箱不能为空");
			 return false;
		}
		if (!filter.test(email)){
			 alert('您的邮箱格式不正确');
			 return false;
		}

		$.ajax({ 
        	   url: '${base}/sendEmailCode.do',
        	   async:false,
        	   type: 'post', 
        	   dataType:'json', 
        	   data: {'user.email':email}, 
        	   beforeSend: function() {
					  o.value="邮件发送中...";
					 
			   },
        	   success: function (data) { 
        		   if(data.result==0){
        		  		var mailUrl="mail."+email.split("@")[1];
        	  			window.location.href=qmd.base+'/succsesEmail.do?mailUrl='+mailUrl+'&user.email='+email;
        	  		}
        		   else if(data.result==1){
        	   			alert("邮件发送失败,原因是:"+data.reason);
        	   			 o.value="下一步";
        	   			return false;
        	   		}
        	   }, 
        	   error: function (XMLHttpRequest, textStatus, errorThrown) { 
    	   			alert(errorThrown); 
        	   } 
    	  });	 
	}
	
	
		
</script>
</head>
<body>
<#include "/content/common/header.ftl">

<div style="background:#fff; width:998px; height:525px; margin:20px auto; border:1px solid #b5b5b6; border-radius:20px;">
   <div style="width:963px; padding-left:35px;">
       <h3 style="color:#595757; font-size:16px; height:69px; line-height:69px;">账户登录</h3>
       <div style=""><img src="${base}/static/img/v3.png" /></div>
	      <ul style=" color:#3e3a39; padding:50px 0px 30px 55px; position:relative;">
	       	<form id="checkPhoneForm" method="post" action="checkPhone.do">
		         <li style="color:#595757; font-size:14px;margin-bottom:20px;">
		         	<font style="color:#e60012;">*</font>邮箱&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		         	<input type="text" name="user.email" id="email" value="${(sessionUserId.email)!}" style="width:250px; height:40px; line-height:40px; padding-left:8px; color:#9fa0a0; font-size:14px; border:1px solid #b5b5b6;">
		          </li>
		       	 <li style="padding:40px 0px 0px 95px;">
		         	你也可以手机认证，请点击这里&nbsp;&nbsp;<a href="${base}/user/validatePhone.do" style="text-decoration: none;"><font style="color:#df601d;">手机认证</font></a>
		         </li>
		         <li style="padding:10px 0px 0px 85px;">
		         	<input type = "button" onclick="sendEmailCode(this)" id="sub_button" value="下一步" style=" display:block; width:235px; height:55px; line-height:55px; text-align:center; background:#df601d; color:#fff; font-size:16px; border-radius:20px;"/>
		         </li>
	         </form>
         
         <li style="padding:20px 0px 0px 85px; font-size:14px; color:#595757; background:url(img/v7.png) 45px 10px no-repeat;"><font style="font-weight:bold;">客服热线（工作时间 9:00-17:00）</font><font style="color:#727171;"> 400-057-7820</font></li>
         <li style="position:absolute; right:150px; bottom:110px;"><a href=""><img src="${base}/static/img/v5.png" /></a></li>
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
