<#assign menu="wylc">
<#include "/content/common/header.ftl">
<script type="text/javascript" src="${Application ["qmd.static.baseUrl"]}js/borrow/borrowInvestPledge.js"></script>
<link rel="stylesheet" href="${Application ["qmd.static.baseUrl"]}css/center.css" media="screen" />
<link rel="stylesheet" href="${Application ["qmd.static.baseUrl"]}css/jquery-ui/jquery-ui-1.9.2.css" />
<script src="${Application ["qmd.static.baseUrl"]}js/jquery-1.8.1.js"></script>
<script src="${Application ["qmd.static.baseUrl"]}js/jquery-ui-1.9.2.js"></script>
<!--content-->
  <div class="index mt">
  
     <div class="tixianhh">确认投标</div>
     <div class="investiputd">
	   <ul ></ul>
	    <ul class="tixiann">
		   
		   <table class="txconfirm">
		      <tr >
			    <td align=right width="20%">借款标题：</td>
				<td class="titdrgiht" >${borrow.name}</td>
				</tr>
                
			   <tr >
			   <td align=right width="20%">标类型：</td>
			    <td class="titdrgiht" ><#if borrow.type == 1>天标</#if><#if borrow.type == 2>信用标</#if><#if borrow.type == 0>秒  标</#if></td>
				</tr>
                <tr>
				<td align=right width="20%">年利率：</td>
				<td class="titdrgiht">${borrow.apr}%</td>
			  </tr>
              <tr>
				<td align=right width="20%">借款期限：</td>
				<td class="titdrgiht">${borrow.timeLimit}个月</td>
			  </tr>
               <tr >
			   <td align=right width="20%">发标日期：</td>
			    <td class="titdrgiht" >${borrow.verifyTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                </tr>
               <tr >
			   <td align=right width="20%">发标者：</td>
			    <td class="titdrgiht" >${borrow.username}</td>
				</tr>
				 <tr >
			   <td align=right width="20%">已完成：</td>
			    <td class="titdrgiht" ><div class="progressbar_1"> 
        <div class="bar" style="width: ${borrow.schedule}%;"></div> 
    </div> <span class="loadfont"> ${borrow.schedule}%</span> &nbsp&nbsp&nbsp&nbsp&nbsp;还差：￥${borrow.balance}</td>
				</tr>
                <tr>
				<td align=right width="20%">借款金额：</td>
				<td class="titdrgiht"><span class="red">￥${borrow.account} 元</span></td>
			  </tr>
                 <tr >
			   <td align=right width="20%">剩余时间：</td>
			    <td class="titdrgiht" ><#if borrow.overDate??><span class="lxftime" endtime="${borrow.overDate?string("MM/dd/yyyy HH:mm:ss")}" balance="${borrow.balance}"></span></#if> </td>
				</tr>
				
				
				
		   </table>
		   <form id="bInvest" enctype="multipart/form-data">
		   <li class="tixianti"><s class="tixiantit"></s>投资100元,年利率${borrow.apr}%，期限￥${borrow.timeLimit}天,可获得利息收益￥${interest}元 </ul>
           <li class="cassaddtop"><span class="red">*</span> 投标金额：<input type="text" class="toubiao2" id="account" name="investMoney"> 元 <span >（可用余额：${user.ableMoney}元，最小投资${borrow.lowestAccount}元，最大投资额:<#if borrow.mostAccount == ''>无限制<#else>${borrow.mostAccount}元</#if>）</span></li>
		   <li class="cassaddtop"><span class="red">*</span> 安全密码：<input type="password" class="toubiao1" id="payPassword" name="safePassword"> <span><a href="#" class="blue">忘记密码？</a></span></li>
		   <input type="hidden" id="borrowId" name="bId" value="${borrow.id}">
		   <li class="cassaddtop"><span class="red">*</span>  验证码： <input maxlength="6" id="mycode" name="mycode" class="toubiao2" type="text" value=""  />
               	<img src="${base}/rand.do" onclick="changeValidateCode(this)"  title="点击图片重新获取验证码" /></span></li>
		   <li class="tixibtn"><input id="borrowInvest" class="submit " value="确定投标"  type="button" ></input></li>
		   </form>
		</ul>
	 </div>
 
  </div>
<style> 
/*进度条样式*/ 
.progressbar_1{ 
    background-color:#eee; 
    height:16px; 
    width:150px; 
    border:1px solid #bbb; 
    color:#222; 
} 
.progressbar_1 .bar { 
    background-color:#6CAF00; 
    height:16px; 
    width:0; 
} 
</style>

<#include "/content/common/foot.ftl">