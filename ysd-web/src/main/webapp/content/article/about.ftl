<!DOCTYPE html>
<html lang="en">
<head>
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-${articleCategory.name}</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/company_dynamic.css" />
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
<div class="shadow_bg">
<!--内容区域-->
<div class="content">
    <!--侧边导航-->
    <#include "/content/common/about_left.ftl">
  
  
  
    <!--公司动态-->
    <div class="announce news fr">
        <div class="announce_title">${articleCategory.name}</div>
        <#list pager.result as article>
        <div class="announce_wrap">
            <a href="<#if article.url!><@article.url?interpret /><#else>${base}/article/content/${article.id}.htm</#if>"><div class="announce_content fl">${article.title}</div></a>
            <div class="announce_date fr">${article.createDate?string("yyyy-MM-dd")}</div>
        </div>
       </#list>
       <form id="listForm" method="post" action="${base}/article/list/${sign}.htm" >
			
				
		         	<@pagination pager=pager baseUrl="about.htm?sign=${sign}">
					<#include "/content/borrow/pager.ftl">
					</@pagination>
		    	
	    </form>

    </div>
</div>

<div class="clear"></div>
<br><br><br><br><br><br>
<!--底部-->
</div>
<#include "/content/common/footer.ftl">


</body>


<script>	
$(function(){
	$("#header_gywm").addClass("current");
	$("#header_aqbz").find("a").css('border',0);
});
</script>
</html>