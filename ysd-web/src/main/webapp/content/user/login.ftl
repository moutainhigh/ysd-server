<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-登录</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript">
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
				  error.appendTo(element.parent());
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
								$.message({type: data.status, content: data.message});
								alert(data.message);
							}else{
								randimg();
								$password.val('');
								$.message({type: data.status, content: data.message});
								alert(data.message);
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
</head>
<body>
<#include "/content/common/header.ftl">

<div class="content">
  <div style="width:985px; margin:0 auto; margin-top:35px; padding-bottom:35px; clear:both;">
       <div style=" padding:25px 0px 25px 25px; background:url(/static/img/d3.png) 95% center #fff no-repeat; width:960px;">
        <div style="border:1px solid #c6c6c6; padding:25px 30px 95px 50px; width:295px;">
           <div style="float:left; padding-bottom:30px; width:295px;">
              <span style="color:#be9964; font-size:19px;float:left;">登  录</span>
              <span style="color:#4b4b4b;font-family:'宋体';float:right; margin-top:8px; ">还不是会员？<a href="${base}/user/reg.do" style="color:#4b4b4b;font-family:'宋体'; font-weight:bold; ">免费注册</a></span>
           </div>
            <form name="loginForm" id="loginForm" class="registerform"  method="post">
     		<input type="hidden" id="loginRedirectUrl" name="loginRedirectUrl" value = "${loginRedirectUrl}"/>
           <div style="clear:both;width:295px;">
               <ul>
                 <li style="color:#4b4b4b;font-family:'宋体'; margin-bottom:5px; ">用户名</li>
                 <li><input type="text" name="user.username" id="username" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/></li>
                  <li style="height:20px;"></li>
               </ul>
               <ul>
                 <li style="color:#4b4b4b;font-family:'宋体'; margin-bottom:5px;position:relative;">
                   <span>登录密码</span>
                   <a href="${base}/user/lostpass/usephone.do?p=0" style="color:#4b4b4b;font-family:'宋体'; position:absolute; right:5px;top:0px; ">忘记密码?</a>
                 </li>
                 <li><input type="password" name="user.password" id="password" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/></li>
                 <li style="height:20px;"></li>
               </ul>
               <ul>
                 <li style="color:#4b4b4b;font-family:'宋体'; margin-bottom:5px; position:relative; ">
                   <span>验证码</span>
                   
  <!--                 <a style="color:#4b4b4b;font-family:'宋体'; position:absolute; right:5px;top:0px;" class = "kaqu_huanyizhang">换一张</a>-->
                 </li>
                 <li style="position:relative;">
                 	<input type="text" name="mycode" onfocus="verifyCode();" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
                 	<span id="vCode" style=" visibility:hidden;"></span>
                 </li>
                 <li style="height:25px;"></li>
               </ul>
               <ul>
                 <li>
           			<input type="submit" style="background:url(/static/img/d2.png) 0 0 no-repeat; width:286px; height:39px; line-height:39px; text-align:center;color:#fff;font-size:16px;" value="立即登录"/>
                 </li>
               </ul>
           </div>
           </form>	
        </div>
      </div>
     <div style="clear:both;"></div>
  </div>
  
</div><!-- content end -->

	<#include "/content/common/footer.ftl">
	<script src="/static/js/script.js"></script>


<#-- 

<#include "/content/common/header.ftl">

<!-- content ->
<div class="content clearfix">
 <div style="width:960px; margin:0 auto;">
    <h3 style=" background:url(/static/img/h3_bg.png) 0 0 repeat; width:960px; height:41px; line-height:41px; float:left;">
      <span style=" font-size:18px; font-family:'微软雅黑'; color:#fff; font-weight:bold; float:left; margin-left:25px;">登&nbsp;&nbsp;录</span>
      <span style=" color:#fff; font-size:14px;float:right; margin-right:25px;">还没账号<a href="${base}/user/reg.do" style=" color:#fff; font-weight:700; ">[立即注册]</a></span>
    </h3>
    <div style="clear:both;"></div>
    <div style=" width:958px; border:1px solid #d4c8aa;background:url(/static/img/f10.png) 0 0 no-repeat; padding-bottom:69px;">
							<form name="loginForm" id="loginForm" class="registerform"  method="post">
					         	<input type="hidden" id="loginRedirectUrl" name="loginRedirectUrl" value = "${loginRedirectUrl}"/>
      <table width="70%" cellpadding="0" cellspacing="0" style=" margin-left:25px; margin-top:85px;">
        <tbody>
           <tr>
              <td align="right" style="color:#775623; font-weight:bold;">用&nbsp;户&nbsp;名：</td>
              <td><input type="text" name="user.username" id="username" type="text" style=" background:url(/static/img/input_0.png) 0 0 no-repeat; width:194px; height:34px; line-height:34px; padding-left:25px; border:0 none; vertical-align:top;" /></td>
           </tr>
           <tr height="10"></tr>
           <tr>
              <td align="right" style="color:#775623; font-weight:bold;">登录密码：</td>
              <td>
               <input type="password" name="user.password" id="password" style=" background: url(/static/img/input_1.png) 0 0 no-repeat; width:192px; height:34px; line-height:34px; padding-left:27px; border:0 none; vertical-align:top;" />
              <a href="${base}/user/lostpass.do?p=0" style=" vertical-align:middle; display:inline-block; padding-top:5px; padding-left:10px;">忘记密码</a>
              </td> 
           </tr>
           <tr height="10"></tr>
           <tr>
              <td align="right" style="color:#775623; font-weight:bold;">验证码：</td>
              <td>
                <input type="text" name="mycode" style=" background:url(/static/img/input1.png) 0 0 no-repeat; width:116px; height:34px; line-height:34px; padding-left:4px; border:0 none; vertical-align:top;" />
                <a class="kaqu_yiyi"><img id = "code_img" src="${base}/rand.do"  /></a>
                <a style="display:inline-block; vertical-align:middle; position:relative;top:-7px;left:-7px;" class = "kaqu_huanyizhang">换一张</a>
              </td>
           </tr>
           <tr height="10"></tr>
           <tr>
              <td></td>
              <td>
               <input type="submit" value="立即登录"  style=" background:url(/static/img/input3.png) 0 0; width:121px; height:35px; color:#fff; font-size:14px; font-weight:bold; border:0 none;"/>
              </td>
           </tr>
           <!--
           <tr height="30"></tr>
           <tr>
              <td align="right">快捷登录：</td>
              <td>
                <a href=""><span style="display:inline-block; background:url(/static/img/22.png) 0 0 no-repeat; width:137px; height:31px;"></span></a>
                <a href=""><span style="display:inline-block; background:url(/static/img/22.png) -139px 0 no-repeat; width:127px; height:31px;"></span></a>
                <a href=""><span style="display:inline-block; background:url(/static/img/22.png) -267px 0 no-repeat; width:127px; height:31px;"></span></a>
              </td>
           </tr>
           ->
        </tbody>
      </table>
							</form>	
    </div>
 </div>
</div>
<!-- footer ->
			
		
		
		
	<#include "/content/common/footer.ftl">
	<script src="/static/js/script.js"></script>
	<div style="display:none">建议使用cnzz</div>       
-->
</body>

</html>
