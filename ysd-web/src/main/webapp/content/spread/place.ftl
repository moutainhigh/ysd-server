<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-注册</title>
    <#include "/content/common/meta.ftl">

    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <script src="${base}/js/dpi.js"></script>
    <link rel="stylesheet" href="${base}/css/common.css"  media="screen and (min-width: 751px)"/>
    <link rel="stylesheet" href="${base}/css/invest_friend_web.css?v=1.0" media="screen and (min-width: 751px)">
    <link rel="stylesheet" href="${base}/css/invest_friend_phone.css?v=1.0" media="screen and (max-width: 750px)">
     <style>
        #footer {
            background: url(${base}/img/invest_footer_bg.png);
        }
    </style>

</head>
<body>
<!-- 头部 -->
<#include "/content/common/header.ftl">
<!-- 主内容区 -->
<div class="content">
    <div class="img"></div>
    <div class="register">
        <form id="registerForm" AUTOCOMPLETE="off">
            <table>
            <#if friendUsername?exists && friendUsername !=''>
                <tr class="invest_person">
                    <td colspan="2">邀请人：<span>${friendUsername}</span></td>
                </tr>
            </#if>
                <tr>
                    <td colspan="2"><input type="text" id="place_username" name="username" class="phone" placeholder="请输入手机号码" ></td>
                </tr>
                <tr class="input_hint"> <td colspan="2"></td></tr>
                <tr class="identify">
                    <td><input type="text" name='code' id="place_code" placeholder="验证码"></td>
                    <td><img onclick="verifyCodeLink();" src="${base}/placeRand.do" id="code_img"></img></td>
                </tr>
                <tr class="input_hint"><td colspan="2"></td></tr>
                <tr class="message">
                    <td><input type="text" placeholder="请输入短信验证码"  name="smsCode" value=""></td>
                    <td><a id="getcode" onclick="sendAuthCode(this);" >获取验证码</a></td>
                </tr>
                <tr class="input_hint"><td colspan="2"></td></tr>
                <tr class="pwd">
                    <td colspan="2"><input type="password" id="place_pwd" name="pwd" placeholder="请输入密码，长度8~16位，必须包含字母"></td>
                </tr>
                <tr class="input_hint"><td colspan="2"></td></tr>
                <tr class="button">
                    <td colspan="2"><input type="submit" value="确认注册"></td>
                </tr>
            </table>
        </form>
    </div>
    <div class="clear"></div>
    <div class="feature">
        <div class="left">
            <div class="img"></div>
            <div class="content">
                <div class="top">放心理财 安全无忧</div><div class="hr"></div><div class="bottom">优选合作机构，严格监管承诺回购</div>
            </div>
        </div>
        <div class="center">
            <div class="img"></div>
            <div class="content">
                <div class="top">放心理财 安全无忧</div><div class="hr"></div><div class="bottom">优选合作机构，严格监管承诺回购</div>
            </div>
        </div>
        <div class="right">
            <div class="img"></div>
            <div class="content">
                <div class="top">放心理财 安全无忧</div><div class="hr"></div><div class="bottom">优选合作机构，严格监管承诺回购</div>
            </div>
        </div>
    </div>
    <div class="clear"></div>
</div>
<!--底部-->
	<#include "/content/common/footer-center.ftl">
