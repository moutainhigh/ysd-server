<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-找回密码</title>
<#include "/content/common/meta.ftl">
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">
<div class="find">
  <div style="width:1200px;height:600px; margin:30px auto 90px;background:#fff;">
      <div class="top">
          <p><span>找回密码</span>若您无法使用上述方法找回，请联系客服400-057-7820或重新注册</p>
      </div>
      <div>
          <ul class="step">
            <li>
              <p class="circle">1</p>
              <p class="info" style="left:-20px;">填写账户名</p>
            </li>
            <li class="line"></li>
            <li class="cur">
              <p class="circle">2</p>
              <p class="info">验证身份</p>
            </li>
            <li class="line"></li>
            <li>
              <p class="circle">3</p>
              <p class="info">重置密码</p>
            </li>
            <li class="line"></li>
            <li>
              <p class="circle">4</p>
              <p class="info" style="left:0px;">完成</p>
            </li>
          </ul>
      </div>
      <div style=" width:455px; margin:90px auto 150px;">
          <table width="100%" cellpadding="0" cellspacing="0" border="0" style="color:#383838; font-size:16px;">
            <tbody>
            	<form action="${base}/user/findPsw2.do" method="post">
              <tr height="50">
               <td align="right" width="125"><sup style=" color:#fd7c1a; position:relative; top:4px;right:2px;">*</sup>手机号</td>
               <td align="left" style="padding-left:20px;padding-top:3px; color:#383838;">
                  ${user.phone}
                  <input type="hidden" name="user.phone" value="${user.phone}" id="phone">
               </td>
       	 </tr>
            <tr height="50">
               <td align="right" width="125" style="padding-top:20px;"><sup style=" color:#fd7c1a; position:relative; top:4px;right:2px;">*</sup>验证码</td>
               <td align="left" style="padding-left:20px;padding-top:24px;">
                  <input type="text" id="mycodePhone" placeholder="请输入验证码" style="width:168px; height:40px; line-height:40px; border:1px solid #dbdbdb; padding-left:10px;border-top-left-radius:5px; border-bottom-left-radius:5px;float:left;" name="mycode"><button onclick="verifyCode();" value="" style="width:90px; height:42px; line-height:42px; color:#383838; border-top-right-radius:5px;border-bottom-right-radius:5px; border:1px solid #dbdbdb; background:#fff;margin-left:-1px;float:left;" type="button"><img src="${base}/rand.do" width='90' height='42' alt="" id="code_img"/></button>
                  <a href="#" onclick="verifyCode();" style="margin: 6px 0 0 10px;  display: inline-block;"><img src="/img/reload.png" width='23' height='27' alt="" /></a>
               </td>
            </tr>
            <tr height="50">
               <td align="right" width="125" style="padding-top:20px;"><sup style=" color:#fd7c1a; position:relative; top:4px;right:2px;">*</sup>短信验证码</td>
               <td align="left" style="padding-left:20px;padding-top:24px;position: relative;">
            	
         
               <input type="text" style="width:254px; height:44px; line-height:44px; padding-left:4px; border:1px solid #e6e6e6; border-radius:5px;"  placeholder="请输入验证码" name="codeReg">
                <button type="button" class="getcode" id="getcode" onclick="sendAuthCode(this);" style="top:24px;">获取验证码</button>
               
               </td>
            </tr>
            <tr height="60">
               <td align="right" width="125"></td>
               <td align="left" style="padding-left:20px;padding-top:24px;width: 294px;">
                  <input type="submit" value="下一步" style="width:108px;height:36px;border-radius:5px;line-height:36px;background-color:#fd7c1a;color:#fff;display:block;text-align:center;cursor:pointer;" />
               </td>
            </tr>
          </tbody></table>
        </div>
  </div>
</div><!-- 找回密码 end -->
<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">

	function verifyCode(){
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
				    		var mycodePhone = $("#mycodePhone").val();
				    		if(phoneReg.length<=0){
				    			alertMessage("手机号码不能为空！");
				    			return false;
				    		}
				    		
				    		if(mycodePhone.length<=0){
				    			alertMessage("请输入图形验证码！");
				    			return false;
				    		}
				    		var regPhone =/^(13[0-9]|17[0-9]|18[0-9]|15[0-9]|18[8|9])\d{8}$/;
				    		if (!regPhone.test(phoneReg)){
				    			alertMessage("手机号格式不正确");
				    			return false;
				    	 	}
				    	 	
				    	 	$.ajax({ 
						        	   url: '${base}/sendCheckPhoneCode.do',
						        	   async:false,
						        	   type: 'post', 
						        	   dataType:'json', 
						        	   data: {'phoneReg':phoneReg,'mycode':mycodePhone}, 
						        	   beforeSend: function() {
											  o.innerHTML="短信发送中...";
											  o.setAttribute("disabled", true);
											  $("#getcode").removeClass("getcode");
			        						  $("#getcode").addClass("getcode_disable");
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
