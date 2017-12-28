<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><#if verifyType==1>个人账户认证审核<#elseif verifyType==2>借款人审核<#elseif verifyType==3>VIP审核<#elseif verifyType==4>企业资料认证审核<#elseif verifyType==5>个人借款人资质认证<#elseif verifyType==6>企业借款人资质认证<#elseif verifyType==7>手机认证审核<#else>用户信息审核</#if></title>
<meta name="Author" content="QMD++ Team" />
<meta name="Copyright" content="QMD++" />
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
	var $verifyForm = $("#verifyForm");
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
<body class="input">
	<div class="bar">
		<#if verifyType==1>个人账户认证审核<#elseif verifyType==2>借款人审核<#elseif verifyType==3>VIP审核<#elseif verifyType==4>企业资料认证审核<#elseif verifyType==5>个人借款人资质认证<#elseif verifyType==6>企业借款人资质认证<#elseif verifyType==7>手机认证审核<#else>用户信息审核</#if>
	</div>
	<div class="body">
		<form id="verifyForm" action="user!verifycheck.action" method="post">
			<input type="hidden" name="id" value="${user.id}">
			<input type="hidden" name="verifyType" value="${verifyType}">
					
			<input type="hidden" name="pager.pageNumber" id="pageNumber" value="${(pager.pageNumber)!}" />
			<input type="hidden" name="pager.orderBy" id="orderBy" value="${(pager.orderBy)!}" />
			<input type="hidden" name="pager.order" id="order" value="${(pager.order)!}" />
			<input type="hidden" name="pager.searchBy" id="order" value="${(pager.searchBy)!}" />
			<input type="hidden" name="pager.keyword" id="order" value="${(pager.keyword)!}" />
			<table class="inputTable">
			<#if verifyType==1><!--实名认证审核-->
					<tr>
						<th>
							ID: 
						</th>
						<td>
							${(user.id)!}
						</td>
					</tr>
			
					<tr>
						<th>
							用户名/手机: 
						</th>
						<td>
							<input type="text" name="user.phone" class="formText" value="${(user.phone)!}" />
						</td>
					</tr>
					<tr>
						<th>
							会员类型: 
						</th>
						<td>
							<#if user.memberType==0>个人<#elseif user.memberType==1>企业</#if>&nbsp;/
							<#if user.typeId==0>投资人<#elseif user.typeId==1>借款人</#if>
						</td>
					</tr>
					
					<tr>
						<th>
							真实姓名: 
						</th>
						<td>
							<input type="text" name="user.realName" class="formText" value="${(user.realName)!}" />
						</td>
					</tr>
					<tr>
						<th>
							身份证号: 
						</th>
						<td>
							<input type="text" name="user.cardId" class="formText" value="${(user.cardId)!}" />
						</td>
					</tr>
					<tr>
						<th>
							设置:
						</th>
						<td>
							<label>
								<@checkbox name="user.isEnabled" value="${(user.isEnabled)!true}" />启用
							</label>
							<label>
								<@checkbox name="user.isLock" value="${(user.isLock)!true}" />锁定
							</label>
						</td>
					</tr>
					
				
			<#elseif verifyType==2>	<!--借款人审核-->
			
					<tr>
						<th>
							用户名:
						</th>
						<td>
							${user.username}
						</td>
					</tr>
					<tr>
						<th>
							真实姓名: 
						</th>
						<td>
							${user.realName}
						</td>
					</tr>
					<tr>
						<th>
							性别: 
						</th>
						<td>
							<#if user.sex =="1">男<#elseif user.sex="2">女<#else>未知</#if>
						</td>
					</tr>
					<tr>
						<th>
							借款理由: 
						</th>
						<td>
							${(user.applyBorrower.borrowReason)!'无理由'}
						</td>
					</tr>
					<tr>
						<th>
							抵押类型: 
						</th>
						<td>
							<#if (user.applyBorrower.pledgeStatus)! && user.applyBorrower.pledgeStatus==1>${user.applyBorrower.pledgeType}<#else>没有</#if>
						</td>
					</tr>
					
					<tr>
						<th>
							资料: 
						</th>
						<td>
							<#if (user.applyBorrower.informationUrl)!><a href = "${base}${(user.applyBorrower.informationUrl)!}">下载</a><#else>没有上传资料 </#if>
						</td>
					</tr>
					
			<#elseif verifyType==3><!--VIP审核-->
				<tr>
						<th>
							用户名
						</th>
						<td>
							${user.username}
						</td>
					</tr>
					<tr>
						<th>
							真实姓名: 
						</th>
						<td>
							${user.realName}
						</td>
					</tr>
					<tr>
						<th>
							性别: 
						</th>
						<td>
							<#if user.sex =="1">男<#elseif user.sex="2">女<#else>未知</#if>
						</td>
					</tr>
					<tr>
						<th>
							客服名字: 
						</th>
						<td>
							${(user.applyVip.examinerName)!}
						</td>
					</tr>
					<tr>
						<th>
							说明: 
						</th>
						<td>
							${(user.applyVip.remarker)!}
						</td>
					</tr>
			<#elseif verifyType==4><!--企业资料实名认证 -->
					<tr>
						<th>
							企业名称:
						</th>
						<td>
							${user.userInfo.privateName}
						</td>
					</tr>
					<tr>
						<th>
							法人姓名:
						</th>
						<td>
							${user.realName}
						</td>
					</tr>
					<tr>
						<th>
							企业登记号: 
						</th>
						<td>
							${user.userInfo.registration}
						</td>
					</tr>
					
					<tr>
						<th>
							税务登记号: 
						</th>
						<td>
							${user.userInfo.taxRegistration}
						</td>
					</tr>
					<tr>
						<th>
							组织机构号: 
						</th>
						<td>
							${user.userInfo.organization}
						</td>
					</tr>
					<tr>
						<th>
							营业执照: 
						</th>
						<td>
							<#if user.userInfo.privateCharterImg!><a href="<@imageUrlDecode imgurl="${user.userInfo.privateCharterImg}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.userInfo.privateCharterImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件正面图片;</font></#if>
						</td>
					</tr>
					<tr>
						<th>
							税务登记证: 
						</th>
						<td>
							<#if user.userInfo.privateTaxImg!><a href="<@imageUrlDecode imgurl="${user.userInfo.privateTaxImg}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.userInfo.privateTaxImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件正面图片;</font></#if>
						</td>
					</tr>
					<tr>
						<th>
							组织机构代码证: 
						</th>
						<td>
							<#if user.userInfo.privateOrganizationImg!><a href="<@imageUrlDecode imgurl="${user.userInfo.privateOrganizationImg}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.userInfo.privateOrganizationImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件正面图片;</font></#if>
						</td>
					</tr>
					<tr>
						<th>
							开户许可证: 
						</th>
						<td>
							<#if user.userInfo.accountLicenceImg!><a href="<@imageUrlDecode imgurl="${user.userInfo.accountLicenceImg}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.userInfo.accountLicenceImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件正面图片;</font></#if>
						</td>
					</tr>
				<#elseif verifyType==5><!--个人借款人资质认证-->
					<tr>
						<th>
							用户名:
						</th>
						<td>
							${user.username}
						</td>
					</tr>
					<tr>
						<th>
							真实姓名: 
						</th>
						<td>
							${user.realName}
						</td>
					</tr>
					
					<tr>
						<th>
							性别: 
						</th>
						<td>
							<#if user.sex =="1">男<#elseif user.sex="2">女<#else>未知</#if>
						</td>
					</tr>
					<tr>
						<th>
							手机号码: 
						</th>
						<td>
							${user.phone}
						</td>
					</tr>
					<tr>
						<th>
							身份证: 
						</th>
						<td>
							<#if user.cardPic1!><a href="<@imageUrlDecode imgurl="${user.cardPic1}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.cardPic1}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件正面图片;</font></#if>
							<#if user.cardPic2!><a href="<@imageUrlDecode imgurl="${user.cardPic2}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.cardPic2}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件正面图片;</font></#if>
						</td>
					</tr>
					<tr>
						<th>
							正面免冠照: 
						</th>
						<td>
							<#if user.userInfo.bareheadedImg!><a href="<@imageUrlDecode imgurl="${user.userInfo.bareheadedImg}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.userInfo.bareheadedImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件正面图片;</font></#if>
						</td>
					</tr>
					<tr>
						<th>
							联系地址: 
						</th>
						<td>
							${user.userInfo.address}
						</td>
					</tr>
					<tr>
						<th>
							婚姻状况: 
						</th>
						<td>
							<@listing_childname sign="user_marry" key_value="${user.userInfo.marry}"; type>
									${type}
							</@listing_childname>
						</td>
					</tr>
					<tr>
						<th>
							工作单位: 
						</th>
						<td>
							${user.userInfo.companyName}
						</td>
					</tr>
					<tr>
						<th>
							单位地址: 
						</th>
						<td>
							${user.userInfo.companyAddress}
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
							月均收入: 
						</th>
						<td>
							<@listing_childname sign="user_income" key_value="${user.userInfo.income}"; type>
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
							社保: 
						</th>
						<td>
							<@listing_childname sign="user_shebao" key_value="${user.userInfo.socialInsuranceStatus}"; type>
									${type}
							</@listing_childname>
						</td>
					</tr>
					<tr>
						<th>
							购车: 
						</th>
						<td>
							<@listing_childname sign="user_car" key_value="${user.userInfo.carStatus}"; type>
									${type}
							</@listing_childname>
						</td>
					</tr>
					<tr>
						<th>
							住房条件: 
						</th>
						<td>
							<@listing_childname sign="user_housing" key_value="${user.userInfo.housing}"; type>
									${type}
							</@listing_childname>
						</td>
					</tr>
					
					<tr>
						<th>
							个人介绍: 
						</th>
						<td>
							${user.userInfo.others}
						</td>
					</tr>
					
				<#elseif verifyType==6><!--企业借款人资质认证-->
					<tr>
						<th>
							用户名:
						</th>
						<td>
							${user.username}
						</td>
					</tr>
					<tr>
						<th>
							法人:
						</th>
						<td>
							${user.realName}
						</td>
					</tr>
					<tr>
						<th>
							性别: 
						</th>
						<td>
							<#if user.sex =="1">男<#elseif user.sex="2">女<#else>未知</#if>
						</td>
					</tr>
					<tr>
						<th>
							身份证号: 
						</th>
						<td>
							${user.cardId}<br/>
						</td>
					</tr>
					<tr>
						<th>
							身份证正面/反面: 
						</th>
						<td>
							<#if user.cardPic1!><a href="<@imageUrlDecode imgurl="${user.cardPic1}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.cardPic1}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件正面图片;</font></#if>
							<#if user.cardPic2!><a href="<@imageUrlDecode imgurl="${user.cardPic2}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.cardPic2}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件正面图片;</font></#if>
						</td>
					</tr>
					<tr>
						<th>
							企业名称:
						</th>
						<td>
							${user.userInfo.privateName}
						</td>
					</tr>
					<tr>
						<th>
							企业登记号: 
						</th>
						<td>
							${user.userInfo.registration}
						</td>
					</tr>
					
					<tr>
						<th>
							税务登记号: 
						</th>
						<td>
							${user.userInfo.taxRegistration}
						</td>
					</tr>
					<tr>
						<th>
							组织机构号: 
						</th>
						<td>
							${user.userInfo.organization}
						</td>
					</tr>
					<tr>
						<th>
							营业执照: 
						</th>
						<td>
							<#if user.userInfo.privateCharterImg!><a href="<@imageUrlDecode imgurl="${user.userInfo.privateCharterImg}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.userInfo.privateCharterImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件正面图片;</font></#if>
						</td>
					</tr>
					<tr>
						<th>
							税务登记证: 
						</th>
						<td>
							<#if user.userInfo.privateTaxImg!><a href="<@imageUrlDecode imgurl="${user.userInfo.privateTaxImg}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.userInfo.privateTaxImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件正面图片;</font></#if>
						</td>
					</tr>
					<tr>
						<th>
							组织机构代码证: 
						</th>
						<td>
							<#if user.userInfo.privateOrganizationImg!><a href="<@imageUrlDecode imgurl="${user.userInfo.privateOrganizationImg}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.userInfo.privateOrganizationImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件正面图片;</font></#if>
						</td>
					</tr>
					<tr>
						<th>
							开户许可证: 
						</th>
						<td>
							<#if user.userInfo.accountLicenceImg!><a href="<@imageUrlDecode imgurl="${user.userInfo.accountLicenceImg}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.userInfo.accountLicenceImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件正面图片;</font></#if>
						</td>
					</tr>
					<tr>
						<th>
							注册资金: 
						</th>
						<td>
							${user.userInfo.registeredCapital}
						</td>
					</tr>
					<tr>
						<th>
							企业地址: 
						</th>
						<td>
							${user.userInfo.privatePlace}
						</td>
					</tr>
					<tr>
						<th>
							企业人数：
						</th>
						<td>
							<@listing_childname sign="employees" key_value="${user.userInfo.privateEmployees}"; type>
									${type}
							</@listing_childname>人
						</td>
					</tr>
					<tr>
						<th>
							企业年收入：
						</th>
						<td>
							<@listing_childname sign="enterprise_income" key_value="${user.userInfo.privateIncome}"; type>
									${type}
							</@listing_childname>元
						</td>
					</tr>
					<tr>
						<th>
							联系人：
						</th>
						<td>
							${user.userInfo.linkman}
						</td>
					</tr>
					<tr>
						<th>
							联系电话：
						</th>
						<td>
							${user.userInfo.privatePhone}
						</td>
					</tr>
					<tr>
						<th>
							企业介绍：
						</th>
						<td>
							${user.userInfo.introduce}
						</td>
					</tr>
			<#elseif verifyType==7><!--手机认证-->
					<tr>
						<th>
							用户名:
						</th>
						<td>
							${user.username}
						</td>
					</tr>
					<tr>
						<th>
							真实姓名: 
						</th>
						<td>
							${user.realName}
						</td>
					</tr>
					
					<tr>
						<th>
							性别: 
						</th>
						<td>
							<#if user.sex =="1">男<#elseif user.sex="2">女<#else>未知</#if>
						</td>
					</tr>
					<tr>
						<th>
							手机号码: 
						</th>
						<td>
							${user.phone}
						</td>
					</tr>
			
			</#if>
					<tr>
						<th>
							审核：
						</th>
						<td>
							<input type="radio" name="verifyValue" class="verifyValue_class" value="1" checked><span>通过</span>
							<input type="radio" name="verifyValue" class="verifyValue_class"  value="0" ><span>拒绝</span>
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