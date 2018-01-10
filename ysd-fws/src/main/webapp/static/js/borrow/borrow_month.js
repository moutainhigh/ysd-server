 $().ready(function() {
 
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
	
	
})	;
 
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

//<!-- 计算天利息!-->
function commitFlow(obj)
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
		
		var apr = document.getElementById("apr").value;
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
//	<!-- 弹出层!-->
function setRepayment(){
	var number = document.getElementById("number").value;
	var account=document.getElementById("account").value;
	var apr = document.getElementById("apr").value;
	var limit = document.getElementById("timeLimit").value;
	var borrowType=document.getElementById("borrowType").value;
	
	if(account==""){
		alert("请输入您的借款金额！");
	}else if(apr==""){
		alert("请输入您的借款年利率！");
	}else if((borrowType==4&&parseFloat(apr)>30)||borrowType!=4&&parseFloat(apr)>0.8){
		alert("您输入借款利率过大，请重新输入！");
	} else if(limit==""){
		alert("请输入您的借款期限！");
	} else{
    		// 还款计划设置
    		
    				
    			
	    	//test = "<table>"+test+"</table>"
		    //$("#repay").html(test);
		   var str="";
	    	var num ;
	    	if(borrowType==4){
	    		num= changeTwoDecimal(parseFloat(document.getElementById("timeLimit").value/1));
	    	}else{
	    		num= changeTwoDecimal(parseFloat(document.getElementById("timeLimit").value/10));
	    	}
	    	
	    	document.getElementById("number").value=num;
	    	if(num==0){
	    		alert("请输入正确的借款期限！");
			}else{ 
		    	for(var i=0;i<num;i++){
		    		var j=parseFloat(i)+parseFloat(1);
		    		var tet;
		    		if(borrowType==4){
		    			tet=j;
			    	}else{
			    		tet=j*10;
			    	}
		    	

		    		if(i<num-1){
		    			if(borrowType==4){
		    				str +="<tr><td><input type=\"checkbox\" onclick=\"checkValue("+i+");\" name=\"testdate\" id =\""+tet+"\" value=\""+tet+"\"></td><td> "+tet+"月&nbsp;</td>";
		    			}else{
		    				str +="<tr><td><input type=\"checkbox\" onclick=\"checkValue("+i+");\" name=\"testdate\" id =\""+tet+"\" value=\""+tet+"\"></td><td> "+tet+"天&nbsp;</td>";
		    			}
						
		    			str +="<td><select id=\"txt"+i+"\" name=\"txt"+i+"\" onchange=\"checkValue("+i+");\">";
		    			str +="<option value=\"0\" 	 >0%</option>";
                      	str +="<option value=\"10\" >10%</option>";
                      	str +="<option value=\"20\" >20%</option>";
                      	str +="<option value=\"30\" >30%</option>";
                      	str +="<option value=\"40\" >40%</option>";
                      	str +="<option value=\"50\" >50%</option>";
                      	str +="<option value=\"60\" >60%</option>";
                      	str +="<option value=\"70\" >70%</option>";
                      	str +="<option value=\"80\" >80%</option>";
                      	str +="<option value=\"90\" >90%</option>";
						str +="</select></td>";
						str +="<td id=\"zonge"+i+"\"></td>";
						str +="<td><input type=\"text\" name=\"money"+i+"\" id=\"money"+i+"\" size=\"9\"value=\"0\" readonly/>  元</td>";
						str +="<td id=\"lixi"+i+"\">0</td></tr>";
					}else{
						if(borrowType==4){
							str +="<tr><td><input type=\"checkbox\" checked onclick=\"return false;\" name=\"testdate\" id =\""+tet+"\" value=\""+tet+"\"></td><td> "+tet+"月&nbsp;</td>";
		    			}else{
		    				str +="<tr><td><input type=\"checkbox\" checked onclick=\"return false;\" name=\"testdate\" id =\""+tet+"\" value=\""+tet+"\"></td><td> "+tet+"天&nbsp;</td>";
		    			}
						
						str +="<td><select id=\"txt"+i+"\" name=\"txt"+i+"\" onmousemove=\"this.setCapture();\" onmouseout=\"this.releaseCapture();\" onfocus=\"this.blur();\" >";
		               	str +="<option value=\"0\" 	 >0%</option>";
                      	str +="<option value=\"10\" >10%</option>";
                      	str +="<option value=\"20\" >20%</option>";
                      	str +="<option value=\"30\" >30%</option>";
                      	str +="<option value=\"40\" >40%</option>";
                      	str +="<option value=\"50\" >50%</option>";
                      	str +="<option value=\"60\" >60%</option>";
                      	str +="<option value=\"70\" >70%</option>";
                      	str +="<option value=\"80\" >80%</option>";
                      	str +="<option value=\"90\" >90%</option>";
		                str +="<option value=\"100\" selected=\"selected\" >100%</option>";
						str +="</select></td>" ;
						var lApr;
						var payin;
						if(borrowType==4){
							lApr = parseFloat(document.getElementById("apr").value)/1200;
							payin= changeTwoDecimal(num*lApr*parseFloat(account));
						}else{
							lApr = parseFloat(document.getElementById("apr").value)/1000;
							payin = changeTwoDecimal(num*10*lApr*parseFloat(account));
						}
						
						var zong = changeTwoDecimal(parseFloat(payin)+parseFloat(account));
						str +="<td id=\"zonge"+i+"\">"+zong+"</td>";
						str +="<td><input type=\"text\" name=\"money"+i+"\" id=\"money"+i+"\" size=\"9\" value=\""+changeTwoDecimal(account)+"\" readonly/>  元</td>";
						
						str +="<td id=\"lixi"+i+"\">"+payin+"</td></tr>";
					}
		    	}
		    str +="<tr><td colspan=\"3\">小计</td><td id=\"lastZong\"><em class=\"c1\">￥"+zong+"</em></td><td >"+changeTwoDecimal(account)+"</td><td id=\"lastLix\">￥"+payin+"</td></tr>";
//		    str = "<table>"+str+"</table>";
		    
	   	 }
	    	KP.options.drag = true;
			KP.show("还款计划设置", 570, 460);
	    	$(KP.options.content)
			.load(qmd.base+"/content/borrow/repaymentPlan.html",function(){
				 $("#setPlanTable").html("");
				 $("#setPlanTable").html(str);
			});
    		
	   	}
    	
		
	}
	
