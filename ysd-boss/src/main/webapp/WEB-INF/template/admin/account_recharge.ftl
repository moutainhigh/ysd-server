<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>线下充值</title>
<meta name="Author" content="QMD++ Team" />
<meta name="Copyright" content="QMD++" />
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
				remote: {
					 type:"POST",
					 url:"user!checkUsername.action"
				}
			},
			"userAccountRecharge.money": {
				required: true,
				number: true,
				radixPoint: true
			},
			"userAccountRecharge.reward":{
				required: true,
				number: true,
				radixPoint: true
			},
			"userAccountRecharge.remark": {
				required: true
			}
		},
		messages: {
			"user.username": {
				required: "请填写用户名",
				remote: "用户名不存在"
			},
			"userAccountRecharge.money": {
				required: "请填写充值金额",
				number: "充值金额必须为数字",
				radixPoint:"不能超过二位小数点"
			},
			"userAccountRecharge.reward":{
				required: "请填写充值奖励",
				number: "充值奖励必须为数字",
				radixPoint:"不能超过二位小数点"
			},
			"userAccountRecharge.remark": {
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

	$("input[name=userAccountRecharge.money]").focusout(function() {
		  $.ajax({
				url: "${base}/admin/account!ajaxOffLinejl.action",
				data: {"userAccountDetail.money":$("input[name=userAccountRecharge.money]").val()},
				type: "POST",
				dataType: "json",
				cache: false,
				success: function(data) {
					if(data.status=="success"){
						$("input[name=userAccountRecharge.reward]").val(data.message);
					}
				}
			});
	});
});
</script>
</head>
<body class="input admin">
	<div class="bar">
		线下充值
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="account!rechargeDo.action" method="post">
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
						<input type="hidden" name="userAccountRecharge.type" class="formText" value="recharge_offline_trial,线下充值"/>线下充值
					</td>
				</tr>
				<tr>
					<th>
						充值金额: 
					</th>
					<td>
						<input type="text" name="userAccountRecharge.money" class="formText" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						充值奖励: 
					</th>
					<td>
						<input type="text" name="userAccountRecharge.reward" class="formText" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						备注: 
					</th>
					<td>
						<textarea name="userAccountRecharge.remark" class="formTextarea"></textarea>
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