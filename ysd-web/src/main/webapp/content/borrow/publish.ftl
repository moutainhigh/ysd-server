<!DOCTYPE html>
<html>
  <head>
    <title>MyHtml.html（暂时无用页面）</title>
	
    <#include "/content/common/meta.ftl">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    	<script type="text/javascript" src="${base}/static/js/jquery/jquery-1.8.1.js"></script>
    
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
	<!--
	<script type="text/javascript">
	
		$(document).ready(
			function(){
				alert("jquery加载成功");
			}
		);
		
	</script>
	-->
	<script type="text/javascript"> 
 
</script>
  </head>
  
  <body>
  <!-- header -->
<#include "/content/common/header.ftl">
<!--content-->
  <div class="index mt">
  <ul class="cb">
      <p class="c_b"><a href="http://www.swdai.com" class="cbhome"> </a> <a href="#" class="cbp">我的账户</a> &gt <a href="#">支付密码</a></p>
	  </ul>
        <!--left-->
	   <#include "/content/common/user_center_left.ftl">
	         <!--right-->
	         <div class="c_right">
			     <div class="top_bg"></div>
				 <div class="rightwrap">
				      <div class="rightcon">
				       <ul class="wtitle_l">
                             <li><a class="wtitle_lNa" name="" href="#">正在招标</a></li>
                             <li><a  name="" href="${base}/#">尚未发布</a></li>
                             <li><a name="" href="${base}/#">正在还款</a></li>
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
									   <td><#if (borrow.type)==1>
									   		质押
									   		<#else>
									   		秒标
									   		</#if>
									   </td>
									   <td>￥${borrow.account}</td>
									   <td>${borrow.apr}%</td>
									   <td>${borrow.timeLimit}个月</td>
									    <td></td>
									   <td>${borrow.schedule}%</td>
									   <td>
									   	<#if (borrow.status)==0>
									   		发布审批中
									   	<#elseif (borrow.status)==1>
									   		正在招标
									   	<#elseif (borrow.status)==2>
									   		发标审核失败
									   	<#elseif (borrow.status)==4>
									   		满标审核失败
									   	<#else>
									   		撤回
									   	</#if>
									   </td>
									   <td>删除</td>
										</tr>
    	<a href=""></a><br>
    								</#list>
									  
									 
									  <!--page-->

                                       <tr  >
                                         <td align="right" colspan="9">
										     <ul class="fr">
                                             <#list 1..page.getPageCount() as p>
	  										<a href="borrowList.do?page.pageNumber=${p}&&borrow.status=1">${p}</a>
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


  </div>
  <!--footer-->
  <#include "/content/common/foot.ftl">
  <div>
  
  </div>
</body>
</html>
