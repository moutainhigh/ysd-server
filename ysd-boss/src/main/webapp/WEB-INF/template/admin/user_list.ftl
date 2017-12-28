<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>会员列表 </title>
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
				location.href='derive_report!exceUserList.action'+
				'?username='+$("input[name='pager.keyword']").val()+
				'&startDate='+$("input[name=beginDate]").val()+
				'&endDate='+$("input[name='endDate']").val();
			}
		})
	});	
</script>
</head>
<body class="list">
	<div class="bar">
		会员列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="user!list.action" method="post">
			<div class="listBar">
				<label>查&nbsp;&nbsp;找: </label>
				<select name="pager.searchBy">
					<option value="username" <#if pager.searchBy == "username"> selected</#if>>
						用户名
					</option>
				</select>
				<input type="text" name="pager.keyword" value="${pager.keyword!}" />&nbsp;&nbsp;
				<label>注册时间: </label>
					<input type="text" id="beginDate" name="beginDate" class="formText" value="${(beginDate?string("yyyy-MM-dd"))!}" style="width:80px" title="起始时间" onclick="WdatePicker()" />&nbsp;到&nbsp;
				    <input type="text" id="endDate" name="endDate" class="formText" value="${(endDate?string("yyyy-MM-dd"))!}" style="width:80px" title="截止时间" onclick="WdatePicker()" />

				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				<input type="button" id="exceButton" class="formButton" value="导出数据">
				&nbsp;&nbsp;
				<label>每页显示: </label>
				<select name="pager.pageSize" id="pageSize">
					<option value="10"<#if pager.pageSize == 10> selected</#if>>
						10
					</option>
					<option value="20"<#if pager.pageSize == 20> selected</#if>>
						20
					</option>
					<option value="50"<#if pager.pageSize == 50> selected</#if>>
						50
					</option>
					<option value="100"<#if pager.pageSize == 100> selected</#if>>
						100
					</option>
				</select>
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th >
						<a href="#" class="sort" name="id" hidefocus>ID</a>
					</th>
					<th >
						<a href="#" class="sort" name="" hidefocus>注册时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="memberType" hidefocus>会员类型/属性</a>
					</th>
					<th>
						<a href="#" class="sort" name="username" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="sort" name="realName" hidefocus>真实姓名</a>
					</th>
					<th>
						<a href="#" class="sort" name="realStatus" hidefocus>实名认证</a>
					</th>
					<th>
						<a href="#" name="" hidefocus>渠道来源</a>
					</th>
					<th>
						<a href="#" name="" hidefocus>注册来源</a>
					</th>
					<th>
						<a href="#" class="sort" name="isLock" hidefocus>状态</a>
					</th>
					<#if showAll> 
					<th>
						<span>操作</span>
					</th>
					</#if>
				</tr>
				<#list pager.result as user>
					<tr>
						<td>
							${user.id}
						</td>
						<td>
							<#if user.createDate>
							${user.createDate?string("yyyy-MM-dd HH:mm:ss")}
							</#if>
						</td>
						<td>
							<#if user.memberType==0>个人<#elseif user.memberType==1>企业</#if>&nbsp;/
							<#if user.typeId==0>投资人<#elseif user.typeId==1>借款人</#if>
						</td>
						<td>
							${user.username}
						</td>
						<td>
							${user.realName}
						</td>
						<td>
							<#if user.realStatusStr=='已认证'>
								<span class="green">已认证</span>
							<#else>
								<span class="red"> 未认证 </span>
							</#if>
						</td>
						<td>
							${user.placeName!'--'}
						</td>
						<td>
							<#if user.appType=='0'>
								ios
							<#elseif user.appType=='1'>
								安卓
							<#else>
							    pc或其他	
							</#if>	
						</td>
						
						<td>
							<#if user.isEnabled && !user.isLock >
								<span class="green">正常</span>
							<#elseif !user.isEnabled>
								<span class="red"> 未启用 </span>
							<#elseif user.isLock>
								<span class="red"> 已锁定 </span>
							</#if>
						</td>
						<#if showAll> 
						<td>
							<a href = "user!info.action?id=${user.id}" title="[详情]">[详情]</a>
						</td>
						</#if>
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