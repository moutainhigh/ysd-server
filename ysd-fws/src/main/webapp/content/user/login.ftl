<#escape x as x?html>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-登录</title>
	<#include "/content/common/meta.ftl">
<script type="text/javascript">
	$().ready(function() {
		
		var $loginForm = $("#loginForm");
		var $loginRedirectUrl = $("#loginRedirectUrl");
		var $password = $("#password");
		var $mycode=$("#mycode");
		$loginRedirectUrl.val(getParameter("loginRedirectUrl"));
		// 获取参数
		function getParameter(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
			var r = window.location.search.substr(1).match(reg);
			if (r != null) {
				return decodeURIComponent(r[2]);
			} else {
				return null;
			}
		}
		
		// 表单验证
		$loginForm.validate({
			errorClass:"login_error",
			errorLabel:"login_error",
			rules: {
				"user.username":{
					required: true,
					remote:{
						type:"POST",
					 	url:"${base}/user/checkUsername.do"
					}
				},
				"user.password":{
					required:true
				},
				"mycode":{
					required:true
				}
			},
			messages: {
				"user.username":{
					required: "用户名不能为空!",
					remote: "用户名或卡号不存在"
				},
				"user.password":{
					required:"密码不能为空!"
				},
				"mycode":{
					required:"验证码不能为空!"
				}
			},
			errorPlacement: function(error, element) {
			  error.appendTo(element.parent());
			},
			submitHandler: function(form) {
				$.ajax({
						url: "${base}/user/ajaxLogin.do",
						data: $loginForm.serialize(),
						type: "POST",
						dataType: "json",
						cache: false,
						beforeSend: function() {
							$loginForm.find("submit").attr("disabled", true);
						},
						success: function(data) {
							if(data.status=="success"){
								window.location.href = data.message;
							}else if(data.status=="warn"){
								randimg();
								$mycode.val('');
								alert(data.message);
								return false;
							}else{
								randimg();
								$password.val('');
								alert(data.message);
								return false;
							}
						}
					});
				}
		});
	});
</script>
</head>
<body>
<div class="login_bg">
    <div class="login_bg_t"><a href="#"><!--<img src="img/logo.png" width="142" height="45" alt="logo" />--></a></div>
    
    <div class="loginBox">    	
        <div class="leftText">
        </div>
        <div class="login_form">
            <form name="loginForm" id="loginForm" class="registerform"  method="post" <#--> onkeydown="if(event.keyCode==13){return false;}"--> >
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="34" class="text_shadow">用户名:</td>
                    <td><input class="inputBg1"  name="user.username" id="username"  type="text" />
                    	<br/><div class="Validform_checktip" style="display:none;"></div>
                    </td>
                  </tr>
                  <tr>
                    <td height="58" class="text_shadow">密码:</td>
                    <td><input class="inputBg2" name="user.password" id="password" type="password" />
                    	<br/><div class="Validform_checktip" style="display:none;"></div>
                    </td>
                  </tr>
                  <tr>
                    <td height="34" class="text_shadow">验证码:</td>
                    <td>
                    	<div class="p"><input class="inputBg3" name="mycode" id = "mycode" type="text" />
                    		<a class="kaqu_yiyi">
								<img id = "code_img" src="${base}/rand.do" width="51" height="20" />
							</a>
                    	</div>
                    	<br/><div class="Validform_checktip" style="display:none;"></div>
                    </td>
                  </tr>
                  <tr>
                  	 <td height="58" class="text_shadow"></td>
                    <td height="88">
						<span class="submit">
							<input type="submit" class="loginBtn" value=" " />　
						</span>
                    </td>
                  </tr>
                </table>
            </form>
        </div>                
    </div>
  <div class="login_bg_b"></div>
</div>
</body>

</html>
</#escape>