<!--左边导航开始!-->
<div class="left_nav">
	<div class="title"><span class="fl white fb">我是服务商</span><a  class="fr" href="#"><img src="${base}/img/left_nav_arrow.gif" /></a></div>
	<div class="cont" id="cont">
        <!--nodes start!-->
        <#--<div class="circle_top2"><h5 class="fl fb">发布借款</h5><span class="display icon"></span></div>-->
        <div class="navNodes">
        <#-->
            <div class="nodes noborder">            		
                <ul class="border">
                     <#if (loginUser.agency.flowRule)! ==1><li><a id = "li_a_zqlzxm" href="${base}/borrow/borrowInputRaise.do?btp=7">定投宝 债权转让项目</a></li></#if>
                     <#if (loginUser.agency.pledgeRule)! ==1><li><a id = "li_a_dyzyxm" href="${base}/borrow/borrowInputRaise.do?btp=8">定投宝 直接出让项目</a></li></#if>
                     <#if (loginUser.agency.creditRule)! ==1><li><a id = "li_a_fbxyxm" href="${base}/borrow/borrowInputRaise.do?btp=9">发布信用项目</a></li></#if>
                     <#if (loginUser.agency.tasteRule)! ==1><li><a id = "li_a_fbtyxm" href="${base}/borrow/borrowInputRaise.do?btp=10">发布体验项目</a></li></#if>
                     <#if (loginUser.agency.flowRule)! ==1><li><a id = "li_a_fbtyxm" href="${base}/borrow/borrowInputRaise.do?btp=11">灵活宝项目</a></li></#if>
                </ul>
            </div>-->
            <div class="nodes noborder">
                <h6><span class="fl fb">项目管理</span><span class="systole icon"></span></h6>		
                <ul class="border">
                    <li><a id = "li_a_jkgl" href="${base}/borrow/inputBorrowRaise.do?btp=14">发布项目</a></li><#--
                     <li><a id = "li_a_jkgl" href="${base}/borrow/inputBorrowRaise.do?btp=16">发布新手项目</a></li>-->
                     <li><a id = "li_a_jkgl" href="${base}/borrow/userBorrowMgmt.do?bty=0">所有项目列表</a></li>
   <!--                 <li><a id = "li_a_jkgl" href="${base}/borrow/userBorrowMgmt.do?bty=1">募集中的项目</a></li>
                    <li><a id = "li_a_jkgl" href="${base}/borrow/userBorrowMgmt.do?bty=2">募集失败的项目</a></li>-->
                </ul>
            </div>
            <#--
            <div class="nodes noborder">
                <h6><span class="fl fb">展期管理</span><span class="systole icon"></span></h6>		
                <ul class="border">
                    <li><a id = "li_a_sqzq" href="${base}/defer/apply.do">申请展期</a></li>
                    <li><a id = "li_a_ysqzq" href="${base}/defer/applySuccess.do">已申请展期</a></li>
                    <li><a id = "li_a_zqyfb" href="${base}/defer/published.do">展期已发布</a></li> 
                </ul>          
            </div> 
            
            <div class="nodes noborder">
                <h6><span class="fl fb">项目预约管理</span><span class="systole icon"></span></h6>		
                <ul class="border">
                    <li><a id = "li_a_yyxmlb" href="${base}/reservation/list.do">预约项目列表</a></li>
                </ul>          
            </div> 
            -->
            <div class="nodes noborder"> 
                <h6><span class="fl fb">还款管理</span><span class="systole icon"></span></h6>		
                <ul class="border">
                   <#-- <li><a id = "li_a_wyhk" href="${base}/borrow/hkmx.do">我要还款</a></li>
                    <li><a id = "li_a_whk" href="${base}/borrow/userBorrowNoDone.do">还款中项目</a></li>
                    <li><a id = "li_a_hkwc" href="${base}/borrow/userBorrowDone.do">还款完成</a></li>
                    -->
                    <li><a id = "li_a_whk" href="${base}/borrow/userBorrowNoDone.do">项目还款</a></li>
                    <li><a id = "li_a_hkwc" href="${base}/borrow/userBorrowDone.do">已还款记录</a></li>
                    
                </ul>
            </div> 
            
  <#--            <div class="nodes noborder">
                <h6><span class="fl fb">合同管理</span><span class="systole icon"></span></h6>		
                <ul class="border">
                   <li><a id = "li_a_htlb" href="${base}/contractBorrow/contractBorrowList.do">合同列表</a></li>
                    <li><a id = "li_a_htcl" href = "${base}/contractBorrow/contractBorrowProcessList.do">合同处理</a></li>
               		
                </ul>
            </div> -->
    	 <#-->     
            <div class="nodes noborder">
                <h6><span class="fl fb">借款人管理</span><span class="systole icon"></span></h6>		
                <ul class="border">
                    <li><a id = "li_a_jkrlb" href="${base}/agency/borrowerList.do">借款人列表</a></li>
                </ul>
            </div>   
               
            <div class="nodes noborder">
                <h6><span class="fl fb">放款账户</span><span class="systole icon"></span></h6>		
                <ul class="border">
                    <li><a id = "li_a_yhzhlb" href="${base}/agency_pt/bankList.do">银行账户列表</a></li>
                </ul>
            </div>   
         <#--      
            <div class="nodes noborder">
                <h6><span class="fl fb">合同管理</span><span class="systole icon"></span></h6>		
                <ul class="border">
                    <li><a id = "li_a_htlb" href="${base}/contractBorrow/contractBorList.do">合同列表</a></li>
                 <li><a id = "li_a_htcl" href = "${base}/contractBorrow/contractBorrowProcessList.do">合同处理</a></li>
                </ul>
            </div> 
          ->     
           
        </div>
        <!--nodes end!-->                     
		<div class="circle_bottom2"></div>       
	</div>
</div><div class="cc"></div>
<!--左边导航结束!-->
</td>
		<td valign="top" style="border-left:1px solid #a1a1a1;height:100%;">