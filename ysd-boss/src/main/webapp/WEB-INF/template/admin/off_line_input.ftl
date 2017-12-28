<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>	
<#assign flag="借款">
<#if type==0><#assign flag="投资">投资人线下投资审核<#elseif type==1>借款人线下借款审核<#else>审核</#if>
</title>
<meta name="Author" content="QMD++ Team" />
<meta name="Copyright" content="QMD++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {
	var $verifyForm = $("#verifyForm");
	var $submitButton = $("#submitButton");
	$submitButton.click( function() {
		var temp='<font color="red">'+$(":radio:checked + span").text()+'</font>'; 
		$.dialog({type: "warn", content: "认证将被"+temp, ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			function right(){
				 $verifyForm.submit();
			}
	});			
});
</script>
</head>
<body class="input">
	<div class="bar">
		<#if type==0>投资人线下投资审核<#elseif type==1>借款人线下借款审核</#if>
	</div>
	<div class="body">
		<form id="verifyForm" action="off_line!updateStatus.action" method="post">
			<input type="hidden" name="id" value="${offLine.id!}">
			<input type="hidden" name="type" value="${type}">
					
			<input type="hidden" name="pager.pageNumber" id="pageNumber" value="${(pager.pageNumber)!}" />
			<input type="hidden" name="pager.orderBy" id="orderBy" value="${(pager.orderBy)!}" />
			<input type="hidden" name="pager.order" id="order" value="${(pager.order)!}" />
			<input type="hidden" name="pager.searchBy" id="order" value="${(pager.searchBy)!}" />
			<input type="hidden" name="pager.keyword" id="order" value="${(pager.keyword)!}" />
			<table class="inputTable">
			
					<tr>
						<th>
							真实姓名: 
						</th>
						<td>
							${offLine.realName}
						</td>
					</tr>
					<tr>
						<th>
							性别: 
						</th>
						<td>
							<#if offLine.sex =="1">男<#elseif offLine.sex="2">女<#else>未知</#if>
						</td>
					</tr>
					<tr>
						<th>
							手机号码: 
						</th>
						<td>
							${offLine.phone}
						</td>
					</tr>
					<tr>
						<th>
							申请日期: 
						</th>
						<td>
							${(offLine.createDate?string("yyyy-MM-dd HH:mm:ss"))!}
						</td>
					</tr>
					<tr>
						<th>
							所属地区：
						</th>
						<td>
							${offLine.areaStore}
						</td>
					</tr>
					<tr>
						<th>
							${flag}金额：
						</th>
						<td>
							${offLine.money}元
						</td>
					</tr>
					<tr>
						<th>
							${flag}时长：
						</th>
						<td>
							${offLine.duration}
						</td>
					</tr>
					<tr>
						<th>
							<#if type==1>意向利率<#else>预期收益</#if>：
						</th>
						<td>
							${offLine.rate}%
						</td>
					</tr>
			<#if type==1>	<!--借款人审核-->
					<tr>
						<th>
							借款目的：
						</th>
						<td>
							${offLine.purpose}
						</td>
					</tr>
					
					<tr>
						<th>
							抵押物介绍：
						</th>
						<td>
							${offLine.introduce}
						</td>
					</tr>
			</#if>
			<#if offLine.status==0>
					<tr>
						<th>
							审核：
						</th>
						<td>
							<input type="radio" name="offLine.status" class="verifyValue_class" value="1" checked><span>通过</span>
							<input type="radio" name="offLine.status" class="verifyValue_class"  value="0" ><span>拒绝</span>
						</td>
					</tr>
					<tr>
						<th>
							备注
						</th>
						<td>
							<textarea class="formTextarea"  name ="offLine.remark" ></textarea>
						</td>
					</tr>
			<#else>
			<tr>
						<th>
							状态：
						</th>
						<td>
							<#if offLine.status==1>通过<#else>拒绝</#if>
						</td>
					</tr>
					
					<tr>
						<th>
							备注：
						</th>
						<td>
							${offLine.remark}
						</td>
					</tr>
			</#if>		
			</table>
			<#if offLine.status==0>
				<div class="buttonArea">
					<input type="button" id="submitButton" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
					<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
				</div>
			</#if>
		</form>
	</div>
</body>
</html>