<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>${Application ["qmd.setting.name"]}-注册完成</title>
<#include "/content/common/meta.ftl">
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
</head>
<body>
<#include "/content/common/header.ftl">

<div class="content">
  <div style="width:100%; height:48px; background:#eae9e9; border-bottom:1px solid #c6c6c6;">
    <div style=" width:985px;height:48px; margin:0 auto; background:url(img/d8.png) 0 0 no-repeat;">
    </div>
  </div>
  
  <div style="width:985px; margin:0 auto; margin-top:15px; padding-bottom:15px; clear:both;">
      <div style=" padding:20px 0px 20px 20px; background:url(${base}/static/img/d10.png) 0 0 repeat; width:965px; height:437px;">
        <div style=" padding:90px 0px 0px 0px; width:375px; height:350px; background:#fff;">
        		
        		<div style="width:375px; text-align:center;"><img src="${base}/static/img/d11.png" width="77" height="67" /></div>
	           <div style=" width:375px; text-align:center; margin-top:15px;color:#4b4b4b; font-size:16px;">感谢您完成注册</div>
	           <div style=" width:375px; text-align:center; margin-top:15px;"><a href="${base}/userCenter/index.do" style="color:#4b4b4b;font-family:'宋体'; text-decoration:underline;">欢迎进入网站</a></div>
        	
            <#--<form id="payPasswordForm" method="post">
		       <ul style=" color:#3e3a39; padding:50px 0px 30px 70px; position:relative; float:left;">
				 	<li style="color:#595757; font-size:14px;margin-bottom:10px;">
			         <font style="color:#807F80; padding-left: 132px;font-size:24px;">设置安全密码</font>
			        </li>  
			       	<li style="color:#595757; font-size:14px;margin-bottom:10px;">
			         <font style="color:#807F80; padding-left: 132px;">为了你的资金安全请不要外泄此密码</font>
			        </li>         
				 	<li style="color:#595757; font-size:14px;margin-bottom:20px;">
			         <font style="color:#807F80; padding-left: 29px;">输入安全密码：</font>
			         <input id="new_payPassword" name = "newPayPassword" type="password"  style="width:232px; height:40px; line-height:40px; padding-left:8px; color:#9fa0a0; font-size:14px; border:1px solid #b5b5b6;">
			       </li>
			       <li style="color:#595757; font-size:14px;">
			         <font style="color:#807F80;">再次输入安全密码：</font>
			         <input id="new_payPassword_again" type="password"  style="width:232px; height:40px; line-height:40px; padding-left:8px; color:#9fa0a0; font-size:14px; border:1px solid #b5b5b6;">
			       </li>
			  		<li style="padding:20px 0px 0px 130px;"><input type="button" onclick="tijiao()" class="denglu" value="确定"/></li>     	
		       </ul>
	       </form>-->
        </div>
      </div>
     <div style="clear:both;"></div>
  </div>
  
</div><!-- content end -->

<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
			function tijiao(){
				var newPayPassword=$("#new_payPassword").val();
				var newPayPasswordAgin=$("#new_payPassword_again").val();
				if(newPayPassword.length<8){
					alert("安全密码不能少余8位");
					return false;
				}
				if(newPayPassword.length>20){
					alert("安全密码大于20位");
					return false;
				}
				if(newPayPassword!=newPayPasswordAgin){
					alert("两次输入的密码不一致");
					return false;
				}
					$.ajax({
						url: "${base}/ajaxUpdatePassword.do",
						data: {'newPayPassword':newPayPassword}, 
						type: "POST",
						dataType: "json",
						cache: false,
						success: function(data) {
							if(data.status=="error"){
								alert(data.message);
								return false;
							}
							if(data.status=="success"){
								window.location.href = "${base}/mmsucess.do";
							}
						},
						error: function (XMLHttpRequest, textStatus, errorThrown) { 
	        	   			alert(errorThrown); 
		        	   } 
					});
			}
	</script>
</html>
