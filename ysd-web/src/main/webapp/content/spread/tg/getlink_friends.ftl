<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-好友管理</title>
	<#include "/content/common/meta.ftl">
<script type="text/javascript" src="/static/js/common/jquery.qrcode.js"></script> 
<script type="text/javascript" src="/static/js/common/qrcode.js"></script> </head>
	<script type="text/javascript" src="/static/js/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="/static/js/jquery/jquery.validate.methods.js"></script>
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">
<div class="content">
 <div style="width:955px; margin:0 auto; padding-bottom:20px;">
	<#include "/content/common/user_center_header.ftl">
 <div style="width:955px; float:left; background:#fff; clear:both;">
	<#include "/content/common/user_center_left.ftl">
 	<div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
			<a href="${base}/" style="color:#646464;font-family:'宋体';">${Application ["qmd.setting.name"]}</a><a style="color:#646464;font-family:'宋体';">></a>
			<a href="${base}/userCenter/index.do" style="color:#646464;font-family:'宋体';">我的账户</a><a style="color:#646464;font-family:'宋体';">></a>
			<a class="liebiao" style="color:#646464;font-family:'宋体';">好友管理</a>
		</div>
		 <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
           <a href="javascript:void(0);" class="hr hre">好友推荐</a>
			<a  class="hr" href="${base}/spread/registerAward.do">红包明细</a>
			<#--<a class="hr " href="${base}/spread/total.do">投资奖励明细</a>-->   
          </div>
				
         <ul style=" margin-bottom:0px; font-size:14px; width: 624px; padding:20px; color:#424242; margin-top:0px; background:#fafafa;">
  <li style="  margin-bottom:10px;margin-top:60px;">
    <ul>
           <li style="line-height:28px; margin-right:20px;">
             <span>
              <a>
                 <span style=" font-size:16px; font-weight:normal; background:#be9964;width:80px; height:20px; line-height:20px; text-align:center; color:#fff; border-radius:10px; display:inline-block;">累计奖励</span>
               </a>
             </span>
           </li>
          <li style=" line-height:16px; margin-top:3px; padding-left:30px;">
           <span style=" margin-right:85px;">推荐注册奖励：&nbsp;<font style=" color:#D14324; font-size:20px;">${(totalRegisterMoney?string.currency)!'￥0.00'}元</font></span>
           <span style=" margin-right:85px;">好友投资奖励：&nbsp;<font style=" color:#D14324; font-size:20px;">${(totalMoney?string.currency)!'￥0.00'}元</font></span>
          </li>
    </ul>
  </li>
  <li style="  margin-bottom:10px;">
    <ul>
           <li style="line-height:28px;margin-right:20px;">
             <span>
               <a>
                 <span style=" font-size:16px; font-weight:normal; background:#be9964;  width:80px; height:20px; line-height:20px; text-align:center; color:#fff; border-radius:10px; display:inline-block;">邀请链接</span>
               </a>
             </span>
           </li>
          <li style="padding-left:0;float:left;">
        <#--  <#if loginUser.tgNo==''>
                <span style="color:#0000ff; text-decoration:underline;">${Application ["qmd.setting.url"]}/fd/</span>
                <input type="text" onfocus="if(this.value == '8位数字或字母'){this.value = ' '}" onblur="if(this.value == ' '){this.value = '8位数字或字母'}" javascript="" maxlength="12" value="8位数字或字母" style="width:120px; height:22px; line-height:22px; margin-right:3px; padding-left:2px; color:#666666;border: 1px solid #7d7d7d;" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" name="user.tgNo" id="tgNo" class="kaqu_anquanmima">
                <span style="color:#0000ff; text-decoration:underline; margin-right:10px;">.htm</span>
                <a href="javascript:void(0);" id = "getLinkId" onclick="javascript:get_remark_link();" style=" background:url(${base}/static/img/a25.png) no-repeat 0px 0px; width:64px; height:28px; line-height:28px; text-align:center; color:#fff; font-size:14px; display:inline-block;"  id="getLinkId" href="javascript:void(0);">确定</a>
          <#else>
          	<input type="text" class="kaqu_anquanmima"  style="width:300px; height:22px; line-height:22px; margin-right:3px; padding-left:2px; color:#666666;border: 1px solid #7d7d7d;" id = "link" value = "<#if loginUser.tgNo!>${Application ["qmd.setting.url"]}/fd/${loginUser.tgNo}.htm</#if>" readOnly>
				
	         </#if>-->
	         
	         
	         	
			<input type="button" value="复制邀请链接" data-clipboard-target="fe_text" id="d_clip_button" style="margin-top:10px;float:left;"/>
		
			<div style="margin-top:18px;float:left;"><span id="fe_text">${Application ["qmd.setting.url"]}/${loginUser.tgNo}</span></div>
			
			<div class="bdsharebuttonbox" style="float:left;margin-left:10px;margin-top:15px;">
							
				<a class="bds_tsina" title="分享到新浪微博" href="#" data-cmd="tsina"></a>
				<a class="bds_tqq" title="分享到腾讯微博" href="#" data-cmd="tqq"></a>
				<a class="bds_renren" title="分享到人人网" href="#" data-cmd="renren"></a>
				<a class="bds_t163" title="分享到网易微博" href="#" data-cmd="t163"></a>
				<a class="" href="#" data-cmd="more"></a>
	<#--			<a class="bds_more" href="#" data-cmd="more"></a>-->
			</div>
			 <!-- JiaThis Button BEGIN -->
			</li>
      </ul>
  </li>
     <li style="margin-bottom:10px;clear:both;padding-top:15px;">
    	<ul>
           <li style="line-height:28px;margin-right:20px;margin-bottom:10px;">
             <span>
               <a>
                 <span style=" font-size:16px; font-weight:normal; background:#be9964;  width:80px; height:20px; line-height:20px; text-align:center; color:#fff; border-radius:10px; display:inline-block; font-size:14px;">二维码</span>
               </a>
             </span>
           </li>
           <li>
		        <div class="qrcodeTable" style="margin-left:0;float:left;"></div> 
		        <div style="margin-left:20px;margin-top:30px;float:left;">使用微信扫一扫，分享到朋友圈<br/>或者发送给朋友，分享我就爱车</div>
           </li>
      </ul>
   </li>
   
     <li style="  margin-bottom:10px;clear:both;padding-top:15px;">
      <ul>
           <li style="line-height:28px;margin-right:20px;">
             <span>
               <a>
                 <span style=" font-size:16px; font-weight:normal; background:#be9964; width:80px; height:20px; line-height:20px; text-align:center; color:#fff; border-radius:10px; display:inline-block;">奖励说明</span>
               </a>
             </span>
           </li>
          <li style="padding-left:0;">
           <ul>
                <li> 一、<font style="font-weight:bold;">推荐注册奖励</font></li> 
                <li>好友完成“实名认证”、“邮箱认证”后，将获得20元红包奖励！（<a target="_blank" href="${base}/article/content/483.htm"  style="color:#646464;font-family:'宋体';">如何使用红包？</a>）</li><br>
                <li>二、<font style="font-weight:bold;">好友投资奖励</font></li>  
                <li> 获得好友投资额*${listNum}%*投资标月数的红包奖励；</li> 
            </ul>
          </li>
       </ul>
  </li>
 </ul>     
        <form id="listForm" method="post" action="${base}/spread/toGetLink.do<#if n!=''>?n=${n}</#if>">
						<#--<div style="" class="kaqu_w90">
							好友用户名：<input type = "text" id = "uname" name = "uname" value = "${(uname)!}" class="kaqu_w100" style = "width :105px">
							<input type="button" onclick="gotoPage(1);" class = "kaqu_w120" value = "搜索"/> 所属上级：${user.username} <#if user.tgOneLevelUserId == loginUser.id><a href = "${base}/spread/toGetLink.do">返回上级</a></#if>
						</div>
						 <div style="font-family:'宋体'; color:#000; margin-top:80px; clear:both;">
			            <div style="float:left;">   
			                  <span style="color:#666666;">好友用户名：</span>
			                  <input type="text" id = "uname" name = "uname" value = "${(uname)!}"  class="kaqu_anquanmima" style="width:130px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
			                  <input type="button" onclick="gotoPage(1);" style="display:inline-block; width:65px; height:35px; line-height:35px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" value = "搜索"/>
			                  <#if user.tgOneLevelUserId != loginUser.id><span style="margin-left:20px;">总好友数量：<font style="color:red;">${totalCount}</font></span></#if>
			               </div>
			               <div style="float:right; margin-top:5px;">
			                  <span style="color:#ea5504; font-size:14px; margin-right:45px;">所属上级：${user.username}</span>
			                  <#if user.tgOneLevelUserId == loginUser.id><a href = "${base}/spread/toGetLink.do" style="font-size:14px;color:#666666; ">返回上级</a></#if>
			               </div>
			         </div>
			       	<div class="sepblk_w"></div>-->
			       	
			       	
						<div class="tableblk" style="width:100%;margin:20px 0;">
						<div style="font-size:18px;padding-bottom:6px;"><span>好友管理</span></div>
					<table width="100%" cellpadding="0" cellspacing="0"  style="border:0px solid #c6c6c6; border-right:none; border-bottom:none;">
						<thead bgcolor="#efebdf" align="center">
					    
							<tr height="36">
		                    		<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">好友用户名</th>
									<#--<th>好友类型</th>-->
									<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">加入时间</th>
									<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">账户认证</th>
									<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">邮箱认证</th>
									<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">推荐注册奖励</th>
									<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">好友投资红包</th>
							<#--		<th>操作</th>-->
								</tr>
							</thead>
							<tbody align="center">
		                    	<#list pager.result as us>
		                    		<#if (us.id)!>
				                    	<#assign flag = "">
										<#if us_index %2 != 0>
											<#assign flag = "td1">
										</#if>
										<tr height="39">
											<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
												<#if us.tgOneLevelUserId == loginUser.id>
													${us.username}
												<#else>
													${us.username?substring(0,1)}****${us.username?substring(us.username?length-1,us.username?length)}
												</#if>
											</td>
											<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(us.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</td>
											<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><#if us.realStatus ==1>&#8730;<#else>&#935;</#if></td>
											<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><#if us.emailStatus ==1>&#8730;<#else>&#935;</#if></td>
											<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><#if (us.tgStatus ==1)>${(us.sumRegisterMoney?string.currency)!'￥0.00'}<#else>--</#if> </td>
											<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(us.sumTgMoney?string.currency)!'￥0.00'}</td>
										</tr>
									</#if>
								</#list>       
								</tbody>                 
							</table>
							</div>
		                        <@pageFlip pager=pager>
									<#include "/content/common/pager.ftl">
								</@pageFlip>
					</form>
			</div>
     <div style=" clear:both; padding-bottom:60px;"></div>
    <div style=" clear:both"></div>
    <div style=" background-image:url(${base}/static/img/3_bg.png); background-position:0 bottom; background-repeat:repeat-x; height:9px;"></div>
   
   
