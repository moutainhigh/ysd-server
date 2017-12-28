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
		<@sec.authorize ifAnyGranted="ROLE_BORROW_XMCS,ROLE_BORROW_MEFS,ROLE_BORROW_SYXMLB">
			<dl>
				<dt>
					<span>项目管理</span>
				</dt>
				
				<@sec.authorize ifAnyGranted="ROLE_BORROW_XMCS">
					<dd>
						<a href="borrow!tenderList.action" target="mainFrame">项目初审</a>
					</dd>
				</@sec.authorize>
		
				<@sec.authorize ifAnyGranted="ROLE_BORROW_MEFS">
					<dd>
						<a href="borrow!fullList.action" target="mainFrame">满额复审</a>
					</dd>
				</@sec.authorize>
				<@sec.authorize ifAnyGranted="ROLE_BORROW_SYXMLB">
					<dd>
						<a href="borrow!list.action" target="mainFrame">所有项目列表 </a>
					</dd>
				</@sec.authorize>
			</dl>
			
		</@sec.authorize>
		
		<@sec.authorize ifAnyGranted="ROLE_BORROW_JKRFKSH,ROLE_BORROW_FKLB">
			<dl>
				<dt>
					<span>项目放款管理&nbsp;</span>
				</dt>
				<@sec.authorize ifAnyGranted="ROLE_BORROW_JKRFKSH">
					<dd>
						<a href="fangkuan!borrowerListIng.action" target="mainFrame">借款人放款审核</a>
					</dd>
				</@sec.authorize>
				<@sec.authorize ifAnyGranted="ROLE_BORROW_FKLB">
					<dd>
						<a href="fangkuan!borrowerListSuccess.action" target="mainFrame">放款列表</a>
					</dd>
				</@sec.authorize>
			</dl>
		</@sec.authorize>
		
		<@sec.authorize ifAnyGranted="ROLE_BORROW_HKCZSH,ROLE_BORROW_XMHKJL">
			<dl>
				<dt>
					<span>项目还款管理</span>
				</dt>
					<@sec.authorize ifAnyGranted="ROLE_BORROW_HKCZSH">
						<dd>
							<a href="borrow_recharge!list.action" target="mainFrame">还款充值审核</a>
						</dd>
					</@sec.authorize>
					<@sec.authorize ifAnyGranted="ROLE_BORROW_XMHKJL">
						<dd>
							<a href="borrow_repayment_detail!borrowRepayment.action" target="mainFrame">项目回款记录 </a>
						</dd>
					</@sec.authorize>
			</dl>
		</@sec.authorize>
		<dl>
	
	</div>
</body>
</html>