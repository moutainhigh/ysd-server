<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>${Application ["qmd.setting.name"]}-我的合同</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>

<script type="text/javascript">
function printContract() {
	if (confirm('确定打印吗？')) {
　　	window.print();
	}
}
</script>	
	
</head>
<body>
<body style=" background-color:#fcfbfa;">
<div class="hetong_head">
   <div class="hetong_content">
      <div class="hetong_contenta01">
      	<a href="${base}/index.do"><img src="${base}/static/img/kaqu_logo3.png" /></a>
      </div>
      <div class="hetong_contenta02">
         <div class="hetong_contenta03"><span class="kaqu_st7">全国服务热线</span><span class="kaqu_st8">400&nbsp;888&nbsp;9527</span></div>
         <ul class="hetong_contenta04">
            <li class="hetong_contenta05">项目合同</li>
            <li class="hetong_contenta06">
<#-->
               <a>抵押物：房产抵押贷款</a>
               <a>贷款金额：50000.00元</a>  
               <a>目标月利率：14.40%</a>            
               <a class="kaqu_hetonga07">借款期限：1个月</a>
-->
&nbsp;
            </li>
         </ul>
      </div>
   </div>
</div><!-- hetong_head end -->
<div class="hetong_neirong">
 <div class="hetong_neirong_a01">
    <div class="hetong_neirong_a02">
      <div class="hetong_neirong_xinxi">
         <h3 class="hetong_neirong_xinxibiaoti">测试合同</h3>
         <div>
          
<p style="text-align:center;" align="center">
	<b><span style="font-size:24.0pt;line-height:125%;font-family:宋体;">&nbsp;</span></b>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-size:24.0pt;line-height:125%;font-family:宋体;">&nbsp;</span></b>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-size:24.0pt;line-height:125%;font-family:宋体;">&nbsp;</span></b>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-size:24.0pt;line-height:125%;font-family:宋体;">&nbsp;</span></b>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-size:24.0pt;line-height:125%;font-family:宋体;">动产质押借款合同<span></span></span></b>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-align:center;" align="center">
	<span style="font-family:宋体;">合同编号：</span><u><span style="font-family:宋体;">${test.borrow.businessCode}</span></u>
</p>
<p style="text-align:center;" align="center">
	<span style="font-family:宋体;">借 款 人：<u><span> ${substringWord(test.borrower.realName,1,"**")}</span></u><span></span></span>
</p>
<p style="text-indent:105.0pt;">
	<span style="font-family:宋体;">出 质 人：<u><span>${substringWord(test.contractParam.pawner,1,"**")}</span></span></u>
</p>
<p style="text-indent:105.0pt;">
	<span style="font-family:宋体;">出借人<span>/</span>质权人</span><span style="font-family:宋体;">：</span><u><span style="font-family:宋体;">${substringWord(test.user.realName,1,"**")}</span></u>
</p>
<p>
	<u><span style="font-size:10.5pt;line-height:125%;font-family:宋体;"><span style="text-decoration:none;">&nbsp;</span></span></u>
</p>
<p>
	<u><span style="font-size:10.5pt;line-height:125%;font-family:宋体;"><span style="text-decoration:none;">&nbsp;</span></span></u>
</p>
<p>
	<u><span style="font-size:10.5pt;line-height:125%;font-family:宋体;"><span style="text-decoration:none;">&nbsp;</span></span></u>
</p>
<p>
	<u><span style="font-size:10.5pt;line-height:125%;font-family:宋体;"><span style="text-decoration:none;">&nbsp;</span></span></u>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span></b>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span></b>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span></b>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span></b>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span></b>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-family:宋体;">前<span><span>&nbsp; </span></span>言<span></span></span></b>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-size:12.0pt;line-height:125%;font-family:宋体;">关于动产质押借款合同的说明<span></span></span></b>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">1.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">《动产质押借款合同》（以下简称“本合同”）由两部分组成：第一部分为<a name="OLE_LINK2"></a><a name="OLE_LINK1"></a><span>“借款明细”</span>，该明细中所列借款利率等内容会根据第二部分的相关内容做相应的调整；第二部分为“个人借款及质押通用条款”。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">2.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">借款人是指“借款明细”中列明的借款人，为符合中华人民共和国法律（以下简称“中国法律”，仅为本合同之目的，不包括香港特别行政区、澳门特别行政区和台湾省的法律法规）规定的具有完全民事权利能力和民事行为能力，独立行使和承担本合同项下权利义务的自然人。借款人为我就爱车的注册用户。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">3.</span><span> </span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">出借人<span>/</span>质权人是指“借款明细”、“个人借款及质押通用条款”中列明的出借人<span>/</span>质权人，为符合中国法律规定的具有完全民事权利能力和民事行为能力，独立承担本合同项下权利义务的自然人。出借人<span>/</span>质权人为我就爱车的注册用户。出借人<span>/</span>质权人不可撤销地授权我就爱车将其个人信息，包括但不限于姓名、有效身份证件号码等，为本合同项下之目的提供给第三方。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">4<span style="color:#FF6600;">.</span></span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#FF6600;">出质人是指以自己的名下拥有的能独立处分的动产为借款人的借款行为向</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">出借人<span>/</span>质权人<span style="color:#FF6600;">的提供合法、有效、足够值的质押担保，详见附件<span>1</span>：《质押物清单》。同时，该出质人同为保证人，质押物所有人同意并委托杭州中民力集投资管理有限公司在借款人支付利息及归还本金发生逾期时，有权将质押物通过拍卖、变卖等方式先行处置，以用于偿还出借人<span>/</span>质权人借款本金及利息，并支付相应违约金及资产处置费用。<span></span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">5.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">本合同项下借款人、出借人<span>/</span>质权人、出质人单独称“一方”，合成“各方”。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-size:12.0pt;line-height:125%;font-family:宋体;">第一部分 借款明细<span></span></span></b>
