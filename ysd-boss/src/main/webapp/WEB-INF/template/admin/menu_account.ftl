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
			<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_ZJGK,ROLE_ACCOUNT_ZJMX,ROLE_ACCOUNT_TZRDSMX,ROLE_ACCOUNT_JLMX">
				<dl>
					<dt>
						<span>投资人资金情况</span>
					</dt>
					<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_ZJGK">
						<dd>
							<a href="account!list.action" target="mainFrame">资金概况</a>
						</dd>
					</@sec.authorize>
					<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_ZJMX">
						<dd>
							<a href="account!accountDetailList.action" target="mainFrame">资金明细</a>
						</dd>
					</@sec.authorize>
					<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_TZRDSMX">
						<dd>
							<a href="user_repayment_detail!list.action" target="mainFrame">投资人代收明细</a>
						</dd>
					</@sec.authorize>
					<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_JLMX">
						<dd>
							<a href="user_award_detail!list.action" target="mainFrame">奖励明细</a>
						</dd>
					</@sec.authorize>
				</dl>
		</@sec.authorize>
		
			<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_TRZRTXSH,ROLE_ACCOUNT_SYTXJL">
				<dl>
					<dt>
						<span>提现管理</span>
					</dt>
					<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_TRZRTXSH">
					<dd>
						<a href="account_cash!investList.action" target="mainFrame">投资人提现审核</a>
					</dd>
					</@sec.authorize>
					<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_SYTXJL">
						<dd>
							<a href="account_cash!list.action" target="mainFrame">所有提现记录</a>
						</dd>
					</@sec.authorize>
				</dl>
			</@sec.authorize>
	
			<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_SYCZJL,ROLE_ACCOUNT_CZSH,ROLE_ACCOUNT_XXCZ">
				<dl>
					<dt>
						<span>充值管理</span>
					</dt>
					
					<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_SYCZJL">
						<dd>
							<a href="user_account_recharge!list.action" target="mainFrame">所有充值记录</a>
						</dd>
					</@sec.authorize>
					<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_CZSH">
					<dd>
						<a href="user_account_recharge!verifyListOnLine.action" target="mainFrame">充值审核</a>
					</dd>
					</@sec.authorize>
					<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_XXCZ">
						<dd>
							<a href="account!recharge.action" target="mainFrame">线下充值</a>
						</dd>
					</@sec.authorize>
				</dl>
			</@sec.authorize>
			
			<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_KCYE,ROLE_ACCOUNT_KFSH">
			<dl>
				<dt>
					<span>余额调整</span>
				</dt>
				<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_KCYE">
					<dd>
						<a href="rewards!deduct.action" target="mainFrame">扣除余额</a>
					</dd>
				</@sec.authorize>
				<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_KFSH">
					<dd>
						<a href="rewards!deductList.action?flag=0" target="mainFrame">扣费审核</a>
					</dd>
				</@sec.authorize>
			</dl>
			</@sec.authorize>
			
			<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_HBFF,ROLE_ACCOUNT_HBPL,ROLE_ACCOUNT_HBMX,ROLE_ACCOUNT_XTHBGL">
			<dl>
				<dt>
					<span>红包管理</span>
				</dt>
				<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_HBFF">
					<dd>
						<a href="user_hongbao!input.action" target="mainFrame">红包单发</a>
					</dd>
				</@sec.authorize>
				<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_HBPL">
					<dd>
						<a href="user_hongbao!inputPL.action" target="mainFrame">红包多发</a>
					</dd>
				</@sec.authorize>
				
				<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_HBMX">
					<dd>
						<a href="user_hongbao!list.action" target="mainFrame">红包明细</a>
					</dd>
				</@sec.authorize>
				
				<@sec.authorize ifAnyGranted="ROLE_ACCOUNT_XTHBGL">
					<dd>
						<a href="hongbao!list.action" target="mainFrame">系统红包管理</a>
					</dd>
				</@sec.authorize>
			</dl>
			</@sec.authorize>
			
	</div>
</body>
</html>