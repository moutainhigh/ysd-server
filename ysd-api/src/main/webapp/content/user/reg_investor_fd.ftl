<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${Application ["qmd.setting.name"]}-个人账户注册</title>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="application-name" content="${Application ["qmd.setting.name"]}" />
    <meta name="msapplication-tooltip" content="${Application ["qmd.setting.name"]}" />
	<meta name="keywords" content="${Application ["qmd.setting.metaKeywords"]}" />
	<meta name="description" content="${Application ["qmd.setting.metaDescription"]}" />
	<meta name="robots" content="all" />
	<link rel="shortcut icon" href="${base}/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="${base}/static/css/base2.css" type="text/css" />
	<link rel="stylesheet" href="${base}/static/css/fy.css" type="text/css" />
	
	<script src="${base}/static/js/jquery.min.js"></script>
	<script src="${base}/static/js/base.js"></script>
	<script src="${base}/static/base/js/base.js"></script>
	<script src="${base}/static/js/common/qmd.js"></script>	

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
						alert('请阅读并同意《温金所服务协议》');
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
</script>
</head>
<body>

	<#include "/content/common/header.ftl">
	<div class="main">
	<div class="banner png">
    	<div class="login">
      <form id="registerForm" method="post" action="/user/registerFd.do">
  		<input type="hidden" name="user.typeId" id="typeId0" value="0"  />
  		<input type="hidden" name="user.regMoney" id="" value="0" />
  		<input type="hidden" name="user.memberType" id="" value="0" />
  		<input type="hidden" name="r" id="Id0" value="${r}" /><#--推广编号-->
            	<div class="message">
            		
                    <div class="M_first">
                        已有账号<a href="${base}/user/login.do" target="_Blank">[立即登录]</a>
                    </div>
                    <div class="M_two">
            		 <p>邀请人</p>
            		${friendUsername}
            		</div >
                    <div class="M_name">
                        <p>手机号码</p>
                        <input type="text" type="text" name="user.phone" id="phone"/>  
                    </div>	
                    <div class="M_password">
                        <p>登录密码</p>
                        <input type="password" name="user.password" id="pwd_investor" >
                    </div> 
                    <div class="M_again">
                        <p>确认密码</p>
                        <input type="password" name="reUserPassword" id="reUserPassword"  >
                    </div> 
                     <div class="M_email">
                        <p>验证码</p>
                        <input type="text" id="yzm_text" name="mycode" /> 
                        <a class="kaqu_yiyi"><img id = "code_img" src="${base}/rand.do" width="63" height="32" /></a> 
                    </div>	
                    <div class="M_agree">
                        <input type="checkbox" id="agreeCheckbox" value="false" checked /><span>我已阅读并同意<a href="#">《${Application ["qmd.setting.name"]}服务协议》</a></span>
                    </div>
  <!--                       <input type="submit" value="立即注册" class="M_btn" />-->
                 <div class="zhuce"><a onclick="$('#registerForm').submit()" >立即注册</a></div>
               </div>        
			</form>
        </div>
        <div class="love">
        	<h2>我就爱车</h2>
            <p>安全 / 稳健 / 收益高 </p>
        </div>
    </div>  
    <div class="haoli1">
    	<div class="top">
        	<div>
            	<h3 class="png">我们的优势</h3>
            </div>
        </div>
        <div class="bottom">
              <table border="0" cellspacing="0" cellpadding="0">
                  <tr class="tr01">
                      <td>我们的优势</td>
                      <td>我就爱车</td>
                      <td>陆金所</td>
                      <td>余额宝</td>
                      <td>银行活期</td>
                      <td>银行1年定期</td>
                      <td>理财产品</td>
                  </tr>
                  <tr class="tr04"></tr>
                  <tr class="tr02">
                      <td>安全性</td>
                      <td>100%担保公司本息保障</td>
                      <td>8.4% </td>
                      <td>4%</td>
                      <td>0.35%</td>
                      <td>3% </td>
                      <td>5%-10%</td>
                  </tr>
                  <tr class="tr04"></tr>
                  <tr class="tr03">
                      <td>年收益</td>
                      <td>6.2%-18.25%</td>
                      <td>陆金所</td>
                      <td>余额宝</td>
                      <td>银行活期</td>
                      <td>银行1年定期</td>
                      <td>理财产品</td>
                  </tr>
                  <tr class="tr04"></tr>
                  <tr class="tr02">
                      <td>提现周期</td>
                      <td>灵活选择</td>
                      <td>3个月以上</td>
                      <td>随时</td>
                      <td>随时</td>
                      <td>1年</td>
                      <td>有产品确定</td>
                  </tr>
                  <tr class="tr04"></tr>
                  <tr class="tr03">
                      <td>投资门槛</td>
                      <td>500元即可投资</td>
                      <td>1万元</td>
                      <td>无</td>
                      <td>无</td>
                      <td>50元起存</td>
                      <td>5万起</td>
                  </tr>
              </table>
         </div>
    </div>
  <div class="haoli2">
    	<div class="top">
        	<div>
            	<h3 class="png">注册送好礼</h3>
            </div>
        </div>
        <div class="bottom">
	 		 <img src="/static/img/huiyuan3.jpg" width="984" height="192" />
        </div>
    </div>
	<div class="haoli3">
    	<div class="top">
        	<div>
            	<h3 class="png">好友推荐有好礼</h3>
            </div>
        </div>
        <div class="bottom">
        	<div class="left png"></div>
        	<div class="right">
            	<p>在平台已成功实名认证的用户，</p>
                <p>每推荐一位好友投资成功，奖励10元；</p>
                <p>以此类推，推荐费用不断累积，</p>
                <p>直到100万礼金送完为止！</p>
                <p>APP、微信注册用户、日利宝投资用户不参与此项活动</p>
                <div><a href="#">立即推广</a></div>
            </div>
         </form>  
         </div>
    </div>
