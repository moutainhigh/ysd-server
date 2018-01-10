<div class="header" id="header">
  <div style="width:1190px; margin:0 auto;">
    <div style="width:1190px; height:42px; line-height:42px; float:left;">
        <div style="float:left; color:#d8d8d8; font-size:14px; padding-top:0;">客服热线：${(commMess.phone)!} &nbsp;&nbsp;<#--联系QQ 3164381290--></div>
        <div style="float:right;padding-top:0;font-size:14px;">
         <#if loginUser>
        		<a href="${base}/userCenter/index.do">
			           <span style="display:inline-block;width:120px;height:26px;line-height:26px;text-align:center;color:#FFF;font-size:14px;">${Application ["qmd.setting.name"]}欢迎您！</span>
			          </a>
			           <a  style=" color:#fff; font-size:14px; padding:0px 10px;" id="header_top_login_in_username">${loginUser.username}</a>
			          <span style="color:#fff;">|</span>
			          <a href="${base}/userCenter/logout.do"><span style=" color:#fff; font-size:14px; padding:0px 10px;">安全退出</span></a>
			          <span style="color:#fff;">|</span>
		        	<a href="${base}/userCenter/index.do"><span style="width:14px;height:16px;display:inline-block;background:url(/img/user.png) no-repeat;margin:10px 5px 0 10px;"></span><span style=" color:#fff; font-size:14px;padding:0px 10px;">我的账户</span></a>
        	  		 <span style="color:#fff;">|</span>
	        	<a href="${base}/article/list/help_center.htm"><span style=" color:#fff; font-size:14px;padding:0px 10px;">帮助中心</span></a>
		      
        <#else>
          <a href="${base}/user/login.do">
           <span style="color:#d8d8d8;">登录</span>
          </a>
          <span style="color:#d8d8d8; padding:0px 10px;">|</span>
          <a href="${base}/user/reg.do"><span style=" color:#d8d8d8; ">注册</span></a>
      </#if>
        </div>
     </div>
    <div style="width:1190px; clear:both; float:left;overflow:hidden;">
     	<div class="logo"><a href="${base}/index.do">乐商贷</a></div>
          <ul class="list_ul">
             <li><a href="${base}/index.do"><span class="hp" id="header_sy">首页</span></a></li>
             <li><a href="${base}/borrow/list.do"><span class="hp" id="header_xmzx">项目中心</span></a></li>
             <li><a href="${base}/safe1.do"><span class="hp" id="header_aqbz">安全保障</span></a></li>
             <li><a href="${base}/article/list/about_us.htm"><span class="hp" id="header_gywm">关于我们</span></a></li>
             <li><a href="${base}/userCenter/index.do"><span class="hp" id="header_wdzh">我的账户</span></a></li>
          </ul>
     </div>
  </div>
</div><!-- header end -->



