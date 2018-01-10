<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-个人账户注册</title>
	 <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" type="text/css" href="${base}/css/style.css" />
	<#include "/content/common/meta.ftl">
	<script src="${base}/static/js/jquery.min.js"></script>
	<script src="${base}/static/js/base.js"></script>
	<script src="${base}/static/base/js/base.js"></script>
	<script src="${base}/static/js/common/qmd.js"></script>	
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript">
 $(function(){
        $("section").height(window.screen.height - $("section").offset().top)
    })


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
					}
					
				},
				messages: {
					"user.phone":{
						required:"手机号不能为空"
					}
				},
				errorPlacement: function(error, element) {
				  error.appendTo(element.parent("li").next("li"));
				},
				submitHandler: function(form) {
					
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
<div class="invest">
        <header class="hd">
    	</header>
        <!--header--结束-->
        <section class="cont">
         <form id="registerForm" method="post" action="/user/checkRegPhone.do">
  			<input type="hidden" name="user.typeId" id="typeId0" value="0"  />
  			<input type="hidden" name="r" id="Id0" value="${r}" />
  			  <div class="friend">您的好友 ${friendUsername}邀请您加入</div>
		         <div><img src="/img/moblie/logo.png" style="height:3em;display:inline-block;" alt=""></div>
		         <div class="shuru">
			 
             	<input type="text" name="user.phone" id="phone" placeholder="请输入您的手机号码">
			 <button class="get" onclick="$('#registerForm').submit()">获取现金红包</button>
         </form>  
        </section>
        <!--section--结束-->
 <!--       <footer>
        	<!--<div class="pingtai">
                <span></span><p>平台资金账户由国有银行实时监督</p>
            </div>-->
<!--            <img src="/static/img/moblie/zc03.png">
        </footer>
        <!--footer--结束-->
    </div>
</body>


</html>

