<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>编辑银行卡 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {

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
			"bank.bankId": "required",
			"bank.branch": "required"
		},
		messages: {
			"bank.bankId": "请选择银行",
			"bank.branch": "请填写支行"
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
		编辑银行卡
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="account_bank!update.action" method="post">
			<input type="hidden" name="bank.id" value="${bank.id}" />
			<table class="inputTable">
				<tr>
					<th>
						ID: 
					</th>
					<td>
						${(bank.id)!}
					</td>
				</tr>
				<tr>
					<th>
						用户名/手机号: 
					</th>
					<td>
						${(bank.user.phone)!}
					</td>
				</tr>
				<tr>
					<th>
						真实姓名: 
					</th>
					<td>
						${(bank.user.realName)!}
					</td>
				</tr>
				<tr>
					<th>
						银行卡账号: 
					</th>
					<td>
						
						${(bank.account)!}
					</td>
				</tr>
				<tr>
					<th>
						银行: 
					</th>
					<td>
						<select name="bank.bankId">
							<#list banks as item>
							<option value="${item.keyValue}" <#if bank.bankId==item.keyValue> selected </#if>>${item.name}</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						开户行: 
					</th>
					<td>
						<input type="text" name="bank.branch" class="formText" value="${(bank.branch)!}" />
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