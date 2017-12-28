<#escape x as x?html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-投资中心</title>
	<#include "/content/common/meta.ftl">
	<script src="/js/scroll.js"></script>
	
<SCRIPT language=javascript> 
	function clickTagsForm(id) {
		$("#tabsType").val(id);
		$("#tabsForm").submit();
	}
	
	function clickSearchDay() {
		var selval = $("#boxDayFlg").val();
		if(selval==1) {
			$("#span_year").hide();
			$("#span_empty").hide();
			$("#span_day").show();
		} else if(selval==2){
			$("#span_day").hide();
			$("#span_empty").hide();
			$("#span_year").show();
		} else {
			$("#span_year").hide();
			$("#span_day").hide();
			$("#span_empty").show();
		}
	}
	
	
</SCRIPT>
</head>
<body onload="processLoad()">
 <!-- header -->
<#include "/content/common/header.ftl">

<div class="content">
<form id="listForm" method="post" action="list.do" >
<input type="hidden" id="hid_p" name="p" value="${p}"/>
  <div style="width:1190px; clear:both; margin:0 auto; padding-bottom:50px;">
    <div style="width:1190px; float:left; margin-top:20px;">
    <!--
     <div class="xmxq_df">
       <a  href="javascript:queryList('p',0);" <#if p==0 ||p==""> class="xmxq_dt_s1"</#if> id=p_0>全部定投理财产品</a>
       <@listing_childlist sign="borrow_business_type"; listingList>
			<#list listingList as listing>
				<a href="javascript:queryList('p',${listing.keyValue});" class=" <#if p==listing.keyValue>xmxq_dt_s1</#if>" id="p_${listing.keyValue}"> ${listing.name}</a>
			</#list>
		</@listing_childlist>
     </div>
     -->
     <div style="width:838px;padding:0 0 0 0; background:#fff; float:left;border:1px solid #e6e6e6;">
     <#list pager.result as borrow>
          <div style="width:838px; float:left; padding:10px 0 35px 0; border-bottom:1px solid #e6e6e6; background:#fff;<#if borrow.type==16> background:#fff url(/img/newplayer.png) no-repeat right top;</#if>" class="fabiao_v1">
            <div style="padding:10px 28px 10px 35px; width:775px; float:left;">
                <h3 style="font-weight:normal; color:#666666; font-size:18px; margin-bottom:24px;"><img src="/img/a5.png" width="20" height="20" style="margin-top: -4px;" /> ${substringWord(borrow.name, 20)}</h3>
               <ul style="float:left; width:185px;">
               	 <li class="color" style="font-size:24px; margin-bottom:7px;font-weight:bold;">
               	 	<@numberPercent val="${borrow.baseApr}"; npt>${npt?substring(0,(npt?length-1))}</@numberPercent><font style="font-size:16px;color:#808080;font-weight:normal;">%</font>
               	 	 <#if borrow.awardApr gt 0>
               	 	<span style="background:url(../img/9.png) no-repeat;color:#fff;font-size:16px;padding:0 10px;font-weight:normal;width: 69px;  display: inline-block;">+<@numberPercent val="${borrow.awardApr}"; npt>${npt}</@numberPercent></span>
               	 	</#if>
               	 </li>
                 <li style="color:#808080; font-size:16px;">年化收益</li>
               </ul>
               <ul style="float:left; width:160px;">
               	 <li style="color:#333; font-size:24px; margin-bottom:7px;font-weight:bold;">${(borrow.account)!'0'}<font style="font-size:16px;color:#808080;font-weight:normal;">元</font></li>
                 <li style="color:#808080; font-size:16px;">总金额</li>
               </ul>
               <ul style="float:left; width:150px;">
               	 <li style="color:#333; font-size:24px; margin-bottom:7px;font-weight:bold;">${borrow.timeLimit}<font style="font-size:16px;color:#808080;font-weight:normal;">天</font></li>
                 <li style="color:#808080; font-size:16px;">理财期限</li>
               </ul>
               <ul style="width:140px; float:left;">
                <li>
                    <div class="circle" style="width:62px;height:62px;" width="62" height="62">
                      <canvas width="62" height="62"></canvas>
                      <div class="tac f12 color" style="width:100%;margin-top:-42px;">
                          <span style="font-size:1.35em;font-weight:bold;">${borrow.schedule}</span>%
                      </div>
                    </div>
                 </li>
               </ul>
               <ul style="float:right; margin-top:0; width:135px; margin-right:0;">
                 <li>
                 	<a href="${base}/borrow/detail.do?bId=${borrow.id}" >
                 		<#if borrow.status==1>
          					<span class="lijitoubiao_s1">${borrow.showButtonName} </span>
          				<#elseif borrow.status==3>
	          				<span class="lijitoubiao_s1 lijitoubiao_s2">${borrow.showButtonName} </span>
	         			<#else>
	          				<span class="lijitoubiao_s1 lijitoubiao_s2"">${borrow.showButtonName} </span>
          				</#if>
                 	</a>
                 </li>
               </ul>
             </div>
             <div style="width:100%;clear:both;"></div>
        </div>
      </#list> 
        
        <#if pager.totalCount==0>
			<div class="nodata"  align="center" >项目记录为空</div>
		</#if>
		<@pageFlip pager=pager>
			<#include "/content/borrow/pager.ftl">
		</@pageFlip>
    </div><!-- left end  --> 
    <div style="width:338px; float:right;">
        <div style="border:1px solid #e6e6e6; background:#fff; margin-top:0;">
         <h3 style="width:338px; height:44px; line-height:44px;border-bottom:1px solid #e6e6e6; font-weight:normal;">
            <span style="color:#646464;font-size:18px; padding-left:15px; padding-top:0;">网站数据</span>
         </h3>
         <ul style="clear:both; padding:15px 0 15px 20px;" class="hover_ak0">
            <li style="padding-bottom:15px; color:#808080; font-size:16px; ">
              会员人数：<font class="color" font-size:20px;"><@listing_keyValue sign="index_report_statistics_user_count" mold="NC"; kv>${kv}</@listing_keyValue></font> <span style="color:#333;">位</span>
            </li>
            <li style="padding-bottom:15px; color:#808080; font-size:16px; ">
              累计投资金额：<font class="color" font-size:20px;"><@listing_keyValue sign="index_report_statistics_tender" mold="NC"; kv>${kv}</@listing_keyValue></font> <span style="color:#333;">元</span>
            </li>
            <li style="padding-bottom:15px; color:#808080; font-size:16px; ">
              为用户赚取：<font class="color" font-size:20px;"><@listing_keyValue sign="index_report_statistics_interest" mold="NC" point="0"; kv>${kv}</@listing_keyValue></font> <span style="color:#333;">元</span>
            </li>
         </ul>
      </div>
      <div style="border:1px solid #e6e6e6; background:#fff; float:left; margin:20px 0;">
             <h3 style="width:338px; height:44px; line-height:44px;border-bottom:1px solid #e6e6e6; font-weight:normal;">
                <span style="color:#646464;font-size:18px; padding-left:15px; padding-top:0;">最新投资</span>
             </h3>
             <div class="list_lh" style="width:338px;height:344px;overflow:hidden;">
              <ul>
                <#list borrowTenderList as bt>
                 <li style=" width:308px; margin:0 auto; border-bottom:1px dotted #e6e6e6; padding:20px 0 13px 0;cursor: pointer;">
                  <h3 style="font-weight:normal;font-size:14px; color:#333333;"><font style="color:#808080;">${substringWord(bt.username,3,"*****")}<#if bt.username?length gt 10>${bt.username?substring(8,11)}</#if>&nbsp;投资</font>
                  		<a href="${base}/borrow/detail.do?bId=${bt.borrowId}"  style="color:#7c96e8;"  target="_blank"> ${substringWord(bt.title, 22, "")}</a></h3>
                  <p style="font-size:14px; color:#fd7c1a;margin-top:15px;">￥${bt.account?eval?string("0.00")} <span style="color:#808080;float:right; ">${bt.createDateStr}</span></p>
                </li>
	         </#list>  
              </ul>
            </div>
        </div>
        <a href="${base}/activity/list.do"><img src="/img/a3.png" width="338" height="180" /></a>
        <div style="border:1px solid #e6e6e6; background:#fff; margin-top:20px;">
         <h3 style="width:338px; height:44px; line-height:44px;border-bottom:1px solid #e6e6e6; font-weight:normal;">
            <span style="color:#646464;font-size:18px; padding-left:15px; padding-top:0;">常见问题</span>
         </h3>
         <ul style="clear:both; padding:15px 0px;" class="hover_ak0">
         	<@article_list sign='help_center' count=5; mtList>
		           <#list mtList as mt>
		           		 <li style="padding-bottom:15px; ">
		           		 	<a href="<#if mt.url!><@mt.url?interpret /><#else>${base}/article/content/${mt.id}.htm</#if>" style="color:#808080; font-size:16px; padding-left:15px;"><font style="color:#ff7f00;">●</font> ${substringWord(mt.title, 22, "")}</a>
			           </li>
										
	        	 	</#list>
	        </@article_list>
         </ul>
      </div><!-- 4 end -->   
    </div><!-- right end  --> 
     <div style="clear:both;"></div>
    </div>
  </div>
  <div style="width:100%;clear:both;"></div>
  	</form>
</div><!-- content end -->
<!-- footer -->
<#include "/content/common/footer.ftl">

</body>
<script>
  
function queryList(tp,vl) {
	$("#hid_"+tp).val(vl);
	$("#pageNumber").val(0);
	$("#listForm").submit();
}


$("#header_xmzx").addClass("hq");

if($("#hid_t").val() ==''){$("#hid_t").val(0)}
$("#t_"+$("#hid_t").val()).addClass("yangshi");
if($("#hid_r").val() ==''){$("#hid_r").val(0)}
$("#r_"+$("#hid_r").val()).addClass("yangshi");
if($("#hid_l").val() ==''){$("#hid_l").val(0)}
$("#l_"+$("#hid_l").val()).addClass("yangshi");
if($("#hid_p").val() ==''){$("#hid_p").val(0)}
$("#p_"+$("#hid_p").val()).addClass("xmxq_dt_s1");



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
</html>
</#escape>