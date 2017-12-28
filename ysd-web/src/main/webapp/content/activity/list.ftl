<!DOCTYPE html>
<html lang="en">
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-活动专区</title>
	<#include "/content/common/meta.ftl">
	<link rel="stylesheet" href="${base}/css/common.css"/>
	<link rel="stylesheet" href="${base}/css/activity_area.css">
	<style>
		ul.mpage {
		    /* margin-right: 67px; */
		    /* width: 685px; */
		    background-color: #fff;
		    position: relative;
		    margin: 0 auto;
		    margin-top: 55px;
		    padding-bottom: 45px;
		    padding-top: 45px;
		    /* margin-bottom: 45px; */
		    text-align: center;
		    font-size: 18px;
		}
	</style>
</head>
<body>
	<!-- 头部 -->
   <#include "/content/common/header.ftl">
   <div class='contentbg'></div>
    <!-- 内容 -->
	<div id="content">
		<div class="tips">
			<span class="ing <#if activity.status==1> current</#if>"><a href="${base}/activity/list.do?activity.status=1">进行中</a></span>
			<span class="ed <#if activity.status==2> current</#if>"><a href="${base}/activity/list.do?activity.status=2">已结束</a></span>
		</div>
		<!--活动进行中-->
		<div class=<#if activity.status==1>"activiting"<#else>"activitied"</#if> >
		<#list pager.result as acItem>	
			<div class="activity">	    
				<a href="${base}/activity/detail.do?id=${acItem.id}" style="float:left;" target="_blank"><img src="${Application["qmd.img.baseUrl"]}/web${acItem.imgWeb}" width="820px" height="300px" /></a>
				<div class="details">
					<span class="name">${acItem.title}</span>
					<span class="date">${acItem.startTime?string("yyyy.MM.dd")}—${acItem.endTime?string("yyyy.MM.dd")}</span>
					<a href="${base}/activity/detail.do?id=${acItem.id}" target="_blank"><span class="detail">活动详情</span></a>
				</div>
			</div>
		</#list>
		<#if (pager.totalCount > 1)>
			<@pageFlip pager=pager>
				<#include "/content/activity/pager.ftl">
			</@pageFlip>
		</#if>
		</div>
	</div>
	<!-- 尾部 -->
   <#include "/content/common/footer.ftl">
	<script src="${base}/js/jquery-1.11.3.min.js"></script>
	<script src="${base}/js/common.js"></script>
	<script type= text/javascript >
		$(function(){
			$('#header_sy a').css('border',0);
			$('#header_sy').addClass('current');
		});
	</script>
</body>
</html>