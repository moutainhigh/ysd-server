<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>线下充值账号列表 </title>
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
		线下充值账号列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="off_line_account!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='off_line_account!input.action'" value="添加账号" hidefocus />
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<a href="#" class="sort" name="id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="sort" name="department" hidefocus>姓名</a>
					</th>
					<th>
						<a href="#" class="sort" name="username" hidefocus>账号</a>
					</th>
					<th>
						<a href="#" class="sort" name="email" hidefocus>银行</a>
					</th>
					<th>
						<a href="#" class="sort" name="name" hidefocus>支行</a>
					</th>
					<th>
						<a href="#" class="sort" name="orderList" hidefocus>排序</a>
					</th>
					<th>
						<span>是否启用</span>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>创建日期</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as offLineAccount>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${offLineAccount.id}" />
						</td>
						<td>
							${offLineAccount.id}
						</td>
						<td>
							${(offLineAccount.legalName)!}
						</td>
						<td>
							${offLineAccount.account}
						</td>
						<td>
							<@listing_childname sign="recharge_bank" key_value="${(offLineAccount.bankId)!}"; bank>
								${bank}
							</@listing_childname>
						</td>
						<td>
							${(offLineAccount.branch)!}
						</td>
						<td>
							${offLineAccount.orderList}
						</td>
						<td>
							<span class="${offLineAccount.isEnabled?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							<span title="${offLineAccount.createDate?string("yyyy-MM-dd HH:mm:ss")}">${offLineAccount.createDate}</span>
						</td>
						<td>
							<a href="off_line_account!edit.action?id=${offLineAccount.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="off_line_account!delete.action" value="删 除" disabled hidefocus />
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