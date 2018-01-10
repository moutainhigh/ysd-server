<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑支付方式</title>
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
			"rechargeConfig.paymentProductId":{
				required:true,
				alphanumeric: true,
				<#if isAddAction>
					remote:"recharge_config!ajaxPaymentProductId.action"
				<#else>
					remote:"recharge_config!ajaxPaymentProductId.action?oldProductId=${rechargeConfig.paymentProductId?url}"
				</#if>
			},
			"rechargeConfig.name": "required",
			"rechargeConfig.paymentFee": {
				required: true,
				min: 0,
				radixPoint: true
			},
			"rechargeConfig.orderList": "digits",
			"rechargeConfig.className":"required",
			"file": {
				imageFile:true
			}
		},
		messages: {
			"rechargeConfig.paymentProductId":{
				required:"请填写支付方式标识",
				alphanumeric: "只允许输入字母或数字",
				remote:"标识已存在"
			},
			"rechargeConfig.name": "请填写支付方式名称",
			"rechargeConfig.paymentFee": {
				required: "请填写支付费率/固定费用",
				min: "支付费率/固定费用必须为零或正数",
				radixPoint:"不能超过二位小数点"
			},
			"rechargeConfig.orderList": "排序必须为零或正整数",
			"rechargeConfig.className":"类路径不能为空",
			"file": {
				imageFile:true
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	var $radio_c=$(":input[name='rechargeConfig.paymentFeeType']");
	$radio_c.change(function(){
		 if($("input[name='rechargeConfig.paymentFeeType']:checked").val()=="scale" || $("input[name='rechargeConfig.paymentFeeType']:checked").val()=="scalejl"){
		  		$("#percentage_div").show();
		 }else{
			 	$("#percentage_div").hide();
		 }
	});
	
})
</script>
</head>
<body class="input">
	<div class="bar">
		<#if isAddAction>添加支付方式<#else>编辑支付方式</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>recharge_config!save.action<#else>recharge_config!update.action</#if>" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						支付方式标识: 
					</th>
					<td>
						<input type="text" name="rechargeConfig.paymentProductId" class="formText" value="${(rechargeConfig.paymentProductId)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						支付方式名称: 
					</th>
					<td>
						<input type="text" name="rechargeConfig.name" class="formText" value="${(rechargeConfig.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						商户ID: 
					</th>
					<td>
						<input type="text" name="rechargeConfig.bargainorId" class="formText" value="${(rechargeConfig.bargainorId)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						密钥: 
					</th>
					<td>
						<input type="text" name="rechargeConfig.bargainorKey" class="formText" value="${(rechargeConfig.bargainorKey)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						LOGO: 
					</th>
					<td>
						<input type="file" id="logoPath" name="file" />	 
						<#if (rechargeConfig.logoPath??)!>
								<input type="button" onclick="window.open('${base}${(rechargeConfig.logoPath)!}','_bank')" value="查看"/>
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						类路径: 
					</th>
					<td>
						<input type="text" name="rechargeConfig.className" class="formText" value="${(rechargeConfig.className)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				
				<tr>
					<th>
						支付手续费设置: 
					</th>
					<td>
						<#list paymentFeeTypeList as list>
							<label class="requireField">
								<input type="radio" name="rechargeConfig.paymentFeeType" value="${list}"<#if ((isAddAction && list == "scale") || list == rechargeConfig.paymentFeeType)!> checked </#if>>
								${action.getText("PaymentFeeType." + list)}
							</label>
						</#list>
					</td>
				</tr>
					<th>
						支付费率/固定费用: 
					</th>
					<td>
						<input type="text" name="rechargeConfig.paymentFee" class="formText" value="${(rechargeConfig.paymentFee)!"0"}" /><span id="percentage_div">%</span>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						是否启用: 
					</th>
					<td>
						<@checkbox name="rechargeConfig.isEnabled" value="${(rechargeConfig.isEnabled)!true}" />启用
					</td>
				</tr>
				<tr>
					<th>
						排序: 
					</th>
					<td>
						<input type="text" name="rechargeConfig.orderList" class="formText" value="${(rechargeConfig.orderList)!}" title="只允许输入零或正整数" />
					</td>
				</tr>
				<tr>
					<th>
						介绍: 
					</th>
					<td>
						<textarea name="rechargeConfig.description" id="editor" class="editor">${(rechargeConfig.description)!}</textarea>
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