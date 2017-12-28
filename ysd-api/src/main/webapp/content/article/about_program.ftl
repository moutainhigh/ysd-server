<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}
	<#if articleCategorySuperior!>
				-${articleCategorySuperior.name}
				</#if>-${articleCategory.name}</title>
	<#include "/content/common/meta.ftl">
</head>
<body>
<#include "/content/common/header.ftl">
<!-- content -->

<div class="content clearfix">
	<div class="about clearfix">
	<!-- left -->
	<div class="sidebar">
        <!-- 文章左侧-->
        <#include "/content/common/about_left.ftl">
		<!-- advisory -->
		<#include "/content/common/about_advisory.ftl">
	</div>
	<!-- right -->
	<div class="master">
			<div class="breadcrumb">
				<a href="${base}/">${Application ["qmd.setting.name"]}</a>
				<#if articleCategorySuperior!>
				 &gt; <strong><a href="${base}/aboutProgram.do">${articleCategorySuperior.name}</a></strong>
				</#if>
				 &gt; <strong><a href="${base}/aboutProgram.do">${articleCategory.name}</a></strong>
			</div>
			<form id="listForm" method="post" action="about.do?sign=${sign}" >
				<div class="about-list">
						<h2>${articleCategory.name}</h2>
						<ul>
							 <#list pager.result as article>
								<#if article.url!>
									<li><a href="<@article.url?interpret />">【${article.name}】${article.title}</a></li>
								<#else>
									<li><a href="${base}/article/content/${article.id}.htm">【${article.name}】${article.title}</a></li>
								</#if>
							</#list>
						</ul>
				</div>
				<!-- paging -->
				<@pagination pager=pager baseUrl="about.do?sign=${sign}">
					<#include "/content/pager.ftl">
				</@pagination>
			</form>
		</div>
	</div>
</div>
<#include "/content/common/footer.ftl">
</body>
</html>
