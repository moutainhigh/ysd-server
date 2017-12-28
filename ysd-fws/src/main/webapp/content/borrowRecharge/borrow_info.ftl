<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>${Application ["qmd.setting.name"]}-还款充值</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${base}/js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${base}/js/common/ajaxImageUpload.js"></script>
	
<script type="text/javascript">
function borrowRecharge() {


var v_rechargeName = $("#input_rechargeName").val();
var v_rechargeDate = $("#input_rechargeDate").val();
var v_rechargeAccount = $("#input_rechargeAccount").val();
var v_rechargeBank = $("#input_rechargeBank").val();
var v_vouchers = $("#vouchers").val();
var v_safepwd =$("#input_safepwd").val();

if(v_rechargeName=='') {
	alert("请输入打款人");
	return;
}
if(v_rechargeDate=='') {
	alert("请输入打款时间");
	return;
}
if(v_rechargeAccount=='') {
	alert("请输入充值账户");
	return;
}
if(v_rechargeBank=='') {
	alert("请输入充值银行");
	return;
}
if(v_vouchers=='') {
	alert("请输入充值凭证");
	return;
}
if(v_safepwd=='') {
	alert("请输入安全密码");
	return;
}



	$.ajax({
        type: "post",
        dataType : "json",
        data: $('#rechargeForm').serialize(),
        url: qmd.base+'/borrowRecharge/rechargeSave.do',
        success: function(data, textStatus){
        	if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
        		alert("申请失败！");
        	} else if (data.status=="success") {
        		alert("申请成功");
        		window.location.reload();
        	} else {
	        	alert(data.message);
        	}
        	
        	
        },
        error:function(statusText){
        	alert("请重新登录！");
        	window.location.href=qmd.base;
        },
        exception:function(){
        	alert(statusText);
        }
	});
	
}

<#--上传图片后回调-->
function uploadFileHideBack () {
	alert('上传成功！！');
}
</script>
	
</head>
<body class="body_bg">

	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_account.ftl">

