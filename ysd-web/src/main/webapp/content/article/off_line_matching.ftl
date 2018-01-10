<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-线下配对</title>
<#include "/content/common/meta.ftl">
</head>
<body>
<#include "/content/common/header.ftl">
<div class="banner wrapper">
	<a href=""><img src="${base}/static/img/990x320.jpg" alt="我要投资" /></a>
</div>
<!-- content -->
<div class="content clearfix">

	<div class="clearfix">
		<div class="application-list fl">
			<div class="top_"></div>
			<div class="con_">
				<h2 class="c2">资金需求列表</h2>
				<table>
					<@off_line_list type_id="1" status="0" count="13"; offLineList>
						<#list offLineList as offLine>
							<tr>
								<td>${substring(offLine.realName, 1, "******")}</td>
								<td>${substring(offLine.purpose, 15, "...")}</td>
								<td><em class="c1">${offLine.money?string.currency}元</em></td>
								<td>${offLine.duration}</td>
							</tr>
						</#list>
					</@off_line_list>
				</table>
			</div>
			<div class="bottom_"></div>

			<a href=""><img src="${base}/static/img/btnBorrowings.jpg" alt="我要线下借款" /></a>
		</div>

		<div class="application-list fr">
			<div class="top_"></div>
			<div class="con_">
				<h2 class="c2">闲散资金列表</h2>
				<table>
					<@off_line_list type_id="0" status="0" count="13"; offLineList>
						<#list offLineList as offLine>
							<tr>
								<td>${substring(offLine.realName, 1, "******")}</td>
								<td>投资</td>
								<td><em class="c1">${offLine.money?string.currency}元</em></td>
								<td>${offLine.duration}</td>
							</tr>
						</#list>
					</@off_line_list>
				</table>
			</div>
			<div class="bottom_"></div>

			<a href=""><img src="${base}/static/img/btnInvestment.jpg" alt="我要线下投资" /></a>
		</div>
	</div>

	<div class="g2 fl">		
		<div class="top_"></div>
		<div class="con_">
			<h2>线下业务介绍</h2>
			<p>${articleCategory.metaDescription}</p>
		</div>
		<div class="bottom_"></div>

		<div class="top_"></div>
		<div class="con_">
			<h2>部分合作伙伴</h2>
			<p><img src="${base}/static/img/partners.png" alt="金贷通合作伙伴" /></p>
		</div>
		<div class="bottom_"></div>
	</div>
	<div class="g7 fr">
		<div class="tab clearfix">
			<ul>
				<li><a href="#" class="current"><span>成功案例</span></a></li>
			</ul>		
		</div>
		<div class="tab-con">
			<form id="listForm" method="post" action="matching.do" >
				<div class="tender-list">
				 <#list pager.result as article>
					<table>
						<tr>
							<td width="110">
								<a href="${article.url!'#'}" target="_blank"><img src="<#if article.coverImg!><@imageUrlDecode imgurl="${article.coverImg}"; imgurl>${imgurl}</@imageUrlDecode><#else>${base+'/static/img/95x95.gif'}</#if>" class="marinimg" width="80" height="80"></a>
							</td>
							<td>
								<h3><a href="${article.url!'#'}" class="c2">${article.title}</a></h3>
								${substring(covertString(article.content), 500, "...")}
							</td>
						</tr>
					</table>
				</#list>
				</div>
				
				<!-- paging -->
				<@pagination pager=pager baseUrl="/matching.do" parameterMap = parameterMap>
					<#include "/content/pager.ftl">
				</@pagination>
			</form>
		</div>
		<div class="bottom_"></div>		
	</div>

</div>
<#include "/content/common/footer.ftl">

</body>
</html>
