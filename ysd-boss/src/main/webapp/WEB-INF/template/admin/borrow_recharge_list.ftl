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
		<form id="listForm" action="borrow_recharge!list.action" method="post">
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
						<th><a href="#" class="sort" name="id" hidefocus>ID</a></th>	
						<th><a href="#" class="sort" name="id" hidefocus>充值时间</a></th>	
						<th><a href="#" class="sort" name="id" hidefocus>类型</a></th>	
						<th><a href="#" class="sort" name="id" hidefocus>项目</a></th>	
						<th><a href="#" class="sort" name="id" hidefocus>服务商</a></th>	
						<th><a href="#" class="sort" name="id" hidefocus>用户</a></th>	
						<th><a href="#" class="sort" name="id" hidefocus>充值方式</a></th>	
						<th><a href="#" class="sort" name="id" hidefocus>充值金额</a></th>	
						<th><a href="#" class="sort" name="id" hidefocus>状态</a></th>	
						<th><a href="#" class="sort" name="id" hidefocus>操作</a></th>	
					
				</tr>
				<#list pager.result as item>
					<tr>
						<td>
							${item.id!}
						</td>
						<td>
							${item.rechargeDate!}
						</td>
						<td>
							<#if item.type==1>还款充值
							<#elseif item.type==2>退还保证金
							</#if>
						</td>
						<td>
							${item.borrow.name}
						</td>
						<td>
							${item.agency.companyName}
						</td>
						<td>
							${item.user.username}
						</td>
						<td>
							<#if item.rechargeType==1>转账<#elseif item.rechargeType==2>现金</#if>
						</td>
						<td>
							${item.money!}
						</td>
						<td>
							<#if item.status==0>失效
							<#elseif item.status==1>完成
							<#elseif item.status==2>审核中
							<#elseif item.status==3>审核失败
							<#elseif item.status==4>撤回
							<#else>未申请
							</#if>
						</td>
						<td>
							<#if item.status==2><a href="${base}/admin/borrow_recharge!verify.action?id=${item.id}">审核</a><#else>--</#if>
							
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