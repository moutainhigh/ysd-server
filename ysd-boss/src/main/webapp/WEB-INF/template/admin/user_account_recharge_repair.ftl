<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>补单-针对用户充值记录【暂时没用】 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<#assign flag="">
<#if userAccountRecharge.rechargeInterface.name=='国付宝'>
	<#assign flag="doSignleGfb">
<#elseif userAccountRecharge.rechargeInterface.name=='新生支付'>
	<#assign flag="doSignleXszf">
</#if>
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	 
	var $queryButton = $("#queryButton");//核查
	var $disposeButton = $("#disposeButton");//处理
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
				minlength: 5,
				maxlength: 100
			}
		},
		messages: {
			"userAccountRecharge.verifyRemark": {
				required: "请填写备注信息",
				minlength: "最少5个字符",
				maxlength: "最多100个字符"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	
	//核查
	$queryButton.click(function(){
	var $q_id = $("#query_id");
	var $d_id = $("#dispose_id");
		$.ajax({
			url: "source_record!${flag}.action",
			data:{"id":${userAccountRecharge.id} },
			type: "POST",
			dataType: "json",
				beforeSend: function() {
					$q_id.html('查询中......');
					$d_id.html('查询中......');
				},
			success: function(data) {
				$.message({type: data.status, content: data.message});
				if(data.status=="success"){
					$q_id.html(data.portData);
					$d_id.html(data.compareStatus);
				}else{
					$q_id.html(data.message);
					$d_id.html('无法处理');
				}
			}
		});
	});

	//处理
	$disposeButton.click(function(){
		var $this = $(this);
		$.dialog({type: "warn", content: "您确定处理此条数据吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: disposeData});
		function disposeData() {
			$.ajax({
				url: "source_record!doSignle.action",
				type: "POST",
				dataType: "json",
				success: function(data) {
					$.message({type: data.status, content: data.message});
				}
			});
		}
		return false;
	});
});
</script>
</head>
<body class="input admin">
	<div class="bar">
		补单-针对充值记录 
	</div>
	<div class="body">
		<form id="validateForm" action="user_account_recharge!update.action" method="post">
			<input type="hidden" name="id" value="${userAccountRecharge.id}" />
			<table class="inputTable tabContent">
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
						费用：
					</th>
					<td>
						${userAccountRecharge.fee!?string.currency}
					</td>
				</tr>
				<tr>
					<th>
						实际到账：
					</th>
					<td>
						${userAccountRecharge.money?string.currency}
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
								<font color="green">处理中</font>
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
				<#if userAccountRecharge.type=='1'>
				
				<tr>
					<th>
						核对结果:
					</th>
					<td>
						<div id = "query_id"></div>
					</td>
				</tr>
				<tr>
					<th>
						处理结果:
					</th>
					<td>
						<div id = "dispose_id"></div>
					</td>
				</tr>
					<tr>
						<th>
							操作:
						</th>
						<td>
							<input type = "button" id="queryButton" class="formButton" value="核对" />
							<input type = "button" id="disposeButton" class="formButton" value="处理" />
						</td>
					</tr>
				</#if>
				<tr>
					<th>
						充值时间/IP:
					</th>
					<td>
						${(userAccountRecharge.createDate)!?string("yyyy-MM-dd HH:mm:ss")}/${(userAccountRecharge.ipUser)!}
					</td>
				</tr>
				<#if userAccountRecharge.adminVerify>
					<tr>
						<th>
							审核备注:
						</th>
						<td>
							${(userAccountRecharge.verifyRemark)!}
						</td>
					</tr>
					<tr>
						<th>
							审核人:
						</th>
						<td>
							${(userAccountRecharge.adminVerify.name)!}
						</td>
					</tr>
					
					<tr>
						<th>
							审核时间/IP:
						</th>
						<td>
							${(userAccountRecharge.modifyDate)!?string("yyyy-MM-dd HH:mm:ss")}/${(userAccountRecharge.ipVerify)!}
						</td>
					</tr>
				</#if>
				
				<#if userAccountRecharge.type='0' && userAccountRecharge.status=2>
				<tr>
					<th>审核此充值信息</th>
					<td >
						
					</td>
				</tr>
				
					<tr>
						<th>
							状态:
						</th>
						<td>
							<input type="radio" name="userAccountRecharge.status" value="1" checked>充值成功
							<input type="radio" name="userAccountRecharge.status" value="2">充值失败
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
			<div class="buttonArea">
				<#if userAccountRecharge.type='0' && userAccountRecharge.status = 2>
					<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				</#if>
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>