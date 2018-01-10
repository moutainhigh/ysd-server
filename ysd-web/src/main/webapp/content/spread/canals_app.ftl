<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
   	<#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}|专业、安全、透明的互联网金融服务平台-渠道推广</title>
    <link rel="stylesheet" href="${base}/css/canals_app.css">
    <!--<script src="${base}/js/dpi.js"></script>-->
</head>
<body>
    <header><img src='${base}/img/canals_logo.png'></header>
    <div class="canals_bg"><img src='${base}/img/canals_goods.png'></div>
    <div class="register">
        <form id="registerForm_1" >
            <table>
                <tr class="error_hint">
                    <td  colspan="2" style="center-right" id="_alert_1"></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="text" id="place_username_1" name="username" class="phone" placeholder="请输入手机号码"></td>
                </tr>
                <tr class="message">
                    <td>
                    <input type="text" id="smsCode_1" name="smsCode" placeholder="请输入短信验证码"/>
                    <input type="text" name="code" value="000" style="display:none" ></input>
                    </td>
                    <td><button type="button" id="getcode_1" onclick="sendAuthCode('1');" >获取验证码</button></td>
                </tr>
                <tr class="pwd">
                    <td colspan="2"><input type="password" id="place_pwd_1" name="pwd"  placeholder="请输入密码，长度8~16位，必须包含字母"></td>
                </tr>
                <tr class="pwd">
                    <td colspan="2"><input type="password" id="place_pwd2_1" name="pwd2" placeholder="请再次输入密码"></td>
                </tr>
                <tr class="button">
                    <td colspan="2"><input type="button"  onclick="submitForm('1');" value="注册领1088元红包"></td>
                </tr>
                <tr class="pic">
                    <td  colspan="2"><input type="checkbox" checked="" id="agreeCheckbox_1" >我已阅读并同意 <a href="${base}/regTreaty.do" target="_blank"><span>《服务协议》</span></a></td>
                </tr>
                </table>
        </form>
    </div>
    <div class="dist">
        <div class="detail profit">
            <div class="text">高收益 <br><span>5%-14.4%</span></div>
        </div>
        <div class="detail security">
            <div class="text">更安全 <br><span>国企控股平台</span></div>
        </div>
        <div class="detail low">
            <div class="text">低门槛 <br><span>100元起投</span></div>
        </div>
        <div class="detail multiple">
            <div class="text">多选择 <br><span>7天-180天</span></div>
        </div>
    </div>
    <div class="new">
    <img src="${base}/img/canals_yellow.png" class="yellow">
        <div class="new_wrap">
            <div class="font_red">14.4%</div>
            <div class="font_red">7 天</div>
            <div class="font_red none">100元</div>
            <div class="font_black">年化收益</div>
            <div class="font_black">项目期限</div>
            <div class="font_black">起投金额</div>
        </div>
    </div>
    <div class="about">
    <img src="${base}/img/canals_blue.png" alt="" class="blue">
        <div class="about_wrap">
           	温州乐商投资有限公司成立于2011年4月27日，注册资本金5000万人民币，现实缴资本5000万人民币。 公司旗下“乐商贷”平台已获得浙江省通信管理局ICP备案，并已和渤海银行开展银行存管业务对接，预计10月份完成银行存管。 乐商贷聚焦小微金融及商会内部金融，致力于打造全场景金融新生态。乐商贷充分融合金融与“互联网+”思维，以O2O模式让金融对接资产， 以专业开放的方式将民间资金引入普惠金融体系，实现财富管理、生活服务、支付和供应链之间的闭环体系，倡导以普惠金融实践者为使命，致力于成为中国互金行业领导者。
        </div>
    </div>
    <button class="download" value="立即下载APP">立即下载APP</button>
    <div class="hint">理财有风险  入市须谨慎 </div>


    <div class="alert" style="display: none">
        <div class="alert_wrap">
            <div class="shadow"></div>
            <div class="alert_content">
                <div class="close fr"></div>
                <div class="clear"></div>
                <div class="congratulate">恭喜您</div>
                <div class="money">获得<span>1088元</span>理财红包</div>
                <div class="way">领取方式:下载乐商贷APP,登录即可领取</div>
                <button class="downloads" value="立即下载APP">立即下载 APP</button>
            </div>
        </div>
    </div>
