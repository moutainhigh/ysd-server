<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>对接服务商审核 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $tab = $("#tab");
	var $verifyForm = $("#verifyForm");
	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
	var $submitButton = $("#submitButton");
	$submitButton.click( function() {
		var temp='<font color="red">'+$(":radio:checked + span").text()+'</font>'; 
		$.dialog({type: "warn", content: "认证将被&nbsp;&nbsp;&nbsp;"+temp, ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			function right(){
				 $verifyForm.submit();
			}
	});	
});
</script>
</head>
<body class="input admin">
	<div class="bar">
		审核对接服务商【${agencyReady.uusername}】的信息
	</div>
	<div class="body">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="基本信息" hidefocus />
			</li>
			<li>
				<input type="button" value="工商注册信息" hidefocus />
			</li>
		</ul>
		<form id="verifyForm" class="form" action="agency!agencyReadyCheck.action"  method="post">
			<input type = "hidden" name = "id" value = "${agencyReady.id}">
			<table class="inputTable tabContent">
				<tr>
					<th>
						服务商名称:
					</th>
					<td>
						${(agencyReady.companyName)!}
					</td>
				</tr>
				<tr>
					<th>
						服务商登录名:
					</th>
					<td>
						${(agencyReady.uusername)!}
					</td>
				</tr>
				<tr>
					<th>
						所在地区:
					</th>
					<td>
						 ${(agencyReady.areaStore)!}
					</td>
				</tr>
				<tr>
					<th>
						联系电话:
					</th>
					<td>
						${(agencyReady.contactPhone)!}
					</td>
				</tr>
				<tr>
					<th>
						联系地址:
					</th>
					<td>
						${(agencyReady.contactAddress)!}
					</td>
				</tr>
				<tr>
					<th>
						电子邮箱:
					</th>
					<td>
						${(agencyReady.uemail)!}
					</td>
				</tr>
				<tr>
					<th>
						联系人姓名:
					</th>
					<td>
						${(agencyReady.linkman)!}
					</td>
				</tr>
				<tr>
					<th>
						联系人手机:
					</th>
					<td>
						${(agencyReady.linkmanMobile)!}
					</td>
				</tr>
				<tr>
					<th>
						主营业务:
					</th>
					<td>
						${(agencyReady.mainBusiness)!}
					</td>
				</tr>
				<tr>
					<th>
						备注:
					</th>
					<td>
						${(agencyReady.remark)!}
					</td>
				</tr>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<th>
						法人姓名:
					</th>
					<td>
						${(agencyReady.urealname)!}
					</td>
				</tr>
				<tr>
					<th>
						法人身份证:
					</th>
					<td>
						<#if !isAddAction && agencyReady.cardPic1!><a href="${imgUrl}${agencyReady.cardPic1}" target="_blank"><font color="red">查看</font></a></#if>
					</td>
				</tr>
				<#--><tr>
					<th>
						法人身份证反面:
					</th>
					<td>
						<#if !isAddAction && agencyReady.cardPic2!><a href="${imgUrl}${agencyReady.cardPic2}" target="_blank"><font color="red">查看</font></a></#if>
					</td>
				</tr>-->
				<tr>
					<th>
						营业执照号码:
					</th>
					<td>
						${(agencyReady.privateCharter)!}
					</td>
				</tr>
				<tr>
					<th>
						营业执照上传:
					</th>
					<td>
						<#if !isAddAction && agencyReady.privateCharterImg!><a href="${imgUrl}${agencyReady.privateCharterImg}" target="_blank"><font color="red">查看</font></a></#if>
					</td>
				</tr>
				<tr>
					<th>
						税务登记证号:
					</th>
					<td>
						${(agencyReady.taxRegistration)!}
					</td>
				</tr>
				<tr>
					<th>
						税务登记证上传:
					</th>
					<td>
						<#if !isAddAction && agencyReady.privateTaxImg!><a href="${imgUrl}${agencyReady.privateTaxImg}" target="_blank"><font color="red">查看</font></a></#if>
					</td>
				</tr>
				
				<tr>
					<th>
						机构代码证号:
					</th>
					<td>
						${(agencyReady.organization)!}
					</td>
				</tr>
				<tr>
					<th>
						机构代码证上传:
					</th>
					<td>
						<#if !isAddAction && agencyReady.privateOrganizationImg!><a href="${imgUrl}${agencyReady.privateOrganizationImg}" target="_blank"><font color="red">查看</font></a></#if>
					</td>
				</tr>
				<tr>
					<th>
						开户许可证上传:
					</th>
					<td>
						<#if !isAddAction && agencyReady.accountLicenceImg!><a href="${imgUrl}${agencyReady.accountLicenceImg}" target="_blank"><font color="red">查看</font></a></#if>
					</td>
				</tr>
				<tr>
					<th>
						合同签章上传:
					</th>
					<td>
						<#if !isAddAction && agencyReady.signImage!><a href="${imgUrl}${agencyReady.signImage}" target="_blank"><font color="red">查看</font></a></#if>
					</td>
				</tr>
				<tr>
					<th>
						成立日期:
					</th>
					<td>${(agencyReady.establishDate?string("yyyy-MM-dd"))!}</td>
				</tr>
				<tr>
					<th>
						营业期限:
					</th>
					<td>${(agencyReady.businessStart?string("yyyy-MM-dd"))!} - ${(agencyReady.businessEnd?string("yyyy-MM-dd"))!'长期'}</td>
				</tr>
				<tr>
					<th>
						注册地址:
					</th>
					<td>
						${(agencyReady.address)!}
					</td>
				</tr>
				<tr>
					<th>
						注册资本:
					</th>
					<td>
						${(agencyReady.capital?string.currency)!}
					</td>
				</tr>
				<tr>
					<th>
						经营范围:
					</th>
					<td>
						${(agencyReady.scope)!}
					</td>
				</tr>
			</table>
			<table class="inputTable">
				<tr>
					<th>
						审核：
					</th>
					<td>
						<label><input type="radio" name="agencyReady.status" class="verifyValue_class" value="1" checked><span>通过</span></label>
						<label><input type="radio" name="agencyReady.status" class="verifyValue_class"  value="0" ><span>拒绝</span></label>
					</td>
				</tr>
				<tr>
					<th>
						备注
					</th>
					<td>
						<textarea class="formTextarea"  name ="remark" ></textarea>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="button" id="submitButton" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>