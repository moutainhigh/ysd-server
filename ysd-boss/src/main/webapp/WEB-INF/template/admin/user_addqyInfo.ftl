<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>编辑借款人</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {
	var $tab = $("#tab");
	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
	var $areaSelect = $("#areaSelect");
	
	// 地区选择菜单
	$areaSelect.lSelect({
		url: "${base}/admin/area!ajaxArea.action"// AJAX数据获取url
	});
	
	
	var $fileListTable = $("#fileListTable");

	var fileListIndex = 0;
	
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
			"userInfo.privateName":{
				required:true
			},
			"user.realName":{
				required:true
			},
			"areaId":{
				required:true
			},
			"user.username":{
				required:true,
				remote: "user!checkUsernameByAdd.action"
			},
			"user.email": {
				required: true,
				email: true,
				remote: "user!checkEmail.action"
			},
			"user.phone": {
				required: true,
				tel: true,
				remote: "user!checkUserPhone.action"
			},
			"userInfo.registeredCapital":{
				required:true,
				digits: true
			},
			"user.password": {
				required: true,
				minlength: 8,
				maxlength: 20
			},
			"reupassword": {
				required: true,
				equalTo:"#upassword"
			}
		},
		messages: {
			"userInfo.privateName":{
				required:"请输入企业名称"
			},
			"user.realName":{
				required:"请输入法人姓名"
			},
			"areaId":{
				required:"请选择对接服务商所在地区"
			},
			"user.username":{
				required:"请输入用户名",
				remote:"用户名已存在"
			},
			"user.email": {
				required:"请输入电子邮箱",
				email: "电子邮箱格式不正确",
				remote:"电子邮箱已存在"
			},
			"user.phone": {
				required:"请输入联系电话",
				tel: "联系电话格式不正确",
				remote:"联系电话已存在"
			},
			
			
			"userInfo.registeredCapital":{
				required:"请输入注册资本",
				digits: "注册资本金额为正数"
			},
			
			"user.password": {
				required:"请输入密码",
				minlength: "密码最少8位",
				maxlength: "密码最多20位"
			},
			"reupassword": {
				required:"请输入重复密码",
				equalTo: "两次输入的密码不一致"
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
		添加企业借款人
	</div>
	
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
	
		<ul id="tab" class="tab">
				<li>
					<input type="button" value="基本信息" hidefocus />
				</li>
			</ul>
		<form id="validateForm" class="form" action="user!infoSave.action" enctype="multipart/form-data" method="post">
			<input type = "hidden" name = "id" value = "${id}">
			<input type = "hidden" name = "user.typeId" value ="1">
			<input type = "hidden" name = "user.memberType" value ="1">
			<table class="inputTable tabContent">
				<tr>
					<th>
						用户名:
					</th>
					<td>
						
						<input type="text" name="user.username" class="formText" maxlength="50" value="${(user.username)!}" /><font color="red">*</font>
						
						<!--&nbsp;&nbsp;<input type="checkbox" name="agencyReady.tasteRule" value="1"/>可否发布体验-->
					</td>
				</tr>
				<tr>
					<th>
						法人姓名:
					</th>
					<td>
						<input type="text" name="user.realName" class="formText" maxlength="50" value="${(user.realName)!}" /><font color="red">*</font>
					</td>
				</tr>
				<!--<tr>
					<th>
						担保服务商:
					</th>
					<td>
						<select  name="agency.agencydbid">
				           	 <option value="">请选择担保服务商</option>
					      		<#list agencyDbList as agencyDb>
									<option value="${agencyDb.id}" <#if agency.agencydbid == agencyDb.id>selected</#if> >${agencyDb.companyName}</option>
								</#list>
			            </select>
					</td>
				</tr>-->
	<#--		<tr>
					<th>
						所在地区:
					</th>
					<td>
						  <input type="text" id="areaSelect" name="areaId" class="hidden" value="<#if user.area!>${user.area}<#elseif user.city!>${user.city}<#else>${user.province}</#if>" defaultSelectedPath="${user.province!},${user.city!},${user.area!}" /></td>
					</td>
				</tr>-->	
				<tr>
					<th>
						联系电话:
					</th>
					<td>
						<input type="text" name="user.phone" class="formText" value="${(user.phone)!}" /><font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>
						电子邮箱:
					</th>
					<td>
						<input type="text" name="user.email" class="formText"  maxlength="100" value="${(user.email)!}" /><font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>
						登录密码:
					</th>
					<td>
						<input type="password" name="user.password" id = "upassword"  maxlength="50" class="formText" value="" /><font color="red">*</font>
						<div class="blank"></div>
					</td>
				</tr><tr>
					<th>
						重复密码:
					</th>
					<td>
						<input type="password" id ="reupasswordId" name="reupassword" class="formText" maxlength="50" value="" /><font color="red">*</font>
						<div class="blank"></div>
					</td>
				</tr>
				<tr>
					<th>注册资金：</th>
					<td>
						<input type="text" name="userInfo.registeredCapital" id="registeredCapital" value="${(userInfo.registeredCapital)!}" class="formText"/><font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>
						法人身份证上传:
					</th>
					<td>
						<input type="file" name="cardPic1"  class="fileListClass"/><#if !isAddAction && userInfo.cardPic1!><a href="${imgUrl}${userInfo.cardPic1}" target="_blank">查看</a></#if>
					</td>
				</tr>
				<tr>
					<th>
						联系地址:
					</th>
					<td>
						<input type="text" name="userInfo.address" maxlength="100" class="formText" style="width:375px" value="${(userInfo.address)!}" />
					</td>
				</tr>
				<tr>
					<th>
						法人身份证反面:
					</th>
					<td>
						<input type="file" name="cardPic2"  class="fileListClass"/><#if !isAddAction && userInfo.cardPic2!><a href="${imgUrl}${userInfo.cardPic2}" target="_blank">查看</a></#if>
					</td>
				</tr>
				<tr>
					<th>
						企业名称:
					</th>
					<td>
						<input type="text" name="userInfo.privateName" class="formText" maxlength="50" value="${(userInfo.privateName)!}" />
					</td>
				</tr>
				<tr>
					<th>
						营业执照上传:
					</th>
					<td>
						<input type="file" name="privateCharterImg"  class="fileListClass"/><#if !isAddAction && userInfo.privateCharterImg!><a href="${imgUrl}${userInfo.privateCharterImg}" target="_blank">查看</a></#if>
					</td>
				</tr>
				<tr>
					<th>
						税务登记证上传:
					</th>
					<td>
						<input type="file" name="privateTaxImg"  class="fileListClass"/><#if !isAddAction && userInfo.privateTaxImg!><a href="${imgUrl}${userInfo.privateTaxImg}" target="_blank">查看</a></#if>
					</td>
				</tr>
				
				<tr>
					<th>
						机构代码证号:
					</th>
					<td>
						<input type="text" name="userInfo.organization" class="formText" maxlength="80" value="${(userInfo.organization)!}" />
					</td>
				</tr>
				<tr>
					<th>
						机构代码证上传:
					</th>
					<td>
						<input type="file" name="privateOrganizationImg"  class="fileListClass"/><#if !isAddAction && userInfo.privateOrganizationImg!><a href="${imgUrl}${userInfo.privateOrganizationImg}" target="_blank">查看</a></#if>
					</td>
				</tr>
				<tr>
					<th>
						开户许可证上传:
					</th>
					<td>
						<input type="file" name="accountLicenceImg"  class="fileListClass"/><#if !isAddAction && userInfo.accountLicenceImg!><a href="${imgUrl}${userInfo.accountLicenceImg}" target="_blank">查看</a></#if>
					</td>
				</tr>
				
				<tr>
					<th>
						注册地址:
					</th>
					<td>
						<input type="text" name="userInfo.address" class="formText"  maxlength="80" value="${(userInfo.address)!}" />
					</td>
				</tr>
				<tr>
					<th>
						企业介绍:
					</th>
					<td>
						<textarea id="introduce" name="userInfo.introduce" cols="40" rows="3" class="txt">${(userInfo.introduce)!}</textarea>
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