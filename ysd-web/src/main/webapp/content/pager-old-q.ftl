<script type="text/javascript">
	function gotoPage(id) {
		$("#pageNumber").val(id);
		$("#listForm").submit();
	}
	
</script>
<input type="hidden" id="pageNumber" name="pager.pageNumber" value="${pageNumber}"/>
<input type="hidden" name="pager.pageSize" value="${pageSize}"/>

<#if (pageCount > 1)>
		<div style="text-align:center;color:#646464;font-family:'宋体'; padding:15px 0px;clear: both;">
			第${pageNumber}页 共${pageCount}页${totalCount}条
			<!--首页-->
			<#if (pageNumber > 1)><a href="javascript:void(0);" onclick="gotoPage(1)" class="ic pFirst" style="color:#646464;">首页</a>
			<#else><a href="javascript:void(0);" class="ic pFirst" style="color:#646464;">首页</a></#if>
			
			<!--上一页-->
			<#if (pageNumber > 1)><a href="javascript:void(0);" onclick="gotoPage(${(pageNumber-1)})" class="ic pPrev" style="color:#646464;">上一页</a>
			<#else><a href="javascript:void(0);" class="ic pPrev" style="color:#646464;">上一页</a></#if>
			
			<!-- 显示页码 -->
			<#list (pageItem?keys)! as key>
				<#if pageNumber != key?number><a href="javascript:void(0);" onclick="gotoPage(${key});" style="color:#646464;">${key}</a>
				<#else><a href="javascript:void(0);" style="color:black;font-size:18px;">${key}</a></#if>
			</#list>
		
			<!--下一页-->
			<#if (pageNumber < pageCount)><a href="javascript:void(0);" onclick="gotoPage(${(pageNumber+1)});" class="ic pNext" style="color:#646464;">下一页</a>
			<#else> <a href="javascript:void(0);" class="ic pNext" style="color:#646464;">下一页</a></#if>
			
			<!--尾页-->
			<#if (pageNumber < pageCount)><a href="javascript:void(0);" onclick="gotoPage(${(pageCount)});" class="ic pLast" style="color:#646464;">尾页</a>
			<#else> <a href="javascript:void(0);" class="ic pLast" style="color:#646464;">尾页</a></#if>
			
			<#--跳转
			<a href="javascript:void(0);" onclick="gotoPageNum(${(pageCount)});">转到</a>  <input type="text" id="inNum" class="txt" value="${pageNumber}" onfocus="this.select();" onkeyup="value=value.replace(/[^0-9]/g,'1')" /> 页 
			-->
		</div>
</#if>