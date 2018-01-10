<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8" />
 <#assign flag="借款">
 <#if loginUser.typeId==0>
  <#assign flag="投资">
 </#if>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-线下${flag}管理-申请记录</title>
	<#include "/content/common/meta.ftl">
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">

<!-- content -->
<div class="admin clearfix">

	<#include "/content/common/user_center_left.ftl">

	<!-- right -->
	<div class="admin-con">
		<!-- location -->
		<div class="breadcrumb">
			<a href="${base}/">${Application ["qmd.setting.name"]}</a> &gt; <a href = "${base}/userCenter/toOffline.do">线下${flag}管理</a>  &gt; 申请记录
		</div>
		
		<div class="tab clearfix">
			<ul>
				<li><a href="${base}/userCenter/toOffline.do"><span>我要线下${flag}</span></a></li>
				<li><a href="javascript:void(0)" class="current"><span>申请记录</span></a></li>
			</ul>		
		</div>
		<div class="tab-con special">
			<form id="listForm" method="post" action="toOfflineList.do" >
			<!-- filter -->
			<div class="filter">
				申请时间：<input type="text" id = "minDate" name="minDate" class="txt" onclick="WdatePicker()" value="${(minDate)!}" size="15">
						   到 <input type="text" id = "maxDate" name="maxDate" class="txt" onclick="WdatePicker()" value="${(maxDate)!}" size="15">
				<!--关键字 <input type="text" class="txt" /> -->
				 <select name = "status" class="sel">
		      		<option value="" >所有状态</option>
		      		<option value="2" <#if status == 2>selected</#if> >配对中</option>
		      		<option value="1" <#if status == 1>selected</#if> >成功</option>
		      		<option value="0" <#if status == 0>selected</#if> >失败</option>
		     	 </select>
				<input type="button" value="搜 索" class="btn s2" onclick="gotoPage(1);" />
			</div>

			<!-- data list -->
			<div class="data-list">
				<table class="tac">
						 <#if loginUser.typeId==0>
						  		<thead>
						  		    <tr>
									 <th width="70">申请时间</th>
									 <th>投资额度</th>
									 <th>期望收益</th>
									 <th>投资时长</th>
									 <th>申请状态</th>
								   </tr>
								</thead>
                               <tbody>
                               <#list pager.result as offLine>
								   <tr>
								   	   <td>${offLine.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
									   <td>${offLine.money?string.currency}元</td>
									   <td>${offLine.rate}%</td>
									   <td>${substring(offLine.duration, 16, "")}</td>
									   <td><#if offLine.status = 2>配对中<#elseif offLine.status==1>已完成<#elseif offLine.status==0>失败</#if></td>
								    </tr>
								 </#list>
								<@pagination pager=pager baseUrl="/userCenter/toOfflineList.do" parameterMap = parameterMap>
									<#include "/content/pager.ftl">
								</@pagination>
						      </tbody>
						  <#elseif loginUser.typeId==1>
						  		<thead>
									<tr>
										 <th>申请时间</th>
										 <th>投资额度</th>
										 <th>期望收益</th>
										 <th>投资时长</th>
										 <th>所属区域</th>
										 <th>借款目的</th>
										 <th>抵押物介绍</th>
										 <th>申请状态</th>
									</tr>
								</thead>
                               <tbody>
                               <#list pager.result as offLine>
								   <tr>
								   	   <td>${offLine.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
									   <td>${offLine.money?string.currency}元</td>
									   <td>${offLine.rate}%</td>
									   <td>${substring(offLine.duration, 16, "")}</td>
									   <td>${substring(offLine.areaStore, 25, "")}</td>
									   <td>${substring(offLine.purpose, 16, "")}</td>
									   <td>${substring(offLine.introduce, 16, "")}</td>
									   <td><#if offLine.status = 2>配对中<#elseif offLine.status==1><span class="c1">已完成</span><#elseif offLine.status==0>失败</#if></td>
								    </tr>
								 </#list>
								<@pagination pager=pager baseUrl="/userCenter/toOfflineList.do" parameterMap = parameterMap>
									<#include "/content/pager.ftl">
								</@pagination>
						      </tbody>
						  </#if>
				</table>
			</div>
			</form>
		</div>
	</div>
</div>

<#include "/content/common/footer.ftl">

</body>
<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	<#if flag="投资">
		$("#offline_investment").addClass("current");
	<#else>
		$("#offline_borrowing").addClass("current");
	</#if>	
});
</script>
</html>
