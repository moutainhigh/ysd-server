/* @since:2013/5/24　	* @author:270029616@qq.com ~~~ ! */

// Ken Popup

$(function(){
		 
			if(typeof(KindEditor) != "undefined") {
			KindEditor.ready(function(K) {
				editor = K.create("#editor", {
				 	width:"500px" ,
					height: "350px",
					items: ["source", "|", "undo", "redo", "|", "preview", "|","justifyleft", "justifycenter", "justifyright", "justifyfull", "|", "clearhtml", "quickformat", "selectall", "|", "fullscreen", "|", "forecolor", "hilitecolor", "bold", "italic", "underline", "/", "image", "flash", "media", "insertfile", "emoticons", "map"],
					syncType: "form",
					allowFileManager: true,
					uploadJson: qmd.base+"/file/ajaxUpload.do",
					fileManagerJson: qmd.base+"/file/ajaxBrowser.do",
					afterChange: function() {
						this.sync();
					}
				});
			});
		}
			
	// 设置认购回期
	$("#wmpsBuy")
		.click(function(){
			var testTime = new Date().getTime();
			var seq = $(".stepableon").attr("seq");
			
			if(parseInt($("#tenderMoney").val()) < parseInt($("#stepPieceSize").val())){
				alert('您购买的份数小于起投份数！');
		    	return;
			}
			//if(parseInt($("#tenderMoney").val()) < parseInt($("#wmpsMinFen").val())){
		   // 	alert('投资份数小于最小投资份数！');
		   // 	return;
		   // }
			if($("#wmpsMaxFen").val()!='' && $("#wmpsMaxFen").val()!='0' && parseInt($("#tenderMoney").val()) > parseInt($("#wmpsMaxFen").val())){
		    	alert('投资份数大于最大投资份数！');
		    	return;
		    }
			$.ajax({
		        type: "get",
		        dataType : "json",
		        url: qmd.base+'/user/checkForUserInvestor.do?testTime='+testTime+'&lowestMoney='+$("#lowest_money").val(),
		        success: function(data){
		        	if(data.message=='login') {
		        		alert("请登录！");
		        		window.location.href=qmd.base+'/user/login.do?loginRedirectUrl=%2Fwmps%2Fvview.do%3Fwid%3D'+$("#wmpsId").val();
		        		return;
		        	}
		        
		        	if (data.message=='recharge') {
		        		alert("请充值！");
		        		window.location.href=qmd.base+'/payment/rechargeTo.do';
		        		return;
		        	}
		        	
		        	KP.options.drag = true;
					KP.show("确定投资", 540, 580);
					$(KP.options.content)
						//.load(qmd.base+"/wmps/poputInvestWmps.do?wid="+$("#wmpsId").val()+"&stepSeq="+seq+"&tenderMoney="+$("#tenderMoney").val()+"&testTime="+testTime);
						.load(qmd.base+"/wmps/poputInvestWmps.do?wid="+$("#wmpsId").val()+"&tenderMoney="+$("#tenderMoney").val()+"&testTime="+testTime);
		        },
		        error:function(statusText){
		        	alert("发生错误！");
		        },
		        exception:function(){
		        	alert("发生错误！");
		        }
			});
			
		});

	
});

function checkPhone(){
	var phone_val = $("#lxphone");
	if(!/^0?1[358]\d{9}$/.test(phone_val.val())){
		alert('联系人手机号输入错误!');
		phone_val.val('');
		return false;
	}
}


//验证产品编号是否重复
function ajaxCheckWmpsCode(){
	var testTime = new Date().getTime();
	if($("#wmpsCode").val() ==""){
		alert('请输入产品编号');
		return false;
	}
	//encodeURIComponent
	$.ajax({
        type: "get",
        dataType : "json",
        url: qmd.base+'/wmps/ajaxCheckWmpsCode.do?testTime='+testTime+'&u='+$("#wmpsCode").val(),
        success: function(data){
        	if(data.status =="error"){
        		alert(data.message);
        	}
        },
        error:function(statusText){
        	alert("发生错误！");
        },
        exception:function(){
        	alert("发生错误！");
        }
	});
	
}
function clearNoNum(obj){

		obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符

		obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字而不是

		obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的

		obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");

		obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数

	}
function clearNoNumApr(obj){

		obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符

		obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字而不是

		obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的

		obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");

		obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d\d\d\d).*$/,'$1$2.$3'); //只能输入两个小数
	}
//<!-- 计算天利息!-->
function commit(obj)
{
	if (parseFloat(obj.value) > 0)
	{
		var realMoney=parseFloat(obj.value);
		var inputValue=parseFloat(obj.value);
		var apr = document.getElementById("apr").value;
		var yearApr = changeTwoDecimal(parseFloat(apr*365)/10);
		document.getElementById("yearapr").value = yearApr;
		document.getElementById("yearapr").innerHTML=yearApr;
	}
}


//计算月利息
function commitMonth(obj){
	if (parseFloat(obj.value) > 0)
	{
		
		var apr = document.getElementById("rate").value;
		var monthApr = changeTwoDecimal(parseFloat(apr/12));
		document.getElementById("monthapr").value = monthApr;
		document.getElementById("monthapr").innerHTML=monthApr;
	}
}
function checkNumber(){
	var limit = document.getElementById("timeLimit").value;
	if((parseFloat(limit)%10)==0){
		return true;
	}else{
		alert("借款天数只能为10的倍数，请重新输入");return false;
		document.getElementById("timeLimit").focus();
	}
}
//<!-- 检测月标输入的月数是否正确!-->
function checkMonth(){
	var limit = document.getElementById("timeLimit").value;
	if(parseFloat(limit)<=12){
			if((parseFloat(limit)%1)==0){
				return true;
			}else{
				alert("借款月数只能为小于13的正整数，请重新输入");return false;
				document.getElementById("timeLimit").focus();
			}
	}else{
		alert("借款月数只能为小于13的正整数，请重新输入");return false;
		document.getElementById("timeLimit").focus();
	}
}



function changeTwoDecimal(x)
{
var f_x = parseFloat(x);
if (isNaN(f_x))
{
alert('您输入的数据不完整');
return false;
}
var f_x = Math.round(x*100)/100;
var s_x = f_x.toString();
var pos_decimal = s_x.indexOf('.');
if (pos_decimal < 0)
{
pos_decimal = s_x.length;
s_x += '.';
}
while (s_x.length <= pos_decimal + 2)
{
s_x += '0';
}
return s_x;
}
function changeValidateCode(obj) {
    var timenow = new Date().getTime();
       //每次请求需要一个不同的参数，否则可能会返回同样的验证码
    	//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。
    obj.src= qmd.base+"/rand.do?d="+timenow;
}