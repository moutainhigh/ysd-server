<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" id="viewport" />
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-注册成功</title>
    <link href="../place/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../place/css/bootstrap-responsive.min.css">
    <link rel="stylesheet" href="../place/css/amazeui.min.css">
    <link href="../css/base.css" rel="stylesheet">
    <link href="../place/css/style1.css" rel="stylesheet">
	<script src="../js/jquery.min.js"></script>
    <script type="text/javascript" src="../place/js/bootstrap.min.js"></script>
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
    .am-g .am-u-sm-centered{background: #fff;padding: 2em 1em 1em;border-radius: 10px;}
    .am-g .erweima{background: #e6e6e6;padding: 1em;border-radius: 10px;}
    .am-g .thumbs{padding:0.5em;border-radius:50%;font-size:1.2em;}
    </style>
  </head>
<body>
  <div class="row" style="width:90%;margin:0 auto;max-width:1200px">
    <div class="col-md-6 col-md-push-6 col-sm-12">
      <img src="../img/fr01.png" class="img-responsive center-block img1" style="width:90%;">
    </div>
    <div class="col-md-6 col-md-pull-6 col-sm-12">
      <div class="am-g">
      <div class="am-u-md-8 am-u-sm-centered">
       <div style="overflow:hidden;padding-left:2em;">
          <div style="float:left;margin-right:1em;"><span class="glyphicon glyphicon-thumbs-up thumbs bgc color1" style=""></span></div>
          <div class="color2" style="float:left;">
            <p class="wanc"><span class="color">恭喜您，注册成功！</span><br />更多精彩尽在APP客户端</p>
          </div>
       </div>
       <div class="erweima">
         <img src="../img/p_load.png" style="max-width:100%;" alt="">
       </div>
       <div><a href="${base}/"  class="color tac" style="margin-top:1em;display:block;">前往乐商贷</a></div>
      </div>
    </div>
    </div> 
  </div>
 
 
   <div class="row introduce" style="margin-top:2em;">
    <div  class="col-md-6 col-sm-6 col-xs-12 col" style="margin-bottom:0.8em;">
      <div class="intr_w">
       <div class="icons land_iocn1" style="width:20%;float:left;"><img src="../img/land_icon1.png" style="width:4em;" alt=""></div>
       <div style="width:80%;float:left;">
         <p class="color1">国有企业＆上市公司  战略入股</p>
         <p class="color3">浙江清华长三角研究院＆先進光電（股票代码3362）战略入股</p>
       </div>
       <div style="clear:both;"></div>
     </div>
    </div>
    <div  class="col-md-6 col-sm-6 col-xs-12 col">
      <div class="intr_w" style="">l
       <div class="icons land_iocn1" style="width:20%;float:left;"><img src="../img/land_icon2.png" style="width:4em;" alt=""></div>
       <div style="width:80%;float:left;">
         <p class="color1">资金安全 多重保护</p>
         <p class="color3">第三方支付账户监管，律师所逐笔见证</p>
       </div>
       <div style="clear:both;"></div>
     </div>
    </div>
  </div>
  <div class="row introduce" style="margin-bottom:5em;">
    <div  class="col-md-6 col-sm-6 col-xs-12 col" >
      <div class="intr_w">
       <div class="icons land_iocn1" style="width:20%;float:left;"><img src="../img/land_icon3.png" style="width:4em;" alt=""></div>
       <div style="width:80%;float:left;">
         <p class="color1">放心理财 安全无忧</p>
         <p class="color3">优选合作机构，严格监管承诺回购</p>
       </div>
       <div style="clear:both;"></div>
     </div>
    </div>
    <div  class="col-md-6 col-sm-6 col-xs-12 col">
      <div class="intr_w">
       <div class="icons land_iocn1" style="width:20%;float:left;"><img src="../img/land_icon4.png" style="width:4em;" alt=""></div>
       <div style="width:80%;float:left;">
         <p class="color1">优质项目 稳健收益</p>
         <p class="color3">100元起投，年化收益高达15%</p>
       </div>
       <div style="clear:both;"></div>
     </div>
    </div>
  </div>
 
 
  <footer class="bottom">
    使用App客户端，体验更多惊喜！&nbsp;<a href="javascript:void(0);" id="downloadid" class="bgc am-radius color1" style="padding:0.3em 0.8em;">立即下载</a>&nbsp;
    <span class="glyphicon glyphicon-remove remove" style="font-size:1.2em;"></span>
  </footer>
</body>
<script>
  $(".remove").click(function(){
    $(".bottom").css('display','none');
  })

	$('#downloadid').click(function(){
		var u = navigator.userAgent;
		var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
		var ua = navigator.userAgent.toLowerCase();  
	    if(ua.match(/MicroMessenger/i)=="micromessenger") {  
	       alert('请通过浏览器新打开此页面');
	    } else { 
			if (isiOS) {
                // TODO
				window.location.href = "https://itunes.apple.com/cn/app/id999649448";
			} else if (isAndroid) {
				window.location.href = "https://www.yueshanggroup.cn/download/yueshangdai${placeChilder.random}.apk";
			} else {
				window.location.href = "https://www.yueshanggroup.cn/download.html";
			}
		
	    }  
	}); 
</script>
</html>
