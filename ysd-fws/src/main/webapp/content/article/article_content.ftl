
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${Application ["qmd.setting.name"]}-${article.title}</title>
	<#include "/content/common/meta.ftl">
 <!-- header -->
</head>
<body>
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_agency.ftl">
<div class="first_left">
				<h3 class="kaqu_licai_acm"><span class="kaqu_lixi">${article.title}</span> <span class="kaqu_chuziren">${(article.createDate?string("yyyy-MM-dd"))!}</span></h3> 
				<ul class="kaqu_caifuzhongxinrte">
					<li  class="kaqu_pingtaif0">
						<p class="kaqu_tianxie">
							${article.content}
						</p>
					</li>
					
				</ul>
			</div>
	<#include "/content/common/footer.ftl">
</body>
</html>