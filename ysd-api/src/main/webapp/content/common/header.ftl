<div class="header" id="header">
  <div style="width:980px; margin:0 auto;">
    <div style="width:980px; float:left;">
        <div style="float:left; color:#c8ba7c; font-size:14px; padding-top:8px;">客服热线：<font style="font-size:18px;">${(commMess.phone)!}</font></div>
        <div id="n_is_login" style="float:right;padding-top:8px;">
        	 <#if loginUser>
        	  <span  >
        	  		  <a href="${base}/userCenter/index.do">
			           <span style="display:inline-block;background:url(${base}/static/img/a2.png) 0 0 repeat;width:120px;height:26px;line-height:26px;text-align:center;color:#3f2508;font-size:14px;font-family:'宋体';">${Application ["qmd.setting.name"]}欢迎您！</span>
			          </a>
			           <a href="${base}/userCenter/index.do" style=" color:#c8ba7c; font-size:14px;font-family:'宋体'; padding:0px 10px;" id="header_top_login_in_username"> ${loginUser.username}</a>
        				<a class="g6">-</a>
			          <span style="color:#c8ba7c;">|</span>
			          <a href="${base}/userCenter/logout.do"><span style=" color:#c8ba7c; font-size:14px;font-family:'宋体'; padding:0px 10px;">安全退出</span></a>
			          <span style="color:#c8ba7c;">|</span>
		        	<a href="${base}/userCenter/index.do"><span style=" color:#c8ba7c; font-size:14px;font-family:'宋体';padding:0px 10px;">我的账户</span></a>
        	  
		      </span>
		    <#else>
		    	<span >
		          <a href="${base}/user/reg.do">
		           <span style="display:inline-block;background:url(${base}/static/img/a2.png) 0 0 no-repeat;width:64px;height:26px;line-height:26px;text-align:center;color:#3f2508;font-size:14px;font-family:'宋体';">免费注册</span>
		          </a>
		          <span style="color:#c8ba7c;">|</span>
		          <a href="${base}/user/login.do"><span style=" color:#c8ba7c; font-size:14px;font-family:'宋体'; padding:0px 10px;">立即登录</span></a>
		          <span style="color:#c8ba7c;">|</span>
	        	<a href="${base}/article/list/help_center.htm"><span style=" color:#c8ba7c; font-size:14px;font-family:'宋体';padding:0px 10px;">帮助中心</span></a>
        	 </span>
        	</#if>
        	
        	
        </div>
     </div>
    <div style="width:980px; clear:both; float:left;">
        <div style="float:left;padding-top:34px;"><a href="${base}/index.do"><img src="${base}/static/img/a3.png" width="163" height="50" /></a></div>
        <div style="float:right;">
          <ul class="list_ul">
             <li><a href="${base}/index.do"><span class="hp " id="top_index">首页</span></a></li><#--选中样式 hq -->
             <li><a href="${base}/borrow/list.do"><span class="hp" id="top_borrows">投资理财</span></a></li>
               <li><a href="${base}/newPoint.do"  ><span class="hp"id="top_newPoint">新手指引</span></a></li>
<!--             <li><a href="${base}/article/list/project_introduction.htm"  ><span class="hp"id="top_projects">项目介绍</span></a></li>-->
             <li><a href="${base}/safe.do"  ><span class="hp"id="top_anqu">安全保障</span></a></li>
             <li><a href="${base}/article/list/about_us.htm"><span class="hp" id="top_us">关于我们</span></a></li>
             <li><a href="${base}/userCenter/index.do"><span class="hp" id="sp_header_my">我的账户</span></a></li>
          </ul>
        </div>
     </div>
  </div>
</div><!-- header end -->



<#--
<div class="header" id="header">
  <div style="width:980px; margin:0 auto;">
    <div style="width:980px; float:left;">
        <div style="float:left; color:#c8ba7c; font-size:14px; padding-top:8px;">客服热线：<font style="font-size:18px;">400-057-7820</font></div>
        <div id="n_is_login" style="float:right;padding-top:8px;">
         	 <span id="header_top_login_out">
		          <a href="${base}/user/reg.do">
		           <span style="display:inline-block;background:url(${base}/static/img/a2.png) 0 0 no-repeat;width:64px;height:26px;line-height:26px;text-align:center;color:#3f2508;font-size:14px;font-family:'宋体';">免费注册</span>
		          </a>
		          <span style="color:#c8ba7c;">|</span>
		          <a href="${base}/user/login.do"><span style=" color:#c8ba7c; font-size:14px;font-family:'宋体'; padding:0px 10px;">立即登录</span></a>
		          <span style="color:#c8ba7c;">|</span>
	        	<a href="${base}/article/list/help_center.htm"><span style=" color:#c8ba7c; font-size:14px;font-family:'宋体';padding:0px 10px;">帮助中心</span></a>
        	 </span>
        	 
        	  <span id="header_top_login_in" style="display:none;">
        	  		  <a href="${base}/userCenter/index.do">
			           <span style="display:inline-block;background:url(${base}/static/img/a2.png) 0 0 repeat;width:120px;height:26px;line-height:26px;text-align:center;color:#3f2508;font-size:14px;font-family:'宋体';">${site_name}欢迎您！</span>
			          </a>
			           <a href="${base}/userCenter/index.do" style=" color:#c8ba7c; font-size:14px;font-family:'宋体'; padding:0px 10px;" id="header_top_login_in_username"></a>
        				<a class="g6">-</a>
			          <span style="color:#c8ba7c;">|</span>
			          <a href="${base}/userCenter/logout.do"><span style=" color:#c8ba7c; font-size:14px;font-family:'宋体'; padding:0px 10px;">安全退出</span></a>
			          <span style="color:#c8ba7c;">|</span>
		        	<a href="${base}/userCenter/index.do"><span style=" color:#c8ba7c; font-size:14px;font-family:'宋体';padding:0px 10px;">我的账户</span></a>
        	  
		      </span>
        	
        	
        </div>
     </div>
    <div style="width:980px; clear:both; float:left;">
        <div style="float:left;padding-top:34px;"><a href="${base}/index.do"><img src="${base}/static/img/a3.png" width="163" height="50" /></a></div>
        <div style="float:right;">
          <ul class="list_ul">
             <li><a href="${base}/index.do"><span class="hp " id="sp_header_index">首页</span></a></li>
             <li><a href="${base}/borrow/list.do"><span class="hp" id="sp_header_bwlist">投资理财</span></a></li>
             <li><a href="${base}"><span class="hp" id="sp_header_borrower">我要借款</span></a></li>
             <li><a href="${base}/article/list/about_us.htm"><span class="hp" id="sp_header_us">关于我们</span></a></li>
             <li><a href="${base}/userCenter/index.do"><span class="hp" id="sp_header_my">我的账户</span></a></li>
          </ul>
        </div>
     </div>
  </div>
</div>
-->

