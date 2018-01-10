<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-收益计算器</title>
	<#include "/content/common/meta.ftl">
	
<script type="text/javascript">

var checkBoxPeriodArray = new Array();
var optionPeriodArray = new Array();

function showPlan() {

	<#-- 年利率 -->
	var yearRate = $("#yearRate").val();
	var yearRateNo = parseFloat(yearRate);
	<#-- 月利率 -->
	var monthRate = formateMoney(yearRateNo/12);
	<#-- 投标奖励 -->
	var bonus = $("#bonus").val();
	<#-- 利息管理费 -->
	var fee = $("#fee").val();
	<#-- 投标金额 -->
	var money = $("#money").val();
	<#-- 借款期限 -->
	var period = $("#period").val();
	
	var periodNo = parseInt(period);
	var str = '';
	
	var moneyMoney = money;
	
	var monthMoney = money;
	var monthInterest =0;
	var monthTotal = 0;
	
	<#-- 余额 -->
	var balance =  parseFloat(money);
	
	<#-- 还款(不含最后一期) -->
	for(var i=0;i<(periodNo-1);i++){
		var boxChecked = '';
		if (checkBoxPeriodArray[i]==1) {
			boxChecked = 'checked';
		}
	
		str = str + '<tr>';
		str = str + '  <td><input type="checkbox" id="box_'+i+'" name="planPeriod" onclick="boxClick();" '+boxChecked+'/></td>';
		str = str + '  <td>'+(i+1)+'</td>';
		str = str + '  <td>'+getSelectList(optionPeriodArray[i],0,i)+'</td>';
		str = str + '  <td id="td_totalMoney_'+(i)+'">0.00</td>';
		str = str + '  <td id="td_monthMoney_'+(i)+'"></td>';
		str = str + '  <td id="td_interestMoney_'+(i)+'"></td>';
		str = str + '</tr>';
	}
	
	monthMoney = formateMoney(balance)
	monthInterest = formateMoney(parseFloat(monthMoney)*monthRate*periodNo/100);
	monthTotal = formateMoney(parseFloat(monthMoney)+parseFloat(monthInterest));
	
	<#-- 还款(最后一期) -->
		str = str + '<tr>';
		str = str + '  <td><input type="checkbox" id="box_'+(periodNo-1)+'" name="planPeriod" checked onclick="return false;"/></td>';
		str = str + '  <td>'+(i+1)+'</td>';
		str = str + '  <td>'+getSelectList(optionPeriodArray[i],1,(periodNo-1))+'</td>';
		str = str + '  <td id="td_totalMoney_'+(periodNo-1)+'">'+monthTotal+'</td>';
		str = str + '  <td id="td_monthMoney_'+(periodNo-1)+'">'+monthMoney+'</td>';
		str = str + '  <td id="td_interestMoney_'+(periodNo-1)+'">'+monthInterest+'</td>';
		str = str + '</tr>';
		
	<#-- 小计 -->
	str = str + '<tr>';
	str = str + '  <td colspan="3">小计</td>';
	str = str + '  <td id="counterTotal">'+monthTotal+'</td>';
	str = str + '  <td id="counterMoney">'+monthMoney+'</td>';
	str = str + '  <td id="counterInterest">'+monthInterest+'</td>';
	str = str + '</tr>';
	
	
	KP.options.drag = true;
	KP.show("还款计划设置", 570, 460);
	$(KP.options.content)
	.load(qmd.base+"/content/common/incomeCounter.html",function(){
		 $("#setPlanTable").html("");
		 $("#setPlanTable").html(str);
		 refreshPlan();
	});

}

function selectClick(l) {

	if(document.getElementById("box_"+l).checked==false) {
		$("#selRate_"+l).val(0);
	}

	<#-- 借款期限 -->
	var period = $("#period").val();
	var periodNo = parseInt(period);
	
	var selectLast = 100;
	for(var i=0;i<(periodNo-1);i++){
	
		if(document.getElementById("box_"+i).checked==false) {
			$("#selRate_"+i).val(0);
		} else {
			selectLast = selectLast - parseInt($("#selRate_"+i).val());
		}
	}
	if (selectLast < 10) {
		alert("请输入正确还款比例！");
		$("#selRate_"+l).val(0);
		selectClick(l);
	} else {
		$("#selRate_"+(periodNo-1)).val(selectLast);
		refreshPlan();
	}
	
}