</div><!-- main end -->

<!--
<div class="content">
  <div style="width:100%; height:48px; background:#eae9e9; border-bottom:1px solid #c6c6c6;">
    <div style=" width:985px;height:48px; margin:0 auto; background:url(${base}/static/img/d6.png) 0 0 no-repeat;">
    </div>
  </div>
  <form id="registerForm" method="post" action="registerFd.do">
  <input type="hidden" name="user.typeId" id="typeId0" value="0"  />
  <input type="hidden" name="user.regMoney" id="" value="0" />
  <input type="hidden" name="user.memberType" id="" value="0" />
  <input type="hidden" name="r" id="Id0" value="${r}" /><#--推广编号-->
  <!--
  <div style="width:985px; margin:0 auto; margin-top:15px; padding-bottom:15px; clear:both;">
      <div style=" padding:20px 0px 20px 20px; background:url(/static/img/d5.png) 0 0 no-repeat; width:965px; height:637px;">
        <div style=" padding:10px 40px 35px 40px; width:295px; background:#fff;">
           <div style="float:left; padding-bottom:15px; width:285px;">
              <span style="color:#4b4b4b;font-family:'宋体';float:right; margin-top:8px; ">已有帐号<a href="${base}/user/login.do" style="color:#4b4b4b;font-family:'宋体'; font-weight:bold; ">[立即登录]</a></span>
           </div>
           <div style="clear:both;width:295px;">
          	 <ul>
                 <li style="color:#4b4b4b;font-family:'宋体'; margin-bottom:5px; ">邀请人</li>
                 <li>${friendUsername}</li>
                 <li style="height:20px;"></li>
               </ul>
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
<!--
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
                 <li>
                 	<input type="text" id="yzm_text" name="mycode" style="width:174px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
                 	<a class="kaqu_yiyi"><img id = "code_img" src="${base}/rand.do" width="63" height="32" /></a>
                 </li>
                 
                 <li style="height:30px;"></li>
               </ul>
               
               <ul>
                 <li><input type="checkbox" id="agreeCheckbox" value="false" checked /><span style="font-family:'宋体'; margin-left:4px;">我已阅读并同意《${Application ["qmd.setting.name"]}服务协议》</span></li>
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

</body>
</html>

