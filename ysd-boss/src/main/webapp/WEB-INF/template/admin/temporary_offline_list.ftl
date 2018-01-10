<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>线下充值数据核对 </title>
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
		线下充值数据核对&nbsp;总记录数: <span id="total_span">${pager.totalCount}</span> (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="temporary!offlineList.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" id="saveRechargeClass"  value="保存" hidefocus />
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<!--<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>-->
					<th>
						<a href="#" class="" name="id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="" name="createDate" hidefocus>提交时间</a>
					</th>
					<th>
						<a href="#" class="" name="rechargeDate" hidefocus>完成时间</a>
					</th>
					<th>
						<a href="#" class="" name="rechargeInterface" hidefocus>数据接口</a>
					</th>
					<th>
						<a href="#" class="" name="tradeNo" hidefocus>订单号</a>
					</th>
					<th>
						<a href="#" class="" name="status" hidefocus>状态</a>
					</th>
					<th>
						<a href="#" class="" name="money" hidefocus>充值金额</a>
					</th>
					<th>
						<a href="#" class=""  hidefocus>状态核对结果</a>
					</th>
					<th>
						<a>操作</a>
					</th>
				</tr>
				<#list pager.result as u>
					<#assign d0 = u.comDate?split(",")[0] />
					<#assign d1 = u.comDate?split(",")[1] />
					<#assign m0 = u.comDate?split(",")[2] />
					<#assign m1 = u.comDate?split(",")[3] />
					<#assign tg_flag = false />
					
					<#if (d0=='1' && m0=='1') ||(d0=='6' && m0=='1')  ||(d0=='5')>
						<#assign tg_flag = true />
					</#if>
					<tr <#if tg_flag> class = "tgsh_class" </#if>>
						<!--<td>
							<input type="checkbox" name="idsBatch" value="${u.id},${d0}" />
						</td>-->
						<td>
							${u.id}
						</td>
						<td>
							${(u.createDate?string("yyyy-MM-dd HH:mm:ss"))!}
						</td>
						<td>
							${(u.rechargeDate?string("yyyy-MM-dd HH:mm:ss"))!}
						</td>
						<td>
							${u.rechargeInterface.name}
						</td>
						<td>
							${u.tradeNo}
						</td>
						<td>
							<#if u.status==2><span class="red">审核中<#elseif u.status==1><span class = "green">成功</#if></span>
						</td>
						<td>
							${u.money?string.currency}
						</td>
						<td>
							${d1}
						</td>
						<td><#if tg_flag>
								已通过
							<#else>
								<a href="javascript:void(0)" class="pass" ret="${d0}" rid="${u.id}">通过</a>
							</#if>
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
	$pass = $(".pass");
	$save = $("#saveRechargeClass");
	$pass.click(function(){
		var $this = $(this);
		var ret = $this.parent().find("a").attr("ret");
		var id = $this.parent().find("a").attr("rid");
		
		$.dialog({type: "warn", content: "确认要通过吗？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			function right(){
				$.ajax({
					url: "${base}/admin/temporary!ajaxPass.action",
					data: {"rechargeStatus.comparisonState":ret,"id":id},
					type: "POST",
					dataType: "json",
					cache: false,
					success: function(data) {
						$.message({type: data.status, content: data.message});
						if(data.status=="success"){
							//$total=$("#total_span");
							//$total.html(parseInt($total.html())-1);
							$this.parent().parent().remove();
						}
					}
				});
			}
	});
	
	
	$save.click(function(){
		var $this = $(this);
		$.dialog({type: "warn", content: "处理完毕，保存结果", ok: "确 定", cancel: "取 消", modal: true, okCallback:passRight});
			function passRight(){
				$.ajax({
					url: "${base}/admin/temporary!ajaxMigrate.action",
					type: "POST",
					dataType: "json",
					cache: false,
					success: function(data) {
						$.message({type: data.status, content: data.message});
						$total=$("#total_span");
						$total.html(parseInt($total.html())-data.size);
						$tgsh_class = $(".tgsh_class");
						if($tgsh_class.size()>0){
							$tgsh_class.remove();
						}
					}
				});
			}
	});
	
	
	
})
</script>
</html>