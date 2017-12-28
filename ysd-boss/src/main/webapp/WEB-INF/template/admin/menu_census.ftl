<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理菜单 </title>
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
<body class="menu" style="overflow-x: hidden;overflow-y:auto;margin-bottom: -15px;">
	<div class="body">
			
			<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_COMPARISON,ROLE_ACCOUNT_COMPARISON_OFFLINE,ROLE_ACCOUNT_COMPARISON_RESULT,ROLE_ACCOUNT_TYPE_RECORD">
				<dl>

					<dt>
						<span>网站数据核对</span>
					</dt>
<#--
					<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_COMPARISON">
						<dd>
							<a href="temporary!list.action" target="mainFrame">线上充值数据核对</a>
						</dd>
					</@sec.authorize>
					
					<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_COMPARISON_OFFLINE">
						<dd>
							<a href="temporary!offlineList.action" target="mainFrame">线下充值数据核对</a>
						</dd>
					</@sec.authorize>
					<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_COMPARISON_RESULT">
						<dd>
							<a href="temporary!basicList.action" target="mainFrame">保存结果</a>
						</dd>
					</@sec.authorize>
-->
					<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_TYPE_RECORD">
						<dd>
							<a href="account!accountRechargeDetailTotal.action" target="mainFrame">资金分类记录</a>
						</dd>
						<dd>
							<a href="report_statistics_daily!index.action" target="mainFrame">每日资金统计</a>
						</dd>
					</@sec.authorize>
				</dl>
			</@sec.authorize>	
			
			<@sec.authorize ifAnyGranted="ROLE_CENSUS_USER_ACCOUNT">
				<dl>
					<dt>
						<span>会员资金核对</span>
					</dt>
					
					<@sec.authorize ifAnyGranted="ROLE_CENSUS_USER_ACCOUNT">
						<dd>
							<a href="census!userList.action" target="mainFrame">每日资金核对</a>
						</dd>
						<dd>
							<a href="census!userDetail.action" target="mainFrame">会员资金核对</a>
						</dd>
					</@sec.authorize>
				</dl>
			</@sec.authorize>
	</div>
</body>
</html>