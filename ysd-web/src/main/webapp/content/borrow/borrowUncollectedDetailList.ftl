<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <meta charset="gb2312" />
  <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-未收明细</title>
  <meta name="application-name" content="网络P2P借贷" /><#include "/content/common/meta.ftl">
   <meta name="msapplication-tooltip" content="网络P2P借贷-上海借贷-杭州借贷" />
   <meta http-equiv="x-ua-compatible" content="ie=7" />
	<meta name="keywords" content="${Application ["qmd.setting.metaKeywords"]}" />
	<meta name="description" content="${Application ["qmd.setting.metaDescription"]}" />
	<meta name="robots" content="all" />
	<link rel="shortcut icon" href="${base}/static/img/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="${base}/static/css/base.css" media="screen" />
	<link rel="stylesheet" href="${base}/static/css/center.css" media="screen" />
		
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
                             <li><a name="" href="${base}/borrow/userTenderBorrowList.do?bStatus=3,4">正在收款</a></li>
                             <li><a class="wtitle_lNa " name="" href="#">未收明细</a></li>
                             <li><a name="" href="${base}/borrow/receivedDetailList.do">已收明细</a></li>
							 <li><a name="" href="${base}/borrow/borrowDetailList.do">投标明细</a></li>
							 <li><a name="" href="${base}/borrow/userTenderBorrowList.do?bStatus=7">还清借款</a></li>
                          </ul>
                          <!--
						  <ul class="invest clearfix">
						      <li>借出总额：￥0</li>
							  <li>已收总额：￥0</li>
							  <li>未收总额：￥0</li>
							  <li>已收总额：￥0</li>
							  <li>未收利息：￥0</li>
						  </ul>
					-->
                          <form id="listForm" method="post" action="uncollectedDetailList.do" >
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
							        <th style="border-left: 1px solid #D8D8D8;" scope="col">借款标题</th>
									<th>应收日期</th>
									<th>借款者</th>
									<th>第几期/总期数</th>
									<th>收款总额</th>
									<th>应收本金</th>
									<th>应收利息</th>
<#--
									<th>逾期利息</th>
									<th>逾期天数</th>
									<th>状态</th>
-->
								</tbody>
                                   <tbody>
                                   <#list pager.result as borrowTemp>
									   <tr>
									   <td>${borrowTemp.id}</td>
									   <td><a href = "${base}/borrow/detail.do?bId=${borrowTemp.borrowId}" title = "${borrowTemp.borrowName}" target="_blank">${substring(borrowTemp.borrowName, 16, "")}</a></td>
									   <td>${(borrowTemp.repaymentTime?string("yyyy-MM-dd"))!}</td>
									   <td>${borrowTemp.borrowUserName}</td>
									   <td>${borrowTemp.borrowPeriods+1}/${borrowTemp.timeLimit}</td>
									   <td>${borrowTemp.repaymentAccount}</td>
									   <td>${borrowTemp.waitAccount}</td>
									   <td>${borrowTemp.waitInterest}</td>
<#--
									   <td>${borrowTemp.interestAccount?string.currency}</td>
								   		<td> 
								   			 <#if borrowTemp.lateInterest>
								   				${borrowTemp.lateInterest?string.currency}
								   		 	 </#if>
								   		 </td>
								   		  <td>
										<#if borrowTemp.repaymentDate>
											${overdue(borrowTemp.repaymentDate?string("yyyy-MM-dd"))}天
										</#if>
										
									   <td>
									   		<#if borrowTemp.status =0 >
									   			未还
									   		</#if>
									   	</td>
-->
										</tr>
									 </#list>
									  <!--page-->
										<@pagination pager=pager baseUrl="/borrow/uncollectedDetailList.do">
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