</p>
<div align="center">
	<table style="border-collapse:collapse;border:none;" border="1" cellpadding="0" cellspacing="0">
		<tbody>
			<tr>
				<td colspan="7" style="border:solid windowtext 1.0pt;background:#F2F2F2;" width="588">
					<p style="text-align:center;" align="center">
						<b><span style="font-size:10.5pt;font-family:宋体;">借款人<span></span></span></b>
					</p>
				</td>
			</tr>
			<tr>
				<td style="border:solid windowtext 1.0pt;" width="67">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">姓名<span></span></span>
					</p>
				</td>
				<td style="border:solid windowtext 1.0pt;" width="95">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">${substringWord(test.borrower.realName,1,"**")}</span>
					</p>
				</td>
				<td style="border:solid windowtext 1.0pt;" width="71">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">用户名<span></span></span>
					</p>
				</td>
				<td colspan="2" style="border:solid windowtext 1.0pt;" width="132">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">${substringWord(test.borrower.username,1,"**")}</span>
					</p>
				</td>
				<td style="border:solid windowtext 1.0pt;" width="71">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">身份证号<span></span></span>
					</p>
				</td>
				<td style="border:solid windowtext 1.0pt;" width="152">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">${substring(test.borrower.cardId, test.borrower.cardId?length-6, "******")}</span>
					</p>
				</td>
			</tr>
			<tr>
				<td colspan="7" style="border:solid windowtext 1.0pt;background:#F2F2F2;" width="588">
					<p style="text-align:center;" align="center">
						<b><span style="font-size:10.5pt;font-family:宋体;">出借人<span></span></span></b>
					</p>
				</td>
			</tr>
			<tr>
				<td style="border:solid windowtext 1.0pt;" width="67">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">姓名<span></span></span>
					</p>
				</td>
				<td style="border:solid windowtext 1.0pt;" width="95">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">${substringWord(test.user.realName,1,"**")}</span>
					</p>
				</td>
				<td style="border:solid windowtext 1.0pt;" width="71">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">用户名<span></span></span>
					</p>
				</td>
				<td colspan="2" style="border:solid windowtext 1.0pt;" width="132">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">${substringWord(test.user.username,1,"**")}</span>
					</p>
				</td>
				<td style="border:solid windowtext 1.0pt;" width="71">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">身份证号<span></span></span>
					</p>
				</td>
				<td style="border:solid windowtext 1.0pt;" width="152">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">${substring(test.user.cardId, test.user.cardId?length-6, "******")}</span>
					</p>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="border:solid windowtext 1.0pt;" width="162">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">借款金额<span></span></span>
					</p>
				</td>
				<td colspan="5" style="border:solid windowtext 1.0pt;" width="426">
					<p>
						<span style="font-size:10.5pt;font-family:宋体;">人民币（大写）<u><span><span>${digitUppercase(test.tender.account)}</span></span></u>元（￥<u><span><span>${test.tender.account}</span></span>）</u><span></span></span>
					</p>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="border:solid windowtext 1.0pt;" width="162">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">借款用途<span></span></span>
					</p>
				</td>
				<td colspan="2" style="border:solid windowtext 1.0pt;" width="137">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">${test.contractParam.purpose}</span>
					</p>
				</td>
				<td colspan="2" style="border:solid windowtext 1.0pt;" width="137">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;color:black;">借款利率<span></span></span>
					</p>
				</td>
				<td style="border:solid windowtext 1.0pt;" width="152">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;color:black;"><@aprofyear apr="${test.tender.investApr}"; apryear>${apryear}</@aprofyear>%/</span><span style="font-size:10.5pt;font-family:宋体;color:black;">年<span></span></span>
					</p>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="border:solid windowtext 1.0pt;" width="162">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">借款放款日<span></span></span>
					</p>
				</td>
				<td colspan="2" style="border:solid windowtext 1.0pt;" width="137">
					<p style="text-align:center;" align="center">
						<u><span style="font-size:10.5pt;font-family:宋体;"><span><#if test.borrow.endTime?exists>${test.borrow.endTime?string("yyyy")}</#if></span></span></u><span style="font-size:10.5pt;font-family:宋体;">年<u><span><span><#if test.borrow.endTime?exists>${test.borrow.endTime?string("MM")}</#if></span></span></u>月<u><span><span><#if test.borrow.endTime?exists>${test.borrow.endTime?string("dd")}</#if></span></span></u>日<span></span></span>
					</p>
				</td>
				<td colspan="2" style="border:solid windowtext 1.0pt;" width="137">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">最终到期日<span></span></span>
					</p>
				</td>
				<td style="border:solid windowtext 1.0pt;" width="152">
					<p style="text-align:center;" align="center">
						<u><span style="font-size:10.5pt;font-family:宋体;"><span><#if test.borrow.finalRepayDate?exists>${test.borrow.finalRepayDate?string("yyyy")}</#if></span></span></u><span style="font-size:10.5pt;font-family:宋体;">年<u><span><span><#if test.borrow.finalRepayDate?exists>${test.borrow.finalRepayDate?string("MM")}</#if></span></span></u>月<u><span><span><#if test.borrow.finalRepayDate?exists>${test.borrow.finalRepayDate?string("dd")}</#if></span></span></u>日<span></span></span>
					</p>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="border:solid windowtext 1.0pt;" width="162">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">起息日<span></span></span>
					</p>
				</td>
				<td colspan="2" style="border:solid windowtext 1.0pt;" width="137">
					<p style="text-align:center;" align="center">
						<u><span style="font-size:10.5pt;font-family:宋体;"><span><#if test.borrow.endTime?exists>${test.borrow.endTime?string("yyyy")}</#if></span></span></u><span style="font-size:10.5pt;font-family:宋体;">年<u><span><span><#if test.borrow.endTime?exists>${test.borrow.endTime?string("MM")}</#if> </span></span></u>月<u><span><span><#if test.borrow.endTime?exists>${test.borrow.endTime?string("dd")}</#if></span></span></u>日<span></span></span>
					</p>
				</td>
				<td colspan="2" style="border:solid windowtext 1.0pt;" width="137">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">还款方式<span></span></span>
					</p>
				</td>
				<td style="border:solid windowtext 1.0pt;" width="152">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">按月<span style="color:red;">预</span>付息，到期还本<span></span></span>
					</p>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="border:solid windowtext 1.0pt;" width="162">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;color:black;">还款日<span>/</span>结息日</span><span style="font-size:10.5pt;font-family:宋体;"></span>
					</p>
				</td>
				<td colspan="5" style="border:solid windowtext 1.0pt;" width="426">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">每月<u><span><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></u>日<span></span></span>
					</p>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="border:solid windowtext 1.0pt;" width="162">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">每月还款金额<span></span></span>
					</p>
				</td>
				<td colspan="5" style="border:solid windowtext 1.0pt;" width="426">
					<p style="text-align:center;" align="center">
						<span style="font-size:10.5pt;font-family:宋体;">人民币<u><span><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></u>元<span></span></span>
					</p>
				</td>
			</tr>
			<tr>
				<td style="border:none;" width="67">
					<br />
				</td>
				<td style="border:none;" width="95">
					<br />
				</td>
				<td style="border:none;" width="71">
					<br />
				</td>
				<td style="border:none;" width="66">
					<br />
				</td>
				<td style="border:none;" width="66">
					<br />
				</td>
				<td style="border:none;" width="71">
					<br />
				</td>
				<td style="border:none;" width="152">
					<br />
				</td>
			</tr>
		</tbody>
	</table>
