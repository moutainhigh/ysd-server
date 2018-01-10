<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>邀请好友信息列表 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
	$().ready( function() {
	
		$exceButton=$("#exceButton");
		
		$exceButton.click(function(){
			$.dialog({type: "warn", content: "确定导出搜索条件下记录？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			
			function right(){
				location.href='derive_report_new!exceSpreadlistMoney.action'+
				'?username='+$("input[name='username']").val();
			}
		})
	});	
</script>
</head>
<body class="list">
	<div class="bar">
		邀请好友项目投资列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="user!spreadlistMoney.action" method="post">
			<div class="listBar">
				<label>邀请人: </label>
				<input type="text" name="username" value="${username!}" />&nbsp;&nbsp;
				
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				<input type="button" id="exceButton" class="formButton" value="导出数据">
				<#if sumMoney!''>
					总收益(元): ${sumMoney}
				</#if>
				&nbsp;&nbsp;
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="sort" name="" hidefocus>被邀请人</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>真实姓名</a>
					</th>
				
					<th>
						<a href="#" class="sort" name="" hidefocus>投资项目</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>投资时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>项目状态</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>项目收益</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>邀请人</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>邀请人姓名</a>
					</th>
				</tr>
				<#list pager.result as user>
					<tr>
						<td>
							${user.username}
						</td>
						<td>
							${user.realname}
						</td>
						<td>
							${user.borrowName}
						</td>
						<td>
						<#if user.createDate!'' >
							${user.createDate?string("yyyy-MM-dd HH:mm:ss")}	
						</#if>
						</td>
						<td>
						<#if user.borrowStatus==1>
							正在招标中
						</#if>
						<#if user.borrowStatus==3>
							还款中
						</#if>
						<#if user.borrowStatus==6>
							撤回的标
						</#if>
						<#if user.borrowStatus==7>
							已完成的标
						</#if>
						</td>
						<td>
							${user.borrowInterest}
						</td>
						<td>
							${user.inviteName}
						</td>
						<td>
							${user.inviteRealName}
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