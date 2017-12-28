<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-${borrow.name}</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/common/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="${base}/static/js/common/jquery.fancybox-1.3.4.pack.js"></script>
	<style>
  .tzxx dd {padding-left:36px;font-size:14px;color:#666;}
  .tzxx dd ul{overflow:hidden;}
  .btn{float:left;width:20px;height:30px;background:url(/img/tz01.png) no-repeat 0 center;}
  .btn.current{background-image:url(/img/tz05.png);}
  .red{width:1100px;border-top:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;padding:0 13px 20px 85px;display:block;}
  .red.cur{display: none;}
  .red ul li{cursor: pointer;}
  .choose{background: url(${base}/img/tz02.png) no-repeat 150px 16px;}
</style>
    <#include "/content/common/meta.ftl">
</head>
<body>
<!-- header -->
<#include "/content/common/header.ftl">
<div class="content">
  <div class="content">
  <div style="width:1200px; margin:0 auto; padding-bottom:40px;">
     <div style="color:#666;padding:22px 0px 12px 0px; border-bottom:1px solid #c6c6c6;font-size:16px;">${Application ["qmd.setting.name"]}>> 投资中心 >> <span style="color:#fd7c1a;">确认投资</span></div>
     <div style="width:1080px;height:160px;padding:20px 70px 0 50px;background:#fff;box-shadow:1px 1px 1px #ccc;margin:20px auto 40px;">
    
    <form id="investForm" style="padding-left:45px;">
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
      <dl style="line-height:30px;" class="tzxx">
     	<dt style="color:#4b4b4b;font-size:16px;line-height:30px;  margin-bottom: 10px;">投资信息</dt>
     	<dd>
        	<ul>
        		<li style="float:left;" >项目名称：<span>${borrow.name}</span></li>
        		<li  style="float:right;font-size:16px;" >投资金额：<span style="color:#fd7c1a;" class="total">${(tenderAbleMoney+tenderContinueMoney)}</span></li>
        	</ul>
        </dd>
     	<dd>
        	<ul>
        		<li style="float:left;margin-right:40px;" >还款日期：<span>${borrow.finalRepayDate?string("yyyy-MM-dd")}</span></li>
        		<li style="float:left;margin-right:40px;" >还款方式：<span>
        			<#if borrow.style == 1>分期付息
					<#elseif borrow.style == 2>到期付本息
					<#elseif borrow.style == 3>等额本金
					</#if>
				</span></li>
                <li style="float:left;" >到期收益：<span style="color:#fd7c1a;">
                	${dqsy}
                </span></li>
        	</ul>
        </dd>
        <dd>
        	<div>额外奖励：<span>
        		<#if borrow.type !='17' && borrow.award==1 && borrow.awardType?exists>
        			<#if borrow.awardType == 0>
        				现金奖励
        			<#elseif borrow.awardType == 1>
        				红包奖励
        			</#if>
        			(${borrow.funds}%)
        		<#else>
        			无
        		</#if>
        	</span></div>
        </dd>
     </dl>
     </div>
     <#if borrow.type !='17' && borrow.awardScale?exists && borrow.awardScale gt 0>
       <div style="width:1200px;padding-top:20px;background:#fff;box-shadow:1px 1px 1px #ccc;margin:20px auto 40px;color:#8c8c8c;">
       <h3 style="font-size:16px;font-weight:normal;padding:0px 0 10px 50px;">支付信息</h3>
       <div style="overflow:hidden;padding-left:85px;padding-right:50px;height:50px;">
       		<ul>
            	<li class="btn" ></li>
            	<li style="float:left;font-size:14px;padding-left:6px;line-height:30px;">账户可用红包共 <span style="color:#fd7c1a;" class="hbTotal">${loginUser.awardMoney}</span> 元</li>
                <li style="float:left;color:#0163ae;margin-left:20px;font-size:14px;line-height:30px;">* 本次投资最多能使用<span class="hbLimit">${(tenderAbleMoney+tenderContinueMoney)}</span>元红包（投资金额的<span class="bili">${borrow.awardScale}</span>%），请勾选使用</li>
       			<li class="hbPay" style="float:right;font-size:16px;line-height:30px;">红包支付：<span style="color:#fd7c1a;">0.00</span></li>
       		</ul>
       </div>
       <div class="red">
       	<div style="overflow:hidden;">
       		<#list hongbaoList as hb>
       			<input type="hidden" name="hongbao" id="hbId${hb_index}" value="">
	        	<ul style="padding:15px 76px 0 0px;width:143px;height:98px;float:left;overflow:hidden;">
	        		<li style="float:left;width:143px;height:98px;background:url(/img/hongbao.png) no-repeat;background-size: cover;"><p class="hbMoney" hbtype="hbId${hb_index}" hbid="${hb.id}" style="color:#fffba5;font-weight:bold;font-size:16px;text-align:center;margin:22px 0 5px;">${hb.money}</p><p style="color:#fffba5;text-align:center;">${hb.overDays}天后过期</p></li>
	        	</ul>
        	</#list>
        </div>
       </div>
       </#if>
       <div style="width:1100px;height:68px;line-height:68px;margin-left:50px;margin-right:50px;border-bottom:1px dashed #c6c6c6;font-size:16px;color:#8c8c8c;">
        <ul>
      		 <#if borrow.type == '17'>
   				<li style="float:left;padding-left:10px;">账户体验金 <span style="color:#fd7c1a;">${loginUser.tasteMoney?string.currency}</span> 元</li>
            <#else>
        		<li style="float:left;padding-left:10px;">账户可用余额共 <span style="color:#fd7c1a;">${loginUser.ableMoney?string.currency}</span> 元</li>
        	</#if>
        	<li class="needPay" style="float:right;padding-right:10px;">账户需支付：<span style="color:#fd7c1a;">${(tenderAbleMoney+tenderContinueMoney)}</span></li>
        </ul>
       </div>
       <div style="padding:50px 50px 50px 85px;width:1065px;font-size:16px;">
       	<ul style="overflow:hidden;">
       		<li style="float:left;color:#8c8c8c;">
            	<p style="background:url(${base}/static/img/tz03.png) no-repeat 10px center;line-height:36px;padding-left:40px;">本项目到期后本金及收益将自动归还至您的账户余额</p>
 <!--               <p style="background:url(${base}/static/img/tz04.png) no-repeat 10px center;line-height:36px;padding-left:40px;">浙江我就爱车典当有限责任公司对项目提供担保赔付</p>-->
            </li>
       		<li style="float:right;width:386px;">
                <ul>
                	<#if borrow.isDxb==1>
                    	<li style="margin-bottom:22px;"><span style="width:116px; padding-right:10px; text-align:right;color:#646464;font-size:16px;display:inline-block;">请输入定向密码</span><input type="password" id="dxpwd" name="dxpwd" style="width:245px; height:38px; line-height:38px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;outline:none;"></li>
                    </#if>
                    <li style="margin-bottom:22px;"><span style="width:116px; padding-right:10px; text-align:right;color:#646464;font-size:16px;display:inline-block;">请输入交易密码</span><input type="password" id="idsafepwd" name="safepwd" style="width:245px; height:38px; line-height:38px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;outline:none;"></li>
                    <li style="margin-bottom:22px;position:relative;"><span style="width:116px; padding-right:10px; text-align:right;color:#646464;font-size:16px;display:inline-block;">请输入验证码</span>
                    <input type="text" id="mycode" name="mycode" onfocus="verifyCode();" AUTOCOMPLETE="off"  style="width:245px; height:38px; line-height:38px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;outline:none;">
                    <span style="position:absolute;right:10px;top:1px;" id="vCode"></span>
                    </li>
                    <li><a href="javascript:void();" onclick="submitInverst();"  style="display:inline-block; width:108px; height:36px; line-height:36px; background:#fd7c1a; color:#fff; text-align:center; font-size:16px;border-radius:5px;margin-right:20px;margin-left:125px;cursor:pointer;">确认并支付</a><a href="javascript:void(0);" style="display:inline-block; width:108px; height:36px; line-height:36px; background:#dbdbdb; color:#666; text-align:center; font-size:16px;border-radius:5px;">取消</a></li>
                </ul>
            </li>
       	</ul>
       	</form>
       </div>
       </div>
  </div>
 
<!-- footer -->
<#include "/content/common/footer.ftl">
<script type="text/javascript">
 $(function(){
 	$(".btn").click(function(){
 		$(".red").toggleClass('cur');
		$(this).toggleClass('current');
 	})
	
	var total = $(".total").html();
	var bili = $(".bili").html()/100;
	var hbTotal = 0;
	var sum = 0;
	$(".hbLimit").html(parseInt(total*bili)); 
	var hbLimit = parseInt(total*bili);
	for(var i=0;i<$(".red ul").length;i++){
		hbTotal  = hbTotal + parseInt($(".red ul").eq(i).children("li").children(".hbMoney").html());
	}
	$(".hbTotal").html(hbTotal);
	sum = hbLimit;
    $(".red ul li").click(function(){
		if($(this).parent().hasClass("choose")){
			$(this).parent().removeClass("choose");
				$("#"+$(this).children(".hbMoney").attr("hbtype")).val('');
			sum = sum + parseInt($(this).children(".hbMoney").html());
			$(".hbPay span").html(hbLimit-sum);
			$(".needPay span").html((parseInt(total) - hbLimit + sum).toFixed(2));
		}
		else{
			if(parseInt($(this).children(".hbMoney").html()) <= sum){
				$(this).parent().addClass("choose");
				$("#"+$(this).children(".hbMoney").attr("hbtype")).val($(this).children(".hbMoney").attr("hbid"));
				sum = sum - parseInt($(this).children(".hbMoney").html());
				$(".hbPay span").html(hbLimit-sum);
				$(".needPay span").html((parseInt(total) - hbLimit + sum).toFixed(2));
			}
			else{
				alert("红包金额超出使用金额");
			}
			
		}
    })
	
 })
 
 
function verifyCode(){
	$("#vCode").html("<a id='vCodeA' title='点击更换' onclick='verifyCodeLink();'><img width='80' height='30' style='vertical-align:middle; position:relative; left:7px; top:-2px;' id = 'code_img' src='' /></a>");
	$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
	$("#mycode").select();
}

function verifyCodeLink(){
	$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
}

	function submitInverst() {
		
		var idsafepwd = $("#idsafepwd").val();
		if (idsafepwd=='') {
			alert("请输入交易密码");
			return false;
		}
		
		var mycode = $("#mycode").val();
		if(mycode==''){
			alert("请输入验证码");
			return false;
		}
		
		$.ajax({
	        type: "post",
	        dataType : "json",
	        data: $('#investForm').serialize(),
	        url: 'poputInvestDo.do',
	        success: function(data, textStatus){
	        	if(typeof(data.errorMsg) == "undefined" || typeof(data.errorMsg) == "null" || data.errorMsg == null || data.errorMsg == ""){
	        		alert("投标未成功");
					window.location.reload();
	        	} else if (data.errorMsg=="OK") {
	        		alert("投标成功");
	        		window.location.href='${base}/borrow/detail.do?bId=${borrow.id}';
	        	} else {
		        	alert(data.errorMsg);
	        	}
	        },
	        error:function(statusText){
	        	alert(statusText);
	        },
	        exception:function(){
	        	alert(statusText);
	        }
		});
	}



 </script>   
</body>
</html>
