<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑会员 </title>
<meta name="Author" content="QMD++ Team" />
<meta name="Copyright" content="QMD++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");

	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
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

});
</script>
</head>
<body class="input">
	<div class="bar">
		编辑会员
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="user!update.action" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						ID:
					</th>
					<td>
						${(user.id)!}
					</td>
				</tr>
				
					<tr>
					<th>
						用户名: 
					</th>
					<td>
					<#if !isEdit>
						${(user.username)!}
						<input type="hidden" name="user.username" class="formText" value="${(user.username)!}" />
					</#if>
					<#if isEdit>						
						<input type="text" name="user.username" class="formText" value="${(user.username)!}" />
					</#if>
					</td>
					
				</tr>
				
				<tr>
					<th>
						用户手机: 
					</th>
					<td>
						<input type="text" name="user.phone" class="formText" value="${(user.phone)!}" />
					</td>
				</tr>
				<tr>
					<th>
						会员类型: 
					</th>
					<td>
						<#if user.memberType==0>个人<#elseif user.memberType==1>企业</#if>&nbsp;/
						<#if user.typeId==0>投资人<#elseif user.typeId==1>借款人</#if>
					</td>
				</tr>
				
				<tr>
					<th>
						身份认证: 
					</th>
					<td>
						<input type="radio" name="user.realStatus" <#if user.realStatus==1> checked disabled</#if> value="1"/>通过&nbsp;
						<input type="radio" name="user.realStatus" <#if user.realStatus==0> checked <#else> disabled</#if> value="0"/>未通过
					</td>
				</tr>
				<tr>
					<th>
						真实姓名: 
					</th>
					<td>
						<input class="formText" name="user.realName" <#if user.realStatus==1> disabled</#if> value="${user.realName}"/>
					</td>
				</tr>
				<tr>
					<th>
						身份证号码: 
					</th>
					<td>
						<input class="formText" name="user.cardId" <#if user.realStatus==1> disabled</#if> value="${user.cardId}"/>
					</td>
				</tr>
				
				<tr>
					<th>
						设置:
					</th>
					<td>
						<label>
							<@checkbox name="user.isEnabled" value="${(user.isEnabled)!true}" />启用
						</label>
						<label>
							<@checkbox name="user.isLock" value="${(user.isLock)!true}" />锁定
						</label>
					</td>
				</tr>
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						&nbsp;
					</td>
				</tr>
				
				<#if isEditAction>
					<tr>
						<th>
							注册日期
						</th>
						<td>
							${(user.createDate?string("yyyy-MM-dd HH:mm:ss"))!}
						</td>
					</tr>
					<tr>
						<th>
							注册IP
						</th>
						<td>
							${(user.addIp)!}
						</td>
					</tr>
				</#if>
				
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>