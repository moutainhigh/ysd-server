<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>对接服务商审核列表 </title>
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
		对接服务商审核列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="agency!readyList.action" method="post">
			<div class="listBar">
				<label>查&nbsp;&nbsp;找: </label>
				<select name="pager.searchBy">
					<option value="username"<#if pager.searchBy == "username"> selected</#if>>
						用户名
					</option>
				</select>
				<input type="text" name="pager.keyword" value="${pager.keyword!}" />
				<label>状态: </label>
					<select name="agencyReady.status" id="status_sel">
						<option value="1"<#if (agencyReady.status)! == '1'> selected</#if>>已通过</option>
						<option value="2"<#if (agencyReady.status)! == '2'> selected</#if>>审核中</option>
						<option value="0"<#if (agencyReady.status)! == '0'> selected</#if>>拒绝</option>
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
						<a href="#" class="sort" name="createDate" hidefocus>申请时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="companyName" hidefocus>对接服务商名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="uusername" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="" name="uemail" hidefocus>邮箱</a>
					</th>
					<th>
						<a href="#" class="" name="linkman" hidefocus>联系人姓名</a>
					</th>
					<th>
						<a href="#" class="" name="linkmanMobile" hidefocus>联系人手机</a>
					</th>
					<th>
						<a href="#" class="sort" name="status" hidefocus>状态</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as agencyReady>
					<tr>
						<td>
							${agencyReady.id}
						</td>
						<td>
							${agencyReady.createDate?string("yyyy-MM-dd HH:mm:ss")}
						</td>
						<td>
							${agencyReady.companyName}
						</td>
						<td>
							${agencyReady.uusername}
						</td>
						
						<td>
							${agencyReady.uemail}
						</td>
						<td>
							${agencyReady.linkman}
						</td>
						<td>
							${agencyReady.linkmanMobile}
						</td>
						<td>
							<#if agencyReady.status == 1 >
								<span class="green">已通过</span>
							<#elseif  agencyReady.status == 0 >
								<span class="red">拒绝 </span>
							<#elseif  agencyReady.status == 2 >
								<span class="red">审核中 </span>
							</#if>
						</td>
						<td>
							<#if agencyReady.status == 2 >
								<a href = "agency!agencyReadyCheckTo.action?id=${agencyReady.id}" title="[审核]">[审核]</a>
								<#--<a href = "agency_bx!readyEdit.action?id=${agencyReady.id}" title="[编辑]">[编辑]</a>-->
							</#if>
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