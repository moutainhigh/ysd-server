<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>我要提现</title>
	<#include "/content/common/meta.ftl">
 <style>
  .am-header-title li a{display: block;margin-top: 5px;height:30px;line-height:30px;border: 1px solid #fff;}
  .am-list-news-default .am-list-date {color:#000;font-size:1em;}
  .am-list-news .am-list-item-hd{padding:0.2em;}
  .am-list-news-bd{padding:0 10px;}
  .am-list-news-hd h2{font-size:1.4rem;font-weight: normal;}
  .am-list>li{border:0;border-bottom:1px solid #e1e0e0;margin-bottom:0px;padding:0.25em 0;}
  .am-tabs-bd{border:0;}
  </style>
</head>
 <body class="bgc5">
   	  <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40 am-no-layout">
          <div class="am-header-left am-header-nav">
            <a href="${base}/userCenter/index.do">
                  <i class="am-icon-angle-left am-icon-fw" style="font-size:1.5em;"></i>
              </a>
          </div>
          <h1 class="am-header-title">
              <ul style="overflow:hidden;">
                <li class="fl w50"><a href="${base}/payment/rechargeTo.do" style="border-radius:5px 0 0 5px;">充值</a></li>
                <li class="fl w50"><a href="${base}/cash/cashTo.do" class="color bgc2" style="border-radius:0 5px 5px 0;">提现</a></li>
              </ul>
          </h1>
          <div class="am-header-right am-header-nav">
           <a href="${base}/content/h5user/cashList.html">提现记录</a>
          </div>
      </header>
            <div class="bgc5" >
              <div class="am-list-news am-list-news-default" style="margin:0;">
                <div class="color1 bgc tac" style="padding:2em 0 1em;">
                   <p>可用余额（元）</p>
                   <p class="fwb f24">${ucb.ableMoney}</p>
                </div>
                <div class="am-u-md-8 am-u-sm-centered" style="margin-top:1em;">
                  <form name="cashForm" id="cashForm" class="am-form"  method="post">
                   <form id="rechargeForm" method="post"  class="am-form" action="/payment/rechargeSave.do" target="_blank">
                    <fieldset class="am-form-set">    
                      <div class="am-form-group  am-form-icon">
                         <input type="text" id="cashMoney" name ="cashMoney" class="am-form-field" style="padding-left:2em!important;"  placeholder="请输入转出金额">
                         <i class="am-icon-usd f12" style="color:#ffbe61;position:absolute;left:0.5em;top:1em;"></i>
                         <span style="position:absolute;right:0.5em;top:0.5em;"></span>
                      </div>
                      <#--<div class="am-form-group  am-form-icon">-->
                        <#--<input type="password" name="safepwd" id="safepwd" class="am-form-field" style="padding-left:2em!important;" placeholder="请输入交易密码">-->
                        <#--<i class="am-icon-unlock-alt f12" style="color:#70ebd2;position:absolute;left:0.5em;top:1em;"></i>-->
                        <#--<a class="color6" href="${base}/userCenter/payPwdLostTo.do" style="position:absolute;right:0.5em;top:0.5em;">忘记?</a>-->
                      <#--</div>-->
                      <div class="am-form-group color3" style="font-size:0.85em;">
                        <p>提现至：&nbsp;&nbsp;<i class="am-icon-lock f12"></i>&nbsp;&nbsp;${ucb.bankName}(尾号${cardNo})</p>
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
				
                     	<button type="button" id="cash_button" class="am-btn am-btn-block am-radius color1 bgc">确认提现</button>

                    </fieldset>     
                  </form>
                </div>
                <div class="color8 bgc2" style="padding:1em 1em 2em;">
                    <h3><i class="am-icon-credit-card-alt f12"></i> 余额提现</h3>
                    <p>每月前5笔提现免手续费，超过每笔收2元手续费，本月您已累计提现${ucb.userCashChargeTimes!'0'}笔</p>
                </div>
              </div>
           </div>
        </div>

	 <script type="text/javascript">
		$(function(){
			var $cashForm = $("#cashForm");
	       var str="";
			$("#cash_button").click(function(){
				
				
					$.ajax({
							url: "${base}/cash/cashSave.do",
							data: $cashForm.serialize(),
							type: "POST",
							dataType: "json",
							cache: false,
							beforeSend: function() {
								$cashForm.find("submit").attr("disabled", true);
							},
							success: function(data) {
							    //alert(data.status);
								if(data.status=="success"){
									/*$(".am-modal-bd").html(data.message);
									$('#my-alert').modal({
										onConfirm: function(options) {
											window.location.href = "$/userCenter/index.do";
										}
									});*/
                                    window.location.href = data.message;
								}else{
									$(".am-modal-bd").html(data.message);
									$('#my-alert').modal();
								}
							}
						});
					
			});
		});
</script>
  
</body>
</html>
