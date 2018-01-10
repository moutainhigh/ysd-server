<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${Application ["qmd.setting.name"]}-我的账户 -个人账户实名认证</title>
<#include "/content/common/meta.ftl">
<script type="text/javascript" src="${base}/static/js/jquery/jquery.card.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.cn.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/static/js/common/ajaxfileupload.js"></script>
<script type="text/javascript" src="${base}/static/js/common/ajaxImageUpload.js"></script>

<script type="text/javascript">
$().ready( function() {
	$("#standard_certification").addClass("current");
	var $realnameForm = $("#realnameForm");
	var $areaSelect = $("#areaSelect");
	
	// 地区选择菜单
	$areaSelect.lSelect({
			url: "${base}/area/ajaxArea.do"// AJAX数据获取url
	});
	 // 表单验证
	$realnameForm.validate({
		ignore: "",
		rules: {
			"user.realName":{
				required: true,
				realName:true,
				minlength:2,
				maxlength:20
			},
			"user.phone":{
				required:true,
				phone:true
			},
		//	"user.birthday":{
		//		required:true,
		//		dateISO:true
		//	},
			"user.cardId":{
				required: true,
				isIdCardNo:true,
				remote:"${base}/userCenter/checkCardiId.do"
			},
			"cardImageFore":{
				required:true
			},
			"cardImageBack":{
				required:true
			},
			"user.payPassword":{
				required: true,
				remote:"${base}/userCenter/ajaxPayPassword.do"
			}
		},
		messages: {
			"user.realName":{
				required: "真实姓名不能为空",
				realName:"真实姓名格式错误",
				minlength: "最少2个字",
				maxlength: "最多20个字"
			},
			"user.phone":{
				required:"手机号不能为空"
			},
		//	"user.birthday":{
		//		required:"生日不能为空",
		//		dateISO:"生日格式错误"
		//	},
			"user.cardId":{
				required:"身份证号码不能为空",
				isIdCardNo:"身份证号码格式不对",
				//remote:"该身份证号已认证"
				remote:"该身份证号已认证"
			},
			"cardImageFore":{
				required:"请上传身份证"
			},
			"cardImageBack":{
				required:"请上传身份证"
			},
			"user.payPassword":{
				required: "请输入安全密码",
				remote:"安全密码输入错误"
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
	
	// 表单验证
	//$.validator.addMethod("fileListRequired", $.validator.methods.required, "请选择上传证件图片");
	//$.validator.addMethod("fileListImageFile", $.validator.methods.imageFile, "证件图片格式错误");
	
	//$.validator.addClassRules("fileListClass", {
	//	fileListRequired:true,
	//	fileListImageFile:true
	//});
	
});

function uploadFileHideBack() {
	alert("上传成功！");
}

$(function(){
	$("#member_realname").addClass("nsg nsg_a1");
});
</script>
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">



<div class="content">
  <div style="width:955px; margin:0 auto; padding-bottom:20px;">
    <#include "/content/common/user_center_header.ftl">
    
    <div style="width:955px; float:left; background:#fff; clear:both;">
        <#include "/content/common/user_center_left.ftl">
        
        <div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
			<a style="color:#646464;font-family:'宋体';">${Application ["qmd.setting.name"]}</a><a>></a>
			<a style="color:#646464;font-family:'宋体';" href="${base}/userCenter/index.do">我的账户</a><a>></a>
			<a style="color:#646464;font-family:'宋体';" >个人账户认证</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            		
					<a href="javascript:void(0);" class="hr hre">个人账户认证</a>         
          </div>

           <div style="height:23px;line-height:23px;text-align:center;color:#595757;font-family:'宋体';margin-top:100px; margin-bottom:30px; clear:both;"></div>  
        
  <#if loginUser.realStatus==0>
   <form id="realnameForm" method="post" action="realnameUpdate.do" enctype="multipart/form-data">
    <table width="100%" cellpadding="0" cellspacing="0">
     <tbody>
       <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">用户名：</td>
         <td style="color:#646464;font-family:'宋体';">${loginUser.username}</td>
       </tr>
       <tr height="18"></tr>
       <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">真实姓名：</td>
         <td style="color:#646464;font-family:'宋体';"><input name="user.realName" id="realName" value = "${loginUser.realName!}" style="width:170px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/></td>
       </tr>
       <tr height="18"></tr>
       <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">姓&nbsp;别：</td>
         <td style="color:#646464;font-family:'宋体';"><label style=" margin-right:20px;" ><input  name="user.sex"  value="1" <#if loginUser.sex=='' || loginUser.sex==1 >checked</#if> type="radio" />&nbsp;先生</label><label><input type="radio" name="user.sex" value="2" <#if loginUser.sex == 2>checked</#if>/>&nbsp;女士</label></td>
       </tr>
       <tr height="18"></tr>
       <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">手机号码：</td>
         <td style="color:#646464;font-family:'宋体';"><input name="user.phone" id="phone" value = "${loginUser.phone}" <#if loginUser.phoneStatus!=0 >readOnly</#if> type="text" style="width:170px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/></td>
       </tr>
      <#-- <tr height="18"></tr>
       <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">出生日期：</td>
         <td style="color:#646464;font-family:'宋体';">
           <input name="user.birthday" value="${(loginUser.birthday?string("yyyy-MM-dd"))!}" onclick="WdatePicker()" type="text" style="width:170px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
           <a class="checked_v7">格式：1999-09-01</a>
         </td>
       </tr>-->
       <tr height="18"></tr>
       <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">所在地区：</td>
     	<td style="color:#646464;font-family:'宋体';"><input type="text" id="areaSelect" name="areaId" class="hidden" value="<#if loginUser.area!>${loginUser.area}<#elseif loginUser.city!>${loginUser.city}<#else>${loginUser.province}</#if>" defaultSelectedPath="${loginUser.province!},${loginUser.city!},${loginUser.area!}" /></td>
       </tr>
       <tr height="18"></tr>
       <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">身份证号码：</td>
         <td style="color:#646464;font-family:'宋体';"><input name="user.cardId" value="${loginUser.cardId!}" id="cardId"  type="text" style="width:170px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/></td>
       </tr>
     <tr height="18"></tr>
      <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">身份证正面：</td>
         <td style="color:#646464;font-family:'宋体';">
           	<input type="file" name="imageFile"  class="fileListClass" id="fileCardFore"/>
			<input type="hidden" name="cardImageFore" id="cardImageFore" />
           <a class="checked_v8" id="btnUploadFore" onclick="ajaxFileUploadImageWithRtn('fileCardFore','${base}/file/ajaxUploadImage.do','cardImageFore',uploadFileHideBack);" style="display:inline-block; width:55px; height:32px; line-height:32px; background:#ffc333; color:#000; text-align:center;border-radius:5px;font-family:'宋体';"/>上传</a>
         </td>
       </tr>
       <tr height="18"></tr>
       <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">身份证背面：</td>
         <td style="color:#646464;font-family:'宋体';">
         	<input type="file" name="imageFile"  class="fileListClass" id="fileCardBack"/>
			<input type="hidden" name="cardImageBack" id="cardImageBack" />
			<a class="checked_v8"  id="btnUploadBack" onclick="ajaxFileUploadImageWithRtn('fileCardBack','${base}/file/ajaxUploadImage.do','cardImageBack',uploadFileHideBack);"  style="display:inline-block; width:55px; height:32px; line-height:32px; background:#ffc333; color:#000; text-align:center;border-radius:5px;font-family:'宋体';"/>上传</a>
         </td>
       </tr>
       <tr height="18"></tr>
       <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">安全密码：</td>
         <td style="color:#646464;font-family:'宋体';">
         	<#if loginUser.payPassword!>
         		<input name="user.payPassword" id="payPassword" type="password" style="width:170px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
         	<#else>
          		<a href="${base}/userCenter/toPayPassword.do" >请先设置一个安全密码</a>
          		<input type="hidden"  name="user.payPassword">
          	</#if>
        	 
         </td>
       </tr>
       <tr height="32"></tr>
       <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';"></td>
         <td style="color:#646464;font-family:'宋体';">
          <input type="submit" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" value="确定"/>
          <input type="submit" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#d4d4d4; color:#000; text-align:center; font-size:16px;border-radius:5px;" value="取消"/>
         </td>
       </tr>
     </tbody>
    </table>
    </form>
    <#else>
			 <table width="100%" cellpadding="0" cellspacing="0">
			     <tbody>
			       <tr>
			         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">用户名：</td>
						<td style="color:#646464;font-family:'宋体';">${loginUser.username}　　<!--<a href=""><b class="c1">去验证&gt;</b></a>--></td>
					</tr>
				   <tr height="18"></tr>
				  	 <tr>
				 		<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">真实姓名：</td>
						<!--<td>${substring(loginUser.realName, 1, "***")}</td>-->
						<td style="color:#646464;font-family:'宋体';">${loginUser.showRealName}</td>
					</tr>
					   <tr height="18"></tr>
					   <tr>
						<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">姓&nbsp;别：</td>
						<td style="color:#646464;font-family:'宋体';"> <#if loginUser.sex=='1'>先生<#else>女士</#if></td>
					</tr>
				       <tr height="18"></tr>
				       <tr>
				         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">手机号码：</td>
						<td style="color:#646464;font-family:'宋体';">
							${loginUser.phone}
				       </tr>
				       <tr height="18"></tr>
				       <tr>
				         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">出生日期：</td>
				         <td style="color:#646464;font-family:'宋体';">${(loginUser.birthday?string("yyyy-MM-dd"))!}</td>
					</tr>
			       	<tr height="18"></tr>
			       	<tr>
		        		 <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">所在地区：</td>
						<td style="color:#646464;font-family:'宋体';">${loginUser.areaStore}</td>
					</tr>
					<tr height="18"></tr>
			      	<tr>
				         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">身份证号码：</td>
						<td style="color:#646464;font-family:'宋体';"> ${substring(loginUser.cardId, loginUser.cardId?length-6, "******")}</td>
					</tr>
					<!--
					<tr>
						<td class="item">身份证正面：</td>
						<td></td>
					</tr>
					<tr>
						<td class="item">身份证背面：</td>
						<td></td>
					</tr>
					--><tr height="18"></tr>
			      	<tr>
						<td class="item"></td>
						<td >
							<#if loginUser.realStatus==1>
								<input  type="button" id = "button" value="已通过" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;"/>
							<#elseif loginUser.realStatus==2>
								<input type="button" id = "button" value="审核中" display:inline-block; width:145px; height:39px; line-height:39px; background:#d4d4d4; color:#000; text-align:center; font-size:16px;border-radius:5px;/>
							</#if>
			         </td>
			       </tr>
			     </tbody>
			    </table>
    </#if>
            
            
            
        </div>
    </div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->




<#include "/content/common/footer.ftl">
</body>

</html>
