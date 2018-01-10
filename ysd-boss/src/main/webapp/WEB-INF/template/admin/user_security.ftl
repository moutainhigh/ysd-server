<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>系统设置</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	var $tab = $("#tab");
	
	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
	

	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		ignore: ".ignoreValidate",
		rules: {
			"user.password": {
				minlength: 8,
				maxlength: 16
			},
			"rePassword": {
				equalTo: "#password"
			},
			"user.payPassword": {
				minlength: 8,
				maxlength: 16
			},
			"rePayPassword": {
				equalTo: "#payPassword"
			},
			"user.email": {
				email: true,
				remote: "user!checkEmail.action?id=${id}"
			},
			"user.phone": {
				phone: true,
				remote: "user!checkPhone.action?id=${id}"
			}
			
		},
		messages: {
			"user.password": {
				minlength: "登录密码长度必须大于等于8",
				maxlength: "登录密码长度必须小于等于16"
			},
			"rePassword": {
				equalTo: "两次登录密码输入不一致"
			},
			"user.payPassword": {
				minlength: "安全密码长度必须大于等于8",
				maxlength: "安全密码长度必须小于等于16"
			},
			"rePayPassword": {
				equalTo: "两次安全密码输入不一致"
			},
			"user.email": {
				email: "E-mail格式不正确",
				remote:"E-mail已存在"
			},
			"user.phone": {
				phone: "手机号格式不正确",
				remote: "手机号已存在"
			}
			
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
})
</script>
</head>
<body class="input">
	<div class="bar">
		会员安全管理
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" class="form" action="user!securityManage.action" method="post">
		<input type="hidden" name ="id" value="${id}">
			<ul id="tab" class="tab">
				<li>
					<input type="button" value="登录密码" hidefocus />
				</li>
				<li>
					<input type="button" value="安全管理" hidefocus />
				</li>
				<li>
					<input type="button" value="邮箱管理" hidefocus />
				</li>
				<li>
					<input type="button" value="手机管理" hidefocus />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						用户名: 
					</th>
					<td>
						${user.username}
					</td>
				</tr>
				<tr>
					<th>
						登录密码: 
					</th>
					<td>
						<input type="password" id="password" name="user.password" class="formText" />
					</td>
				</tr>
				<tr>
					<th>
						确认密码: 
					</th>
					<td>
						<input type="password" name="rePassword" class="formText" />
					</td>
				</tr>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<th>
						用户名: 
					</th>
					<td>
						${user.username}
					</td>
				</tr>
				<tr>
					<th>
						安全密码: 
					</th>
					<td>
						<input type="password" id="payPassword" name="user.payPassword" class="formText" />
					</td>
				</tr>
				<tr>
					<th>
						确认密码: 
					</th>
					<td>
						<input type="password" name="rePayPassword" class="formText" />
					</td>
				</tr>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<th>
						用户名: 
					</th>
					<td>
						${user.username}
					</td>
				</tr>
				<tr>
					<th>
						邮箱: 
					</th>
					<td>
						<input type="text" id="email" name="user.email" class="formText" value = "${user.email}"/>
					</td>
				</tr>
				<tr>
					<th>
						认证状态: 
					</th>
					<td>
						<input type = "radio" name = "user.emailStatus" value="0" <#if user.emailStatus=='' ||( user.emailStatus!  && user.emailStatus = 0 )>checked</#if> >未认证
						<input type = "radio" name = "user.emailStatus" value="2" <#if user.emailStatus!  && user.emailStatus = 2>checked</#if> >认证中
						<input type = "radio" name = "user.emailStatus" value="1" <#if user.emailStatus!  && user.emailStatus = 1>checked</#if> >已认证
					</td>
				</tr>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<th>
						用户名: 
					</th>
					<td>
						${user.username}
					</td>
				</tr>
				<tr>
					<th>
						手机号: 
					</th>
					<td>
						<input type="text" id="phone" name="user.phone" class="formText" value="${user.phone}" />
					</td>
				</tr>
				<tr>
					<th>
						认证状态: 
					</th>
					<td>
						<input type = "radio" name = "user.phoneStatus" value="0" <#if user.phoneStatus=='' ||( user.phoneStatus!  && user.phoneStatus = 0 )>checked</#if>>未认证
						<input type = "radio" name = "user.phoneStatus" value="1" <#if user.phoneStatus!  && user.phoneStatus = 2>checked</#if>>认证中
						<input type = "radio" name = "user.phoneStatus" value="1" <#if user.phoneStatus!  && user.phoneStatus = 1>checked</#if>>已认证
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>