</div>
<p style="text-align:center;" align="center">
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">&nbsp;</span></b>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-size:12.0pt;line-height:125%;font-family:宋体;">第二部分 个人借款及质押通用条款<span></span></span></b>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">一、借款<span></span></span></b>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">1.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">借款金额是指“借款及担保明细”中列明的借款本金金额。借款币种为人民币。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">2.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">借款期限是指自放款日起至最终到期日（全部借款到期日）止的期间。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">3.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">借款用途是指“借款及担保明细”中列明的借款用途。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">4.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">借款利率是指“借款及担保明细”中列明的借款利率，以年利率的方式表示。月利率<span>=</span>年利率<span>/12</span>，日利率<span>=</span>年利率<span>/<span style="color:red;">360</span></span>。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">5.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">起息日是指整个借款标募集完成之日。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">6.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">本合同项下借款按月结息，提前一个月支付。每月的结息日是指“借款及担保明细”中列明的日期；如当月无该日期，则以当月的最后一日为结息日。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">7. </span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">出借人<span>/</span>质权人通过我就爱车以网络在线点击确认的方式接受本合同后，即不可撤销地授权我就爱车将金额等同于“借款及担保明细”中列明的借款本金金额的资金<span style="color:red;">在扣除借款放款日至起息日这段时间的日利息后</span>，由出借人<span>/</span>质权人我就爱车用户名项下账户（以下简称“出借人<span>/</span>质权人我就爱车账户”）划转至借款人我就爱车用户名项下账户（以下简称“借款人我就爱车账户”）中，划转完毕即视为借款发放成功。借款发放成功之日即为借款合同借款期起始日。<span></span></span>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">二、还款<span></span></span></b>
</p>
<p style="text-indent:21.75pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">1.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">借款人应承担如下还款义务：<span></span></span>
</p>
<p style="text-indent:21.75pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑴借款人应按时足额向出借人<span>/</span>质权人支付本金和利息；<span></span></span>
</p>
<p style="text-indent:21.75pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑵如发生逾期还款，借款人需按本合同约定向出借人<span>/</span>质权人支付逾期罚息；<span></span></span>
</p>
<p style="text-indent:21.75pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑶如发生提前还款，借款人需按本合同的约定向出借人<span>/</span>质权人支付提前还款违约金；<span></span></span>
</p>
<p style="text-indent:21.75pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">借款人应支付归还的上述款项统称为“借款人应付款项”。<span></span></span>
</p>
<p style="text-indent:21.75pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:black;">2.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:black;">本合同项下借款以<u>每月</u></span><u><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:red;">预</span></u><u><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:black;">付息、到期还本</span></u><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:black;">的方式还款。<span></span></span>
</p>
<p style="text-indent:21.75pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:black;">共计支付利息<span>/</span>本息期数为：<u><span><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></u>期；还款日为每月<u><span><span>&nbsp;&nbsp; </span></span></u>日。<span></span></span>
</p>
<p style="text-indent:21.75pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:black;">3.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:black;">还款途径：<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">借款人通过我就爱车以网络在线点击确认的方式接受本合同后，即不可撤销地授权我就爱车将金额等同于“借款及担保明细”中列明的每月应付利息与借款本金由借款人我就爱车账户划转至出借人<span>/</span>质权人我就爱车账户中。<span></span></span>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:black;">三、逾期还款<span></span></span></b>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">1.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">还款日<span>24</span>时前，借款人未足额支付应付款项的，则视为逾期。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">2.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">如借款人的还款金额不足以足额清偿全部到期应付款项的，借款人应按如下顺序支付应付款项：<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑴单期还款：当借款人仅有一期应付款项未按时全额支付时，其还款资金支付顺序依次为：<span>A. </span>利息；<a name="OLE_LINK3"></a><span>B. </span>本金；<span>C. </span>逾期罚息。<span style="color:red;"></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑵多期还款：当借款人存在多期应付款项未按时全额支付时，其还款资金须根据应付款项拖欠的时间，从拖欠时间最长的应付款项开始支付，依次（期）偿还；同一期应付款项的支付顺序均为：<span>A.</span>逾期罚息；<span>B.</span>利息；<span>C.</span>本金。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">3.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">借款人逾期的，逾期款项中的借款本金部分自逾期之日起按<span>0.07%/</span>天计取逾期罚息，直至清偿完毕之日。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">4.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">逾期款项中的利息、逾期罚息不计利息。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">5.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">若借款人对任何一期应付款项逾期满<span>10</span>日的，则本合同项下全部借款于该期应付款项逾期第<span>11</span>日当日全部提前到期；该期借款逾期未满<span>10</span>日但已届最终借款到期日的，仍以最终借款到期日为全部借款到期日。<span></span></span>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">四、承诺及保证<span></span></span></b>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">1.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">借款人和出借人<span>/</span>质权人各自在此确认为具有完全民事权利能力和完全民事行为能力的自然人，有权签订并履行本合同。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">2.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">出借人<span>/</span>质权人保证：出借人<span>/</span>质权人为我就爱车注册用户并在本合同有效期内保持我就爱车注册用户身份；出借人<span>/</span>质权人向借款人提供的资金来源合法。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">3.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">借款人应根据我就爱车的不时需要如实向出借人<span>/</span>质权人、我就爱车提供个人情况（包括但不限于姓名、身份证号、学历、联系方式、联系地址、职业信息、联系人信息等）以及借款用途等相关信息。借款人承诺并保证其向出借人<span>/</span>质权人、我就爱车提供的所有信息均为真实、完整和有效的。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">4.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">借款人承诺：如发生任何影响或者可能影响借款人经济状况、信用状况、还款能力的事由，包括但不限于借款人的工作单位、职位、工作地点、薪酬等事项的变化，借款人应于前述变更发生之日起<span>2</span>个工作日内通知担保公司及我就爱车。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">5.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">借款人承诺根据本合同列明的借款用途使用借款资金，并保证不挪用借款资金或将借款资金用于以下目的和用途：<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑴以任何形式进入证券市场，或用于股本权益性投资；<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑵用于国家明令禁止或限制的经营活动。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">6.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">出质人承诺：⑴出质人是本合同项下质物的完全的、有效的、合法的所有者，质物不存在所有权或经营管理权方面的争议。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑵出质人提供质押担保完全出于自愿，在本合同项下的全部意思表示真实。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑶本合同项下质物依法可以设定质押，不会受到任何限制。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑷质物来源合法，依法可以流通或转让。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑸出质人已对本合同项下质物的瑕疵做出充分、合理的书面说明。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑹本合同签订前未对本合同项下质物做出过包括但不限于设立抵押、质押、馈赠或转让在内的任何处分。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑺当借款人未按本合同约定履行其债务时，无论出借人<span>/</span>质权人对本合同项下的债权是否拥有其他担保（包括但不限于保证、抵押、质押、保函、备用信用证等担保方式），出借人<span>/</span>质权人均有权直接处分质物，出质人对此放弃抗辩权。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">7.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">各方承诺，各方不会利用我就爱车进行信用卡套现、洗钱、非法集资或其他不正当交易行为，否则应依法独立承担法律责任。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">8.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">各方确认，借款人和出借人<span>/</span>质权人授权和委托我就爱车根据本合同所采取的全部行为和措施的法律后果均归属于借款人及出借人<span>/</span>质权人本人；在任何情形下，我就爱车不是本合同项下任何借款或债务的债务人或需要以其自有资金偿还本合同项下的任何借款或债务。我就爱车亦不是本合同项下担保的担保人或需要以其自有资金承担本合同项下的担保责任。<span></span></span>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">五、质物<span></span></span></b>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">1.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">出质人质押担保的范围包括：本合同项下的融资本金、利息、罚息、复利、违约金、赔偿金、保管费用以及出借人<span>/</span>质权人为实现债权、质权所发生的律师费等全部费用和所有其他应付费用。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">2.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">本合同项下质权的效力及于质物的主权利、从权利、主物、从物、附和物、加工物和孳息等，以及因质物毁损、灭失或被征用而产生的保险金、赔偿金、补偿金。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">3.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">各方委托杭州中民力集投资管理有限公司为质物指定交由浙江涌金仓储股份有限公司（以下称监管人）进行监管。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">4.</span></b><b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">出借人<span>/</span>质权人委托<u>杭州中民力集投资管理有限公司</u>（以下称质权委托人）作为出借人<span>/</span>质权人委托人全权处理质物的移交及监管事宜。<span></span></span></b>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">5.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">本合同项下的质物移交、监管程序在出质人、质权委托人、监管人三方签署的《动产监管协议》中约定。因质物仓储、监管产生的相关费用及支付方式有出质人跟监管人另行约定。<span></span></span>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">六、质权的实现<span></span></span></b>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">1.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">发生下列情况之一时，合同双方一致同意委托浙江天合拍卖有限公司拍卖本合同质押物以偿还出借人<span>/</span>质权人项下全部债务（包括但不限于评估费、调查费、鉴定费、拍卖佣金等）并签订《委托拍卖合同》。该委托拍卖合同以拍卖人收到出借人<span>/</span>质权人书面通知启动拍卖程序之日起生效；该拍卖委托的终止或拍卖物的撤回必须经得本合同各方的书面同意。本条款的执行不与诉讼（仲裁）条款相违背，出借人<span>/</span>质权人在借款人或出质人违反本合同约定义务的情况下有权自行选择适用何种处置方案实现其债权：<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑴本合同约定的还款期限已到，借款人未依约归还借款本息或所延期限已到仍不能归还借款本息的；<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑵出质人死亡而无继承人履行合同，或者继承人放弃继承的；<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑶如出质人所提供质物有损坏或价值明显减少的可能，足以危害出借人<span>/</span>质权人权利，且出质人拒绝提供相应担保的，出借人<span>/</span>质权人有权直接拍卖或变卖质物，并将拍卖或变卖所得提前实现所担保的本合同项下全部债权。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑷根据本合同的约定或法律规定出借人<span>/</span>质权人可以提前实现质权的其他情形。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">2.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">发生上述所列情况之一时，出借人<span>/</span>质权人向拍卖方发出《拍卖通知》，提请拍卖方执行拍卖，由拍卖方发布实施拍卖，并办理质押物（拍卖物）所有权变更的手续。<span></span></span>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">七、保险<span></span></span></b>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">1.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">本合同签订后<u>五个工作</u>日内，出质人应到有关保险机构办理质物的财产保险基本险及附加手续；保险期限应不短于主债务到期日；保险金额不低于本合同债务本息。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">2.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">出质人应在保险单中明确规定：出险时出借人<span>/</span>质权人为第一受益人，保险单中不应有任何限制出借人<span>/</span>质权人权益的条款。保险单交由质权委托人持有。一旦发生保险事故，保险人应将保险金直接划付至出借人<span>/</span>质权人指定的账户；在保险事故发生前，出质人已履行本合同债务的，质权委托人应将该保险单返还出质人。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">3.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">在本合同有效期内，出质人不得以任何理由中断或撤销保险。如保险中断，出借人<span>/</span>质权人有权代为办理保险手续，一切费用由出质人承担。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">4.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">对保险赔偿金，出质人同意出借人<span>/</span>质权人有权选择下列方法进行处理，并协助办理有关手续：<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑴清偿或提前清偿本合同项下债务本息及相关费用；<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑵转为定期存款，存单用于质押；<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑶经出借人<span>/</span>质权人同意，用于修复质物，以恢复质物价值；<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑷向出借人<span>/</span>质权人指定的第三人提存；<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑸出质人提供符合出借人<span>/</span>质权人要求的新的担保后，可将保险赔偿金自由处分。<span></span></span>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">八、借款人及出质人权利义务<span></span></span></b>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">1.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">借款人承担本合同项下有关的各项费用，包括但不限于律师服务、鉴定、检验、估价、仓储、监管、交易及诉讼的费用。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">2.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">出质人的行为足以使质物价值减少的，应停止其行为；造成质物价值减少时，有义务恢复质物的价值，或提供与减少的价值相当的担保。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">3.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">质权存续期间，如果因第三人的行为导致质物价值减少、质物毁损或灭失的，所得赔偿金应当存入出借人<span>/</span>质权人指定账户，如果出质人不能提供令出借人<span>/</span>质权人满意的新的担保，出质人同意出借人<span>/</span>质权人以该赔偿金担保债权的履行，同时质物价值未减少的部分，仍作为债权的担保。如果出质人提供符合出借人<span>/</span>质权人要求的新的担保，出借人<span>/</span>质权人应将赔偿金返还出质人。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">4.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">出借人<span>/</span>质权人质权受到或可能受到来自任何第三方的侵害时，出质人有义务立即通知并协助出借人<span>/</span>质权人免受侵害。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">5.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">本合同生效后，出借人<span>/</span>质权人依法将债权转让给第三人的，出质人仍在原质押担保范围内承担担保责任。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">6.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">未经出借人<span>/</span>质权人书面同意，借款人不得转让本合同项下的全部或部分权利或义务。<span></span></span>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">九、出借人<span>/</span>质权人权利和义务<span></span></span></b>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">1.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">有权要求出质人协助，避免质权受到来自任何第三方的侵害。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">2.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">在本合同有效期内，出借人<span>/</span>质权人依法转让主债权时，应及时通知出质人。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">3.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">对出质人在履行本合同项下义务时所提交的有关文件、财务报表及其他相关资料中的未公开信息保密，但法律、法规、金融规章另有规定的除外；<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">4.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">处分本合同项下质物的所得，在偿还本合同质押担保范围内的全部债务后还有剩余的，将剩余部分退还出质人。<span></span></span>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">十、违约责任<span></span></span></b>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">1.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">借款人未经出借人<span>/</span>质权人同意，擅自转让本合同借款债务的，借款人应向担保公司支付借款本金总额<span>10%</span>的金额作为不如实告知违约金。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">2.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">发生下列任何一项或几项情形的，视为借款人违约：<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑴借款人违反其在本合同所做的任何承诺和保证的；<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑵借款人的任何财产遭受没收、征用、查封、扣押、冻结等可能影响其履约能力的不利事件，且不能及时提供有效补救措施的；<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑶借款人的财务状况出现影响其履约能力的不利变化，且不能及时提供有效补救措施的。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">3.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">若借款人违约或根据担保公司、出借人合理判断借款人可能发生违约事件的，经担保公司同意，出借人（委托我就爱车）有权采取下列任何一项或几项救济措施：<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑴立即暂缓、取消发放全部或部分借款；<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑵宣布已发放借款全部提前到期，借款人应立即偿还所有应付款项；<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑶解除本合同；<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">⑷采取法律、法规以及本合同约定的其他救济措施。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">4.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">出质人将质物进行再质押或转让或作出任何其他伤害出借人<span>/</span>质权人在本合同项下质押权利的处置，出借人<span>/</span>质权人有权要求出质人限期纠正违约、提供相应担保、赔偿损失，有权提前处分质押物，并可要求出质人支付借款金额万分之<u> 叁 </u>的违约金。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">5.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">如因借款人或出质人过错造成本合同无效，借款人或出质人应赔偿出借人<span>/</span>质权人全部损失，并可要求借款人或出质人支付借款金额万分之<u><span><span>&nbsp;&nbsp; </span></span>叁<span><span>&nbsp; </span></span></u>的违约金。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">6.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">本合同生效后各方应全面履行本合同约定的义务。任何一方不履行或不完全履行本合同约定义务的，应向守约方支付本合同借款金额万分之<u> 叁 </u>的违约金，并赔偿由此给守约方造成的损失。<span></span></span>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">十一、法律使用和管辖<span></span></span></b>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;font-family:宋体;">本合同的签订、履行、终止、解释均适用中国法律。各方同意，因本合同所产生的或与本合同有关的一切争议，各方应协商解决；</span><span style="font-size:10.5pt;font-family:宋体;">协商不成的，可向合同签约地有管辖权的法院提起诉讼。本合同签约地为中华人民共和国浙江省杭州市下城区。</span><span style="font-size:10.5pt;font-family:宋体;"></span>
</p>
<p style="background:white;">
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;background:white;">十二、其他<span></span></span></b>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">1.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">合同生效：本合同自各方签字（盖章）之日起生效。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">2.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">本合同一式肆份，借款人、出借人<span>/</span>质权人、出质人各执一份，我就爱车电子商务有限公司留存一份，效力同等。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">3.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">本合同项下各方同意并承诺，本合同项下各方提供的信息均应在提供给本合同其他各方的同时提供给我就爱车。本合同各方授权我就爱车根据本合同任意一方的合理要求向其提供本合同各方向我就爱车提供的所有信息。<span></span></span>
</p>
<p style="text-indent:21.75pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">4.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">借款人、出借人<span>/</span>质权人和出质人均同意并确认，借款人、出借人<span>/</span>质权人、出质人通过其我就爱车账户和银行账户采取的行为所产生的法律效果和法律责任归属于借款人、出借人<span>/</span>质权人和出质人本人；借款人、出借人<span>/</span>质权人、出质人授权和委托我就爱车根据本合同所采取的全部行动和措施的法律后果均归属于借款人、出借人<span>/</span>质权人、出质人本人，与我就爱车无关，我就爱车也不因此承担任何责任。借款人和出借人<span>/</span>质权人同意，我就爱车有权就其为借款人和出借人<span>/</span>质权人提供的中介服务收取服务费。<span></span></span>
</p>
<p style="text-indent:21.0pt;">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">6.</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">本合同经</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;">出借人<span>/</span>质权人<span style="color:#333333;">和借款人、出质人通过我就爱车以网络在线点击确认的方式订立。本合同各方委托我就爱车保管所有与本合同有关的书面文件或电子信息；本合同各方确认并同意由我就爱车提供的与本合同有关的书面文件或电子信息在无明显错误的情况下应作为本合同有关事项的终局证明。<span></span></span></span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">借款人<span>:${substringWord(test.borrower.realName,1,"**")}</span></span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;">出借人<span>/</span>质权人<span style="color:#333333;">: ${substringWord(test.user.realName,1,"**")}</span></span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#FF6600;">出质人：<span>${substringWord(test.contractParam.pawner,1,"**")}</span></span>
</p>
<p style="text-align:right;" align="right">
	<u><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;"><span><#if test.tender.createDate>${test.tender.createDate?string("yyyy")}</#if></span></span></u><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">年<u><span><span><#if test.tender.createDate>${test.tender.createDate?string("MM")}</#if> </span></span></u>月<u><span><span><#if test.tender.createDate>${test.tender.createDate?string("dd")}</#if> </span></span></u>日</span><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#FF6600;"></span>
