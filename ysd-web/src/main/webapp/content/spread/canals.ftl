<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-渠道推广</title>
    <link rel="stylesheet" href="${base}/css/common.css">
    <link rel="stylesheet" href="${base}/css/canals.css">
</head>

<body>
<div class="header">
    <div class="header_wrap">
        <img src="${base}/img/new_jcb.png" alt="">
        <div class="special"><span class="left">温州乐商投资有限公司旗下投资平台</span><span class="right"></span></div>
    </div>
</div>
<div class="activity">
    <div class="activity_wrap">
        <div class="register">
            <form id="registerForm_1" >
                <table>
                    <tbody>
                    <tr class="invest_person">
                        <td colspan="2"><span>注册有礼</span></td>
                    </tr>
                     <tr class="error_hint">
                        <td colspan="2" id="_alert_1"></td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="text" id="place_username_1" class="phone"  name="username" placeholder="请输入手机号码"></td>
                    </tr>
                    <tr class="message">
                        <td>
                        	<input type="text" id="smsCode_1" name="smsCode" placeholder="请输入短信验证码">
                        	<input type="text" name="code" value="000" style="display:none" ></input>
                        </td>
                        <td><input type="button" id="getcode_1" onclick="sendAuthCode('1');" value="获取验证码"></input></td>
                    </tr>
                    <tr class="pwd">
                        <td colspan="2"><input type="password" id="place_pwd_1" name="pwd" placeholder="请输入密码，长度8~16位，必须包含字母"></td>
                    </tr>
                    <tr class="pwd">
                        <td colspan="2"><input type="password" id="place_pwd2_1" name="pwd2" placeholder="请再次输入密码"></td>
                    </tr>
                    <tr class="button">
                        <td colspan="2"><input type="button"   onclick="submitForm('1');" value="注册领1088元红包"></td>
                    </tr>
                    <tr class="pic">
                        <td  colspan="2"><input type="checkbox" checked="" id="agreeCheckbox_1">我已阅读并同意 <a href="${base}/regTreaty.do" target="_blank"><span>《服务协议》</span></a>
                        	<br/>
                        	已有账号?&nbsp;&nbsp;<a href='${base}/user/login.do'><span style='color:#ff0000'>立即登录</span></a>
                        </td>
                    </tr>
                    </tbody></table>
            </form>
        </div>
    </div>
</div>
<div class="security">
    <div class="security_wrap">
        <div class="box guoyou">
            <span class="top">浙江中际通用航空有限公司</span><br>
            <span class="bottom">互联网+</span>
        </div>
        <div class="box hefa">
            <span class="top">合法权益保障</span><br>
            <span class="bottom">律师事务所全程法律监督</span>
        </div>
        <div class="box fengkong">
            <span class="top">风控审信保障</span><br>
            <span class="bottom">六重风控体系项目安全透明</span>
        </div>
    </div>
</div>
<div class="content">
    <a javascript: void(0)" onclick="my_alert()"><img src="${base}/img/new_580.png" class="new_5"></a>
    <div class="content_wrap">
        <img src="${base}/img/new_person.png" class="new_p">
        <div class="new_person">
            <div class="top">
                <div class="name">专属红包</div>
                <div class="redbag"> 注册即可领取 <span>1088元</span></div>
                <a href="javascript: void(0)" onclick="my_alert()">立即领取</a>
            </div>
            <div class="bottom">
                <div class="top">
                    <span class="temp1">新手专享</span>
                    <span class="temp2">新用户专享</span>
                </div>
                <div class="detail">
                    <div class="name first">预期年化收益 <span class="rate">14.4%</span></div>
                    <div class="name second">投资期限 <span class="date">7天</span></div>
                    <div class="name">起投金额 <span class="date">100元</span></div>
                    <a href="javascript: void(0)" onclick="my_alert()">立即投标</a>
                </div>
            </div>
        </div>

        <img src="${base}/img/new_product.png" class="new_m">
        <div class="more">
            <div class="jingping">
                <div class="top">
                    <span class="temp1">精品理财</span>
                </div>
                <div class="name first">预期年化收益 <span class="rate">7.56%</span></div>
                <div class="name second">投资期限 <span class="date">30天</span></div>
                <div class="name">起投金额 <span class="date">100元</span></div>
                <a href="javascript: void(0)" onclick="my_alert()">立即投标</a>
            </div>

            <div class="duanqi">
                <div class="top">
                    <span class="temp1">短期理财</span>
                </div>
                <div class="name first">预期年化收益 <span class="rate">8.28%</span></div>
                <div class="name second">投资期限 <span class="date">60天</span></div>
                <div class="name">起投金额 <span class="date">100元</span></div>
                <a href="javascript: void(0)" onclick="my_alert()">立即投标</a>
            </div>
            <div class="zhongqi">
                <div class="top">
                    <span class="temp1">中期理财</span>
                </div>
                <div class="name first">预期年化收益 <span class="rate">8.64%</span></div>
                <div class="name second">投资期限 <span class="date">90天</span></div>
                <div class="name">起投金额 <span class="date">100元</span></div>
                <a href="javascript: void(0)" onclick="my_alert()">立即投标</a>
            </div>
            <div class="changqi">
                <div class="top">
                    <span class="temp1">长期理财</span>
                </div>
                <div class="name first right_less">预期年化收益 <span class="rate">10.08%</span></div>
                <div class="name second panfeng">投资期限 <span class="date">180天</span></div>
                <div class="name">起投金额 <span class="date">100元</span></div>
                <a href="javascript: void(0)" onclick="my_alert()">立即投标</a>
            </div>
        </div>

        <div class="honner">
            <div class="name">荣誉资质</div>
            <img src="${base}/img/new_honner1.png">
            <img src="${base}/img/new_honner2.png">
            <img src="${base}/img/new_honner3.png">
            <img src="${base}/img/new_honner4.png">
            <img src="${base}/img/new_honner5.png">
            <img src="${base}/img/new_honner6.png">
        </div>
    </div>
