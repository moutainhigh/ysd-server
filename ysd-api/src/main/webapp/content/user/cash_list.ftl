<!DOCTYPE html>
<html>
<head>
  <title>${Application ["qmd.setting.name"]}-我的账户-提现记录</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
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
            <a  style="color:#646464;font-family:'宋体';">提现记录</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            <a href="${base}/userCenter/getMoney.do" class="hr">我要提现</a>
            <a href="javascrip:void(0);" class="hr hre">提现记录</a>     
          </div>
          
          
		<form id="listForm" method="post" action="cashList.do" >
		<input type="hidden" id="cashId" name="accountCash.id" value="0"/>
          
          <div style="font-family:'宋体'; color:#000; margin-top:80px; clear:both;">
				查询时间：
                <input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${(minDate?string("yyyy-MM-dd"))!}" style="width:130px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/> 到 
                <input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${(maxDate?string("yyyy-MM-dd"))!}" style="width:130px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
				<a href="javascript:gotoPage(1);" style="display:inline-block; width:65px; height:35px; line-height:35px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;">搜 索</a>
			</div>  
         <#-- <div style="height:33px;line-height:33px;text-align:center;color:#595757;font-family:'宋体';background:#efebdf; margin-top:20px; margin-bottom:20px;">
            	<a style="margin-right:120px;">成功提现:  ￥100000</a><a style="margin-right:120px;">提现到账:  ￥100000</a><a style="margin-right:0px;">手续费:  ￥100000</a>
          </div>
          -->
          <div style=" margin-top:10px;">
            <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
				<thead bgcolor="#efebdf" align="center">
						<tr height="35">
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">提现银行</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">提现帐号</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">提现总额</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">到账金额</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">手续费</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">提现时间</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">状态</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">操作</th>
						</tr>
					</thead>
						<tbody align="center">
						
						<#list pager.result as accountCash>
							<tr height="50">
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${accountCash.name}</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">****${accountCash.account?substring(4,accountCash.account?length-4)} ****</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${accountCash.total?string.currency}</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(accountCash.credited?string.currency)!}</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(accountCash.fee?string.currency)!}</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									<#if !accountCash.createDate>
										${accountCash.createDate}
									 <#else>
										${accountCash.createDate?string("yyyy-MM-dd HH:mm:ss")}
									</#if>
								</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									<#if accountCash.status == 0>
										审核中
									<#elseif accountCash.status==1 >
										提现成功
									<#elseif accountCash.status==3>
										用户撤销
									<#elseif accountCash.status==4 >
										处理中
									<#else>
										提现审核失败
									</#if>
								</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									<#if accountCash.status == 0>
										<a href="javascript:void(0);" id="rootback_${accountCash.id}" onclick="cashRootBack(${accountCash.id})">撤回</a>
									<#elseif accountCash.status==4 >
										处理中请等待
									<#elseif accountCash.status==1 >
										${accountCash.verifyRemark}
									<#else>
										提现失败
									</#if>
								</td>
							</tr>
				</#list>
					</tbody>
				</table>  
                  <#if pager.totalCount==0>
					<div class="nodata"  align="center" >提现明细记录为空</div>
				  </#if>
					<@pagination pager=pager >
						<#include "/content/pager.ftl">
					</@pagination>
				</div>
			</form>      
      </div>
	
	<#-- 		右边内容块 结束				-->
	
	</div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->

 <!--footer-->
<#include "/content/common/footer.ftl">





	<#-->
	
