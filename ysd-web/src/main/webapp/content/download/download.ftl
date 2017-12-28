<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
<title>乐商贷APP下载</title>
<style type="text/css">
*{
	margin:0;
	padding:0;
	font-family:"Microsoft YaHei";
	box-sizing:content-box;
}
body{
	font-size:14px;
	color:#333;
}
.download-wrap{
	margin:0 auto;
	max-width:750px;
}
.download-wrap .title{
	font-size:16px;
	text-align:center;
	line-height:50px;
	border-bottom:1px solid #e0e0e0;
}
.download-img{
	padding:20px 0;
	text-align:center;
}
.download-img img{width:28%;}
.download-img a{
	display:inline-block;
	margin-top:10px;
	padding:5px 0;
	width:35%;
	color:#fff;
	text-decoration:none;
	background:#d62d18;
	border-radius:20px;
}
.download-img .two-btn a{
	width:20%;
	min-width:100px;
	border-radius:6px;
}
.download-img .ios-btn{
	color:#d62d18;
	background:#fff;
	border:1px solid #d62d18;
}
.download-content{padding:0 15px;}
.download-content .stitle{line-height:40px;}
.apply-img{
	width:100%;
	overflow:auto;
}
.apply-img img{
	float:left;
	margin-right:5px;
	width:200px;
}
.apply-img div{
	width:9999px;
}
.download-content p{
	font-size:12px;
	color:#999;
	line-height:25px;
}
.mt-10{margin-top:10px;}
</style>
</head>
<body>
	<div class="download-wrap">
		<div class="title">乐商贷</div>
		<div class="download-img">
			<img src="../img/download/ic_launcher.png" alt=""/>
			<#--<p><a href="${base}/download/yueshangdai.apk">点击下载</a></p>-->
			<p class="two-btn"><a href="#" onclick="downloadApp()" style="margin-right:5%">android下载</a>
                <a class="ios-btn" href="https://itunes.apple.com/cn/app/%E4%B9%90%E5%95%86%E8%B4%B7/id1288643093?mt=8">ios下载</a></p>
		</div>
		<div class="download-content">
            <div class="stitle">温馨提示</div>
            <p style="color: red">请点击微信右上角按钮,然后在弹出菜单中,点击在浏览器中打开后,点击下载即可</p>

			<div class="stitle">应用截图</div>
			<div class="apply-img">
			    <div>
					<img src="../img/download/1.jpg?v=1" alt=""/>
					<img src="../img/download/2.jpg?v=1" alt=""/>
					<img src="../img/download/3.jpg?v=1" alt=""/>
					<img src="../img/download/4.jpg?v=1" alt=""/>
					<img src="../img/download/5.jpg?v=1" alt=""/>
				</div>
			</div>


			<div class="stitle">应用介绍</div>
			<p>乐商贷———温州乐商投资有限公司成立于2011年4月27日，注册资本金5000万人民币，现实缴资本5000万人民币。 公司旗下“乐商贷”平台已获得浙江省通信管理局ICP备案，并已和渤海银行开展银行存管业务对接，预计10月份完成银行存管。 乐商贷聚焦小微金融及商会内部金融，致力于打造全场景金融新生态。乐商贷充分融合金融与“互联网+”思维，以O2O模式让金融对接资产， 以专业开放的方式将民间资金引入普惠金融体系，实现财富管理、生活服务、支付和供应链之间的闭环体系，倡导以普惠金融实践者为使命，致力于成为中国互金行业领导者。</p>
			<p class="mt-10">1.渤海银行直接管存，ICP信息服务业务许可证</p>
			<p>2.多重风控+小额分散,独立的第三方机构担保</p>
			<p class="mt-10">乐商贷客服热线：400-057-7820</p>
		</div>
	</div>
</body>
<script type="text/javascript">
    function isWeiXin(){
        var ua = window.navigator.userAgent.toLowerCase();
        if(ua.match(/MicroMessenger/i) == 'micromessenger'){
            return true;
        }else{
            return false;
        }
    }

    function downloadApp() {
        if (isWeiXin()) {
            alert("请点击微信右上角按钮,然后在弹出菜单中,点击在浏览器中打开后,点击下载即可");
            return;
        }
        window.location.href = "${base}/download/yueshangdai.apk";
    }
</script>
</html>