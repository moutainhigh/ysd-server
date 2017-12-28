	<!-- nav -->
<div class="bar">
	<div class="nav">
		<ul>
			<li <#if menu = 'index'>class="first"</#if> ><a href = "${base}">首页</a></li>
			<li <#if menu = 'wylc'>class="first"</#if> ><a href="${Application ["qmd.baseUrl"]}borrow/list.do">我要理财</a></li>
			<li><a href="${Application ["qmd.baseUrl"]}borrow/borrowChoose.do">我要贷款</a></li>
			<li><a href="">投资课堂</a></li>
			<li <#if menu = 'bzzx'>class="first"</#if> ><a href="${base}/article/list.do?id=2">帮助中心</a></li>
			<li><a href="">安全保障</a></li>
			<li><a href="">官方微博</a></li>
		</ul>
           
		 <div class="fbar">	
           <ul class="cbar">
            <span><a href="${base}/article/list.do?id=2">工作原理</a></span> | 
			<span><a href="${base}/article/list.do?id=4">政策法规</a></span> | 
			<span><a href="">资费说明</a></span> | 
			<span><a href="">成功故事</a></span> | 
			<span><a href="">关于我们</a></span>
           </ul>
           <span class="rbar"> </span>
          </div>
	</div>
</div>
