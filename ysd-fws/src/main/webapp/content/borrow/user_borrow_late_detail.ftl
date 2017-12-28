<!DOCTYPE html>
<html>
 <head>
	<title>${Application ["qmd.setting.name"]}-我的账户-逾期还款明细</title>
	<#include "/content/common/meta.ftl">
	
<script type="text/javascript">
function ajaxLateRepaymentBackFull() {

	if (!confirm("确定要全额还款吗？")) {
		return false;
	}
	
	$.ajax({
        type: "post",
        dataType : "json",
        data: $('#repayForm').serialize(),
        url: '/borrow/ajaxLateRepaymentBackFull.do',
        success: function(data, textStatus){
        	if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
        		alert("还款失败！");
        	} else if (data.status=="success") {
        		alert("还款成功");
        		window.location.reload();
        	} else {
	        	alert(data.message);
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

function poputLateRepaymentBackLost() {
	var testTime = new Date().getTime();
	var $borrowId = $("#borrowId").val();
    KP.options.drag = true;
	KP.show("有损还款", 400, 250);
	$(KP.options.content).load(qmd.base+"/borrow/lateRepaymentBackLost.do?bid="+$borrowId+"&testTime="+testTime);
}

function ajaxLateRepaymentBackLost() {

	if($("#backMoney").val()=='') {
		alert("请输入还款金额！");
		return;
	}

	if (!confirm("确定要有损还款吗？")) {
		return false;
	}
	
	$.ajax({
        type: "post",
        dataType : "json",
        data: $('#lostForm').serialize(),
        url: '/borrow/ajaxLateRepaymentBackLost.do',
        success: function(data, textStatus){
        	if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
        		alert("还款失败！");
        	} else if (data.status=="success") {
        		alert("还款成功");
        		window.location.reload();
        	} else {
	        	alert(data.message);
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
			<li><a href="${base}/borrow/hkmx.do">还款管理</a></li>
			<#if borrow.status!=7>
				<li>未还款</li>
			<#elseif borrow.status==7>
				<li>还款完成</li>
			</#if>
		</ul>
	</div>
 
 <input type="hidden" id="borrowId" value="${borrow.id}"/>   
    <div class="mainWrap">
        <div class="broderShadow">
        	<h3>借款详情</h3>
            
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
               <td class="text_r org grayBg" width="40"></td>
                <td class="text_r grayBg" width="86">项目名称：</td>
                <td>${borrow.name}</td>
                
                 <td class="text_r org grayBg"></td>
                 <td class="text_r grayBg">状态：</td>
                <td>
                	<font color="red"><#if borrow.lateStatus==1>利息逾期<#elseif borrow.lateStatus==2>项目逾期</#if><#if borrow.status==3>未还<#elseif borrow.status==7>已还</#if></font>
                </td>
              </tr>
              <tr>
                <td class="text_r org grayBg" width="40"></td>
                <td class="text_r grayBg" width="86">借款金额：</td>
                <td>￥${borrow.account}</td>
                
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">借款利率：</td>
                <td>
                  <#if borrow.type==4>${borrow.apr}%/年<#else>${borrow.apr}‰/天</#if></td>
				</td>
              </tr>
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">借款期限：</td>
                <td>${borrow.timeLimit}天</td>
                
              	<td class="text_r org grayBg"></td>
                <td class="text_r grayBg">还款方式：</td>
                <td><#if borrow.style==1>到期还本息<#elseif borrow.style==2>按月付息到期还本</#if></td>
             
              </tr>
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">发布时间：</td>
                <td>${borrow.verifyTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">借款时间：</td>
                <td>${borrow.successTime?string("yyyy-MM-dd HH:mm:ss")}</td>
              </tr>
            </table> 
            
            
            <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <#--<td width="19"><input type="checkbox"></td>-->
                <td >序号</td>
				<td >计划还款日</td>
				<td >计划还款本息</td>
				<td >实还日期</td>
				<td >实还本息</td>
				<td >逾期罚息</td>
				<td >逾期天数</td>
				<td >状态</td>
				<td >操作</td>
            </tr>
          </thead>
          <tbody>
          		 <#assign colflg=true>
              <#list borrowRepDetailList as borrowRepaymentDetail>
              	<#if (borrowRepaymentDetail.status!=-1)>
				 <tr>
				 	<#--<td><input type="checkbox"></td>-->
					<td>${(borrowRepaymentDetail.orderNum) }</td>
					<td>${borrowRepaymentDetail.repaymentTime?string("yyyy-MM-dd")}</td>
					<td>￥${borrowRepaymentDetail.showRepayTotal} </td>
					<td>
						<#if !borrowRepaymentDetail.repaymentYestime>--
						<#else>${borrowRepaymentDetail.repaymentYestime?string("yyyy-MM-dd HH:mm:ss")}
						</#if>
					</td>
					<td>￥
						<#if !borrowRepaymentDetail.repaymentYesaccount>--
						<#else>${borrowRepaymentDetail.repaymentYesaccount}
						</#if></td>
					<td>￥<#if !borrowRepaymentDetail.lateInterest>0<#else>${borrowRepaymentDetail.lateInterest}</#if></td>
					<td>${borrowRepaymentDetail.lateDays}天</td>
					<td>
						<#if (borrowRepaymentDetail.status==1)>已还
						<#else>待还款
						</#if>
					</td>
					<td>
						<#if (borrowRepaymentDetail.status==1)>
						<#else><a href="javascript:void(0);" class = "kaqu_huankuanzhong " id="payback_${borrowRepaymentDetail.id}" onclick="borrowerPayBack(${borrowRepaymentDetail.id})">还款</a>
						</#if>
					</td>
				</tr>
				<#else>
				<tr>
					<td>${(borrowRepaymentDetail.orderNum) }</td>
					<td>${borrowRepaymentDetail.repaymentTime?string("yyyy-MM-dd")}</td>
					<#if colflg==true>
						<#assign colflg=false>
						<td colspan="7" rowspan="${countDown}">项目逾期</td>
					</#if>
				</tr>
				</#if>
			</#list> 
			<tr>
				<td colspan="9" class="tac">&nbsp;</td>
			</tr>           
          </tbody>
          <tfoot>
              
          </tfoot>
        </table>
        
        
        <h3>逾期信息</h3>
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
				<td >逾期起始日</td>
				<td >未还本金</td>
				<td >未还利息</td>
				<td >逾期天数</td>
				<td >罚息</td>
				<td >最终还款日</td>
				<td >逾期已还</td>
            </tr>
          </thead>
          <tbody>
              <tr>
				<td >${borrow.lateDateBegin?string("yyyy-MM-dd")}</td>
				<td >${borrow.lateAccount}</td>
				<td >${borrow.lateInterest}</td>
				<td >${borrow.lateDays}</td>
				<td >${borrow.latePenalty}</td>
				<td ><#if borrow.lateDateEnd?exists>${borrow.lateDateEnd?string("yyyy-MM-dd")}<#else>--</#if></td>
				<td ><#if borrow.status==3>逾期未还<#elseif borrow.status==7>逾期已还</#if></td>
            </tr>
			<#if borrow.status==3>
			<tr>
				<td colspan="7" class="tac">
					<input type="button" value="全额还款" onclick="ajaxLateRepaymentBackFull();" />&nbsp;&nbsp;&nbsp;
					<input type="button" value="有损还款" onclick="poputLateRepaymentBackLost();"/></td>
			</tr>           
			</#if>
          </tbody>
          <tfoot>
              
          </tfoot>
        </table>
                 
        </div>                
    </div>
    
</div>
<form id="repayForm">
	<input type="hidden" name="bid" value="${borrow.id}"/>
</form>

	<#include "/content/common/footer.ftl">
</body>

<script type="text/javascript">
$(function(){
	$("#menuDocking").addClass("kaqu_bg");
	$("#menuDocking_ul").addClass("user_content_top1");
	$("#menuDocking_hkmx").addClass("user_dlk");
});
</script>
</html>
