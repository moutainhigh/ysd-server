<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-${borrow.name}</title>
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
			alertMessage("请输入投标金额");
			return false;
		}
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
	         	str +='  <span class="bgc1" style="border-radius:5px;padding:5px;display: inline-block;color:#fff;">';
	        	str +=CDay;
	        	str +='</span>天';
	        }
	       	str +='  <span class="bgc1" style="border-radius:5px;padding:5px;display: inline-block;color:#fff;">';
	        str +=CHour;
	        str +='</span> 小时  <span class="bgc1" style="border-radius:5px;padding:5px;display: inline-block;color:#fff;">';
	        str +=CMinute;
	        str +='</span> 分  <span class="bgc1" style="border-radius:5px;padding:5px;display: inline-block;color:#fff;">';
	        str +=CSecond;
	        str +='</span>秒';
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
		alertMessage("请登录！");
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
<div class="content" style="width:1200px; margin:0 auto; padding-bottom:40px;color:#666;">
<div style="padding:22px 0px 12px 0px;font-size:18px;">首页 > 项目中心 ><span class="color">${borrow.name}</span></div>
<div style="overflow:hidden;">
    <div class="left" style="float:left;width:854px;">
      
      	<#if borrow.type==16>
         <div style="width:854px;height:370px;border:1px solid #eeebeb;background:#fff url(img/newplayer.png) no-repeat top right;position:relative;">
        <#else>
        <div style="width:854px;height:370px;border:1px solid #eeebeb;background:#fff;position:relative;">
         </#if>
          <h3 style="font-size:18px;height:56px;line-height:56px;padding-left:18px;font-weight:normal;border-bottom:1px solid #e6e6e6;">${borrow.name}</h3>
          <ul style="overflow:hidden;padding:45px 0 35px;border-bottom:1px solid #e6e6e6;font-size:16px;">
            <li style="float:left;width:245px;text-align:center;">
              <p>项目利率</p><p class="color" style="font-size:30px;height:64px;line-height:64px;border-right:1px solid #e6e6e6;"><@numberPercent val="${borrow.baseApr}"; npt>${npt}</@numberPercent><#if borrow.awardApr gt 0><span style="background:url(../img/9.png) no-repeat 0 20px;color:#fff;font-size:16px;padding:0 10px;font-weight:normal;width: 59px;  display: inline-block;">+<@numberPercent val="${borrow.awardApr}"; npt>${npt}</@numberPercent></span></p></#if>
            </li>
            <li style="float:left;width:180px;text-align:center;">
              <p>理财期限</p>
              <p style="font-size:30px;height:64px;line-height:64px;border-right:1px solid #e6e6e6;">${borrow.timeLimit}<span style="font-size:18px;"><#if borrow.isday==0>个月<#else>天</#if></span></p>
            </li>
            <li style="float:left;width:240px;text-align:center;">
              <p>项目总额</p>
              <p style="font-size:30px;height:64px;line-height:64px;border-right:1px solid #e6e6e6;"><span style="font-size:18px;">￥</span>
              <#if borrow.account?number gt 10000>
            	 ${((borrow.account?eval)*0.0001)?string("0.00")}万
         	  <#else>
         		 ${borrow.account?eval?string("0.00")}
              </#if>
              </p>
            </li>
            <li style="float:left;width:180px;text-align:center;">
              <p>起投金额</p>
              <p style="font-size:30px;height:64px;line-height:64px;"><span style="font-size:18px;">￥</span>${(borrow.lowestAccount)!}</p>
            </li>
          </ul>
          <ul style="overflow:hidden;padding:38px 0 38px 40px;font-size:16px;line-height:36px;">
            <li style="float:left;width:360px;">
              收益方式：  <span style="color:#4c4c4c;;">
				<#if borrow.style == 1>分期付息
				<#elseif borrow.style == 2>到期付本息
				<#elseif borrow.style == 3>等额本金
				</#if>
			</span> 
            </li>
            <li style="float:left;width:440px;">
             	 发布日期：  <span style="color:#4c4c4c;">${(borrow.verifyTime?string("yyyy-MM-dd HH:mm"))!}</span> 
            </li>
         <#--    
	        <li style="float:left;width:360px;">
	             	 合作机构：  <span style="color:#4c4c4c;;">${(borrow.agencyName)!}</span> 
            </li>
			        
			            <li style="float:left;width:440px;">
			              安全保障：  <span style="color:#4c4c4c;;">逾期10天，车商及平台处置质押物</span> 
			            </li>
            -->
          </ul>
      </div>
      <div style="width:854px;border:1px solid #eeebeb;background:#fff;color:#666;margin:10px 0 50px;">
        <h3 style="width:139px;font-weight:normal;font-size:16px;height:48px;line-height:48px;text-align:center;border-top:3px solid #ff7f00;border-right:1px solid #e6e6e6;background:#fff;">项目信息</h3>
        <div style="padding:10px;font-size:14px;">
          <h4 style="font-size:20px;color:#333;font-weight:normal;border-bottom:1px solid #e6e6e6;height:48px;line-height:48px;">项目详细信息</h4>
          <div style="overflow:hidden;">
          <h5 style="font-size: 16px;margin-top: 10px;float:left;width:100px;padding-left:20px;">债务人信息</h5>
          <p style="line-height:30px;padding:10px 0;float:left;width:700 px;">${(borrow.eachTime)!}</p>
         </div>
         <div style="overflow:hidden;">
          <h5 style="font-size: 16px;margin-top: 10px;float:left;width:100px;padding-left:20px;">项目描述</h5>
          <p style="line-height:30px;padding:10px 0;float:left;width:700px;">${borrow.content}</p>
          </div>
         <div style="overflow:hidden;">
          <h5 style="font-size: 16px;margin-top: 10px;float:left;width:100px;padding-left:20px;">资金用途</h5>
          <p style="line-height:30px;padding:10px 0;float:left;width:700px;">${(borrow.useReason)!}</p>
          </div>
         <div style="overflow:hidden;">
          <h5 style="font-size: 16px;margin-top: 10px;float:left;width:100px;padding-left:20px;">还款来源</h5>
          <p style="line-height:30px;padding:10px 0;float:left;width:700px;">${(borrow.borStages)!}</p>
         </div>
         <!--
          <h4 style="font-size:20px;color:#333;font-weight:normal;border-bottom:1px solid #e6e6e6;height:48px;line-height:48px;">资料审核</h4> 
             <#if verifyMessJson>
          	<ul style="overflow:hidden;text-align:center;font-size:12px;margin:25px 0;">
          	 <#if verifyMessJson.idCard==1>
            <li style="float:left;margin:0 10px;">
              <div><a href="#" style="width:44px;height:44px;display:inline-block;background:url(/img/icons.png) no-repeat 0 0;"></a></div>
              <div style="margin-top:10px;"><a href="#" style="color:#666;margin-top:12px;">身份证</a></div>
            </li>
            </#if>
            <#if verifyMessJson.household==1>
            <li style="float:left;margin:0 10px;">
              <div><a href="#" style="width:44px;height:44px;display:inline-block;background:url(/img/icons.png) no-repeat -82px 0;"></a></div>
              <div style="margin-top:10px;"><a href="#" style="color:#666;margin-top:12px;">户口本</a></div>
            </li>
            </#if>
            <#if verifyMessJson.zhengXin==1>
            <li style="float:left;margin:0 10px;">
              <div><a href="#" style="width:44px;height:44px;display:inline-block;background:url(/img/icons.png) no-repeat -164px 0;"></a></div>
              <div style="margin-top:10px;"><a href="#" style="color:#666;margin-top:12px;">征信报告</a></div>
            </li>
           </#if>
            <#if verifyMessJson.income==1>
            <li style="float:left;margin:0 10px;">
              <div><a href="#" style="width:44px;height:44px;display:inline-block;background:url(/img/icons.png) no-repeat -246px 0;"></a></div>
              <div style="margin-top:10px;"><a href="#" style="color:#666;margin-top:12px;">收入证明 </a></div>
            </li>
            </#if>
            <#if verifyMessJson.anCase==1>
            <li style="float:left;margin:0 10px;">
              <div><a href="#" style="width:44px;height:44px;display:inline-block;background:url(/img/icons.png) no-repeat -328px 0;"></a></div>
              <div style="margin-top:10px;"><a href="#" style="color:#666;margin-top:12px;">在执行案件查询 </a></div>
            </li>
            </#if>
            <#if verifyMessJson.cardDriving==1>
            <li style="float:left;margin:0 10px;">
              <div><a href="#" style="width:44px;height:44px;display:inline-block;background:url(/img/icons.png) no-repeat -408px 0;"></a></div>
              <div style="margin-top:10px;"><a href="#" style="color:#666;margin-top:12px;">车辆行驶证 </a></div>
            </li>
             </#if>
             <#if verifyMessJson.gouZhi==1>
            <li style="float:left;margin:0 10px;">
              <div><a href="#" style="width:44px;height:44px;display:inline-block;background:url(/img/icons.png) no-repeat -491px 0;"></a></div>
              <div style="margin-top:10px;"><a href="#" style="color:#666;margin-top:12px;">购置置税凭证 </a></div>
            </li>
             </#if>
             <#if verifyMessJson.guJia==1>
            <li style="float:left;margin:0 10px;">
              <div><a href="#" style="width:44px;height:44px;display:inline-block;background:url(/img/icons.png) no-repeat -572px 0;"></a></div>
              <div style="margin-top:10px;"><a href="#" style="color:#666;margin-top:12px;">车辆评估报告</a></div>
            </li>
            </#if>
             <#if verifyMessJson.gcfp==1>
            <li style="float:left;margin:0 10px;">
              <div><a href="#" style="width:44px;height:44px;display:inline-block;background:url(/img/icons.png) no-repeat -654px 0;"></a></div>
              <div style="margin-top:10px;"><a href="#" style="color:#666;margin-top:12px;">购车发票 </a></div>
            </li>
            </#if>
            <#if verifyMessJson.jdczs==1>
            <li style="float:left;margin:0 10px;">
              <div><a href="#" style="width:44px;height:44px;display:inline-block;background:url(/img/icons.png) no-repeat -736px -1px;"></a></div>
              <div style="margin-top:10px;"><a href="#" style="color:#666;">机动车登记证书 </a></div>
            </li>
          </#if>
           </ul>
          </#if>
          -->
          <h4 style="font-size:20px;color:#333;font-weight:normal;border-bottom:1px solid #e6e6e6;height:48px;line-height:48px;">项目资料公示<span style="color:#fd7c1a;font-size:14px;margin-left:15px;">100%实物拍摄，盗图必究</span></h4>
          <div style="margin:20px 0;">
          <#if (carImgList?size > 0)>	
		       <#list carImgList as cari>
		            <img width="814" src="<@imageUrlDecode imgurl="${cari.url}"; imgurl>${imgurl}</@imageUrlDecode>"  />
            		<p style="text-align:center;margin:10px 0 20px;" width='813' height='603' >${(cari.name)!}</p>
			                 
			    </#list>
	        </#if> 
          </div>
          <h4 style="font-size:20px;color:#333;font-weight:normal;border-bottom:1px solid #e6e6e6;height:48px;line-height:48px;">还款计划</h4>
          <div style="padding:25px 0;">
              <table width="100%" cellpadding="0" cellspacing="0" style="background:#fff; border:1px solid #c6c6c6; border-right:none;border-bottom:none; color:#666;">
                <thead align="center">
                   <tr height="40">
                      <th style="color:#4c4c4c; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">期数</th>
                      <th style="color:#4c4c4c; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">还款日期</th>
                      <th style="color:#4c4c4c; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">还款总额</th>
                      <th style="color:#4c4c4c; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">还款本金</th>
                      <th style="color:#4c4c4c; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">还款利息</th>
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
                     <td style="color:#fd7c1a;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">${repaymentInfo.account?string.currency}</td>
                     <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${repaymentInfo.capital?string.currency}</td>
                     <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${repaymentInfo.interest?string.currency}</td>
                  </tr>
        		</tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
    <div class="right" style="width:335px;float:right;font-size:14px;">
    
    <#if borrow.status == 1>
       <div style="width:335px;height:370px;background:#fff;border:1px solid #e6e6e6;">
         <div style="padding:16px 12px;border-bottom:1px solid #e6e6e6;">募集倒计时 &nbsp;&nbsp;<div id="showTime" style="display:inline-block;"></div>
        </div>
        <p style="text-align:center;font-size:16px;padding:30px 0 10px;">已融资<span class="color"><span class="detail">${borrow.schedule}</span>%</span>&nbsp;&nbsp;&nbsp;&nbsp;剩余 <span class="color">
         <#if borrow.balance?number gt 10000>
            	 ${((borrow.balance?eval)*0.0001)?string("0.00")}</span>万
         <#else>
         		 ${borrow.balance?eval?string("0.00")}</span>元
        </#if></p>
         <div class="schedule" style="padding:15px 40px;"><i style="width: 258px; border-radius: 5px; height: 12px; background: #ff7f00;display: inline-block;"></i></div>
        <form id="borrowRaiseInvestForm" action="${base}/borrow/toInvest.do"  method="post" >
		<input type="hidden" id="bId" name="bId" value="${borrow.id}"/>
        <div style="text-align:center;padding:10px 0;"><input type="text"  id="tenderAbleMoney" name="tenderAbleMoney"  value="${showUserAbleMoney}"onfocus="if(this.value == '输入金额为100的整数倍'){this.value = ' '}" onblur="if(this.value == ' '){this.value = '输入金额为100的整数倍'}"style="width:256px;height:56px;border-radius:15px;background:#efefef;line-height:56px;text-align:center;font-size:16px;font-family: 'Microsoft Yahei';" /></div>
        <li><input type="hidden" id="tenderContinueMoney" name="tenderContinueMoney" value="${showContinueTotal}" /></li>
        <#if loginUser>
        <p style="text-align:center;padding:10px 0;color:#fd7c1a;">可用金额：${userAbleMoney?string.currency}</p>
        </#if>
         <#if showButtonNameFlg==1>
        <div style="text-align:center;padding:10px 0;">
        	<input type="button" id="tender" class="bgc1" style="color:#fff; display:inline-block; width:228px; height:38px; line-height:38px;  font-size:16px;border-radius:10px;text-align:center;" value="${showButtonName}"/>			
        </div>
        </#if>
       </div>
 
 		</form>            
       <#else>
		<div style="width:335px;height:370px;background:#fff;border:1px solid #e6e6e6;position:relative;">
         <div style="padding:16px 12px;border-bottom:1px solid #e6e6e6;font-size:18px;color:#fd7c1a;">目前状态</div>
        <p style="text-align:center;margin-top:32px;font-size:18px;">还款总收益</p>
        <p style="text-align:center;color:#fd7c1a;margin-top:36px;"><span style="font-size:30px;">${(gains)!}</span> 元</p>
        <p style="text-align:center;margin-top:20px;">共有<span style="color:#fd7c1a;">${tenderNum}</span>人参与投资</p>
        <div style="text-align:center;margin-top:36px;"><a href="#" style="color:#666; display:inline-block;background:#efefef; width:290px; height:38px; line-height:38px;  font-size:16px;border-radius:10px;text-align:center;">
        				<#if borrow.status == 2>发标审核拒绝
						<#elseif borrow.status == 3>还款中
						<#elseif borrow.status == 4>满标审核失败
						<#elseif borrow.status == 5>审核中
						<#elseif borrow.status == 6>过期或撤回
						<#elseif borrow.status == 7>已还完
						<#else><#if countDown==-1>招标期已过<#elseif countDown==0>此标已满</#if>
						</#if>
		</a></div>
       	<#if borrow.status == 3> <div style="width:140px;height:136px;background:url(/img/huai.png) no-repeat;position:absolute;right:0;bottom:35px;"></div></#if>
       </div>
       </#if>
       <div style="width:335px;padding-bottom:20px;background:#fff;border:1px solid #e6e6e6;margin-top:10px;">
         <div style="padding:16px 12px;border-bottom:1px solid #e6e6e6;font-size:18px;">投资记录</div>
         <#if  borrowTenderList! && borrowTenderList?size gt 0 && (borrow.status ==1 ||borrow.status ==3 ||borrow.status ==5 ||borrow.status ==7 )>
         <ul style="overflow:hidden;padding:0 12px;">
         	<#-->
         	 <#list borrowTenderList as tender>
		        <#if tender.autoTenderStatus !=1>
                <li style="width:310px;margin:30px 0 15px;">
                  <dl style="overflow: hidden;">
                    <dt style="width:65px;height:65px;background:#ededed;border-radius:50%;margin-right: 12px;float:left;"><img src="/img/person.png" style="width:65px;height:65px;border-radius:50%;"></dt>
                    <dd style="width:233px;font-size:14px;float:left;color:#939394;">
                      <p style="margin-bottom:8px;"><span style="color:#7c96e8;font-size:16px;margin-right:16px;font-weight:bold;">急先锋</span><span>第一位投资用户</span></p>
                      <p style="line-height:20px;"><span class="proInfo"><span class="color" style="">${substringWord(tender.username,3,"*****")}${tender.username?substring(8,11)}</span><span>&nbsp;获得 <span class="color" style="font-weight:bold;">
                      ${borrow.tzxf}
                      </span>&nbsp;元现金奖励</span></span></p>
                    </dd>
                  </dl>
                </li>
                 <#break>
		           </#if>
	             </#list>  
	           -->  
	             
                <li style="width:310px;margin:30px 0 15px;">
                  <dl style="overflow: hidden;">
                    <dt style="width:65px;height:65px;background:#ededed;border-radius:50%;margin-right: 12px;float:left;"><img src="/img/person1.png" style="width:65px;height:65px;border-radius:50%;"></dt>
                    <dd style="width:233px;font-size:14px;float:left;color:#939394;">
                      <p style="margin-bottom:8px;"><span style="color:#7c96e8;font-size:16px;margin-right:16px;font-weight:bold;">投资土豪</span><span> 单项目单笔最高的用户</span></p>
                      <p style="line-height:20px;"><span class="proInfo"><span class="color"> ${substringWord(maxUsername,3,"*****")}${maxUsername?substring(8,11)}</span> 投资 <span style="color:#fd7c1a;font-weight:bold;">${maxMoney}</span>
                        	元 <#if borrow.status==1>暂时领先，您还有机会</#if></span>
                      <span>获得 <span class="color" style="font-weight:bold;">
                      <#--<@listing_keyValue sign="tzth_award"; kv>${kv}</@listing_keyValue>-->${borrow.tzth}
                      </span>&nbsp;元现金奖励</span></p>
                    </dd>
                  </dl>
                </li>
               <#if borrow.status == 3 || borrow.status == 5 || borrow.status ==7>
              <li style="width:310px;margin:30px 0 15px;">
                <dl style="overflow: hidden;">
                  <dt style="width:65px;height:65px;background:#ededed;border-radius:50%;margin-right: 12px;float:left;"><img src="/img/person2.png" style="width:65px;height:65px;border-radius:50%;"></dt>
                  <dd style="width:233px;font-size:14px;float:left;color:#939394;">
                    <p style="margin-bottom:8px;"><span style="color:#7c96e8;font-size:16px;margin-right:16px;font-weight:bold;">收官大哥</span><span>最后一位投资的用户</span></p>
                    <p style="line-height:20px;"><span class="proInfo"><span class="color" style="">${substringWord(borrowTenderList[(borrowTenderList?size-1)].username,3,"*****")}${borrowTenderList[(borrowTenderList?size-1)].username?substring(8,11)}</span><span>&nbsp;获得 <span style="color:#fd7c1a;font-weight:bold;">
                    <#--<@listing_keyValue sign="sgdg_award"; kv>${kv}</@listing_keyValue>-->${borrow.sgdg}
                    </span>&nbsp;元现金奖励</span></span></p>
                  </dd>
                </dl>
              </li>
              <#else>
               <li style="width:310px;margin:30px 0 15px;">
                <dl style="overflow: hidden;">
                  <dt style="width:65px;height:65px;background:#ededed;border-radius:50%;margin-right: 12px;float:left;"><img src="/img/person2.png" style="width:65px;height:65px;border-radius:50%;"></dt>
                  <dd style="width:233px;font-size:14px;float:left;color:#939394;">
                    <p style="margin-bottom:8px;"><span style="color:#7c96e8;font-size:16px;margin-right:16px;font-weight:bold;">收官大哥</span><span>最后一位投资的用户</span></p>
                    <p style="line-height:20px;"><span class="proInfo"><span class="color" style="">席位空缺中，剩余<span style="color:#fd7c1a">${borrow.balance}</span> 元，赶紧投资</span><span>获得 <span style="color:#fd7c1a;font-weight:bold;"><#--><@listing_keyValue sign="sgdg_award"; kv>${kv}</@listing_keyValue>-->${borrow.sgdg}</span>&nbsp;元现金奖励</span></span></p>
                  </dd>
                </dl>
              </li>
              </#if>
          </ul>
          </#if>
            <form id="listForm" action="${base}/borrow/detail.do"  method="post" >
          	<input type="hidden" id="bId" name="bId" value="${borrow.id}"/>
          <div style=" width:335px; margin:20px auto 0;color:#666;font-size:14px;">
             <table width="100%" cellpadding="0" cellspacing="0" border="0">
               <thead bgcolor="#f5f5f5">
                 <tr height="38">
                   <th style="font-size:16px;font-weight: normal;">时间</th>
                   <th style="font-size:16px;font-weight: normal;">投资人</th>
                   <th style="font-size:16px;font-weight: normal;">金额</th>
                 </tr>
               </thead>
               <tbody align="center">
                <#list pager.result as tender>
                  <tr height="72">
                    <td width='90' style="border-bottom:1px solid #e6e6e6;">${tender.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                    <td style="border-bottom:1px solid #e6e6e6;">${substringWord(tender.username,3,"*****")}${tender.username?substring(8,11)}<#if tender.clientType !=0><img src="/img/a17.png" width="12" height="18" style="position:relative; left:3px; top:-2px;"></#if></td>
                    <td style="border-bottom:1px solid #e6e6e6; ">${tender.showAccount?string.currency}</td>
                  </tr>
                </#list>
               </tbody>
             </table>
             <@pageFlip pager=pager>
				<#include "/content/common/pager.ftl">
			</@pageFlip>
           </div>
           </from>
       </div>
    </div>
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
	
$("#header_xmzx").addClass("hq");
  
    var animation_loop;
   var target=parseInt($('.detail').html());
   var i=0;
    function a(){
       $('.schedule i').width(258*0.01*i);
       if (i == target)
        {
          if(target == 100){$('.schedule i').css('background','#7b94e8')};
        return clearInterval(animation_loop)
      }
       else i++;
      }
  a();
  if(typeof animation_loop != undefined) clearInterval(animation_loop);
  animation_loop = setInterval(a, 30);
</script>
<#-->
<script>
	var status = ${borrow.status};
	function userPro(){
		var table = $('#investTable tbody').children();
		var n = table.size();
		if (n) {
			$('#proBox .proInfo').eq(0).html('恭喜 <span style="color:#be9964">' + table.eq(0).find('td').eq(0).html() + ' </span>'); 
			if (status == 3 || status == 7) {
				$('#proBox .proInfo').eq(2).html('恭喜 <span style="color:#be9964">' + table.eq(n-1).find('td').eq(0).html() + ' </span>'); 
			} else {
				$('#proBox .proInfo').eq(2).html('席位空缺中，赶紧投资'); 
			}
			for (var i=0;i<n;i++) {





			}
			if (status == 3 || status == 7) {
				$('#proBox .proInfo').eq(1).html('<span style="color:#be9964">' + 137****821 + '</span> 投资 <span style="color:#be9964">' + 46000 + '</span>元暂时领先，您还有机会');
			} else {
				$('#proBox .proInfo').eq(1).html('恭喜 <span style="color:#be9964">' + 137****821 + '</span> 投资 <span style="color:#be9964">' + 46000 + '</span>，');
			}
		} else {
			$('#proBox .proInfo').html('席位空缺中，赶紧投资');
		}
	}
	userPro();
</script>

<#-->

</body>
</html>
