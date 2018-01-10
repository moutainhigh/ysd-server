<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-我的账户</title>
	<#include "/content/common/meta.ftl">
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">


<#include "/content/common/user_center_header.ftl">

<div class="content">
  <div style="width:1200px; margin:0 auto; padding-bottom:60px; margin-top:20px;">
    <!--left-->
	<#include "/content/common/user_center_left.ftl">
    <!--right-->
    <div style="width:942px; float:right;">
      <div style="border:1px solid #e6e6e6; background:#fff; padding-bottom:60px;">
         <div style=" width:940px; height:63px; border-bottom:1px solid #e6e6e6;">
           <div style="width:470px; height:63px; float:left; background:url(img/a13.png) 40px center no-repeat;">
             <span style="float:left; font-size:16px; margin:20px 0 0 54px; color:#666;">账户总额111：<font style="color:#fd7c1a;">${(selfaccountHeader.total?string("0.00"))!}</font> 元</span>
             <span style="float:right;margin-right:10px; margin-top:20px;">
				<#if noPayPsw == 1>
				<label class="alrm"><a href="${base}/member/toPayPassword.do" style="color:#000;">设置交易密码，保护账户安全</a></label>
				<#elseif realStatus != 1>
				<label class="alrm"><a href="${base}/userCenter/toBank.do" style="color:#000;">进行“绑卡认证”，保护账户安全</a></label>
				<#else>
				<a href="${base}/payment/rechargeTo.do" style="display:inline-block; width:55px; height:26px;  line-height:26px; text-align:center; border-radius:3px; color:#fff; font-size:14px; background:#fd7c1a; margin-right:16px;">充值</a>
               	<a id="getMoney" data-href="${base}/userCenter/getMoney.do" style="display:inline-block; width:55px; height:26px;  line-height:26px; text-align:center; border-radius:3px; color:#fff; font-size:14px; background:#7c96e8; ">提现</a>
				</#if>            
             </span>
           </div>
           <div style="width:1px; height:40px; margin-top:11px; border-left:1px solid #e6e6e6; float:left;"></div>
           <div style="width:468px; height:63px; float:left; background:url(img/a14.png) 40px center no-repeat;">
             <span style="float:left; font-size:16px; margin:20px 0 0 54px; color:#666;">可用余额：<font style="color:#fd7c1a;">${(selfaccountHeader.ableMoney?string("0.00"))!}</font> 元</span>
             <span style="float:right;margin-right:80px; margin-top:20px;">
               <a href="${base}/account/detail.do?type=detail" style="display:inline-block; width:55px; height:26px;  line-height:26px; text-align:center; border-radius:3px; color:#fff; font-size:14px; background:#fd7c1a;">详情</a>
             </span>
           </div>
         </div>
         <div style="width:900px; height:108px; margin:28px auto;">
            <ul style="width:290px;border:1px solid #e6e6e6;border-radius:5px; height:106px; text-align:center; margin-right:12px; float:left;"> 
              <li style="margin:24px 0 6px 0; font-size:14px; color:#666;">投资中本金（元）</li>
              <li style="color:#fd7c1a; font-size:24px;">${((selfaccountHeader.investorCollectionCapital!0)?string("0.00"))!}</li>
            </ul>
            <ul style="width:290px;border:1px solid #e6e6e6;border-radius:5px; height:106px; text-align:center; margin-right:12px; float:left;"> 
              <li style="margin:24px 0 6px 0; font-size:14px; color:#666;">待到账收益（元）</li>
              <li style="color:#7c96e8; font-size:24px;">${((selfaccountHeader.investorCollectionInterest!0)?string("0.00"))!}</li>
            </ul>
            <ul style="width:290px;border:1px solid #e6e6e6;border-radius:5px; height:106px; text-align:center; margin-right:0; float:left;"> 
              <li style="margin:24px 0 6px 0; font-size:14px; color:#666;">累计收益（元）</li>
              <li style="color:#7c96e8; font-size:24px;">
              	<@userAccountDetailSum_by_type_count userid="${loginUser.id}" types="interest_repayment,interest_repayment_continued" countType="add"; sum>
							${sum?string("0.00")}
				</@userAccountDetailSum_by_type_count>
              </li>
            </ul>
         </div>
         <ul style="width:940px; height:50px; background:#eee;" class="user_list_qh">
           <li data="0" <#if type==0>class="tzlist_user"</#if>><a href="${base}/userCenter/borrowDetailList.do?type=0" style="color:#666;">待收回款</a></li>
           <li data="1" <#if type==1>class="tzlist_user"</#if>><a href="${base}/userCenter/borrowDetailList.do?type=1" style="color:#666;">已收回款</a></li>
           <li data="2" <#if type==2>class="tzlist_user"</#if>><a href="${base}/userCenter/borrowDetailList.do?type=2" style="color:#666;">投资记录</a></li>
         </ul>
        <form id="listForm" method="post" action="borrowDetailList.do" >
      	<input type="hidden" name="type" id="type" value="${type}"/>
         <div style="width:100%; clear:both; padding-top:20px;">
           <div style="display:block; width:900px; margin:0 auto;" class="user_div_qh">
             <table width="100%" cellpadding="0" cellspacing="0" border="0">
               <thead bgcolor="#fd7c1a">
               <#if type==2>
               	<tr height="38">
                   <th style="color:#fff; font-size:14px;">投资时间</th>
                   <th style="color:#fff; font-size:14px;">项目名称</th>
                   <th style="color:#fff; font-size:14px;">状态</th>
                   <th style="color:#fff; font-size:14px;">投资本金</th>
                   <th style="color:#fff; font-size:14px;">到期收益</th>
                   <th style="color:#fff; font-size:14px;">操作</th>
                 </tr>
               <#else>
                 <tr height="38">
                   <th style="color:#fff; font-size:14px;">回款时间99</th>
                   <th style="color:#fff; font-size:14px;">项目名称</th>
                   <th style="color:#fff; font-size:14px;">期数</th>
                   <th style="color:#fff; font-size:14px;">回款本金</th>
                   <th style="color:#fff; font-size:14px;">到期收益</th>
                   <th style="color:#fff; font-size:14px;">状态</th>
                 </tr>
                </#if>
               </thead>
               <tbody align="center">
                <#list pager.result as value>
                 <#if type==2>
                 	<tr height="72">
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">${value.repaymentDate?string("yyyy-MM-dd HH:mm:ss")}<#if value.clientType !=0><img src="/img/a17.png" width="12" height="18"  style="position:relative; left:3px; top:-2px;"/></#if></td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">${substring(value.title, 16, "")}</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">
							<#if value.status = 3>还款中
							<#elseif value.status=0>发布审批中
							<#elseif value.status=1>正在招标中
							<#elseif value.status=2>发标审核拒绝
							<#elseif value.status=4>满标审核失败
							<#elseif value.status=5>等待满标审核
							<#elseif value.status=6>过期或撤回
							<#elseif value.status=7>已还完
							<#else>${value.status}
							</#if>
					</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">${value.loanAmountFee?string.currency}</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#fd7c1a; font-size:14px;">${(value.interest)!}</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#4c95ce; font-size:14px;"><a href="${base}/borrow/tzborrowDetail.do?id=${value.id}">详情</a></td>
                  </tr>
                <#else>
                  <tr height="72">
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">${value.repaymentTime?string("yyyy-MM-dd")}</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">${substring(value.borrowName, 16, "...")}</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">${(value.borrowPeriods+1)}/<#if value.borrowtype==2>1<#elseif value.borrowtype==5>${value.borrowDivides}<#else>${value.divides}</#if></td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">${value.waitAccount}</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#fd7c1a; font-size:14px;">${value.waitInterest}</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#4c95ce; font-size:14px;">
	                    <#if value.status==0>未收
						<#elseif value.status==1>已收
					     <#else>${value.status}
						</#if>
					</td>
                  </tr>
                 </#if>
                </#list>
                
               </tbody>
             </table>
             <@pagination pager=pager baseUrl="/borrowDetailList.do" parameterMap = parameterMap>
					<#include "/content/common/pager.ftl">
				</@pagination>  
           </div>
         </div>
      </div>
    </div>
    </form>
    <div style="width:100%;clear:both; height:1px;"></div>
  </div>
