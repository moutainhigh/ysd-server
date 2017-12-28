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
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
</head>
<body class="list">
	<div class="bar">
		红包明细&nbsp;总记录数: <span id="total_span">${pager.totalCount}</span> (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="user_hongbao!list.action" method="post">
		<input type="hidden" name="pager.searchBy" value="user.username">
			<div class="listBar">
			<label>用户名：</label>
				<input type="text" name="pager.keyword" id="keyword" value="${(pager.keyword)!}" />
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="" name="id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>红包归属</a>
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
						<a href="#" class="" name="" hidefocus>可用终端</a>
					</th>
					<th>
						<a href="#" class="" name="" hidefocus>状态</a>
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
						<td><#if hb.user?exists>
							${(hb.user.username)!'--'}
							<#else>--</#if>
						</td>
						<td>
							${hb.money}
						</td>
						<td>
							${hb.expDate}天
						</td>
						<td>
							${(hb.endTime?string("yyyy-MM-dd"))!'--'}
						</td>
						<td>
							${hb.limitStart}&nbsp;-&nbsp;${hb.limitEnd}&nbsp;天
						</td>
						<td>
							<#if hb.isPc==1>PC</#if>
							<#if hb.isPc==1 && hb.isApp==1>、</#if>
							<#if hb.isApp==1>移动</#if>
							<#if hb.isHfive==1 && hb.isApp==1>、</#if>
							<#if hb.isHfive==1>H5</#if>
						</td>
						<td>
							<#if hb.status ==-1>待发放<#elseif hb.status ==0>未使用<#elseif hb.status ==1>已使用<#else>失效</#if>
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