<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-密码重置</title>
    <#include "/content/common/meta.ftl">
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/reset_pwd.css" />
</head>
<body>
   <!-- 头部 -->
   <#include "/content/common/header.ftl">
    <!-- 主内容区 -->
    <div class="content">
        <div class="hint">
             <span>找回密码&nbsp;&nbsp;&nbsp;</span>
            若您无法找回密码，请联系客服400-057-7820
        </div>
        <div class="progress">
            <ul>
                <li class="current progress_bz">1</li>
                <li></li>
                <li class="current progress_bz">2</li>
                <li></li>
                <li class="current progress_bz">3</li>
                <li></li>
                <li class="progress_bz">4</li>
            </ul>
            <div class="progress_name">
                <span class="one">确认账号</span>
                <span class="two">安全验证</span>
                <span class="three">重置密码</span>
                <span class="four">完成</span>
            </div>
        </div>
         <!--重置密码-->
        <form action="${base}/user/findPsw3.do" class="reset_pwd" method="post" id="registerForm">
            <div class="input_tips">
                <img src="../img/tips.png" alt="">
                <span>手机号格式错误！</span>
            </div>
            <table>
                <tr>
                    <td>新密码：</td>
                    <td colspan="2"> <input type="password" name="password" id="password" placeholder="请输入密码，长度8~16位，必须包含字母" style='font-size:13px;'></td>
                </tr>
                <tr>
                    <td>确认密码：</td>
                    <td><input type="password" name='password2' placeholder=" 再次输入密码" id="password2" style='font-size:13px;'></td>
                </tr>
                <tr>
                    <td colspan="3"><input type="submit" value="完成"></td>
                </tr>
            </table>
        </form>
    </div>
 	<!-- 尾部 -->
	<#include "/content/common/footer.ftl">
    <script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
    <script src="${base}/js/common.js"></script>
    <script src="${base}/js/reset_pwd.js"></script>
    <script type="text/javascript" src="${base}/js/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/js/jquery/jquery.validate.methods.js"></script>
    <script type="text/javascript">
    $().ready(function() {
		var $registerForm = $("#registerForm");	
		// 表单验证
			$registerForm.validate({
				errorClass:"login_error",
				errorLabel:"login_error",
				rules: {
					"password2":{
						required: true,
						equalTo:"#password"
					},
					"password":{
						required: true,
						strongTxt:true,
						minlength:8,
						maxlength:16
					}
				},
				messages: {	
					"password2": {
						required: "请再次输入密码",
						equalTo: "两次密码输入不一致"
					},			
					"password": {
						required: "请填写密码",
						minlength: "密码必须大于等于8",
						maxlength: "密码必须小于等于16"
					}
				},
				errorPlacement: function(error, element) {
				  	alertMessage(error);
				},
				success:function(){
					$('.input_tips').css('visibility','hidden');
				},
				submitHandler: function(form) {
					form.submit();
				}
			});		
	});
		/*
		$('input[type=password]').blur(function(){
			var newpass=$(this).val();
			var ispassword=/.*[a-zA-Z]+.*$/;
			if(!ispassword.test(newpass)){
				alert("密码必须包含字母!");
			}
		});
		
		function checkpwdsame(){
			if($("#password").val()){
				if($("#password").val() != $("#password2").val()){
					alertMessage("密码不一致");
					return false;
				}
			}else{
				alertMessage("密码不能为空");
				 return false;
			}
			return true;
		}
		*/
		//我的账号
		$(function(){
			$('#header_wdzh').addClass('current');
			$('#header_gywm a').css('border',0);
		});
		function verifyCode(){
			$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		}
		function alertMessage(msg){
			$('.input_tips').find('span').html(msg);
	        $('.input_tips').css('visibility','visible');
	    }
	</script>
</body>
</html>