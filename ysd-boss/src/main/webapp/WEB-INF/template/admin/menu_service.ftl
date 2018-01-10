<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理菜单</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript">
$().ready(function() {
	var $menuItem = $(".menu a");
	var $menudd = $(".menu dd");
	$menuItem.click(function() {
		var $this = $(this);
		$menudd.removeClass("current");
		$this.parent().addClass("current");
	});
})
</script>
</head>
<body class="menu">
	<div class="body">
		<@sec.authorize ifAnyGranted="ROLE_BORROWER_VERIFY_GR,ROLE_BORROWER_VERIFY_QY">	
		<dl>
			<dt>
				<span>对接服务商管理</span>
			</dt>
			<@sec.authorize ifAnyGranted="ROLE_BORROWER_ADD_GR">
				<dd>
					<a href="agency!readyAdd.action" target="mainFrame">新增对接服务商</a>
				</dd>
			</@sec.authorize>
			<@sec.authorize ifAnyGranted="ROLE_BORROWER_VERIFY_GR">
				<dd>
					<a href="agency!readyList.action" target="mainFrame">对接服务商审核</a>
				</dd>
			
				<dd>
					<a href="agency!list.action" target="mainFrame">对接服务商列表</a>
				</dd>
			</@sec.authorize>
		</dl>
		</@sec.authorize>	
			
			
	</div>
</body>
</html>