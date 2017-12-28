<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>渠道管理列表 </title>
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
		渠道管理列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="place!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='place!input.action'" value="新增渠道" hidefocus />

			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="sort" name="" hidefocus>编号</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>渠道名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>活动数量</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>备注</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as place>
					<tr>
						<td>
							${place.id}
						</td>
						<td>
							${place.name}
						</td>
						<td>
							${place.size!'0'}
						</td>
						<td>
							${place.remark}
						</td>
						<td>
							<a href="place!childerinput.action?id=${place.id}" title="发布活动">[发布活动]</a>
							<a href="place!childerList.action?id=${place.id}" title="查看活动">[查看活动]</a>
							<a href="place!edit.action?id=${place.id}" title="编辑">[编辑]</a>
							<#-->
							<a href="place!delete.action?id=${place.id}" title="删除">[删除]</a>
							-->
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