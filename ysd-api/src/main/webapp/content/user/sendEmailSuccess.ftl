<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-我的账户 -邮箱认证（暂时无用页面）</title>
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
			<a href="${base}/">${Application ["qmd.setting.name"]}</a> &gt; <a href="${base}/userCenter/index.do">我的账户</a>  &gt; 邮箱认证
		</div>
		<div class="tab clearfix">
			<ul>
				<li><a href="" class="current"><span>邮箱认证</span></a></li>
			</ul>		
		</div>
		<div class="tab-con">
			<dl class="confirm">
				<dt><img src="${base}/static/img/ic_ok.png" /></dt>
				<dd><h3>您的邮箱${loginUser.email}已经认证</h3><a href="" class="c2" id = "new_email">重新认证</a></dd>
			</dl>
			
			<dl class="confirm">
				<dt><img src="${base}/static/img/ic_ok.png" /></dt>
				<dd><h3>已经向您的邮箱lovexihu@gamil.com发送了一封验证邮件，请进入验证！</h3>验证邮寄24小时内有效，请尽快登录您的邮箱点击验证连接完成验证
				<span id="certificationMailStatus"><a href="javascript:void(0);" id="replaySendEmail">重新发送</a></span></dd>
			</dl>		
			<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
		</div>
		<div class="tab-con_bottom"></div>
		
	</div>
</div>
<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
	$().ready( function() {
		var $replaySendEmail = $("#replaySendEmail");
		var $certificationMailStatus = $("#certificationMailStatus");
		
		$replaySendEmail.click(function(){
			$.ajax({
					url: "${base}/userCenter/ajaxSendEmail.do",
					type: "POST",
					dataType: "json",
					cache: false,
					beforeSend: function() {
						$certificationMailStatus.html('确认邮件发送中，请稍后...');
					},
					success: function(data) {
						$.message({type: data.status, content: data.message});
						if (data.status == "success") {
							$certificationMailStatus.html('确认邮件发送成功，请及时到注册邮箱中确认...');
						} else {
							$certificationMailStatus.html('确认邮件发送失败，<a href="javascript:void(0);" id="replaySendEmail">重新发送</a>');
						}
					}
				});
		})
	});
</script>
</html>
