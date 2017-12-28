<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-账户管理</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/account_manage.css" />
</head>
<body id="bg">
    <!-- 头部 -->
    <#include "/content/common/header.ftl">

    <!--内容区域-->
    <div id="main">
        <!--左边导航栏-->
        <#include "/content/common/user_center_left.ftl">
        <!--右边主内容区-->
        <div class="content fr">
            <div class="title">账户信息</div>
            <div class="account">
                账户：<span>${loginUser.username}</span>
            </div>
            <div class="title">密码管理</div>
            <div class="login">
                登录密码
                <a href="${base}/member/toPassword.do"><div class="modify">修改</div></a>
            </div>
            <div class="deal">
                交易密码
               <a href="${base}/member/toPayPassword.do"><div class="modify">修改</div></a>
            </div>
        </div>
        <div class="clear"></div>
    </div>

    <!--底部-->
<#include "/content/common/footer-center.ftl">
    <script src="${base}/js/jquery-1.11.3.min.js"></script>
    <script src="${base}/js/common.js"></script>
    <script type="text/javascript">
		$().ready( function() {
			
			$("#header_wdzh").addClass("current");<#-- header.ftl 选中样式 -->
			$("#header_gywm").find('a').css('border',0);
			$(".center_left_zhgl").addClass("current");<#-- user_center_left.ftl 选中样式 -->
		});

	</script>
</body>
</html>