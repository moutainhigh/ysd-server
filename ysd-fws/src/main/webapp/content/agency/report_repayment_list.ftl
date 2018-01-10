<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-统计列表</title>
	<#include "/content/common/meta.ftl">
	
	
<script type="text/javascript">
function downout() {
	$("#out_minDate").val($("#minDate").val());
	$("#out_maxDate").val($("#maxDate").val());
	if($("#kaquFlg").attr("checked")) {
		$("#out_kaquFlg").val(1);
	} 
	$("#outForm").submit();
}
</script>

</head>
<body class="body_bg">
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_user.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>	


<div class="mainBox">
	<div class="breadCrumbs shadowBread">
		<ul>
			<li> <a href="${base}/userCenter/index.do">账户中心</a></li>
            <li>统计列表</li>
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <form id="outForm" action="/report/repaymentDown.do" method="post">
    	<input type="hidden" id = "out_minDate" name="begin" />
         <input type="hidden" id = "out_maxDate" name="end" />  
        <input type="hidden" id = "out_kaquFlg" name="accountOnly" />  
    </form>
    <form id="listForm" action="/report/repaymentList.do" method="post">
    <div class="search shadowBread">		
        
        <input type="text" id = "minDate" name="begin" onclick="WdatePicker()" value="<#if begin?exists>${begin?string("yyyy-MM-dd")}</#if>" class="searchText"/>
         <input type="text" id = "maxDate" name="end" onclick="WdatePicker()" value="<#if end?exists>${end?string("yyyy-MM-dd")}</#if>" class="searchText"/>  
         &nbsp;&nbsp;
         <input type="checkbox" value="1" id="kaquFlg" name="accountOnly" <#if accountOnly?exists && accountOnly==1>checked</#if> />含利息
         &nbsp;&nbsp;
        
			<input type="submit"  value="查询"/> 
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button"  value="导出" onclick="downout();"/> 
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <td>项目ID</td>
				<td>项目编号</td>
				<td>还款日期</td>
				<td>本金</td>
				<td>利息</td>
				<td>服务商</td>
				<td>状态</td>
				<td>项目金额</td>
				<td>项目天数</td>
				<td>利率</td>
				<td>发布日期</td>
				<td>最终还款日</td>
				<td>项目名称</td>
				<td style="width:300px;">项目参数</td>
            </tr>
          </thead>
          <tbody>
              <#list pager.result as item>
	
                    	<tr height="39">
                    		<td >${item.borrowId}</td>
                    		<td >${item.businessCode}</td>
                    		<td >${item.repaymentDate?string("yyyy-MM-dd")}</td>
                    		<td >${item.waitAccount}</td>
                    		<td >${item.waitInterest}</td>
                    		<td >${item.username}</td>
                    		<td >${item.showStatus}</td>
                    		<td >${item.account}</td>
                    		<td >${item.timeLimit}</td>
                    		<td >${item.showRateYear}</td>
                    		<td ><#if item.verifyTime?exists>${item.verifyTime?string("yyyy-MM-dd")}</#if></td>
                    		<td ><#if item.finalRepayDate?exists>${item.finalRepayDate?string("yyyy-MM-dd")}</#if></td>
                    		<td >${item.borrowName}</td>
                    		<td style="word-break:break-all;">${item.contractParam}</td>
                    	</tr>
			</#list>
          </tbody>
     	 <tfoot>
              <tr>
                <td colspan="14">
					
					<@pageFlip pager=pager>
						<#include "/content/common/pager.ftl">
					</@pageFlip>  
                
                </td>
              </tr>
          </tfoot>
        </table>
        <!--table end!-->      
        
    </div>
    </form>         
</div>


	<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
$(function(){
		$(".gjxx").attr("id","gjxx");
		$("#repaymentList").addClass("li_a_select");
	
});
</script>
</html>
