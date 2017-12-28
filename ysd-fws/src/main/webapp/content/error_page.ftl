<!DOCTYPE html>
<html>
<head>
	<title>${Application ["qmd.setting.name"]} 错误页面提示</title>
	<#include "/content/common/meta.ftl">
	
</head>
<body>
 <!-- header -->
	<#include "/content/common/user_center_header.ftl">
<div class="find">
  <div style="width:1200px;height:530px; margin:30px auto 90px;background:#fff;">
      <div class="top">
          <p><span>系统通知</span></p>
      </div>
      <div style="margin:85px auto 0;font-size:16px;">
         <div style="color:#666;text-align:center;">
            <span style="width:34px;height:34px;display:inline-block;line-height:34px;text-align:center;color:#fff;font-weight:bold;border-radius:50%;background:#f3485a;margin-right:8px;font-size:24px;">!</span>
			抱歉，您请求的页面现在无法打开
          </div>
         <div style="text-align:center;margin-top:130px;">
           <a style="width:108px;height:36px;border-radius:5px;line-height:36px;background-color:#f3485a;color:#fff;display:inline-block;text-align:center;cursor:pointer;">你可以：稍后再试 或 联系客服</a>
         </div>
      </div>
  </div>
</div><!-- 找回密码 end -->
<#include "/content/common/footer.ftl">

</body>
</html>