function boxClick() {

	<#-- 借款期限 -->
	var period = $("#period").val();
	var periodNo = parseInt(period);
	
	var selectLast = 100;
	for(var i=0;i<(periodNo-1);i++){
	
		if(document.getElementById("box_"+i).checked) {
			selectLast = selectLast - parseInt($("#selRate_"+i).val());
		} else {
			$("#selRate_"+i).val(0);
		}
	}
	$("#selRate_"+(periodNo-1)).val(selectLast);

	refreshPlan();
	
}

function refreshPlan() {

	<#-- 年利率 -->
	var yearRate = $("#yearRate").val();
	var yearRateNo = parseFloat(yearRate);
	<#-- 月利率 -->
	var monthRate = formateMoney(yearRateNo/12);
	<#-- 投标奖励 -->
	var bonus = $("#bonus").val();
	<#-- 利息管理费 -->
	var fee = $("#fee").val();
	<#-- 投标金额 -->
	var money = $("#money").val();
	var moneyNo =  parseFloat(money);
	<#-- 借款期限 -->
	var period = $("#period").val();
	var periodNo = parseInt(period);
	<#-- 余额 -->
	var balance =  parseFloat(money);
	
	<#-- 有效投资总额 -->
	var totalMonthBalance = balance;

	<#-- 余额比例 -->
	var selectPeriodArray = new Array(periodNo);
	for(var i=0;i<periodNo;i++){
		if(document.getElementById("box_"+i).checked) {
			selectPeriodArray[i] = $("#selRate_"+i).val();
		} else {
			selectPeriodArray[i] = 0;
		}
	}
	
	var moneyPeriodArray = new Array(periodNo);
	for(var i=0;i<periodNo;i++){
		var moneyPeriod = parseFloat(selectPeriodArray[i]) * moneyNo / 100;
		moneyPeriodArray[i] = formateMoney(moneyPeriod);
		$("#td_monthMoney_"+i).html(formateMoney(moneyPeriod));
	}
		
	var interestPeriodArray = new Array(periodNo);
	
	var monthPeriod = 1;
	for(var i=0;i<periodNo;i++){
		if(document.getElementById("box_"+i).checked) {
			var interestPeriod =  parseFloat(monthRate) * parseFloat(balance) * monthPeriod / 100;
			interestPeriodArray[i] = formateMoney(interestPeriod);
			$("#td_interestMoney_"+i).html(formateMoney(interestPeriod));
			
			balance = formateMoney(parseFloat(balance) - parseFloat(moneyPeriodArray[i]));
			
			totalMonthBalance = formateMoney(parseFloat(totalMonthBalance) + parseFloat(balance));
			
			monthPeriod = 1;
		} else {
			interestPeriodArray[i] = "0.00";
			$("#td_interestMoney_"+i).html("0.00");
			totalMonthBalance = formateMoney(parseFloat(totalMonthBalance) + parseFloat(balance));
			monthPeriod = monthPeriod +1;
		}
	}
	
	var totalPeriodArray = new Array(periodNo);
	for(var i=0;i<periodNo;i++){
		var totalPeriod = parseFloat(moneyPeriodArray[i]) + parseFloat(interestPeriodArray[i]);
		totalPeriodArray[i] = formateMoney(totalPeriod);
		$("#td_totalMoney_"+i).html(formateMoney(totalPeriod));
	}
	
	var counterInterest = 0;
	for(var i=0;i<periodNo;i++){
		counterInterest = counterInterest + parseFloat(interestPeriodArray[i]);
	}
	$("#counterInterest").html(formateMoney(counterInterest));
	
	var counterMoney = 0;
	for(var i=0;i<periodNo;i++){
		counterMoney = counterMoney + parseFloat(moneyPeriodArray[i]);
	}
	$("#counterMoney").html(formateMoney(counterMoney));
	
	var counterTotal = 0;
	for(var i=0;i<periodNo;i++){
		counterTotal = counterTotal + parseFloat(totalPeriodArray[i]);
	}
	$("#counterTotal").html(formateMoney(counterTotal));

} 

