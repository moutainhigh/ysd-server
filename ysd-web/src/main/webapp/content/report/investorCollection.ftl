<!DOCTYPE html>
<html>
 <head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-投资分析</title>
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
                text: '待收明细',
                x: -20,
                style: {
					color: '#9C0E0E',
					fontWeight: 'bold'
				}
            },
            xAxis: {
                categories: [${analysisBean.day}],
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
                    color: '#808080'
                }]
            },
            tooltip: {
                valueSuffix: '元'
            },
            colors: [
			   '#9C0E0E',
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
		      	<#if collectionType =='capital'>
 					name: '待收本金',
                	data: [${analysisBean.capital}]
                <#elseif collectionType =='interest'>
                	name: '待收利息',
                	data: [${analysisBean.interest}]
                <#else>
                	name: '待收总额',
                	data: [${analysisBean.repaymentAccount}]
				</#if>
            }]
        });
    });
    
    
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
				<li><a href="${base}/user/investorAverages.do"><span>日均</span></a></li>
				<li><a href="javascript:void(0);" class="current"><span>待收明细</span></a></li>
				<li><a href="${base}/user/investDaily.do"><span>投资金额</span></a></li>
			</ul>		
		</div>
		<div class="tab-con special">
		<!--
			<div class="tar">
				您的日均投资额度为：<em class="c1">￥10000</em>，排名第 <em class="c1">2</em> 位
			</div>
		-->
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
			<form id="listForm" method="post" action="${base}/user/investorCollection.do" >
			<!-- filter -->
			<div class="filter">
				<!--
				发布时间：<input type="text" class = "txt txt_focus" id = "minDate" name="minDate" class="formText" onclick="WdatePicker()" value="${(minDate)!}">
				 到<input type="text" class = "txt txt_focus" id = "maxDate" name="maxDate" class="formText" onclick="WdatePicker()" value="${(maxDate)!}">
				 关键字<input type="text" class = "txt txt_focus" name = "keywords" value="${keywords}"/>
				-->
				<select name="collectionType" class="sel">
		      		<option value="total"  <#if collectionType ==''||collectionType =='total'>selected</#if> >待收总额</option>
		      		<option value="capital" <#if collectionType =='capital'>selected</#if> >待收本金</option>
		      		<option value="interest" <#if collectionType =='interest'>selected</#if> >待收利息</option>
		     	 </select>
				<input type="submit" value="查询" class="btn s2"  />
			</div>
			</form>
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
