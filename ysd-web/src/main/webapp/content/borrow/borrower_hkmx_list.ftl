<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-还款管理-我要还款</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
	
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
        	//alert(statusText);
        	alert("请重新登录！");
        	window.location.href='${base}/user/login.do?loginRedirectUrl=%2Fborrow%2Fhkmx.do';
        },
        exception:function(){
        	alert(statusText);
        }
	});
	
//	$.dialog({type: "warn", content: "确认要还款吗？", ok: "确 定", cancel: "取 消", modal: true, okCallback:function(){
//		$("#repayId").val(rid);
//		$("#repayForm").submit();
//	}});
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
	
	<#-- 		右边内容块 开始 				-->
	<div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
            <a href="${base}/userCenter/index.do" style="color:#646464;font-family:'宋体';">我的账户</a>
            <a style="color:#646464;font-family:'宋体'; padding:0px 4px;">></a>
            <a href="" style="color:#646464;font-family:'宋体';">还款管理</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            <a href="javascript:void(0);" class="hr hre">还款管理</a>
            <a href="${base}/borrow/userBorrowNoDone.do" class="hr ">未还款</a>     
            <a href="${base}/borrow/userBorrowDone.do" class="hr ">还款完成</a>     
          </div>
		<form id="listForm" method="post" action="hkmx.do">
         		 <div style="font-family:'宋体'; color:#000; margin-top:80px; clear:both;">
					关键字： <input type="text" name="keywords" value="${keywords}" style="width:130px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;" />
					应还日期：<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${(minDate)}" style="width:130px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"> 到 
					<input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${(maxDate)}" style="width:130px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;">  
					<input type="button" onclick="gotoPage(1);" style="display:inline-block; width:65px; height:35px; line-height:35px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" value = "搜索"/>			
				</div>
            <div style=" margin-top:10px;">
				 <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
					<thead bgcolor="#efebdf" align="center">
						<tr height="35">
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">标题</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">第几期</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">应还日期</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">借款本金</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">保证金</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">应还利息</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">实际需还金额</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">操作</th>							                                                    
						</tr>
					</thead>
					
					<tbody align="center">
					<#list pager.result as borrowTemp>
							<tr height="50">
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><a href="${base}/borrow/detail.do?bId=${borrowTemp.borrowId}" target="_blank" title="${borrowTemp.title}">${substring(borrowTemp.title, 16, "")}</a></td>                                            
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(borrowTemp.orderNum+1)}/${borrowTemp.divides}</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
							   		<#if borrowTemp.repaymentDate>
							   			${borrowTemp.repaymentDate?string("yyyy-MM-dd")}
							   		</#if>
							   	</td>
							   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${borrowTemp.capitalAccount?string.currency}</td>
							   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(borrowTemp.showDepositMoney)?string.currency}</td>
							   	<#-->
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(borrowTemp.capitalAccount+borrowTemp.interestAccount)?string.currency}</td>
								-->
							   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${borrowTemp.interestAccount?string.currency}</td>
							   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(borrowTemp.showRepaymentAccount)?string.currency}</td>	
									<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><a href="javascript:void(0)" onclick="return borrowerPayBack(${borrowTemp.id});"  id="payback_${borrowTemp.id}"  class="Repay">还款</a></td>
			<!--<td><a href="${base}/borrow/borrowRepayDetail.do?borrowRepaymentDetail.id=${borrowTemp.id}&flag=1" onclick="return borrowerPayBack(${borrowTemp.id});">还款</a></td>-->
								</tr>
					</#list>
					</tbody>
				</table>
				<@pagination pager=pager baseUrl="/borrow/hkmx.do" parameterMap = parameterMap>
					<#include "/content/pager.ftl">
				</@pagination>
				</div>
			</form>	
			<form id="repayForm" >
				<input type="hidden" id="repayId" name="borrowRepaymentDetail.id" value="0"/>
				<input type="hidden" name="flag" value="1"/>
			</form>
			</div>
			
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