function incomeCounter() {

	<#-- 年利率 -->
	var yearRate = $("#yearRate").val();
	var yearRateNo = parseFloat(yearRate);
	<#-- 月利率 -->
	var monthRate = formateMoney(yearRateNo/12);
	<#-- 投标奖励 -->
	var bonus = $("#bonus").val();
	<#-- 利息管理费 -->
	var fee = $("#fee").val();
	<#-- 投标金额 -->
	var money = $("#money").val();
	var moneyNo =  parseFloat(money);
	<#-- 借款期限 -->
	var period = $("#period").val();
	var periodNo = parseInt(period);
	<#-- 余额 -->
	var balance =  parseFloat(money);
	
	<#-- 有效投资总额 -->
	var totalMonthBalance = balance;
	
	var show_totalMonthBalance = formateMoney(parseFloat(totalMonthBalance));
	
	<#-- 选中 -->
	checkBoxPeriodArray = new Array(periodNo);
	for(var i=0;i<periodNo;i++){
		if(document.getElementById("box_"+i).checked) {
			checkBoxPeriodArray[i] = 1;
		} else {
			checkBoxPeriodArray[i] = 0;
		}
	}
	
	<#-- 余额比例 -->
	optionPeriodArray = new Array(periodNo);
	for(var i=0;i<periodNo;i++){
		if(checkBoxPeriodArray[i]==1) {
			optionPeriodArray[i] = $("#selRate_"+i).val();
		} else {
			optionPeriodArray[i] = 0;
		}
	}
	
	var moneyPeriodArray = new Array(periodNo);
	
	for(var i=0;i<periodNo;i++){
		var moneyPeriod = parseFloat(optionPeriodArray[i]) * moneyNo / 100;
		moneyPeriodArray[i] = formateMoney(moneyPeriod);
		$("#td_monthMoney_"+i).html(formateMoney(moneyPeriod));
	}
		
	var interestPeriodArray = new Array(periodNo);
	
	var monthPeriod = 1;
	for(var i=0;i<periodNo;i++){
		if(checkBoxPeriodArray[i]==1) {
			var interestPeriod =  parseFloat(monthRate) * parseFloat(balance) * monthPeriod / 100;
			interestPeriodArray[i] = formateMoney(interestPeriod);
			$("#td_interestMoney_"+i).html(formateMoney(interestPeriod));
			
			balance = formateMoney(parseFloat(balance) - parseFloat(moneyPeriodArray[i]));
			
			totalMonthBalance = formateMoney(parseFloat(totalMonthBalance) + parseFloat(balance));
			
			if (parseFloat(balance) > 0) {
				show_totalMonthBalance = show_totalMonthBalance + "+" + formateMoney(parseFloat(balance));
			}
			
			monthPeriod = 1;
		} else {
			interestPeriodArray[i] = "0.00";
			$("#td_interestMoney_"+i).html("0.00");
			totalMonthBalance = formateMoney(parseFloat(totalMonthBalance) + parseFloat(balance));
			if (parseFloat(balance) > 0) {
				show_totalMonthBalance = show_totalMonthBalance + "+" + formateMoney(parseFloat(balance));
			}
			monthPeriod = monthPeriod +1;
		}
	}
	
	var totalPeriodArray = new Array(periodNo);
	for(var i=0;i<periodNo;i++){
		var totalPeriod = parseFloat(moneyPeriodArray[i]) + parseFloat(interestPeriodArray[i]);
		totalPeriodArray[i] = formateMoney(totalPeriod);
		$("#td_totalMoney_"+i).html(formateMoney(totalPeriod));
	}
	
	
	var counterInterest = 0;
	for(var i=0;i<periodNo;i++){
		counterInterest = counterInterest + parseFloat(interestPeriodArray[i]);
	}
	
	var counterMoney = 0;
	for(var i=0;i<periodNo;i++){
		counterMoney = counterMoney + parseFloat(moneyPeriodArray[i]);
	}
	
	var counterTotal = 0;
	for(var i=0;i<periodNo;i++){
		counterTotal = counterTotal + parseFloat(totalPeriodArray[i]);
	}
	
	<#-- 展示table -->
	var strTable = '';
	strTable = strTable + '<tr>';
	strTable = strTable + '	<th>还款日期</th>';
	strTable = strTable + '	<th>还款比例</th>';
	strTable = strTable + '	<th>还款总额</th>';
	strTable = strTable + '	<th>还款本金</th>';
	strTable = strTable + '	<th>还款利息</th>';
	strTable = strTable + '</tr>';
	<#-- 初始化表格行 -->
	for(var i=0;i<(periodNo);i++){
		strTable = strTable + '<tr>';
		strTable = strTable + '  <td style="text-align:center;">第'+(i+1)+'月</td>';
		strTable = strTable + '  <td style="text-align:center;">'+optionPeriodArray[i]+'%</td>';
		strTable = strTable + '  <td style="text-align:right;">'+formateMoney(totalPeriodArray[i])+'</td>';
		strTable = strTable + '  <td style="text-align:right;">'+formateMoney(moneyPeriodArray[i])+'</td>';
		strTable = strTable + '  <td style="text-align:right;">'+formateMoney(interestPeriodArray[i])+'</td>';
		strTable = strTable + '</tr>';
	}	
	strTable = strTable + '<tr>';
	strTable = strTable + '  <td colspan="2" style="text-align:center;"><strong>小计</strong></td>';
	strTable = strTable + '  <td style="text-align:right;">'+formateMoney(counterTotal)+'</td>';
	strTable = strTable + '  <td style="text-align:right;">'+formateMoney(counterMoney)+'</td>';
	strTable = strTable + '  <td style="text-align:right;">'+formateMoney(counterInterest)+'</td>';
	strTable = strTable + '</tr>';
	
	$("#planTable").html(strTable);
	
	<#-- 奖励收益 -->
	var showIncomeBonus = formateMoney(moneyNo * parseFloat(bonus)/100);
	$("#showIncomeBonus").html(showIncomeBonus);
	<#-- 利息收益 -->
	var showIncomeInterest = formateMoney(counterInterest * (100 - parseFloat(fee)) / 100);
	$("#showIncomeInterest").html(showIncomeInterest);
	<#-- 总收益 -->
	var showIncomeTotal = formateMoney(parseFloat(showIncomeBonus) + parseFloat(showIncomeInterest));
	$("#showIncomeTotal").html(showIncomeTotal);
	
	<#-- 平均月投资额 -->
	var aveMoneyMonth = formateMoney(parseFloat(totalMonthBalance) / periodNo);
	<#-- 平均月收益率 -->
	var aveRateMonth = formateMoney(parseFloat(showIncomeTotal) /parseFloat(aveMoneyMonth) / periodNo * 100);
	$("#aveRateMonth").html(aveRateMonth+"%");
	
	show_totalMonthBalance = "【"+showIncomeTotal+"÷("+show_totalMonthBalance+") × 100】";
	$("#show_totalMonthBalance").html(show_totalMonthBalance);
	<#-- 年收益率 -->
	var aveRateYear = formateMoney(parseFloat(aveRateMonth) * 12);
	$("#aveRateYear").html(aveRateYear+"%");
	
	KP.close();
	
}

