<!DOCTYPE html>
<html>
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-借款管理</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>	
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
            <a href="" style="color:#646464;font-family:'宋体';">我的账户</a>
            <a style="color:#646464;font-family:'宋体'; padding:0px 4px;">></a>
            <a href="" style="color:#646464;font-family:'宋体';">项目管理</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            <a href="" class="hr hre">项目管理</a>
          </div>
          <form id="listForm" method="post" action="userBorrowMgmt.do" >
          <div style="font-family:'宋体'; color:#000; margin-top:80px; clear:both;">
				发布时间：<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${minDate}"style="width:80px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;">
						  	 到
							 <input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${maxDate}" style="width:80px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;">
					 		关键字 <input type="text" name="keywords" value="${keywords}" style="width:80px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/> 
							<select id="borrStatus" style="width:130px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" name="borrStatus">
								<option value="111" >所有状态</a>
					            <@listing_childlist sign="borrow_type"; listingList>
									<#list listingList as listing>
										<option value="${listing.keyValue}" <#if borrStatus==listing.keyValue>selected</#if> >${listing.name}</a>
										</option>
									</#list>
								</@listing_childlist>
							</select>
							<input type="button" onclick="gotoPage(1);" style="display:inline-block; width:65px; height:35px; line-height:35px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" value = "搜索"/>
			</div>          
            <div style=" margin-top:10px;">
               <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
					<thead bgcolor="#efebdf" align="center">
						<tr height="39">							
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">标题</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">类型</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">金额</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">利率</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">期限</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">发布时间</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">状态</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">操作</th>			
						</tr>
					</thead>
					<tbody align="center">
				
						<#list pager.result as borrow>
									<tr height="50">
												<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><a href="${base}/borrow/detail.do?bId=${borrow.id}" target="_blank" title="${borrow.name}">${substring(borrow.name, 16, "")}</a></td> 
												<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><#--<#if borrow.type==0>秒标<#elseif borrow.type==1>天标<#elseif borrow.type==2>流转标
													<#elseif borrow.type==3>信用标<#elseif borrow.type==4>月标</#if>
													${borrow.showBorrowType}
													<#if borrow.type==0>秒标<#elseif borrow.type==1>天标<#elseif borrow.type==2>流转标
													<#elseif borrow.type==3>信用标<#elseif borrow.type==4>月标
													<#elseif borrow.type==5>流转标<#elseif borrow.type==6>月标</#if>		
													-->										
													${borrow.showBorrowType}
													</td>
											<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">￥${(borrow.account)}</td>
											<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><#if borrow.type==4 || borrow.type==6>
													${borrow.apr}%/年
												<#elseif borrow.type==5>
													${borrow.apr}‰/天
												</#if>
											</td>
											<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${borrow.timeLimit}<#if borrow.type==4 || borrow.type==6>个月<#else>天</#if></td>
											<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
												<#if borrow.createDate >${borrow.createDate?string("yyyy-MM-dd HH:mm:ss")}
												<#else>${borrow.createDate}
												</#if></td>
											<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
												<@listing_childname sign="borrow_type" key_value="${borrow.status}"; type>
													${type}
												</@listing_childname></td>
											<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
												<#if borrow.status ==0>
													   		<a href="javascript:void(0);" class="amendBank" borrowid="${borrow.id}">撤回</a><#-->&nbsp;|&nbsp;<a href = "${base}/borrow/borrowMessage.do?borrow.id=${borrow.id}">编辑</a>-->
												<#elseif borrow.status ==1>
													   			--
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
												<a href="${base}/borrow/borrowMessage.do?borrow.id=${borrow.id}">编辑</a>
											</td>
										</tr>
										</#list>
					</tbody>
				</table>
				<#if (pager.result?size < 1)>
						
						<div ><span style=" font-size:14px;">没有找到任何记录!</span></div>
					<#else>
					<@pagination pager=pager baseUrl="/borrow/userBorrowMgmt.do" parameterMap = parameterMap>
						<#include "/content/pager.ftl">
					</@pagination>
				</#if>
				</form>
            </div>
       </div>

	<#-- 		右边内容块 结束				-->
	
	</div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->

 <!--footer-->
<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
$(function(){
	$("#sp_header_my").addClass("hq");
	$("#borrow_manage").addClass("nsg_a1");
});
</script>
</html>
