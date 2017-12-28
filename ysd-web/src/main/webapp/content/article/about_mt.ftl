<!DOCTYPE html>
<html lang="en">
<head>
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-媒体报道</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/media_report.css">
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
<script src="${base}/js/jquery-1.11.3.min.js"></script>
<script src="${base}/js/common.js"></script>
<!-- 头部 -->
<#include "/content/common/header.ftl">
<!--内容区域-->
<div class="shadow_bg">
<div class="content">
    <!--侧边导航-->
     <#include "/content/common/about_left.ftl">
    <!--媒体报道-->
    <div class="report fr">
        <div class="report_title">媒体报道</div>
       
        <#list pager.result as article>
        <div class="report_wrap">
                        <#if article.coverImg==''>
		   	 			   <img width="154px" height="99px" src="${base}/img/mt.png" class="fl">
		   	    		<#else>
		   	    		 <img width="154px" height="99px" src="${Application["qmd.img.baseUrl"]}/web${article.coverImg}" class="fl">
		   	    		</#if>	   
            <div class="report_font fr">
           	<a  href="<#if article.url!><@article.url?interpret /><#else>${base}/article/content/${article.id}.htm</#if>"> <div class="report_content_title">${article.title}</div></a>
           
                <div class="report_source">
                    <span class="report_source_name">${article.author}</span>
                    <span class="date"></span>
                </div>
                <a ><div class="report_content">${article.metaDescription}</div></a>
            </div>
            <div class="clear"></div>
        </div>
   	    </#list>
        <form id="listForm" method="post" action="${base}/article/list/${sign}.htm" >
		
		         	<@pagination  pager=pager baseUrl="about.htm?sign=${sign}">
					<#include "/content/borrow/pager.ftl">
					</@pagination>
		    	
	    </form>
      
       
    </div>
</div>





<div class="clear"></div>
<br><br><br><br><br><br>
</div>
<!--底部-->
<#include "/content/common/footer.ftl">
</body>
<script>	
$(function(){
	$("#header_gywm").addClass("current");
	$("#header_aqbz").find("a").css('border',0);
});
</script>

</html>