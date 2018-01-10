<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>审核列表 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
</head>
<#assign flag="借款">
<#if type==0>
	<#assign flag="投资">
</#if>
<body class="list">
	<div class="bar">
		${flag}审核列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="off_line!list.action" method="post">
		<input type="hidden" name = "type" value="${type}" />
			<div class="listBar">
				<label>查&nbsp;&nbsp;找: </label>
				<select name="pager.searchBy">
					<option value="realName"<#if pager.searchBy == "realName"> selected</#if>>
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
					<th>
						<a href="#" class="sort" name="id" hidefocus>编号</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>申请时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="realName" hidefocus>姓名</a>
					</th>
					<th>
						<a href="#" class="sort" name="sex" hidefocus>性别</a>
					</th>
					<th>
						<a href="#" class="sort" name="phone" hidefocus>手机</a>
					</th>
					<th>
						<a href="#" class="sort" name="money" hidefocus>${flag}金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="duration" hidefocus>${flag}时长</a>
					</th>
					<th>
						<a href="#" class="sort" name="rate" hidefocus><#if type==1>意向利率<#else>预期收益</#if></a>
					</th>
					<#if type==1>
						<th>
							<a href="#" class="sort" name="purpose" hidefocus>借款目的</a>
						</th>
					</#if>
					<th>
						<span>状态</span>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as offLine>
					<tr>
						<td>
							${offLine.id}
						</td>
						<td>
							${offLine.createDate?string("yyyy-MM-dd HH:mm:ss")}
						</td>
						<td>
							${offLine.realName}
						</td>
						<td>
							<#if offLine.sex==1>男<#else>女</#if>
						</td>
						<td>
							${offLine.phone}
						</td>
						<td>
							${offLine.money?string.currency}
						</td>
						<td>
							${offLine.duration}
						</td>
						<td>
							${offLine.rate}
						</td>
						<#if type==1>
							<td>
								${offLine.purpose}
							</td>
						</#if>
						<td>
							<#if offLine.status==2>配对中<#elseif offLine.status==1>已完成<#elseif offLine.status==0>拒绝</#if>
						</td>
						<td> 
							<a href="off_line!edit.action?id=${offLine.id}&type=${type}&pager.pageNumber=${(pager.pageNumber)!}&pager.orderBy=${(pager.orderBy)!}&pager.order=${(pager.order)!}&pager.searchBy=${(pager.searchBy)!}&pager.keyword=${(pager.keyword)!}" title="<#if offLine.status==2>审核<#else>查看</#if>">[<#if offLine.status==2>审核<#else>查看</#if>]</a>
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