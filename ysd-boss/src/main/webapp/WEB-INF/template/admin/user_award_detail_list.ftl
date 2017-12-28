<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>红包明细 </title>
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
		红包明细&nbsp;总记录数: <span id="total_span">${pager.totalCount}</span> (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="user_award_detail!list.action" method="post">
		<input type="hidden" name="pager.searchBy" value="user.username">
			<div class="listBar">
			<label>用户名：</label>
				<input type="text" name="pager.keyword" id="keyword" value="${(pager.keyword)!}" />
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="" name="id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>时间</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>奖励类型</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>收入</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>支出</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>备注</a>
					</th>
				</tr>
				<#list pager.result as aw>
					<tr>
						<td>
							${aw.id}	
						</td>
						<td><#if aw.user?exists>
							${(aw.user.username)!'--'}
							<#else>--</#if>
						</td>
						<td>
							${(aw.createDate?string("yyyy-MM-dd"))!'--'}
						</td>
						<td>
							<@listing_childname sign="account_link" key_value="${(aw.type)!}"; accountType>
								${accountType}
							</@listing_childname>
						</td>
						<td>
							<#if aw.signFlg ==1>${aw.money}<#else>--</#if>
						</td>
						<td>
							<#if aw.signFlg ==-1>-${aw.money}<#else>--</#if>
						</td>
						<td>
							${aw.remark}
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