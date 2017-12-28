<#escape x as x?html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-投资中心</title>
	<#include "/content/common/meta.ftl">
	
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
 <style>
  	.am-form-group {margin-bottom: 1rem;}
    h3{font-size: 0.95em;background-color: #edeef0; padding: 0.3em 1em;}
    h4{padding: 0.5em 0;font-weight: normal;font-size: 1em;}
    hr{height:1px;}
    .content{padding:0 1em 1em;}
    .content p{text-indent: 2em;}   
    hr.am-divider{margin: 1.5rem auto 0.5rem;}
  </style>
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">

<div class="content">
<div style="width:970px; margin:0 auto;">
    
		
		<form id="listForm" method="post" action="list.do" >
		<input type="hidden" id="hid_t" name="t" value="${t}"/>
		<input type="hidden" id="hid_r" name="r" value="${r}"/>
		<input type="hidden" id="hid_l" name="l" value="${l}"/>
		<input type="hidden" id="hid_p" name="p" value="${p}"/>
	 <ul class="tab">
    	<li><a href="javascript:queryList('p',0);" class="tab_list <#if p==0 ||p=="">tab_list_a1</#if>" id="p_0">所有项目</a></li>
    	<@listing_childlist sign="borrow_business_type"; listingList>
			<#list listingList as listing>
				<li><a href="javascript:queryList('p',${listing.keyValue});" class="tab_list <#if p==listing.keyValue>tab_list_a1</#if>" id="p_${listing.keyValue}"> ${listing.name}</a></li>
			</#list>
		</@listing_childlist>
      </ul>
      <ul style="clear:both; margin-bottom:10px; text-align: left;" >
      

	<li style="border-bottom:1px dashed #d4d4d4;padding-top:10px; padding-bottom:10px;" class="bluer">
        <span style="background:url(${base}/static/img/b1.png) 0 0 no-repeat; width:69px; height:26p; line-height:26px; text-align:center; color:#ece0b9;font-family:'宋体'; display:inline-block;font-weight:700;">年化利率</span>
        	<a href="javascript:queryList('r',0);" <#if r==0 ||r=="">class="bllue"</#if> id="r_0" style="padding-right:40px;">全部</a>
			<a href="javascript:queryList('r',1);" <#if r==1>class="bllue"</#if> id="r_1" style="padding-right:40px;">10%以下</a>
			<a href="javascript:queryList('r',2);" <#if r==2>class="bllue"</#if> id="r_2" style="padding-right:40px;">10%~13%</a>
			<a href="javascript:queryList('r',3);" <#if r==3>class="bllue"</#if> id="r_3" style="padding-right:40px;">13%~16%</a>
			<a href="javascript:queryList('r',4);" <#if r==4>class="bllue"</#if> id="r_4" style="padding-right:40px;">16%~20%</a>
			<a href="javascript:queryList('r',5);" <#if r==5>class="bllue"</#if> id="r_5" style="padding-right:40px;">20%以上</a>
	</li>
	<li style=" padding-top:10px;padding-bottom:10px;" class="bluer">
		<span style="background:url(${base}/static/img/b1.png) 0 0 no-repeat; width:69px; height:26p; line-height:26px; text-align:center; color:#ece0b9;font-family:'宋体'; display:inline-block;font-weight:700;">项目总额</span>
			<a href="javascript:queryList('t',0);" <#if t==0 ||t=="">class="bllue"</#if> id="t_0" style="padding-right:35px;">全部</a>
			<a href="javascript:queryList('t',1);" <#if t==1>class="bllue"</#if> id="t_1" style="padding-right:35px;">10万元以下</a>
			<a href="javascript:queryList('t',2);" <#if t==2>class="bllue"</#if> id="t_2" style="padding-right:35px;">10万元-50万元</a>
			<a href="javascript:queryList('t',3);" <#if t==3>class="bllue"</#if> id="t_3" style="padding-right:35px;">50万元-100万</a>
			<a href="javascript:queryList('t',4);" <#if t==4>class="bllue"</#if> id="t_4" style="padding-right:35px;">100万元以上</a>
	</li>

	<li style=" padding-top:10px;padding-bottom:10px;" class="bluer">
		<span style="background:url(${base}/static/img/b1.png) 0 0 no-repeat; width:69px; height:26p; line-height:26px; text-align:center; color:#ece0b9;font-family:'宋体'; display:inline-block;font-weight:700;">投资期限</span>
			<a href="javascript:queryList('l',0);" <#if l==0 ||l=="">class="bllue"</#if> id="l_0" style="padding-right:40px;">全部</a>
			<a href="javascript:queryList('l',1);" <#if l==1>class="bllue"</#if> id="l_1" style="padding-right:40px;">1个月以内</a>
			<a href="javascript:queryList('l',2);" <#if l==2>class="bllue"</#if> id="l_2" style="padding-right:40px;">1-3个月</a>
			<a href="javascript:queryList('l',3);" <#if l==3>class="bllue"</#if> id="l_3" style="padding-right:40px;">1-6个月</a>
			<a href="javascript:queryList('l',4);" <#if l==4>class="bllue"</#if> id="l_4" style="padding-right:40px;">3-6个月</a>
			<a href="javascript:queryList('l',5);" <#if l==5>class="bllue"</#if> id="l_5" style="padding-right:40px;">6-12个月</a>
			<a href="javascript:queryList('l',6);" <#if l==6>class="bllue"</#if> id="l_6" style="padding-right:40px;">12个月以上</a>
	</li>
	<ul style="width:970px; height:43px; line-height:43px;clear:both; float:left; border-bottom:1px solid #c6c6c6;border-top:1px solid #c6c6c6;">
         <li style="float:left; width:125px; padding-left:95px;color:#4b4b4b;font-family:'宋体';font-size:14px;font-weight:bold; ">名称</li>
         <li style="float:left; width:155px;color:#4b4b4b;font-family:'宋体';font-size:14px; font-weight:bold; ">年化收益</li>
         <li style="float:left; width:135px;color:#4b4b4b;font-family:'宋体';font-size:14px;font-weight:bold;  ">金额</li>
         <li style="float:left; width:125px;color:#4b4b4b;font-family:'宋体';font-size:14px; font-weight:bold; ">期限</li>
         <li style="float:left; width:155px;color:#4b4b4b;font-family:'宋体';font-size:14px;font-weight:bold;  ">奖励</li>
         <li style="float:left; width:180px;color:#4b4b4b;font-family:'宋体';font-size:14px; font-weight:bold; ">进度</li>
      </ul>
		<#list pager.result as borrow>
		
		  <ul style="float:left; clear:both; width:967px; height:74px; line-height:74px; padding-left:3px;border-bottom:1px solid #c6c6c6; text-align: left;">
        <li style="width:235px; float:left;">
	          <a href="${base}/borrow/detail.do?bId=${borrow.id}">
	          	<#if borrow.type==0><img src="${base}/static/img/img/miao.png" width="28" height="28" />
				<#elseif borrow.type=15><img src="${base}/static/img/borrow/ya.png" width="28" height="28" />
				<#elseif borrow.type=5><img src="${base}/static/img/borrow/zhuan.png" width="28" height="28" />
				<#elseif borrow.type=14><img src="${base}/static/img/borrow/ya.png" width="28" height="28" />
				</#if>
	          	<span style="color:#4b4b4b;font-family:'宋体';font-size:14px; margin-left:5px;">${substringWord(borrow.name, 9)}</span> 
	          </a>
        </li>
          <li style="width:113px; float:left;"><span style="color:#4b4b4b;font-family:'宋体';font-size:14px;"><@numberPercent val="${borrow.varyYearRate}"; npt>${npt}</@numberPercent></span></li>
        <li style="width:163px; float:left;"><span style="color:#4b4b4b;font-family:'宋体';font-size:14px;">￥${(borrow.account)!'0'}元</span></li>
        <li style="width:100px; float:left;"><span style="color:#4b4b4b;font-family:'宋体';font-size:14px;">${borrow.timeLimit}<#if borrow.isday==0>个月<#else>天</#if></span></li>
        <li style="width:163px; float:left;"><span style="color:#4b4b4b;font-family:'宋体';font-size:14px;">
		        <#if borrow.funds??>
						<#if borrow.funds=="">无<#else>${borrow.funds}%</#if>
					<#else>
						无
				</#if>
				</span></li>
        <li style="width:100px; float:left;margin-top:5px;">
        <span class="pct"><span class="huan" ><font style="font-family:'宋体'; font-size:14px;color:#000;" >${borrow.schedule}%</font></span></span>
        </li>
        <li style="width:93px; float:left; margin-top:16px;">
          <a href="${base}/borrow/detail.do?bId=${borrow.id}">
          	<#if borrow.status==1>
          		<span class="btn_1 wancheng_1">${borrow.showButtonName} </span>
          	<#elseif borrow.status==3>
	          	<span class="btn_1 wancheng_2">${borrow.showButtonName} </span>
	         <#else>
	          	<span class="btn_1 wancheng_2">${borrow.showButtonName} </span>
          	</#if>
          </a>
        </li>
      </ul>	
      
      </#list>
		<@pageFlip pager=pager>
			<#include "/content/pager.ftl">
		</@pageFlip>
				<!--fanye end-->
			</div>
			<!--back1 end-->
		</div>
		<!--big_content end -->
		</form>
	</div>
	<!--content end-->
</div>
<!--biaoti_bg end-->


<!-- footer -->
<#include "/content/common/footer.ftl">

</body>
<script>

function queryList(tp,vl) {
	$("#hid_"+tp).val(vl);
	$("#pageNumber").val(0);
	$("#listForm").submit();
}

jQuery(function(){
	jQuery(".zhuangtai").mouseover(function(){
		jQuery(this).css("background-color","#e9eff5");
		jQuery(this).css('background-image','url(${base}/static/img/c3.png)');
		});
	jQuery(".zhuangtai").mouseleave(function(){
		jQuery(this).css("background-color","transparent");
		jQuery(this).css('background-image','url(${base}/static/img/c2.png)');
		});
	});

$("#top_borrows").addClass("hq");

$(".man").each(function(){
			var p=parseInt($(this).text());
			$(this).children(".pct").css("background-position", -Math.floor(p*0.2)*32 + "px 0");
		});


if($("#hid_t").val() ==''){$("#hid_t").val(0)}
$("#t_"+$("#hid_t").val()).addClass("yangshi");
if($("#hid_r").val() ==''){$("#hid_r").val(0)}
$("#r_"+$("#hid_r").val()).addClass("yangshi");
if($("#hid_l").val() ==''){$("#hid_l").val(0)}
$("#l_"+$("#hid_l").val()).addClass("yangshi");
if($("#hid_p").val() ==''){$("#hid_p").val(0)}
$("#p_"+$("#hid_p").val()).addClass("yangshi");

$(".pct").each(function(){
			var p=parseInt($(this).text());
			$(this).children(".huan").css("background-position", -Math.floor(p*0.2)*64 + "px 0");
		});
</script>
</html>
</#escape>