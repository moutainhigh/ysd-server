
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-我的账户</title>
	<#include "/content/common/meta.ftl">
 <!-- header -->
</head>
<body>
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_agency.ftl">
<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="#">${Application ["qmd.setting.name"]}服务商业务管理平台</a></li>
			<li>首页</li>
		</ul>
	</div>
    
    <div class="mainWrap">
		

		<div class="row">
			<div class="col2">
				<div class="row">
					<div class="col4" style="width:50%;">
							<div class="inbox">
								<ul class="color0">
									<li class="logo"></li>
									<li class="number">${(depositMoney?string.currency)!'￥0.00'}</li>
									<li class="name">未还资金总额</li>
								</ul>
							</div>
						</div>
						<div class="col4" style="width:50%;">
							<div class="inbox">
								<ul class="color1">
									<li class="logo"></li>
									<li class="number">${(feeMoney?string.currency)!}</li>
									<li class="name">手续费总收益</li>
								</ul>
							</div>
						</div>
					</div>
				<div class="inbox">
					<div class="blk">
						<div class="title">用户信息</div>
						<div class="blk_line blk_logo">
							<ul>
								<li>${loginUser.username}:</li>
								<li>欢迎登录，您是${Application ["qmd.setting.name"]}的<font style="color:#ea5504;">对接服务商</font>！</li>
							</ul>
						</div>
						<div class="blk_line blk2"><span>上次登录时间：${loginUser.lastTime?string("yyyy-MM-dd HH:mm:ss")}</span></div>
						<div class="blk_line blk2"><span>上次登录IP：${loginUser.lastIp}</span></div>
						<div style="clear:both;"></div>
					</div>
				</div>

				<div class="inbox">
					<div class="blk">
						<div class="title">常用功能</div>
						<div class="quick">
							<table width="100%" border="0" cellpadding="10" cellspacing="12">
								<tr>
									<td>
										<a href="${base}/borrow/inputBorrowRaise.do?btp=14">
											<font class="orgdot">●</font>发布普通项目
										</a>
									</td>
									<td>
										<a href="${base}/borrow/userBorrowMgmt.do?bty=0">
											<font class="orgdot">●</font>项目列表
										</a>
									</td>
								</tr>
								<tr>
									<td>
										<a href="${base}/borrow/hkmx.do">
											<font class="orgdot">●</font>项目还款
										</a>
									</td>
									<td>
										<a href="${base}/payment/rechargeTo.do">
											<font class="orgdot">●</font>还款充值
										</a>
									</td>
								</tr>
								<tr>
									<td>
										<a href="${base}/agency_pt/cashFlow.do">
											<font class="orgdot">●</font>项目放款
										</a>
									</td>
									<td>
										<a href="${base}/agency_pt/detail.do">
											<font class="orgdot">●</font>账号信息
										</a>
									</td>
								</tr>
							</table>
		   				</div>
					</div>
				</div>
			</div>

			<div class="col2">
				<div class="inbox">
					<div class="blk">
						<div class="title color1">
							<span class="selected">最近还款</span>
						</div>
						<div class="list">
							<ul>
							 <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
							          <thead>
							            <tr>
							                <td>最近还款日</td>
											<td>项目标题</td>
									        <td>借款人</td>
									        <td>本期应还本金</td>
									        <td>本期应还利息</td>
									        <td>操作</td>	
							            </tr>
							          </thead>
							          <tbody>
							              <#list borrowList as borrow>
											<#assign flag = "">
											<#if borrow_index %2 != 0>
												<#assign flag = "td1">
											</#if>
												<tr height="39">
													<td class="${flag}"><#if borrow.nextRepayDate?exists>${borrow.nextRepayDate?string("yyyy-MM-dd")}</#if></td>
													<td class="${flag}"><a href="${Application ["qmd.setting.memberUrl"]}/borrow/detail.do?bId=${borrow.id}" target="_blank" title="${borrow.name}">${substring(borrow.name, 30, "")}</a></td>                                            
													<td class="${flag}">${borrow.realName}</td>
													<td class="${flag}">￥${borrow.repayCapital}</td>
													<td class="${flag}">￥${borrow.repayInterest}</td>
													<td class="${flag}"><a href="${base}/borrow/borrowRepayList.do?borrow.id=${borrow.id}">【还款】</a></td>
												</tr>
									</#list>
								</tbody>
								</table>
							</ul>
						</div>
					</div>
					<br>
					<br>
					<br>
					
					<div class="inbox">
					<div class="blk">
						<div class="title color1">
							<span class="selected">最近还款</span>
						</div>
						<div class="list">
							<ul>
							 <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
						          <thead>
						            <tr>
						                <td>项目标题</td>
										<td>业务类型</td>
										<td>期限</td>
										<td>天利率</td>
										<td>年利率</td>
										<td>状态</td>
						            </tr>
						          </thead>
						          <tbody>
						              
						             <#list borrowListNew as borrow>
											<#assign flag = "">
											<#if borrow_index %2 != 0>
												<#assign flag = "td1">
											</#if>
										<tr >
											<td class="${flag}"><a href="${Application ["qmd.setting.memberUrl"]}/borrow/detail.do?bId=${borrow.id}" target="_blank" title="${borrow.name}">${substring(borrow.name, 16, "")}</a></td> 
											<td class="${flag}">
											
											</td>
											<td class="${flag}">
												${borrow.apr}‰/天 
											</td>
											<td class="${flag}">
												<@aprofyear apr="${borrow.apr}"; apryear>${apryear}</@aprofyear>%
											</td>
											
											<td class="${flag}">
												<@listing_childname sign="borrow_type" key_value="${borrow.status}"; type>
													${type}
												</@listing_childname>
											</td>
									</tr>
									</#list>    
						          </tbody>
						        </table>
							</ul>
						</div>
					</div>
					
				</div>
				
			</div>
		</div>
	</div>
</div>

	<#include "/content/common/footer.ftl">
	<script>
		$().ready( function() {
			$('.title span').click(function(){
				$(this).addClass('selected').siblings().removeClass('selected');
				var i=$('.title span').index(this);
				$('.list > ul').eq(i).show().siblings().hide();
			})
		});
	</script>
</body>
</html>