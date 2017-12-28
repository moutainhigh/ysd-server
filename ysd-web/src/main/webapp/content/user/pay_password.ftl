<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-密码修改</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/password_modify.css" />
</head>
<body id="bg">
    <!-- 头部 -->
    <#include "/content/common/header.ftl">

    <!--内容区域-->
    <div id="main">
        <!--左边导航栏-->
       <#include "/content/common/user_center_left.ftl">
        <div class="content fr">
            <div class="options">
                <div><a href="${base}/member/toPassword.do">登录密码修改</a></div>
                <div><a href="${base}/member/toPayPassword.do">交易密码修改</a></div>
            </div>
            <!--交易密码修改-->
            <div class="deal">
                
                <form action="${base}/member/passwordUpdate.do" method="post" AUTOCOMPLETE="off" class="already_bind">
         		<input type="hidden" name="p" value="2"/>
                    <table>
                        <tr>
                            <td>已绑定手机号：</td>
                            <td colspan="2" class="tel" id='phone'>${user.phone}</td>
                        </tr>
                        <tr class="message_identify">
                            <td>短信验证码：</td>
                            <td><input type="text" title="message_identify" name="code"></td>
                            <td><span onclick='sendAuthCode(this)'>获取验证码</span></td>
                        </tr>
                        <tr class="new_pwd">
                            <td>新交易密码：</td>
                            <td><input type="password" name="newPayPassword" value="" title="new_pwd" placeholder="密码由6~16位字母、数字及符号组成" ></td>
                        </tr>
                        <tr>
                            <td colspan="3"><input type="button" value="确认" class='newPayButton'></td>
                        </tr>
                    </table>
                </form>
                <div class="tips">
                    交易密码设置提示：
                    <div>1、交易密码用于您在乐商贷使用账户余额进行投资或提现时输入</div>
                    <div>2、为确保您账户资金安全，交易密码不能和支付密码一致</div>
                </div>
            </div>
        </div>
        <div class="clear"></div>
    </div>

    <!--底部-->
    <#include "/content/common/footer-center.ftl">
</body>
<script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${base}/js/modify.js"></script>
<script src="${base}/js/common.js"></script>
</html>

<script>
	$("#header_wdzh").addClass("current");<#-- header.ftl 选中样式 -->
	$("#header_gywm").find('a').css('border',0);
	$(".center_left_zhgl").addClass("current");
	$(".options").find('div').eq(1).addClass('current');

	/**
	$('.left_kuang li').last().css('border-bottom','0');
	$('.user_list_qh li').click(function(){
		var i=$(this).index();
		$('.user_list_qh li').removeClass('tzlist_user');
		$(this).addClass('tzlist_user');
		$('.user_div_qh').css('display','none');
		$('.user_div_qh').eq(i).css('display','block');
	});
	*/
	function alertMessage(text){
		alert(text);
	}
	$('.newPayButton').click(function(){
		var newPass=$('input[name=newPayPassword]').val();
		if(newPass.length<6){
			alertMessage("交易密码不得少于6位");
			return false;
		}
		$(this).parents('form').submit();
	});
	//发送验证码
	var wait2=60;
	function sendAuthCode(o){
		if (wait2 ==0) {
			        o.removeAttribute("disabled");         
			        o.innerHTML="获取验证码";
			        $(o).removeClass("getcode_disable");
			        $("#getcode").addClass("getcode");
			        wait2 = 60;
			        $(o).attr("onclick","sendAuthCode(this);");
		} else {
		    	if(wait2==60){
		    				$(o).removeAttr("onclick");
				    		var phoneReg = $("#phone").html();
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
						        	   url: '${base}/sendMyPhoneCode.do',
						        	   async:false,
						        	   type: 'post', 
						        	   dataType:'json', 
						        	   data: {'phoneReg':phoneReg}, 
						        	   beforeSend: function() {
											  o.innerHTML="短信发送中...";
											  o.setAttribute("disabled", true);
											  $("#getcode").removeClass("getcode");
			        						  $(o).addClass("getcode_disable");
									   },
						        	   success: function (data) { 
						        		   if(data.result==0){
					 		   		            o.innerHTML= wait2 + "s";
					 		   		            wait2--;
					 		   		            setTimeout(function(){sendAuthCode(o)}, 1000);
					 		   		         
						        	  		}
						        		   else if(data.result==1){
						        	   			alertMessage("短信发送失败,原因是:"+data.reason);
						        	   			o.removeAttribute("disabled");         
										        o.innerHTML="获取验证码";
										        $(o).removeClass("getcode_disable");
			        							$("#getcode").addClass("getcode");
						        	   		}
						        	   }, 
						        	   error: function (XMLHttpRequest, textStatus, errorThrown) { 
					        	   			alertMessage(errorThrown); 
						        	   } 
				        	  });
		    	}else{
				            o.innerHTML= wait2 + "s";
				            wait2--;
				            setTimeout(function(){sendAuthCode(o)}, 1000);	
		    	}  
		}		 
	}
</script>  
 </body>
</html>
