<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>系统消息</title>
	<#include "/content/common/meta.ftl">
  <style>
  	.am-form-group {margin-bottom: 1rem;}
  	.am-form .am-form-group input{border: 1px solid #e3e3e3;}
  </style>
</head>
    <body class="bgc5 am-dimmer-active" style="height:100%;overflow:hidden;">
		      <fieldset class="am-form-set">   	
				<!-- 弹框开始 -->
				<div id="my-alert" tabindex="-1" class="am-modal am-modal-alert am-modal-active" style="display: block; margin-top: -50px;">
				  <div class="am-modal-dialog" style="height: 100px;">
				    <div class="am-modal-bd">
				    ${msg}
				    </div>
				    <div class="am-modal-footer">
				      <span class="am-modal-btn" onclick="history.back(-1);">确定</span>
				    </div>
				  </div>
				</div>
				<!-- 弹框结束 -->
		      </fieldset>     
		    </form>
		  </div>
		</div>
		
    </body>
</html>
