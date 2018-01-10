<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <title>${borrowBean.name}</title>
 <#include "/content/common/meta.ftl">
  <script src="/h5/mobile_js/qmd.js"></script>
   <style>
  	.am-form-group {margin-bottom: 1rem;}
  	.am-form .am-form-group input{border: 1px solid #e3e3e3;}
    .plus{  background: url(../mobile_img/10.png) no-repeat center center;display: inline-block;background-size: 100%;padding: 0.1em 0.7em;color: #ff7f00;  vertical-align: top;margin-top: 1.2em;}
</style>
</head>
<body class="bgc5">
    <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40">
      <div class="am-header-left am-header-nav">
          <a href="#left-link" onclick="window.history.back(); return false;" class="">
             <i class="am-icon-angle-left am-icon-fw" style="font-size:1.5em;"></i>
          </a>
      </div>
      <h1 class="am-header-title">
       ${borrowBean.name}
      </h1>
      <div class="am-header-right am-header-nav">
          <a href="#right-link" class="">
          	<i class="am-header-icon am-icon-share" style="font-size:1.1em;"></i>
          </a>
      </div>
    </header><!-- header end-->
	<div class="bgc tac" style="padding:2em 0;color:#fff;">
	 	<p>年化收益</p>
	 		 	<p class=""><span class="fwb f30">${borrowBean.baseApr}</span>%
	 		 	<#if borrowBean.awardApr gt 0><span class="plus">+${borrowBean.awardApr}%</span></#if></p>
	 	<p>期限 <span class="f11" style="color:#f8eced;">${borrowBean.timeLimit}</span> 天&nbsp;&nbsp;|&nbsp;&nbsp; <span class="f11" style="color:#f8eced;">${(borrowBean.lowestAccount)!}</span> 元起购</p>
	 	<div style="width:86%;height:2px;background:#fff;margin:0.8em auto;position: relative;">
	 		<div style="width:${borrowBean.showSchedule}%;height:6px;background:#f6da6b;position: absolute;left:0;top:-2px;"></div>
	 	</div>
	 	<p>总金额 <span class="f11" style="color:#f8eced;">${borrowBean.account}</span> 元&nbsp;&nbsp;&nbsp;剩余可投 <span class="f11" style="color:#f8eced;">${(borrowBean.balance)!}</span> 元</p>
	</div>
	<div class="bgc2" style="border:1px solid #ccc;margin:1em;line-height:2.5em;padding-left:1em;">
		<p>计息方式：满标审核当天计息</p>
	<#--<p>资金保障：${(borrowBean.capitalEnsure)!}</p>-->	
		<p>还款方式：${(borrowBean.styleName)!}</p>
		<p>发布时间：${(borrowBean.verifyTime?string("yyyy-MM-dd HH:mm"))!}</p>
	</div>
  <ul class="am-list am-list-static am-list-border bgc2 color6" style="margin:1em;">
      <li class="" style="">
       <a href="${base}/repaymentInfo/detail.do?id=${borrowBean.id}">
        <i class="am-icon-file-text am-icon-fw" style="color: #f6ae54;"></i> 项目基本信息
        <span style="float:right;"><i class="am-icon-angle-right am-icon-fw color5"></i></span>
        </a>
      </li>
      <li class="">
      <a href="${base}/borrowPic/detail.do?id=${borrowBean.id}">
        <i class="am-icon-photo am-icon-fw" style="color: #f24949;"></i> 项目材料公示
        <span style="float:right;"><i class="am-icon-angle-right am-icon-fw color5"></i></span>
        </a>
      </li>
      <li class="">
      <a href="#" onclick="gotonext(${borrowBean.id})">
        <i class="am-icon-list am-icon-fw" style="color: #016cbc;"></i> 投资记录
        <span style="float:right;"><i class="am-icon-angle-right am-icon-fw color5"></i></span>
       </a>
      </li>
    </ul>
    	<!-- 弹框开始 -->
					<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
					  <div class="am-modal-dialog">
					    <div class="am-modal-bd"></div>
					    <div class="am-modal-footer">
					      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
					    </div>
					  </div>
					</div>
				<!-- 弹框结束 -->
    <input type="hidden" id="bId" name="bId" value="${borrowBean.id}"/>
     <#if loginUser>
    	<button type="button" id="borrow_button"class="am-btn am-btn-block am-radius color1 bgc" style="width:80%;margin:0 auto;">立即投资</button> 
    <#else>
    	<a href="${base}/login.do"class="am-btn am-btn-block am-radius color1 bgc" style="width:80%;margin:0 auto;">立即登录</button> 
    </#if>

</body>
 <script type="text/javascript">
		$(function(){
			$("#borrow_button").click(function(){
				var bid = $("#bId").val();
					$.ajax({
							url: "${base}/checkMoney.do",
							type: "POST",
							dataType: "json",
							cache: false,
							
							success: function(data) {
								if(data.status=="success"){
									
										window.location.href = "${base}/poputInvest/detail.do?id="+bid;
								}else{
									$(".am-modal-bd").html(data.message);
									$('#my-alert').modal({
										onConfirm: function(options) {
											window.location.href = "${base}/payment/rechargeTo.do";
										}
									});
								}
							}
						});
					
			});
		});
		
	function gotonext(id){
		window.location =qmd.base+'/content/h5borrow/touList.html?'+id;
	}
	</script>
</html>