</div>

<div class="alert" style="display: none">
    <div class="alert_wrap">
        <div class="shadow"></div>
        <div class="alert_content">
            <div class="close fr"></div>
            <img src="${base}/img/new_alert.png">
            <div class="register">
                <form id="registerForm_2">
                    <table>
                        <tbody><tr class="invest_person">
                            <td colspan="2"><span>注册有礼</span></td>
                        </tr>
                        <tr class="error_hint">
	                        <td colspan="2" id="_alert_2" style="color:red;font-size:14px"></td>
	                    </tr>
                        <tr>
                            <td colspan="2"><input type="text" id="place_username_2" class="phone" name="username" placeholder="请输入手机号码"></td>
                        </tr>
                        <tr class="message">
                            <td>
	                            <input type="text" id="smsCode_2" name="smsCode" placeholder="请输入短信验证码">
	                        	<input type="text" name="code" value="000" style="display:none" ></input>
                            </td>
                            <td><input id="getcode_2" type="button" onclick="sendAuthCode('2');" value="获取验证码"></input></td>
                        </tr>
                        <tr class="pwd">
                            <td colspan="2"><input type="password" id="place_pwd_2" name="pwd" placeholder="请输入密码，长度8~16位，必须包含字母"></td>
                        </tr>
                        <tr class="pwd">
                            <td colspan="2"><input type="password" id="place_pwd2_2" name="pwd2" placeholder="请再次输入密码"></td>
                        </tr>
                        <tr class="button">
                            <td colspan="2"><input type="button"   onclick="submitForm('2');" value="注册领1088元红包"></td>
                        </tr>
                        <tr class="pic">
                            <td  colspan="2"><input type="checkbox" checked="" id="agreeCheckbox_2">我已阅读并同意 <a href="${base}/regTreaty.do" target="_blank"><span>《服务协议》</span></a></td>
                        </tr>
                        </tbody></table>
                </form>
                <div class="if">已有账号？<a href="${base}/user/login.do" style="text-decoration: none;">立即登录</a></div>
            </div>
        </div>
    </div>
</div>
<!--
<div class="partner">
    <div class="partner_wrap">
        <div class="name">合作伙伴</div>
      <div class="pat">
      		<#list huobanpicList as item>
	 			<a href="<@imageUrlDecode imgurl="${item.img}"; imgurl>${imgurl}</@imageUrlDecode>"><img src="<@imageUrlDecode imgurl="${item.img}"; imgurl>${imgurl}</@imageUrlDecode>" /></a>
			</#list>
      </div>
       		 
    </div>
</div>
-->

<div class="wrap_1">
  	  <div class="wrapArea">
  	  	 <!-- 合作伙伴 -->
	   	  <div class="partner">
	   	  	 <h3>合作伙伴</h3>
	   	  	 <ul class="partnerList">
	   	  	 	<#list huobanpicList as item>
	   	  	 		<li><a href="<@imageUrlDecode imgurl="${item.img}"; imgurl>${imgurl}</@imageUrlDecode>" target="_blank"><img src="<@imageUrlDecode imgurl="${item.img}"; imgurl>${imgurl}</@imageUrlDecode>" /></a></li>
				</#list> 
	   	  	 </ul>
	   	  </div>
	   	  
	   	  <!-- 乐商贷手机客户端下载 -->
	   	  <div class="downlaod">
	   	  	 <h3>乐商贷手机客户端下载</h3>
	   	  	 <#--<img src="${base}/img/app_download11.png" alt="app二维码" />-->

	   	  </div>
	   	  <div class="clear"></div>
  	  </div>
</div>


   <!-- 尾部 -->
   <#include "/content/common/footer.ftl">
