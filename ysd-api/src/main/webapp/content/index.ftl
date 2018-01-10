<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>首页</title>
	<#include "/content/common/meta.ftl">
	  <style>
  .plus{  position: absolute;top: 25%;right: -8.0rem;font-size: 1.2em;
  color: #fff;background: url(../h5/mobile_img/9.png) no-repeat center center;
  background-size: 100%;padding: 0.2em 0.4em;}
  </style>
</head>
    <body class="bgc1">
        <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40">
	      <div class="am-header-left am-header-nav">
	          <#--
	          <a href="#left-link" class="">
	              <i class="am-header-icon am-icon-bullhorn" style="font-size:1.1em;"></i>
	          </a>
	          -->
	      </div>
	      <h1 class="am-header-title">
	          	首页
	      </h1>
	      <div class="am-header-right am-header-nav">
	          <a href="#right-link" class="">
	          </a>
	      </div>
	  </header><!-- header end-->
	    <div data-am-widget="slider" class="am-slider am-slider-a1" data-am-slider='{&quot;directionNav&quot;:false}'>
		  <ul class="am-slides">
				<#list indexBean.indexImageItemList as scrollpic>
		     		 <li><img height="160" src="<@imageUrlDecode imgurl="${scrollpic.imageUrl}"; imgurl>${imgurl}</@imageUrlDecode>"></li>
				</#list>
		  </ul>
		</div><!-- slider end-->
		<div style="position:relative;">
			<p class="color3 tac f12" style="margin-top:1.5em;">${indexBean.indexBorrow.name}</p>
             <div style="width:10em;height:10em;margin:1em auto;position:relative;">
	            <canvas width="100" height="100" id="borrowSchedule" data-to="${indexBean.indexBorrow.schedule}"></canvas>
	            <div class="tac f12" style="width:100%;margin-top:-7.2em;">
	            	<div >年化收益</div>
	                <div class="color" style="height:1.7em;line-height:1.5em;font-size:1.5em;margin: 0 auto;border-bottom: 1px dashed #ccc;"><span id="percent" style="font-size:1.35em;font-weight:bold;">${indexBean.indexBorrow.baseApr}</span>%</div>
	                <div>期限${indexBean.indexBorrow.timeLimit}天</div>
	            </div>
	            <#if indexBean.indexBorrow.awardApr gt 0>
	            	<div class="plus">+${indexBean.indexBorrow.awardApr}%</div>
	            </#if>
            </div> 
        </div>
		<div>
			<ul class="color4 f11" style="overflow:hidden;width:90%;margin:0 auto;">
				<li class="fl w50 tac">总额<span class="fwb" style="margin:0 8px;">${indexBean.indexBorrow.account}</span>元</li>
				<li class="fl w50 tac">起投金额<span class="fwb" style="margin:0 8px;">${indexBean.indexBorrow.lowestAccount}</span>元</li>
			</ul>
		</div>
			<a class="am-btn am-btn-danger am-radius" href="${base}/borrow/detail.do?id=${indexBean.indexBorrow.id}" target="_blank" style="display:block;width:90%;margin:1em auto 0.3em;">立即投资</a>
			
		<div class="footer" >
		  <ul class="am-navbar-nav am-cf am-avg-sm-4" style="background-color:#f9f9f9;">
		    <li>
		      <a class="active" href="${base}/index.do">
		        <span class="am-icon home"></span>
		        <span class="am-navbar-label">首页</span>
		      </a>
		    </li>
		     <li>
		      <a href="content/h5invest/investList.html">
		        <span class="am-icon list"></span>
		        <span class="am-navbar-label">产品列表</span>
		      </a>
		    </li>
		    <li>
		      <a href="${base}/userCenter/index.do">
		        <span class="am-icon account"></span>
		        <span class="am-navbar-label">我的钱袋</span>
		      </a>
		    </li>
	   	 <li>
		      <a href="${base}/moreTo.do">
		        <span class="am-icon more"></span>
		        <span class="am-navbar-label">更多</span>
		      </a>
		    </li>
		  </ul>
		</div>
	  <script>
	  init($('#borrowSchedule').get(0));
	  function init(element){
		var frontColor = "#ff7f00", backGround = "#ffddaa";
		var r = element.parentNode.clientHeight;
		element.setAttribute("width", r);
		element.setAttribute("height", r);
		var animation_loop;
		var ctx = element.getContext('2d');
		var sp = document.getElementById('percent');
		var target = Math.round(element.getAttribute('data-to')), current = 1, i = 0, Pi = Math.PI;

		function drawArc(_color, _arc1, _arc2){
			ctx.beginPath();
			ctx.lineWidth = 6;
			ctx.arc(r*0.5,r*0.5,r*0.5-ctx.lineWidth/2,_arc1*Pi,_arc2*Pi,false);
			ctx.strokeStyle = _color;
			ctx.stroke();
			ctx.closePath();
		}
		function process(){
			ctx.clearRect(0, 0, r, r);
			if (current == 100) drawArc(frontColor, 0 , 2);
			else {
				drawArc(frontColor, -0.5, current*0.02-0.5);
				drawArc(backGround, current*0.02-0.5, 1.5);
			}
			//sp.innerHTML = current;
			//$('#percent').html(current);
			if (current == target) return clearInterval(animation_loop);
			else current++;
		}

		drawArc(backGround, 0, 2);
		if (typeof animation_loop != undefined) clearInterval(animation_loop);
		if (target) animation_loop = setInterval(process, 30);
	}
	  </script>
    </body>
</html>
