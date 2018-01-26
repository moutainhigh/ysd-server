
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
    <form id="listForm" method="post" action="${base}/fangkuan/fangkuanList.do">
     <div class="search shadowBread">		
        	<span class="block">
        		<select class="kaqu_w100" name="searchBy">
	               <option value="name" <#if searchBy?exists && searchBy == "name"> selected="selected"</#if> >放款项目</option>
	               <option value="borrowerRealName" <#if searchBy?exists && searchBy == "borrowerRealName"> selected="selected"</#if> >借款人</option>
				</select>
        	
        	</span>
            <span class="block" style="margin-left:10px;">
        			&nbsp;&nbsp;<input type="text" name="keywords" value="${keywords}" class="searchText" style="height:18px;"/>&nbsp;
				
				<select class="kaqu_w100" name="timeBy">
	               <option value="sq" <#if searchBy?exists && searchBy == "sq"> selected="selected"</#if> >放款申请时间</option>
	               <option value="sh" <#if searchBy?exists && searchBy == "sh"> selected="selected"</#if> >放款审核时间</option>
				</select>
				
				<input type="text" id = "minDate" name="minDate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'maxDate\')}'})" value="${(minDate)!}" class="kaqu_w100" style = "width :80px">
				   &nbsp;&nbsp;到&nbsp;&nbsp;
				 <input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'minDate\')}'})" value="${(maxDate)!}" class="kaqu_w100" style = "width :80px">
				<#--排序 -->
				状态：
				<select class="text_sketch" id="kq_select_order_id" name = "status" style="">
		          <option value="2" <#if status?exists && status== 2> selected </#if>>审核中</option>
		          <option value="1" <#if status?exists && status== 1> selected </#if>>放款成功</option>
		          <option value="0" <#if status?exists && status== 0> selected </#if> >放款失败</option>
		        </select>
		        
		        <input type="button" id = "searchButton" class = "kaqu_w120" value = "搜索"/>&nbsp;&nbsp;&nbsp;
                
            </span>    
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <td>放款申请时间</td>
				<td>放款审核时间</td>
        		<td>放款项目</td>
				<td>借款人</td>
				<td>放款金额</td>
				<td>放款账号</td>
				<td>开户银行</td>
				<td>状态</td>
				<td>操作</td>
            </tr>
          </thead>
          <tbody>
          <#list pager.result as fk>
              <tr>
                <td>${fk.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
                <td>
                	 <#if fk.status ==1>
	              	 	${(fk.verifyTime?string("yyyy-MM-dd HH:mm:ss"))!'--'}
	              	 <#else>
	              	 	--
	              	 </#if>
                </td>
                <td>${fk.name}</td>
                <td>${fk.borrowerRealName}</td>
                <td>${(fk.fangkuanMoney?string.currency)!'￥0.00'}元</td>
                <td>${fk.bankCard}</td>
				<td>${fk.bankBranch}</td>
				<td>${fk.showStatus}</td>
				<td>
					<#if fk.status ==0>
						<a href = "javascript:void(0);" class = "kaqu_huankuanzhong  a_view" id = "${fk.id}">【详情】</a>
					<#elseif fk.status ==2>
						<a href = "javascript:void(0);" class = " kaqu_huankuanzhong  a_recall" id = "${fk.id}">【撤回】 </a>
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
	
		$(".a_recall").click(function(){
			if (confirm("确认要撤回此条记录吗？")) {
				
				var testTime = new Date().getTime();
				var $this = $(this);
				var $id = $this.attr('id');
				$.ajax({
			        type: "post",
			        dataType : "json",
			        url: qmd.base+'/fangkuan/ajaxRecall.do?id='+$id+'&testTime='+testTime,
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
	

		// 查看放款详情操作
		$(".a_view").click(function(){
				var testTime = new Date().getTime();
				var $this = $(this);
				var $id = $this.attr('id');
	        	KP.options.drag = true;
				KP.show("查看放款详情", 380, 250);
				$(KP.options.content).load(qmd.base+"/fangkuan/poputFangkuanView.do?id="+$id+"&testTime="+testTime);
		});
	
	});
</script>
</body>
</html>