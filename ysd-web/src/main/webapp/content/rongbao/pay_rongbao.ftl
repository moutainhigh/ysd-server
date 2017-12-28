
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-融宝快捷确认支付</title>
    <link rel="stylesheet" href="../css/common.css" />
	<!--<meta http-equiv="pragma" content="no-cache">
    <title>融宝快捷确认支付接口</title>
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">-->
    <link rel="stylesheet" href="../css/common.css" />
    <link rel="stylesheet" href="../css/rongbao.css" />
	
    </style>
  </head>
  <body id="bg">
   <#include "/content/common/header.ftl">
   
	<div class="content">
		<p class="paymentTitle">第三方支付</p>
		<form action="${base}/payment/toPayOrder.do" method="post" onsubmit='return checkCodes();'  target="_blank">
			<input type='hidden' name='orderNo' value='${jsonMap.order_no}'>
			<input type='hidden' name='pay_token' value='${Session.pay_token}'>
			<div class='payMessage'>
				<p>订单号：${jsonMap.order_no}</p><br/>
				<p>支付金额：<span>${jsonMap.money}元</span></p>
				<p>交易时间：${jsonMap.create_time}</p>
			</div>
			<div class='userMessage'>
				<div class='line'>
					<span>银行卡号：</span>
					<div>
						<span>${jsonMap.bank_name}</span>
						<span> 储蓄卡  |  ${jsonMap.bank_no}</span>
					</div>
				</div>
				<div class='line'>
					<span>持卡人：</span>
					<input type='hidden' name='name' id='name' value="${jsonMap.name}">
					<label for='name'>${jsonMap.name}</label>
				</div>
				<div class='line'>
					<span>身份证号：</span>
					<input type='hidden' name='card_id' id='card_id' value="${jsonMap.card_id}" >
					<label for='card_id'>${jsonMap.card_id}</label>
				</div>
				<div class='line'>
					<span>银行预留手机号：</span>
					<input type='hidden' name='phone' id='phone' value="${jsonMap.phone}">
					<label for='phone'>${jsonMap.phone}</label>
				</div>
				<div class='line'>
					<span>手机验证码：</span>
					<input type='text'  name="checkCode"   value="" class='code' placeholder='请输入手机验证码'>
					<input type='button' class='sendCode' value="获取验证码">
				</div>
				<div class='line'>
					<span></span>
					<input type='submit' value="确认支付">
				</div>
			</div>
		</form>
		<div class="hint">
            <span>温馨提示：</span>
            <p>1、如果充值金额超出单笔支付限额，可通过多次充值实现；</p>
            <p>2、单笔限额、每日限额已各银行为准，请关注您银行卡的充值限制，以免造成不便；</p>
            <p>3、充值完成后，请前往“我的账户”--“资金记录”--“充值记录”，查看充值结果</p>
        </div>
	</div>
  <div class="clear"></div>

    <!--底部-->
    
	<#include "/content/common/footer-center.ftl">
    
  </body>
<script src="${base}/js/jquery-1.11.3.min.js"></script>
<script src="${base}/js/common.js"></script>
<script>
	function checkCodes(){
		var code=$('input[name=checkCode]').val();
		if(code=="" || typeof(code)=='undefined'){
			//alert('请输入验证码');
			return false;
		}
	}
	var wait2=60;
	function sendAuthCode(){
		if (wait2 ==0) {
	        $('.sendCode').removeAttr("disabled");         
	        $('.sendCode').val("获取验证码");
	        $('.sendCode').removeClass('senderror');
	        wait2 = 60;
		} else {
			$('.sendCode').attr("disabled", true);
	        $('.sendCode').addClass('senderror');
            $('.sendCode').val("重新发送(" + wait2 + ")");
            wait2--;
            setTimeout(function(){sendAuthCode()}, 1000);
	    }
	}
	sendAuthCode();	
	var isMobile=/^[1][0-9]{10}$/;
	$('.sendCode').click(function(){
		var tel=$('#phone').val();
		var order_no=$('input[name=orderNo]').val();
		if(!isMobile.test(tel)){
			alert('手机号码错误');
			return false;
		}
		$.ajax({
			url: '/user/ajaxGetPhoneCodeRongbao.do',
			async:false,
			type: 'post', 
			dataType:'json', 
			data: {'phone':tel,'orderNo':order_no},
			success:function(data){
				sendAuthCode();	
			},
			error:function(){
				alert('发送失败，请重新发送');
			}
		});
		
	});
</script>
</html>
