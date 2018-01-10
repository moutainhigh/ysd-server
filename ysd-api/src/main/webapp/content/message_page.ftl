<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]} 系统信息</title>
	<#include "/content/common/meta.ftl">
	
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">

   <div style="padding:100px 50px 150px 50px; background:#efebdf; width:800px; margin:0 auto; text-align:center;">
      <div style="color:#595757; font-size:27px; padding:50px 0px 0 0;">${msg}</div>
 	  <div><a href="${base}${msgUrl}" style="color:#f74405;font-family:'宋体'; margin-top:20px;">请点击这里</a></div>
  </div>

<#include "/content/common/footer.ftl">

</body>
</html>
