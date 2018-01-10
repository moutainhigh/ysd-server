<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-我的账户-<#if loginUser.payPassword!>修改<#else>设置</#if>安全密码</title>
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
			<a style="color:#646464;font-family:'宋体';" >修改安全密码</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            		
					<a href="${base}/userCenter/toPassword.do" class="hr ">登录密码</a>   
					<a href="javascript:void(0);" class="hr hre" >安全密码</a>      
          </div>

           <div style="text-align:center;color:#595757;font-family:'宋体';margin-top:100px; margin-bottom:30px; clear:both;"></div>  
        
   <form id="payPasswordForm" method="post">
   <#if loginUser.realStatus!>
    	<input type="hidden" id="strn" name="strn" value="${loginUser.realStatus}">
    <#else>
    	<input type="hidden" id="strn" name="strn" value="0">
    </#if>
   <input type="hidden" name="p" value="2">
    <table width="100%" cellpadding="0" cellspacing="0" class="checked_v4">
     <tbody>
  		<#if loginUser.payPassword!>
       <tr>
         <td style="width:110px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">我的旧密码：</td>
         <td class="checked_v6">
           <input id="old_payPassword" name = "user.payPassword" type="password" style="width:250px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
         	
         </td>
         
       </tr>
       <tr height="18"></tr>
       </#if>
       <tr>
         <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">请输入新密码：</td>
         <td class="checked_v6"><input id="new_payPassword" name = "newPayPassword" type="password" style="width:250px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
         	
         </td>
       	
       </tr>
       <tr height="18"></tr>
       <tr>
         <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">请再次输入新密码：</td>
         <td class="checked_v6"><input id="new_payPassword_again" name = "newPayPasswordAgain" type="password" style="width:250px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
         	
         </td>
       	
       </tr>
       <tr height="18"></tr>
       <tr>
         <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';"></td>
         <td class="checked_v6">
          <input type="submit" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" value="确定"/>
          <a href="${base}/user/lostpass.do?p=1" style="color:#D14324;">忘记密码？</a>
         </td>
       </tr>
     </tbody>
    </table>
	</form>
            
            
            
        </div>
    </div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->



<#include "/content/common/footer.ftl">
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
  <script type="text/javascript" src="${base}/static/js/jquery/password_strength_plugin.js"></script>
		<script type="text/javascript">
		$().ready( function() {
			
			$("#pwd_management_left").addClass("current");
			//$(".password_adv").passStrength({ 
			//	messageloc: 0 
			//}); 
			
			var $payPasswordForm = $("#payPasswordForm");
			 // 表单验证
			$payPasswordForm.validate({
				rules: {
					"user.payPassword":{
						required: true,
						remote:"${base}/userCenter/ajaxPayPassword.do"
					},
					"newPayPassword":{
						required: true,
						strongTxt:true,
						minlength: 8,
						maxlength: 20
					},
					"newPayPasswordAgain":{
						required: true,
						equalTo: "#new_payPassword"
					}
				},
				messages: {
					"user.payPassword":{
						required: "请输入当前安全密码",
						remote:"当前密码输入错误"
					},
					"newPayPassword":{
						required: "请输入新安全密码",
						minlength: "密码必须大于等于8",
						maxlength: "密码必须小于等于20"
					},
					"newPayPasswordAgain":{
						required: "请再次输入新安全密码",
						equalTo: "两次安全密码输入不一致"
					}
				},
				errorClass:"erron",
				
				errorPlacement: function(error, element) {
				  error.appendTo(element.parent());
				},
				submitHandler: function(form) {
					$.ajax({
						url: "${base}/userCenter/ajaxPasswordUpdate.do",
						data: $payPasswordForm.serialize(),
						type: "POST",
						dataType: "json",
						cache: false,
						beforeSend: function() {
							$payPasswordForm.find("submit").attr("disabled", true);
						},

						success: function(data) {
							<#--$.message({type: data.status, content: data.message});-->
							if(data.status=="error"){
								$("#old_payPassword").val("");
								$("#old_payPassword").focus();
								alert(data.message);
							}
							if(data.status=="success"){
								alert(data.message);
								setTimeout(function(){
								$("#old_payPassword").val("");
								$("#new_payPassword").val("");
								$("#new_payPassword_again").val("");
								},2000);
								if($("#strn").val()==0){
									window.location.href = "${base}/userCenter/realname.do";
								}else{
									window.location.reload();
								}
								
							}
						}
					});
				}
			});
			
			
			
		});
$(function(){
	$("#member_password").addClass("nsg nsg_a1");
});
	</script>
</body>
</html>
