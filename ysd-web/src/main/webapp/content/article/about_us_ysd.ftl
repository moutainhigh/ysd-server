<!DOCTYPE html>
<html lang="en">
<head>
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-关于我们-平台简介</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/about_us.css">
    <style>
        #header {
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
<!--内容区域-->
<div class="shadow_bg">
    <div class="content">
        <!--侧边导航-->
	 	 <#include "/content/common/about_left.ftl">
        <!--关于我们-->
        <div class="about_us fr">
            <img src="${base}/img/about_03_03.jpg" class="fr">
            <div class="briefing_title">我们是谁</div>
            <div class="briefing_detail">温州乐商投资有限公司成立于2011年4月27日，注册资本金5000万人民币，现实缴资本5000万人民币。公司旗下“乐商贷”平台已获得浙江省通信管理局ICP备案，并已和渤海银行开展银行存管业务对接，预计10月份完成银行存管。乐商贷聚焦小微金融及商会内部金融，致力于打造全场景金融新生态。乐商贷充分融合金融与“互联网+”思维，以O2O模式让金融对接资产，以专业开放的方式将民间资金引入普惠金融体系，实现财富管理、生活服务、支付和供应链之间的闭环体系，倡导以普惠金融实践者为使命，致力于成为中国互金行业领导者。
            </div>
            <div class="briefing_title">我们做什么</div>
            <div class="briefing_detail">经营范围主要包括：对实业的投资,对房地产的投资,对酒店业的投资,对旅游业的投资,对建筑业的投资,nauy借贷撮合业务服务。
            </div>
            <div class="briefing_title">我们的优势</div>
            <div class="advantage">
                <div class="advantage_one">
                    <!--<img src="${base}/img/about_03.png">-->
                </div>
                <div class="advantage_two">
                    <!--<img src="${base}/img/about_05.png">-->
                </div>
                <div class="advantage_three">
                    <!--<img src="${base}/img/about_0.png">-->
                </div>
            </div>
            <div class="briefing_title">我们的背景</div>
            <img src="${base}/img/ab_bg_13.jpg" style="width: 100%">
          <div class="briefing_title">公司资质</div>
            <img src="${base}/img/inverment.jpg"  style="width: 100%">
           <!-- <div class="briefing_title" id="map">联系我们</div>
            <img src="${base}/img/map_03.png" style="width: 100%">
            <div class="email">
                <span class="fl">公司邮箱：service@yueshang.onaliyun.com</span>
                <span class="fr">公司地址：</span>
            </div>
            <div class="clear"></div>
            <div class="tel">
                全国统一服务热线：400-057-7820（人工服务时间9:00-17:30）
            </div>-->
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