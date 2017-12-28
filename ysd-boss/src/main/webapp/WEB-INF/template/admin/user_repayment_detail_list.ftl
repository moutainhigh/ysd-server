<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>待收列表</title>
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
				location.href='derive_report!exceUserRepermentDetail.action'+
				'?pager.searchBy='+$("#search_sel").val()+
				'&pager.keyword='+encodeURI(encodeURI($("input[name='pager.keyword']").val()))+
				'&dateType='+$("input[name=dateType][checked]").val()+
				'&startDate='+$("input[name=startDate]").val()+
				'&endDate='+$("input[name='endDate']").val();
			}
		})
	});	
</script>
</head>
<body class="list">
	<div class="bar">
		待收列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="user_repayment_detail!list.action" method="post">
			<div class="listBar">
				<label>查&nbsp;&nbsp;找: </label>
				
				<select name="pager.searchBy" id="search_sel">
					<option value="user.username"<#if pager.searchBy == "user.username"> selected</#if>>
						用户名
					</option>
					<option value="borrow.name"<#if pager.searchBy == "borrow.name"> selected</#if>>
						标题
					</option> 
				</select>
				<input type="text" name="pager.keyword" value="${pager.keyword!}" />
				<input type="text" id="startDate" name="startDate" class="formText" value="${(startDate?string("yyyy-MM-dd"))!}" style="width:80px" title="起始时间" onclick="WdatePicker()" />&nbsp;到&nbsp;
			    <input type="text" id="endDate" name="endDate" class="formText" value="${(endDate?string("yyyy-MM-dd"))!}" style="width:80px" title="截止时间" onclick="WdatePicker()" />
				
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				&nbsp;&nbsp;
				<label>每页显示: </label>
				<select name="pager.pageSize" id="pageSize">
					<option value="10"<#if pager.pageSize == 10> selected</#if>>
						10
					</option>
					<option value="20"<#if pager.pageSize == 20> selected</#if>>
						20
					</option>
					<option value="50"<#if pager.pageSize == 50> selected</#if>>
						50
					</option>
					<option value="100"<#if pager.pageSize == 100> selected</#if>>
						100
					</option>
				</select>
				
				<input type="button" id="exceButton" class="formButton" value="导出数据">
				
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="sort" name="id" hidefocus>ID</a>
					</th>						
					<th>
						<a href="#" class="" name="user.username" hidefocus>投资人</a>
					</th>
					<th>
						<a href="#" class="" name="borrow.name" hidefocus>借款标题</a>
					</th>
					<th>
						<a href="#" class="" name="borrow.timeLimit" hidefocus>项目期限</a>
					</th>
					<th>
						<a href="#" class="" name="orderNum" hidefocus>期数</a>
					</th>
					<th>
						<a href="#" class="sort" name="repaymentTime" hidefocus>应还时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="repaymentAccount" hidefocus>应还金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="placeName" hidefocus>渠道来源</a>
					</th>
					<th>
						<span>还款状态</span>
					</th>
				</tr>
				<#list pager.result as userRepaymentDetail>
					<tr>
						<td>
							${userRepaymentDetail.id!}
						</td>
						<td>
							${(userRepaymentDetail.username)!}
						</td>
						<td>
							${(userRepaymentDetail.borrowName)!}
						</td>
						<td>
							${(userRepaymentDetail.timeLimit)!}天
						</td>
						<td>
							${userRepaymentDetail.borrowPeriods}/${(userRepaymentDetail.borrowDivides)!}
						</td>
						<td>
							<#if userRepaymentDetail.repaymentDate>
								${userRepaymentDetail.repaymentDate!?string("yyyy-MM-dd")}
							</#if>	
						</td>
						<td>
							${userRepaymentDetail.repaymentAccount}
						</td>
						<td>
							${userRepaymentDetail.placeName}
						</td>
						<td>
							<#if userRepaymentDetail.status = 0>
								未还
							<#else>	
								已还
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