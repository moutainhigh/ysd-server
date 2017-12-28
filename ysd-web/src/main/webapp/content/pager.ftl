<script type="text/javascript">
	function gotoPage(id) {
		$("#pageNumber").val(id);
		$("#listForm").submit();
	}
	
</script>
<input type="hidden" id="pageNumber" name="pager.pageNumber" value="${pageNumber}"/>
<input type="hidden" name="pager.pageSize" value="${pageSize}"/>

<#if (pageCount > 1)>
		<ul class="page">
			<li>${pageNumber}<li>  <li> ${totalCount}</li>
			<!--首页-->
			<#if (pageNumber > 1)><a href="javascript:void(0);" onclick="gotoPage(1)" >首页</a>
			<#else><a href="javascript:void(0);" >首页</a></#if>
			
			<!--上一页-->
			<#if (pageNumber > 1)><a href="javascript:void(0);" onclick="gotoPage(${(pageNumber-1)})" >上一页</a>
			<#else><a href="javascript:void(0);" >上一页</a></#if>
			
			<!-- 显示页码 -->
			<#list (pageItem?keys)! as key>
				<#if pageNumber != key?number><a href="javascript:void(0);" onclick="gotoPage(${key});" >${key}</a>
				<#else><a href="javascript:void(0);" >${key}</a></#if>
			</#list>
		
			<!--下一页-->
			<#if (pageNumber < pageCount)><a href="javascript:void(0);" onclick="gotoPage(${(pageNumber+1)});" >下一页</a>
			<#else> <a href="javascript:void(0);" >下一页</a></#if>
			
			<!--尾页-->
			<#if (pageNumber < pageCount)><a href="javascript:void(0);" onclick="gotoPage(${(pageCount)});" >尾页</a>
			<#else> <a href="javascript:void(0);" >尾页</a></#if>
			
			
		</ul>
</#if>