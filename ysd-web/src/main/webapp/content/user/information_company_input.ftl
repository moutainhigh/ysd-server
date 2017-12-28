<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户 -申请开通线上借款</title>
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
										<td width="150" class="base_item">企业名称：</td>
										<td>${loginUserInfo.privateName}</td>
									</tr>
									<tr>
										<td class="base_item">法人姓名：</td>
										<td>${loginUser.showRealName}</td>
									</tr>
									<tr>
										<td class="base_item">性别：</td>
										<td>
											<input type="radio" name="user.sex"  value="1"  <#if loginUser.sex=="1" || loginUser.sex=="">checked</#if> >男
											<input type="radio" name="user.sex"  value="2"  <#if loginUser.sex=="2">checked</#if> >女
										</td>
									</tr>
									<tr>
										<td class="base_item">法人身份证号：</td>
										<td><input type="text" name="user.cardId" class="base_txt" value="${loginUser.cardId!}" id="cardId"></td>
									</tr>
									<tr>
										<td class="base_item">身份证正面：</td>
										<td><input type="file" name="cardFile1"/></td>
									</tr>
									<tr>
										<td class="base_item">身份证反面：</td>
										<td><input type="file" name="cardFile2"/></td>
									</tr>
									<tr>
										<td class="base_item">注册资金：</td>
										<td>
											<input type="text" name="userInfo.registeredCapital" id="registeredCapital" value="${loginUserInfo.registeredCapital}" class="base_txt"/>
										</td>
									</tr>
									<tr>
										<td class="base_item">企业地址：</td>
										<td>
											<input type="text" name="userInfo.privatePlace" id="privatePlace" value="${loginUserInfo.privatePlace}" class="base_txt"/>
										</td>
									</tr>
									<tr>
										<td class="base_item">企业员工数：</td>
										<td>
											<select id="housing" name="userInfo.privateEmployees" style="width:180px; height:24px; padding:2px; border:1px solid #7d7d7d;">
												<option value="">请选择&nbsp; </option>
												<@listing_childlist sign="employees"; listingList>
													<#list listingList as listing>
														<option value="${listing.keyValue}" <#if (listing.keyValue == loginUserInfo.privateEmployees)!>selected</#if> >
															${substring(listing.name, 24, "...")}
														</option>
													</#list>
												</@listing_childlist>
											</select>
										</td>
									</tr>
									<tr>
										<td class="base_item">企业年收入：</td>
										<td>
											<select id="privateIncome" name="userInfo.privateIncome" style="width:180px; height:24px; padding:2px; border:1px solid #7d7d7d;">
												<option value="">请选择&nbsp; </option>
												<@listing_childlist sign="enterprise_income"; listingList>
													<#list listingList as listing>
														<option value="${listing.keyValue}" <#if (listing.keyValue == loginUserInfo.privateIncome)!>selected</#if> >
															${substring(listing.name, 24, "...")}
														</option>
													</#list>
												</@listing_childlist>
											</select>
										</td>
									</tr>
									<tr>
										<td class="base_item">联系人：</td>
										<td>
											<input type="text" name="userInfo.linkman" id="linkman" value="${loginUserInfo.linkman}" class="base_txt"/>
										</td>
									</tr>
									<tr>
										<td class="base_item">联系电话：</td>
										<td>
											<input type="text" name="userInfo.privatePhone" id="privatePhone" value="${loginUserInfo.privatePhone}" class="base_txt"/>
										</td>
									</tr>
									<tr>
										<td class="base_item">企业介绍：</td>
										<td><textarea id="introduce" name="userInfo.introduce" cols="40" rows="3" class="txt">${(loginUserInfo.introduce)!}</textarea></td>
									</tr>
									<tr>
										<td class="base_item">交易密码：</td>
										<td>
											<#if loginUser.payPassword!>
											<input  type="password"  id="payPassword" name="user.payPassword"  class="base_txt">
											<#else>
												<a href="${base}/userCenter/toPayPassword.do" >请先设置一个交易密码</a>
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
					"user.realName":{
						required: true,
						realName:true,
						minlength:2,
						maxlength:20
					},
					"user.cardId":{
						required: true,
						isIdCardNo:true
					},
					"userInfo.registeredCapital":{
						required: true,
						minlength:2,
						maxlength:100
					},
					"userInfo.privatePlace":{
						required: true,
						minlength:2,
						maxlength:100
					},
					"userInfo.privateIncome":{
						required: true
					},
					"userInfo.privateEmployees":{
						required: true
					},
					"userInfo.linkman":{
						required: true,
						minlength:2,
						maxlength:100
					},
					"userInfo.introduce":{
						required: true,
						minlength:2,
						maxlength:100
					},
					"userInfo.privatePhone":{
						required: true,
						minlength:2,
						maxlength:100
					},
				"user.payPassword":{
					required: true,
					remote:"${base}/userCenter/ajaxPayPassword.do"
				}
			},
			messages: {
				
				
				"user.realName":{
					required: "真实姓名不能为空!",
					realName:"真实姓名格式错误",
					minlength: "最少2个字",
					maxlength: "最多20个字"
				},
				"userInfo.registration":{
					required:"企业登记号不能为空!",
					minlength: "最少2个字",
					maxlength: "最多100个字"
				},
				"userInfo.taxRegistration":{
					required:"税务登记号不能为空!",
					minlength: "最少2个字",
					maxlength: "最多100个字"
				},
				"userInfo.organization":{
					required:"组织机构号不能为空!",
					minlength: "最少2个字",
					maxlength: "最多100个字"
				},
				"userInfo.registeredCapital":{
					required:"注册资金不能为空!",
					minlength: "最少2个字",
					maxlength: "最多100个字"
				},
				"userInfo.privatePlace":{
					required:"企业地址不能为空!",
					minlength: "最少2个字",
					maxlength: "最多100个字"
				},
				"userInfo.privateIncome":{
					required:"企业年收入不能为空!"
				},
				"userInfo.privateEmployees":{
					required:"企业员工数收入不能为空!"
				},
				"userInfo.linkman":{
					required:"联系人不能为空!",
					minlength: "最少2个字",
					maxlength: "最多100个字"
				},
				"userInfo.introduce":{
					required:"企业介绍不能为空!",
					minlength: "最少2个字",
					maxlength: "最多200个字"
				},
				"userInfo.privatePhone":{
					required:"企业联系电话不能为空!",
					minlength: "最少2个字",
					maxlength: "最多100个字"
				},
				"user.payPassword":{
					required:"请输入交易密码",
					remote:"交易密码输入错误!"
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