</p>
<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;"><br />
</span> 
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">附件<span>1</span>：<span></span></span></b>
</p>
<p>
	<span style="font-size:12.0pt;font-family:STSong;">&nbsp;</span>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-family:宋体;color:#333333;">质押物清单<span></span></span></b>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></b><b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">单位：元<span></span></span></b>
</p>
<table style="border-collapse:collapse;border:none;" border="1" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<td style="border:solid windowtext 1.0pt;" width="45">
				<p style="text-align:center;" align="center">
					序号
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="58">
				<p style="text-align:center;" align="center">
					出质人
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="132">
				<p style="text-align:center;" align="center">
					<span>质押物名称</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>规格与计量单位</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="72">
				<p style="text-align:center;" align="center">
					<span>数 量</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span><span>价格<span></span></span>
				</p>
				<p style="text-align:center;" align="center">
					<span>（元<span>/</span>吨）</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" valign="top" width="84">
				<p style="text-align:center;" align="center">
					质押物
				</p>
				<p style="text-align:center;" align="center">
					价值
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					所占比例
				</p>
			</td>
		</tr>
<#list  test.contractPledge as pledge>
		<tr>
			<td style="border:solid windowtext 1.0pt;" width="45">
				<p style="text-align:center;" align="center">
					<span>&nbsp;${(pledge_index+1)}</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="58">
				<p style="text-align:center;" align="center">
					<span>&nbsp;${test.contractParam.pawner}</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="132">
				<p style="text-align:center;" align="center">
					<span>&nbsp;${pledge.pawnName}</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;${pledge.pawnUnit}</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="72">
				<p style="text-align:center;" align="center">
					<span>&nbsp;${pledge.pawnQuantity}</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;${pledge.pawnUnitPrice}</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" valign="top" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;${pledge.pawnTotalPrice}</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;${test.tender.holdRate?string("percent")}</span>
				</p>
			</td>
		</tr>
