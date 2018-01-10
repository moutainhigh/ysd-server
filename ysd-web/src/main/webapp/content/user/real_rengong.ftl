<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户 -个人账户实名认证</title>
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
        
        <div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
			<a style="color:#646464;font-family:'宋体';">${Application ["qmd.setting.name"]}</a><a>></a>
			<a style="color:#646464;font-family:'宋体';" href="${base}/userCenter/index.do">我的账户</a><a>></a>
			<a style="color:#646464;font-family:'宋体';" >个人账户认证</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            		
					<a href="javascript:void(0);" class="hr hre">个人账户认证</a>         
          </div>

           <div style="height:23px;line-height:23px;text-align:center;color:#595757;font-family:'宋体';width：100%；margin-top:0; margin-bottom:30px; clear:both;"></div>  
							请联系客服进行实名认证审核。
        </div>
    </div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->




<#include "/content/common/footer.ftl">
</body>
<script>
$(function(){
	$("#member_realname").addClass("nsg nsg_a1");
});
</script>
</html>
