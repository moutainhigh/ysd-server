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
			<dl>
				<dt>
					<span>借款人放款管理&nbsp;</span>
				</dt>
					<dd>
						<a href="fangkuan!borrowerListIng.action" target="mainFrame">审核列表</a>
					</dd>
					<dd>
						<a href="fangkuan!borrowerListSuccess.action" target="mainFrame">放款列表</a>
					</dd>
			</dl>
			<dl>
				<dt>
					<span>保证金放款管理&nbsp;</span>
				</dt>
					<dd>
						<a href="fangkuan!depositListIng.action" target="mainFrame">审核列表</a>
					</dd>
					<dd>
						<a href="fangkuan!depositListSuccess.action" target="mainFrame">放款列表</a>
					</dd>
			</dl>
			<dl>
				<dt>
					<span>手续费放款管理&nbsp;</span>
				</dt>
					<dd>
						<a href="fangkuan!feeListIng.action" target="mainFrame">审核列表</a>
					</dd>
					<dd>
						<a href="fangkuan!feeListSuccess.action" target="mainFrame">放款列表</a>
					</dd>
			</dl>
			<dl>
				<dt>
					<span>放款流水列表&nbsp;</span>
				</dt>
				<dd>
					<a href="borrow_account_detail!list.action" target="mainFrame">放款流水列表</a>
				</dd>
			</dl>
	</div>
</body>
</html>