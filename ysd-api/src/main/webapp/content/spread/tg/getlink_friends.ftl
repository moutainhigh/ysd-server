<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-我的账户-好友管理</title>
	<#include "/content/common/meta.ftl">
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
			<a href="${base}/">${Application ["qmd.setting.name"]}</a><a>></a>
			<a href="${base}/userCenter/index.do">我的账户</a><a>></a>
			<a class="liebiao">好友管理</a>
		</div>
		 <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
           <a href="javascript:void(0);" class="hr hre">好友管理</a>
			<a  class="hr" href="${base}/spread/registerAward.do">红包明细</a>
			<a class="hr " href="${base}/spread/total.do">投资奖励明细</a>   
          </div>
				
         <ul style=" margin-bottom:0px; font-size:14px; width: 724px; padding:20px; color:#424242; margin-top:0px; background:#fafafa;">
  <li style="  margin-bottom:10px;">
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
          <li style="padding-left:30px;">
          <#if loginUser.tgNo==''>
                <span style="color:#0000ff; text-decoration:underline;">${Application ["qmd.setting.url"]}/fd/</span>
                <input type="text" onfocus="if(this.value == '8位数字或字母'){this.value = ' '}" onblur="if(this.value == ' '){this.value = '8位数字或字母'}" javascript="" maxlength="12" value="8位数字或字母" style="width:120px; height:22px; line-height:22px; margin-right:3px; padding-left:2px; color:#666666;border: 1px solid #7d7d7d;" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" name="user.tgNo" id="tgNo" class="kaqu_anquanmima">
                <span style="color:#0000ff; text-decoration:underline; margin-right:10px;">.htm</span>
                <a href="javascript:void(0);" id = "getLinkId" onclick="javascript:get_remark_link();" style=" background:url(${base}/static/img/a25.png) no-repeat 0px 0px; width:64px; height:28px; line-height:28px; text-align:center; color:#fff; font-size:14px; display:inline-block;"  id="getLinkId" href="javascript:void(0);">确定</a>
          <#else>
          	<input type="text" class="kaqu_anquanmima"  style="width:300px; height:22px; line-height:22px; margin-right:3px; padding-left:2px; color:#666666;border: 1px solid #7d7d7d;" id = "link" value = "<#if loginUser.tgNo!>${Application ["qmd.setting.url"]}/fd/${loginUser.tgNo}.htm</#if>" readOnly>
				  <#--<a href="javascript:void(0);" id = "copy1" onclick="javascript:copytext('link');" style=" background:url(${base}/static/img/f7.png) no-repeat 0px 0px; width:64px; height:28px; line-height:28px; text-align:center; color:#fff; font-size:14px; display:inline-block;">复制</a>-->
				<div class="bdsharebuttonbox" style="float:left;margin-left:10px;">
								
					<a class="bds_tsina" title="分享到新浪微博" href="#" data-cmd="tsina"></a>
					<a class="bds_tqq" title="分享到腾讯微博" href="#" data-cmd="tqq"></a>
					<a class="bds_renren" title="分享到人人网" href="#" data-cmd="renren"></a>
					<a class="bds_t163" title="分享到网易微博" href="#" data-cmd="t163"></a>
					<a class="bds_more" href="#" data-cmd="more"></a>
				</div>
	         </#if>
          </li>
      </ul>
  </li>
    <li style="  margin-bottom:10px;">
      <ul>
           <li style="line-height:28px;margin-right:20px;">
             <span class="kaqu_sx1">
               <a>
                 <span style=" font-size:16px; font-weight:normal; background:#be9964;  width:80px; height:20px; line-height:20px; text-align:center; color:#fff; border-radius:10px; display:inline-block;">提示</span>
               </a>
             </span>
           </li>
          <li style="padding-left:30px;">
            <ul>
            <li>1、您可自行设置好友邀请链接，邀请链接首次生成后将无法修改；</li>
            <li>2、您的好友使用该链接完成注册后，将自动成为您的一级好友；</li>
 <#--           <l>3、经您一级好友所推荐加入卡趣网的会员，将自动成为您的二级好友。</li>-->
            </ul>
          </li>
       </ul>
  	</li>       
     <li style="  margin-bottom:10px;">
      <ul>
           <li style="line-height:28px;margin-right:20px;">
             <span>
               <a>
                 <span style=" font-size:16px; font-weight:normal; background:#be9964; width:80px; height:20px; line-height:20px; text-align:center; color:#fff; border-radius:10px; display:inline-block;">奖励说明</span>
               </a>
             </span>
           </li>
          <li style="padding-left:30px;">
           <ul>
                <li> 一、<font style="font-weight:bold;">推荐注册奖励</font>：以下奖励进入<font style="font-weight:bold;">“红包账户”</font>（<a target="_blank" href="http://www.swdai.com/hongbao.do">如何使用红包？</a>）</li> 
                <li>好友完成“实名认证”、“邮箱认证”并完成首次投资后，将获得1000元奖励！</li><br>
                <li>二、<font style="font-weight:bold;">好友投资奖励</font>：以下奖励进入<font style=" font-weight:bold;">“可用账户”</font></li>  
                <li> 获得好友投资额*${listNum}%*投资标月数的奖励；</li> 
            <#--    <li> （2）获得二级好友投资额最高0.1%的奖励；</li> 
                <li style="width:680px"> （2）好友首次投资，推广人除获得第（1）条的奖励外，还将额外获得好友投资额的1%，最高不超过100元的奖励。</li> -->
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
						-->
						 <div style="font-family:'宋体'; color:#000; margin-top:80px; clear:both;">
			               <div style="float:left;">   
			                  <span style="color:#666666;">好友用户名：</span>
			                  <input type="text" id = "uname" name = "uname" value = "${(uname)!}"  class="kaqu_anquanmima" style="width:130px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
			                  <input type="button" onclick="gotoPage(1);" style="display:inline-block; width:65px; height:35px; line-height:35px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" value = "搜索"/>
			                  <#if user.tgOneLevelUserId != loginUser.id><span style="margin-left:20px;">总好友数量：<font style="color:red;">${totalCount}</font></span></#if>
			               </div>
			               <div style="float:right; margin-top:5px;">
			                  <#--<span style="color:#ea5504; font-size:14px; margin-right:45px;">所属上级：${user.username}</span>-->
			                  <#if user.tgOneLevelUserId == loginUser.id><a href = "${base}/spread/toGetLink.do" style="font-size:14px;color:#666666; ">返回上级</a></#if>
			               </div>
			         </div>
			       	<div class="sepblk_w"></div>
						<div class="tableblk">
					<table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
						<thead bgcolor="#efebdf" align="center">
					      <tr height="35">
							<tr height="36">
		                    		<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">好友用户名</th>
									<#--<th>好友类型</th>-->
									<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">加入时间</th>
									<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">账户认证</th>
									<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">邮箱认证</th>
									<#--<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">是否投资</th>-->
									<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">推荐注册奖励</th>
									<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">好友投资奖励</th>
							<#--		<th>操作</th>-->
								</tr>
							</thead>
							<tbody>
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
											<#--<td class = "${flag}">
												<#if us.tgOneLevelUserId == loginUser.id>一级<#else>二级</#if>
											</td>-->
											<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(us.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</td>
											<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><#if us.realStatus ==1>是<#else>否</#if></td>
											<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><#if us.emailStatus ==1>是<#else>否</#if></td>
											<#--<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><#if (us.sumTenderMoney >0)>是<#else>否</#if></td>-->
											<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><#if (us.tgStatus ==1)>${(us.sumTenderMoney?string.currency)!'￥0.00'}<#else>￥0.00</#if> </td>
											<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(us.sumTgMoney?string.currency)!'￥0.00'}</td>
										<#--<td class = "${flag}"><#if us.tgOneLevelUserId == loginUser.id><a href = "${base}/spread/toGetLink.do?n=${us.id}">【所属下级】</a></#if></td>-->
										</tr>
									</#if>
							</tbody>
								</#list>                        
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
	
	function get_remark_link(){
		var tgNo = $.trim($("#tgNo").val());
		if(tgNo =='8位数字或字母'){
			alert('请输入8位数字或字母');
			return;
		}
		if(tgNo.length < 4 ){
			alert('至少输入4位数字或字母');
			return;
		}
		if(tgNo.length > 8 ){
			alert('最多输入8位数字或字母');
			return;
		}
		if (confirm("生成链接后将无法修改，确认生成吗？")) {
			$.ajax({
				url: "${base}/spread/ajaxGetLink.do",
				data: {"user.tgNo":tgNo},
				type: "POST",
				dataType: "json",
				cache: false,
				 success: function(data, textStatus){
					if(data.status =='success'){
						window.location.reload();
					}else{
						alert(data.message);
						return false;
					}
				},
		        error:function(statusText){
		        	alert(statusText);
		        },
		        exception:function(){
		        	alert(statusText);
		        }
			});
		}
	}
	
