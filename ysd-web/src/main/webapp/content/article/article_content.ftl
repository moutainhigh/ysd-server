<!DOCTYPE html>
<html lang="en">
<head>
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-${articleCategory.name}</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
 <!--  <link rel="stylesheet" href="${base}/css/company_dynamic.css"> -->
<style>
#header .header_wrap .header_shadow {
	background-color: #ffffff;
    background-color: #ffffff\9;
    opacity: 1;
    filter: alpha(opacity=1);
}
.content {
    width: 1200px;
    margin:0 auto;
    background: #f2f2f2;
    margin-top: 38px;
}
.content .announce {
    background-color: #fff;
    width: 1000px;
    margin-left: 20px;
    padding-left: 50px;
    padding-right: 50px;
    padding-bottom: 100px;
    box-sizing: border-box;
    min-height:700px;
}
.content .announce .announce_title {
    background-color: #fff;
    font-size: 24px;
    color: #ef3e44;
    text-align: center;
    margin-top: 18px;
    line-height:68px;
    border-bottom: 1px solid #e5e5e5;
}
.content .announce .announce_wrap {
    height: 67px;
    line-height: 67px;
    padding-left: 20px;
    font-size: 14px;
    color: #333;
    border-bottom: 1px dashed #e5e5e5;
}
.content .announce .announce_wrap > a {
    color: #333;
    text-decoration: none;
}
.content .announce .announce_wrap > div:nth-child(2) {
    text-align: right;
    color: #666666;
    padding-right: 20px;
}
.content .announce ul.page {
    width: 365px;
    background-color: #fff;
    margin: 0 auto;
    margin-top: 60px;
    margin-bottom: 92px;
}
.content .announce ul.page li {
    width: 50px;
    height: 50px;
    display: inline-block;
    border: 1px solid #e5e5e5;
    font-size: 18px;
    text-align: center;
    line-height: 50px;
    cursor: pointer;
    /*margin-left: -6px;*/
}
.content .announce ul.page li:last-child {
    width: 95px;
}
.content .announce_detail_title{
    font-size: 20px;
    text-align: center;
    margin-top:28px;
    height:37px;
    line-height: 37px;
}
.content .announce_detail_time{
    height:41px;
    line-height: 41px;
    font-size: 14px;
    text-align: center;
    border-bottom: 1px solid #e5e5e5;
    color:#666666;
}
.content .announce_detail_wrap{
   /*  text-align: center; */
    padding-top: 18px;
    padding-bottom: 180px;
}
.content .announce_detail_wrap img{
  width: 100%;
  height: auto;
}
.announce_detail_wrap * {
	font: 16px/2 "Microsoft Yahei","微软雅黑",Tahoma,Arial,Helvetica,STHeiti !important;
	color: #333 !important;
}
.announce_detail_wrap .name {
	text-align: right !important;
	color: #111 !important;
	margin-top: 25px;
}
</style>
</head>
<body>

<!-- 头部 -->
<#include "/content/common/header.ftl">


<!--内容区域-->
<div class="content">
    <!--侧边导航-->
 	 <#include "/content/common/about_left.ftl">
  
    <!--公司动态-->
    <div class="announce news fr">
    	<#if article.title=="平台简介"><link rel="stylesheet" href="${base}/css/about_us.css"></#if>
        <div class="announce_detail_title">${article.title}</div>
        <div class="announce_detail_time"><!--${article.createDate?string("yyyy-MM-dd HH:mm:ss")} -->来源：<#if article.author!=''>${article.author}<#else>乐商贷</#if></div>
        <div class="announce_detail_wrap">
            ${article.pcContent}
            <div class="name" style="text-align: right; font-size:14px;color: black">温州乐商投资有限公司</div>
            <div class="clear"></div> 
        </div>
		 
    </div>
</div>

<div class="clear"></div>
<br><br><br><br><br><br>
<!--底部-->
<#include "/content/common/footer.ftl">
<script src="${base}/js/jquery-1.11.3.min.js"></script>
<script src="${base}/js/common.js"></script>

</body>
<script>	
$(function(){
	$("#header_gywm").addClass("current");
	$("#header_aqbz").find("a").css('border',0);
});
</script>

</html>
