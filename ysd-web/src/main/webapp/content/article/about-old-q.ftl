<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-${articleCategory.name}</title>
	<#include "/content/common/meta.ftl">
</head>
<body>
	<#include "/content/common/header.ftl">
<div style="width:100%;">
  <div style="width:1200px; height:42px;line-height:42px; margin:0 auto;font-size:16px;color:#808080;">
    ${Application ["qmd.setting.name"]} > ${articleCategory.name}
  </div>
</div>
<div class="content">
	<div style="width:1200px; margin:0 auto; padding-bottom:60px;">
     <!-- 左侧菜单-->
	<#include "/content/common/about_left.ftl">
  	<div style="background:#fff; width:968px;height:606px;float:right; border:1px solid #e6e6e6;padding:0 20px;">
       <div style="height:56px;line-height:56px; font-size:16px; color:#fd7c1a; border-bottom:1px solid #e6e6e6;">
         <h3 style=" font-weight:normal;">${articleCategory.name}</h3>
       </div>
       <div>
           <ul style="min-height:430px;" class="help_centers">
           		<#list pager.result as article>
             	<li><a href="<#if article.url!><@article.url?interpret /><#else>${base}/article/content/${article.id}.htm</#if>">${article.title}</a>
             		<#--><span>${article.createDate?string("yyyy-MM-dd")}&nbsp;&nbsp;&nbsp;&nbsp;></span>-->
             	</li>
             	</#list>
           </ul>
            <form id="listForm" method="post" action="${base}/article/list/${sign}.htm" >
				<div class="base_paging">
		         	<@pagination pager=pager baseUrl="about.htm?sign=${sign}">
					<#include "/content/pager.ftl">
					</@pagination>
		    	</div>
	    	</form>
       </div>
     </div>
    <div style="width:100%;clear:both; height:1px;"></div>
  </div>
  
</div><!-- content end -->

	<#include "/content/common/footer.ftl">
<script>	
$(function(){
	$("#header_gywm").addClass("hq");
});
</script>
</body>
</html>
