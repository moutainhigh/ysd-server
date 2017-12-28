<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-邀请好友</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/invest_friend.css" />
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
        <div class="invest_link">
            <div class="title">复制邀请链接发给好友</div>
            <div class="link"><textarea id="copycont" >https://www.yueshanggroup.cn/fd/${loginUser.tgNo}</textarea>
           <!-- <input type="text"  id="fe_text" placeholder="https://www.yueshanggroup.cn/fd/${loginUser.tgNo}"  value="https://www.yueshanggroup.cn/fd/${loginUser.tgNo}"   />-->
            </div>
            <div class="copy">复制邀请链接</div>
        </div>
        <div class="weixin">
        	<div class="qrcodeTable"></div>
            <div class="title">通过微信推荐好友</div>
            <div class="step1">第一步：打开微信，点击右上角“+”号，点击扫一扫</div>
            <div class="step2">第二步：发给朋友，分享投资梦</div>
        </div>
        <div class="invest_introduction">
            <div class="title">邀请好友规则说明：</div>
            <!--<div class="step1">1.邀请好友通过上面的链接或者扫描二维码完成注册。</div>
            <div class="step2">2.好友完成“绑卡认证”，您将获得280元的“红包”奖励。</div>-->
            ${setting.offLineRechargeDes}
        </div>
        <table>
            <tr>
                <td>好友手机</td>
                <td>加入时间</td>
                <td>账户认证</td>
                <td>投资奖励</td>
            </tr>
        </table>
        <div class='friend_phone'>
	        <table>
	            <tr>
	           	 <#if pager.result?size==0><tr><td>暂无记录</td></tr></#if>
	                <#list pager.result as us>
	                  <tr height="72">
	                    <td >${us.username}</td>
	                    <td >${us.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
	                    <td >
		                    <#if us.realStatus ==1>
								<!--<img src="/img/yes.png" alt=""  />-->已认证
							<#else>
		                      <!--<img src="/img/no.png" alt=""  />-->未认证
		                    </#if>
	                    </td>
	                    <td >${(us.uadSumMoney?string.currency)!'0.00'}</td>
	                  </tr>
	               </#list>
	            </tr>
	        </table>
	        <!-- 分页 -->
	        <form id="listForm" method="post" action="toGetLink.do" >
		        <#if (pager.totalCount > 1)>
					<@pageFlip pager=pager>
						<#include "/content/borrow/pager.ftl">
					</@pageFlip>
				</#if>
	        </form>
        </div>
    </div>
    <div class="clear"></div>
</div>

<!--底部-->

<#include "/content/common/footer-center.ftl">
<script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${base}/js/base.js"></script>
<script type="text/javascript" src="${base}/js/my_assets.js"></script>
<script src="${base}/js/common.js"></script>
<script type="text/javascript">

		$().ready( function() {
			$("#header_wdzh").addClass("current");<#-- header.ftl 选中样式 -->
			$("#header_gywm").find('a').css('border',0);
			$(".center_left_yqhy").addClass("current");<#-- user_center_left.ftl 选中样式 -->
		});

	</script>
	<script type="text/javascript">
   		$().ready( function() {
		window._bd_share_config = {
		common : {
			bdText : '${setting.miniMoney}',
			bdUrl : '<#if loginUser.tgNo!>https://www.yueshanggroup.cn/fd/${loginUser.tgNo}<#else>https://www.yueshanggroup.cn/</#if>',
			bdPic : qmd.base+'/img/a4.png'
		},
		share : [{
			"bdSize" : 32
		}],
		image : [{
			viewType : 'list',
			viewPos : 'top',
			viewColor : 'black',
			viewSize : '32',
			viewList : ['qzone','tsina','huaban','tqq','renren']
		}],
		selectShare : [{
			"bdselectMiniList" : ['qzone','tqq','kaixin001','bdxc','tqf']
		}]
	}
	with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?cdnversion='+~(-new Date()/36e5)];
	
			
			
		});

	</script>
	<script src="/js/qrcode.js"></script>
	<script src="/js/jquery.qrcode.js"></script>
<script src="${base}/js/ZeroClipboard.js"></script>
<script type="text/javascript">
    // 定义一个新的复制对象
    var clip = new ZeroClipboard( document.getElementById("d_clip_button"), {
        moviePath: qmd.base+"/static/js/common/ZeroClipboard.swf"
    });
    // 复制内容到剪贴板成功后的操作
    clip.on( 'complete', function(client, args) {
        alert("复制成功!");
    });
    $(function(){		
		$(".qrcodeTable").each(function(){
			$(this).qrcode({text: 'https://www.yueshanggroup.cn/fd/${loginUser.tgNo}',width:140,height:140});
		});
		
	});
</script>
</body>
</html>
