<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-我的账户-企业账户认证</title>
	<#include "/content/common/meta.ftl">
</head>
<body>

 <!-- header -->
<#include "/content/common/header.ftl">
<div style="width:100%; background:url(${base}/static/img/d6.png) 0 0 repeat-x; min-width:1000px; padding-bottom:30px;" class="biaoti_bg">
<div class="content">
 <div class="biaoti">
  <a href="${base}/">${Application ["qmd.setting.name"]}</a><a>> </a>
  <a href="${base}/userCenter/index.do">我的账户</a>
  <a class="liebiao">企业账户认证</a>
 </div><!--biaoti end-->
<div style="width:1000px;" class="big_content">
<!-- content -->
<div class="admin clearfix">

	<#include "/content/common/user_center_left.ftl">
	
	
	

<div class="right_jiluneirong">
  <div class="tabsblk">
    <a href=""  class="checked">企业账户认证</a>
  </div>
  <div class="sepblk"></div>
  <div class="inputblk">
   <form id="realnameForm" method="post" action="enterpriseUpdate.do" enctype="multipart/form-data">
	 <#if loginUser.realStatus==0>
    <table width="100%" cellpadding="0" cellspacing="0" class="checked_v4">
     <tbody>
       
       
       <tr>
			<td width="90" class="checked_v5">企业名称：</td>
			<td><input type="text" style="width:178px; height:24px; border:1px solid #7d7d7d;" name="userInfo.privateName" id="privateName" value="${loginUserInfo.privateName}" /></td>
		</tr><tr height="18"></tr>
		<tr>
			<td class="checked_v5">法人姓名：</td>
			<td><input type="text" style="width:178px; height:24px; border:1px solid #7d7d7d;" name="user.realName" id="realName" /></td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="checked_v5">企业登记号：</td>
			<td><input type="text" name="userInfo.registration" style="width:178px; height:24px; border:1px solid #7d7d7d;" id="registration" /></td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="checked_v5">营业执照：</td>
			<td><input type="file" name="fileList[0]"  class="fileListClass" >&nbsp;</td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="checked_v5">税务登记号：</td>
			<td><input type="text" name="userInfo.taxRegistration" style="width:178px; height:24px; border:1px solid #7d7d7d;"  id="taxRegistration"  ></td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="checked_v5">税务登记证：</td>
			<td><input type="file" name="fileList[1]" class="fileListClass">&nbsp;</td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="checked_v5">组织机构号：</td>
			<td><input type="text" name="userInfo.organization" style="width:178px; height:24px; border:1px solid #7d7d7d;"  id="cardId"  ></td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="checked_v5">组织机构代码证：</td>
			<td><input type="file" name="fileList[2]" class="fileListClass">&nbsp;</td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="checked_v5">开户许可证：</td>
			<td><input type="file" name="fileList[3]" class="fileListClass">&nbsp;</td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="checked_v5">安全密码：</td>
			<td> 
				<#if loginUser.payPassword!>
					<input type="password" name="user.payPassword" style="width:178px; height:24px; border:1px solid #7d7d7d;"  id="payPassword"/>
				<#else>
					<a href="${base}/userCenter/toPayPassword.do" >请先设置一个安全密码</a>
					<input type="hidden"  name="user.payPassword">
				</#if>
			</td>
		</tr><tr height="18"></tr>
		
       <tr>
         <td class="checked_v5"></td>
         <td class="checked_v6">
          <input type="submit" class="queding" value="确定"/>
          <input type="reset" class="quxiao" value="取消"/>
         </td>
       </tr>
     </tbody>
    </table>
    <#else>
    
	<table width="100%" cellpadding="0" cellspacing="0" class="checked_v4">
     <tbody>
       
       
       <tr>
			<td width="90" class="checked_v5">企业名称：</td>
			<td>${loginUserInfo.privateName}</td>
		</tr><tr height="18"></tr>
		<tr>
			<td class="checked_v5">法人姓名：</td>
			<td>${loginUser.showRealName}</td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="checked_v5">企业登记号：</td>
			<td>${loginUserInfo.registration}</td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="checked_v5">营业执照：</td>
			<td></td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="checked_v5">税务登记号：</td>
			<td>${loginUserInfo.taxRegistration}</td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="checked_v5">税务登记证：</td>
			<td></td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="checked_v5">组织机构号：</td>
			<td>${loginUserInfo.organization}</td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="checked_v5">组织机构代码证：</td>
			<td></td>
		</tr><tr height="18"></tr>
		<tr>
			<td width="90" class="checked_v5">开户许可证：</td>
			<td></td>
		</tr><tr height="18"></tr>
		
		<tr>
         <td class="checked_v5"></td>
         <td class="checked_v6">
	        <#if loginUser.realStatus==1>
				<input type="button" id = "button" value="已通过" class="queding"/>
			<#elseif loginUser.realStatus==2>
				<input type="button" id = "button" value="审核中" class="queding"/>
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
						required: "请输入安全密码",
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
