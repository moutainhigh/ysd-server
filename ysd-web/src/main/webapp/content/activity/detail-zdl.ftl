<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-活动专区</title>
<#include "/content/common/meta.ftl">
</head>
<body>
<!-- header -->
<#include "/content/common/header.ftl">

<div style="width:100%;">
	<div style="width:1200px; height:42px;line-height:42px; margin:0 auto;font-size:16px;color:#808080;">
   	 	${Application ["qmd.setting.name"]}> 活动专区
    	<a href="${base}/activity/list.do" style="color:#fd7c1a;float:right;font-size:14px;">返回</a>
	</div>
</div>
<div class="content">
  <div style="width:1200px; margin:0 auto; padding-bottom:60px;">
    <div style="background:#fff; width:1160px;border:1px solid #e6e6e6;padding:0 20px;">
    <#--
       <div style="margin:40px auto 20px;text-align: center;"><img src="${Application["qmd.img.baseUrl"]}/web/${activity.imgWeb}" alt="" /></div>
       
       <div style="border-bottom:1px solid #e6e6e6;text-align:center;padding:16px 0;">
         <h3 style=" font-weight:normal;font-size:16px;color:#4c4c4c;line-height:38px;">${activity.title}</h3>
         <p style=" font-size:12px;color:#808080;">活动时间:${activity.startTime?string("yyyy-MM-dd")}—${activity.endTime?string("yyyy-MM-dd")}</p>
       </div>
       -->
       <div style="padding:10px 0 160px;color:#666;">
           ${activity.pcContent}
       </div>
     </div>
    <div style="width:100%;clear:both; height:1px;"></div>
  </div>
</div><!-- content end -->
<#include "/content/common/footer.ftl">
<script>
	$('.subNav li').last().css('border-bottom','0');
</script>  
 </body>
</html>
