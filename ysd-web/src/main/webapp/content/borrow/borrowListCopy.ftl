<!DOCTYPE html>
<html>
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-续标管理</title>
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
            <a style="color:#646464;font-family:'宋体';">续标管理</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            <a href="" class="hr hre">续标管理</a>
          </div>
          <form id="listForm" method="post" action="userBorrowMgmt.do" >
          <div style="font-family:'宋体'; color:#000; margin-top:80px; clear:both;">
		 		关键字 <input type="text" name="keywords" value="${keywords}" style="width:150px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/> 
				<input type="button" onclick="gotoPage(1);" style="display:inline-block; width:65px; height:35px; line-height:35px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" value = "搜索"/>
			</div>          
            <div style=" margin-top:10px;">
               <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
					<thead bgcolor="#efebdf" align="center">
						<tr height="39">							
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">标题</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">状态</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">项目总额</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">已发总额</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">待发总额</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">未发总额</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">操作</th>			
						</tr>
					</thead>
					<tbody align="center">
						<#list pager.result as borrow>
							<tr height="50">
								<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									<a href="${base}/borrow/detail.do?bId=${borrow.id}" target="_blank" title="${borrow.name}">${substring(borrow.name, 16, "")}</a>
								</td> 
								<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									<#if (borrow.showChildMoneyWait>0)>续标中<#else>已续完</#if>
								</td>
								<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">￥${borrow.parentMoney}</td>
								<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">￥${borrow.childMoneyFinish}</td>
								<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">￥${borrow.childMoneyReady}</td>
								<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">￥${borrow.showChildMoneyWait}</td>
								<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									<#if (borrow.showChildMoneyWait>0)>
										<a href="${base}/borrow/inputBorrowPromoteCopy.do?borrowId=${borrow.id}">续标</a>
									</#if>
									<a href="${base}/borrow/borrowRepayList.do?borrow.id=${borrow.id}">查看详情</a>	
								</td>
							</tr>
						</#list>
					</tbody>
				</table>
					<@pagination pager=pager >
					<#include "/content/pager.ftl">
				</@pagination>
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
