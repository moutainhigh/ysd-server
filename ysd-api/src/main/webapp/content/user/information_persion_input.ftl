<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-我的账户 -申请开通线上借款</title>
	<#include "/content/common/meta.ftl">
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">

<div style="width:100%; background:url(${base}/static/img/d6.png) 0 0 repeat-x; min-width:1000px; padding-bottom:30px;" class="biaoti_bg">
	<div class="content">
		<div class="biaoti">
			<a href="${base}/">${Application ["qmd.setting.name"]}</a><a>></a>
			<a href="${base}/userCenter/index.do">我的账户</a><a>></a>
			<a class="liebiao">申请开通线上借款</a>
		</div>
		<!--biaoti end-->
		<div style="width:1000px;" class="big_content">
			<#include "/content/common/user_center_left.ftl">
			<!--left_menu end-->
			
			<div class="right_jiluneirong">
				<div class="tabsblk">
					<a href="javascript:void(0);"  class="checked">申请线上借款</a>
				</div>
				<div class="sepblk"></div>
				<div class="white">
					<div class="base_tabCon">
						<div style="color:#D14324;  font-weight:bold; padding:10px 25px; font-size:16px;">投资3步骤之一</div>
						<div style=" float:left; width:100%; height:80px;" class="base_step base_step_1">
							<ul>
								<li class="base_yu">
									<h3>填写借款人资料</h3>
									成为会员，确认您的投资身份
								</li>
								<li class="base_yi">
									<h3>等待网站审核</h3>
									提前为投资项目注入资金
								</li>
								<li class="base_yo">
									<h3>发布借款信息</h3>
									选择您满意的项目进行投资
								</li>
							</ul>
						</div>
						<!-- frm -->
						
						<form id="realnameForm" method="post" action="upInformation.do" enctype="multipart/form-data">
						<div style="clear:both;" class="base_frm">
							<table width="100%">
								<tbody>
									<tr>
										<td width="150" class="base_item">用户名：</td>
										<td> ${loginUser.username} <#--<a href=""><b style="color:#D14324; ">去验证&gt;</b></a>--></td>
									</tr>
									<tr>
										<td class="base_item">真实姓名：</td>
										<td>${loginUser.showRealName}</td>
									</tr>
									<tr>
										<td class="base_item">性别：</td>
										<td><#if loginUser.sex=="1">男<#else>女</#if></td>
									</tr>
									<tr>
										<td class="base_item">手机：</td>
										<td>${loginUser.phone}</td>
									</tr>
									<tr>
										<td class="base_item">正面免冠照：</td>
										<td><input type="file" name="fileList[0]"/></td>
									</tr>
									<tr>
										<td class="base_item">联系地址：</td>
										<td>
											<input id="address" type="text" name="userInfo.address" value="${loginUserInfo.address}" class="base_txt"/>
											<label id="WrongAddress" class="shuru0316" for="userInfo.address"></label>
										</td>
									</tr>
									<tr>
										<td class="base_item">婚姻状况：</td>
										<td>
											<select id="marry" name="userInfo.marry" style="width:180px; height:24px; padding:2px; border:1px solid #7d7d7d;">
												<option value="">请选择&nbsp; </option>
												<@listing_childlist sign="user_marry"; listingList>
													<#list listingList as listing>
														<option value="${listing.keyValue}" <#if (listing.keyValue == loginUserInfo.marry)!>selected</#if> >
															${substring(listing.name, 24, "...")}
														</option>
													</#list>
												</@listing_childlist>
											</select>
										</td>
									</tr>
									<tr>
										<td class="base_item">工作单位：</td>
										<td><input id="companyName" type="text" name="userInfo.companyName" value="${(loginUserInfo.companyName)!}" class="base_txt"/></td>
									</tr>
									<tr>
										<td class="base_item">单位地址：</td>
										<td><input id="companyAddress" type="text" name="userInfo.companyAddress" value="${(loginUserInfo.companyAddress)!}" class="base_txt"/></td>
									</tr>
									<tr>
										<td class="base_item">担任职位：</td>
										<td>
											<select id="companyOffice" name="userInfo.companyOffice" style="width:180px; height:24px; padding:2px; border:1px solid #7d7d7d;">
												<option value="">请选择&nbsp; </option>
												<@listing_childlist sign="user_company_office"; listingList>
													<#list listingList as listing>
														<option value="${listing.keyValue}" <#if (listing.keyValue == loginUserInfo.companyOffice)!>selected</#if> >
															${substring(listing.name, 24, "...")}
														</option>
													</#list>
												</@listing_childlist>
											</select>
										</td>
									</tr>
									<tr>
										<td class="base_item">月均收入：</td>
										<td>
											<select id="income" name="userInfo.income" style="width:180px; height:24px; padding:2px; border:1px solid #7d7d7d;">
												<option value="">请选择&nbsp; </option>
												<@listing_childlist sign="user_income"; listingList>
													<#list listingList as listing>
														<option value="${listing.keyValue}" <#if (listing.keyValue == loginUserInfo.income)!>selected</#if> >
															${substring(listing.name, 24, "...")}
														</option>
													</#list>
												</@listing_childlist>
											</select>
										</td>
									</tr>
									<tr>
										<td class="base_item">文化程度：</td>
										<td>
											<select id="educationRecord" name="userInfo.educationRecord" style="width:180px; height:24px; padding:2px; border:1px solid #7d7d7d;">
												<option value="">请选择&nbsp; </option>
												<@listing_childlist sign="user_education"; listingList>
													<#list listingList as listing>
														<option value="${listing.keyValue}" <#if (listing.keyValue == loginUserInfo.educationRecord)!>selected</#if> >
															${substring(listing.name, 24, "...")}
														</option>
													</#list>
												</@listing_childlist>
											</select>
										</td>
									</tr>
									<tr>
										<td class="base_item">社保：</td>
										<td>
											<select id="socialInsuranceStatus" name="userInfo.socialInsuranceStatus" style="width:180px; height:24px; padding:2px; border:1px solid #7d7d7d;">
												<option value="">请选择&nbsp; </option>
												<@listing_childlist sign="user_shebao"; listingList>
													<#list listingList as listing>
														<option value="${listing.keyValue}" <#if (listing.keyValue == loginUserInfo.socialInsuranceStatus)!>selected</#if> >
															${substring(listing.name, 24, "...")}
														</option>
													</#list>
												</@listing_childlist>
											</select>
										</td>
									</tr>
									<tr>
										<td class="base_item">购车：</td>
										<td>
											<select id="carStatus" name="userInfo.carStatus" style="width:180px; height:24px; padding:2px; border:1px solid #7d7d7d;">
												<option value="">请选择&nbsp; </option>
												<@listing_childlist sign="user_car"; listingList>
													<#list listingList as listing>
														<option value="${listing.keyValue}" <#if (listing.keyValue == loginUserInfo.carStatus)!>selected</#if> >
															${substring(listing.name, 24, "...")}
														</option>
													</#list>
												</@listing_childlist>
											</select>
										</td>
									</tr>
									<tr>
										<td class="base_item">住房条件：</td>
										<td>
											<select id="housing" name="userInfo.housing" style="width:180px; height:24px; padding:2px; border:1px solid #7d7d7d;">
												<option value="">请选择&nbsp; </option>
												<@listing_childlist sign="user_housing"; listingList>
													<#list listingList as listing>
														<option value="${listing.keyValue}" <#if (listing.keyValue == loginUserInfo.housing)!>selected</#if> >
															${substring(listing.name, 24, "...")}
														</option>
													</#list>
												</@listing_childlist>
											</select>
										</td>
									</tr>
									<tr>
										<td class="base_item">个人介绍：</td>
										<td><textarea id="others" name="userInfo.others" cols="40" rows="3" class="txt">${(loginUserInfo.others)!}</textarea></td>
									</tr>
									<tr>
										<td class="base_item">安全密码：</td>
										<td>
											<#if loginUser.payPassword!>
											<input  type="password"  id="payPassword" name="user.payPassword"  class="base_txt">
											<#else>
												<a href="${base}/userCenter/toPayPassword.do" >请先设置一个安全密码</a>
												<input type="hidden"  name="user.payPassword"/>
											</#if>
											
										</td>
									</tr>
									<tr>
										<td class="base_item"></td>
										<td>
											<input type="submit" class="queding" value="确定"/>
											<input type="reset" class="quxiao" value="取消"/>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						</form> 
					</div>
					<!--base_tabCon end -->
				</div>
				<div class="fanye"></div>
			</div>
			<!--right_jiluneirong end-->
			<div style="clear:both;"></div>
		</div>
		<!--big_content end-->
	</div>
	<!--content end-->
