<!DOCTYPE html>
<html>
 <head>
	<title>${Application ["qmd.setting.name"]}-我的账户-还款管理-<#if borrStatus ==3>未还款<#elseif borrStatus ==7>还款完成</#if></title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>
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
            <li><#if borrStatus ==3>未还款<#elseif borrStatus ==7>还款完成</#if></li>
			
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <div class="search shadowBread">		
        <form id="listForm" method="post" action="${base}/borrow/hkmx.do">
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
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <#--<td width="19"><input type="checkbox"></td>-->
                <td>项目编号</td>
				<td>最终还款日</td>
		        <td>利率</td>
		        <td>借款本金</td>
		        <td>应还利息</td>
		        <td>未还本金</td>
		        <td>未还利息</td>
		        <td>逾期天数</td>
		        <td>罚息</td>
		        <td>实还本金</td>
		        <td>状态</td>
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
										<td class="${flag}"><a href="${Application ["qmd.setting.memberUrl"]}/borrow/detail.do?bId=${borrow.id}" target="_blank" title="${borrow.name}">${substring(borrow.businessCode, 16, "")}</a></td>                                            
										<td class="${flag}"><#if borrow.finalRepayDate??>${borrow.finalRepayDate?string("yyyy-MM-dd")}</#if></td>
										<td class="${flag}"><#if borrow.type==4>${borrow.apr}%/年<#else>${borrow.apr}‰/天</#if></td>
										<#--<td class="${flag}">${borrow.timeLimit}<#if borrow.type==4>月<#else>天</#if></td>-->
										<td class="${flag}">￥${borrow.repayCapital}</td>
										<td class="${flag}">￥${borrow.repayInterest}</td>
										<td class="${flag}">￥${borrow.payedCapital}</td>
										<td class="${flag}">￥${borrow.payedInterest}</td>
										<td class="${flag}">￥${(borrow.lateDays)}</td>
										<td class="${flag}">￥${(borrow.latePenalty)}</td>
										<td class="${flag}">￥${(borrow.payedCapital)}</td>
										<td class="${flag}"><#if borrow.status==7>已还<#else>未还</#if></td>
										<td class="${flag}"><a href="${base}/borrow/userBorrowLateDetail.do?borrow.id=${borrow.id}">查看详情</a></td>
									</tr>
			</#list>
          </tbody>
          <tfoot>
              <tr>
                <td colspan="12">
					<div class="datagrid-pager pagination">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tbody>
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
                             </tbody>
                         </table>
                    </div>                
                
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
	$(".sssj").attr("id","sssj");
	<#if borrStatus ==3>$("#li_a_whk").addClass("li_a_select");
	<#elseif borrStatus ==7>$("#li_a_hkwc").addClass("li_a_select");
	</#if>
});

		function latePayLost(){
				var testTime = new Date().getTime(); 
				
				if($("#backMoney").val()==''){
					alert('请输入还款金额！');
					return false;
				}
				$.ajax({
			        type: "post",
			        dataType : "json",
			        data:  $("#payForm").serialize(),
			        url: qmd.base+'/borrow/ajaxLateRepaymentBackLost.do?testTime='+testTime,
			        success: function(data, textStatus){
			        	alert(data.message);
						window.location.reload();
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
</html>
