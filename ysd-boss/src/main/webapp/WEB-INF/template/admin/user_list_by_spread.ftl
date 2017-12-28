<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>邀请好友信息列表 </title>
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
		邀请好友信息列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="user!spreadlist.action" method="post">
			<div class="listBar">
				<label>用户名: </label>
				<input type="text" name="username" value="${username!}" />&nbsp;&nbsp;
				
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				&nbsp;&nbsp;
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th >
						<a href="#" class="sort" name="id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>用户名/手机</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>真实姓名</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>注册时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>邀请人</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>邀请人姓名</a>
					</th>
				</tr>
				<#list pager.result as user>
					<tr>
						<td>
							${user.id}
						</td>
						<td>
							${user.username}/${user.phone}
						</td>
						<td>
							${user.realname}
						</td>
						<td>
							${user.createDate?string("yyyy-MM-dd HH:mm:ss")}
						</td>
						<td>
							${user.spreadUsername}
						</td>
						<td>
							${user.spreadRealname}
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