function onblurFlash() {
	incomeCounterFlash();
}

function periodChange() {

	var period = $("#period").val();
	var periodNo = parseInt(period);
    checkBoxPeriodArray = new Array(periodNo);
 	optionPeriodArray = new Array(periodNo);
 	for(var i =0;i<periodNo;i++) {
 		if (i == (periodNo-1)) {
 			checkBoxPeriodArray[i]=1;
 			optionPeriodArray[i] = 100;
 		} else {
 			checkBoxPeriodArray[i]=0;
 			optionPeriodArray[i] = 0;
 		}
 	}

	showPlan();
}

function incomeCounterFlash() {

	<#-- 年利率 -->
	var yearRate = $("#yearRate").val();
	var yearRateNo = parseFloat(yearRate);
	<#-- 月利率 -->
	var monthRate = formateMoney(yearRateNo/12);
	<#-- 投标奖励 -->
	var bonus = $("#bonus").val();
	<#-- 利息管理费 -->
	var fee = $("#fee").val();
	<#-- 投标金额 -->
	var money = $("#money").val();
	var moneyNo =  parseFloat(money);
	<#-- 借款期限 -->
	var period = $("#period").val();
	var periodNo = parseInt(period);
	<#-- 余额 -->
	var balance =  parseFloat(money);
	
	<#-- 有效投资总额 -->
	var totalMonthBalance = balance;
	
	var show_totalMonthBalance = formateMoney(parseFloat(totalMonthBalance));
	
	var moneyPeriodArray = new Array(periodNo);
	
	for(var i=0;i<periodNo;i++){
		var moneyPeriod = parseFloat(optionPeriodArray[i]) * moneyNo / 100;
		moneyPeriodArray[i] = formateMoney(moneyPeriod);
		$("#td_monthMoney_"+i).html(formateMoney(moneyPeriod));
	}
		
	var interestPeriodArray = new Array(periodNo);
	
	var monthPeriod = 1;
	for(var i=0;i<periodNo;i++){
		if(checkBoxPeriodArray[i]==1) {
			var interestPeriod =  parseFloat(monthRate) * parseFloat(balance) * monthPeriod / 100;
			interestPeriodArray[i] = formateMoney(interestPeriod);
			$("#td_interestMoney_"+i).html(formateMoney(interestPeriod));
			
			balance = formateMoney(parseFloat(balance) - parseFloat(moneyPeriodArray[i]));
			
			totalMonthBalance = formateMoney(parseFloat(totalMonthBalance) + parseFloat(balance));
			if (parseFloat(balance) > 0) {
				show_totalMonthBalance = show_totalMonthBalance + "+" + formateMoney(parseFloat(balance));
			}
			
			monthPeriod = 1;
		} else {
			interestPeriodArray[i] = "0.00";
			$("#td_interestMoney_"+i).html("0.00");
			totalMonthBalance = formateMoney(parseFloat(totalMonthBalance) + parseFloat(balance));
			if (parseFloat(balance) > 0) {
				show_totalMonthBalance = show_totalMonthBalance + "+" + formateMoney(parseFloat(balance));
			}
			monthPeriod = monthPeriod +1;
		}
	}
	
	var totalPeriodArray = new Array(periodNo);
	for(var i=0;i<periodNo;i++){
		var totalPeriod = parseFloat(moneyPeriodArray[i]) + parseFloat(interestPeriodArray[i]);
		totalPeriodArray[i] = formateMoney(totalPeriod);
		$("#td_totalMoney_"+i).html(formateMoney(totalPeriod));
	}
	
	
	var counterInterest = 0;
	for(var i=0;i<periodNo;i++){
		counterInterest = counterInterest + parseFloat(interestPeriodArray[i]);
	}
	
	var counterMoney = 0;
	for(var i=0;i<periodNo;i++){
		counterMoney = counterMoney + parseFloat(moneyPeriodArray[i]);
	}
	
	var counterTotal = 0;
	for(var i=0;i<periodNo;i++){
		counterTotal = counterTotal + parseFloat(totalPeriodArray[i]);
	}
	
	<#-- 展示table -->
	var strTable = '';
	strTable = strTable + '<tr>';
	strTable = strTable + '	<th>还款日期</th>';
	strTable = strTable + '	<th>还款比例</th>';
	strTable = strTable + '	<th>还款总额</th>';
	strTable = strTable + '	<th>还款本金</th>';
	strTable = strTable + '	<th>还款利息</th>';
	strTable = strTable + '</tr>';
	<#-- 初始化表格行 -->
	for(var i=0;i<(periodNo);i++){
		strTable = strTable + '<tr>';
		strTable = strTable + '  <td style="text-align:center;">第'+(i+1)+'月</td>';
		strTable = strTable + '  <td style="text-align:center;">'+optionPeriodArray[i]+'%</td>';
		strTable = strTable + '  <td style="text-align:right;">'+formateMoney(totalPeriodArray[i])+'</td>';
		strTable = strTable + '  <td style="text-align:right;">'+formateMoney(moneyPeriodArray[i])+'</td>';
		strTable = strTable + '  <td style="text-align:right;">'+formateMoney(interestPeriodArray[i])+'</td>';
		strTable = strTable + '</tr>';
	}	
	strTable = strTable + '<tr>';
	strTable = strTable + '  <td colspan="2" style="text-align:center;"><strong>小计</strong></td>';
	strTable = strTable + '  <td style="text-align:right;">'+formateMoney(counterTotal)+'</td>';
	strTable = strTable + '  <td style="text-align:right;">'+formateMoney(counterMoney)+'</td>';
	strTable = strTable + '  <td style="text-align:right;">'+formateMoney(counterInterest)+'</td>';
	strTable = strTable + '</tr>';
	
	$("#planTable").html(strTable);
	
	<#-- 奖励收益 -->
	var showIncomeBonus = formateMoney(moneyNo * parseFloat(bonus)/100);
	$("#showIncomeBonus").html(showIncomeBonus);
	<#-- 利息收益 -->
	var showIncomeInterest = formateMoney(counterInterest * (100 - parseFloat(fee)) / 100);
	$("#showIncomeInterest").html(showIncomeInterest);
	<#-- 总收益 -->
	var showIncomeTotal = formateMoney(parseFloat(showIncomeBonus) + parseFloat(showIncomeInterest));
	$("#showIncomeTotal").html(showIncomeTotal);
	
	<#-- 平均月投资额 -->
	var aveMoneyMonth = formateMoney(parseFloat(totalMonthBalance) / periodNo);
	<#-- 平均月收益率 -->
	var aveRateMonth = formateMoney(parseFloat(showIncomeTotal) /parseFloat(aveMoneyMonth) / periodNo * 100);
	$("#aveRateMonth").html(aveRateMonth+"%");
	
	show_totalMonthBalance = "【"+showIncomeTotal+"÷("+show_totalMonthBalance+") × 100】";
	$("#show_totalMonthBalance").html(show_totalMonthBalance);
	
	<#-- 年收益率 -->
	var aveRateYear = formateMoney(parseFloat(aveRateMonth) * 12);
	$("#aveRateYear").html(aveRateYear+"%");
	
	KP.close();
	
}

