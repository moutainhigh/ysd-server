<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>忘记登录密码</title>
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
	        	  忘记登录密码
	      </h1>
	      <div class="am-header-right am-header-nav"></div>
	  </header><!-- header end-->

   		<div class="am-g" style="margin-top:1em;">
		  <div class="am-u-md-8 am-u-sm-centered">
		    <form id="pwdForm" class="am-form">
		      <fieldset class="am-form-set">
		      	<div class="am-form-group am-form-icon">
		      		<input type="text" id="phoneReg" name="phone" placeholder="请输入认证手机号码" style="padding-left:2em!important;">
		      		<i class="am-icon-mobile f15" style="position:absolute;left:0.5em;top:0.8em;"></i>
		      	</div>
		        <div class="am-form-group am-form-icon">
		      		<input type="text" id="mycodePhone" name="codeReg" placeholder="请输入验证码" maxlength="6" style="width:44%;height:2.4em;float:left;padding-left:2em!important;">
		      		<button type="button" id="getcode" onclick="sendAuthCode(this);" class="am-btn am-btn-default am-radius bgc color1" style="float:right;width:44%;height:2.4em;">获取验证码</button>
		      		<i class="am-icon-comment-o f12" style="position:absolute;left:0.4em;top:0.9em;"></i>
		      		<div style="clear:both;"></div>
		      	</div>
		      </fieldset>
		      <button type="button" id="pwd_button" class="am-btn am-btn-block color1 bgc">重置登录密码</button>
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
				    		var phoneReg = $("#phoneReg").val();
				    		if(phoneReg.length<=0){
				    			$(".am-modal-bd").html('请输入手机号');
								$('#my-alert').modal();
				    			
				    			return false;
				    		}
				    		var regPhone =/^(13[0-9]|17[0-9]|18[0-9]|15[0-9]|18[8|9])\d{8}$/;
				    		if (!regPhone.test(phoneReg)){alert(phoneReg);
				    			$(".am-modal-bd").html('手机号格式不正确');
								$('#my-alert').modal();
				    			return false;
				    	 	}
				    	 	
				    	 	$.ajax({
					        	   url: '${base}/rest/sendPCodeb',
					        	   async:false,
					        	   type: 'post', 
					        	   dataType:'json', 
					        	   data: {'phoneReg':phoneReg}, 
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
					url: "${base}/rest/checklogp",
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
									window.location.href="${base}/login.do";
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
