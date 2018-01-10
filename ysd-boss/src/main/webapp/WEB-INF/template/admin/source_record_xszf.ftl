<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>【新生支付】充值记录列表 </title>
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

	var $dispose = $("#disposeButton");
	
	//处理
	$dispose.click( function() {
		var $this = $(this);
		$.dialog({type: "warn", content: "您确定处理这些数据吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: disposeData});
		function disposeData() {
			$.ajax({
				url: "source_record!comparison.action",
				type: "POST",
				dataType: "json",
				success: function(data) {
					$.message({type: data.status, content: data.message});
				}
			});
		}
		return false;
	});
	
})
</script>
</head>
<body class="list">
	<div class="bar">
		【新生支付】记录列表&nbsp;总记录数: ${userAccountRechargeList!?size}
	</div>
	<div class="body">
		<form id="listForm" action="source_record!doSourceXszf.action" method="post">
			<input type = "hidden" name = "id" value=${id} />
			<div class="listBar">
			<label>提交时间: </label>
					 <input type="text" id="startDate" name="startDate" class="formText" value="${(startDate?string("yyyy-MM-dd"))!}" style="width:80px" title="起始时间" onclick="WdatePicker()" />&nbsp;到&nbsp;
				     <input type="text" id="endDate" name="endDate" class="formText" value="${(endDate?string("yyyy-MM-dd"))!}" style="width:80px" title="截止时间" onclick="WdatePicker()" />
					&nbsp;&nbsp;&nbsp;
					交易状态:<select name="status" id="stauts">
								<option value=""<#if status> selected</#if>>不限</option>
								<option value="1"<#if status! == '1'> selected</#if>>成功</option>
								<option value="2"<#if status! == '2'> selected</#if>>处理中</option>
							</select>
				<input type="button" id="searchButton" class="formButton" value="查询" hidefocus />
				<input type="button" id="disposeButton" class="formButton" value="处理" hidefocus /><br/>
			</div>
			<table id="listTable" class="listTable">
				<tr>
					
					<th>
						<a href="#">用户名</a>
					</th>
					<th>
						<a href="#">订单号</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>提交时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="modifyDate" hidefocus>完成时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="money" hidefocus>交易金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="fee" hidefocus>手续费</a>
					</th>
					<th>
						<a href="#" class="sort" name="tradeState" hidefocus>交易状态</a>
					</th>
					<th>
						<a href="#" class="sort" name="tradeState" hidefocus>接口返回状态</a>
					</th>
					<th>
						<a href="#" class="sort" name="compareStarus" hidefocus>对比结果</a>
					</th>
				</tr>
				<#list userAccountRechargeList! as userAccountRecharge>
					<tr>
						<td>
							${userAccountRecharge.user.username}
						</td>
						<td>
							${userAccountRecharge.tradeNo}
						</td>
						<td>
							${(userAccountRecharge.createDate?string("yyyy-MM-dd HH:mm:ss"))!}
						</td>
						<td>
							${(userAccountRecharge.rechargeDate?string("yyyy-MM-dd HH:mm:ss"))!}
						</td>
						<td>
							${userAccountRecharge.money?string.currency}
						</td>
						<td>
							${userAccountRecharge.fee?string.currency}
						</td>
						<td>${userAccountRecharge.status}
							<#if userAccountRecharge.status==0>失败<#elseif userAccountRecharge.status==1>成功<#elseif userAccountRecharge.status==2>处理中</#if>
						</td>
						<td>${userAccountRecharge.portData?split(":")[0]}:${userAccountRecharge.portData?split(":")[1]}
						</td>
						<td>${userAccountRecharge.compareStatus}
							<#if userAccountRecharge.compareStatus==1>成功<#elseif userAccountRecharge.compareStatus==2>成功【订单处理中】<#else><font color="red">失败</font></#if>
						</td>
					</tr>
				</#list>
			</table>
		</form>
	</div>
</body>
</html>