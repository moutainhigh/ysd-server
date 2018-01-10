<!doctype html>
<html class="no-js">
<head>
 <title>${Application ["qmd.setting.name"]}-绑卡认证</title>
 <#include "/content/common/meta.ftl">
  <style>
  .am-form-group{margin-bottom: 0.5em;}
  .am-form-group .bank{width:1em;height:1em;background: url(/img/gs.jpg) n o-repeat;background-size: contain;display: inline-block;}
  </style>
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
          <form id="bankInputForm"  class="am-form" method="post" action="bankSignSave.do">
        	<input type="hidden" name="btype" value="${(btype)!}">
            <fieldset class="am-form-set">  
              <div style="padding:0.5em 0;">请确保以下信息真实有效</div>  
              <div class="am-form-group  am-form-icon">
                 <input type="text"  name="realName" id="realName" value="${(userBank.realName)!}" id="doc-ipt-3-a" class="am-form-field" style="padding-left:3em!important;" placeholder="你的真实姓名">
                 <span style="position:absolute;left:0.5em;top:0.4em;">姓名</span>
              </div>
              <div class="am-form-group  am-form-icon">
                <input type="text"  name="idNo" value="${userBank.idNo!}" id="idNo" id="doc-ipt-3-a" class="am-form-field" style="padding-left:4em!important;" placeholder="你的真实身份证号">
                <span style="position:absolute;left:0.5em;top:0.4em;">身份证</span>
              </div>
              <div class="am-form-group  am-form-icon">
                <select id="bankId" name="bankId" style="padding-left:5em!important;">
               	<@listing_childlist sign="account_bank"; listingList>
						<#list listingList as listing>
							<option value="${listing.keyValue}" <#if (listing.keyValue == accountBank.bankId)!> selected</#if>>${substring(listing.name, 30, "...")}</a>
							</option>
				</#list>
                </select>
                <span style="position:absolute;left:0.5em;top:0.4em;">选择银行</span>
              </div>
              <div class="am-form-group  am-form-icon">
                <input type="text"  id="branch" name="cashBank.branch"  value="${(userBank.branch)!}"  id="doc-ipt-3-a" class="am-form-field" style="padding-left:0.5em!important;" placeholder="请填写您的开户行">
              </div>
              <div class="am-form-group  am-form-icon">
                <input type="text" name="cashBank.cardNo" value="${(userBank.cardNo)!}"  id="cardNo" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');" id="doc-ipt-3-a" class="am-form-field" style="padding-left:0.5em!important;" placeholder="请填写您的账户">
              </div>
              <div class="am-form-group  am-form-icon">
                <input type="text" id="doc-ipt-3-a" class="am-form-field" style="padding-left:0.5em!important;" placeholder="">
              </div>
              <div style="padding:0 0 0.5em;">交易密码用户投资及提现时使用</div> 
              <div class="am-form-group  am-form-icon">
                 <input type="password" id="safepwd" name ="safepwd" class="am-form-field" style="padding-left:7em!important;" placeholder="8~20个字符">
                 <span style="position:absolute;left:0.5em;top:0.4em;"><#if btype==0>设置<#else>输入</#if>交易密码</span>
              </div>
              <button type="submit" class="am-btn am-btn-block am-radius color1 bgc">前往认证</button>
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
