<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${Application ["qmd.setting.name"]}-国资控股|专业安全透明的互联网金融服务平台-登录</title>
<#include "/content/common/meta.ftl">
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.methods.js"></script>

<div class="login">
  <div style="width:1190px;height:495px;padding:40px 0 100px; margin:0px auto;background:url(/img/cele.png) no-repeat 120px 100px;">
    <div style="width:413px;border:1px solid #dcdcdc;border-radius:10px;float:right;">
      <div style="width:370px;height:38px;border-bottom:1px solid #dcdcdc;color:#666;padding:16px 20px 0 23px;">
          <span style=" font-size:18px;float:left;">用户登录</span>
          <span style="float:right;font-size:14px; margin-top:6px; "><a href="${base}/user/findPsw.do" style="color:#666; ">忘记密码？ </a> | <a href="${base}/user/reg.do" style="color:#666; "> 免费注册</a></span>
      </div>
      <div class="form" style="">
      	<form id="loginForm" name="loginForm" method="post">
      		<input type="hidden" id="loginRedirectUrl" name="loginRedirectUrl" value = "${loginRedirectUrl}"/>
          <ul class="ul">
            <li class="li01">用户名：</li>
            <li class="li02"><input type="text"  placeholder="请输入手机号" name="user.username" id="username"></li>
          </ul>
          <ul style="display:none;">
            <li class="li01"></li>
            <li class="li02" style="color:#fd7c1a;"></li>
          </ul>
          <ul class="ul">
            <li class="li01">密码：</li>
            <li class="li02"><input type="password"  placeholder="请输入密码" name="user.password" id="password"></li>
          </ul>
          <ul style="display:none;">
            <li class="li01"></li>
            <li class="li02" style="color:#fd7c1a;"></li>
          </ul>
          <ul class="ul">
            <li class="li01">验证码：</li>
            <!--<li class="li02"><input type="text" style="width:158px;" placeholder="请输入验证码" name="mycode"><a href="#" title='点击更换' onclick='verifyCodeLink();'><img src="${base}/rand.do" width='70' height='30'  style="vertical-align:middle; margin:0 10px 0 10px;" id="code_img"></a></li>-->
            <li class="li02" style="position:relative;"><input type="text" style="" placeholder="请输入验证码" name="mycode"><a title='点击更换' onclick="verifyCodeLink();" style="position:absolute;right:0;top:0;"><img id="code_img" src="${base}/rand.do" width="84" height="42" style="vertical-align:middle;"></a></li>
          </ul> 
          <ul style="display:none;">
            <li class="li01"></li>
            <li class="li02" style="color:#fd7c1a;"></li>
          </ul>
          <ul class="ul">
          <li  class="li01"></li>
          <li class="li02"><input type="submit" value="登录" style="vertical-align:middle;background:#fd7c1a;color:#fff;cursor:pointer;"></li>
        </ul> 
        </form>
      </div>
    </div>
  </div>
</div><!-- login end -->
<#include "/content/common/footer.ftl">
 </body>
<script type="text/javascript">
$("#header_wdzh").addClass("hq");<#-- header.ftl 选中样式 -->
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
				  element.parents(".ul").next("ul").css("display","block");
				  error.appendTo(element.parents(".ul").next("ul").find("li").eq(1));
				},
			
<#--	errorPlacement: function(error, element) {-->	
			  <#--error.appendTo(element.parent());-->
<#--				var $er =  $(errorSpan);
		  		$er.css("display","block");
		  		error.appendTo($er);
			},
-->				
			<#--errorLabelContainer:$("#errorSpan"),
			
			errorContainer: "errorSpan",
			wrapper: "span",
			-->
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
		$("#vCode").html("<a id='vCodeA' title='点击更换' onclick='verifyCodeLink();'><img id = 'code_img' src='' width='80' height='30' style='vertical-align:middle; position:relative; left:7px; top:-2px;' /></a>");
		$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		$("#vCode").attr("style", "position:absolute;right:13px;top:3px;");
		$("#mycode").select();
	}
	
	function verifyCodeLink(){
		$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
	}
</script>
</html>
