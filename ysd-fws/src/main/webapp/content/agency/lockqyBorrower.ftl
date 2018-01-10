<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-个人借款人详情</title>
	<#include "/content/common/meta.ftl">
<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>	
 <script type="text/javascript" src="${base}/js/jquery/jquery.card.js"></script>
 <script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
 
</head>
<body class="body_bg">
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_customer.ftl">

<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="${base}/userCenter/index.do">账户中心</a></li>
			<li><a href="${base}/agency/borrowerList.do">客户管理</a></li>
            <li>借款人详情</li>
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
            <form id="borrowerForm" action="${base}/agency/insertBorrower.do" method="post">
             <h3>个人信息</h3>
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">企业名称：</td>
                <td>
                	${(userInfo.privateName)!}
                </td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">所在地区：</td>
                <td>${(user.areaStore)!}</td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">地址：</td>
                <td> ${(user.address)!}</td>
              </tr>
          
              
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">联系人姓名：</td>
                <td> ${(userInfo.linkman)!}</td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">联系人手机：</td>
                <td> ${(userInfo.privatePhone)!}</td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">主营业务：</td>
                <td> ${(userInfo.others)!}</td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">地址：</td>
                <td> ${(user.address)!}</td>
              </tr>
              
        	</table> 
        	<h3>工商注册信息&nbsp;&nbsp;&nbsp;&nbsp;</h3>
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">法人姓名：</td>
                <td>
                       ${user.realName}
				 </select>
                </td>
              </tr>
               <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">法人身份证：</td>
                <td>${(user.cardId)!}</td>
              </tr>
               <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">营业执照号码：</td>
                <td>${(userInfo.registration)!}</td>
              </tr>   
          
               <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">税务登记证号：</td>
                <td>${(userInfo.taxRegistration)!}</td>
              </tr>   
               <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">机构代码证号：</td>
                <td>${(userInfo.organization)!}</td>
              </tr>   
               <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">成立时间：</td>
                <td>${(userInfo.clsj?string("yyyy-MM-dd"))!}</td>
              </tr>   
               <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">营业期限：</td>
                <td>${(userInfo.yyqxStart?string("yyyy-MM-dd"))!} - ${(userInfo.yyqxEnd?string("yyyy-MM-dd"))!}</td>
              </tr>   
               <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">注册地址：</td>
                <td>${(userInfo.address)!}</td>
              </tr>   
               <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">注册资本：</td>
                <td>${(userInfo.registeredCapital)!}</td>
              </tr> 
          
              
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">身份证正面：</td>
                <td> 
                	<@imageUrlDecode imgurl="${user.cardPic1}"; imgurl>
						<img src = "${imgurl}" width="120" heigth="80" />
					</@imageUrlDecode>
				</td>
         	   </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">身份证反面：</td>
                <td> 
                	<@imageUrlDecode imgurl="${user.cardPic2}"; imgurl>
						<img src = "${imgurl}" width="120" heigth="80" />
					</@imageUrlDecode></td>
             </tr>
              
              
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">营业执照：</td>
                <td> 
                	<@imageUrlDecode imgurl="${userInfo.privateCharterImg}"; imgurl>
						<img src = "${imgurl}" width="120" heigth="80" />
					</@imageUrlDecode></td>
       		  </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">税务登记证：</td>
                <td> 
                	<@imageUrlDecode imgurl="${userInfo.privateTaxImg}"; imgurl>
						<img src = "${imgurl}" width="120" heigth="80" />
					</@imageUrlDecode></td>
             </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">组织机构代码证：</td>
                <td> 
                	<@imageUrlDecode imgurl="${userInfo.privateOrganizationImg}"; imgurl>
						<img src = "${imgurl}" width="120" heigth="80" />
					</@imageUrlDecode></td>
             </tr>
          
             </table>  
            <h3>放款账户信息&nbsp;&nbsp;&nbsp;&nbsp;</h3>
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">开户银行：</td>
                <td>
                         <@listing_childname sign="account_bank" key_value="${accountBank.bankId}"; accountBankName>
								${accountBankName}
						</@listing_childname>
				 </select>
                </td>
              </tr>
               <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">银行卡号：</td>
                <td>${(accountBank.account)!}</td>
              </tr>
               <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">开户行：</td>
                <td>${(accountBank.branch)!}</td>
              </tr>   
             </table>     
        </div>                
    </div>
</div>
	<#include "/content/common/footer.ftl">
</body>


<script type="text/javascript">
$(function(){
	$(".tjxx").attr("id","tjxx");
	$("#li_a_jkrlb").addClass("li_a_select");

});
</script>
</html>
