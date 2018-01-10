<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-个人账户注册</title>
<#include "/content/common/meta.ftl">

<style type="text/css">
.regh li{background:url('/static/images/regicons.png'); list-style:none; float:left; height:66px;}
li.s1{ width:130px; background-position:0px -611px;}
li.s2{ width:50px; background-position:-137px -66px;}
li.s2 .t1{ float:left; margin-top:37px; font-size:18px; margin-left:19px; color:#FFF}
li.s3{ width:185px; background-position:0px -718px;}
li.s4{ width:50px; background-position:-276px -165px;}
li.s4 .t2{ float:left; margin-top:37px; font-size:18px; margin-left:17px; color:#FFF}
li.s5{ width:185px; background-position:0px -718px;}
li.s6{ width:50px; background-position:-368px -264px;}
li.s7{ width:135px; background-position:0px -718px;}
.reght .tt1{ float:left; margin-left:127px; font-size:14px;}
.reght .tt2{ float:left; margin-left:160px; font-size:14px;}
.reght .tt3{ float:left; margin-left:168px; font-size:14px;}
.regtop{ background:url('/static/images/regtop1.png') no-repeat; width:785px; height:479px; margin:0 auto;box-shadow: 0px 1px 3px #333333;}
.regtop .geren{ width:362px; height:79px; float:left}
.regtop .jigou{ width:362px; height:79px; float:left}
.phoneCode {background:url(/static/img/btns.png) no-repeat -585px 0; width:98px; height:35px; line-height:35px; text-align:center; display:inline-block; font-size:16px;color:#000;position:relative; left:1px; border:0 none;}

</style>

<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript">
	$().ready(function() {
		var $code_c = $(".kaqu_huanyizhang");
		var $code_c2 = $(".kaqu_yiyi");
		var $code_img = $("#code_img");
		var $mycode=$("#mycode");
		var $registerForm = $("#registerForm");	
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
					"reUserPassword":{
						required: true,
						equalTo:"#pwd_investor"
					},
<#--					"user.email": {
						required: true,
						email:true,
						remote: "${base}/user/checkEmail.do"
					},-->
					"mycode":{
						required:true
					},
					"isAgreeAgreement":{
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
					"reUserPassword": {
						required: "请再次输入密码",
						equalTo: "两次密码输入不一致"
					},
<#--					"user.email": {
						required: "请填写Email",
						email: "Email格式不正确",
						remote: "Email已存在"
					},-->
					"mycode":{
						required:"验证码不能为空!"
					},
					"isAgreeAgreement":{
						required:"请阅读会员协议"
					}
				},
				errorPlacement: function(error, element) {
				  error.appendTo(element.parent("li").next("li"));
				},
				submitHandler: function(form) {
					
					if(!$("#agreeCheckbox")[0].checked){
						alert('请阅读并同意《${Application ["qmd.setting.name"]}服务协议》');
						return;
					}
					
					form.submit();
				}
			});		
		$code_c.click(function() {
			$code_img.attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		});
		$code_c2.click(function() {
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
</script>
</head>
<body>

	<#include "/content/common/header.ftl">
<div class="content">
  <div style="width:100%; height:48px; background:#eae9e9; border-bottom:1px solid #c6c6c6;">
    <div style=" width:985px;height:48px; margin:0 auto; background:url(${base}/static/img/d6.png) 0 0 no-repeat;">
    </div>
  </div>
 <form id="registerForm" method="post" action="register.do" class="registerform">
  <input type="hidden" name="user.typeId" id="typeId0" value="0"  />
  <div style="width:985px; margin:0 auto; margin-top:15px; padding-bottom:15px; clear:both;">
      <div style=" padding:20px 0px 20px 20px; background:url(/static/img/d5.png) 75% center no-repeat #fff; width:965px; height:437px;">
        <div style=" padding:10px 40px 35px 40px; width:295px; background:#fff;">
           <div style="float:left; padding-bottom:15px; width:285px;">
              <span style="color:#4b4b4b;font-family:'宋体';float:right; margin-top:8px; ">已有帐号<a href="${base}/user/login.do" style="color:#4b4b4b;font-family:'宋体'; font-weight:bold; ">[立即登录]</a></span>
           </div>
           <div style="clear:both;width:295px;">
               <ul>
                 <li style="color:#4b4b4b;font-family:'宋体'; margin-bottom:5px; ">手机号码</li>
                 <li><input type="text" type="text" name="user.phone" id="phone" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/></li>
                 <li style="height:20px;"></li>
               </ul>
<#--               <ul>
                 <li style="color:#4b4b4b;font-family:'宋体'; margin-bottom:5px; ">常用邮箱</li>
                 <li><input type="text" name="user.email" id="email"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/></li>
                 <li style="height:20px;"></li>
               </ul>
-->
               <ul>
                 <li style="color:#4b4b4b;font-family:'宋体'; margin-bottom:5px; ">登录密码</li>
                 <li><input type="password" name="user.password" id="pwd_investor" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/></li>
                 <li style="height:20px;"></li>
               </ul>
               <ul>
                 <li style="color:#4b4b4b;font-family:'宋体'; margin-bottom:5px; ">确认密码</li>
                 <li><input type="password" name="reUserPassword" id="reUserPassword" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/></li>
                 <li style="height:20px;"></li>
               </ul>
               <ul>
                 <li style="color:#4b4b4b;font-family:'宋体'; margin-bottom:5px; ">验证码</li>
                 <li style="position:relative;">
                 	<input type="text" id="yzm_text" name="mycode" onfocus="verifyCode();" style="width:284px; height:32px; line-height:32px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
                 	<a class="kaqu_yiyi" style="position:absolute;right:6px;top:1px;"><img id = "code_img" src="${base}/rand.do" width="63" height="32" /></a>
                 </li>
                 
                 <li style="height:30px;"></li>
               </ul>
               
               <ul>
                 <li><input type="checkbox" id="agreeCheckbox" value="false" checked /><span style="font-family:'宋体'; margin-left:4px;">我已阅读并同意<a href="${base}/regTreaty.do" target="_blank" style="color:#3c7bb3;">《${Application ["qmd.setting.name"]}服务协议》</a></span></li>
                 <li style="height:15px;"></li>
               </ul>
               <ul>
                 <li style="background:url(/static/img/d2.png) 0 0 no-repeat; width:286px; height:39px; line-height:39px; text-align:center;">
                   <a onclick="$('#registerForm').submit()" ><span style="color:#fff;font-size:16px;font-weight:bold; display:block;">立即注册</span></a>
                 </li>
               </ul>
           </div>
        </div>
      </div>
     <div style="clear:both;"></div>
  </div>
</form>  
</div><!-- content end -->

<#include "/content/common/footer.ftl">

	<#--
	<#include "/content/common/header.ftl">

<form id="registerForm" method="post" action="register.do" class="registerform">
	<input type="hidden" name="user.typeId" id="typeId0" value="0" />
	<input type="hidden" name="user.memberType" id="memberType0" value="0" />
<div class="content clearfix">
 <div style="width:960px; margin:0 auto;">
    <h3 style=" background:url(/static/img/reg.png) 0 0 no-repeat; width:953px; height:46px;"></h3>
    <div style=" width:951px; height:418px; border:1px solid #d4c8aa; background:url(/static/img/r3.png) 0 0 no-repeat;">
      <div style="float:left; width:400px; height:400px; background:#fff; padding:10px 0px 0px 25px; margin-left:10px; margin-top:0px; margin-bottom:0px;">
          <table width="100%" cellpadding="0" cellspacing="0">
            <tbody>
               <tr>
                  <td align="right" style="color:#775623; font-weight:bold;">用&nbsp;户&nbsp;名：</td>
                  <td>
                      <input
                       name="user.username" id="username" 
                       type="text" style=" background:url(/static/img/input.png) 0 0 no-repeat; width:211px; color:#6d6c71; height:34px;line-height:34px; padding-left:8px; border:0 none; vertical-align:top;"  
                    />
                  </td>
               </tr>
               <tr height="6"></tr>
               <tr>
                  <td align="right" style="color:#775623; font-weight:bold;">常用邮箱11：</td>
                  <td>
                      <input 
                      name="user.email" id="email" 
                      type="text" style=" background:url(/static/img/input.png) 0 0 no-repeat; width:211px; color:#6d6c71; height:34px;line-height:34px; padding-left:8px; border:0 none; vertical-align:top;"  
                      />
                  </td>
               </tr>
               <tr height="6"></tr>
         	<tr>
              <td align="right" style="color:#775623; font-weight:bold;">手机号码：</td>
              <td>
               <input
               	type="text" name="user.phone" id="phone"
                style=" background:url(/static/img/input.png) 0 0 no-repeat; width:211px; color:#6d6c71; height:34px;line-height:34px; padding-left:8px; border:0 none; vertical-align:top;"/>
                <span style=" display:inline-table"></span>
              </td> 
           </tr>
               <tr height="6"></tr>
               
               <tr>
              <td align="right" style="color:#775623; font-weight:bold;">手机验证码：</td>
              <td>
               <input type="text" name="codeReg" id="mycodePhone" style=" background:url(/static/img/input1.png) 0 0 no-repeat; width:112px; height:34px; line-height:34px; padding-left:8px; border:0 none; vertical-align:top; margin-right:25px;" />
              	<span id="phoneCode" style=" visibility:hidden;"></span>
              	<span id="phoneCode2"> 
              		<input id="yzm_button" type="button" onclick="sendAuthCode(this)" class="phoneCode" value="获取验证码">
              	</span>
              </td> 
           </tr>
           <tr height="6"></tr>
               <tr>
                  <td align="right" style="color:#775623; font-weight:bold;">登录密码：</td>
                  <td>
                      <input 
						type="password" name="user.password" id="pwd_investor" 
						 style="background:url(/static/img/input.png) 0 0 no-repeat;width:211px;color:#6d6c71;height:34px;line-height:34px;padding-left:8px;border:0 none;vertical-align:top;"  
                    />
                  </td> 
               </tr>
               <tr height="6"></tr>
               <tr>
                  <td align="right" style="color:#775623; font-weight:bold;">确认密码：</td>
                  <td><input 
					type="password" name="reUserPassword" id="reUserPassword"
					 style=" background:url(/static/img/input.png) 0 0 no-repeat; width:211px; color:#6d6c71; height:34px;line-height:34px; padding-left:8px; border:0 none; vertical-align:top;" /></td>
               </tr>
               <tr height="6"></tr>
               <tr>
                  <td align="right" style="color:#775623; font-weight:bold;">验证码：</td>
                  <td>
                    <input type="text" id="yzm_text" name="mycode" style=" background:url(/static/img/input1.png) 0 0 no-repeat; width:112px; height:34px; line-height:34px; padding-left:8px; border:0 none; vertical-align:top; margin-right:25px;" />
                    <a class="kaqu_yiyi"><img id = "code_img" src="${base}/rand.do" width="63" height="32" /></a>
                  </td>
               </tr>
               <tr height="6"></tr>
               <tr>
                  <td></td>
                  <td style="color:#000;">
                    <input type="checkbox" id="agreeCheckbox" value="false" checked  style=" vertical-align:text-top"/>&nbsp;我已阅读并同意《温金所所服务协议》
                  </td>
               </tr>
               <tr height="6"></tr>
               <tr>
                  <td></td>
                  <td>
                   <input type="submit" value="立即注册"  style=" background:url(/static/img/r4.png) 0 0; width:211px; height:35px; color:#fff; font-size:14px; font-weight:bold; border:0 none;"/>
                  </td>
               </tr>
               <tr height="6"></tr>
               <tr>
                  <td></td>
                  <td>
                    <span style=" font-family:'宋体'; color:#c3ab71; padding-left:50px;">已有账号<a href="${base}/user/login.do" style=" font-family:'宋体'; color:#c3ab71; font-weight:700; text-decoration:none;">[立即登录]</a></span>
                  </td>
               </tr>
            </tbody>
          </table>
      </div>
      <div style="float:right; width:515px; padding-top:50px; text-align:center;"><img src="/static/img/r5.png" width="316" height="367"/></div>
    </div>
 </div>
</div>


</form>
	<#include "/content/common/footer.ftl">
-->
</body>
</html>
