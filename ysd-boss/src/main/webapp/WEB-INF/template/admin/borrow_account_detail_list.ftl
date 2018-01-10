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
<body class="list">
	<div class="bar">
		项目放款明细列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="borrow_account_detail!list.action" method="post">
			<div class="listBar">
				&nbsp;&nbsp;
				<label>查&nbsp;&nbsp;找: </label>
				<select name="searchBy">
					<option value="name" <#if searchBy == "name"> selected</#if>>
						放款项目
					</option>
					<option value="borrowRealname" <#if searchBy == "borrowRealname"> selected</#if>>
						借款人
					</option> 
				</select>
				<input type="text" name="keywords" value="${keywords!}" />
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
						<a href="#"  name="id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#"  name="username" hidefocus>创建时间</a>
					</th>
					<th>
						<a href="#"  name="email" hidefocus>类型</a>
					</th>
					<th>
						<a href="#"  name="email" hidefocus>放款项目</a>
					</th>
					<th>
						<a href="#"  name="name" hidefocus>借款人</a>
					</th>
					<th>
						<a href="#"  name="department" hidefocus>放款金额</a>
					</th>
					<th>
						<a href="#"  name="loginDate" hidefocus>项目总额</a>
					</th>
					<th>
						<a href="#"  name="loginDate" hidefocus>已还本金</a>
					</th>
					<th>
						<a href="#"  name="loginDate" hidefocus>已还利息</a>
					</th>
					<th>
						<a href="#"  name="loginDate" hidefocus>未还本金</a>
					</th>
					<th>
						<a href="#"  name="loginDate" hidefocus>未还利息</a>
					</th>
				</tr>
				<#list pager.result as bad>
					<tr>
						<td>
							${bad.id}
						</td>
						<td>
							${bad.createDate?string("yyyy-MM-dd HH:mm:ss")}
						</td>
						<td>
							<@listing_childname sign="borrow_account_detail" key_value="${(bad.type)!}"; accountType>
								${accountType}
							</@listing_childname>
						</td>
						<td>
							${bad.borrow.name}
						</td>
						<td>
							${bad.user.realName}
						</td>
						<td>
							￥${(bad.money)!'0'}
						</td>
						<td>
							￥${(bad.borrowTotal)!'0'}
						</td>
						<td>
							￥${(bad.borrowCapitalYes)!'0'}
						</td>
						<td>
							￥${(bad.borrowInterestYes)!'0'}
						</td>
						<td>
							￥${(bad.borrowCapitalNo)!'0'}
						</td>
						<td>
							￥${(bad.borrowInterestNo)!'0'}
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