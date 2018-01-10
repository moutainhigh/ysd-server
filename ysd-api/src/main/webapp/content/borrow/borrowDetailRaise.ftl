<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-${borrow.name}</title>
	<#include "/content/common/meta.ftl">

	<script type="text/javascript" src="${base}/static/js/common/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="${base}/static/js/common/jquery.fancybox-1.3.4.pack.js"></script>
	<link rel="stylesheet" type="text/css" href="${base}/static/css/jquery.fancybox-1.3.4.css" media="screen" />
	
<script language=javascript> 

function checkTender() {
		var idableAmount =  $.trim($("#idableAmount").val());
		$("#idableAmount").val(idableAmount);
		var idcontinueAmount = $.trim($("#idcontinueAmount").val());
		$("#idcontinueAmount").val(idcontinueAmount);
		
		if (idableAmount=='' && idcontinueAmount=='') {
			alert("请输入投标金额");
			return false;
		}
}

	function submitInverst() {
		$("#btnInvest").hide();
		$("#btnInvesting").show();
<#--		
		var idableAmount =  $.trim($("#idableAmount").val());
		$("#idableAmount").val(idableAmount);
		var idcontinueAmount = $.trim($("#idcontinueAmount").val());
		$("#idcontinueAmount").val(idcontinueAmount);
		
		if (idableAmount=='' && idcontinueAmount=='') {
			alert("请输入投标金额");
			$("#btnInvest").show();
			$("#btnInvesting").hide();
			return false;
		}
		
<#if borrow.type!=4>
		if (idableAmount=='') {
			alert("请输入投标金额");
			$("#btnInvest").show();
			$("#btnInvesting").hide();
			return false;
		}
</#if>
		
		if (idableAmount!='') {
			if (isNaN(idableAmount)) {
				alert("请输入正确投资金额!");
				$("#btnInvest").show();
				$("#btnInvesting").hide();
				return false;
			}
			if (numberZC(idableAmount)==false) {
				alert("投标金额必须是100的整数倍!");
				$("#btnInvest").show();
				$("#btnInvesting").hide();
				return false;
			}
		}
		if (idcontinueAmount!='') {
			if (isNaN(idcontinueAmount)) {
				alert("请输入正确续投金额!");
				$("#btnInvest").show();
				$("#btnInvesting").hide();
				return false;
			}
			if (numberZC(idcontinueAmount)==false) {
				alert("续投金额必须是100的整数倍!");
				$("#btnInvest").show();
				$("#btnInvesting").hide();
				return false;
			}
		}
-->
		<#if borrow.isDxb==1>
		var dxpwd = $("#dxpwd").val();
		if (dxpwd=='') {
			alert("请输入定向密码");
			$("#btnInvest").show();
			$("#btnInvesting").hide();
			return false;
		}
		</#if>
		
		var idsafepwd = $("#idsafepwd").val();
		if (idsafepwd=='') {
			alert("请输入安全密码");
			$("#btnInvest").show();
			$("#btnInvesting").hide();
			return false;
		}
		
		var mycode = $("#mycode").val();
		if(mycode==''){
			alert("请输入验证码");
			$("#btnInvest").show();
			$("#btnInvesting").hide();
			return false;
		}
		
		$.ajax({
	        type: "post",
	        dataType : "json",
	        data: $('#investForm').serialize(),
	        url: 'poputInvestDo.do',
	        success: function(data, textStatus){
	        	if(typeof(data.errorMsg) == "undefined" || typeof(data.errorMsg) == "null" || data.errorMsg == null || data.errorMsg == ""){
	        		alert("投标未成功");
	        		$("#btnInvest").show();
					$("#btnInvesting").hide();
					window.location.reload();
	        	} else if (data.errorMsg=="OK") {
	        		alert("投标成功");
	        		KP.close();
	        		window.location.reload();
	        	} else {
		        	alert(data.errorMsg);
		        	$("#btnInvest").show();
					$("#btnInvesting").hide();
					window.location.reload();
	        	}
	        },
	        error:function(statusText){
	        	alert(statusText);
	        },
	        exception:function(){
	        	alert(statusText);
	        }
		});
	}
	
	var time_end_client;
	function detailInit() {
		
		var ttt = ${countDown};
		if (ttt!=1) {
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
	        
	        var str = "";
	        if (CDay > 0) {
	         	str +='<font style="color:#be9964;">';
	        	str +=CDay;
	        	str +='</font> 天';
	        }
	       	str +='<font style="color:#be9964;">';
	        str +=CHour;
	        str +='</font> 小时 <font style="color:#be9964;">';
	        str +=CMinute;
	        str +='</font> 分 <font style="color:#be9964;">';
	        str +=CSecond;
	        str +='</font>秒';
        	$("#showTime").html(str);
        	
        	setTimeout("showCountDown()",1000);
        }
	
	}
	
	function numberZC(s) {
		var n = parseFloat(s);
		if(n%100==0) {
			return true;
		}
		return false;
	}
	
	function jump_login() {
		alert("请登录！");
		window.location.href='${base}/user/login.do?loginRedirectUrl=%2Fborrow%2Fdetail.do%3FbId%3D${borrow.id}';
	}
	
	function openAgreement(){
		url='${base}/borrow/borrowAgreement.do?borrow.id=${borrow.id}';
		window.open(url);
	}
	detailInit();
	
	$(document).ready(function() {
<#--
		//$("#mytest").imgbox({
		//		'speedIn'		: 0,
		//		'speedOut'		: 0,
		//		'alignment'		: 'center',
		//		'overlayShow'	: true,
		//		'allowMultiple'	: false
		//});
-->		
		$(".imgFancybox").fancybox({
				'type'				: 'image'
			});
			
	});
	
