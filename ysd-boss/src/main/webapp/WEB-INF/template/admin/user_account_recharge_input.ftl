<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>审核充值记录 </title>
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
			"userAccountRecharge.verifyRemark": {
				required: true,
				maxlength: 100
			}
		},
		messages: {
			"userAccountRecharge.verifyRemark": {
				required: "请填写备注信息",
				maxlength: "最多100个字符"
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
		审核充值记录 
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="user_account_recharge!update.action" method="post">
			<input type="hidden" name="id" value="${userAccountRecharge.id}" />
			<input type="hidden" name="rechargeInterfaceId" value="${rechargeInterfaceId}" />
			<ul id="tab" class="tab">
				<li>
					<input type="button" value="充值信息" hidefocus />
				</li>
				<li>
					<input type="button" value="用户资料" hidefocus />
				</li>
				<#if userAccountRecharge.adminOperator!>
					<li>
						<input type="button" value="管理员" hidefocus />
					</li>
				</#if>
			<#if userAccountRecharge.rechargeInterface! && (userAccountRecharge.rechargeInterface.id)! =0 && userAccountRecharge.offLineAccount! && userAccountRecharge.offLineAccount.isEnabled >
				<li>
					<input type="button" value="线下充值账户" hidefocus />
				</li>
			</#if>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						用户名:
					</th>
					<td>
						${(userAccountRecharge.user.username)!}
					</td>
				</tr>
				<tr>
					<th>
						订单号: 
					</th>
					<td>
						${userAccountRecharge.tradeNo}
					</td>
				</tr>
				<tr>
					<th>
						充值类型 :
					</th>
					<td>
						<#if userAccountRecharge.type == 1>线上充值<#else><font color="red">线下充值</font></#if>
					</td>
				</tr>
				<tr>
					<th>
						支付方式:
					</th>
					<td>
						${userAccountRecharge.rechargeInterface.name}
					</td>
				</tr>
				<tr>
					<th>
						充值总额：
					</th>
					<td>
						${userAccountRecharge.money!?string.currency}
					</td>
				</tr>
				<tr>
					<th>
						手续费：
					</th>
					<td>
						${(userAccountRecharge.fee?string.currency)!'0.00'}
					</td>
				</tr>
				<tr>
					<th>
						奖励：
					</th>
					<td>
						${(userAccountRecharge.reward?string.currency)!'0.00'}
					</td>
				</tr>
				<tr>
					<th>
						实际到账：
					</th>
					<td>
						${(userAccountRecharge.money-userAccountRecharge.fee+userAccountRecharge.reward)?string.currency}
					</td>
				</tr>
				<tr>
					<th>
						备注
					</th>
					<td>
						${(userAccountRecharge.remark)!}
					</td>
				</tr>
				<tr>
					<th>
						状态：
					</th>
					<td>
						<#if userAccountRecharge.status = 2>
							<#if userAccountRecharge.type ='0'>
								<font color="green">等待审核</font>
							<#elseif userAccountRecharge.type ='1'>
								<font color="red">审核中</font>
							<#else>
								未知
							</#if>
						<#elseif userAccountRecharge.status = 1>
							<font color="blue">充值成功 </font>
						<#elseif userAccountRecharge.status = 0>
							<font color="red">充值失败</font>
						<#else>
							未知
						</#if>
					</td>
				</tr>
				
				<tr>
					<th>
						充值时间/IP:
					</th>
					<td>
						${(userAccountRecharge.createDate)!?string("yyyy-MM-dd HH:mm:ss")}/${(userAccountRecharge.ipUser)!}
					</td>
				</tr>
				
				
				<#if rechargeInterfaceId! && userAccountRecharge.status=2>
					<tr>
						<th>
							状态:
						</th>
						<td>
							<input type="radio" name="userAccountRecharge.status" value="1" checked><span>充值成功</span>
							<input type="radio" name="userAccountRecharge.status" value="0"><span>充值失败</span>
						</td>
					</tr>
					<tr>
						<th>
							审核备注:
						</th>
						<td>
							<textarea name="userAccountRecharge.verifyRemark" class="formTextarea"></textarea>
							<label class="requireField">*</label>
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
						${(userAccountRecharge.user.username)!}
					</td>
				</tr>
				<tr>
					<th>
						真实姓名:
					</th>
					<td>
						${(userAccountRecharge.user.realName)!}
					</td>
				</tr>
				<tr>
					<th>
						邮箱:
					</th>
					<td>
						${(userAccountRecharge.user.email)!}
					</td>
				</tr>
				<tr>
					<th>
						性别:
					</th>
					<td>
					<#if (userAccountRecharge.user.sex)! =1>男<#elseif (userAccountRecharge.user.sex)! =2><#else>未知</#if>
					</td>
				</tr>
				<tr>
					<th>
						证件:
					</th>
					<td>
						${(userAccountRecharge.user.cardId)!}
					</td>
				</tr>
				<tr>
					<th>
						电话:
					</th>
					<td>
						${(userAccountRecharge.user.phone)!}
					</td>
				</tr>
				<tr>
					<th>
						余额:
					</th>
					<td>
						${(userAccountRecharge.user.account.ableMoney)!?string.currency}
					</td>
				</tr>
			</table>
			
			<#if userAccountRecharge.adminOperator!>
				<table class="inputTable tabContent">					
					<tr>
						<th>
							线下充值管理员:
						</th>
						<td>
							${(userAccountRecharge.adminOperator.username)!}
						</td>
					</tr>
					
					<tr>
						<th>
							线下充值时间/IP:
						</th>
						<td>
							${(userAccountRecharge.createDate)!?string("yyyy-MM-dd HH:mm:ss")}/${(userAccountRecharge.ipOperator)!}
						</td>
					</tr>
					<tr>
						<th>
							备注:
						</th>
						<td>
							${(userAccountRecharge.remark)!}
						</td>
					</tr>
				</table>
			</#if>
			<#if userAccountRecharge.adminVerify!>
					<table class="inputTable tabContent">
					<tr>
						<th>
							线下充值审核管理员:
						</th>
						<td>
							${(userAccountRecharge.adminVerify.username)!}
						</td>
					</tr>
					
					<tr>
						<th>
							线下充值审核时间/IP:
						</th>
						<td>
							${(userAccountRecharge.rechargeDate)!?string("yyyy-MM-dd HH:mm:ss")}/${(userAccountRecharge.ipVerify)!}
						</td>
					</tr><tr>
						<th>
							线下充值审核备注:
						</th>
						<td>
							${(userAccountRecharge.verifyRemark)!}
						</td>
					</tr>
					</table>
			</#if>
			
			<#if userAccountRecharge.rechargeInterface! && (userAccountRecharge.rechargeInterface.id)! =0 && userAccountRecharge.offLineAccount! && userAccountRecharge.offLineAccount.isEnabled>
					<table class="inputTable tabContent">
						<tr>
							<th>
								账号:
							</th>
							<td>
								${(userAccountRecharge.offLineAccount.account)!}
							</td>
						</tr>
						<tr>
							<th>
								姓名:
							</th>
							<td>
								${(userAccountRecharge.offLineAccount.legalName)!}
							</td>
						</tr>
						<tr>
							<th>
								银行:
							</th>
							<td>
								<@listing_childname sign="recharge_bank" key_value="${(userAccountRecharge.offLineAccount.bankId)!}"; bank>
									${bank}
								</@listing_childname>
							</td>
						</tr>
						<tr>
							<th>
								支行名称:
							</th>
							<td>
								${(userAccountRecharge.offLineAccount.branch)!}
							</td>
						</tr>
					</table>
			</#if>
			<div class="buttonArea">
				<#if rechargeInterfaceId! && userAccountRecharge.status = 2>
					<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				</#if>
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>