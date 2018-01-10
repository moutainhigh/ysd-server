<!--左边导航开始!-->
<#if showLoginUser.typeId==3 && showLoginUser.agencytype ==1><#--对接服务商-->
<div class="left_nav">
	<div class="title"><span class="fl white fb">客户管理</span><a  class="fr" href="#"><img src="${base}/img/left_nav_arrow.gif" /></a></div>
	<div class="cont" id="cont">
        <!--nodes start!-->
        <div class="navNodes">
            <div class="nodes">
                <h6><span class="fl fb">客户管理</span><span class="systole icon"></span></h6>		
                <ul class="border">
                    <li><a id = "li_a_tjgrjkr" href="${base}/agency/inputBorrower.do">添加个人借款人</a></li>
                    <#--yujian <li><a id = "li_a_tjqyjkr" href="${base}/agency/inputqyBorrower.do">添加企业借款人</a></li>-->
                    <li><a id = "li_a_jkrlb" href="${base}/agency/borrowerList.do">借款人列表</a></li>
                </ul>        
            </div> 
            
            
        </div>
        <!--nodes end!-->                     
		<div class="circle_bottom2"></div>       
	</div>
</div><div class="cc"></div>
</#if>
<!--左边导航结束!-->
</td>
		<td valign="top" style="border-left:1px solid #a1a1a1;height:100%;">