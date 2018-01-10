<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>发布活动 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
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
			"placeChilder.name": "required",
			"placeChilder.remark": "required"
		},
		messages: {
			"placeChilder.name": "请输入活动标题",
			"placeChilder.remark": "请输入活动备注"
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
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="place!childeredit.action" method="post">
			<input type="hidden" name="id" value="${(placeChilder.id)!}" />
			<input type="hidden" name="random" value="${(random)!}" />
			<table class="inputTable">
				<tr>
					<th>
						渠道名称: 
					</th>
					<td>
						${placeChilder.place.name}
					</td>
				</tr>
				<tr>
					<th>
						活动标题: 
					</th>
					<td>
						<input type="text" id="name" name="placeChilder.name" value="${placeChilder.name}"  class="formText"/>
					</td>
				</tr>
				<tr>
					<th>
						活动链接: 
					</th>
					<td>
						<input type="text" readOnly value="${placeChilder.url}" class="formText"/>
					</td>
				</tr>
				<tr>
					<th>
						活动图片: 
					</th>
					<td>
						<input type="file" name="img" class="formText"/>
					</td>
				</tr>
				<tr>
					<th>
						备注: 
					</th>
					<td>
						<textarea id="remark" name="placeChilder.remark" class="formTextarea">${placeChilder.remark}</textarea>
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