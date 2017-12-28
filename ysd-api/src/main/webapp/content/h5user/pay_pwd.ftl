<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>修改交易密码</title>
	<#include "/content/common/meta.ftl">
  <style>
  	 .am-form-group {margin-bottom: 1rem;}
  	.am-form .am-form-group input{border: 1px solid #e3e3e3;}
  </style>
</head>
    <body class="bgc5" style="height:100%;overflow:hidden;">
        <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40">
	      <div class="am-header-left am-header-nav">
	      	<a href="#left-link">
	          	  <i class="am-icon-angle-left am-icon-fw" style="font-size:1.5em;"></i>
	          </a>
	      </div>
	      <h1 class="am-header-title">
	         	 修改交易密码
	      </h1>
	      <div class="am-header-right am-header-nav"></div>
	  </header><!-- header end-->

   		<div class="am-g" style="margin-top:1em;">
		  <div class="am-u-md-8 am-u-sm-centered">
		    <form id="pwdForm" class="am-form">
		    	<input type="hidden" name="token">
		      <fieldset class="am-form-set">
		      	<div class="am-form-group">
		      		<input type="password" name="oldPassword" placeholder="请输入原交易密码">
		      	</div>
		        <div class="am-form-group">
		      		<input type="password" name = "newPassword" placeholder="请输入8-16位新交易密码">
		      	</div>
		      	<div class="am-form-group">
		      		<input type="password" name="againPassword" placeholder="请再次输入新的交易密码">
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
				
		      </fieldset>
		      <button type="button" id="pwd_button" class="am-btn am-btn-block bgc color1">修改</button>
		    </form>
		    <div class="tac" style="margin-top:1em;"><a href="${base}/userCenter/payPwdLostTo.do" class="color9">忘记密码?</a></div>
		  </div>
		</div>
    </body>
	<script type="text/javascript">
		$(function(){
			var $pwdForm = $("#pwdForm");
			$("#pwd_button").click(function(){
					$.ajax({
							url: "${base}/userCenter/updatePaypwd.do",
							data: $pwdForm.serialize(),
							type: "POST",
							dataType: "json",
							cache: false,
							beforeSend: function() {
								$pwdForm.find("submit").attr("disabled", true);
							},
							success: function(data) {
								if(data.status=="success"){
									$(".am-modal-bd").html(data.message);
									$('#my-alert').modal({
										onConfirm: function(options) {
											window.location.href="${base}/userCenter/detail.do";
										}
									});
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

