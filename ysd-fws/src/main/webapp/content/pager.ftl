<script type="text/javascript">
	function gotoPage(id) {
		$("#pageNumber").val(id);
		$("#listForm").submit();
	}
	
</script>
<input type="hidden" id="pageNumber" name="pager.pageNumber" value="${pageNumber}"/>
<input type="hidden" name="pager.pageSize" value="${pageSize}"/>
<#if (pageCount > 1)>
	<div class="P10 tac">
		
			共 ${pageCount} 页
		
		<#if (pageNumber > 1)>
				<a href="javascript:void(0);" onclick="gotoPage(1)">首页</a>
			
		<#else>
				<span>首页</span>
		</#if>
		
		<#if (pageNumber > 1)>
				<!--<a href="${base}${prePageUrl}">上一页</a>-->
				<a href="javascript:void(0);" onclick="gotoPage(${(pageNumber-1)});">上一页</a>
		<#else>
				<span>上一页</span>
		</#if>
		
		<#list (pageItem?keys)! as key>
			<#if (key_index == 0 && key?number > 1)>
				...
			</#if>
			<#if pageNumber != key?number>
					<!--<a href="${base}${pageItem[key]}">${key}</a>-->
					<a href="javascript:void(0);" onclick="gotoPage(${key});">${key}</a>
			<#else>
					<span>${key}</span>
			</#if>
			<#if (!key_has_next && key?number < pageCount)>
				...
			</#if>
		</#list>
	    
		<#if (pageNumber < pageCount)>
				<!--<a href="${base}${nextPageUrl}">下一页</a>-->
				<a href="javascript:void(0);" onclick="gotoPage(${(pageNumber+1)});">下一页</a>
		<#else>
				<span>下一页</span>
		</#if>
		
		<#if (pageNumber < pageCount)>
				<!--<a href="${base}${lastPageUrl}">末页</a>-->
				<a href="javascript:void(0);" onclick="gotoPage(${pageCount});">末页</a>
		<#else>
				<span>末页</span>
		</#if>
	</div>
</#if>


<div class="datagrid-pager pagination">
<table border="0" cellpadding="0" cellspacing="0">
    <tbody>
        <tr>
            <td><select class="pagination-page-list"><option selected="selected">20</option><option>50</option><option>100</option></select></td>
            <td><a id="undefined" class="l-btn l-btn-plain l-btn-disabled" href="javascript:void(0)" icon="pagination-first" title="第一页" onclick="gotoPage(1)">
            	<span class="l-btn-bottom"><span class="l-btn-text"><span class="l-btn-empty pagination-first">&nbsp;</span></span></span>
               </a>
            </td>
            <td><a id="undefined" class="l-btn l-btn-plain" href="javascript:void(0)" icon="pagination-prev" title="上一页" onclick="gotoPage(${(pageNumber-1)});">
            	<span class="l-btn-bottom"><span class="l-btn-text"><span class="l-btn-empty pagination-prev">&nbsp;</span></span></span>
                </a>
            </td>
            <td><span style="padding-left:6px;">第</span></td>
            <td><input class="pagination-num" value="1" size="2" type="text"></td>
            <td><span style="padding-right:6px;">页 &nbsp;共1页</span></td>
            <td><a id="undefined" class="l-btn l-btn-plain" href="javascript:void(0)" icon="pagination-next" title="下一页" onclick="gotoPage(${(pageNumber+1)});">
            	<span class="l-btn-bottom"><span class="l-btn-text"><span class="l-btn-empty pagination-next">&nbsp;</span></span></span>
                </a>
            </td>
            <td><a id="undefined" class="l-btn l-btn-plain" href="javascript:void(0)" icon="pagination-last" title="最末页" onclick="gotoPage(${pageCount});">
            	<span class="l-btn-bottom"><span class="l-btn-text"><span class="l-btn-empty pagination-last">&nbsp;</span></span></span>
                </a>
            </td>
            <td><a id="undefined" class="l-btn l-btn-plain" href="javascript:void(0)" icon="pagination-load">
            	<span class="l-btn-bottom"><span class="l-btn-text"><span class="l-btn-empty pagination-load" title="刷新">&nbsp;</span></span></span>
                </a>
            </td>
         </tr>
     </tbody>
 </table>
</div>






















