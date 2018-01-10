<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>${Application ["qmd.setting.name"]}-我的合同</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>

<script type="text/javascript">
function printContract() {
	if (confirm('确定下载吗？')) {
　　		window.location.href="${base}/contractBorrow/download.do?contractBorrowId=${contractBorrowId}";
	}
}
</script>	
	
</head>
<body>
<body style=" background-color:#fcfbfa;">
<div class="hetong_head">
   <div class="hetong_content">
      <div class="hetong_contenta01">
      	
      </div>
      <div class="hetong_contenta02">
        
         
      </div>
   </div>
</div><!-- hetong_head end -->
<div class="hetong_neirong">
 <div class="hetong_neirong_a01">
    <div class="hetong_neirong_a02">
      <div class="hetong_neirong_xinxi">
        
         <div>
			<object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="800" border="0"><!--IE-->
			  <param name="_Version" value="65539">
			  <param name="_ExtentX" value="20108">
			  <param name="_ExtentY" value="10866">
			  <param name="_StockProps" value="0">
			  <param name="SRC" value="${base}/contractBorrow/download.do?contractBorrowId=${contractBorrowId}" width="100%" height="800" >
		　	　<embed src="${base}/contractBorrow/download.do?contractBorrowId=${contractBorrowId}" width="100%" height="800" ></embed><!--FF-->
		　　</object>
         </div>
      </div>
    </div>
    <div class="hetong_neirong_a03">
    <#-->
    本申请单仅作为我就爱车受理借款人借款申请之凭证，不作为任何合同之要约。
    -->
    </div>
    <div class="hetong_neirong_a04">
      <input type="button" value="下载" class="hetong_neirong_a05" onclick="printContract();"/>
<#-->
      <input type="button" value="发送邮箱" class="hetong_neirong_a05" />
      <input type="button" value="邮寄合同" class="hetong_neirong_a05 hetong"/>
-->
    </div>
 </div> 
</div><!-- hetong_neirong end -->

</body>
</html>
