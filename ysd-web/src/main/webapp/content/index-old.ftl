<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台</title>
	<#include "/content/common/meta.ftl">
	<script src="${base}/js/scroll.js"></script>
	
	
</head>
<body >
 <!-- header -->
<#include "/content/common/header.ftl">
<!--focus start-->
<div class="focus" id="focus">
	<div id="focus_m" class="focus_m">
		<ul>
		<#list scrollpicList as scrollpic>
				<li style="background: url(<@imageUrlDecode imgurl="${scrollpic.img}"; imgurl>${imgurl}</@imageUrlDecode>) no-repeat center center rgb(<#if scrollpic.remark?exists && scrollpic.remark !=''>${scrollpic.remark}<#else>255,255,255</#if>) ;"  onclick="javascript:window.location.href='<@scrollpic.url?interpret />';"></li>
		</#list>
	</ul>		 
	</div>
	<a title="上一张" hidefocus="true" id="focus_l" class="focus_l" href="javascript:;"><b></b><span></span></a>
	<a title="下一张" hidefocus="true" id="focus_r" class="focus_r" href="javascript:;"><b></b><span></span></a>
</div>
	<script src="${base}js/slides.js"></script>
<div class="content">
    <div class="bgc"> 
      <div style="width:1190px; margin:0px auto;overflow:hidden;">
        <div style="width:838px;padding:52px 0px 42px 0; background:#fff; float:left;">
          <div style=" float:left; width:198px; padding-left:110px; background:url(img/f1.png) 48px 6px no-repeat;">
             <div class="color" style=" font-size:24px;margin-bottom:5px;"><@listing_keyValue sign="index_report_statistics_user_count" mold="NC"; kv>${kv}</@listing_keyValue></div>
             <div style="color:#666;font-size:14px;">精明的理财人已经加入</div>
          </div>
          <div style=" float:left;width:215px;padding-left:65px;background:url(img/f2.png) -0 6px no-repeat;">
             <div class="color" style=" font-size:24px; margin-bottom:5px;"><@listing_keyValue sign="index_report_statistics_tender" mold="NC"; kv>${kv}</@listing_keyValue></div>
             <div style="color:#666;font-size:14px;">累计投资金额</div>
          </div>
          <div style=" float:left;width:185px;padding-left:65px;background:url(img/f3.png) -0 6px no-repeat;">
             <div class="color" style=" font-size:24px;margin-bottom:5px;"><@listing_keyValue sign="index_report_statistics_interest" mold="NC" point="0"; kv>${kv}</@listing_keyValue></div>
             <div style="color:#666;font-size:14px;">为用户赚取收益</div>
          </div>
        </div>
         <div style=" float:right;width:336px;border-left:1px solid #e6e6e6;margin:25px 0px;padding:17px 0;background:url(img/f4.png) 40px 20px no-repeat #fff;">
         	<div style="color:#666666;font-size:18px; padding-left:130px; margin-bottom:8px;">下载手机客户端</div>
              <div style="padding-left:120px;">
               <a href="${base}/download.html"><span style="background:url(img/a2.png) 0 0 no-repeat; width:92px; height:30px; display:inline-block;"></span></a>
               <a href="${base}/download.html"><span style="background:url(img/a2.png) -92px 0 no-repeat; width:92px; height:30px; display:inline-block;"></span></a>
             </div>
        </div>
      </div>
    </div>
    
    <div style="width:1190px; margin:0 auto; padding-bottom:60px;">
    <#if xsBorrowPledgeList?exists && xsBorrowPledgeList?size gt 0 >
  <div style="clear:both; width:1190px; margin-bottom:20px;">
    <div style="width:1188px;background:#fff; border:1px solid #e6e6e6;float:left;  ">
      <div style=" width:218px;position:relative; z-index:1;float:left;">
        <div style="background:url(img/a4.png); width:218px; height:196px; position:absolute; left:0; top:0px;">
          <div style="width:203px; text-align:center;color:#fff; font-size:14px; ">
            <div style=" padding:72px 0 0 0;"> 限未投资过新手标的用户</div>
            <div style="">仅享有一次机会</div>
          </div>
        </div>
      </div>
      <div style="width:970px; float:left; margin-left:218px;">
       <div style="width:920px; height:196px; margin:0 auto;">
        <div style="font-size:18px; padding-left:0; padding:10px 0; border-bottom:1px solid #e6e6e6;"><a href="${base}/borrow/detail.do?bId=${xsBorrowPledgeList.get(0).id}" style=" color:#666666;">${xsBorrowPledgeList.get(0).name}</a></div>
        <div style="padding:34px 0 14px 0; width:900px; float:left;">
           <ul style="float:left; width:150px;">
             <li style="color:#333; font-size:24px; margin-bottom:7px;font-weight:bold;">
             <#if xsBorrowPledgeList.get(0).account?number gt 10000>
            	 ${((xsBorrowPledgeList.get(0).account?number)*0.0001)}<font style="font-size:18px;font-weight:normal;">万</font>
            <#else>${xsBorrowPledgeList.get(0).account}元</#if>
             <li style="color:#808080; font-size:16px;">总金额</li>
           </ul>
           <ul style="float:left; width:180px;">
            <li class="color" style="font-size:24px; margin-bottom:7px;font-weight:bold;"><@numberPercent val="${xsBorrowPledgeList.get(0).baseApr}"; npt>${npt?substring(0,npt?length-1)}</@numberPercent><font style="font-size:16px;color:#808080;font-weight:normal;">%</font>
             <#if xsBorrowPledgeList.get(0).awardApr gt 0>
            	<span style="background:url(img/9.png) no-repeat;color:#fff;font-size:16px;padding:0 10px;font-weight:normal;width: 69px;  display: inline-block;">+<@numberPercent val="${xsBorrowPledgeList.get(0).awardApr}"; npt>${npt}</@numberPercent></span></li>
             </#if>
             <li style="color:#808080; font-size:16px;">年化收益</li>
           </ul>
           <ul style="float:left; width:160px;">
             <li style="color:#333; font-size:24px; margin-bottom:7px;font-weight:bold;">${xsBorrowPledgeList.get(0).timeLimit}<font style="font-size:16px;font-weight:normal;">天</font></li>
             <li style="color:#808080; font-size:16px;">理财期限</li>
           </ul>
           <ul style="float:left; width:150px;">
             <li style="color:#333; font-size:24px; margin-bottom:7px;font-weight:bold;">
             <#if xsBorrowPledgeList.get(0).balance?number gt 10000>
            	 ${((xsBorrowPledgeList.get(0).balance?eval)*0.0001)?string("0.00")}<font style="font-size:18px;font-weight:normal;">万</font>
            <#else>${xsBorrowPledgeList.get(0).balance?eval?string("0.00")}<font style="font-size:16px;font-weight:normal;">元</font></#if>
          </li>
             <li style="color:#808080; font-size:16px; ">剩余金额</li>
           </ul>
           <ul style="width:120px; float:left;">
             <li>
              <div class="circle" style="width:62px;height:62px;">
                <canvas width="62" height="62"></canvas>
                <div class="tac f12 color" style="width:100%;margin-top:-42px;">
                    <span style="font-size:16px;">${xsBorrowPledgeList.get(0).schedule}</span>%
                </div>
              </div>
            </li>
           </ul>
           <ul style="float:right; margin-top:20px; width:115px; margin-right:20px;">
             <li><a href="${base}/borrow/detail.do?bId=${xsBorrowPledgeList.get(0).id}" class="lijitoubiao_s1 <#if xsBorrowPledgeList.get(0).showButtonName=='已过期'>lijitoubiao_s2<#elseif xsBorrowPledgeList.get(0).status ==1 ><#else> lijitoubiao_s2</#if>">${xsBorrowPledgeList.get(0).showButtonName}</a></li>
           </ul>
        </div>
        </div>
      </div>
    </div>
  </div>
  </#if>
  
  <div style="width:1190px; clear:both;">
    <div style="width:1190px; float:left; margin-top:20px;">
     <div style="width:838px;padding:0 0 0 0; background:#fff; float:left;border:1px solid #e6e6e6;border-bottom:0;">
          <div style="float:left; clear:both; width:838px; height:44px; line-height:44px; border-bottom:1px solid #e6e6e6;">
           <span style="color:#ff7f00; font-size:16px; padding:0 10px 0 13px;">特色理财区 |</span>
           <!--
           <span style="color:#666666; font-size:14px;">投资人次 <@listing_keyValue sign="index_report_statistics_detail_count" mold="NC" point="0"; kv>${kv}</@listing_keyValue>次    工作日 10:00、13:00、16:00、19:00、21:00 更新</span>
           -->
          </div>
          
	 <#if borrowPledgeList?exists && borrowPledgeList?size gt 0 >
      <#list borrowPledgeList as borrow> <#if borrow_index gt 4><#break> </#if>
          <div style="width:838px; float:left; padding:10px 0 35px 0; border-bottom:1px solid #e6e6e6; background:#fff;" class="fabiao_v1">
            <div style="padding:10px 28px 10px 35px; width:775px; float:left;">
               <h3 style="font-weight:normal; color:#666666; font-size:18px; margin-bottom:24px;"><img src="img/a5.png" width="20" height="20" style="margin-top: -4px;" /> ${borrow.name}</h3>
               <ul style="float:left; width:195px;">
                 <li class="color" style="font-size:24px; margin-bottom:7px;font-weight:bold;">
                 	<@numberPercent val="${borrow.baseApr}"; npt>${npt?substring(0,npt?length-1)}</@numberPercent><font style="font-size:16px;color:#808080;font-weight:normal;">%</font>
                 	 <#if borrow.awardApr gt 0>
                 	<span style="background:url(img/9.png) no-repeat;color:#fff;font-size:16px;padding:0 10px;font-weight:normal;width: 69px;  display: inline-block;">+<@numberPercent val="${borrow.awardApr}"; npt>${npt}</@numberPercent></span>
                 	</#if>
                 </li>
                 <li style="color:#808080; font-size:16px;">年化收益</li>
               </ul>
               <ul style="float:left; width:160px;">
                 <li style="color:#333; font-size:24px; margin-bottom:7px;font-weight:bold;">
	                 <#if borrow.account?number gt 10000>
	            	 ${((borrow.account?eval)*0.0001)?string("0.00")}<font style="font-size:16px;color:#808080;font-weight:normal;">万元</font>
	            	<#else>${borrow.account?eval?string("0.00")}<font style="font-size:16px;color:#808080;font-weight:normal;">元</font></#if>
                </li>
                 <li style="color:#808080; font-size:16px;">总金额</li>
               </ul>
               <ul style="float:left; width:150px;">
                 <li style="color:#333; font-size:24px; margin-bottom:7px;font-weight:bold;">${borrow.timeLimit}<font style="font-size:16px;color:#808080;font-weight:normal;">天</font></li>
                 <li style="color:#808080; font-size:16px;">理财期限</li>
               </ul>
               <ul style="width:130px; float:left;">
                 <li>
                    <div class="circle" style="width:62px;height:62px;">
                      <canvas width="62" height="62"></canvas>
                      <div class="tac f12 color" style="width:100%;margin-top:-42px;">
                          <span  style="font-size:1.35em;font-weight:bold;">${borrow.schedule}</span>%
                      </div>
                    </div>
                 </li>
               </ul>
               <ul style="float:right; margin-top:0; width:135px; margin-right:0;">
                 <li><a href="${base}/borrow/detail.do?bId=${borrow.id}" class="lijitoubiao_s1 <#if borrow.showButtonName == "已过期">lijitoubiao_s2<#elseif borrow.status ==1> <#else>lijitoubiao_s2</#if>">${borrow.showButtonName}</a></li>
               </ul>
             </div>
             <div style="width:100%;clear:both;"></div>
        </div>
         </#list>
       <#else>
        <div style="width:838px; float:left; padding:10px 0 35px 0; border-bottom:1px solid #e6e6e6; background:#fff;" class="fabiao_v1">
            <div style="padding:10px 28px 10px 35px; width:775px;height:300px;background:url(img/nopic.png) no-repeat center center; float:left;">
             </div>
             <div style="width:100%;clear:both;"></div>
        </div>
	</#if>
    </div>
  
   <div style="width:338px; float:right;">
      <div style="border:1px solid #e6e6e6; background:#fff; margin:0px 0 20px;">
         <h3 style="float:left; width:338px; height:44px; line-height:44px;border-bottom:1px solid #e6e6e6; font-weight:normal;">
            <span style="float:left;color:#646464;font-size:18px; padding-left:15px; padding-top:0;">官方公告</span>
            <a href="" style="margin-top:0;float:right; margin-right:15px;">
              <span style="color:#666666; font-size:12px;">更多</span>
            </a>
         </h3>
         <ul style="clear:both; padding:15px 0px;" class="hover_ak0">
         	<@article_list sign='site_notice' count=5; mtList>
	           	<#list mtList as mt>
						<li style="padding-bottom:15px; ">
			               <a href="<#if mt.url!><@mt.url?interpret /><#else>${base}/article/content/${mt.id}.htm</#if>" style="color:#808080; font-size:16px; padding-left:15px;"><font style="color:#ff7f00;">●</font>&nbsp;&nbsp;&nbsp;&nbsp;${substringWord(mt.title, 16, "...")}</a>
			            </li>			
        	 	</#list>
        	</@article_list>
         </ul>
      </div>
      
      <a href="${base}/activity/list.do"><img src="img/a3.png" width="338" height="180"></a>
     
      <div style="border:1px solid #e6e6e6; background:#fff; float:left; margin:20px 0 0;">
        <h3 style="width:338px; height:44px; line-height:44px;border-bottom:1px solid #e6e6e6; font-weight:normal;">
            <span style="color:#646464;font-size:18px; padding-left:15px; padding-top:0;">最新投资</span>
         </h3>
        <div class="list_lh" style="width:338px;height:344px;overflow:hidden;">
          <ul>
          <#list borrowTenderList as bt>
                 <li style=" width:308px; margin:0 auto; border-bottom:1px dotted #e6e6e6; padding:20px 0 13px 0;cursor: pointer;">
                  <h3 style="font-weight:normal;font-size:14px; color:#333333;"><font style="color:#808080;">${substringWord(bt.username,3,"*****")}<#if bt.username?length gt 10>${bt.username?substring(8,11)}</#if>&nbsp;投资</font>
                  		<a href="${base}/borrow/detail.do?bId=${bt.borrowId}"  style="color:#7c96e8;"  target="_blank"> ${substringWord(bt.title, 22, "...")}</a></h3>
                  <p class="color" style="font-size:14px;margin-top:15px;">￥${bt.account?eval?string("0.00")} <span style="color:#808080;float:right; ">${bt.createDateStr}</span></p>
                </li>
	        </#list>  
          </ul>
        </div>
      </div>
      </div>    
    </div>
    <div style="clear:both;"></div>
   </div>
  </div>
  <div style="width:100%;clear:both;"></div>
