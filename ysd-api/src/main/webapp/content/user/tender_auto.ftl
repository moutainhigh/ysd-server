<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <title>${Application ["qmd.setting.name"]}-我的账户-自动投标设置</title>
	<#include "/content/common/meta.ftl">
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
	
function btnSubmit(st) {

	var reg = /^\d+(\.\d+)?$/;
	if(!reg.test($("#autoTenderMoneyTop").val())){
		alert('请输入正确的单次投标限额！');
		return false; 
	}
	if(!reg.test($("#autoTenderMoneyLeave").val())){
		alert('请输入正确的保留余额！');
		return false; 
	}
	if(!reg.test($("#autoTenderRateBegin").val())){
		alert('请输入正确的年化利息！');
		return false; 
	}
	if(!reg.test($("#autoTenderRateEnd").val())){
		alert('请输入正确的年化利息！');
		return false; 
	}
	if (checkRate($("#autoTenderRateBegin").val(),$("#autoTenderRateEnd").val())) {
		alert('请输入正确的年化利息！');
		return false; 
	}
	
	if(!reg.test($("#autoTenderRewardBegin").val())){
		alert('请输入正确的奖励范围！');
		return false; 
	}
	if(!reg.test($("#autoTenderRewardEnd").val())){
		alert('请输入正确的奖励范围！');
		return false; 
	}
	if (checkRate($("#autoTenderRewardBegin").val(),$("#autoTenderRewardEnd").val())) {
		alert('请输入正确的奖励范围！');
		return false; 
	}
	
	if ($("#payPassword").val()=='') {
		alert('请输入安全密码！');
		return false; 
	}
	if ($("#mycode").val()=='') {
		alert('请输入校验码！');
		return false; 
	}
	

$("#autoTenderStatus").val(st);

$("#tenderAutoForm").submit();

}

function checkRate(v1,v2) {
	var f1 = parseFloat(v1);
	var f2 = parseFloat(v2);
	if (f1 > 100 || f2 > 100) {
		return true;
	}
	if (f1 > f2) {
		return true;
	}
	
	return false;
	
}

function clickRuleAll() {
	$("#autoTenderMoneyTop").val(0);
	$("#autoTenderMoneyLeave").val(0);
}
</script>
 </head>

 <body>
<!-- header -->
<#include "/content/common/header.ftl">

