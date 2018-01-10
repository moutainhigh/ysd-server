<!DOCTYPE html>
<html>
  <head>
  <meta charset="gb2312" />
  <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-投资者-债权转让协议</title>
	<#include "/content/common/meta.ftl">
  </head>
  
  <body>
<#include "/content/common/header.ftl">
<div class="content">
<div style="width:955px; float:none; background:#fff; margin:0 auto;">
        
        
        <div style="width:670px; margin:0 auto; padding:0px 15px 0px 18px; ">
                    
          <div style=" margin-top:10px;">

			<div style="padding:30px;">
				<h3 style="color:#363636; font-size:24px; border-bottom:1px solid #e0e0e0; font-weight:normal; padding-bottom:5px; margin-bottom:20px;text-align:center;">
					<strong>借款协议</strong>
				</h3>
				<div style="color:#363636; font-size:14px; line-height:25px;">
	
	<div class="bd" style="position:relative;z-index:9999;">
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:222.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">编号：&nbsp;${substring(agreementId, 4, "")}年&nbsp;${borrow.id}号</span><span style="font-family:仿宋;font-size:12.0000pt;"><br />
</span><span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;甲方（借款人）：</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${borrow.realName!'--'}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;身份证号码：</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${borrow.userCardId!'--'}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;居住地址：&nbsp;</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;邮箱：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${(borrow.userEmail)!}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;乙方（出借人）：</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${user.realName!'--'}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;身份证号码:</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${user.cardId!'--'}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;联系地址：</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${(user.areaStore)!}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;邮箱：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${(user.email)!}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:24.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">本借款协议（“本协议”）由以上两方根据《民法通则》、《合同法》等相关法律法规，在自愿、平等、诚实信用的基础上经协商达成一致后，在杭州市</span><span style="font-family:仿宋;font-size:12.0000pt;">西湖</span><span style="font-family:仿宋;font-size:12.0000pt;">区签订。</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;第一条&nbsp;甲方向乙方借款，借款信息如下</span><span style="font-family:仿宋;font-size:12.0000pt;">：&nbsp;&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:24.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">1.1&nbsp;借款金额：人民币（大写）</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${digitUppercase(borrowTender.account)}&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">，小写</span><span style="font-family:宋体;font-size:12.0000pt;">¥：</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${borrowTender.account}&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">元整。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;1.2&nbsp;借款期限：借款自</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${borrow.successTime?string("yyyy")}&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">年</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${borrow.successTime?string("MM")}&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">月</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${borrow.successTime?string("dd")}&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">日起至</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${borrow.finalRepayDate?string("yyyy")}&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">年</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${borrow.finalRepayDate?string("MM")}&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">月</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${borrow.finalRepayDate?string("dd")}&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">日，</span><span style="font-family:仿宋;font-size:12.0000pt;">共</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${borrow.timeLimit}&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">个月，该借款利率为</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${((borrow.apr?eval)/12) ?string("#.###")}%&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">/月。按月付息，到期利随本清。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:18.0500pt;text-align:left;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;</span><span style="font-family:仿宋;font-weight:bold;text-decoration:underline;font-size:12.0000pt;">还款时间为借款到期日（即还款日）前一工作日16:00前，若甲方未在此期限前归还借款，则由担保方承担连带保证责任，担保方于借款到期日前一工作日18:00前归还借款。如还款日遇到法定假日或公休日，还款日提前至前一工作日。本条款为有关还款的特别约定，不受借款到期日的约束。</span><span style="font-family:仿宋;font-weight:bold;text-decoration:underline;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;1.3本合同项下借款用途为</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">：</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;text-decoration:underline;">&nbsp;&nbsp;${carInfoJson.use!'--'}&nbsp;&nbsp;</span><span style="font-family:仿宋;color:#FF0000;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;第二条&nbsp;还款账户&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;"><br />
</span><span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;2.1&nbsp;乙方指定以下账户为还款账户：</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:23.2500pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">开户行：</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;<#if accountBankList?size gt 0>
	
	<@listing_childname sign="account_bank" key_value="${accountBankList[0].bankId}"; accountBankName>
				${accountBankName}
	</@listing_childname>
	
	,${accountBankList[0].branch}<#else>--</#if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:23.2500pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">户名：</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;<#if accountBankList?size gt 0>${user.realName!'--'}<#else>--</#if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:23.2500pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">账号：</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;<#if accountBankList?size gt 0>${accountBankList[0].account!'--'}<#else>--</#if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;第三条&nbsp;提前还款</span><span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;3.1&nbsp;本协议项下借款原则上不同意提前还款。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;3.2&nbsp;</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">若甲方单方提前还款，则视为甲方违约，甲方需向乙方支付借款本金及提前还款日止的利息之外，再额外支付7天利息；</span><span style="font-family:仿宋;font-size:12.0000pt;">若提前还款日距具体借款到期日小于7天，则按约定借款到期日计算利息；</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">日利息=本金*年利率/36</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">0</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">天</span><span style="font-family:仿宋;font-size:12.0000pt;">。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;color:#000000;font-weight:bold;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;第四条&nbsp;提前到期</span><span style="font-family:仿宋;color:#000000;font-weight:bold;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:18.0000pt;">
	<span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">&nbsp;各方确认，若发生以下情形之一，乙方或债权受让方有权宣布借款提前到期，要求甲方提前偿还所有借款本息等一切借款所涉相关费用</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:18.0000pt;">
	<span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">&nbsp;4.1&nbsp;甲方在本合同项下的任一笔借款本金或利息发生逾期；</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:18.0000pt;">
	<span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">&nbsp;4.2&nbsp;甲方擅自改变借款用途或挪作他用；&nbsp;</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:18.0000pt;">
	<span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">&nbsp;4.3甲方或担保人未按时向乙方提供乙方需要的资料或向乙方提供的有关资料不真实或向乙方隐瞒重大事项，危及乙方借款安全的；</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:18.0000pt;">
	<span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">&nbsp;4.4&nbsp;甲方或担保人因发生失业、停业、被宣告失踪、处于限制民事行为能力或丧失民事行为能力状态、被监禁等、或发生重大疾病、重大事故等影响其履行对乙方担保责任的能力。或因发生合并、分立、重组、改制等事件，或因法院裁决或行政命令而被解散、停业、吊销或注销营业执照、清算、破产、关闭，导致丧失债务清偿能力或无法履行担保责任，甲方或担保人未按乙方的要求另行提供有效足额担保的；</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:18.0000pt;">
	<span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">&nbsp;4.5&nbsp;任一担保人将担保财产或财产性权利拆除、出售、转让、交换、赠与、或以其他方式处置担保财产或财产性权利，足以危及乙方借款安全，甲方未按乙方的要求另行提供有效足额担保的；</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;4.6任一担保财产或财产性权利发生毁损、灭失、价值明显减少、涉及诉讼被法院查封、扣押、冻结等可能危及借款安全的情况，未能获得补救，而甲方未按乙方的要求另行提供有效足额担保的；</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:18.0000pt;">
	<span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">&nbsp;4.7甲方因任何其它借款、担保、赔偿或其他偿债责任到期不能履行，或担保人因发生上述情况而影响其履行对乙方担保责任的能力，甲方未按乙方的要求另行提供有效足额担保的；</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:18.0000pt;">
	<span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">&nbsp;4.8&nbsp;甲方或担保人明确表示或以自己的行为表明不履行本合同项下的任何义务；</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:18.0000pt;">
	<span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">&nbsp;4.9&nbsp;甲方因任何原因涉及经济纠纷类诉讼；</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:18.0000pt;">
	<span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">&nbsp;4.10甲方或担保人违反其向乙方做出的陈述和保证，或违反本合同项下任何一项义务，且乙方认为上述行为危及借款安全的；</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:18.0000pt;">
	<span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">&nbsp;4.11&nbsp;国家的相关政策发生重大调整或发生不可抗力、情势变更等情形。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第五条&nbsp;逾期</span><span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.1&nbsp;甲方未按本协议1.2约定时间归还借款本金、利息或乙方/债权受让方依据本协议第四条约定情形宣布借款提前到期后甲方不履行还款义务的，视为逾期。对应归还借款本息总额中未付部分款项，按未归还总金额0.1%/日支付从逾期之日起至实际支付之日止的逾期罚息。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.2&nbsp;因甲方逾期还款造成乙方或受让乙方债权的第三方实现债权产生的费用由甲方承担，该费用包括但不限于保全费、诉讼费、执行费、鉴定费、律师费、差旅费等。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;第六条&nbsp;抵/质押物</span><span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;text-align:left;">
