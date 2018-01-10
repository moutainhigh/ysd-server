<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户 -申请开通线上借款</title>
	<#include "/content/common/meta.ftl">
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">

<!-- content -->
<div class="admin clearfix">
	<#include "/content/common/user_center_left.ftl">

	<!-- right -->
	<div class="admin-con">
		<!-- location -->
		<div class="breadcrumb">
			<a href="${base}/">${Application ["qmd.setting.name"]}</a> &gt; <a href="${base}/userCenter/index.do">我的账户</a> &gt; 申请开通线上借款
		</div>
		
		<div class="tab clearfix">
			<ul>
				<li><a href="" class="current"><span><#if loginUser.memberType==0>个人<#else>企业</#if>申请开通线上借款</span></a></li>
			</ul>		
		</div>
		<div class="tab-con special">
		
			<!-- grand total  -->
			<h3 class="tac c1">投资3步骤</h3>
			<div class="step">
				<ul class="step_1 clearfix">
					<li>
						<h3>填写借款人资料</h3>
						成为会员，确认您的投资身份
					</li>
					<li>
						<h3>等待网站审核</h3>
						提前为投资项目注入资金
					</li>
					<li class="last">
						<h3>发布借款信息</h3>
						选择您满意的项目进行投资
					</li>
				</ul>
			</div>
			
			<!-- frm -->
			<div class="frm">
		 	<#if loginUser.avatarStatus ==0>
			<form id="realnameForm" method="post" action="upInformation.do" enctype="multipart/form-data">
				<table width="100%">
					 <#if loginUser.memberType==0>
					          <tr>
					          	<td class="item">用户名： </td>
					          	<td> ${loginUser.username} &nbsp;&nbsp;</td>
					          </tr>
					          <tr>
					          	<td class="item">真实姓名： </td>
					          	<!--<td> ${substring(loginUser.realName, 1, "***")}</td>-->
					          	<td>${loginUser.showRealName}</td>
					          </tr>
					          <tr>
					          	<td class="item">性别： </td>
					          	<td> 
					          		<#if loginUser.sex=="1">男<#else>女</#if>
								</td>
					          </tr>   
					         	<tr>
					         	 <td class="item">手机： </td>
						    		<td>
						    			${loginUser.phone}
						    		</td>
								</tr>
						 	  <tr height=34>
						      	<td align=right class="tdmg">正面免冠照： </td>
						      	<td>
						      		<input type="file" name="fileList[0]"  class="fileListClass">&nbsp;
						      		</td>
							  </tr>
					          <tr>
					          	<td class="item">联系地址： </td>
					          	<td> 
					          		<input id="address" class="txt  " type="text" name="userInfo.address" value="${loginUserInfo.address}">
									<label id="WrongAddress" class="shuru0316" for="userInfo.address"></label>
								</td>
					          </tr>
					          <tr>
                                 <td class="item">婚姻状况： </td>
                                 <td>
                                	<select id="marry" name="userInfo.marry"  class="sel">
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
                                   <td class="item">工作单位： </td>
                                   <td>
                                   	 <input id="companyName" type="text" class="txt  " name="userInfo.companyName" value="${(loginUserInfo.companyName)!}">
                                   </td>
	                          </tr>
						  	   <tr>
                                   <td class="item">单位地址： </td>
                                   <td>
                                   	 <input id="companyName" type="text" class="txt  " name="userInfo.companyAddress" value="${(loginUserInfo.companyAddress)!}">
                                   </td>
	                           </tr>
							  <tr>
                               <td class="item"> 担任职位： </td>
                                <td>
                                <select id="companyOffice" name="userInfo.companyOffice"  class="sel">
                               		<option value="">请选择&nbsp; </option>
                                  	<@listing_childlist sign="user_company_office"; listingList>
										<#list listingList as listing>
											<option value="${listing.keyValue}" <#if (listing.keyValue == loginUserInfo.companyOffice)!> selected</#if>>
												${substring(listing.name, 24, "...")}
											</option>
										</#list>
									</@listing_childlist>
                            	</select>
                              </td>
							  <tr>
                                <td class="item">月均收入：  </td>
                                <td>
	                                 <select id="income" name="userInfo.income" class="sel">
	                                   <option value="">请选择&nbsp; </option>
	                                      <@listing_childlist sign="user_income"; listingList>
											<#list listingList as listing>
												<option value="${listing.keyValue}" <#if (listing.keyValue == loginUserInfo.income)!> selected</#if>>
													${substring(listing.name, 24, "...")}
												</option>
											</#list>
										</@listing_childlist>
	                                 </select>
                                 </td>
                              </tr>
                              <!--新加-->
                              	
					          <tr>
                                 <td class="item">文化程度： </td>
                                 <td>
                                	<select id="educationRecord" name="userInfo.educationRecord"  class="sel">
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
                                   <td class="item">社保： </td>
                                   <td>
                                   	<select id="socialInsuranceStatus" name="userInfo.socialInsuranceStatus"  class="sel">
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
                                   <td class="item">购车： </td>
                                   <td>
                                   	<select id="carStatus" name="userInfo.carStatus"  class="sel">
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
                                   <td class="item">住房条件： </td>
                                   <td>
                                   	 <select id="housing" name="userInfo.housing"  class="sel">
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
                              <!--end-->
							  <tr>
                               <td class="item"> 个人介绍： </td>
                               <td>
                                 <textarea id="others" cols="50" rows="5" name="userInfo.others">${(loginUserInfo.companyRemark)!}</textarea>
                               </td>
                          	 </tr>
						 <#else>
                              <tr>
					          	<td class="item">企业名称： </td>
					          	<td><a href="javascript： void(0)" title="${loginUserInfo.privateName}">${substring(loginUserInfo.privateName, 50, "")}</td>
					          </tr>
					          <tr>
				          		<td class="item">法人姓名： </td>
				          		<!--<td>${substring(loginUser.realName, 1, "***")}</td>-->
				          		<td>${loginUser.showRealName}</td>
					          </tr>
							  <tr>
                             	 <td class="item">性&nbsp;&nbsp;&nbsp;&nbsp;别： </td>
                                 <td>
	                           		<input type="radio" name="user.sex"  value="1"  class="sex" <#if loginUser.sex=="1" || loginUser.sex=="">checked</#if> >男
									<input type="radio" name="user.sex"  value="2"  class="sex" <#if loginUser.sex=="2">checked</#if> >女
                                 </td>
							 </tr>
					          <tr>
					          	<td class="item">法人身份证： </td>
					          	<td>
					          		<input type="text" name="user.cardId" class="txt  " value="${loginUser.cardId!}" id="cardId">
					          	</td>
					          </tr>
						 	  <tr >
						      	<td class="item">身份证正面：</td>
						      	<td>
							      	<input type="file" name="cardFile1" class="txt  ">&nbsp;
							      	</td>
							  </tr>
						      <tr>
						     	 <td class="item">身份证反面：</td>
						     	 <td>
							     	 <input type="file" name="cardFile2" class="txt  ">&nbsp;
							     </td>
							  </tr>
                              <tr>
				          		<td class="item">注册资金： </td>
				          		<td>
									<input type="text" name="userInfo.registeredCapital" class="txt  " id="registeredCapital" value="${loginUserInfo.registeredCapital}">元
								</td>
					          </tr>
					           <tr>
					          	<td class="item">企业地址： </td>
					          	<td>
									<input type="text" name="userInfo.privatePlace" class="txt  " id="privatePlace" value="${loginUserInfo.privatePlace}">
								</td>
					          </tr>
					           
					           <tr>
					          	<td class="item">企业员工数： </td>
					          	<td>
					          		 <select id="housing" name="userInfo.privateEmployees"  class="sel">
		                                <option value="">请选择&nbsp; </option>
				                         <@listing_childlist sign="employees"; listingList>
											<#list listingList as listing>
												<option value="${listing.keyValue}" <#if (listing.keyValue == loginUserInfo.privateEmployees)!>selected</#if> >
													${substring(listing.name, 24, "...")}
												</option>
											</#list>
										</@listing_childlist>
                               		</select>人
					          	</td>
					          </tr>
					          <tr>
					          	<td class="item">企业年收入： </td>
					          	<td>
					          		<select id="housing" name="userInfo.privateIncome"  class="sel">
		                                <option value="">请选择&nbsp; </option>
				                         <@listing_childlist sign="enterprise_income"; listingList>
											<#list listingList as listing>
												<option value="${listing.keyValue}" <#if (listing.keyValue == loginUserInfo.privateIncome)!>selected</#if> >
													${substring(listing.name, 24, "...")}
												</option>
											</#list>
										</@listing_childlist>
                               		</select>元
					          	</td>
					          </tr>
					           <tr>
					          	<td class="item">联系人： </td>
					          	<td> 
					          		<input type="text" name="userInfo.linkman" class="txt" id="linkman" value="${loginUserInfo.linkman}">
					          	</td>
					          </tr>
					           <tr>
					          	<td class="item">联系电话： </td>
					          	<td> 
					          		<input type="text" name="userInfo.privatePhone" class="txt" id="privatePhone" value="${loginUserInfo.privatePhone}">
					          	</td>
					          </tr>
					          <tr>
					          	<td class="item">企业介绍： </td>
					          	<td>
					          		<textarea name="userInfo.introduce" class="txt" rows="3" cols="40" id="introduce" >${loginUserInfo.introduce}</textarea>
					          	</td>
					          </tr>
						   </#if>
						 <tr>
				          	<td class="item">交易密码： </td><td> 
					          	<#if loginUser.payPassword!>
					          		<input type="password" class="txt" name="user.payPassword" id="payPassword"/>
					          	<#else>
					          		<a href="${base}/userCenter/toPayPassword.do" >请先设置一个交易密码</a>
					          		<input type="hidden"  name="user.payPassword">
					          	</#if>
				          	</td>
				        </tr>
						<tr>
							<td class="item"></td>
							<td>
								<input type="submit" value="认 证" class="btn s3_"  />
								<input type="reset" value="取 消" class="btn s3_" />
							</td>
						</tr>				
				</table>		
			 </form> 
			<#else>
				<table width="100%">
             	 <#if loginUser.memberType==0>
	             	 <tr>
			          	<td class="item">用户名： </td><td> ${loginUser.username}</td>
			          </tr>
			          <tr>
			          	<td class="item">真实姓名： </td>
			          	<!--<td> ${substring(loginUser.realName, 1, "***")}</td>-->
			          	<td>${loginUser.showRealName}</td>
			          </tr>
			          <tr>
			          	<td class="item">性别： </td><td> <#if loginUser.sex='1'>男<#else>女</#if></td>
			          </tr>
			          <tr>
			          	<td class="item">手机号码： </td><td> ${loginUser.phone}</td>
			          </tr>
				 	  <tr>
				      	<td class="item">正面免冠照：</td><td>
				      	<input type="button" class="coporate" value="查看" onclick="window.open('<@imageUrlDecode imgurl="${loginUserInfo.bareheadedImg}"; imgurl>${imgurl}</@imageUrlDecode>','_blank')"></input></td>
					  </tr>
			          <tr>
			          	<td class="item">联系地址： </td><td> ${loginUserInfo.address!}</td>
			          </tr>
			          <tr>
			          	<td class="item">婚姻状况： </td>
			          	<td> 
				          	<@listing_childname sign="user_marry" key_value="${loginUserInfo.marry}"; type>
									${type}
							</@listing_childname>
						</td>
			          </tr>
			          <tr>
			          	<td class="item">工作单位： </td><td> ${loginUserInfo.companyName!}</td>
			          </tr>
			          <tr>
			          	<td class="item">单位地址： </td><td> ${loginUserInfo.companyAddress!}</td>
			          </tr>
			          <tr>
			          	<td class="item">担任职位： </td>
			          	<td>
			          		<@listing_childname sign="user_company_office" key_value="${loginUserInfo.companyOffice}"; type>
								${type}
							</@listing_childname>
			          	</td>
			          </tr>
			          <tr>
			          	<td class="item">月收入： </td><td>
			          	<@listing_childname sign="user_income" key_value="${loginUserInfo.income}"; type>
							${type}
						</@listing_childname>
			          	</td>
			          </tr>
			          
			          <tr>
			          	<td class="item">文化程度： </td><td>
			          	<@listing_childname sign="user_education" key_value="${loginUserInfo.educationRecord}"; type>
							${type}
						</@listing_childname>
			          	</td>
			          </tr>
			          <tr>
			          	<td class="item">社保： </td><td>
			          	<@listing_childname sign="user_shebao" key_value="${loginUserInfo.socialInsuranceStatus}"; type>
							${type}
						</@listing_childname>
			          	</td>
			          </tr>
			          <tr>
			          	<td class="item">购车： </td><td>
			          	<@listing_childname sign="user_car" key_value="${loginUserInfo.carStatus}"; type>
							${type}
						</@listing_childname>
			          	</td>
			          </tr>
			          <tr>
			          	<td class="item">住房条件： </td><td>
			          	<@listing_childname sign="user_housing" key_value="${loginUserInfo.housing}"; type>
							${type}
						</@listing_childname>
			          	</td>
			          </tr>
			          <tr>
			          	<td class="item">个人介绍： </td><td> ${loginUserInfo.others!}</td>
			          </tr>
		          <#else>
			          <tr>
			          	<td class="item">企业名称： </td><td> ${loginUserInfo.privateName} </td>
			          </tr>
			          <tr>
			          	<td class="item">法人姓名： </td>
			          	<!--<td> ${substring(loginUser.realName, 1, "***")}&nbsp;&nbsp;&nbsp;<#if loginUser.sex=='1'>先生<#else>女士</#if></td>-->
			          	<td> ${loginUser.showRealName}&nbsp;&nbsp;&nbsp;<#if loginUser.sex=='1'>先生<#else>女士</#if></td>
			          </tr>
			          <tr>
			          	<td class="item">法人身份证： </td><td> ${loginUser.cardId!}</td>
			          </tr>
				 	  <tr>
				      	<td class="item">身份证正面：</td>
				      	<td></td>
					 </tr>
				      <tr height=34>
				     	 <td class="item">身份证反面：</td>
				     	 <td></td>
					 </td>
					  </tr>
	                    <tr>
			          	<td class="item">注册资金： </td><td>${loginUserInfo.registeredCapital}元</td>
			          </tr>
			          
			           <tr>
			          	<td class="item">企业地址： </td><td> ${loginUserInfo.privatePlace}</td>
			          </tr>
			           <tr>
			          	<td class="item">企业员工数： </td>
			          	<td>
			          		<@listing_childname sign="employees" key_value="${loginUserInfo.privateEmployees}"; type>
								${type}
							</@listing_childname>人
			          	</td>
			          </tr>
			           <tr>
			          	<td class="item">企业年收入： </td>
			          	<td>
			          		<@listing_childname sign="enterprise_income" key_value="${loginUserInfo.privateIncome}"; type>
								${type}
							</@listing_childname>元
			          	</td>
			          </tr>
			           <tr>
			          	<td class="item">联系人： </td><td> <input type="text" name="userInfo.linkman" id="linkman" value="${loginUserInfo.linkman}"></td>
			          </tr>
			           <tr>
			          	<td class="item">联系电话： </td><td> <input type="text" name="userInfo.privatePhone"  id="privatePhone" value="${loginUserInfo.privatePhone}"></td>
			          </tr>
			           <tr>
			          	<td class="item">企业介绍： </td>
			          	<td> 
			          		${loginUserInfo.introduce}
			          	</td>
			          </tr>
		          </#if>
                  <tr>
				      <td class="item"></td>
					  <td>
					  	<span class="btn_l s3">
					 		<#if loginUser.avatarStatus==1>
					 			<input  type="button" id = "button" value="通 过" class="btn s3_" />
					 		<#elseif loginUser.avatarStatus==2>
					 			<input type="button" id = "button" value="审核中" class="btn s3_" />
					 		</#if>
					  	</span>
					  </td>
				  </tr>
			  </table> 
             </#if>
			</div>
			<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
		</div>
		<div class="tab-con_bottom"></div>
		
	</div>
</div>
<#include "/content/common/footer.ftl">
</body>
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.card.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	$().ready( function() {
		$("#borrower_information").addClass("current");
		var $realnameForm = $("#realnameForm");

		 // 表单验证
		$realnameForm.validate({
			rules: {
				<#if loginUser.memberType==0>
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
				<#elseif loginUser.memberType==1>
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
				</#if>
				"user.payPassword":{
					required: true,
					remote:"${base}/userCenter/ajaxPayPassword.do"
				}
			},
			messages: {
				<#if loginUser.memberType==0>
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
				<#elseif loginUser.memberType==1>
				
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
				</#if>
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
