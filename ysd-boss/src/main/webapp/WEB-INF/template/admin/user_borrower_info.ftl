<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>借款人详情 </title>
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

});
</script>
</head>
<body class="input admin">
	<div class="bar">
		借款人信息
	</div>
	<div class="body">
	
			<table class="inputTable tabContent">
				<tr>
					<th>
						真实姓名:
					</th>
					<td>
						${(user.realName)!}
					</td>
				</tr>
				<#if user.birthday!>
				<tr>
					<th>
						生日:
					</th>
					<td>
						${user.birthday?string("yyyy-MM-dd")}
					</td>
				</tr>
				</#if>
				<tr>
					<th>
						身份证号码:
					</th>
					<td>
						${(user.cardId)!}
					</td>
				</tr>
				<tr>
					<th>
						身份证正面: 
					</th>
					<td>
						<#if user.cardPic1!><a href="<@imageUrlDecode imgurl="${user.cardPic1}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.cardPic1}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件正面图片;</font></#if>
					</td>
				</tr>
				<tr>
					<th>
						身份证反面: 
					</th>
					<td>
						<#if user.cardPic2!><a href="<@imageUrlDecode imgurl="${user.cardPic2}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.cardPic2}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件反面图片;</font></#if>
					</td>
				</tr>
				<tr>
					<th>
						联系号码: 
					</th>
					<td>
						${(user.phone)!}
					</td>
				</tr>
				<tr>
					<th>
						婚姻:
					</th>
					<td>
						<@listing_childname sign="user_marry" key_value="${user.userInfo.marry}"; type>
								${type}
						</@listing_childname>
					</td>
				</tr>
				<tr>
					<th>
						联系地址:
					</th>
					<td>
						${(user.address)!}
					</td>
				</tr>
				
				<tr>
					<th>
						工作单位:
					</th>
					<td>
						${(user.userinfo.companyName)!}
					</td>
				</tr>
				<tr>
					<th>
						担任职位:
					</th>
					<td>
						<@listing_childname sign="user_company_office" key_value="${user.userInfo.companyOffice}"; type>
									${type}
							</@listing_childname>
					</td>
				</tr>
				<tr>
					<th>
						文化程度:
					</th>
					<td>
						<@listing_childname sign="user_education" key_value="${user.userInfo.educationRecord}"; type>
									${type}
							</@listing_childname>
					</td>
				</tr>
				<tr>
					<th>
						开户银行:
					</th>
					<td>
						<#list user.accountBankSet as bank>
							<@listing_childname sign="account_bank" key_value="${bank.bankId}"; type>
								${type}&nbsp;&nbsp;
							</@listing_childname>
						</#list>
					</td>
				</tr>
				
				<tr>
					<th>
						银行卡号:
					</th>
					<td>
						<#list user.accountBankSet as bank>
							${bank.bankId}&nbsp;&nbsp;
						</#list>
					</td>
				</tr>
				
				<tr>
					<th>
						开户行:
					</th>
					<td>						
						<#list user.accountBankSet as bank>
							${bank.branch}&nbsp;&nbsp;
						</#list>
					</td>
				</tr>
				
			</table>
			<div class="buttonArea">
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
	</div>
</body>
</html>