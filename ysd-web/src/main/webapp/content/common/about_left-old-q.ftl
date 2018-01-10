<div style="width:182px; float:left;">
	<ul class="subNav" style=" border:1px solid #e6e6e6;">
	<@article_category sign = "about_jdt";articleCategoryList>
		<#list articleCategoryList as ac>
			<li><a href="${base}/article/list/${ac.sign}.htm" <#if sign == ac.sign>class="cur" </#if>>${ac.name}</a></li>	  
		</#list>
	</@article_category>
    </ul>   
</div>