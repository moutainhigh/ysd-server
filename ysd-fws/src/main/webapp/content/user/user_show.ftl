
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-账户管理-账号信息</title>
	<#include "/content/common/meta.ftl">
 <!-- header -->
</head>
<body>
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_user.ftl">
<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="#">账户管理</a></li>
			<li><a href="#">账号信息</a></li>
		</ul>
	</div>
    <#assign text="${msg}" />
	<#assign data=text?eval />
    <div class="mainWrap">
        <div class="broderShadow">
        	<h3>基本信息</h3>
        		<form action="${base}/show.do" method="post">
					<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="96">分类</td>
		                <td><select name="r"><option value="c" <#if r=='c'>selected</#if>>充值</option><option value="t0" <#if r=='t0'>selected</#if>>提现</option></select>
		                <input type="submit" value="提交" id="submitButton" class="btn1 white"></td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="96">日期(yyyyMMdd)：</td>
		                <td><input type="text" name="begin" value="${begin}"/>-<input type="text" name="end" value="${end}"/>日期格式例:<font color="red">20150106</font></td>
		              </tr>
					<#list showBeanList as t>
					<tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="96">${t.name}：</td>
		                <td>${t.money?string(",###0.00")}</td>
		              </tr>
  					</#list>
		              </table>
		             </form>
		           </div>
        
    </div>
    
</div>

	<#include "/content/common/footer.ftl">
<#--
<script type="text/javascript">
	$().ready(function() {
		$(".gjxx").attr("id","gjxx");
		$("#li_a_zhxx").addClass("li_a_select");
	})
	</script>
	-->
</body>
	
</body>
</html>

<!DOCTYPE html>
<html>
<head>
	
</head>
<body>
	
</body>
</html>
