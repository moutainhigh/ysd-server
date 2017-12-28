<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>自动投资</title>
 <#include "/content/common/meta.ftl"> 
<style>
  	.am-form-group {margin-bottom: 1rem;}
  	.am-form .am-form-group input{border: 1px solid #e3e3e3;}
    #toggle{font-size: 2.2em;}
    .am-form input.jine{height:28px;line-height: 28px;text-align: right;padding: 0;  border: 0;}
  </style>
</head>
    <body class="bgc2" style="height:100%;overflow:hidden;">
      <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40">
	      <div class="am-header-left am-header-nav">
	      	<a href="#right-link">
	          	  <i class="am-icon-angle-left am-icon-fw" style="font-size:1.5em;"></i>
	          </a>
	      </div>
	      
	      	      <h1 class="am-header-title">
	          自动投资
	      </h1>
	      <div class="am-header-right am-header-nav"></div>
	    </header><!-- header end-->
   	  <div style="padding:1em;">
   	  	<div data-am-widget="list_news" class="am-list-news am-list-news-default am-no-layout" style="margin:0;">
	        <div class="am-list-news-hd am-cf" style="padding:0;">
	            <h2 style="font-size:1em;">自动投资状态</h2>
	            <span class="am-list-news-more am-fr"><i id="toggle" class="am-icon-toggle-off color"></i></span>
	        </div>
          <form name="autoForm" id="autoForm" class="am-form" style="margin-top:1.5em;" method="post">
           <input type="hidden" name="user.autoTenderRule" value="1"/>
      		<input type="hidden" name="userAuto.autoTenderStatus" id="autoTenderStatus" value="${userAuto.autoTenderStatus}"/>
            <fieldset class="am-form-set">    
              <ul style="overflow:hidden;  border: 1px solid #e3e3e3;padding:0.4em 0.5em;margin-bottom:0.5em;">
                <li class="fl">每次最高投资金额</li>
                <li class="color fr"><input type="text" name="userAuto.autoTenderMoneyTop" id="autoTenderMoneyTop" value="${(userAuto.autoTenderMoneyTop)!}" class="jine color"></li>
              </ul>
              <div class="am-form-group  am-form-icon" style="margin-bottom:1em;">
                <input type="password"  name="user.payPassword" id="payPassword" class="am-form-field" style="padding-left:2em!important;" placeholder="请输入交易密码">
                <i class="am-icon-lock f12 color" style="position:absolute;left:0.5em;top:1em;"></i>
              </div>
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
				
              <button type="button" id="auto_button" class="am-btn am-btn-block am-radius color1 bgc">保存设置</button>
            </fieldset>     
          </form>
	    </div>
   	  </div>
	  <script>
	      $(function(){
       $("#toggle").click(function(event) {
         if($(this).hasClass('am-icon-toggle-off')){
            $(this).removeClass('am-icon-toggle-off').addClass('am-icon-toggle-on');
            $('#autoTenderMoneyTop').attr("disabled",false);
         }
         else if($(this).hasClass('am-icon-toggle-on')){
            $(this).removeClass('am-icon-toggle-on').addClass('am-icon-toggle-off');
             $('#autoTenderMoneyTop').attr("disabled",true);
         }
       });
      })
      
      $(function(){

			$("#auto_button").click(function(){
				var $autoForm = $("#autoForm");
				var $autoTenderMoneyTop = $("#autoTenderMoneyTop").val();
				var $payPassword = $("#payPassword").val();
				if($autoTenderMoneyTop==""||$autoTenderMoneyTop.lenght<1){
					$(".am-modal-bd").html("请输入金额");
					$('#my-alert').modal();
				}else if($payPassword==""||$payPassword.lenght<1){
					$(".am-modal-bd").html("请输入密码");
					$('#my-alert').modal();
				}else{
				
					$.ajax({
							url: "${base}/zdtzSave.do",
							data: $autoForm.serialize(),
							type: "POST",
							dataType: "json",
							cache: false,
							beforeSend: function() {
								$autoForm.find("submit").attr("disabled", true);
							},
							success: function(data) {
								if(data.status=="success"){
									$(".am-modal-bd").html("保存成功");
									$('#my-alert').modal({
										onConfirm: function(options) {
											window.location.href = "${base}/userCenter/index.do";
										}
									});
								}else{
									$(".am-modal-bd").html(data.message);
									$('#my-alert').modal();
								}
							}
						});
				}
			});
		});
	  </script>
    </body>
</html>
