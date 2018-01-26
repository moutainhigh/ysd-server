<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="application-name" content="${Application ["qmd.setting.name"]}" />
<meta name="msapplication-tooltip" content="${Application ["qmd.setting.name"]}" />
<meta name="keywords" content="${Application ["qmd.setting.metaKeywords"]}" />
<meta name="description" content="${Application ["qmd.setting.metaDescription"]}" />
<meta name="robots" content="all" />
<link type="text/css"  href="${base}/css/css.css" rel="stylesheet"/>
<link type="text/css"  href="${base}/css/dtree.css" rel="stylesheet"/>
<link type="text/css"  href="${base}/css/global.css" rel="stylesheet"/>

<script type="text/javascript" src="${base}/js/jquery/jquery.js" charset="utf-8"></script>
<script type="text/javascript" src="${base}/js/layer/layer.js" charset="utf-8"></script>
<script type="text/javascript" src="${base}/js/common/qmd.js"></script>
<script type="text/javascript" src="${base}/js/common/base.js"></script>
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript">
$(function(){
	$('ul.border').show();
	
	$(function(){
		$('.tableShadow tbody tr:even').addClass('whiteBg').removeClass('blueBg');
		$('.tableShadow tbody tr:odd').addClass('blueBg').removeClass('whiteBg');
	});
	
	$('.nodes h6').toggle(
		function(){
			$(this).children('.icon').addClass('display').removeClass('systole').parent().next('ul').show();
		},function(){
			$(this).children('.icon').addClass('systole').removeClass('display').parent().next('ul').hide();
		}	  
	);
	$('.circle_top2').toggle(
		function(){
			$(this).children('.icon').addClass('systole').removeClass('display');
			$('ul.border').hide();
		},function(){
			$(this).children('.icon').addClass('display').removeClass('systole');
			$('ul.border').show();
		}	  
	);
	$('#menu li').hover(
		function(){
			$("ul",this).stop(true,true).show(300);
		},function(){
			$("ul",this).stop(true,true).hide(150);
		}	  
	);
});
$(document).ready(function()  
{  
	// 浏览器的高度和div的高度  
	var height = $(window).height();   
	var divHeight = $("#cont").height();  
	  
	//获取div对象  
	var divh = $("#cont").get(0);  
	  
	//div高度大于屏幕高度把屏幕高度赋给div，并出现滚动条  
	if (divHeight > height-98)  
	{  
		divh.style.height = (height-85) + "px";  
		divh.style.overflow = "auto";  
	}  
}); 
</script>