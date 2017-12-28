<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>忘记交易密码</title>
	<#include "/content/common/meta.ftl">
  <style>
  	.am-form-group {margin-bottom: 1rem;}
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
	        	  忘记交易密码
	      </h1>
	      <div class="am-header-right am-header-nav"></div>
	  </header><!-- header end-->

   		<div class="am-g" style="margin-top:1em;">
		  <div class="am-u-md-8 am-u-sm-centered">
		    <form id="pwdForm" class="am-form">
		    	<input type="hidden" id="token_hidden" name="token">
		      <fieldset class="am-form-set">
		      
		      	<div class="am-form-group">
		      		<input type="text" name="cardId" placeholder="请输入身份证后8位">
		      	</div>
		      	<div class="am-form-group">
		      		<p>你绑定的手机号码：${substringWord(user.phone,3,"*****")}${user.phone?substring(8,11)}</p>
		      	</div>
		        <div class="am-form-group">
		      		<input type="text" placeholder="请输入验证码" name="codeReq" maxlength="6" style="width:44%;height:2.4em;float:left;">
		      		<button type="button" id="getcode" onclick="sendAuthCode(this);" 
		      		 class="am-btn am-btn-default am-radius bgc color1" style="float:right;width:44%;height:2.4em;">获取验证码</button>
		      		<div style="clear:both;"></div>
		      	</div>
		      	<div class="am-form-group">
		      		<input type="password" name="newPassword" placeholder="请输入新的交易密码">
		      	</div>
		      </fieldset>
		      <button type="button" id="pwd_button" class="am-btn am-btn-block color1 bgc">确定</button>
		    </form>
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
		  </div>
		</div>
    </body>
    <script type="text/javascript">

//发送短信验证码
	var wait2=60;
	function sendAuthCode(o){
		if (wait2 ==0) {
	        o.removeAttribute("disabled");         
	        o.innerHTML="获取验证码";
	        $('#getcode').removeAttr("disabled");
	        $("#getcode").removeClass("getcode");
	        wait2 = 60;
		} else {
		    	if(wait2==60){
				    	 	$.ajax({
					        	   url: '${base}/rest/sendPCodeUser',
					        	   async:false,
					        	   type: 'post', 
					        	   dataType:'json', 
					        	   data: {'token':$("#token_hidden").val()}, 
					        	   beforeSend: function() {
										  o.innerHTML="短信发送中...";
										  o.setAttribute("disabled", true);
										  $("#getcode").addClass("getcode");
			        					  $('#getcode').attr('disabled',"true");
								   },
					        	   success: function (data) { 
					        		   if(data.rcd=="R0001"){
				 		   		            o.innerHTML= wait2 + "秒";
				 		   		            wait2--;
				 		   		            setTimeout(function(){sendAuthCode(o)}, 1000);
					        	  		}else{
				    						$(".am-modal-bd").html(data.rmg);
											$('#my-alert').modal();
				    						
					        	   			o.removeAttribute("disabled");         
									        o.innerHTML="获取验证码";
			       							$('#getcode').removeAttr("disabled");
		        							$("#getcode").removeClass("getcode");
					        	   		}
					        	   }, 
					        	   error: function (XMLHttpRequest, textStatus, errorThrown) { 
				        	   			alert(errorThrown); 
					        	   } 
			        	  });
		    	}else{
		            o.innerHTML= wait2 + "秒";
		            wait2--;
		            setTimeout(function(){sendAuthCode(o)}, 1000);	
		    	}  
		}		 
	}
	
	$(function(){
			var $pwdForm = $("#pwdForm");
			$("#pwd_button").click(function(){
				$.ajax({
					url: "${base}/rest/lostSafePwd",
					data: $pwdForm.serialize(),
					type: "POST",
					dataType: "json",
					cache: false,
					beforeSend: function() {
						$pwdForm.find("button").attr("disabled", true);
					},
					success: function(data) {
						if(data.rcd=="R0001"){
							$(".am-modal-bd").html(data.rmg);
							$('#my-alert').modal({
								onConfirm: function(options) {
									window.location.href="${base}/userCenter/detail.do";
								}
							});
						}else{
							$(".am-modal-bd").html(data.rmg);
							$('#my-alert').modal();
						}
					}
				});
			});
		});
	</script>
</html>
