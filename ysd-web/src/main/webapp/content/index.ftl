<!DOCTYPE html>
<html lang="en">
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台</title>
	<#include "/content/common/meta.ftl">
	<link rel="stylesheet" href="${base}/css/common.css" />
	<link rel="stylesheet" href="${base}/css/index.css" />
</head>
<body>
     <!-- header -->
<#include "/content/common/header.ftl">

    <!-- 主内容区 -->
    <div id="content">
		<!-- 注册登录模块	 -->
    	<#if loginUser>
        <#else>
        <div class="loginArea">
			<div class="register">
				<p class="money">起投金额: <span>100元</span></p>
				<p class="name">超高年化收益率</p>
				<p class="percent">14.00%</p>
				<div class="line"></div>
				<p class="word">投资理财更安全</p>
				<p class="word">注册即送<span>580元</span>红包</p>
				<a class="regiBtn" href="${base}/user/reg.do">立即注册</a>
				<div class="login">
					<p>已有账号？<a style="color:#eae36c;display:inline-block" class="loginBtn" href="${base}/user/login.do">立即登录</a></p>
				</div>
			</div>
		</div>
       
      	</#if>
    	
    	
    	
    	
         <!-- banner区 -->
		<div class="bannerArea">
			<ul class="banner">
				
            <#list scrollpicList as scrollpic>
					<li><img  
						&lt;#&ndash;onclick="javascript:window.open('<@scrollpic.url?interpret />');" &ndash;&gt;
					src="<@imageUrlDecode imgurl="${scrollpic.img}"; imgurl>${imgurl}</@imageUrlDecode>"/></li>


				</#list>
				
			</ul>
			<div class="dot">
				
				<#list scrollpicList as scrollpic>
				
					 <#if scrollpic_index == 0>
				      <span class="dotList active1"></span>
				      <#else>
				      <span class="dotList"></span>
				     </#if>
					
					
				</#list>
				
			</div>
			
			
			
			<span class="left fl"></span>
			<span class="right fr"></span>
		</div>
    	<!-- 网站信息区 -->
	   <div class="introduce">
	   	  <ul class="intro_1">
              <li class="li1">
                  <div class="div_1">
					  <i></i>
                      <p class="title">收益丰厚</p>
                      <p class="detail">注册即送588红包</p>
                  </div>
              </li>
              <li class="li2">
                  <div class="div_2">
                      <i></i>
                      <p class="title">风控全面</p>
                      <p class="detail">层层把关打造行业风控闭环</p>
                  </div>
              </li>
              <li class="li3">
                  <div class="div_3">
                      <i></i>
                      <p class="title">银行存管</p>
                      <p class="detail">银行资金存管</p>
                  </div>
              </li>
              <li class="li4">
                  <div class="div_4">
                      <i></i>
                      <p class="title">资金安全</p>
                      <p class="detail">存管监控，本息到账率100%</p>
                  </div>
              </li>
	   	  </ul>
	   </div>
	   <div class="conWrap">
	   	  <!-- 新手福利 -->
           <div class="index-title">
               <p>爆款推荐</p>
               <span></span>
           </div>
	        <div class="noviceArea">
		        <div class="leftImg">
		        	<img class="noviceImg_1" src="${base}/img/novice_1.png" />
		        	<p class="noviceWord_1">爆款推荐</p>
		        	<p class="noviceWord_2">限量购买 先到先得</p>
		        </div>
		        <div class="noviceInfo">
		        <#if list1?size!=0>
		        	<div class="noviceTitle">
		        	   <p><a href="${base}/borrow/detail.do?bId=${list1.get(0).id}">${list1.get(0).name}</a></p>
		        	</div>
		        	<ul class="invest">
		        		<li>

			        		<p class="rate"><@numberPercent val="${list1.get(0).baseApr}"; npt>${npt?substring(0,npt?length-1)}</@numberPercent><span style="font-size:20px;color:#ef3e44">%</span></p>
			        		<span>预期年化收益</span>
			        		<#if list1.get(0).awardApr gt 0>
			        			<div class="jiaxi">+<@numberPercent val="${list1.get(0).awardApr}"; npt>${npt}</@numberPercent></div>
			        		</#if>

		        		</li>
		        		<li>
		        			<p>${list1.get(0).timeLimit}天</p>
			        		<span>项目期限</span>
		        		</li>
		        		<li>
		        			<p>
			        			<#if list1.get(0).balance?number gt 10000>
	            	 				${((list1.get(0).balance?eval)*0.0001)?string("0.00")}万
	            				<#else>
	            					${list1.get(0).balance?eval?string("0.00")}元
	            				</#if>
            				</p>
			        		<span>剩余金额</span>
		        		</li>
		        	</ul>
		        	<div class="progress">
		        		<div class="progressY" style="width:${list1.get(0).schedule}%"></div>
		        	</div>
		        	<a class="toubiaoBtn" href="${base}/borrow/detail.do?bId=${list1.get(0).id}">立即投标</a>
                </#if>
				<#if list1?size==0>
				    <div class="none-list">更多精彩，敬请期待</div>
				</#if>
		        </div>
		        <div class="rightImg">
		        	<img class="noviceImg_2" src="${base}/img/novice_2.png" />
		        	<div>
		        		<p class="p1">活动专区</p>
		        		<p class="p2">参与即送红包！</p>
		        		<a class="detailBtn" href="${base}/activity/list.do">查看详情</a>
		        	</div>
		        </div>
	        </div>
	     <!-- 热门推荐 -->
	   	    <div class="hotTuiJian">
	   	  	    <div class="ltArea">
		   	  	  	<ul class="investType clearfix">
		   	  	  		<li class="active2"><p>定期优选 </p><span></span></li>
		   	  	  		<li><p>精品理财</p><span></span></li>
		   	  	  		<li><p>新手专享</p><span></span></li>
		   	  	  	</ul>
	   	  	    </div>
	   	  	    <div class="gtArea">
                    <ul class="clearfix">
                    <#if list4?? && (list4?size > 0) >
                        <#list list4 as borrow>
                            <li class="extra">
                                <div class="title">${borrow.name}</div>
                                <div class="Profit">
                                    <p>年化收益</p>
                                    <span><@numberPercent val="${borrow.baseApr}"; npt>${npt?substring(0,npt?length-1)}</@numberPercent></span><span style="font-size:20px;color:#ef3e44;font-style:none">%</span>
                                    <#if borrow.awardApr gt 0>
                                        <span class="jiaxi">+<@numberPercent val="${borrow.awardApr}"; npt>${npt}</@numberPercent></span>
                                    </#if>
                                </div>
                                <div class="limit">
                                    <p class="term">项目期限<br /><span>${borrow.timeLimit}<font style="font-size:16px;color:#808080;font-weight:normal;">天</font></span></p>
                                    <p class="sumMoney">项目总额<br />
                                        <span>
                                            <#if borrow.account?number gt 10000>
                                            ${((borrow.account?eval)*0.0001)?string("0.00")}<font style="font-size:16px;color:#808080;font-weight:normal;">万元</font>
                                            <#else>
                                            ${borrow.account}<font style="font-size:16px;color:#808080;font-weight:normal;">元</font>
                                            </#if>
                                            </span>
                                    </p>
                                </div>
                                <div class="progress">
                                    <div class="progressY" style="width:${borrow.schedule}%"></div>
                                </div>
                                <div class="result">
                                    <p><!--已售：<span class="progressNum">${borrow.schedule}%</span>-->剩余可投金额：<span>
                                        <#if borrow.balance?number gt 10000>
                                        ${((borrow.balance?eval)*0.0001)?string("0.00")}<font style="font-size:16px;color:#808080;font-weight:normal;">万元</font>
                                        <#else>
                                        ${borrow.balance}<font style="font-size:16px;color:#808080;font-weight:normal;">元</font>
                                        </#if>
                                        </span></p>
                                </div>
                                <a class="toubiaoBtn" href="${base}/borrow/detail.do?bId=${borrow.id}">${borrow.showButtonName}</a>
                            </li>
                        </#list>
                    <#else >
                        <div class="no-data">更多精彩,敬请期待</div>
                    </#if>
                    </ul>
				     <ul class="clearfix" style="display:none">
                        <#if list3?? && (list3?size > 0) >
                              <#list list3 as borrow>
                                    <li class="extra">
                                        <div class="title">${borrow.name}</div>
                                        <div class="Profit">
                                            <p>年化收益</p>
                                            <span><@numberPercent val="${borrow.baseApr}"; npt>${npt?substring(0,npt?length-1)}</@numberPercent></span><span style="font-size:20px;color:#ef3e44;font-style:none">%</span>
                                            <#if borrow.awardApr gt 0>
                                                <span class="jiaxi">+<@numberPercent val="${borrow.awardApr}"; npt>${npt}</@numberPercent></span>
                                            </#if>
                                        </div>
                                        <div class="limit">
                                            <p class="term">项目期限<br /><span>${borrow.timeLimit}<font style="font-size:16px;color:#808080;font-weight:normal;">天</font></span></p>
                                            <p class="sumMoney">项目总额<br />
                                                <span>
                                                     <#if borrow.account?number gt 10000>
                                                         ${((borrow.account?eval)*0.0001)?string("0.00")}<font style="font-size:16px;color:#808080;font-weight:normal;">万元</font>
                                                    <#else>
                                                        ${borrow.account}<font style="font-size:16px;color:#808080;font-weight:normal;">元</font>
                                                    </#if>
                                                </span>
                                            </p>
                                        </div>
                                        <div class="progress">
                                            <div class="progressY" style="width:${borrow.schedule}%"></div>
                                        </div>
                                        <div class="result">
                                            <p><!--已售：<span class="progressNum">${borrow.schedule}%</span>-->剩余可投金额：<span>
                                             <#if borrow.balance?number gt 10000>
                                                 ${((borrow.balance?eval)*0.0001)?string("0.00")}<font style="font-size:16px;color:#808080;font-weight:normal;">万元</font>
                                            <#else>
                                                ${borrow.balance}<font style="font-size:16px;color:#808080;font-weight:normal;">元</font>
                                            </#if>
                                            </span></p>
                                        </div>
                                        <a class="toubiaoBtn" href="${base}/borrow/detail.do?bId=${borrow.id}">${borrow.showButtonName}</a>
                                    </li>
                               </#list>
                        <#else >
                            <div class="no-data">更多精彩,敬请期待</div>
                        </#if>
				    </ul>
				     <ul class="clearfix" style='display:none;'>
                      <#if list2?? && (list2?size > 0) >
                          <#list list2 as borrow>
                                <li class="extra">
                                    <div class="title">${borrow.name}</div>
                                    <div class="Profit">
                                        <p>年化收益</p>
                                        <span><@numberPercent val="${borrow.baseApr}"; npt>${npt?substring(0,npt?length-1)}</@numberPercent></span><span style="font-size:20px;color:#ef3e44;font-style:none">%</span>
                                        <#if borrow.awardApr gt 0>
                                            <span class="jiaxi">+<@numberPercent val="${borrow.awardApr}"; npt>${npt}</@numberPercent></span>
                                        </#if>
                                    </div>
                                    <div class="limit">
                                        <p class="term">项目期限<br /><span>${borrow.timeLimit}<font style="font-size:16px;color:#808080;font-weight:normal;">天</font></span></p>
                                        <p class="sumMoney">项目总额<br /><span>
                                                <#if borrow.account?number gt 10000>
                                                     ${((borrow.account?eval)*0.0001)?string("0.00")}<font style="font-size:16px;color:#808080;font-weight:normal;">万元</font>
                                                <#else>
                                                    ${borrow.account}<font style="font-size:16px;color:#808080;font-weight:normal;">元</font>
                                                </#if>
                                        </span></p>
                                    </div>
                                    <div class="progress">
                                        <div class="progressY" style="width:${borrow.schedule}%"></div>
                                    </div>
                                    <div class="result">
                                        <p><!--已售：<span class="progressNum">${borrow.schedule}%</span>-->剩余可投金额：<span>
                                         <#if borrow.balance?number gt 10000>
                                             ${((borrow.balance?eval)*0.0001)?string("0.00")}<font style="font-size:16px;color:#808080;font-weight:normal;">万元</font>
                                        <#else>
                                            ${borrow.balance}<font style="font-size:16px;color:#808080;font-weight:normal;">元</font>
                                        </#if>
                                        </span></p>
                                    </div>
                                    <a class="toubiaoBtn" href="${base}/borrow/detail.do?bId=${borrow.id}">${borrow.showButtonName}</a>
                                </li>
                           </#list>
                      <#else >
                          <div class="no-data">更多精彩,敬请期待</div>
                      </#if>
				    </ul>
	   	  	    </div>
	   	    </div>
	   </div>
   	  <!-- why-choice -->
		<div class="index-title">
			<p>为何选择乐商贷</p>
			<span></span>
		</div>
   	    <div class="choice-reason">
	   	    <ul class="clearfix">
	   	  	    <li>
					<div class="icon icon1"></div>
					<p class="title">合规</p>
					<div class="detail">
						<p>渤海银行直接管存</p>
						<p>ICP信息服务业务许可证</p>
					</div>
				</li>
                <li>
                    <div class="icon icon2"></div>
                    <p class="title">安全</p>
                    <div class="detail">
                        <p>多重风控+小额分散</p>
                        <p>独立的第三方机构担保</p>
                    </div>
                </li>
                <li>
                    <div class="icon icon3"></div>
                    <p class="title">权威</p>
                    <div class="detail">
                        <p>乐清市投资协会常务副会长单位</p>
                    </div>
                </li>
                <li>
                    <div class="icon icon4"></div>
                    <p class="title">实力</p>
                    <div class="detail">
                        <p>注册资本金实到5000万</p>
                    </div>
                </li>
	   	    </ul>
   	   </div>
	   	  <!-- 实时数据区 -->
   	    <div class="dongtaiArea">
   	       <div class="wrap3">
   	            <!-- 媒体报道 -->
		   	    <div class="media">
		   	    	<div class="mediaTitle">
						媒体报道
						<a href="${base}/article/list/media_report.htm"><span class="more">更多<i><img src="./${base}/img/arrow-right.png"></i></span></a>
					</div>
					<#list articleListMt as mt>
					<#if (mt_index ==0)>
		   	    	<div class="mediaInfo">
		   	    		<#if  mt.coverImg==''>
		   	 			   <img src="${base}/img/mt.png" class="fl">
		   	    		<#else>
		   	    		 <img src="${Application["qmd.img.baseUrl"]}/web${mt.coverImg}" class="fl">
		   	    		</#if>	
		   	    		<div class="article">
		   	    			<a href="${base}/article/content/${mt.id}.htm"><h3><#if mt.author!>${mt.author}<#else>媒体报道</#if>：${mt.title}</h3></a>
		   	    			<p>${substringWord(mt.metaDescription, 36, "...")}</p>
		   	    		</div>
		   	    	</div>		
		   	     	</#if>
		   	     	<ul class="mediaText">  	
	   	    		<#if (mt_index >=1)>
	   	    			<li><#if mt.author!>${mt.author?replace('\\d{4}年\\d{1,2}月\\d{1,2}日$','','r')}<#assign str_long=mt.author?replace('\\d{4}年\\d{1,2}月\\d{1,2}日$','','r')/><#else>媒体报道<#assign str_long="媒体报道"/></#if> ：<a href="${base}/article/content/${mt.id}.htm" title="${mt.title}">${substringWord(mt.title,32-(str_long?length),"...")}</a></li>
	   	    			</#if>
	   	    		</#list>
	   	    		</ul>
	   	    		
		   	    </div>
		   	     <!-- 网站公告-->
		   	  	<div class="company">
		   	  		<div class="companyTitle">
						网站公告
						<a href="${base}/article/list/site_notice.htm"><span class="more">更多<i><img src="${base}/img/arrow-right.png" alt=""></i></span></a>
					</div>
                    <ul class="companyText">
                    	<@article_list sign='site_notice' count=6; mtList>
				           	<#list mtList as mt>
				           			<#if mt_index gt 0>
				           				<li >
				           			<#else>
				           				<li class="first">
				           			</#if>
				           			
				           				<a href="<#if mt.url!><@mt.url?interpret /><#else>${base}/article/content/${mt.id}.htm</#if>">
				           					${substringWord(mt.title, 16, "...")}
				           				</a> 
				           				<span class="time fr">${mt.createDate?string("yyyy-MM-dd")}</span>
					           				<#if mt_index gt 0>
					           				<#else>
					           				<i></i>
						           			</#if>
				           			</li>
			        	 	</#list>
			        	</@article_list>
                    </ul>
		   	  	</div>
	   	  	    <!-- 最新投资 -->
		   	  	<div class="newInvest">
		   	  		<div class="newInvestTitle">
						最新投资
						<a href="${base}/borrow/listNew.do"><span class="more">更多<i><img src="./${base}/img/arrow-right.png" alt=""></i></span></a>
					</div>
		   	  		<div class="investScroll">
			   	  		<ul class="investList">
			   	  		
			   	  			<#list borrowTenderList as bt>
				                
				                
				                <li>
		   	    			    <div class="investInfo">
		   	    			    	<p><span class="user">${substringWord(bt.username,3,"*****")}<#if bt.username?length gt 10>${bt.username?substring(8,11)}</#if>&nbsp;</span>
		   	    			    	投资 <span class="bid"><a href="${base}/borrow/detail.do?bId=${bt.borrowId}"  style="color:red;"  target="_blank"> ${substringWord(bt.title, 11, "…")}</a></span>
		   	    			    	</p>
		   	    			    	<p><span class="money">￥${bt.account?eval?string("0.00")}</span><span class="time">${bt.createDateStr}</span></p>
		   	    			    </div>
		   	    				</li>
				                
				                
					        </#list>  
			   	  		
			   	  		
			   	  		
		   	    		</ul>
	   	    		</div>
		   	  	</div>
	   	    </div>
   	    </div>
	   	  <!-- 乐商贷成就 -->
		<div class="achievement">
			<#--<div class="achieveInfo">
				<h3>乐商贷成就</h3>
				<div class="rotate" data-setting='{
                                                "width":1000,
                                                "height":320,
                                                "posterWidth":320,
                                                "posterHeight":320,
                                                "scale":1,
                                                "autoPlay":true,
                                                "delay":3000,
                                                "speed":300,
                                                "vericalAlign":"top"
    												}'>
					<div class="prevent-btn"></div>
					<ul class="achieveList">
						<li class="achieveItem"><a target="_blank" href="${base}/article/list/honor.htm"><img src="${base}/img/achievement1.png"  /></a></li>
						<li class="achieveItem"><a target="_blank" href="${base}/article/list/honor.htm"><img src="${base}/img/achievement2.png" /></a></li>
						<li class="achieveItem"><a target="_blank" href="${base}/article/list/honor.htm"><img src="${base}/img/achievement3.png" /></a></li>
						<li class="achieveItem"><a target="_blank" href="${base}/article/list/honor.htm"><img src="${base}/img/achievement4.png" /></a></li>
						<li class="achieveItem"><a target="_blank" href="${base}/article/list/honor.htm"><img src="${base}/img/achievement5.png" /></a></li>
					</ul>
					<div class="next-btn"></div>
				</div>
			</div>-->
	   	  <div class="wrap_1">
	   	  	  <div class="wrapArea">
	   	  	  	 <!-- 合作伙伴 -->
			   	  <div class="partner">
			   	  	 <h3>合作伙伴</h3>
			   	  	 <ul class="partnerList clearfix">
			   	  	 	<#list huobanpicList as item>
			   	  	 		<li><a href="<@imageUrlDecode imgurl="${item.img}"; imgurl>${imgurl}</@imageUrlDecode>" target="_blank"><img width="237px" height="60px" src="<@imageUrlDecode imgurl="${item.img}"; imgurl>${imgurl}</@imageUrlDecode>" /></a></li>
						</#list> 
			   	  	 </ul>
			   	  </div>
	   	  	  </div>
	   	  </div>
		</div>
	   	  <!-- 右侧工具栏 -->
        <div class="rightNav">
        	<ul>
        		<li class='calc'></li>
        		<li class='wxewm'>
        			<img src="${base}/img/yueshangdai_weixin_gzh.jpg" alt="二维码" />
        		</li>
        		<li class="navtop"></li>
        	</ul>
        </div>
    </div>
    <!-- 尾部 -->
   <#include "/content/common/footer.ftl">
   
    <script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${base}/js/slider.js"></script>
	<script type="text/javascript" src="${base}/js/carousel.js"></script>
	<script type="text/javascript" src="${base}/js/scroll.js"></script>
	<script type="text/javascript" src="${base}/js/index.js"></script>
	<script type="text/javascript" src="${base}/js/common.js"></script>
     <script type='text/javascript'>
         (function(m, ei, q, i, a, j, s) {
             m[i] = m[i] || function() {
                         (m[i].a = m[i].a || []).push(arguments)
                     };
             j = ei.createElement(q),
                     s = ei.getElementsByTagName(q)[0];
             j.async = true;
             j.charset = 'UTF-8';
             j.src = 'https://static.meiqia.com/dist/meiqia.js?_=t';
             s.parentNode.insertBefore(j, s);
         })(window, document, 'script', '_MEIQIA');
         _MEIQIA('entId', 72876);
     </script>
</body>
</html>