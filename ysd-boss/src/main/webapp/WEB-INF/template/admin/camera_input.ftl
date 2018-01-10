<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑客户端开机图片 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");

	var $url = $("#url");
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"camera.name": "required",
			"camera.url": "required",
			"camera.orderList": 	"digits",
			"file": {
				imageFile:true
			}
		},
		messages: {
			"camera.name": "请填写名称",
			"camera.url": "请填写链接地址",
			"camera.orderList": 	"排序必须为零或正整数",
			"file": {
				imageFile:"上传图片格式错误"
			}
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
		<#if isAddAction>添加<#else>编辑</#if>首页图片轮播
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>camera!save.action<#else>camera!update.action</#if>" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						名称: 
					</th>
					<td>
						<input type="text" id="name" name="scrollpic.name" class="formText" value="${(camera.name)!}" />	 
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						图片: 
					</th>
					<td>
						<input type="file" id="camera_file" name="file" />	 
						<#if (camera.img??)!>
								<input type="button" onclick="window.open('${imgUrl}${(camera.img)!}','_bank')" value="查看"/>
						</#if>
						</td>
				</tr>
				<tr>
					<th>
						链接地址: 
					</th>
					<td>
						<input type="text" id="url" name="scrollpic.url" class="formText" value="${(scrollpic.url)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						设置: 
					</th>
					<td>
						<label>
							<@checkbox name="camera.isVisible" value="${(camera.isVisible)!true}" />显示
						</label>
						<label>
							<@checkbox name="scrollpic.isBlankTarget" value="${(scrollpic.isBlankTarget)!false}" />在新窗口中打开
						</label>
						<label>
							<@checkbox name="scrollpic.isApp" value="${(scrollpic.isApp)!false}" />手机
						</label>
					</td>
				</tr>
				<tr>
					<th>
						排序: 
					</th>
					<td>
						<input type="text" name="scrollpic.orderList" class="formText" value="${(scrollpic.orderList)!}" title="只允许输入零或正整数" />
					</td>
				</tr>
				<tr>
					<th>
						描述: 
					</th>
					<td>
						<textarea name="scrollpic.remark" class="formTextarea">${(scrollpic.remark)!}</textarea></td>
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