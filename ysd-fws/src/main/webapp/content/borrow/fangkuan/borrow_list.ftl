
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-资金管理-放款管理-项目放款</title>
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
            <li><a href="#">项目放款</a></li>
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <form id="listForm" method="post" action="${base}/fangkuan/borrowList.do">
    <div class="search shadowBread">
    	<span class="block">
    		<select class="kaqu_w100" name="searchBy">
               <option value="name" <#if searchBy?exists && searchBy == "name"> selected="selected"</#if> >项目标题</option>
               <option value="borrowerRealName" <#if searchBy?exists && searchBy == "borrowerRealName"> selected="selected"</#if> >借款人</option>
			</select>
		</span>
		<span class="block" style="margin-left:10px;">
    		&nbsp;&nbsp;<input type="text" name="keywords" value="${keywords}" class="searchText" style="height:18px;"/>&nbsp;
		</span>
             <input type="button" onclick="gotoPage(1);" class = "kaqu_w120" value = "搜索"/>
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <td>项目标题</td>
        		<td>借款人</td>
				<td>项目起息日</td>
				<td>项目金额</td>
				<td>项目余额</td>
				<td>服务费</td>
				<td>保证金</td>
				<td>操作</td>
            </tr>
          </thead>
          <tbody>
          <#list pager.result as fk>
              <tr>
                <td>${fk.name}</td>
                <td>${fk.borrowerRealName}</td>
                <td>${fk.firstRepaymentTime?string("yyyy-MM-dd")}</td>
                <td>${(fk.borrowTotal?string.currency)!'￥0.00'}</td>
                <td>${(fk.borrowMoney?string.currency)!'￥0.00'}元</td>
                <#if fk.feeType == 1>
              	  <td>${(fk.feeMoney?string.currency)!'￥0.00'}元</td>
                <#else>
                  <td>${((fk.borrowTotal*fk.partAccount/100)?string.currency)!'￥0.00'}元</td>
                </#if>
                
				<td>${(fk.depositMoney?string.currency)!'￥0.00'}元</td>
				<td>
					<a href = "javascript:void(0);" class = "kaqu_huankuanzhong  fk_a_view" bid = "${fk.id}">【申请放款】</a>
				</td>
              </tr> 
           </#list>         
          </tbody>
          <tfoot>
              <tr>
                <td colspan="9">
					<#if pager.totalCount==0>
						<div class="nodata">放款项目记录为空</div>
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
		$("#li_a_zqzrfk").addClass("li_a_select");
	

		// 查看理财产品投资详情操作
		$(".fk_a_view").click(function(){
				var testTime = new Date().getTime();
				var $this = $(this);
				var $bid = $this.attr('bid');
	        	KP.options.drag = true;
				KP.show("申请放款", 400, 540);
				$(KP.options.content).load(qmd.base+"/fangkuan/poputAddFangkuanView.do?id="+$bid+"&testTime="+testTime);
		});
	
	});
	
	
function ajaxAddFangkuan() {

	if($("#paypwdId").val()=='') {
		alert("请输入安全密码！");
		return;
	}
	
	if(!$("#agreeCheckbox")[0].checked){
		alert('请选择《打款协议》');
		return;
	}
	$.ajax({
        type: "post",
        dataType : "json",
        data: $('#fangkuanForm').serialize(),
        url: '${base}/fangkuan/ajaxAddFangkuan.do',
        success: function(data, textStatus){
        	if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
        		alert("放款失败！");
        	} else if (data.status=="success") {
        		alert("放款成功");
        		window.location.reload();
        	} else {
	        	alert(data.message);
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
</body>
</html>