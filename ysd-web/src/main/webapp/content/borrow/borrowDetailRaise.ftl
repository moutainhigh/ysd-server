<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-产品详情</title>
    <link rel="stylesheet" href="${base}/css/common.css">
    <link rel="stylesheet" href="${base}/css/product_detail.css">
    <style>
    	#header .header_wrap .header_shadow {
    		background-color: #ffffff;
		    background-color: #ffffff\9;
		    opacity: 1;
		    filter: alpha(opacity=1);
    	}
    </style>
</head>
<body>
    <!-- 头部 -->
	<#include "/content/common/header.ftl">
    <!--产品名称-->
    <div class="content"></div>
    <div class="product_name">
        <div class="left">
            <div class="name">${borrow.name}</div>
            <table class="details">
				<tr class="num">
					<td class="first"><@numberPercent val="${borrow.baseApr}"; npt>${npt}</@numberPercent><#if borrow.awardApr gt 0><span class="msg">+<@numberPercent val="${borrow.awardApr}"; npt>${npt}</@numberPercent></span></#if></td>
					<td>${borrow.timeLimit}<span><#if borrow.isday==0>个月<#else>天</#if></span></td>
					<td>
						<#if borrow.account?number gt 10000>
							 ${((borrow.account?eval)*0.0001)?string("0.##")}万
					    <#else>
						 ${borrow.account?eval?string("0.00")}
					    </#if>
					</td>
					<td class="last">${(borrow.lowestAccount)!}<span>元</span></td>
				</tr>
				<tr class="desc">
					<td class="first">预期年化收益</td>
					<td>项目期限</td>
					<td>项目总额</td>
					<td class="last">起投金额</td>
				</tr>
			</table>
            <div class="tips">
                <div class="tips_left">收益方式 &nbsp;
	                <span>
		                <#if borrow.style == 1>分期付息，到期本息
						<#elseif borrow.style == 2>到期付本息
						<#elseif borrow.style == 3>等额本息
						</#if>
	                </span>
                </div>
                <div class="tips_right">发布日期 &nbsp;<span>${(borrow.verifyTime?string("yyyy-MM-dd HH:mm"))!}</span></div>
            </div>
            <div class="rule_wrap">
                <div class="payment_rule">还款规则</div>
                <div class="rule_detail">
			                    到期付本息：投资到期后归还本金和利息 <br>
			                    等额本息：按月归还同等数额的本金和利息<br>
			                    分期付息，到期本息：每期返还相应利息，项目到期后返回当期利息及本金<br>
                </div>
            </div>
        </div>
       <div class="right <#if borrow.schedule =="100"||borrow.status != 1>end<#elseif loginUser><#else>login</#if>"> <!--  end   login-->
		<img src="${base}/img/sellOut_icon.png" alt="">
        <div class="countdown" id="showTime">
        </div>
        <div class="end_status">
            	当前状态
        </div>
        <div class="now">
            <div class="now_left fl">已融资 <span>${borrow.schedule}%</span></div>
            <div class="now_right fr" id="borrow_balance_" data-balance="${borrow.balance?number}">
            	   剩余
		         <#if borrow.balance?number gt 10000>
		            <span>${((borrow.balance?eval)*0.0001)?string("0.00")}万</span>
		         <#else>
		         	<span>${borrow.balance?eval?string("0.00")}元</span>
		        </#if>
            </div>
            <div class="clear"></div>
        </div>
        <div class="progress">
            <div class="value" style="width:${borrow.schedule}%"></div>
        </div>
        <div class="tips_wrap"><div class="tips"></div></div>
        <form id="borrowRaiseInvestForm" action="${base}/borrow/toInvestH.do" method="post" >
        	<input type="hidden" id="bId" name="bId" value="${borrow.id}"/>
            <input type="text" placeholder="起投金额${(borrow.lowestAccount)!}元起" id="tenderAbleMoney" name="tenderAbleMoney"  />
            <input type="hidden" id="tenderContinueMoney" name="tenderContinueMoney" value="${showContinueTotal}" />
            <div class="charge">
		        <#if loginUser>
		        	<div class="fl">可用金额：<span date_ablemoney="${userAbleMoney}"> ${userAbleMoney?string.currency}</span></div>
		        </#if>
                <a target="_blank" href="${base}/payment/rechargeTo.do"><div class="fr">充值</div></a>
                <div class="clear"></div>
            </div>
            <input id="tender" class="subm" type="submit" value="立即投标">
            <a href="javascript:void(0)" class="login_In" onclick="goLogin()">立即登录</a>
            <a href="javascript:void(0);" class="sellEnd"><#if borrow.status==1||borrow.status==5>立即投标<#elseif borrow.status==3>还款中<#else>已结束</#if></a>
        </form>
    </div>
    </div>
    <!--项目信息-->
    <div class="project_msg">
	<div class="title">项目信息</div>
	<div class="debt debt_title">债务人信息</div>
	<div class="debt_msg">
		<span class="debt_msg_name">${(borrow.eachTime)!}</span>
		<div class="debt_title">项目描述</div>
		<div class="debt_msg_describe">${borrow.content}</div>
		<div class="debt_title">资金用途</div>
		<div class="debt_msg_for">${(borrow.useReason)!}</div>
		<div class="debt_title">还款来源</div>
		<div class="debt_msg_payment">${(borrow.borStages)!}</div>
	</div>
