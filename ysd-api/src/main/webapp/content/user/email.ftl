<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-我的账户-邮箱认证</title>
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
			<a style="color:#646464;font-family:'宋体';" >邮箱认证</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            		
					<a href="javascript:void(0);" class="hr hre">邮箱认证</a>      
          </div>

           <div style="text-align:center;color:#595757;font-family:'宋体';margin-top:100px; margin-bottom:30px; clear:both;"></div>  
        
   <#if loginUser.emailStatus==0 || p=="again">
  <div class="formblk">
  <form method="post"  action="emailSend.do">
   <table width="100%" cellpadding="0" cellspacing="0" class="checked_v4">
     <tbody>
     	
       <tr>
         <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">认证邮箱：</td>
         <td class="checked_v6"><input name="user.email" id="email" value = "<#if p!= 'again'>${loginUser.email}</#if>" type="text" style="width:250px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/></td>
       </tr>
       <tr height="18"></tr>
       <tr>
         <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">安全密码：</td>
         <td class="checked_v6">
       		<#if loginUser.payPassword!>
				<input type="password" name="user.payPassword" id="payPassword"  style="width:250px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
			<#else>
				<a href="${base}/userCenter/toPayPassword.do" >请先设置一个安全密码</a>
				<input type="hidden"  name="user.payPassword">
			</#if>
         	
         </td>
       </tr>
       <tr height="18"></tr>
       <tr>
         <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';"></td>
         <td class="checked_v6">
          <input type="submit" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" value="确定"/>
          <input type="reset" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#d4d4d4; color:#000; text-align:center; font-size:16px;border-radius:5px;" value="取消"/>
         </td>
       </tr>
       
     </tbody>
    </table>
    </form>
    <#elseif loginUser.emailStatus==1 && p!="again">
    <div class="inputblk">
    
       	 <table width="510" cellpadding="0" cellspacing="0" style=" margin:50px auto">
          <tbody>
	       	<tr height="25">
		       	<td><span style="color:#363636; font-size:14px;">您的邮箱${loginUser.email}已经认证</span></td>
		        <td><#--<a style="font-size:12px; color:#D14324; padding-left:0px;font-weight:bold;" href="#">请进入验证!</a>--></td></tr>
	         <tr height="25">
   		   <td>
       		<a href="${base}/userCenter/toEmail.do?p=again" style="font-size:14px; color:green; padding-left:5px; font-weight:bold;" href="#">重新验证</a>
 		  </td>
         </tr>
      	</tbody>
       </table>
       <#elseif loginUser.emailStatus==2>
       <div class="inputblk">
    
       <table width="510" cellpadding="0" cellspacing="0" style=" margin:50px auto">
          <tbody>
       	<tr height="25">
                <td><span style="color:#363636; font-size:14px;">已经向您的邮箱${loginUser.email}发送了一封验证邮件，请进入验证！</span></td>
                <td><#--<a style="font-size:12px; color:#D14324; padding-left:0px;font-weight:bold;" href="#">请进入验证!</a>--></td></tr>
             <tr height="25">
                <td>
                  <a href="javascript:void(0);" id="replaySendEmail" style="font-size:14px; color:green; padding-left:5px; font-weight:bold;" href="#">重新发送</a>
                  <a style="font-size:14px; color:#363636; padding-left:10px;">验证邮寄24小时内有效</a>
                </td>
             </tr>
      	</tbody>
       </table>
        </#if>
    
    </form>
            
            
            
        </div>
    </div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->





<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript">
		$().ready( function() {
			$("#email_authentication_left").addClass("current");
			var $emailForm = $("#emailForm");
			 // 表单验证
			$emailForm.validate({
				rules: {
					"user.email":{
						required: true,
						email2:true,
						remote: "${base}/user/valEmail.do"
					},
					"user.payPassword":{
						required:true,
						remote:"${base}/userCenter/ajaxPayPassword.do"
					}
				},
				messages: {
					"user.email":{
						required: "邮箱不能为空",
						email2:"Email格式不对",
						remote: "Email已存在"
					},
					"user.payPassword":{
						required:"请输入安全密码",
						remote:"安全密码输入错误"
					}
				},
				errorPlacement: function(error, element) {
				  error.appendTo(element.parent());
				},
				submitHandler: function(form) {
					form.submit();
				}
			});
			
			var $replaySendEmail = $("#replaySendEmail");
			var $certificationMailStatus = $("#certificationMailStatus");
			
			$replaySendEmail.click(function(){
				$.ajax({
						url: "${base}/userCenter/ajaxSendEmail.do",
						type: "POST",
						dataType: "json",
						cache: false,
						beforeSend: function() {
							$certificationMailStatus.html('确认邮件发送中，请稍后...');
						},
						success: function(data) {
							$.message({type: data.status, content: data.message});
							if (data.status == "success") {
								alert(data.message);
								$certificationMailStatus.html('确认邮件发送成功，请及时到注册邮箱中确认...');
							} else {
								alert(data.message);
								$certificationMailStatus.html('确认邮件发送失败，<a href="javascript:void(0);" class="fr c2" id="replaySendEmail">重新发送</a>');
							}
						}
					});
			})
		});
$(function(){
	$("#member_email").addClass("nsg nsg_a1");
});
	</script>
</html>
