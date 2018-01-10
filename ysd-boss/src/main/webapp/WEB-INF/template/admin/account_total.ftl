<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>资金分类列表</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
	$().ready( function() {
	
		$exceButton=$("#exceButton");
		$typeId = $("#typeId");
		$startDate=$("#startDate");
		$endDate = $("#endDate");
		
		$exceButton.click(function(){
			$.dialog({type: "warn", content: "确定导出搜索条件下记录？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			
			function right(){
				location.href='derive_report!execAccountTotal.action?startDate='+$startDate.val()+'&type='+$typeId.val()+'&endDate='+$endDate.val();
			}
		})
	});	
</script>
</head>
<body class="list">
	<div class="bar">
		资金分类列表
	</div>
	<div class="body">
		<form id="listForm" action="account!accountRechargeDetailTotal.action" method="post">
			<div class="listBar">
				<label>查&nbsp;&nbsp;找: </label>
				<select id="typeId" name="type">
					<option value="" <#if type="">selected="selected"</#if>>全部</option>
					 <@listing_childlist sign="account_type"; listingList>
						<#list listingList as listing>
							<option value="${listing.keyValue}" <#if type == listing.keyValue>selected</#if> >${substring(listing.name, 24, "...")}</a>
							</option>
						</#list>
					  </@listing_childlist>
				</select>&nbsp;&nbsp;
				<label>时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;间:</label>&nbsp;&nbsp;
					<input type="text" id="startDate" name="startDate" class="formText" value="${(startDate?string("yyyy-MM-dd"))!}" style="width:80px" title="起始时间" onclick="WdatePicker()" />&nbsp;到&nbsp;
				     <input type="text" id="endDate" name="endDate" class="formText" value="${(endDate?string("yyyy-MM-dd"))!}" style="width:80px" title="截止时间" onclick="WdatePicker()" />
					&nbsp;&nbsp;&nbsp;
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				<input type="button" id="exceButton" class="formButton" value="导出数据">
			</div>
			<table id="listTable" class="listTable">
				<tr>	
					<th>
						<a href="#" class="" name="count" hidefocus>条数</a>
					</th>					
					<th>
						<a href="#" class="" name="type" hidefocus>分类</a>
					</th>
					<th>
						<a href="#" class="" name="totalMoney" hidefocus>总额</a>
					</th>
				</tr>
						
							<#list accountDetailList as accountDetail>
								
								<@listing_childname sign="account_type" key_value ="${(accountDetail.type)!}"; name>
									<#if name!>
										<tr>
											<td>
												${accountDetail.count}
											</td>
											<td>
												${name}
											</td>
											<td>
												${accountDetail.money?string.currency}
											</td>
										</tr>
									</#if>
								</@listing_childname>
							</#list>
							<#list listingList as listing>
									<tr>
											<td>
												0
											</td>
											<td>
												${listing.name}
											</td>
											<td>
												${0?string.currency}
											</td>
									</tr>
							</#list>
				
			</table>
		</form>
			
	</div>
</body>
</html>