</div><!-- content end -->


<!--合作伙伴-->
<div class="bgc">
  <div style="width:1190px; margin:0 auto; padding-bottom:40px;">
      <h3 style="color:#666; font-size:20px; padding:15px 0; border-bottom:1px solid #e6e6e6; margin-bottom:20px; font-weight:normal;">合作伙伴</h3>
      <ul class="partner_v1"style="width:1190px; float:left;">
         <#list huobanpicList as item>
			<li <#if item_index gt 0 && (item_index+1) %5==0>  style="margin-right:0;</#if>>
				<a href="${item.url}"><img src="<@imageUrlDecode imgurl="${item.img}"; imgurl>${imgurl}</@imageUrlDecode>" width="218" height="58" /></a>
			</li>
		</#list> 
      </ul>
      <div style="width:100%;clear:both;"></div>
  </div>
</div>
<#include "/content/common/footer.ftl">
<!-- footer end -->

 <script>
$().ready(function(){
	var u = navigator.userAgent;
		var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
		var ua = navigator.userAgent.toLowerCase();  
	    if(ua.match(/MicroMessenger/i)=="micromessenger") {  
	     		window.location.href = "https://h5.yueshanggroup.cn";
	    } else { 
			if (isiOS) {
				window.location.href = "https://h5.yueshanggroup.cn";
			} else if (isAndroid) {
				window.location.href = "https://h5.yueshanggroup.cn";
			}
	    }
});
</script>

