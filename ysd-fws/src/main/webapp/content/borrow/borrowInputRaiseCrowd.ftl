<!DOCTYPE html>
<html>
  <head>
  <title>${Application ["qmd.setting.name"]}-发布项目</title>
	<#include "/content/common/meta.ftl">
	<script src="${base}/js/borrow/borrowRaise.js"></script>
	<script type="text/javascript" src="${base}/js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${base}/js/common/ajaxImageUpload.js"></script>
	<script type="text/javascript" src="${base}/js/common/swfupload.cookies.js"></script>
	<script type="text/javascript" src="${base}/js/jquery/jquery.lSelect.js"></script>
<!-- 上传必备插件 -->
	<script type="text/javascript" src="${base}/static/js/plugin/swfupload/swfupload.js"></script>
	<script type="text/javascript" src="${base}/static/js/plugin/swfupload/swfupload.queue.js"></script>
	<script type="text/javascript" src="${base}/static/js/plugin/swfupload/swfupload.speed.js"></script>
	<script type="text/javascript" src="${base}/static/js/plugin/swfupload/handlers.js"></script>
<!--必要样式-->
<style type="text/css">
/* reset */
*{margin:0;padding:0;list-style-type:none;}
a{blr:expression(this.onFocus=this.blur())}/*去掉a标签的虚线框，避免出现奇怪的选中区域*/
a,img{border:0;}
body{_background-image:url(about:blank);/*用浏览器空白页面作为背景*/_background-attachment:fixed; /* prevent screen flash in IE6 确保滚动条滚动时，元素不闪动*/ } 
body{font:12px/180% Arial,Lucida,Verdana,"宋体",Helvetica,sans-serif;color:#333;background:#fff;}


.demo{padding:10px;margin:0 auto;width:542px}

.logoupload{height:50px;padding:20px 0 0 0;}
.logoupload .uploadtip{float:left;padding:5px 10px;background:#E8F0FF;border:solid 1px #BCD2FF;line-height:18px;margin:8px 0 0 15px;display:inline;color:#5e5e5e;}
.logoupload .uploadtip b{color:#ff6600;margin:0 5px;}
.logoupload .btnbox{width:143px;height:45px;float:left;}
.logoupload .fl-progress{margin-top:0!important;margin-left:0;}
.logoupload .uploadbtn{float:left;width:145px;height:47px;line-height:999em;overflow:hidden;font-size:14px;display:block;color:#ffffff;}


/* progress-box */
.progress-box{width:160px;margin:10px 0 0 0;clear:both;}
.progress-num{height:30px;line-height:30px;overflow:hidden;font-size:14px;}
.progress-num b{font-weight:400;color:#3366cc;}
.progress-bar{width:160px;height:8px;overflow:hidden;position:relative;background:#dedede;}
.progress-bar .bar-line{position:absolute;left:0;top:0;height:8px;overflow:hidden;background:#007cdc;}

/* batch-pic */
.batch-pic{padding:20px 0 0 5px;overflow-x:hidden;position:relative;}
.batch-pic li{width:152px;height:160px;float:left;position:relative;margin:0 12px 0 0;}
.batch-pic li .p-pic{border:solid 1px #d8d8d8;height:110px;overflow:hidden;}
.batch-pic li .p-pic img{width:170px;}
.batch-pic li .p-des{height:28px;line-height:28px;overflow:hidden;color:#5e5e5e;}
.batch-pic li .p-des span{color:#3366cc;margin:0 5px;}
.batch-pic li .p-text input{padding:3px;width:164px;margin-top:5px;border:solid 1px #d8d8d8;}
.batch-pic li .p-btn{margin:10px 0 0 0;}
.batch-pic li .p-btn .upbtn{width:103px;height:34px;display:block;overflow:hidden;margin:0 auto;}
.batch-pic li .delete-pic{background:url(/static/img/red-close-btn.gif) no-repeat;width:27px;height:27px;overflow:hidden;cursor:pointer;position:absolute;right:-18px;top:25px;}
input[type='checkbox'],input[type='radio'] {vertical-align: middle;}
</style>
<script>
function updateProgress(file) {
	$('.progress-box .progress-bar > div').css('width', parseInt(file.percentUploaded) + '%');
	$('.progress-box .progress-num > b').html(SWFUpload.speed.formatPercent(file.percentUploaded));
}

function initProgress() {
	$('.progress-box').show();
	$('.progress-box .progress-bar > div').css('width', '0%');
	$('.progress-box .progress-num > b').html('0%');
}

function successAction(fileInfo) {
	setUploadImage(fileInfo);
	// 如果上传完成了
	$('.progress-box').hide();
}

function setUploadImage(fileInfo) {;
	var up_path = fileInfo.url;
	var up_value = fileInfo.imgValue;
	var up_width = 100;
	var up_height = 100;
	
	var listEls = $('.batch-pic');
	var innerHtml = 
	'<li>'+
		'<div class="delete-pic"><input id="voucher_1" type="hidden" name="vouchers" value="'+up_value+'"/></div>'+
		'<div class="p-des" style="margin-bottom:10px;">描述*：<input type="text"style="width:105px;height:26px;border:1px solid #ccc;"  name="vouchersTitle" class="kaqu_r3">'+
		'</div>'+
		'<div class="p-pic"><img src="' + up_path + '" width="'+up_width+'" hight="'+up_height+'"/></div>'+ 
	'</li>';
	listEls.find('ul').append(innerHtml);
	initImageListFn();
}

function initImageListFn() {
	$('.batch-pic').find('ul > li .delete-pic').each(function() {
		$(this).unbind('click').click(function() {
			$(this).parent().remove();
		});
	});
	
}
var swfImageUpload;

$().ready(function() {
	var settings = {
		flash_url : "${base}/static/js/plugin/swfupload/swfupload.swf",
		flash9_url : qmd.base+"/fws/static/js/plugin/swfupload/swfupload_fp9.swf",
		upload_url: "${base}/file/sofUpload.do",// 接受上传的地址
		file_size_limit : "2 MB",// 文件大小限制
		file_types : "*.jpg;*.gif;*.png;*.jpeg;",// 限制文件类型
		file_types_description : "Image Files",// 说明，自己定义
		file_upload_limit : 100,
		file_queue_limit : 0,
		file_post_name: "Filedata",
		custom_settings : {},
		debug: false,
		// Button settings
		button_image_url: "/fws/static/js/plugin/swfupload/upload-btn.png",
		button_width: "143",
		button_height: "45 ",
		button_placeholder_id: 'uploadBtnHolder',
		button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
		button_cursor : SWFUpload.CURSOR.HAND,
		button_action: SWFUpload.BUTTON_ACTION.SELECT_FILES,
		
		moving_average_history_size: 40,
		
		// The event handler functions are defined in handlers.js
		swfupload_preload_handler : preLoad,
		swfupload_load_failed_handler : loadFailed,
		file_queued_handler : fileQueued,
		file_dialog_complete_handler: fileDialogComplete,
		upload_start_handler : function (file) {
			initProgress();
			updateProgress(file);
		},
		upload_progress_handler : function(file, bytesComplete, bytesTotal) {
			updateProgress(file);
		},
		upload_success_handler : function(file, data, response) {
			// 上传成功后处理函数
			var fileInfo = eval("(" + data + ")");
			successAction(fileInfo);
		},
		upload_error_handler : function(file, errorCode, message) {
			alert('上传发生了错误！');
		},
		file_queue_error_handler : function(file, errorCode, message) {
			if(errorCode == -110) {
				alert('您选择的文件太大了。');	
			}
		}
	};
	swfImageUpload = new SWFUpload(settings);
	initImageListFn();
		
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
	if(!($("#timeLimit").val()% $("#borrowDivides").val())){
 	}else{
 		alert("请选择正确期数");
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
		else if (checkPositiveInteger($("#account").val())==false) {
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
		}else if($("#eachTime").val()==''){
			alert("借款人信息不能为空，请重新输入!");
			return false;
		}else if($("#useReason").val()==''){
			alert("资金用途不能为空，请重新输入!");
			return false;
		}else if($("#borStages").val()==''){
			alert("还款来源不能为空，请重新输入!");
			return false;
		}else if($("#buserId").val()==''){
			alert("请添加借款人!");
			return false;
		}
		else{
			return true;
		}	
		
	}
function checkimg(){
	var $code_img = $("#code_img");
	$code_img.attr("src", qmd.base+"/rand.do?timestamp" + (new Date()).valueOf());
}

function change_is(type){
  var obj=document.getElementsByName(type);  
  for(var i=0; i<obj.length; i++){
    if(obj[i].checked) {
    	$("#verify_"+type).val(obj[i].value);
    }else{
    	$("#verify_"+type).val(0);
    }
   }
}

	</script>
	
</head>
 <#--
<body onload="checkimg()">
-->
<body class="body_bg">
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_agency.ftl">

<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="${base}/userCenter/index.do">账户中心</a></li>
			<li><a href="#">发布借款</a></li>
            <li><#if borrow.type=14>发布普通项目
            						<#elseif borrow.type=15>月标
            						<#elseif borrow.type=16>发布新手项目
									<#elseif borrow.type=17>体验标
									<#elseif borrow.type=8>抵押质押
									<#elseif borrow.type=9>信用
									<#else>
									</#if></li>
			
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
        	<h3>项目基本信息</h3>
       	 <form id="raiseForm" name="raiseForm" method="post" action="${base}/borrow/saveBorrowCrowd.do"  onsubmit="return check_form_promote();" enctype="multipart/form-data" >
				 <input type="hidden" name="borrow.award" id="award" value="0" />
				 <input type="hidden" name="borrow.isDxb" id="isDxb" value="0" />
				 <input type="hidden" name="number" id="number" value="0" />
				 <#--<input type="hidden" name="borrow.type" id="borrowType" value="${btp}" />-->
				 <input type="hidden" name="borrow.isday" id="borrowIsday" value="${borrowIsday}" />
				 
				 
				 <input type="hidden" id="verify_idCard" name="verifyMessJson.idCard" value="0" />
				 <input type="hidden" id="verify_household" name="verifyMessJson.household" value="0" />
				 <input type="hidden" id="verify_zhengXin" name="verifyMessJson.zhengXin" value="0" />
				 <input type="hidden" id="verify_income" name="verifyMessJson.income" value="0" />
				 <input type="hidden" id="verify_anCase" name="verifyMessJson.anCase" value="0" />
				 <input type="hidden" id="verify_cardDriving" name="verifyMessJson.cardDriving" value="0" />
				 <input type="hidden" id="verify_gouZhi" name="verifyMessJson.gouZhi" value="0" />
				 <input type="hidden" id="verify_guJia" name="verifyMessJson.guJia" value="0" />
				 <input type="hidden" id="verify_gcfp" name="verifyMessJson.gcfp" value="0" />
				 <input type="hidden" id="verify_jdczs" name="verifyMessJson.jdczs" value="0" />
       	 	
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="text_r org grayBg" width="40"></td>
                <td class="text_r grayBg" width="86">项目类型：</td>
				  <td>
					  <select name = "borrow.type">
					  <option value=" 14" >普通标</option>
					  <option value=" 16" >新手标</option>
					  <option value=" 20" >活动标</option>
					</select >
				  </td>
				  <#--sjc 20180111-->
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">项目编号：</td>
                <td><input class="input2 w_252" id = "businessCode" name="borrow.businessCode" type="text" size="35"/></td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">业务类型：</td>
                <td>
                	<select id="isVouch" name="borrow.isVouch"  class="kaqu_w101">
						<@listing_childlist sign="borrow_business_type"; listingList>
							<#list listingList as listing>
								<option value="${listing.keyValue}" >${listing.name}</option>
							</#list>
						</@listing_childlist>
					</select>
                
               </td>
              </tr>
             <#--> <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">担保公司：</td>
                <td>
                 	<input disabled="disabled" class="input1" type="text" value = "<#if agencyDb?if_exists>${agencyDb.companyName}<#else>无</#if>" size="35"/>
				</td>
              </tr> -->
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">借款人：</td>
                <td>
	            	<select class="kaqu_w101" name="borrow.userId" id="buserId">
		                <#list borrowerList as borrower>
							<option value="${borrower.id}">${borrower.username}</option>
						</#list>
					</select>
				</td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">项目标题：</td>
                <td><input class="input2 w_252" id="borrowname" name="borrow.name" type="text" size="35"/></td>
              </tr>
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">借款人信息：</td>
                <td><textarea class="textarea" id="eachTime" name="borrow.eachTime" style="width:400px"></textarea></td>
              </tr> 
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">项目描述：</td>
                <td><textarea class="textarea" id="borrowContentInfo" name="borrow.content" style="width:400px"></textarea></td>
              </tr> 
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">资金用途：</td>
                <td><textarea class="textarea" id="useReason" name="borrow.useReason" style="width:400px"></textarea></td>
              </tr> 
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">还款来源：</td>
                <td><textarea class="textarea" id="borStages" name="borrow.borStages" style="width:400px"></textarea></td>
              </tr> 
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">借款合同类型：</td>
				<td>
					<input  name="borrow.isDo"  value="1" <#if borrow.isDo=='' || borrow.isDo==1 >checked</#if> type="radio" />&nbsp;网站直接出借</label><label><input type="radio" name="borrow.isDo" value="2" <#if borrow.isDo == 2>checked</#if>/>&nbsp;债权转让</label>
				
				</td>
			</tr> 
               <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">审核内容：</td>
	     		 <td>
					<input type="checkbox"  name="idCard" id="idCard" onclick="change_is('idCard')" value="1" style="vertical-align:middle;" /> 身份证
					<#--<input type="checkbox"  name="household" id="household" onclick="change_is('household')" style="vertical-align:middle;" value="1" /> 户口本-->
					<input type="checkbox"  name="zhengXin" id="zhengXin" onclick="change_is('zhengXin')" style="vertical-align:middle;" value="1" /> 征信报告
					<input type="checkbox"  name="income" id="income" onclick="change_is('income')"  style="vertical-align:middle;" value="1" /> 收入证明
					<input type="checkbox"  name="anCase" id="anCase" onclick="change_is('anCase')"  style="vertical-align:middle;" value="1" /> 在执行案件查询
					<input type="checkbox"  name="cardDriving" id="cardDriving" onclick="change_is('cardDriving')"  style="vertical-align:middle;" value="1" /> 车辆行驶证
					<input type="checkbox"  name="gouZhi" id="gouZhi" onclick="change_is('gouZhi')"  style="vertical-align:middle;" value="1" /> 购置税凭证
					<input type="checkbox"  name="guJia" id="guJia" onclick="change_is('guJia')"  style="vertical-align:middle;" value="1" /> 车辆评估报告
					<input type="checkbox"  name="gcfp" id="gcfp" onclick="change_is('gcfp')"  style="vertical-align:middle;" value="1" /> 购车发票
					<input type="checkbox"  name="jdczs" id="jdczs" onclick="change_is('jdczs')"  style="vertical-align:middle;" value="1" /> 机动车登记证书
				</td>
			 </tr>
             </table> 
             <#-- -----------------table1 over    -------------------->
             
         <h3>项目参数设置</h3>
           
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
			 <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="86">项目金额：</td>
                <td><input class="input2 w_252" name="borrow.account" id="account"  value="${borrow.account}" onkeyup="value=value.replace(/[^0-9]/g,'')" onBlur="showRepayPlan();" type="text" size="35"/>元
                <img src="${base}/static/img/qs.png" width="20" height="20" class="kaqu_e5"/></td>
              </tr>
          <!--    
               <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="86">车辆评估价：</td>
                <td><input class="input2 w_252" name="borrow.accountYes" id="accountYes"  value="${borrow.accountYes}" onkeyup="value=value.replace(/[^0-9]/g,'')"  type="text" size="35"/>元
                <img src="${base}/static/img/qs.png" width="20" height="20" class="kaqu_e5"/></td>
              </tr>-->
               <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">投资有效期：</td>
                <td>
	                <select id="validTime" name="borrow.validTime" class="kaqu_w101">
						<@listing_childlist sign="borrow_valid_time"; listingList>
							<#list listingList as listing>
								<option value="${listing.description}" >${listing.name}</a></option>
							</#list>
						</@listing_childlist>
					</select>
				</td>
              </tr>
             <#if borrowIsday==0>
			<tr>
				<td class="text_r org grayBg"></td>
				<td  class="text_r grayBg">年利率：</td>
				<td style=""><input type="text" class="input2 w_252" name="borrow.apr" id="apr"  value="${(borrow.apr)!}" onKeyUp="value=value.replace(/[^0-9.]/g,'')"  onblur="commit_year2month('apr','monthapr');loadRepaymentPlan();"/>
					&nbsp;&nbsp;%，月利率为<span id="monthapr">0</span>%&nbsp;&nbsp; 
				</td>
			</tr><tr height="20"><td></td><td></td></tr>
			<#elseif borrowIsday==1>
			<tr>
				<td class="text_r org grayBg"></td>
				<td  class="text_r grayBg">天利率：</td>
				<td style=""><input type="text" class="input2 w_252" name="borrow.apr" id="apr" value="${(borrow.apr)!}" onKeyUp="value=value.replace(/[^0-9.]/g,'')" onblur="commit_day2year('apr','yearapr');loadRepaymentPlan();"/>
					&nbsp;&nbsp;‰，年利率为<span id="yearapr">0</span>%。&nbsp;&nbsp; 
				</td>
			</tr><tr height="20"><td></td><td></td></tr>
			</#if>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">最小认购数：</td>
                <td>
                	<select id="lowestAccount" name="borrow.lowestAccount" class="kaqu_r3">
						<@listing_childlist sign="borrow_lowest_account"; listingList>
							<#list listingList as listing>
							
								<option value="${listing.description}">${listing.name}</option>
							</#list>
						</@listing_childlist>
					</select>		
				</td>
              </tr>
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">最大认购数：</td>
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
              <tr>
                 <td class="text_r org grayBg"></td>
				<td  class="text_r grayBg">支持奖励：</td>
                <td>
	            	<input type="checkbox"  name="award1" id="award1" onclick="change_award(1)" value="1" />是否启用，
					<input type="radio" name="borrow.awardType" id="awardType0" value="0" checked="">现金奖励
<!--					<input type="radio" name="borrow.awardType" id="awardType1" value="1" >红包奖励-->
					<input type="text" id="funds" name="borrow.funds" disabled value="" onkeyup="clearNoNum(this)" size="10" class="input2 w_252"/>%
				</td>
              </tr>
              <tr height="10"><td></td><td></td></tr>
               
              <tr>
                <td class="text_r org grayBg"></td>
				<td  class="text_r grayBg">借款时长：</td><#-- 有效天数 -->
                <td>
                 	<input type="text" name ="borrow.timeLimit" id="timeLimit" onblur="loadRepaymentPlan();" onkeyup="value=value.replace(/[^0-9]/g,'')"  class="input2 w_252"/>
									&nbsp;<#if borrowIsday==0>个月<#elseif borrowIsday==1>天</#if>
                </td>
              </tr> 
              <#if borrowIsday==0>
					<input type="hidden" name="borrow.divides" id="borrowDivides" value="0"/>
			  <#elseif borrowIsday==1>
              <tr>
				 <td class="text_r org grayBg"></td>
				<td  class="text_r grayBg">还款期数:</td>
				<td style="">
					<select class="kaqu_w101" name="borrow.divides" id="borrowDivides" onchange="loadRepaymentPlan();" >
						<option value="1">1期</option>
						<option value="2">2期</option>
						<option value="3">3期</option>
						<option value="4">4期</option>
						<option value="5">5期</option>
						<option value="6">6期</option>
						<option value="7">7期</option>
						<option value="8">8期</option>
						<option value="9">9期</option>
						<option value="10">10期</option>
						<option value="11">11期</option>
						<option value="12">12期</option>
						<option value="13">13期</option>
						<option value="14">14期</option>
						<option value="15">15期</option>
						<option value="16">16期</option>
						<option value="17">17期</option>
						<option value="18">18期</option>
						<option value="19">19期</option>
						<option value="20">20期</option>
						<option value="21">21期</option>
						<option value="22">22期</option>
						<option value="23">23期</option>
						<option value="24">24期</option>
					</select>
				</td>
				</#if>
			<tr>
				<td class="text_r org grayBg"></td>
				<td  class="text_r grayBg">还款方式：</td>
				<td style="">
				<select class="kaqu_r3" name="borrow.style" id="borrowStyle" onchange="loadRepaymentPlan();" >
					<option value="1">分期付息</option>
					<option value="2">到期付本息</option>
					<option value="3">等额本金</option>
				</select>
			</td>
		 </tr>
		  <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">还款计划：</td>
                <td>
					<font style=" color:#ea5504">*还款计划是以最低利率计算</font>
				</td>
              </tr> 
		  <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg"></td>
                <td>
						<table class="tableShadow"  border="0" cellspacing="1" cellpadding="0" style="width: 35%;background:#fff;">
							<thead>
								<tr>
									<td class="kaqu_r6">还款期数</td>
									<td class="kaqu_r6">还款日期</td>
									<td class="kaqu_r6">还款总额</td>
									<td class="kaqu_r6">还款本金</td>
									<td class="kaqu_r6">还款利息</td>
								</tr>
							</thead>
							<tbody  id="planTable">
							</tbody>
						</table>
				</td>
              </tr> 	
             <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">是否定向：</td>
                <td>
					<input type="checkbox" name="isDxb1" id="isDxb1" onclick="change_isDxb(1)" value="1"/>&nbsp;&nbsp;是，定向密码为：&nbsp;&nbsp;<input type="text" id="pwd" name="borrow.pwd" disabled class="input1 w_252"/>
					<img src="${base}/static/img/qs.png" width="20" height="20" class="kaqu_e5"/>
				</td>
              </tr> 
              </table>
             <#-- -----------------table2 over    -------------------->
             
     		<h3>项目资料</h3>
     		<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
     		<!--
              <tr>
              	<td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">车辆品牌：</td>
                <td>
                	<select name = "carInfoJson.carBrand"  id = "sel_brand_id" class = "sel_brand_class">
                		<option>请选择</option>
                	</select>
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">车系：</td>
                <td>
                	<select name = "carInfoJson.carSeries" id = "sel_series_id" class = "sel_series_class">
                		<option value="0">请选择</option>
                	</select>
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">车型：</td>
                <td>
                	<select name = "carInfoJson.carSpec"  id="carSpec" class = "sel_spec_class">
                		<option value="0">请选择</option>
                	</select>
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">上牌时间：</td>
                <td>
                	<select name = "carTime.onCardTimeYear" id="onCardTimeYear"  class = "sel_year_class">
                		<option value="0">请选择</option>
                	</select> 年 
                	<select name = "carTime.onCardTimeMonth" id="onCardTimeMonth" class = "sel_month_class">
                		<option value="0">请选择</option>
            		</select> 月	
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">发证时间：</td>
                <td>
                	<select name = "carTime.certificationTimeYear" id="certificationTimeYear" class = "sel_year_class">
                		<option value="0">请选择</option>
                	</select> 年 
                	<select name = "carTime.certificationTimeMonth" id="certificationTimeMonth" class = "sel_month_class">
                		<option value="0">请选择</option>
            		</select> 月	
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
          
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">行驶里程：</td>
                <td>
                	<input type="text" name="carInfoJson.roadHaul" id="roadHaul" onKeyUp="value=value.replace(/[^0-9.]/g,'')" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" size="35"/>
                </td>
              </tr>
              <tr height="10"><td></td><td></td></tr> 
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">车辆识别号：</td>
                <td>
                	<input type="text"  name="carInfoJson.vin" id="vin"  class="input2 w_252" size="35"/>
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">车牌编号：</td>
                <td>
                	<input type="text"  name="carInfoJson.carNum" id="carNum" class="input2 w_252" size="35"/>
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">发动机号：</td>
                <td>
                	<input type="text" name="carInfoJson.engineNumber" id="engineNumber" class="input2 w_252" size="35"/>
                </td>
              </tr>
              <tr height="10"><td></td><td></td></tr> 

              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">用途：</td>
                <td>
                	<input type="text" name="carInfoJson.use" id="use" class="input2 w_252" size="35"/>
                </td>
              </tr> 
              <tr height="10"><td></td><td></td></tr>
          
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">车型链接：</td>
                <td>
                	<input type="text" name="carInfoJson.modelsLink"  id="modelsLink" class="input2 w_252" size="35"/>
                </td>
              </tr>
              <tr height="10"><td></td><td></td></tr> 
              -->
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg" style="width:113px;">上传项目资料：</td>
                <td>
                <div class="demo" style="margin:0;">
					<div class="logoupload">
						<div class="btnbox">
							<a id="uploadBtnHolder" class="uploadbtn" href="javascript:;">上传替换</a>
						</div>
						<div class="uploadtip">建议图片尺寸：<b>950x320</b>像素</div>
						
						<div class="progress-box fl-progress" style="display:none;">
							<div class="progress-num">上传进度：<b>0%</b></div>
							<div class="progress-bar"><div style="width:0%;" class="bar-line"></div></div>
						</div>
					</div>
					<div class="batch-pic">
						<ul>
							</ul>
						</div>
				</div>
        	 </tr>
        	</table>
        	 <#-- -----------------table3 over    -------------------->
    	  <h3>安全验证</h3>
        	<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
        	<tbody>
			<tr>
				<td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="86">安全密码：</td>
				<td>
					<input type="password" name="safepwd"  class="input2 w_252" size="35"/>
				</td>
			</tr>
			<tr>
				<td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">验证码：</td>
				<td>
					<input type="text" name="mycode" id="mycode" class="input2 w_252" size="35"/>
					<img id = "code_img" src="${base}/rand.do?d=${commonRandomFlash}" onclick="changeValidateCode(this)" title="点击图片重新获取验证码" />
				</td>
			</tr>
         	<tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg"></td>	
            	<td >
	            	 <input type="submit" value="确定" class="btn1 white"/>
				     <input type="reset" value="返回" onclick="window.history.back(); return false;" class="btn2 ml_15"/>
                </td>
              </tr>
         	</tbody>
           </table>  
            </form>
        </div>                
    </div>
</div>
	<form id="planForm">
	<input type="hidden" name="borrowType" id="plan_borrowType" value="${btp}" />
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
		
		<#if borrow.type=14>$("#li_a_zqlzxm").addClass("li_a_select");
		<#elseif borrow.type=15>$("#li_a_dyzyxm").addClass("li_a_select");
		<#elseif borrow.type=9>$("#li_a_fbxyxm").addClass("li_a_select");
		<#elseif borrow.type=10>$("#li_a_fbtyxm").addClass("li_a_select");
		<#else>
		</#if>
		$(".sssj").attr("id","sssj");
		
	
});
</script>
</html>
