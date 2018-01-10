<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>${Application ["qmd.setting.name"]}-发布借款</title>
	<#include "/content/common/meta.ftl">
</head>
<body>
<!-- header -->


<#include "/content/common/header.ftl">

<div class="content">
  <div style="width:955px; margin:0 auto; padding-bottom:20px;">
	<#include "/content/common/user_center_header.ftl">
 <div style="width:955px; float:left; background:#fff; clear:both;">
	<#include "/content/common/user_center_left.ftl">
	
	<#-- 		右边内容块 开始 				-->

 
        <div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
            <a href="" style="color:#646464;font-family:'宋体';">我的账户</a>
            <a style="color:#646464;font-family:'宋体'; padding:0px 4px;">></a>
            <a href="" style="color:#646464;font-family:'宋体';">项目管理</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            <a href="" class="hr hre">发布项目</a>
          </div>
          <div style="clear:both;color:#646464;font-family:'宋体';margin-top:80px; padding:15px 15px;">
             <ul style="background:#efebdf; height:auto; float:left; padding:20px 40px 20px 40px; margin-bottom:20px;">
						 <#--<li style="background:url(${base}/static/img/ya-1.png) no-repeat 0 5px; padding:20px 0px 100px 95px; float:left; margin-right:40px;">
                           <p style="margin-bottom:10px;"><a href="" style="color:#f74405;font-family:'宋体';">[了解详情]</a></p>
                           <a href="${base}/borrow/borrowInputMoon.do" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;">抵押质押项目</a>
                        </li>
						
						<li style="background:url(${base}/static/img/zhuan-1.png) no-repeat 0 5px; padding:20px 0px 100px 95px; float:left; margin-right:40px;">
                           <p style="margin-bottom:10px;"><a href="" style="color:#f74405;font-family:'宋体';">[了解详情]</a></p>
                           <a href="${base}/borrow/borrowInputFlow.do" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;">债权转让项目</a>
                        </li>-->
                       
                        <li style="background:url(${base}/static/img/tian-1.png) no-repeat 0 5px; padding:20px 0px 100px 95px; float:left; margin-right:40px;">
                           <p style="margin-bottom:10px;"><a href="" style="color:#f74405;font-family:'宋体';">[了解详情]</a></p>
                           <a href="${base}/borrow/inputBorrowPromote.do?borrowType=11" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;">发布天标</a>
                        </li>
						<li style="background:url(${base}/static/img/ya-1.png) no-repeat 0 5px; padding:20px 0px 100px 95px; float:left; margin-right:40px;">
                           <p style="margin-bottom:10px;"><a href="" style="color:#f74405;font-family:'宋体';">[了解详情]</a></p>
                           <a href="${base}/borrow/inputBorrowRaise.do?borrowType=15" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;">发布质押月标</a>
                        </li>
						<#--<li style="background:url(${base}/static/img/jiang-1.png) no-repeat 0 5px; padding:20px 0px 100px 95px; float:left; margin-right:40px;">
                           <p style="margin-bottom:10px;"><a href="" style="color:#f74405;font-family:'宋体';">[了解详情]</a></p>
                           <a href="{base}/borrow/borrowInputFlow.do" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;">发布流转标标</a>
                        </li>-->
			</ul>		  
         </div>  
   </div>

<#-- 		右边内容块 结束				-->
	
	</div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->

 <!--footer-->
<#include "/content/common/footer.ftl">


</body>
<script type="text/javascript">
$(function(){
	$("#sp_header_my").addClass("hq");
	$("#borrow_want").addClass("nsg_a1");
});
</script>
</html>
