<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理中心 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $menuItem = $("#menu .menuItem");
	var $previousMenuItem;
	
	$menuItem.click( function() {
		var $this = $(this);
		if ($previousMenuItem != null) {
			$previousMenuItem.removeClass("current");
		}
		$previousMenuItem = $this;
		$this.addClass("current");
	})

})
</script>
</head>
<body class="header">
	<div class="body">
		<div class="bodyLeft">
			<div class="logo"></div>
		</div>
		<div class="bodyRight">
			<div class="link">
				<span class="welcome">
					<strong><@sec.authentication property="name" /></strong>&nbsp;您好!&nbsp;
				</span>
				<@sec.authorize ifAnyGranted="ROLE_SETTING_XTSZ,ROLE_SETTING_WZCSGL,ROLE_SETTING_XSZFTD,ROLE_SETTING_GLYGL,ROLE_SETTING_JSGL">
				<a href="page!index.action" target="mainFrame">后台首页</a>
				</@sec.authorize>
			</div>
			<div id="menu" class="menu">
				<ul>
					<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_ZJGK,ROLE_ACCOUNT_ZJMX,ROLE_ACCOUNT_TZRDSMX,ROLE_ACCOUNT_JLMX,ROLE_ACCOUNT_TRZRTXSH,ROLE_ACCOUNT_SYTXJL,ROLE_ACCOUNT_SYCZJL,ROLE_ACCOUNT_CZSH,ROLE_ACCOUNT_XXCZ,ROLE_ACCOUNT_KCYE,ROLE_ACCOUNT_KFSH,ROLE_ACCOUNT_HBFF,ROLE_ACCOUNT_HBPL,ROLE_ACCOUNT_HBMX,ROLE_ACCOUNT_XTHBGL">
						<li class="menuItem">
							<a href="menu!account.action" target="menuFrame" hidefocus>资金管理</a>
						</li>
					</@sec.authorize>
						<@sec.authorize ifAnyGranted="ROLE_BORROW_XMCS,ROLE_BORROW_MEFS,ROLE_BORROW_SYXMLB,ROLE_BORROW_JKRFKSH,ROLE_BORROW_FKLB,ROLE_BORROW_HKCZSH,ROLE_BORROW_XMHKJL">
						<li class="menuItem">
							<a href="menu!credit.action" target="menuFrame" hidefocus>项目管理</a>
						</li>
					</@sec.authorize>
					<@sec.authorize ifAnyGranted="ROLE_HTML_GGTPLB,ROLE_HTML_WZFL,ROLE_HTML_WZGL,ROLE_HTML_PTHDGL,ROLE_HTML_HTYMGX,ROLE_HTML_QDGL,ROLE_HTML_LYTJ,ROLE_HTML_FEEDBACK">
						<li class="menuItem">
							<a href="menu!content.action" target="menuFrame" hidefocus>内容管理</a>
						</li>
					</@sec.authorize>
					<@sec.authorize ifAnyGranted="ROLE_USER_HYLB,ROLE_USER_HYXXBJ,ROLE_USER_YHKGL,ROLE_USER_XZFWS,ROLE_USER_FWSSH,ROLE_USER_FWSLB,ROLE_USER_JKRLB,ROLE_HTML_YQHYXX,ROLE_HTML_YQJLTJ,ROLE_HTML_SCTZTJ,ROLE_HTML_YHTZJL">
						<li class="menuItem">
							<a href="menu!member.action" target="menuFrame" hidefocus>会员管理</a>
						</li>
					</@sec.authorize>
					<@sec.authorize ifAnyGranted="ROLE_SETTING_XTSZ,ROLE_SETTING_WZCSGL,ROLE_SETTING_XSZFTD,ROLE_SETTING_GLYGL,ROLE_SETTING_JSGL">
						<li class="menuItem">
							<a href="menu!setting.action" target="menuFrame" hidefocus>系统管理</a>
						</li>
					</@sec.authorize>
	            </ul>
	            <div class="info">
					<a class="profile" href="admin_profile!edit.action" target="mainFrame">个人资料</a>
					<a class="logout" href="${base}/admin/logout" target="_top">退出</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>