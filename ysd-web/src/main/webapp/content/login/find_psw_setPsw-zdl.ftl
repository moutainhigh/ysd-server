<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${Application ["qmd.setting.name"]}-国资控股|专业安全透明的互联网金融服务平台-找回密码</title>
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
            <li class="cur">
              <p class="circle">3</p>
              <p class="info">重置密码</p>
            </li>
            <li class="line"></li>
            <li>
              <p class="circle">4</p>
              <p class="info" style="left:0px;">完成</p>
            </li>
          </ul>
      </div>
      <div style=" width:400px; margin:70px auto 150px;">
          <table width="100%" cellpadding="0" cellspacing="0" border="0" style="color:#383838; font-size:16px;">
            <tbody>
            	<form action="${base}/user/findPsw3.do" method="post" id="pswForm">
		            <tr height="50">
		               <td align="right" width="125"></td>
		               <td align="left" style="padding-left:20px; color:#808080;">
		                  <div style="width:294px; height:42px; line-height:42px;background:#e6f4fa; border:1px solid #c6e9ef; padding-left:10px;font-size:14px; ">
		                    <span style="width:22px;height:22px;display:inline-block;line-height:22px;text-align:center;color:#fff;font-weight:bold;border-radius:50%;background:#006dc1;margin-right:5px;">!</span>身份验证通过，请设置新的登录密码。
		                  </div>
		               </td>
		            </tr>
		            <tr height="50">
		               <td align="right" width="125" style="padding-top:20px;"><sup style=" color:#fd7c1a; position:relative; top:4px;right:2px;">*</sup>新密码</td>
		               <td align="left" style="padding-left:20px;padding-top:24px; color:#383838;">
		                  <input type="password" placeholder="密码为8-20个字符组成" style="width:294px; height:42px; line-height:42px; border:1px solid #dbdbdb;border-radius:5px; padding-left:10px; " name="password" id="password">
		               </td>
		            </tr>
		            <tr height="50">
		               <td align="right" width="125" style="padding-top:20px;"><sup style=" color:#fd7c1a; position:relative; top:4px;right:2px;">*</sup>确认密码</td>
		               <td align="left" style="padding-left:20px;padding-top:24px; color:#383838;">
		                  <input type="password" placeholder="再次输入密码" style="width:294px; height:42px; line-height:42px; border:1px solid #dbdbdb;border-radius:5px; padding-left:10px; " id="password2">
		               </td>
		            </tr>
		            <tr height="60">
		               <td align="right" width="125"></td>
		               <td align="left" style="padding-left:20px;padding-top:24px;">
		                  <input type="submit" value="完成"  style="width:108px;height:36px;border-radius:5px;line-height:36px;background-color:#fd7c1a;color:#fff;display:block;text-align:center;cursor:pointer;" />
		               </td>
		            </tr>
		       </form>
          </tbody>
        </table>
        </div>
  </div>
</div><!-- 找回密码 end -->
<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript">
	$().ready(function() {
		var $registerForm = $("#registerForm");	
		// 表单验证
			$registerForm.validate({
				errorClass:"login_error",
				errorLabel:"login_error",
				rules: {
					
					"password":{
						required: true,
						strongTxt:true,
						minlength:8,
						maxlength:16
					},
					"password2":{
						required: true,
						equalTo:"#password"
					}
				},
				messages: {					
					"password": {
						required: "请填写密码",
						minlength: "密码必须大于等于8",
						maxlength: "密码必须小于等于16"
					},
					"password2": {
						required: "请再次输入密码",
						equalTo: "两次密码输入不一致"
					}
				},
				errorPlacement: function(error, element) {
				  	element.parents(".ul").next("ul").css("display","block");
				  	error.appendTo(element.parents(".ul").next("ul").find("li").eq(1));
				},
				submitHandler: function(form) {
					
					if(!$("#agreeCheckbox")[0].checked){
						alertMessage('请阅读并同意《${Application ["qmd.setting.name"]}服务协议》');
						return;
					}
					
					form.submit();
				}
			});		
	});
</script>
</html>
