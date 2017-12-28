<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-服务商信息页</title>
	<#include "/content/common/meta.ftl">
	<link rel="shortcut icon" href="${base}/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="${base}/static/css/base.css" type="text/css" />
	<link rel="stylesheet" href="${base}/static/css/style_fws.css" type="text/css" />
	<link rel="stylesheet" href="${base}/static/css/fy.css" type="text/css" />
	
	<script src="${base}/static/js/jquery.min.js"></script>
	<script src="${base}/static/js/base.js"></script>
	<script src="${base}/static/base/js/base.js"></script>
	<script src="${base}/static/js/common/qmd.js"></script>	
	
<script type="text/javascript">
$(function(){
	$("#top_header_my").addClass("hq");
});
</script>
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">

<!-- content -->
<div class="ready" style="padding-top:40px;background:#f6f6f6;">
  <div class="good" style="margin-bottom:40px;">
     <div class="hardstr" style="margin-bottom:40px; background:#fff;border-top:1px solid #be9964; padding-top:20px;">
        <div class="hast_left">
          <h3 class="grit">${(agency.companyName)!}</h3>
          <div class="kaqu_yewu"><img src="img/shsi.png" width="235" height="80" /></div>
          <ul class="kaqu_zhuying">
            <li>主营业务：${(agency.mainBusiness)!}</li>
            <li>所在地区：${(agency.subpageAddress)!}</li>
            <li>联系方式：${(agency.subpagePhone)!}</li>
          </ul>
        </div><!-- hast_left end -->
        <div class="hast_right">
          <p class="kaqu_gongsi">${(agency.introduction)!}</p>
        </div><!-- hast_right end -->
     </div><!-- hardstr end -->
     
     
     <div class="big">
        <ul class="jeek_root">
          <li class="jeak jeeker">项目列表</li>
          <li class="jeak">企业资质</li>
          <li class="jeak">公司资料</li>
        </ul>
        <div class="tounch">
           <div class="tounch_a  tounch_b">
             <div class="kaqu_jiekuanbiao">
             <#list borrowList as borrow>
                <dl class="kaqu_xiangqing">
                  <dt class="kaqu_tpian"><img src="static/img/che.png" width="195" height="195" /></dt>
                  <dd class="kaqu_biaoti">${(borrow.name)!}</dd>
                  <dd class="kaqu_shouyi"><a class="kaqu_nianhua">年化收益：12.6%</a><a class="kaqu_qixian">期限：${(borrow.timeLimit)!}天</a></dd>
                  <dd class="kaqu_shouyi"><a class="kaqu_xmjindu">项目进度：${(borrow.schedule)!}%</a><a href="www.wojiuaiche.com/borrow/detail.do?bId=${borrow.id}"class="kaqu_ljxiangqin">了解详情</a></dd>
                </dl>
              </#list>
             </div>
           </div>
           <div class="tounch_a">
                <ul class="kaqu_w1a" style=" border-bottom: 1px solid #cccbca;">
                   <li class="kaqu_xm1">企业证书</li>
                   <li class="kaqu_js1">
                     <div class="kaqu_pilu">
                        <dl class="kaqu_shouquan"> 
                          <dt class="kaqu_zhuanr"><img src="<@imageUrlDecode imgurl="${agency.privateTaxImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="216" height="150" /></dt>
                          <dd class="kaqu_shouzhuan"><a href="">税务登记证</a></dd>
                        </dl>
                        <dl class="kaqu_shouquan"> 
                          <dt class="kaqu_zhuanr"><img src="<@imageUrlDecode imgurl="${agency.privateCharterImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="216" height="150" /></dt>
                          <dd class="kaqu_shouzhuan"><a href="">经营许可证</a></dd>
                        </dl>
                        <dl class="kaqu_shouquan"> 
                          <dt class="kaqu_zhuanr"><img src="<@imageUrlDecode imgurl="${agency.privateOrganizationImg}"; imgurl>${imgurl}</@imageUrlDecode>" width="216" height="150" /></dt>
                          <dd class="kaqu_shouzhuan"><a href="">组织机构代码证</a></dd>
                        </dl>
                     </div>
                   </li>
                </ul>
                <ul class="kaqu_w3a" style=" border-bottom: 1px solid #cccbca;">
                   <li class="kaqu_pz1">工商注册信息</li>
                   <li class="kaqu_jk1">
                      <table width="100%" cellpadding="0" cellspacing="0" style=" border:1px solid #cccbca">
                         <tbody>
                           <tr height="34">
                              <td class="kaqu_fuwu" width="120">企业名称</td>
                              <td class="kaqu_xinxi" width="280">杭州盛世长城担保有限公司</td>
                              <td class="kaqu_fuwu" width="120">注册资本</td>
                              <td class="kaqu_xinxi">${(agency.capital)!}</td>
                           </tr>
                           <tr height="34">
                              <td class="kaqu_diqu">注册地址</td>
                              <td class="kaqu_city">${(agency.address)!}</td>
                              <td class="kaqu_fuwu">成立日期</td>
                              <td class="kaqu_xinxi">${(agency.establishDate?string("yyyy-MM-dd HH:mm:ss"))!}</td>
                           </tr>
                           <tr height="34">
                              <td class="kaqu_diqu">法定代表人</td>
                              <td class="kaqu_city">李xx/td>
                              <td class="kaqu_fuwu">营业执照号码</td>
                              <td class="kaqu_xinxi">${(agency.privateCharter)!}</td>
                           </tr>
                           <tr height="34">
                              <td class="kaqu_diqu">税务登记账号</td>
                              <td class="kaqu_city">${(agency.taxRegistration)!}</td>
                              <td class="kaqu_fuwu">机构代码证号</td>
                              <td class="kaqu_xinxi">${(agency.organization)!}</td>
                           </tr>
                           <tr height="34">
                              <td class="kaqu_diqu kauq_chengnuo">营业期限</td>
                              <td class="kaqu_city kauq_chengnuo">${(agency.businessStart?string("yyyy-MM-dd"))!}至${(agency.businessEnd?string("yyyy-MM-dd"))!}</td>
                              <td class="kaqu_diqu kauq_chengnuo">经营范围</td>
                              <td class="kaqu_xinxi kauq_chengnuo">${(agency.scope)!}
                              </td>
                           </tr>
                         </tbody>
                      </table>
                   </li>
                </ul>
                <ul class="kaqu_w2a">
                   <li class="kaqu_xm8">服务承诺</li>
                   <li style="" class="kaqu_js3">
                      <table width="100%" cellpadding="0" cellspacing="0" >
                         <tbody>
                           <tr height="64">
                              <td class="kaqu_diqu kauq_chengnuo"></td>
                              <td colspan="3" class="kaqu_xinxi kauq_chengnuo">
                                <a href=""><img src="${base}/static/img/danbaoico1.png" /></a>
                                <a href=""><img src="${base}/static/img/danbaoico2.png" /></a>
                                <a href=""><img src="${base}/static/img/danbaoico3.png" /></a>
                                <a href=""><img src="${base}/static/img/danbaoico4.png" /></a>
                              </td>
                           </tr>
                         </tbody>
                      </table>
                   </li>
                </ul>
           </div>
           <div class="tounch_a">
                <ul class="kaqu_w1a" style=" border-bottom: 1px solid #cccbca;">
                   <li class="kaqu_xm1">公司实景</li>
                   <li class="kaqu_js2">
                     <div class="kaqu_pilu">
                        <dl class="kaqu_shouquana01"> 
                          <dt class="kaqu_zhuanra01"><img src="${base}/static/img/fly.png" width="127" height="127" /></dt>
                          <dd class="kaqu_shouzhuan"><a href="">债权转让披露书</a></dd>
                        </dl>
                        <dl class="kaqu_shouquana01"> 
                          <dt class="kaqu_zhuanra01"><img src="${base}/static/img/fly.png" width="127" height="127" /></dt>
                          <dd class="kaqu_shouzhuan"><a href="">债权转让披露书</a></dd>
                        </dl>
                        <dl class="kaqu_shouquana01"> 
                          <dt class="kaqu_zhuanra01"><img src="${base}/static/img/fly.png" width="127" height="127" /></dt>
                          <dd class="kaqu_shouzhuan"><a href="">债权转让披露书</a></dd>
                        </dl>
                        <dl class="kaqu_shouquana01"> 
                          <dt class="kaqu_zhuanra01"><img src="${base}/static/img/fly.png" width="127" height="127" /></dt>
                          <dd class="kaqu_shouzhuan"><a href="">债权转让披露书</a></dd>
                        </dl>
                        <dl class="kaqu_shouquana01"> 
                          <dt class="kaqu_zhuanra01"><img src="${base}/static/img/fly.png" width="127" height="127" /></dt>
                          <dd class="kaqu_shouzhuan"><a href="">债权转让披露书</a></dd>
                        </dl>
                     </div>
                   </li>
                </ul>
                <ul class="kaqu_w2a kaqu_w2a01">
                   <li class="kaqu_xm1">联系方式</li>
                   <li class="kaqu_js2">
                      <table width="100%" cellpadding="0" cellspacing="0" style=" border:1px solid #cccbca">
                         <tbody>
                           <tr height="34">
                              <td class="kaqu_fuwu" width="120">联系人姓名</td>
                              <td class="kaqu_xinxi" width="280">${(agency.linkman)!}{</td>
                              <td class="kaqu_fuwu" width="120">联系电话</td>
                              <td class="kaqu_xinxi" width="280">${(agency.linkmanMobile)!}</td>
                           </tr>
                           <tr height="34">
                              <td class="kaqu_diqu kauq_chengnuo" width="120">联系地址</td>
                              <td class="kaqu_city kauq_chengnuo" colspan="3" style=" text-align:left; padding-left:20px;">${(agency.address)!}</td>
                           </tr>
                         </tbody>
                      </table>
                   </li>
                </ul>
                <ul class="kaqu_w1a" style="border-bottom:1px solid #CCCBCA;">
                   <li class="kaqu_xm1">地址图片</li>
                   <li class="kaqu_js2">
                     <ul class="kaqu_pilu">
                        <li><img src="<@imageUrlDecode imgurl="${agency.logo2}"; imgurl>${imgurl}</@imageUrlDecode>" width="720" height="400" /></li>
                     </ul>
                   </li>
                </ul>
           </div>
        </div><!--tounch end-->
                 <script>
            jQuery(function(){
                  jQuery(".jeek_root li.jeak").click(function(){
                     var i=jQuery(this).index()
                      jQuery(".jeek_root li.jeak").removeClass("jeeker"); 
                      jQuery(this).addClass("jeeker");
                      jQuery(".tounch .tounch_a").css("display","none");
                      jQuery(".tounch .tounch_a").eq(i).css("display","block");
                      })
                })
         </script>

     </div><!-- big end -->
              <div class="kaqu_liangtu">
                <div class="kaqu_ttu"><img src="${base}/static/img/hard.png" width="950" height="740" /></div>
             </div>
  </div><!-- good end -->
     <div style="clear:both"></div>
</div><!-- ready end -->

<#include "/content/common/footer.ftl">

</body>
</html>
