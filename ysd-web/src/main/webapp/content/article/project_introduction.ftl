<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-项目介绍</title>
	<#include "/content/common/meta.ftl">
</head>
<body>
<#include "/content/common/header.ftl">
<!-- content -->
<div class="content clearfix">
	<div class="project clearfix">
		<div class="latest">
				<div class="hd clearfix">
					<a href="${base}/article/list/project_introduction.htm" class="more fr">更多</a>
					<h2>借款标项目介绍</h2>	
				</div>
				<div class="introduction-list">
					<@article_list sign="project_introduction" count="10" ; articleList>
						<#list articleList as article>
							<table <#if article_index==0>style="margin-top:10px;"</#if> >
								<tr>
									<td width="100px"><img src="<#if article.coverImg!><@imageUrlDecode imgurl="${article.coverImg}"; imgurl>${imgurl}</@imageUrlDecode><#else>${base+'/static/img/95x95.gif'}</#if>" alt=""  width="95" height="95"/></td>
									<td valign="top">
										<#if article.url!>
											<h3><a href="<@article.url?interpret />" class="tc2">${substringWord(article.title, 20, "")}</a>
											<#--><span class="tc3">${article.createDate?string("yyyy-MM-dd")}</span></h3>-->
										<#else>
											<h3><a href="${base}/article/content/${article.id}.htm" class="tc2">${substringWord(article.title, 20, "")}</a>
											<#--><span class="tc3">${article.createDate?string("yyyy-MM-dd")}</span></h3>-->
										</#if>	
										
										<p style="clear:both;">${substring(covertString(article.content), 350, "...")}</p>						
									</td>
								</tr>
							</table>
						</#list>
					</@article_list>
				</div>
			
			</div>	
		
	<!-- Pledged -->
	<div class="pledged">
	
			<h2>借款标项目</h2>
			<@borrow_list count=2; nubbleList>
				<#list nubbleList as borNB>
					<dl>
						<dt><img alt="" width="320px" height="300px" src="<#if borNB.borImageFirst!><@imageUrlDecode imgurl="${borNB.borImageFirst}"; imgurl>${imgurl}</@imageUrlDecode><#else>${base+'/static/img/320x300.png'}</#if>"></dt>
						<dd>
							<ol>
								<li>标题：<a href = "${base}/borrow/detail.do?bId=${borNB.id}">${borNB.name}</a></li>
								<li>类型：<#if borNB.type == 0>秒  标<#elseif borNB.type == 1>天标<#elseif borNB.type == 2>流转标<#elseif borNB.type == 3>信用标<#elseif borNB.type == 4>月标</#if></li>
								<li>金额：<em class="c1">￥${borNB.account}</em> 元</li>
								<li>收益：<#if borNB.type==4>${borNB.apr}%/年<#else>${borNB.apr}‰/天</#if></li>
								<li>周期：${borNB.timeLimit}<#if borNB.type == 4>月<#else>天</#if></li>
							</ol>
						</dd>
					</dl>
				</#list>
			</@borrow_list>
			
	</div>
</div>
</div>
<#include "/content/common/footer.ftl">
</body>
</html>
