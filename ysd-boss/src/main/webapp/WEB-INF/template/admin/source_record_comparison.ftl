<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>原始充值记录列表</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
</head>
<body class="list">
	<div class="bar">
		比对充值记录列表&nbsp;总记录数: ${sourceRecordList?size}
	</div>
	<div class="body">
		<form id="listForm" action="source_record!doComparison.action" method="post">
			<div class="listBar">
			<label>时间类型: </label>
				<input type="radio" name = "dateType" value="0" checked>提交时间
						 <input type="radio" name = "dateType" value="1">完成时间
						 &nbsp;&nbsp;
						 <input type="text" id="startDate" name="startDate" class="formText" value="${(startDate?string("yyyy-MM-dd"))!}" style="width:80px" title="起始时间" onclick="WdatePicker()" />&nbsp;到&nbsp;
				     <input type="text" id="endDate" name="endDate" class="formText" value="${(endDate?string("yyyy-MM-dd"))!}" style="width:80px" title="截止时间" onclick="WdatePicker()" />
					&nbsp;&nbsp;&nbsp;<br/><br/>
						<label>充值方式：</label><select name="sourceRecord.rechargeConfig.id" id="rechargeConfigId">
								<option value = "">不限</option>
							<#list rechargeConfigList as rechargeConfig>
								<option value="${rechargeConfig.id}"<#if rechargeConfig.id == (sourceRecord.rechargeConfig.id)!> selected</#if>>${rechargeConfig.name}</option>
							</#list>
							</select><br/><br/>
						<label>交易状态：</label><select name="sourceRecord.tradeState" id="rechargeConfigId">
								<option value=""<#if (sourceRecord.tradeState)! == ''> selected</#if>>不限</option>
								<option value="成功"<#if (sourceRecord.tradeState)! == '成功'> selected</#if>>成功</option>
								<option value="失败"<#if (sourceRecord.tradeState)! == '失败'> selected</#if>>失败</option>
							</select><br/><br/>
					<label>订&nbsp;单&nbsp;号：</label><input type = "text" name = "sourceRecord.orderNumber" value="${(sourceRecord.orderNumber)!}">
					&nbsp;&nbsp;&nbsp;【说明:"没有比对结果"表示用户充值时，数据库里没有记录到数据】<br/>
				<input type="button" id="searchButton" class="formButton" value="比对" hidefocus /><br/>
				<!--<label>每页显示: </label>
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
			</select>-->
			</div>
			<table id="listTable" class="listTable">
				  <tr>
				    <th colspan=6 style="text-align:center;">充值接口数据</th>
				    <th colspan=3 style="text-align:center;">网站数据</th>
				    <th colspan=2 style="text-align:center;">比对结果</th>
				  </tr>
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
						<a href="#" class="sort" name="tradeState" hidefocus>交易状态</a>
					</th>
					<th>
						<a href="#" class="sort" name="orderNumber" hidefocus>订单号</a>
					</th>
					<th>
						<a href="#" class="sort" name="transactionAmount" hidefocus>交易金额</a>
					</th>
					
					<th>
						<a href="#" class="sort" name="tradeNo" hidefocus>订单号</a>
					</th>
					<th>
						<a href="#" class="sort" name="status" hidefocus>状态</a>
					</th>
					<th>
						<a href="#" class="sort" name="money" hidefocus>金额</a>
					</th>
					
					
					<th>
						<a href="#" class="sort" name="money" hidefocus>金额状态</a>
					</th>
					<th>
						<a href="#" class="sort" name="money" hidefocus>交易状态</a>
					</th>
				</tr>
				
				<#list sourceRecordList as sourceRecord>
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
							<#if sourceRecord.tradeState==2>失败<#elseif sourceRecord.tradeState==1>成功</#if>
						</td>
						<td>
							${sourceRecord.orderNumber}
						</td>
						<td>
							${sourceRecord.transactionAmount?string.currency}
						</td>
						<@userAccountRecharge_tag trade_no = sourceRecord.orderNumber; userAccountRecharge>
						<#if userAccountRecharge!>
							<td>
								${userAccountRecharge.tradeNo}
							</td>
							<td>
								<#if userAccountRecharge.status=0>失败<#elseif userAccountRecharge.status=1>成功<#else>处理中</#if>
							</td>
							<td>
								${userAccountRecharge.money?string.currency}
							</td>
							<#else>
							<td></td><td></td><td></td>
						</#if>	
						</@userAccountRecharge_tag>
						<td>
							<#if sourceRecord.moneyState==0>失败<#elseif sourceRecord.moneyState==1>成功</#if>
						</td>
						<td>
							<#if sourceRecord.comparisonState==0>
								失败
							<#elseif sourceRecord.comparisonState==1>
								成功
							<#elseif sourceRecord.comparisonState==2>
								没有比对结果
							</#if>
						</td>
					</tr>
				</#list>
			</table>
			<#if sourceRecordList==''>
				<div class="noRecord">没有找到任何记录!</div>
			</#if>
		</form>
	</div>
</body>
</html>