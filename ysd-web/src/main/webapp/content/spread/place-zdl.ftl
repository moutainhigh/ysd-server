<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" id="viewport" />
   	<#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-注册</title>

    <link href="../place/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../place/css/bootstrap-responsive.min.css">
    <link rel="stylesheet" href="../place/css/amazeui.min.css">
    <link rel="stylesheet" href="../place/css/pop.css">
    <link href="../css/base.css" rel="stylesheet">
    <link href="../place/css/style1.css" rel="stylesheet">
	<script src="../js/jquery.min.js"></script>
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
    </style>
  </head>
<body>
 <div class="header" id="header">
    <div style="width:1190px; margin:0 auto;">
      <div style="width:1190px; height:42px; line-height:42px; float:left;">
          <div style="float:left; color:#d8d8d8; font-size:14px; padding-top:0;">客服热线：${(commMess.phone)!}</div>
       </div>
      <div style="width:1190px; clear:both; float:left;overflow:hidden;">
        <div class="logo"><a href="${base}/index.do">乐商贷</a></div>
            <ul class="list_ul">
               <li><a href="${base}/index.do"><span class="hp hq" id="header_sy">首页</span></a></li>
               <li><a href="${base}/borrow/list.do"><span class="hp" id="header_xmzx">项目中心</span></a></li>
               <li><a href="${base}/newPoint.do"><span class="hp" id="header_xszy">新手指引</span></a></li>
               <li><a href="${base}/safe1.do"><span class="hp" id="header_aqbz">安全保障</span></a></li>
               <li><a href="${base}/article/list/about_us.htm"><span class="hp" id="header_gywm">关于我们</span></a></li>
               <li><a href="${base}/userCenter/index.do"><span class="hp" id="header_wdzh">我的账户</span></a></li>
                <li><a href="#"><span class="hp" id="he">返回旧版</span></a></li>
            </ul>
       </div>
    </div>
  </div>
  <div class="row" style="width:90%;margin:0 auto;max-width:1200px">
    <div class="col-md-6 col-md-push-6 col-sm-12">
      <img src="<#if scrollpic !=''><@imageUrlDecode imgurl="${scrollpic.img}"; imgurl>${imgurl}</@imageUrlDecode><#else>../img/fr01.png</#if>" class="img-responsive center-block img1" style="width:90%;">
    </div>
    <div class="col-md-6 col-md-pull-6 col-sm-12">
      <div class="am-g">
      <div class="am-u-md-8 am-u-sm-centered" style="background: #fff;padding: 2em 1em 1em;border-radius: 10px;">
        <form class="am-form" id="registerForm" AUTOCOMPLETE="off">
     	 <fieldset class="am-form-set">
     	 <#if friendUsername?exists && friendUsername !=''>
            <div class="am-form-group  am-form-icon" style="font-size:1.4rem;color:#666;">
               			邀请人：${friendUsername}
            </div>
         </#if>
            <div class="am-form-group  am-form-icon">
               <input type="text" id="place_username" name="username" value="" onFocus="$('#err_show1').html('');" class="am-form-field" style="padding-left:2em!important;" placeholder="请输入常用手机号">
                <span class="glyphicon glyphicon-phone"></span>
            </div>
            <div class="err_show color" id="err_show1"></div>
            
               <input type="password" id="place_pwd" name="pwd" value="" onFocus="$('#err_show2').html('');" class="am-form-field" style="padding-left:2em!important;" placeholder="登录密码（8~16位）">
            <div class="am-form-group  am-form-icon">
                <span class="glyphicon glyphicon-lock"></span>
            </div>
            <div class="err_show color" id="err_show2"></div>
            
            <div class="am-form-group">
              <input type="text" id="place_code" name="code" value="" onFocus="$('#err_show3').html('');" placeholder="图形验证码" maxlength="4" style="width:50%;height:2.4em;float:left;">
	              <a onclick="verifyCodeLink();" class="am-btn am-btn-default am-radius bgc2" style="float:right;width:44%;height:2.4em;padding:0;">
	             	 <img src="${base}/placeRand.do" id="code_img" style="max-height:100%;max-height:100%;max-width:100%;" alt="刷新验证码">
	              </a>
              <div style="clear:both;"></div>
            </div>
            <div class="err_show color" id="err_show3"></div>
            
            <div class="am-form-group">
              <input type="text" name="smsCode" value="" onFocus="$('#err_show4').html('');" placeholder="短信验证码" maxlength="6" style="width:50%;height:2.4em;float:left;">
              <button type="button"  id="getcode" onclick="sendAuthCode(this);" class=" am-radius bgc color1 " style="float:right;width:44%;height:2.5em;font-size:1.6rem;border:0;">获取验证码</button>
              <div style="clear:both;"></div>
            </div>
            <div class="err_show color" id="err_show4"></div>
            
            <button type="submit" class="am-btn-block am-radius color1 bgc" style="height:2.5em;border:0;font-size:1.6rem;" />立即注册</button>
          </fieldset>
        </form>
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
      <div class="intr_w" style="">
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