<script>
$("#header_sy").addClass("hq");<#-- header.ftl 选中样式 -->
 function init(element){
    var frontColor = "#ff7f00", backGround = "#bfbfbf";
    var r = element.clientHeight;
    element.setAttribute("width", r);
    element.setAttribute("height", r);
    var animation_loop;
    var ctx = element.getElementsByTagName("canvas")[0].getContext('2d');
    var sp = element.getElementsByTagName("span")[0];
    var target = parseInt(sp.innerHTML), current = 1, i = 0, Pi = Math.PI;
    function drawArc(_color, _arc1, _arc2){
      ctx.beginPath();
      ctx.lineWidth = 4;
      ctx.arc(r*0.5,r*0.5,r*0.5-ctx.lineWidth/2,_arc1*Pi,_arc2*Pi,false);
      ctx.strokeStyle = _color;
      ctx.stroke();
      ctx.closePath();
    }
    function process(){
      ctx.clearRect(0, 0, r, r);
      if (current == 100) drawArc(frontColor, 0 , 2);
      else {
        drawArc(frontColor, -0.5, current*0.02-0.5);
        drawArc(backGround, current*0.02-0.5, 1.5);
      }
      //sp.innerHTML = current;
      //$('#percent').html(current);
      if (current == target) return clearInterval(animation_loop);
      else current++;
    }

    drawArc(backGround, 0, 2);
    if (typeof animation_loop != undefined) clearInterval(animation_loop);
    if (target) animation_loop = setInterval(process, 30);
  }
  function processLoad(){
    $('.content .circle').each(function(){
        init(this);
      });
    }
  $("div.list_lh").myScroll({
    speed:40, //数值越大，速度越慢
    rowHeight:87 //li的高度
  });
</script>
</body>
</html>