<div style="float:right; width:760px;">
 <div style=" padding-left:15px; background:#ede3cb; height:30px; line-height:30px; margin-bottom:10px;">
    <a href="" style="color:#000; margin-right:5px;">${Application ["qmd.setting.name"]}</a><a style="color:#000; margin-right:5px;">><a href="" style="color:#000; margin-right:5px;">我的账户</a><a style="color:#000; margin-right:5px;">></a><a href="" style="color:#000; margin-right:5px;">资金管理</a><a style="color:#000; margin-right:5px;">></a><a href="" style="color:#000; margin-right:5px;"><#if loginUser.typeId==0>我的提现<#else>项目提现</#if>>></a><a href="" style="color:#000; margin-right:5px;">提现记录</a>
  </div>
  <div>
      <a href="${base}/userCenter/getMoney.do" style="display:inline-block; background:url(${base}/static/img/s2.png) 0 0; width:122px; height:28px; line-height:28px;color:#814a03; font-size:14px; font-weight:700; text-align:center;"><#if loginUser.typeId==0>我的提现<#else>项目提现</#if></a>
      <a href = "javascript:void(0);" style="display:inline-block; background:url(${base}/static/img/s1.png) 0 0; width:122px; height:28px; line-height:28px; color:#fff; font-size:14px; font-weight:700; text-align:center;">提现记录</a>
    </div>
	<form id="listForm" method="post" action="cashList.do" >
		<input type="hidden" id="cashId" name="accountCash.id" value="0"/>
		<div style="border:1px solid #c3c3c3;">
			<div style="border:none; width:740px; margin:10px auto;">
				<div style=" background:#ebdec6; padding-left:10px; padding-top:3px; padding-bottom:3px;">
			查询时间：<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${(minDate?string("yyyy-MM-dd"))!}" style="background:url(${base}/static/img/input14.png) 0 0 no-repeat;width:120px; height:27px; padding-left:5px;border:0 none;">
					   &nbsp;&nbsp;到&nbsp;&nbsp;
					 <input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${(maxDate?string("yyyy-MM-dd"))!}" style="background:url(${base}/static/img/input14.png) 0 0 no-repeat;width:120px; height:27px; padding-left:5px;border:0 none;">
					 <input type="button" onclick="gotoPage(1);" style=" background:url(${base}/static/img/cz.png); width:57px; height:29px; border:0 none; color:#fff;" value = "搜索"/>
				</div>
			</div>
			<div class="base_data-list2" style="width:740px;">
				<table class="base_tac" border="1" bordercolor="#ccc">
					<thead bgcolor="#ebdec6">
		             	<tr >
								<th style=" font-family:'宋体'; font-weight:bold; font-size:14px; color:#000;">提现时间</th>
                        		<th style=" font-family:'宋体'; font-weight:bold; font-size:14px; color:#000;">提现银行</th>
								<th style=" font-family:'宋体'; font-weight:bold; font-size:14px; color:#000;">提现账号</th>
								<th style=" font-family:'宋体'; font-weight:bold; font-size:14px; color:#000;">提现总额(元)</th>
								<th style=" font-family:'宋体'; font-weight:bold; font-size:14px; color:#000;">到账金额</th>
								<th style=" font-family:'宋体'; font-weight:bold; font-size:14px; color:#000;">手续费</th>
								<th style=" font-family:'宋体'; font-weight:bold; font-size:14px; color:#000;">状态</th>
								<th style=" font-family:'宋体'; font-weight:bold; font-size:14px; color:#000;">操作</th>
							</tr>
						</thead>
						<tbody>
			<#list pager.result as accountCash>
					<#assign flag = "">
					<#if accountCash_index %2 != 0>
						<#assign flag = "td1">
					</#if>
						<tr height="39">
								<td class="${flag}">
									<#if !accountCash.createDate>
										${accountCash.createDate}
									 <#else>
										${accountCash.createDate?string("yyyy-MM-dd HH:mm:ss")}
									</#if>
								</td>
								<td class="${flag}">${accountCash.name}</td>
								<td class="${flag}">****${accountCash.account?substring(4,accountCash.account?length-4)} ****</td>
								<td class="${flag}">${accountCash.total?string.currency}</td>
								<td class="${flag}">${(accountCash.credited?string.currency)!}</td>
								<td class="${flag}">${(accountCash.fee?string.currency)!}</td>
								
								<td class="${flag}">
									<#if accountCash.status == 0>
										审核中
									<#elseif accountCash.status==1 >
										提现成功
									<#elseif accountCash.status==3>
										用户撤销
									<#elseif accountCash.status==4 >
										处理中
									<#else>
										提现审核失败
									</#if>
								</td>
								<td class="${flag}">
									<#if accountCash.status == 0>
										<a href="javascript:void(0);" id="rootback_${accountCash.id}" onclick="cashRootBack(${accountCash.id})">撤回</a>
									<#elseif accountCash.status==4 >
										处理中请等待
									<#elseif accountCash.status==1 >
										${accountCash.verifyRemark}
									<#else>
										提现失败
									</#if>
								</td>
							</tr>
						</tbody>
				</#list>
			</table>
			<#if pager.totalCount==0>
				<div class="nodata"  align="center" >提现明细记录为空</div>
			</#if>
			<@pageFlip pager=pager>
			<#include "/content/common/pager.ftl">
			</@pageFlip>
	</form>
</div>
    </div>
  </div>
</div>
  <#include "/content/common/footer.ftl">
  
  -->
 </body>
 <script type="text/javascript">
	function cashRootBack(cashid) {

	$("#rootback_"+cashid).hide();
	$("#rootback_"+cashid).after('<span id=\"loading_'+cashid+'\"><img src="${base}/static/img/loadingmini.gif" /></span>');
	
	if (!confirm("确认要撤回此提现记录吗？")) {
		
		$("#loading_"+cashid).remove();
		$("#rootback_"+cashid).show();
		return false;
	}
	
	$("#cashId").val(cashid);
	
	$.ajax({
        type: "post",
        dataType : "json",
        data: $('#listForm').serialize(),
        url: 'ajaxUpdateCash.do',
        success: function(data, textStatus){
        	if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
        		alert("撤回失败！");
        		$("#loading_"+cashid).remove();
				$("#rootback_"+cashid).show();
        	} else if (data.status=="success") {
        		alert("撤回成功");
        		window.location.reload();
        	} else {
	        	alert(data.message);
	        	$("#loading_"+cashid).remove();
				$("#rootback_"+cashid).show();
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
<script type="text/javascript">
$(function(){
	$("#my_deposit").addClass("nsg nsg_a1");
});
</script>
</html>