<div class="f_b">

    <footer class="bottom">
    使用App客户端，体验更多惊喜！&nbsp;<a href="javascript:void(0);" id="downloadid" class="bgc am-radius color1 downloadidClass" style="padding:0.3em 0.8em;">立即下载</a>&nbsp;
    <span class="glyphicon glyphicon-remove remove" style="font-size:1.2em;"></span>
    </footer>
    
    <div class="footer" id="footer" style="padding:20px 0 40px 0;">
   <div class="fo_main">
     <div class="fo_lf" style="margin-top:20px;">
       <ul>
         <li>
           <a href="${base}/newPoint.do">新手指引</a>
           <span>|</span>
           <a href="${base}/article/list/about_us.htm">关于我们</a>
           <span>|</span>
           <a href="${base}/article/list/contact_us.htm">联系我们</a>
           <span>|</span>
           <a href="${base}/safe.do">安全保障</a>
         </li>
         <li>ICP备案：浙ICP备17036398号  </li>
         <li style="margin-bottom:0px;">温州乐商投资有限公司</li>
      </ul>
     </div>
     <div style="float:left; margin-left:100px;">
       <div style="color:#ccc; font-size:16px; margin-bottom:10px;">关注微信</div>
       <div style="float:left;"><img src="/img/a30.jpg" width="88" height="88"></div>
       <div style="float:left; color:#d4d4d4; font-size:14px; margin-left:16px;">
         <div style="margin:10px 0;">400-057-7820(个人业务)</div>
         <div><img src="/img/a6.png">  在线客服</div>
       </div>
     </div>
     <div class="fo_ri" style="margin-top:20px;">
       <ul>
         <li>全国服务热线</li>
         <li style="color:#d2d2d2;">400-057-7820</li>
         <li style="font-size:16px;margin-bottom:0px;">服务时间 9:00-20:00</li>
       </ul>
     </div>
   </div>
   <div style="clear:both;width:1200px; height:1px; "></div>
 </div>
  </div>
  
   <!-- cd-popup -->
    <div class="cd-popup" role="alert">
      <div class="cd-popup-container">
        <div class="tac"><img src="../img/suc.png" alt=""></div>
         <p>恭喜您获得"<span class="color">${hongbao.total}元红包</span>"，下载乐商贷理财APP马上领取</p>
         <a href="#" class="bgc color2 downloadidClass" id="app_show">立即下载APP</a>
         <a href="https://www.yueshanggroup.cn/index.do" class="bgc color2" id="pc_show">立即前往投资</a>
      </div> <!-- cd-popup-container -->
</div> <!-- cd-popup -->
  
</body>

