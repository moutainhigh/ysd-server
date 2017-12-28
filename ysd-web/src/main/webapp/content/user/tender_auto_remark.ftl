<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <meta charset="gb2312" />
  <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-自动投标说明</title>
	<#include "/content/common/meta.ftl">

 </head>

 <body onload="checkimg()">
<!-- header -->
<#include "/content/common/header.ftl">

<!-- content -->
<div class="admin clearfix">
	<#include "/content/common/user_center_left.ftl">
	<!-- admin content -->
	<div class="admin-con">
		<!-- breadcrumb -->
		<div class="breadcrumb">
			<a href="">${Application ["qmd.setting.name"]}</a> &gt; <a href="${base}/userCenter/index.do">我的账户</a> &gt; 自动投标说明
		</div>
				
		<div class="tab clearfix">
			<ul>
				<li><a href="${base}/user/tenderAuto.do"><span>自动投标设置</span></a></li>
				<li><a href="javascript:void(0);" class="current"><span>自动投标说明</span></a></li>
			</ul>		
		</div>
		<div class="tab-con special">
			<!-- frm -->
			<div class="frm">
		
							<!-- tips -->
							<div class="filter">
<p>一、自动投标说明：</p>
<p>1、自动投标优先于手动投标，每一个标的自动投标比例为标总借款额的  <span  class="c1">${tenderAutoAccountRate?string("percent")}</span>。</p>
<p>2、自动投标会按一定规则对所有启用改功能的会员进行排序，当会员获得自投资格时，就会按照其设置进行投标。</p>
<p>3、“可自投余额”定义：指系统在启动自动投标时，会员账户中可用余额和续投余额的有效总额。</p>
<p>4、如借款标设置限额，则以限额优先。</p>
<br/>
<p>二、自动投标设置： </p>
<p>1、如选择单次最高金额投标方式，当“可自投余额”小于单次最高投标金额时，则自动投资”可自投余额“。</p>
<p>2、 如选择“余额全部投标”方式，会员每次获得自投资格时，会将全部余额进行投资。</p>
<p>3、自动投标优先使用续投余额中资金。</p>
<br/>
<p>三、自动投标排名规则：</p>
<p>1、每个开启自动投标的用户，都会进入一个自投队列，队列初始排名以开启时间越早，排名越前面。</p>
<p>2、在标发出时，将按队列排名逐个检索，符合条件的用户进行自投。</p>
<p>2、自投成功的用户将自动移入队列的最后一位，没有自投成功的用户将往前排。</p>
<p>3、新用户开启自投功能后，将进入队列的最后面。</p>
<p>4、用户修改“借款期限”和“还款方式”后，将会被移入队列的最后面。</p>

							</div>
							
					</div>
					
				</div>
			</div>
			</form>
	</div>
</div>
 <!--footer-->
  <#include "/content/common/footer.ftl">
</body>

<script type="text/javascript">
$(function(){
	$("#investment_auto").addClass("current");
});
</script>
</html>
