<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>订单列表</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {
	
		$exceButton=$("#exceButton");
		$username = $("#username");
		$typeId = $("#typeId");
		$status = $("#status");
		$startDate=$("#startDate");
		$endDate = $("#endDate");
		
		$exceButton.click(function(){
			$.dialog({type: "warn", content: "确定导出搜索条件下记录？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			
			function right(){
				location.href='derive_report!execAccountCash.action?startDate='+$startDate.val()+'&accountCash.status='+$status.val()+'&accountCash.user.username='+$username.val()+'&accountCash.user.typeId='+$typeId.val()+'&endDate='+$endDate.val();
			}
		})
	});	
</script>
</head>
<body class="list">
	<div class="bar">
	<#if accountCash!='' && accountCash.user.typeId>
		<#if accountCash.user.typeId==0>
			投资人
		<#else> 
			借款人
		</#if>
	<#else>
		所有
	</#if>
	<#if accountCash!='' && accountCash.status>
		<#if accountCash.status == 0>待审核 
		<#elseif accountCash.status == 1>成功 
		<#elseif accountCash.status == 2>审核失败 
		<#elseif accountCash.status == 3>用户取消
		</#if> 
	 </#if>
		提现列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	
	</div>
	<div class="body">
	<form id="listForm" action="account_cash!list.action" method="post">
	<div class="listBar">
				<!--<label>用户类型：</label>
				 <select name ="accountCash.user.typeId"id="typeId" ><option value="" >全部</option>
				<option value="0" <#if accountCash!='' && accountCash.user.typeId == "0"> selected</#if> >投资人</option>
				<option value="1" <#if accountCash!='' && accountCash.user.typeId == "1"> selected</#if> >借款人</option>
				</select>-->
				<input type="hidden" name="accountCash.user.typeId" id="typeId" value="" />
				<label>状态：</label>
				 <select name ="accountCash.status" id="status" ><option value="">全部</option>
				<option value="0" <#if accountCash!='' && accountCash.status == "0"> selected</#if> >审核中</option>
				<!--<option value="4"<#if accountCash!='' && accountCash.status == "4"> selected</#if>  >处理中</option>-->
				<option value="1" <#if accountCash!='' && accountCash.status == "1"> selected</#if> >已通过</option>
				<option value="2" <#if accountCash!='' && accountCash.status == "2"> selected</#if> >审核未通过</option>
				<!--<option value="3"<#if accountCash!='' && accountCash.status == "3"> selected</#if>  >用户取消提现</option>-->
				</select>
				<label>用户名：</label>
				<input type="text" name="accountCash.user.username" id="username" value="${(accountCash.user.username)!}" />
				<label>时间：</label>
				<input type="text" id="startDate" name="startDate" class="formText" value="${(startDate?string("yyyy-MM-dd HH:mm:ss"))!}" style="width:135px" title="起始时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />&nbsp;到&nbsp;
				 <input type="text" id="endDate" name="endDate" class="formText" value="${(endDate?string("yyyy-MM-dd HH:mm:ss"))!}" style="width:135px" title="截止时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				
				<input type="button" id="exceButton" class="formButton" value="导出数据">
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="sort" name="id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="sort" name="user.username" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="sort" name="user.realName" hidefocus>真实姓名</a>
					</th>
					<th>
						<a href="#" class="sort" name="user.typeId" hidefocus>用户类型</a>
					</th>
					<th>
						<a href="#" class="sort" name="account" hidefocus>提现账号</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>提现银行</a>
					</th>
					<th>
						<a href="#" class="sort" name="branch" hidefocus>支行</a>
					</th>
					<th>
						<a href="#" class="sort" name="total" hidefocus>提现总额</a>
					</th>
					<th>
						<a href="#" class="sort" name="credited" hidefocus>到账金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="fee" hidefocus>手续费</a>
					</th>
					<th>
						<a href="#" class="sort" name="changeNum" hidefocus>调整金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>提现时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="status" hidefocus>状态</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as accountCash>
					<tr>
						<td>
							${accountCash.id}
						</td>
						<td>
							${(accountCash.user.username)!}
						</td>
						<td>
							${(accountCash.user.realName)!}
						</td>
						<td>
						<#if (accountCash.user)!>
							<#if accountCash.user.typeId==0>
								投资人
							<#elseif accountCash.user.typeId==1>
								借款人
							</#if>
						</#if>
						</td>
						<td>
							${accountCash.account}
						</td>
						<td>
							<#list listingList as listing>
							<#if listing.keyValue ==accountCash.bank>
								${listing.name}
							</#if>
							</#list>
							
						</td>
						<td>
							${accountCash.branch}
						</td>
						<td>
							${(accountCash.total?string.currency)!}
						</td>
						<td>
							${(accountCash.credited?string.currency)!}
						</td>
						<td>
							${(accountCash.fee?string.currency)!}
						</td>
						<td>
							${accountCash.changeNum}
						</td>
						<td>
							${(accountCash.createDate?string("yyyy-MM-dd HH:mm:ss"))!}
						</td>
						<td>
							<#if (accountCash.status)==0>  
								审核中
							<#elseif (accountCash.status)==1>
								已通过  
							<#elseif (accountCash.status)==2>
								审核未通过
							<#elseif (accountCash.status)==4>
								处理中
							<#else>
								用户取消提现
							</#if>
						</td>
						<td>
							<#if accountCash.status==0 || accountCash.status==4>  
								
							<#elseif (accountCash.status)!=3>
								<a href="account_cash!edit.action?id=${accountCash.id}" title="[查看]">[查看]</a>
							</#if>
						</td>
					</tr>
				</#list>
			</table>
			
			<#if (pager.result?size > 0)>
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