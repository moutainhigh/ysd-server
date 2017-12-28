<!DOCTYPE html>
<html>
<head>
	<title>${Application ["qmd.setting.name"]}-${borrow.name}</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/common/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="${base}/static/js/common/jquery.fancybox-1.3.4.pack.js"></script>
	<link rel="stylesheet" type="text/css" href="${base}/static/css/jquery.fancybox-1.3.4.css" media="screen" />
<SCRIPT language=javascript>

	function submitInverstFlow() {
	
		var testTime = new Date().getTime(); 
		
		$("#btnWanderTo").hide();
		$("#btnWandering").show();
		$.ajax({
	        type: "post",
	        dataType : "json",
	        data: $('#investFlowForm').serialize(),
	        url: 'investFlowDo.do?testTime='+testTime+'&',
	        success: function(data, textStatus){
	        	if(typeof(data.errorMsg) == "undefined" || typeof(data.errorMsg) == "null" || data.errorMsg == null || data.errorMsg == ""){
	        		alert("投标未成功");
					$("#btnWandering").hide();
					$("#btnWanderTo").show();
					window.location.reload();
	        	} else if (data.errorMsg=="OK") {
	        		alert("投标成功");
	        		KP.close();
	        		window.location.reload();
	        	} else {
		        	alert(data.errorMsg);
		        	$("#btnWandering").hide();
					$("#btnWanderTo").show();
					window.location.reload();
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
	
	function changePiece() {
		<!-- 认购份数 -->
		var wanderPiece = $("#wanderPiece").val();
		<!-- 续投份数 -->
		var wanderPieceContinue = $("#wanderPieceContinue").val();
		<!-- 每份投资金额 -->
		var idMoneyPieceMoney = $("#idMoneyPieceMoney").val();
		<!-- 赎回期数 -->
		//var wanderIus =  $("input[name='wanderPlan']:checked").val();
		<!-- 本期每份的收益-->
		//var wanderIncomeEach = $("#hid_each_income_"+wanderIus).val();
		var wanderIncomeEach = $("#idWanderPieceInterest").val();
		<!--收益 = (认购份数+续投份数) * 本期每份的收益-->
		//var money = parseFloat(idMoneyPieceMoney)*(parseFloat(wanderPiece)+parseFloat(wanderPieceContinue))*parseFloat(wanderIncomeEach);
		var money = (parseFloat(wanderPiece)+parseFloat(wanderPieceContinue))*parseFloat(wanderIncomeEach);
		var showMoney = changeTwoDecimal2(money);
		$("#idShowInCome").html("￥"+showMoney);
		
		var totalPiece = parseFloat(wanderPiece)+parseFloat(wanderPieceContinue);
		
		$("#idShowWanderPiece").html(totalPiece);
		fillTouziMoney();
	}
	
	function clickRepayPlan(daysNum,repayDays) {
	
		<!-- 认购份数 -->
		var wanderPiece = $("#wanderPiece").val();
		<!-- 续投份数 -->
		var wanderPieceContinue = $("#wanderPieceContinue").val();
		<!-- 每份投资金额 -->
		var idMoneyPieceMoney = $("#idMoneyPieceMoney").val();
		<!-- 赎回期数 -->
		var wanderIus =  $("input[name='wanderPlan']:checked").val();
		<!-- 本期每份的收益-->
		var wanderIncomeEach = $("#hid_each_income_"+wanderIus).val();
		<!--收益 = 认购份数 * 本期每份的收益-->
		var money = parseFloat(idMoneyPieceMoney)*(parseFloat(wanderPiece)+parseFloat(wanderPieceContinue))*parseFloat(wanderIncomeEach);
		var showMoney = changeTwoDecimal2(money);
		
		$("#idShowInCome").html("￥"+showMoney);
	
		$("#idShowDackDate").html(repayDays);
	}
	
	function fillTouziMoney() {
	
		<!-- 认购份数 -->
		var wanderPiece = $("#wanderPiece").val();
		var idMoneyPieceMoney = $("#idMoneyPieceMoney").val();
		var money = parseFloat(wanderPiece)*parseFloat(idMoneyPieceMoney);
	
		var showMoney = changeTwoDecimal2(money);
		$("#idPayMoney").html(showMoney);
		
		<!-- 续投份数 -->
		var wanderPieceContinue = $("#wanderPieceContinue").val();
		var moneyContinue = parseFloat(wanderPieceContinue)*parseFloat(idMoneyPieceMoney);
	
		var showMoneyContinue = changeTwoDecimal2(moneyContinue);
		$("#idPayMoneyContinue").html(showMoneyContinue);
	}
	
	function changeTwoDecimal2(x) {
		var f_x = parseFloat(x);
		if (isNaN(f_x)) {
			alert('您输入的数据不完整');
			return false;
		}
		var f_x = Math.round(x*100)/100;
		var s_x = f_x.toString();
		var pos_decimal = s_x.indexOf('.');
		if (pos_decimal < 0) {
			pos_decimal = s_x.length;
			s_x += '.';
		}
		while (s_x.length <= pos_decimal + 2) {
			s_x += '0';
		}
		return s_x;
	}

function openAgreement(){
		url='${base}/borrow/borrowAgreement.do?borrow.id=${borrow.id}';
		window.open(url);
	}
	$(document).ready(function() {
	
		$(".imgFancybox").fancybox({
				'type'				: 'image'
			});
	});
</SCRIPT>
</head>
<body>
<!-- header -->
<#include "/content/common/header.ftl">
<input type="hidden" id="borrow_bid" value="${borrow.id}" />


<div class="content">
  <div style="width:970px; margin:0 auto; padding-bottom:40px;">
      <div style="color:#4b4b4b; font-family:'宋体'; padding:22px 0px 12px 0px; border-bottom:1px solid #c6c6c6;">
			<a href="${base}/">${Application ["qmd.setting.name"]}</a><a>></a>
			<a href="${base}/borrow/list.do">投资中心</a><a>></a>
			<a class="liebiao">借款详情</a>
		</div>
     
     <div style="width:660px; padding-right:24px;float:left; margin-top:20px;">
        <div style="color:#4b4b4b; font-size:24px;"><img src="/static/img/a23.png" width="28" height="28" />&nbsp;&nbsp;${borrow.name}</div>
<#--        <div style="color:#4b4b4b; font-family:'宋体';font-size:14px; padding-top:15px; ">已完成投标4，994，900.00元，即刻投资收益自 2014-08-07起算，每万元预期收益1，100.00元</div>-->
        <div style="width:600px; background:#fff; padding:15px 30px 0px 30px;border-top:1px solid #be9964; margin-top:13px;">
           <ul style="width:295px; float:left;">
             <li style="background:url(img/b3.png) 0 0 no-repeat; height:22px; line-height:22px; padding-left:25px;color:#646464; font-family:'宋体';font-size:14px;">借款金额</li>
             <li style="font-size:27px;color:#be9964;">￥${borrow.account}元(${borrow.wanderPieceSize}份)</li>
           </ul>
           <ul style="width:180px; float:left;">
             <li style="background:url(img/b3.png) -295px 0 no-repeat; height:22px; line-height:22px; padding-left:25px;color:#646464; font-family:'宋体';font-size:14px;">天利率</li>
             <li style="font-size:27px;color:#be9964;">${borrow.apr}‰<#--（年化：<@aprofyear apr="${borrow.apr}"; apryear>${apryear}</@aprofyear>%）--></li>
           </ul>
           <ul style="width:125px; float:left;">
             <li style="background:url(img/b3.png) -475px 0 no-repeat; height:22px; line-height:22px; padding-left:25px;color:#646464; font-family:'宋体';font-size:14px;">借款期限</li>
             <li style="font-size:27px;color:#be9964;">${borrow.timeLimit}天</li>
           </ul>
           <div style="clear:both; border-bottom:1px dashed #d4d4d4; width:600px; height:13px;"></div>
           <ul style="width:600px; float:left; padding-bottom:17px; color:#595757;font-family:'宋体';">
             <li style="width:300px; float:left; padding:15px 0px 0px 0px;">类　　型：流转标</li>
             <li style="width:300px; float:left; padding:15px 0px 0px 0px;">还款方式：
             <#if borrow.style == 1>分期付息
				<#elseif borrow.style == 2>到期付本息
				<#elseif borrow.style == 3>等额本金
				</#if></li>
             <li style="width:300px; float:left; padding:15px 0px 0px 0px;">月还本息： 1,017.00元</li>
             <li style="width:300px; float:left; padding:15px 0px 0px 0px;"><#if borrow.awardType?? && borrow.awardType == 1>红包奖励<#else>现金奖励</#if>： 
             	<#if borrow.award??>
					<#if borrow.award==1>${borrow.funds}%
					<#elseif borrow.award==2 >${borrow.funds}%<img style="vertical-align:text-top;margin:0 -30px 0 10px;" src="/static/img/mark_0.jpg"></img>
					<#else>无</#if>
				</#if>
			</li>
           </ul>
           <div style="clear:both;"></div>
        </div>
        <ul style="width:660px; border-bottom:1px solid #be9964; height:42px; margin-top:20px;" class="ullist">
          <li class="change">项目信息</li>
          <li>还款计划</li>
        </ul>
        <div class="kuang">
          <div class="kuang_a1 kuang_a2">
             <div style="padding-top:20px; margin-top:2px;">
               <div style="color:#000; font-size:14px;font-family:'宋体'; font-weight:bold; padding-bottom:7px;">借款介绍</div>
               <p style="color:#6f6e6e;font-family:'宋体'; text-indent:28px; line-height:18px;">${borrow.content}</p>
             </div>
             <div style="margin-top:32px;">
                <div style="color:#000; font-size:14px;font-family:'宋体'; font-weight:bold; padding-bottom:10px;">借款人资料</div>
                <table width="100%" cellpadding="0" cellspacing="0" style="background:#fff; border:1px solid #c6c6c6; border-right:none;border-bottom:none; color:#646464;font-family:'宋体';">
                   <tbody align="center">
                    <#if user.memberType == 0  && userInfo!>
                     <tr height="39">
                        <td width="85" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
                        <td width="120" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; padding-left:10px; text-align:left;"><#if user.sex == 1>男<#else>女</#if></td>
                        <td width="85" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄：</td>
                        <td width="120" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;padding-left:10px; text-align:left;">${user.age}岁</td>
                        <td width="85" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;">婚姻状况：</td>
                        <td width="120" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;padding-left:10px; text-align:left;">
                        <@listing_childname sign="user_marry" key_value="${userInfo.marry}"; type>
									${type}
								</@listing_childname>
						</td>
                     </tr>
                     <tr height="39">
                        <td width="85" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;">文化程度：</td>
                        <td width="120" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; padding-left:10px; text-align:left;">
                        	<@listing_childname sign="user_education" key_value="${userInfo.educationRecord}"; type>
									${type}
								</@listing_childname>
						</td>
                        <td width="85" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;">每月收入：</td>
                        <td width="120" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;padding-left:10px; text-align:left;">
                        <@listing_childname sign="user_income" key_value="${userInfo.income}"; type>
									${type}
						</@listing_childname>
                        </td>
                        <td width="85" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;">社&nbsp;&nbsp;&nbsp;&nbsp;保：</td>
                        <td width="120" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;padding-left:10px; text-align:left;">
                        <@listing_childname sign="user_shebao" key_value="${userInfo.socialInsuranceStatus}"; type>
									${type}
								</@listing_childname>
						</td>
                     </tr>
                     <tr height="39">
                        <td width="85" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;">住房条件：</td>
                        <td width="120" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; padding-left:10px; text-align:left;">
                        <@listing_childname sign="user_housing" key_value="${userInfo.housing}"; type>
									${type}
						</@listing_childname>
						</td>
                        <td width="85" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;">是否购车：</td>
                        <td width="120" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;padding-left:10px; text-align:left;">
						<@listing_childname sign="user_car" key_value="${userInfo.carStatus}"; type>
									${type}
								</@listing_childname>
						</td>
                        <td width="85" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;">是否逾期：</td>
                        <td width="120" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;padding-left:10px; text-align:left;">无</td>
                     </tr>
                   <#elseif user.memberType==1  && userInfo!>
                   <tr height="39">
							<td width="85" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;">企业名称：</td>
							<td width="120" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; padding-left:10px; text-align:left;">
								<a title="${userInfo.privateName}">${substring(userInfo.privateName, 48, "")}</a>
							</td>
							<td width="85" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;">企业地址：</td>
							<td width="120" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; padding-left:10px; text-align:left;" colspan="3">
								${userInfo.privatePlace}
							</td>
						</tr>
						<tr>
							<td width="85" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;">企业年收入：</td>
							<td width="120" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; padding-left:10px; text-align:left;">
								<@listing_childname sign="enterprise_income" key_value="${userInfo.privateIncome}"; type>
									${type}
								</@listing_childname>
							</td>
							<td width="85" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;">企业员工数：</td>
							<td width="120" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; padding-left:10px; text-align:left;">
								<@listing_childname sign="employees" key_value="${userInfo.privateEmployees}"; type>
									${type}
								</@listing_childname>人
							</td>
							<td width="85" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;">注册资金：</td>
							<td width="120" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; padding-left:10px; text-align:left;">${userInfo.registeredCapital}元</td>
						</tr>
						<tr>
							<td width="85" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;">企业介绍：</td>
							<td  width="120" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; padding-left:10px; text-align:left;"colspan="5">
								${userInfo.introduce}
							</td>
						</tr>
						<tr>
							<td width="85" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;">营业执照：</td>
							<td width="120" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; padding-left:10px; text-align:left;"colspan="5">
								<img src="<@imageUrlDecode imgurl="${userInfo.privateCharterImg}"; imgurl>${imgurl}</@imageUrlDecode>" />
							</td>
						</tr>
					</#if>
                   </tbody>
                </table>
             </div>
             <div style="margin-top:32px;">
                <div style="color:#000; font-size:14px;font-family:'宋体'; font-weight:bold; padding-bottom:10px;">待还记录</div>
                <table width="100%" cellpadding="0" cellspacing="0" style="background:#fff; border:1px solid #c6c6c6; border-right:none;border-bottom:none; color:#646464;font-family:'宋体'; ">
                   <tbody align="center">
                     <tr height="39">
                        <td width="272" style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6; color:#be9964; font-weight:bold;">借款标题</td>
                        <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;  text-align:center;background:#f6f6f6;color:#be9964; font-weight:bold;">期数</td>
                        <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; background:#f6f6f6;color:#be9964; font-weight:bold;">还款本息</td>
                        <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;text-align:center;background:#f6f6f6;color:#be9964; font-weight:bold;">实际到息日期</td>
                     </tr>
                     <#list userBorrowList as userBorrow>
                     <tr height="35">
                        <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${userBorrow.name}</td>
                        <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">${userBorrow.timeLimit}<#if userBorrow.type==4>月<#else>天</#if></td>
                        <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">￥${userBorrow.account}</td>
                        <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;"><#if userBorrow.finalRepayDate??>${userBorrow.finalRepayDate?string("yyyy-MM-dd")}</#if></td>
                     </tr>
                  </#list>
                   </tbody>
                </table>
             </div>
          </div>
          <div class="kuang_a1">
            <div  style="padding-top:40px; margin-top:2px;">
                <#if borrow.type==11||borrow.type==12>
                <table width="100%" cellpadding="0" cellspacing="0" style="background:#fff; border:1px solid #c6c6c6; border-right:none;border-bottom:none; color:#646464;font-family:'宋体'; ">
                  <thead align="center">
                     <tr height="40">
                        <th style="color:#000; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">期数</th>
                        <th style="color:#000; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">还款日期</th>
                        <th style="color:#000; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">还款总额</th>
                        <th style="color:#000; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">还款本金</th>
                        <th style="color:#000; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">还款利息</th>
                     </tr>
                  </thead>
                  <tbody align="center">
          <#list repaymentInfo.repaymentDetailList as d>
                    <tr height="40">
                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${d.orderNum}</td>
                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">第${d.repaymentDateInt}<#if borrow.isday==0>月<#elseif borrow.isday==1>天</#if></td>
                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${d.account?string.currency}</td>
                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${d.capital?string.currency}</td>
                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${d.interest?string.currency}</td>
                    </tr>
                  </#list>
                    <tr height="40">
                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; " colspan="2">小计</td>
                       <td style="color:#ff830a;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">${repaymentInfo.account?string.currency}</td>
                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${repaymentInfo.capital?string.currency}</td>
                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${repaymentInfo.interest?string.currency}</td>
                    </tr>
	 		</tbody>
       	</table>
       </#if>
            </div>
          </div>
          <script>
          jQuery(function(){
             jQuery('.ullist li').click(function(){
				 var i=jQuery(this).index();
				 jQuery('.ullist li').removeClass('change');
				 jQuery(this).addClass('change');
				 jQuery('.kuang .kuang_a1').css('display','none')
				 jQuery('.kuang .kuang_a1').eq(i).css('display','block')
				 });
          })
		  </script>
        </div><!--kuang end-->
     </div>
     
     <div style="width:285px; float:right;padding-top:30px;border-left:1px solid #c6c6c6; ">
       <ul style="padding-left:55px; padding-bottom:10px;margin-bottom:0px; ">
          <li style=" padding-bottom:12px;color:#5c5e60; font-size:14px;font-family:'宋体';">
            <span>投标进度：</span>
            <span class="pct"><span class="huan" style="display:inline-block;"><font style="font-family:'宋体'; font-size:14px;color:#000;">${borrow.schedule}%</font></span></span>
          </li>
          <li style=" padding-bottom:12px;color:#5c5e60; font-size:14px;font-family:'宋体';">还　　差：￥${borrow.balance}(${borrow.showBalanceSize}份)</li>
          <li style=" padding-bottom:12px;color:#5c5e60; font-size:14px;font-family:'宋体';"> 最小投份数：${borrow.lowestAccount}份</li>
          <li style=" padding-bottom:12px;color:#5c5e60; font-size:14px;font-family:'宋体';">最大投份数：<#if borrow.mostAccount == 0>无限制<#elseif borrow.mostAccount == ''>无限制<#else>${borrow.mostAccount}份</#if></li>
          <li style="background:url(/static/img/b4.png) 0 0 no-repeat; width:178px; height:39px; line-height:39px; text-align:center;">
          	<#if showButtonNameFlg==1>
						<input type="button" id="poputFlow" style="background:#be9964;width:150px;height:30px;line-height:30px;color:#fff;font-size:16px; margin-left: 55px;margin-bottom:20px;" value="${showButtonName}"/>
			<#else>
						<input type="button" style="background:#be9964;width:150px;height:30px;line-height:30px;color:#fff;font-size:16px; margin-left: 55px;margin-bottom:20px;" value="${showButtonName}"/>
			</#if>
          </li>
       </ul>
       <div style=" width:265px; padding-left:20px;">
         <div style="border-bottom:1px solid #be9964; padding-bottom:5px; color:#4b4b4b;font-size:14px;font-family:'宋体'; font-weight:bold;">投标情况</div>
         <div style=" padding-bottom:0px;">
           <table width="100%" cellpadding="0" cellspacing="0" border="0">
              <thead align="center">
                 <tr height="40">
                    <th style="color:#4b4b4b;font-size:14px;font-family:'宋体'; font-weight:bold;">时间</th>
                    <th style="color:#4b4b4b;font-size:14px;font-family:'宋体'; font-weight:bold;">投资人</th>
                    <th style="color:#4b4b4b;font-size:14px;font-family:'宋体'; font-weight:bold;">投标金额</th>
                 </tr>
              </thead>
              <tbody align="center">
              <#list borrowTenderList as tender>
                 <td style="color:#4b4b4b;font-family:'宋体';border-bottom:1px dashed #c6c6c6;">${tender.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
                    <td style="color:#4b4b4b;font-family:'宋体';border-bottom:1px dashed #c6c6c6;">${tender.username}</td>
                    <td style="color:#4b4b4b;font-family:'宋体';border-bottom:1px dashed #c6c6c6;">￥${tender.account}</td>
              </#list>
                
              </tbody>
           </table>
         </div>
       </div>
     </div>
     
     <div style="clear:both;"></div>
  </div>
</div><!-- content end -->



<!-- footer -->
<#include "/content/common/footer.ftl">

<script>
jQuery(function(){
	jQuery(".zong li").click(function(){
		var i=jQuery(this).index()
		jQuery(".zong li").removeClass("xiangqing");
		jQuery(this).addClass("xiangqing");
		jQuery(".f1 .f2").css('display','none')
		jQuery(".f1 .f2").eq(i).css('display','block')
		});
		
	});
jQuery(function(){
	jQuery(".n1 li").click(function(){
		var i=jQuery(this).index()
		jQuery(".n1 li").removeClass("n2");
		jQuery(this).addClass("n2");
		jQuery(".k1 .k2").css('display','none')
		jQuery(".k1 .k2").eq(i).css('display','block')
		});
		
	});
$(".pct").each(function(){
			var p=parseInt($(this).text());
			$(this).children(".huan").css("background-position", -Math.floor(p*0.2)*64 + "px 0");
		});
	
$("#top_borrows").addClass("nav_list");
</script>
			
</body>
</html>
