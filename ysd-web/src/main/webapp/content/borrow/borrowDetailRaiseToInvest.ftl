<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-${borrow.name}</title>
    <link rel="stylesheet" href="${base}/css/common.css">
    <link rel="stylesheet" href="${base}/css/confirm_investment.css">
    <style>
    	#header .header_wrap .header_shadow {
    		background-color: #ffffff;
		    background-color: #ffffff\9;
		    opacity: 1;
		    filter: alpha(opacity=1);
    	}
    </style>  
</head>
<body>
<!-- 头部 -->
   <#include "/content/common/header.ftl">
    <!--内容1部分-->
    <div class="content"></div>
    <div class="content1">
        <div class="title">投资信息</div>
        <div class="detail">
            <table>
                <tr class="tr_1">
                    <td class="font_red td_1">${borrow.name}</td>
                    <td id="myTenderMoney" class="font_red td_2" data="${borrow.balance}">${(tenderAbleMoney+tenderContinueMoney)}</td>
                    <td class="td_3 font_red">
                    	<#if borrow.style == 1>分期付息
						<#elseif borrow.style == 2>到期付本息
						<#elseif borrow.style == 3>等额本金
						</#if>
                    </td>
                    <td id="myShouyi" class="font_red font30 td_4" borrow-apr="${borrow.apr}" borrow-day="${borrow.timeLimit}"  >${dqsy}</td>
                </tr>
                <tr class="tr_2">
                    <td>项目名称</td>
                    <td>投资金额</td>
                    <td>还款方式</td>
                    <td>到期收益</td>
                </tr>
            </table>
        </div>
        <div class="payment_date">
            	还款日期：<span>${borrow.finalRepayDate?string("yyyy-MM-dd")}</span>
        </div>
    </div>
    <!--内容2部分-->
    <div class="content2">
        <div class="title">
        	支付信息
        	<#if borrow.type !='16' && hbbb.userHongbaoList !=null> 
        		<span>系统会自动为您选择使用红包最大收益的匹配方案，若您想使用其余红包，则需要增加相应的投资金额。 </span>
        	</#if>
        </div>
        <#if hbbb.userHongbaoList !=null> 
	        <div class="red_bag">
	            <#list hbbb.userHongbaoList  as hb>
	            <div class="red_bag_warp <#if hb.on == true> selected</#if>" hb-selected="<#if hb.on == true>true</#if>" data-red="${hb.money}" data-money="${hb.investFullMomey}">
                	<input type="hidden" name="hongbao" value="${hb.id}">
	                <span class="select fr"></span>
	                <div class="redbag_model">
	                    <div class="title">
	                       	 ￥<span class="redbag_value">${hb.money}</span><span class="redbag_name" title="${hb.name}"><#if hb.source?number==6>${hb.sourceString}<#else>${hb.name}</#if></span>
	                    </div>
	                    <div class="use_detail">
	                        <div class="deadline">有效期：截止 ${hb.endTime?string("yyyy-MM-dd")}</div>
	                        <div class="project_deadline">项目期限：满${hb.limitStart}天可用</div>
	                        <div class="use_conditional">投资金额：满${hb.investFullMomey}元可用</div>
	                    </div>
	                </div>
	            </div>
	            </#list>
	        </div>
	     <#else>
	     <div class="red_bag unReg">
	     </div>
        </#if>
        <div class="details">
            <div class="left">
                <div class="top">
                    <span class="left">当前投资总额为 <strong data="${(tenderAbleMoney+tenderContinueMoney)}">${(tenderAbleMoney+tenderContinueMoney)}</strong>元  </span>
                    <span class="right">红包支付 <strong>${hbbb.bestHbMoney}</strong>元</span>
                </div>
                <div class="bottom">
                    <span class="left">账户可用余额共 <strong data="${userAbleMoney}">${userAbleMoney}</strong>元</span>
                    <span class="right">还需充值 <strong>${userRechargeMoney}</strong>元</span>
                </div>
            </div>
            <div class="right">
                <a id='_rechargTo' href="${base}/payment/rechargeTo.do?money=${userRechargeMoney}" target="_blank" ><div class="recharge" id="_chargeButton" <#if (userRechargeMoney == 0) >style="display: none;"</#if> >充值</div></a>
                <a href="javascript:$('#borrowRaiseInvestForm').submit();" ><div class="default" id="_hbmSelectButton" style="display: none;"  >默认选择</div></a>
                <form style="display:none" id="borrowRaiseInvestForm" action="${base}/borrow/toInvestH.do" method="post" >
		        	<input type="hidden" id="bId" name="bId" value="${borrow.id}"/>
		            <input type="hidden" name="tenderAbleMoney" value="${tenderAbleMoney}" />
		            <input type="hidden" id="tenderContinueMoney" name="tenderContinueMoney" value="${showContinueTotal}" />
		        </form>
            </div>
        </div>
        
        <div class="pay">
            <div class="tips">温馨提示：实际支付金额=当前投资总额-红包支付金额</div>
            <form  id="investForm">
	            <input type="hidden" name="borrowTender.borrowId" value="${borrow.id}"/>
				<#if borrow.type == '17'>
					<input type="hidden" name="borrowTender.tasteAmount" value="${tenderAbleMoney}"/>
				<#else>
					<input type="hidden" name="borrowTender.ableAmount" value="${tenderAbleMoney}"/>
				</#if>
				<#if borrow.isday=='0'>
					<input type="hidden" name="borrowTender.continueAmount" value="${tenderContinueMoney}"/>
				<#else>
					<input type="hidden"  name="borrowTender.continueAmount" value="0"/>
				</#if>
				<div id="useReg">
					<#if borrow.type !='16' && hbbb.userHongbaoList !=null> 
	            		<#list hbbb.userHongbaoList  as hb>
	            			<#if hb.on == true>
	            				<input type="hidden" name="hongbao" value="${hb.id}">
	            			</#if>
	            		</#list>
	            	</#if>
				</div>
                <table>
                	<#if borrow.isDxb==1>
                	<tr>
                        <td>请输入定向密码：</td>
                        <td colspan="2"><input type="password" id="dxpwd" name="dxpwd"></td>
                        <td></td>
                    </tr>
                    </#if>
                    <tr>
                        <td>请输入交易密码：</td>
                        <td colspan="2"> <input type="password" id="idsafepwd" name="safepwd" class="phone_num" title="phone_num"></td>
                        <td><span class="payError AllError"></span></td>
                    </tr>
                    <tr>
                        <td style="padding-bottom: 0">请输入验证码：</td>
                        <td style="padding-bottom: 0"><input style="width: 162px;" type="text"  id="mycode" name="mycode" class="identify_num" title="identify_num"  AUTOCOMPLETE="off" ></td>
                        <td style="padding-bottom: 0"><img id="code_img" onclick="verifyCode();" style="height: 38px;" src="${base}/rand.do" alt="" class="identify_img"></td>
                        <td><span class="codeError AllError"></span></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td colspan="2" style="text-align: left;"><a href="${base}/member/toPayPassword.do" style="font-size: 14px; color: #ef3e44" target="_blank">忘记交易密码？</a></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <input type="button" onclick="submitInverst();" value="确认并支付">
                        </td>
                        <td></td>
                    </tr>
                </table>
            </form>
            <#if (loginUser.payPassword)??>
            <#else>
            	<a href="${base}/member/toPayPassword.do"><div class="set_deal_pwd">设置交易密码</div></a>
            </#if>
        </div>
        
    </div>
    <div class="errorMassage">
    	<div class="errorMassage_bg"></div>
        <div>
        	<div class="errorMassageDiv_bg"></div>
            <div class="close fr">&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <div class="error_hint tips">温馨提示</div>
            <div class="input_tips">当前投资金额为2000.00元，无法再增加红包使用量</div>
            <a href="javascript:void(0);" class="error_know">我知道了</a>
	    	
        </div>
    </div>
    <div class="invest_success">
    	<div class="shadow"></div>
    	<div class="invest_success_wrap"> 
    		<div class="close">X</div>
    		<div class="clear"></div>
	    	<div class=tips></div>
	    	<div class="hint">恭喜您投标成功</div>
	    	<button>我知道了</button>
    	</div>    	
    </div>
    
 	<!-- 尾部 -->
	<#include "/content/common/footer.ftl">
