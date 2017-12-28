<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>借款的还款</title>
<style type="text/css">  
body{ margin:0 ; padding:0;font-family:"微软雅黑";}
ul,li{ list-style:none; margin:0 ; padding:0;}
</style>
</head>
<body>
<div style="background:#E9EFF5; width:100%; height:auto; padding:20px 0;">
	<div style="background:#fff;border-radius:10px; border:1px solid #E0E0E0; width:1000px; margin:0 auto;">
		<div style=" margin-bottom:30px; background-color:#c1d3e6; height:64px;border-radius:10px 10px 0 0; padding-left:40px; line-height:64px;">
			<span style="color:#363636; font-size:16px; margin-right:15px; font-weight:700;">${site_name}</span>
		</div>
		<div style="padding:30px 50px 40px 40px;">
			<ul>
				<li style=" margin-bottom:44px; padding-left:18px;">
					<span style=" width:800px;line-height:30px; display:inline-block;background-color:#f2f4f5;vertical-align:top; color:#363636;padding:2px 0px 2px 10px; font-size:18px;">
					亲爱的${(userName)!"客户"}， 您好！<br/>
					借款人在${repayDate}对[<a href="${websiteurl}/borrow/detail.do?bId=${borrowId}">${borrowName}</a>]借款还款，还款金额为:￥${repayMoney}。<br/>
					请注意查看。<br/>
					</span>
				</li>
			</ul>
			<ul style="border-top:1px solid #f2f4f5; padding-top:15px;">
				<li style="color:#666;font-size:16px; margin-bottom:10px;">联系我们</li>
				<li style="color:#666;font-size:12px; margin-bottom:10px;">全国服务热线<font style="font-size:14px; padding-left:10px;">${site_phone}</font></li>
				<li style="color:#666;font-size:12px; margin-bottom:0px;">官方QQ群<font style="font-size:14px; padding-left:27px;">${site_qq}</font></li>
			</ul>
		</div>
	</div>
</div>

</body>
</html>