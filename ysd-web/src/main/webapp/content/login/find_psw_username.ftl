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
                <li class="progress_bz">2</li>
                <li></li>
                <li class="progress_bz">3</li>
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
        <!--填写账户名-->
        <form action="${base}/user/findPsw1.do" method="post" class="account_name" id="formId">
            <div class="input_tips">
                <img src="${base}/img/tips.png" alt="">
                <span>手机号格式错误！</span>
            </div>
            <table>
                <tr>
                    <td>手机号：</td>
                    <td colspan="2"> <input type="text" placeholder="请输入手机号" class="phone_num" title="phone_num" name="user.phone"></td>
                </tr>
                <tr>
                    <td>验证码：</td>
                    <td><input style="width: 162px;" type="text" class="identify_num" title="identify_num" name="mycode" placeholder="请输入验证码" ></td>
                    <td ><img width='90' height='42' style="height: 38px;" src="${base}/rand.do" id="code_img" class="identify_img"  onclick="verifyCode();"></td>
                </tr>
                <tr>
                    <td colspan="3"><input type="submit" value="下一步"></td>
                </tr>
            </table>
        </form>
    </div>
 	<!-- 尾部 -->
	<#include "/content/common/footer.ftl">
    <script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
    <script src="${base}/js/common.js"></script>
    <script src="${base}/js/reset_pwd.js"></script>
    <script type="text/javascript">
		//我的账号
		$(function(){
			$('#header_wdzh').addClass('current');
			$('#header_gywm a').css('border',0);
		});
		function verifyCode(){
			$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		}
	</script>
	<script type="text/javascript" src="${base}/js/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/js/jquery/jquery.validate.methods.js"></script>
	<script type="text/javascript">
		$().ready(function() {
			var $code_img = $("#code_img");
			var $mycode=$("#mycode");
			var $registerForm = $("#formId");	
			// 表单验证
				$registerForm.validate({
					errorClass:"login_error",
					errorLabel:"login_error",
					rules: {
						"user.phone":{
							required:true,
							phone:true
						},
						"user.password":{
							required: true,
							strongTxt:true,
							minlength:8,
							maxlength:16
						},
						"password2":{
							required: true,
							equalTo:"#password1"
						},
						"mycode":{
							required:true
						},
						"agreeCheckbox":{
							required:true
						}
					},
					messages: {
						"user.phone":{
							required:"手机号不能为空"
						},
						"user.password": {
							required: "请填写密码",
							minlength: "密码必须大于等于8",
							maxlength: "密码必须小于等于16"
						},
						"password2": {
							required: "请再次输入密码",
							equalTo: "两次密码输入不一致"
						},
						"mycode":{
							required:"验证码不能为空!"
						},
						"agreeCheckbox":{
							required:"请阅读会员协议"
						}
					},
					errorPlacement: function(error, element) {
					  element.parents(".account_name").find('.input_tips').css("visibility","visible");
					  element.parents(".account_name").find('.input_tips').find("span").html('');
					  error.appendTo(element.parents(".account_name").find('.input_tips').find("span"));
					},
					success:function(element){
						element.parents(".account_name").find('.input_tips').css("visibility","hidden");
					},
					submitHandler: function(form) {
						
						if(!$("#agreeCheckbox")[0].checked){
							alertMessage('请阅读并同意《${Application ["qmd.setting.name"]}服务协议》');
							return;
						}
						
						form.submit();
					}
				});		
	
		});
		function verifyCode(){
			$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		}
</script>
</body>
</html>