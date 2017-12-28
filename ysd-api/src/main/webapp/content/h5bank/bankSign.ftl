<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>绑卡认证</title>
	<#include "/content/common/meta.ftl"> 
  <style>
  .am-form-group{margin-bottom: 0.5em;}
  .am-form-group .bank{width:1em;height:1em;background: url(mobile_img/gs.jpg) n o-repeat;background-size: contain;display: inline-block;}
  .parentCls{
      position: relative;
  }
  .js-max-input {
    border: solid 1px #ffd2b2;
    background: #fffae5;
    padding: 0 10px 0 10px;
    font-size:20px;
    color: #ff4400;
    width:100%;
    position: absolute;
    top:38px;
    left:0;
    display: none;
  }
  
  </style>
   <script type="text/javascript">
    // 初始化
    $(function(){   
      $(".parentCls input").keyup(function(event) {
         var value = $(this).val();
         if(value == ''){
            $(".js-max-input").css("display","none");
         }else{
            $(".js-max-input").css("display","block");
            $(".js-max-input").html(value);
         }

      });
      $(".parentCls input").unbind('focusout').bind('focusout',function(){
        $(".js-max-input").css("display","none");
      });
      
    });
    
    function check_form(){
    	var $realName = $("#realName").val();
    	var $idNo = $("#idNo").val();
    	var $bankId = $("#bankId").val();
    	var $branch = $("#branch").val();
    	var $cardNo = $("#cardNo").val();
    	var $safepwd = $("#safepwd").val();
    	if($realName==""||$realName.lenght<1){
    		$(".am-modal-bd").html("真实姓名不能为空");
			$('#my-alert').modal();
			return false;
    	}else if($idNo==""||$idNo.lenght<1){
    		$(".am-modal-bd").html("身份证号不能为空");
			$('#my-alert').modal();
			return false;
    	}else if($bankId==""||$bankId.lenght<1){
    		$(".am-modal-bd").html("请选择银行");
			$('#my-alert').modal();
			return false;
    	}else if($branch==""||$branch.lenght<1){
    		$(".am-modal-bd").html("支行不能为空");
			$('#my-alert').modal();
			return false;
    	}else if($cardNo==""||$cardNo.lenght<1){
    		$(".am-modal-bd").html("卡号不能为空");
			$('#my-alert').modal();
			return false;
    	}else if($safepwd==""||$safepwd.lenght<1){
    		$(".am-modal-bd").html("密码不能为空");
			$('#my-alert').modal();
			return false;
    	}else{
    		return true;
    	}
    
    
    }
    
    </script>
</head>
    <body class="bgc5">
      <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40">
        <div class="am-header-left am-header-nav">
          <a href="#right-link">
                <i class="am-icon-angle-left am-icon-fw" style="font-size:1.5em;"></i>
        	</a>	
        </div>
        <h1 class="am-header-title">
            绑卡认证
        </h1>
        <div class="am-header-right am-header-nav"></div>
      </header><!-- header end-->
      <div class="am-u-md-8 am-u-sm-centered">
          <form name="signForm"  class="am-form" action="${base}/bankSignSave.do"  onsubmit="return check_form();"  method="post">
          <input type="hidden" name="btype" value="${(btype)!}">
            <fieldset class="am-form-set">  
              <div style="padding:0.5em 0;">请确保以下信息真实有效</div>  
              <div class="am-form-group  am-form-icon">
                 <input type="text" name="realName" id="realName" class="am-form-field" style="padding-left:3em!important;" autocomplete="off" value="${(userBank.realName)!}" placeholder="你的真实姓名">
                 <span style="position:absolute;left:0.5em;top:0.4em;">姓名</span>
              </div>
              <div class="am-form-group  am-form-icon">
                <input type="text" name="idNo" id="idNo" class="am-form-field" style="padding-left:4em!important;" autocomplete="off" value="${(userBank.idNo)!}" placeholder="你的真实身份证号">
                <span style="position:absolute;left:0.5em;top:0.4em;">身份证</span>
              </div>
              <div class="am-form-group  am-form-icon">
               <select id="bankId" class="base_sel" name="bankId"  style="padding-left:5em!important;">
	                <@listing_childlist sign="account_bank"; listingList>
						<#list listingList as listing>
							<option value="${listing.keyValue}" <#if (listing.keyValue == userBank.bankId)!> selected</#if>><i class="blank"></i>${substring(listing.name, 30, "...")}</a>
							</option>
						</#list>
					</@listing_childlist>
				  </select>
                <span style="position:absolute;left:0.5em;top:0.4em;">选择银行</span>
              </div>
              <div class="am-form-group  am-form-icon">
                <input type="text" name="cashBank.branch"id="branch" class="am-form-field" autocomplete="off" style="padding-left:0.5em!important;" value="${(userBank.branch)!}" placeholder="请填写您的开户行">
              </div>
              <div class="am-form-group  am-form-icon parentCls">
                <input type="text" name="cardNo"id="cardNo" class="am-form-field inputElem" autocomplete="off" style="padding-left:0.5em!important;" value="${(userBank.cardNo)!}" placeholder="请填写您的账户">
                <div class="js-max-input"></div>
              </div>
              <div style="padding:0 0 0.5em;">交易密码用户投资及提现时使用</div> 
              <div class="am-form-group  am-form-icon">
                 <input type="password" id="safepwd" name="safepwd" class="am-form-field" autocomplete="off" style="padding-left:7em!important;" placeholder="8~20个字符">
                 <span style="position:absolute;left:0.5em;top:0.4em;"><#if btype==0>设置<#else>输入</#if>交易密码</span>
              </div>
              
              <!-- 弹框开始 -->
					<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
					  <div class="am-modal-dialog">
					    <div class="am-modal-bd"></div>
					    <div class="am-modal-footer">
					      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
					    </div>
					  </div>
					</div>
				<!-- 弹框结束 -->
				
             <button  type="submit" id="sign_button" onclick="check_form();" class="am-btn am-btn-block am-radius color1 bgc">前往认证</button>
            </fieldset>     
          </form>
        </div>
        <div class="color4 f75" style="padding:0 1em 0.5em;">目前支持：工商银行、农业、中国、建设、邮储、中信、平安光大、交通、兴业、民生、浦发、上海、招商银行。</div>
        <div class="color4 bgc2" style="margin:0 1em;padding:0.5em 0.5em 2em;background:#ffedc3;">
            <h3 class="fwn color"><i class="am-icon-unlock-alt f12"></i> 温馨提示</h3>
            <p>为保证你的账户安全，将会对你提供的银行卡进行扣款验证扣款金额3元</p>
        </div>
    </body>
   
</html>
