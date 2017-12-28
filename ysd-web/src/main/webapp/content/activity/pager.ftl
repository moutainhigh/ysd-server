<script type="text/javascript">
	function gotoPage(id) {
		$("#pageNumber").val(id);
		$("#listForm").submit();
	}
</script>
<form id="listForm" method="post" action="list.do"> 
 	<input type="hidden" name="activity.status" value="${activity.status}"/>
 	<input type="hidden" id="pageNumber" name="pager.pageNumber" value="${pageNumber}"/>
	<input type="hidden" name="pager.pageSize" value="${pageSize}"/>
</form>

<#if (pageCount > 1)>
	<ul class="mpage">
        <li class="bd" <#if (pageNumber > 1)> onclick="gotoPage(1);" <#else></#if>>首页</li>
        <li class="bd" <#if (pageNumber > 1)> onclick="gotoPage(${(pageNumber-1)});" <#else> </#if>>上一页</li>
        <#list (pageItem?keys)! as key>
			<#if pageNumber != key?number> 
				<li class="bde" onclick="gotoPage(${key});">${key}</li>
       		<#else>
       			<li class="current" onclick="gotoPage(${key});">${key}</li>
       		</#if>
		</#list>
        <li class="next bd" <#if (pageNumber < pageCount)> onclick="gotoPage(${(pageNumber+1)});" <#else></#if> >下一页</li>
        <li class="last bd" <#if (pageNumber < pageCount)> onclick="gotoPage(${(pageCount)});" <#else></#if> >尾页</li>
    </ul>
</#if>