function checkValue(type){

	var obj=document.getElementsByName('testdate');  //选择所有name="testdate"的对象，返回数组
	
	var account=document.getElementById("account").value;
	var borrowType=document.getElementById("borrowType").value;
	if(account !==""){
		var j ;
		var lastApr;
		if(borrowType==4){
			j= parseInt(parseInt(document.getElementById("timeLimit").value/1)-parseInt(1));
			 
			lastApr= parseFloat(document.getElementById("apr").value)/1200;
		}else{
			j= parseInt(parseInt(document.getElementById("timeLimit").value/10)-parseInt(1));
			 
			lastApr= parseFloat(document.getElementById("apr").value)/1000;
		}
		
	 	var total=0;
	 	var txtval=0;
	 	var daylast = 0;
	 	var lastZong =0;
	 	var lasLix = 0;
	 	var moneyleft = parseInt(account);
	  	for(var i=0; i<j; i++){
  			var money=0;
  			var cout=0;
  			var coutLix=0;
    		if(obj[i].checked) {
    			// 选择还款比例，计算还款金额
    			money = parseFloat($("#txt"+i).val())*parseFloat(account)/parseFloat(100);
    			// 总的还款比例
    			txtval = parseInt($("#txt"+i).val())+parseInt(txtval);
    			
    			// 还款总比例大于100，当前选择无效
    			if (txtval >=100) {
    			
    				$("#txt"+type).val(0);
    				checkValue(type);
    				alert("请输入正确还款比例！");
    				return;
    				// 最后剩余
    				//txtval = parseInt(100)-parseInt(txtval)+parseInt($("#txt"+type).val());
    				//$("#txt"+j).val(txtval);
    				//$("#txt"+type).val(0);
    				
    				//total =money+parseFloat(total)-parseFloat(document.getElementById("money"+type).value);
    				
    				//document.getElementById("money"+type).value=0;
    				//document.getElementById("money"+j).value=changeTwoDecimal(parseFloat(account)-parseFloat(total));
    				
    				//alert("请输入正确还款比例！");
    				//return;
    			}
    			
    			var daynum = parseInt(obj[i].value) - daylast;
    			daylast = parseInt(obj[i].value);
    			$("#lixi"+i).html(changeTwoDecimal(daynum * lastApr * moneyleft));
    			$("#zonge"+i).html(changeTwoDecimal(daynum * lastApr * moneyleft+money));
    			coutLix =changeTwoDecimal(daynum * lastApr * moneyleft);
    			cout=changeTwoDecimal(daynum * lastApr * moneyleft+money);
    			lasLix = changeTwoDecimal(parseFloat(lasLix)+parseFloat(coutLix));
    			lastZong = changeTwoDecimal(parseFloat(lastZong)+parseFloat(cout));
    			moneyleft = moneyleft-money;
    			
	    		total =parseFloat(money)+parseFloat(total);
	    		document.getElementById("money"+i).value=money;
    		} else {
    			$("#txt"+i).val(0);
    			document.getElementById("money"+i).value=0;
    			$("#lixi"+i).html("");
    			$("#zonge"+i).html(0);
    		}
    	}
    	txtval = parseInt(100)-parseInt(txtval);
    	if (txtval<10) {
    		alert("请输入正确还款比例！");
    	}
    	$("#txt"+j).val(txtval);
    	document.getElementById("money"+j).value=changeTwoDecimal(parseFloat(account)-parseFloat(total));
    	var daynum = parseInt(obj[j].value) - daylast;
    	$("#lixi"+i).html(changeTwoDecimal(daynum * lastApr * moneyleft));
    	$("#zonge"+j).html(changeTwoDecimal(daynum * lastApr * moneyleft+parseFloat(account)-parseFloat(total)));
    	lastZong = changeTwoDecimal(daynum * lastApr * moneyleft+parseFloat(account)-parseFloat(total)+parseFloat(lastZong));
    	lasLix = changeTwoDecimal((daynum * lastApr * moneyleft)+parseFloat(lasLix));
    	$("#lastZong").html(changeTwoDecimal(lastZong));
    	$("#lastLix").html(changeTwoDecimal(lasLix));
    } else {
    	alert("请输入您的借款金额！");
    }
}

