<!-- Consultation -->
<div class="consultation">
<#assign img_src=''>
<@article_list sign='service_hotline' count=1; articleList>
	<#list articleList as article>
		<#assign cover_img=article.coverImg>
		${article.content}
	</#list>
</@article_list>
</div>

<!-- ads -->
<!--
<div class="kAds">
	<img src="<@imageUrlDecode imgurl="${cover_img}"; imgurl>${imgurl}</@imageUrlDecode>" width = "240" height="100" alt="" />
</div>
 -->