<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-红包奖励</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/redbag_use_details.css" />
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
           
            <!--红包-->
            <div class="redbag">
                <div class="back">
                    <a  onclick="javascript:window.location.href='${base}/award/hongbao.do'">返回</a>
                </div>
                <form id="listForm" method="post" action="${base}/award/hongbaoDetailH.do">
                <table>
                    <tr>
                        <td>时间</td>
                        <td>收入</td>
                        <td>支出</td>
                        <td>描述</td>
                    </tr>
                     <#list pager.result as awardDetail>
                    <tr>
                        <td>${(awardDetail.createDate?string("yyyy-MM-dd HH:mm"))!}</td>
                        <td><#if awardDetail.signFlg ==1>${awardDetail.money}<#else>--</#if></td>
                        <td><#if awardDetail.signFlg == -1>${awardDetail.money}<#else>--</#if></td>
                        <td>${awardDetail.remark}</td>
                    </tr>
                     </#list>
                </table>
 
                <@pageFlip pager=pager>
					<#include "/content/borrow/pager.ftl">
				</@pageFlip>
				</form>
            </div>
          
            
        </div>
        <div class="clear"></div>
    </div>

    <!--底部-->
<#include "/content/common/footer-center.ftl">
    <script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
    <script src="${base}/js/common.js"></script>
    <script src="${base}/js/redbag_use_details.js"></script>
     <script type="text/javascript">
		$().ready( function() {
			
			$("#header_wdzh").addClass("current");<#-- header.ftl 选中样式 -->
			$('#header_gywm').find('a').css('border',0);
			$(".center_left_hbjl").addClass("current");<#-- user_center_left.ftl 选中样式 -->
		});

	</script>
</body>
</html>