//复制命令
function copytext(inputid)
{
	var text=document.getElementById(inputid);
	if(text.value==""){alert("没有内容");text.focus();return;}
	text.select(); //选择对象
	copyToClipboard(text.value);
}

function copyToClipboard(txt,flag) {
    if(window.clipboardData) {
            window.clipboardData.clearData();
            window.clipboardData.setData("Text", txt);
            alert("已复制好，可贴粘。");
       	  	return;
    } else if(navigator.userAgent.indexOf("Opera") != -1) {
         window.location = txt;
    }else if(window.navigator.userAgent.indexOf("Chrome") !== -1){
            alert("对不起，谷歌浏览器无法使用一键复制功能,请手动复制或使用快捷键CTRL+C复制内容");
    } else if (window.netscape) {
         try {
              netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
         } catch (e) {
           alert("如果您正在使用FireFox！请在浏览器地址栏输入about:config并回车,然后将 signed.applets.codebase_principal_support设置为true");
         }
         var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
         if (!clip)
              return;
         var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
         if (!trans)
              return;
         trans.addDataFlavor('text/unicode');
         var str = new Object();
         var len = new Object();
         var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
         var copytext = txt;
         str.data = copytext;
         trans.setTransferData("text/unicode",str,copytext.length*2);
         var clipid = Components.interfaces.nsIClipboard;
         if (!clip)
              return false;
         clip.setData(trans,null,clipid.kGlobalClipboard);
         alert("已复制好，可贴粘。");
         return;
    }
}
</script>

<script>
	window._bd_share_config = {
		common : {
			bdText : '亲，我刚在我就爱车取回了一笔收益，好消息当然要与你分享。我就爱车收益高，且通过“多重本息保障”机制，提供全额的履约担保服务，保证投资人的本金和利息的安全，最近我就爱车引入了首轮千万融资，投资门槛低，收益高，这样的好事当然要和你分享，独乐乐不如众乐乐，快注册看下哦！',	
			bdDesc : '亲，我刚在我就爱车取回了一笔收益，好消息当然要与你分享。我就爱车收益高，且通过“多重本息保障”机制，提供全额的履约担保服务，保证投资人的本金和利息的安全，最近我就爱车引入了首轮千万融资，投资门槛低，收益高，这样的好事当然要和你分享，独乐乐不如众乐乐，快注册看下哦！',	
			bdUrl : '<#if loginUser.tgNo!>${Application ["qmd.setting.url"]}/fd/${loginUser.tgNo}.htm<#else>${Application ["qmd.setting.url"]}</#if>', 	
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



</body>
</html>
