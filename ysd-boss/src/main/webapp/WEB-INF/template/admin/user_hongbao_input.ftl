<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${str}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
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
			"user.username": {
				required: true,
				remote:{
					type:"POST",
				 	url:"user!checkUsername.action"
				}
			},
			"userHongbao.name": {
				required: true
			},
			"userHongbao.expDate": {
				required: true,
				integer: true
			},
			"userHongbao.limitStart": {
				required: true,
				integer: true
			},
			"userHongbao.limitEnd": {
				required: true,
				integer: true
			},
			"userHongbao.money": {
				required: true,
				positive: true,
				radixPoint: true,
				min:0,
				max:10000
			},
			"userHongbao.investFullMomey": {
				required: true,
				integer: true
			},
			"userHongbao.sourceString":{
				required: true
			}
		},
		messages: {
			"user.username": {
				required: "请填写用户名",
				remote: "用户名不存在"
			},
			"userHongbao.name": {
				required: "请输入红包标题"
			},
			"userHongbao.expDate": {
				required: "请输入有效期",
				integer: "请输入合法的数字！"
			},
			"userHongbao.limitStart": {
				required: "请输入项目期限起始天数",
				integer: "请输入合法的数字！"
			},
			"userHongbao.limitEnd": {
				required: "请输入项目期限截止天数",
				integer: "请输入合法的数字！"
			},
			"userHongbao.money": {
				required: "请填写红包金额",
				positive: "请输入合法的数字",
				radixPoint: "小数点不能超过2位！",
				min:"不能小于0",
				max:"不能大于10000"
			},
			"userHongbao.investFullMomey": {
				required: "请输投资金额",
				integer: "请输入合法的数字！"
			},
			"userHongbao.sourceString": {
				required: "请输备注"
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
		红包奖励
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="user_hongbao!saveNew.action" method="post">
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
						红包标题	: 
					</th>
					<td>
						<input type="text" name="userHongbao.name" class="formText"/><label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						红包金额: 
					</th>
					<td>
						<input type="text" name="userHongbao.money" class="formText" maxlength="5"/> 元
					</td>
				</tr>
				<tr>
					<th>
						使用有效期: 
					</th>
					<td>
						<input type="text" id="expDate" name="userHongbao.expDate" autocomplete="off" class="formText"  style="width:40px" title="使用有效期" maxlength="4" />&nbsp;天
					</td>
				</tr>
				<tr>
					<th>
						可用项目期限: 
					</th>
					<td>
						<input type="text" name="userHongbao.limitStart" class="formText" style="width:40px" autocomplete="off" maxlength="4"/>&nbsp;&nbsp;&nbsp;天 ---&nbsp;&nbsp;&nbsp;
						<input type="text" name="userHongbao.limitEnd" class="formText" style="width:40px" autocomplete="off" maxlength="4"/> 天
					</td>
				</tr>
				<tr>
					<th>
						投资金额:
					</th>
					<td>
						<input type="text" name="userHongbao.investFullMomey" class="formText" /> 元
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						备注:
					</th>
					<td>
						<textarea name="userHongbao.sourceString" class="formTextarea"></textarea>
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