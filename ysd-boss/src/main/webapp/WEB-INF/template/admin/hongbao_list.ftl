<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>系统红包管理</title>
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
		系统红包管理
	</div>
	<div class="body">
		<form id="listForm" action="hongbao!list.action" method="post">
			
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="sort" name="id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="sort" name="operation" hidefocus>红包类型</a>
					</th>
					<th>
						<a href="#" class="sort" name="operator" hidefocus>总金额</a>
					<th>
						<a href="#" class="sort" name="info" hidefocus>状态</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>操作</a>
					</th>
				</tr>
				<#list pager.result as hb>
					<tr>
						<td>
							${hb.id}
						</td>
						<td>
							${hb.typeString}
						</td>
						<td>
							${hb.total}
						</td>
						<td><#if hb.isEnabled ==0>未启用<#else>启用</#if>
						</td>
						<td>
							<a href="hongbao!edit.action?id=${hb.id}">【编辑】</a>
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