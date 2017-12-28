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
	} else {
		$("#out_kaquFlg").val(0);
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
    <form id="outForm" action="/report/agencyTongjiDown.do" method="post">
    	<input type="hidden" id = "out_minDate" name="begin" />
         <input type="hidden" id = "out_maxDate" name="end" />  
        <input type="hidden" id = "out_kaquFlg" name="kaquFlg" />  
    </form>
    <form id="listForm" action="/report/agencyTongjiList.do" method="post">
    <div class="search shadowBread">		
        
        <input type="text" id = "minDate" name="begin" onclick="WdatePicker()" value="<#if begin?exists>${begin?string("yyyy-MM-dd")}</#if>" class="searchText"/>
         <input type="text" id = "maxDate" name="end" onclick="WdatePicker()" value="<#if end?exists>${end?string("yyyy-MM-dd")}</#if>" class="searchText"/>  
         &nbsp;&nbsp;
         <input type="checkbox" value="1" id="kaquFlg" name="kaquFlg" <#if kaquFlg?exists && kaquFlg==1>checked</#if> />详细
         &nbsp;&nbsp;
        
			<input type="submit"  value="查询"/> <#if diffDt?exists>&nbsp;&nbsp;&nbsp;&nbsp;统计天数为:${diffDt}天</#if>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button"  value="导出" onclick="downout();"/> 
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <#--<td width="19"><input type="checkbox"></td>-->
				<td>用户名</td>
				<td>姓名</td>
				<td>所属地区</td>
				<td>所属分公司</td>
				<td>统计总金额</td>
				<td>日均</td>
				<td>投资次数</td>
            </tr>
          </thead>
          <tbody>
              <#list pager.result as item>
                    	<tr height="39">
                    		<td >${item.username}</td>
                    		<td >${item.userReal}</td>
                    		<td >${item.belongUreal}</td>
                    		<td >${item.belongUname}</td>
                    		<td >${item.totalAccount}</td>
                    		<td >${item.showAverage}</td>
                    		<td >${item.cts}</td>
                    	</tr>
			</#list>
          </tbody>
     	 <tfoot>
              <tr>
                <td colspan="12">
					
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
		$("#li_a_fgsrj").addClass("li_a_select");
	
});
</script>
</html>
