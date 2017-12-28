<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>${Application ["qmd.setting.name"]}-我的合同</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>

<script type="text/javascript">

</script>	
	
</head>
<body>
<body style=" background-color:#fcfbfa;">
<div class="hetong_head">
   <div class="hetong_content">
      <div class="hetong_contenta01">
      	<a href="${base}/index.do"><img src="${base}/static/img/kaqu_logo3.png" /></a>
      </div>
      <div class="hetong_contenta02">
         <div class="hetong_contenta03"><span class="kaqu_st7">全国服务热线</span><span class="kaqu_st8">400&nbsp;888&nbsp;9527</span></div>
         <ul class="hetong_contenta04">
            <li class="hetong_contenta05">项目合同</li>
            <li class="hetong_contenta06">

<#-->
               <a>抵押物：房产抵押贷款</a>
               <a>贷款金额：50000.00元</a>  
               <a>目标月利率：14.40%</a>            
               <a class="kaqu_hetonga07">借款期限：1个月</a>
-->
&nbsp;
            </li>
         </ul>
      </div>
   </div>
</div><!-- hetong_head end -->
<div class="hetong_neirong">
 <div class="hetong_neirong_a01">
    <div class="hetong_neirong_a02">
      <div class="hetong_neirong_xinxi">
         <h3 class="hetong_neirong_xinxibiaoti"></h3>
         <div>
            <table>
<#list cblist as cb>
            	<tr>
            		<td><a href="${base}/contract/contractTest.do?id=${cb.id}">${cb.contractTitle}</a></td>
            	</tr>
</#list>            	
            </table>
         </div>
      </div>
    </div>
    <div class="hetong_neirong_a03">
    <#-->
    本申请单仅作为我就爱车受理借款人借款申请之凭证，不作为任何合同之要约。
    -->
    </div>
    <div class="hetong_neirong_a04">
      <input type="button" value="打印" class="hetong_neirong_a05" onclick="printContract();"/>
<#-->
      <input type="button" value="发送邮箱" class="hetong_neirong_a05" />
      <input type="button" value="邮寄合同" class="hetong_neirong_a05 hetong"/>
-->
    </div>
 </div> 
</div><!-- hetong_neirong end -->
<div class="hetong_footer">
  <div class="hetong_footera01">
    <ul class="hetong_footera02">
      <li class="hetong_footera03">全国服务热线</li>
      <li class="hetong_footera04">400&nbsp;&nbsp;888&nbsp;&nbsp;9527</li>
      <li class="hetong_footera05">服务时间：9:00-17:00</li>
    </ul>
    <div class="hetong_footera06">
       <div class="hetong_footera07">
           <div>ICP备案：浙ICP备17036398号</div>
           <div>增值业务电信经营许可证：浙B2-130008226</div>
           <div>&copy;2013-2014&nbsp;IkQu Inc.All&nbsp;rights&nbsp;reserved.Powered&nbsp;by&nbsp;&nbsp;我就爱车</div>
       </div>
      <div class="hetong_footera08"><img width="210" height="60" src="${base}/static/img/kaqu_logo4.png"></div>
    </div>
  </div>
</div><!-- hetong_neirong end -->
</body>
</html>
