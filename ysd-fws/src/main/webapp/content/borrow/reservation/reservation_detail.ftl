<!DOCTYPE html>
<html>
 <head>
	<title>${Application ["qmd.setting.name"]}-我的账户-项目预约管理-预约项目列表</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>
	<style>
		.kaqu_w70_zqzr {
		    background: url("${base}/static/img/right.png") no-repeat scroll 125px 8px rgba(0, 0, 0, 0);
		    color: #363636;
		    float: left;
		    font-size: 20px;
		    width: 115px;
		}
	</style>
</head>
<body class="body_bg">

	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_agency.ftl">
<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="${base}/userCenter/index.do">账户中心</a></li>
			<li><a href="${base}/reservation/list.do">项目预约管理</a></li>
            <li>预约项目列表</li>
			
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
	<div class="search shadowBread">	
			
			 项目总金额：￥${borrow.account} 元          已预约金额： ${reservatoinTotalMoney?string.currency}
			 	<#if borrow.reservation ==3 || borrow.reservation ==5 >
			 		预约成功金额：${reservatoinFinishTotalMoney?string.currency}
			 	</#if>
	</div>
				<!-- data list -->
				 <div class="mainWrap">       
       	 <!--table start!-->
      	  <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
		            <thead>
	             		<tr>
							<td>编号</td>
							<td>投资人</td>
							<td>预约金额</td>
							<td>是否完成预约</td>
							<td>完成预约时间</td>
							<td>预约成功奖励</td>
							<td>扣除定金</td>
						</tr>
						</thead>
						<tbody>
						<#list reservationList as reservation>
							<#assign flag = "">
							<#if reservation_index %2 != 0>
								<#assign flag = "td1">
							</#if>
								<tr height="39">
									<td class="${flag}">${((reservationList?size)-reservation_index)}</td>
									<td class="${flag}">${(reservation.username)!}</td>
									<td class="${flag}">${(reservation.reservationMoney?string.currency)!}</td>
									<td class="${flag}"><#if reservation.status == 0 || reservation.status == 3>否<#else>是</#if></td>
									
									<td class="${flag}"><#if reservation.status != 0 && reservation.status != 3>${reservation.modifyDate?string("yyyy-MM-dd")}<#else>--</#if></td>
									<td class="${flag}"><#if reservation.status != 0 && reservation.status != 3>${reservation.awardMoney?string.currency}<#else>--</#if></td>
									<td class="${flag}"><#if reservation.status == 3>${reservation.downPaymentMoney?string.currency}<#else>--</#if></td>
									
								</tr>
						</#list>
						</tbody>
					</table>
   <!--table end!-->               
    </div>
    
</div>
<#include "/content/common/footer.ftl">
</body>
</html>
