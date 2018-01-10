<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-我的账户-我要充值</title>
	<#include "/content/common/meta.ftl">
 <style>
  
  .am-list-news-default .am-list-date {color:#000;font-size:1em;}
  .am-list-news .am-list-item-hd{padding:0.2em;}
  .am-list-news-bd{padding:0 10px;}
  .am-list-news-hd h2{font-size:1.4rem;font-weight: normal;}
  .am-list>li{border:0;border-bottom:1px solid #e1e0e0;margin-bottom:0px;padding:0.25em 0;}
  .am-tabs-bd{border:0;}
  </style>
</head>
 <body class="bgc5 recharge">
   	  <div data-am-widget="tabs" class="am-tabs am-tabs-default bgc" style="margin:0;">
        <div style="position:absolute;"><i class="am-icon-angle-left am-icon-fw h40 lh40 color1" style="font-size:1.5em;float:left;"></i></div>
        <ul class="am-tabs-nav am-cf h40 lh40" style="width:35%;margin:0 auto;">
            <li class="am-active"><a style="border-radius:5px 0 0 5px;" href="[data-tab-panel-0]">充值</a></li>
            <li class=""><a style="border-radius:0 5px 5px 0;"  href="[data-tab-panel-2]">提现</a></li>
        </ul>
        <div class="color1 f75" style="position:absolute;top:1em;right:0.5em;">充值记录</div>
        <div class="am-tabs-bd" style="bordr:0;">
            <div data-tab-panel-0 class="am-tab-panel am-active bgc5" style="padding:0;">
              <div data-am-widget="list_news" class="am-list-news am-list-news-default" style="margin:0;">
                <div class="color1 bgc tac" style="padding:2em 0 1em;">
                   <p>可用余额（元）</p>
                   <p class="fwb f24">${urb.ableMoney}</p>
                </div>
                <div class="am-u-md-8 am-u-sm-centered" style="margin-top:1em;">
                   <form id="rechargeForm" method="post"  class="am-form" action="/payment/rechargeSave.do" target="_blank">
                    <fieldset class="am-form-set">    
                      <div class="am-form-group  am-form-icon">
                         <input type="text" name="userAccountRecharge.money" id="money" onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'');"  class="am-form-field" style="padding-left:2em!important;" placeholder="请输入充值金额">
                         <i class="am-icon-usd f12" style="color:#ffbe61;position:absolute;left:0.5em;top:1em;"></i>
                         <span style="position:absolute;right:0.5em;top:0.5em;">元</span>
                      </div>
                      <div class="am-form-group  am-form-icon">
                        <input type="text" name="safepwd" id="safepwd" class="am-form-field" style="padding-left:2em!important;" placeholder="请输入交易密码">
                        <i class="am-icon-unlock-alt f12" style="color:#70ebd2;position:absolute;left:0.5em;top:1em;"></i>
                        <a class="color6" style="position:absolute;right:0.5em;top:0.5em;">忘记</a>
                      </div>
                      <div class="am-form-group color3" style="font-size:0.85em;">
                        <p>支付方式:&nbsp;&nbsp;<i class="am-icon-lock f12"></i>&nbsp;&nbsp;${urb.bankName}(尾号${cardNo})</p>
                      </div>
                      <button type="submit" id="paymentRechargeButton" class="am-btn am-btn-block am-radius color1 bgc">确认充值</button>
                    </fieldset>     
                  </form>
                </div>
              </div>
            </div>
            <div data-tab-panel-2 class="am-tab-panel bgc5" style="padding:0;">
              <div data-am-widget="list_news" class="am-list-news am-list-news-default" style="margin:0;">
                <div class="color1 bgc tac" style="padding:2em 0 1em;">
                   <p>可用余额（元）</p>
                   <p class="fwb f24">${urb.ableMoney}</p>
                </div>
                <div class="am-u-md-8 am-u-sm-centered" style="margin-top:1em;">
                  <form class="am-form">
                    <fieldset class="am-form-set">    
                      <div class="am-form-group  am-form-icon">
                         <input type="text" id="doc-ipt-3-a" class="am-form-field" style="padding-left:2em!important;" placeholder="请输入充值金额">
                         <i class="am-icon-usd f12" style="color:#ffbe61;position:absolute;left:0.5em;top:1em;"></i>
                         <span style="position:absolute;right:0.5em;top:0.5em;">元</span>
                      </div>
                      <div class="am-form-group  am-form-icon">
                        <input type="text" id="doc-ipt-3-a" class="am-form-field" style="padding-left:2em!important;" placeholder="请输入交易密码">
                        <i class="am-icon-unlock-alt f12" style="color:#70ebd2;position:absolute;left:0.5em;top:1em;"></i>
                        <a class="color6" style="position:absolute;right:0.5em;top:0.5em;">忘记</a>
                      </div>
                      <div class="am-form-group color3" style="font-size:0.85em;">
                        <p>支付方式：&nbsp;&nbsp;<i class="am-icon-lock f12"></i>&nbsp;&nbsp;工商银行(尾号2137)</p>
                      </div>
                      <button type="submit" class="am-btn am-btn-block am-radius color1 bgc">确认充值</button>
                    </fieldset>     
                  </form>
                </div>
                <div class="color8 bgc2" style="padding:1em 1em 2em;">
                    <h3><i class="am-icon-credit-card-alt f12"></i> 余额提现</h3>
                    <p>每月前5笔提现免手续费，超过每笔收2元手续费，本月您已累计提现0笔</p>
                    <p>14点之前发起的提现当天到账；</p>
                    <p>14点之后发起的提现第二天到账；</p>
                </div>
              </div>
           </div>
        </div>
    </div>
    
    
 <!-- header -->
<#include "/content/common/header.ftl">


<#include "/content/common/user_center_header.ftl">

<div class="content">
  <div style="width:1200px; margin:0 auto; padding-bottom:60px; margin-top:20px;">
    <!--left-->
	<#include "/content/common/user_center_left.ftl">
    <!--right-->
   <div style="width:942px; float:right;">
      <div style="border:1px solid #e6e6e6; background:#fff; padding-left:40px;">
         <div class="bank_zh" style="margin-top: 30px; padding-bottom:113px;padding-left: 0;">
                 <div class="banks_add">
                 <form id="rechargeForm" method="post" action="/payment/submit.do" target="_blank">
                 	<input type="hidden" name="id" value="${loginUser.id}"/>
                 	
                  <table width="600" cellpadding="0" cellspacing="0" border="0">
                      <tbody>
                       <tr>
                         <td class="bank_td bank_td1" valign="top" style="width:70px;font-size:14px; color:#666; font-weight:bold; padding-right:5px; margin-top:15px;">充值方式：</td>

                         <td width="490">
                         <#list rechargeConfigList as rechargeConfig >
                         		
					                       <label style=" margin-right:25px;">
					                       	<input type="radio" name="payment" id="rechargeInterface" value="${rechargeConfig.id}"<#if rechargeConfig_index =0> checked </#if> />
					                       <span style=" display:inline-block; padding-left:10px;font-size:16px;">${rechargeConfig.name}</span></label>
	      <!--				      
					       <div style="width:152px; height:44px; float:left; position:relative; z-index:1; margin-right:10px; cursor:default;" class="user_zhifubank_s1">
                               <img src="<@imageUrlDecode imgurl="${rechargeConfig.logoPath}"; imgurl>${imgurl}</@imageUrlDecode>" width="152" height="44" />
                               <input type="radio" name="payment" id="rechargeInterface" value="${rechargeConfig.id}"<#if rechargeConfig_index =0> checked </#if> class="radio" />
                               <label for=${rechargeConfig.id} class="radio"></label>
                            </div>
                            -->
					      <!--
                         	<#if rechargeConfig_index ==0>
		                 		<input type="hidden" id="rechargeInterface" name="payment" value="${rechargeConfig.id}" />
		                 	</#if>
                           <div style="width:152px; height:44px; float:left; position:relative; z-index:1; margin-right:10px; cursor:default;" class="<#if rechargeConfig_index ==0>user_zhifubank_s2<#else>user_zhifubank_s1</#if>" data="${rechargeConfig.id}">
                             <img src="<@imageUrlDecode imgurl="${rechargeConfig.logoPath}"; imgurl>${imgurl}</@imageUrlDecode>" width="152" height="44" />
                             <div style="width:20px; height:20px; position:absolute; right:0; top:0; z-index:999;background:url(/img/a26.png) center center no-repeat;"></div>
                           </div>
                           -->
                         </#list>  
                           
                         </td>
                       </tr>
                       <tr height="20"><td></td><td></td></tr>
                       <tr>
                         <td class="bank_td bank_td1" style="margin-top:0;font-size:14px;">账户余额：</td>
                         <td>
                          <span style=" color:#fd7c1a; font-size:14px; font-weight:bold;">${ableMoney}</span>元
                         </td>
                       </tr>
                       <tr height="20"><td></td><td></td></tr>
                       <tr>
                         <td class="bank_td bank_td1" style="margin-top:10px; padding-top:0;font-size:14px;">充值金额：</td>
                         <td style="position:relative;width:490px;">
                            <span style="font-size:14px; position:absolute; left:7px; top:12px;">￥</span>
                          	<input type="text" name="userAccountRecharge.money" id="money" onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'');" maxlength="6" style="width:262px; height:44px; line-height:44px; padding-left:22px; border:1px solid #e6e6e6; border-radius:0; color:#333; font-size:16px;"  placeholder="输入充值金额">&nbsp;&nbsp;元
                         </td>
                       </tr>
                       <tr height="20"><td></td><td></td></tr>
                       <tr>
                         <td class="bank_td bank_td1" style="margin-top:10px; padding-top:0;font-size:14px;">验证码：</td>
                         <td style="position:relative;width:490px;">
                          <input type="text" style="width: 278px;height: 40px; padding-left: 6px;border: 1px solid #dcdcdc;margin-right:10px;" placeholder="请输入验证码" name="mycode" onfocus="verifyCode()" id="mycode"> <a style="position:absolute;left:201px;top:0;" onclick="verifyCodeLink();" title="点击更换"><img width="84" height="42" style="vertical-align:middle;" src="${base}/rand.do" id="code_img"></a>
                          
                         </td>
                       </tr>
                       <tr>
                         <td></td>
                         <td>
                           <input type="submit" id="paymentRechargeButton" class="btn_t1" value="充值"/>
                         </td>
                       </tr>
                     </tbody>
                   </table>
                   </form>
                 </div>
              </div>
      </div>