</div><!-- content end -->     

<#include "/content/common/footer.ftl">
<script>
	$("#header_wdzh").addClass("hq");<#-- header.ftl 选中样式 -->
	<#-- $("#cztx").addClass("click_current"); user_center_left.ftl 选中样式 -->

	$("#getMoney").click(function(){
		var href=$(this).attr("data-href");
		$.ajax({
			url: "${base}/user/ajaxCheckBindBank.do",
			type: "POST",
			dataType: "json",
			cache: false,
			success: function(data) {
				if(data.status=="success"){
					window.location.href = href;
				}else{
					alertMessageBank(data.message);
				}
			}
		});
	});
	
	function alertMessageBank(message){
		KP.options.drag = true;
		KP.show("提示", 330, 180);
		var show_content = '<div style=" padding:5px 10px 0;"><div style="color:#666; font-size:14px; padding:20px 0; margin-bottom: 5px;border-bottom:1px solid #e6e6e6;">'+ message +'</div><div style="text-align:center;"><a  href="${base}/userCenter/toBank.do" style="display:inline-block; width:95px; height:30px;  line-height:30px; text-align:center; border-radius:3px; color:#fff; font-size:14px; background:#fd7c1a; margin-top:20px;">确定</a></div></div>';
		$(KP.options.content).html(show_content);
	}
</script>  
</body>
</html>
