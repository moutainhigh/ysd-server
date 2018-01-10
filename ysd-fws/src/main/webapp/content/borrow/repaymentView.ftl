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
<body class="body_bg">

	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_agency.ftl">
<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li> <a href="${base}/userCenter/index.do">账户中心</a></li>
			<li><a href="${base}/borrow/userBorrowNoDone.do">还款管理</a></li>
			<#if borrow.status!=7>
				<li>未还款</li>
			<#elseif borrow.status==7>
				<li>还款完成</li>
			</#if>
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
        	<h3>借款详情</h3>
            
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
               <td class="text_r org grayBg" width="40"></td>
                <td class="text_r grayBg" width="86">项目标题：</td>
                <td>${borrow.name}</td>
                
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">借款人：</td>
                <td>
                  ${borrow.realName}
				</td>
              </tr>
              <tr>
                <td class="text_r org grayBg" width="40"></td>
                <td class="text_r grayBg" width="86">项目总额：</td>
                <td>￥${borrow.account}</td>
            	
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">项目期限：</td>
                <td>
                  ${borrow.timeLimit}天
				</td>
              </tr>
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">起息日：</td>
                <td>${borrow.successTime?string("yyyy-MM-dd")}</td>
                
              	<td class="text_r org grayBg"></td>
                <td class="text_r grayBg">还款方式：</td>
                <td><#if borrow.style==1>到期还本息<#elseif borrow.style==2>按月付息到期还本</#if></td>
             
              </tr>
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">项目余额：</td>
                <td>￥${borrow.borrowMoney!'0'}&nbsp;&nbsp;&nbsp;<a href="${base}/borrowRecharge/borrowInfo.do?id=${borrow.id}">【前往充值】</a></td>
                
                <td ></td>
                <td></td>
                <td></td>
              </tr>
            </table> 
            
            
            <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <#--
                <td >序号</td>
				<td >计划还款日</td>
				<td >计划还款本息</td>
				<td >实还日期</td>
				<td >实还本息</td>
				<td >逾期罚息</td>
				<td >逾期天数</td>
				<td >状态</td>
				<td >操作</td>
				-->
				<td >期数</td>
				<td >计划还款日</td>
				<td >应还本金</td>
				<td >应还利息</td>
				<td >实还本金</td>
				<td >实还利息</td>
				<td >逾期天数</td>
				<td >逾期罚息</td>
				<td >状态</td>
				<#if borrow.status!=7>
				<td >操作</td>
				</#if>
            </tr>
          </thead>
          <tbody>
              <#list borrowRepDetailList as borrowRepaymentDetail>
				 <tr>
					<td>${(borrowRepaymentDetail.orderNum+1)}/${borrow.divides}</td>
					<td>${borrowRepaymentDetail.repaymentTime?string("yyyy-MM-dd")}</td>
					<td>￥${borrowRepaymentDetail.capital} </td>
					<td>￥${borrowRepaymentDetail.interest}</td>
					<#if borrowRepaymentDetail.status==1>
					<td>￥${borrowRepaymentDetail.capital}</td>
					<td>￥${borrowRepaymentDetail.interest}</td>
					<#else>
					<td>---</td>
					<td>---</td>
					</#if>
					<td><#if borrowRepaymentDetail.lateDays== 0>--<#else> ${borrowRepaymentDetail.lateDays}</#if></td>
					<td>
						 ￥${borrowRepaymentDetail.lateInterest!'0'}
						</td>
					<td><#if (borrowRepaymentDetail.status==1)>已还
						<#else>待还
						</#if></td>
					<#if borrow.status!=7>
					<td>
						<#if (borrowRepaymentDetail.status==1)>
							--
						<#else>
							<a href="javascript:void(0);" class = "kaqu_huankuanzhong " id="payback_${borrowRepaymentDetail.id}" onclick="borrowerPayBack(${borrowRepaymentDetail.id})">【还款】</a>
						</#if>
					</td>
					</#if>
				</tr>
			</#list> 
			<#--<tr>
				<td colspan="9" class="tac">注：带有“（预计）”标记的金额说明该金额并非实际还款金额，它只是假设以当前时间为还款时间的情况下用户将要还多少金额。</td>
			</tr>     -->      
          </tbody>
          <tfoot>
              
          </tfoot>
        </table>
                 <form id="repayForm" >
						<input type="hidden" id="repayId" name="borrowRepaymentDetail.id" value="0"/>
						<input type="hidden" name="flag" value="2"/>
					</form>
        </div>                
    </div>
    
</div>


	<#include "/content/common/footer.ftl">
</body>

<script type="text/javascript">
$(function(){
	$(".sssj").attr("id","sssj");
	$("#li_a_hkwc").addClass("li_a_select");
	
});
</script>
</html>