</#list>
		<tr>
			<td style="border:solid windowtext 1.0pt;" width="45">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="58">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="132">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="72">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" valign="top" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
		</tr>
		<tr>
			<td style="border:solid windowtext 1.0pt;" width="45">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="58">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="132">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="72">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" valign="top" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
		</tr>
		<tr>
			<td style="border:solid windowtext 1.0pt;" width="45">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="58">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="132">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="72">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" valign="top" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
		</tr>
		<tr>
			<td style="border:solid windowtext 1.0pt;" width="45">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="58">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="132">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="72">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" valign="top" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
		</tr>
		<tr>
			<td style="border:solid windowtext 1.0pt;" width="45">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="58">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="132">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="72">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" valign="top" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
		</tr>
		<tr>
			<td style="border:solid windowtext 1.0pt;" width="45">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="58">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="132">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="72">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" valign="top" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
		</tr>
		<tr>
			<td style="border:solid windowtext 1.0pt;" width="45">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="58">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="132">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="72">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" valign="top" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
		</tr>
		<tr>
			<td style="border:solid windowtext 1.0pt;" width="45">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="58">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="132">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="72">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" valign="top" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;</span>
				</p>
			</td>
		</tr>
		<tr>
			<td colspan="2" style="border:solid windowtext 1.0pt;" width="103">
				<p style="text-align:center;" align="center">
					合计价值
				</p>
			</td>
			<td colspan="5" style="border:solid windowtext 1.0pt;" width="456">
				<p style="text-align:center;" align="center">
					<span>&nbsp;${test.contractParam.pledgeMoneyTotal}</span>
				</p>
			</td>
			<td style="border:solid windowtext 1.0pt;" valign="top" width="84">
				<p style="text-align:center;" align="center">
					<span>&nbsp;${test.tender.holdRate?string("percent")}</span>
				</p>
			</td>
		</tr>
	</tbody>
