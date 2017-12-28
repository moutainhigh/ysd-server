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
	
	
	
//	$.dialog({type: "warn", content: "确认要还款吗？", ok: "确 定", cancel: "取 消", modal: true, okCallback:function(){
//		$("#repayId").val(rid);
//		$("#repayForm").submit();
//	}});
}

function ajaxAgileRepaymentBack() {
	if(confirm("是否进行最终还款?")) {
	  	$.ajax({
	        type: "post",
	        dataType : "json",
	        data: $('#repayForm').serialize(),
	        url: qmd.base+'/borrow/ajaxAgileRepaymentBack.do',
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
	} else {
	 	return false;
	}
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
        
        	<span class="block">项目本金：</span>
            <span class="block">
            	${borrowRepaymentDetail.capitalNormal}
            </span>    
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap" style="margin-bottom:5px;">       
        <!--table start!-->
		<input type="hidden" id="currentTime" value="${currentTime?string("yyyy-MM-dd")}"/>
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <td>债权编号</td>
				<td>投资人</td>
				<td>回购金额</td>
				<td>已付利息</td>
            </tr>
          </thead>
          <tbody>
              <#list borrowTempList as bd>
					<tr>
						<td>${bd.agileCode}</td>                                            
						<td>${bd.name}</td>
					   	<td>${(bd.agileMoney)?string.currency}</td>
					   	<td>${(bd.agileYesinterest)?string.currency}</td>
					</tr>
				</#list>
          </tbody>
        </table>
        </form>
        <!--table end!-->               
    </div>
    
    <div class="search shadowBread">		
    
    	<input type="button" onclick="ajaxAgileRepaymentBack();" class = "kaqu_w120" value = "最终回购" />
<#--
        <form id="listForm" method="post" action="${base}/borrow/ajaxAgileRepaymentBack.do">
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
-->
        <div class="clear"></div>
	</div>
	
    <form id="repayForm" >
		<input type="hidden" id="repayId" name="brdId" value="${borrowRepaymentDetail.id}"/>
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
