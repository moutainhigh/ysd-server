<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>数据统计</title>
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
		$endDate = $("#endDate");
		$username=$("#username");
		
		$exceButton.click(function(){
			$.dialog({type: "warn", content: "确定导出搜索条件下记录？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			
			function right(){
				location.href='derive_report!exceCensusUserList.action'+
				'?username='+encodeURI($username.val())+
				'&endDate='+$endDate.val();
			}
		})
	});	
</script>
</head>
<body class="list">
	<div class="bar">
		每日资金核对表 &nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="census!userList.action" method="post">
			<div class="listBar">
				用户名：<input type="hidden" name="pager.searchBy" value="username">
				<input type="text" id="username" name="pager.keyword" value="${pager.keyword!}" />
				日期：<input type="text" id="endDate" name="dateEnd" class="formText" value="${(dateEnd?string("yyyy-MM-dd"))!}" style="width:135px" title="截止时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				<label>&nbsp;&nbsp;&nbsp;</label>
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				<input type="button" id="exceButton" class="formButton" value="导出数据">
				&nbsp;&nbsp;
				
			</div>
			<!--ID	用户名	真实姓名	总余额	可用余额	冻结金额	待收金额-->
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="sort">用户ID</a>
					</th>
					<th>
						<a href="#" class="sort">用户名</a>
					</th>
					<th>
						<a href="#" class="sort">姓名</a>
					</th>
					<th>
						<a href="#" class="sort">总金额</a>
					</th>
					<th>
						<a href="#" class="sort">资金转入</a>
					</th>
					<th>
						<a href="#" class="sort">累计充值</a>
					</th>
					<th>
						<a href="#" class="sort">已收净利息</a>
					</th>
					<th>
						<a href="#" class="sort">累计奖励</a>
					</th>
					<th>
						<a href="#" class="sort">提现总额</a>
					</th>
					<th>
						<a href="#" class="sort">扣费总额</a>
					</th>
					<th>
						<a href="#" class="sort">验算金额</a>
					</th>
				</tr>
				<#list pager.result as item>
					<tr>
						<td>
							${(item.userId)!}
						</td>
						<td>
							${(item.username)!}
						</td>
						<td>
							${(item.realName)!}
						</td>
						<td>
							${(item.total0?string.currency)!}
						</td>
						<td>
							${(item.suma11?string.currency)!}
						</td>
						<td>
							${(item.suma2?string.currency)!}
						</td>
						<td>
							${(item.suma3?string.currency)!}
						</td>
						<td>
							${(item.suma4?string.currency)!}
						</td>
						<td>
							${(item.sumb21?string.currency)!}
						</td>
						<td>
							${(item.sumb41?string.currency)!}
						</td>
						<td>
							${(item.sum0?string.currency)!}
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