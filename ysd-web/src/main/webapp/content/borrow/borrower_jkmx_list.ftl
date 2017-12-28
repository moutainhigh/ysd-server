<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <meta charset="gb2312" />
  <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-借款明细</title>
  <meta name="application-name" content="网络P2P借贷" />
   <meta name="msapplication-tooltip" content="网络P2P借贷-上海借贷-杭州借贷" />
   <meta http-equiv="x-ua-compatible" content="ie=7" />
	<meta name="keywords" content="${Application ["qmd.setting.metaKeywords"]}" />
	<meta name="description" content="${Application ["qmd.setting.metaDescription"]}" />
	<meta name="robots" content="all" />		
    <#include "/content/common/meta.ftl">
 </head>

 <body>
 <!-- header -->
<#include "/content/common/header.ftl">
<!--content-->
  <div class="index mt">
  <ul class="cb">
      <p class="c_b"><a href="${base}/">${Application ["qmd.setting.name"]}</a> &gt; <a href="${base}/userCenter/index.do">我的账户</a> &gt <a href="#">支付密码</a></p>
	  </ul>
      <!--left-->
	   <#include "/content/common/user_center_left.ftl">
	         <!--right-->
<div class="c_right">
			     <div class="top_bg"></div>
				 <div class="rightwrap">
				      <div class="rightcon">
				       <ul class="wtitle_l">
                             <li><a name="" href="../borrow/userBorrowList.do?status=1">正在招标</a></li>
                             <li><a name="" href="../borrow/userBorrowList.do?status=-1">尚未发布</a></li>
                             <li><a name="" href="../borrow/userBorrowList.do?status=3,4">正在还款</a></li>
                             <li><a name="" href="${base}/borrow/hkmx.do">我要还款</a></li>
							 <li><a class="wtitle_lNa" name="" href="#">借款明细</a></li>
							 <li><a name="" href="${base}/borrow/borrowList.do?borrow.status=7">已还借款</a></li>
							 <li><a name="" href="${base}/borrow/borrowList.do?borrow.status=3">已完成的借款</a></li>
                          </ul>
                          
					 	<form id="listForm" method="post" action="jkmx.do" >
                             <input type = "hidden" name="pager.pageSize" id="pageSize" value = "10">
							 <input type="hidden" id="pageNumber" name="pager.pageNumber" value="${pager.pageNumber}" />
                             
                             <ul class="invests">
							      <li>时间 <input type="text" id = "minDate" name="minDate" class="formText" onclick="WdatePicker()" value="${(minDate)!}"></li> 
							      <li>到 <input type="text" id = "maxDate" name="maxDate" class="formText" onclick="WdatePicker()" value="${(maxDate)!}"></li>
								  <li><button id="searchButton" class="investbtn">搜索</button></li>
							  </ul>
								
					          <table class="fieldset listtable">
							  <tbody>
							  		<th>编号</th>
							        <th style="border-left: 1px solid #D8D8D8;" scope="col">投资者</th>
							        <th>借入日期</th>
									<th>借入总额</th>
									<th>应还款总额</th>
									<th>已还款金额</th>
									<th>已还利息</th>
									<th>已还滞纳金</th>
									<th>待还总金额</th>
									<th>待还利息</th>									
								</tbody>

                                   <tbody>
                                   <#list pager.result as borrowTemp>
									   <tr>
									   <td>${borrowTemp.id}</td>
									   <td>${borrowTemp.name}</td>
									   <td>
									   		<#if borrowTemp.repaymentDate>
									   			${borrowTemp.repaymentDate?string("yyyy-MM-dd")}
									   		</#if>
									   	</td>
									   <td>${borrowTemp.loanAmountFee?string.currency}</td>
									   <td>${borrowTemp.totalIncomeFee?string.currency}</td>
									   <td>${borrowTemp.totalReceivedFee?string.currency}</td>
									   <td>${borrowTemp.receivedInterestFee?string.currency}</td>
									   <td>0</td>
									   <td>${borrowTemp.collectAmountFee?string.currency}</td>
									   <td>${borrowTemp.collectInterestFee?string.currency}</td>
										</tr>
									 </#list>
									  <!--page-->
									  <@pagination pager=pager baseUrl="/borrow/jkmx.do"  parameterMap = parameterMap>
											<#include "/content/pager.ftl">
									  </@pagination>
							      </tbody>
						  </table> 
                          </form>
					  </div>
				 
				 </div>
				 <div class="bottom_bg"></div>
			 </div>
			<!--c_right end--> 
			 </div>
  </div>
  <!--footer-->
  <div>
  </div>
  <!--footer end-->
 </body>
	<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
</html>
