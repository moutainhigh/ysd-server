<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-充值结果信息</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
	<link rel="stylesheet" href="${base}/css/common.css">
<style>
	body{ margin:0 ; padding:0;font-family:"微软雅黑";}
	ul,li{ list-style:none; margin:0 ; padding:0;}
	#header { background: #fff;}
	.success, .error, .progress {display: none}
</style>
 <script>
		function redirectUrl() {
			<#if msgUrl!=''>
				window.location.href = "${msgUrl}"
			<#else>
				window.history.back();
			</#if>
		}
</script> 
</head>
<body>
 <#include "/content/common/header.ftl">
<link rel="stylesheet" href="${base}/css/payment.css">

<input id="book" type="text" value="${tradeNo}" style="display: none"/>

<script>
	
	$.ajax({
				type:"POST",
				url:"${base}/userCenter/payResultJson.do",
				data: {'orderNo': $("#book").val()},
				dataType: "json",
				success: function (data) {
	        		   if(data.rcd == 'R0001'){       	<!-- 成功 -->
	        			   $(".success").show();
	        		   }
	        		   else if(data.rcd == 'R0002'){    <!-- 进行中 -->
	        		       $(".progress").show();
	        		       var my_msg;
	        		       var start = new Date().getSeconds();
	        		       var end;
	        		       var timer = setInterval(function() {
	        		       
	        		       		$.ajax({
	        		       			type:"POST",
									url:"${base}/userCenter/payResultJson.do",
									data: {'orderNo': $("#book").val()},
									dataType: "json",
									async:false,
									success: function(msg) {
										my_msg = msg.rcd;
									}
	        		       		});
	        		       		end = new Date().getSeconds();
	        		       		var dura = end - start;
	        		       		if((dura>20) || ((dura<0)&&(dura>-20))) {
	        		       			clearInterval(timer);
	        		       			$(".Terror1").show();
	        		       		}
	        		       		
	        		       		if(my_msg != "R0002") {
	        		       			clearInterval(timer);
	        		       			if(my_msg == "R0001") {
	        		       				 $(".progress").hide();
	        		       				 $(".error").hide();
	        		       				 $(".success").show();
	        		       			} else if(my_msg == "R0003") {
	        		       				$(".success").hide();
	        		       				 $(".progress").hide();
	        		       				  $(".error").show();
	        		       			}
	        		       		}
	        		       }, 4000);
	        		       
	        		 } else {  							<!-- 错误-->
	    		 	  	$(".error").show();
	    		   	}
	        	}
			});
			
</script>


<!-- 成功 -->
<div class="recharge success">
  <div class='rechargeCont findCont'>
      <p class='title'>恭喜你，充值成功</p>
      <p>您已经成功充值<span>${(userAccountRecharge.money)!}</span>元</p>
      <p>如遇问题请拨打乐商贷客服电话:<span>400-057-7820</span>，服务时间：周一至周五<span>09:00-20:00</span></p>
      <div class='rechargebtn'>
      	<a href='${base}/userCenter/index.do' class='red'>返回账户</a>
      	<a href='${base}/payment/rechargeTo.do' class='blue'>继续充值</a>
      	<a href='${base}/borrow/listNew.do' class='yellow'>立即投资</a>
      </div>
  </div>
</div>

<!-- 处理中 -->
<div class="progress">
  <div class='findCont'>
      <p class='Terror'>系统正在处理中···</p><!-- ${msg!'页面不存在'} -->
      <p class='Terror1' style="display: none">支付平台处理中，请耐心等待···</p>
      <p>如遇问题请拨打乐商贷客服电话:<span>400-057-7820</span>，服务时间：周一至周五<span>09:00-17:30</span></p>
      <div>
      	<a href="javascript:void(0);" onclick="redirectUrl();" class='red'>返回</a>
      </div>
  </div>
</div>

<!-- 失败 -->
<div class="find error">
  <div class='findCont'>
      <img src='${base}/img/payment_error.png' />
      <p class='Terror'>${msg!'页面不存在'}</p><!-- ${msg!'页面不存在'} -->
      <p>如遇问题请拨打乐商贷客服电话:<span>400-057-7820</span>，服务时间：周一至周五<span>09:00-17:30</span></p>
      <div>
      	<a href="javascript:void(0);" onclick="redirectUrl();" class='red'>返回</a>
      </div>
  </div>
</div>


<#include "/content/common/footer.ftl">
<script src="${base}/js/common.js"></script>
</body>
</html>