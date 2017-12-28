<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
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
     
    	
     	
     	<div style="background:#fff; width:968px;float:right; border:1px solid #e6e6e6;padding:0 20px;">
       		<div style="border-bottom:1px solid #e6e6e6;text-align:center;padding:16px 0;">
         		<h3 style=" font-weight:normal;font-size:16px;color:#4c4c4c;line-height:38px;">${article.title}</h3>
         		<#--><p style=" font-size:12px;color:#808080;">发布${article.createDate?string("yyyy-MM-dd HH:mm:ss")}</p>-->
       		</div>
       		<div style="padding:10px 0 160px;color:#666;">
           		${article.pcContent}
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
