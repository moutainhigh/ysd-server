<!DOCTYPE html>
<html>
  <head>
    <title>${Application ["qmd.setting.name"]} 系统信息</title>
	
    <#include "/content/common/meta.ftl">
   
  <body>
 <!-- header -->
<#include "/content/common/header.html">

<div style="width:100%;background:#fff;">
  <div style="width:960px; margin:20px auto; ">
     <div style="background:#ff6600; height:40px; line-height:40px; text-indent:20px; color:#fff; font-size:16px;">系统消息</div>
     <div style=" width:800px; height:350px; border:1px solid #ccc; margin:20px auto;">
       <table width="450" style=" margin:140px auto">
         <tr>
           <td rowspan="3" width="58"><img src="/static/images/chenggong.jpg" /></td>
         </tr>
         <tr><td><span style="color:#646464; font-size:24px;  line-height: 30px;" >${msg}</span></td></tr>
         <tr><td><a href="/userCenter/index.do" style="font-size:12px; color:#F00; padding-left:5px;">进入用户中心</a></td></tr>
       </table>
     
     </div>
  </div>
</div>

<#include "/content/common/footer.html">

</body>
</html>