</table>
<p>
	<span>&nbsp;</span>
</p>
<p style="text-align:right;" align="right">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:right;" align="right">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:left;" align="left">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:right;" align="right">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:right;" align="right">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:right;" align="right">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p style="text-align:right;" align="right">
	<span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">&nbsp;</span>
</p>
<p>
	<b><span style="font-size:10.5pt;line-height:125%;font-family:宋体;color:#333333;">附件<span>2</span>：<span></span></span></b>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-family:宋体;">授权委托书<span></span></span></b>
</p>
<p>
	<span style="font-size:10.5pt;line-height:200%;font-family:宋体;color:#333333;">致 浙江涌金仓储股份有限公司 （监管方）：<span></span></span>
</p>
<p style="text-align:center;text-indent:26.25pt;" align="center">
	<span style="font-size:10.5pt;line-height:200%;font-family:宋体;color:#333333;">出借人<span>/</span>质权人<u><span><span>${substringWord(test.user.realName, 1, "**")}</span></span></u>因《动产质押借款合同》依法取得质物的占有权，现委托杭州中民力集投资管理有限公司作为质权人合法委托代理人，授权其代表质权人处理质物的占有和移交工作。该委托代理人的授权范围为：全权代表质权人与出质人及监管方进行磋商、签署文件和处理与<u><span>__</span>质物<span>__</span></u>有关的事务。在整个代理过程中，该代理人的一切行为，均代表质权人，与质权人的行为具有<span></span></span>
