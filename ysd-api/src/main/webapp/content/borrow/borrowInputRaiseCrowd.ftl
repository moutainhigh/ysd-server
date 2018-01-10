<!DOCTYPE html>
<html>
  <head>
  <title>${Application ["qmd.setting.name"]}-发布项目</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${base}/static/js/common/ajaxImageUploadBr.js"></script>
	<script type="text/javascript" src="${base}/js/jquery/jquery.lSelect.js"></script>
<script>
$().ready(function() {
	var jsyear = [];
	var d = new Date();
	var year1 = d.getFullYear()+10;
	
	for(var i = 0;i < 40;i++){
		jsyear.push(year1 - i);
	}
	var jsmonth = [1,2,3,4,5,6,7,8,9,10,11,12];
	
	$.each(jsyear,function(i){ 
		$(".sel_year_class").append("<option>"+jsyear[i]+"</option>") 
	}); 
	
	$.each(jsmonth,function(i){ 
		$(".sel_month_class").append("<option>"+jsmonth[i]+"</option>") 
	}); 
	
	$.ajax({
		url: "${base}/borrow/ajaxBrand.do",
		type: "GET",
		dataType: "json",
		cache: false,
		success: function(data) {
			var news = eval("("+data.message+")");
			if(news.message=="成功"){
				var brand = news.result.branditems;
				$.each(brand,function(i){ 
					$(".sel_brand_class").append("<option bid="+brand[i].id +" value = '"+ brand[i].name +"'>"+brand[i].bfirstletter+"-"+brand[i].name+"</option>") 
				}); 
			}
			
		}
	});
	
	
	$("#sel_brand_id").change(function(){
		$.ajax({
			url: "${base}/borrow/ajaxSeries.do",
			data:{brandId:$("#sel_brand_id").find("option:selected").attr("bid")},
			type: "GET",
			dataType: "json",
			cache: false,
			success: function(data) {
				var news = eval("("+data.message+")");
				$(".sel_series_class").html("<option>请选择</option>")
				if(news.message=="成功"){
					var factory = news.result.factoryitems;
					$.each(factory,function(i){ 
						
						$(".sel_series_class").append("<option disabled='disabled'>"+factory[i].name+"</option>");
						
						var series = factory[i].seriesitems;
						$.each(series,function(i){ 
							$(".sel_series_class").append("<option sid="+series[i].id+" value = '"+series[i].name+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+series[i].name+"</option>");
						
						});
						
					}); 
				}
				
			}
		});
	})
	
	
	$("#sel_series_id").change(function(){
		$.ajax({
			url: "${base}/borrow/ajaxSpec.do",
			data:{seriesId:$("#sel_series_id").find("option:selected").attr("sid")},
			type: "GET",
			dataType: "json",
			cache: false,
			success: function(data) {
				var news = eval("("+data.message+")");
				$(".sel_spec_class").html("<option>请选择</option>")
				if(news.message=="成功"){
					var yearitem = news.result.yearitems;
					$.each(yearitem,function(i){ 
						
						$(".sel_spec_class").append("<option disabled='disabled'>"+yearitem[i].name+"</option>");
						
						var spec = yearitem[i].specitems;
						$.each(spec,function(i){ 
							$(".sel_spec_class").append("<option value = '"+spec[i].name+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+spec[i].name+"</option>");
						
						});
						
					}); 
				}
				
			}
		});
	})

});


