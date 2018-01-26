<!DOCTYPE html>
<html>
 <head>
	<title>${Application ["qmd.setting.name"]}-借款管理</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>	
	<script type="text/javascript">
	$().ready( function() {	
		$amendBank = $(".amendBank");
		$deleteBank = $(".deleteBank");
		$amendBank.click(function(){
			$this=$(this);
			//$.dialog({type: "warn", content: "确认要撤回此标吗？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			//function right(){
			
			if (!confirm("确认要撤回此标吗？")) {
				return;
			}
			var d = new Date().getTime();
				$.ajax({
					url: "${base}/borrow/ajaxBorrowRecall.do",
					data: {"borrow.id":$this.attr("borrowid"),"d":d},
					type: "POST",
					dataType: "json",
					cache: false,
					success: function(data) {
						//$.message({type: data.status, content: data.message});
						if(data.status=="success"){
							//$this.parent().parent().remove();
							alert("撤回成功！");
							window.location.reload();
						} else {
							alert(data.message);
							return;
						}
					}
				});
			//}
		});
		
		$deleteBank.click(function(){
			$this=$(this);
			//$.dialog({type: "warn", content: "确认要删除此标吗？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			if (!confirm("确认要删除此标吗？")) {
				return;
			}
			//function right(){
			var d = new Date().getTime();
				$.ajax({
					url: "${base}/borrow/ajaxDelectBorrow.do",
					data: {"borrow.id":$this.attr("borrowid"),"d":d},
					type: "POST",
					dataType: "json",
					cache: false,
					success: function(data) {
						//$.message({type: data.status, content: data.message});
						if(data.status=="success"){
							//$this.parent().parent().remove();
							alert("删除成功！");
							window.location.reload();
						} else {
							alert(data.message);
							return;
						}
					}
				});
			//}
		});
	});
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
			<li><a href="${base}/borrow/userBorrowMgmt.do">项目管理管理</a></li>
           
			
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <div class="search shadowBread">		
        <form id="listForm" method="post" action="${base}/borrow/userBorrowMgmt.do" >
        <input type="hidden" name="bty" id="bty" value="${bty}" />
        	<span class="block">发布时间：</span>
            <span class="block">
            	<input type="text" id = "minDate" name="minDate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'maxDate\')}'})" value="${minDate}" class="searchText">
               <span class="block">&nbsp;&nbsp;到&nbsp;&nbsp;</span> 
				 <input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'minDate\')}'})" value="${maxDate}" class="searchText">
				  <span class="block">&nbsp;&nbsp;</span> 
			<#if bty==0>
				<select id="borrStatus" name="borrStatus">
					<option value="111" >所有状态</a>
		            <@listing_childlist sign="borrow_type"; listingList>
						<#list listingList as listing>
							<option value="${listing.keyValue}" <#if borrStatus==listing.keyValue>selected</#if> >${listing.name}</a>
							</option>
						</#list>
					</@listing_childlist>
				</select>
			</#if>
				  <span class="block">&nbsp;&nbsp;</span> 
				  <input type="button" onclick="gotoPage(1);" value = "搜索"/>
            </span> 
                              
       
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
                
               <#-- <td width="19"><input type="checkbox"></td>-->
                <td>标题</td>
				<td>类型</td>
				<td>业务类型</td>
				<td>金额</td>
				<td>期限</td>
				<td>利率</td>
				<td>年利率</td>
				<td>奖励</td>
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
				<tr >
					<#--<td><input type="checkbox"></td>-->
					<td class="${flag}"><a href="${Application ["qmd.setting.memberUrl"]}/borrow/detail.do?bId=${borrow.id}" target="_blank" title="${borrow.name}">${substring(borrow.name, 16, "")}</a></td> 
					<td class="${flag}"><#if borrow.type==0>秒标<#elseif borrow.type==14>天标<#elseif borrow.type=16>新手标
									<#elseif borrow.type=17>体验标<#elseif borrow.type==2>流转标
						<#elseif borrow.type==3>信用标<#elseif borrow.type==15>月标</#if>
<#--						${borrow.showBorrowType} -->
					</td>
					<td class="${flag}">
						<@listing_childlist sign="borrow_business_type"; listingList>
							<#list listingList as listing>
								<#if borrow.isVouch ==listing.keyValue>
								${listing.name}
								</#if>
							</#list>
						</@listing_childlist>
					</td>
				<td class="${flag}">￥${(borrow.account)}</td>
				<td class="${flag}">${borrow.timeLimit}<#if borrow.type==4>月<#else>天</#if></td>
				<td class="${flag}"><#if borrow.type==4>
						${borrow.apr}%/年
					<#else>
						${borrow.apr}‰/天 
					</#if>
				</td>
				<td class="${flag}">
					<@numberPercent val="${borrow.varyYearRate}"; npt>${npt}</@numberPercent>
				</td>
				<td class="${flag}">
					${(borrow.funds)!}<#if borrow.funds>%<#else>无</#if>
				</td>
				<td class="${flag}">
					<@listing_childname sign="borrow_type" key_value="${borrow.status}"; type>
						${type}
					</@listing_childname></td>
				<td class="${flag}">
					<#if borrow.status ==0>
						   		<a href="javascript:void(0);" class="amendBank" borrowid="${borrow.id}">撤回</a><#-->&nbsp;|&nbsp;<a href = "${base}/borrow/borrowMessage.do?borrow.id=${borrow.id}">编辑</a>-->
					<#elseif borrow.status ==1>
						   		---	
					<#elseif borrow.status ==2>
						   		--
					<#elseif borrow.status ==3>
						   		--
					<#elseif borrow.status ==4>
						   		--
					<#elseif borrow.status ==5>
						   		--
					<#elseif borrow.status ==6>
						   		<a href="javascript:void(1);" class="deleteBank" borrowid="${borrow.id}">删除</a>
					<#elseif borrow.status ==7>
						   		--
					</#if>
<!--<a href="${base}/borrow/borrowInputWork.do?id=${borrow.id}">编辑</a>-->				
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
	$(".sssj").attr("id","sssj");
	$("#li_a_jkgl").addClass("li_a_select");
	
});
</script>
</html>
