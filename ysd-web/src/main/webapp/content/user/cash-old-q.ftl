<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-我的账户-我要提现</title>
	<#include "/content/common/meta.ftl">
	<script src="${base}/js/cash.js"></script>
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
      <div style="border:1px solid #e6e6e6; background:#fff; ">
         <div class="bank_zh" style="margin-top: 30px; padding-bottom:29px;padding-left: 30px;">
                 <div class="banks_add">
			 <form id="getMoneyForm" method="post" action="cash.do" onsubmit="return check();" autocomplete="off" >
				 <input type="hidden" name="ableCashMoney" id="ableCashMoney" value="${user.ableMoney}"/>
				 <input type="hidden" name="maxCashMoney" id="maxCashMoney" value="${maxCashMoney}"/>
				 <input type='hidden' id='init_value' value="180" />
                  <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tbody>
                       <tr>
                         <td class="bank_td bank_td1" valign="top" style="font-size:14px;">提现至银行卡：</td>
                         <td>
                             <div class="banks_k1">
                                 <div class="banks_bk banks_${accountBank.bankId}"></div>
                                 <div class="banks_haoma"><p>***************${accountBank.account?substring(accountBank.account?length-4,accountBank.account?length)}</p></div>
                                 <div class="banks_name"><a>${accountBank.branch} </a></div>
                        	 </div>
                         </td>
                       </tr>
                       <tr height="20"><td></td><td></td></tr>
                       <tr>
                         <td class="bank_td bank_td1" style="margin-top:0;font-size:14px;">可提现金额：</td>
                         <td>
                          <span style=" color:#fd7c1a; font-size:16px;">${user.ableMoney?string.currency}元</span>
                         </td>
                       </tr>
                  	 <tr height="20"><td></td><td></td></tr>
                       <#--<tr>
                         <td class="bank_td bank_td1" style="margin-top:0;font-size:14px;padding-top:10px;">手续费：</td>
                         <td>
                          <span style=" color:#fd7c1a; font-size:16px;width:305px; display:inline-block;padding-top:10px;">￥0.00元</span>
                          <span style=" color:#999; font-size:14px; display:inline-block; width:385px;">
                         			 用户每月可免费提现<b style="color:#363636;">${cashChargeTimes}</b>笔</br>超过次数收取每笔提现金额*"<b style="color:#363636;">${cashChargeMoney}</b>"及${fixedFee}元提现手续费。
                          </span>
                         
                         </td>
                       </tr>-->
                       <tr height="20"><td></td><td></td></tr>
                       <tr>
                         <td class="bank_td bank_td1" style="margin-top:10px; padding-top:10px;font-size:14px;">提现金额：</td>
                         <td style="position:relative;">
                         <span style="font-size:14px; position:absolute; left:10px; top:22px;">￥</span>
                          <input type="text" name="accountCash.total" id="total" minlength="3" maxlength="6" onkeyup="value=value.replace(/[^0-9\.]/g,'')"  AUTOCOMPLETE="off" style="font-size: 16px; width:272px; height:44px; line-height:44px; padding-left:22px; border:1px solid #e6e6e6; border-radius:0; color:#666;">
                          <span style=" color:#999; font-size:14px; display:inline-block; width:385px; position:relative; left:10px; top:10px;">提现金额必须在100元以上，当天14：00后提现隔天到账<br />周末及法定假日提现将在下一个工作日到账。</span>
                         </td>
                       </tr>
                       <tr height="20"><td></td><td></td></tr>
                       <tr>
		                <td style="width:115px; padding-right:20px; text-align:right;color:#666; font-size:14px;">验证码</td>
		                <td>
		                  <div style="position:relative; width:300px;"><input type="text" style="width:294px; height:44px; line-height:44px; padding-left:4px; border:1px solid #e6e6e6; border-radius:5px;" name="code" maxlength="6" id="phoneCode">
		                  <div style="width:120px; height:44px; border-radius:0 5px 5px 0; cursor:pointer; position:absolute; right:0; top:1px; color:#fff; text-align:center; font-size:14px; background:#006dc1; line-height:44px;" id="getcode">获取短信验证码</div>
		                </div>
		                </td>
		              </tr>
                       <tr>
                         <td></td>
                         <td>
                            <input type="submit" class="btn_t1" style="width:180px;" value="确定"/>
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
    <script type="text/javascript">
		$().ready( function() {
			$("#header_wdzh").addClass("hq");<#-- header.ftl 选中样式 -->
			$("#cztx").addClass("click_current");<#-- user_center_left.ftl 选中样式 -->
			
		});
		
		if($("#init_value").val() < 180){
			$("#init_value").val('180');
		}
		
		$('#getcode').click(function(){
			if($("#init_value").val()== 180){
				$.getJSON('${base}/sendPhoneCodeCash.do',function(data){
					if(typeof(data.result) == "undefined" || typeof(data.result) == "null" || data.result == null || data.result == ""){
		        		alertMessage("验证失败");
		        		return false;
		        	} else if (data.result=="0") {
	    				time = setInterval('auto_jump()',1000);   
		        	} else {
			        	alert(data.reason);
			        	return false;
		        	}
				});
			}
		});
		
		
	function auto_jump() {	
	    secs = $("#init_value").val() - 1;  
	    
	    $("#init_value").val(secs);  
	    
	    if(secs < 0){
	     	$("#init_value").val('180');
	    	return false;
	    }  
	    
	    if(secs == 0){
	     	clearInterval(time);
	        $("#getcode").html('重新发送');
	    	document.getElementById('getcode').style.backgroundColor='#006dc1';
	    	document.getElementById('getcode').style.color='#fff';
	    }else{
	    	document.getElementById('getcode').style.backgroundColor='#f2f2f2';
	    	document.getElementById('getcode').style.color='#666666';
	        $("#getcode").html(secs+'秒后重新获取');  
	    }
	}
		
		
		
		
		
		
		
		
		
	</script>
</body>
</html>