<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript">
	$().ready(function() {
	
	
	
		var u = navigator.userAgent;
		var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
		var ua = navigator.userAgent.toLowerCase();  
	    if(ua.match(/MicroMessenger/i)=="micromessenger") {  
	       alert('请点击微信右上角按钮，然后在弹出的菜单中，点击在浏览器中打开，即可安装');
	    } else { 
			if (isiOS) {
				$("#app_show").show();
				$("#pc_show").hide();
			} else if (isAndroid) {
				$("#app_show").show();
				$("#pc_show").hide();
			} else {
				$("#app_show").hide();
				$("#pc_show").show();
			}
	    }  
	
	
	
	
	
	
	
	
	
	
		var $registerForm = $("#registerForm");	
		// 表单验证
			$registerForm.validate({
				errorClass:"login_error",
				rules: {
					"username":{
						required:true,
						phone:true
					},
					"pwd":{
						required: true,
						strongTxt:true,
						minlength:8,
						maxlength:16
					},
					"code":{
						required:true
					},
					"smsCode":{
						required:true
					}
				},
				messages: {
					"username":{
						required:"手机号不能为空",
						phone:"手机号码格式错误"
					},
					"pwd": {
						required: "请输入密码",
						minlength: "密码必须大于等于8",
						maxlength: "密码必须小于等于16"
					},
					"code":{
						required:"请输入图形验证码"
					},
					"smsCode":{
						required:"请输入短信验证码"
					}
				},
				errorPlacement: function(error, element) {
				  	error.appendTo(element.parents(".am-form-group").next(".err_show").eq(0));
				},
				submitHandler: function(form) {
					$.ajax({ 
				        type: "post",
				        dataType : "json",
				        data: $("#registerForm").serialize(),
				        url: '${base}/ajaxNewPlace.do',
			    	    success: function(data, textStatus){
			    	    	if(data.rcd=='R0001'){
			    	    		//window.location.href='${base}/regSuccess.do?id=${(placeChilder.id)!}';
   								 $('.cd-popup').addClass('is-visible');
			    	    	}else{
			    	    		$("#err_show"+data.rcd).html(data.rmg);
			    	    	}
			    	    }
					});
				}
	});
});

  $('.cd-popup').on('click', function(event){
      event.preventDefault();
      $(this).removeClass('is-visible');
  });
  
	function verifyCodeLink(){
		$("#code_img").attr("src", "/placeRand.do?timestamp" + (new Date()).valueOf());
	}
	//发送短信验证码
	var wait2=60;
	function sendAuthCode(o){
		$("#err_show4").html('');
		if (wait2 ==0) {
			        o.removeAttribute("disabled");         
			        o.innerHTML="获取验证码";
			        $('#getcode').removeAttr("disabled");
			        $("#getcode").removeClass("getcode");
			        wait2 = 60;
		} else {
		    	if(wait2==60){
				    		var phoneReg = $("#place_username").val();
				    	 	var mycode = $("#place_code").val();
				    		if(phoneReg.length<=0){
				    			$("#username_msg").html('请输入手机号');
				    			return false;
				    		}
				    		var regPhone =/^(13[0-9]|17[0-9]|18[0-9]|15[0-9]|18[8|9])\d{8}$/;
				    		if (!regPhone.test(phoneReg)){
				    			$("#username_msg").html('手机号格式不正确');
				    			return false;
				    	 	}
				    	 	$.ajax({
					        	   url: '${base}/sendPhoneCode.do',
					        	   async:false,
					        	   type: 'post', 
					        	   dataType:'json', 
					        	   data: {'phoneReg':phoneReg,'mycode':mycode}, 
					        	   beforeSend: function() {
										  o.innerHTML="短信发送中...";
										  o.setAttribute("disabled", true);
										  $("#getcode").addClass("getcode");
			        					  $('#getcode').attr('disabled',"true");
								   },
					        	   success: function (data) { 
					        		   if(data.result=="0"){
				 		   		            o.innerHTML="重新发送(" + wait2 + ")";
				 		   		            wait2--;
				 		   		            setTimeout(function(){sendAuthCode(o)}, 1000);
					        	  		}else{
				    						$("#err_show4").html(data.reason);
					        	   			o.removeAttribute("disabled");         
									        o.innerHTML="获取验证码";
			       							$('#getcode').removeAttr("disabled");
		        							$("#getcode").removeClass("getcode");
					        	   		}
					        	   }, 
					        	   error: function (XMLHttpRequest, textStatus, errorThrown) { 
				        	   			$("#username_msg").html(errorThrown);
				    					return false;
					        	   } 
			        	  });
		    	}else{
		            o.innerHTML="重新发送(" + wait2 + ")";
		            wait2--;
		            setTimeout(function(){sendAuthCode(o)}, 1000);	
		    	}  
		}		 
	}

</script>

<script>
  $(".remove").click(function(){
    $(".bottom").css('display','none');
  })
  
  	//退出登录
	$('.downloadidClass').click(function(){
		var u = navigator.userAgent;
		var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
		var ua = navigator.userAgent.toLowerCase();  
	    if(ua.match(/MicroMessenger/i)=="micromessenger") {  
	       alert('请点击微信右上角按钮，然后在弹出的菜单中，点击在浏览器中打开，即可安装');
	    } else { 
			if (isiOS) {
				window.location.href = "https://itunes.apple.com/cn/app/id999649448";
			} else if (isAndroid) {
				window.location.href = "https://www.yueshanggroup.cn/download/yueshangdai${(placeChilder.random)!}.apk";
			} else {
				window.location.href = "https://www.yueshanggroup.cn/download.html";
			}
	    }  
	}); 
</script>
</html>
