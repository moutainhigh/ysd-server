<!DOCTYPE html>
<html>
<head>
	<title>${Application ["qmd.setting.name"]}${tempStr}结果信息</title>
	<#include "/content/common/meta.ftl">
	
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">
        <div style=" width:960px; margin:20px auto; background:#fff;">
		    <div style="background:#ede3cb; height:40px; line-height:40px; text-indent:20px; color:#000; font-size:16px;">系统消息</div>
			     <div style=" width:800px; height:350px; border:1px solid #ccc; margin:20px auto;">
				       	<table width="500" style=" margin:140px auto">
			         	  <tr>
				           <td rowspan="3" width="80"><img src="${base}/static/img/chenggong.jpg" /></td>
				         </tr>
				         <tr><td colspan="5"><span style="color:#646464; font-size:27px;">您通过"线下充值" 成功充值${(userAccountRecharge.money)!}元，请耐心等待客服审核...!</span></td></tr> 
				         
				       <tr>
				         	<td>
				         		<a href="${base}/account/detail.do?type=recharge" style="font-size:15px; color:#F00; ">查看充值记录</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
				         	</td>
				         	<td>
				         	<a href="${base}/userCenter/index.do" style="font-size:15px; color:#F00; ">返回我的账户</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
				         		
				         	</td>
				         	<td>
				         		<a href="${base}/payment/rechargeTo.do" style="font-size:15px; color:#F00; ">继续充值</a>
				         	</td>
				         </tr>
				       </table>
			     </div>
		  	</div>  
<#include "/content/common/footer.ftl">

</body>
</html>
