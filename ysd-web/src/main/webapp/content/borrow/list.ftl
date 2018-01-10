<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
    <#include "/content/common/meta.ftl">
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-产品列表</title>
	<link rel="stylesheet" href="${base}/css/common.css" />
	<link rel="stylesheet" href="${base}/css/product_list.css" />
</head>
<body>
	<!-- 头部 -->
	<#include "/content/common/header.ftl">
    <!-- 主内容区 -->
    <div id="content">
    <form id="listForm" method="post" action="listNew.do" >
    	<div class="firstArea">
    		<div class="condition">	
    			<input type="hidden" id="qbtype_input" name="qbtype" value="${qbtype?default("0")}"/>
    			<input type="hidden" id="qbstatus_input" name="qbstatus" value="${qbstatus?default("0")}"/>
    			<input type="hidden" id="qblimit_input" name="qblimit" value="${qblimit?default("0")}"/>
	    		<div class="nav">
	    			<ul>
	    				<li>项目类型：</li>
	    				<li>项目状态：</li>
	    				<li>项目期限：</li>
	    			</ul>
	    		</div>
	    		<div class="navList">
	    			<ul>
	    				<li onclick="queryBorrow(1,'0');" <#if qbtype=="0" >class="active"</#if> ><i class="left"></i><span>全部</span><i class="right"></i></li>
	    				<li onclick="queryBorrow(1,'16');" <#if qbtype=="16" >class="active"</#if> ><i class="left"></i><span>新手标</span><i class="right"></i></li>
	    				<li onclick="queryBorrow(1,'14');" <#if qbtype=="14" >class="active"</#if> ><i class="left"></i><span>优质标</span><i class="right"></i></li>
	    			</ul>
	    			<ul>
	    				<li onclick="queryBorrow(2,'0');" <#if qbstatus=="0" >class="active"</#if> ><i class="left"></i><span>全部</span><i class="right"></i></li>
	    				<li onclick="queryBorrow(2,'1');" <#if qbstatus=="1" >class="active"</#if> ><i class="left"></i><span>投标中</span><i class="right"></i></li>
	    				<li onclick="queryBorrow(2,'2');" <#if qbstatus=="2" >class="active"</#if> ><i class="left"></i><span>还款中</span><i class="right"></i></li>
	    				<li onclick="queryBorrow(2,'3');" <#if qbstatus=="3" >class="active"</#if> ><i class="left"></i><span>已结束</span><i class="right"></i></li>
	    			</ul>
	    			<ul>
	    				<li onclick="queryBorrow(3,'0');" <#if qblimit=="0" >class="active"</#if> ><i class="left"></i><span>全部</span><i class="right"></i></li>
	    				<li onclick="queryBorrow(3,'1');" <#if qblimit=="1" >class="active"</#if> ><i class="left"></i><span>0~1个月</span><i class="right"></i></li>
	    				<li onclick="queryBorrow(3,'2');" <#if qblimit=="2" >class="active"</#if> ><i class="left"></i><span>1~3个月</span><i class="right"></i></li>
	    				<li onclick="queryBorrow(3,'3');" <#if qblimit=="3" >class="active"</#if> ><i class="left"></i><span>3~6个月</span><i class="right"></i></li>
	    				<li onclick="queryBorrow(3,'4');" <#if qblimit=="4" >class="active"</#if> ><i class="left"></i><span>6~12个月</span><i class="right"></i></li>
	    				<li onclick="queryBorrow(3,'5');" <#if qblimit=="5" >class="active"</#if> ><i class="left"></i><span>12个月以上</span><i class="right"></i></li>
	    			</ul>
    			</div>
    		</div>
    		<div class="websiteData">
    			<p>网站数据</p>
    			<div class="data">
    				<div>
						<img src="${base}/img/icon_1.png" alt="" />
						<span>会员人数：<span class="num person_num"></span>位</span>
    				</div>
    				<div>
						<img src="${base}/img/icon_2.png" alt="" />
						<span>累计投资金额：<span class="num inverstment_num"></span>元</span>
    				</div>
    				<div>
						<img src="${base}/img/icon_3.png" alt="" />
						<span>为用户赚取：<span class="num profit_num"></span>元</span>
    				</div>
    			</div>
    		</div>
    	</div>
    	<div class="secondArea">
	    	<div class="leftArea">
	    	
	    	<#list pager.result as borrow>
	    			
	    			<div class="bid <#if borrow.type == "16"> new</#if><#if borrow.schedule == "100"> end</#if>">
	    			<img src="${base}/img/new_icon_03.png">
		  			<div class="title">${substringWord(borrow.name, 20)}</div>
		  			<div class="Profit">
		  				<p>年化收益</p>
		  				<span><@numberPercent val="${borrow.baseApr}"; npt>${npt?substring(0,(npt?length-1))}</@numberPercent></span<span style="font-size:20px;color:#ef3e44;font-style:none">%</span>
				        <#if borrow.awardApr gt 0>
				        	<span class="jiaxi">+<@numberPercent val="${borrow.awardApr}"; npt>${npt}</@numberPercent></span>
	               	 
	               	 	</#if>
		  			</div>
		  			<div class="limit">
		  				<p class="term">项目期限<br /><span>${borrow.timeLimit}天</span></p>
		  				<p class="sumMoney">项目总额<br />
			  				<span>
				  				<#if borrow.account?number gt 10000>
						            ${((borrow.account?eval)*0.0001)?string("0.00")}万元
					            <#else>
					            	${borrow.account}元
			            		</#if>
			  				</span>
		  				</p>
		  				<div class="progress">
			        		<div class="progressY" style="width:${borrow.schedule}%"></div>
		            	</div>
		  			</div>
		  			
				    <div class="result" style="overflow:hidden">
				    	<p class="sold">已售：<span class="progressNum">${borrow.schedule}%</span></p>
				    	<p class="totol">
					    	剩余可投金额：
					    	<span>
						    	 <#if borrow.balance?number gt 10000>
					            	 ${((borrow.balance?eval)*0.0001)?string("0.00")}万元
					            <#else>
					            	${borrow.balance}元
			            		</#if>
					    	</span>
					    </p>
				    </div>
		   			 <a class="toubiaoBtn" href="${base}/borrow/detail.do?bId=${borrow.id}"><#if borrow.status==1||borrow.status==5>立即投标<#elseif borrow.status==3>还款中<#else>已结束</#if></a>
		  		</div>
	    	</#list>
	    	 <div class="clear"></div>
			<#if pager.totalCount==0||pager.totalCount==null>
				<div class="nodata"  align="center" >项目记录为空</div>
			<#else>
				<@pageFlipNew pager=pager>
					<#include "/content/borrow/pager.ftl">
				</@pageFlipNew>	
			</#if>
			
		  	</div>
		  	<div class="rightArea">
		  		<div class="activity">
		  			<h1>活动专区</h1>
		  			<a href='${base}/activity/list.do'><img src="${base}/img/newactivte.jpg"></a>
		  		</div>
		  		<div class="invest_records">
		  			<h1>投资记录</h1>
		  			<div class="investScroll">
			   	  		<ul class="investList">
		   	    			<#list borrowTenderList as bt>
				               <li>
		   	    			    <div class="investInfo">
		   	    			    	<p>
		   	    			    	<span class="user">${substringWord(bt.username,3,"*****")}<#if bt.username?length gt 10>${bt.username?substring(8,11)}</#if></span>
		   	    			    	投资
		   	    			    	<span class="bid"><a href="${base}/borrow/detail.do?bId=${bt.borrowId}"  style="color:#7c96e8;" class="fr" target="_blank"> ${substringWord(bt.title, 9, "…")}</a></span>
		   	    			    	</p>
		   	    			    	<p><span class="money">￥${bt.account?eval?string("0.00")}</span><span class="time">${bt.createDateStr}</span></p>
		   	    			    </div>
		   	    			 </li>
					        </#list> 
						</ul>
	   	    		</div>
		  		</div>
		  		<div class="question">
		  			<h1>帮助中心</h1>
					<a href="${base}/article/list/help_center.htm"><span>更多...</span></a>
					<div class="questionScroll">
						<ul class="question_list">
						<@article_list sign='help_center' count=5; mtList>
					           <#list mtList as mt>
					           	   <li>
					           	   	 	<a href="<#if mt.url!><@mt.url?interpret /><#else>${base}/article/content/${mt.id}.htm</#if>" > ${substringWord(mt.title, 22, "")}</a>
					           	   </li>
				        	 	</#list>
				        </@article_list>
						</ul>
					</div>
		  		</div>
		  	</div>
    	</div>
    	<div class="clear"></div>
    	<div class="invite_friends">
    		<a href="${base}/award/toGetLink.do"><img src="${base}/img/hongbao_bg.png" /></a>
    	</div>
    </form>
	</div>
	<!-- 尾部 -->
	<#include "/content/common/footer.ftl">
</body>
<script src="${base}/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${base}/js/scroll.js"></script>
<script src="${base}/js/common.js"></script>
<script type="text/javascript" src="${base}/js/product_list.js"></script>
</html>