
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-我的账户</title>
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
			<li><a href="#">我就爱车服务商业务管理平台</a></li>
			<li>首页</li>
		</ul>
	</div>
    
    <div class="mainWrap">

		<div class="row">
			<div class="col2">
				<div class="inbox">
					<div class="blk">
						<div class="title">用户信息</div>
						<div class="blk_line blk_logo"><#--<img src="${Application ["qmd.img.baseUrl"]}${agencySpread.logo}" width="200" height="50" />--></div>
						<div class="blk_line blk_logo">
							<ul>
								<li>${loginUser.username}:</li>
								<li>欢迎登录，您是我就爱车的<font style="color:#ea5504;">推广服务商</font>！</li>
							</ul>
						</div>
						<div class="blk_line blk2"><span>上次登录时间：${loginUser.lastTime?string("yyyy-MM-dd HH:mm:ss")}</span></div>
						<div class="blk_line blk2"><span>上次登录IP：${loginUser.lastIp}</span></div>
						<div style="clear:both;"></div>
					</div>
				</div>

				<div class="inbox">
					<div class="blk">
						<div class="title">常用功能</div>
						<div class="quick">
							<table width="100%" border="0" cellpadding="10" cellspacing="12">
								<tr>
									<td>
										<a href="${base}/spread/spreadMemberList.do">
											<font class="orgdot">●</font>推广渠道
										</a>
									</td>
									<td>
										<a href="${base}/spread/tjProfile.do">
											<font class="orgdot">●</font>客户数据统计
										</a>
									</td>
								</tr>
								<tr>
									<td>
										<a href="${base}/payment/rechargeToAgency.do">
											<font class="orgdot">●</font>门店充值
										</a>
									</td>
									<td>
										<a href="${base}/agency_tg/detail.do">
											<font class="orgdot">●</font>账号信息
										</a>
									</td>
								</tr>
								<tr>
									<td>
										<a href="${base}/agency_tg/subpageDetail.do">
											<font class="orgdot">●</font>子页面管理
										</a>
									</td>
									<td>
										<a href="${base}/userCenter/toPassword.do">
											<font class="orgdot">●</font>密码管理
										</a>
									</td>
								</tr>
							</table>
		   				</div>
					</div>
				</div>
			</div>

			
		</div>
	</div>
</div>

	<#include "/content/common/footer.ftl">
	<script>
		$().ready( function() {
			$('.title span').click(function(){
				$(this).addClass('selected').siblings().removeClass('selected');
				var i=$('.title span').index(this);
				$('.list > ul').eq(i).show().siblings().hide();
			})
		});
	</script>
</body>
</html>