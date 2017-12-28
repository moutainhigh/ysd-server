<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${Application ["qmd.setting.name"]}-重置<#if p==1>安全<#else>登录</#if>密码</title>
<#include "/content/common/meta.ftl">
</head>
<body>
<#include "/content/common/header.ftl">



<div class="content">
  <div style="width:955px; margin:0 auto; padding-bottom:20px;">
    
    
    <div style="width:955px; float:left; background:#fff; clear:both;">
       
        
        <div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
			<h2>&nbsp;重新设置<#if p==1>安全<#else>登录</#if>密码&nbsp;</h2>
          </div>
          <br/><br/><br/>
		 <form id="setPasswordForm" method="post">
		  	 <input type="hidden" name="id" value="${user.id}">
             <input type="hidden" name="p" value="${p}">
			<table width="100%" cellpadding="0" cellspacing="0" class="checked_v4">
				<tr>
					<td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">用户名：</td>
					<td style="color:#646464;font-family:'宋体';">
						${user.username}
					</td>
				</tr>
				<tr>
					<td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">邮箱：</td>
					td style="color:#646464;font-family:'宋体';"> 
						${user.email}
					</td>
				</tr>
				<#if p==0>  
					<tr>
						<td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">登录密码：</td>
						<td style="color:#646464;font-family:'宋体';"> <input type="password" id="password" name="user.password" style="width:250px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
	                		<label id="label_email_id"  for="user.password"></label>
						</td>
					</tr>
				<#else>
					<tr>
						<td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">安全密码：</td>
						<td style="color:#646464;font-family:'宋体';"> <input type="password" id="password" name="user.payPassword" style="width:250px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
	                		<label id="label_email_id"  for="user.payPassword"></label>
						</td>
					</tr>
				</#if>
				<tr>
					<td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">再次输入密码：</td>
					<td style="color:#646464;font-family:'宋体';"> <input type="password" id="reUserPassword" name="reUserPassword" style="width:250px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
                		<label id="label_email_id"  for="reUserPassword"></label>
					</td>
				</tr>
				<tr>
					<td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';"></td>
					<td style="color:#646464;font-family:'宋体';">
						<span class="btn_l s3"><input type="submit" value="保存" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;"/></span>　
					</td>
				</tr>			
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
	$().ready(function() {
	
		var $setPasswordForm = $("#setPasswordForm");
		
		//$(".password_adv").passStrength({
		//	messageloc: 0 
		//}); 
		
		 // 表单验证
		$setPasswordForm.validate({
			rules: {
			<#if p ==0>
				"user.password":{
					required: true,
					strongTxt:true,
					minlength:8,
					maxlength:16
				},
			<#elseif p==1>
				"user.payPassword":{
					required: true,
					strongTxt:true,
					minlength:8,
					maxlength:16
				},
			</#if> 	
				"reUserPassword":{
					required: true,
					equalTo:"#password"
				}
			},
			messages: {
			<#if p ==0>
				"user.password": {
					required: "请填写密码",
					minlength: "密码必须大于等于8",
					maxlength: 	 "密码必须小于等于16"
				},
			<#elseif p==1>
				"user.payPassword": {
					required: "请填写密码",
					minlength: "密码必须大于等于8",
					maxlength: 	 "密码必须小于等于16"
				},
			</#if> 	
				"reUserPassword": {
					required: "请填写确认密码",
					equalTo: "两次密码输入不一致"
				}
			},
			errorPlacement: function(error, element) {
			  error.appendTo(element.parent());
			},
			submitHandler: function(form) {
				$.ajax({
					url: "${base}/user/ajaxsetPwd.do",
					data: $setPasswordForm.serialize(),
					type: "POST",
					dataType: "json",
					cache: false,
					beforeSend: function() {
						$setPasswordForm.find("submit").attr("disabled", true);
					},
					success: function(data) {
						$.message({type: data.status, content: data.message});
						if(data.status=="success"){
							setTimeout(function(){
							$("#set_password_div").html('<a href="${base}">返回首页</a>');
							},2000);
						}
					}
				});
			}
		});
	})
	</script>
</html>
