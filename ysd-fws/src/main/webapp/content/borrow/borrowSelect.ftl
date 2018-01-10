<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>${Application ["qmd.setting.name"]}-发布借款</title>
	<#include "/content/common/meta.ftl">
</head>
<body>
<!-- header -->
<#include "/content/common/user_center_header.ftl">

<div class="user_middle">
	<div class="user_content">
    	<div class="user_content_bottom kaqu_w00">
          	<div class="kaqu_x0">
            	<div class="kaqu_x1">
                	<span  class="kaqu_x2"> 
                  		<span class="kaqu_x3">
	                   <a href="${base}/index.do">乐商贷首页</a>&nbsp;
	                   <a>></a>&nbsp;
	                   <a href="${base}/userCenter/index.do">账户中心</a>&nbsp;
	                   <a>></a>&nbsp;
	                   <a href="${base}/borrow/borrowChoose.do">发布借款</a>
                  		</span>
       				</span>
				</div><!--kaqu_x1 end-->
			</div><!--kaqu_x0 end--> 
			<div class="kaqu_w50">
          		<ul class="fl kaqu_w60">
	              	<li class="kaqu_w70">发布借款</li>
	          	  	<li class="list_fs kaqu_w80"><a href = "javascript:void(0);">项目选择</a></li>
         		</ul>
         		<div>
					<ul class="kaqu_w1a">
						<li class="kaqu_xm1">债权转让项目</li>
						<li class="kaqu_js2">
							<ul class="kaqu_pilu">
								<li class="kaqu_zuzhi" style=" background:none; border:none;"><img src="${base}/static/img/liu.png" width="180" height="150" /></li>
								<li class="kaqu_daima">
									<div>发布债权转让项目</div>
									<div class="kaqu_liuzhuanbiao">该项目是由有资金需求的融资方，将原有的债权进行再次转让而产生的理财项目。</div>
									<div><a href="${base}/borrow/borrowInputRaise.do?btp=7" class="kaqu_chakan">债权转让项目</a></div>
								</li>
							</ul>
						</li>
					</ul>

					<ul class="kaqu_w1a">
						<li class="kaqu_xm1">抵押质押项目</li>
						<li class="kaqu_js2">
							<ul class="kaqu_pilu">
								<li class="kaqu_zuzhi" style=" background:none; border:none;"><img src="${base}/static/img/yue1.png" width="180" height="150" /></li>
								<li class="kaqu_daima">
									<div>发布抵押质押项目</div>
									<div class="kaqu_liuzhuanbiao">该项目是由有资金需求的融资方，在办理好房产、汽车或其他动产等资产的抵押、质押等手续后，通过对接服务商进行发布的理财项目。</div>
									<div><a href="${base}/borrow/borrowInputRaise.do?btp=8" class="kaqu_chakan">抵押质押项目</a></div>
								</li>
							</ul>
						</li>
					</ul>
					<ul class="kaqu_w1a">
						<li class="kaqu_xm1">信用标</li>
						<li class="kaqu_js2">
							<ul class="kaqu_pilu">
								<li class="kaqu_zuzhi" style=" background:none; border:none;"><img src="${base}/static/img/biao_new.jpg" width="180" height="150" /></li>
								<li class="kaqu_daima">
									<div>发布信用标</div>
									<div class="kaqu_liuzhuanbiao">信用标信用标信用标信用标信用标</div>
									<div><a href="${base}/borrow/borrowInputRaise.do?btp=9" class="kaqu_chakan">发布信用标</a></div>
								</li>
							</ul>
						</li>
					</ul>
					<ul class="kaqu_w2a">
						<li class="kaqu_xm1">体验标</li>
						<li class="kaqu_js2">
							<ul class="kaqu_pilu">
								<li class="kaqu_zuzhi" style=" background:none; border:none;"><img src="${base}/static/img/sy_99.jpg" width="180" height="150" /></li>
								<li class="kaqu_daima">
									<div>发布体验标</div>
									<div class="kaqu_liuzhuanbiao">体验标体验标体验标</div>
									<div><a href="${base}/borrow/borrowInputRaise.do?btp=10" class="kaqu_chakan">发布体验标</a></div>
								</li>
							</ul>
						</li>
					</ul>
				</div>
         		<div style=" clear:both; padding-bottom:60px;"></div>
           	</div>
			<div style=" clear:both"></div>
    	</div><!--user_content_bottom end-->
   		<div style=" background-image:url(${base}/static/img/3_bg.png); background-position:0 bottom; background-repeat:repeat-x; height:9px;"></div>
	</div><!--user_content end-->
	<div style=" clear:both; padding-bottom:50px;"></div>
</div><!--user_middle end-->
<div style=" clear:both"></div>

<#include "/content/common/footer.html">

</body>
<script type="text/javascript">
$(function(){
	$("#menuDocking").addClass("kaqu_bg");
	$("#menuDocking_ul").addClass("user_content_top1");
	$("#menuDocking_borrowChoose").addClass("user_dlk");
});
</script>
</html>
