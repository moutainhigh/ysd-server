<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-提现已绑卡</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/ti_xian.css" />
</head>
    <body id="bg">
    <!-- 头部 -->
    <#include "/content/common/header.ftl">

    <!--内容区域-->
    <div id="main">
        <!--左边导航栏-->
       <#include "/content/common/user_center_left.ftl">
        <!--右边内容区-->
        <div class="content fr">
            <!--提现-->
            <div class="tixian">
            
            <form id="getMoneyForm" method="post" action="cash.do" onsubmit="return check();" autocomplete="off">
                <div class="to_bankcard">
                    <span>提现到银行卡：</span>
                    <div class="card_type">
                        <div class="banks_bk banks_${accountBank.bankId}"></div>
                        <div class="card_num">***************${accountBank.account?substring(accountBank.account?length-4,accountBank.account?length)}</div>
                    </div>
                </div>
                <div class="tixian_detail">
        
                 <input type="hidden" name="ableCashMoney" id="ableCashMoney" value="${user.ableMoney}"/>
				 <input type="hidden" name="maxCashMoney" id="maxCashMoney" value="${maxCashMoney}"/>
				 <input type='hidden' id='init_value' value="60" />
				 <input type='hidden' id='idcard' value="${loginUser.realStatus}" />
                        <table>
                            <tr class="tixian_available">
                                <td>可提现金额：</td>
                                <td colspan="2"><span>${user.ableMoney?string.currency}元</span></td>
                            </tr>
                            <tr class="tixian_tr">
                                <td>提现金额：</td>
                                <td><input type="text" placeholder="￥" name="accountCash.total" id="total" minlength="3"  onkeyup="value=value.replace(/[^0-9\.]/g,'')"  AUTOCOMPLETE="off" ></td>
                                <td class="font14">提现金额必须在${minCashMoney}元以上， 当天14:00后提现隔天到账<br>
                                    周末及法定假日提现将在下一个工作日到账</td>
                            </tr>
                     
                            
                            <tr class="identify_tr">
                                <td>验证码：</td>
                                <td colspan="2">
                                    <input type="text" class="left" title=""  name="code" maxlength="6" id="phoneCode">
                                    <input name="tixianbtn"  id="getcode" class="right" type="button" value="获取验证码">
                                    <!--<div >获取短信验证码</div>-->
                                </td>
                            </tr>
                            <tr>
                            	<td colspan="3" class='input_tips'  style="visibility:hidden;">
					                <img src="../img/tips.png" alt="">
					                <span></span>
                            	</td>
                            </tr>
                            <tr>
                                <td colspan="3" class="tixian_sub"><input type="submit" value="提现"></td>
                            </tr>
                        </table>
                    </form>
                           
                </div>
                
            </div>
            <div class="recharge_ts">
                    	<span class="recharge_tb"></span>温馨提示：<span>您每月前${cashChargeTimes}笔提现免手续费，从第6笔开始，每笔收取${fixedFee}元手续费，本月您已累计提现${userCashChargeTimes}笔。</span>
                          		
                          		
            </div> 
            <div class="blank"></div>
        </div>
        <div class='recharge_uncard'>
        	<div class='uncard_content'>
        		<div class="close fr"></div>
        		<div class="uncard_title tips">温馨提示</div>
        		<div class="uncard_cont tips">绑定银行卡后才能使用快捷支付及提现功能</div>
        		<div class="options">
        			<a href="javascript:void(0);"><span class="back_account">我知道了</span></a>
        		</div>
        	</div>
        </div>
        <div class="tixian_success_wrap" style="display:none;">
            <div class="tixian_success">
                <div class="close fr"></div>
                <div class="success_hint tips">提现申请已提交成功！</div>
                <table class="table_detail">
                    <tr>
                        <td>提现银行</td>
                        <td>银行卡号</td>
                        <td>提现金额</td>
                        <td>到账金额</td>
                        <td>到账时间</td>
                    </tr>
                    <tr>
                        <td>建设银行</td>
                        <td>6228565****5565</td>
                        <td>100</td>
                        <td>100</td>
                        <td>预计2016.9.23  10点前</td>
                    </tr>
                </table>
                <div class="warm_tips">
                    温馨提示：<span>如果您的银行卡无法正常使用或者出现其他情况，资金将会自动退回到您的账户。</span>
                </div>
                <div class="options">
                    <a href="${base}/my_assets.html"><span class="back_account">返回账户</span></a>
                    <a href="${base}/ti_xian.html"><span class="continue_tixian">继续提现</span></a>
                </div>
            </div>
        </div>
        <div class="clear"></div>
    </div>

    <!--底部-->
    <#include "/content/common/footer-center.ftl">
</body>
<script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${base}/js/tixian.js"></script>
<script src="${base}/js/common.js"></script>

    <script type="text/javascript">

        var minCash = ${minCashMoney};
		$().ready( function() {
			$("#header_wdzh").addClass("current");<#-- header.ftl 选中样式 -->
			$('#header_gywm').find('a').css('border',0);
			$(".center_left_wdzc").addClass("current");<#-- user_center_left.ftl 选中样式 -->
			
		});
		
		if($("#init_value").val() < 60){
			$("#init_value").val('60');
		}
		
		$('#getcode').click(function(){ 
			if($("#init_value").val()== 60){
				$.getJSON('${base}/sendPhoneCodeCash.do',function(data){
					if(typeof(data.result) == "undefined" || typeof(data.result) == "null" || data.result == null || data.result == ""){
		        		alertMessage("验证失败");
		        		return false;
		        	} else if (data.result=="0") {
	    				time = setInterval('auto_jump()',1000);   
		        	} else {
			        	$('.input_tips').find('span').html(data.reason);
			        	$('.input_tips').css('visibility','visible');
			        	return false;
		        	}
				});
			}
		});
		
		
	function auto_jump() {	
	    secs = $("#init_value").val() - 1;  
	    
	    $("#init_value").val(secs);  
	    
	    if(secs < 0){
	     	$("#init_value").val('60');
	    	return false;
	    }  
	    
	    if(secs == 0){
	     	clearInterval(time);
	        $("#init_value").val('60');
			$("#getcode").val('获取验证码');
	    	document.getElementById('getcode').style.backgroundColor='#ef3e44';
	    	document.getElementById('getcode').style.color='#fff';
	    }else{
	    	document.getElementById('getcode').style.backgroundColor='#f2f2f2';
	    	document.getElementById('getcode').style.color='#666666';
	        $("#getcode").val(secs+'秒后重新获取');  
	    }
	}
	</script>
</body>
</html>
