<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>活动列表 </title>
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
		活动列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="activity!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='activity!add.action'" value="添加活动" hidefocus />
				&nbsp;&nbsp;
				<label>查找: </label>
				<select name="pager.searchBy">
					<option value="title"<#if pager.searchBy == "title"> selected</#if>>
						活动标题
					</option>
				</select>
				<input type="text" name="pager.keyword" value="${pager.keyword!}" />
				&nbsp;&nbsp;
				<label>状态: </label>
				<select name="status" >
					<option value="">请选择...</option>					
					<option value="0"<#if status==0>selected</#if>>未开始</option>
					<option value="1"<#if status==1>selected</#if>>进行中</option>
					<option value="2"<#if status==2>selected</#if>>已结束</option>
					<option value="-1"<#if status==-1>selected</#if>>已下架</option>
					
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
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<a href="#" class="sort" name="id" hidefocus>编号</a>
					</th>
					<th>
						<a href="#" class="sort" name="title" hidefocus>活动标题</a>
					</th>
					<th>
						<a href="#" class="sort" name="startTime" hidefocus>开始时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="endTime" hidefocus>结束时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="status" hidefocus>状态</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as activity>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${activity.id}" />
						</td>
						<td>
							${activity.id}
						</td>
						<td>
							${substring(activity.title, 30, "...")}
						</td>
						<td>
							${activity.startTime?string("yyyy-MM-dd HH:mm:ss")}
						</td>
						<td>
							${activity.endTime?string("yyyy-MM-dd HH:mm:ss")}
						</td>
						<td>
							<#if activity.status == 0>
							未开始
							<#elseif activity.status == 1>
							进行中
							<#elseif activity.status == 2>
							已结束
							<#elseif activity.status == -1>
							已下架
							</#if>
						</td>
						<td>
							<a href="activity!edit.action?id=${activity.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="activity!delete.action" value="删 除" disabled hidefocus />
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