<!-- 头部 -->
    <div class="header-tool">
		<div class="content-tool clearfix">
            <ul class="menu">
                <#--<li><a href="${base}/article/newDownload.do"><i></i>手机客户端</a></li>-->
                <#if loginUser>
                    <li class="name login"><a href="${base}/userCenter/index.do"><div class="two">欢迎${substringWord(loginUser.username,3,"*****")}${loginUser.username?substring(8,11)}</div></a></li>
                    <li class="register"><a href="${base}/userCenter/logout.do"><div class="one">退出登录</div></a></li>
                <#else>
                    <li class="login"><a href="${base}/user/login.do"><div class="two">登录</div></a></li>
                    <li class="register"><a href="${base}/user/reg.do"><div class="one">注册</div></a></li>
                </#if>
                <li class="droupdown user">
					<a href="${base}/article/list/help_center.htm"><div class="three"><img src="${base}/img/user.png" alt=""/></div></a>
                    <div>
                        <a href="${base}/article/list/help_center.htm"><div class="three">帮助中心</div></a>
                        <a href="${base}/userCenter/logout.do"><div class="four">退出登录</div></a>
                    </div>
				</li>
            </ul>
			<p class="tel">客服热线：400-057-7820</p>
		</div>
	</div>
    <div id="header">
    	<div class="header_wrap">
    	<div class="header_shadow"></div>
	    	<div class="headArea">	    	           	
		    	<div class="logo">
		    		<h1>
		    			<a href="${base}/">
		    				乐商贷
		    			</a>
		    		</h1>    		
		    	</div><!--
		    	--><ul class="nav">
				<li id="header_sy"><a href="${base}/">首页</a></li>
				<li id="header_xmzx"><a href="${base}/borrow/listNew.do">项目中心</a></li>
				<li id="header_aqbz"><a href="${base}/safe1.do">安全保障</a></li>
				<li id="header_gywm" class="droupdown"><a href="${base}/article/list/about_us.htm">关于我们 <i class="icon"></i></a>
					<#include "/content/common/about_left.ftl">
				</li>
				<li id="header_wdzh" class="droupdown"><a href="${base}/userCenter/index.do" style="border:none">我的账户</a>
					<#if loginUser>
						<#include "/content/common/user_center_left.ftl">
					</#if>
				</li>
		    	</ul>
		   </div> 	
	    </div>
    </div>
    
    