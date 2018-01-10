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
 
 <script type="text/javascript">
$().ready( function() {
	
	var $borrowerForm = $("#borrowerForm");
	
	 // 表单验证
	$borrowerForm.validate({
		rules: {
			"user.username":{
				required: true,
				username: true,
				minlength: 4,
				maxlength: 16,
				remote:{
					type:"POST",
					url: "${base}/user/checkUsernameReg.do"
				}
			},
			"user.password":{
				required: true,
				minlength: 8,
				maxlength: 16,
			},
			"reUserPassword":{
				required: true,
				equalTo:"#new_password"
			},
			"user.realName":{
				required: true,
				realName:true,
				minlength:2,
				maxlength:20
			},
			"accountBank.account":{
				required: true,
				minlength: 12,
				maxlength:26
			},
			"accountBank.branch":{
				required: true,
				minlength: 5,
				maxlength: 50
			},
			"user.phone":{
				required:true,
				phone:true
			},
			
			"user.cardId":{
				required: true,
				isIdCardNo:true,
				remote:"${base}/userCenter/checkCardiId.do"
			}
		},
		messages: {
			"user.username":{
				required: "请输入用户名",
				username: "用户名只允许包含中文、英文、数字和下划线",
				minlength: "用户名必须大于等于4",
				maxlength: "用户名必须小于等于16",
				remote: "用户名已存在或包含敏感字符，请重新输入"
			},
			"user.password":{
				required: "请输入密码",
				minlength: "密码必须大于等于8",
				maxlength: "密码必须小于等于16"
			},
			"reUserPassword": {
				required: "请再次输入密码",
				equalTo: "两次密码输入不一致"
			},
			"user.realName":{
				required: "真实姓名不能为空",
				realName:"真实姓名格式错误",
				minlength: "最少2个字",
				maxlength: "最多20个字"
			},
			"accountBank.account":{
				required: "请输入银行账号",
				minlength: "银行账号最少10位",
				maxlength: "银行账号最多21位"
			},
			"accountBank.branch":{
				required: "请输入开户行",
				minlength: "最少5个字符",
				maxlength: "最多50个字符"
			},
			"user.phone":{
				required:"手机号不能为空"
			},
			"user.cardId":{
				required:"身份证号码不能为空",
				isIdCardNo:"身份证号码格式不对",
				remote:"该身份证号之前已经通过实名认证"
			}
		},
		errorPlacement: function(error, element) {
		  error.appendTo(element.parent());
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});

});
function addUserItem() {

	var x=parseInt($("#idSort").val());
	x = x + 1;
	$("#idSort").val(x);
	
	var str = '<tr id="trid_'+x+'">';
	str += '<td height="39">';
	str += ' <input type="text" name="vouchersName" class="base_txt" style="width:100px;"/>';
	str += '</td>';
	str += '<td >';
	str += ' <input type="text" name="vouchersGuan" class="base_txt" style="width:100px;"/>';
	str += '</td>';
	str += '<td >';
	str += ' <input type="text" name="vouchersArdd" class="base_txt" style="width:100px;"/>';
	str += '</td>';
	str += '<td >';
	str += ' <input type="text" name="vouchersPhone" class="base_txt" style="width:100px;"/>';
	str += '</td>';
    str += '<td>';
    str += '	<input type="button" value="删除" onclick="moveOut(\''+x+'\')" /></td>';
    str += '</tr>';
    
    var tr_len = $("#table_img tr").length;
	if(tr_len==0) {
		$("#table_img").html(str);
	} else {
		$("#table_img").find('tr:last').after(str);
	}
}

function moveOut(x) {
	var obj = $("#trid_"+x);
	obj.remove();
}
</script>
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
                <td class="text_r grayBg" width="96">真实姓名：</td>
                <td>
                	${(user.realName)!} &nbsp;<#if user.sex==1>先生<#elseif user.sex==2>女士</#if>
                </td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">生&nbsp;日：</td>
                <td>${(user.birthday?string("yyyy-MM-dd"))!}</td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">身份证号码：</td>
                <td> ${(user.cardId)!}</td>
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
                <td class="text_r grayBg">联系号码：</td>
                <td>${(user.phone)!} </td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">婚姻：</td>
                <td>
                <#if userInfo.marry==1>已婚<#elseif userInfo.marry==2>离异<#elseif userInfo.marry==3>未婚<#elseif userInfo.marry==4>丧偶</#if>
				</td>
              </tr> 
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">联系地址：</td>
                <td>${(user.address)!}</td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">工作单位：</td>
                <td>${(userInfo.companyName)!} </td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">担任职位：</td>
                <td>
	                <@listing_childname sign="user_company_office" key_value="${(userInfo.companyOffice)!}"; accountBankName>
						${accountBankName}
					</@listing_childname>             		
                 </td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">文化程度：</td>
                <td>
	                <@listing_childname sign="user_education" key_value="${(userInfo.educationRecord)!}"; accountBankName>
						${accountBankName}
					</@listing_childname> 
                </td>
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
