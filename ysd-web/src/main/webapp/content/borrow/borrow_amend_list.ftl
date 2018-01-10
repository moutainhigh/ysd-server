<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <meta charset="gb2312" />
  <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户 会员中心（暂时无用）</title>
  <#include "/content/common/meta.ftl">
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
      <p class="c_b">
      	<a href="${base}/">${Application ["qmd.setting.name"]}</a> &gt; <a href="${base}/userCenter/index.do">我的账户</a> &gt <a href="#">支付密码</a></p>
	  </ul>
      <!--left-->
	   <#include "/content/common/user_center_left.ftl">
	         <!--right-->
<div class="c_right">
			     <div class="top_bg"></div>
				 <div class="rightwrap">
				      <div class="rightcon">
				       <ul class="wtitle_l">
                             <li><a <#if bStatus[0] == "1">class="wtitle_lNa"</#if> name="" href="${base}/borrow/userBorrowList.do?bStatus=1">正在招标</a></li>
                             <li><a <#if bStatus[0] == "-1">class="wtitle_lNa"</#if> name="" href="${base}/borrow/userBorrowList.do?bStatus=0">尚未发布</a></li>
                             <li><a <#if bStatus[0] == "3,4">class="wtitle_lNa"</#if> name="" href="${base}/borrow/userBorrowList.do?bStatus=3,4">正在还款</a></li>
                             <li><a name="" href="${base}/borrow/hkmx.do">我要还款</a></li>
							 <li><a name="" href="${base}/borrow/jkmx.do">借款明细</a></li>
							 <li><a name="" href="${base}/borrow/borrowList.do?borrow.status=7">已还借款</a></li>
							 <li><a name="" href="${base}/borrow/borrowList.do?borrow.status=3">已完成的借款</a></li>
                          </ul>
					
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
									   <td><a href="javascript:void(0);" class="deleteBank" borrowid="${borrow.id}">撤回</a>&nbsp;|&nbsp;<a href = "${base}/borrow/borrowMessage.do?borrow.id=${borrow.id}">修改</a></td>
										</tr>
									 </#list>
									 <!--page-->

                                       <tr  >
                                         <td align="right" colspan="9">
										     <ul class="fr">
                                             <#list 1..page.getPageCount() as p>
	  										<a href="userBorrowList.do?page.pageNumber=${p}&&bStatus=0">${p}</a>
											</#list>      
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
 
  <script type="text/javascript">
	$().ready( function() {
		$deleteBank = $(".deleteBank");
		$deleteBank.click(function(){
			$this=$(this);
			$.dialog({type: "warn", content: "确认要撤回此标吗？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			function right(){
				$.ajax({
					url: "${base}/borrow/ajaxBorrowRecall.do",
					data: {"borrow.id":$this.attr("borrowid")},
					type: "POST",
					dataType: "json",
					cache: false,
					success: function(data) {
						$.message({type: data.status, content: data.message});
						if(data.status=="success"){
							$this.parent().parent().remove();
						}
					}
				});
			}
		});
		
		
	});
 </script>
</html>
