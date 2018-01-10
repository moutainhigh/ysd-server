<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>红包明细 </title>
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
		$username = $("#username");
		$hongbaoName = $("#keyword");
		$status = $("#status_sel");
		$beginDate=$("#beginDate");
		$endDate = $("#endDate");
		$exceButton.click(function(){
			if($("#totalCount").val()>9999){
				alert("导出数据过多");
				return;
			}
		
			$.dialog({type: "warn", content: "确定导出搜索条件下记录？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			
			function right(){
				location.href='derive_report_new!execUserhongbaoList.action'+
				'?username='+$username.val()+
				'&hongbaoName='+encodeURI(encodeURI($hongbaoName.val()))+
				'&status='+$status.val()+
				'&beginDate='+$beginDate.val()+
				'&endDate='+$endDate.val();
			}
		})
	});	
</script>






</head>
<body class="list">
	<div class="bar">
		红包明细&nbsp;总记录数: <span id="total_span">${pager.totalCount}</span> (共${pager.pageCount}页)
		<input type="hidden" id="totalCount" value="${pager.totalCount}">
	</div>
	<div class="body">
		<form id="listForm" action="user_hongbao!list.action" method="post">
		<input type="hidden" name="pager.searchBy" value="user.username">
			<div class="listBar">
				<label>用户名：</label>
				<input type="text" name="username" id="username" value="${(username)!}" />
				<label>红包标题：</label>
				<input type="text" name="hongbaoName" id="keyword" value="${(hongbaoName)!}" />		
				<label>发放状态: </label>
					<select name="status" id="status_sel">
						<option value=""<#if status> selected</#if>>不限</option>
						<option value="-1"<#if (status)! == '-1'> selected</#if>>待发放</option>
						<option value="0"<#if (status)! == '0'> selected</#if>>未使用</option>
						<option value="1"<#if (status)! == '1'> selected</#if>>已使用</option>
						<option value="2"<#if (status)! == '2'> selected</#if>>失效</option>
					</select>
				<label>时间: </label>
					<input type="text" id="beginDate" name="beginDate" class="formText" value="${(beginDate?string("yyyy-MM-dd"))!}" style="width:80px" title="起始时间" onclick="WdatePicker()" />&nbsp;到&nbsp;
				    <input type="text" id="endDate" name="endDate" class="formText" value="${(endDate?string("yyyy-MM-dd"))!}" style="width:80px" title="截止时间" onclick="WdatePicker()" />	
			<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
			<input type="button" id="exceButton" class="formButton" value="导出数据">
			
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="" name="id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>红包标题</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>红包金额</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>红包有效期</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>红包过期时间</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>适用项目期限</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>满多少可用</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>投资项目名</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>状态</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>状态修改时间</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>操作</a>
					</th>
				</tr>
				<#list pager.result as hb>
					<tr>
						<td>
							${hb.id}	
						</td>
						<td>
							${hb.username}
						</td>
						<td>
							${hb.hongbaoName}
						</td>
						<td>
							${hb.hongbaoMoney}元
						</td>
						<td>
							${hb.expDate}天
						</td>
						<td>
							${(hb.endTime?string("yyyy-MM-dd"))!'--'}
						</td>
						<td>
							${hb.limitStart}&nbsp;天
						</td>
						<td>
							${hb.investFullMomey}&nbsp;元
						</td>
						<td>
							${hb.borrowName}&nbsp;
						</td>
						<td>
							<#if hb.status ==-1>待发放<#elseif hb.status ==0>未使用<#elseif hb.status ==1>已使用<#else>失效</#if>
						</td>
						<td>
							${(hb.modifyDate?string("yyyy-MM-dd HH:mm.ss"))!'--'}
						</td>
						<td>
							<#if hb.status == -1><a href="javascript:void(0);" onclick="ajaxCustomerRealname('${hb.id}');">【审核】</a><#elseif hb.status ==2>【失效】<#else>--</#if>
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
<script type="text/javascript">
function ajaxCustomerRealname(id) {

	if (confirm("确认要审核通过此条数据吗？")) {
		var testTime = new Date().getTime();
		$.ajax({
		        type: "post",
		        dataType : "json",
		        url: '${base}/admin/user_hongbao!ajaxVerify.action?id='+id+'&testTime='+testTime+'&',
		        success: function(data, textStatus){
		        	if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
		        		alert("审核失败");
						window.location.reload();
		        	} else if (data.status=="success") {
		        		alert("审核成功");
		        		window.location.reload();
		        	} else {
			        	alert(data.message);
						window.location.reload();
		        	}
		        },
		        error:function(statusText){
		        	alert(statusText);
		        },
		        exception:function(){
		        	alert(statusText);
		        }
			});
		}
}
	
</script>
</html>