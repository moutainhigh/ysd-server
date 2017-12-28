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
    	.ryzzbig{
    		position:fixed;
    		width:100%;
    		height:100%;
    		top:0;
    		left:0;
    		z-index:1000;
    		text-align:center;
    		display:none;
    	}
    	.ryzzbig_bg{
		    background-color: #000;
		    background-color: #000\9;
		    opacity: .3;
		    filter: alpha(opacity=30);
    		position:absolute;
    		top:0;
    		left:0;
    		width:100%;
    		height:100%;
    		cursor:pointer;
    	}
    	.ryzzbig img{
    		position:relative;
    		width:640px;
    		height:480px;
    		margin:0 auto;
    		top:50%;
    		margin-top:-240px;
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
        <div class="report_title">荣誉资质</div>
       
        <#list pager.result as article>
        <div class="report_wrap">
                        <#if article.coverImg==''>
		   	 			   <img width="172px" height="114px" src="${base}/img/mt.png" class="fl">
		   	    		<#else>
		   	    		 <img width="172px" height="114px" src="${Application["qmd.img.baseUrl"]}/web${article.coverImg}" class="fl">
		   	    		</#if>	   
            <div class="report_font fr" style='width:700px;'>
           	 	<div class="report_content_title" style='margin-top:5px;'>${article.title}</div>
           
                <div class="report_source" style='height:42px;margin-top:6px;width:680px;'>
                    <span class="report_source_name">${article.author}</span>
                    <span class="date"></span>
                </div>
                <a ><div class="report_content" style='color:#999;'>${article.metaDescription}</div></a>
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
<div class='ryzzbig'>
	<div class='ryzzbig_bg'></div>
	<img src='https://www.yueshanggroup.cn/web/data/upfiles/images/201701/3b8b5668c30a4a5e98c5a88d377cdb12.jpg'>
	<!--https://www.yueshanggroup.cn/web/data/upfiles/images/201612/526d9ff2bf8c4743890cc6254a46e30c.jpg-->
</div>
<!--底部-->
<#include "/content/common/footer.ftl">
</body>
<script>	
$(function(){
	$("#header_gywm").addClass("current");
	$("#header_aqbz").find("a").css('border',0);
	$('.report_wrap').find('img').click(function(){
		var src=$(this).attr('src');
		$(".ryzzbig").find('img').attr('src',src);
		$('.ryzzbig').show();
		var new_image = new Image();
		new_image.src = $(".ryzzbig").find('img').attr('src');
		//alert(new_image.height);
	})
	$('.ryzzbig_bg').click(function(){
		$('.ryzzbig').hide();
	})
});
</script>

</html>