<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;6.1&nbsp;<span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${carInfoJson.carBrand!'--'}&nbsp; </span></span><span style="font-family:仿宋;font-size:12.0000pt;">品牌<span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${carInfoJson.carSeries}-${carInfoJson.carSpec}&nbsp; </span>型号的汽车，车辆牌号为<span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${substringWord(carInfoJson.carNum, 3, "****")}&nbsp; </span>，发动机号为<span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${substringWord(carInfoJson.engineNumber, 8, "****")}&nbsp; </span>，车架号为：<span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${substringWord(carInfoJson.vin, 6, "****")}&nbsp; <br />
</span></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">6.2&nbsp;甲方承诺上述抵押物车辆的实际价格为人民币<span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${digitUppercase(borrow.accountYes)}&nbsp; </span>（大写）元整（￥<span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${borrow.accountYes}&nbsp; </span>）。</span><br />
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;6.3&nbsp;本协议甲方提供车辆担保方式为：□车辆抵押，适用本协议车辆抵押相关条款；□车辆质押，适用本协议车辆质押相关条款。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;6.4&nbsp;如本协议项下债权转让至第三方，本协议约定的抵押权/质押权也一并转让，即使抵/质押物仍然抵押登记在乙方名下，也不影响债权人行使抵押权，甲乙双方均有义务配合债权人行使抵/质押权。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;第七条&nbsp;抵/质押担保的范围</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;7.1本协议项下借款本金及其利息、罚息等；</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;7.2&nbsp;乙方实现债权或抵/质押权的费用（包括但不限于：甲方支出的诉讼费或仲裁费、因诉讼财产保全而提供担保财产的担保费用（按所担保金额的2％计算）、公告费、律师费，实现抵/质押物财产变现的评估费、鉴定费、催告费、拖车费、保管费、登记费等费用）。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7.3&nbsp;抵/质押车辆办理过户前因违章而产生的应由甲方承担的罚款和滞纳金、抵/质押车辆在抵/质押期间须办理的财产保险或其它车辆保险。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;第八条&nbsp;本条款车辆抵押适用</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;8.1&nbsp;甲乙双方应在本协议签订后到相应的登记部门办理抵押登记手续。本协议项下的权利义务履行完毕后，甲乙双方应到登记部门办理抵押登记注销手续。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;8.2&nbsp;甲方同意在办理抵押车辆借款手续时，在车辆上安装GPS全球卫星定位系统，未经乙方或乙方所授权管理方的同意，甲方不得擅自驾驶该抵押车辆离开车辆所属省份或直辖市，如甲方违反该约定或甲方故意毁坏GPS全球卫星定位系统（或自GPS全球卫星定位系统毁损的当日内未向乙方报告的），乙方随时有权对抵押车辆实施停车并要求甲方将车辆交付给乙方占有。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;8.3&nbsp;甲方对抵押车辆应向保险公司承保足额车损险、盗抢险、第三者责任险（100万元）、自燃险、涉水险及各项不计免赔等险种，若因未投保以上险种致出险时无法理赔，则所有损失由甲方承担。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;第九条&nbsp;本条款车辆质押适用</span><span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;9.1&nbsp;本协议项下的质押物甲方同意在质押期间由乙方占有、保管和使用，质押物的财产凭证以及车辆保险单的正本应交由乙方或乙方所授权管理方执管。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;9.2&nbsp;甲方未按本协议约定的时间偿还借款本息，逾期罚息等，则乙方有权处置质押车辆，按双方共同认定的价值出售质押车辆。若乙方暂未出售质押车辆，则乙方享有质押车辆的使用权、收益权。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;9.3&nbsp;乙方在车辆质押期间，可以按每日10公里的车程行驶车辆以维护或移库。质押车辆在质押期间出现交通事故，由保险公司及责任人赔偿，且甲方有责任以车主身份负责处理事故，甲方不负责其他任何赔偿。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;9.4&nbsp;甲方对质押车辆应向保险公司承保足额车损险、盗抢险、第三者责任险（100万元）、自燃险、涉水险及各项不计免赔等险种，若因未投保以上险种致出险时无法理赔，则所有损失由甲方承担。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;第十条&nbsp;抵/质押物处置</span><span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;甲方若未按期归还借款本金与利息，甲方授权乙方或乙方所授权管理方处置抵/质押车辆，处置所得价款偿还的先后顺序为：实现债权的费用、逾期违约金、应还利息、应还本金。处置后如有剩余部分退还给甲方，如有不足，则乙方有权向甲方对不足部分继续追索。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;第十一条&nbsp;费用</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;与本协议及本协议项下抵押物有关的一切费用（包括但不限评估、鉴定、装GPS、登记、公证及抵押物的保管、处分等费用）均由甲方支付。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;第十二条&nbsp;担保</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;12.1&nbsp;担保方（另行出具担保函）同意对本协议项下的借款承担连带保证责任。</span><span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;12.2&nbsp;保证担保的范围包括本协议项下的借款本金、利息、违约金、诉讼费、律师费等实现债权的费用和所有其它应付费用。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;12.3&nbsp;如本协议项下债权转让至第三方，本协议约定的抵/质押权也一并转让，即使抵/质押物仍然抵/质押登记在乙方名下，也不影响债权人行使抵/质押权，甲乙双方均有义务配合债权人行使抵/质押权。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;第十三条&nbsp;履约保证金</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;甲方按本协议项下借款</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">%提供履约保证金。该保证金存放于乙方指定账户。若甲方逾期未归还借款，则乙方有权没收，不予归还。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;第十四条&nbsp;争议解决方式及通知方式</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;14.1</span><span style="font-family:仿宋;font-size:12.0000pt;">因本协议引起的或与本协议有关的任何争议，协商不成的，任何一方可向本协议签署地下城区人民法院提起诉讼。</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoBodyText" style="margin-left:0.0000pt;text-align:left;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;14.2&nbsp;</span><span style="font-family:仿宋;color:#000000;font-size:12.0000pt;">本协议各方之间的书面通知或文件往来可以采用任何一种方式送达：1）、邮件送达方式，发送人以协议载明邮箱发出邮件即视为送达。2）快递送达方式，以协议载明各方地址为准，送达回执显示日或无回执情形下快递邮寄后第4日视为送达日期，若各方地址变更未履行通知义务的，则按照原地址寄送视为送达。3）传真送达方式，以协议载明传真号为准；&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;第十五条&nbsp;其他</span><span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-weight:bold;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">双方同意在甲方正常履行到期还款义务后，可继续在原有借款额度内向乙方借款。本协议到期后，若双方无异议，且乙方实际履行放款义务的，则视为甲方继续向乙方借款。本借款协议及担保函继续有效，本借款协议担保物继续对借款承担保证责任。借款金额以实际放款数为准，借款期限为</span><span style="font-family:仿宋;text-decoration:underline;font-size:12.0000pt;">&nbsp;${borrow.timeLimit}&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;">个月。直至乙方不同意继续放款，本协议终止。&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="NewStyle16" style="margin-left:0.0000pt;text-indent:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;text-indent:24.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;"><br />
</span><span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;"><br />
</span><span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;甲方（借款人）&nbsp;：</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;</span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;签字：&nbsp;&nbsp;${borrow.realName!'--'}</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;签约日期：</span><span style="font-family:仿宋;font-size:12.0000pt;">${borrow.successTime?string("yyyy年MM月dd日")}</span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;</span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;</span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;乙方（出借人）&nbsp;：&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;"><br />
</span><span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;签字：&nbsp;&nbsp;${user.realName!'--'}</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-family:仿宋;font-size:12.0000pt;"><br />
</span><span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;&nbsp;&nbsp;&nbsp;签约日期：&nbsp;&nbsp;${borrow.successTime?string("yyyy年MM月dd日")}</span><span style="font-family:仿宋;font-size:12.0000pt;"></span>
</p>
<p class="MsoNormal" style="margin-left:0.0000pt;">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;</span>
</p>
<p class="MsoNormal">
	<span style="font-family:仿宋;font-size:12.0000pt;">&nbsp;</span>
</p>
				</div>
			</div>
                   
          </div>
       </div>
    </div>    </div>
<!--biaoti_bg end-->

  <#include "/content/common/footer.ftl">

 <!--帐户信息公开设置 结束-->
<!--帐户信息公开设置 开始-->
  </body>
</html>
