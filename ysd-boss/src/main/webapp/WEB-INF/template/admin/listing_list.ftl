<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>分类列表</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$(document).ready(function() {

	var $deleteListing = $("#listTable .deleteListing");
	var $listingName = $("#listTable .listingName");
	var $hh = $(".listingName .category");
	$hh.parent().parent().hide();
	
	// 删除商品分类
	$deleteListing.click( function() {
		if (confirm("您确定要删除此分类吗?") == false) {
			return false;
		}
	});

	// 树折叠
	$listingName.click( function() {
		var $this = $(this);
		var grade = $this.parent().attr("grade");
		var isHide;
		$this.parent().nextAll("tr").each(function(){
			$this = $(this);
			var thisLevel = $this.attr("grade");
			if(thisLevel <=  grade) {
				return false;
			}
			if(isHide == null) {
				if($this.is(":hidden")){
					isHide = true;
				} else {
					isHide = false;
				}
			}
			if(isHide) {
				$this.show();
			} else {
				$this.hide();
			}
		});
	});	
})
</script>
</head>
<body class="list">
	<div class="bar">
		分类列表&nbsp;总记录数: ${listingTreeList?size}
	</div>
	<div class="body articleCategory">
		<form id="listForm" action="listing!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='listing!add.action'" value="添加分类" hidefocus />
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<span>分类名称</span>
					</th>
					<th>
						<span>标识</span>
					</th>
					<th>
						<span>值</span>
					</th>
					<th>
						<span>状态</span>
					</th>
					<th>
						<span>排序</span>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list listingTreeList as listing>
					<tr grade="${listing.grade}">
						<td class="listingName">
							<#if listing.grade == 0>
								<span class="pointer firstCategory" style="margin-left: ${listing.grade * 20}px;">
									${listing.name}
								</span>
							<#else>
								<span class="pointer category" style="margin-left: ${listing.grade * 20}px;">
									${listing.name}
								</span>
							</#if>
						</td>
						<td>
							${listing.sign}
						</td>
						<td>
							${listing.keyValue}
						</td>
						<td>
							<#if listing.isEnabled >
								<span class="green">正常</span>
							<#else>
								<span class="red"> 未启用 </span>
							
							</#if>
						</td>
						<td>
						<#if listing.grade != 0>&nbsp;&nbsp;&nbsp;</#if>
							${listing.orderList}
						</td>
						<td>
							<#if (listing.children?size > 0)>
								<span title="无法删除">[删除]</span>
							<#else>
								<a href="listing!delete.action?id=${listing.id}" class="deleteListing" title="删除">[删除]</a>
							</#if>
							<a href="listing!edit.action?id=${listing.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if listingTreeList?size == 0>
				<div class="noRecord">没有找到任何记录!</div>
			</#if>
		</form>
	</div>
</body>
</html>