</div>
<!--biaoti_bg end-->

<#include "/content/common/footer.ftl">
</body>
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.card.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	$().ready( function() {
	
		$("#borrower_choose").addClass("nsg nsg_a1");
		
		var $realnameForm = $("#realnameForm");

		 // 表单验证
		$realnameForm.validate({
			rules: {
					"userInfo.address":{
						required: true,
						minlength:2,
						maxlength:100
					},
					"userInfo.companyName":{
						required: true,
						minlength:2,
						maxlength:100
					},
					"userInfo.companyAddress":{
						required: true,
						minlength:2,
						maxlength:100
					},
					"userInfo.marry":{
						required: true
					},
					"userInfo.companyOffice":{
						required: true
					},
					"userInfo.income":{
						required: true
					},
					"userInfo.educationRecord":{
						required: true
					},
					"userInfo.socialInsuranceStatus":{
						required: true
					},
					"userInfo.housing":{
						required: true
					},
					"userInfo.carStatus":{
						required: true
					},
					"userInfo.others":{
						required: true
					},
				
				"user.payPassword":{
					required: true,
					remote:"${base}/userCenter/ajaxPayPassword.do"
				}
			},
			messages: {
					"userInfo.address":{
						required: "请填写地址",
						minlength: "最少2个字",
						maxlength: "最多100个字"
					},
					"userInfo.companyName":{
						required: "请填写公司名称",
						minlength: "最少2个字",
						maxlength: "最多100个字"
					},
					"userInfo.companyAddress":{
						required: "请填写单位地址",
						minlength: "最少2个字",
						maxlength: "最多100个字"
					},
					"userInfo.marry":{
						required: "请选择婚姻状况"
					},
					"userInfo.companyOffice":{
						required: "请选择担任职位"
					},
					"userInfo.income":{
						required: "请选择月均收入"
					},
					"userInfo.educationRecord":{
						required: "请选择文化程度"
					},
					"userInfo.socialInsuranceStatus":{
						required: "请选择是否缴纳社保"
					},
					"userInfo.housing":{
						required: "请选择住房条件"
					},
					"userInfo.carStatus":{
						required: "请选择是否购车"
					},
					"userInfo.others":{
						required: "请填写个人介绍"
					},
				"user.payPassword":{
					required:"请输入安全密码",
					remote:"安全密码输入错误!"
				}
			},
			errorPlacement: function(error, element) {
			  error.appendTo(element.parent());
			},
			submitHandler: function(form) {
				form.submit();
			}
		});
		
		// 表单验证
		$.validator.addMethod("fileListRequired", $.validator.methods.required, "请选择上传证件图片");
		$.validator.addMethod("fileListImageFile", $.validator.methods.imageFile, "证件图片格式错误");
		
		$.validator.addClassRules("fileListClass", {
			fileListRequired:true,
			fileListImageFile: true
		});
	});
</script>
</html>
