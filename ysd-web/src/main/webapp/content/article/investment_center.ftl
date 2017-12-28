<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-投资中心-${articleCategory.name!}</title>
	<#include "/content/common/meta.ftl">
<script>
	$(function(){
	$(".ul_li_div li").each(function(i){
		$(this).click(function(){
				$(".ul_li_div li a").removeClass("current").eq(i).addClass("current");
				$(".div_div_class>div").hide().eq(i).show();
			});
		});
	});
</script>
</head>
<body>
<!-- header -->

<#include "/content/common/header.ftl">
<div class="banner wrapper">
	<a href="${base}${articleCategory.url}">
	<!-- width="1000" height="300"  暂时用-->
		<img  width="1000" height="300" src="<#if articleCategory.img!><@imageUrlDecode imgurl="${articleCategory.img}"; imgurl>${imgurl}</@imageUrlDecode><#else>${base+'/static/img/990x240.jpg'}</#if>" alt="我要投资"/>
	</a>
</div>

<!-- content -->
<div class="content clearfix">
	<div class="tab clearfix ul_li_div">
		<ul>
			<#list articleCategoryList as articleCategory>
				<li><a href="javascript:void(0);" class = "<#if articleCategory_index ==0>current</#if>">
					<span><i class="ic <#if articleCategory_index==0> ic_service-description<#elseif articleCategory_index==1>ic_transaction<#elseif articleCategory_index==2>ic_safety<#elseif articleCategory_index==3>ic_transfer-bonds</#if>"></i>${articleCategory.name}</span></a>
				</li>
			</#list>
		</ul>		
	</div>
	<div class="tab-con div_div_class">
		<!-- Service Description -->
		<#list articleCategoryList as articleCategory>
			<div class="service-description <#if articleCategory_index !=0>hide</#if>">
				<@article_list sign='${articleCategory.sign}' count='1';articleList>
					<#list articleList as article>
						${article.content}
					</#list>
				</@article_list>
			</div>
		</#list>
	</div>
	<div class="c_bottom"></div>
</div>

<!-- footer -->

<#include "/content/common/footer.ftl">

</body>
</html>
