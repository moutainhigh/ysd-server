<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-帮助中心</title>
	<#include "/content/common/meta.ftl">
</head>
<body>
<#include "/content/common/header.ftl">

<!-- content -->
<div class="help-center content clearfix">
	<!-- left -->
	<div class="hcl">
		<!-- Safeguard -->
		<div class="safeguard">
			<h2>帮助中心</h2>
			<dl>
				<dt><i class="ic ic_disputes-claims"></i></dt>
				<dd>
					<h3 class="c2">消费发生纠纷先行赔付</h3>
					以顾客利益为先，一旦出现问题，优先为顾客赔偿解决！
				</dd>
			</dl>
			<dl>
				<dt><i class="ic ic_lightning-audit"></i></dt>
				<dd>
					<h3 class="c2">闪电审核，让您支付更快捷</h3>
					当天10点前订单当天发出，10点后第二天发出！
				</dd>
			</dl>
			<dl>
				<dt><i class="ic ic_refund"></i></dt>
				<dd>
					<h3 class="c2">7天无条件退款服务</h3>
					7天内无条件退款
				</dd>
			</dl>
		</div>

		<!-- advisory -->
		<div class="advisory">
			<ul>
				<li>全国服务热线：<b class="c1">4008-6666-66</b></li> 
				<li>服务时间：每日8：00-22：00</li> 
				<li>客服邮箱：service@jindaitong.com</li>
			</ul>
		</div>
	</div>
	<!-- right -->
	<div class="hcr">
		<div class="top_"></div>
		<div class="con_">
			<div class="location">
				<a href="">融金通</a> &gt; <strong>帮助中心</strong>
			</div>
			
			<div class="qa-od clearfix">
				<!-- qa -->
				<div class="qa fl">
					<h2>常见问题</h2>
					<div class="top_"></div>
					<div class="con_">
						<ul>
						<@article_list sign="faq" count="10" ; articleList>
							<#list articleList as article>
								<#if article.url!>
									<li><a href="<@article.url?interpret />" target="_blank">${substring(article.title, 20, "")}</a></li>
								<#else>
									<li><a href="${base}/article/content/${article.id}.htm" target="_blank">${substring(article.title, 20, "")}</a></li>
								</#if>
							</#list>
						</@article_list>
						</ul>
					</div>
					<div class="bottom_"></div>
				</div>

				<!-- operation demo -->
				<div class="operation-demo fr">
					<h2>操作演示</h2>
					<div class="top_"></div>
					<div class="con_">
						<ul>
							<@article_list sign="operation_demo" count="10" ; articleList>
								<#list articleList as article>
									<#if article.url!>
										<li><a href="<@article.url?interpret />" target="_blank">${substring(article.title, 20, "")}</a></li>
									<#else>
										<li><a href="${base}/article/content/${article.id}.htm" target="_blank">${substring(article.title, 20, "")}</a></li>
									</#if>
								</#list>
							</@article_list>
						</ul>
					</div>
					<div class="bottom_"></div>
				</div>				
			</div>

			<div class="hot-help">
				<h2>热点问题：</h2>
				<ul class="clearfix">
					<@article_list sign="hot_issue" count="20" ; articleList>
						<#list articleList as article>
							<#if article.url!>
								<li><a href="<@article.url?interpret />" target="_blank">${substring(article.title, 20, "")}</a></li>
							<#else>
								<li><a href="${base}/article/content/${article.id}.htm" target="_blank">${substring(article.title, 20, "")}</a></li>
							</#if>
						</#list>
					</@article_list>
				</ul>
			</div>
		
		</div>
		<div class="bottom_"></div>
	</div>
	
</div>
<#include "/content/common/footer.ftl">
</body>
</html>
