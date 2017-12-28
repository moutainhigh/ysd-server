
<#assign comm = commMess>
<#if comm!>
<div class="footer" style="padding:20px 0 40px 0;">
   <div class="fo_main">
     <div class="fo_lf" style="margin-top:20px;">
       <ul>
         <li>
           <a href="${base}/newPoint.do">新手指引</a>
           <span>|</span>
           <a href="${base}/article/list/about_us.htm">关于我们</a>
           <span>|</span>
           <a href="${base}/article/content/450.htm">联系我们</a>
           <span>|</span>
           <a href="${base}/safe1.do">安全保障</a>
         </li>
         <li>ICP备案：${comm.certtext!}  </li>
         <li style="margin-bottom:0px;">${(comm.copyright)!}</li>
   		</ul>
     </div>
     <div style="float:left; margin-left:100px;">
       <div style="color:#ccc; font-size:16px; margin-bottom:10px;">关注微信</div>
       <div style="float:left;"><img src="/img/a30.jpg" width="88" height="88" /></div>
       <div style="float:left; color:#d4d4d4; font-size:14px; margin-left:16px;">
         <div style="margin:10px 0;">${(comm.ywPhone)!}(个人业务)</div>
         <div><img src="/img/a6.png" />  在线客服</div>
       </div>
     </div>
     <!--可信网站图片LOGO安装开始-->
	<span id="kx_verify">
	</span>
	<script type="text/javascript">
	(function (){var _kxs = document.createElement('script');_kxs.id = 'kx_script';_kxs.async = true;_kxs.setAttribute('cid', 'kx_verify');_kxs.src = ('https:' == document.location.protocol ? 'https://ss.knet.cn' : 'https://rr.knet.cn')+'/static/js/icon3.js?sn=e1610123301006485881z6000000&tp=icon3';_kxs.setAttribute('size', 0);var _kx = document.getElementById('kx_verify');_kx.parentNode.insertBefore(_kxs, _kx);})();</script>
<!--可信网站图片LOGO安装结束-->
<a id='___szfw_logo___' href='https://credit.szfw.org/CX20161019026677890153.html' target='_blank'><img src='http://icon.szfw.org/cert.png' border='0' /></a>
<script type='text/javascript'>(function(){document.getElementById('___szfw_logo___').oncontextmenu = function(){return false;}})();</script>

     <div class="fo_ri" style="margin-top:20px;">
       <ul>
         <li>全国服务热线</li>
         <li style="color:#d2d2d2;">${(comm.phone)!}</li>
         <li style="font-size:16px;margin-bottom:0px;">服务时间 9:00-20:00</li>
         <#-->
	 		<li style="font-size:16px;margin-bottom:0px;">联系QQ 3164381290</li>
 		-->
       </ul>
     </div>
   </div>
   <div style="clear:both;width:1200px; height:1px; "></div>
 </div>
 </#if>