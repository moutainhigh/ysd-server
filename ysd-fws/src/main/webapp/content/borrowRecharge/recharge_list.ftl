<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>${Application ["qmd.setting.name"]}-还款充值记录</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>
	
<script type="text/javascript">

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
            <li>充值记录</li>
			
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <div class="search shadowBread">		
        <form id="listForm" method="post" action="${base}/borrowRecharge/rechargeList.do">
        	<span class="block">项目：</span>
            <span class="block">
            	<input type="text" name="keywords" value="${keywords}" class="searchText"/>&nbsp;
            </span>    
            <span class="block">充值日期：</span>           
            <span class="block">
           		<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${(minDate)}" class="searchText" >&nbsp;
            </span><span class="block">到&nbsp;</span> 
            <span class="block">
            	<input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${(maxDate)}" class="searchText" > &nbsp;
            </span>
        </form>
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
                <#--<td width="19"><input type="checkbox"></td>
                <td>类型</td>-->
                <td>充值时间</td>
				<td>充值审核时间</td>
				<td>充值项目</td>
				<td>打款人</td>
				<td>充值金额</td>
				<td>充值方式</td>
				<td>打款银行及账号</td>
				<td>状态</td>
				<td>操作</td>
            </tr>
          </thead>
          <tbody>
              <#list pager.result as item>
					<#--<td><input type="checkbox"></td>-->
					<tr>
						<#--<td><#if item.type==1>还款充值<#elseif item.type==2>退还保证金<#else>--</#if></td>-->
						<td>
					   		<#if item.rechargeDate?exists>
					   			${item.rechargeDate?string("yyyy-MM-dd")}
					   		<#else>--
					   		</#if>
					   	</td>
					   	<td>
					   		<#if item.verifyTime?exists>
					   			${item.verifyTime?string("yyyy-MM-dd")}
					   		<#else>--
					   		</#if>
					   	</td>
					   	
					   	<td>${item.borrowName}</td>   
					   	<td>${item.rechargeName}</td>   
						<td>${item.money?string.currency}</td>
					   	<td><#if item.rechargeType==1>转账<#elseif item.rechargeType==2>现金</#if></td>
					   	<td>${item.rechargeBank}<#if item.rechargeAccount?exists><br/>${item.rechargeAccount}</#if></td>
					   	<td><#if item.status==0>失效
							<#elseif item.status==1>完成
							<#elseif item.status==2>审核中
							<#elseif item.status==3>充值失败
							<#elseif item.status==4>撤回
							<#else>未申请
							</#if>
						</td>
						<td>
							<#if item.status==2>
								<a href="">【凭证】</a> 
								<a href = "javascript:void(0);" class = " kaqu_huankuanzhong  a_recall" id = "${item.id}">【撤回】 </a>
							<#elseif item.status==3>
								<a href = "javascript:void(0);" class = "kaqu_huankuanzhong  a_view" id = "${item.id}">【详情】</a>
							<#else>
								--
							</#if>
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
</div>
	<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
$(function(){
	$("#id_tbody tr:odd").find("td").each(function(i){
    	$(this).addClass("td1");
  	});

	$(".lssj").attr("id","lssj");
	$("#li_a_czjl").addClass("li_a_select");
	
		$(".a_recall").click(function(){
			if (confirm("确认要撤回此条记录吗？")) {
				
				var testTime = new Date().getTime();
				var $this = $(this);
				var $id = $this.attr('id');
				$.ajax({
			        type: "post",
			        dataType : "json",
			        url: qmd.base+'/borrowRecharge/ajaxRecall.do?id='+$id+'&testTime='+testTime,
			        success: function(data, textStatus){
			        	if(typeof(data.errorMsg) == "undefined" || typeof(data.errorMsg) == "null" || data.errorMsg == null || data.errorMsg == ""){
			        		alert("操作失败");
							window.location.reload();
			        	} else if (data.errorMsg=="OK") {
			        		alert("撤回成功");
		        			window.location.reload();
			        	} else {
				        	alert(data.errorMsg);
							window.location.reload();
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
	
	
	// 查看充值详情操作
	$(".a_view").click(function(){
			var testTime = new Date().getTime();
			var $this = $(this);
			var $id = $this.attr('id');
        	KP.options.drag = true;
			KP.show("查看充值详情", 380, 250);
			$(KP.options.content).load(qmd.base+"/borrowRecharge/poputBorrowRechargeView.do?id="+$id+"&testTime="+testTime);
	});
});
</script>
</html>
