<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>更多</title>
	<#include "/content/common/meta.ftl">
</head>
    <body class="bgc5">
       <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40">
	      <div class="am-header-left am-header-nav">
	      	<a href="#left-link" class="">
	          <#--> <i class="am-icon-angle-left am-icon-fw" style="font-size:1.5em;"></i>-->
	        </a>
	      </div>
	      <h1 class="am-header-title">
	        	  更多
	      </h1>
	      <div class="am-header-right am-header-nav">
	      
	      	<a href="#right-link" class="">
	          <i class="am-header-icon " style="font-size:1.1em;"></i>
	        </a>
	      </div>
	  </header><!-- header end-->
   		<ul class="am-list am-list-static am-list-border bgc2 color6" style="margin-top:20px;">
	      <li class="" style="border:0;" onclick="window.location.href='${base}/activity/list.do'">
	        <i class="am-icon-star am-icon-fw f12" style="color: #fcc266;"></i> 活动中心
	        <div style="float:right;"><i class="am-icon-angle-right am-icon-fw color5"></i></div>
	      </li>
	      <li class="" onclick="linkTo('app_site_notice','消息公告');">
	        <i class="am-icon-bullhorn am-icon-fw f12" style="color: #f16556;"></i> 消息公告
	        <span style="float:right;"><i class="am-icon-angle-right am-icon-fw color5"></i></span>
	      </li>
	    </ul>
	    <ul class="am-list am-list-static am-list-border bgc2 color6" style="margin-top:20px;">
	      <li class="" style="border:0;" onclick="window.location.href='${base}/award/friendsList.do'">
	        <i class="am-icon-share-square am-icon-fw f12" style="color: #61b0ed;"></i> 邀请好友
	        <div style="float:right;"><i class="am-icon-angle-right am-icon-fw color5"></i></div>
	      </li>
	      <li class="" onclick="linkTo('app_user_hand','关于我们');">
	        <i class="am-icon-user am-icon-fw f12" style="color: #19dde9;"></i> 关于我们
	        <span style="float:right;"><i class="am-icon-angle-right am-icon-fw color5"></i></span>
	      </li>
	      <li class="" onclick="linkTo('app_help_center','帮助中心');">
	        <i class="am-icon-question-circle am-icon-fw f12" style="color: #57e5b8;"></i> 帮助中心
	        <span style="float:right;"><i class="am-icon-angle-right am-icon-fw color5"></i></span>
	      </li>
	      <li class="" data-am-modal="{target: '#my-actions'}">
	        <i class="am-icon-phone-square am-icon-fw f12" style="color: #74c94b;"></i> 客服电话
	        <span style="float:right;"><i class="am-icon-angle-right am-icon-fw color5"></i></span>
	      </li>
	    </ul>
	    <#if loginUser!>
		    <div style="width:90%;margin:1em auto;">
		    	<button type="button" onclick="window.location.href='${base}/logout.do'" class="am-btn am-btn-block am-radius color1 bgc">退出</button>
		    </div>
	    </#if>
	    
		<div class="footer" >
		  <ul class="am-navbar-nav am-cf am-avg-sm-4" style="background-color:#f9f9f9;">
		     <li>
		      <a href="${base}/index.do">
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
		      <a class="active" href="${base}/moreTo.do">
		        <span class="am-icon more"></span>
		        <span class="am-navbar-label">更多</span>
		      </a>
		    </li>
		  </ul>
		</div>
	    
	    
	    <#-- 客服电话 -->
			<div class="am-modal-actions" id="my-actions">
			  <div class="am-modal-actions-group">
			    <ul class="am-list">
			      <li class="">拨打服务热线？</li>
			      <li><a href="tel:4000577820"><span >呼叫 ${setting.phone}</span></a></li>
			    </ul>
			  </div>
			  <div class="">
			    <button class="am-btn am-btn-secondary am-btn-block" data-am-modal-close>取消</button>
			  </div>
			</div>
	    
	    <!-- JiaThis Button BEGIN -->
	    <#-->
<div class="jiathis_style_m"></div>
<script type="text/javascript" src="http://v3.jiathis.com/code/jiathis_m.js" charset="utf-8"></script>
-->
<!-- JiaThis Button END -->
    </body>
     <script>
     	function linkTo(id,title){
     		window.location.href='${base}/article/list.do?id='+id+'&title='+title;
     	}
     </script>
</html>
