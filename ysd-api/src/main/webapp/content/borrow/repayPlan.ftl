<!DOCTYPE html>
<html>
  <head>
  <meta charset="gb2312" />
  <title>${Application ["qmd.setting.name"]}-借款人-借款标信息填写页</title>

  <meta name="application-name" content="网络P2P借贷" />
   <meta name="msapplication-tooltip" content="网络P2P借贷-上海借贷-杭州借贷" />
   <meta http-equiv="x-ua-compatible" content="ie=7" />
	<meta name="keywords" content="${Application ["qmd.setting.metaKeywords"]}" />
	<meta name="description" content="${Application ["qmd.setting.metaDescription"]}" />
	<meta name="robots" content="all" />
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="${base}/static/js/jquery/jquery-1.8.1.js"></script>
    <script type="text/javascript" src="${base}/static/js/jquery/jquery.lSelect.js"></script>
	<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
 
<script type="text/javascript"> 

 
 	function clickReturn() {
 		var num = $("#num").val();
 		var ret = "";
 		for (var i =0;i<num;i++) {
 		
 			if ($("#box_"+i).attr("checked")) {
 				ret+="{\"payday\":"+$("#box_"+i).val()+",\"payMoney\":"+$("#money_"+i).val()+"},";
 			}
 		}
 		ret = ret.substr(0,ret.length-1);
 		
 	 	window.returnValue=ret;
 	 	window.close();
 	}

	</script>
  </head>
  
  <body>
   <!-- header -->
	<!--content-->
    <div id="main2">
 
<!--content-->
  <div class="index mt">
  <ul class="cb">
      <p class="c_b"><a href="${base}/">${Application ["qmd.setting.name"]}</a> &gt; <a href="${base}/userCenter/index.do">我的账户</a> &gt <a href="#">资金管理</a> &gt 我要提现</p>
	  </ul>
      <!--left-->
	         <!--right-->
	         <input type="hidden" name="num" id="num" value="${num}" />
	         <div class="c_right">
			     <div class="top_bg"></div>
				 <div class="rightwrap">
				      <div class="rightcon">
				      <h2><strong>还款计划设置</strong> </h2>
					    
                          <table class="autotou clr cassaddtop">
							<#list borrowBeanList as borrow>
								<tr>
									<td><input type="checkbox" id="box_${borrow_index}" value="${borrow.payday}"/></td>
									<td>第${borrow.payday}天</td>
									<td><input type="text" id="money_${borrow_index}" value=""></td>
								</tr>
							</#list>
						  </table>
					  </div>
				 <input type="button" value="计算还款利息" onclick="clickReturn();" />
				 </div>
				 <div class="bottom_bg"></div>
			 
			 
			 </div>


  </div>
  <!--footer-->
  <div>	
 <!--帐户信息公开设置 结束-->
<!--帐户信息公开设置 开始-->
</form>
  </body>
</html>
