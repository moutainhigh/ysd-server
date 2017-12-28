 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>标列表 </title>
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
		过期标待审列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="borrow!outDateBorrow.action" method="post">
		<input type = "hidden" name="borrow.id">
			<div class="listBar">
				用户名：<input type="text" name="borrow.user.username" id="username" value="${(borrow.user.username)!}" />
				
				标类型:
				 <select name ="borrow.type"id="type" ><option value="" <#if borrow=''|| borrow.type==''>selected</#if> >全部</option>
				<option value="1" <#if borrow!='' && borrow.type == 1>selected</#if> >天标</option>
				<option value="0" <#if borrow!='' && borrow.type == 0>selected</#if> >秒标</option>
				<option value="2" <#if borrow!='' && borrow.type == 2>selected</#if> >流转标</option>
				<option value="3" <#if borrow!='' && borrow.type == 3>selected</#if> >信用标</option>
				</select>
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
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
					<th>
						<a href="#" class="sort" name="id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="sort" name="username" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="sort" name="name" hidefocus>借款标题</a>
					</th>
					<th>
						<a href="#" class="sort" name="account" hidefocus>借款金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="apr" hidefocus>天利率</a>
					</th>
					<th>
						<a href="#" class="sort" name="timeLimit" hidefocus>借款期限</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>借款类型</a>
					</th>
					<th>
						<a href="#" class="sort" name="status" hidefocus>状态</a>
					</th>
					
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as borrow>
					<#if borrow.borrowDetailSet>
					<tr>
						
						<td style="color:#FF0000">
							${borrow.id}
						</td>
						<td style="color:#FF0000">
							${borrow.user.username}
						</td>
						<td style="color:#FF0000">
							${borrow.name}
						</td>
						<td style="color:#FF0000">
							${borrow.account}元
						</td>
						<td style="color:#FF0000">
							${borrow.apr}‰
						</td>
						<td style="color:#FF0000">
							${borrow.timeLimit}天
						</td>
						<td>
							<#if (borrow.type)==0> 秒标
							<#elseif  (borrow.type)==1> 天标
							<#elseif  (borrow.type)==2> 流转标
							<#elseif  (borrow.type)== 3> 信用标
							<#else> 其它
							</#if>	
						</td>
						<td>
							已过期
						</td>
							
						<td>
							<a href="javascript:void(0);" class="deleteBank" id="${borrow.id}" title="[撤回]"><span style="color:#FF0000">[撤回]</span></a>
							<a href="borrow!query.action?id=${borrow.id}" title="[查看]">[查看]</a>
							
						</td>
					</tr>
					<#else>
						<tr>
							
							<td>
								${borrow.id}
							</td>
							<td>
								${borrow.user.username}
							</td>
							<td >
								${borrow.name}
							</td >
							<td >
								${borrow.account}元
							</td >
							<td>
								${borrow.apr}‰
							</td>
							<td>
								${borrow.timeLimit}天
							</td>
							<td>
								<#if (borrow.type)==0> 秒标
								<#elseif  (borrow.type)==1> 天标
								<#elseif  (borrow.type)==2> 流转标
								<#elseif  (borrow.type)== 3> 信用标
								<#else> 其它
								</#if>	
							</td>
							<td>
								已过期
							</td>
								
							<td>
								<a href="javascript:void(0);" class="deleteBank" id="${borrow.id}" title="[撤回]">[撤回]</a>
								<a href="borrow!query.action?id=${borrow.id}" title="[查看]">[查看]</a>
								
							</td>
						</tr>
					</#if>
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

<script type="text/javascript">
	$().ready( function() {
		$deleteBank = $(".deleteBank");
		$deleteBank.click(function(){
			$this=$(this);
			$.dialog({type: "warn", content: "确认要撤回此标吗？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			function right(){
				$.ajax({
					url: "${base}/admin/borrow!withdraw.action",
					data: {"id":$this.attr("id")},
					type: "POST",
					dataType: "json",
					cache: false,
					success: function(data) {
						$.message({type: data.status, content: data.message});
						if(data.status=="success"){
							$this.parent().parent().remove();
						}
					}
				});
			}
		});
		
		
		});
 </script>
</html>