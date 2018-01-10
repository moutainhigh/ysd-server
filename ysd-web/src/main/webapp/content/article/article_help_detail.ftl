<!DOCTYPE html>
<html lang="en">
<head>
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-关于我们</title>
    <link rel="stylesheet" href="../css/common.css" />
    <link rel="stylesheet" href="../css/cooperative_partner.css">
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
<#include "/content/common/header.ftl">
<!--内容区域-->
<div class="shadow_bg">
<div class="content">
    <!--侧边导航-->
   <#include "/content/common/about_left.ftl">
   
   <!--<table>
   
   <#list l as l1>
   <tr>
   		<td>${l1.key}</td>
   		<td>${l1.value}</td>
   </tr>
   </#list>
   </table>-->
   
   <!--<table>
   	<#list articleList as l>
	<tr>		
		<td>${l.title}</td>
		<td>${l.metaKeywords}</td>
		<td>${l.content}</td>
	</tr>
	<tr>-------------</tr>
	</#list>
   </table>-->
   
   <!--合作-->
   <div class="partner fr">
   		<h3>${title}</h3>
   	   <#list l as l1>
	   <div class="${l1.key}" id="${l1.key}">
	   	<p class='partner_title'>${l1.value}</p>
	   	<ul id="partner_cont">
	   		<#list articleList as l>
	   			<#if l.metaKeywords==l1.key>
		   		<li class='partner_wrap '>
		   			<p class="partner_content fl">${l.title}</p>
		            <i class="fr"></i>
		            <div class="clear"></div>
		            <div class="partner_desc">${l.content}</div>
		        </li>
		       </#if>
			</#list>
	   	</ul>
	   </div>
	   </#list>
   </div>
</div>

<div class="clear"></div>
<br><br><br><br><br><br>

</div>

<#include "/content/common/footer.ftl">
</body>
<script src="${base}/js/jquery-1.11.3.min.js"></script>
<script src="${base}/js/common.js"></script>
<script type="text/javascript" src="${base}/js/partner.js"></script>
<script>	
$(function(){
	$("#header_gywm").addClass("current");
	$("#header_aqbz").find("a").css('border',0);
	var r = window.location.href;
	var type=r.substr(r.length-2,2);
	console.log(type);
	$('#'+type).find('li').addClass('on');
});
</script>
</html>
