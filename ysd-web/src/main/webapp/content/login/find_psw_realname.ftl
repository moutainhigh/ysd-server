<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-密码重置</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/reset_pwd.css" />
</head>
<body>
   <!-- 头部 -->
   <#include "/content/common/header.ftl">
    <!-- 主内容区 -->
    <div class="content">
        <div class="hint">
            <span>找回密码&nbsp;&nbsp;&nbsp;</span>
            若您无法找回密码，请联系客服400-057-7820
        </div>
        <div class="progress">
            <ul>
                <li class="current progress_bz">1</li>
                <li></li>
                <li class="current progress_bz">2</li>
                <li></li>
                <li class="progress_bz">3</li>
                <li></li>
                <li class="progress_bz">4</li>
            </ul>
            <div class="progress_name">
                <span class="one">确认账号</span>
                <span class="two">安全验证</span>
                <span class="three">重置密码</span>
                <span class="four">完成</span>
            </div>
        </div>
         <!--验证身份-->
        <form action="${base}/user/findPsw2.do" method="post" class="identify" >
            <div class="input_tips">
                <img src="../img/tips.png" alt="">
                <span>手机号格式错误！</span>
            </div>
            <table>
                <tr>
                    <td>手机号：</td>
                    <td colspan="2" style="color: #ef3e44;">
                     ${user.phone}
						<input type="hidden" name="user.phone" value="${user.phone}" id="phone">
	                </td>
                </tr>
                <tr>
                    <td>验证码：</td>
                    <td><input type="text" id="mycodePhone" placeholder="请输入验证码" title="" class="identify_num" name="mycode" style="width: 162px;"></td>
                    <td><img onclick="verifyCode();" style="height: 38px;" id="code_img" src="${base}/rand.do" class="identify_img"></td>
                </tr>
                <tr>
                    <td>短信验证码：</td>
                    <td><input type="text" class="identify_num" style="width: 162px;" title=""  placeholder="请输入短信验证码" name="codeReg"></td>
                    <td class="get_inum"> <button type="button" class="getcode" id="getcode" onclick="sendAuthCode(this);" >获取验证码</button>
                </tr>
                <tr>
                    <td colspan="3"><input type="submit" value="下一步"/></td>
                </tr>
            </table>

        </form>
    </div>
 	<!-- 尾部 -->
	<#include "/content/common/footer.ftl">
    <script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
    <script src="${base}/js/common.js"></script>
    <script src="${base}/js/reset_pwd.js"></script>
    <script type="text/javascript">
		//我的账号
		$(function(){
			$('#header_wdzh').addClass('current');
			$('#header_gywm a').css('border',0);
		});
		function verifyCode(){
			$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		}
		function alertMessage(msg){
			$('.input_tips').find('span').html(msg);
	        $('.input_tips').css('visibility','visible');
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
					    			alertMessage("请输入验证码！");
					    			return false;
					    		}
					    		var regPhone =/^(13[0-9]|14[0-9]|17[0-9]|18[0-9]|15[0-9]|18[8|9])\d{8}$/;
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
				        							verifyCode();
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
</body>
</html>