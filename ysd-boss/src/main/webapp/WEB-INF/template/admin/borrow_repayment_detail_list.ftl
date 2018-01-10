<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>逾期标列表</title>
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
		逾期标列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="borrow_repayment_detail!list.action" method="post">
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
						<a href="#" class="sort" name="id" hidefocus>ID</a>
					</th>						
					<th>
						<a href="#" class="" name="borrow.user.username" hidefocus>借款人</a>
					</th>
					<th>
						<a href="#" class="" name="borrow.name" hidefocus>借款标题</a>
					</th>
					<th>
						<a href="#" class="" name="orderNum" hidefocus>期数</a>
					</th>
					<th>
						<a href="#" class="sort" name="repaymentTime" hidefocus>应还时间</a>
					</th>
					<th>
						<a href="#" class="" name="repaymentAccount" hidefocus>借款本金</a>
					</th>
					<th>
						<a href="#" class="" name="repaymentAccount" hidefocus>应还利息</a>
					</th>
					<th>
						<a href="#" class="sort" name="repaymentAccount" hidefocus>保证金</a>
					</th>
					<th>
						<a href="#" class="sort" name="repaymentAccount" hidefocus>实际金额</a>
					</th>
					<th>
						<a href="#" class="" name="repaymentDays" hidefocus>逾期天数</a>
					</th>					
					<th>
						<span>罚金操作</span>
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
							${(borrowRepaymentDetail.orderNum+1)}/${(borrowRepaymentDetail.borrow.timeLimit)!}
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
							${borrowRepaymentDetail.showDepositMoney}
						</td>
						<td>
							${borrowRepaymentDetail.showRepaymentAccount}
						</td>
						<td>
						<#if borrowRepaymentDetail.repaymentTime>
							${overdue(borrowRepaymentDetail.repaymentTime?string("yyyy-MM-dd"))}天
						</#if>
							
							
						</td>
						<td>
							<#if borrowRepaymentDetail.webstatus = 1>
								已代还
							<#else>	
								还款
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