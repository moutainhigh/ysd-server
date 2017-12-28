<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-注册</title>
<#include "/content/common/meta.ftl">
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">
<div class="register">
  <div style="width:1190px;height:730px;margin:30px auto;background:#fff;border:1px solid #dcdcdc;border-radius:5px;">
  <#--  <div style="border-bottom:1px solid #dcdcdc;height:68px;line-height:68px;color:#fd7c1a;font-size:20px;text-align:center;">
      注册
      <span style="float:right;color:#666;font-size:14px;margin-right:40px;">已有账户马上<a href="" style="color:#fd7c1a;">登录</a></span>
    </div>
   -->
    <div style=" width:420px; margin:110px auto 150px;">
       <div style="float:left;width:72px;height:72px;background:url(/img/dui.png) no-repeat;"></div>
       <div style="float:left;color:#666;margin-left:6px;margin-top:11px;">
         <p style="font-size:20px;color:#006dc1;">恭喜注册成功</p>
         <#if hongbao.isEnabled == 1 && hongbao.total gt 0>
         	<p style="font-size:14px;margin-top:6px;">完成首次投资即可获得<span style="color:#fd7c1a;">${hongbao.total}元</span>红包</p>
         </#if>
         <div style="margin-top:46px;">
           <a href="${base}/userCenter/toBank.do" style="width:125px;height:36px;line-height:36px;text-align:center;background:#fd7c1a;border-radius:5px;color:#fff;font-size:16px;display:inline-block;margin-right:10px;">实名认证</a>
           <a href="${base}/userCenter/index.do" style="color:#006dc1;">个人中心</a>
         </div>
       </div>
    </div>
  </div>
</div><!-- login end -->
<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
$("#header_wdzh").addClass("hq");<#-- header.ftl 选中样式 -->
</script>
</html>
