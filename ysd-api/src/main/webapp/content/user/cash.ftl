<!DOCTYPE html>
<html>
<head>
  <title>${Application ["qmd.setting.name"]}-我的账户-我要提现</title>
	<#include "/content/common/meta.ftl">
	<script src="${base}/static/js/borrow/cash.js"></script>
<script type="text/javascript">
function checkimg(){
	var $code_img = $("#code_img");
	$code_img.attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
	}
	
	
	function verifyCode(){
		$("#vCode").html("<a id='vCodeA' title='点击更换' onclick='verifyCodeLink();'><img id = 'code_img' src='' /></a>");
		$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		$("#vCode").attr("style", "");
		$("#mycode").select();
	}
	
	function verifyCodeLink(){
		$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
	}
</script>
 </head>

<body onload="checkimg()">
<!-- header -->

<#include "/content/common/header.ftl">

<div class="content">
  <div style="width:955px; margin:0 auto; padding-bottom:20px;">
	<#include "/content/common/user_center_header.ftl">
 <div style="width:955px; float:left; background:#fff; clear:both;">
	<#include "/content/common/user_center_left.ftl">

	<#-- 		右边内容块 开始 				-->

	<div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
            <a href="${base}/userCenter/index.do" style="color:#646464;font-family:'宋体';">我的账户</a>
            <a style="color:#646464;font-family:'宋体'; padding:0px 4px;">></a>
            <a style="color:#646464;font-family:'宋体';">我的提现</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            <a href="javascrip:void(0);" class="hr hre">我要提现</a>
            <a href="${base}/userCenter/cashList.do" class="hr">提现记录</a>  
          </div>
           <div style="height:48px;line-height:23px;text-align:center;color:#595757;font-family:'宋体';background:#efebdf;margin-top:100px; margin-bottom:30px; clear:both;">
				温馨提示：银行账户的开户人姓名必须为"<font style="color:#f74405; font-weight:bold;">${user.showRealName}</font>"，否则将导致提现失败,
				用户每笔提现金额小于1000元的收取提现手续费<b style="color:#363636;">${fixedFee}</b>元，15天内充值的提现金额需额外收取此金额"<b style="color:#363636;">${feeValue}</b>"手续费。
			</div>  
			
			 <form id="getMoneyForm" method="post" action="cash.do" onsubmit="return check();">
				 <input type="hidden" name="user.id" value=${user.id} />
				 <input type="hidden" name="feeValue" id="feeValue" value=${feeValue} />
				 <input type="hidden" name="cashType" id="cashType" value=${cashType} />
				 <input type="hidden" name="fixedFee" id="fixedFee" value=${fixedFee} />
				 <input type="hidden" name="user.halfMonthyMoney" value=${user.showHalfMonthyMoney} />
				 <input type="hidden" id="free_cash" value="${ableRecharge}" />
				 <input type="hidden" name="user.ableMoney" id="ableMoney" value="${user.ableMoney}"/>
				 <input type="hidden" name="ableCashMoney" id="ableCashMoney" value="${ableCashMoney}"/>
				 <input type="hidden" name="user.typeId" id="typeId" value="${user.typeId}"/>
				 <input type="hidden" name="maxCashMoney" id="maxCashMoney" value="${maxCashMoney}"/>
				 <input type="hidden" id="userCashChargeMoney" value="${userCashChargeMoney}"/>
            <table width="100%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">开户名：</td>
                <td><font style="color:#646464;font-family:'宋体';">${user.showRealName}</font></td>
              </tr>
              <tr height="10"><td></td><td></td></tr>
              <tr>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" valign="top">开户银行：</td>
                <td style="color:#646464;font-family:'宋体';">如果您还没设置银行帐号，请点击<a style="color:#f74405;font-family:'宋体';" href="${base}/userCenter/toBankInput.do">[设置银行账户]</a>
				  <ul style="background:#efebdf;color:#646464;font-family:'宋体'; padding:10px 10px; margin-top:5px;">
                    <#list accountBanklist as accountBank>
						<li style="margin-top:5px;"><label><input type="radio" <#if accountBank_index==0>checked</#if> name="accountBank.id" id="id" value="${accountBank.id}"" >${substring(accountBank.name, 24, "...")}&nbsp;&nbsp;&nbsp;&nbsp;${accountBank.branch}&nbsp;&nbsp;&nbsp;&nbsp;****${accountBank.account?substring(4,accountBank.account?length-4)}****</label></li>
					</#list>
                   </ul>
				 </td>              
               </tr>
              <tr height="10"><td></td><td></td></tr>
              
              <tr>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">账户总额：</td>
                <td>
                  <span name="user.total" id="utotal" style="color:#646464;font-family:'宋体';"> ${user.total?string.currency}</span>元
                </td>
              </tr>
              <tr height="10"><td></td><td></td></tr>
              
              <tr>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">当日内充值金额：</td>
                <td>
                  <span name="oneDayRecharge"  id="oneDayRecharge" style="color:red;font-family:'宋体';">${oneDayRecharge?string.currency}</span>元
                </td>
              </tr>
              <tr height="10"><td></td><td></td></tr>
              <#if user.typeId==1>
              	  <tr>
	                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">可提现金额：</td>
	                <td>
	                  <span name="ableCashMoney"  id="ableCashMoney" value="${user.ableMoney}"  style="font-family:'宋体';">${user.ableMoney?string.currency}</span>元
	                </td>
	              </tr>
	              <tr height="10"><td></td><td></td></tr>
	         <#else>
				<#if cashType=="1">
					 <tr>
		                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">15天内充值金额：</td>
		                <td>
		                  <span name="user.halfMonthyMoney"  style="font-family:'宋体';">${user.halfMonthyMoney}元</span>
		                  <span name="ableCashMoney"  id="ableCashMoney" value="${ableCashMoney}"  style="font-family:'宋体';">可提现金额：${(ableCashMoney)?string.currency}元</span>
		                </td>
		              </tr>
		              <tr height="10"><td></td><td></td></tr>
				<#elseif cashType=="0">
              		<tr>
		                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">可提现金额：</td>
		                <td>
		                  <span name="ableCashMoney" id="ableCashMoney" value="${ableRecharge?string.currency}"  style="font-family:'宋体';">${user.ableMoney?string.currency}元</span>
		                </td>
		              </tr>
		              <tr height="10"><td></td><td></td></tr>
		        </#if>
		        <#if (userCashChargeTimes>=10000)>  
		        	<tr>
		                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">固定手续费：</td>
		                <td>
		                  <span name="ableCashMoney" id="ableCashMoney" value="${ableRecharge?string.currency}"  style="font-family:'宋体';">${(userCashChargeMoney)?string.currency}元</span>
						  <span>本月提现${userCashChargeTimes}次，本次需要收取单笔固定手续费</span>
		                </td>
		              </tr>
		              <tr height="10"><td></td><td></td></tr>  
		          </#if>
			</#if>  
		    <#if accountBanklist.size()== 0>
		    <#else>
		           
              <tr>
                <td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">提现金额：</td>
                <td>
                  <input type="text"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" name="accountCash.total" id="total" onblur="commit(this);" onkeyup="value=value.replace(/[^0-9\.]/g,'')"  />  
                	 大于100元小于<span id="maxCashMoney">${maxCashMoney}</span>元<span id="real_money"></span>
                </td>
              </tr>
              <tr height="10"><td></td><td></td></tr>
              
              
              <tr>
                <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">安全密码：</td>
                <td>
                	<#if !user.payPassword>
						<span><strong > <a href="${base}/userCenter/toPayPassword.do" class="red">请点击设置安全密码</a></strong></span>
					<#else>
	                  <input type="password" name="user.payPassword" id="payPassword"   style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
	                  <a href="${base}/user/lostpass.do?p=1" style="color:#f74405;font-family:'宋体';">忘记密码？</a>
                   </#if>
               </td>
              </tr>
              
               <tr height="10"><td></td><td></td></tr>
              <tr>
                <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">验证码：</td>
                <td>
                  <input type="text" id="mycode" name="mycode" onfocus="verifyCode();" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
                 <span id="vCode" style=" visibility:hidden;"></span>
               </td>
               </tr>
          <tr height="80">
            <td></td>
            <td>
                <input type="submit" value="确定" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;">
            </td>
          </tr>
          </#if>
        </table>
   	</form>
    </div>
	<#-- 		右边内容块 结束 				-->
	</div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->

 <!--footer-->
<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
$(function(){
	$("#my_deposit").addClass("nsg nsg_a1");
});
</script>
</html>
