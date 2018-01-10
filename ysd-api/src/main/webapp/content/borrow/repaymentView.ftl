<!DOCTYPE html>
<html>
 <head>
	<title>${Application ["qmd.setting.name"]}-我的账户-还款明细</title>
	<#include "/content/common/meta.ftl">
	
<script type="text/javascript">
function borrowerPayBack(rid) {

	$("#payback_"+rid).hide();
	$("#payback_"+rid).after('<span id=\"loading_'+rid+'\"><img src="${base}/static/img/loadingmini.gif" /></span>');
	
	if (!confirm("确定要还款吗？")) {
		
		$("#loading_"+rid).remove();
		$("#payback_"+rid).show();
		return false;
	}
	
	$("#repayId").val(rid);
	
	$.ajax({
        type: "post",
        dataType : "json",
        data: $('#repayForm').serialize(),
        url: 'ajaxPayBack.do',
        success: function(data, textStatus){
        	if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
        		alert("还款失败！");
        		$("#loading_"+rid).remove();
				$("#payback_"+rid).show();
        	} else if (data.status=="success") {
        		alert("还款成功");
        		window.location.reload();
        	} else {
	        	alert(data.message);
	        	$("#loading_"+rid).remove();
				$("#payback_"+rid).show();
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
</script>
	
</head>
<body>
<!-- header -->

<#include "/content/common/header.ftl">

<div class="content">
  <div style="width:955px; margin:0 auto; padding-bottom:20px;">
	<#include "/content/common/user_center_header.ftl">
 <div style="width:955px; float:left; background:#fff; clear:both;">
	<#include "/content/common/user_center_left.ftl">
	
	<#-- 		右边内容块 开始 		-->
<div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
            <a href="${base}/userCenter/index.do" style="color:#646464;font-family:'宋体';">我的账户</a>
            <a style="color:#646464;font-family:'宋体'; padding:0px 4px;">></a>
            <a href="${base}/borrow/hkmx.do" style="color:#646464;font-family:'宋体';">还款管理</a>
             <a style="color:#646464;font-family:'宋体'; padding:0px 4px;">></a>
             <a style="color:#646464;font-family:'宋体';">还款明细</a>
            
          </div>
          <div style=" width:661px;background:#d4d4d4;  margin:15px  0; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            <a href="${base}/borrow/hkmx.do" class="hr">还款管理</a>
            <#if borrStatus ==3>
	            <a href="javascript:void(0);" class="hr hre">未还款</a>     
	            <a href="${base}/borrow/userBorrowDone.do" class="hr ">还款完成</a>     
            <#elseif borrStatus ==7>
	            <a href="${base}/borrow/userBorrowNoDone.do" class="hr ">未还款</a>     
	            <a href="javascript:void(0);" class="hr hre">还款完成</a> 
            </#if>
 	 </div>
 	 
 	 <div style="width:670px;color:#646464; clear: both;font-family:'宋体';font-size:14px;font-weight:700; padding-bottom:10px;border-bottom:1px solid #c6c6c6; position:relative;">
            <span>借款详情</span>
          </div>
          <ul style="padding-bottom:30px;">
             <li style=" padding-top:14px;">
               <span style="color:#727171;font-family:'宋体';display:inline-block; width:215px; ">${borrow.name}</span>
              
             </li>
             <li style=" padding-top:14px;">
               <span style="color:#727171;font-family:'宋体';display:inline-block; width:215px; ">借款金额：<font style="color:#727171;font-family:'宋体';">￥${borrow.account}</font></span>
               <span>
                 <a href="" style="color:#727171;font-family:'宋体';">借款期限：${borrow.timeLimit}<#if borrow.isday==0>个月<#elseif borrow.isday==1>天</#if></a>
                 <a style=" padding:0px 5px;color:#727171;font-family:'宋体';">|</a>
            	 <a href="" style="color:#727171;font-family:'宋体';">发布时间：${borrow.verifyTime?string("yyyy-MM-dd HH:mm:ss")}</a>
               </span>
             </li>
             <li style=" padding-top:14px;">
               <span style="color:#727171;font-family:'宋体';display:inline-block; width:215px; ">借款利率：<font style="color:#727171;font-family:'宋体';"><@numberPercent val="${borrow.varyYearRate}"; npt>${npt}</@numberPercent></font></span>
               <span>
                 <a href="" style="color:#727171;font-family:'宋体';">还款方式：<#if borrow.style==1>分期付息<#elseif borrow.style==2>到期付本息<#elseif borrow.style==3>等额本金</#if></a>

               </span>
             </li>
             <li style=" padding-top:14px;">
               <span style="color:#727171;font-family:'宋体';display:inline-block; width:215px; ">借款时间：<font style="color:#727171;font-family:'宋体';">${borrow.successTime?string("yyyy-MM-dd HH:mm:ss")}</font></span>
               
             </li>
          </ul>
 	 
 	 
		 <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
				<thead bgcolor="#efebdf" align="center">
					<tr height="35">
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;" >序号</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;" >计划还款日</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;" >计划还款本息</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;" >实还日期</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;" >实还本息</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;" >逾期罚息</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;" >逾期天数</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;" >状态</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;" >操作</th>
						</tr>
						</thead>
						<tbody align="center">
							<#list borrowRepDetailList as borrowRepaymentDetail>
							 <tr height="50">
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(borrowRepaymentDetail.orderNum) }</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${borrowRepaymentDetail.repaymentTime?string("yyyy-MM-dd")}</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">￥${borrowRepaymentDetail.showRepayTotal} </td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									<#if !borrowRepaymentDetail.repaymentYestime>--
									<#else>${borrowRepaymentDetail.repaymentYestime?string("yyyy-MM-dd HH:mm:ss")}
									</#if>
								</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">￥
									<#if !borrowRepaymentDetail.repaymentYesaccount>--
									<#else>${borrowRepaymentDetail.repaymentYesaccount}
									</#if></td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">￥<#if !borrowRepaymentDetail.lateInterest>0<#else>${borrowRepaymentDetail.lateInterest}</#if></td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${borrowRepaymentDetail.lateDays}天</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									<#if (borrowRepaymentDetail.status==1)>已还
									<#else>待还款
									</#if>
								</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									<#if (borrowRepaymentDetail.status==1)>
									<#else>
										<a href="javascript:void(0)"  id="payback_${borrowRepaymentDetail.id}" onclick="borrowerPayBack(${borrowRepaymentDetail.id})" class="Repay" >还款</a>
											
									</#if>
								</td>
							</tr>
							</#list>
							<tr height="50">
								<td colspan="9" style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"align="left">
									注：带有“（预计）”标记的金额说明该金额并非实际还款金额，它只是假设以当前时间为还款时间的情况下用户将要还多少金额。
								</td>
							</tr>
						</tbody>
					</table>
					
					<form id="repayForm" >
						<input type="hidden" id="repayId" name="borrowRepaymentDetail.id" value="0"/>
						<input type="hidden" name="flag" value="2"/>
					</form>

<#-- 		右边内容块 结束				-->
	
	</div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->


 <!--footer-->

<#include "/content/common/footer.ftl">


</body>

<script type="text/javascript">
$(function(){
	$("#borrower_repayment").addClass("nsg nsg_a1");
});
</script>
</html>
