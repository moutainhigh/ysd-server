<div class="footer">
  <div style="width:920px; margin:0 auto;">
    <div style="float:left; margin-top:20px;  margin-right:30px;">
      <ul style=" background:url(${base}/static/img/a28.png) 0 4px no-repeat; border-bottom:1px solid #c6c6c6;padding-right:45px; padding-left:42px; padding-bottom:8px;">
        <li style="color:#f6f6f6; font-size:14px; font-family:'宋体';">ABOUT</li>
        <li style="color:#be9964; font-size:16px;">关于我们</li>
      </ul>
      <ul style="float:left; width:145px; padding-left:5px; padding-top:10px;">
        <li style="float:left; margin-bottom:10px;"><a href="${base}/article/list/about_us.htm" style="color:#a6a5a5;font-family:'宋体';">关于我们</a></li>
        <li style="color:#a6a5a5;font-family:'宋体'; padding:0px 18px;float:left; margin-bottom:10px;">|</li>
        <li style="float:left; margin-bottom:10px;"><a href="${base}/article/list/site_rule.htm" style="color:#a6a5a5;font-family:'宋体';">资费规则</a></li>
        <li style="float:left;"><a href="${base}/article/list/help_center.htm" style="color:#a6a5a5;font-family:'宋体';">帮助中心</a></li>
        <li style="color:#a6a5a5;font-family:'宋体';padding:0px 18px;float:left;">|</li>
        <li style="float:left;"><a href="${base}/article/list/contact_us.htm" style="color:#a6a5a5;font-family:'宋体';">联系我们</a></li>
      </ul>
    </div>
    <div style="float:left; margin-top:20px;  margin-right:90px;">
      <ul style=" background:url(${base}/static/img/a28.png) -185px 4px no-repeat; border-bottom:1px solid #c6c6c6;padding-right:406px; padding-left:42px; padding-bottom:8px;">
        <li style="color:#f6f6f6; font-size:14px; font-family:'宋体';">PARTNERS</li>
        <li style="color:#be9964; font-size:16px;">合作伙伴</li>
      </ul>
      <ul style="float:left; width:505px; padding-left:5px; margin-top:10px; background:#fff;">
        <@article_list sign='cooperative_partner' count=5; articleList>
		<#list articleList as article>
			<li style="float:left;"><a href="${article.url}"><img src="<@imageUrlDecode imgurl="${article.coverImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="100" height="46" /></a></li>
		</#list>
	</@article_list>
        
      </ul>
    </div>
    <div style="float:right; margin-top:14px;">
      <div style="float:left;background:url(${base}/static/img/a29.png) 0 0 no-repeat;width:23px;height:76px;font-weight:bold;padding-top:12px;text-align:center;color:#fff;font-size:14px;font-family:'宋体';">关注我们</div>
      <div style="float:left;"><a href="#"><img src="${base}/static/img/a30.png" width="101" height="102" /></a></div>
    </div>
    <div style="text-align:center; clear:both; padding-top:28px; color:#efe5bb;font-family:'宋体';">${(commMess.copyright)!}</div>
  </div>
</div><!-- footer end -->


<#--
<div class="footer">
  <div style="width:920px; margin:0 auto;">
    <div style="float:left; margin-top:20px;  margin-right:30px;">
      <ul style=" background:url(${base}/static/img/a28.png) 0 4px no-repeat; border-bottom:1px solid #c6c6c6;padding-right:45px; padding-left:42px; padding-bottom:8px;">
        <li style="color:#f6f6f6; font-size:14px; font-family:'宋体';">ABOUT</li>
        <li style="color:#be9964; font-size:16px;">关于我们</li>
      </ul>
      <ul style="float:left; width:145px; padding-left:5px; padding-top:10px;">
        <li style="float:left; margin-bottom:10px;"><a href="${base}/article/list/about_us.htm" style="color:#a6a5a5;font-family:'宋体';">关于我们</a></li>
        <li style="color:#a6a5a5;font-family:'宋体'; padding:0px 18px;float:left; margin-bottom:10px;">|</li>
        <li style="float:left; margin-bottom:10px;"><a href="${base}/article/list/site_rule.htm" style="color:#a6a5a5;font-family:'宋体';">法律法规</a></li>
        <li style="float:left;"><a href="${base}/article/list/help_center.htm" style="color:#a6a5a5;font-family:'宋体';">帮助中心</a></li>
        <li style="color:#a6a5a5;font-family:'宋体';padding:0px 18px;float:left;">|</li>
        <li style="float:left;"><a href="${base}/article/list/contact_us.htm" style="color:#a6a5a5;font-family:'宋体';">联系我们</a></li>
      </ul>
    </div>
    <div style="float:left; margin-top:20px;  margin-right:90px;">
      <ul style=" background:url(${base}/static/img/a28.png) -185px 4px no-repeat; border-bottom:1px solid #c6c6c6;padding-right:406px; padding-left:42px; padding-bottom:8px;">
        <li style="color:#f6f6f6; font-size:14px; font-family:'宋体';">PARTNERS</li>
        <li style="color:#be9964; font-size:16px;">合作伙伴</li>
      </ul>
      <ul style="float:left; width:505px; padding-left:5px; margin-top:10px; background:#fff;">
        <@article_list sign='cooperative_partner' count=5; articleList>
		<#list articleList as article>
			<li style="float:left;"><a href="${article.url}"><img src="<@imageUrlDecode imgurl="${article.coverImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="100" height="46" /></a></li>
		</#list>
	</@article_list>
        
      </ul>
    </div>
    <div style="float:right; margin-top:14px;">
      <div style="float:left;background:url(${base}/static/img/a29.png) 0 0 no-repeat;width:23px;height:76px;font-weight:bold;padding-top:12px;text-align:center;color:#fff;font-size:14px;font-family:'宋体';">关注我们</div>
      <div style="float:left;"><a href="#"><img src="${base}/static/img/a30.png" width="101" height="102" /></a></div>
    </div>
    <div style="text-align:center; clear:both; padding-top:28px; color:#efe5bb;font-family:'宋体';">${copyright}</div>
  </div>
</div> --><!-- footer end -->