<script src="${base}/js/jquery-1.11.3.min.js"></script>
<script src="${base}/js/common.js"></script>
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript">
	function verifyCodeLink(){
		$("#code_img").attr("src", "/placeRand.do?timestamp" + (new Date()).valueOf());
	}

	$().ready(function() {



		var $registerForm = $("#registerForm");
		// 表单验证
			$registerForm.validate({
				errorClass:"login_error",
				rules: {
					"username":{
						required:true,
						phone:true
					},
					"pwd":{
						required: true,
						strongTxt:true,
						minlength:8,
						maxlength:16
					},
					"code":{
						required:true
					},
					"smsCode":{
						required:true
					}
				},
				messages: {
					"username":{
						required:"手机号不能为空",
						phone:"手机号码格式错误"
					},
					"pwd": {
						required: "请输入密码",
						minlength: "密码必须大于等于8",
						maxlength: "密码必须小于等于16"
					},
					"code":{
						required:"请输入图形验证码"
					},
					"smsCode":{
						required:"请输入短信验证码"
					}
				},
				errorPlacement: function(error, element) {
				  	error.appendTo(element.parents("tr").next(".input_hint").find('td'));
				  	element.parents("tr").next(".input_hint").css('visibility','visible');
				},
				//success:function(success,element){
				//	success.parents("tr").css('visibility','hidden');
				//},
				submitHandler: function(form) {
					$.ajax({
				        type: "post",
				        dataType : "json",
				        data: $("#registerForm").serialize(),
				        url: '${base}/ajaxNewPlace.do',
			    	    success: function(data, textStatus){
			    	    	if(data.rcd=='R0001'){
			    	    		window.location.href='${base}/user/regSuccess.do';
   								 $('.cd-popup').addClass('is-visible');
			    	    	}else{
			    	    		//alert(data.rmg);
			    	    		$('.message').next(".input_hint").find('td').html("<label for='smsCode' class='login_error'>"+data.rmg+"</label>");
			    	    		$('.message').next(".input_hint").css('visibility','visible');
			    	    	}
			    	    }
					});
				}
	});
});

  $('.cd-popup').on('click', function(event){
      event.preventDefault();
      $(this).removeClass('is-visible');
  });

	function verifyCodeLink(){
		$("#code_img").attr("src", "/placeRand.do?timestamp" + (new Date()).valueOf());
	}
	//发送短信验证码
	var wait2=60;
	function sendAuthCode(o){
		$("#err_show4").html('');
		if (wait2 ==0) {
			        o.removeAttribute("disabled");
			        o.innerHTML="获取验证码";
			        $('#getcode').removeAttr("disabled");
			        $("#getcode").removeClass("getcode");
			        wait2 = 60;
			        $('#getcode').attr('onclick','sendAuthCode(this);');
		} else {
		    	if(wait2==60){
				    		var phoneReg = $("#place_username").val();
				    	 	var mycode = $("#place_code").val();
				    		if(phoneReg.length<=0){
			    	    		$('#place_username').parents('tr').next(".input_hint").find('td').html("<label for='place_username' class='login_error'>手机号不能为空</label>");
			    	    		$('#place_username').parents('tr').next(".input_hint").css('visibility','visible');
				    			return false;
				    		}
				    		var regPhone =/^(13[0-9]|14[0-9]|17[0-9]|18[0-9]|15[0-9]|18[8|9]|14[0-9])\d{8}$/;
				    		if (!regPhone.test(phoneReg)){
			    	    		$('#place_username').parents('tr').next(".input_hint").find('td').html("<label for='place_username' class='login_error'>手机号码格式错误</label>");
			    	    		$('#place_username').parents('tr').next(".input_hint").css('visibility','visible');
				    			return false;
				    	 	}
				    	 	$.ajax({
					        	   url: '${base}/sendPhoneCode.do',
					        	   async:false,
					        	   type: 'post',
					        	   dataType:'json',
					        	   data: {'phoneReg':phoneReg,'mycode':mycode},
					        	   beforeSend: function() {
										  o.innerHTML="短信发送中...";
										  o.setAttribute("disabled", true);
										  $("#getcode").addClass("getcode");
			        					  $('#getcode').attr('disabled',"true");
								   },
					        	   success: function (data) {
					        		   if(data.result=="0"){
					        		   		$('#getcode').attr('onclick','');
				 		   		            o.innerHTML="重新发送(" + wait2 + ")";
				 		   		            wait2--;
				 		   		            setTimeout(function(){sendAuthCode(o)}, 1000);
					        	  		}else{
				    						//$("#err_show4").html(data.reason);
						    	    		$('#place_username').parents('tr').next(".input_hint").find('td').html("<label for='place_username' class='login_error'>"+data.reason+"</label>");
						    	    		$('#place_username').parents('tr').next(".input_hint").css('visibility','visible');
					        	   			o.removeAttribute("disabled");
									        o.innerHTML="获取验证码";
			       							$('#getcode').removeAttr("disabled");
		        							$("#getcode").removeClass("getcode");
					        	   		}
					        	   },
					        	   error: function (XMLHttpRequest, textStatus, errorThrown) {
				        	   			$("#username_msg").html(errorThrown);
				    					return false;
					        	   }
			        	  });
		    	}else{
		            o.innerHTML="重新发送(" + wait2 + ")";
		            wait2--;
		            setTimeout(function(){sendAuthCode(o)}, 1000);
		    	}
		}
	}

  $(".remove").click(function(){
    $(".bottom").css('display','none');
  })

  	//退出登录
	$('.downloadidClass').click(function(){
		var u = navigator.userAgent;
		var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
		var ua = navigator.userAgent.toLowerCase();
	    if(ua.match(/MicroMessenger/i)=="micromessenger") {
	       alert('请点击微信右上角按钮，然后在弹出的菜单中，点击在浏览器中打开，即可安装');
	    } else {
			if (isiOS) {
                // TODO
				window.location.href = "https://itunes.apple.com/cn/app/id999649448";
			} else if (isAndroid) {
				window.location.href = "https://www.yueshanggroup.cn/download/yueshangdai${(placeChilder.random)!}.apk";
			} else {
				window.location.href = "https://www.yueshanggroup.cn/download.html";
			}
	    }
	});
</script>

</body>
</html>