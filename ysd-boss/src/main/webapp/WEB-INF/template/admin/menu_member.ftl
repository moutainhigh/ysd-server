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
		<@sec.authorize ifAnyGranted="ROLE_USER_HYLB,ROLE_USER_HYXXBJ,ROLE_USER_YHKGL,ROLE_HTML_YQHYXX,ROLE_HTML_YQJLTJ,ROLE_HTML_SCTZTJ,ROLE_HTML_YHTZJL">	
		 <dl>
			<dt>
				<span>投资人管理</span>
			</dt>
			<@sec.authorize ifAnyGranted="ROLE_USER_HYLB">
				<dd>
					<a href="user!list.action" target="mainFrame">会员列表</a>
				</dd>
			</@sec.authorize>
			<@sec.authorize ifAnyGranted="ROLE_USER_HYXXBJ">
				<dd>
					<a href="user!editList.action" target="mainFrame">会员信息编辑</a>
				</dd>
			</@sec.authorize>
			<@sec.authorize ifAnyGranted="ROLE_USER_YHKGL">
				<dd>
					<a href="account_bank!list.action" target="mainFrame">银行卡管理</a>
				</dd>
			</@sec.authorize>
			
			<@sec.authorize ifAnyGranted="ROLE_HTML_YQHYXX">
				<dd>
					<a href="user!spreadlist.action" target="mainFrame">邀请好友信息</a>
				</dd>
			</@sec.authorize>
			<@sec.authorize ifAnyGranted="ROLE_HTML_YQJLTJ">
				<dd>
					<a href="user!spreadlistMoney.action" target="mainFrame">邀请奖励统计</a>
				</dd>
			</@sec.authorize>
				
			<@sec.authorize ifAnyGranted="ROLE_HTML_SCTZTJ">
				<dd>
					<a href="report_first_tender!list.action" target="mainFrame">首次投资统计</a>
				</dd>
			</@sec.authorize>
		
			<@sec.authorize ifAnyGranted="ROLE_HTML_YHTZJL">
				<dd>
					<a href="report_first_tender!allList.action" target="mainFrame">用户投资记录</a>
				</dd>
			</@sec.authorize>
			</dl>
		</@sec.authorize>
		
		<@sec.authorize ifAnyGranted="ROLE_USER_XZFWS,ROLE_USER_FWSSH,ROLE_USER_FWSLB">	
		<dl>
			<dt>
				<span>项目服务商管理</span>
			</dt>
			
			<@sec.authorize ifAnyGranted="ROLE_USER_XZFWS">
				<dd>
					<a href="agency!readyAdd.action" target="mainFrame">新增服务商</a>
				</dd>
			</@sec.authorize>
			
			<@sec.authorize ifAnyGranted="ROLE_USER_FWSSH">
				<dd>
					<a href="agency!readyList.action" target="mainFrame">服务商审核</a>
				</dd>
			</@sec.authorize>
			
			<@sec.authorize ifAnyGranted="ROLE_USER_FWSLB">
				<dd>
					<a href="agency!list.action" target="mainFrame">服务商列表</a>
				</dd>
			</@sec.authorize>

		</dl>
		</@sec.authorize>
		
		<@sec.authorize ifAnyGranted="ROLE_USER_JKRLB">	
		<dl>
			<dt>
				<span>借款人管理</span>
			</dt>
			
			<dd>
				<a href="user!borrowerList.action" target="mainFrame">借款人列表</a>
			</dd>

		</dl>
		</@sec.authorize>
	</div>
</body>
</html>