</p>
<p>
	<span style="font-size:10.5pt;line-height:200%;font-family:宋体;color:#333333;">同等法律效力。质权人将承担该代理人行为的全部法律后果和法律责任。<span></span></span>
</p>
<p>
	<a name="_GoBack"></a><span style="font-family:宋体;"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span>
</p>
<p style="text-align:center;text-indent:26.25pt;" align="center">
	<span style="font-family:宋体;"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span>&nbsp;&nbsp;</span></span><span style="font-size:10.5pt;line-height:200%;font-family:宋体;color:#333333;">质权人：<u><span>${substringWord(test.user.realName, 1, "**")}</span></u></span>
</p>
<p style="text-align:center;text-indent:26.25pt;" align="center">
	<u><span style="font-size:10.5pt;line-height:200%;font-family:宋体;color:#333333;"><span style="text-decoration:none;">&nbsp;</span></span></u>
</p>
<p style="text-align:right;text-indent:26.25pt;" align="right">
	<u><span style="font-size:10.5pt;line-height:200%;font-family:宋体;color:#333333;"><span><#if test.tender.createDate>${test.tender.createDate?string("yyyy")}</#if> </span></span></u><span style="font-size:10.5pt;line-height:200%;font-family:宋体;">年<u><span><span><#if test.tender.createDate>${test.tender.createDate?string("MM")}</#if> </span></span></u>月<u><span><span><#if test.tender.createDate>${test.tender.createDate?string("dd")}</#if> </span></span></u>日<span></span></span>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-family:宋体;">&nbsp;</span></b>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-family:宋体;">&nbsp;</span></b>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-family:宋体;">&nbsp;</span></b>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-family:宋体;">&nbsp;</span></b>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-family:宋体;">&nbsp;</span></b>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-family:宋体;">&nbsp;</span></b>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-family:宋体;">&nbsp;</span></b>
