<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#assign str='' />
<#if flag =='0'>
	<#assign str = '余额扣除' />
<#elseif flag =='1'>
	<#assign str = '现金奖励' />
<#elseif flag =='2'>
	<#assign str = '资金转入' />
<#elseif flag =='3'>
	<#assign str = '红包奖励' />
<#elseif flag =='4'>
	<#assign str = '红包扣除' />
<#elseif flag =='5'>
	<#assign str = '增加体验金' />
<#elseif flag =='6'>
	<#assign str = '扣除体验金' />
</#if>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<title>${str}审核</title>
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
				location.href='derive_report!exceRewards.action'+
				'?pager.searchBy='+$("#search_sel").val()+
				'&pager.keyword='+$("input[name='pager.keyword']").val()+
				'&startDate='+$("input[name=startDate]").val()+
				'&endDate='+$("input[name='endDate']").val()+
				'&type=${rewards.type}'+
				'&status='+$("#status_sel").val();
			}
		})
	});	
</script>
</head>
<body class="list">
	<div class="bar">
		${str}审核&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="rewards!<#if rewards.type==0>deductList<#elseif rewards.type==1>rewardList<#elseif rewards.type==2>moneyIntoList</#if>.action" method="post">
		<input type = "hidden" name="rewards.type" value = ${rewards.type}>
			<div class="listBar">
				<label>查&nbsp;&nbsp;找: </label>
				<select name="pager.searchBy" id = "search_sel">
					<option value="user.username"<#if pager.searchBy == "user.username"> selected</#if>>
						用户名
					</option>
				</select>&nbsp;&nbsp;
				<input type="text" name="pager.keyword" value="${pager.keyword!}" />
				&nbsp;&nbsp;
				<label>状&nbsp;&nbsp;态: </label>
				<select name="status" id = "status_sel">
					<option value = "">不限</option>
					<option value="0"<#if rewards.status == 0> selected</#if>>
						失败
					</option>
					<option value="1"<#if rewards.status == 1> selected</#if>>
						成功
					</option>
					<option value="2"<#if rewards.status == 2> selected</#if>>
						审核中
					</option>
				</select>&nbsp;&nbsp;
				<label>添加时间</label>
				<input type="text" id="startDate" name="startDate" class="formText" value="${(startDate?string("yyyy-MM-dd"))!}" style="width:80px" title="起始时间" onclick="WdatePicker()" />&nbsp;到&nbsp;
		     	<input type="text" id="endDate" name="endDate" class="formText" value="${(endDate?string("yyyy-MM-dd"))!}" style="width:80px" title="截止时间" onclick="WdatePicker()" />
				
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				<input type="button" id="exceButton" class="formButton" value="导出数据">
				
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="sort" name="id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="" name="user.username" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>添加时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="modifyDate" hidefocus>处理时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="money" hidefocus>金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="status" hidefocus>状态</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as rewards>
					<tr>
						<td>
							${rewards.id}
						</td>
						<td>
							${(rewards.user.username)!}
						</td>
						<td>
							${(rewards.createDate?string("yyyy-MM-dd HH:mm:ss"))!}
						</td>
						<td>
							${(rewards.modifyTime?string("yyyy-MM-dd HH:mm:ss"))!}
						</td>
						<td>
							${(rewards.money?string.currency)!}
						</td>
						<td>
							<#if rewards.status==0><span class="black">失败<#elseif rewards.status ==1><span class="green">成功<#elseif rewards.status == 2><span class="red">审核中</#if></span>
						</td>
						<td>
							<a href = "rewards!verify.action?id=${rewards.id}"><#if rewards.status == 2 ><span class="red">审核<#else><span class="green">查看</#if></a></span>
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