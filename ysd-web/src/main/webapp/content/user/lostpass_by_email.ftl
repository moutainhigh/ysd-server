<#escape x as x?html>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-通过邮箱找回<#if p==1>安全<#else>登录</#if>密码</title>
<#include "/content/common/meta.ftl">
</head>
<body>
<#include "/content/common/header.ftl">
<!-- content -->

<div class="content">
  <div style="width:955px; margin:0 auto; padding-bottom:20px;">
    
    
    <div style="width:955px; float:left; background:#fff; clear:both;">
       
        
        <div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
			<h2>&nbsp;找回<#if p==1>安全<#else>登录</#if>密码&nbsp;</h2>
          </div>
          <br/><br/><br/>
          	<form id="lostpassForm" method="post">
			<input type = "hidden" name = "p" value="${p}"/>
          <table width="100%" cellpadding="0" cellspacing="0" class="checked_v4">
		     <tbody>
		       <tr>
		         <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">用户名：</td>
		         <td style="color:#646464;font-family:'宋体';">
		           <input id="username" name="user.username" type="text" style="width:250px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
		         </td>
		       </tr>
		       <tr height="18"></tr>
		       <tr>
		         <td style="width:110px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">邮箱：</td>
		         <td style="color:#646464;font-family:'宋体';"><input id="email" name="user.email" type="text" style="width:250px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/></td>
		       </tr>
		       
		       <tr height="18"></tr>
		       <tr>
		         <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';"></td>
		         <td style="color:#646464;font-family:'宋体';">
		          <input type="submit" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" value="确定"/>
		          
		         </td>
		       </tr>
		     </tbody>
		    </table>
          </form>
           <div style="height:23px;line-height:23px;text-align:center;color:#595757;font-family:'宋体';margin-top:100px; margin-bottom:30px; clear:both;"></div>  
            
            
            
            
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
	var $lostpassForm = $("#lostpassForm");

	 // 表单验证
	$lostpassForm.validate({
		rules: {
			"user.username":{
				required: true,
				remote:{
					type:"POST",
				 	url:"${base}/user/checkUsername.do"
				}
			},
			"user.email":{
				required: true,
				email:true,
				remote: "${base}/user/ajaxCheckEmailByFindPwd.do"
			}
		},
		messages: {
			"user.username":{
				required: "用户名不能为空!",
				remote: "用户名不存在"
			},
			"user.email": {
				required: "请填写邮箱",
				email: "邮箱格式有问题",
				remote: "邮箱不存在!"
			}
		},
		errorPlacement: function(error, element) {
		   error.appendTo(element.parent());
		},
		submitHandler: function(form) {
			$.ajax({
				url: "${base}/user/lostpass/findPwdByEmail.do",
				data: $lostpassForm.serialize(),
				type: "POST",
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$lostpassForm.find("submit").attr("disabled", true);
				},
				success: function(data) {
					$.message({type: data.status, content: data.message});
					if(data.status=="error"){
						alert(data.message);
					}else if(data.status=="success"){
						setTimeout(function(){
						$("#lostpass_div").html('邮件已经发送，请登录邮箱查看。<a href="'+qmd.base+'">返回首页</a>');
						},2000);
						
					}
				}
			});
		}
	});
	
$(function(){
	$("#member_email").addClass("nsg nsg_a1");
});
</script>
</html>
</#escape>