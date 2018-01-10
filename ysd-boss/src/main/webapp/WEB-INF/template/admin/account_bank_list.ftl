<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>银行卡列表 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
	function unbind(id){
		if(confirm('确定要解除绑定吗？')){
			$.ajax({
					url: "account_bank!delete.action",
					data: {"id" : id},
					type: "POST",
					dataType: "json",
					success: function(data) {
						if(data.status=="success"){
							alert("解绑成功！");
							window.location.reload();
						} else {
							alert(data.message);
							return;
						}
					}
				});
		}
	}
</script>
</head>
<body class="list">
	<div class="bar">
		所有银行卡记录列表 &nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="account_bank!list.action" method="post">
			<div class="listBar">
				用户名:<input type="hidden" name="pager.searchBy" value="user.phone">
				<input type="text" id="phone" name="pager.keyword" value="${pager.keyword!}" />
				<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="sort" name="account.id" hidefocus>ID</a>
					</th>
					
					<th>
						<a href="#" class="sort" name="user.phone" hidefocus>用户名</a>
					</th>
					
					<th>
						<a href="#" class="sort" name="user.realName" hidefocus>账户姓名</a>
					</th>
					
					<th>
						<a href="#" class="sort" name="account" hidefocus>账号</a>
					</th>
					<th>
						<a href="#" class="sort" name="bankName" hidefocus>银行</a>
					</th>
					<th>
						<a href="#" class="sort" name="branch" hidefocus>开户行</a>
					</th>
					
					
					<th>
						<a href="#"  hidefocus>操作</a>
					</th>
		
				</tr>
				<#list pager.result as bank>
					<tr>
						<td>
							${(bank.id)!}
						</td>
						<td>
							${(bank.user.phone)!}
						</td>
						<td>
							${(bank.user.realName)!}
						</td>
						<td>
							${(bank.account)!}
						</td>
						<td>
							${(bank.bankName)!}
						</td>
						<td>
							${(bank.branch)!}
						</td>
						
						
						<td>
							<a href="account_bank!edit.action?id=${(bank.id)!}">[编辑信息]</a>
							<a onclick="unbind(${bank.id});">[解除绑定]</a>
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