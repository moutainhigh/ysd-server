<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <title>${Application ["qmd.setting.name"]}-我的账户-续投转出页面</title>
	<#include "/content/common/meta.ftl">
	<script src="${base}/static/js/borrow/rollOutMoney.js"></script>
<script type="text/javascript">
function checkimg(){
	var $code_img = $("#code_img");
	$code_img.attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
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
        
        <div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
			<a style="color:#646464;font-family:'宋体';">${Application ["qmd.setting.name"]}</a><a>></a>
			<a style="color:#646464;font-family:'宋体';" href="${base}/userCenter/index.do">我的账户</a><a>></a>
			<a style="color:#646464;font-family:'宋体';" >续投转出</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            		<a href="${base}/borrow/bidSetRollList.do" class="hr ">续投设置</a>
					<a href="javascript:void(0);" class="hr hre">续投转出</a>         
          </div>
           <div style="height:23px;line-height:23px;text-align:center;color:#595757;font-family:'宋体';background:#efebdf;margin-top:100px; margin-bottom:30px; clear:both;">
				温馨提示：当天返回的续投金额不能从续投内转出提现，若要提现请第二天转出。
			</div>  
            
            
		 	<form id="getMoneyForm" method="post" action="rollOut.do" onsubmit="return check();">
		 	<input type="hidden" name="user.id" value=${user.id} />
			<input type="hidden" name="rollBackMoney" id="rollBackMoney" value="${rollBackMoney}"/>
            <table width="100%" cellpadding="0" cellspacing="0" border="0">
								<tbody>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">真实姓名：</td>
										<td><font style="color:#646464;font-family:'宋体';">${user.showRealName}</font></td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">账户总额：</td>
										<td><span  name="user.total" id="utotal" style="color:#646464;font-family:'宋体';">${user.total?string.currency}</span>元</td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">续投余额：</td>
										<td><span  name="user.continueTotal" id="continueTotal" style="color:#646464;font-family:'宋体';">${user.continueTotal?string.currency}</span>元</td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">可转出金额：</td>
										<td><span  name="rollBackMoney"  id="rollBackMoney" style="color:#646464;font-family:'宋体';">${rollBackMoney?string.currency}</span>元</td>
									</tr><tr height="10"><td></td><td></td></tr>
									
									
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">转出金额：</td>
										<td>
											<input type="text" name="rollTotal" id="rollTotal" onblur="commit(this);"  onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d{0,2})?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"  style="width:215px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/></td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';"> 安全密码：</td>
										<td>
										<#if !user.payPassword>
											<span><strong > <a href="${base}/userCenter/toPayPassword.do" class="red">请点击设置安全密码</a></strong></span>
										<#else>
											<input type="password" name="user.payPassword" id="payPassword" style="width:215px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"> <a style="color:#363636; margin-left:5px;" href="${base}/user/lostpass.do?p=1">忘记密码？</a>
										</#if>
										</td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';"> 验证码：</td>
										<td>
											<input type="text" id="mycode" name="mycode" onfocus="verifyCode();" style="width:215px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
											<img id = "code_img" src="${base}/rand.do" onclick="changeValidateCode(this)" title="点击图片重新获取验证码" /></span>
										</td>
									</tr><tr height="10"><td></td><td></td></tr>
									<tr>
										<td valign="top" class="base_item"></td>
										<td><input type="submit" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" value="转出"/></td>
									</tr>
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
	$("#investment_continue").addClass("nsg nsg_a1");
}); 
</script>
</html>
