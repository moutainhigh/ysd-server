<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>${Application ["qmd.setting.name"]}-合同处理</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${base}/js/common/ajaxfileupload_ht.js"></script>
	<link href="${base}/css/css.css" rel="stylesheet" type="text/css" />
	
</head>

<script type="text/javascript">
	var idx 

	function closeKP(){
		$("#KP").hide();
	}
	function mc(id){
		idx = id;
		if(document.all){
			<#--$("#KP").css.display("block");-->
			$("#KP").show();
		}else{ 
			$("#file").click();
		}
		
	}
	function mm(e){
		<#--var fileName =  document.getElementById("file").files[0].name;-->
		var src=e.target || window.event.srcElement;
		var fileName=src.value.substring( src.value.lastIndexOf('\\')+1);
		if (confirm("确认上传"+fileName+"文件？")) {
			$.ajaxFileUpload({
					url: "${base}/contractBorrow/upload.do?fileName="+fileName+"&contractBorrowId="+idx,
					secureuri : false,
					fileElementId:'file',
					dataType: "json",
					success: function(data) {
						data = eval("("+data+")")
						alert(data.message);
						if(data.status=="success"){
							self.location.reload();
						}
					},
					error:function(data){
						data = eval("("+data+")")
						alert(data.error);
					}
				});
		}
		
	}

</script>
<body class="body_bg">
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_agency.ftl">


<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="${base}/userCenter/index.do">账户中心</a></li>
            <li><a href="#">合同管理</a></li>
            <li><a href="#">处理合同</a></li>
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <div class="search shadowBread">		
        <form id="listForm" method="post" action="${base}/contractBorrow/contractBorrowProcessList.do">
	       <!--tenderbox--> 
	        <div class="KP" id="KP" style="width: 400px; height: 50px; top: 470px; left: 700px; display:none;">
				<div class="bd" id="KPC" style="height:45px">
					<table style=" margin-top:10px; margin-left:40px;" align="center" width="80%">
						<tr>
							<td><input type="file" id="file" name="file" onchange="mm(event);display: none;"/></td>
							<td><input type="button"/ value="取消" onclick="closeKP();"></td>
						</tr>
						
					</table>
				</div>
			</div>
			<!--tenderbox end--> 
        	<span class="block">关键字：</span>
            <span class="block">
	            <select id="borrStatus" class="kaqu_w110" name="selectType">
					<option value="">请选择</a>
					<option value="1" <#if selectType==1>selected</#if> >所属项目名称
					<option value="2" <#if selectType==2>selected</#if> >所属项目编号
					<option value="3" <#if selectType==3>selected</#if> >合同名称
					</option>
				</select>
            	<input class="searchText" name="keywords" value="${keywords}" type="text" />
            </span>                   
        	<input type="button" onclick="gotoPage(1);" class = "kaqu_w120" value = "搜索"/>
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                
                <#--<td width="19"><input type="checkbox"></td>-->
                <td>项目名称</td>
        		<td>合同名称</td>
				<td>投资人用户名</td>
				<td>投资金额</td>
				<td>期限</td>
				<td>利率</td>
				<td>上传时间</td>
				<td>状态</td>
				<td>操作</td>
            </tr>
          </thead>
          <tbody>
              <#list pager.result as item>
                	<#assign flag = "">
					<#if item_index %2 != 0>
						<#assign flag = "td1">
					</#if>
					<tr height="39">
						<#--<td class = "${flag}">${(item.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</td>-->
						<#--<td class = "${flag}"><a target="_blank" href="${base}/contract/contractDetail.do?id=${item.id}" >查看详情</a></td>-->
						<#-- <td><input type="checkbox"></td>-->
						<td class = "${flag}">${item.name}</td>
						<td class = "${flag}">${item.contractTitle}</td>
						<td class = "${flag}">${item.username}</td>
						<td class = "${flag}">￥${item.account}</td>
						<td class = "${flag}">${item.timeLimit}</td>
						<td class = "${flag}">${item.investApr}%</td>
						<td class = "${flag}"><#if item.statusSign==1>${(item.modifyDate?string("yyyy-MM-dd HH:mm:ss"))!}<#else>-</#if> </td>
						<td class = "${flag}"><#if item.statusSign==1><span style="color:green">已签</span><#else><span style="color:red">未签</span></#if></td>
						<td class = "${flag}">
							<#if item.statusSign==1>
								<a target="_blank" href="${base}/contractBorrow/showPdf.do?contractBorrowId=${item.id}" >【查看】</a>
								<a target="_blank" onclick="mc(${item.id});" >【重新上传】</a>
								
							<#else>
								<a target="_blank" href="${base}/contractBorrow/download.do?contractBorrowId=${item.id}" >【下载】</a>
								<a target="_blank" onclick="mc(${item.id});" >【上传】</a>
								<#-- href="${base}/contractBorrow/upload.do?typeId=${item.id}"  -->
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
        <!--table end!-->               
    </div>
    </form>
</div>
	<#include "/content/common/footer.ftl">
</body>
<script>
	$().ready( function() { 
		$(".sssj").attr("id","lssj");
		$("#li_a_htcl").addClass("li_a_select");
	});
</script>
</html>
