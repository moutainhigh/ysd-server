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
            <li class="cur">
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
            <li>
              <p class="circle">4</p>
              <p class="info" style="left:0px;">完成</p>
            </li>
          </ul>
      </div>
      <div style=" width:400px; margin:90px auto 150px;">
          <table width="100%" cellpadding="0" cellspacing="0" border="0" style="color:#383838; font-size:16px;">
            <tbody>
            	<form action="${base}/user/findPsw1.do" method="post" id="formId">
            	<tr height="50">
	               <td align="right" width="125"><sup style=" color:#fd7c1a; position:relative; top:4px;right:2px;">*</sup>手机号</td>
	               <td align="left" style="padding-left:20px;padding-top:3px; color:#383838;">
	                  <input type="text" placeholder="请输入手机号" style="width:294px; height:42px; line-height:42px; border:1px solid #dbdbdb;border-radius:5px; padding-left:10px; " name="user.phone">
	               </td>
            	</tr>
	            <tr height="50">
	               <td align="right" width="125" style="padding-top:20px;"><sup style=" color:#fd7c1a; position:relative; top:4px;right:2px;">*</sup>验证码</td>
	               <td align="left" style="padding-left:20px;padding-top:24px;">
	                  <input type="text" placeholder="请输入验证码" style="width:168px; height:40px; line-height:40px; border:1px solid #dbdbdb; padding-left:10px;border-top-left-radius:5px; border-bottom-left-radius:5px;float:left;" name="mycode"><button onclick="verifyCode();" value="" style="width:90px; height:42px; line-height:42px; color:#383838; border-top-right-radius:5px;border-bottom-right-radius:5px; border:1px solid #dbdbdb; background:#fff;margin-left:-1px;float:left;" type="button"><img src="${base}/rand.do" width='90' height='42' alt="" id="code_img"/></button>
	                  <a href="#" onclick="verifyCode();" style="margin: 6px 0 0 10px;  display: inline-block;"><img src="/img/reload.png" width='23' height='27' alt="" /></a>
	               </td>
	            </tr>
	            <tr height="60">
	               <td align="right" width="125"></td>
	               <td align="left" style="padding-left:20px;padding-top:3px;">
	                  <input type="submit" value="下一步" style="width:108px;height:36px;border-radius:5px;line-height:36px;background-color:#fd7c1a;color:#fff;display:block;text-align:center;cursor:pointer;" />
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
		var $code_img = $("#code_img");
		var $mycode=$("#mycode");
		var $registerForm = $("#registerForm");	
		// 表单验证
			$registerForm.validate({
				errorClass:"login_error",
				errorLabel:"login_error",
				rules: {
					"user.phone":{
						required:true,
						phone:true
					},
					"user.password":{
						required: true,
						strongTxt:true,
						minlength:8,
						maxlength:16
					},
					"password2":{
						required: true,
						equalTo:"#password1"
					},
					"mycode":{
						required:true
					},
					"agreeCheckbox":{
						required:true
					}
				},
				messages: {
					"user.phone":{
						required:"手机号不能为空"
					},
					"user.password": {
						required: "请填写密码",
						minlength: "密码必须大于等于8",
						maxlength: "密码必须小于等于16"
					},
					"password2": {
						required: "请再次输入密码",
						equalTo: "两次密码输入不一致"
					},
					"mycode":{
						required:"验证码不能为空!"
					},
					"agreeCheckbox":{
						required:"请阅读会员协议"
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
	function verifyCode(){
		$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
	}
</script>
</html>
