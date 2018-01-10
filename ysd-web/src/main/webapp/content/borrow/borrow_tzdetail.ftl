<!DOCTYPE html>
<html lang="en">
<head>
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-投资记录详情</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/invest_record_detail.css">
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
            <div class="invest_detail">
                <div class="back">
                    <a href="${base}/userCenter/borrowDetailList.do?type=2">
                        <span>返回</span>
                    </a>
                </div>
                <div class="invest_detail_desc1">
                    <span>${(borrowTender.title)!}</span>
                  
                    <a href="${base}/borrow/detail.do?bId=${borrowTender.borrowId}"><span>更多项目信息</span></a>
                </div>
                <table class="invest_detail_desc2">
                    <tr>
                        <td>投资本金（元）</td>
                        <td>到期收益（奖励收益）</td>
                        <td>投资时间</td>
                    </tr>
                    <tr>
                        <td>${(borrowTender.account)!}</td>
                        <td>${(borrowTender.interest)!}</td>
                        <td>${borrowTender.createDate?string("yyyy-MM-dd")}</td>
                    </tr>
                </table>
                <div class="invest_detail_desc3">
                	<img src="../img/repayment.png" class="repayment">
                    <div class="top">
                        <span>支付方式：账户余额支付${(borrowTender.ableAmount)!}  红包支付${(borrowTender.hongbaoAmount)!}</span>
                        <span class="right">还款方式：
                        <#if borrowTender.style == 1>分期付息
						<#elseif borrowTender.style == 2>到期付本息
						<#elseif borrowTender.style == 3>等额本金
						</#if>
                        </span>
                    </div>
                    <div class="bottom">
                        <label for="pro">
                        	<span class="right" ><a id="pro" target='_black' href="${base}/borrow/borrowAgreement.do?borrow.id=${borrowTender.borrowId}&id=${borrowTender.id}">投资协议查看</a></span>
                    	</label>
                    </div>
                </div>
                <div class="invest_detail_desc4">
                    <table class='invest_detail_desc4Title'>
                        <tr>
                            <td>回款时间</td>
                            <td>回款本金</td>
                            <td>当前利息</td>
                            <td>回款至</td>
                            <td>状态</td>
                        </tr>
                     </table>
                     <div class='invest_detail_desc4Cont'>
	                     <table>
	                        <#list userRepDetailList as urd>
	                        <tr class='last_child'>
	                            <td width="209px">${urd.repaymentDate?string("yyyy-MM-dd")}</td>
	                            
	                            <td width="209px">
	                            <#if urd.waitAccount == 0>
								    到期还本金
								<#else>
								 ${urd.waitAccount}
	                            </#if>
	                         
	                            </td>
	                            <td width="209px">${urd.waitInterest}</td>
	                            <td width="159px">账户余额</td>
	                            <td width="109px">
	                            <#if urd.status==0>	
	            				待回款
	            				 <#else>
	            				已回款
	            			</#if></td>
	                        </tr>
	                         </#list>
	                    </table>
                    </div>
                </div>
                <div class="useless"></div>
            </div>
        </div>
        <div class="clear"></div>
    </div>

    <!--底部-->
	<#include "/content/common/footer-center.ftl">
    <script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${base}/js/my_assets.js"></script>
    <script src="${base}/js/common.js"></script>
</body>
<script type="text/javascript">
$(function(){
	$("#header_wdzh").addClass("current");
	$('#header_gywm').find('a').css('border',0);
	$(".center_left_wdzc").addClass("current");
	});
	</script>
</html>
