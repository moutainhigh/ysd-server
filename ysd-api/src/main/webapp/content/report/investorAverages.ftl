<!DOCTYPE html>
<html>
 <head>
	<title>${Application ["qmd.setting.name"]}-我的账户-投资分析</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
	<script src="${base}/static/js/highcharts/highcharts.js"></script>
	
	
<script type="text/javascript">
$(function () {
        $('#container').highcharts({
            chart: {
                type: 'line',
                marginRight: 25,
                marginBottom: 25
            },
            title: {
                text: '投资月均',
                x: -20, //center
                style: {
					color: '#9C0E0E',
					fontWeight: 'bold'
				}
            },
            xAxis: {
                categories: [
                <#list viewInvestorAverageMonthList as view>
                	'${view.investorMonth}月'<#if view_has_next>,</#if>
                </#list>
                ],
                lineColor: '#D2A572'
            },
            yAxis: {
                title: {
                    text: '金额',
                    style: {
						color: '#9C0E0E',
						fontWeight: 'bold'
					}
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#9C0E0E'
                }]
            },
            tooltip: {
                valueSuffix: '元'
            },
            colors: [
			   '#9C0E0E'
			],
            credits: {
            	enabled: false
            },
            legend: {
            	enabled: false,
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -10,
                y: 100,
                borderWidth: 0
            },
            series: [{
                name: '投资',
                data: [
                <#list viewInvestorAverageMonthList as viewa>
                	${viewa.showAverageMonth}<#if viewa_has_next>,</#if>
                </#list>
				]
            }]
        });
    });
    
 	function ajaxInvestorAverages() {
 		var dd = new Date().getTime();
 		var dd_begin = $("#minDate").val();
 		var dd_end = $("#maxDate").val();
    	$.ajax({
	        type: "post",
	        dataType : "json",
	        data: {firstDate:dd_begin,todayDate:dd_end,d:dd},
	        url: '${base}/user/ajaxInvestorAverages.do',
	        success: function(data, textStatus){
	        	if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
	        		alert("查询失败");
	        		$("#searchResult").html("");
	        	} else if (data.status=="success") {
	        		$("#searchResult").html(data.message);
	        	} else {
		        	alert(data.message);
	        	}
	        },
	        error:function(statusText){
	        	alert(statusText);
	        },
	        exception:function(){
	        	alert(statusText);
	        }
		});
    }
</script>
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">

<!-- content -->
<div class="admin clearfix">

	<#include "/content/common/user_center_left.ftl">

	<!-- right -->
	<div class="admin-con">
		<!-- location -->
		<div class="breadcrumb">
			<a href="${base}/">${Application ["qmd.setting.name"]}</a> &gt; <a href="${base}/userCenter/index.do">我的账户</a> &gt; 投资分析
		</div>
		
		<div class="tab clearfix">
			<ul>
				<li><a href="javascript:void(0);" class="current"><span>日均</span></a></li>
				<li><a href="${base}/user/investorCollection.do"><span>待收明细</span></a></li>
				<li><a href="${base}/user/investDaily.do"><span>投资金额</span></a></li>
			</ul>		
		</div>
		<div class="tab-con special">
			<div class="tar">
				您的本月日均投资额度为：<em class="c1">￥${averageMonth}</em>，排名第 <em class="c1">${averageRank}</em> 位
			</div>
			<!-- grand total  -->
			<!--
			<div class="grand-total tac">
					累计已收利息:<@userAccountDetailSum_by_type userid="${loginUser.id}" type="interest_repayment"; sum>
									${sum?string.currency}
								</@userAccountDetailSum_by_type>&nbsp;&nbsp;
              	         累计利息管理费:<@userAccountDetailSum_by_type userid="${loginUser.id}" type="tender_mange"; sum>
									${sum?string.currency}
								</@userAccountDetailSum_by_type>&nbsp;
				         累计净收利息:<@userAccountDetailSum_by_type_count userid="${loginUser.id}" types="interest_repayment,tender_mange" countType="sub"; sum>
									${sum?string.currency}
								</@userAccountDetailSum_by_type_count>&nbsp;
			</div>
			-->
			<div class="filter">
				统计时间：<input type="text" class = "txt txt_focus" id = "minDate" name="minDate" class="formText" onclick="WdatePicker()" value="${(firstDate)!}" size="10">
				 到<input type="text" class = "txt txt_focus" id = "maxDate" name="maxDate" class="formText" onclick="WdatePicker()" value="${(todayDate)!}" size="10">
				 <input type="button" value="查询" onclick="ajaxInvestorAverages();" class="btn s2"/>  日均：<em class="c1" id="searchResult">${averageMonth}</em>
			</div>
			
			<!-- data list -->
			<div class="data-list">
				<div class="chart" id="container" style="height:200px;">
				</div>
			</div>
	</div>
</div>
</div>
<#include "/content/common/footer.ftl">
<script type="text/javascript">
$(function(){
	$("#investment_analysis").addClass("current");
});
</script>
</body>
</html>
