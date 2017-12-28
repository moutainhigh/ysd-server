<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>投资确认</title>
    <link rel="stylesheet" href="${base}/css/common.css">
    <link rel="stylesheet" href="${base}/css/confirm_investment.css">
</head>
<body>
  <!-- 头部 -->
	<#include "/content/common/header.ftl">
    <!--内容1部分-->
    <div class="content1">
        <div class="title">投资信息</div>
        <div class="detail">
            <table>
                <tr>
                    <td class="font_red">${borrow.name}</td>
                    <td class="font_red">${(tenderAbleMoney+tenderContinueMoney)}</td>
                    <td>
                    	<#if borrow.style == 1>分期付息
						<#elseif borrow.style == 2>到期付本息
						<#elseif borrow.style == 3>等额本金
						</#if>
                    </td>
                    <td class="font_red font30">${dqsy}</td>
                    <td>
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
                    </td>
                </tr>
                <tr>
                    <td>项目名称</td>
                    <td>投资金额</td>
                    <td>还款方式</td>
                    <td>到期收益</td>
                    <td>额外奖励</td>
                </tr>
            </table>
        </div>
        <div class="payment_date">
            	还款日期：<span>${borrow.finalRepayDate?string("yyyy-MM-dd")}</span>
        </div>
    </div>
    <!--内容2部分-->
    <div class="content2">
        <div class="title">支付信息 <span>系统会自动为您选择使用红包最大收益的匹配方案，若您想使用其余红包，则需要增加相应的投资金额。                                      </span></div>
        <div class="red_bag">
            <div class="red_bag_warp">
                <span class="select fr selected"></span>
                <div class="redbag_model">
                    <div class="title">
                        ￥<span class="redbag_value">200</span><span class="redbag_name">注册红包</span>
                    </div>
                    <div class="use_detail">
                        <div class="deadline">有效期：截止10月31日</div>
                        <div class="project_deadline">项目期限：满60天可用</div>
                        <div class="use_conditional">投资金额：满5000元可用</div>
                    </div>
                </div>
            </div>
            <div class="red_bag_warp">
                <span class="select fr"></span>
                <div class="redbag_model">
                    <div class="title">
                        ￥<span class="redbag_value">200</span><span class="redbag_name">注册红包</span>
                    </div>
                    <div class="use_detail">
                        <div class="deadline">有效期：截止10月31日</div>
                        <div class="project_deadline">项目期限：满60天可用</div>
                        <div class="use_conditional">投资金额：满5000元可用</div>
                    </div>
                </div>
            </div>
            <div class="red_bag_warp">
                <span class="select fr"></span>
                <div class="redbag_model">
                    <div class="title">
                        ￥<span class="redbag_value">200</span><span class="redbag_name">注册红包</span>
                    </div>
                    <div class="use_detail">
                        <div class="deadline">有效期：截止10月31日</div>
                        <div class="project_deadline">项目期限：满60天可用</div>
                        <div class="use_conditional">投资金额：满5000元可用</div>
                    </div>
                </div>
            </div>
            <div class="red_bag_warp">
                <span class="select fr"></span>
                <div class="redbag_model">
                    <div class="title">
                        ￥<span class="redbag_value">200</span><span class="redbag_name">注册红包</span>
                    </div>
                    <div class="use_detail">
                        <div class="deadline">有效期：截止10月31日</div>
                        <div class="project_deadline">项目期限：满60天可用</div>
                        <div class="use_conditional">投资金额：满5000元可用</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="details">
            <div class="left">
                <div class="top">
                    <span class="left">当前投资总额为 <strong>1000.00</strong>元  </span>
                    <span class="right">红包支付 <strong>500.00</strong>元</span>
                </div>
                <div class="bottom">
                    <span class="left">账户可用余额共<strong>1000.00</strong>元</span>
                    <span class="right">还需充值<strong>1000.00</strong>元</span>
                </div>
            </div>
            <div class="right">
                <div class="recharge">充值</div>
                <div class="default">默认选择</div>
            </div>
        </div>
        <div class="pay">
            <form action="#">
                <table>
                    <tr>
                        <td>请输入交易密码：</td>
                        <td colspan="2"> <input type="text" class="phone_num" title="phone_num"></td>
                    </tr>
                    <tr>
                        <td>请输入验证码：</td>
                        <td><input style="width: 162px;" type="text" class="identify_num" title="identify_num"></td>
                        <td ><img style="height: 38px;" src="${base}/img/identify_img.png" alt="" class="identify_img"></td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <input type="submit" value="确认并支付">
                        </td>
                    </tr>
                </table>
            </form>
            <div class="tips fr">温馨提示：本项目到期后本金及收益将自动归还至您的账户余额</div>
        </div>
    </div>
 	<!-- 尾部 -->
	<#include "/content/common/footer.ftl">
</body>
<script src="${base}/js/jquery-1.11.3.min.js"></script>
<script src="${base}/js/common.js"></script>
<script src="${base}/js/product_detail_invest.js"></script>
</html>