</body>
<script src="${base}/js/jquery-1.11.3.min.js"></script>
<script src="${base}/js/common.js"></script>
<script src="${base}/js/confirm_investment.js"></script>
<script type="text/javascript">
	//选红包
	function selectHongbao(that){
		var Rmoney=0,Tmoney=0;//红包额，满多少可用
		$('#useReg').html('');
		$(".red_bag .selected").each(function (){
			var Rmoney1=parseInt($(this).attr('data-red'));
			Rmoney=Rmoney+Rmoney1;
			var Tmoney1=parseInt($(this).attr('data-money'));
			Tmoney=Tmoney+Tmoney1;
			var Regid=$(this).find('input[type=hidden]').attr('value');
			$('#useReg').append('<input name="hongbao" type="hidden" value="' + Regid + '" />');
			
		});
		var money=parseInt($('.details').find('.top .left strong').attr('data'));//投资总额
		if(Tmoney<money){//如果满多少钱可用 < 输入的投资金额（满多少钱可用 ，就是变成了实际投资额）
			Tmoney = money; 
		};
		var smoney=parseInt($('#myTenderMoney').attr('data'));
		if(Tmoney>smoney){//投资金额大于剩余金额
    		$('.errorMassage').find('.input_tips').html("投资金额大于剩余金额"+smoney);
    		$('.errorMassage').show();
    		$(that).removeClass('selected');
    		return false;
		}
		var UserMoney = parseFloat($('.details').find('.bottom .left strong').attr('data'));//可用金额
		var surplusMoney = (UserMoney + Rmoney - Tmoney);//剩余金额
		if(surplusMoney < 0){
			//显示充值金额&按钮
			var rechargeMoney = (Tmoney - UserMoney - Rmoney).toFixed(2);
			$('#_rechargTo').attr('href','${base}/payment/rechargeTo.do?money='+rechargeMoney);
			$('.details').find('.bottom .right strong').html(rechargeMoney);
			$("#_chargeButton").css('display','inline-block');
		}else{
			//隐藏充值金额&按钮
			$('.details').find('.bottom .right strong').html(0);
			$("#_chargeButton").css('display','none');
		}	
		$('.details').find('.top .right strong').html(Rmoney);
		$('.details').find('.top .left strong').html(Tmoney);
		$('#investForm').find('input[name="borrowTender.ableAmount"]').val(Tmoney);
		$('#myTenderMoney').html(Tmoney);
		//收益
		var apr = $('#myShouyi').attr('borrow-apr');
		var limit = $('#myShouyi').attr('borrow-day');
		var myShouyi =  (Tmoney*apr/1000*limit).toFixed(2);
		$('#myShouyi').html(myShouyi);
		console.log(Rmoney+ " " + Tmoney);
		//显示默认选择按钮
		$("#_hbmSelectButton").css('display','inline-block');
		
	}
	
	//验证码
	function verifyCode(){
		$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
	}
	//提交
	function submitInverst() {
		$('.AllError').css('visibility','hidden');
		 <#if (loginUser.payPassword)??>
        	var payPasswordIsNull = false;
          <#else>
        	var payPasswordIsNull = true;//无交易密码
         </#if>
        if(payPasswordIsNull){
        	$('.payError').html("请设置交易密码！");
        	$('.payError').css('visibility','visible');
        	return false;
        } 
		var idsafepwd = $("#idsafepwd").val();
		if (idsafepwd=='') {
			$('.payError').html("请输入交易密码");
        	$('.payError').css('visibility','visible');
			return false;
		}
		
		var mycode = $("#mycode").val();
		if(mycode==''){
			$('.codeError').html("请输入验证码");
        	$('.codeError').css('visibility','visible');
        	verifyCode();
			return false;
		}
		//提示充值
		var chargMoney = 0;
		var charg = $('.details').find('.bottom .right strong').html();
		if(charg!=''){
			chargMoney = parseFloat(charg);
		}
		if(chargMoney>0){
			//alert("请充值！");
			$('.errorMassage').find('.input_tips').html("请充值！");
    		$('.errorMassage').show();
			return false;
		}
		$.ajax({
	        type: "post",
	        dataType : "json",
	        data: $('#investForm').serialize(),
	        url: 'poputInvestDo.do',
	        success: function(data, textStatus){
	        	if(typeof(data.errorMsg) == "undefined" || typeof(data.errorMsg) == "null" || data.errorMsg == null || data.errorMsg == ""){
	        		//alert("投标未成功");
	        		$('.errorMassage').find('.input_tips').html("投标未成功,请刷新后重新投标");
	        		$('.errorMassage').show();
					//window.location.reload();
					verifyCode();
	        	} else if (data.errorMsg=="OK") {
	        		//alert("投标成功");
	        		$(".invest_success").css("display", "block");
	        		$(".invest_success .invest_success_wrap button").click(function() {
	        			$(".invest_success").css("display", "none;");
	        			window.location.href='${base}/borrow/detail.do?bId=${borrow.id}';
	        		});
	        		
	        	} else {
		        	//$('.payError').html(data.errorMsg);
        			//$('.payError').css('visibility','visible');
	        		$('.errorMassage').find('.input_tips').html(data.errorMsg);
	        		$('.errorMassage').show();
	        		verifyCode();
	        	}
	        },
	        error:function(statusText){
	        	//alert(statusText);
        		$('.errorMassage').find('.input_tips').html(statusText);
        		$('.errorMassage').show();
        		verifyCode();
	        },
	        exception:function(){
	        	//alert(statusText);
        		$('.errorMassage').find('.input_tips').html(statusText);
        		$('.errorMassage').show();
	        }
		});
	}
</script>
</html>