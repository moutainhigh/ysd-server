function alertMessage(message){
    $('.input_tips').find('span').html(message);
    $('.input_tips').css('visibility','visible');
}
$('.recharge_uncard').find('.close').click(function(){
	$('.recharge_uncard').hide();
});
$('.recharge_uncard').find('.options a').click(function(){
	$('.recharge_uncard').hide();
});
$('#total').focus(function(){
	$('.input_tips').css("visibility","hidden");
})
function check()
{
		var idcard=$('#idcard').val();
		if(idcard!=1){
			$('.recharge_uncard').show();
			return false;
		}
		var inputValue=document.getElementById("total").value;
		var ableCashMoney = document.getElementById("ableCashMoney").value;//最大可提现额
		var maxCashMoney = document.getElementById("maxCashMoney").value;//最大提现金额
		var phoneCode = document.getElementById("phoneCode").value;//最大提现金额 
		var strP=/^(([1-9]\d*)|\d)(\.\d{1,2})?$/;  
		if(!strP.test(document.getElementById("total").value)){
			alertMessage('请输入正确的提现金额！');
			document.getElementById("total").value="";
			return false; 
		}
		if(phoneCode ==""){
			alertMessage('请您输入手机验证码!');
			return false;
		}	
		if(inputValue==0||inputValue==""){
			alertMessage('请您输入正确的提现金额!');
			return false;
		}else if(ableCashMoney ==0 ){
				alertMessage('您账户没有可提现金额');
				document.getElementById("total").value="";
				return false;
			
		}else if(parseFloat(document.getElementById("total").value) > parseFloat(document.getElementById("ableCashMoney").value)){
			alertMessage('您输入的金额大于您可用于提现的金额');
			document.getElementById("total").value="";
			return false;
		}else if(inputValue < minCash){
			alertMessage("您好，提现资金不能低于"+minCash+"元");
			document.getElementById("total").value="";
			return false;
		}else{
			return true;
		}
		/**
		 * else if(inputValue < 100 || parseFloat(document.getElementById("total").value) > parseFloat(maxCashMoney)){
			alertMessage("您好，提现资金不能低于100元高于"+maxCashMoney+"元");
			document.getElementById("total").value="";
			return false;
		}
		**/
}
