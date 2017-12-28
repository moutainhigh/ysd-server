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
  <div style="width:100%; height:48px; background:#eae9e9; border-bottom:1px solid #c6c6c6;">
    <div style=" width:985px; margin:0 auto;">
      <ul style="float:left; border-left:1px solid #c6c6c6;" class="ne_list">
        <li><a href="${base}/article/list/about_us.htm"<#if sign == 'about_us'|| (sign !='security'&& sign != 'help_center')>class="far"</#if>  >关于我们</a></li>
        <li><a href="${base}/article/list/security.htm"<#if sign =='security'>class="far"</#if>>安全保障</a></li>
        <li><a href="${base}/article/list/help_center.htm" <#if sign == 'help_center'>class="far"</#if>>帮助中心</a></li>
      </ul>
    </div>
  </div>
  
  <div style="width:985px; margin:0 auto; clear:both; background:#fff; padding-bottom:25px;">
      <!-- 左侧菜单-->
	  <#include "/content/common/about_left.ftl">
     
     <div style="float:right; width:635px; margin-right:25px; padding-top:55px;">
       <div style="border-bottom:1px solid #c6c6c6; padding-bottom:6px;">
         <div style="color:#000; font-size:18px; float:left;"></div>
         <div style="float:right; margin-top:8px;">
           <a href="${base}/"><img src="${base}/static/img/c7.png" /></a>
           <a style="color:#000;font-family:'宋体';">></a>
           <a style="color:#000;font-family:'宋体';" href="${base}/article/list/${articleCategory.sign}.htm">${articleCategory.name}</a>
           <a style="color:#000;font-family:'宋体';">></a>
           <a style="color:#000;font-family:'宋体';" href="${base}/">${article.title}</a>
         </div>
         <div style="clear:both;"></div>
       </div>
       <ul style="width:635px;">
      	<li style="height:50px; line-height:50px; border-bottom:1px dotted #000; text-align: center; "><span style="color:#000; font-size:18px; x;" >${article.title}</span></li>
         	${article.content}
       </ul>
     </div>
     <div style="clear:both;"></div>
  </div>
  
</div><!-- content end -->

	<#include "/content/common/footer.ftl">
</div><!-- footer end -->
</body>
</html>
