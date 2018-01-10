<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${Application ["qmd.setting.name"]}-借款人注册</title>
<#include "/content/common/meta.ftl">
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript">
	$().ready(function() {
		var $registerForm = $("#registerForm");
		var $username = $("#username");
		var $password = $("#password");
		var $reUserPassword = $("#reUserPassword");
		var $mycode = $("#mycode");
		
		
		 // 表单验证
			$registerForm.validate({
				rules: {
					"user.username":{
						required: true,
						username: true,
						minlength: 6,
						maxlength: 16,
						remote:{
							type:"POST",
							url: "${base}/user/checkUsernameReg.do"
						}
					},
					"user.password":{
						required: true,
						strongTxt:true,
						minlength:8,
						maxlength:16
					},
					"reUserPassword":{
						required: true,
						equalTo:"#password"
					},
					"user.email": {
						required: true,
						email:true,
						remote: "${base}/user/checkEmail.do"
					},
					"isAgreeAgreement":{
						required:true
					}
				},
				messages: {
					"user.username": {
						required: "请填写用户名",
						username: "用户名只允许包含中文、英文、数字和下划线",
						minlength: "用户名必须大于等于6",
						maxlength: "用户名必须小于等于16",
						remote: "用户名已存在"
					},
					"user.password": {
						required: "请填写密码",
						minlength: "密码必须大于等于8",
						maxlength: "密码必须小于等于16"
					},
					"reUserPassword": {
						required: "请再次输入密码",
						equalTo: "两次密码输入不一致"
					},
					"user.email": {
						required: "请填写E-mail",
						email: "E-mail格式不正确",
						remote: "E-mail已存在"
					},
					"isAgreeAgreement":{
						required:"请阅读会员协议!"
					}
				},
				errorPlacement: function(error, element) {
				  error.appendTo(element.parent());
				},
				submitHandler: function(form) {
					form.submit();
				}
			});
	});
</script>
</head>
<body>
<#include "/content/common/header.ftl">
<!-- content -->
<div class="content clearfix">
	<div class="tab clearfix">
		<ul>
			<li>申请类型</li>
			<li><a href="${base}/user/reg.do"><span>投资人</span></a></li>
			<li><a href="javascript:void(0);" class="current"><span>借款人</span></a></li>
		</ul>		
	</div>
	<div class="tab-con">
		
		<!-- frm -->
		<div class="user frm">  
			<form id="registerForm" method="post" action="register.do">
                 <input type = "hidden" name = "user.typeId" value="1"/>
				<table>
					<tr>
						<td class="item" width="150">会员类型：</td>
						<td>
							<label><input type="radio" name="user.memberType" id="memberType0" value="0" checked/> 个人</label>　
							<label><input type="radio" name="user.memberType" id="memberType1" value="1"  /> 企业</label>
						</td>
					</tr>
					<tr>
						<td class="item">Email：</td>
						<td>
							<input type="text" name="user.email" id="email" class="txt" size="35" />
							<label id="email_label" for="user.email"></label>
						</td>
					</tr>
					<tr>
						<td class="item">用户名：</td>
						<td>
							<input type="text" maxlength="16" name="user.username" id="username" class="txt " size="35" /> 
							<label id="username_label" for="user.username"></label>
						</td>
					</tr>
					<tr>
						<td class="item">设置登录密码：</td>
						<td>
							 <input type="password" name="user.password" id="password" class="txt "  size="35" />
							 <label id="password_label" for="user.password"></label>
                             <span class="Rclear"><!--验证密码强度--></span>
							 </td>
					</tr>
					<tr>
						<td class="item">再次输入登录密码：</td>
						<td>
							<input type="password" name="reUserPassword" id="reUserPassword" class="txt " size="35"  />
							<label id="reUserPassword_label" for="reUserPassword"></label>
						</td>
					</tr>
					<tr>
						<td class="item"></td>
						<td><input type="checkbox" id="registerWindowIsAgreeAgreement" name="isAgreeAgreement" value="true" checked />
							 请阅读<a target="_blank" href="${base}/reg_treaty.htm" class="reda10_ft12">《会员协议》</a></td>
					</tr>
				<tr>
						<td class="item"></td>
						<td><span class="btn_l s3"><input type="submit" value="同意以上协议并注册" /></span>
						</td>
					</tr>					
				</table>
			</form>				
		</div>
	</div>
	<div class="c_bottom"></div>
</div>
<#include "/content/common/footer.ftl">

</body>
</html>
