<script type="text/javascript">
	function gotoPage(id) {
		$("#pageNumber").val(id);
		$("#listForm").submit();
	}
	
	function gotoPageNum(tc) {
		var nm = $("#inNum").val();
		if (nm > tc) {
			nm = tc;
		}
		gotoPage(nm);
	}
	
</script>
<input type="hidden" id="pageNumber" name="pager.pageNumber" value="${pageNumber}"/>
<input type="hidden" name="pager.pageSize" value="${pageSize}"/>

<#if (pageCount > 1)>

    <div style=" text-align:center; padding:40px 0; clear:both;" class="page_s0">
         <#if (pageNumber > 1)><a href="javascript:void(0);" onclick="gotoPage(1)" class="page_s1">首页</a>
         <#else><a href="javascript:void(0);" class="page_s1">首页</a></#if>
         <#if (pageNumber > 1)>  <a href="javascript:void(0);"  onclick="gotoPage(${(pageNumber-1)})" class="page_s1">上一页</a>
         <#else> <a href="javascript:void(0);"  onclick="gotoPage(${(pageNumber-1)})" class="page_s1">上一页</a></#if>
          <#list (pageItem?keys)! as key>
				<#if pageNumber != key?number> <a href="javascript:void(0);" onclick="gotoPage(${key});" class="page_s1">${key}</a>
           		<#else><a href="javascript:void(0);" onclick="gotoPage(${key});" class="page_s1 page_s2">${key}</a></#if>
			</#list>
          <#if (pageNumber < pageCount)><a href="javascript:void(0);" onclick="gotoPage(${(pageNumber+1)});" class="page_s1 page_s2">下一页</a>
          <#else> <a href="javascript:void(0);"class="page_s1">下一页</a></#if>
           <#if (pageNumber < pageCount)><a href="javascript:void(0);" onclick="gotoPage(${(pageCount)});" class="page_s1">尾页</a>
           <#else> <a href="javascript:void(0);" class="page_s1">尾页</a></#if>
        </div>
        
</#if>

