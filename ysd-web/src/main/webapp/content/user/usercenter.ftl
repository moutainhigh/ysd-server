<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的资产</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/my_assets.css">
</head>
    <body id="bg">
    <!-- 头部 -->
	<#include "/content/common/header.ftl">
	


    <!--内容区域-->
    <div id="main">
        <!--右边导航栏-->
        <#include "/content/common/user_center_left.ftl">
      
      
        <!--右侧内容区-->
        <div class="content fr">
            <div class="assets">
                <div class="assets_top">
                    <div class="assets_top_msg">
                        <table class="personal">
                            <tr >
                                <td rowspan="2" class="center"><img src="${base}/img/jcb_03.png"></td>
                                <td>您好，${loginUser.username}</td>
                                <td class="center"><a href="${base}/userCenter/profile.do"><img src="${base}/img/account_pwd.png"></a></td>
                                <td class="center"><a href="${base}/award/toGetLink.do"><img src="${base}/img/account_adv.png"></a></td>
                            </tr>
                            <tr>
                                <td>健康、财富、喜乐全部给您</td>
                                <td class="center font14"><a href="${base}/userCenter/profile.do">密码管理</a></td>
                                <td class="center font14"><a href="${base}/award/toGetLink.do">邀请好友</a></td>
                            </tr>
                        </table>
                        <table class="person_detail">
                            <tr>
                                <td style="color:#ff9000">${(selfaccountHeader.total?string("0.00"))!}</td>
                                <td>${(selfaccountHeader.ableMoney?string("0.00"))!}</td>
                                <td>${((selfaccountHeader.investorCollectionCapital!0)?string("0.00"))!}</td>
                                <td>${((selfaccountHeader.investorCollectionInterest!0)?string("0.00"))!}</td>
                                <@userAccountDetailSum_by_type_count userid="${loginUser.id}" types="interest_repayment,interest_repayment_continued" countType="add"; sum>
								<td>${sum?string("0.00")}</td>
								</@userAccountDetailSum_by_type_count>
                            </tr>
                            <tr>
                                <td>账户总额（元）</td>
                                <td>可用余额（元）</td>
                                <td>投资中本金（元）<i class="extra"></i>
                                	<div class="rule_detail">
                                		当您投资的项目未审核时，投资金额处于冻结状态，不显示金额大小；当您投资的项目通过审核时，显示投资金额，并显示为投资中金额。                         
                            		</div>
                                </td>
                                <td>待到账收益（元）</td>
                                <td>历史总收益（元）</td>
                            </tr>
                            
                        </table>
                        <div class="option">
                            <div class="tixian"><a href="javascript:void(0)" onclick="alert('请用app提现！！！')">提现</a></div>
                            <div class="charge current"><a href="${base}/payment/rechargeTo.do">充值</a></div>
                        </div>
                    </div>
                </div>
                <div class="assets_bottom">
                    <div class="nav">
                        <div ><a href="${base}/userCenter/borrowDetailList.do?type=0" >待收回款</a></div>
                        <div ><a href="${base}/userCenter/borrowDetailList.do?type=1" >已收回款</a></div>
                        <div ><a href="${base}/userCenter/borrowDetailList.do?type=2" >投资记录</a></div>
                    </div>
	        <#if type==0>
	        <form id="listForm" method="post" action="${base}/userCenter/borrowDetailList.do?type=0" >
	        <#elseif type==1>
	        <form id="listForm" method="post" action="${base}/userCenter/borrowDetailList.do?type=1" >
	        <#elseif type==2>
	        <form id="listForm" method="post" action="${base}/userCenter/borrowDetailList.do?type=2" >
	        <#else>
	        <form id="listForm" method="post" action="${base}/userCenter/borrowDetailList.do?" >
	        </#if>
                    <!--<form id="listForm" method="post" action="borrowDetailList.do" >-->
				      <!--	<input type="hidden" name="type" id="type" value="${type}"/>-->
		             <table>
		               <thead>
		               <#if type==2>
		               	<tr >
		                   <th class='one'>投资时间</th>
		                   <th class='two'>项目名称</th>
		                   <th class='three'>状态</th>
		                   <th class='four'>投资本金</th>
		                   <th class='five'>到期收益</th>
		                   <th class='six'>操作</th>
		                 </tr>
		               <#else>
		                 <tr height="38">
		                   <th class='one'>回款时间</th>
		                   <th class='two'>项目名称</th>
		                   <th class='three'>期数</th>
		                   <th class='four'>回款本金</th>
		                   <th class='five'>到期收益</th>
		                   <th class='six'>状态</th>
		                 </tr>
		                </#if>
		               </thead>
		               <tbody >
		           		<#if pager.result?size==0><tr height="72"><td colspan='6'>暂无记录</td></tr></#if>
		                <#list pager.result as value>
		                 <#if type==2>
		                 	<tr height="72">
		                    <td >${value.repaymentDate?string("yyyy-MM-dd HH:mm:ss")}</td>
		                    <td >${substring(value.title, 16, "")}</td>
		                    <td >
									<#if value.status = 3>还款中
									<#elseif value.status=0>发布审批中
									<#elseif value.status=1>正在招标中
									<#elseif value.status=2>发标审核拒绝
									<#elseif value.status=4>满标审核失败
									<#elseif value.status=5>等待满标审核
									<#elseif value.status=6>过期或撤回
									<#elseif value.status=7>已还完
									<#else>${value.status}
									</#if>
							</td>
		                    <td >${value.loanAmountFee?string.currency}</td>
		                    <td >${(value.interest)!}</td>
		                    
		                    <td >
		                    <#if value.status = 3>
		                    <a href="${base}/borrow/tzborrowDetail.do?id=${value.id}">查看详情</a>
		                    <#elseif value.status=7>
		                    <a href="${base}/borrow/tzborrowDetail.do?id=${value.id}">查看详情</a>
		                   <#else>
		                  	 --
		                    </#if>
		                    </td>
		                 
		                 
		                 
		                  </tr>
		                <#else>
		                  <tr height="72">
		                    <td >${value.repaymentTime?string("yyyy-MM-dd")}</td>
		                    <td >${substring(value.borrowName, 16, "...")}</td>
		                    <td >${(value.borrowPeriods+1)}/<#if value.borrowtype==2>1<#elseif value.borrowtype==5>${value.borrowDivides}<#else>${value.divides}</#if></td>
		                    <td >${value.waitAccount}</td>
		                    <td >${value.waitInterest}</td>
		                    <td >
			                    <#if value.status==0>未收
								<#elseif value.status==1>已收
							     <#else>${value.status}
								</#if>
							</td>
		                  </tr>
		                 </#if>
		                </#list>
		                
		               </tbody>
		             </table>
		             <@pagination pager=pager >
							<#include "/content/borrow/pager.ftl">
					 </@pagination> 
					 </form>
		        </div>

            </div>
        </div>
        <div class="clear"></div>
    </div>

    <!--底部-->
    <#include "/content/common/footer-center.ftl">
    
</body>
<script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${base}/js/my_assets.js"></script>
<script src="${base}/js/common.js"></script>
<script type="text/javascript">
function getQueryString(name) { 
var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
var r = window.location.search.substr(1).match(reg); 
if (r != null) return unescape(r[2]); return null; 
} 
$(function(){
	$("#header_wdzh").addClass("current");
	$(".center_left_wdzc").addClass("current");
	$("#header_gywm").find('a').css('border',0);
	var type=getQueryString('type');
	if(type!=0){
	$(".assets_bottom .nav").find('div').eq(type).addClass('current');
	}else{
	$(".assets_bottom .nav").find('div').eq(0).addClass('current');
	}
	$(".extra").mouseover(function() {
		$(".rule_detail").show();
	});
	$(".extra").mouseout(function() {
		$(".rule_detail").hide();
	});
});

</script>
</html>
