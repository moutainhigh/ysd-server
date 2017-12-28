<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <meta charset="gb2312" />
  <title>${Application ["qmd.setting.name"]}-我的账户 会员中心（暂时无用页面）</title>
  <meta name="application-name" content="网络P2P借贷" />
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
                             <li><a <#if bStatus[0] == "3,4">class="wtitle_lNa "</#if> name="" href="../borrow/userTenderBorrowList.do?bStatus=3,4">正在收款</a></li>
                             <li><a name="" href="${base}/borrow/uncollectedDetailList.do">未收明细</a></li>
                             <li><a name="" href="${base}/borrow/receivedDetailList.do">已收明细</a></li>
							 <li><a name="" href="${base}/borrow/borrowDetailList.do">投标明细</a></li>
							 <li><a <#if bStatus[0] == "7">class="wtitle_lNa "</#if> name="" href="../borrow/userTenderBorrowList.do?bStatus=7">还清借款</a></li>
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
                              <ul class="invests">
							    
						      <li>发布时间 <input type="text"></li> <li>到 <input type="text"></li>
							  <li>关键字 <input type="text"></li> <li><button type="submit" class="investbtn">搜索</button></li>
							    </ul>
								
					          <table class="fieldset listtable">
							    
                              
							  <tbody>
							        <th style="border-left: 1px solid #D8D8D8;" scope="col">标题</th>
									<th>类型</th>
									<th>金额(元)</th>
									<th>年利率</th>
									<th>期限(月)</th>
									<th>发布时间</th>
									<th>进度</th>
									<th>状态</th>
									<th>操作</th>
									
								</tbody>

                                   <tbody>
                                   <#list page.result as borrow>
									   <tr>
									   <td>${borrow.name}</td>
									   <td>${borrow.type_id}</td>
									   <td>￥${borrow.amount}</td>
									   <td>${borrow.apr}%</td>
									   <td>${borrow.timeLimit}月</td>
									    <td>${borrow.addTime?string("yyyy-MM-dd HH:mm:ss")}</td>
									   <td>${borrow.schedule}%</td>
									   <td>有效</td>
									   <td>删除</td>
										</tr>
									 </#list>
									  <!--page-->

                                       <tr  >
                                         <td align="right" colspan="9">
										     <ul class="fr">
                                              <span>
                                              <label>共 （8） 页</label>
                                               </span>
                                              <span>首页</span>
                                             <span>上一页</span>
                                               <span>下一页</span>
											   <span>尾页</span>
											   </ul>
                                                </td>
                                            </tr>
						      
							      </tbody>
						  </table> 
                          
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
</html>
