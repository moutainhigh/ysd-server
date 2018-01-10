<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-企业账户认证</title>
	<#include "/content/common/meta.ftl">
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
			<a style="color:#646464;font-family:'宋体';" >企业账户认证</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            		
					<a href="javascript:void(0);" class="hr hre">企业账户认证</a>         
          </div>

          
   <form id="realnameForm" method="post" action="enterpriseUpdate.do" enctype="multipart/form-data">
	 <#if loginUser.realStatus==0>
    <table width="100%" cellpadding="0" cellspacing="0">
     <tbody>
       <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">企业名称：</td>
			<td style="color:#646464;font-family:'宋体';"><input type="text" style="width:170px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"name="userInfo.privateName" id="privateName" value="${loginUserInfo.privateName}" /></td>
		</tr><tr height="18"></tr>
		<tr>
			<td  style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">法人姓名：</td>
			<td style="color:#646464;font-family:'宋体';"><input type="text" style="width:178px; height:24px; border:1px solid #7d7d7d;" name="user.realName" id="realName" /></td>
		</tr><tr height="18"></tr>
		<tr>
			<td  style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">企业登记号：</td>
			<td style="color:#646464;font-family:'宋体';"><input type="text" name="userInfo.registration" style="width:200px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" id="registration" /></td>
		</tr><tr height="18"></tr>
		<tr>
			<td  style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">营业执照：</td>
			<td style="color:#646464;font-family:'宋体';"><input type="file" name="fileList[0]"  class="fileListClass" >&nbsp;</td>
		</tr><tr height="18"></tr>
		<tr>
			<td  style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">税务登记号：</td>
			<td style="color:#646464;font-family:'宋体';"><input type="text" name="userInfo.taxRegistration" style="width:200px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"  id="taxRegistration"  ></td>
		</tr><tr height="18"></tr>
		<tr>
			<td  style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">税务登记证：</td>
			<td style="color:#646464;font-family:'宋体';"><input type="file" name="fileList[1]" class="fileListClass">&nbsp;</td>
		</tr><tr height="18"></tr>
		<tr>
			<td  style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">组织机构号：</td>
			<td style="color:#646464;font-family:'宋体';"><input type="text" name="userInfo.organization" style="width:200px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"  id="cardId"  ></td>
		</tr><tr height="18"></tr>
		<tr>
			<td  style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">组织机构代码证：</td>
			<td style="color:#646464;font-family:'宋体';"><input type="file" name="fileList[2]" class="fileListClass">&nbsp;</td>
		</tr><tr height="18"></tr>
		<tr>
			<td  style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">开户许可证：</td>
			<td style="color:#646464;font-family:'宋体';"><input type="file" name="fileList[3]" class="fileListClass">&nbsp;</td>
		</tr><tr height="18"></tr>
		<tr>
			<td  style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">交易密码：</td>
			<td style="color:#646464;font-family:'宋体';"> 
				<#if loginUser.payPassword!>
					<input type="password" name="user.payPassword" style="width:178px; height:24px; border:1px solid #7d7d7d;"  id="payPassword"/>
				<#else>
					<a href="${base}/userCenter/toPayPassword.do" >请先设置一个交易密码</a>
					<input type="hidden"  name="user.payPassword">
				</#if>
			</td>
		</tr><tr height="18"></tr>
		
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
    <#else>
    
	 <table width="100%" cellpadding="0" cellspacing="0">
	 <tbody>
       <tr>
			<td width="90" class="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">企业名称：</td>
			<td  style="color:#646464;font-family:'宋体';">${loginUserInfo.privateName}</td>
		</tr><tr height="18"></tr>
		<tr>
			<td class="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">法人姓名：</td>
			<td style="color:#646464;font-family:'宋体';">${loginUser.showRealName}</td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">企业登记号：</td>
			<td style="color:#646464;font-family:'宋体';">${loginUserInfo.registration}</td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">营业执照：</td>
			<td style="color:#646464;font-family:'宋体';"></td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">税务登记号：</td>
			<td style="color:#646464;font-family:'宋体';">${loginUserInfo.taxRegistration}</td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">税务登记证：</td>
			<td style="color:#646464;font-family:'宋体';"></td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">组织机构号：</td>
			<td style="color:#646464;font-family:'宋体';">${loginUserInfo.organization}</td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">组织机构代码证：</td>
			<td style="color:#646464;font-family:'宋体';"></td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">开户许可证：</td>
			<td style="color:#646464;font-family:'宋体';"></td>
		</tr><tr height="18"></tr>
		
			<tr>
				<td class="item"></td>
					<td >
					<#if loginUser.realStatus==1>
						<input  type="button" id = "button" value="已通过" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;"/>
					<#elseif loginUser.realStatus==2>
					  <input type="button" id = "button" value="审核中" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#d4d4d4; color:#000; text-align:center; font-size:16px;border-radius:5px;" />
				</#if>
			    </td>
			</tr>
		</tbody>
	</table>
    </#if>
    </form>
  </div>
  <div class="fanye"></div>
</div><!--right_jiluneirong end-->
<#--备份10-->
	

<#--备份10-->
<#include "/content/common/footer.ftl">
<div style="clear:both;"></div>
</div><!--big_content end-->
</div><!--content end-->
</div><!--biaoti_bg end-->
</body>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript">
		$().ready( function() {
			$("#standard_certification").addClass("current");
			var $realnameForm = $("#realnameForm");

			 // 表单验证
			$realnameForm.validate({
				rules: {
					"userInfo.privateName":{
						required: true,
						minlength:2,
						maxlength:200
					},
					"user.realName":{
						required: true,
						realName: true,
						minlength:2,
						maxlength:20
					},
					"userInfo.registration":{
						required: true,
						minlength:2,
						maxlength:100
					},
					"userInfo.taxRegistration":{
						required: true,
						minlength:2,
						maxlength:100
					},
					"userInfo.organization":{
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
					"userInfo.privateName":{
						required: "企业名称不能为空!",
						minlength: "最少2个字",
						maxlength: "最多100个字"
					},
					"user.realName":{
						required: "企业法人不能为空",
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
					"user.payPassword":{
						required: "请输入交易密码",
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
