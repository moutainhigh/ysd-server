<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-${articleCategory.name}</title>
	<#include "/content/common/meta.ftl">
</head>
<body>
	<#include "/content/common/header.ftl">

<div class="content">
  
  
  <div style="width:985px; margin:0 auto; clear:both; background:#fff; padding-bottom:25px;">
     <!-- 左侧菜单-->
	<#include "/content/common/about_left.ftl">
     
     <!--右侧-->
	     <div style="float:right; width:635px; margin-right:25px; padding-top:55px;">
		       <div style="border-bottom:1px solid #c6c6c6; padding-bottom:6px;">
		         <div style="color:#000; font-size:18px; float:left;">${articleCategory.name}</div>
		         <div style="float:right; margin-top:8px;">
		           <a href="${base}/"><img src="${base}/static/img/c7.png" /></a>
		           <a style="color:#000;font-family:'宋体';">></a>
		           <a style="color:#000;font-family:'宋体';" href="${base}/article/list/about_us.htm">关于我们</a>
		           <a style="color:#000;font-family:'宋体';">></a>
		           <a style="color:#000;font-family:'宋体';" href="${base}/article/list/${articleCategory.sign}.htm">${articleCategory.name}</a>
		         </div>
		         <div style="clear:both;"></div>
		       </div>
		       <ul style="width:635px;">
		       		
		       		<#list pager.result as article>
					    <!-- 文字链接-->
						 <li style="height:50px; line-height:50px; border-bottom:1px dotted #000;">
						 	<a href="<#if article.url!><@article.url?interpret /><#else>${base}/article/content/${article.id}.htm</#if>" style="color:#646464;font-family:'宋体';">
						 		${article.title}
						 	</a>
						 </li>		
					</#list>
		        </ul>
		     </div>
		     <div style="clear:both;"></div>
	  	</div>
  	<!--右侧 end-->
  
</div><!-- content end -->

	<#include "/content/common/footer.ftl">
<script>	
$(function(){
	$("#top_us").addClass("hq");
});
</script>
</body>
</html>
