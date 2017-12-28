<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加对接服务商 </title>
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
	
	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	var $areaSelect = $("#areaSelect");
	
	// 地区选择菜单
	$areaSelect.lSelect({
		url: "${base}/admin/area!ajaxArea.action"// AJAX数据获取url
	});
	// 表单验证
	<#-->$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"agencyReady.companyName":{
				required:true
			},
			"agencyReady.agencydbid":{
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
				remote: "user!checkPhone.action"
			},
			"agencyReady.linkman":{
				required:true
			},
			"agencyReady.linkmanMobile": {
				required: true,
				phone: true
			},
			"agencyReady.capital":{
				required:true,
				digits: true
			},
			"agencyReady.upassword": {
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
			"agencyReady.companyName":{
				required:"请输入对接服务商名称"
			},
			"agencyReady.agencydbid":{
				required:"请选择担保服务商"
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
			"agencyReady.linkman":{
				required:"请输入联系人姓名"
			},
			"agencyReady.linkmanMobile": {
				required:"请输入联系人手机号",
				phone: "联系人手机号格式不正确"
			},
			"agencyReady.capital":{
				required:"请输入注册资本",
				digits: "注册资本金额为正数"
			},
			
			"agencyReady.upassword": {
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
	});-->

});
</script>
</head>
<body class="input admin">
	<div class="bar">
		添加对接服务商
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
				<li>
					<input type="button" value="工商注册信息" hidefocus />
				</li>
				<li>
					<input type="button" value="网站账号信息" hidefocus />
				</li>
			</ul>
		<form id="validateForm" class="form" action="<#if !isAddAction>agency!readyUpdate.action<#else>agency!readySave.action</#if>" enctype="multipart/form-data" method="post">
			<input type = "hidden" name = "id" value = "${id}">
			<table class="inputTable tabContent">
				<tr>
					<th>
						对接服务商名称:
					</th>
					<td>
						<#if !isAddAction>
							${(agencyReady.companyName)!}
						<#else>
							<input type="text" name="agencyReady.companyName" class="formText" maxlength="50" value="${(agencyReady.companyName)!}" /><font color="red">*</font>
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						所在地区:
					</th>
					<td>
						  <input type="text" id="areaSelect" name="areaId" class="hidden"/><font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>
						联系电话:
					</th>
					<td>
						<input type="text" name="user.phone" class="formText" value="${(agencyReady.contactPhone)!}" /><font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>
						联系地址:
					</th>
					<td>
						<input type="text" name="agencyReady.contactAddress" maxlength="100" class="formText" style="width:375px" value="${(agencyReady.contactAddress)!}" />
					</td>
				</tr>
				<tr>
					<th>
						电子邮箱:
					</th>
					<td>
						<input type="text" name="user.email" class="formText"  maxlength="100" value="${(agencyReady.uemail)!}" /><font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>
						联系人姓名:
					</th>
					<td>
						<input type="text" name="agencyReady.linkman" class="formText"  maxlength="50" value="${(agencyReady.linkman)!}" /><font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>
						联系人手机:
					</th>
					<td>
						<input type="text" name="agencyReady.linkmanMobile" class="formText" maxlength="11" value="${(agencyReady.linkmanMobile)!}" /><font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>
						主营业务:
					</th>
					<td>
						<textarea type="text" name="agencyReady.mainBusiness" class="formTextarea">${(agencyReady.mainBusiness)!}</textarea>
					</td>
				</tr>
				<tr>
					<th>
						备注:
					</th>
					<td>
						<textarea type="text" name="agencyReady.remark" class="formTextarea">${(agencyReady.remark)!}</textarea>
					</td>
				</tr>
			</table>
			
			
			<table class="inputTable tabContent">
				<tr>
					<th>
						法人姓名:
					</th>
					<td>
						<input type="text" name="agencyReady.urealname" class="formText" maxlength="50" value="${(agencyReady.urealname)!}" />
					</td>
				</tr>
				<tr>
					<th>
						法人身份证上传:
					</th>
					<td>
						<input type="file" name="cardPic1"  class="fileListClass"/><#if !isAddAction && agencyReady.cardPic1!><a href="${imgUrl}${agencyReady.cardPic1}" target="_blank">查看</a></#if>
					</td>
				</tr>
				<#--<tr>
					<th>
						法人身份证反面:
					</th>
					<td>
						<input type="file" name="cardPic2"  class="fileListClass"/><#if !isAddAction && agencyReady.cardPic2!><a href="${imgUrl}${agencyReady.cardPic2}" target="_blank">查看</a></#if>
					</td>
				</tr>-->
				<tr>
					<th>
						营业执照号码:
					</th>
					<td>
						<input type="text" name="agencyReady.privateCharter" class="formText" maxlength="80" value="${(agencyReady.privateCharter)!}" />
					</td>
				</tr>
				<tr>
					<th>
						营业执照上传:
					</th>
					<td>
						<input type="file" name="privateCharterImg"  class="fileListClass"/><#if !isAddAction && agencyReady.privateCharterImg!><a href="${imgUrl}${agencyReady.privateCharterImg}" target="_blank">查看</a></#if>
					</td>
				</tr>
				<tr>
					<th>
						税务登记证号:
					</th>
					<td>
						<input type="text" name="agencyReady.taxRegistration" class="formText"  maxlength="80" value="${(agencyReady.taxRegistration)!}" />
					</td>
				</tr>
				<tr>
					<th>
						税务登记证上传:
					</th>
					<td>
						<input type="file" name="privateTaxImg"  class="fileListClass"/><#if !isAddAction && agencyReady.privateTaxImg!><a href="${imgUrl}${agencyReady.privateTaxImg}" target="_blank">查看</a></#if>
					</td>
				</tr>
				
				<tr>
					<th>
						机构代码证号:
					</th>
					<td>
						<input type="text" name="agencyReady.organization" class="formText" maxlength="80" value="${(agencyReady.organization)!}" />
					</td>
				</tr>
				<tr>
					<th>
						机构代码证上传:
					</th>
					<td>
						<input type="file" name="privateOrganizationImg"  class="fileListClass"/><#if !isAddAction && agencyReady.privateOrganizationImg!><a href="${imgUrl}${agencyReady.privateOrganizationImg}" target="_blank">查看</a></#if>
					</td>
				</tr>
				<tr>
					<th>
						开户许可证上传:
					</th>
					<td>
						<input type="file" name="accountLicenceImg"  class="fileListClass"/><#if !isAddAction && agencyReady.accountLicenceImg!><a href="${imgUrl}${agencyReady.accountLicenceImg}" target="_blank">查看</a></#if>
					</td>
				</tr>
				<tr>
					<th>
						合同签章上传:
					</th>
					<td>
						<input type="file" name="signImage"  class="fileListClass"/><#if !isAddAction && agencyReady.signImage!><a href="${imgUrl}${agencyReady.signImage}" target="_blank">查看</a></#if>
					</td>
				</tr>
				
				
				<#--><tr>
					<th>
						企业类型:
					</th>
					<td>
						 <select  name="agencyReady.enterprise">
				           	 <option value="">请选择机构类型</option>
					      	 <@listing_childlist sign="institutional_type"; listingList>
								<#list listingList as listing>
									<option value="${listing.keyValue}" <#if listing.keyValue == (agencyReady.enterprise)! >selected</#if> >${substring(listing.name, 24, "...")}</a>
								</option>
								</#list>
							</@listing_childlist>
			            </select>
					</td>
				</tr>-->
				<tr>
					<th>
						成立日期:
					</th>
					<td>
						<input type="text" name="agencyReady.establishDate" class="formText" value="${(agencyReady.establishDate)!}" onclick="WdatePicker()"  style="width:75px"/>
					</td>
				</tr>
				<tr>
					<th>
						营业期限:
					</th>
					<td><input type="text" name="agencyReady.businessStart" class="formText" value="${(agencyReady.businessStart)!}" onclick="WdatePicker()"  style="width:75px"/> - 
					    <input type="text" name="agencyReady.businessEnd" class="formText" value="${(agencyReady.businessEnd)!}" onclick="WdatePicker()"  style="width:75px"/>
					</td>
				</tr>
				<tr>
					<th>
						注册地址:
					</th>
					<td>
						<input type="text" name="agencyReady.address" class="formText"  maxlength="80" value="${(agencyReady.address)!}" />
					</td>
				</tr>
				<tr>
					<th>
						注册资本:
					</th>
					<td>
						<input type="text" name="agencyReady.capital" class="formText" maxlength="20"  value="${(agencyReady.capital)!}" /><font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>
						经营范围:
					</th>
					<td>
						<textarea name="agencyReady.scope" class="formTextarea" >${(agencyReady.scope)!}</textarea>
					</td>
				</tr>
				
			</table>
			
			
			<table class="inputTable tabContent">
				<tr>
					<th>
						对接服务商登录名:
					</th>
					<td>
					<#if !isAddAction>
						${agencyReady.uusername}
					<#else>		
						<input type="text" name="user.username" class="formText" value=""  maxlength="20"/><font color="red">*</font>
					</#if>
					</td>
				</tr>
				<tr>
					<th>
						登录密码:
					</th>
					<td>
						<input type="password" name="agencyReady.upassword" id = "upassword"  maxlength="50" class="formText" value="" /><font color="red">*</font>
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
				
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>