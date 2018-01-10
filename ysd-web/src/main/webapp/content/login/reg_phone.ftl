<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-注册</title>
<#include "/content/common/meta.ftl">
</head>
<body>
 <!-- header -->
 <#include "/content/common/header.ftl">
<div class="register">
  <div style="width:1190px;height:730px;margin:30px auto;background:#fff url(/img/zhuce.png) no-repeat 645px 170px;border:1px solid #dcdcdc;border-radius:5px;">
    <div style="border-bottom:1px solid #dcdcdc;height:68px;line-height:68px;color:#fd7c1a;font-size:20px;text-align:center;">
      注册
      <span style="float:right;color:#666;font-size:14px;margin-right:40px;">已有账户马上<a href="" style="color:#fd7c1a;">登录</a></span>
    </div>
    <div>
          <ul class="step" style="width:640px;margin-top:35px;">
            <li>
              <p class="circle">1</p>
              <p class="info" style="left:0px;">注册</p>
            </li>
            <li class="line"></li>
            <li class="cur">
              <p class="circle">2</p>
              <p class="info">手机验证</p>
            </li>
            <li class="line"></li>
            <li>
              <p class="circle">3</p>
              <p class="info" style="left:0px;">完成</p>
            </li>
          </ul>
      </div>
    <div style="width:480px;margin:30px 0 0 100px;">
      <div class="form" style="">
      	<form id="checkPhoneForm" method="post" action="${base}/checkPhone.do"> 
          <ul class="ul">
            <li class="li01">手机号：</li>
            <li class="li02" style="font-size:22px;">${user.phone}&nbsp;&nbsp;<!--<a href="" style="font-size:14px;color:#006dc1;">更换</a>--></li>
            <input id="phone" name="user.phone" value="${user.phone}" type="hidden">
          </ul>
          <ul style="display:none;">
            <li class="li01"></li>
            <li class="li02" style="color:#fd7c1a;">用户名不正确</li>
          </ul>
          <ul class="ul">
            <li class="li01">验证码：</li>
            <li class="li02" ><input type="text" style="width:150px;" placeholder="请输入验证码" name="codeReg" id="mycodePhone"><input id="yzm_button" onclick="sendAuthCode(this)" value="获取验证码" style="background:#f2f2f2;border:1px solid #dbdbdb;width:136px;height:40px;line-height:40px;text-align:center;display:inline-block;color:#666666;margin-left:10px;border-radius:5px;cursor:pointer;"></li>
          </ul> 
          <ul style="display:none;">
            <li class="li01"></li>
            <li class="li02" style="color:#fd7c1a;">验证码不正确</li>
          </ul>
          <ul class="ul">
          <li  class="li01"></li>
          <li class="li02"><input type="button" onclick="subForm()" value="下一步" style="vertical-align:middle;background:#fd7c1a;color:#fff;cursor:pointer;"></li>
        </ul> 
       </form>
      </div>
    </div>
  </div>
</div><!-- login end -->
<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
$("#header_wdzh").addClass("hq");<#-- header.ftl 选中样式 -->

	//发送验证码
	var wait2=60;
	function sendAuthCode(o){
		if (wait2 ==0) {
			        o.removeAttribute("disabled");         
			        o.value="获取验证码";
			        $("#yzm_button").removeClass("kaqu_w120");
					$("#yzm_button").addClass("phoneCode");
			        wait2 = 60;
		} else {
		    	if(wait2==60){
				    		var phoneReg = $("#phone").val();
				    		if(phoneReg.length<=0){
				    			alertMessage("手机号码不能为空！");
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
						        	   data: {'phoneReg':phoneReg}, 
						        	   beforeSend: function() {
											  o.value="短信发送中...";
											  o.setAttribute("disabled", true);
							    			  $("#yzm_button").removeClass("phoneCode");
											  $("#yzm_button").addClass("kaqu_w120");
									   },
						        	   success: function (data) { 
						        		   if(data.result==0){
						        	  			  // alert("短信发送成功");
					 		   		            o.value="重新发送(" + wait2 + ")";
					 		   		            wait2--;
					 		   		            setTimeout(function(){sendAuthCode(o)}, 1000);
					 		   		         
						        	  		}
						        		   else if(data.result==1){
						        	   			alertMessage("短信发送失败,原因是:"+data.reason);
						        	   			o.removeAttribute("disabled");         
										        o.value="获取验证码";
										        $("#yzm_button").removeClass("kaqu_w120");
												$("#yzm_button").addClass("phoneCode");
						        	   		}
						        	   }, 
						        	   error: function (XMLHttpRequest, textStatus, errorThrown) { 
					        	   			alertMessage(errorThrown); 
						        	   } 
				        	  });
		    	}else{
				            o.value="重新发送(" + wait2 + ")";
				            wait2--;
				            setTimeout(function(){sendAuthCode(o)}, 1000);	
		    	}  
		}		 
	}
	
	function subForm(){
			var phone_cc=$("#phone").val();
			var mycodePhone_cc=$("#mycodePhone").val();
			if(phone_cc==""||phone_cc==null){
				alertMessage("手机号不能为空");
				return false;
			}
			if(mycodePhone_cc==""||mycodePhone_cc==null){
				alertMessage("验证码不能为空");
				return false;
			}
			$("#checkPhoneForm").submit();
	}
		
</script>
</html>
