<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-登录</title>
   
  <#include "/content/common/meta.ftl">
  <link rel="stylesheet" href="${base}/css/common.css" />
   <link rel="stylesheet" href="${base}/css/account_login.css?v=1.0.0" />
</head>

<body>
 <script src="${base}/js/jquery-1.11.3.min.js"></script>
 <script src="${base}/js/common.js"></script>
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.methods.js"></script>
<!-- <script src="${base}/js/account_login.js"></script>-->


    <!-- 头部 -->
  
  <#include "/content/common/header.ftl">
  
    <!--内容区域-->
    <div class="content">
        <!--登录模块-->
        <div class="mlogin">
            <div class="mlogin_hint" >
                <span class="mlogin_hint_no_count">没有账号？</span>
                <span class="mlogin_hint_register"><a href="${base}/user/reg.do">立即注册</a></span>
            </div>
            <div class="input_tips" style='visibility:hidden;'>
                <img src="../img/tips.png" alt="">
                <span>手机号格式错误！</span>
            </div>
            

            <form  id="loginForm" name="loginForm" method="post" class="mlogin_form">
            	<input type="hidden" id="loginRedirectUrl" name="loginRedirectUrl" value ="${loginRedirectUrl}"/>
         
                <input type="text" placeholder="请输入手机号码" class="phone_num" name="user.username" id="username" autocomplete="off"/>
               
        
                <input type="text" placeholder="请输入验证码" name="mycode" class="identify_num" autocomplete="off"/>
                
                <a title='点击更换' id='vCodeA' onclick="verifyCodeLink();"><img id="code_img" src="${base}/rand.do" class="identify_img"></a>
               
                
                <input type="password" placeholder="请输入登录密码" class="login_pwd" name="user.password" id="password" autocomplete="off">
                <div class="forget"><a href="${base}/user/findPsw.do">忘记密码？</a></div>
                <input type="submit" value="登录" >
            </form>
        </div>
        <div class="clear"></div>
     </div>   

       <!--底部-->
        
    <#include "/content/common/footer.ftl">
</body>



<script type="text/javascript">
$("#header_wdzh").addClass("current");<#-- header.ftl 选中样式 -->
$('#header_gywm').find('a').css('border',0);
	$().ready(function() {
		
		var $loginForm = $("#loginForm");
		var $loginRedirectUrl = $("#loginRedirectUrl");
		var $password = $("#password");
		var $code_c = $(".code_c");
		var $code_img = $("#code_img");
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
					required: "用户名不能为空。",
					remote: "用户名不存在。"
				},
				"user.password":{
					required:"密码不能为空。"
				},
				"mycode":{
					required:"验证码不能为空。"
				}
			},
			errorClass:"erron",
				errorPlacement: function(error, element) {
				  element.parents(".mlogin").find('.input_tips').css("visibility","visible");
				  element.parents(".mlogin").find('.input_tips').find("span").html('');
				  error.appendTo(element.parents(".mlogin").find('.input_tips').find("span"));
				},
			success:function(element){
				element.parents(".mlogin").find('.input_tips').css("visibility","hidden");
				element.parents(".mlogin").find('.input_tips').find("span").html('');
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
								//$.message({type: data.status, content: data.message});
								alertMessage(data.message);
							}else{
								randimg();
								$password.val('');
								//$.message({type: data.status, content: data.message});
								alertMessage(data.message);
							}
						}
					});
				}
		});
		
		$code_c.click(function() {
			$code_img.attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		});
		
		function randimg(){
			$code_img.attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		}
		
	});
	
	function verifyCode(){
		$("#vCode").html("<a id='vCodeA' title='点击更换' onclick='verifyCodeLink();'><img id = 'code_img' src='' width='124' height='52' style='vertical-align:middle; position:relative; left:7px; top:-2px;' /></a>");
		$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		$("#vCode").attr("style", "position:absolute;right:13px;top:3px;");
		$("#mycode").select();
	}
	
	function verifyCodeLink(){
		$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
	}
	function alertMessage(message){
    $('.input_tips').find('span').html(message);
    $('.input_tips').css('visibility','visible');
    //return false;
}
</script>
</html>
