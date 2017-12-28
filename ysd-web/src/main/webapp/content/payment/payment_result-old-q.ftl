<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台 充值结果信息</title>
	<#include "/content/common/meta.ftl">

<style>
body{ margin:0 ; padding:0;font-family:"微软雅黑";}
ul,li{ list-style:none; margin:0 ; padding:0;}
#header {
background-color: #fff;
}
</style>

</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">

<div class="find">
  <div style="width:1200px;height:530px; margin:30px auto 90px;background:#fff;">
      <div class="top">
          <p><span>系统通知</span></p>
      </div>
      <div style="margin:85px auto 0;font-size:16px;">
         <div style="color:#666;text-align:center;">
            <span style="width:34px;height:34px;display:inline-block;line-height:34px;text-align:center;color:#fff;font-weight:bold;border-radius:50%;background:#fd7c1a;margin-right:8px;font-size:24px;">!</span>恭喜您，充值成功！您已经成功充值 ${(userAccountRecharge.money)!}元
          </div>
         <div style="text-align:center;margin-top:130px;">
         		<a href="${base}/payment/rechargeTo.do" style="width:108px;height:36px;border-radius:5px;line-height:36px;background-color:#fd7c1a;color:#fff;display:inline-block;text-align:center;cursor:pointer; font-size:14px;">[继续充值]</a>
			    <a href="${base}/userCenter/index.do" style="width:108px;height:36px;border-radius:5px;line-height:36px;background-color:#fd7c1a;color:#fff;display:inline-block;text-align:center;cursor:pointer; font-size:14px;">[返回我的账户]</a>
         </div>
      </div>
  </div>
</div><!-- end -->

<#include "/content/common/footer.ftl">

</body>
</html>
