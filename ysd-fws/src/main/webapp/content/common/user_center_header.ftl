<table width=100% height=100% border="0" cellpadding="0" cellspacing="0">
 	<tr>
		<td colspan="2">
			<div class="header">
				<div class="leftbg">
			    	<h1 class="logo fl"><a href="${base}/"><!--<img src="${base}/img/logo.png" width="142" height="45" alt="logo" title="logo" />--></a></h1>		
					<ul id="menu"><!--menu start!-->
			        	
			        	<#if showLoginUser.typeId==3 && showLoginUser.agencytype ==1><#--对接服务商-->
			        	<li><a class="sssj" href="${base}/borrow/userBorrowMgmt.do"><span>项目管理</span></a>
							<ul class="shadowMenu">
				<!--				<li><a href="${base}/borrow/borrowInputRaise.do?btp=7">发布借款</a></li>-->
								<li><a href="${base}/borrow/inputBorrowRaise.do?btp=14">发布普通借款</a></li>
								<li><a href="${base}/borrow/inputBorrowRaise.do?btp=16">发布新手借款</a></li>
								<li><a href="${base}/borrow/userBorrowMgmt.do?bty=0">项目管理</a></li>
								<li><a href="${base}/borrow/userBorrowNoDone.do">还款管理</a></li>
<#--								<li><a href="#">合同管理</a>
							
								<li><a href="${base}/borrow/borrowInputRaise.do?btp=7">发布借款</a></li>
								<li><a href="${base}/defer/apply.do">展期管理</a></li>
								<li><a href="${base}/agency/borrowerList.do">借款人管理</a></li>
								<li><a href="${base}/agency_pt/toBankInput.do">放款帐户</a></li>
								<li><a href="${base}/contractBorrow/contractBorrowList.do">合同管理</a></li>
								-->
								<div class="cc"></div>
							</ul>
						</li>
						<li><a class="lssj" href="${base}/borrowRecharge/borrowList.do"><span>资金管理</span></a>
							<ul class="shadowMenu">
			                    <li><a href="${base}/borrowRecharge/borrowList.do">还款充值</a></li>
			                    <li><a href="${base}/fangkuan/borrowList.do">放款管理</a></li>
			                    <div class="cc"></div>
			                </ul>
						</li>
			        	<li><a class="gjxx" href="${base}/agency_pt/detail.do"><span>账户管理</span></a>
							<ul class="shadowMenu">
								<li><a href="${base}/agency_pt/detail.do">账户管理</a></li>
			                	<li><a href="${base}/userCenter/toPassword.do">密码管理</a></li>
			                    <div class="cc"></div>
			                </ul>
			            </li>
						<li><a class="tjxx" href="${base}/agency/borrowerList.do"><span>客户管理</span></a>
							<ul class="shadowMenu">
								<li><a href="${base}/agency/borrowerList.do">借款人管理</a></li>
							<#--
			                	<li><a href="${base}/spread/spreadMemberList.do">推广渠道</a></li>
			                	<li><a href="${base}/spread/spreadTg.do">推广人员</a></li>
			                	<li><a href="${base}/spread/customUserList.do">客户管理</a></li>
			                	<li><a href="${base}/spread/tjProfile.do">客户数据统计</a></li>
			                	<li><a href="${base}/payment/rechargeToAgency.do">门店充值</a></li>
			                -->
			                    <div class="cc"></div>
			                </ul>
						</li>     
						</#if>
			        </ul><!--menu over!-->        
			    </div>
			
				<div class="right">
					<div class="fr"><a href="javascript:;"id="logout" data-href="${base}/userCenter/logout.do" title="退出" /></a></div>
					<div class="fr logo_info">
						<p>当前用户：${loginUser.username}</p>
					</div>
			    </div>    
			</div>
			
			
		</td>
	</tr>
	<tr>
		<td style="width:240px; height: 100%;vertical-align:top">