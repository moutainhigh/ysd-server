<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>活动列表 </title>
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
		活动列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="place!childerList.action?id=${id}" method="post">
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="sort" name="" hidefocus>编号</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>活动标题</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>图片</a>
					</th>
					<th>
						<a href="#" class="sort" name="" hidefocus>活动链接</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as placechilder>
					<tr>
						<td>
							${placechilder.id}
						</td>
						<td>
							${placechilder.name}
						</td>
						<td>
							<a href="${imgUrl}${placechilder.img}" target="_blank">查看</a>
						</td>
						<td>
							<a href="${placechilder.url}" target="_blank">${placechilder.url}</a>
						</td>
						<td>
							<a title="编辑" href="place!childerToedit.action?id=${placechilder.id}">[编辑]</a>
							
							<a title="删除" class = "childerdel" href="javascript:void(0);" cid="${placechilder.id}">[删除]</a>
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
$().ready(function() {
	$childerdel = $(".childerdel");
	$childerdel.click(function(){
		var $this = $(this);
		var cid = $this.parent().find("a").attr("cid");
		
		$.dialog({type: "warn", content: "确认要删除吗？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			function right(){
				$.ajax({
					url: "${base}/admin/place!childerdelete.action",
					data: {"id":cid},
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
	
	
	
})
</script>

</html>