function getSelectList(sel,rd,idx) {
	var str = '';
	if (rd==1) {
		str = str + '<select id="selRate_'+idx+'" onfocus="this.blur();" onmouseout="this.releaseCapture();" onmousemove="this.setCapture();">';
	} else {
		str = str + '<select id="selRate_'+idx+'" onchange="selectClick('+idx+')">';
	}
	var gaps = 10;
	
	for (var i =0;i<=10;i++) {
		var line = gaps*i;
		str = str + '<option value="'+(gaps*i)+'" '+getSelected(line,sel)+'>'+line+'%</option>';
	}
	str = str + '</select>';
	
	return str;
}
function getSelected(sel1,sel2) {
	var selNo1 = parseInt(sel1);
	var selNo2 = parseInt(sel2);
	var str = '';
	if (selNo1==selNo2) {
		str = 'selected';
	}
	return str;
}

function formateMoney(x){
	var f_x = parseFloat(x);
	if (isNaN(f_x)) {
		alert('您输入的数据不完整');
		return false;
	}
	var f_x = Math.round(x*100)/100;
	var s_x = f_x.toString();
	var pos_decimal = s_x.indexOf('.');
	if (pos_decimal < 0) {
		pos_decimal = s_x.length;
		s_x += '.';
	}
	while (s_x.length <= pos_decimal + 2) {
		s_x += '0';
	}
	return s_x;
}

