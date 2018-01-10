<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>投资确认页</title>
	<#include "/content/common/meta.ftl">
	 <link rel="stylesheet" href="${base}/h5/assets/css/pop.css">
  <style>
  .cd-popup .am-tabs-default .am-tabs-nav>.am-active a{background-color:#fff;color:#f44246;}
  .cd-popup .am-tabs-default .am-tabs-nav a{
    background-color:#f65042;
    color:#fff;
    border:1px solid #fff;
    height: 30px;
    margin: 5px 0;
    line-height: 30px;
  }
  .cd-popup .am-list-news-default .am-list-date {color:#000;font-size:1em;}
  .cd-popup .am-list-news .am-list-item-hd{padding:0.2em;}
  .cd-popup .am-list-news-bd{padding:0 10px;}
  .cd-popup .am-list-news-hd h2{font-size:1.4rem;font-weight: normal;}
  .cd-popup .am-list>li{
    border:0;
    margin:1em 0;
    padding:0.5em;
    height:4em;
    border:1px solid #e6e6e6;
  }
  .cd-popup .am-tabs-bd{border:0;}
  .cd-popup .am-tabs-default .am-tabs-nav>.am-active a { 
      background-color: #fff;
      color:#000;
  }
  .cd-popup .am-list-news-default .am-list>li.bd-color{border: 1px solid #ff7f00;}
  </style>
</head>
    <body class="bgc5">
      <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40 am-no-layout">
        <div class="am-header-left am-header-nav">
          <a href="#left-link">
              <i class="am-icon-angle-left am-icon-fw" style="font-size:1.5em;"></i>
          </a>
        </div>
        <h1 class="am-header-title">
            投资确认页
        </h1>
        <div class="am-header-right am-header-nav"></div>
    </header>
    <!-- header end  --> 
    <div data-am-widget="list_news" class="am-list-news am-list-news-default" style="margin:0;">
      <div class="am-u-md-8 am-u-sm-centered" >
        <form name="investForm" id="investForm" class="am-form"  method="post">
        <input type="hidden" name="awardScale" id="awardScale" value="${bean.awardScale}"/>
         <input type="hidden" name="hongbaoArray" id="hongbaoArray" value=""/>
          <input type="hidden" name="id" id="id" value="${borrow.id}"/>
          <input type="hidden" name="clientType" id="clientType" value="3"/>
          <fieldset class="am-form-set">
            <div>
              <ul class="color4" style="overflow:hidden;margin:0.5em 0;">
                <li class="fl">起购金额：<span class="color">${(borrow.lowestAccount)!}</span>元</li>
                <li class="fr">剩余可购：<span class="color">${(borrow.balance)!}</span>元</li>
              </ul>
              <ul class="color4" style="overflow:hidden;margin:0.5em 0;">
                <li class="fr f75 color1" style="background:#6fbbf5;padding:0.2em 0.5em;border-radius:5px;">* 最多使用0元</li>
              </ul>
            </div>    
            <div class="am-form-group  am-form-icon">
               <input type="text" name="tenderMoney" id="tenderMoney" class="am-form-field tar" onkeyup="myfun();"  style="padding-right:2em!important;" autocomplete="off"  placeholder="请输入投资金额">
               <span style="position:absolute;left:0.5em;top:0.5em;">你需申购</span>
               <span style="position:absolute;right:0.5em;top:0.5em;">元</span>
            </div>
            <div>
              <ul class="color4" style="overflow:hidden;margin:0.5em 0;">
                <li class="fl">红包抵扣现金</li>
                <li class="fr f75 color1" style="background:#6fbbf5;padding:0.2em 0.5em;border-radius:5px;">* 最多使用<span id="hong"> </span>元</li>
              </ul>
            </div>
            <ul class="am-list am-list-static am-list-border bgc2 color3" style="margin-top:20px;padding:0 0.5em;">
              <li class="cd-popup-trigger" style="border:0;border-bottom: 1px solid #dedede;margin-bottom:0;">
                		使用红包
                <div style="float:right;"><span id="lasthong">0</span>元 <i class="am-icon-angle-right am-icon-fw color5"></i></div>
              </li>
              <li class="" style="border:0;">
                可用余额需支付
                <span style="float:right;"><span id="zhimoney"> </span>元</span>
              </li>
            </ul>  
            <div class="am-form-group  am-form-icon" style="margin-bottom:1em;">
                <input type="password" name="safepwd" id="safepwd" class="am-form-field" style="padding-left:2em!important;" placeholder="请输入交易密码">
                <i class="am-icon-lock f12 color" style="position:absolute;left:0.5em;top:1em;  z-index: 0;"></i>
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
           <button type="button" id="invest_button" class="am-btn am-btn-block am-radius color1 bgc">确认投资</button>
            <p style="width:70%;margin:1em auto;"><i class="am-icon-shield" style="margin-right:0.5em;color:#6fbbf5;"></i>本项目到期本金及收益自动归还至您的账户余额</p>
          </fieldset>     
        </form>
      </div>
    </div>
    <!-- cd-popup -->
  <div class="cd-popup" role="alert">
    <div class="cd-popup-container">
      <div data-am-widget="list_news" class="am-list-news am-list-news-default" style="margin:0;">
        <div class="am-list-news-bd" >
            <ul class="am-list" style="height:10em;overflow-y:scroll;">
            	<#list hbItemList as h>
                <li class="am-g am-list-item-dated" style="position: relative;">
                   <div class="tubiao" style="width:2.2em;height:3.2em;background:url(/img/a0.png) no-repeat;background-size:contain;position: absolute;top:0;right:0;"></div>
                    <input type="hidden" name="hid" id="hid" value="${h.id}"/>
                    <div class="fl" style="width:30%;height:3em;line-height:4.5em;padding-left:0.6em;background:url(/img/hb.png) no-repeat;background-size:contain;color:#fffba5;"><span id="hmoney">${(h.money)!}</span>元</div>
                    <div class="fl color4" style="width:70%;height:3em;">
                        <p class="f12" style="line-height:1.5em;">${h.sourceString}</p>
                        <p class="color5">${(h.endTime?string("yyyy-MM-dd"))!}到期</p>
                    </div>
                </li>
               </#list>
   
            </ul>
            <button  class="am-btn am-btn-block am-radius color1 bgc confirmHb">完成</button>
        </div>
    </div>
    </div> <!-- cd-popup-container -->
  </div> <!-- cd-popup -->
    <script>
      $('.cd-popup-trigger').on('click', function(event){
        event.preventDefault();
        $('.cd-popup').addClass('is-visible');
      });
     $(".cd-popup .am-list li").click(function(event) {
     	var $tenderMoney = $("#tenderMoney").val();
     	if($tenderMoney==""||$tenderMoney.lenght<1){
				$(".am-modal-bd").html("请输入金额");
				$('#my-alert').modal({
						onConfirm: function(options) {
							event.preventDefault();
          					$('.cd-popup').removeClass('is-visible');
						}
					});
				 
		}else{
         if($(this).hasClass('bd-color')){
            $(this).removeClass('bd-color');
            var hba = $("#hongbaoArray").val();
            $("#lasthong").html($("#lasthong").html()-$(this).children().children("#hmoney").html());
             $("#zhimoney").html( parseInt($("#tenderMoney").val())-parseInt($("#lasthong").html()));
            var strs= new Array(); //定义一数组 

			 strs=hba.split(","); //字符分割 
			
            var ary= $(this).children("#hid").val();
 			strs.splice($.inArray(ary,strs),1);
 			var str;
			for( i=0;i<strs.length ;i++ ){
				if(i==0){
					str=strs[i];
				}else{
					str = str+','+strs[i];
				}
			}
			$("#hongbaoArray").val(str)
         }else{

           
            var hba = $("#hongbaoArray").val();
            if(hba==""||hba.lenght<1){
            	 $("#hongbaoArray").val($(this).children("#hid").val());
            }else{
            	$("#hongbaoArray").val(hba+','+$(this).children("#hid").val());
            }
			
			if((parseInt($("#lasthong").html())+parseInt($(this).children().children("#hmoney").html()))>parseInt($("#hong").html())){
            	$(".am-modal-bd").html("选择红包大于最多使用金额");
				$('#my-alert').modal();
            }else{
             	 $(this).addClass('bd-color');
           		 $("#lasthong").html(parseInt($("#lasthong").html())+parseInt($(this).children().children("#hmoney").html()));
           		 $("#zhimoney").html( parseInt($("#tenderMoney").val())-parseInt($("#lasthong").html()));
           } 
         }
		}
       });

      //close popup
      $('.confirmHb').on('click', function(event){
          event.preventDefault();
          $('.cd-popup').removeClass('is-visible');
      });
      
      
  function myfun(){
  	var $money = $("#tenderMoney").val();
  	var $awardScale = $("#awardScale").val();
  	$("#zhimoney").html( $money);
  	$("#hong").html( ($("#awardScale").val()*$("#tenderMoney").val())/100);
  	
  }
     $(function(){
			var $investForm = $("#investForm");
			var $tenderMoney = $("#tenderMoney").val();
			var $safepwd = $("#safepwd").val();
			$("#invest_button").click(function(){
				
					$.ajax({
							url: "${base}/investDo.do",
							data: $investForm.serialize(),
							type: "POST",
							dataType: "json",
							cache: false,
							beforeSend: function() {
								$investForm.find("submit").attr("disabled", true);
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
			});
		});
    </script> 
    </body>
</html>
