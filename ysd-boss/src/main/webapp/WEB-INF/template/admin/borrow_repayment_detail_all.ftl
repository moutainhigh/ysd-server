<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>还款列表</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>

</head>
<body class="list">
	<div class="bar">
		还款列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="borrow_repayment_detail!borrowRepayment.action" method="post">
			<div class="listBar">
				<label>查&nbsp;&nbsp;找: </label>				
				<select name="pager.searchBy">
					<option value="borrow.user.username"<#if pager.searchBy == "borrow.user.username"> selected</#if>>
						用户名
					</option>
					<option value="borrow.name"<#if pager.searchBy == "borrow.name"> selected</#if>>
						标题
					</option> 
				</select>
				<input type="text" name="pager.keyword" value="${pager.keyword!}" />
				还款状态：<select name="status">
							<option value="">所有</option>
							<option value="0" <#if (status)! == '0'> selected</#if>>未还</option>
							<option value="1" <#if (status)! == '1'> selected</#if>>已还</option>
						</select>
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
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="" name="id" hidefocus>ID</a>
					</th>						
					<th>
						<a href="#" class="" name="borrow.user.username" hidefocus>借款人</a>
					</th>
					<th>
						<a href="#" class="" name="borrow.name" hidefocus>借款标题</a>
					</th>
					<th>
						<a href="#" class="" name="orderNum" hidefocus>状态</a>
					</th>
					<th>
						<a href="#" class="" name="orderNum" hidefocus>期数</a>
					</th>
					<th>
						<a href="#" class="" name="repaymentTime" hidefocus>应还时间</a>
					</th>
					<th>
						<a href="#" class="" name="repaymentAccount" hidefocus>借款本金</a>
					</th>
					<th>
						<a href="#" class="" name="repaymentAccount" hidefocus>应还利息</a>
					</th>
					<th>
						<a href="#" class="" name="repaymentDays" hidefocus>应发奖励</a>
					</th>		
					<th>
						<a href="#" class="" name="repaymentAccount" hidefocus>保证金</a>
					</th>			
					<th>
						<span>实际续还金额</span>
					</th>
				</tr>
				<#list pager.result as borrowRepaymentDetail>
					<tr>
						<td>
							${borrowRepaymentDetail.id!}
						</td>
						<td>
						<#if borrowRepaymentDetail.borrow>
							${(borrowRepaymentDetail.borrow.user.username)!}
						</#if>	
						</td>
						<td>
							${(borrowRepaymentDetail.borrow.name)!}
						</td>
						<td>
							<#if borrowRepaymentDetail.status=='0'>未还
							<#elseif borrowRepaymentDetail.status=='1'>已还
							<#else>&nbsp;
							</#if>	
						</td>
						<td>
							${(borrowRepaymentDetail.orderNum+1)}/${(borrowRepaymentDetail.borrow.divides)!}
						</td>
						<td>
							<#if borrowRepaymentDetail.repaymentTime>
								${borrowRepaymentDetail.repaymentTime!?string("yyyy-MM-dd")}
							</#if>
							
						</td>
						<td>
							${borrowRepaymentDetail.capital}
						</td>
						<td>
							${borrowRepaymentDetail.interest}
						</td>
						<td>
							${borrowRepaymentDetail.reminderFee}
						</td>
						<td>
							${borrowRepaymentDetail.showDepositMoney}
						</td>
						<td>
							${borrowRepaymentDetail.showRepaymentAccount}
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