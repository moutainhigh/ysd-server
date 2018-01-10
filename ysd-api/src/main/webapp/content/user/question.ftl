<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <meta charset="gb2312" />
  <title>全民贷-我的账户 -修改密码保护</title>
  <#include "/content/common/meta.ftl">
  </head>
<body>
<!-- header -->
<#include "/content/common/header.ftl">

<!-- content -->
<div class="content clearfix">

	<#include "/content/common/user_center_left.ftl">

	<!-- right -->
	<div class="my-account">
		<!-- location -->
		<div class="location">
			<a href="${base}/">${Application ["qmd.setting.name"]}</a> &gt; <a href="${base}/userCenter/index.do">我的账户 </a>&gt; 修改密码保护
		</div>
		
     	<div class="tab clearfix">
			<ul>
				<li><a href="${base}/userCenter/toPassword.do"><span>登录密码</span></a></li>
				<li><a href="${base}/userCenter/toPayPassword.do"><span>安全密码</span></a></li>
                <li><a class="current" href="javascript:void(0);"><span>密码保护</span></a></li>
            </ul>
        </div>
        <div class="tab-con">	

			<!-- frm -->
			<div class="frm">
				<form id="questionForm" method="post"  action="${base}/userCenter/tosetQuestion.do"> 
				<table width="90%">
					<tr >
						<td style="text-align:center;" class="item" colspan="2">您已经设置了密码保护功能，请先输入答案再进行修改。</td>
					</tr>
					<tr >
						 <td align=right class="item" >请选择问题：</td><td>${user.questionMes}
						 </td></li>
					</tr>
					<tr >
						 <td align=right class="item" >请输入答案：</td>
						 <td><input type="text"  name = "user.answer" >
                         </td>
					</tr>
                    <tr >
						<td class="item"></td>
						<td><span class="btn_l s3"><input class="submit" type="submit" value="确 定"/></span></td>
					</tr>
				</table> 
             </form>
			</div>
			
			<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
		</div>
		<div class="tab-con_bottom"></div>
		
	</div>
</div>

<#include "/content/common/footer.ftl">
 </body>
</html>