<#----------------------------->
//计算月利息
function commit_year2month(str1,str2){
	var apr = $("#"+str1).val();
	var monthApr = changeTwoDecimal(parseFloat(apr)/12);
	$("#"+str2).html(monthApr);
}
function commit_day2year(str1,str2){
	var apr = $("#"+str1).val();
	var monthApr = changeTwoDecimal(parseFloat(apr)*36);
	$("#"+str2).html(monthApr);
}
//<!--控制奖励-->
function change_award(type){
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
function change_isDxb(type){
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


function loadRepaymentPlan() {
	if($("#account").val()=='') {
		return;
	}
	if($("#apr").val()=='') {
		return;
	}
	if($("#timeLimit").val()=='') {
		return;
	}
	if($("#borrowStyle").val()=='') {
		return;
	}
	if($("#borrowDivides").val()=='') {
		return;
	}
	
	$("#plan_borrowAccount").val($("#account").val());
	$("#plan_borrowApr").val($("#apr").val());
	$("#plan_borrowTimeLimit").val($("#timeLimit").val());
	$("#plan_borrowStyle").val($("#borrowStyle").val());
	$("#plan_borrowDivides").val($("#borrowDivides").val());
	$("#plan_borrowIsday").val($("#borrowIsday").val());
	
	$.ajax({
        type: "post",
        data: $('#planForm').serialize(),
        url: '${base}/borrow/ajaxShowPlan.do',
        error: function(){
			alert('操作错误,请与系统管理员联系!');
		},
		success: function(data){
			$("#planTable").html(data);
		}

	});

}

function check_form_promote(){

	var type = $("#borrowType").val();
	if ($("#borrowname").val()=='') {
			alert("标题不能为空，请重新输入!");
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
	
function onblurAutoRateRaise(obj,no) {
	var y_rv=parseFloat(obj.value);
	var m_rv=rateYear2Month(y_rv);
	$("#span_monthrate_"+no).html(m_rv);
}
function onblurAutoRate(obj,no) {
	var d_rv=parseFloat(obj.value);
	var y_rv=rateDay2Year(d_rv);
	$("#span_yearrate_"+no).html(y_rv);
}
<#--校验正浮点数-->
function checkFloat(str)
{
    var reg = /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
    if(!reg.test(str))
    {
        return false;
    }
    return true;
}
<#--校验正整数-->
function checkPositiveInteger(str) 
{
    var reg = /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
    if(!reg.test(str))
    {
        return false;
    }
    return true;
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
function checkRepaymentPeriod() {
	if ($("#timeLimit").val() != ''&& $("#repaymentPeriod").val()!=''&&checkMod($("#timeLimit").val(),$("#repaymentPeriod").val())==false ) {
		$("#hidPeriodSize").val(number2Decimal(getDivideInt($("#timeLimit").val(),$("#repaymentPeriod").val()),0));
		$("#showPeriodSize").html($("#hidPeriodSize").val());
		alert("借款时长不能被借款周期整除，请重新输入!");
		return false;
	}
	if ($("#timeLimit").val() != ''&& $("#repaymentPeriod").val()!=''&&checkMod($("#timeLimit").val(),$("#repaymentPeriod").val())==true ) {
		$("#hidPeriodSize").val(number2Decimal(getDivideInt($("#timeLimit").val(),$("#repaymentPeriod").val()),0));
		$("#showPeriodSize").html($("#hidPeriodSize").val());	
	}
	if ($("#timeLimit").val() == ''|| $("#repaymentPeriod").val()=='') {
		$("#hidPeriodSize").val(0);
		$("#showPeriodSize").html($("#hidPeriodSize").val());	
	}
	
	showRepayPlan();
}
<#--小数位整理：2位小数-->
function number2Decimal(srcNumber, n) {
　　var dstNumber = parseFloat(srcNumber).toFixed(n+2);
　　if (isNaN(dstNumber)) {
　　　　return srcNumber;
　　}
　　if (dstNumber >= 0) {
　　　　dstNumber = parseInt(dstNumber * Math.pow(10, n) + 0.5) / Math.pow(10, n);
　　} else {
　　　　var tmpDstNumber = -dstNumber; dstNumber = parseInt(tmpDstNumber * Math.pow(10, n) + 0.5) / Math.pow(10, n);
　　}
　　var dstStrNumber = dstNumber.toString();
　　var dotIndex = dstStrNumber.indexOf('.');
　　if (dotIndex < 0 && n > 0) {
　　　　dotIndex = dstStrNumber.length; dstStrNumber += '.';
　　}

　　while (dstStrNumber.length <= dotIndex + n) {
　　　　dstStrNumber += '0';
　　}
　　return dstStrNumber;
}


<#--点击：是否有奖励-->
 function change_award(){
   	if ($("#award1").is(':checked')) {
   		$("#funds").attr("disabled",false);
   		$("#funds").removeClass("input1").addClass("input2");
   		$("#award").val('1');
   	} else {
   		$("#funds").val('');
   		$("#funds").attr("disabled",true);
   		$("#funds").removeClass("input2").addClass("input1");
   		$("#award").val('0');
   	}
}


function uploadFileHideBack () {
	alert('上传成功！！');
}

// 借款凭证上传后
function uploadFileHideBackItem(aid) {
	alert('上传成功！');
	var obj = $("#"+aid);
	obj.after('<a style=" color:#c43b3b;" href="javascript:void(0);">上传成功</a>');
	obj.remove();
}


// 车辆照片
function addImgCar() {

	var x=parseInt($("#idCar").val());
	x = x + 1;
	$("#idCar").val(x);
	
	var str = '';
	str += '	<tr id="car_'+x+'" class="blueBg">';
	str += '		<td class="kaqu_sa">';
	str += '			<input type="text" name="carTitle" style="width:180px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>';
	str += '			<input type="file" name="imageFile" id="imageCar_'+x+'" style="height:34px;margin-bottom:10px;"/>';
	str += '			<input type="hidden" name="carImg" id="carimg_'+x+'" />';
	str += '		</td>';
	str += '		<td class="kaqu_sd">';
	str += '			<a id="btnCar_'+x+'" onclick="ajaxFileUploadImageWithRtn(\'3\',\'imageCar_'+x+'\',\'${base}/file/ajaxUploadImage.do\',\'carimg_'+x+'\',uploadFileHideBackItem(\'btnCar_'+x+'\'));" href="javascript:void(0);" class="kaqu_se">上传</a>';
	str += '		</td>';
	str += '		<td class="kaqu_e14">';
	str += '			<a onclick="moveUpCar(\''+x+'\')" href="javascript:void(0);" class="kaqu_top">上移</a>';
	str += '			<a onclick="moveDownCar(\''+x+'\')" href="javascript:void(0);" class="kaqu_down">下移</a>';
	str += '			<a onclick="moveOutCar(\''+x+'\')" href="javascript:void(0);"class="kaqu_close">删除</a>';
	str += '		</td>';
	str += '	</tr>';
	
    var tr_len = $("#table_car tr").length;
	if(tr_len==0) {
		$("#table_car").html(str);
	} else {
		$("#table_car").find('tr:last').after(str);
	}
}


function moveUpCar(x) {
	var obj = $("#car_"+x);
	var prev = obj.prev();
	prev.before(obj);
}
function moveDownCar(x) {
	var obj = $("#car_"+x);
	var next = obj.next();
	next.after(obj);
}
function moveOutCar(x) {
	var obj = $("#car_"+x);
	obj.remove();
}


// 交易凭证
function addImgItem() {

	var x=parseInt($("#idSort").val());
	x = x + 1;
	$("#idSort").val(x);
	
	var str = '';
	str += '	<tr  id="trid_'+x+'" class="blueBg">';
	str += '		<td class="kaqu_sa">';
	str += '			<input type="text" name="vouchersTitle" style="width:180px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>';
	str += '			<input type="file" name="imageFile" id="imageItem_'+x+'" style="height:34px;margin-bottom:10px;"/>';
	str += '			<input type="hidden" name="vouchers" id="voucher_'+x+'" />';
	str += '		</td>';
	str += '		<td class="kaqu_sd">';
	str += '			<a id="btnUpload_'+x+'" onclick="ajaxFileUploadImageWithRtn(\'3\',\'imageItem_'+x+'\',\'${base}/file/ajaxUploadImage.do\',\'voucher_'+x+'\',uploadFileHideBackItem(\'btnUpload_'+x+'\'));" href="javascript:void(0);" class="kaqu_se">上传</a>';
	str += '		</td>';
	str += '		<td class="kaqu_e14">';
	str += '			<a onclick="moveUp(\''+x+'\')" href="javascript:void(0);" class="kaqu_top">上移</a>';
	str += '			<a onclick="moveDown(\''+x+'\')" href="javascript:void(0);" class="kaqu_down">下移</a>';
	str += '			<a onclick="moveOut(\''+x+'\')" href="javascript:void(0);"class="kaqu_close">删除</a>';
	str += '		</td>';
	str += '	</tr>';
	
    var tr_len = $("#table_img tr").length;
	if(tr_len==0) {
		$("#table_img").html(str);
	} else {
		$("#table_img").find('tr:last').after(str);
	}
}

function moveUp(x) {
	var obj = $("#trid_"+x);
	var prev = obj.prev();
	prev.before(obj);
}
function moveDown(x) {
	var obj = $("#trid_"+x);
	var next = obj.next();
	next.after(obj);
}
function moveOut(x) {
	var obj = $("#trid_"+x);
	obj.remove();
}

function check_form_promote(){

	var type = $("#borrowType").val();
	if ($("#borrowname").val()=='') {
			alert("标题不能为空，请重新输入!");
			return false;
		}else if($("#businessCode").val()==''){
			alert("项目编号不能为空，请重新输入!");
			return false;
		}else if ($("#account").val()=='') {
			alert("借款金额不能为空，请重新输入!");
			return false;
		}
		else if ($("#accountYes").val()=='') {
			alert("车辆估价金额不能为空，请重新输入!");
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

<#---※※※直投设置开始※※※----->
<#--新增直投信息-->

function checkDirectItem() {
	var tr_len = $("#table_direct tr").length;
	if(tr_len <=0) return false;
	var ret = false;
	var tol = 0;
	$("#table_direct").find('input:text').each(function(i,t){
		if($(this).attr("name")=='directMoney') {
			var k = $(this).val();
			if (checkMod(k,500)==false) {
				ret = true;
				return;
			}
			tol = parseFloat(tol)+parseFloat(k);
		}
	});
	if (ret==true) return ret;
	if (parseFloat($("#account").val()) < tol) {
		return true;
	}
	
	return ret;
	
}

function addDirectItem() {

	var x=parseInt($("#idSortDirect").val());
	x = x + 1;
	$("#idSortDirect").val(x);
	
	var str = '';
	str += '	<tr  id="direct_trid_'+x+'" class="blueBg">';
	str += '		<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">';
	str += '			<input type="text" name="directUserName" class="input2" style="width:150px;"/>';
	str += '		</td>';
//	str += '		<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">';
//	str += '			<span id="direct_td_'+x+'">0</span>';
//	str += '		</td>';
	str += '		<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">';
	str += '			<input type="text" name="directMoney" class="input2"  style="width:100px;"/>';
	str += '		</td>';
	str += '		<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">';
	str += '			<input type="text" name="directRateDay" id="txt_directRateDay_'+x+'" class="input2"  style="width:80px;" onBlur="onBlurDirectRateYear(\''+x+'\')"/><span>‰/天</span>  ';
	str += '		</td>';
	str += '		<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">';
	str += '			<span id="span_directRateYear_'+x+'"></span>%年';
	str += '			<input type="hidden" name="directRateYear" id="directRateYear_'+x+'" class="input2" style="width:80px;"/>';
	str += '		</td>';
	str += '		<td class="kaqu_e14" style="padding:0px 10px 10px 10px;">';
	str += '			<a onclick="moveOutDirect(\''+x+'\')" href="javascript:void(0);"class="kaqu_close kaqu_close1">删除</a>';
	str += '		</td>';
	str += '	</tr>';
	
    var tr_len = $("#table_direct tr").length;
	if(tr_len==0) {
		$("#table_direct").html(str);
	} else {
		$("#table_direct").find('tr:last').after(str);
	}
}

function onBlurDirectRateYear(x) {
	var txt_directRateDay =  parseFloat($("#txt_directRateDay_"+x).val());
	var txt_directRateYear = rateDay2Year(txt_directRateDay);
	$("#directRateYear_"+x).val(txt_directRateYear);
	$("#span_directRateYear_"+x).html(txt_directRateYear);
	
}

function fillDirectInfo() {
	var str ='';
	$("#table_direct tr").each(function(i,t){
		if(i>0) str+=','
		str+=fillDirectInfo_tr($(this));
	});
	if(str !='') {
		str = '['+str+']';
	}
	return str;
}

function fillDirectInfo_tr(obj) {
	var tr_obj=$(obj).find('input:text');
	var str = '';
	tr_obj.each(function(i,t){
		if(i>0) str+=','
		str += '\"'+$(this).attr('name')+'\":'+'"'+$(this).val()+'"';
	});
	if(str !='') {
		str = '{\"directUserId\":0,'+str+'}';
	}
	
	return str;
}

<#--删除直投信息-->
function moveOutDirect(x) {
	var obj = $("#direct_trid_"+x);
	obj.remove();
}

<#---※※※直投设置结束※※※----->

<#--校验
	false:借款金额不能被总份数整除-->
function checkPieceInput() {
	
	if ($("#account").val() != ''&&checkMod($("#account").val(),500)==false ) {
		alert("借款金额不能被500整除，请重新输入!");
		return false;
	}
	
	if ($("#account").val() != '') {
		$("#wanderPieceSize").val(getDivideInt($("#account").val(),500));
	}

	if ($("#account").val() != ''&& $("#wanderPieceSize").val()!=''&&checkMod($("#account").val(),$("#wanderPieceSize").val())==false ) {
		$("#showPieceMoney").html(number2Decimal(getDivideInt($("#account").val(),$("#wanderPieceSize").val()),2));
		alert("借款金额不能被总份数整除，请重新输入!");
		return false;
	}
	
	if ($("#account").val() != ''&& $("#wanderPieceSize").val()!=''&&checkMod($("#account").val(),$("#wanderPieceSize").val())==true ) {
		$("#showPieceMoney").html(number2Decimal(getDivideInt($("#account").val(),$("#wanderPieceSize").val()),2));
	}
}
<#--整除校验
	true：str可以被dov整除-->
function checkMod(str,dov) {
	var a = parseInt(str);
	var b = parseInt(dov);
	if (a%b==0){
		return true;
	}
	return false;
}
<#--相除-->
function getDivideInt(str,dov) {
	var a = parseInt(str);
	var b = parseInt(dov);
	if (b==0) {
		return 0;
	}
	return a/b;
}

<#--合同参数-->
function fillContractParam() {
	var str = '';
	var tr_ojbs = $(".contract_param");
	if (tr_ojbs.length > 0) {
		tr_ojbs.each(function(i,t){
			if( i>0 && $(t).find('input:text').length > 0) str+=',';
			
			var txt_objs=$(t).find('input:text');
			txt_objs.each(function(ii,tt){
				str += '\"'+$(this).attr('name')+'\":'+'"'+$(this).val()+'"';
			});
		});
	}
	
	if ($("#hidden_financeSignImg").length>0) {
		str+=',';
		str += '\"financeSignImg\":'+'"'+$("#hidden_financeSignImg").val()+'"';
	}
	
	if(str !='') {
		str = '{'+str+'}';
	}
	
	$("#borrowContractParam").val(str);
}
<#--抵押物列表-->
function fillContractPawns() {
	var str ='';

	$("#table_contract_pawn tr").each(function(i,tm){
		if(i>0) str+=',';
		str+=fillContract_pawn_tr($(this));
	});
	if(str !='') {
		str = '['+str+']';
	}
	$("#borrowContractPawns").val(str);
}
<#--抵押物单个-->
function fillContract_pawn_tr(obj) {
	var tr_obj=$(obj).find('input:text');
	var str = '';
	tr_obj.each(function(i,t){
		if(i>0) str+=',';
		str += '\"'+$(this).attr('name')+'\":'+'"'+$(this).val()+'"';
	});
	if(str !='') {
		str = '{'+str+'}';
	}
	
	return str;
}
<#---※※※抵押物设置 开始※※※----->
<#--新增抵押物--->
function addContractPawnItem() {
	var tr_len = $("#table_contract_pawn tr").length;
	if(tr_len==0) {
		$("#table_contract_pawn").html($("#init_pawn").html());
	} else {
		$("#table_contract_pawn").find('tr:last').after($("#init_pawn").html());
	}
}
<#--删除抵押物-->
function delContractPawnItem(obj) {
	$(obj).parent().parent().remove();
}
<#---※※※抵押物设置 结束※※※----->

function changeValidateCode(obj) {
        var timenow = new Date().getTime();
           //每次请求需要一个不同的参数，否则可能会返回同样的验证码
        	//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。
        obj.src="${base}/rand.do?d="+timenow;
	}
	
//<!--设定定向标-->
function change_d(type){
  var obj=document.getElementsByName('isDxb1');  
  for(var i=0; i<obj.length; i++){
    if(obj[i].checked) {
    	$("#pwd").removeClass("input1").addClass("input2");
    	jQuery("#pwd").attr("disabled",false); 
    	document.getElementById("isDxb").value=obj[i].value;
    }else{
    	$("#pwd").removeClass("input2").addClass("input1");
    	jQuery("#pwd").attr("disabled",true);
    	document.getElementById("isDxb").value=0;
    	document.getElementById("pwd").value="";
    }
   }
}	
	
	</script>
	
</head>
 <#--
<body onload="checkimg()">
-->
<body class="body_bg">
	<#include "/content/common/header.ftl">

<div class="content">
  <div style="width:955px; margin:0 auto; padding-bottom:20px;">
	<#include "/content/common/user_center_header.ftl">
 <div style="width:955px; float:left; background:#fff; clear:both;">
	<#include "/content/common/user_center_left.ftl">
	<div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
      <div style="padding-top:30px;">
        <a href="" style="color:#646464;font-family:'宋体';">我的账户</a>
        <a style="color:#646464;font-family:'宋体'; padding:0px 4px;">></a>
        <a href="" style="color:#646464;font-family:'宋体';">项目管理</a>
      </div>
      <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
        <a href="" class="hr hre">项目发布</a>
      </div>	
    
       	 	<form id="raiseForm" name="raiseForm" method="post" action="${base}/borrow/saveBorrowCrowd.do"  onsubmit="return check_form_promote();" enctype="multipart/form-data" >
				 <input type="hidden" name="borrow.award" id="award" value="0" />
				 <input type="hidden" name="borrow.isDxb" id="isDxb" value="0" />
				 <input type="hidden" name="number" id="number" value="0" />
				 <input type="hidden" name="borrow.borStages" id="borStages" value="0" />
				 <input type="hidden" name="borrow.type" id="borrowType" value="${borrowType}" />
				 <input type="hidden" name="borrow.isday" id="borrowIsday" value="${borrowIsday}" />
    <div style="border:0px solid #c3c3c3;">
		<div class="base_frm" style="padding-top:30px;padding-bottom:30px; background:#fff;">
			<table >
              <tr>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="120">业务类型：</td>
                <td>
                	<select id="isVouch" name="borrow.isVouch"  style="width:186px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;">
						<@listing_childlist sign="borrow_business_type"; listingList>
							<#list listingList as listing>
								<option value="${listing.keyValue}" >${listing.name}</option>
							</#list>
						</@listing_childlist>
					</select>
				</td>
              </tr>
              <tr height="10"><td></td><td></td></tr>
              <tr>
					<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="120">项目类型：</td>
					<td style="">
						<#if borrowType==14>天标
						<#elseif borrowType==15>月标
						<#elseif borrowType==13>流转标
						<#else>&nbsp;</#if>
					</td>
				</tr>
				<tr height="10"><td></td><td></td></tr>
              <tr>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="120">项目编号：</td>
                <td><input style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"id = "businessCode" name="borrow.businessCode" type="text" size="35"/></td>
              </tr>
              <tr>
               <tr height="10"><td></td><td></td></tr>
              <tr>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="120">项目标题：</td>
                <td><input  id="borrowname" name="borrow.name" type="text" size="35" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/></td>
              </tr>
 				 <tr height="10"><td></td><td></td></tr>
              <tr>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="120">募集金额：</td>
                <td><input name="borrow.account" id="account"  value="${(borrow.account)!}" onkeyup="value=value.replace(/[^0-9]/g,'')" type="text" size="35" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>元
                <img src="${base}/static/img/qs.png" width="20" height="20" class="kaqu_e5"/></td>
              </tr>
               <tr height="10"><td></td><td></td></tr>
                 <tr>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="120">车辆评估价：</td>
                <td><input name="borrow.accountYes" id="accountYes"  value="${(borrow.accountYes)!}" onkeyup="value=value.replace(/[^0-9]/g,'')" type="text" size="35" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>元
                <img src="${base}/static/img/qs.png" width="20" height="20" class="kaqu_e5"/></td>
              </tr>
               <tr height="10"><td></td><td></td></tr>
              <tr>
				<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">投资有效期：</td>
				<td style="">
				<select id="validTime" name="borrow.validTime" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;">
					<@listing_childlist sign="borrow_valid_time"; listingList>
						<#list listingList as listing>
							<option value="${listing.description}" >${listing.name}</a></option>
						</#list>
					</@listing_childlist>
				</select>
					&nbsp;天&nbsp;
				</td>
			  </tr><tr height="10"><td></td><td></td></tr>
							
			<#if borrowIsday==0>
			<tr>
				<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">年利率：</td>
				<td style=""><input type="text" name="borrow.apr" id="apr"  value="${(borrow.apr)!}" onkeyup="clearNoNum(this)" onblur="commit_year2month('apr','monthapr');loadRepaymentPlan();"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
					&nbsp;&nbsp;%，月利率为<span id="monthapr">0</span>%&nbsp;&nbsp; 
				</td>
			</tr><tr height="20"><td></td><td></td></tr>
			<#elseif borrowIsday==1>
			<tr>
				<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">天利率：</td>
				<td style=""><input type="text" name="borrow.apr" id="apr" value="${(borrow.apr)!}" onKeyUp="value=value.replace(/[^0-9.]/g,'')" onblur="commit_day2year('apr','yearapr');loadRepaymentPlan();" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
					&nbsp;&nbsp;%，年利率为<span id="yearapr">0</span>%。&nbsp;&nbsp; 
				</td>
			</tr><tr height="20"><td></td><td></td></tr>
			</#if>
              <tr>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="120">最小支持金额：</td>
                <td>
					<select id="lowestAccount" name="borrow.lowestAccount" class="kaqu_r3">
						<@listing_childlist sign="borrow_lowest_account"; listingList>
							<#list listingList as listing>
								<#if listing.description=="100">
								<#else>
									<option value="${listing.description}">${listing.name}</option>
								</#if>
							</#list>
						</@listing_childlist>
					</select>
				</td>
              </tr>
              <tr>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="120">最大支持金额：</td>
                <td>
                 	<select id="mostAccount" name="borrow.mostAccount" class="kaqu_r3" >
						<option value="" selected="selected">不限</option>
						<@listing_childlist sign="borrow_most_account"; listingList>
							<#list listingList as listing>
								<option value="${listing.description}" >${listing.name}</option>
							</#list>
						</@listing_childlist>
					</select>
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              <tr>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="120">支持奖励：</td>
                <td>
	            	<input type="checkbox"  name="award1" id="award1" onclick="change_award(1)" value="1" />是否启用，
					<input type="radio" name="borrow.awardType" id="awardType0" value="0" checked="">现金奖励
					<input type="radio" name="borrow.awardType" id="awardType1" value="1" >红包奖励
					<input type="text" id="funds" name="borrow.funds" disabled value="" onkeyup="clearNoNum(this)" size="10" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>%
				</td>
              </tr>
              <tr height="10"><td></td><td></td></tr>
               
              <tr>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="120">借款时长：</td><#-- 有效天数 -->
                <td>
                 	<input type="text" name ="borrow.timeLimit" id="timeLimit" onblur="loadRepaymentPlan();" onkeyup="value=value.replace(/[^0-9]/g,'')"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
									&nbsp;<#if borrowIsday==0>个月<#elseif borrowIsday==1>天</#if>
                </td>
              </tr> 
              <#if borrowIsday==0>
					<input type="hidden" name="borrow.divides" id="borrowDivides" value="0"/>
			  <#elseif borrowIsday==1>
              <tr>
				<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">还款期数:</td>
				<td style="">
					<select class="kaqu_r3" name="borrow.divides" id="borrowDivides" onchange="loadRepaymentPlan();" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" >
						<option value="1">1期</option>
						<option value="2">2期</option>
						<option value="3">3期</option>
						<option value="4">4期</option>
						<option value="5">5期</option>
						<option value="6">6期</option>
					</select>
				</td>
			</tr><tr height="10"><td></td><td></td></tr>
				</#if>
			<tr>
				<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">还款方式：</td>
				<td style="">
				<select class="kaqu_r3" name="borrow.style" id="borrowStyle" onchange="loadRepaymentPlan();" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" >
					<option value="1">分期付息</option>
					<option value="2">到期付本息</option>
					<option value="3">等额本金</option>
				</select>
			</td>
		 </tr><tr height="10"><td></td><td></td></tr>
			<tr>
				<td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';"  >还款计划：</td>
								<td>
									<table width="99%" class="base_tac" bordercolor="#cfcfcf" cellpadding="0" cellspacing="0" style="border:1px solid #ccc;">
										<thead>
											<tr height="30" >
												<th style="border-bottom:1px solid #ccc;" ><a>期数</a></th>
												<th style="border-bottom:1px solid #ccc;" ><a>还款日期</a></th>
												<th style="border-bottom:1px solid #ccc;" ><a>还款总额</a></th>
												<th style="border-bottom:1px solid #ccc;" ><a>还款本金</a></th>
												<th style="border-bottom:1px solid #ccc;" ><a>还款利息</a></th>
											</tr>
										</thead>
										<tbody  id="planTable">
										</tbody>
									</table>
								</td>
					</tr><tr height="10"><td></td><td></td></tr>
						<tr>
								<td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">是否定向：</td>
								<td style="">
									<input type="checkbox" name="isDxb1" id="isDxb1" onclick="change_isDxb(1)" value="1"/>&nbsp;&nbsp;是，定向密码为：&nbsp;&nbsp;<input type="text" id="pwd" name="borrow.pwd" disabled value="" size="10"style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
									
								</td>
							</tr>
            </table> 
             <#-- -----------------table1 over    -------------------->
             
         <h3>车辆基本信息</h3>
           
			<table >
              <tr>
              	<td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">车辆品牌：</td>
                <td>
                	<select name = "carInfoJson.carBrand"  id = "sel_brand_id" class = "sel_brand_class">
                		<option>请选择</option>
                	</select>
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">车系：</td>
                <td>
                	<select name = "carInfoJson.carSeries" id = "sel_series_id" class = "sel_series_class">
                		<option value="0">请选择</option>
                	</select>
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">车型：</td>
                <td>
                	<select name = "carInfoJson.carSpec"  class = "sel_spec_class">
                		<option value="0">请选择</option>
                	</select>
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">上牌时间：</td>
                <td>
                	<select name = "carTime.onCardTimeYear"  class = "sel_year_class">
                		<option value="0">请选择</option>
                	</select> 年 
                	<select name = "carTime.onCardTimeMonth" class = "sel_month_class">
                		<option value="0">请选择</option>
            		</select> 月	
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">发证时间：</td>
                <td>
                	<select name = "carTime.certificationTimeYear"  class = "sel_year_class">
                		<option value="0">请选择</option>
                	</select> 年 
                	<select name = "carTime.certificationTimeMonth" class = "sel_month_class">
                		<option value="0">请选择</option>
            		</select> 月	
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
          
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">行驶里程：</td>
                <td>
                	<input type="text" name="carInfoJson.roadHaul"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" size="35"/>
                </td>
              </tr>
              <tr height="10"><td></td><td></td></tr> 
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">车辆识别号：</td>
                <td>
                	<input type="text"  name="carInfoJson.vin"   style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" size="35"/>
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">发动机号：</td>
                <td>
                	<input type="text"  name="carInfoJson.engineNumber"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" size="35"/>
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">保修到期：</td>
                <td>
                	<select name = "carTime.warrantyExpiresTimeYear"  class = "sel_year_class">
                		<option value="0">请选择</option>
                	</select> 年 
                	<select name = "carTime.warrantyExpiresTimeMonth" class = "sel_month_class">
                		<option value="0">请选择</option>
            		</select> 月	
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">交强险到期：</td>
                <td>
                	<select name = "carTime.insurancExpiresTimeYear"  class = "sel_year_class">
                		<option value="0">请选择</option>
                	</select> 年 
                	<select name = "carTime.insurancExpiresTimeMonth" class = "sel_month_class">
                		<option value="0">请选择</option>
            		</select> 月	
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">商业险到期：</td>
                <td>
                	<select name = "carTime.commercialExpiresTimeYear"  class = "sel_year_class">
                		<option value="0">请选择</option>
                	</select> 年 
                	<select name = "carTime.commercialExpiresTimeMonth" class = "sel_month_class">
                		<option value="0">请选择</option>
            		</select> 月	
                </td>
          	</tr> 
              <tr height="10"><td></td><td></td></tr>
              
              
              
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">变速箱：</td>
                <td>
                	<input type="text" name="carInfoJson.gearbox"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" size="35"/>
                </td>
              </tr>
              <tr height="10"><td></td><td></td></tr> 
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">排量：</td>
                <td>
                	<input type="text" name="carInfoJson.cc"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" size="35"/>
                </td>
              </tr>
              <tr height="10"><td></td><td></td></tr> 
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">车辆颜色：</td>
                <td>
                	<input type="text" name="carInfoJson.vehicleColor"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" size="35"/>
                </td>
              </tr>
              <tr height="10"><td></td><td></td></tr> 
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">车身结构：</td>
                <td>
                	<input type="text" name="carInfoJson.structure"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" size="35"/>
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">环保标准：</td>
                <td>
                	<input type="text" name="carInfoJson.environmental"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" size="35"/>
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">用途：</td>
                <td>
                	<input type="text" name="carInfoJson.use"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" size="35"/>
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              
              <tr>
                <td class="text_r org grayBg">*</td>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">车辆描述：</td>
                <td><textarea class="textarea" name="borrow.content" style="width:400px">${(borrow.content)!}</textarea></td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
             
              
              <tr>
                <td class="text_r org grayBg"></td>
          	    <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="80">车辆照片：</td>
                <td><input type="button" value="新增照片" class = "btn3 white" onclick="addImgCar();">
					<input type="hidden" id="idCar" value="0"/>
					<img src="${base}/static/img/qs.png" width="20" height="20" title="新增照片"/ class="kaqu_e5"></td>
              </tr>
              <tr height="10"><td></td><td></td></tr> 
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg"></td>
                <td>
					<table class="tableShadow"  border="0" cellspacing="1" cellpadding="0" style="width: 65%;">
						<thead>
							<tr>
								<td>照片标题</td>
								<td>操作</td>
								<td>编辑</td>
							</tr>
						</thead>
						<tbody id="table_car">
							
						</tbody>
					</table>
				</td>
     		 </tr> 
     		 <tr height="10"><td></td><td></td></tr>	
              
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">车型链接：</td>
                <td>
                	<input type="text" name="carInfoJson.modelsLink"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" size="35" size="35"/>
                </td>
              </tr>
              <tr height="10"><td></td><td></td></tr> 
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">交易凭证：</td>
                <td><input type="button" value="新增凭证" class = "btn3 white" onclick="addImgItem();">
					<input type="hidden" id="idSort" value="0"/>
					<img src="${base}/static/img/qs.png" width="20" height="20" title="新增凭证"/ class="kaqu_e5">借款合同、担保函、担保合同、营业执照等</td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg"></td>
                <td>
						<table class="tableShadow"  border="0" cellspacing="1" cellpadding="0" style="width: 65%;">
								<thead>
									<tr>
										<td>凭证标题</td>
										<td>操作</td>
										<td>编辑</td>
									</tr>
								</thead>
								<tbody id="table_img">
									
								</tbody>
							</table>
				</td>
         	 </tr> 
         	 <tr height="20"><td></td><td></td></tr>		
             </table> 
             <#-- -----------------table1 over    -------------------->
             
       
   		 
    	  <h3>安全验证</h3>
        	<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
        	<tbody>
			<tr>
				<td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="86">安全密码：</td>
				<td>
					<input type="password" name="safepwd"   style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" size="35" size="35"/>
				</td>
			</tr>
			<tr height="10"><td></td><td></td></tr>
			<tr>
				<td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">验证码：</td>
				<td>
					<input type="text" name="mycode" id="mycode"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" size="35" size="35"/>
					<img id = "code_img" src="${base}/rand.do" onclick="changeValidateCode(this)" title="点击图片重新获取验证码" />
				</td>
			</tr>
			<tr height="20"><td></td><td></td></tr>		
			
         	<tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg"></td>	
            	<td >
	            	
                	 <input type="submit" value="确定" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" />
				          	<input type="reset" value="取消" onclick="window.history.back(); return false;" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#d4d4d4; color:#000; text-align:center; font-size:16px;border-radius:5px;" />
                </td>
              </tr>
         	</tbody>
           </table>  
             
            </form>       
        </div>                
    </div>
    
</div>
<form id="planForm">
	<input type="hidden" name="borrowType" id="plan_borrowType" value="${borrowType}" />
	<input type="hidden" name="borrowAccount" id="plan_borrowAccount"/>
	<input type="hidden" name="borrowApr" id="plan_borrowApr"/>
	<input type="hidden" name="borrowTimeLimit" id="plan_borrowTimeLimit"/>
	<input type="hidden" name="borrowStyle" id="plan_borrowStyle"/>
	<input type="hidden" name="borrowDivides" id="plan_borrowDivides"/>
	<input type="hidden" name="borrowIsday" id="plan_borrowIsday"/>
	</form>

	<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
$(function(){

	var $areaSelect = $("#areaSelect");
	
	// 地区选择菜单
	$areaSelect.lSelect({
			url: "${base}/area/ajaxArea.do"// AJAX数据获取url
	});
	
});
</script>

</html>