</script>

</head>
<body>
<!-- header -->
<#include "/content/common/header.ftl">
<input type="hidden" id="borrow_bid" value="${borrow.id}"/>

<div class="content">
  <div style="width:970px; margin:0 auto; padding-bottom:40px;">
     <div style="color:#4b4b4b; font-family:'宋体'; padding:22px 0px 12px 0px; border-bottom:1px solid #c6c6c6;"><a href="${base}/">${Application ["qmd.setting.name"]}</a><a>></a>
			<a href="${base}/borrow/list.do">投资中心</a><a>></a>
			<a class="liebiao">借款详情</a>
		</div>
     <div style="width:100%;">
         <div style="width:660px; padding-right:24px;float:left; margin-top:20px;">
            <div style="color:#4b4b4b; font-size:24px; margin-bottom:30px;">
            	<#if borrow.type==0><img src="${base}/static/img/img/miao.png" width="28" height="28" />
				<#elseif borrow.type=15><img src="${base}/static/img/borrow/ya.png" width="28" height="28" />
				<#elseif borrow.type=5><img src="${base}/static/img/borrow/zhuan.png" width="28" height="28" />
				<#elseif borrow.type=14><img src="${base}/static/img/borrow/ya.png" width="28" height="28" />
				</#if>
            &nbsp;&nbsp;${borrow.name}</div>
            <div style="width:630px; background:#fff; padding:15px 0px 0px 30px;border-top:1px solid #be9964; margin-top:13px;">
               <ul style="width:90px; float:left;">
                 <li style="height:22px; line-height:22px; padding-left:0px;color:#939394; font-family:'宋体';font-size:14px;">项目利率</li>
                 <li style="font-size:24px;color:#be9964;"><@numberPercent val="${borrow.varyYearRate}"; npt>${npt}</@numberPercent></li>
               </ul>
               <ul style="width:90px; float:left;">
                 <li style=" height:22px; line-height:22px; padding-left:0px;color:#939394; font-family:'宋体';font-size:14px;">理财期限</li>
                 <li style="font-size:24px;color:#be9964;">${borrow.timeLimit}<#if borrow.isday==0>个月<#else>天</#if></li>
               </ul>
               <ul style="width:215px; float:left;">
                 <li style="height:22px; line-height:22px; padding-left:0px;color:#939394; font-family:'宋体';font-size:14px;">项目金额</li>
                 <li style="font-size:24px;color:#be9964;">￥${borrow.account}元</li>
               </ul>
               
               <ul style="width:215px; float:left;">
                 <li style="height:22px; line-height:22px; padding-left:0px;color:#939394; font-family:'宋体';font-size:14px;">车辆评估价</li>
                 <li style="font-size:24px;color:#be9964;">￥${borrow.accountYes}元</li>
               </ul>
               <div style="clear:both; border-bottom:1px dashed #d4d4d4; width:600px; height:13px;"></div>
               <ul style="width:600px; float:left; padding-bottom:27px; padding-top:8px; color:#939394;font-family:'宋体';">
                 <li style=" float:left; padding:15px 0px 0px 0px; width:190px;">收益方式：
                 	<#if borrow.style == 1>分期付息
					<#elseif borrow.style == 2>到期付本息
					<#elseif borrow.style == 3>等额本金
					</#if>
				</li>
                 <li style="float:left; margin:15px 20px 0 0px;">|</li>
                 <li style=" float:left; padding:15px 0px 0px 0px; width:365px;">借款用户名：${substringWord(user.username, 4, "****")}</li>
                 <li style=" float:left; padding:15px 0px 0px 0px; width:190px;">发布日期：${(borrow.createDate?string("yyyy-MM-dd HH:mm:ss"))!} </li>
                 <li style="float:left; margin:15px 20px 0 0px;">|</li>
                 <li style=" float:left; padding:15px 0px 0px 0px;">安全保障：逾期10天，车商及平台处置质押车辆</li>
               </ul>
               <div style="clear:both;"></div>
             </div>
            </div>
            <div style="width:285px; float:right;margin-top:20px;border-left:1px solid #c6c6c6; ">
               <ul style="padding-left:10px; padding-bottom:5px; font-size:14px; ">
                  <li style="padding:5px 5px; margin-bottom:10px; background:#fff; text-align:center; color:#939394;" >募集倒计时 <div id="showTime"></div>
                  		<#if borrow.status == 2>发标审核拒绝
						<#elseif borrow.status == 3>正在还款中
						<#elseif borrow.status == 4>满标审核失败
						<#elseif borrow.status == 5>此标已满
						<#elseif borrow.status == 6>过期或撤回
						<#elseif borrow.status == 7>已还完
						<#else><#if countDown==-1>招标期已过<#elseif countDown==0>此标已满</#if>
						</#if>
                  </li>
                  <li style="text-align:center; color:#373636;margin-bottom:10px;">已融资 : <font style="color:#be9964; font-weight:bold;">${borrow.schedule}%</font>&nbsp;&nbsp;&nbsp;&nbsp;剩  余 :<font style="font-weight:bold;color:#be9964;">${borrow.balance}元</font></li>
                  <li style=" text-align:center; padding-bottom:12px;color:#5c5e60; font-size:14px;font-family:'宋体';">
                    <span>投标进度：</span>     
                    <span class="pct"><span class="huan" style="display:inline-block;"><font style="font-family:'宋体'; font-size:14px;color:#000;">${borrow.schedule}%</font></span></span>
                  </li>  
                  <li style=" padding-bottom:12px;color:#939394; font-size:12px;font-family:'宋体';text-align:center;"><span style=" font-size:16px; color:#666;">投资金额：￥</span>
                    <input type="text" id="tenderAbleMoney" name="tenderAbleMoney"  value="${showUserAbleMoney}" onfocus="if(this.value == '输入金额为100的整数倍'){this.value = ' '}" onblur="if(this.value == ' '){this.value = '输入金额为100的整数倍'}" style="border-radius:3px;font-size:12px; border:1px solid #c9c8c8; width:200px; height:35px;color:#939394; line-height:35px; padding-left:10px;"/>
                  </li>
                  <#if borrow.type==15>
                   <li style=" padding-bottom:12px;color:#939394; font-size:12px;font-family:'宋体';text-align:center;"><span style=" font-size:16px; color:#666;">续投金额：￥</span>
                    <input type="text"  id="tenderContinueMoney" name="tenderContinueMoney" value="${showContinueTotal}"  onfocus="if(this.value == '输入金额为100的整数倍'){this.value = ' '}" onblur="if(this.value == ' '){this.value = '输入金额为100的整数倍'}" style="border-radius:3px;font-size:12px; border:1px solid #c9c8c8; width:200px; height:35px;color:#939394; line-height:35px; padding-left:10px;"/>
                  </li>
                  <#else>
						<li><input type="hidden" id="tenderContinueMoney" name="tenderContinueMoney" value="${showContinueTotal}" /></li>
				  </#if>
						<li><input type="hidden" id="tenderContinueMoney" name="tenderContinueMoney" value="${showContinueTotal}" /></li>
				  <#if loginUser>
                  	<li style=" padding-bottom:12px;color:#be9964; font-size:12px;font-family:'宋体';font-weight:bold;text-align:center;">可用金额：${loginUser.ableMoney?string.currency}</li>
                   </#if>
                   <#if showButtonNameFlg==1>
	                    <li style="text-align:center;">
							<input type="button" id="tender" style="color:#fff; display:inline-block;background:url(/static/img/b4.png) 0 0 no-repeat; width:178px; height:39px; line-height:39px;  font-size:16px;font-family:'宋体';font-weight:bold;" value="${showButtonName}"/>			
						</li>
				  <#else>

          		
 	</#if>                  
               </ul>
            </div>
            <div style="clear:both;"></div>
         </div>
    <ul style="width:100%; border-bottom:1px solid #be9964; height:42px; margin-top:20px; clear:both;" class="ullist">
          <li class="change">项目信息</li>
          <li>质押车辆信息</li>
          <li>募集记录</li>
          <li>还款记录</li>
        </ul>
        <div class="kuang" style="width:100%;">
          <div class="kuang_a1 kuang_a2">
             <div style="padding-top:20px; margin-top:2px;">
               <div style="color:#000; font-size:14px;font-family:'宋体'; font-weight:bold; padding-bottom:7px;">项目详细信息</div>
               <p style="color:#6f6e6e;font-family:'宋体'; text-indent:28px; line-height:18px;">
				${borrow.content}
				</p>
             </div>
              <div style="margin-top:32px; margin-bottom:32px;">
                <div style="color:#000; font-size:14px;font-weight:bold; padding-bottom:20px;">资料审核</div>
                <#if verifyMessJson>
                 <ul style="width:100%; float:left; font-size:14px;">
                 <#if verifyMessJson.idCard==1>
                   <li style="float:left; text-align:center; margin-right:24px;">
                     <div style="margin-bottom:0px; height:30px;"><a><img src="/static/img/s1.png" /></a></div>
                     <div><a style="color:#939394;">身份证</a></div>
                   </li>
                  </#if>
                   <#if verifyMessJson.household==1>
                   <li style="float:left; text-align:center; margin-right:24px;">
                     <div style="margin-bottom:0px; height:30px;"><a><img src="/static/img/s2.png" /></a></div>
                     <div><a style="color:#939394;">户口本</a></div>
                   </li>
                    </#if>
                    <#if verifyMessJson.zhengXin==1>
                   <li style="float:left; text-align:center; margin-right:24px;">
                     <div style="margin-bottom:0px; height:30px;"><a><img src="/static/img/s3.png" /></a></div>
                     <div><a style="color:#939394;">征信报告</a></div>
                   </li>
                    </#if>
                    <#if verifyMessJson.income==1>
                   <li style="float:left; text-align:center; margin-right:24px;">
                     <div style="margin-bottom:0px; height:30px;"><a><img src="/static/img/s4.png" /></a></div>
                     <div><a style="color:#939394;">收入证明</a></div>
                   </li>
                    </#if>
                    <#if verifyMessJson.anCase==1>
                   <li style="float:left; text-align:center; margin-right:24px;">
                     <div style="margin-bottom:0px; height:30px;"><a><img src="/static/img/s5.png" /></a></div>
                     <div><a style="color:#939394;">在执行案件查询</a></div>
                   </li>
                    </#if>
                    <#if verifyMessJson.cardDriving==1>
                   <li style="float:left; text-align:center; margin-right:24px;">
                     <div style="margin-bottom:0px; height:30px;"><a><img src="/static/img/s6.png" /></a></div>
                     <div><a style="color:#939394;">车辆行驶证</a></div>
                   </li>
                    </#if>
                    <#if verifyMessJson.gouZhi==1>
                   <li style="float:left; text-align:center; margin-right:24px;">
                     <div style="margin-bottom:0px; height:30px;"><a><img src="/static/img/s7.png" /></a></div>
                     <div><a style="color:#939394;">购置税凭证</a></div>
                   </li>
                    </#if>
                    <#if verifyMessJson.guJia==1>
                   <li style="float:left; text-align:center; margin-right:24px;">
                     <div style="margin-bottom:0px; height:30px;"><a><img src="/static/img/s8.png" /></a></div>
                     <div><a style="color:#939394;">车辆评估报告</a></div>
                   </li>
                    </#if>
                    <#if verifyMessJson.gcfp==1>
                   <li style="float:left; text-align:center; margin-right:24px;">
                     <div style="margin-bottom:0px; height:30px;"><a><img src="/static/img/s9.png" /></a></div>
                     <div><a style="color:#939394;">购车发票</a></div>
                   </li>
                    </#if>
                    <#if verifyMessJson.jdczs==1>
                   <li style="float:left; text-align:center; margin-right:24px;">
                     <div style="margin-bottom:0px; height:30px;"><a><img src="/static/img/s10.png" /></a></div>
                     <div><a style="color:#939394;">机动车登记证书</a></div>
                   </li>
                    </#if>
                 </ul>
                </#if>
                 <div style="clear:both;"></div>
             </div>
             <div style="margin-top:32px; clear:both;">
                <div style="color:#000; font-size:14px;font-family:'宋体'; font-weight:bold; padding-bottom:10px;">借款凭证</div>
        
         <#if voucherImgList!>
        <ul class="detail_n3"style="margin-bottom:20px; float:left;">
        
        <#list voucherImgList as image>
        	 <li <#if image_index==0> class="detail_n4" </#if> >${image.name}
        	 <#if (image_index +1) != voucherImgList?size>
        	 	<span>|</span>
        	 </#if>
        	 </li>
        </#list>
        	<#--
           <li class="detail_n4">借款合同<span>|</span></li>
           <li>担保函<span>|</span></li>
           <li>担保合同<span>|</span></li>
           <li>营业执照</li>-->
        </ul>
        <script>
          $('.detail_n3 li').click(function(){
             var i=$(this).index();
             $('.detail_n3 li').removeClass('detail_n4');
             $(this).addClass('detail_n4');
             $('.detail_n5').css('display','none');
             $('.detail_n5').eq(i).css('display','block');
              })
         </script>
           <div style="width:970px; border-bottom:1px solid #e6e6e6; margin-bottom:0px;">
           <#list voucherImgList as image>
               <div class="detail_n5" <#if image_index ==0> style="display:block; position:relative;"<#else>style="position:relative;"</#if> >
               <div class="kaqu_jigoufuwushang">
                 <div class="kaqu_aul_${image_index}">
                  <ul>
                   <#list image.imgUrlList as url>
                     <li class="kaqu_alist">
                     	<a class="imgFancybox" title="" href="<@imageUrlDecode imgurl="${url.url}"; imgurl>${imgurl}</@imageUrlDecode>"><img  width="200" height="220" src="<@imageUrlDecode imgurl="${url.url}"; imgurl>${imgurl}</@imageUrlDecode>"></a>
                     </li>
                    <#if (url_index+1) % 6 == 0>
		             		</ul>
		             		<ul>
		             </#if>	 
                 </#list>
                  </ul>
                 </div>                  
               </div> <!--kaqu_jigoufuwushang end -->
                  <a id="btns_click${image_index*2}" class="detail_n6">&lt;</a>
                  <a id="btns_click${image_index*2+1}" class="detail_n7">&gt;</a>
				<script type="text/javascript">
                    jQuery(function(){
                    var OneWidth = jQuery(".kaqu_aul_${image_index}>ul").width();
                    var TotalPages = jQuery(".kaqu_aul_${image_index}>ul").length;
                    var TotalWidth = OneWidth*TotalPages;
                    var i = 0;
                	jQuery(".kaqu_aul_${image_index}").width(TotalWidth);
                    jQuery("#btns_click${image_index*2+1}").click(function(){
                    i++;
                    if(i==TotalPages){
                    i=0;
                    }
               		 jQuery(".kaqu_aul_${image_index}").animate({left:-i*OneWidth});
                    })
                    jQuery("#btns_click${image_index*2}").click(function(){
                    i--;
                    if(i<0){
                    i=TotalPages-1;
           			 }
               		 jQuery(".kaqu_aul_${image_index}").animate({left:-i*OneWidth});
                    })
                    })
                </script>
               </div>
             </#list>
         </div>
     </div><!--detail_k4 end -->
     <div class="detail_k4">
     <#else>
     	<div class="detail_k4" style="display:block;">
     </#if>
            <div style="width:100%; background:#fff; padding:13px 0;">
                 <div style="float:left; margin:0 20px;">
                   <A href=""><img src="/static/img/s12.png" /></A>
                 </div>
                 <div style="float:left; margin-top:12px;">
                   <div style="color:#535353; font-size:20px;">合法合规审查</div>
                   <div style="color:#b2b2b2; font-size:15px; line-height:24px;">律师事务所专业律师对基础债权的形成过程全程进行审核和见证，并基础债权的真实性、合法性<br />债权形成的法律文本</div>
                 </div>
                 <div style="clear:both;"></div>
             </div>
          </div>
          </div>
          
          <div class="kuang_a1">
              <div style="color:#000; font-size:14px;font-family:'宋体'; font-weight:bold; padding-bottom:10px; padding:40px 0 20px 0; position:relative;">
              质押车辆信息<a href="<#if carInfo.modelsLink>${(carInfo.modelsLink)!}<#else>#</#if> " target="_Blank" style="position:absolute; right:0px; top:15px; color:#be9964;">了解车型详情>></a>
              </div> 
                <table width="100%" cellpadding="0" cellspacing="0" style=" font-size:14px;color:#939394; ">
                  <tbody align="left">
                    <tr height="40">
                       <td width="75">车辆品牌：</td>
                       <td width="210">${(carInfo.carBrand)!}</td>
                       <td width="75">车 系：</td>
                       <td width="260">${(carInfo.carSeries)!}</td>
                       <td width="85">车 型：</td>
                       <td>${(carInfo.carSpec)!}</td>
                    </tr>
                    <tr height="40">
                       <td>上牌时间：</td>
                       <td>${(carInfo.onCardTime)!}</td>
                       <td>行驶里程：</td>
                       <td>${(carInfo.roadHaul)!}</td>
                       <td>车辆识别号：</td>
                       <td>${(carInfo.vin)!}</td>
                    </tr>
                    <tr height="40">
                       <td>发动机号：</td>
                       <td>${(carInfo.engineNumber)!}</td>
                       <td>发证时间：</td>
                       <td>${(carInfo.certificationTime)!}</td>
                       <td>用 途：</td>
                       <td>${(carInfo.use)!}</td>
                    </tr>
                  </tbody>
                </table>
              <div style="color:#000; font-size:14px;font-family:'宋体'; font-weight:bold; padding-bottom:10px; padding:20px 0;">车辆照片</div>  
	              <#if (carImgList?size > 0)>	
		                <#list carImgList as cari>
			                       
				              <img src="<@imageUrlDecode imgurl="${cari.url}"; imgurl>${imgurl}</@imageUrlDecode>" width="140" height="140" />
			            </#list>
	               </#if> 
          </div>
          
          <div class="kuang_a1">
            <div  style="padding-top:40px; margin-top:2px;">
                <table width="100%" cellpadding="0" cellspacing="0" style="background:#fff; border:1px solid #c6c6c6; border-right:none;border-bottom:none; color:#646464;font-family:'宋体'; ">
                  <thead align="center">
                     <tr height="40">
                       <td style="color:#000; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">投资人</td>
                       <td style="color:#000; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">年化利率</td>
                       <td style="color:#000; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">投资金额</td>
                       <td style="color:#000; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">有效金额</td>
                       <td style="color:#000; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">投标时间</td>
                       <td style="color:#000; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">状态</td>
                     </tr>
                  </thead>
                  <tbody align="center">
                  <#list borrowTenderList as tender>
	                     <tr height="40">
	                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${substringWord(tender.username, 2, "****")}</td>
	                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; "><@numberPercent val="${borrow.varyYearRate}"; npt>${npt}</@numberPercent></td>
	                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${tender.showMoney?string.currency}</td>
	                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${tender.showAccount?string.currency}</td>
	                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${tender.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
	                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; "><#if tender.status == "1">全部通过<#else>部分通过</#if>(<#if tender.autoTenderStatus == 1>自动<#else>手动</#if>)</td>
	                     </tr>
                   </#list> 
                  </tbody>
                </table>
            </div>
          </div>
           <div class="kuang_a1">
            <div  style="padding-top:40px; margin-top:2px;">
  			<#if borrow.type==14||borrow.type==15>
                <table width="100%" cellpadding="0" cellspacing="0" style="background:#fff; border:1px solid #c6c6c6; border-right:none;border-bottom:none; color:#646464;font-family:'宋体'; ">
                  <thead align="center">
                     <tr height="40">
                        <th style="color:#000; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">期数</th>
                        <th style="color:#000; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">还款日期</th>
                        <th style="color:#000; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">还款总额</th>
                        <th style="color:#000; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">还款本金</th>
                        <th style="color:#000; font-weight:bold;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">还款利息</th>
                     </tr>
                  </thead>
                  <tbody align="center">
          <#list repaymentInfo.repaymentDetailList as d>
                    <tr height="40">
                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${d.orderNum}</td>
                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">第${d.repaymentDateInt}<#if borrow.isday==0>月<#elseif borrow.isday==1>天</#if></td>
                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${d.account?string.currency}</td>
                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${d.capital?string.currency}</td>
                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${d.interest?string.currency}</td>
                    </tr>
                  </#list>
                    <tr height="40">
                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; " colspan="2">小计</td>
                       <td style="color:#ff830a;border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6;">${repaymentInfo.account?string.currency}</td>
                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${repaymentInfo.capital?string.currency}</td>
                       <td style="border-bottom:1px solid #c6c6c6;border-right:1px solid #c6c6c6; ">${repaymentInfo.interest?string.currency}</td>
                    </tr>
	 		</tbody>
       	</table>
       </#if>
            </div>
          </div>
          <script>
          jQuery(function(){
             jQuery('.ullist li').click(function(){
				 var i=jQuery(this).index();
				 jQuery('.ullist li').removeClass('change');
				 jQuery(this).addClass('change');
				 jQuery('.kuang .kuang_a1').css('display','none')
				 jQuery('.kuang .kuang_a1').eq(i).css('display','block')
				 });
          })
		  </script>
        </div><!--kuang end-->
     </div>
     
     
</div><!-- content end -->

<!-- footer -->
<#include "/content/common/footer.ftl">

<script>
jQuery(function(){
	jQuery(".zong li").click(function(){
		var i=jQuery(this).index()
		jQuery(".zong li").removeClass("xiangqing");
		jQuery(this).addClass("xiangqing");
		jQuery(".f1 .f2").css('display','none')
		jQuery(".f1 .f2").eq(i).css('display','block')
		});
		
	});
jQuery(function(){
	jQuery(".n1 li").click(function(){
		var i=jQuery(this).index()
		jQuery(".n1 li").removeClass("n2");
		jQuery(this).addClass("n2");
		jQuery(".k1 .k2").css('display','none')
		jQuery(".k1 .k2").eq(i).css('display','block')
		});
		
	});
	
$(".pct").each(function(){
			var p=parseInt($(this).text());
			$(this).children(".huan").css("background-position", -Math.floor(p*0.2)*64 + "px 0");
		});
	
$("#top_borrows").addClass("nav_list");
</script>

</body>
</html>
