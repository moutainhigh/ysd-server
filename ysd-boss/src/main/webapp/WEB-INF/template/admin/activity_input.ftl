<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑活动 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
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
			"activity.title": "required",
			"activity.content": "required",
			"activity.status": "required",
			"activity.orderList": "digits",
			"imgWeb":{
				imageFile:true
			},
			"imgApp":{
				imageFile:true
			}
		},
		messages: {
			"activity.title": "请填写活动标题",
			"activity.content": "请填写活动内容",
			"activity.status": "请选择活动状态",
			"activity.orderList": "排序必须为零或正整数"
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
		<#if isAddAction>添加活动<#else>编辑活动</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>activity!save.action<#else>activity!update.action</#if>" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						活动标题: 
					</th>
					<td>
						<input type="text" name="activity.title" class="formText" value="${(activity.title)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						开始时间: 
					</th>
					<td>
						<input type="text" name="activity.startTime" class="formText" value="${(activity.startTime)!}" onclick="WdatePicker()"/>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						结束时间: 
					</th>
					<td>
						<input type="text" name="activity.endTime" class="formText" value="${(activity.endTime)!}" onclick="WdatePicker()"/>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						pc列表图片: 
					</th>
					<td>
						<input type="file" id="imgWeb" name="imgWeb" />
						<#if !isAddAction>
							<#if activity.imgWeb!><a href="${imgUrl}${activity.imgWeb!}" target ="_blank">查看</a><#else><font color="red">没有上传图片;</font></#if>
						</#if>
					</td>
				</tr>
				
				<tr>
					<th>
						移动端列表图片: 
					</th>
					<td>
						<input type="file" id="imgApp" name="imgApp" /><font color="red">【app端图片宽高比为：1:3】</font>
						<#if !isAddAction>
							<#if activity.imgApp!><a href="${imgUrl}${activity.imgApp!}" target ="_blank">查看</a><#else><font color="red">没有上传图片;</font></#if>
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						PC内容: 
					</th>
					<td>
						<textarea id="editor" name="activity.pcContent" class="editor" height="600">${(activity.pcContent)!}</textarea>
						<div class="blank"></div>
					</td>
				</tr>
				<tr>
					<th>
						移动端内容: 
					</th>
					<td>
						<input type="file" id="imgContentApp" name="imgContentApp" /><font color="red">【app端图片宽高比为：1:3】</font>
						<#if !isAddAction>
							<#if activity.content!><a href="${activity.content!}" target ="_blank">查看</a><#else><font color="red">没有上传图片;</font></#if>
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						活动状态: 
					</th>
					<td>
						<select name="activity.status" ><#assign activityStatus = (activity.status)!''>
							<option value="">请选择...</option>	
							<option value="0"<#if activityStatus == 0> selected</#if>>未开始</option>
							<option value="1"<#if activityStatus == 1> selected</#if>>进行中</option>
							<option value="2"<#if activityStatus == 2> selected</#if>>已结束</option>
							<option value="-1"<#if activityStatus == -1> selected</#if>>下架</option>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						排序: 
					</th>
					<td>
						<input type="text" name="activity.orderList" class="formText" value="${(activity.orderList)!'1000'}" title="只允许输入零或正整数" />
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