</div><!--user_content end-->
<div style=" clear:both; padding-bottom:50px;"></div>
</div><!--user_middle end-->
<div style=" clear:both"></div>
<#include "/content/common/footer.ftl">

<script type="text/javascript">
	$().ready( function() {
		
		$("#menuUser").addClass("kaqu_bg");
		$("#menuUser_ul").addClass("user_content_top1");
		$("#menuUser_SpreadList").addClass("user_dlk");
		 
	});
</script>

<script>
	window._bd_share_config = {
		common : {
			bdText : '亲，我刚在${Application ["qmd.setting.name"]}取回了一笔收益，好消息当然要与你分享。${Application ["qmd.setting.name"]}收益高，且通过“多重本息保障”机制，提供全额的履约担保服务，保证投资人的本金和利息的安全，最近${Application ["qmd.setting.name"]}引入了首轮千万融资，投资门槛低，收益高，这样的好事当然要和你分享，独乐乐不如众乐乐，快注册看下哦！',	
			bdDesc : '亲，我刚在${Application ["qmd.setting.name"]}取回了一笔收益，好消息当然要与你分享。${Application ["qmd.setting.name"]}收益高，且通过“多重本息保障”机制，提供全额的履约担保服务，保证投资人的本金和利息的安全，最近${Application ["qmd.setting.name"]}引入了首轮千万融资，投资门槛低，收益高，这样的好事当然要和你分享，独乐乐不如众乐乐，快注册看下哦！',	
			bdUrl : '<#if loginUser.tgNo!>${Application ["qmd.setting.url"]}/${loginUser.tgNo}<#else>${Application ["qmd.setting.url"]}</#if>', 	
			bdPic : 'http://www.swdai.com/static/img/a4.png'
		},
		share : [{
			"bdSize" : 16
		}],
		image : [{
			viewType : 'list',
			viewPos : 'top',
			viewColor : 'black',
			viewSize : '16',
			viewList : ['qzone','tsina','huaban','tqq','renren']
		}],
		selectShare : [{
			"bdselectMiniList" : ['qzone','tqq','kaixin001','bdxc','tqf']
		}]
	}
	with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?cdnversion='+~(-new Date()/36e5)];
	
$(function(){
	$("#member_friend").addClass("nsg nsg_a1");
});
</script>

	
<script src="/static/js/common/ZeroClipboard.js"></script>
<script type="text/javascript">
    // 定义一个新的复制对象
    var clip = new ZeroClipboard( document.getElementById("d_clip_button"), {
        moviePath: qmd.base+"/static/js/common/ZeroClipboard.swf"
    } );
    // 复制内容到剪贴板成功后的操作
    clip.on( 'complete', function(client, args) {
        alert("复制成功!");
    } );
    $(function(){		
		$(".qrcodeTable").each(function(){
			$(this).qrcode({text: '${Application ["qmd.setting.url"]}/p_${loginUser.tgNo}',width:100,height:100});
		});
		
	});
</script>

</body>
</html>