</body>
<script src="${base}/js/jquery-1.11.3.min.js"></script>
<script>
	//发送短信验证码
	var sendSMS = false;
	var wait2=60;
	function sendAuthCode(num){
		if (wait2 ==0) {
					$('#getcode_'+num).html("获取验证码");
					 $('#getcode_'+num).removeAttr("disabled");
			        wait2 = 60;
			        $('#getcode_'+num).attr("onclick","sendAuthCode("+num+");");
		} else {
		    	if(wait2==60){
				    		var phoneReg = $("#place_username_"+num).val();
				    		if(phoneReg.length<=0){
			    	    		$("#_alert_"+num).html('<img src="${base}/img/canals_tips.png" /><span></span>手机号不能为空');
				    			return false;
				    		}
				    		var regPhone =/^(13[0-9]|14[0-9]|17[0-9]|18[0-9]|15[0-9]|18[8|9]|14[0-9])\d{8}$/;
				    		if (!regPhone.test(phoneReg)){
								$("#_alert_"+num).html('<img src="${base}/img/canals_tips.png" /><span></span>手机格式不正确');
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
					        	   		  $('#getcode_'+num).html("发送中...");
			        					  $('#getcode_'+num).attr('disabled',"true");
								   },
					        	   success: function (data) { 
					        		   if(data.result=="0"){
					        		   		//发送过手机验证码
					        		   		sendSMS = true;
					        		   		$('#getcode_'+num).attr('onclick','');
				 		   		            $('#getcode_'+num).html("" + wait2 + "s");
				 		   		            wait2--;
				 		   		            setTimeout(function(){sendAuthCode(num)}, 1000);
					        	  		}else{
					        	  			wait2=0;	
					        	  			$("#_alert_"+num).html('<img src="${base}/img/canals_tips.png" /><span></span>'+data.reason);
									       	$('#getcode_'+num).html("获取验证码");
									       	$('#getcode_'+num).removeAttr("disabled");
					        	   		}
					        	   }, 
					        	   error: function (XMLHttpRequest, textStatus, errorThrown) { 
				        	   			$("#_alert_"+num).html(errorThrown);
				    					return false;
					        	   } 
			        	  });
		    	}else{
		    		$('#getcode_'+num).html("" + wait2 + "s");
		            wait2--;
		            setTimeout(function(){sendAuthCode(num)}, 1000);	
		    	}  
		}		 
	}

	//注册
	var goSubmit = true;//防止重复提交
	function submitForm(num){
		if(goSubmit){
			goSubmit = false;
		}else{
			return false;
		}
		
		//手机号
		var phoneReg = $("#place_username_"+num).val();
		if(phoneReg.length<=0){
			$("#_alert_"+num).html('<img src="${base}/img/canals_tips.png" /><span></span>手机号不能为空');
			goSubmit = true;
			return false;
		}
		var regPhone =/^(13[0-9]|14[0-9]|17[0-9]|18[0-9]|15[0-9]|18[8|9]|14[0-9])\d{8}$/;
		if (!regPhone.test(phoneReg)){
			$("#_alert_"+num).html('<img src="${base}/img/canals_tips.png" /><span></span>手机格式不正确');
			goSubmit = true;
			return false;
	 	}
		//短信验证码
		if(!sendSMS){
			$("#_alert_"+num).html('<img src="${base}/img/canals_tips.png" /><span></span>获取验证码');
			goSubmit = true;
			return false;
		}
		if(wait2<=0||wait2>=60){
			$("#_alert_"+num).html('<img src="${base}/img/canals_tips.png" /><span></span>获取验证码');
			goSubmit = true;
			return false;
		}
		var smsCode = $("#smsCode_"+num).val();
		if(smsCode==' '||smsCode==''|| typeof(smsCode)=='undefined'){
			$("#_alert_"+num).html('<img src="${base}/img/canals_tips.png" /><span></span>请输入手机验证码');
			goSubmit = true;
			return false;
		}
		//密码
		var pwd=$('#place_pwd_'+num).val();
		var pwd2= $('#place_pwd2_'+num).val();
		var ispassword=/.*[a-zA-Z]+.*$/;
		if(!ispassword.test(pwd)){
	    	$("#_alert_"+num).html('<img src="${base}/img/canals_tips.png" /><span></span>密码必须包含字母!');
	    	goSubmit = true;
	    	return false;
		}
		if(pwd=='' || typeof(pwd)=='undefined'){
	    	$("#_alert_"+num).html('<img src="${base}/img/canals_tips.png" /><span></span>请输入密码');
	    	goSubmit = true;
	    	return false;
	    }else if(pwd.length<8){
	    	$("#_alert_"+num).html('<img src="${base}/img/canals_tips.png" /><span></span>密码由8~16位字母、数字及符号组成');
	    	goSubmit = true;
	    	return false;
	    }else if( pwd!=pwd2){
	    	$("#_alert_"+num).html('<img src="${base}/img/canals_tips.png" /><span></span>密码不一致');
	    	goSubmit = true;
	    	return false;
	    }
		//协议
	    if(!$("#agreeCheckbox_"+num)[0].checked){
	    	$("#_alert_"+num).html('<img src="${base}/img/canals_tips.png" /><span></span>请阅读并同意协议');
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
		    	if(data.rcd=='R0001'){
		    		$(".alert").show();
		    	}else{
		    		$("#_alert_"+num).html('<img src="${base}/img/canals_tips.png" /><span></span>'+data.rmg);
		    	}
		    	goSubmit = true;
		    	return false;
		    }
		});
	}



	
    $(".alert .close").click(function() {
        $(".alert").hide();
    });
//    $(".button input").click(function() {
//        $(".alert").show();
//    });
    $(".download").click(function() {
       downloadApp();
    });
    $(".downloads").click(function() {
       downloadApp();
    });
	
	function downloadApp(){
	    var ua = window.navigator.userAgent.toLowerCase();
	    if(ua.match(/MicroMessenger/i) == 'micromessenger'){//微信
	        window.open("http://a.app.qq.com/o/simple.jsp?pkgname=com.rongxun.JingChuBao");
	    }else{//非微信
	        notWeixin();
	    }
	}
	
	function notWeixin(){
		 var u = navigator.userAgent, app = navigator.appVersion;
	    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
	    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	    if(isAndroid){
	        window.open("http://dl.yueshanggroup.cn/H5.apk");
	    }else if(isiOS){
	        window.open("https://itunes.apple.com/us/app/jin-chu-bao-guo-qi-kong-gu/id999649448?l=zh&ls=1&mt=8");
	    }
	}

</script>

</html>