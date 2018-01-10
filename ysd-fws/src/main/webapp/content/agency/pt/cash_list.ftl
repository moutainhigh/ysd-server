
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-资金管理-放款管理-放款记录</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>
 <!-- header -->
</head>
<body>
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_account.ftl">

<div class="mainBox">
	<!--面包屑-->
<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="#">资金管理</a></li>
			<li><a href="#">放款管理</a></li>
            <li><a href="#">放款记录</a></li>
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <form id="listForm" method="post" action="${base}/agency_pt/cashList.do">
    <input type="hidden" id="cashId" name="cashList.id" value="0"/>
    <div class="search shadowBread">
    	
    
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <td>项目编号</td>
				<td>项目类型</td>
        		<td>借款人</td>
				<td>借款人姓名</td>
				<td>本次放款金额<#--提现金额--></td>
				<td>本次到账金额<#--提现金额--></td>
				<td>手续费<#--累计放款金额--></td>
				<td>放款状态</td>
				<td>操作</td>
            </tr>
          </thead>
          <tbody>
          <#list pager.result as ac>
              <tr>
                <td>${ac.businessCode}</td>
                <td>${ac.btype}</td>
                <td>${ac.username}</td>
                <td>${ac.realName}</td>
                <td>${(ac.total?string.currency)!'￥0.00'}元</td>
                <td>${(ac.credited?string.currency)!'￥0.00'}元</td>
				<td>${(ac.fee?string.currency)!'￥0.00'}元</td>
				<td>${ac.fkStatus}</td>
				<td>
					<a href = "javascript:void(0);" class = "kaqu_huankuanzhong  cash_a_view" acid = "${ac.id}">【查看】</a>
					<#if ac.status ==0>
						<a href = "javascript:void(0);" class = " kaqu_huankuanzhong  cash_a_recall" acid = "${ac.id}">【撤回】 </a>
					</#if>
				</td>
              </tr> 
           </#list>         
          </tbody>
          <tfoot>
              <tr>
                <td colspan="9">
					<#if pager.totalCount==0>
						<div class="nodata">放款记录为空</div>
					</#if>
					<@pageFlip pager=pager>
						<#include "/content/common/pager.ftl">
					</@pageFlip>
                </td>
              </tr>
          </tfoot>
        </table>
        <!--table end!--> 
        </from>              
    </div>
</div>
	<#include "/content/common/footer.ftl">
<script type="text/javascript">
	$(function(){

		$(".lssj").attr("id","lssj");
		$("#li_a_fkjl").addClass("li_a_select");
	
		$(".cash_a_recall").click(function(){
			//$(this).addClass("kaqu_huankuanzhong1");
			//$(this).after('<span class="kaqu_xuanzhuan kaqu_xuanzhuan1"></span>');
			if (confirm("确认要撤回此条记录吗？")) {
				
				var testTime = new Date().getTime();
				var $this = $(this);
				var $acid = $this.attr('acid');
				$.ajax({
			        type: "post",
			        dataType : "json",
			        url: 'ajaxRecallCashInfo.do?accountCash.id='+$acid+'&testTime='+testTime,
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
	

		// 查看理财产品投资详情操作
		$(".cash_a_view").click(function(){
				var testTime = new Date().getTime();
				var $this = $(this);
				var $acid = $this.attr('acid');
	        	KP.options.drag = true;
				KP.show("查看放款详情", 540, 540);
				$(KP.options.content).load(qmd.base+"/agency_pt/poputView.do?accountCash.id="+$acid+"&testTime="+testTime);
		});
	
	});
</script>
</body>
</html>