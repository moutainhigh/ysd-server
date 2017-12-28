<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-${borrow.name}</title>
	<#include "/content/common/meta.ftl">

	<script type="text/javascript" src="${base}/static/js/common/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="${base}/static/js/common/jquery.fancybox-1.3.4.pack.js"></script>
	<link rel="stylesheet" type="text/css" href="${base}/static/css/jquery.fancybox-1.3.4.css" media="screen" />
	
<script language=javascript> 

function checkTender() {
		var idableAmount =  $.trim($("#idableAmount").val());
		$("#idableAmount").val(idableAmount);
		var idcontinueAmount = $.trim($("#idcontinueAmount").val());
		$("#idcontinueAmount").val(idcontinueAmount);
		
		if (idableAmount=='' && idcontinueAmount=='') {
			alert("请输入投标金额");
			return false;
		}
}

	function submitInverst() {
		$("#btnInvest").hide();
		$("#btnInvesting").show();
<#--		
		var idableAmount =  $.trim($("#idableAmount").val());
		$("#idableAmount").val(idableAmount);
		var idcontinueAmount = $.trim($("#idcontinueAmount").val());
		$("#idcontinueAmount").val(idcontinueAmount);
		
		if (idableAmount=='' && idcontinueAmount=='') {
			alert("请输入投标金额");
			$("#btnInvest").show();
			$("#btnInvesting").hide();
			return false;
		}
		
<#if borrow.type!=4>
		if (idableAmount=='') {
			alert("请输入投标金额");
			$("#btnInvest").show();
			$("#btnInvesting").hide();
			return false;
		}
</#if>
		
		if (idableAmount!='') {
			if (isNaN(idableAmount)) {
				alert("请输入正确投资金额!");
				$("#btnInvest").show();
				$("#btnInvesting").hide();
				return false;
			}
			if (numberZC(idableAmount)==false) {
				alert("投标金额必须是100的整数倍!");
				$("#btnInvest").show();
				$("#btnInvesting").hide();
				return false;
			}
		}
		if (idcontinueAmount!='') {
			if (isNaN(idcontinueAmount)) {
				alert("请输入正确续投金额!");
				$("#btnInvest").show();
				$("#btnInvesting").hide();
				return false;
			}
			if (numberZC(idcontinueAmount)==false) {
				alert("续投金额必须是100的整数倍!");
				$("#btnInvest").show();
				$("#btnInvesting").hide();
				return false;
			}
		}
-->
		<#if borrow.isDxb==1>
		var dxpwd = $("#dxpwd").val();
		if (dxpwd=='') {
			alert("请输入定向密码");
			$("#btnInvest").show();
			$("#btnInvesting").hide();
			return false;
		}
		</#if>
		
		var idsafepwd = $("#idsafepwd").val();
		if (idsafepwd=='') {
			alert("请输入安全密码");
			$("#btnInvest").show();
			$("#btnInvesting").hide();
			return false;
		}
		
		var mycode = $("#mycode").val();
		if(mycode==''){
			alert("请输入验证码");
			$("#btnInvest").show();
			$("#btnInvesting").hide();
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
	        		$("#btnInvest").show();
					$("#btnInvesting").hide();
					window.location.reload();
	        	} else if (data.errorMsg=="OK") {
	        		alert("投标成功");
	        		KP.close();
	        		window.location.reload();
	        	} else {
		        	alert(data.errorMsg);
		        	$("#btnInvest").show();
					$("#btnInvesting").hide();
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
	
	var time_end_client;
	function detailInit() {
		
		var ttt = ${countDown};
		if (ttt!=1) {
			return;
		}
	
		var time_now_server = new Date('${nowDate?string("MM/dd/yyyy HH:mm:ss")}').getTime();
		var time_now_client=new Date().getTime();//当前时间:浏览器
		var time_check = time_now_server-time_now_client;//服务器和本地的偏差值
		
		var endtime = new Date('${borrow.overDate?string("MM/dd/yyyy HH:mm:ss")}').getTime();
		time_end_client = endtime - time_check;//校准结束时间
		
		showCountDown();
		
	}
	
	function showCountDown() {
		var time_now=new Date().getTime()
		var showtime = time_end_client - time_now;
		if (showtime<=0) {
			$("#showTime").html("招标期已过");
		} else {
		
			var seconds = showtime/1000;
	        var minutes = Math.floor(seconds/60);
	        var hours = Math.floor(minutes/60);
	        var CDay = Math.floor(hours/24);
	        var CHour= hours % 24;
	        var CMinute= minutes % 60;
	        var CSecond= Math.floor(seconds%60);
	        
	        var str = "";
	        if (CDay > 0) {
	        	str +=CDay+"天"
	        }
	        str +=CHour+"小时"+CMinute+"分"+CSecond+"秒";
        	$("#showTime").html(str);
        	
        	setTimeout("showCountDown()",1000);
        }
	
	}
	
	function numberZC(s) {
		var n = parseFloat(s);
		if(n%100==0) {
			return true;
		}
		return false;
	}
	
	function jump_login() {
		alert("请登录！");
		window.location.href='${base}/user/login.do?loginRedirectUrl=%2Fborrow%2Fdetail.do%3FbId%3D${borrow.id}';
	}
	
	function openAgreement(){
		url='${base}/borrow/borrowAgreement.do?borrow.id=${borrow.id}';
		window.open(url);
	}
	detailInit();
	
	$(document).ready(function() {
<#--
		//$("#mytest").imgbox({
		//		'speedIn'		: 0,
		//		'speedOut'		: 0,
		//		'alignment'		: 'center',
		//		'overlayShow'	: true,
		//		'allowMultiple'	: false
		//});
-->		
		$(".imgFancybox").fancybox({
				'type'				: 'image'
			});
			
	});
	
</script>

</head>
<body>
<!-- header -->
<#include "/content/common/header.ftl">
<input type="hidden" id="borrow_bid" value="${borrow.id}"/>

<div class="content">
<div style="width:970px; margin:0 auto; padding-bottom:40px;">
     <div style="color:#4b4b4b; font-family:'宋体'; padding:22px 0px 12px 0px; border-bottom:1px solid #c6c6c6;">
			<a href="${base}/">${Application ["qmd.setting.name"]}</a><a>></a>
			<a href="${base}/borrow/list.do">投资中心</a><a>></a>
			<a class="liebiao">借款详情</a>
		</div>
	 <div style="width:660px; padding-right:24px;float:left; margin-top:20px;">
        <div style="color:#4b4b4b; font-size:24px;">
					<#if borrow.type==0><img src="${base}/static/img/borrow/miao.gif" width="28" height="28" />
					<#elseif borrow.type=1><img src="${base}/static/img/borrow/ya.gif" width="28" height="28" />
					<#elseif borrow.type=2><img src="${base}/static/img/borrow/zhuan.gif" width="28" height="28" />
					<#elseif borrow.type=4><img src="${base}/static/img/borrow/ya.gif" width="28" height="28" />
					</#if>
					<#if borrow.isDxb==1><img src="${base}/static/img/borrow/suo.gif" width="28" height="28" /></#if>
					<#if borrow.award==1||borrow.award==2><img src="${base}/static/img/borrow/jiang.gif" width="28" height="28" /></#if>
<#--					<#if agreement==0 >
					<a href="javascript:void(0);" onclick="openAgreement()" style="float:right; color:#00479D; font-size:14px; margin:10px 30px 0 0px;">借款协议</a>
					</#if>
--->
			&nbsp;&nbsp;${borrow.name}</div>
			<div style="color:#4b4b4b; font-family:'宋体';font-size:14px; padding-top:15px; ">
	        	<#if borrow.type==0>
					投资100元，年利率${borrow.apr}%，完成后，可获得利息收益￥${interest}元
				<#elseif borrow.type==4>
					投资100元，年利率${borrow.apr}%，完成后，可获得利息收益￥${interest}元
				<#else>
					投资100元，年利率${borrow.apr}%，期限${borrow.timeLimit}月，可获得利息收益￥${interest}元
				</#if>
			</div>
			  
			 <div style="width:600px; background:#fff; padding:15px 30px 0px 30px;border-top:1px solid #be9964; margin-top:13px;">
           <ul style="width:295px; float:left;">
             <li style="background:url(/static/img/b3.png) 0 0 no-repeat; height:22px; line-height:22px; padding-left:25px;color:#646464; font-family:'宋体';font-size:14px;">借款金额</li>
             <li style="font-size:27px;color:#be9964;">￥${borrow.account}元</li>
           </ul>
           <ul style="width:180px; float:left;">
             <li style="background:url(/static/img/b3.png) -295px 0 no-repeat; height:22px; line-height:22px; padding-left:25px;color:#646464; font-family:'宋体';font-size:14px;">年利率</li>
             <li style="font-size:27px;color:#be9964;">
             	<@numberPercent val="${borrow.varyYearRate}"; npt>${npt}</@numberPercent>
			</li>
           </ul>
           <ul style="width:125px; float:left;">
             <li style="background:url(/static/img/b3.png) -475px 0 no-repeat; height:22px; line-height:22px; padding-left:25px;color:#646464; font-family:'宋体';font-size:14px;">借款期限</li>
             <li style="font-size:27px;color:#be9964;">${borrow.timeLimit}<#if borrow.isday==0>个月<#else>天</#if></li>
           </ul>
           <div style="clear:both; border-bottom:1px dashed #d4d4d4; width:600px; height:13px;"></div>
           <ul style="width:600px; float:left; padding-bottom:17px; color:#595757;font-family:'宋体';">
             <li style="width:300px; float:left; padding:15px 0px 0px 0px;">保障方式： 本金</li>
             <li style="width:300px; float:left; padding:15px 0px 0px 0px;">还款方式：
             	<#if borrow.style == 1>分期付息
				<#elseif borrow.style == 2>到期付本息
				<#elseif borrow.style == 3>等额本金
				</#if></li>
             <li style="width:300px; float:left; padding:15px 0px 0px 0px;">发布时间： ${(borrow.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</li>
             <li style="width:300px; float:left; padding:15px 0px 0px 0px;">奖 励：	
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
          <li style=" padding-bottom:12px;color:#5c5e60; font-size:14px;font-family:'宋体';">剩余金额：￥${borrow.balance}</li>
          <li style=" padding-bottom:12px;color:#5c5e60; font-size:14px;font-family:'宋体';">账户余额：￥<#if loginUser?exists &&  loginUser.typeId==0 && loginUser.ableMoney?exists>${loginUser.ableMoney}<#else>0</#if></li>
		 <li style="margin-bottom:5px;"><span style=" font-size:16px; color:#666;">投资金额：￥</span><input type="text" class="shuru" id="tenderAbleMoney" name="tenderAbleMoney"  value="${showUserAbleMoney}" style="border:1px solid #ccc;height:30px;line-height:30px;" /></li>
	
		<#if borrow.type==12>						
		<li><span style=" font-size:16px; color:#666;">续投金额：￥</span>
		<input type="text" class="shuru" id="tenderContinueMoney" name="tenderContinueMoney" value="${showContinueTotal}" style="border:1px solid #ccc;height:30px;line-height:30px;"/></li>
		<#else>
						<li><input type="hidden" id="tenderContinueMoney" name="tenderContinueMoney" value="${showContinueTotal}" /></li>
		</#if>
						<li><input type="hidden" id="tenderContinueMoney" name="tenderContinueMoney" value="${showContinueTotal}" /></li>
					

		<#if borrow.type==12>	
						
		</#if>
	  </ul>

		<#if showButtonNameFlg==1>
			<input type="button" id="tender" style="background:#be9964;width:150px;height:30px;line-height:30px;color:#fff;font-size:16px; margin-left: 55px;margin-bottom:20px;" value="${showButtonName}"/>			
		<#else>

          <li style=" padding-bottom:12px;color:#5c5e60; font-size:14px;font-family:'宋体';" id="showTime">
						<#if borrow.status == 2>发标审核拒绝
						<#elseif borrow.status == 3>正在还款中
						<#elseif borrow.status == 4>满标审核失败
						<#elseif borrow.status == 5>此标已满
						<#elseif borrow.status == 6>过期或撤回
						<#elseif borrow.status == 7>已还完
						<#else><#if countDown==-1>招标期已过<#elseif countDown==0>此标已满</#if>
						</#if>
		</li> 
 </#if>         
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
                 <tr height="65">
                    <td style="color:#4b4b4b;font-family:'宋体';border-bottom:1px dashed #c6c6c6;">${tender.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
                    <td style="color:#4b4b4b;font-family:'宋体';border-bottom:1px dashed #c6c6c6;">${tender.username}</td>
                    <td style="color:#4b4b4b;font-family:'宋体';border-bottom:1px dashed #c6c6c6;">￥${tender.account}</td>
                 </tr>
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
