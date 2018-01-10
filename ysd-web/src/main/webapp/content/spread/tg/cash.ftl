<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-红包使用详情</title>
    <#include "/content/common/meta.ftl">
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
            <div class="options">
                <div  onclick="javascript:window.location.href='/award/hongbao.do'">红包</div>
                <div class='current'>奖励</div>
            </div>
        
        
            <div class="bonuses" >
             <form id="listForm" method="post" action="${base}/award/hongbaoDetailH.do?keyHb=2"<#if keyHbType?exists>?keyHbType=${keyHbType}</#if>">
                <div class="bonuses_options">
                    <a  href ="${base}/award/hongbaoDetailH.do?keyHb=2">全部</a>
                    <a  href ="${base}/award/hongbaoDetailH.do?keyHb=2&keyHbType=1">邀请奖励</a><!--对应原先的好友投资奖励-->
                    <a  href ="${base}/award/hongbaoDetailH.do?keyHb=2&keyHbType=2">普通奖励</a>
                </div>
                <!--全部-->
                <table class="all">
                    <tr>
                        <td>时间</td>
                       <!-- <td>类型</td>-->
                        <td>奖励金额</td>
                        <td>描述</td>
                    </tr>
                    <#if pager.result?size==0 ><tr><td colspan="4">暂无奖励记录！</td></tr></#if>
                    <#list pager.result as accountDetail>
              
                    <tr>
                        <td>${(accountDetail.createDate?string("yyyy-MM-dd HH:mm"))!}</td>
                       <!--
                        <td>
                        <@listing_childname sign="account_link" key_value="${accountDetail.type}"; accountBankName>
							${accountBankName}
						</@listing_childname>
						</td>
						-->
                        <td>${accountDetail.money?string.currency}</td>
                        <td>${accountDetail.remark}</td>
                    </tr>
                    </#list>
                </table>
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
    <script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
    <script src="${base}/js/common.js"></script>
    <script src="${base}/js/redbag_use_details.js"></script>

    <script type="text/javascript">
    	function getQueryString(name) { 
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
			var r = window.location.search.substr(1).match(reg); 
			if (r != null) return unescape(r[2]); return null; 
		}
		$().ready( function() {
			
			$("#header_wdzh").addClass("current");<#-- header.ftl 选中样式 -->
			$("#header_gywm").find('a').css('border',0);
			$(".center_left_hbjl").addClass("current");<#-- user_center_left.ftl 选中样式 -->
			var type=getQueryString('keyHbType');
				if(type!=0){
				$(".bonuses_options").find('a').eq(type).addClass('current');
				}else{
				$(".bonuses_options").find('a').eq(0).addClass('current');
			}
		});

	</script>
</body>
</html>
