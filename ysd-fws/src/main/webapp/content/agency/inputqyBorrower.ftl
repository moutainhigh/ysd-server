<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-添加企业借款人</title>
	<#include "/content/common/meta.ftl">
<script type="text/javascript" src="${base}/static/js/jquery/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>	
 <script type="text/javascript" src="${base}/js/jquery/jquery.card.js"></script>
 <script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
	<script type="text/javascript" src="${base}/js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${base}/js/common/ajaxImageUpload.js"></script>
 
 <script type="text/javascript">
$().ready( function() {
	
	var $borrowerForm = $("#borrowerForm");
	var $areaSelect = $("#areaSelect");
			
	// 地区选择菜单
	$areaSelect.lSelect({
		url: "${base}/area/ajaxArea.do"// AJAX数据获取url
	});
	 // 表单验证
	$borrowerForm.validate({
		rules: {
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
			"userInfo.privatePhone":{
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
			"userInfo.privatePhone":{
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
            <li>添加企业借款人</li>
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
            <form id="borrowerForm" action="${base}/agency/insertqyBorrower.do" method="post">
             <h3>企业基本信息</h3>
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">企业名称：</td>
                <td>
                		<input class="input2 " name = "userInfo.privateName" value="${(userInfo.privateName)!}"  type="text"/>
            	</td>
         	 </tr>
         	 <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">所在地区：</td>
                <td><input type="text" id="areaSelect" name="areaId" class="hidden" <#if user?exists> value="<#if user.area!>${user.area}<#elseif user.city!>${user.city}<#elseif user.province!>${(user.province)!}</#if>"  defaultSelectedPath="${user.province!},${user.city!},${user.area!}" </#if>/>
                </td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">地址：</td>
                <td><input class="input2 " name = "user.address" value="${(user.address)!}"  type="text"/> </td>
              </tr>
              
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">联系人姓名：</td>
                <td><input class="input2 " name = "userInfo.linkman" value="${(userInfo.linkman)!}"  type="text"/> </td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">联系人手机：</td>
                <td><input class="input2 " name = "userInfo.privatePhone" value="${(userInfo.privatePhone)!}"  type="text"/> </td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">主营业务：</td>
                <td><textarea class="textarea" id="userInfoOthers" name="userInfo.others" value="${(userInfo.others)!}" style="width:400px"></textarea> </td>
              </tr>
    		</table> 	
   		<h3>工商注册信息&nbsp;&nbsp;&nbsp;&nbsp;</h3>
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">法人姓名：</td>
                <td><input class="input2 " name = "user.realName" value="${(user.realName)!}"  type="text"/></td>
              </tr>
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">法人身份证：</td>
                <td><input class="input2 " name = "user.cardId"  value="${(user.cardId)!}" type="text"/></td>
              </tr>
         	 <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">营业执照号码：</td>
                <td><input class="input2 " name = "userInfo.registration" value="${(userInfo.registration)!}"  type="text"/></td>
              </tr>
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">税务登记证号：</td>
                <td><input class="input2 " name = "userInfo.taxRegistration"  value="${(userInfo.taxRegistration)!}" type="text"/></td>
              </tr>
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">机构代码证号：</td>
                <td><input class="input2 " name = "userInfo.organization"  value="${(userInfo.organization)!}" type="text"/></td>
              </tr>
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">成立时间：</td>
                <td><input type="text" id = "clsj" name="userInfo.clsj" onclick="WdatePicker()" value="${(userInfo.clsj?string("yyyy-MM-dd"))!}" class="searchText" style = "width :80px">
                </td>
              </tr>
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">营业期限：</td>
              <td><input type="text" id = "yyqxStart" name="userInfo.yyqxStart" onclick="WdatePicker()" value="${(userInfo.yyqxStart?string("yyyy-MM-dd"))!}" class="searchText" style = "width :80px">
                -
                <input type="text" id = "yyqxEnd" name="userInfo.yyqxEnd" onclick="WdatePicker()" value="${(userInfo.yyqxEnd?string("yyyy-MM-dd"))!}" class="searchText" style = "width :80px">
                </td>
              </tr>
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">注册地址：</td>
                <td><input class="input2 " name = "userInfo.address" value="${(userInfo.address)!}"  type="text"/></td>
              </tr>
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">注册资本：</td>
                <td><input class="input2 " name = "userInfo.registeredCapital"  value="${(userInfo.registeredCapital)!}" type="text"/></td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">身份证正面：</td>
              	<td>
            		<input type="hidden"  name="cardImageFore" id="cardImageFore" value="${(user.cardPic1)!}"/>
					<input type="file" id="imageFile" name="imageFile" class="kaqu_w100 kaqu_w102">
					<a onclick="ajaxFileUploadImageWithRtn('4','imageFile','${base}/file/ajaxUploadImage.do','cardImageFore',uploadFileHideBack);" id="btnLogo1" href="javascript:void(0);" class="kaqu_e1">上传</a>
		         </td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">身份证反面：</td>
              	<td>
            		<input type="hidden" name="cardImageBack" id="cardImageBack" value="${(user.cardPic2)!}"/>
					<input type="file" id="imageFile" name="imageFile" class="kaqu_w100 kaqu_w102">
					<a onclick="ajaxFileUploadImageWithRtn('4','imageFile','${base}/file/ajaxUploadImage.do','cardImageBack',uploadFileHideBack);" id="btnLogo2" href="javascript:void(0);" class="kaqu_e1">上传</a>
		         </td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">营业执照：</td>
              	<td>
            		<input type="hidden" name="img1" id="img1" value="${(userInfo.privateCharterImg)!}"/>
					<input type="file" id="imageFile" name="imageFile" class="kaqu_w100 kaqu_w102">
					<a onclick="ajaxFileUploadImageWithRtn('4','imageFile','${base}/file/ajaxUploadImage.do','img1',uploadFileHideBack);" id="btnLogo3" href="javascript:void(0);" class="kaqu_e1">上传</a>
		         </td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">税务登记证：</td>
              	<td>
            		<input type="hidden" name="img2" id="img2" value="${(userInfo.privateTaxImg)!}"/>
					<input type="file" id="imageFile" name="imageFile" class="kaqu_w100 kaqu_w102">
					<a onclick="ajaxFileUploadImageWithRtn('4','imageFile','${base}/file/ajaxUploadImage.do','img2',uploadFileHideBack);" id="btnLogo4" href="javascript:void(0);" class="kaqu_e1">上传</a>
		         </td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">组织机构代码证：</td>
              	<td>
            		<input type="hidden" name="img3" id="img3" value="${(userInfo.privateOrganizationImg)!}"/>
					<input type="file" id="imageFile" name="imageFile" class="kaqu_w100 kaqu_w102">
					<a onclick="ajaxFileUploadImageWithRtn('4','imageFile','${base}/file/ajaxUploadImage.do','img3',uploadFileHideBack);" id="btnLogo5" href="javascript:void(0);" class="kaqu_e1">上传</a>
		         </td>
              </tr>
        	</table>
        	
            <h3>放款账户信息&nbsp;&nbsp;&nbsp;&nbsp;</h3>
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">开户银行：</td>
                <td>
                 <select id="bankid" class="base_sel" name="accountBank.bankId"  style="width:200px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;">
	                <@listing_childlist sign="account_bank"; listingList>
						<#list listingList as listing>
							<option value="${listing.keyValue}" <#if (listing.keyValue == accountBank.bankId)!> selected</#if>>${substring(listing.name, 24, "...")}</a>
							</option>
						</#list>
					</@listing_childlist>
				 </select>
                </td>
              </tr>
               <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">银行卡号：</td>
                <td><input class="input2 " name = "accountBank.account" value="${(accountBank.account)!}"  type="text"/></td>
              </tr>
               <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">开户行：</td>
                <td><input class="input2 " name = "accountBank.branch" value="${(accountBank.branch)!}"  type="text"/></td>
          	</tr> 
               <tr>
                <td class="text_r grayBg">&nbsp;</td>
                <td class="text_r grayBg">&nbsp;</td>
            	<td height="40">
                	<input class="btn1 white" type="submit" value="提交">
       	   			<input class="btn2 ml_15" type="reset" value="重置">
                </td>
              </tr>  
             </table>
            </form>       
        </div>                
    </div>
    
</div>

	<#include "/content/common/footer.ftl">

</body>


<script type="text/javascript">
$(function(){
	$(".tjxx").attr("id","tjxx");
	$("#li_a_tjqyjkr").addClass("li_a_select");

});

<#--上传图片后回调-->
function uploadFileHideBack () {
	alert('上传成功！！');
}

<#--上传 借款凭证后回调-->
function uploadFileHideBackItem(aid) {
	alert('上传成功！');
	var obj = $("#"+aid);
	obj.after('<a style=" color:#c43b3b;" href="javascript:void(0);">上传成功</a>');
	obj.remove();
}
</script>
</html>
