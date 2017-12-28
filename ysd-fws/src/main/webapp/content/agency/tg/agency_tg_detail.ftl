
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-账户管理-账号信息</title>
	<#include "/content/common/meta.ftl">
 <!-- header -->
</head>
<body>
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_user.ftl">
<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="#">账户管理</a></li>
			<li><a href="#">账号信息</a></li>
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
        	<h3>基本信息</h3>
				<input type="hidden" name="id" value="${loginUser.id}">
					<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="96">服务商名称：</td>
		                <td>${agency.companyName}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">所在地区：</td>
		                <td>${loginUser.areaStore}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">联系电话：</td>
		                <td>${loginUser.phone}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">联系地址：</td>
	               		<td>${loginUser.address}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">电子邮箱：</td>
		                <td>${loginUser.email}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">联系人姓名：</td>
		                <td>${agency.linkman}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">联系人手机：</td>
		                <td>${agency.linkmanMobile}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">服务承诺：</td>
		                <td><#if (agency.servicePromise)!>
									<#list agency.servicePromise?split(",") as a> 
										<#if a?trim =='guarantee01'>
											<img src="${base}/static/img/danbaoico1.png" id="tp1" title="已经通过线下实际身份验证"/>
										<#elseif a?trim == 'guarantee03'>
											<img src="${base}/static/img/danbaoico3.png" id="tp3" title="如融资方发生违约可闪电代偿"/>
										<#elseif a?trim =='guarantee04'>
											<img src="${base}/static/img/danbaoico4.png" id="tp4" title="支持履约担保服务"/>
										</#if>
									</#list>
								</#if>
							</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">主营业务：</td>
		                <td>${agency.mainBusiness}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">备注：</td>
		                <td>${agency.remark}</td>
		              </tr>
		              
		              </table>
		           </div>
   <div class="broderShadow">
        	<h3>工商注册信息</h3>
				<input type="hidden" name="id" value="${loginUser.id}">
					<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
				             
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="96">法人姓名：</td>
		                <td>${loginUser.realName}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">营业执照号码：</td>
		                <td>${agency.privateCharter}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">营业执照图片：</td>
		                <td><a href = "<@imageUrlDecode imgurl="${agency.privateCharterImg}"; imgurl>${imgurl}</@imageUrlDecode>" target = "_blank">查看</a></td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">税务登记号：</td>
		                <td>${agency.taxRegistration}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">税务登记证图片：</td>
		                <td><a href = "<@imageUrlDecode imgurl="${agency.privateTaxImg}"; imgurl>${imgurl}</@imageUrlDecode>" target = "_blank">查看</a></td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">机构代码证号：</td>
		                <td>${agency.organization}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">机构代码证图片：</td>
		                <td><a href = "<@imageUrlDecode imgurl="${agency.privateOrganizationImg}"; imgurl>${imgurl}</@imageUrlDecode>" target = "_blank">查看</a></td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">开户许可证图片：</td>
		                <td><a href = "<@imageUrlDecode imgurl="${agency.accountLicenceImg}"; imgurl>${imgurl}</@imageUrlDecode>" target = "_blank">查看</a></td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">营业期限：</td>
		                <td>${(agency.businessStart?string("yyyy-MM-dd"))!}-${(agency.businessEnd?string("yyyy-MM-dd"))!}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">注册地址：</td>
		                <td>${agency.address}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">注册资本：</td>
		                <td>${(agency.capital?string.currency)!'￥0.00'}元</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">经营范围：</td>
		                <td>${agency.scope}</td>
		              </tr>
		        	</table> 
        </div>                
    </div>
    
</div>

	<#include "/content/common/footer.ftl">
<script type="text/javascript">
	$().ready(function() {
		$(".gjxx").attr("id","gjxx");
		$("#li_a_zhxx").addClass("li_a_select");
	})
	</script>
</body>
	
</body>
</html>