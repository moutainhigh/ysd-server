<#assign menu="wylc">
<#include "/content/common/header.ftl">
<script type="text/javascript" src="${Application ["qmd.static.baseUrl"]}js/borrow/borrowInvest.js"></script>
<link rel="stylesheet" href="${Application ["qmd.static.baseUrl"]}css/jquery-ui/jquery-ui-1.9.2.css" />
<script src="${Application ["qmd.static.baseUrl"]}js/jquery-1.8.1.js"></script>
<script src="${Application ["qmd.static.baseUrl"]}js/jquery-ui-1.9.2.js"></script>
 <!--content-->
  <div class="index mt">
      <!--adone-->
        <div  style="MARGIN: 0px auto;DISPLAY: block" id="js_ads_banner_top">
	       <a href="#" target="_blank"><img src="${Application ["qmd.static.baseUrl"]}img/ad/banner_s.jpg" width=980 height=60></a>
        </div>
 
        <div  style=" DISPLAY: none" id="js_ads_banner_top_slide">
	       <a href="#" target="_blank"><img src="${Application ["qmd.static.baseUrl"]}img/ad/banner_b.jpg" width=980 height=400></a> 
        </div>
     
             <!--content-->
              <div class="content">
                  <!--left-->
                  <div class="show_l">
				       <h4 class="s_title">${borrow.name}</h4>
					   <div class="gift-exchange">
				<div class="ge-product ">
					<div class="gep-pic">
						<div class="gepPic">
							<#list borImageList as borrowTemp>
								<#if borrowTemp_index==0>
									<img src="<@imageUrlDecode imgurl="${borrowTemp}"; imgurl>${imgurl}</@imageUrlDecode>" width="310px" height="310px" alt="" />
								</#if>
							</#list>
						</div>
						<ul class="clearfix" id="gepPic">
							<#list borImageList as borrowTemp>
								<li <#if borrowTemp_index==0>class="on"</#if> ><img width="38px" height="38px" src="<@imageUrlDecode imgurl="${borrowTemp}"; imgurl>${imgurl}</@imageUrlDecode>" bigpic="<@imageUrlDecode imgurl="${borrowTemp}"; imgurl>${imgurl}</@imageUrlDecode>" alt="" /><s></s></li>
							</#list>
							<!--
							<li class="on"><img src="${Application ["qmd.static.baseUrl"]}img/pro/s1.jpg" bigpic="${Application ["qmd.static.baseUrl"]}img/pro/1.jpg" alt="" /><s></s></li>
							<li><img src="${Application ["qmd.static.baseUrl"]}img/pro/s2.jpg" bigpic="${Application ["qmd.static.baseUrl"]}img/pro/2.jpg" alt="" /><s></s></li>
                            <li><img src="${Application ["qmd.static.baseUrl"]}img/pro/s1.jpg" bigpic="${Application ["qmd.static.baseUrl"]}img/pro/1.jpg" alt="" /><s></s></li>
                            <li><img src="${Application ["qmd.static.baseUrl"]}img/pro/s2.jpg" bigpic="${Application ["qmd.static.baseUrl"]}img/pro/2.jpg" alt="" /><s></s></li>
                            -->
						</ul>
					</div>
 
					<!-- 基本的 -->
					<div class="gep-coefficient">
					    <ul >
						<p class="show_price">借款金额：<span class="red">￥<b>${borrow.account}</b></span> 元</p>
						</ul>
						<ul class="showe">
							<li><span>类 &nbsp;&nbsp; 型：<#if borrow.type == 1>天标</#if><#if borrow.type == 2>流转标</#if><#if borrow.type == 3>信用标</#if><#if borrow.type == 0>秒  标</#if><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发 标 者：${borrow.username}</span></li>
							<li><span>年 利 率：${borrow.apr}%</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>借款期限：${borrow.timeLimit}个月</span> </li>
							<!--
							<li><span>还款方式：按月分期</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>交易类型： 在线交易</span></li>
							-->
							<li><span>发布时间：${borrow.createDate?string("yyyy-MM-dd HH:mm:ss")}</span> </li>
						</ul>
						<div class="quantity">
                        <ul>
							<li><span class="loadfont"> 已经完成：<div class="progressbar_1"> 
        <div class="bar" style="width: ${borrow.schedule}%;"></div> 
    </div> 
							${borrow.schedule}%</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>还差：￥${borrow.balance}</span></li>
							　<li><span>最小投资额：￥${borrow.lowestAccount}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>最大投资额：<#if borrow.mostAccount == ''>无限制<#else>￥${borrow.mostAccount}</#if></span></li>　
							<li >剩余时间：
							<#if borrow.overDate??><span class="lxftime" endtime="${borrow.overDate?string("MM/dd/yyyy HH:mm:ss")}" balance="${borrow.balance}"></span></#if> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span><strong class="red showbtns">协议已签</strong></span></li>
							
							<li><input id="borrowInvests" type="button" onclick="location='invest.do?bId=${borrow.id}'" 
							<#if borrow.status == 0>value="发标待审中" disabled=disabled</#if>
							<#if borrow.status == 1>value="立即投标"</#if>
							<#if borrow.status == 2>value="发标审核拒绝" disabled=disabled</#if>
							<#if borrow.status == 3>value="正在还款中" disabled=disabled</#if>
							<#if borrow.status == 4>value="满标审核失败" disabled=disabled</#if>
							<#if borrow.status == 5>value="等待满标审核" disabled=disabled</#if>
							<#if borrow.status == 6>value="过期或撤回" disabled=disabled</#if>
							<#if borrow.status == 7>value="已还完" disabled=disabled</#if>
							 class="showbtn" ></input></li>
							<li class="shown">投资100元,年利率${borrow.apr}%，期限￥${borrow.timeLimit}个月,可获得利息收益￥${interest}元</li>
                            </ul>
						</div>
					</div>
				</div>
 
				 <!--tab-->
             <div class="showtab ">
               <ul id="selRoomTab">
						<li><a href="javascript:void(0)" class="wtitle_lNa"><span>借款详情</span></a></li>
						<li><a href="javascript:void(0)" ><span>借款者资料</span></a></li>
                        <li><a href="javascript:void(0)" ><span>待还记录</span></a></li>
                        <li><a href="javascript:void(0)" ><span>投标记录</span></a></li>
				</ul>	
                </div>
                <!--tab content-->
                <div id="selRoom" class="tab-con ">
                     <div class="showtab1">
						${borrow.content}
                    </div>
                
                <div class="showtab1 hide">
					 借款人ID:${user.id}<br>
					 借款人用户名:${user.username}<br>
					 借款人性别:<#if user.sex == 1>男<#else>女</#if><br>
					 借款人真实姓名:${user.name}<br>
					 地址:${user.address}<br>               
					</div>
                      <div class="showtab1 hide">
						 <table class="fieldset listtable">
							    
 
							       
							  <tbody>
									<th>借款标题</th>
									<th>期数</th>
									<th>还款本息</th>						
									<th>实际到期日期</th>
									
								</tbody>
 
                                   <tbody>
                                   <#list userBorrowList as userBorrow>
                                   	<tr>
									   <td>${userBorrow.name}</td>
									   <td>${userBorrow.timeLimit}</td>
									   <td>${userBorrow.name}</td>
                                        <td><#if userBorrow.endTime??>${userBorrow.endTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
										</tr>
                                   </#list>
									  <!--page
 
                                       <tr  >
                                         <td align="right" colspan="5">
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
							      -->
						  </table> 
					</div>
                      <div class="tab4 hide">
						 <table class="fieldset listtable">
							    
 
							  
							  <tbody>
							        <th style="border-left: 1px solid #D8D8D8;" scope="col">投标人</th>
									<th>年利率</th>
									<th>投标金额</th>
									<th>有效金额</th>
									<th>投标时间</th>						
									<th style="border-right: 1px solid #D8D8D8;">状态</th>
									
								</tbody>
 
                                   <tbody>
                                   <#list borrowTenderList as tender>
                                   	<tr>
									   <td>${tender.username}</td>
									   <td>${borrow.apr}%</td>
									   <td>￥${tender.money?string("0.00")}</td>
									   <td>￥${tender.account}</td>
                                        <td>${tender.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
									   <td><#if tender.status == "1">全部通过<#else>部分通过</#if></td>
										</tr>
                                   </#list>
									  <!--page
 
                                       <tr  >
                                         <td align="right" colspan="5">
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
							      -->
						  </table> 
						  
					</div>
					
                </div>
            
                <!--tab-->							
				
			</div>
			
		</div>
                        
                              <!--right-->
                             <div class="show_r">
                                  <!--news-->
                                      <div class="rbordert">
                                      <h3><strong>天标</strong><a href="#" target="_blank"><img src="${Application ["qmd.static.baseUrl"]}img/more.jpg"></a></h3>
                                       
                                      <ul class="news">
                                         <li><a href="#" target="_blank">全民贷7月份正式上线</a></li>
                                         <li><a href="#" target="_blank">关于我们--全民贷7月份正式上线</a></li>
                                         <li><a href="#" target="_blank">全民贷7月份正式上线</a></li>
                                         <li><a href="#" target="_blank">关于我们--全民贷7月份正式上线</a></li>
                                        <li><a href="#" target="_blank">关于我们--全民贷7月份正式上线</a></li>
                                        <li><a href="#" target="_blank">关于我们--全民贷7月份正式上线</a></li>
                                      </ul>
                                  </div>
								  
                                                     <!--entity-->
                                                     <div class="rbordert skill">
                                      <h3><strong>线下投标</strong><a href="#" target="_blank"><img src="${Application ["qmd.static.baseUrl"]}img/more.jpg"></a></h3>
                                       
                                      <ul class="news">
                                         <li><a href="#" target="_blank">全民贷7月份正式上线</a></li>
                                         <li><a href="#" target="_blank">关于我们--全民贷7月份正式上线</a></li>
                                         <li><a href="#" target="_blank">全民贷7月份正式上线</a></li>
                                         <li><a href="#" target="_blank">关于我们--全民贷7月份正式上线</a></li>
                                        <li><a href="#" target="_blank">关于我们--全民贷7月份正式上线</a></li>
                                        <li><a href="#" target="_blank">关于我们--全民贷7月份正式上线</a></li>
										 <li><a href="#" target="_blank">全民贷7月份正式上线</a></li>
                                         <li><a href="#" target="_blank">关于我们--全民贷7月份正式上线</a></li>
                                         <li><a href="#" target="_blank">全民贷7月份正式上线</a></li>
                                         <li><a href="#" target="_blank">关于我们--全民贷7月份正式上线</a></li>
                                        <li><a href="#" target="_blank">关于我们--全民贷7月份正式上线</a></li>
                                        <li><a href="#" target="_blank">关于我们--全民贷7月份正式上线</a></li>
                                      </ul>
                                  </div>
                                    
                                     </div>
 
                  </div>
                             
                                           
             </div>
                
<script> 
$(document).ready(function(){
	//  tab
	var selRoomTab = $("#selRoomTab a"),
		selRoom = $("#selRoom>div");
	selRoomTab.each(function(i){
		$(this).click(function(){
			selRoomTab.removeClass("wtitle_lNa").eq(i).addClass("wtitle_lNa");
			selRoom.hide().eq(i).show();
		});
	});
});
$(function(){
	var gP = $("#gepPic>li"),
		gPBox = $(".gepPic");
	gP
	.click(function(){
		gP.removeClass("on");
		$(this).addClass("on");
 
		gPBox.empty().html("<img src="+ $(this).children("img").attr("bigpic") +" />");
 
 
	});
});
</script>
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