$(function(){  
	<#-- 借款期限 -->
	var period = $("#period").val();
	var periodNo = parseInt(period);
    checkBoxPeriodArray = new Array(periodNo);
 	optionPeriodArray = new Array(periodNo);
 	for(var i =0;i<periodNo;i++) {
 		if (i == (periodNo-1)) {
 			checkBoxPeriodArray[i]=1;
 			optionPeriodArray[i] = 100;
 		} else {
 			checkBoxPeriodArray[i]=0;
 			optionPeriodArray[i] = 0;
 		}
 	}
 	incomeCounterFlash();
});

</script>
	
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">

<!--合计收入-->
<input type="hidden" id="heji_all"/>
<!--利息收入-->
<input type="hidden" id="heji_intr"/>
<input type="hidden" id="income_intr"/>
<input type="hidden" id="income_fee"/>
<!--本金-->
<input type="hidden" id="heji_cat"/>

<input type="hidden" id="income_jiang"/>

<!-- 金额累计-->
<input type="hidden" id="jiekuan"/>

<!-- content -->
<div class="member clearfix">

	<ul class="hd clearfix">
		<li class="fl"><h2>收益计算器</h2></li>
	</ul>
	<div class="bd">
	
		<table width="100%">
			<tr>
				<th style="text-align:right;"><strong>借款标类型：</strong></th>
				<td>月标</td>
			</tr>
			<tr>
				<th style="text-align:right;"><strong>年利率：</strong></th>
				<td><input type="text" value="21.6" id="yearRate" onblur="onblurFlash();">%</td>
			</tr>
			<tr>
				<th style="text-align:right;"><strong>投标奖励：</strong></th>
				<td><input type="text" value="1.3" id="bonus" onblur="onblurFlash();">%</td>
			</tr>
			<tr>
				<th style="text-align:right;"><strong>利息管理费：</strong></th>
				<td><input type="text" value="10" id="fee" onblur="onblurFlash();">%</td>
			</tr>
			<tr>
				<th style="text-align:right;"><strong>投标金额：</strong></th>
				<td><input type="text" value="10000" id="money" onblur="onblurFlash();"></td>
			</tr>
			<tr>
				<th style="text-align:right;"><strong>借款期限：</strong></th>
				<td><select id="period" onchange="periodChange();">
						<option value="1">1月</option>
						<option value="2">2月</option>
						<option value="3">3月</option>
						<option value="4">4月</option>
						<option value="5">5月</option>
						<option value="6">6月</option>
				</td>
			</tr>
			<tr>
				<th style="text-align:right;"><strong>还款方式：</strong></th>
				<td><input type="button" id="btnCounter" class="btn s1" style="padding:4px 0 8px;" value="还款计划设置" onclick="showPlan();"/></td>
			</tr>
			<tr>
				<th style="text-align:right;"><strong>还款计划：</strong></th>
				<td><div class="data-list"><table id="planTable"></table></div></td>
			</tr>
			<tr>
				<th style="text-align:right;"></th>
				<td><strong>计算收益</strong></td>
			</tr>
			<tr>
				<th style="text-align:right;"><strong>年化收益率：</strong></th>
				<td>&nbsp;<span id="aveRateYear"></span></td>
			</tr>
			<tr>
				<th style="text-align:right;"><strong>月收益率：</strong></th>
				<td>&nbsp;<span id="aveRateMonth"></span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="show_totalMonthBalance"></span></td>
			</tr>
			<tr>
				<th style="text-align:right;"><strong>利率收益：</strong></th>
				<td>&nbsp;<span id="showIncomeInterest"></span></td>
			</tr>
			<tr>
				<th style="text-align:right;"><strong>奖励收益：</strong></th>
				<td>&nbsp;<span id="showIncomeBonus"></span></td>
			</tr>
			<tr>
				<th style="text-align:right;"><strong>总收益：</strong></th>
				<td>&nbsp;<span id="showIncomeTotal"></span></td>
			</tr>
		</table>
	</div>
</div>
<#include "/content/common/footer.ftl">
</body>
</html>
