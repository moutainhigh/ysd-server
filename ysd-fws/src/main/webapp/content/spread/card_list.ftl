
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-客户管理-推广人员-会员卡管理</title>
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
			<li><a href="#">会员卡管理</a></li>
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
  <form id="listForm" method="post" action="${base}/spread/clubCardList.do">
    <div class="search shadowBread">		
        	<span class="block">卡号：</span>
            <span class="block">
            	<input type = "text" name = "clubCard.cardNo" value = "${(clubCard.cardNo)!}" class = "kaqu_w100" style = "width :175px">
            	激活状态：
        		 <select name = "clubCard.status">
					<option value = "1" <#if (clubCard.status)! == 1> selected</#if> >未激活</option>
					<option value = "2" <#if (clubCard.status)! == 2> selected</#if> >已激活</option>
				  </select>
				<input type="button" id = "searchButton" class = "kaqu_w120" value = "搜索"/>
            </span> 
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
        <table id="listTable" class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
       			<td width="19"><input type="checkbox" class="allCheck_club"></td>
        		<td>卡号</td>
        		<td>类型</td>
        		<td>推广人</td>
        		<td>状态</td>
            </tr>
          </thead>
          <tbody>
          <#list pager.result as card>
              <tr>
                <td><#if card.status == 1 >	<input type="checkbox" name="idss" value="${card.id}" /></#if></td>
                <td>${card.cardNo}</td>
		  		<td>${card.btype}</td>
	  			<td>${card.fullName}</td>
		  		<td>${card.bstatus}</td>
              </tr> 
           </#list>   
           <#if pager.totalCount &gt; 0 > 
          	<tr>
          		<td>
           			<input type="button" id="zyButton"  url="club_card!turnMove.action" value="转 移"/>
           		</td>
                <td colspan="9"></td>
           	</tr>
           </#if>      
          </tbody>
          <tfoot>
              <tr>
                <td colspan="9">
					<#if pager.totalCount==0>
						<div class="nodata">会员卡记录为空</div>
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
			$("#li_a_hykgl").addClass("li_a_select");
			
			var $zyButton = $("#zyButton");//转移
	
		var $allCheckClub = $("#listTable input.allCheck_club");// 全选复选框
	
		var $idssClubCheck = $("#listTable input[name='idss']");// ID复选框
	
		
		// 全选
		$allCheckClub.click( function() {
			var $this = $(this);
			if ($this.attr("checked")) {
				$idssClubCheck.attr("checked", true);
				$zyButton.attr("disabled", false);
			} else {
				$idssClubCheck.attr("checked", false);
				$zyButton.attr("disabled", true);
			}
		});
		
		// 无复选框被选中时,删除按钮不可用
		$idssClubCheck.click( function() {
			var $this = $(this);
			if ($this.attr("checked")) {
				$this.parent().parent().addClass("checked");
					$zyButton.attr("disabled", false);
			} else {
				$this.parent().parent().removeClass("checked");
				var $idssClubChecked = $("#listTable input[name='idss']:checked");
				if ($idssClubChecked.size() > 0) {
					$zyButton.attr("disabled", false);
				} else {
					$zyButton.attr("disabled", true);
				}
			}
		});
	
	// 批量转移
		$zyButton.click(function(){
			var testTime = new Date().getTime();
			KP.options.drag = true;
			KP.show("转移会员卡", 540, 200);
			$(KP.options.content).load(qmd.base+'/spread/selectSpreadMember.do?testTime='+testTime);
		
		});
		});
		
		
		//弹出转移会员卡，点击确定
		function zyFinish(){
				var testTime = new Date().getTime(); 
				var $idssClubChecked = $("#listTable input[name='idss']:checked");//会员卡复选框
				var $allCheckClub = $("#listTable input.allCheck_club");// 全选复选框
				
				if ($idssClubChecked.size() <= 0) {
					alert('请选择卡号！');
					return false;
				}
				if($("#spreadMemberId").val()==''){
					alert('请选择推广人员！');
					return false;
				}
				$.ajax({
			        type: "post",
			        dataType : "json",
			        data: $idssClubChecked.serialize(),
			        url: qmd.base+'/spread/ajaxZyClubCard.do?spreadMember.id='+$("#spreadMemberId").val()+'&testTime='+testTime,
			        success: function(data, textStatus){
			        	alert(data.message);
						$allCheckClub.attr("checked", false);
						$idssClubChecked.attr("checked", false);
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