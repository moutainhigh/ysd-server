<!DOCTYPE html>
<html lang="en">
<head>
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-帮助中心</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/help_center.css" />
     <style>
    	#header .header_wrap .header_shadow {
    		background-color: #ffffff;
		    background-color: #ffffff\9;
		    opacity: 1;
		    filter: alpha(opacity=1);
    	}
    </style>
</head>
<body>
<!-- 头部 -->
<#include "/content/common/header.ftl">
<div class="shadow_bg">
<!--内容区域-->
<div class="content">
    <!--侧边导航-->
    <#include "/content/common/about_left.ftl">
    <!--帮助中心-->
    <div class="help_center fr">
        <div class="help_center_title">帮助中心</div>
        <div class="help_center_content">
            <div class="help_center_content_left">
                <div class="account box">
                    <div class="account box_title">会员账户</div>
                    <div class="hr">
                        <div class="hr_left"></div>
                    </div>
                    <div>
                        <div class="account_option">
                            <span><a href="${base}/article/helpCenter.do?enumKey=A#a1">注册</a></span>
                            <span><a href="${base}/article/helpCenter.do?enumKey=A#a2">登录</a></span>
                            <span><a href="${base}/article/helpCenter.do?enumKey=A#a3">密码</a></span>
                            <span><a href="${base}/article/helpCenter.do?enumKey=A#a4">更换手机号码</a></span>
                        </div>
                    </div>
                </div>
                <div class="investment box">
                    <div class="investment box_title">投资</div>
                    <div class="hr">
                        <div class="hr_left"></div>
                        <div class="hr_right"></div>
                    </div>
                    <div>
                        <div class="account_option">
                            <span><a href="${base}/article/helpCenter.do?enumKey=C#c1">绑卡认证</a></span>
                            <span><a href="${base}/article/helpCenter.do?enumKey=C#c2">解绑银行卡</a></span>
                            <span><a href="${base}/article/helpCenter.do?enumKey=C#c3">更换银行卡</a></span>
                        </div>
                    </div>
                </div>
                <div class="benefit box">
                    <div class="benefit box_title">我的优惠</div>
                    <div class="hr">
                        <div class="hr_left"></div>
                        <div class="hr_right"></div>
                    </div>
                    <div>
                        <div class="account_option">
                            <span><a href="${base}/article/helpCenter.do?enumKey=E#e1">红包</a></span>
                            <span><a href="${base}/article/helpCenter.do?enumKey=E#e2">奖励</a></span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="help_center_content_left">
                <div class="product box">
                    <div class="product box_title">理财产品</div>
                    <div class="hr">
                        <div class="hr_left"></div>
                        <div class="hr_right"></div>
                    </div>
                    <div>
                        <div class="account_option">
                            <span><a href="${base}/article/helpCenter.do?enumKey=B#b1">新手标</a></span>                       
                            <span><a href="${base}/article/helpCenter.do?enumKey=B#b2">金兑宝</a></span>
                            <span><a href="${base}/article/helpCenter.do?enumKey=B#b3">金企宝</a></span>
                            <span><a href="${base}/article/helpCenter.do?enumKey=B#b4">特定项目融资</a></span>
                        </div>
                    </div>
                </div>
                <div class="income box">
                    <div class="income box_title">回款与收益</div>
                    <div class="hr">
                        <div class="hr_left"></div>
                        <div class="hr_right"></div>
                    </div>
                    <div>
                        <div class="account_option">
                            <span><a href="${base}/article/helpCenter.do?enumKey=D#d1">收益计算</a></span>
                            <span><a href="${base}/article/helpCenter.do?enumKey=D#d2">提现到账</a></span>
                        </div>
                    </div>
                </div>
                <div class="noun box">
                    <div class="noun box_title">名词解释</div>
                    <div class="hr">
                        <div class="hr_left"></div>
                        <div class="hr_right"></div>
                    </div>
                    <div>
                        <div class="account_option">
                            <span><a href="${base}/article/helpCenter.do?enumKey=F#f1">年化收益 </a></span>
                            <span><a href="${base}/article/helpCenter.do?enumKey=F#f2">计息方式 </a></span>
                            <span><a href="${base}/article/helpCenter.do?enumKey=F#f3">还款方式 </a></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="clear"></div>
<br><br><br><br><br><br>
</div>
<!--底部-->

<#include "/content/common/footer.ftl">
</body>
<script src="${base}/js/jquery-1.11.3.min.js"></script>
<script src="${base}/js/common.js"></script>
<script>	
$(function(){
	$("#header_gywm").addClass("current");
	$("#header_aqbz").find("a").css('border',0);
});
</script>
</html>
