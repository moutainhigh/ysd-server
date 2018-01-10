<!DOCTYPE html>
<html>
 <head>
	<title>${Application ["qmd.setting.name"]}-我的账户-项目预约管理-预约项目列表</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>
	<style>
		.kaqu_w70_zqzr {
		    background: url("${base}/static/img/right.png") no-repeat scroll 125px 8px rgba(0, 0, 0, 0);
		    color: #363636;
		    float: left;
		    font-size: 20px;
		    width: 115px;
		}
	</style>
</head>
<body class="body_bg">

	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_agency.ftl">
<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="${base}/userCenter/index.do">账户中心</a></li>
			<li><a href="${base}/reservation/list.do">项目预约管理</a></li>
            <li>预约项目列表</li>
			
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <div class="search shadowBread">		
        <form id="listForm" method="post" action="${base}/reservation/list.do" >
           <span class="block"> 项目编号：</span>  
          
           	 <input type="text" class="searchText" name = "keywords" value="${(keywords)!}"/>&nbsp;&nbsp;&nbsp;
             <span class="block"> 预约募集日期：</span>  
           <span class="block">
            	<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${(minDate)!}" class="searchText" style = "width :80px">
            	<span class="block">&nbsp;到&nbsp;</span>
					 	<input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${(maxDate)!}" class="searchText" style = "width :80px">
				  <span class="block">&nbsp;&nbsp;编号：</span>
				 
				<input type="button" onclick="gotoPage(1);" value = "搜索"/>
			</span> 
            
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <td>项目编号</td>
				<td>类型</td>
				<td>发布日期</td>
				<td>项目总金额</td>
				<td>可预约比例</td>
				<td>预约成功奖励</td>
				<td>定金比例</td>
				<td>预约募集开始时间</td>
				<td>公开募集开始时间</td>
				<td>状态</td>
				<td>预约金额</td>
				<td>完成预约金额</td>
				<td>操作</td>
            </tr>
          </thead>
          <tbody>
              <#list pager.result as borrow>
					<#assign flag = "">
					<#if borrow_index %2 != 0>
						<#assign flag = "td1">
					</#if>
						<tr height="39">
							<td class="${flag}">
								<#if borrow.reservationStatus == 1>
									<a href = "${Application ["qmd.setting.memberUrl"]}/reservation/detail.do?bId=${borrow.id}" title = "" target="_blank">
								<#elseif borrow.reservationStatus == 3>
									<a href = "${Application ["qmd.setting.memberUrl"]}/reservationRaise/detail.do?bId=${borrow.id}" title = "" target="_blank">
								<#elseif borrow.reservationStatus == 5>	
									<a href = "${Application ["qmd.setting.memberUrl"]}/borrow/detail.do?bId=${borrow.id}" title = "" target="_blank"> 
								</#if>
									${substring(borrow.businessCode, 26, "")}
								</a>
							</td>
							<td class="${flag}">${borrow.showBorrowType}-${borrow.showBusinessTypeName}</td>
							<td class="${flag}">${(borrow.createDate?string("yyyy-MM-dd"))!}</td>
							<td class="${flag}">${borrow.account?eval?string.currency}</td>
							<td class="${flag}">${borrow.reservationProportion}%</td>
							<td class="${flag}">${borrow.reservationAwardProportion}%</td>
							<td class="${flag}">${borrow.downPaymentProportion}%</td>
							<td class="${flag}">${(borrow.reservationEmitTime?string("yyyy-MM-dd HH:mm:ss"))!}</td>
							<td class="${flag}">${(borrow.reservationReleaseTime?string("yyyy-MM-dd HH:mm:ss"))!}</td>
							<td class="${flag}">
								<#if borrow.status ==1>
									${borrow.showReservationStatus}
								<#else>
									${borrow.showBorrowStatus}								
								</#if>
							</td>
							<td class="${flag}">${(borrow.reservationSize*borrow.wanderPieceMoney)?string.currency}</td>
							<td class="${flag}">${(borrow.reservationFinishMoney?string.currency)!'￥0'}</td>
							<td class="${flag}">
								<a href = "${base}/reservation/detail.do?bid=${borrow.id}" >【项目详情】</a> 
								<#if borrow.reservationStatus == 1>
									<a href = "javascript:void(0);" class = "res_recall" borrowId="${borrow.id}">【撤回项目】</a> 
								</#if>
								
							</td>
						</tr>
				</#list>
          </tbody>
          <tfoot>
              <tr>
                <td colspan="13">
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
    
</div>
		<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
$(function(){
	$().ready( function() { 
		$(".sssj").attr("id","sssj");
		$("#li_a_yyxmlb").addClass("li_a_select");
	});
	
	//撤回项目操作
	$(".res_recall").click(function(){
		if (confirm("确定要撤回此预约项目吗？")) {
			var testTime = new Date().getTime();
			var $this = $(this);
			var $borrowId = $this.attr('borrowId');
			$.ajax({
			    type: "post",
			    dataType : "json",
			    url: qmd.base + '/reservation/ajaxRecall.do?bid='+$borrowId+'&testTime='+testTime,
			    success: function(data, textStatus){
			    	if(typeof(data.errorMsg) == "undefined" || typeof(data.errorMsg) == "null" || data.errorMsg == null || data.errorMsg == ""){
			    		alert("撤回失败！");
			    		return;
			    	} else if (data.errorMsg=="OK") {
			    		alert("撤回成功");
			    		window.location.reload();
			    	} else {
			        	alert(data.errorMsg);
			        	return;
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
	});		
	
});

</script>
</html>
