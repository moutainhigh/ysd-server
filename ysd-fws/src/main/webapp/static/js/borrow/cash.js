function check()
{
		var realMoney=document.getElementById("total").value;
		var inputValue=document.getElementById("total").value;
		var ableCashMoney = document.getElementById("ableCashMoney").value;
		var typeId = document.getElementById("typeId").value;
		var maxCashMoney = document.getElementById("maxCashMoney").value;//最大提现金额
		var minCashMoney = document.getElementById("minCashMoney").value;//最小提现金额
		
		//var strP=/^[1-9]+[0-9]*]*$/;   
		var strP=/^(([1-9]\d*)|\d)(\.\d{1,2})?$/; 
		if(!strP.test(document.getElementById("total").value)){
			alert('请输入正确的提现金额！');
			document.getElementById("total").value="";
			document.getElementById("real_money").innerHTML = '<br />';
			return false; 
		}
		if(typeId==0){
			var free_cash = document.getElementById("free_cash").value;
			var feeValue = document.getElementById("feeValue").value;
			
			if(inputValue==0||inputValue==""){
				alert('请您输入正确的提现金额!');
				return false;
			}else if(free_cash < 0){
				free_cash = 0;
				alert('请您输入正确的提现金额!');
				return false;
			}else if(ableCashMoney ==0 ){
				if(free_cash > 0){
					alert('您账户所有金额为24小时内充值金额，不能提现');
					return false;
				}else{
					alert('您账户没有可提现金额');
					document.getElementById("total").value="";
					document.getElementById("real_money").innerHTML = '<br />';
					return false;
				}
			}else if(parseFloat(document.getElementById("total").value) > parseFloat(document.getElementById("ableCashMoney").value)){
				alert('您输入的金额大于您可用于提现的金额');
				document.getElementById("total").value="";
				document.getElementById("real_money").innerHTML = '<br />';
				return false;
			}else if( parseFloat(obj.value) < parseFloat(minCashMoney) || parseFloat(obj.value) > parseFloat(maxCashMoney)){
				alert("您好，提现资金不能低于"+minCashMoney+"元高于"+maxCashMoney+"元");
				obj.value=0;
				document.getElementById("real_money").innerText = 0;
				return false;
			}else{
				
				if($("#usercardId").length>0) {
					if($("#usercardId").val()=='') {
						alert('请输入您的身份证号码');
						return false;
					}
				}
				
				return true;
			}
	}else{
		if(parseFloat(document.getElementById("total").value) > parseFloat(document.getElementById("ableCashMoney").value)){
			alert('您输入的金额大于您可用于提现的金额');
			document.getElementById("total").value="";
			document.getElementById("real_money").innerHTML = '<br />';
			return false;
		}
	}
}
function commit(obj)
{
	if (parseFloat(obj.value) > 0)
	{
		var typeId = document.getElementById("typeId").value;
		if(typeId==0){
			var realMoney=parseFloat(obj.value);
			var inputValue=parseFloat(obj.value);
			var free_cash = document.getElementById("free_cash").value;//总免费提现额
			var feeValue = document.getElementById("feeValue").value;
			var ableCashMoney = document.getElementById("ableCashMoney").value;//最大可提现额
			var ableMoney = document.getElementById("ableMoney").value;//可用额
			var maxCashMoney = document.getElementById("maxCashMoney").value;//最大提现金额
			var minCashMoney = document.getElementById("minCashMoney").value;//最小提现金额
			var total = document.getElementById("utotal").innerHTML;
			var ableMoneyRecharge = $("#ableMoneyRecharge").html();
			if(free_cash < 0){
				free_cash = 0;
				
			}
			if(ableCashMoney ==0 ){
				if(free_cash > 0){
					alert('您账户所有金额为24小时内充值金额，不能提现');
					return false;
				}else{
					alert('您账户没有可提现金额');
					return false;
				}
			}else if((parseFloat(obj.value) > parseFloat(document.getElementById("ableCashMoney").value))||parseFloat(obj.value) > parseFloat(ableMoney)){
				alert('您输入的金额大于您可用于提现的金额');
				document.getElementById("total").value="";
				document.getElementById("real_money").innerHTML = '<br />';
				return false;
			}else if(inputValue < parseFloat(minCashMoney)  || parseFloat(obj.value) > parseFloat(maxCashMoney)){
				alert("您好，提现资金不能低于"+ parseFloat(minCashMoney) +"元高于"+maxCashMoney+"元");
				obj.value="";
				document.getElementById("real_money").innerHTML = '<br />';
				return;
			}else if (inputValue > free_cash){
				alert('您申请提现的金额中包含受10天内充值资金提现规则限制,超出部分将收取(超出额 乘以'+ feeValue+')的提现手续费,还请注意!');
			}
	
			var cashAmount;
			cashAmount = parseFloat(obj.value);
			getCashFeeValue(cashAmount);
		}
	}else{
		alert('请您输入正确的提现金额!');
		return false;
	}
}

function getCashFeeValue(cashAmount){

	var free_cash = document.getElementById("free_cash").value;//总免费提现额
	var feeValue = document.getElementById("feeValue").value;
	var fixedFee = document.getElementById("fixedFee").value;
	
	var userRecentFee = document.getElementById("userRecentFee").value;
	
	var cashFee;

	if(cashAmount <= free_cash){
		//if(cashAmount >= 100 && cashAmount <= 30000){
		//	cashFee = 3;
		//}else if(cashAmount > 30000 && cashAmount <= 50000){
			cashFee = fixedFee;
		//}
	}else{
		var exceed = cashAmount - free_cash; // 超出部分的提现额

		//if(free_cash >= 0 && free_cash < 1000){
		//	var free_cash_fee = free_cash * 0.003;
		//}else if(free_cash >= 1000 && free_cash <= 30000){
		//	var free_cash_fee = 3;
		//}else{
			var free_cash_fee = fixedFee;
		//}

//		if(exceed == cashAmount){
//			var free_cash_fee = 0;
//		}
		var exceed_fee = changeTwoDecimal(exceed * parseFloat(feeValue));
		
		//exceed_fee = changeTwoDecimal(exceed_fee + parseFloat(userRecentFee));
		
		cashFee = changeTwoDecimal( parseFloat(exceed_fee) + parseFloat(free_cash_fee));
	}
	
	cashFee = changeTwoDecimal(parseFloat(cashFee) + parseFloat(userRecentFee));
	
	if (cashAmount <= free_cash){
		document.getElementById("real_money").innerHTML = '<br />实际到账：<font color="#FF0000" id="real_money">'+changeTwoDecimal(cashAmount-cashFee)+'</font> 元,提现费用:<font color="green">'+changeTwoDecimal(cashFee)+'</font>';
	}else{
		document.getElementById("real_money").innerHTML = '<br />实际到账：<font color="#FF0000" id="real_money">'+changeTwoDecimal(cashAmount-cashFee)+'</font> 元'+',受限提现费用:<font color="green">'+changeTwoDecimal(cashFee)+'</font><br /><font style="color:blue;font-size: 14px; font-weight: bold;">小数部分可能受四舍五入影响,结果仅供参考,请以账户中心提现记录为准</font>';
	}
}

	function changeTwoDecimal(x)
	{
		var f_x = parseFloat(x);
		var f_x = Math.round(x*100)/100;
		return f_x;
	}
	
	function changeValidateCode(obj) {
        		var timenow = new Date().getTime();
           		//每次请求需要一个不同的参数，否则可能会返回同样的验证码
        		//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。
        		obj.src=qmd.base+"/rand.do?d="+timenow;
    }