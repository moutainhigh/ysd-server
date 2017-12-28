<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>用户详情 </title>
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
	var $tab = $("#tab");
	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
});
</script>
</head>
<body class="input admin">
	<div class="bar">
		【${user.username}】的详情信息
	</div>
	<div class="body">
	
			<ul id="tab" class="tab">
				<li>
					<input type="button" value="基本信息" hidefocus />
				</li>
				<li>
					<input type="button" value="详细信息" hidefocus />
				</li>
				<li>
					<input type="button" value="认证状态" hidefocus />
				</li>
				<li>
					<input type="button" value="资金账户" hidefocus />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						ID: 
					</th>
					<td>
						${user.id}
					</td>
				</tr>
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
						会员类型/属性: 
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
						${user.realName}
					</td>
				</tr>
				
				<tr>
					<th>
						手机号: 
					</th>
					<td>
						${user.phone}
					</td>
				</tr>
				<tr>
					<th>
						身份证号: 
					</th>
					<td>
						${user.cardId}
					</td>
				</tr>
				<tr>
					<th>
						状态: 
					</th>
					<td>
						<#if user.isEnabled && !user.isLock >
							<span class="green">正常</span>
						<#elseif !user.isEnabled>
							<span class="red"> 未启用 </span>
						<#elseif user.isLock>
							<span class="red"> 已锁定 </span>
						</#if>
					</td>
				</tr>
				
			</table>
			<table class="inputTable tabContent">
				<tr>
					<th>
						用户名:
					</th>
					<td>
						${(user.username)!}
					</td>
				</tr>
				<#if user.memberType==0>
				<!--个人借款人-->
				<tr>
					<th>
						身份证: 
					</th>
					<td>
						<#if user.cardPic1!><a href="<@imageUrlDecode imgurl="${user.cardPic1}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.cardPic1}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件正面图片;</font></#if>
						<#if user.cardPic2!><a href="<@imageUrlDecode imgurl="${user.cardPic2}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.cardPic2}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传证件反面图片;</font></#if>
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
						${(user.userinfo.address)!}
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
				<#--<tr>
					<th>
						学历:
					</th>
					<td>
						${(user.userinfo.address)!}
					</td>
				</tr>
				<tr>
					<th>
						工作行业:
					</th>
					<td>
						${(user.userinfo.address)!}
					</td>
			</tr>-->
				<tr>
					<th>
						工作单位:
					</th>
					<td>
						${(user.userinfo.address)!}
					</td>
				</tr>
				<tr>
					<th>
						单位地址:
					</th>
					<td>
						${(user.userinfo.companyAddress)!}
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
						月收入:
					</th>
					<td>
						<@listing_childname sign="user_income" key_value="${user.userInfo.income}"; type>
									${type}元
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
						个人介绍:
					</th>
					<td>
						${(user.userinfo.others)!}
					</td>
				</tr>
				<tr>
					<th>
						备注:
					</th>
					<td>
						${(user.userinfo.companyRemark)!}
					</td>
				</tr>
				<#elseif user.memberType==1>
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
							<#if user.userInfo.privateCharterImg!><a href="<@imageUrlDecode imgurl="${user.userInfo.privateCharterImg}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.userInfo.privateCharterImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传图片;</font></#if>
						</td>
					</tr>
					<tr>
						<th>
							税务登记证: 
						</th>
						<td>
							<#if user.userInfo.privateTaxImg!><a href="<@imageUrlDecode imgurl="${user.userInfo.privateTaxImg}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.userInfo.privateTaxImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传图片;</font></#if>
						</td>
					</tr>
					<tr>
						<th>
							组织机构代码证: 
						</th>
						<td>
							<#if user.userInfo.privateOrganizationImg!><a href="<@imageUrlDecode imgurl="${user.userInfo.privateOrganizationImg}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.userInfo.privateOrganizationImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传图片;</font></#if>
						</td>
					</tr>
					<tr>
						<th>
							开户许可证: 
						</th>
						<td>
							<#if user.userInfo.accountLicenceImg!><a href="<@imageUrlDecode imgurl="${user.userInfo.accountLicenceImg}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${user.userInfo.accountLicenceImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="50" heigth="50"/></a><#else><font color="red">没有上传图片;</font></#if>
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
						${(user.username)!}
					</td>
				</tr>
					<tr>
					<th>
						实名认证:
					</th>
					<td>
						<#if user.realStatus = 0>未申请<#elseif user.realStatus=1>通过<#elseif user.realStatus=2><span class = "red">审核中</span><#else>未知</#if>
					</td>
				</tr>
				<tr>
					<th>
						邮箱认证:
					</th>
					<td>
						<#if user.emailStatus = 0>未申请<#elseif user.emailStatus=1>通过<#elseif user.emailStatus=2>审核中<#else>未知</#if>
					</td>
				</tr>
				<tr>
					<th>
						手机认证:
					</th>
					<td>
						<#if user.phoneStatus = 0>未申请<#elseif user.phoneStatus=1>通过<#elseif user.phoneStatus=2><span class = "red">审核中</span><#else>未知</#if>
					</td>
				</tr>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<th>
						用户名:
					</th>
					<td>
						${(user.username)!}
					</td>
				</tr>
				<tr>
					<th>
						总金额:
					</th>
					<td>
						${(user.account.total?string.currency)!}
					</td>
				</tr>
				<tr>
					<th>
						可用余额:
					</th>
					<td>
						${(user.account.ableMoney?string.currency)!}
					</td>
				</tr>
				<tr>
					<th>
						冻结金额:
					</th>
					<td>
						${(user.account.unableMoney?string.currency)!}
					</td>
				</tr>
				<tr>
					<th>
						待收金额:
					</th>
					<td>
						${(user.account.collection?string.currency)!}
					</td>
				</tr>
				<#if user.typeId==0>
					<tr>
						<th>
							待收本金:
						</th>
						<td>
							${(user.account.investorCollectionCapital?string.currency)!}
						</td>
					</tr>
					<tr>
						<th>
							待收利息:
						</th>
						<td>
							${(user.account.investorCollectionInterest?string.currency)!}
						</td>
					</tr>
				<#elseif user.typeId==1>
					<tr>
						<th>
							待付本金:
						</th>
						<td>
							${(user.account.borrowerCollectionCapital?string.currency)!}
						</td>
					</tr>
					<tr>
						<th>
							待付利息:
						</th>
						<td>
							${(user.account.borrowerCollectionInterest?string.currency)!}
						</td>
					</tr>
				</#if>
			</table>
			<div class="buttonArea">
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
	</div>
</body>
</html>