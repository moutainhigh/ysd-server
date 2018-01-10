<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>充值银行列表</title>
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
		充值银行列表管理&nbsp;总记录数: ${(listingChildrenRechargeBankList?size)!'0'}
	</div>
	<div class="body">
		<form id="listForm" action="listing!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='listing!addRechargeBank.action'" value="添加银行" hidefocus />
				
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="sort" name="name" hidefocus>编号</a>
					</th>
					<th>
						<a href="#" class="sort" name="name" hidefocus>银行名称</a>
					</th>
					<th>
						<a href="#" class="" name="logo" hidefocus>LOGO</a>
					</th>
					<th>
						<span>是否显示</span>
					</th>
					<th>
						<a href="#" class="sort" name="orderList" hidefocus>排序</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>添加时间</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list listingChildrenRechargeBankList as listing>
					<tr>
						<td>
							${listing_index+1}
						</td>
						<td>
							${listing.name}
						</td>
						<td>
							<#if (listing.logo??)!>
								<a href="${imgUrl}${listing.logo}" target="_blank">查看</a>
							<#else>
								-
							</#if>
						</td>
						<td>
							<span class="${listing.isEnabled?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							${listing.orderList}
						</td>
						<td>
							<span title="${listing.createDate?string("yyyy-MM-dd HH:mm:ss")}">${listing.createDate}</span>
						</td>
						<td>
							<a href="listing!editRechargeBank.action?id=${listing.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
		</form>
	</div>
</body>
</html>