function repayPlan(){
  var obj=document.getElementsByName('testdate');  //选择所有name="testdate"的对象，返回数组
  //取到对象数组后，我们来循环检测它是不是被选中
  var s='';
  var str="";
  var qishu=0;
  var account=document.getElementById("account").value;
  var apr = parseFloat(document.getElementById("apr").value);
  var borrowType=document.getElementById("borrowType").value;
   var zong=0;
   var interest;
   var total;
   var obj_length=obj.length;
   var daynum = 0;
  for(var i=0; i<obj_length; i++){
  	var money; 
    if(obj[i].checked) {
    	 qishu ++;
    	var ojb = document.getElementById("money"+i);
    	money=obj[i].value+','+ojb.value+':';  //如果选中，将value添加到变量s中
    	s+=money;
    	daynum = parseInt(obj[i].value) - daynum;
    	var lapr;
    	if(borrowType==4){
    		lapr=parseFloat(apr)/parseFloat(1200);
    	}else{
    		lapr=parseFloat(apr)/parseFloat(1000);
    	}
    	if(zong==0){
    		interest = changeTwoDecimal(parseFloat(parseFloat(lapr)*parseFloat(account)*daynum));
    	}else{
    		var all = parseFloat(account)-parseFloat(zong);
    		interest = changeTwoDecimal(parseFloat(parseFloat(lapr)*parseFloat(all)*daynum));
    	}
    	daynum = parseInt(obj[i].value);
    	zong=parseFloat(ojb.value)+parseFloat(zong);
    	 total =changeTwoDecimal(parseFloat(interest)+parseFloat(ojb.value));
    	 if(borrowType==4){
    		 str += "<tr id='repayList"+i+"'><td>"+qishu+"</td><td>第"+obj[i].value+"月</td><td>"+total+"</td><td>"+ojb.value+"</td><td>"+interest+"</td></tr>";
    	 }else{
    		 str += "<tr id='repayList"+i+"'><td>"+qishu+"</td><td>第"+obj[i].value+"天</td><td>"+total+"</td><td>"+ojb.value+"</td><td>"+interest+"</td></tr>";
    	 }
    	
    }
  }
  KP.close();
 // art.dialog.get('aui_id').close();
  if(qishu==0){
  	alert("请您选择还款的期数和金额");
  }else{
//  closeLayer();
  	str = "<div><table class=\"tac\"><thead  bgcolor=\"#f0f0f2\"><tr><th>期数</th><th>还款日期</th><th>还款总额</th><th>本金</th><th>利息</th></tr></thead>"+str+"</table>";
    	$("#test").html(str);
  	document.getElementById("borStages").value=s;
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
//<!--控制奖励-->
function change_j(type){
  var obj=document.getElementsByName('award1');
  var ojb=document.getElementsByName('award2');  
  for(var i=0; i<obj.length; i++){
    if(obj[i].checked) {
    	jQuery("#funds").attr("disabled",false); 
    	if(ojb[i].checked){
    		document.getElementById("award").value=ojb[i].value;
    	}else{
    		document.getElementById("award").value=obj[i].value;
    	}
    }else{
    	jQuery("#funds").attr("disabled",true);
    	document.getElementById("funds").value="";
    	document.getElementById("award").value=0;
    }
    if(ojb[i].checked){
    	 if(obj[i].checked) {
    		 document.getElementById("award").value=ojb[i].value; 
    	 }else{
    		 document.getElementById("award").value=0;
    		 ojb[i].checked = false;
    		 alert("请设置启用奖励和奖励的比例！");
    	 }
    }
   }
}	

//<!--设定定向标-->
function change_d(type){
  var obj=document.getElementsByName('isDxb1');  
  for(var i=0; i<obj.length; i++){
    if(obj[i].checked) {
    	jQuery("#pwd").attr("disabled",false); 
    	document.getElementById("isDxb").value=obj[i].value;
    }else{
    	jQuery("#pwd").attr("disabled",true);
    	document.getElementById("isDxb").value=0;
    }
   }
}	

function addImage1(objName) {
	var ojb = $("#testtest"+objName);
	var oldValue=parseInt(objName);
	oldValue++;
	ojb.after("<tr id='testtest"+oldValue+"'><td>&nbsp;</td><td><input type=\"file\" value=\""+oldValue+"\"  name=\"borImagesFile\">&nbsp;<input type=\"button\" id=\"btn_"+oldValue+"\" value=\""+oldValue+"\" onclick=\"addImage('"+oldValue+"');\"/></td></tr>");
	var btnOjb = $("#btn_"+objName);
	btnOjb.after("<input type=\"button\" onclick=\"deleteImage('"+objName+"')\" value=\"删除\"/>");
	btnOjb.remove();
}

 function addImage(objName) {
    	var ojb = $("#testtest"+objName);
    	var oldValue=parseInt(objName);
    	oldValue++;
    	for(var num=0;num<oldValue;num++){
    	ojb.after("<tr id='testtest"+oldValue+"'><td>&nbsp;</td><td><input type=\"file\" value=\""+oldValue+"\"  name=\"borImagesFile\">&nbsp;<input type=\"button\" id=\"btn_"+oldValue+"\" value=\""+oldValue+"\" onclick=\"addImage('"+oldValue+"');\"/></td></tr>");
    	}
    	var btnOjb = $("#btn_"+objName);
    	btnOjb.after("<input type=\"button\" onclick=\"deleteImage('"+objName+"')\" value=\"删除\"/>");
    	btnOjb.remove();
    }
    
    function deleteImage(objName) {
    	var ojb = $("#testtest"+objName);
    	ojb.remove();
    }
 
function changeValidateCode(obj) {
        var timenow = new Date().getTime();
           //每次请求需要一个不同的参数，否则可能会返回同样的验证码
        	//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。
        obj.src= qmd.base+"/rand.do?d="+timenow;
	}
function checkimg(){
	var $code_img = $("#code_img");
	$code_img.attr("src", qmd.base+"/rand.do?timestamp=" + (new Date()).valueOf());
	}
	
function get_award_value()
{
    var form1 = document.forms['form1'];
    
    for (i=0; i<form1.award.length; i++)    {
        if (form1.award[i].checked)
        {
           return form1.award[i].value;
        }
    }
}

function check_form(){

	var type = $("#borrowType").val();
	if ($("#borrowname").val()=='') {
			alert("标题不能为空，请重新输入!");
			return false;
		}else if($("#businessCode").val()==''){
			alert("项目编号不能为空，请重新输入!");
			return false;
		}else if($("#businessType").val()==''){
			alert("业务类型不能为空，请重新选择!");
			return false;
		}else if ($("#borImageFirst").val()=='') {
			alert("图片不能为空，请重新输入!");
			return false;
		}else if ($("#account").val()=='') {
			alert("借款金额不能为空，请重新输入!");
			return false;
		}else if (checkPositiveInteger($("#account").val())==false) {
			alert("借款金额不正确，请重新输入!");
			return false;
		}else if ($("#apr").val()=='') {
			alert("借款利率不能为空，请重新输入!");
			return false;
		}else if (checkFloat($("#apr").val()==false)) {
			alert("借款利率不正确，请重新输入!");
			return false; 
		}else if(type!=4&&changeTwoDecimal($("#apr").val())>1){
			alert("借款利率过高，请重新输入!");
			return false;
		} else if(type==4&&changeTwoDecimal($("#apr").val())>30){
			alert("借款利率过高，请重新输入!");
			return false;
		} else if (($("#award").val()==1||$("#award").val()==2) && $("#funds").val()=='') {
			alert("奖励不能为空，请重新输入!");
			return false;
		}else if (($("#award").val()==1||$("#award").val()==2) && parseFloat($("#funds").val())<=0) {
			alert("奖励不能小于0，请重新输入!");
			return false;
		} else if ($("#timeLimit").val()=='') {
			alert("借款时长不能为空，请重新输入!");
			return false;
		} else if (checkPositiveInteger($("#timeLimit").val())==false) {
			alert("借款时长不正确，请重新输入!");
			return false;
		}  else if(type==1){
			if($("#borStages").val()==''||$("#borStages").val()=='0') {
				alert("请设置还款计划!");
				return false;
			}
		} else if ($("#isDxb").val()==1 && $("#pwd").val()=='') {
			alert("定向密码不能为空，请重新输入!");
			return false;
		} else if ($("#editor").val()=='') {
			alert("标的详情不能为空，请重新输入!");
			return false;
		} else if ($("#mycode").val()=='') {
			alert("校验码不能为空，请重新输入!");
			return false;
		}else{
			return true;
		}	
		
	}

	function checkFloat(str)//正浮点数
	{
	    var reg = /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
	    if(!reg.test(str))
	    {
	        return false;
	    }
	    return true;
	}

	function checkPositiveInteger(str) // 正整数
	{
	    var reg = /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
	    if(!reg.test(str))
	    {
	        return false;
	    }
	    return true;
	}