<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-红包奖励</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/redbag_bonuses.css" />
</head>
<body id="bg">
    <!-- 头部 -->
 
	<#include "/content/common/header.ftl">

    <!--内容区域-->
    <div id="main">
        <!--左边导航栏-->
       
        <#include "/content/common/user_center_left.ftl">
        <!--右边主内容区-->
        <div class="content fr">
            <div class="options">
                <div class='current'>红包</div>
                <div><a href="${base}/award/hongbaoDetailH.do?keyHb=2">奖励</a></div>
            </div>
            <!--红包-->
             <div class="red_bag">
                <div class="redbag_options">
               	    <a href="${base}/award/hongbao.do" ><#if status =='' || status == 0>  <span class="un_use fl current"><#else><span class="un_use fl "> </#if> 未使用</span></a>
                    <a href="${base}/award/hongbao.do?status=1" ><#if status?exists && status ==1>  <span class="un_use fl current"><#else><span class="un_use fl "> </#if> 已使用</span></a>
                    <a href="${base}/award/hongbao.do?status=2"><#if  status?exists && status ==2>  <span class="un_use fl current"><#else><span class="un_use fl "> </#if> 已过期</span></a>
                   
                    <a href="${base}/award/hongbaoDetailH.do"><span class="redbag_detail fr">查看红包详情</span></a>
             </div>

                <!--添加div状态-->
              
         <#if status =='' || status == 0>
            <div class="redbag un option_content <#if pager.result?size==0>unReg</#if>"  >
         <#elseif status?exists && status ==1>
            <div class="used redbag option_content <#if pager.result?size==0>unReg</#if>" >
         <#elseif  status?exists && status ==2>
         	<div class="overdue redbag option_content <#if pager.result?size==0>unReg</#if>" >
         </#if>
              
               <form id="listForm" method="post" action="${base}/award/hongbao.do<#if status?exists>?status=${status}</#if>">   
            
                  <#list pager.result as hb>
            	  <#if hb.status == 0>
                  
                     <div class="redbag_model">
                        <div class="title">
                            ￥<span class="redbag_value">${hb.money}</span><span class="redbag_name" title="${hb.name}"><#if hb.source?number==6>${hb.sourceString}<#else>${hb.name}</#if></span>
                        </div>
                        <div class="use_detail">
                            <div class="deadline">有效期：截止${hb.endTime?string("yyyy-MM-dd")}</div>
                            <div class="project_deadline">项目期限：满${hb.limitStart}天可用</div>
                            <div class="use_conditional">投资金额：满${hb.investFullMomey}元可用</div>
                        </div>
                    </div>
                    
                    
               <#elseif hb.status ==1>

                <!--已使用-->
     
                    <div class="redbag_model">
                        <div class="title">
                            ￥<span class="redbag_value">${hb.money}</span><span class="redbag_name"><#if hb.source?number==6>${hb.sourceString}<#else>${hb.name}</#if></span>
                        </div>
                        <div class="use_detail">
                            <div class="deadline">有效期：截止${hb.endTime?string("yyyy-MM-dd")}</div>
                            <div class="project_deadline">项目期限：满${hb.limitStart}天可用</div>
                            <div class="use_conditional">投资金额：满${hb.investFullMomey}元可用</div>
                        </div>
                    </div>
                     
                <!--已过期-->
                 <#elseif hb.status ==2>
                
                    <div class="redbag_model">
                        <div class="title">
                            ￥<span class="redbag_value">${hb.money}</span><span class="redbag_name"><#if hb.source?number==6>${hb.sourceString}<#else>${hb.name}</#if></span>
                        </div>
                        <div class="use_detail">
                            <div class="deadline">有效期：截止${hb.endTime?string("yyyy-MM-dd")}</div>
                            <div class="project_deadline">项目期限：满${hb.limitStart}天可用</div>
                            <div class="use_conditional">投资金额：满${hb.investFullMomey}元可用</div>
                        </div>
                    </div>
                  </#if>  
                    
                 </#list> 
                  <@pageFlip pager=pager>
					<#include "/content/borrow/pager.ftl">
				</@pageFlip>
				
				 </form>
              </div>
            </div>        
                <div class="tips">
                    红包使用提示：
                    <div>1、每个红包都有不同的项目期限要求和使用有效期，红包只能用于投资抵扣，不能用来兑换提现。</div>
                    <div>2、红包可叠加使用，但需满足所有被勾选的红包标注投资金额（满X可用）的总额不超过“项目投资限额”及”剩余实际可投金额”。</div>
                </div>
            </div>
            <!--奖励-->
             
        </div>
        	
        
        </form>
        <div class="clear"></div>
    </div>

    <!--底部-->
	<#include "/content/common/footer-center.ftl">

    <script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
    <script src="${base}/js/common.js"></script>
    <script src="${base}/js/redbag_bonuses.js"></script>
    <script type="text/javascript">
		$().ready( function() {
			
			$("#header_wdzh").addClass("current");<#-- header.ftl 选中样式 -->
			$('#header_gywm').find('a').css('border',0);
			$(".center_left_hbjl").addClass("current");<#-- user_center_left.ftl 选中样式 -->
		});

	</script>
</body>

</html>