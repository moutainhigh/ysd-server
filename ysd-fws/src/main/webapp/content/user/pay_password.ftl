
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-账户管理-密码管理-<#if loginUser.payPassword!>修改<#else>设置</#if>安全密码</title>
	<#include "/content/common/meta.ftl">
 <!-- header -->
</head>
<body>
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_user.ftl">
<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="#">账户管理</a></li>
			<li><a href="#">密码管理</a></li>
			<li><a href="#"><#if loginUser.payPassword!>修改<#else>设置</#if>安全密码</a></li>
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
        	<h3><#if loginUser.payPassword!>修改<#else>设置</#if>安全密码</h3>
            <form id="passwordForm">
        		<input type="hidden" name="p" value="2">
				<input type="hidden" name="id" value="${loginUser.id}">
					<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
					<#if loginUser.payPassword!>
		              <tr>
		                <td class="text_r org grayBg" width="40">*</td>
		                <td class="text_r grayBg" width="119">我的旧密码：</td>
		                <td><input  type="password" class="input2 " name="user.payPassword" id="old_password" maxlength = "30" /> </td>
		              </tr> 
		             <#else>
		            	 <tr>
							<td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="119"></td>
						<td><font color = "red">请设置安全密码!<font></td>
						</tr>
		             </#if>
		              <tr>
		                <td class="text_r org grayBg" width="40">*</td>
		                <td class="text_r grayBg" width="86">请输入新密码：</td>
		                <td><input  type="password" class="input2 " id="new_payPassword" name = "newPayPassword"  maxlength = "30" /> </td>
		              </tr>
		             
		              <tr>
		                <td class="text_r org grayBg" width="40">*</td>
		                <td class="text_r grayBg" width="86">请再次输入新密码：</td>
		                <td><input  type="password" class="input2 " id="new_pay_password_again" name = "newPayPasswordAgain" maxlength = "30" /> </td>
	              	  </tr>
		              <tr>
		                <td class="text_r org grayBg">*</td>
		                <td class="text_r grayBg">验证码：</td>
		                <td><input class="input2 " name="mycode" id="mycode" type="text"  id="money"  maxlength="4"/>
		                	<a class="kaqu_yiyi">
		                	<img id = "code_img" src="${base}/rand.do" title="点击图片重新获取验证码" />
		                	</a></td>
		              </tr>                                                                             
		              <tr>
		                <td class="text_r grayBg">&nbsp;</td>
		                <td class="text_r grayBg">&nbsp;</td>
		            	<td height="40">
		                	<input class="btn1 white" type="button" id = "submitButton" value="提交">
		       	   			<input class="btn2 ml_15" type="reset" value="重置">
		                </td>
		              </tr>
		        	</table> 
            </form>       
        </div>                
    </div>
    
</div>

	<#include "/content/common/footer.ftl">
	<script>
		$().ready( function() { 
			$(".gjxx").attr("id","gjxx");
			$("#li_a_aqmm").addClass("li_a_select");
			
			var $passwordForm = $("#passwordForm");
			 // 表单验证
			 
			$passwordForm.validate({
				rules: {
					"user.password":{
						required: true,
						remote:"${base}/userCenter/ajaxPassword.do"
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
					"user.password":{
						required: "请输入旧密码",
						remote:"当前密码输入错误"
					},
					"newPayPassword":{
						required: "请输入新密码",
						minlength: "密码必须大于等于8",
						maxlength: "密码必须小于等于20"
					},
					"newPayPasswordAgain":{
						required: "请再次输入新密码",
						equalTo: "两次密码输入不一致"
					}
				},
				errorPlacement: function(error, element) {
				  error.appendTo(element.parent());
				},
				submitHandler: function(form) {
				}
			});
			
			$("#submitButton").click(function(){
					$.ajax({
						url: qmd.base+"/userCenter/ajaxPasswordUpdate.do",
						data: $passwordForm.serialize(),
						type: "POST",
						dataType: "json",
			            async : false, 
						success: function(data) {
							if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
				        		alert("修改失败！");
				        	} else if (data.status=="success") {
				        		alert(data.message);
				        		window.location.href = qmd.base + '/userCenter/toPassword.do';
				        	} else {
					        	alert(data.message);
				        	}
						},
			            error : function() {  
			                alert('参数错误，请联系管理员！');
			            } 
					});
			});
		});
	</script>
</body>
</html>