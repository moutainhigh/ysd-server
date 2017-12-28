<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑线下充值账号 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript" src="${base}/template/common/js/bankInput.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	$("#account").bankInput();
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
		rules: {
			"offLineAccount.account": {
				required: true,
				minlength: 10,
				maxlength:25
			},
			"offLineAccount.bankId": {
				required: true
			},
			"offLineAccount.branch": {
				required: true,
				minlength: 5,
				maxlength:200
			},
			"offLineAccount.legalName": {
				required: true,
				minlength: 2,
				maxlength:50
			},
			"offLineAccount.orderList": "digits"
		},
		messages: {
				"offLineAccount.account": {
					required: "请填写银行账号",
					minlength: "银行账号必须大于等于10",
					maxlength: "银行账号必须小于等于25"
				},
				"offLineAccount.bankId": {
					required: "请选择银行名称"
				},
				"offLineAccount.branch": {
					required: "请填写支行名称",
					minlength: "支行名称必须大于等于5",
					maxlength: "支行名称必须小于等于200"
				},
				"offLineAccount.legalName": {
					required: "请填写开户名",
					minlength: "开户名必须大于等于2",
					maxlength: "开户名必须小于等于50"
				},
				"offLineAccount.orderList": "排序必须为零或正整数"
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});

});
</script>
</head>
<body class="input admin">
	<div class="bar">
		<#if isAddAction>添加<#else>编辑</#if>线下充值账号
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>off_line_account!save.action<#else>off_line_account!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable tabContent">
				<tr>
					<th>
						开户名: 
					</th>
					<td>
						<input type="text" name="offLineAccount.legalName" id="legalName" class="formText" value = "${(offLineAccount.legalName)!}"/>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						账号: 
					</th>
					<td>
						<input type="text" name="offLineAccount.account" id="account" class="formText" value = "${(offLineAccount.account)!}"/>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						银行: 
					</th>
					<td>
						 <select id="bankId" name="offLineAccount.bankId">
						 	<option value="">请选择</option>
							  <@listing_childlist sign="recharge_bank"; listingList>
								<#list listingList as listing>
									<option value="${listing.keyValue}" <#if (offLineAccount.bankId)! = listing.keyValue>selected</#if>>${substring(listing.name, 24, "...")}</a>
									</option>
								</#list>
							  </@listing_childlist>
                          </select>
                          <label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						支行名称: 
					</th>
					<td>
						<input type="text" name="offLineAccount.branch" class="formText"  value = "${(offLineAccount.branch)!}"/>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						设置: 
					</th>
					<td>
						<label>
							<@checkbox name="offLineAccount.isEnabled" value="${(offLineAccount.isEnabled)!true}" />启用
						</label>
					</td>
				</tr>
				<tr>
					<th>
						排序: 
					</th>
					<td>
						<input type="text" name="offLineAccount.orderList" class="formText" value="${(offLineAccount.orderList)!}" title="只允许输入零或正整数" />
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