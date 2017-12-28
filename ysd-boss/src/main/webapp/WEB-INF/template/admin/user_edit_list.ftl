<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>会员信息编辑 </title>
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
		会员信息编辑&nbsp;总记录数11: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="user!editList.action" method="post">
			<div class="listBar">
				<label>查&nbsp;&nbsp;找: </label>
				<select name="pager.searchBy">
					<option value="username"<#if pager.searchBy == "username"> selected</#if>>
						用户名
					</option>
					<option value="email"<#if pager.searchBy == "email"> selected</#if>>
						E-mail
					</option>
				</select>
				<input type="text" name="pager.keyword" value="${pager.keyword!}" />&nbsp;&nbsp;
				<select name="status">
					<option value="0"<#if status == 0> selected</#if>>全部</option>
					<option value="1"<#if status == 1> selected</#if>>正常</option>
					<option value="2"<#if status == 2> selected</#if>>未启用</option>
					<option value="3"<#if status == 3> selected</#if>>已锁定</option>
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
					<th >
						<a href="#" class="sort" name="id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="sort" name="memberType" hidefocus>会员类型/属性</a>
					</th>
					<th>
						<a href="#" class="sort" name="username" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="sort" name="realName" hidefocus>真实姓名</a>
					</th>
					<th>
						<a href="#" class="sort" name="sex" hidefocus>性别</a>
					</th>
					<th>
						<a href="#" class="sort" name="email" hidefocus>E-mail</a>
					</th>
					<th>
						<a href="#" class="sort" name="phone" hidefocus>手机</a>
					</th>
					<th>
						<a href="#" class="sort" name="cardId" hidefocus>身份证</a>
					</th>
					<th>
						<a href="#" class="sort" name="realStatus" hidefocus>实名认证</a>
					</th>
					<th>
						<a href="#" class="sort" name="isLock" hidefocus>状态</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as user>
					<tr>
						<td>
							${user.id}
						</td>
						<td>
							<#if user.memberType==0>个人<#elseif user.memberType==1>企业</#if>&nbsp;/
							<#if user.typeId==0>投资人<#elseif user.typeId==1>借款人</#if>
						</td>
						<td>
							${user.username}
						</td>
						<td>
							${user.realName}
						</td>
						<td>
							<#if user.sex ==1>男<#else>女</#if>
						</td>
						<td>
							${user.email}
						</td>
						<td>
							${user.phone}
						</td>
						<td>
							${user.cardId}
						</td>
						<td>
							<#if user.realStatus == 1 >
								<span class="green">已认证</span>
							<#elseif user.realStatus == 0>
								<span class="red"> 未认证 </span>
							<#else>
								<span class="red"> 其他 </span>
							</#if>
						</td>
						<td>
							<#if user.isEnabled && !user.isLock >
								<span class="green">正常</span>
							<#elseif !user.isEnabled>
								<span class="red"> 未启用 </span>
							<#elseif user.isLock>
								<span class="red"> 已锁定 </span>
							</#if>
						</td>
						<td>
							<a href = "user!edit.action?id=${user.id}" title="[编辑]">[编辑]</a>
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