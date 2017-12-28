<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#assign flag=false />
<#if type==0>
	<#assign flag=false />
<#elseif type==1>
	<#assign flag=true />
</#if>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>用户<#if !flag>费用扣除<#else>添加奖励</#if></title>
<meta name="Author" content="QMD++ Team" />
<meta name="Copyright" content="QMD++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
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
			<#if !flag>
				"userAccountDetail.type": {
					required: true
				},
			</#if>
			"userAccountDetail.money": {
				required: true,
				positive: true
			},
			"userAccountDetail.remark": {
				required: true
			}
		},
		messages: {
			"user.username": {
				required: "请填写用户名",
				remote: "用户名不存在"
			},
			<#if !flag>
				"userAccountDetail.type": {
					required: "请选择扣费类型"
				},
			</#if>
			"userAccountDetail.money": {
				required: "请填写金额",
				positive: "请输入合法的正数！"
			},
			"userAccountDetail.remark": {
				required: "请输入备注"
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
	
	var $verifyForm = $("#verifyForm");
	var $submitButton = $("#submitButton");
	$submitButton.click( function() {
		var temp='<font color="red">'+$(":radio:checked + span").text()+'</font>'; 
		$.dialog({type: "warn", content: "认证将被"+temp, ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			function right(){
				 $verifyForm.submit();
			}
	});		

});
</script>
</head>
<body class="input admin">
	<div class="bar">
		<#if !flag>费用扣除<#else>添加奖励</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="account!deductDo.action" method="post">
			<input type = "hidden" name = "type" value="${type}" />
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
						类型: 
					</th>
					<td>
					<#if !flag>
						 <select id="userAccountDetail_type" name="userAccountDetail.type">
						 	<option value="">请选择</option>
							  <@listing_childlist sign="account_type"; listingList>
								<#list listingList as listing>
									<option value="${listing.keyValue},${listing.name}" >${substring(listing.name, 24, "...")}</a>
									</option>
								</#list>
							  </@listing_childlist>
                          </select>
                          <label class="requireField">*</label>
                      <#else>
                      	<input type="hidden" name="userAccountDetail.type" class="formText" value="offline_reward,线下奖励"/>线下奖励
                      </#if>
					</td>
				</tr>
				<tr>
					<th>
						金额: 
					</th>
					<td>
						<input type="text" name="userAccountDetail.money" class="formText" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						备注: 
					</th>
					<td>
						<textarea name="userAccountDetail.remark" class="formTextarea"></textarea>
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