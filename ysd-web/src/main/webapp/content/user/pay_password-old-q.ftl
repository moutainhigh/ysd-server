<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-我的账户</title>
<#include "/content/common/meta.ftl">
</head>
<body>
<!-- header -->
<#include "/content/common/header.ftl">


<#include "/content/common/user_center_header.ftl">

<div class="content">
  <div style="width:1200px; margin:0 auto; padding-bottom:60px; margin-top:20px;">
    <!--left-->
	<#include "/content/common/user_center_left.ftl">
    <!--right-->
    <div style="width:942px; float:right; ">
      <div style="border:1px solid #e6e6e6;background:#fff;">
        <div style="  width:100%;background:#eeeeee; height:51px; border-bottom:1px solid #e6e6e6;">
            <a class="hr hre" href="#">交易密码设置</a>
            <a class="hr" href="${base}/member/toPassword.do">登录密码设置</a>
        </div>
        <div style=" padding-top:10px; padding-left:50px; padding-bottom:50px; clear:both;">
          <div class="k_right_h1"><img src="/img/tishi.png"><p>温馨提示:</p></div>
          <div style="color:#666666; font-size:14px; padding-left:70px; line-height:25px; margin-bottom:30px;">1.交易密码用于您在乐商贷使用账户余额进行投资或提现时输入<br />
              2.为确保您账户资金安全，交易密码不能和登录密码一致
          </div>   
         	<form action="${base}/member/passwordUpdate.do" method="post" AUTOCOMPLETE="off">
         		<input type="hidden" name="p" value="2"/>
         <table cellspacing="0" cellpadding="0" border="0" width="100%">
              <tbody><tr>
                <td style="width:115px; padding-right:20px; text-align:right;color:#666; font-size:14px;">已绑定手机号</td>
                <td><span style="color:#006dc1; font-size:20px;">${user.phone}<input type="hidden" name="user.phone" value="${user.phone}" id="phone"/></span></td>
              </tr>
              <tr height="20"><td></td><td></td></tr>
              <tr>
                <td style="width:115px; padding-right:20px; text-align:right;color:#666; font-size:14px;">验证码</td>
                <td>
                  <div style="position:relative; width:300px;"><input type="text" maxlength="6" AUTOCOMPLETE="off" value="" style="width:294px; height:44px; line-height:44px; padding-left:4px; border:1px solid #e6e6e6; border-radius:5px;" name="code">
                  <#--
                  <span style="width:120px; height:44px; border-radius:0 5px 5px 0; cursor:pointer; position:absolute; right:0; top:1px; color:#fff; text-align:center; font-size:14px; background:#006dc1; line-height:44px;" id="getcode" onclick="sendAuthCode(this);">获取短信验证码</span>
                  -->
             	 <button type="button" class="getcode" id="getcode" onclick="sendAuthCode(this);">获取验证码</button>
                </div>
                </td>
              </tr>
              <tr height="20"><td></td><td></td></tr>
              <tr>
                <td style="width:115px; padding-right:20px; text-align:right;color:#666; font-size:14px;"><span style="position:relative; top:-7px;">新交易密码</span></td>
                <td>
                  <input type="password" AUTOCOMPLETE="off" value=""  style="width:294px; height:44px; line-height:44px; padding-left:4px; border:1px solid #e6e6e6; border-radius:5px; margin-bottom:5px;" name="newPayPassword">
                  <div style="color:#b2b2b2; font-size:14px;">8-20个字符，建议使用字母加数字或符号</div>
                </td>
              </tr>
              <tr height="60">
                <td></td> 
                <td>
                    <input class="btn_t1" style="display:inline-block;" value="确认" type="submit"/>
                </td>
              </tr>
            </tbody>
           </table>
        </form>
          </div>      
        </div>
    </div>
    <div style="width:100%;clear:both; height:1px;"></div>
  </div>
</div><!-- content end -->

<#include "/content/common/footer.ftl">
<script>
	$("#header_wdzh").addClass("hq");<#-- header.ftl 选中样式 -->

	$('.left_kuang li').last().css('border-bottom','0');
	$('.user_list_qh li').click(function(){
		var i=$(this).index();
		$('.user_list_qh li').removeClass('tzlist_user');
		$(this).addClass('tzlist_user');
		$('.user_div_qh').css('display','none');
		$('.user_div_qh').eq(i).css('display','block');
	});
	
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
				    		if(phoneReg.length<=0){
				    			alertMessage("手机号码不能为空！");
				    			return false;
				    		}
				    		var regPhone =/^(13[0-9]|17[0-9]|18[0-9]|15[0-9]|18[8|9])\d{8}$/;
				    		if (!regPhone.test(phoneReg)){
				    			alertMessage("手机号格式不正确");
				    			return false;
				    	 	}
				    	 	
				    	 	$.ajax({ 
						        	   url: '${base}/sendMyPhoneCode.do',
						        	   async:false,
						        	   type: 'post', 
						        	   dataType:'json', 
						        	   data: {'phoneReg':phoneReg}, 
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
 </body>
</html>