<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="${base}/userCenter/index.do">账户中心</a></li>
			<li><a href="${base}/borrowRecharge/borrowList.do">还款充值</a></li>
            <li>还款充值</li>
			
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    
    
    <div class="mainWrap">       
        <div class="broderShadow">
        	<h3>还款充值</h3>
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="text_r grayBg" width="100">项目标题：</td>
                <td>${borrow.name}</td>
                <td class="text_r grayBg" width="100">借款人：</td>
                <td>${borrow.username}</td>
              </tr>
              <tr>
                <td class="text_r grayBg" width="100">项目总额：</td>
                <td>${borrow.account}</td>
                <td class="text_r grayBg" width="100">项目期限：</td>
                <td>${borrow.timeLimit}</td>
              </tr>
              <tr>
                <td class="text_r grayBg" width="100">保证金：</td>
                <td>${borrow.depositMoney}</td>
                <td class="text_r grayBg" width="100">项目余额：</td>
                <td>${borrow.borrowMoney}</td>
              </tr>
              <tr>
                <td class="text_r grayBg" width="100">起息日：</td>
                <td>
                	<#if borrow.successTime>
			   			${borrow.successTime?string("yyyy-MM-dd")}
			   		</#if>
                </td>
                <td class="text_r grayBg" width="100">还款方式：</td>
                <td>
                	<#if borrow.style==1>分期付息，到期本息
                	<#elseif  borrow.style==2>到期付本息
                	<#elseif  borrow.style==3>等额本金
                	</#if>
                </td>
              </tr>
          	</table>
        
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <#--<td width="19"><input type="checkbox"></td>-->
                <td>期数</td>
				<td>还款日期</td>
				<td>应还本金</td>
				<td>应还利息</td>
				<td>需充值金额</td>
				<td>还款状态</td>
				<#--><td>充值状态</td>-->
            </tr>
          </thead>
          <tbody>
              <#list borrowRepaymentDetailList as item>
					<#--<td><input type="checkbox"></td>-->
					<tr>
						<td>
					   		${(item.orderNum+1)}/${borrow.divides}
					   	</td>
					   	<td>
					   		<#if item.repaymentTime>
					   			${item.repaymentTime?string("yyyy-MM-dd")}
					   		</#if>
					   	</td>
						<td>${item.capital}</td>
					   	<td>${item.interest}</td>
					   	<td>${item.rechargeMoney?string.currency}</td>
						<td>
							<#if item.status==1>已还<#else>未还</#if>
						</td>
						<#--><td>
							<#if item.rechargeStatus==0>未
							<#elseif item.rechargeStatus==1>完成
							<#elseif item.rechargeStatus==2>审核中
							<#elseif item.rechargeStatus==3>审核失败
							<#elseif item.rechargeStatus==4>撤回
							</#if>
						</td>-->
					</tr>
				</#list>
          </tbody>
          
        </table>
        
        <#if borrowRecharge?exists>
        
        <#if borrowRecharge.status?exists && (borrowRecharge.status==1||borrowRecharge.status==2)>
        	<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="text_r grayBg" width="100">充值金额：</td>
                <td>${borrowRecharge.money}</td>
              </tr>
              <tr>
                <td class="text_r grayBg" width="100">打款人：</td>
                <td>${borrowRecharge.rechargeName}</td>
              </tr>
              <tr>
                <td class="text_r grayBg" width="100">打款时间：</td>
                <td>
                	<#if borrowRecharge.rechargeDate>
			   			${borrowRecharge.rechargeDate?string("yyyy-MM-dd HH:mm:ss")}
			   		</#if></td>
              </tr>
              <tr>
                <td class="text_r grayBg" width="100">充值方式：</td>
                <td><#if borrowRecharge.rechargeType==1>转账<#elseif borrowRecharge.rechargeType==2>现金</#if></td>
              </tr>
              <tr>
                <td class="text_r grayBg" width="100">充值账号：</td>
                <td>${borrowRecharge.rechargeAccount}</td>
              </tr>
              <tr>
                <td class="text_r grayBg" width="100">充值银行：</td>
                <td>${borrowRecharge.rechargeBank}</td>
              </tr>
              <tr>
                <td class="text_r grayBg" width="100">充值凭证：</td>
                <td>${borrowRecharge.rechargeFile}</td>
              </tr>
              
               <tr>
                <td class="text_r grayBg" width="100">充值状态：</td>
                <td>
					<#if borrowRecharge.status==0>未
							<#elseif borrowRecharge.status==1>完成
							<#elseif borrowRecharge.status==2>审核中
							<#elseif borrowRecharge.status==3>审核失败
							<#elseif borrowRecharge.status==4>撤回
							<#else>未申请
							</#if>
				</td>
              </tr>
              
              
              
          	</table>
        
        <#else>
        
        <form id="rechargeForm">
        <input type="hidden" name="borrowRecharge.borrowRepaymentId" value="${borrowRecharge.borrowRepaymentId}"/>
        <input class="input2 " name = "borrowRecharge.money" value="${borrowRecharge.money}"  type="hidden"/>
        <table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
        		<#if borrowRecharge.status==3>
        		
        		 <tr>
                <td class="text_r grayBg" width="100">失败原因：</td>
                <td>${borrowRecharge.verifyRemark}</td>
              </tr>
        		
        		</#if>
        
        
              <tr>
                <td class="text_r grayBg" width="100">充值金额：</td>
                <td>${borrowRecharge.money}</td>
              </tr>
              
              <tr>
                <td class="text_r grayBg" width="100">打款人：</td>
                <td><input class="input2 " id="input_rechargeName" name = "borrowRecharge.rechargeName"  value="${borrowRecharge.rechargeName}"  type="text"/></td>
              </tr>
              
              <tr>
                <td class="text_r grayBg" width="100">打款时间：</td>
                <td>
                	
                	<input class="input2 " id="input_rechargeDate" name = "borrowRecharge.rechargeDate"  value="<#if borrowRecharge.rechargeDate?exists>${borrowRecharge.rechargeDate?string("yyyy-MM-dd HH:mm:ss")}</#if>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text"/>
                </td>
              </tr>
              
              <tr>
                <td class="text_r grayBg" width="100">充值方式：</td>
                <td>
                	<input id="rad" name="borrowRecharge.rechargeType" type="radio" value="1" <#if borrowRecharge.rechargeType?exists><#if borrowRecharge.rechargeType!=2>checked</#if><#else>checked</#if> /> 转账
					<input id="rad" name="borrowRecharge.rechargeType" type="radio" value="2" <#if borrowRecharge.rechargeType?exists && borrowRecharge.rechargeType==2>checked</#if> /> 现金
                </td>
              </tr>
              
              <tr>
                <td class="text_r grayBg" width="100">充值账号：</td>
                <td><input class="input2 " id="input_rechargeAccount" name = "borrowRecharge.rechargeAccount"  value="${borrowRecharge.rechargeAccount}"  type="text"/></td>
              </tr>
              <tr>
                <td class="text_r grayBg" width="100">充值银行：</td>
                <td><input class="input2 " id="input_rechargeBank" name = "borrowRecharge.rechargeBank"  value="${borrowRecharge.rechargeBank}"  type="text"/></td>
              </tr>
              <tr>
                <td class="text_r grayBg" width="100">充值凭证：</td>
                <td>
                
                	<input type="hidden" id="vouchers" name="borrowRecharge.rechargeFile" value="${(borrowRecharge.rechargeFile)!}"/>
					<input type="file" id="imageFile" name="imageFile" class="kaqu_w100 kaqu_w102">

					<a onclick="ajaxFileUploadImageWithRtn('4', 'imageFile','${base}/file/ajaxUploadImage.do','vouchers',uploadFileHideBack);" id="btnLogo1" href="javascript:void(0);" class="kaqu_e1">上传</a>
					<a onclick="viewImage('vouchers')" class="kaqu_e2" href="javascript:void(0);">查看</a>
                
                </td>
              </tr>
              <tr>
                <td class="text_r grayBg" width="100">安全密码：</td>
                <td><input class="input2 " id="input_safepwd" name = "safepwd"  type="password"/></td>
              </tr>
             
             <tr>
                <td class="text_r grayBg">&nbsp;</td>
            	<td height="40">
                	<input class="btn1 white" type="button" value="提交" onclick="borrowRecharge();">
       	   			<input class="btn2 ml_15" type="reset" value="重置">
                </td>
              </tr>
          	</table>
        </form>
        </#if>
        </#if>
        </div>
        <!--table end!-->               
    </div>
</div>
	<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
$(function(){
	$("#id_tbody tr:odd").find("td").each(function(i){
    	$(this).addClass("td1");
  	});

	$(".sssj").attr("id","sssj");
	$("#li_a_hkcz").addClass("li_a_select");
});
</script>
</html>