</p>
<p style="text-align:center;" align="center">
	<b><span style="font-family:宋体;">&nbsp;</span></b>
</p>
<p>
	<span>&nbsp;</span>
</p>


         </div>
      </div>
    </div>
    <div class="hetong_neirong_a03">
    <#-->
    本申请单仅作为我就爱车受理借款人借款申请之凭证，不作为任何合同之要约。
    -->
    </div>
    <div class="hetong_neirong_a04">
      <input type="button" value="打印" class="hetong_neirong_a05" onclick="printContract();"/>
<#-->
      <input type="button" value="发送邮箱" class="hetong_neirong_a05" />
      <input type="button" value="邮寄合同" class="hetong_neirong_a05 hetong"/>
-->
    </div>
 </div> 
</div><!-- hetong_neirong end -->
<div class="hetong_footer">
  <div class="hetong_footera01">
    <ul class="hetong_footera02">
      <li class="hetong_footera03">全国服务热线</li>
      <li class="hetong_footera04">400&nbsp;&nbsp;888&nbsp;&nbsp;9527</li>
      <li class="hetong_footera05">服务时间：9:00-17:00</li>
    </ul>
    <div class="hetong_footera06">
       <div class="hetong_footera07">
           <div>ICP备案：浙ICP备17036398号</div>
           <div>增值业务电信经营许可证：浙**</div>
           <div>&copy;2013-2014&nbsp;IkQu Inc.All&nbsp;rights&nbsp;reserved.Powered&nbsp;by&nbsp;&nbsp;我就爱车</div>
       </div>
      <div class="hetong_footera08"><img width="210" height="60" src="${base}/static/img/kaqu_logo4.png"></div>
    </div>
  </div>
</div><!-- hetong_neirong end -->
</body>
</html>