<div class="content">
  <div style="width:955px; margin:0 auto; padding-bottom:20px;">
    <#include "/content/common/user_center_header.ftl">
    
    <div style="width:955px; float:left; background:#fff; clear:both;">
        <#include "/content/common/user_center_left.ftl">
        
        <div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
			<a style="color:#646464;font-family:'宋体';">${Application ["qmd.setting.name"]}</a><a>></a>
			<a style="color:#646464;font-family:'宋体';" href="${base}/userCenter/index.do">我的账户</a><a>></a>
			<a style="color:#646464;font-family:'宋体';" >自动投标设置</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            		
					<a href="javascript:void(0);" class="hr hre">自动投标设置</a>         
          </div>
           <div style="height:23px;line-height:23px;text-align:center;color:#595757;font-family:'宋体';background:#efebdf;margin-top:100px; margin-bottom:30px; clear:both;">
							<#if user.autoTenderStatus!=1>
							<#else>
								自动投标排名：<span>${tenderAutoRank}</span>&nbsp;名
							</#if>
							&nbsp;&nbsp;&nbsp;成功自投次数：<span>${user.autoTenderTimes} </span>&nbsp;次
							&nbsp;&nbsp;&nbsp;设置时间：<span><#if user.autoTenderDate?if_exists>${user.autoTenderDate?string('yyyy-MM-dd HH:mm:ss')}</#if>
			</div>  
            
            
						 <form id="tenderAutoForm" method="post" action="${base}/user/saveTenderAuto.do"">
						 <input type="hidden" id="autoTenderStatus" name="user.autoTenderStatus" value="${user.autoTenderStatus}"/>	
							<table width="100%" cellpadding="0" cellspacing="0">
								<tbody>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">自动投标状态：</td>
										<td style="color:#646464;font-family:'宋体';"><strong><#if user.autoTenderStatus==1>开启<#else>关闭</#if></strong></td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">可用总额：</td>
										<td style="color:#646464;font-family:'宋体';">${user.ableMoney?string.currency} &nbsp;&nbsp;&nbsp;&nbsp;有效金额：<span>${(((user.ableMoney/100)?int)*100)?string.currency}</span></td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">续投余额：</td>
										<td style="color:#646464;font-family:'宋体';">${user.continueTotal?string.currency}&nbsp;&nbsp;&nbsp;&nbsp;有效金额：<span>${(((user.continueTotal/100)?int)*100)?string.currency}</span></td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">有效投资总额：</td>
										<td style="color:#646464;font-family:'宋体';"><strong>${((((user.ableMoney/100)?int)*100) + (((user.continueTotal/100)?int)*100))?string.currency}</strong>&nbsp;元</td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">投标金额设置：</td>
										<td style="color:#646464;font-family:'宋体';">（自动投标优先投资“续投”中资金）</td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">&nbsp;</td>
										<td style="color:#646464;font-family:'宋体';">
											<input type="radio" name="user.autoTenderRule" value="1" <#if user.autoTenderRule==1>checked</#if> />
											&nbsp;单次最高限额：<input type="text" style="width:50px;height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;" id="autoTenderMoneyTop" name="user.autoTenderMoneyTop" value="${user.autoTenderMoneyTop}"/>元
										</td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">&nbsp;</td>
										<td style="color:#646464;font-family:'宋体';">
											保留余额：<input type="text" style="width:50px;height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;" id="autoTenderMoneyLeave" name="user.autoTenderMoneyLeave" value="${user.autoTenderMoneyLeave}"/>元 以下不进行自投
											&nbsp;&nbsp;<span>（次处为可用余额与续投余额的总和）</span>
										</td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">&nbsp;</td>
										<td style="color:#646464;font-family:'宋体';">
											<input type="radio" name="user.autoTenderRule" value="0" <#if user.autoTenderRule!=1>checked</#if> onclick="clickRuleAll();" />
											余额全部投标
											&nbsp;&nbsp;<span>（此处为可用余额与续投余额的总和）</span></td>
										</td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">年利息范围：</td>
										<td style="color:#646464;font-family:'宋体';">
											<input type="text" style="width:50px;height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;" name="user.autoTenderRateBegin" id="autoTenderRateBegin" value="${user.autoTenderRateBegin}"/>%&nbsp;-&nbsp;
											<input type="text" style="width:50px;height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;" name="user.autoTenderRateEnd" id="autoTenderRateEnd" value="${user.autoTenderRateEnd}"/>%</td>
										</td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">奖励范围：</td>
										<td style="color:#646464;font-family:'宋体';">
											<input type="text" style="width:50px;height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;" name="user.autoTenderRewardBegin" id="autoTenderRewardBegin" value="${user.autoTenderRewardBegin}"/>%&nbsp;-&nbsp;
											<input type="text" style="width:50px;height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;" name="user.autoTenderRewardEnd" id="autoTenderRewardEnd" value="${user.autoTenderRewardEnd}"/>%
										</td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">借款期限范围：</td>
										<td style="color:#646464;font-family:'宋体';">
											<select name="user.autoTenderLimitBegin" id="sel_autoTenderLimitBegin">
												<option value="1">1个月</option>
												<option value="2">2个月</option>
												<option value="3">3个月</option>
											</select>
											-
											<select name="user.autoTenderLimitEnd" id="sel_autoTenderLimitEnd">
												<option value="1">1个月</option>
												<option value="2">2个月</option>
												<option value="3">3个月</option>
											</select>
											&nbsp;&nbsp;&nbsp;&nbsp;<span>*修改影响排名</span>
										</td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">还款方式：</td>
										<td style="color:#646464;font-family:'宋体';">
											<input type="checkbox" name="tenderAutoWayOne" value="1" <#if tenderAutoWayOne==1>checked</#if> >按月付息，到期还本<#--(1月借款标默认为该还款方式)-->
										&nbsp;&nbsp;&nbsp;&nbsp;<span>*修改影响排名</span>
										</td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
									<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">&nbsp;</td>
									<td style="color:#646464;font-family:'宋体';">
										<input type="checkbox" name="tenderAutoWayTwo" value="1" <#if tenderAutoWayTwo==1>checked</#if>>按月分期还本息
									</td>
								</tr><tr height="10"><td></td><td></td></tr>
								<tr>
									<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">&nbsp;</td>
									<td style="color:#646464;font-family:'宋体';">
										<input type="checkbox" name="tenderAutoWayThree" value="1" <#if tenderAutoWayThree==1>checked</#if>>到期还本息
									</td>
								</tr><tr height="10"><td></td><td></td></tr>
									
									
									
							
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';"> 安全密码：</td>
										<td style="color:#646464;font-family:'宋体';">
										<#if !user.payPassword>
											<span><strong > <a href="${base}/userCenter/toPayPassword.do" class="red">请点击设置安全密码</a></strong></span>
										<#else>
											<input type="password" name="user.payPassword" id="payPassword" style="width:178px;height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"> <a style="color:#363636; margin-left:5px;" href="${base}/user/lostpass.do?p=1">忘记密码？</a>
										</#if>
										</td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';"> 验证码：</td>
										<td style="color:#646464;font-family:'宋体';">
											<input type="text" id="mycode" name="mycode" onfocus="verifyCode();" style="width:90px;height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
											<span id="vCode" style=" visibility:hidden;"></span>
										</td>
									</tr><tr height="10"><td></td><td></td></tr>
									
<#if user.autoTenderStatus==1>
								<tr>
									<td class="base_item" valign="top"></td>
									<td style="color:#646464;font-family:'宋体';">
										<input type="button" value="停用" onclick="btnSubmit('0');"  style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" />
										<input type="button" value="保存修改"   onclick="btnSubmit('1');" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" /></td>
								</tr><tr height="10"><td></td><td></td></tr>
<#else>
								<tr>
									<td class="base_item" valign="top"></td>
									<td style="color:#646464;font-family:'宋体';">
										<input type="button" value="启用" onclick="btnSubmit('1');"  style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" />
										<input type="button" value="保存修改"  onclick="btnSubmit('0');"  style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" /></td>
								</tr><tr height="10"><td></td><td></td></tr>
</#if>									
								</tbody>
							</table>
            </form>
            
            
            
        </div>
    </div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->



  <#include "/content/common/footer.ftl">
</body>

<script type="text/javascript">
$(function(){
	$("#investment_tenderAuto").addClass("nsg nsg_a1");
	$("#sel_autoTenderLimitBegin").val(${user.autoTenderLimitBegin});
	$("#sel_autoTenderLimitEnd").val(${user.autoTenderLimitEnd});
});
</script>
</html>
