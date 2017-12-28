<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理中心首页</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
	$().ready( function() {
	
		$exceButton=$("#exceButton");
		
		$exceButton.click(function(){
			$.dialog({type: "warn", content: "确定导出搜索条件下记录？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			
			function right(){
				location.href='derive_report!execReportAverageDaily.action'+
				'?pager.searchBy='+$("#search_sel").val()+
				'&pager.keyword='+$("input[name='pager.keyword']").val()+
				'&startDate='+$("input[name=startDate]").val()+
				'&endDate='+$("input[name='endDate']").val();
			}
		})
	});	
	
	
</script>
</head>
<body class="index">
	<div class="bar">
		账户统计信息
	</div>
	<form id="listForm" action="report_statistics_daily!index.action" method="post">
		<div class="listBar" style=" background-color: #E9F0F4;padding: 3px 8px; ">
		<label>时间：</label>
				<input type="text" id="startDate" name="dateBegin" class="formText" value="${(dateBegin?string("yyyy-MM-dd"))!}" style="width:135px" title="起始时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />&nbsp;到&nbsp;
				 <input type="text" id="endDate" name="dateEnd" class="formText" value="${(dateEnd?string("yyyy-MM-dd"))!}" style="width:135px" title="截止时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					
				<input type="submit" id="searchButton" class="formButton" value="搜 索" hidefocus />
				
				<#--<input type="button" id="exceButton" class="formButton" value="导出数据">-->
		</div>
	<div class="body">

		<div class="bodyLeft" style="width:95%;">
			<table class="listTable">
				<tr>
					<th colspan="2">
						统计期间
					</th>
				</tr>
				<tr>
					<td width="110">
						起始时间: 
					</td>
					<td>
						${(dateBegin?string("yyyy-MM-dd"))!}
						
					</td>
				</tr>
				<tr>
					<td>
						截止时间: 
					</td>
					<td>
						${(dateEnd?string("yyyy-MM-dd"))!}
					</td>
				</tr>
			</table>
			</form>
			<div class="blank"></div>
			<table class="listTable">
				<tr>
					<th colspan="7">
						投资者资金统计
					</th>
				</tr>
				<tr>
					<th>日期</th>
					<th>总额</th>
					<th>可用</th>
					<th>续投</th>
					<th>冻结</th>
					<th>待收本金</th>
					<th>待收利息</th>
				</tr>
<#list dataList as item>
				<tr>
					<td width="130">
						${item.dailyWorkDateInt}
					</td>
					<td>${item.total?string("currency")}</td>
					<td>${item.ableMoney?string("currency")}</td>
					<td>${item.continueTotal?string("currency")}</td>
					<td>${item.unableMoney?string("currency")}</td>
					<td>${item.investorCollectionCapital?string("currency")}</td>
					<td>${item.investorCollectionInterest?string("currency")}</td>
				</tr>
				
</#list>
			
			</table>
			<div class="blank"></div>
		
			
		</div>
		
		<div class="bodyRight">
			
			<div class="blank"></div>
			
		</div>
		<!--<p class="copyright">Copyright © 2005-2011 shopxx.net All Rights Reserved.</p>-->
	</div>
</body>
</html>