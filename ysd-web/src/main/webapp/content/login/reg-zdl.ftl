<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-注册</title>
<#include "/content/common/meta.ftl">
</head>
<body>
 <!-- header -->
 <#include "/content/common/header.ftl">
<div class="register">
  <div style="width:1190px;height:730px;margin:30px auto;background: #fff url(/img/cele.png) no-repeat 630px 200px;border:1px solid #dcdcdc;border-radius:5px;">
    <div style="border-bottom:1px solid #dcdcdc;height:68px;line-height:68px;color:#fd7c1a;font-size:20px;text-align:center;">
      注册
      <span style="float:right;color:#666;font-size:14px;margin-right:40px;">已有账户马上<a href="${base}/user/login.do" style="color:#fd7c1a;">登录</a></span>
    </div>
    
    <div style="width:550px;margin:30px 0 0 100px;">
      <div class="form" style="">
      	<form action="${base}/user/register.do" method="post" id="registerForm" AUTOCOMPLETE="off">
      		<input type="hidden" name="r" id="Id0" value="${r}" /><#--推广编号-->
      		<#if friendUsername??>
      			 <ul class="ul">
            		<li class="li01">邀请人：</li>
            		<li class="li02" style="line-height: 40px;">${friendUsername}</li>
            		<li class="li03"></li>
          		</ul>
      		</#if>
          <ul class="ul">
            <li class="li01">手机号：</li>
            <li class="li02"><input type="text"  placeholder="请输入手机号" name="user.phone" id="phone"></li>
            <li class="li03"></li>
          </ul>
          <ul class="ul">
            <li class="li01">验证码：</li>
            <li class="li02" style="position:relative;"><input type="text" style="" placeholder="请输入验证码" id="mycode" name="mycode">
            <a onclick="verifyCodeLink();" style="position:absolute;right:0;top:0;"><img src="${base}/rand.do" width='84' height='42' style="vertical-align:middle;" id="code_img"></a></li>
          	<li class="li03"></li>
     	 </ul>
          <ul class="ul">
            <li class="li01">短信验证码：</li>
            <!--<li class="li02" ><input type="text" style="width:150px;" placeholder="请输入验证码" name="codeReg" id="mycodePhone"><input id="yzm_button" onclick="sendAuthCode(this)" value="获取验证码" style="background:#f2f2f2;border:1px solid #dbdbdb;width:136px;height:40px;line-height:40px;text-align:center;display:inline-block;color:#666666;margin-left:10px;border-radius:5px;cursor:pointer;"></li>-->
            <li class="li02" style="position:relative;">
            	<input type="text"  value="" AUTOCOMPLETE="off" maxlength="6" style="width:254px; height:44px; line-height:44px; padding-left:4px; border:1px solid #e6e6e6; border-radius:5px;"  placeholder="请输入验证码" name="codeReg">
                <button type="button" class="getcode" id="getcode" onclick="sendAuthCode(this);">获取验证码</button>
            </li>
          </ul> 
          <ul class="ul">
            <li class="li01">登录密码：</li>
            <li class="li02"><input type="password" value="" AUTOCOMPLETE="off" placeholder="密码8-20个字符组成" name="user.password" id="password1"></li>
          	<li class="li03"></li>
          </ul>
          <ul class="ul">
            <li class="li01">确认密码：</li>
            <li class="li02"><input type="password"  placeholder="再次输入密码" name="password2" id="password2"></li>
            <li class="li03"></li>
          </ul>
          
          <#---->
          
         <ul class="ul" style="height:20px;">
            <li class="li01"></li>
            <li><input type="checkbox" id="agreeCheckbox" value="false" checked=""><span style="margin-left:4px;">我已阅读并同意<a href="${base}/regTreaty.do" target="_blank" style="color:#fd7c1a;">《服务协议》</a></span></li>
         </ul>
         <ul class="ul">
          	<li  class="li01"></li>
          	<li class="li02"><input type="button" onclick="$('#registerForm').submit()" value="同意协议并注册" style="vertical-align:middle;background:#fd7c1a;color:#fff;cursor:pointer;"></li>
        	<li class="li03"></li>
        </ul> 
       </form>
      </div>
    </div>
  </div>
</div><!-- login end -->
<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript">
$("#header_wdzh").addClass("hq");<#-- header.ftl 选中样式 -->

	$().ready(function() {
		var $code_c = $(".kaqu_huanyizhang");
		var $code_c2 = $(".kaqu_yiyi");
		var $code_img = $("#code_img");
		var $mycode=$("#mycode");
		var $registerForm = $("#registerForm");	
		// 表单验证
			$registerForm.validate({
				errorClass:"login_error",
				
				rules: {
					"user.phone":{
						required:true,
						phone:true,
						remote:{
							type:"POST",
					 		url:"${base}/user/valPhone.do"
						}
					},
					"user.password":{
						required: true,
						strongTxt:true,
						minlength:8,
						maxlength:16
					},
					"password2":{
						required: true,
						equalTo:"#password1"
					},
					"codeReg":{
						required:true
					},
					"agreeCheckbox":{
						required:true
					}
				},
				messages: {
					"user.phone":{
						required:"手机号不能为空",
						phone:"号码格式错误",
						remote:"手机号已存在"
					},
					"user.password": {
						required: "请填写密码",
						minlength: "密码必须大于等于8",
						maxlength: "密码必须小于等于16"
					},
					"password2": {
						required: "请再次输入密码",
						equalTo: "两次密码输入不一致"
					},
					"codeReg":{
						required:"请输入短信验证码"
					},
					"agreeCheckbox":{
						required:"请阅读会员协议"
					}
				},
				errorPlacement: function(error, element) {
				  	error.appendTo(element.parents(".li02").next(".li03").eq(0));
				},
				
				submitHandler: function(form) {
					
					if(!$("#agreeCheckbox")[0].checked){
						alertMessage('请阅读并同意《${Application ["qmd.setting.name"]}服务协议》');
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
		function verifyCodeLink(){
			$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		}
	
	
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
				    		var mycode = $("#mycode").val();
				    		if(phoneReg.length<=0){
				    			alertMessage("手机号码不能为空！");
				    			return false;
				    		}
				    		if(mycode.length<=0){
				    			alertMessage("请输入图形验证码！");
				    			return false;
				    		}
				    		var regPhone =/^(13[0-9]|14[0-9]|17[0-9]|18[0-9]|15[0-9]|18[8|9])\d{8}$/;
				    		if (!regPhone.test(phoneReg)){
				    			alertMessage("手机号格式不正确");
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
						        	   			alertMessage("短信发送失败,原因是:"+data.reason);
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
