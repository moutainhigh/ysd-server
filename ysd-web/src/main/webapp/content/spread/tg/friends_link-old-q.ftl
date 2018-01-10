<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-我的账户-邀请好友</title>
	<#include "/content/common/meta.ftl">
<script type="text/javascript" src="/static/js/common/jquery.qrcode.js"></script> 
<script type="text/javascript" src="/static/js/common/qrcode.js"></script> </head>
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">


<#include "/content/common/user_center_header.ftl">

<div class="content">
  <div style="width:1200px; margin:0 auto; padding-bottom:60px; margin-top:20px;">
    <!--left-->
	<#include "/content/common/user_center_left.ftl">
    <!--right-->
    
    <div style="width:942px; float:right;background:#fff;color:#4c4c4c;">
      <div style=" width:880px; margin:30px auto 0; padding-bottom:42px;border-bottom:1px solid #c6c6c6;overflow:hidden;">
        <ul style="float:left;">
          <li style="margin-bottom:32px;"><span style=" font-size:16px;color:#4c4c4c;">复制邀请链接发给好友</span></li>
          <li>
          <input type="button" value="复制邀请链接" data-clipboard-target="fe_text" id="d_clip_button" style="width:140px; height:40px; line-height:40px; text-align:center;; color:#fff; font-size:14px;margin-right:20px; background:#fd7c1a; border-radius:3px; display:inline-block; cursor:pointer;"/>
            <input type="text" class="form-control" id="fe_text" placeholder="https://www.yueshanggroup.cn/fd/${loginUser.tgNo}" aria-describedby="basic-addon1" value="https://www.yueshanggroup.cn/fd/${loginUser.tgNo}" disabled="disabled" style="width:380px;color:#000; display:inline-block; height:38px; border-radius:3px;line-height:38px; border:1px solid #dbdbdb; background:#f5f5f5; padding-left:10px;" />
          </li>
    	</ul>
          <ul style="float:right; margin-right:20px;">
            <li style="margin-bottom:32px;"><span style=" font-size:16px;color:#4c4c4c;">通过社交网站邀请好友</span></li>
            <div class="bdsharebuttonbox" style="float:left;margin-left:10px;margin-top:6px;">
						<a class="bds_tsina" title="分享到新浪微博" href="#" data-cmd="tsina"></a>
						<a class="bds_qzone" title="分享到QQ空间" href="#" data-cmd="qzone"></a>
						<a class="bds_tqq" title="分享到腾讯微博" href="#" data-cmd="tqq"></a>
						<a class="bds_renren" title="分享到人人网" href="#" data-cmd="renren"></a>
						<#--<a class="" href="#" data-cmd="more"></a>-->
					</div>
          </ul>
        </div>
           <div style=" width:880px; margin:30px auto 0; padding-bottom:42px;border-bottom:1px solid #c6c6c6;overflow:hidden;">
             <div class="qrcodeTable" style="float:left; width:101px;height:101px;"></div>
             <div style="float:left;margin-left:20px; line-height:25px;font-size:14px;"><span style="font-size:16px;">通过微信推荐好友</span><br />第一步：打开微信，点击右上角“+”号，点击扫一扫<br />第二步：发给朋友，分享投资梦</div>
          </div>
          <div style=" width:880px; margin:22px auto 0; line-height:30px;font-size:14px;">
            <h3 style="font-size:16px;font-weight:normal; margin-bottom:6px;">邀请好友奖励细则：</h3>
            	
            	${setting.offLineRechargeDes}
           
         </div>
         <div style="width:880px; margin:50px auto 90px;font-size:14px;">
             <table width="100%" cellpadding="0" cellspacing="0" border="0">
               <thead bgcolor="#fd7c1a">
                 <tr height="38">
                   <th style="color:#fff; ">好友手机</th>
                   <th style="color:#fff;">加入时间</th>
                   <th style="color:#fff;">账户认证</th>
                   <th style="color:#fff;">投资奖励</th>
                 </tr>
               </thead>
               <tbody align="center">
               <#list pager.result as us>
                  <tr height="72">
                    <td style="border-bottom:1px solid #e6e6e6;border-left:1px solid #e6e6e6; color:#666; ">${us.username}</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666;">${us.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666;">
	                    <#if us.realStatus ==1>
							<img src="/img/yes.png" alt="" width='28' height='28' />
						<#else>
	                      <img src="/img/no.png" alt="" width='28' height='28' />
	                    </#if>
                    </td>
                    <td style="border-bottom:1px solid #e6e6e6;border-right:1px solid #e6e6e6; color:#fd7c1a; ">${(us.uadSumMoney?string.currency)!'0.00'}</td>
                  </tr>
               </#list>
               </tbody>
             </table>
           </div>
    </div>
    
    
    <div style="width:100%;clear:both; height:1px;"></div>
  </div>
</div><!-- content end -->     

<#include "/content/common/footer.ftl">
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
	
			
			
			$("#header_wdzh").addClass("hq");<#-- header.ftl 选中样式 -->
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
			$(this).qrcode({text: 'https://www.yueshanggroup.cn/fd/${loginUser.tgNo}',width:100,height:100});
		});
		
	});
</script>
</body>
</html>
