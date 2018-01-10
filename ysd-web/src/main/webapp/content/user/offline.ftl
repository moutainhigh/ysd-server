<!DOCTYPE html>
<html>
 <head>
 <meta charset="gb2312" />
  <#assign flag="借款">
 <#if loginUser.typeId==0>
  <#assign flag="投资">
 </#if>
  <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-线下${flag}管理- 我要线下${flag}</title>
	<#include "/content/common/meta.ftl">
 </head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">
<!-- content -->
<div class="admin clearfix">
      <!--left-->
	   <#include "/content/common/user_center_left.ftl">

	<!-- right -->
	<div class="admin-con">
		<!-- location -->
		<div class="breadcrumb">
			<a href="${base}/">${Application ["qmd.setting.name"]}</a> &gt; <a href = "${base}/userCenter/toOffline.do">线下${flag}管理</a> &gt; 我要线下${flag}
		</div>
		
		<div class="tab clearfix">
			<ul>
				<li><a href="#" class="current"><span>我要线下${flag}</span></a></li>
				<li><a href="${base}/userCenter/toOfflineList.do"><span>申请记录</span></a></li>
			</ul>		
		</div>
		<div class="tab-con special">
			
			<!-- frm -->
			<div class="frm">
			<form id="offlineForm" method="post" action="saveOffline.do" >
				<table>
					<tr>
						<td class="item" width="150">姓名：</td>
						<!--
						<td>${substring(loginUser.realName, 1, "***")}
							<label> 
								<#if loginUser.sex=="1" >先生<#else>女士</#if>
							</label>
						</td>
						-->
						<td>${loginUser.showRealName}
							<label> 
								<#if loginUser.sex=="1" >先生<#else>女士</#if>
							</label>
						</td>
					</tr>
					<tr>
						<td class="item">手机：</td>
						<td><input type="text" class="txt " value="${loginUser.phone}" readOnly/></td>
					</tr>
					<tr>
						<td class="item">所属区域：</td>
						<td>
							<input type="text" id="areaSelect" name="areaId" class="hidden" value="<#if loginUser.area!>${loginUser.area}<#elseif loginUser.city!>${loginUser.city}<#else>${loginUser.province}</#if>" defaultSelectedPath="${loginUser.province!},${loginUser.city!},${loginUser.area!}" />
						
						</td>
					</tr>
					<#if flag=="借款">
						<tr>
							<td class="item">借款目的：</td>
							<td>
								<input type="text" name="offLine.purpose" id="purpose"  class="txt" size="20" />
								 <label id = "purpose_label" for="offLine.purpose"></label>
							</td>
						</tr>
					</#if>
					<tr>
						<td class="item">${flag}金额：</td>
						<td>
							<input type="text" id="offLine_money" name="offLine.money" class="txt" />&nbsp;元
							<label id = "offLine_money_label" for="offLine.money"></label>
						</td>
					</tr>
					<tr>
						<td class="item">${flag}时长：</td>
						<td>
							<input id="duration" type="text"  name="offLine.duration" class="txt" >
						    <label id = "duration_label" for="offLine.duration"></label>
						</td>
					</tr>
					<tr>
						<td class="item"><#if flag=="借款">意向利率<#else>预期收益</#if>：</td>
						<td>
							<input id="rate" type="text"  name="offLine.rate" class="txt">&nbsp;%
							<label id = "rate_label" for="offLine.rate"></label>
						</td>
					</tr>
					<#if flag=="借款">
						<tr>
							<td class="item" valign="top">抵押物介绍：</td>
							<td>
								<textarea name="offLine.introduce" class="txt"  rows="3" cols="40"></textarea>
						        <label id = "introduce_label" for="offLine.introduce"></label>
							</td>
						</tr>
					</#if>
					<tr>
						<td class="item" valign="top"></td>
						<td>
							<input type="submit" value="提出申请" class="btn s3_" />
						</td>
					</tr>

				</table>
				</form>
			</div>
		</div>		
	</div>
</div>
<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.lSelect.js"></script>
<script type="text/javascript">
	$().ready( function() {
		<#if flag="投资">
			$("#offline_investment").addClass("current");
		<#else>
			$("#offline_borrowing").addClass("current");
		</#if>	
		var $areaSelect = $("#areaSelect");
		var $offlineForm = $("#offlineForm");
		var $offlineSubmitButton = $("#offlineSubmitButton");
		// 地区选择菜单
		$areaSelect.lSelect({
				url: "${base}/area/ajaxArea.do"// AJAX数据获取url
		});
		
		 // 表单验证
		$offlineForm.validate({
			rules: {
				"areaId":{
					required:true
				},
				"offLine.money":{
					required:true,
					positiveInteger:true,
					max:1000000000000
				},
				"offLine.rate":{
					required:true,
					positiveInteger:true,
					max:100
				},
				"offLine.duration":{
					required:true,
					minlength:1,
					maxlength:100
				}
				<#if flag=="借款">,
				"offLine.purpose":{
					required:true,
					minlength:1,
					maxlength:100
				},
				"offLine.introduce":{
					required:true,
					minlength:2,
					maxlength:200
				}
				</#if>
			},
			messages: {
				"areaId":{
					required:"所属地区不能为空"
				},
				"offLine.money":{
					required:"金额不能为空",
					positiveInteger:"必须为正整数",
					max:"金额小于1,000,000,000,000"
				},
				"offLine.rate":{
					required:"<#if flag=='借款'>意向利率<#else>预期收益</#if>不能为空",
					positiveInteger:"必须为正整数",
					max:"小于100"
				},
				"offLine.duration":{
					required:"${flag}时长不能为空",
					minlength:"最少1个字符",
					maxlength:"最多100个字符"
				}
				<#if flag=="借款">,
				"offLine.purpose":{
					required:"借款目的不能为空",
					minlength:"最少1个字符",
					maxlength:"最多100个字符"
				},
				"offLine.introduce":{
					required:"介绍不能为空",
					minlength:"最少1个字符",
					maxlength:"最多200个字符"
				}
				</#if>
			},
			errorPlacement: function(error, element) {
			  error.appendTo(element.parent());
			},
			submitHandler: function(form) {
				form.submit();
			}
		});
	});
</script>
</html>
