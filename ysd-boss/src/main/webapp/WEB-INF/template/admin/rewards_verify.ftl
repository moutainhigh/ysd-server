<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<#assign str='' />
<#if flag =='0'>
	<#assign str = '用户余额调整' />
<#elseif flag =='1'>
	<#assign str = '现金奖励' />
<#elseif flag =='2'>
	<#assign str = '资金转入' />
<#elseif flag =='3'>
	<#assign str = '红包奖励' />
<#elseif flag =='4'>
	<#assign str = '红包扣除' />
<#elseif flag =='5'>
	<#assign str = '增加体验金' />
<#elseif flag =='6'>
	<#assign str = '扣除体验金' />
</#if>
<title>${str}记录 </title>
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
			"rewards.remark": {
				required: true,
				maxlength: 200
			}
		},
		messages: {
			"rewards.remark": {
				required: "请填写备注信息",
				maxlength: "最多200个字符"
			}
		},
		submitHandler: function(form) {
			var temp='<font color="red">'+$(":radio:checked + span").text()+'</font>'; 
			$.dialog({type: "warn", content: "此条记录将被改写"+temp, ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			function right(){
				 form.submit();
			}
		}
	});
	
});
</script>
</head>
<body class="input admin">
	<div class="bar">
		${str}记录 
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="rewards!doVerify.action" method="post">
			<input type="hidden" name="id" value="${rewards.id}" />
			<ul id="tab" class="tab">
				<li>
					<input type="button" value="审核信息" hidefocus />
				</li>
				<li>
					<input type="button" value="用户资料" hidefocus />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						用户名: 
					</th>
					<td>
						${rewards.user.username}
					</td>
				</tr>
				<tr>
					<th>
						操作类型 :
					</th>
					<td>
						${str}
					</td>
				</tr>
				<tr>
					<th>
						金额：
					</th>
					<td>
						${rewards.money!?string.currency}
					</td>
				</tr>
				<tr>
					<th>
						备注：
					</th>
					<td>
						${rewards.remark}
					</td>
				</tr>
				<#if rewards.status ==2>			
				<tr>
					<th>
						审核状态:
					</th>
					<td>
						<input type="radio" name="rewards.status" value="1" checked><span>通过</span>
						<input type="radio" name="rewards.status" value="0"><span>拒绝</span>
					</td>
				</tr>
				<tr>
					<th>
						审核备注:
					</th>
					<td>
						<textarea name="rewards.verifyRemark" class="formTextarea"></textarea>
						<label class="requireField">*</label>
					</td>
				</tr>
				<#else>
				<tr>
					<th>
						审核状态:
					</th>
					<td>
						<#if rewards.status == 1>通过<#else>拒绝</#if>
						
					</td>
				</tr>
				<tr>
					<th>
						审核备注:
					</th>
					<td>
						${rewards.verifyRemark}
					</td>
				</tr>
				</#if>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<th>
						用户名:
					</th>
					<td>
						${(rewards.user.username)!}
					</td>
				</tr>
				<tr>
					<th>
						真实姓名:
					</th>
					<td>
						${(rewards.user.realName)!}
					</td>
				</tr>
				<tr>
					<th>
						邮箱:
					</th>
					<td>
						${(rewards.user.email)!}
					</td>
				</tr>
				<tr>
					<th>
						性别:
					</th>
					<td>
					<#if (rewards.user.sex)! =1>男<#elseif (rewards.user.sex)! =2><#else>未知</#if>
					</td>
				</tr>
				<tr>
					<th>
						证件:
					</th>
					<td>
						${(rewards.user.cardId)!}
					</td>
				</tr>
				<tr>
					<th>
						电话:
					</th>
					<td>
						${(rewards.user.phone)!}
					</td>
				</tr>
				<tr>
					<th>
						余额:
					</th>
					<td>
						${(rewards.user.account.ableMoney?string.currency)!}
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<#if  rewards.status = 2>
					<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				</#if>
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>