<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>在线预约列表 </title>
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
		在线预约列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="online_booking!list.action" method="post">
			<div class="listBar">
				<label>查&nbsp;&nbsp;找: </label>
				<select name="pager.searchBy">
					<option value="name"<#if pager.searchBy == "name"> selected</#if>>
						姓名
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
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<a href="#" class="sort" name="name" hidefocus>姓名</a>
					</th>
					<th>
						<a href="#" class="sort" name="sex" hidefocus>性别</a>
					</th>
					<th>
						<a href="#" class="sort" name="phone" hidefocus>手机号</a>
					</th>
					<th>
						<a href="#" class="" name="department" hidefocus>借款原因</a>
					</th>
					<th>
						<a href="#" class="" name="loginDate" hidefocus>借款周期</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>预约时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="modifyDate" hidefocus>联系时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="isConnection" hidefocus>是否联系</a>
					</th>
					<th>
						<a href="#"  hidefocus>操作</a>
					</th>
				</tr>
				<#list pager.result as onlingBooking>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${onlingBooking.id}" />
						</td>
						<td>
							${onlingBooking.name}
						</td>
						<td>
							<#if onlingBooking.sex =1>男<#else>女</#if>
						</td>
						<td>
							${(onlingBooking.phone)!}
						</td>
						<td>
							<@listing_childname sign="borrow_use" key_value ="${(onlingBooking.borrowReason)!}"; name>
								${name}
							</@listing_childname>
						</td>
						<td>
							${(onlingBooking.loanPeriod)!}
						</td>
						<td>
							<span title="${onlingBooking.createDate?string("yyyy-MM-dd HH:mm:ss")}">${onlingBooking.createDate}</span>
						</td>
						<td>
							<span title="${onlingBooking.modifyDate?string("yyyy-MM-dd HH:mm:ss")}">${onlingBooking.modifyDate}</span>
						</td>
						<td>
							<span class="${onlingBooking.isConnection?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							<a href="online_booking!edit.action?id=${onlingBooking.id}&pager.pageNumber=${(pager.pageNumber)!}&pager.orderBy=${(pager.orderBy)!}&pager.order=${(pager.order)!}&pager.searchBy=${(pager.searchBy)!}&pager.keyword=${(pager.keyword)!}" title="查看/处理">[查看/处理]</a>
						</td>
						
						
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="online_booking!delete.action" value="删 除" disabled hidefocus />
					</div>
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