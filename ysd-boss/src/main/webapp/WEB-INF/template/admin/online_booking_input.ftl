<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>处理${onlineBooking.name}的在线预约</title>
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
			"onlineBooking.remark": "required"
		},
		messages: {
			"onlineBooking.remark": "请填写备注信息"
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
		处理${onlineBooking.name}的在线预约
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="online_booking!ajaxDispose.action" method="post">
			<input type="hidden" name="id" value="${id}" />		
			<input type="hidden" name="pager.pageNumber" id="pageNumber" value="${(pager.pageNumber)!}" />
			<input type="hidden" name="pager.orderBy" id="orderBy" value="${(pager.orderBy)!}" />
			<input type="hidden" name="pager.order" id="order" value="${(pager.order)!}" />
			<input type="hidden" name="pager.searchBy" id="order" value="${(pager.searchBy)!}" />
			<input type="hidden" name="pager.keyword" id="order" value="${(pager.keyword)!}" />
			<table class="inputTable">
				<tr>
					<th>
						姓名:
					</th>
					<td>
						${onlineBooking.name}
					</td>
				</tr>
				<tr>
					<th>
						性别: 
					</th>
					<td>
						<#if onlineBooking.sex==1>男<#else>女</#if>
					</td>
				</tr>
				<tr>
					<th>
						手机: 
					</th>
					<td>
						${onlineBooking.phone}
					</td>
				</tr>
				<tr>
					<th>
						借款原因: 
					</th>
					<td>
						<@listing_childname sign="borrow_use" key_value ="${(onlineBooking.borrowReason)!}"; name>
							${name}
						</@listing_childname>
					</td>
				</tr>
				
				<tr>
					<th>
						借款周期: 
					</th>
					<td>
						${onlineBooking.loanPeriod}
					</td>
				</tr>
				<tr>
					<th>
						预约日期: 
					</th>
					<td>
						${onlineBooking.phone}
					</td>
				</tr>
				<tr>
					<th>
						状态: 
					</th>
					<td>
						<#if onlineBooking.isConnection>已<#else>未</#if>联系
					</td>
				</tr>
				<tr>
					<th>
						备注: 
					</th>
					<td>
						<textarea name="onlineBooking.remark" class = "formTextarea">${(onlineBooking.remark)!}</textarea>
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