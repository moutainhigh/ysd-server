function check()
{
	var rollBackMoney = document.getElementById("rollBackMoney").value;//总免费转出金额
	var rollTotal = document.getElementById("rollTotal").value;
	
		var strP=/^\d+(\.\d{2})?$/;   
		if(!(document.getElementById("rollTotal").value).replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3')){
			alert('请输入正确的转出金额！');
			document.getElementById("rollTotal").value="";
			return false; 
		} else if(parseFloat(document.getElementById("rollTotal").value) > parseFloat(document.getElementById("rollBackMoney").value)){
			alert('您输入的金额大于您可用于转出的金额');
			document.getElementById("rollTotal").value="";
			return false;
		}else if(rollTotal<=0){
			alert('您输入的金额必须大于0');
			document.getElementById("rollTotal").value="";
			return false;
		}else{
			return true;
		}
	
}

function commit(obj)
{
	if (parseFloat(obj.value) > 0)
	{
		
			var realMoney=parseFloat(obj.value);
			var inputValue=parseFloat(obj.value);
			var rollBackMoney = document.getElementById("rollBackMoney").value;//总免费转出金额
			if(parseFloat(obj.value)>parseFloat(rollBackMoney)){
				alert('您输入的金额大于您可用于可转出的金额');
			}
		
	}else{
		alert('请您输入正确的转出金额!');
		return false;
	}
}

	function changeValidateCode(obj) {
        		var timenow = new Date().getTime();
           		//每次请求需要一个不同的参数，否则可能会返回同样的验证码
        		//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。
        		obj.src=qmd.base+"/rand.do?d="+timenow;
    }