 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><#if borrow>	<#if borrow.status>	<#if borrow.status==0>发标待审<#elseif borrow.status==5>	满标待审	<#elseif borrow.status==9>所有借款</#if></#if></#if> </title>
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
	<#if borrow>
		<#if borrow.status>
			<#if borrow.status==0>
				发标待审
			<#elseif borrow.status==5>
				满标待审
			<#elseif borrow.status==9>
				所有借款
			</#if>
		</#if>
	</#if>
		标列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="borrow!list.action" method="post">
		<!--<input type="hidden" name="borrow.status" id="status" value="${borrow.status}" />-->
		<input type = "hidden" name="borrow.id">
			<div class="listBar">
				<label>借款人：</label><input type="text" name="borrow.user.username" id="username" value="${(borrow.user.username)!}" />
				<label>借款标题：</label><input type="text" name="borrow.name" id="name" value="${(borrow.name)!}" />
				<label>标类型：</label>
				 <select name ="borrow.type"id="type" ><option value="" <#if  borrow='' || borrow.type==''>selected</#if> >全部</option>
				<option value="14" <#if borrow!='' && borrow.type == 14>selected</#if> >天标</option>
				<!--<option value="0" <#if borrow!='' && borrow.type == 0>selected</#if> >秒标</option>
				<option value="2" <#if borrow!='' && borrow.type == 5>selected</#if> >流转标</option>
				<option value="3" <#if borrow!='' && borrow.type == 3>selected</#if> >信用标</option>
				<option value="4" <#if borrow!='' && borrow.type == 15>selected</#if> >月标</option>-->
				<option value="16" <#if borrow!='' && borrow.type == 16>selected</#if> >新手标</option>
				<!--<option value="6" <#if borrow!='' && borrow.type == 17>selected</#if> >体验标</option>-->
				</select>
			
				<label>标状态：</label>
				<select name ="borrow.status"id="status" ><option value="" <#if  borrow='' || borrow.status==''>selected</#if> >全部</option>
				<option value="0" <#if borrow!='' && borrow.status == 0>selected</#if> >等待审核</option>
				
				<option value="1" <#if borrow!='' && borrow.status == 1>selected</#if> >正在招标中</option>
				<option value="2" <#if borrow!='' && borrow.status == 2>selected</#if> >审核未通过</option>
				<option value="3" <#if borrow!='' && borrow.status == 3>selected</#if> >还款中</option>
				<option value="4" <#if borrow!='' && borrow.status == 4>selected</#if> >审核未通过</option>
				<option value="5" <#if borrow!='' && borrow.status == 5>selected</#if> >等待满标审核</option>
				<option value="7" <#if borrow!='' && borrow.status == 7>selected</#if> >已完成的标</option>
				
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
						<a href="#" class="sort" name="id" hidefocus>排序号</a>
					</th>
					<th>
						<a href="#" class="sort" name="user.username" hidefocus>借款人</a>
					</th>
					<th>
						<a href="#" class="sort" name="name" hidefocus>借款标题</a>
					</th>
					<th>
						<a href="#" class="sort" name="account" hidefocus>借款金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="apr" hidefocus>利率</a>
					</th>
					<th>
						<a href="#" class="sort" name="timeLimit" hidefocus>借款期限</a>
					</th>
					<th>
						<a href="#" class="sort" name="type" hidefocus>借款类型</a>
					</th>
					<th>
						<a href="#" class="sort" name="status" hidefocus>状态</a>
					</th>
					
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as borrow>
					<tr>
						
						<td>
							${borrow.id}
						</td>
						<td>
							${borrow.showSort}
						</td>
						<td>
							${borrow.user.username}
						</td>
						<td>
							${borrow.name}
						</td>
						<td>
							${borrow.account}元
						</td>
						<td>
							<@numberPercent val="${borrow.varyYearRate}"; npt>${npt}</@numberPercent>
						</td>
						<td>
							${borrow.timeLimit}<#if borrow.isday=='0'>月<#else>天</#if>
						</td>
						<td>
							<#if (borrow.type)==0> 秒标
							<#elseif  (borrow.type)==14> 天标
							<#elseif  (borrow.type)==5> 流转标
							<#elseif  (borrow.type)== 3> 信用标
							<#elseif  (borrow.type)== 4> 月标
							<#elseif  (borrow.type)== 11>天标
							<#elseif  (borrow.type)== 12>月标
							<#elseif  (borrow.type)== 13>流转标
							<#elseif  (borrow.type)== 15>月标
							<#elseif  (borrow.type)== 16>新手标
							<#elseif  (borrow.type)== 17>体验标
							<#else> 其它
							</#if>	
						</td>
						<td>
							<#if (borrow.status)==0> 等待审核
							<#elseif (borrow.status)==1> 正在招标中
							<#elseif (borrow.status)==2> 审核未通过
							<#elseif  (borrow.status)==3> 还款中
							<#elseif (borrow.status)==4> 审核未通过
							<#elseif  (borrow.status)==5> 等待满标审核
							<#elseif  (borrow.status)==7> 已完成的标
							<#else> 审核未通过
							</#if>
						</td>
							
						<td>
						
						<#if (borrow.status)==0  || borrow.status==5>
							<a href="borrow!edit.action?id=${borrow.id}" title="[审核]"><font color="red">[审核]</font></a>
						</#if>
 						<#if (borrow.status)==0>  
<!--							<a href="borrow!updateContent.action?id=${borrow.id}" title="[修改]"><font color="green">[修改]</font></a>
							<a href="javascript:void(0);" class="deleteBank" id="${borrow.id}" title="[撤回]">[撤回]</a>-->
						<#elseif (borrow.status)==1>
							<a href="borrow!query.action?id=${borrow.id}" title="[查看]">[查看]</a>
							<a href="borrow!updateContent.action?id=${borrow.id}" title="[修改]"><font color="green">[修改]</font></a>
							<a href="javascript:void(0);" class="deleteBank" id="${borrow.id}" title="[撤回]">[撤回]</a>
							
						<#else>
							<a href="borrow!query.action?id=${borrow.id}" title="[查看]">[查看]</a>
							<a href="borrow!updateContent.action?id=${borrow.id}&type=1" title="[修改]"><font color="green">[修改排序]</font></a>
						</#if>
							
						</td>
					</tr>
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
						//$.message({type: data.status, content: data.message});
						if(data.status=="success"){
							//$this.parent().parent().remove();
							alert("撤回成功！");
							window.location.reload();
						} else {
							alert(data.message);
							return;
						}
					}
				});
			}
		});
		
		
		});
 </script>
</html>