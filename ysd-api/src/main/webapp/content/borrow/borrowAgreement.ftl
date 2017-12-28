<!DOCTYPE html>
<html>
  <head>
  <meta charset="gb2312" />
  <title>${Application ["qmd.setting.name"]}-投资者-借款协议页</title>
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
					<strong>借款协议书</strong>
				</h3>
				<div style="color:#363636; font-size:14px; line-height:25px;">
	
	<div class="bd" style="position:relative;z-index:9999;">
		<div>借款协议号：${agreementId} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;借款人：${borrow.username}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;出借人：详见本协议第一条 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;签订日期：<#if borrow.type==11>${borrow.successTime?string("yyyy-MM-dd ")}<#elseif borrow.type==12>${borrow.successTime?string("yyyy-MM-dd ")}<#else>${lastRdate}</#if></div>
		<div>借款人通过${Application ["qmd.setting.name"]}网站(以下简称“本网站”)的居间,就有关借款事项与各出借人达成如下协议：</div>
		<div><b>第一条：借款详情如下表所示： </b></div>
		<div>
			<table cellspacing="0" rules="all" border="1" id="ctl00_ContentPlaceHolder1_gvLoans" style="width:98%;border-collapse:collapse;">
				<tr height="30">
					<th align="center" scope="col">出借人</th>
					<th align="center" scope="col">借款金额</th>
					<th align="center" scope="col">借款期限</th>
					<th align="center" scope="col">年化利率</th>
					<th align="center" scope="col">借款开始日</th>
					<th align="center" scope="col">借款到期日</th>
					<th align="center" scope="col">还款本息</th>
				</tr>
					<#if borrow.type==11||borrow.type==12||borrow.type==14||borrow.type==15>
						<#list borrowTenderList as tender>
                    	<tr>
							<td align="center">${tender.username}</td>
							<td align="center">${tender.account}元</td>
						   	<td align="center">${borrow.timeLimit}<#if borrow.isday=='0'>个月<#elseif borrow.isday=='1'>天</#if></td>
						   	<td align="center"><@numberPercent val="${borrow.varyYearRate}"; npt>${npt}</@numberPercent></td>
						   	<td align="center">${borrow.successTime?string("yyyy-MM-dd ")}</td>
                           	<td align="center">${borrow.finalRepayDate?string("yyyy-MM-dd ")}</td>
						   	<td align="center"> ${tender.repaymentAccount}</td>
						</tr>
                    	</#list>
                    <#else>
                    	<#list userRepayDetailList as userRapyDetail>
                    	<tr>
							<td align="center">${userRapyDetail.username}</td>
							<td align="center">${userRapyDetail.account}元</td>
						   	<td align="center">${borrow.timeLimit}<#if borrow.isday=='0'>个月<#elseif borrow.isday=='1'>天</#if></td>
						   	<td align="center"><@numberPercent val="${borrow.varyYearRate}"; npt>${npt}</@numberPercent></td>
						   	<td align="center">${userRapyDetail.createDate?string("yyyy-MM-dd ")}</td>
                           	<td align="center">${userRapyDetail.repaymentDate?string("yyyy-MM-dd ")}</td>
						   	<td align="center"> ${userRapyDetail.repaymentAccount}</td>
						</tr>
                    	</#list>
				</#if>	
		  	</table>
		</div>
		<div>
			<div style="line-height:200%;font-size:12px;font-weight:bold;">第二条：还款 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">1、借款人承诺按照本协议第一条约定的时间和金额按期足额向出借人还款。 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">2、借款人的最后一期还款必须包含全部剩余本金、利息及其他根据本协议产生的全部费用。 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">3、借款人的每一期还款的还款金额计算公式为：每月须还款总金额=每月须还利息+每月须还本金。 </div>
			<br/>
			<div style="line-height:200%;font-size:12px;font-weight:bold;">第三条：逾期还款 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">1、借款标发生逾期，由平台"风险备用金"于逾期当天进行本息赔付。同时，对借款人进行催款。</div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">2、在3天内，如借款人不及时进行还款，平台将逾期借款的信息以书面形式通知平台担保公司。</div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">3、平台担保公司在收到通知后的5个工作日内，对该笔借款进行债权回购，将平台先行赔付的全额本息支付给网站；后续的催讨和抵押物的处置将由担保公司负责。</div>
			<br/>
			<div style="line-height:200%;font-size:12px;font-weight:bold;">第四条：借款的支付和还款方式 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">1、出借人在同意向借款人出借相应款项时,已委托本网站在本借款协议生效时将该笔借款直接划付至借款人帐户。 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">2、借款人已委托本网站将还款直接划付至出借人帐户。 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">3、借款人和出借人均同意，本网站接受借款人或出借人委托所采取的划付款项行为，所产生的法律后果均由相应委托方借款人或出借人自行承担。 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">4、若借款人的任何一期还款不足以偿还应还本金、利息和违约金,则出借人之间同意按照各自出借金额在出借金额总额中的比例收取还款。 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">5、本协议的履行地为本网站的住所地。 </div>
			<br/>
			<div style="line-height:200%;font-size:12px;font-weight:bold;">第五条：提前到期和提前偿还 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">1、双方同意,若借款人出现如下任何一种情况,则本协议项下的全部借款自动提前到期,借款人在收到本网站发出的借款提前到期通知后，应立即清偿全部本金、利息、逾期利息及根据本协议产生的其他全部费用：</div> 
			<div style="line-height:200%;text-indent:2em;font-size:12px;">(1)借款人因任何原因逾期支付任何一期还款超过5天的； </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">(2)借款人的工作单位、职务或住所变更后，未在30天内通知本网站；</div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">(3)借款人发生影响其清偿本协议项下借款的其他不利变化，未在30天内通知本网站。 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">2、双方同意,借款人有权提前清偿全部或者部分借款而不承担任何的违约责任(借款超过1日不足1个月者利息按足月计算)。 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">3、本借款协议中的每一出借人与借款人之间的借款均是相互独立的,一旦借款人逾期未归还借款本息,任何一出借人有权单独对该出借人未收回的借款本息向借款人追索或者提起诉讼。 </div>
			<br/>
			<div style="line-height:200%;font-size:12px;font-weight:bold;">第六条：法律适用和管辖 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">本协议的签订、履行、终止、解释均适用中华人民共和国法律,并由平台运营公司所在地区的人民法院管辖。 </div>
			<br/>
			<div style="line-height:200%;font-size:12px;font-weight:bold;">第七条：特别条款 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">1、借款人保证，借款人的借款用于合法用途，不将所借款项用于任何违法活动(包括但不限于赌博、吸毒、贩毒、卖淫嫖娼等)及一切高风险投资(如证券期货、彩票等)。如借款人违反前述保证或有违反前述保证的嫌疑，则出借人有权采取下列措施：</div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">（1）宣布提前收回全部借款；</div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">（2）出借人向公安等有关行政机关举报,追回此款并追究借款人的刑事责任，出借人和借款人均同意并授权本网站采取前述措施。</div> 
			<div style="line-height:200%;text-indent:2em;font-size:12px;">2、本网站仅作为借款人和出借人之间小额资金互助平台,借款人和出借人均不得利用本网站平台进行信用卡套现和其他洗钱等不正当交易行为,否则出借人、借款人和/或本网站有权向公安等有关行政机关举报,追究其相关法律责任。</div>
			<br/>
			<div style="line-height:200%;font-size:12px;font-weight:bold;">第八条：其他 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">1、本协议采用电子文本形式制成,或通过电子邮件的形式发送至协议各方本网站的认证信箱。 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">2、本协议自借款人在本网站发布的借款标的审核成功之日即本协议题头标明的签订日起生效,借款人、出借人、本网站各执一份,并具同等法律效力。 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">3、借款人、出借人在履行本协议过程中，应遵守本网站的各项规定。 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">4、出借人、借款人同意、授权或认可，本网站作为网络借款的居间平台根据本协议的规定和本网站的其他规定行使各项权利、发出各项通知或采取各项措施，一切法律后果和风险均由借款人或出借人承担。 </div>
			<div style="line-height:200%;text-indent:2em;font-size:12px;">5、本网站拥有对本协议的最终解释权。</div>
		</div>
<#--		<div style="position:absolute; bottom:0px;right:0px;width:168px;height:168px;z-index:-9999;"><img src="${base}/static/img/company.png" width="168" height="168"></div>-->
	</div>
	
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
