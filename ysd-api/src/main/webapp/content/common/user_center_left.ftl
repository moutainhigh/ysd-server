	 <div style="width:252px; float:left; background:url(${base}/static/img/e8.png) right top repeat-y #efebdf; padding-top:28px;">
          
		<#if loginUser.typeId == 0>
           <dl style="width:252px; margin-bottom:40px;">
              <dt style="width:178px; margin:0 auto; border-bottom:1px solid #be9964; padding-bottom:4px;font-size:16px;font-family:'宋体';color:#be9964; background:url(${base}/static/img/e3.png) 0 0 no-repeat; position:relative; margin-bottom:5px;">
                <span style="margin-left:24px; font-weight:700;">我是投资人</span>
                <span style="background:url(${base}/static/img/e7.png) 0 0 no-repeat; width:13px; height:13px; display:inline-block; position:absolute; right:0px; top:3px;"></span>
              </dt>
              <dd><a href="${base}/borrow/borrowDetailList.do" class="nsg " id="investment_record">投资记录</a></dd> 
              <dd><a href="${base}/borrow/bidBorrowDetailList.do" class="nsg " id="investment_income">收益明细</a></dd> 
              <dd><a href="${base}/borrow/bidSetRollList.do" class="nsg " id="investment_continue">续投设置</a></dd> 
              <dd><a href="${base}/user/tenderAuto.do" class="nsg " id="investment_tenderAuto">自动投标</a></dd>  
      	
           </dl>
       	<#elseif loginUser.typeId==1>
           	<dl style="width:252px; margin-bottom:40px;">
	              <dt style="width:178px; margin:0 auto; border-bottom:1px solid #be9964; padding-bottom:4px;font-size:16px;font-family:'宋体';color:#be9964; background:url(${base}/static/img/e3.png) 0 0 no-repeat; position:relative; margin-bottom:5px;">
	                <span style="margin-left:24px; font-weight:700;">我是借款人</span>
	                <span style="background:url(${base}/static/img/e7.png) 0 0 no-repeat; width:13px; height:13px; display:inline-block; position:absolute; right:0px; top:3px;"></span>
	              </dt>
	              <dd><a href="${base}/userCenter/toInformation.do" class="nsg " id="borrower_choose">我要借款</a></dd> 
	              <dd><a href="${base}/borrow/userBorrowMgmt.do" class="nsg " id="borrower_recode">借款管理</a></dd> 
	              <dd><a href="${base}/borrow/borrowListCopy.do" class="nsg " id="borrower_copy">续标管理</a></dd> 
	              <dd><a href="${base}/borrow/hkmx.do" class="nsg " id="borrower_repayment">还款管理</a></dd>  
           </dl>
       </#if>
           
       <dl style="width:252px; margin-bottom:40px;">
          <dt style="width:178px; margin:0 auto; border-bottom:1px solid #be9964; padding-bottom:4px;font-size:16px;font-family:'宋体';color:#be9964; background:url(${base}/static/img/e4.png) 3px 0 no-repeat; position:relative; margin-bottom:5px;">
            <span style="margin-left:26px; font-weight:700;">资金管理</span>
            <span style="background:url(${base}/static/img/e7.png) 0 0 no-repeat; width:13px; height:13px; display:inline-block; position:absolute; right:0px; top:3px;"></span>
          </dt>
          <dd><a href="${base}/payment/rechargeTo.do" class="nsg" id="my_recharge">我的充值</a></dd>
          <dd><a href="${base}/userCenter/getMoney.do" class="nsg" id="my_deposit">我的提现</a></dd>
          <dd><a href="${base}/account/detail.do?type=statistics" class="nsg" id="fund_statistics">资金详情</a></dd>
       </dl>
       
       <dl style="width:252px; margin-bottom:80px;">
          <dt style="width:178px; margin:0 auto; border-bottom:1px solid #be9964; padding-bottom:4px;font-size:16px;font-family:'宋体';color:#be9964; background:url(${base}/static/img/e5.png) 3px 0 no-repeat; position:relative; margin-bottom:5px;">
            <span style="margin-left:26px; font-weight:700;">账户管理</span>
            <span style="background:url(${base}/static/img/e7.png) 0 0 no-repeat; width:13px; height:13px; display:inline-block; position:absolute; right:0px; top:3px;"></span>
          </dt>
          <dd><a href="${base}/userCenter/realname.do" class="nsg" id="member_realname">账户认证</a></dd>
          <dd><a href="${base}/userCenter/toBankInput.do" class="nsg" id="member_banks">银行账户</a></dd>
          <dd><a href="${base}/userCenter/toPassword.do" class="nsg" id="member_password">密码管理</a></dd>
          <dd><a href="${base}/userCenter/toEmail.do" class="nsg" id="member_email">邮箱认证</a></dd>
          <dd><a href="${base}/spread/toGetLink.do" class="nsg"  id="member_friend">好友管理</a></dd>
          <#--><dd><a href="" class="nsg">系统消息</a></dd>-->
       </dl>
          
	  
           
           
           <ul style="width:224px; border-top:1px solid #be9964; padding:23px 0px 44px 28px;">
             <@article_list sign='site_notice' count=5; articleList>
				<#list articleList as article>
					 <li style="margin-top:22px;"><a href="<#if article.url!><@article.url?interpret /><#else>${base}/article/content/${article.id}.htm</#if>" style="color:#646464;font-family:'宋体';">·<#if (article.title?length > 13)>${article.title?substring(0,13)}...<#else>${article.title}</#if></a></li>
				</#list>
			</@article_list>
          </ul>
        </div>
  
