<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-登录</title>
	<#include "/content/common/meta.ftl">  

 <style>
  	.am-form-group {margin-bottom: 1rem;}
  	.am-form .am-form-group input{border: 1px solid #e3e3e3;}
  </style>
</head>


    <body class="bgc5" style="height:100%;overflow:hidden;">
        <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40">
	      <div class="am-header-left am-header-nav">
	      	<a href="#right-link">
	          	  <i class="am-icon-angle-left am-icon-fw" style="font-size:1.5em;"></i>
	          </a>
	      </div>
	      <h1 class="am-header-title">
	          登录
	      </h1>
	      <div class="am-header-right am-header-nav"></div>
	  </header><!-- header end-->
   		<div class="am-g" style="margin-top:1em;">
		  <div class="am-u-md-8 am-u-sm-centered">
		    <form name="loginForm" id="loginForm" class="am-form"  method="post">
		      <fieldset class="am-form-set">   	
		      	<div class="am-form-group">
		      		<input type="text" name="user.username" id="username" placeholder="请输入手机号码" style="height:2.4em;">
		      	</div>
				<div class="am-form-group">
		      		<input type="password" name="user.password" id="password" placeholder="请输入登录密码" style="height:2.4em;">
		      	</div>
		
				<!-- 弹框开始 -->
					<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
					  <div class="am-modal-dialog">
					    <div class="am-modal-bd"></div>
					    <div class="am-modal-footer">
					      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
					    </div>
					  </div>
					</div>
				<!-- 弹框结束 -->
				<div class="tar" style="margin:-0.6em 0 0.6em;"><a href="${base}/lostPwdTo.do" class="color">忘记密码？</a></div>
				<button type="button" id="login_button" class="am-btn am-btn-block bgc color1">登录</button>			
				<div class="tac" style="margin-top:1em;"><a href="${base}/reg.do" class="color">立即注册</a></div>  
		      </fieldset>     

		    </form>
		  </div>
		</div>
	 </body>
	 
	 <script type="text/javascript">
		$(function(){
			var $loginForm = $("#loginForm");
			$("#login_button").click(function(){
				
				
					$.ajax({
							url: "${base}/ajaxLogin.do",
							data: $loginForm.serialize(),
							type: "POST",
							dataType: "json",
							cache: false,
							beforeSend: function() {
								$loginForm.find("submit").attr("disabled", true);
							},
							success: function(data) {
								if(data.status=="success"){
									
									window.location.href = "${base}/userCenter/index.do";
									//$(".am-modal-bd").html("登录成功");
									//$('#my-alert').modal({
									//	onConfirm: function(options) {
									//		window.location.href = "${base}/userCenter/index.do";
									//	}
									//});
								}else{
									$(".am-modal-bd").html(data.message);
									$('#my-alert').modal();
								}
							}
						});
					
			});
		});
	</script>
</html>
