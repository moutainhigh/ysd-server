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
		<@sec.authorize ifAnyGranted="ROLE_SETTING_XTSZ,ROLE_SETTING_WZCSGL">
			<dl>
				<dt>
					<span>网站设置</span>
				</dt>
				<@sec.authorize ifAnyGranted="ROLE_SETTING_XTSZ">
				<dd>
					<a href="setting!edit.action" target="mainFrame">系统设置</a>
				</dd>
				</@sec.authorize>
				<@sec.authorize ifAnyGranted="ROLE_SETTING_WZCSGL">
					<dd>
						<a href="listing!list.action" target="mainFrame">网站参数管理</a>
					</dd>
				</@sec.authorize>
			</dl>
		</@sec.authorize>
		<@sec.authorize ifAnyGranted="ROLE_SETTING_XSZFTD">
			<dl>
				<dt>
					<span>支付管理</span>
				</dt>
				<@sec.authorize ifAnyGranted="ROLE_SETTING_XSZFTD">
					<dd>
						<a href="recharge_config!list.action" target="mainFrame">线上支付通道</a>
					</dd>
				</@sec.authorize>
			</dl>
		</@sec.authorize>
		<@sec.authorize ifAnyGranted="ROLE_SETTING_GLYGL,ROLE_SETTING_JSGL">
			<dl>
				<dt>
					<span>权限设置&nbsp;</span>
				</dt>
				<@sec.authorize ifAnyGranted="ROLE_SETTING_GLYGL">
					<dd>
						<a href="admin!list.action" target="mainFrame">管理员列表</a>
					</dd>
				</@sec.authorize>
				<@sec.authorize ifAnyGranted="ROLE_SETTING_JSGL">
					<dd>
						<a href="role!list.action" target="mainFrame">角色管理</a>
					</dd>
				</@sec.authorize>
			</dl>
		</@sec.authorize>
	</div>
</body>
</html>