<div class="data_public">
            <!-- Add jQuery library -->
            <script type="text/javascript" src="${base}/lib/jquery-1.8.2.min.js"></script>

            <!-- Add mousewheel plugin (this is optional) -->
            <script type="text/javascript" src="${base}/lib/jquery.mousewheel-3.0.6.pack.js"></script>

            <!-- Add fancyBox main JS and CSS files -->
            <script type="text/javascript" src="${base}/source/jquery.fancybox.js?v=2.1.3"></script>
            <link rel="stylesheet" type="text/css" href="${base}/source/jquery.fancybox.css?v=2.1.2" media="screen" />

            <!-- Add Button helper (this is optional) -->
            <link rel="stylesheet" type="text/css" href="${base}/source/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
            <script type="text/javascript" src="${base}/source/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>

            <!-- Add Thumbnail helper (this is optional) -->
            <link rel="stylesheet" type="text/css" href="${base}/source/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
            <script type="text/javascript" src="${base}/source/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>

            <!-- Add Media helper (this is optional) -->
            <script type="text/javascript" src="${base}/source/helpers/jquery.fancybox-media.js?v=1.0.5"></script>
            <div class="title">项目资料公示</div>
            <#if (carImgList?size > 0)>	
	            <#assign ns = (carImgList?size)/3 />
	            <#if (carImgList?size)%3 != 0>
		    		<#assign ns = ns+1/>
		    	</#if>
	            <div class="data">
	                <p>
	                	<#list carImgList as cari>
				            <a class="fancybox" href="<@imageUrlDecode imgurl="${cari.url}"; imgurl>${imgurl}</@imageUrlDecode>" data-fancybox-group="gallery" title="${(cari.name)!}">
		                        <img src="<@imageUrlDecode imgurl="${cari.url}"; imgurl>${imgurl}</@imageUrlDecode>" alt="" />
		                    </a>
					    </#list>
	                </p>
	            </div>
	            
              <#if (ns > 1) >  
	            <div class="data_nav">
                	<i class="left"></i>
	                <#list  1..ns as n>
                		<#if n==1 >
				    		<span class="current"></span>
				    	<#else>
				    		<span></span>
				    	</#if>
	                 </#list>
	                <i class="right"></i>
	          	</div>
              </#if>
	       </#if>
        </div>
    </div>
