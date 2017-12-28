<!DOCTYPE html>
<html>
  <head>
    <title>${Application ["qmd.setting.name"]} 系统信息</title>
	
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
			<#if (errorMessages?? && errorMessages?size gt 0)>
				<#list errorMessages as actionMessage>${actionMessage}&nbsp;</#list>
			<#elseif fieldErrors?? && fieldErrors?size gt 0>
				<#list fieldErrors.keySet() as fieldErrorKey>
					<#list fieldErrors[fieldErrorKey] as fieldErrorValue>
										${fieldErrorValue}&nbsp;
					</#list>
				</#list>
			<#else>
								页面不存在或者出现系统错误！
			</#if>
          </div>
         <div style="text-align:center;margin-top:130px;">
           <a href="${base}/userCenter/index.do"style="width:108px;height:36px;border-radius:5px;line-height:36px;background-color:#f3485a;color:#fff;display:inline-block;text-align:center;cursor:pointer;">首页</a>
         </div>
      </div>
  </div>
</div><!-- 找回密码 end -->
<#include "/content/common/footer.ftl">

</body>
</html>