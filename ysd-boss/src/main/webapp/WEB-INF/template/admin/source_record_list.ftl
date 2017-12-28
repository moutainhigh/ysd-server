<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>第三方充值数据列表 </title>
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
		第三方充值数据列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="source_record!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='source_record!add.action'" value="导入数据" hidefocus />
			
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="sort" name="rechargeConfig" hidefocus>数据接口</a>
					</th>
					<th>
						<a href="#" class="sort" name="addDate" hidefocus>提交时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="completionDate" hidefocus>完成时间</a>
					</th>
					<th>
						<a href="#" class="" name="transactionNumber" hidefocus>订单号</a>
					</th>
					<th>
						<a href="#" class="sort" name="tradeState" hidefocus>交易状态</a>
					</th>
					<th>
						<a href="#" class="sort" name="treatmentState" hidefocus>处理状态</a>
					</th>
					<!--<th>
						<a href="#" class="sort" name="tradeType" hidefocus>交易类型</a>
					</th>
					<th>
						<a href="#" class="sort" name="balanceType" hidefocus>收支类型</a>
					</th>-->
					<th>
						<a href="#" class="sort" name="payment" hidefocus>支付方式</a>
					</th>
					<!--<th>
						<a href="#" class="sort" name="tradingFloor" hidefocus>交易场所</a>
					</th>
					<th>
						<a href="#" class="sort" name="counterparty" hidefocus>交易对方</a>
					</th>-->
					<th>
						<a href="#" class="sort" name="associatedBank" hidefocus>关联银行</a>
					</th>
					<#--
						<th>
							<a href="#" class="sort" name="bankOrderNumber" hidefocus>银行订单号</a>
						</th>
						<th>
							<a href="#" class="sort" name="orderNumber" hidefocus>订单号</a>
						</th>
					-->
					<th>
						<a href="#" class="sort" name="actualAmount" hidefocus>实际收(付)金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="transactionAmount" hidefocus>交易金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="handlingCharge" hidefocus>手续费</a>
					</th>
					<th>
						<a href="#" class="sort" name="remark" hidefocus>备注</a>
					</th>
				</tr>
				<#list pager.result as sourceRecord>
					<tr>
						<td>
							${sourceRecord.rechargeConfig.name}
						</td>
						<td>
							${(sourceRecord.addDate?string("yyyy-MM-dd HH:mm:ss"))!}
						</td>
						<td>
							${(sourceRecord.completionDate?string("yyyy-MM-dd HH:mm:ss"))!}
						</td>
						<td>
							${sourceRecord.orderNumber}
						</td>
						<td>
							<#if sourceRecord.tradeState==2>失败<#elseif sourceRecord.tradeState==1>成功</#if>
						</td>
						<td>
							${sourceRecord.treatmentState}
						</td>
						<!--<td>
							${sourceRecord.tradeType}
						</td>
						<td>
							${sourceRecord.balanceType}
						</td>-->
						<td>
							${sourceRecord.payment}
						</td>
						<!--<td>
							${sourceRecord.tradingFloor}
						</td>
						<td>
							${sourceRecord.counterparty}
						</td>-->
						<td>
							${sourceRecord.associatedBank}
						</td>
						<#--
							<td>
								${sourceRecord.bankOrderNumber}
							</td>
							<td>
								${sourceRecord.orderNumber}
							</td>
						-->
						<td>
							${sourceRecord.actualAmount?string.currency}
						</td>
						<td>
							${sourceRecord.transactionAmount?string.currency}
						</td>
						<td>
							${sourceRecord.handlingCharge?string.currency}
						</td>
						<td>
							${sourceRecord.remark}
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