<#escape x as x?html>
<!DOCTYPE html>
<html>
<head>
<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-通过手机号找回<#if p==1>安全<#else>登录</#if>密码</title>
<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.card.js"></script>
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.lSelect.js"></script>
	<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.cn.js"></script>
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
	<script type="text/javascript">
		
	$().ready(function() {
	
		var $lostpassPhoneForm = $("#lostpassPhoneForm");
		var $lostpassPhoneButton = $("#lostpassPhoneButton");
		var $phone=$("#phone");
		var $username=$("#username");
		var $mycode = $("#mycode");
		
		$lostpassPhoneButton.click( function() {
			$lostpassPhoneForm.submit();
		})
		
		 // 表单验证
			$lostpassPhoneForm.validate({
				rules: {
					
					"user.phone":{
						required: true,
						remote: "${base}/user/checkPowsPhone.do"
					},
					"mycode":{
						required:true
					}
				},
				messages: {
				
					"user.phone": {
						required: "手机号必填",
						remote: "对应手机号用户不存在"
					},
					"mycode":{
						required:"验证码必填"
					}
				},
				errorPlacement: function(error, element) {
				  error.appendTo(element.parent());
				},
				submitHandler: function(form) {
					form.submit();
				}
			});
			
	})
	
	
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
				    			alert("手机号码不能为空！");
				    			return false;
				    		}
				    		var regPhone =/^(13[0-9]|14[0-9]|17[0-9]|18[0-9]|15[0-9]|18[8|9])\d{8}$/;
				    		if (!regPhone.test(phoneReg)){
				    			alert("手机号格式不正确");
				    			return false;
				    	 	}
				    	 	
				    	 	$.ajax({ 
						        	   url: '${base}/sendCheckPhoneCode.do',
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
						        	   			alert("短信发送失败,原因是:"+data.reason);
						        	   			o.removeAttribute("disabled");         
										        o.value="获取验证码";
										        $("#yzm_button").removeClass("kaqu_w120");
												$("#yzm_button").addClass("phoneCode");
						        	   		}
						        	   }, 
						        	   error: function (XMLHttpRequest, textStatus, errorThrown) { 
					        	   			alert(errorThrown); 
						        	   } 
				        	  });
		    	}else{
				            o.value="重新发送(" + wait2 + ")";
				            wait2--;
				            setTimeout(function(){sendAuthCode(o)}, 1000);	
		    	}  
		}		 
	}
	
	</script>
	
	
  </head>
  
  <body>
  <#include "/content/common/header.ftl">
<!-- content -->

<div class="content">
  <div style="width:955px; margin:0 auto; padding-bottom:20px;">
    
    
    <div style="width:955px; float:left; background:#fff; clear:both;">
       
        
        <div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
			<h2>&nbsp;找回<#if p==1>安全<#else>登录</#if>密码&nbsp;</h2>
          </div>
          <br/><br/><br/>
          	 <form id="lostpassPhoneForm" method="post" action="findPwdByPhone.do"> 
			<input type = "hidden" name = "p" value="${p}"/>
          <table width="100%" cellpadding="0" cellspacing="0" class="checked_v4">
		     <tbody>
		       <tr>
		         <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">手机号码：</td>
		         <td style="color:#646464;font-family:'宋体';">
		           <input id="phone" name="user.phone" type="text" style="width:200px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
		             <input type = "button" id="yzm_button" onclick="sendAuthCode(this)" class="phoneCode" style="background:url(/static/img/d12.png) 0 0 no-repeat; width:99px; height:33px; line-height:33px; text-align:center; color:#ffc333; font-size:16px; display:inline-block; margin-left:2px;" value="免费获取" /> 
		         </td>
		       </tr>
		       <tr height="18"></tr>
		       <tr>
		         <td style="width:110px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">短信验证码：</td>
		         <td style="color:#646464;font-family:'宋体';"><input name="codeReg" id="mycode" type="text" style="width:250px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/></td>
		       </tr>
		       
		       <tr height="18"></tr>
		       <tr>
		         <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';"></td>
		         <td style="color:#646464;font-family:'宋体';">
		          <a href="javascript:void(0);" id="lostpassPhoneButton" onclick="#"><span style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" >找回密码</span></a>
		          
		         </td>
		       </tr>
		     </tbody>
		    </table>
          </form>
           <div style="height:23px;line-height:23px;text-align:center;color:#595757;font-family:'宋体';margin-top:100px; margin-bottom:30px; clear:both;"></div>  
            
            
            
            
        </div>
    </div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->


<#include "/content/common/footer.ftl">
</html>
</#escape>