</div>
    <!--投资记录-->
    <div class="record">
        	<div class="title">投资记录</div>
         	<#if  borrowTenderList! && borrowTenderList?size gt 0 && (borrow.status ==1 ||borrow.status ==3 ||borrow.status ==5 ||borrow.status ==7 )>
	         <div class="bonuses_wrap">
	            <div class="bonuses_border"></div>
	            <div class="bonuses">
	                <div class="bonuses_content">
	                    	<!--<div class="top"><span class="logo">壕</span> <span class="title">投资土豪</span> 用户<i>  ${substringWord(maxUsername,3,"*****")}${maxUsername?substring(8,11)} </i>凭 <i>${maxMoney}</i>元投资额<#if borrow.status==1>暂时</#if>领先，<#if borrow.status==1>可</#if>获得<i>${borrow.tzth}</i>元红包奖励。</div>-->
	                      <#if borrow.status?number == 3 || borrow.status?number ==7>
	                        <div class="top"><span class="logo">壕</span> <span class="title">投资土豪</span> 单笔最大投资额<i>${maxMoney}</i>元，获得了<i>${borrow.tzth}</i>元土豪红包（如有多位土豪，则平分该红包）</div>	 
	                	  	<div class="bottom"><span class="logo">收</span> <span class="title">收官大哥</span> 最后一位投资用户<i>${substringWord(borrowTenderList[(borrowTenderList?size-1)].username,3,"*****")}${borrowTenderList[(borrowTenderList?size-1)].username?substring(8,11)}</i>，获得了<i>${borrow.sgdg}</i>元收官大哥红包</div>
	                	  <#else>
	                	    <div class="top"><span class="logo">壕</span> <span class="title">投资土豪</span> 当前单笔最大投资额<i>${maxMoney}</i>元，该用户有机会获得<i>${thMoneyCurrentMostInvest}</i>元土豪红包，赶紧超越他（如有多位土豪，则平分该红包）</div>
	                    	<div class="bottom"><span class="logo">收</span> <span class="title">收官大哥</span> 最后一位投资用户有机会获得<i>${jbMoneyMost}</i>元收官大哥红包，项目剩余可投金额<i>${borrow.balance}</i>元，赶紧投资</div>
	                	  </#if>
	                </div>
	            </div>
	            <div class="bonuses_border bottom"></div>
	        </div>
	        </#if>
	        <form id="listForm" action="${base}/borrow/detail.do"  method="post" >
	        	<input type="hidden" id="bId" name="bId" value="${borrow.id}"/>
        	<table>
	            <tr class="title" class="first">
	                <td class="first">时间</td>
	                <td class="td2">投资人</td>
	                <td class="last">金额</td>
	            </tr>
	            <#list pager.result as tender>
		            <tr>
		                <td class="first">${tender.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
		                <td class="td2">${substringWord(tender.username,3,"*****")}${tender.username?substring(8,11)}</td>
		                <td class="last">${tender.showAccount?string.currency}</td>
		            </tr>
	            </#list>
	            <#if pager.totalCount==0 || pager.totalCount==null>
		            <tr>
		            	<td></td>
		                <td style="background: white; text-align:center; padding-left: 40px;" >投资记录为空</td>
		                <td></td>
		            </tr>
	            </#if>
        	</table>
	        <!--分页-->
	        <#if pager.totalCount!=0 && pager.totalCount!=null>
					<@pageFlipNew pager=pager>
						<#include "/content/borrow/pager.ftl">
					</@pageFlipNew>	
			</#if>
		 </from>
    </div>
    <!--还款计划-->
    <div class="payment">
        <div class="title">还款计划</div>
        <table>
            <tr>
                <td class="child1">期数</td>
                <td class="child2">还款日期</td>
                <td class="child3">还款总额</td>
                <td class="child4">还款本金</td>
                <td class="child5">还款利息</td>
            </tr>
            <#list repaymentInfo.repaymentDetailList as d>
            <tr>
                <td>${d.orderNum}</td>
                <td>第${d.repaymentDateInt}<#if borrow.isday==0>月<#elseif borrow.isday==1>天</#if></td>
                <td>${d.account?string.currency}</td>
                <td>${d.capital?string.currency}</td>
                <td>${d.interest?string.currency}</td>
            </tr>
            </#list>
            <tr>
                <td colspan="2">小计</td>
                <td>${repaymentInfo.account?string.currency}</td>
                <td>${repaymentInfo.capital?string.currency}</td>
                <td>${repaymentInfo.interest?string.currency}</td>
            </tr>
        </table>
    </div>
    <!--右侧工具栏-->
    <div class="rightNav">
        	<ul>
        		<li class='calc'></li>
        		<li class='wxewm'>
                    <#-- TODO 微信公众号正在申请中 -->
        			<#--<img src="${base}/img/erweima.png" alt="二维码" />-->
        		</li>
        		<li class="navtop"></li>
        	</ul>
        </div>
    <!--login-->
    <div class="GoLogin" style="display: none">
    	<div class="GoLogin_bg"></div>
        <div>
        	<div class="GoLoginDiv_bg"></div>
            <div class="close fr">&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <div class="success_hint tips">没有账号？<a href="${base}/user/reg.do">立即注册</a></div>
            <div class="input_tips">
                <img src="${base}/img/tips.png" alt="">
                <span>手机号格式错误！</span>
            </div>
             <form id="loginForm" method="post"   style="display:block;" >
                <input type="hidden" id="loginRedirectUrl" name="redirectUrl" value = "/borrow/detail.do?bId=${borrow.id}"/>
                <input type="text" placeholder="请输入手机号码" class="phone_num" name="user.username" id="username">
                <input type="password" placeholder="请输入登录密码" class="login_pwd" name="user.password" id="password">
                <input type="text" placeholder="验证码" class="identify_num" name="mycode" id="mycode">
                <a title='点击更换' id='vCodeA' onclick="verifyCodeLink();"><img id="code_img"  style="cursor:pointer;position: relative; border-radius: 10px; left: 13px; top: 15px; height: 46px; width:124px;" src="${base}/rand.do" class="identify_img"/></a>
                <br><a style='display:inline-block;float:right;margin-right:75px;color:#fd7371;margin-top:10px;' href="${base}/user/findPsw.do">忘记密码？</a>
                <input type="button" id = "loginSubmit" value="确认登录" class="last">
	    	</form>
        </div>
    </div>
    <script type="text/javascript">
    	$("#loginSubmit").click(function(){
			var $loginRedirectUrl = $("#loginRedirectUrl");
			var $code_img = $("#code_img");
			var $username = $("#username");
			var $password = $("#password");
			var $mycode=$("#mycode");

			//用户
			if($username.val()==''||typeof($username.val()) == undefined){
				alertMessage("手机号不能为空");
				return;
			}
			if($password.val()==''||typeof($password.val()) == undefined){
				verifyCodeLink();
				$mycode.val('');
				alertMessage("密码不能为空");
				return;
			}
			if($mycode.val()==''||typeof($mycode.val()) == undefined){
				verifyCodeLink();
				$mycode.val('');
				alertMessage("验证码不能为空");
				return;
			}			
			$.ajax({
				url:"${base}/user/checkUsername.do",
				data: {'user.username':$username.val()},
				type: "POST",
				dataType: "json",
				cache: false,
				success: function(data) {
					if(data == true || data == 'true'){
					}else{
						verifyCodeLink();
						$mycode.val('');
						alertMessage("用户不存在");
						return;
					}
				}
			});
			$.ajax({
				url: "${base}/user/ajaxLogin.do",
				data: {'loginRedirectUrl':$loginRedirectUrl.val(),'user.username':$username.val(),'user.password':$password.val(),'mycode':$mycode.val()},
				type: "POST",
				dataType: "json",
				cache: false,
				success: function(data) {
					if(data.status=="success"){
						window.location.href = data.message;
					}else if(data.status=="warn"){
						verifyCodeLink();
						$mycode.val('');
						alertMessage(data.message);
					}else{
						verifyCodeLink();
						$password.val('');
						$mycode.val('');
						alertMessage(data.message);
					}
				}
			});			
		});
		
		function verifyCodeLink(){
			$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		}
		function alertMessage(message){
		    $('.input_tips').find('span').html(message);
		    $('.input_tips').css('visibility','visible');
		}
    </script>
   	<!-- 尾部 -->
	<#include "/content/common/footer.ftl">
   <!-- <script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script> -->
    <script type="text/javascript" src="${base}/js/slider.js"></script>
    <script type="text/javascript" src="${base}/js/carousel.js"></script>
    <script type="text/javascript" src="${base}/js/scroll.js"></script>
    <script type="text/javascript" src="${base}/js/index.js"></script>
    <script src="${base}/js/common.js"></script>
    <script src="${base}/js/product_detail.js"></script>
    <script language=javascript>
	    var time_end_client;
		function detailInit() {
			var ttt = ${countDown};
			if (ttt!=1) {
				$("#showTime").css("display", "none");
				$(".end_status").css("display", "block");
				return;
			}
		
			var time_now_server = new Date('${nowDate?string("MM/dd/yyyy HH:mm:ss")}').getTime();
			var time_now_client=new Date().getTime();//当前时间:浏览器
			var time_check = time_now_server-time_now_client;//服务器和本地的偏差值
			
			var endtime = new Date('${borrow.overDate?string("MM/dd/yyyy HH:mm:ss")}').getTime();
			time_end_client = endtime - time_check;//校准结束时间
			
			showCountDown();
		}
	    function showCountDown() {
			var time_now=new Date().getTime()
			var showtime = time_end_client - time_now;
			if (showtime<=0) {
				$("#showTime").html("招标期已过");
			} else {
				var seconds = showtime/1000;
		        var minutes = Math.floor(seconds/60);
		        var hours = Math.floor(minutes/60);
		        var CDay = Math.floor(hours/24);
		        var CHour= hours % 24;
		        var CMinute= minutes % 60;
		        var CSecond= Math.floor(seconds%60);
	       		if(CDay >= 0 && CDay <= 9){
	       			CDay = '0' + CDay;
	       		}
	       		if(CHour >= 0 && CHour <= 9){
	       			CHour = '0' + CHour;
	       		}
	       		if(CMinute >= 0 && CMinute <= 9){
	       			CMinute = '0' + CMinute;
	       		}
	       		if(CSecond >= 0 && CSecond <= 9){
	       			CSecond = '0' + CSecond;
	       		}
		        var str = "募集倒计时：";
		        if (CDay > 0) {
		         	str +='<span>';
		        	str +=CDay;
		        	str +='</span>天';
		        }
		       	str +='<span>';
		        str +=CHour;
		        str +='</span>小时<span>';
		        str +=CMinute;
		        str +='</span>分<span>';
		        str +=CSecond;
		        str +='</span>秒';
	        	$("#showTime").html(str);
	        	setTimeout("showCountDown()",1000);
	        }
		}
		detailInit();
    </script>
</body>
</html>