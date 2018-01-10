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
            
           
            <div class="briefing_detail">温州乐商投资有限公司成立于2011年4月，注册资本金为5000万元人民币，实缴资本为5000万元人民。
                成立7年来，公司一直秉承“透明、高效、稳健”的经营宗旨，立足传统金融，创新服务小微,扎实服务实体的业务理念，向广大投融资客户提供便利、安全的网络借贷信息中介服务。
                公司旗下运营网站有“乐商贷”。网站域名：www.yueshanggroup.cn（备案号：浙ICP备17036398）。

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