<!doctype html>
<html class="no-js">
<head>
	<title>项目材料公示</title>
 	<#include "/content/common/meta.ftl">
  <style>
  	.am-form-group {margin-bottom: 1rem;}
    h3{font-size: 0.95em;background-color: #edeef0; padding: 0.3em 1em;}
    h4{padding: 0.5em 0;font-weight: normal;font-size: 1em;}
    hr{height:1px;}
    .content{padding:0 1em 1em;}
    .content p{text-indent: 2em;}   
    hr.am-divider{margin: 1.5rem auto 0.5rem;}
  </style>
</head>
    <body class="color2 bgc3">
        <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40 color1">
	      <div class="am-header-left am-header-nav">
	      	<a href="#left-link" onclick="window.history.back(); return false;">
	          	  <i class="am-icon-angle-left am-icon-fw" style="font-size:1.5em;"></i>
	        </a>
	      </div>
	      <h1 class="am-header-title">
	        项目材料公示
	      </h1>
	      <div class="am-header-right am-header-nav"></div>
	  </header><!-- header end-->
   		
   		<div>
   			<h3>项目资料公示</h3>
        <div class="content">
            <ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-avg-md-3 am-avg-lg-4 am-gallery-default" data-am-gallery="{ pureview: true }" >
              <#list borrowImgList as img>
              <li>
                <div class="am-gallery-item">
                      <img src="<@imageUrlDecode imgurl="${img.url}"; imgurl>${imgurl}</@imageUrlDecode>" />
                </div>
              </li>
             </#list>
             
          </ul>
        </div>
   		</div>
    <script src="mobile_js/jquery.min.js"></script>
	  <script src="mobile_js/amazeui.js"></script>
	  <script>
	 
	  </script>
    </body>
</html>
