
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${Application ["qmd.setting.name"]}-客户管理-推广人员</title>
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
			<li><a href="#">推广人员</a></li>
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
     <form id="listForm" method="post" action="${base}/spread/spreadTg.do">
    <div class="search shadowBread">		
        	<span class="block">类型：</span>
            <span class="block">
        		  <select name = "selectType">
					<option value = "">请选择</option>
					<option value = "0" <#if selectType! && selectType =="0">selected</#if>>用户名</option>
					<option value = "1" <#if selectType! && selectType =="1">selected</#if>>姓名</option>
					<option value = "2" <#if selectType! && selectType =="2">selected</#if>>手机</option>
				  </select>
				  <input type = "text" name = "keywords" value = "${(keywords)!}" class = "kaqu_w100" style = "width :175px">
				<input type="button" id = "searchButton" class = "kaqu_w120" value = "搜索"/>
            </span>    
		<a id="btnadd" class="l-btn l-btn-plain fl" href="${base}/spread/spreadTgInput.do">
			<span class="l-btn-tool"><span class="l-btn-text pic-add" style="padding-left:20px;">添加</span></span>
		</a>   
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <td>推广人员用户名</td>
        		<td>姓名</td>
        		<td>手机</td>
    			<td>会员卡（张）</td>
        		<td>好友链接</td>
        		<td>状态</td>
        		<td>操作</td>
            </tr>
          </thead>
          <tbody>
          <#list pager.result as spreadTg>
              <tr>
                <td>${spreadTg.uusername}</td>
		  		<td>${spreadTg.fullName}</td>
	  			<td>${spreadTg.mobile}</td>
				<td>${spreadTg.cards}</td>
				<td>
					<#if spreadTg.utgno !=''>
						<a href = "${Application ["qmd.setting.url"]}/fd/${spreadTg.utgno}.htm" target = "_blank">${Application ["qmd.setting.url"]}/fd/${spreadTg.utgno}.htm</a>
					<#else>
						<font color = "red">没有设置邀请链接</font>
					</#if>
				</td>
		  		<td>${spreadTg.tgStatus}</td>
				<td>
					<#if spreadTg.statusCode ==1>
						<a class = "xfClass" sid = "${spreadTg.id}"  href = "javascript:void(0);">【发卡】</a>
						<a href = "${base}/spread/customUserList.do?user.spreadMemberId=${spreadTg.id}">【客户列表】</a>
						<a class = "delClass" sid = "${spreadTg.id}" href = "javascript:void(0);">【离职】</a>
					</#if>
				</td>
              </tr> 
           </#list>         
          </tbody>
          <tfoot>
              <tr>
                <td colspan="9">
					<#if pager.totalCount==0>
						<div class="nodata">推广人员记录为空</div>
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
			$("#li_a_tgry").addClass("li_a_select");
		
	<#--离职-->
	$(".delClass").click(function(){
		if (confirm("此推广人员已经 “离职” 吗？")) {
			var testTime = new Date().getTime();
			var $this = $(this);
			var $sid = $this.attr('sid');
			$.ajax({
		        type: "post",
		        dataType : "json",
		        url: 'ajaxSpreadDelete.do?id='+$sid+'&testTime='+testTime,
		        success: function(data, textStatus){
		        	if(typeof(data.errorMsg) == "undefined" || typeof(data.errorMsg) == "null" || data.errorMsg == null || data.errorMsg == ""){
		        		alert("操作失败");
						window.location.reload();
		        	} else if (data.errorMsg=="OK") {
		        		alert("操作成功");
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
	
	
function cardTypeSelectChange(obj){
	var testTime = new Date().getTime();
	$.ajax({
	        type: "post",
	        dataType : "json",
	        url: qmd.base+'/spread/ajaxSelectCardType.do?clubCard.type='+obj.value+'&testTime='+testTime,
	        success: function(data, textStatus){
	      	    var selHTML = '';
	      	    if(data.status == 'success'){
	        	$.each(data.clubCardList, function(i) {
                 	 selHTML +='<label style="display:inline-block;width:75px;margin-bottom:10px;"><input type = "checkbox" name = "idss" value = "'+data.clubCardList[i].id+'"" checked/>'+data.clubCardList[i].cardNo+'</label>&nbsp;&nbsp;&nbsp;';
                 });
                } 
				$("#cardsTd").html(selHTML);
				$("#cardNumSpanId").html('【共'+ data.cardNums +'张】');
	        },
	        error:function(statusText){
	        	alert(statusText);
	        },
	        exception:function(){
	        	alert(statusText);
	        }
		});
}

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