<script src="${base}/js/jquery-1.11.3.min.js"></script>
<script src="${base}/js/common.js"></script>
<script src="${base}/js/canals.js"></script>
<script type="text/javascript">
//发送短信验证码
var sendSMS = false;
var wait2=60;
function sendAuthCode(num){
	if (wait2 ==0) {
				$('#getcode_'+num).val("获取验证码");
				 $('#getcode_'+num).removeAttr("disabled");
		        wait2 = 60;
		        $('#getcode_'+num).attr("onclick","sendAuthCode("+num+");");
	} else {
	    	if(wait2==60){
			    		var phoneReg = $("#place_username_"+num).val();
			    		if(phoneReg.length<=0){
		    	    		$("#_alert_"+num).html('手机号不能为空');
			    			return false;
			    		}
			    		var regPhone =/^(13[0-9]|14[0-9]|17[0-9]|18[0-9]|15[0-9]|18[8|9]|14[0-9])\d{8}$/;
			    		if (!regPhone.test(phoneReg)){
							$("#_alert_"+num).html('手机格式不正确');
			    			return false;
			    	 	}
			    	 	$("#_alert_"+num).html('');
			    	 	$.ajax({
				        	   url: '${base}/sendPhoneCodeNew.do',
				        	   async:false,
				        	   type: 'post', 
				        	   dataType:'json', 
				        	   data: {'phoneReg':phoneReg}, 
				        	   beforeSend: function() {
				        	   		  $('#getcode_'+num).val("短信发送中...");
		        					  $('#getcode_'+num).attr('disabled',"true");
							   },
				        	   success: function (data) { 
				        		   if(data.result=="0"){
				        		   		//发送过手机验证码
				        		   		sendSMS = true;
				        		   		$('#getcode_'+num).attr('onclick','');
			 		   		            $('#getcode_'+num).val("重新发送(" + wait2 + ")");
			 		   		            wait2--;
			 		   		            setTimeout(function(){sendAuthCode(num)}, 1000);
				        	  		}else{
				        	  			wait2=0;	
				        	  			$("#_alert_"+num).html(data.reason);
								       	$('#getcode_'+num).val("获取验证码");
								       	$('#getcode_'+num).removeAttr("disabled");
				        	   		}
				        	   }, 
				        	   error: function (XMLHttpRequest, textStatus, errorThrown) { 
			        	   			$("#_alert_"+num).html(errorThrown);
			    					return false;
				        	   } 
		        	  });
	    	}else{
	    		$('#getcode_'+num).val("重新发送(" + wait2 + ")");
	            wait2--;
	            setTimeout(function(){sendAuthCode(num)}, 1000);	
	    	}  
	}		 
}
//注册
var goSubmit = true;
function submitForm(num){
	if(goSubmit){
		goSubmit = false;
	}else{
		return false;
	}
	
	//手机号
	var phoneReg = $("#place_username_"+num).val();
	if(phoneReg.length<=0){
		$("#_alert_"+num).html('手机号不能为空');
		goSubmit = true;
		return false;
	}
	var regPhone =/^(13[0-9]|14[0-9]|17[0-9]|18[0-9]|15[0-9]|18[8|9]|14[0-9])\d{8}$/;
	if (!regPhone.test(phoneReg)){
		$("#_alert_"+num).html('手机格式不正确');
		goSubmit = true;
		return false;
 	}
	//短信验证码
	if(!sendSMS){
		$("#_alert_"+num).html('获取验证码');
		goSubmit = true;
		return false;
	}
	if(wait2<=0||wait2>=60){
		$("#_alert_"+num).html('获取验证码');
		goSubmit = true;
		return false;
	}
	var smsCode = $("#smsCode_"+num).val();
	if(smsCode==' '||smsCode==''|| typeof(smsCode)=='undefined'){
		$("#_alert_"+num).html('请输入手机验证码');
		goSubmit = true;
		return false;
	}
	//密码
	var pwd=$('#place_pwd_'+num).val();
	var pwd2= $('#place_pwd2_'+num).val();
	var ispassword=/.*[a-zA-Z]+.*$/;
	if(!ispassword.test(pwd)){
    	$("#_alert_"+num).html('密码必须包含字母!');
    	goSubmit = true;
    	return false;
	}
	if(pwd=='' || typeof(pwd)=='undefined'){
    	$("#_alert_"+num).html('请输入密码');
    	goSubmit = true;
    	return false;
    }else if(pwd.length<8){
    	$("#_alert_"+num).html('密码由8~16位字母、数字及符号组成');
    	goSubmit = true;
    	return false;
    }else if( pwd!=pwd2){
    	$("#_alert_"+num).html('密码不一致');
    	goSubmit = true;
    	return false;
    }
	//协议
    if(!$("#agreeCheckbox_"+num)[0].checked){
    	$("#_alert_"+num).html('请阅读并同意协议');
    	goSubmit = true;
    	return false;
    }
    $("#_alert_"+num).html('');
	$.ajax({ 
	    type: "post",
	    dataType : "json",
	    data: $("#registerForm_"+num).serialize(),
	    url: '${base}/ajaxNewPlace.do',
	    success: function(data, textStatus){
	    	goSubmit = true;
	    	if(data.rcd=='R0001'){
	    		window.location.href='${base}/user/regSuccess.do';
	    	}else{
	    		$("#_alert_"+num).html(data.rmg);
	    		return false;
	    	}
	    }
	});
}
</script>   
</body>
</html>