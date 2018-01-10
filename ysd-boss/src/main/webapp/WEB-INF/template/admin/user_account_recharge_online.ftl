<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>充值记录列表</title>
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
		
		$exceButton.click(function(){
			
			$.dialog({type: "warn", content: "确定导出搜索条件下记录？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			
			function right(){
				location.href='derive_report!exceRecharge.action'+
				'?pager.searchBy='+$("#search_sel").val()+
				'&pager.keyword='+$("input[name='pager.keyword']").val()+
				'&dateType='+$("input[name=dateType][checked]").val()+
				'&startDate='+$("input[name=startDate]").val()+
				'&endDate='+$("input[name='endDate']").val()+
				'&rechargeInterfaceId='+$("#interface_sel").val()+
				//'&status='+$("#status_sel").val()+ 充值审核
				'&status=2'+
				'&moneygt='+$("input[name='moneygt']").val()+
				'&moneylt='+$("input[name='moneylt']").val();
			}
		})
	});	
</script>
</head>
<body class="list">
	<div class="bar">
	充值审核&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="user_account_recharge!verifyListOnLine.action" method="post">
		<input type = "hidden" name="userAccountRecharge.id">
			<div class="listBar">
				<label>查&nbsp;&nbsp;找: </label>
				<select name="pager.searchBy" id="search_sel">
					<option value = "">不限</option>
					<option value="user.username"<#if pager.searchBy == "user.username"> selected</#if>>
						用户名
					</option>
					<option value="tradeNo"<#if pager.searchBy == "tradeNo"> selected</#if>>
						订单号
					</option>
				</select>&nbsp;&nbsp;
				<input type="text" name="pager.keyword" value="${pager.keyword!}" />
				<#if !rechargeInterfaceId>
				<label>&nbsp;&nbsp;</label>				
					<input type="radio" name = "dateType" value="0" <#if dateType=='' || dateType==0>checked</#if>>提交时间
					<input type="radio" name = "dateType" value="1" <#if dateType! && dateType==1>checked</#if>>完成时间
					<input type="text" id="startDate" name="startDate" class="formText" value="${(startDate?string("yyyy-MM-dd"))!}" style="width:80px" title="起始时间" onclick="WdatePicker()" />&nbsp;到&nbsp;
			     	<input type="text" id="endDate" name="endDate" class="formText" value="${(endDate?string("yyyy-MM-dd"))!}" style="width:80px" title="截止时间" onclick="WdatePicker()" />
				<label><br/><br/></label>		
				<label>充值方式: </label>
					<select name="userAccountRecharge.rechargeInterface.id"  id="interface_sel">
						<option value = "">不限</option>
						<option value = "0" <#if userAccountRecharge! && userAccountRecharge.rechargeInterface! && userAccountRecharge.rechargeInterface.id ==0>selected</#if> >线下充值</option>
					<#list rechargeConfigList as rechargeConfig>
						<#if rechargeConfig.id !=0>
							<option value="${rechargeConfig.id}"<#if userAccountRecharge! && userAccountRecharge.rechargeInterface! && rechargeConfig.id == (userAccountRecharge.rechargeInterface.id)!> selected</#if>>${rechargeConfig.name}</option>
						</#if>
					</#list>
					</select>
					&nbsp;&nbsp;
				<label>充值状态: </label><label>审核中 </label>
				<!--
					<select name="status" id="status_sel">
						<option value=""<#if status> selected</#if>>不限</option>
						<option value="1"<#if (status)! == '1'> selected</#if>>成功</option>
						<option value="2"<#if (status)! == '2'> selected</#if>>审核中</option>
						<option value="0"<#if (status)! == '0'> selected</#if>>失败</option>
					</select>
					-->
				<label>&nbsp;&nbsp;</label>		
				<label>充值金额: </label>
					大于等于&nbsp;&nbsp;<input type="text" name="moneygt" style="width:80px" value="${moneygt}">&nbsp;&nbsp;元&nbsp;&nbsp;
					小于等于&nbsp;&nbsp;<input type="text" name="moneylt" style="width:80px" value="${moneylt}">&nbsp;&nbsp;元
				<label>&nbsp;&nbsp;</label>
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				<input type="button" id="exceButton" class="formButton" value="导出数据">
				<#else>
				<input type="hidden" name="rechargeInterfaceId" value="0">
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				</#if>
				
				&nbsp;&nbsp;
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="sort" name="id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="" name="user.username" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>充值时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="tradeNo" hidefocus>订单号</a>
					</th>
					<th>
						<a href="#" class="sort" name="type" hidefocus>类型</a>
					</th>
					<th>
						<a href="#" class="sort" name="rechargeInterface.id" hidefocus>充值方式</a>
					</th>
					<th>
						<a href="#" class="sort" name="money" hidefocus>充值金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="fee" hidefocus>手续费</a>
					</th>
					<th>
						<a href="#" class="sort" name="reward" hidefocus>充值奖励</a>
					</th>
					<th>
						<a href="#" class="sort" name="status" hidefocus>状态</a>
					</th>
					<#--
					<th>
						<a href="#" class="sort" name="compareStatus" hidefocus>比对处理结果</a>
					</th>-->		
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as userAccountRecharge>
					<tr>
						<td>
							${userAccountRecharge.id}
						</td>
						<td>
							${userAccountRecharge.user.username}
						</td>
						<td>
							${(userAccountRecharge.createDate?string("yyyy-MM-dd HH:mm:ss"))!}
						</td>
						<td>
							${(userAccountRecharge.tradeNo)!}
						</td>
						<td>
							<#if userAccountRecharge.type==1>线上充值<#else><font color="red">线下充值</font></#if>
						</td>
						<td>
							<@rechargeConfig_tag id="${userAccountRecharge.rechargeInterface.id}" ; r>
								${r.name}
							</@rechargeConfig_tag>
						</td>
						<td>
							${(userAccountRecharge.money?string.currency)!}
						</td>
						<td>
							${(userAccountRecharge.fee?string.currency)!'￥0.00'}
						</td>
						<td>
							${(userAccountRecharge.reward?string.currency)!'￥0.00'}
						</td>
						<td>
							<#if userAccountRecharge.status==0>失败<#elseif userAccountRecharge.status==1><span class = "green">成功</span><#else><span class = "red">审核中</span></#if>
						</td>
						<td>
							
								<#if userAccountRecharge.status = 2>
								<a href = "user_account_recharge!edit.action?id=${(userAccountRecharge.id)!}&rechargeInterfaceId=${userAccountRecharge.rechargeInterface.id}"><font color="red">审核</font></a>
								<!--	<a href = "user_account_recharge!edit.action?id=${(userAccountRecharge.id)!}"><font color="red">审核中</font></a>-->
								<#elseif userAccountRecharge.status=0 ||  userAccountRecharge.status=1>
									<a href = "user_account_recharge!edit.action?id=${(userAccountRecharge.id)!}"><font color="green">查看</a></font>
								<#else>
									未知错误	
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