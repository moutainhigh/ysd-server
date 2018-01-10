<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>路演（日常）统计 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
</head>
<body class="list">
	<div class="bar">
		路演（日常）统计&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="place!tongji.action" method="post">
		<div class="listBar">
				&nbsp;&nbsp;
				<label>标题: </label>
				<input type="text" name="name" value="${name!}" />
				
				<label>发布时间：</label>
				<input type="text" id="dateStart" name="dateStart" class="formText" value="${(dateStart)!}" style="width:85px" title="起始时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />&nbsp;到&nbsp;
				<input type="text" id="dateEnd" name="dateEnd" class="formText" value="${(dateEnd)!}" style="width:85px" title="截止时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				&nbsp;&nbsp;
				
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="sort" name="" hidefocus>渠道名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>活动标题</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>图片</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>发布时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>查看数</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>注册数</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>登录数</a>
					</th>
				</tr>
				<#list pager.result as placechilder>
					<tr>
						<td>
							${placechilder.place.name}
						</td>
						<td>
							${placechilder.name}
						</td>
						<td>
							<a href="${imgUrl}${placechilder.img}" target="_blank">查看</a>
						</td>
						<td>
							${placechilder.createDate?string("yyyy-MM-dd HH:mm:ss")}
						</td>
						<td>
							${placechilder.viewNum}
						</td>
						<td>
							${placechilder.regNum}
						</td>
						<td>
							${placechilder.loginNum}
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