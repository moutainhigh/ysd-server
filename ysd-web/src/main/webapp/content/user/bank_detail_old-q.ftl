<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-我的账户-银行卡</title>
	<#include "/content/common/meta.ftl">
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
   <div style="width:942px; float:right;">
      <div style="border:1px solid #e6e6e6; background:#fff; ">
      		<div class="k_right_h1"><img src="${base}/img/tishi.png" /><p>温馨提示:&nbsp;&nbsp;<a>只能有一张银行卡用于提现及在手机端投资哦</a></p></div>
         <div class="bank_zh" style="margin-top: 30px; padding-bottom:29px;">
                 <div class="banks_add">
                  <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tbody>
                      <#if accountBank?exists >
                       <tr>
                         <td class="bank_td bank_td1" valign="top" style="font-size:14px;">我的银行卡：</td>
                         <td>
                             <div class="banks_k1">
                                 <div class="banks_bk banks_${accountBank.bankId}"></div>
                                 <div class="banks_haoma"><p>***************${accountBank.account?substring(accountBank.account?length-4,accountBank.account?length)}</p></div>
                                 <div class="banks_name"><a>${accountBank.branch} </a></div>
                             </div>
                         </td>
                       </tr>
                        <tr><td></td>
                         <td><#if accountBank.status==0>
                           <a href="${base}/userCenter/toBankInput.do" class="btn_t1">签约中</a>
                           <#else><a class="btn_t1">签约成功</a></#if>
                         </td>
                       </tr>
                       <#else>
                        <tr>
                         <td class="bank_td bank_td1" valign="top" style="font-size:14px;">我的银行卡：</td>
                         <td>
                             <div class="banks_k1">
                                 <div class="banks_bk banks_"></div>
                                 <div class="banks_haoma"><p></p></div>
                                 <div class="banks_name"><a> </a></div>
                             </div>
                         </td>
                       </tr>
                        <tr><td></td>
                         <td>
                           <a href="${base}/userCenter/toBankInput.do" class="btn_t1">立即绑定</a>
                         </td>
                       </tr>
                       </#if>
                     </tbody>
                   </table>
                 </div>
              </div>
      </div>
    </div>
    <div style="width:100%;clear:both; height:1px;"></div>
  </div>
</div><!-- content end -->     

<#include "/content/common/footer.ftl">
<script>
	$("#header_wdzh").addClass("hq");<#-- header.ftl 选中样式 -->
	$("#yhkgl").addClass("click_current");<#-- user_center_left.ftl 选中样式 -->

</script>  
</body>
</html>
