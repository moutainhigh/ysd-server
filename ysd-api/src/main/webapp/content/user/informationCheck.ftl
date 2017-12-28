<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-我的账户 -申请开通线上借款</title>
	<#include "/content/common/meta.ftl">
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">

<div style="width:100%; background:url(${base}/static/img/d6.png) 0 0 repeat-x; min-width:1000px; padding-bottom:30px;" class="biaoti_bg">
	<div class="content">
		<div class="biaoti">
			<a href="${base}/">${Application ["qmd.setting.name"]}</a><a>></a>
			<a href="${base}/userCenter/index.do">我的账户</a><a>></a>
			<a class="liebiao">申请开通线上借款</a>
		</div>
		<!--biaoti end-->
		<div style="width:1000px;" class="big_content">
			<#include "/content/common/user_center_left.ftl">
			<!--left_menu end-->
			
			<div class="right_jiluneirong">
				<div class="tabsblk">
					<a href="javascript:void(0);"  class="checked">申请线上借款</a>
				</div>
				<div class="sepblk"></div>
				<div class="white">
					<div class="base_tabCon">
						<div style="color:#D14324;  font-weight:bold; padding:10px 25px; font-size:16px;">投资3步骤之二</div>
						<div style=" float:left; width:100%; height:80px;" class="base_step base_step_2">
							<ul>
								<li class="base_yu">
									<h3>填写借款人资料</h3>
									成为会员，确认您的投资身份
								</li>
								<li class="base_yi">
									<h3>等待网站审核</h3>
									提前为投资项目注入资金
								</li>
								<li class="base_yo">
									<h3>发布借款信息</h3>
									选择您满意的项目进行投资
								</li>
							</ul>
						</div>
						<div style="border:1px solid #C1D3E6; background:#fff; padding:10px 0 10px 20px; margin:0px 20px; color:#363636;font-size:14px; clear:both;">
							<h3 style="color:#D14324;font-size:14px; padding-bottom:5px; ">您已成功填写资料，系统审核中</h3>
							<#-->
							验证邮寄24小时内有效，请尽快登录您的邮箱点击验证连接完成验证&nbsp;&nbsp;<a href=""><u>重新填写</u></a>
							-->
						</div>
					</div>
					<!--base_tabCon end -->
				</div>
				<div class="fanye"></div>
			</div>
			<!--right_jiluneirong end-->
			<div style="clear:both;"></div>
		</div>
		<!--big_content end-->
	</div>
	<!--content end-->
</div>
<!--biaoti_bg end-->


<#include "/content/common/footer.ftl">
</body>

	<script type="text/javascript">
	$().ready( function() {
	
		$("#borrower_choose").addClass("nsg nsg_a1");
		
	});
</script>
</html>
