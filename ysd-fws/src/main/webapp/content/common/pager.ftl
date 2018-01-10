<script type="text/javascript">
	function gotoPage(id) {
		$("#pageNumber").val(id);
		$("#pageSize").val($("#selectPageSize").val());
		$("#listForm").submit();
	}
	
	function gotoPageNum(tc) {
		var nm = $("#inNum").val();
		if (nm > tc) {
			nm = tc;
		}
		gotoPage(nm);
	}
	
	function gotoPageSize() {
		var nm = $("#inNum").val();
		gotoPage(nm);
	}
</script>

<div class="datagrid-pager pagination">
<input type="hidden" id="pageNumber" name="pager.pageNumber" value="${pageNumber}"/>
<input type="hidden" id ="pageSize" name="pager.pageSize" value="${pageSize}"/>
<table border="0" cellpadding="0" cellspacing="0">
    <tbody>
        <tr>
            <td><select class="pagination-page-list" onchange = "gotoPageSize();" id = "selectPageSize">
            	<option <#if pageSize == 20>selected</#if> >20</option>
            	<option <#if pageSize == 50>selected</#if> >50</option>
            	<option <#if pageSize == 100>selected</#if> >100</option>
            	</select></td>
            <td><a id="undefined" class="l-btn l-btn-plain l-btn-disabled" href="javascript:void(0)" icon="pagination-first" title="第一页" <#if pageNumber &gt; 1>onclick="gotoPage(1)"</#if>>
            	<span class="l-btn-bottom"><span class="l-btn-text"><span class="l-btn-empty pagination-first">&nbsp;</span></span></span>
               </a>
            </td>
            <td>
            	
            	<a id="undefined" class="l-btn l-btn-plain" href="javascript:void(0)" icon="pagination-prev" title="上一页" <#if pageNumber &gt; 1> onclick="gotoPage(${(pageNumber-1)});</#if>">
            	
            	<span class="l-btn-bottom"><span class="l-btn-text"><span class="l-btn-empty pagination-prev">&nbsp;</span></span></span>
                </a>
            </td>
            <td><span style="padding-left:6px;">第</span></td>
            <td><input class="pagination-num" id = "inNum" value="${pageNumber!'1'}" size="2" type="text" onblur="gotoPageNum(${pageCount});"></td>
            <td><span style="padding-right:6px;">页 &nbsp;共${pageCount}页</span></td>
            <td><a id="undefined" class="l-btn l-btn-plain" href="javascript:void(0)" icon="pagination-next" title="下一页" <#if pageNumber &lt; pageCount> onclick="gotoPage(${(pageNumber+1)});</#if>">
            	<span class="l-btn-bottom"><span class="l-btn-text"><span class="l-btn-empty pagination-next">&nbsp;</span></span></span>
                </a>
            </td>
            <td><a id="undefined" class="l-btn l-btn-plain" href="javascript:void(0)" icon="pagination-last" title="最末页" <#if pageNumber &lt; pageCount> onclick="gotoPage(${pageCount});"</#if>>
            	<span class="l-btn-bottom"><span class="l-btn-text"><span class="l-btn-empty pagination-last">&nbsp;</span></span></span>
                </a>
            </td>
            <td><a id="undefined" class="l-btn l-btn-plain" href="javascript:void(0)" icon="pagination-load">
            	<span class="l-btn-bottom"><span class="l-btn-text"><span class="l-btn-empty pagination-load" title="刷新" onclick = "gotoPageSize();">&nbsp;</span></span></span>
                </a>
            </td>
         </tr>
     </tbody>
 </table>
 <#assign start = 1>
 <#if pageSize &lt; totalCount>
 	<#assign end = pageSize>
 <#else>
 	<#assign end = totalCount>
 </#if>
 <#if pageNumber &gt; 1>
 	<#assign start = (pageNumber -1 )*pageSize>
 	<#if (pageNumber)*pageSize &lt; totalCount>
 		<#assign end = (pageNumber)*pageSize >
 	<#else>
 		<#assign end = totalCount >
 	</#if>
 </#if>
 <#if totalCount = 0>
 	<#assign start = 0>
 </#if>
<div class="pagination-info">显示${start}到${end}，共${totalCount}条记录</div>
<div style="clear:both;"></div>
</div>