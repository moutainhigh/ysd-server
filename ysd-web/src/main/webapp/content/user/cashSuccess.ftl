<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户 -提现成功</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/tixian_success.css" />
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
                <div class="tixian_success">
                    <div class="success_hint tips">提现申请已提交成功！</div>
                    <table class="table_detail">
                    <tr>
                        <td>提现银行</td>
                        <td>银行卡号</td>
                        <td>提现金额</td>
                        <td>到账金额</td>
                        <td>到账时间</td>
                    </tr>
                    <tr>
                        <td>${accountCash.name}</td>
                        <td>${accountCash.account}</td>
                        <td>${accountCash.total}</td>
                        <td>${accountCash.credited}</td>
                        <td>预计${lastTime}点前</td>
                    </tr>
                    </table>
                    <div class="warm_tips">
                        温馨提示：<span>如果您的银行卡无法正常使用或者出现其他情况，资金将会自动退回到您的账户。</span>
                    </div>
                    <div class="options">
                        <a href="${base}/userCenter/index.do"><span class="back_account">返回账户</span></a>
                        <a href="${base}/userCenter/getMoney.do"><span class="continue_tixian">继续提现</span></a>
                    </div>
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
			$(".center_left_wdzc").addClass("current");<#-- user_center_left.ftl 选中样式 -->
		});

	</script>
</body>
</html>