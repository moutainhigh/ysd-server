<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-活动专区</title>
<#include "/content/common/meta.ftl">
</head>
<body>
<!-- header -->
<#include "/content/common/header.ftl">


<div class="content" style="background:#fff; padding-bottom:60px;">
	<div style="width:1200px; margin:0 auto;">
    	<ul class="huodong_center_v1" style="width:1200px; height:50px; border-bottom:1px solid #dbdbdb; margin-top:30px; margin-bottom:0;">
      		<li <#if activity.status==1>class="huodong_center_v2 "</#if>><a href="${base}/activity/list.do?activity.status=1"<#if activity.status==1>class="hq"<#else> style="color:#000000;"</#if>>进行中</a></li>
      		<li <#if activity.status==2>class="huodong_center_v2"</#if>><a href="${base}/activity/list.do?activity.status=2" <#if activity.status==2>class="hq"<#else>style="color:#000000;"</#if>>已结束</a></li>
    	</ul>
    	<div style="width:1200px;margin:0 auto;display:block;  " class="huodong_center_v3">
    		<#list pager.result as acItem>
    		<div style=" width:1200px; border-bottom:1px solid #e6e6e6;padding:30px 0;">
       			<div style=" width:1160px;  height:273px;margin:0 auto;">
         			<a href="${base}/activity/detail.do?id=${acItem.id}" style="float:left;"><img src="${Application["qmd.img.baseUrl"]}/web${acItem.imgWeb}" width="836" height="279" /></a>
         			<ul style="float:left; width:294px; height:253px; padding-left:30px; position:relative;">
           				<li style="color:#4c4c4c; font-size:20px;">${acItem.title}</li>
           				<li style="color:#808080; font-size:14px; margin:10px 0;">活动时间：${acItem.startTime?string("yyyy.MM.dd")}—${acItem.endTime?string("yyyy.MM.dd")}</li>
           				<li style="color:#808080; font-size:14px;">活动介绍：${substring(covertString(acItem.pcContent), 250, "...")}</li>
           				<li style="position:absolute; right:20px; bottom:2px;"><#if acItem.status==1><a href="${base}/activity/detail.do?id=${acItem.id}" class="lijitoubiao_s1">活动进行中</a><#elseif acItem.status==2><a href="" class="lijitoubiao_s1 lijitoubiao_s2">已结束</a></#if></li>
         			</ul>
       			</div>
      		</div>
    		</#list>
    		<#if (pager.totalCount > 0)>
				<@pageFlip pager=pager>
					<#include "/content/common/pager.ftl">
				</@pageFlip>
			</#if>
    	</div>
	</div>
  	<div style="width:100%;clear:both;"></div>
</div><!-- content end -->

<#include "/content/common/footer.ftl">
<script>
	//$('.huodong_center_v1 li').last().css('border-bottom','0');
	$('.huodong_center_v1 li').click(function(){
		var i=$(this).index();
		$('.huodong_center_v1 li').removeClass('huodong_center_v2');
		$(this).addClass('huodong_center_v2');
		$('.huodong_center_v3').css('display','none');
		$('.huodong_center_v3').eq(i).css('display','block');
	});
</script>  
 </body>
</html>
