<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#assign str='' />
<#if flag =='0'>
	<#assign str = '用户余额调整' />
<#elseif flag =='1'>
	<#assign str = '现金奖励' />
<#elseif flag =='2'>
	<#assign str = '资金转入' />
<#elseif flag =='3'>
	<#assign str = '红包奖励' />
<#elseif flag =='4'>
	<#assign str = '红包扣除' />
<#elseif flag =='5'>
	<#assign str = '增加体验金' />
<#elseif flag =='6'>
	<#assign str = '扣除体验金' />
</#if>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${str}</title>
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
			"user.username": {
				required: true,
				remote:{
					type:"POST",
				 	url:"user!checkUsername.action"
				}
			},
			"rewards.money": {
				required: true,
				positive: true,
				radixPoint: true
			},
			"rewards.remark": {
				required: true
			}
		},
		messages: {
			"user.username": {
				required: "请填写用户名",
				remote: "用户名不存在"
			},
			"rewards.money": {
				required: "请填写金额",
				positive: "请输入合法的正数！",
				radixPoint:"不能超过二位小数点"
			},
			"rewards.remark": {
				required: "请输入备注"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			$.dialog({type: "warn", content: "确定执行此操作吗？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			function right(){
				form.submit();
			}
		}
	});
		

});
</script>
</head>
<body class="input admin">
	<div class="bar">
		${str}
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="rewards!add.action" method="post">
			<table class="inputTable tabContent">
				<tr>
					<th>
						用户名: 
					</th>
					<td>
						<input type="text" name="user.username" class="formText"/><label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						类型: 
					</th>
					<td>
						<input type="hidden" name="rewards.type" class="formText" value="${flag}"/>${str}
					</td>
				</tr>
				<tr>
					<th>
						金额: 
					</th>
					<td>
						<input type="text" name="rewards.money" class="formText" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						备注: 
					</th>
					<td>
						<textarea name="rewards.remark" class="formTextarea"></textarea>
						<label class="requireField">*</label>
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