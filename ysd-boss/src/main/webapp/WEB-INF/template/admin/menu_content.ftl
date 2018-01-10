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
		<@sec.authorize ifAnyGranted="ROLE_HTML_GGTPLB,ROLE_HTML_WZFL,ROLE_HTML_WZGL,ROLE_HTML_PTHDGL">
			<dl>
				<dt>
					<span>内容管理</span>
				</dt>
				<@sec.authorize ifAnyGranted="ROLE_HTML_GGTPLB">
					<dd>
						<a href="scrollpic!list.action" target="mainFrame">广告图片轮播</a>
					</dd>
				</@sec.authorize>
				<@sec.authorize ifAnyGranted="ROLE_HTML_WZFL">
					<dd>
						<a href="article_category!list.action" target="mainFrame">文章分类</a>
					</dd>
				</@sec.authorize>
				<@sec.authorize ifAnyGranted="ROLE_HTML_WZGL">
					<dd>
						<a href="article!list.action" target="mainFrame">文章管理</a>
					</dd>
				</@sec.authorize>
				<@sec.authorize ifAnyGranted="ROLE_HTML_PTHDGL">
					<dd>
						<a href="activity!list.action" target="mainFrame">平台活动管理</a>
					</dd>
				</@sec.authorize>
			</dl>
		</@sec.authorize>
		
		<@sec.authorize ifAnyGranted="ROLE_HTML_HTYMGX">
			<dl>
				<dt>
					<span>网站更新管理</span>
				</dt>
				<dd>
					<a href="build_html!allInput.action" target="mainFrame">后台页面更新</a>
				</dd>
			</dl>
		</@sec.authorize>
		<@sec.authorize ifAnyGranted="ROLE_HTML_FEEDBACK">
			<dl>
				<dt>
					<span>意见反馈管理</span>
				</dt>
				<dd>
					<a href="feedback!list.action" target="mainFrame">意见反馈列表</a>
				</dd>
			</dl>
		</@sec.authorize>
		
			<@sec.authorize ifAnyGranted="ROLE_HTML_QDGL,ROLE_HTML_LYTJ">
				<dl>
					<dt>
						<span>平台数据&nbsp;</span>
					</dt>
				<@sec.authorize ifAnyGranted="ROLE_HTML_QDGL">
					<dd>
						<a href="place!list.action" target="mainFrame">渠道管理</a>
					</dd>
				</@sec.authorize>
					
					
				<@sec.authorize ifAnyGranted="ROLE_HTML_LYTJ">
					<dd>
						<a href="place!tongji.action" target="mainFrame">路演（日常）统计</a>
					</dd>
				</@sec.authorize>
				<dl>
		  </@sec.authorize>
		
	</div>
</body>
</html>