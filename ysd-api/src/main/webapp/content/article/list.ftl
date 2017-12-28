<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>${title}</title>
  <#include "/content/common/meta.ftl">
  <style>
    .am-accordion-content { padding: .8rem 1rem 1.2rem 2rem;}
  </style>
</head>
    <body class="bgc2">
        <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40">
	      <div class="am-header-left am-header-nav">
	      	<a href="#right-link">
	          	  <i class="am-icon-angle-left am-icon-fw" style="font-size:1.5em;"></i>
	       </a>
	      </div>

	      <h1 class="am-header-title">
	      		    ${title}
	      </h1>
	      <div class="am-header-right am-header-nav"></div>
	  </header><!-- header end-->
	    <section data-am-widget="accordion" class="am-accordion am-accordion-default" data-am-accordion='{ "multiple": true }' style="margin:0;">
      <#list beanList.articleItemList as article>
    <dl class="am-accordion-item"  onclick="window.location.href='${base}/article/detail.do?id=${article.id}'">
          <dt class="am-accordion-title">
            ${article_value+1}.${article.title}
          </dt>
          <#-->
          <dd class="am-accordion-bd am-collapse ">
            
            <div class="am-accordion-content">
          				   ${article.content}
            </div>
      	  </dd>
      	  -->
        </dl>
      </#list>  
        
    </section>
  </body>
</html>
