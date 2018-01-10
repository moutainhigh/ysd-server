<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-绑卡认证</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/add_card.css" />
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

        <!-- yujian
        <div class="my_card">
            我的银行卡：
            <a href="${base}/userCenter/toBankInput.do"><div class="card">
                    添加银行卡
            </div></a>
        </div>
        <div class="warn">
            <div class="tips">温馨提示：</div>
            <div class="tips_content">只能有一张银行卡用于提现及在手机端投资哦！</div>
        </div> -->


        <div class="warn">
            <div class="tips">温馨提示：</div>
            <div class="tips_content">请用手机app进行实名认证！</div>
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
			$('#header_gywm').find('a').css('border',0);
			$(".center_left_bkrz").addClass("current");<#-- user_center_left.ftl 选中样式 -->
		});

	</script>
</body>
</html>