<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-我要充值</title>
	<#include "/content/common/meta.ftl">
	<style>
  input[type='radio'].radio {opacity:0; display:none; height:20px; }
  label.radio{width:152px; height:44px; position:absolute; left:0; top:0; z-index:999;}
  input[type='radio'].radio:checked + .radio{background:url(/img/a26.png) right top no-repeat;} 
</style>
</head>
<body>
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
