<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${Application ["qmd.setting.name"]}-注册</title>
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
	          注册
	      </h1>
	      <div class="am-header-right am-header-nav"></div>
	  </header><!-- header end-->
   		<div class="am-g" style="margin-top:1em;">
		  <div class="am-u-md-8 am-u-sm-centered">
		  <form name="registerForm" id="registerForm" class="am-form"  method="post">
		      <fieldset class="am-form-set">   	
		      	<div class="am-form-group">
		      		<input type="text" name="user.phone" id="phone" placeholder="请输入手机号码" style="height:2.4em;">
		      	</div>
		        <div class="am-form-group">
		      		<input type="text" placeholder="请输入验证码" style="width:50%;height:2.4em;float:left;" name="codeReg" id="codeReg">
		      		<button type="button" id="getcode" onclick="sendAuthCode(this);"  class="am-btn am-btn-default am-radius bgc color1" style="float:right;width:44%;height:2.4em;">获取验证码</button>
		      		<div style="clear:both;"></div>
		      	</div>
				<div class="am-form-group">
		      		<input  type="password"  name="user.password" id="password1" placeholder="请输入登录密码" style="height:2.4em;">
		      	</div>
				<div class="am-form-group">
		      		<input  type="password" name="password2" id="password2"  placeholder="重复登录密码" style="height:2.4em;">
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
				
				<button type="button" id="reg_button"  class="am-btn am-btn-block am-radius color1 bgc" style="">立即注册</button>
				<div class="tac" style="margin-top:1em;"><a href="${base}/login.do" class="color">登录</a></div>
		      </fieldset>     
		    </form>
		  </div>
		</div>
</body>
<script type="text/javascript">

$(function(){
			var $registerForm = $("#registerForm");
			
			$("#reg_button").click(function(){
			var $phone = $("#phone").val();
			var $codeReg = $("#codeReg").val();
			var $password1 = $("#password1").val();
			var $password2 = $("#password2").val();
				if($phone==""||$phone.lenght<1){
					$(".am-modal-bd").html("手机号码不能为空");
					$('#my-alert').modal();
				}else if($codeReg==""||$codeReg.lenght<1){
					$(".am-modal-bd").html("短信码不能为空");
					$('#my-alert').modal();
				}else if($password1==""||$password1.lenght<1){
					$(".am-modal-bd").html("密码不能为空");
					$('#my-alert').modal();
				}else if($password1!=$password2){
					$(".am-modal-bd").html("两次密码不一致");
					$('#my-alert').modal();
				}else{
				
					$.ajax({
							url: "${base}/register.do",
							data: $registerForm.serialize(),
							type: "POST",
							dataType: "json",
							cache: false,
							beforeSend: function() {
								$registerForm.find("submit").attr("disabled", true);
							},
							success: function(data) {
								if(data.status=="success"){
									$(".am-modal-bd").html(data.message);
									$('#my-alert').modal({
										onConfirm: function(options) {
											window.location.href = "${base}/userCenter/index.do";
										}
									});
								}else{
									$(".am-modal-bd").html(data.message);
									$('#my-alert').modal();
								}
							}
						});
					}
			});
		});
		
	
	//发送验证码
	var wait2=60;
	function sendAuthCode(o){
		if (wait2 ==0) {
			        o.removeAttribute("disabled");         
			        o.innerHTML="获取验证码";
			        $("#getcode").removeClass("getcode_disable");
			        $("#getcode").addClass("getcode");
			        // $("#yzm_button").removeClass("kaqu_w120");
					// $("#yzm_button").addClass("phoneCode");
			        wait2 = 60;
		} else {
		    	if(wait2==60){
				    		var phoneReg = $("#phone").val();
				    		if(phoneReg.length<=0){
				    			$(".am-modal-bd").html("手机号码不能为空！");
									$('#my-alert').modal();
				    		}
				    		var regPhone =/^(13[0-9]|17[0-9]|18[0-9]|15[0-9]|18[8|9])\d{8}$/;
				    		if (!regPhone.test(phoneReg)){
				    			$(".am-modal-bd").html("手机号格式不正确");
									$('#my-alert').modal();
				    	 	}
				    	 	
				    	 	$.ajax({ 
						        	   url: '${base}/sendPCode.do',
						        	   async:false,
						        	   type: 'post', 
						        	   dataType:'json', 
						        	   data: {'phoneReg':phoneReg}, 
						        	   beforeSend: function() {
											  o.innerHTML="短信发送中...";
											  o.setAttribute("disabled", true);
											  $("#getcode").removeClass("getcode");
			        						  $("#getcode").addClass("getcode_disable");
							    			  //$("#yzm_button").removeClass("phoneCode");
											  //$("#yzm_button").addClass("kaqu_w120");
									   },
						        	   success: function (data) { 
						        	   
						        		   if(data.result==0){
						        	  			  // alert("短信发送成功");
					 		   		            o.innerHTML="重新发送(" + wait2 + ")";
					 		   		            wait2--;
					 		   		            setTimeout(function(){sendAuthCode(o)}, 1000);
					 		   		         
						        	  		}
						        		   else if(data.result==1){
								        		   $(".am-modal-bd").html("短信发送失败,原因是:"+data.reason);
													$('#my-alert').modal();
						      
						        	   			o.removeAttribute("disabled");         
										        o.innerHTML="获取验证码";
										        $("#getcode").removeClass("getcode_disable");
			        							$("#getcode").addClass("getcode");
										        //$("#yzm_button").removeClass("kaqu_w120");
												//$("#yzm_button").addClass("phoneCode");
						        	   		}
						        	   }, 
						        	   error: function (XMLHttpRequest, textStatus, errorThrown) { 
					        	   			alertMessage(errorThrown); 
						        	   } 
				        	  });
		    	}else{
				            o.innerHTML="重新发送(" + wait2 + ")";
				            wait2--;
				            setTimeout(function(){sendAuthCode(o)}, 1000);	
		    	}  
		}		 
	}

</script>
</html>
