<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-添加个人借款人</title>
	<#include "/content/common/meta.ftl">
<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>	
 <script type="text/javascript" src="${base}/js/jquery/jquery.card.js"></script>
 <script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
	<script type="text/javascript" src="${base}/js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${base}/js/common/ajaxImageUpload.js"></script>
 
 <script type="text/javascript">
$().ready( function() {
	
	var $borrowerForm = $("#borrowerForm");
	
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
            <li>添加个人借款人</li>
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
            <form id="borrowerForm" action="${base}/agency/insertBorrower.do" method="post" target="_blank">
             <h3>个人信息</h3>
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">真实姓名：</td>
                <td>
                		<input class="input2 " name = "user.realName"  type="text"/> &nbsp;&nbsp;&nbsp&nbsp;
                		<input  name="user.sex"  value="1" checked type="radio" />&nbsp;先生</label><label><input type="radio" name="user.sex" value="2" />&nbsp;女士</label>
                </td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">生&nbsp;日：</td>
                <td><input type="text" id = "birthday" name="user.birthday" onclick="WdatePicker()" value="${(user.birthday)!}" class="searchText" style = "width :80px"></td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">身份证号码：</td>
                <td><input class="input2 " name = "user.cardId"/></td>
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
					<a onclick="ajaxFileUploadImageWithRtn('4','imageFile','${base}/file/ajaxUploadImage.do','cardImageBack',uploadFileHideBack);" id="btnLogo1" href="javascript:void(0);" class="kaqu_e1">上传</a>
		         </td>
		         
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">联系号码：</td>
                <td><input class="input2 " name = "user.phone"  type="text"/> </td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">婚姻：</td>
                <td>
	              <input  name="userInfo.marry"  value="1" checked type="radio" />&nbsp;已婚</label><label><input type="radio" name="userInfo.marry" value="2" />&nbsp;离异</label>
	              <label><input type="radio" name="userInfo.marry" value="3" />&nbsp;未婚</label>
	              <label><input type="radio" name="userInfo.marry" value="4" />&nbsp;丧偶</label>
				</td>
              </tr> 
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">联系地址：</td>
                <td><input class="input2 " name = "user.address"  type="text"/> </td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">工作单位：</td>
                <td><input class="input2 " name = "userInfo.companyName"  type="text"/> </td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">担任职位：</td>
                <td>
                 	<select id="companyOffice" name="userInfo.companyOffice"  class="txt">
	                               		<option value="">请选择&nbsp; </option>
	                                  	<@listing_childlist sign="user_company_office"; listingList>
											<#list listingList as listing>
												<option value="${listing.keyValue}" >
													${substring(listing.name, 24, "...")}
												</option>
											</#list>
										</@listing_childlist>
	          		</select>
                 </td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">文化程度：</td>
                <td>
                	<select id="educationRecord" name="userInfo.educationRecord"  class="txt">
			                                <option value="">请选择&nbsp; </option>
					                         <@listing_childlist sign="user_education"; listingList>
												<#list listingList as listing>
													<option value="${listing.keyValue}" >
														${substring(listing.name, 24, "...")}
													</option>
												</#list>
											</@listing_childlist>
	                </select>
                </td>
              </tr>

        	</table> 
<#-- 	
        	 <h3>其他联系人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;新增</a><br/><a href = "javascript:void(0);" class = "kaqu_huankuanzhong" onclick="addUserItem();>【添加】</a></h3>
			<table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <td>姓名</td>
				<td>关系</td>
				<td>联系地址</td>
				<td>联系电话</td>
				<td>操作</td>
            </tr>
          </thead>
          <tbody>
          
                    		<tr height="39">
                    		<td ></td>
                    		<td ></td>
                    		<td ></td>
                    		<td ></td>
                    		<td ></td>
                    	</tr>
			           
          </tbody>
     	 <tfoot>
              <tr>
                <td colspan="9">
					<#if 0==0>
						<div class="nodata">记录为空</div>
					</#if>    
                
                </td>
              </tr>
          </tfoot>
        </table>
 -->         


          <h3>&nbsp;&nbsp;&nbsp;&nbsp;</h3>
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td class="text_r org grayBg" width="40">*</td>
            <td class="text_r grayBg" width="96">开户银行：</td>
            <td>
             <select id="bankid" class="base_sel" name="accountBank.bankId"  style="width:200px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;">
                        <@listing_childlist sign="account_bank"; listingList>
                            <#list listingList as listing>
                                <option value="${listing.keyValue}" <#if (listing.keyValue == accountBankList[0].bankId)!> selected</#if>>${substring(listing.name, 24, "...")}</a>
                                </option>
                            </#list>
                        </@listing_childlist>
             </select>
            </td>
          </tr>
           <tr>
            <td class="text_r org grayBg" width="40">*</td>
            <td class="text_r grayBg" width="96">银行卡号：</td>
            <td><input class="input2 " name = "accountBank.account"  type="text"/></td>
          </tr>
           <tr>
            <td class="text_r org grayBg" width="40">*</td>
            <td class="text_r grayBg" width="96">开户行：</td>
            <td><input class="input2 " name = "accountBank.branch"  type="text"/></td>
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

             <#-->
            <h3>网站信息&nbsp;&nbsp;&nbsp;&nbsp;</h3>
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">用户名：</td>
                <td><input class="input2 " name = "user.username"  type="text"/></td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">请输入密码：</td>
                <td><input class="input2 " id="new_password" name = "user.password" type="password" /></td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">请再次输入密码：</td>
                <td><input class="input2 " type="password" id="new_password2" name="reUserPassword"/></td>
              </tr>                                                                                 
              <tr>
                <td class="text_r grayBg">&nbsp;</td>
                <td class="text_r grayBg">&nbsp;</td>
            	<td height="40">
                	<input class="btn1 white" type="submit" value="提交">
       	   			<input class="btn2 ml_15" type="reset" value="重置">
                </td>
              </tr>
        	</table> -->
            </form>       
        </div>                
    </div>
    
</div>

	<#include "/content/common/footer.ftl">

</body>


<script type="text/javascript">
$(function(){
	$(".tjxx").attr("id","tjxx");
	$("#li_a_tjgrjkr").addClass("li_a_select");

});

<#--  上传图片后回调  -->
function uploadFileHideBack () {
	alert('上传成功！！');
}

<#-- 上传 借款凭证后回调  -->
function uploadFileHideBackItem(aid) {
	alert('上传成功！');
	var obj = $("#"+aid);
	obj.after('<a style=" color:#c43b3b;" href="javascript:void(0);">上传成功</a>');
	obj.remove();
}
</script>
</html>
