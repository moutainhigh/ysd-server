<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>会员列表 </title>
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
				location.href='derive_report!exceFirstTenderList.action'+
				'?username='+encodeURI(encodeURI($("input[name='pager.keyword']").val()))+
				'&startDate='+$("input[name=beginDate]").val()+
				'&endDate='+$("input[name='endDate']").val();
			}
		})
		
		
		
		$RefreshButton=$("#RefreshButton");
		
		$RefreshButton.click(function(){		
			location.href='report_first_tender!refresh.action';
		})
	});	
</script>
</head>
<body class="list">
	<div class="bar">
		首次投资列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="report_first_tender!list.action" method="post">
			<div class="listBar">
				<label>渠道来源: </label>
				<input type="text" name="pager.keyword" value="${pager.keyword!}" />
				<label>投资时间: </label>
					<input type="text" id="beginDate" name="beginDate" class="formText" value="${(beginDate?string("yyyy-MM-dd"))!}" style="width:80px" title="起始时间" onclick="WdatePicker()" />&nbsp;到&nbsp;
				    <input type="text" id="endDate" name="endDate" class="formText" value="${(endDate?string("yyyy-MM-dd"))!}" style="width:80px" title="截止时间" onclick="WdatePicker()" />

				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				<input type="button" id="exceButton" class="formButton" value="导出数据">
				<input type="button" id="RefreshButton" class="formButton" value="刷新">
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th >
						<a href="#" class="sort" name="id" hidefocus>用户ID</a>
					</th>
					<th >
						<a href="#" class="sort" name="" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="sort" name="username" hidefocus>投资时间</a>
					</th>
					<!--
					<th>
						<a href="#" class="sort" name="realName" hidefocus>金额</a>
					</th>
					-->
					<th>
						<a href="#" class="sort" name="" hidefocus>实际投资金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="realName" hidefocus>红包使用总额</a>
					</th>
					<th>
						<a href="#" class="sort" name="realStatus" hidefocus>项目名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="timeLimit" hidefocus>项目期限</a>
					</th>
					<th>
						<a href="#" name="" hidefocus>渠道注册来源</a>
					</th>
					<th>
						<a href="#" name="" hidefocus>投资来源</a>
					</th>
				</tr>
				<#list pager.result as value>
					<tr>
						<td>
							${value.userId}
						</td>
						<td>
							${value.phone}
						</td>
						<td>
							${value.tenderDate?string("yyyy-MM-dd HH:mm:ss")}
						</td>
						<!--
						<td>
							${value.money}
						</td>
						-->
						<td>
							${value.account}
						</td>
						<td>
							${value.hongbaoAmount}
						</td>
						<td>
							${value.borrowName}
						</td>
						<td>
							${value.timeLimit}天
						</td>
						<td>
							${value.placeChilderName!'--'}
						</td>
						<td>
						
						<#if value.clientType = 0>
								pc
							<#elseif value.clientType = 1>	
								安卓
							<#elseif value.clientType = 2>
								ios
							<#elseif value.clientType = 3>
								h5
							<#else >
								新h5
							</#if>
						</td>
				</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="pager">
						<#include "/WEB-INF/template/admin/pager.ftl" />
					</div>
				<div>
			<#else>
				<div class="noRecord">没有找到任何记录!</div>
			</#if>
		</form>
	</div>
</body>
</html>