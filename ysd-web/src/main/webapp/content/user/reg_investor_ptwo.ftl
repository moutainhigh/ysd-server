<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<#include "/content/common/meta.ftl">
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-用户注册</title>
	  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
     <link rel="stylesheet" type="text/css" href="${base}/static/css/reset.css" />
	<script src="${base}/static/js/jquery.min.js"></script>
	<script src="${base}/static/js/base.js"></script>
	<script src="${base}/static/base/js/base.js"></script>
	<script src="${base}/static/js/common/qmd.js"></script>	
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
	<script type="text/javascript">
	//发送验证码
	var wait2=60;
	function sendAuthCode(o){

		var phoneReg = document.getElementById("phone").value;
		if (wait2 ==0) {
			       $("#yzm_button").attr("onclick","sendAuthCode(this);");
			        $("#yzm_button").css("background", "#be9964");
			        $("#yzm_button").html("发送");
			        wait2 = 60;
		} else {
		   if(wait2==60){
				    	 $.ajax({ 
						     	
		    						url : '${base}/sendPhoneCode.do?phoneReg='+phoneReg+'&',
						        	   async:false,
						        	   type: 'post', 
						        	   dataType:'json', 
						        	   beforeSend: function() {
						        	   		 $("#yzm_button").attr("onclick","sendAuthCode(this);");  
											  $("#yzm_button").html("短信发送中...");
											  $("#vile").html("手机短信验证码正在发送中"); 
							    			 
									   },
						        	   success: function (data) { 
						        		   if(data.result==0){
						        	  			  // alert("短信发送成功");
						        	  			 $("#yzm_button").removeAttr("onclick");  
						        	  			 $("#yzm_button").css("background", "#dedbdb");
						        	  			 $("#vile").html("手机短信验证码已发送，请查收！");
						        	  			 $("#yzm_button").html( wait2 ); 
					 		   		            wait2--;
					 		   		            setTimeout(function(){sendAuthCode(o)}, 1000);
					 		   		         
						        	  		}
						        		   else if(data.result==1){
						        	   			alert("短信发送失败,原因是:"+data.reason);
						        	   			 $("#yzm_button").attr("onclick","sendAuthCode(this);");  
						        	   			  $("#yzm_button").css("background", "#be9964");
						        	   			o.removeAttribute("disabled");
						        	   			$("#yzm_button").html("发送");          
										        $("#vile").html("请点击获取手机短信验证码"); 
										       
						        	   		}
						        	   }, 
						        	   error: function (XMLHttpRequest, textStatus, errorThrown) { 
					        	   			alert(errorThrown); 
						        	   } 
				        	  });
		    	}else{
		    				 $("#yzm_button").removeAttr("onclick");
		    				  $("#yzm_button").css("background", "#dedbdb");     
				           $("#yzm_button").html( wait2);  
				            wait2--;
				            setTimeout(function(){sendAuthCode(o)}, 1000);	
		    	}  
		}		 
	}

$().ready(function() {
		var $registerForm = $("#registerForm");	
		if($("#codeReg").val()==''){
			alert("短信验证码不能为空");
		}
		if($("#user.password").val()==''){
			alert("密码不能为空");
		}
		if($("#mycode").val()==''){
			alert("验证码不能为空");
		}
		// 表单验证
			$registerForm.validate({
				errorClass:"login_error",
				errorLabel:"login_error",
				rules: {
					
					"user.password":{
						strongTxt:true,
						minlength:8,
						maxlength:16
					},
					
					
					"isAgreeAgreement":{
						required:true
					}
				},
				messages: {
					
					"user.password": {
						minlength: "密码必须大于等于8",
						maxlength: "密码必须小于等于16"
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
						alert('请阅读并同意《"+${Application ["qmd.setting.name"]}+"服务协议》');
						return;
					}
					
					form.submit();
				}
			});		
		
	});
		
</script>
</head>
<body >
 
 <div class="zhuce02">
 		<form id="registerForm" method="post" action="/user/registerPfd.do">
		<input type="hidden" name="user.typeId" id="typeId0" value="0"  />
  		<input type="hidden" name="user.regMoney" id="" value="0" />
  		<input type="hidden" name="user.memberType" id="" value="0" />
  		<input type="hidden" name="r" id="Id0" value="${r}" /><#--推广编号-->
  		<input type="hidden" name="user.phone" id="phone" value="${user.phone}" />
        <section>
        	<p><span id="vile" name="vile">请点击获取手机短信验证码</span></p>
            <ul>
<#--
                <li class="tuxing">
                    <a class="img" href="#"><img class="kaqu_yiyi" id = "code_img" src="${base}/rand.do"/></a><input type="text"  id="yzm_text" name="mycode" placeholder="请输入图形验证码"><#--<a class="see" href="#">换一张</a>
                </li>-->
                <li class="yanzheng">
                    <span>验证码</span><input type="text"  name="codeReg" id="mycodePhone" placeholder="6位短信验证码"> <a  href="javascript:void(0);" onclick="sendAuthCode(this)" id="yzm_button" class="phoneCode" style="background:#be9964;" >发送</a>
                </li>
                <li class="mima">
                    <span>登录密码</span><input type="password" name="user.password" id="pwd_investor" placeholder="6-20个字母或数字组合">
                </li>
                <li class="xieyi">
                    <input type="checkbox" name="Checkbox" id="agreeCheckbox" value="false" checked ><span>我同意<a href="${base}/regTreaty.do" target="_blank" style="color:#3c7bb3;">《${Application ["qmd.setting.name"]}服务协议》</a></span>
                </li>
                <li class="shuru"></li>
                <li class="wanc"><a  href="javascript:void(0);" onclick="$('#registerForm').submit()">完成注册</a></li>
            </ul>
             
        </section>
        <!--section--结束-->
       </from>
       <!-- <footer>
        	<div class="pingtai">
            	<span></span><p>平台资金账户由国有银行实时监督</p>
             </div>
            <img src="/static/img/moblie/zc03.png">
        </footer>-->
        <!--footer--结束-->
    </div>
   
	
</body>
</html>
