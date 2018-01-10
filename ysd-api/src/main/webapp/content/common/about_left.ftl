  <div style="float:left; padding-top:48px; padding-left:35px;">
       <div style="background:url(${base}/static/img/c3.png) 0 0 no-repeat; width:181px; height:38px; padding-left:40px; line-height:38px;  margin-bottom:1px;">
         <a href="" style="color:#000;font-size:14px;font-family:'宋体';font-weight:700;">关于我们</a>
       </div>
        <@article_category sign = "about_jdt";articleCategoryList>
			<#list articleCategoryList as ac>
				  <div class="about_a0">
			         <a href="${base}/article/list/${ac.sign}.htm"  <#if sign == ac.sign>class="about_a1 about_a2 about_a3" <#else> class="about_a1 about_a2"</#if>  >${ac.name}</a>
			      </div>
			</#list>
	 </@article_category>
       
     </div>
    	