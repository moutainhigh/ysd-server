<!DOCTYPE html>
<html>
 <head>
	<title>${Application ["qmd.setting.name"]}-我的账户-积分明细</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
</head>
<body>
<!-- header -->
<#include "/content/common/header.ftl">

<!-- content -->
<div class="admin clearfix">

	<#include "/content/common/user_center_left.ftl">

	<!-- right -->
	<div class="admin-con">
		<!-- breadcrumb -->
		<div class="breadcrumb">
			<a href="${base}/">${Application ["qmd.setting.name"]}</a> &gt; <a href="${base}/userCenter/index.do">我的账户</a> &gt; 积分明细
		</div>
		
		<div class="tab clearfix">
			<ul>
				<li><a href="${base}/account/detail.do?type=statistics" ><span>资金详情</span></a></li>
				<li><a href="${base}/account/detail.do?type=detail" class="current"><span>资金明细</span></a></li>
				<li><a href="#" class="current"><span>积分明细</span></a></li>
			</ul>		
		</div>
		<div class="tab-con">

			<h3>资金明细</h3>
			<form id="listForm" method="post" action="${base}/account/points.do">
			<!-- filter -->
			<div class="filter">
				记录时间：<input type="text" id = "minDate" name="minDate" class="formText" style="width:75px;" onclick="WdatePicker()" value="${(minDate)}"> 到 <input type="text" id = "maxDate" name="maxDate" class="formText" style="width:75px;" onclick="WdatePicker()" value="${(maxDate)}">
			<input type="button" value="搜 索" class="btn s2"  onclick="gotoPage(1);" />
				
			</div>
			
			<!-- data list -->
			<div class="data-list">
				<table class="tac">
					<thead>
						<tr>
							<th width="70">添加时间</th>
							<th>变更积分</th>
							<th>总积分</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody>
					<#list pager.result as userPointsDetail>
						<tr>
							<td>${(userPointsDetail.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</td>
							<td class="tar">${userPointsDetail.points?string(',###')}</td>
							<td class="tar">${userPointsDetail.userPoints?string(',###')}</td>
							<td>${userPointsDetail.remark}</td>
						</tr>
					</#list>
					</tbody>
				</table>
				<@pagination pager=pager baseUrl="/account/points.do" parameterMap = parameterMap>
					<#include "/content/pager.ftl">
				</@pagination>
			</div>
			</form>
		</div>		
	</div>
</div>

<#include "/content/common/footer.ftl">
<script type="text/javascript">
$(function(){
	$("#account_detail").addClass("current");
});
</script>

</body>
</html>
