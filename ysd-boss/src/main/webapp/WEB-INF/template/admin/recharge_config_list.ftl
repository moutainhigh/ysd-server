<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>支付方式列表</title>
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
		支付方式列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="recharge_config!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='recharge_config!add.action'" value="添加" hidefocus />
			
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="sort" name="id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="sort" name="name" hidefocus>支付名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="paymentProductId" hidefocus>支付标识</a>
					</th>
					<th>
						<a href="#" class="" name="logoPath" hidefocus>LOGO</a>
					</th>
					<th>
						<a href="#" class="sort" name="bargainorId" hidefocus>账号ID</a>
					</th>
					<th>
						<a href="#" class="sort" name="bargainorKey" hidefocus>账号私钥</a>
					</th>
					<th>
						<a href="#" class="sort" name="paymentFeeType" hidefocus>支付手续费类型</a>
					</th>
					<th>
						<a href="#" class="sort" name="paymentFee" hidefocus>支付费用</a>
					</th>
					<th>
						<a href="#" class="sort" name="orderList" hidefocus>排序</a>
					</th>
					<th>
						<a href="#" class="sort" name="isEnabled" hidefocus>是否启用</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as rechargeConfig>
					<tr>
						<td>
							${rechargeConfig.id}
						</td>
						<td>
							${rechargeConfig.name}
						</td>
						<td>
							${rechargeConfig.paymentProductId}
						</td>
						<td><a href = "${imgUrl}${rechargeConfig.logoPath}" target = "_blank">查看</a></td>
						<td>
							${rechargeConfig.bargainorId}
						</td>
						<td>
							${substring(rechargeConfig.bargainorKey, 20, "...")}
						</td>
						<td>
							${action.getText("PaymentFeeType." + rechargeConfig.paymentFeeType)}
						</td>
						<td>
							${rechargeConfig.paymentFee}
						</td>
						<td>
							${rechargeConfig.orderList}
						</td>
						<td>
							<span class="${rechargeConfig.isEnabled?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							<a href="recharge_config!edit.action?id=${rechargeConfig.id}" title="编辑">[编辑]</a>
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