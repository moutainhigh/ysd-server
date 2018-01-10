
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-客户管理-推广渠道</title>
	<#include "/content/common/meta.ftl">
 <!-- header -->
</head>
<body>
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_customer.ftl">
	
<div class="mainBox">
	<!--面包屑-->
<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="#">客户管理</a></li>
			<li><a href="#">推广渠道</a></li>
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <form id="listForm" method="post" action="${base}/spread/spreadMemberList.do">
    <div class="search shadowBread">		
        	<span class="block">名称：</span>
            <span class="block">
        		<input type = "text" name = "spreadMember.fullName" value = "${(spreadMember.fullName)!}" class = "kaqu_w100" style = "width :175px">
				<input type="button" id = "searchButton" class = "kaqu_w120" value = "搜索"/>
            </span>  
		<a id="btnadd" class="l-btn l-btn-plain fl" href="${base}/spread/inputSpread.do">
			<span class="l-btn-tool"><span class="l-btn-text pic-add" style="padding-left:20px;">添加</span></span>
		</a>  
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <td>邀请码</td>
        		<td>名称</td>
        		<td>手机</td>
        		<td>发展会员数</td>
        		<td>邀请链接</td>
        		<td>操作</td>
            </tr>
          </thead>
          <tbody>
      <#list pager.result as spread>
              <tr>
                <td>${spread.spreadNo}</td>
		  		<td>${spread.fullName}（<#if spread.sex =='' || spread.sex ==1>个人<#else>企业</#if>）</td>
	  			<td>${spread.mobile}</td>
		  		<td>${spread.usernums}</td>
		  		<td>
					<#if spread.spreadNo !=''>
						<a href = "${Application ["qmd.setting.url"]}/q/${spread.spreadNo}.htm" target = "_blank">${Application ["qmd.setting.url"]}/q/${spread.spreadNo}.htm</a>
					<#else>
						<font color = "red">没有设置邀请链接</font>
					</#if>
				</td>
		  		<td>
			  		<#if spread.statusCode ==1 >
			  			<a href="${base}/spread/inputSpread.do?id=${spread.id}">编辑</a>&nbsp;&nbsp;
			  			<a class = "delClass" sid = "${spread.id}" href = "javascript:void(0);">删除</a>&nbsp;&nbsp;
			  			<a class = "" href = "${base}/spread/customUserList.do?selectType=0&user.spreadMemberId=${spread.id}">客户列表</a>
			  		<#else>
			  			已删除
			  		</#if>
		  		</td>
              </tr> 
           </#list>         
          </tbody>
          <tfoot>
              <tr>
                <td colspan="9">
					<#if pager.totalCount==0>
						<div class="nodata">推广渠道记录为空</div>
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
	<script>
		$().ready( function() { 
			$(".tjxx").attr("id","tjxx");
			$("#li_a_tgqd").addClass("li_a_select");
			
			<#--删除-->
			$(".delClass").click(function(){
				if (confirm("确认要删除此推广渠道吗？")) {
					var testTime = new Date().getTime();
					var $this = $(this);
					var $sid = $this.attr('sid');
					$.ajax({
				        type: "post",
				        dataType : "json",
				        url: qmd.base+'/ajaxSpreadDelete.do?id='+$sid+'&testTime='+testTime,
				        success: function(data, textStatus){
				        	if(typeof(data.errorMsg) == "undefined" || typeof(data.errorMsg) == "null" || data.errorMsg == null || data.errorMsg == ""){
				        		alert("操作失败");
								window.location.reload();
				        	} else if (data.errorMsg=="OK") {
				        		alert("删除成功");
				        		KP.close();
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
			
			//下发会员卡
			$(".xfClass").click(function(){
				var testTime = new Date().getTime();
				var $this = $(this);
				var $sid = $this.attr('sid');
				KP.options.drag = true;
				KP.show("下发会员卡", 540, 540);
				$(KP.options.content).load(qmd.base+'/spread/selectClubCard.do?spreadMember.id='+$sid+'&testTime='+testTime);
			
			});
		});
		
		
		//弹出下发会员卡，点击确定
		function xfFinish(){
			var testTime = new Date().getTime(); 
			var $idsClubChecked = $(".clubCardTable input[name='idss']:checked");//会员卡复选框
			if ($idsClubChecked.size() <= 0) {
				alert('请选择卡号！');
				return false;
			}
			if($("#spreadMemberId").val()==''){
				alert('参数错误！');
				return false;
			}
			$.ajax({
		        type: "post",
		        dataType : "json",
		        data: $idsClubChecked.serialize(),
		        url: qmd.base+'/spread/ajaxSaveClubCard.do?spreadMember.id='+$("#spreadMemberId").val()+'&testTime='+testTime,
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
</body>
</html>