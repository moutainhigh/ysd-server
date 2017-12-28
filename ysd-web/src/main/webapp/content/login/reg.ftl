<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-注册</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/account_login.css?v=1.0.0" />
    <link rel="stylesheet" href="${base}/css/register.css">
</head>
<body>
     <!-- 头部 -->
     <#include "/content/common/header.ftl">
    <!--内容区域-->
    <div class="content">
        <!--登录模块-->
        <div class="mlogin">
            <div class="mlogin_hint">
                <span class="mlogin_hint_no_count">已有账号？</span>
                <span class="mlogin_hint_register"><a href="${base}/user/login.do">立即登录</a></span>
            </div>
            <#if friendUsername??>
	            <div class="mlogin_hint">
		               	 <span class="mlogin_hint_no_count">邀请人：${friendUsername}</span>
	            </div>
	        </#if>
            <div class="input_tips">
                <img src="${base}/img/tips.png" alt="">
                <span al="0"></span>
            </div>
            <form action="${base}/user/register.do"  class="mlogin_form" onsubmit="return checkAll();"  method="post" id="registerForm" AUTOCOMPLETE="off">
                <input type="hidden" name="r" id="Id0" value="${r}" /><#--推广编号-->
                <input type="text" al="1" name="user.phone" id="phone" placeholder="手机号码" class="phone_num" onBlur="hadPhone();">
                <input type="text" al="2" placeholder="验证码" id="mycode" name="mycode" class="identify_num">
                <a title='点击更换'><img src="${base}/rand.do" class="identify_img" id="code_img" onclick="verifyCodeLink();"></a>
                <input type="text" al="3" name="codeReg" placeholder="短信验证码" class="msg_identify" AUTOCOMPLETE="off" maxlength="6">
                <a href="javascript:void(0);" id="getcode" onclick="sendAuthCode(this);" class="msg_identify_img">获取验证码</a>
                <input type="password" al="4"  AUTOCOMPLETE="off" placeholder="请输入密码，长度8~16位，必须包含字母" class="pwd" name="user.password" id="password1">
                <input type="password" al="5" placeholder="请再次输入密码" class="check_pwd" name="password2" id="password2">
                <label for="agreeCheckbox">
                <input type="checkbox" al="6" id="agreeCheckbox" checked=""  class="protocol">
                <span class="read">我已阅读并同意</span></label><a href="${base}/regTreaty.do" target="_blank"><span class="pro">《服务协议》</span></a>
                <input type="submit" value="确认注册">
                <input type="hidden" name="tokenFlg" value="${tokenFlg}">
            </form>
        </div>
        <div class="clear"></div>
    </div>
        <!--底部-->
        
	<#include "/content/common/footer.ftl">
    </div>
    <script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
    <script src="${base}/js/common.js"></script>
    <script src="${base}/js/register.js"></script>
    <script type="text/javascript">
  	var notExistPhone = false; 
  	function hadPhone(){
		var tel = $("#phone").val();
		if(checkPhone()){
			$.ajax({
				type:"POST",
				url:"${base}/user/valPhone.do",
				data: {'user.phone':tel},
				success: function (data) { 
	        		   if(data=='false'){
	        			   alertInfo('手机号已存在',"1");
	        			   notExistPhone = false;
	        		   }else{
	        		       notExistPhone = true;
	        		   }
	        	}
			});
		}
   	}
   	//验证码
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
			        wait2 = 60;
		} else {
		    	if(wait2==60){
				    	 	var phoneReg = $("#phone").val();
				    		var mycode = $("#mycode").val();
				    	 	if(!checkPhone()||!checkCode()){
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
									   },
						        	   success: function (data) { 
						        		   if(data.result==0){
					 		   		            o.innerHTML="重新发送(" + wait2 + ")";
					 		   		            wait2--;
					 		   		            setTimeout(function(){sendAuthCode(o)}, 1000);
					 		   		         
						        	  		}
						        		   else if(data.result==1){
						        	   			alertInfo("短信发送失败,原因是:"+data.reason);
						        	   			o.removeAttribute("disabled");         
										        o.innerHTML="获取验证码";
										        $("#getcode").removeClass("getcode_disable");
			        							$("#getcode").addClass("getcode");
			        							verifyCodeLink();
						        	   		}
						        	   }, 
						        	   error: function (XMLHttpRequest, textStatus, errorThrown) { 
					        	   			alertInfo(errorThrown); 
					        	   			verifyCodeLink();
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
</body>
</html>