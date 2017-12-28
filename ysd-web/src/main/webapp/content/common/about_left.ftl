<div class="main_nav fl">
	<ul>
	<@article_category sign = "about_jdt";articleCategoryList>
		<#list articleCategoryList as ac>
			<#if sign == ac.sign>
				<li class="current"><a href="${base}/article/list/${ac.sign}.htm" >${ac.name}</a></li>
			<#else>
				<li ><a href="${base}/article/list/${ac.sign}.htm" >${ac.name}</a></li>
			</#if>  
		</#list>
	</@article_category>
    </ul>   
</div>




