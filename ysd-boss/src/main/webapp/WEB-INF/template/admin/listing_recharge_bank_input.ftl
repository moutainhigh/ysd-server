<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑充值银行</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.cn.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.translate.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");

	var $listingName = $("#listingName");
	var $listingSign = $("#listingSign");
	var $listingSignLoadingIcon = $("#listingSignLoadingIcon");

	$listingName.change( function() {
		var $this = $(this);
		$this.translate("zh", "en", {
			data: true,
			replace: true,
			start: function() {
				$listingSignLoadingIcon.show();
			},
			complete: function() {
				var listingSignValue ="123";
				$listingSign.val(listingSignValue);
				$listingSignLoadingIcon.hide();
			},
			error: function() {
				$listingSignLoadingIcon.hide();
			}
		});

	});

	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"listing.name": "required",
			"listing.sign": {
				required: true,
				alphanumeric: true,
				<#if isAddAction>
					remote: "listing!checkSign.action"
				<#else>
					remote: "listing!checkSign.action?oldValue=${listing.sign?url}"
				</#if>
			},
			"listing.orderList": "digits"
		},
		messages: {
			"listing.name": "请填写分类名称",
			"listing.Sign": {
				required: "请填写标识",
				alphanumeric: "只允许输入字母或数字",
				remote: "标识已存在"
			},
			"listing.orderList": "排序必须为零或正整数"
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
		<#if isAddAction>添加充值银行<#else>编辑充值银行</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>listing!save.action<#else>listing!update.action</#if>?flag=1" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="parentId" value="1477" />
			<table class="inputTable">
				<tr>
					<th>
						分类名称: 
					</th>
					<td>
						<input type="text" id="listingName" name="listing.name" class="formText" value="${(listing.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				
				<tr>
					<th>
						标识: 
					</th>
					<td>
						<input type="text" id="listingSign" name="listing.sign" class="formText" value="${(listing.sign)!}" title="该分类的唯一标识，用于分类路径和模板标识" />
						<label class="requireField">*</label>
						<span id="listingSignLoadingIcon" class="loadingIcon hidden">&nbsp;</span>
					</td>
				</tr>
				<tr>
					<th>
						值: 
					</th>
					<td>
						<input type="text" class="formText" name="listing.keyValue" value="${(listing.keyValue)!}" />
					</td>
				</tr>
				<tr>
					<th>
						LOGO: 
					</th>
					<td>
						<input type="file" name="imgFile" />
						<#if (listing.logo??)!>
							&nbsp;&nbsp;&nbsp;<a href="${imgUrl}${listing.logo}" target="_blank">查看</a>
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						是否启用: 
					</th>
					<td>
						<@checkbox name="listing.isEnabled" value="${(listing.isEnabled)!true}" />启用
					</td>
				</tr>
				<tr>
					<th>
						排序: 
					</th>
					<td>
						<input type="text" name="listing.orderList" class="formText" value="${(listing.orderList)!}" title="只允许输入零或正整数" />
					</td>
				</tr>
				<tr>
					<th>
						分类项描述: 
					</th>
					<td>
						<textarea name="listing.description" class="formTextarea">${(listing.description)!}</textarea>
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