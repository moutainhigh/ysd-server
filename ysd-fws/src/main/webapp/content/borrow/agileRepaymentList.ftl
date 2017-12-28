<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>${Application ["qmd.setting.name"]}-我要还款</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>
	
<script type="text/javascript">
function borrowerPayBack(rid) {

	$("#payback_"+rid).hide();
	$("#payback_"+rid).after('<span id=\"loading_'+rid+'\"><img src="${base}/static/img/loadingmini.gif" /></span>');
	
	
	var mmsg = "确定要还款吗？\n";
	if($("#currentTime").val() !=$("#date_"+rid).val()) {
		mmsg+="当前日期与应还日期不一致！！\n"
	}
	mmsg += "应还日期："+$("#date_"+rid).val()+"\n";
	mmsg += "应还金额："+$("#money_"+rid).val()+"\n";
	
	if (!confirm(mmsg)) {
		
		$("#loading_"+rid).remove();
		$("#payback_"+rid).show();
		return false;
	}
	
	$("#repayId").val(rid);
	
	$.ajax({
        type: "post",
        dataType : "json",
        data: $('#repayForm').serialize(),
        url: qmd.base+'/borrow/ajaxPayBack.do',
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
        	alert("请重新登录！");
        	window.location.href=qmd.base;
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
<body class="body_bg">

	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_agency.ftl">

<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="${base}/userCenter/index.do">账户中心</a></li>
			<li><a href="${base}/borrow/hkmx.do">还款管理</a></li>
            <li>我要还款</li>
			
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <div class="search shadowBread">		
        <form id="listForm" method="post" action="${base}/borrow/agileRepaymentList.do">
        	<span class="block">项目编号：</span>
            <span class="block">
            	<input type="text" name="keywords" value="${keywords}" class="searchText"/>&nbsp;
            </span>    
            <span class="block">应还日期：</span>           
            <span class="block">
           		<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${(minDate)}" class="searchText" >&nbsp;
            </span><span class="block">到&nbsp;</span> 
            <span class="block">
            	<input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${(maxDate)}" class="searchText" > &nbsp;
            </span>
        
        <input type="button" onclick="gotoPage(1);" class = "kaqu_w120" value = "搜索"/>
		<#--<a id="btnadd" class="l-btn l-btn-plain fl" href="javascript:void(0)"><span class="l-btn-tool"><span class="l-btn-text pic-add" style="padding-left:20px;">添加</span></span></a>		
		<a id="btnedit" class="l-btn l-btn-plain fl" href="javascript:void(0)"><span class="l-btn-tool"><span class="l-btn-text pic-edit" style="padding-left:20px;">编辑</span></span></a>		
		<a id="btndel" class="l-btn l-btn-plain fl" href="javascript:void(0)"><span class="l-btn-tool"><span class="l-btn-text pic-delete" style="padding-left:20px;padding-right:8px;">删除</span></span></a>
		-->
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
		<input type="hidden" id="currentTime" value="${currentTime?string("yyyy-MM-dd")}"/>
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <td>项目编号</td>
				<td>项目标题</td>
				<td>发布日期</td>
				<td>最终回购日期</td>
				<td>回购总额</td>
				<td>已付利息</td>
				<td>操作</td>
            </tr>
          </thead>
          <tbody>
              <#list pager.result as bor>
					<tr>
						<td><a href="${Application ["qmd.setting.memberUrl"]}/borrow/detail.do?bId=${bor.id}" target="_blank" title="${bor.businessCode}">${substring(bor.businessCode, 16, "")}</a></td>                                            
						<td title="${bor.title}">${substring(bor.businessCode, 10, "")}</td>
						<td>${bor.verifyTime?string("yyyy-MM-dd")}</td>
						<td>${bor.finalRepayDate?string("yyyy-MM-dd")}</td>
					   	<td>${(bor.repayCapital)?string.currency}</td>
					   	<td>${(bor.payedInterest)?string.currency}</td>
						<td>
							<a href="${base}/borrow/agileRepaymentDetail.do?bid=${bor.id}">回购</a>
							<#--
							<input type="hidden" id="date_${borrowTemp.id}" value="<#if borrowTemp.repaymentDate>${borrowTemp.repaymentDate?string("yyyy-MM-dd")}</#if>"/>
							<input type="hidden" id="money_${borrowTemp.id}" value="${(borrowTemp.capitalAccount+borrowTemp.interestAccount)?string.currency}"/>
							-->
						</td>
					</tr>
				</#list>
          </tbody>
          <tfoot>
              <tr>
                <td colspan="9">
					<#if pager.totalCount==0>
						<div class="nodata">记录为空</div>
					</#if>
					<@pageFlip pager=pager>
						<#include "/content/common/pager.ftl">
					</@pageFlip>              
                </td>
              </tr>
          </tfoot>
        </table>
        </form>
        <!--table end!-->               
    </div>
    <form id="repayForm" >
		<input type="hidden" id="repayId" name="borrowRepaymentDetail.id" value="0"/>
		<input type="hidden" name="flag" value="1"/>
	</form>
</div>
	<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
$(function(){
	$("#id_tbody tr:odd").find("td").each(function(i){
    	$(this).addClass("td1");
  	});

	$(".sssj").attr("id","sssj");
	$("#li_a_wyhk").addClass("li_a_select");
});
</script>
</html>