</div>
    <div style="width:100%;clear:both; height:1px;"></div>
  </div>
</div><!-- content end -->     

<#include "/content/common/footer.ftl">
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script>
	$("#header_wdzh").addClass("hq");<#-- header.ftl 选中样式 -->
	$("#cztx").addClass("click_current");<#-- user_center_left.ftl 选中样式 -->

	function verifyCode(){
		$("#vCode").html("<a id='vCodeA' title='点击更换' onclick='verifyCodeLink();'><img id = 'code_img' src='' width='80' height='44' style='cursor:pointer; margin-left:20px;' /></a>");
		$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		$("#mycode").select();
	}
	
	function verifyCodeLink(){
		$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
	}
	
	$(function(){
		$('.user_zhifubank_s1').click(function(){
			 $('.user_zhifubank_s1').removeClass('user_zhifubank_s2');
			 $(this).addClass('user_zhifubank_s2');
			 $('#rechargeInterface').val($(this).attr('data'));
		});
		
	});	
		$("#paymentRechargeButton").click(function(){
			var $rechargeForm = $("#rechargeForm");
			var $money = $("#money");
			var flag = false;
			
			if($money.val()=='' || $money.val().length<0 ){
				alertMessage('请输入正确的充值金额');
				return false;
			}
			$.ajax({
				url: "${base}/user/ajaxVerifyCode.do",
				data: {"mycode":$("#mycode").val()},
				type: "POST",
				dataType: "json",
				cache: false,
				success: function(data) {
					if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
						alertMessage('验证码输入错误');
						flag = false;
					}else if(data.status == 'error'){
						alertMessage(data.message);
						flag = false;
					}else{
						var testTime = new Date().getTime();
						KP.options.drag = true;
						KP.show("请在新打开的页面上完成充值", 524, 260);
						$(KP.options.content).load(qmd.base+"/payment/poputContent.do?testTime="+testTime);
					}
				}
			});
		
		});
	// 表单验证
	$("#rechargeForm").validate({
		errorClass:"alrm",
		rules: {
			
			"mycode":{
				required:true,
				remote:{
					type:"POST",
					url:"${base}/user/checkVerifyCode.do"
				}
			}
		},
		messages: {
			"mycode":{
				required:"验证码不能为空!",
				remote:"验证码错误"
			}
			
		},
		
		
		submitHandler: function(form) {				
			form.submit();
		}
	});		
</script>  
</body>
</html>
