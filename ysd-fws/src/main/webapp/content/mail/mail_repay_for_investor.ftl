<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>借款的还款</title>
<STYLE type="text/css">  
BODY { font-size: 14px; line-height: 1.5  } 
</STYLE>
</head>
<body>
<div style="background:#ececec;padding:35px;">
	<table cellpadding="0" align="center" width="600" style="background:#fff;width:600px;margin:0 auto;text-align:left;position:relative;border-radius:5px;font-size:14px; font-family:'lucida Grande',Verdana;line-height:1.5;box-shadow:0 0 5px #999999;border-collapse:collapse;">
	<tr><th valign="middle" style="height:25px;color:#fff; font-size:14px;line-height:25px; font-weight:bold;text-align:left;padding:15px 35px; border-bottom:1px solid #467ec3;background:#810000;border-radius:5px 5px 0 0;">
		<img style="float:left;margin-right:8px;" src="${imgbase}${logoPath}"/>
	</th></tr>
	<tr><td>
	<div style="margin:0;padding:35px 35px 40px;">	
			<h2 style="font-weight:bold; font-size:14px;margin:5px 0;">尊敬的${userName},您好！<span id="mailUserName"></span></h2>
			<p style="color:#313131;line-height:18px;font-size:14px;margin:20px 0;text-indent:2em;">借款人在${repayDate}对[<a href="${memberUrl}/borrow/detail.do?bId=${borrowId}">${borrowName}</a>]借款还款，还款金额为:￥${repayMoney}。</p>
			<p style="color:#313131;line-height:18px;font-size:14px;margin:20px 0;text-indent:2em;">请注意查看。</p>
	</div>
	</td></tr>
	</table>
</div>
</body>
</html>