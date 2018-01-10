<!--左边导航开始!-->
<div class="left_nav">
	<div class="title"><span class="fl white fb">账户管理</span><a  class="fr" href="#"><img src="${base}/img/left_nav_arrow.gif" /></a></div>
	<div class="cont" id="cont">
        <!--nodes start!-->
        <div class="circle_top2"><h5 class="fl fb">账号信息</h5><span class="display icon"></span></div>
        <div class="navNodes">
            <div class="nodes noborder">            		
                <ul class="border">
                    <li><a id = "li_a_zhxx" href="${base}/agency_pt/detail.do">账户信息</a></li> 
                    <#--
                    <li><a id = "li_a_zymgl" href="${base}/agency_pt/subpageDetail.do">店铺管理</a></li>
                    -->        
                </ul>
            </div>
            <#--
            <div class="nodes noborder">
                <h6><span class="fl fb">子页面管理</span><span class="systole icon"></span></h6>		
                <ul class="border">
                    <li><a id = "li_a_zymgl" href="${base}/agency_pt/subpageDetail.do">子页面管理</a></li>
                </ul>
            </div>
            -->
            <div class="nodes noborder">
                <h6><span class="fl fb">密码管理</span><span class="systole icon"></span></h6>		
                <ul class="border">
                    <li><a id = "li_a_dlmm" href="${base}/userCenter/toPassword.do">登录密码</a></li>
                    <li><a id = "li_a_aqmm" href="${base}/userCenter/toPayPassword.do">安全密码</a></li>  
                </ul>        
            </div> 
            <#--
            <#if loginUser?exists && loginUser.id==2>
            <div class="nodes noborder">
                <h6><span class="fl fb">系统管理</span><span class="systole icon"></span></h6>		
                <ul class="border">
                    <li><a id = "li_a_dlmm" href="${base}/show.do">系统情况</a></li>
                    <li><a id = "li_a_kqtj" href="${base}/report/tongjiList.do">日均统计</a></li>
                    <li><a id = "li_a_fgsrj" href="${base}/report/agencyTongjiList.do">分公司日均</a></li>
                    <li><a id = "li_a_dstj" href="${base}/report/repaymentList.do?accountOnly=1">定投待收统计</a></li>
                </ul>        
            </div> 
           </#if>
          -->
        </div>
        <!--nodes end!-->                     
		<div class="circle_bottom2"></div>       
	</div>
</div><div class="cc"></div>
<!--左边导航结束!-->
</td>
		<td valign="top" style="border-left:1px solid #a1a1a1;height:100%;">