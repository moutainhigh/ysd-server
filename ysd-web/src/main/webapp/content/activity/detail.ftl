<!DOCTYPE html>
<html lang="en">
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-活动详情页</title>
	<#include "/content/common/meta.ftl">
	<link rel="stylesheet" href="${base}/css/common.css"/>
	<link rel="stylesheet" href="${base}/css/activity.css" />
	<script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
</head>

<body ><!-- onload="alertComplete()"-->
	<!-- 头部 -->
   <#include "/content/common/header.ftl">
	<div class='contentbg'></div>
<div id="content">
		<div style="text-align: center">
          ${activity.pcContent}
          <!--   <img id="compman" src="${base}/img/0a.jpg"/> -->
       </div>
	<!--<img id="compman" src="https://www.yueshanggroup.cn/web/data/upfiles/images/201610/b227ced8d7de4b9d85161e439879a641.jpg" alt="Computerman" />-->
	<!-- <img id="compman" src="${base}/img/none_bg.png"/> -->
		<!--  <script type="text/javascript"> 
			function alertComplete()
			{
				if( document.getElementById("compman").complete) {
					if(document.getElementById("compman").width > 1201) {
						document.getElementById("compman").className='large';
					}
					else
						document.getElementById("compman").className='small';
				}
			}
		</script> -->
</div>


	<!-- 尾部 -->
   <#include "/content/common/footer.ftl">
<script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
<script src="${base}/js/common.js"></script>
<script src="${base}/js/activity_detail.js"></script>
<script type= text/javascript >
$(function(){
	$('#header_sy a').css('border',0);
	$('#header_sy').addClass('current');
});
</script>
</body>
</html>