<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-我的账户-添加银行卡</title>
	<#include "/content/common/meta.ftl">
<script type="text/javascript" src="${base}/static/js/jquery/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.cn.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">


<#include "/content/common/user_center_header.ftl">

<div class="content">
  <div style="width:1200px; margin:0 auto; padding-bottom:60px; margin-top:20px;">
    <!--left-->
	<#include "/content/common/user_center_left.ftl">
    <!--right-->
    <div style="width:942px; float:right; ">
      <div style="border:1px solid #e6e6e6;background:#fff;">
        <div style=" padding-top:10px; padding-left:10px; padding-bottom:50px; clear:both;">
          <div class="k_right_h1" style="padding-bottom:30px;"><img src="/img/tishi.png"><p>温馨提示:<span style="font-size:14px;">绑卡认证将验证您的姓名、身份证、银行卡以及办理该银行卡时登记的手机号码。绑卡时将扣取所填银行卡的3元），所扣的费用将以充值形式自动充入您的可用余额中。</span></p></div>
        <form id="bankInputForm" method="post" action="bankSignSave.do">
        <input type="hidden" name="accountBank.id" value="${(accountBank.id)!}">
         <table cellspacing="0" cellpadding="0" border="0" width="100%">
              <tbody>
              <tr height="20"><td></td><td></td></tr>
              <tr>
              	<td style="width:150px; padding-right:20px; text-align:right;color:#666; font-size:14px;">真实姓名</td>
                <td>
                  <input  type="text" name="user.realName" id="realName" value="${(loginUser.realName)!}" style="width:294px; height:44px; line-height:44px; padding-left:4px; border:1px solid #e6e6e6; border-radius:5px;color:#4c4c4c;" placeholder="">
                </td>
              </tr>
              <tr height="20"><td></td><td></td></tr>
              <tr>
                <td style="width:150px; padding-right:20px; text-align:right;color:#666; font-size:14px;">身份证号</td>
                <td>
                  <input type="text" name="user.cardId" value="${loginUser.cardId!}" id="cardId" style="width:294px; height:44px; line-height:44px; padding-left:4px; border:1px solid #e6e6e6; border-radius:5px;color:#4c4c4c;" placeholder="${loginUser.cardId!}">
                </td>
              </tr>
              <tr height="20"><td></td><td></td></tr>
              <tr>
                <td style="width:150px; padding-right:20px; text-align:right;color:#666; font-size:14px;">选择银行</td>
                <td style="position:relative; z-index:1;">
                 <select id="bankid" class="base_sel" name="accountBank.bankId"  style="width:200px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;">
	                <@listing_childlist sign="account_bank"; listingList>
						<#list listingList as listing>
							<option value="${listing.keyValue}" <#if (listing.keyValue == accountBank.bankId)!> selected</#if>>${substring(listing.name, 30, "...")}</a>
							</option>
						</#list>
					</@listing_childlist>
				  </select>
                 <#--
                  <input type="text" style="width:144px; height:44px; line-height:44px; padding-left:4px; border:1px solid #e6e6e6; border-radius:5px;" value="中国银行"  id="input_bank_po"/>
                  <div style=" background:url(/img/a25.png) center center no-repeat; width:25px; height:45px; position:absolute; left:124px; top:0; cursor:pointer;" id="btn_select_banksed"></div>
                  <ul style="width:240px; position:absolute; left:0; top:46px; border:1px solid #e6e6e6;border-bottom:0 solid #e6e6e6; z-index:99999; border-radius:3px; background:#fff; display:none;" class="bank_select_cr">
                    <li style="border-bottom:1px solid #e6e6e6; padding-left:5px; height:44px; line-height:44px; color:#666; font-size:14px; cursor:pointer;">农行</li>
                    <li style="border-bottom:1px solid #e6e6e6; padding-left:5px; height:44px; line-height:44px; color:#666; font-size:14px;cursor:pointer;">建行</li>
                    <li style="border-bottom:1px solid #e6e6e6; padding-left:5px; height:44px; line-height:44px; color:#666; font-size:14px;cursor:pointer;">工行</li>
                  </ul>
               -->
                </td>
              </tr>
              <tr height="20"><td></td><td></td></tr>
              <tr>
                <td style="width:150px; padding-right:20px; text-align:right;color:#666; font-size:14px;">银行卡号</td>
                <td>
              <input type="text" name="accountBank.account" value="${(accountBank.account)!}"  id="account" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');" maxlength="25" style="width:294px; height:44px; line-height:44px; padding-left:4px; border:1px solid #e6e6e6; border-radius:5px;color:#4c4c4c;"  />
                </td>
              </tr>
              <tr height="20"><td></td><td></td></tr>
              <#--
              <tr>
                <td style="width:150px; padding-right:20px; text-align:right;color:#666; font-size:14px;">开户银行所在城市</td>
                <td style="position:relative;">
                <input type="text" id="areaSelect" name="areaId" class="hidden" <#if  accountBank?exists>value="<#if accountBank.area!>${accountBank.area}<#elseif accountBank.city!>${accountBank.city}<#else>${(accountBank.province)!}</#if>" defaultSelectedPath="${accountBank.province!},${accountBank.city!},${accountBank.area!}"</#if> />

                </td>
              </tr>
              <tr height="20"><td></td><td></td></tr>
              -->
              <tr>
                <td style="width:150px; padding-right:20px; text-align:right;color:#666; font-size:14px;">开户行名称</td>
                <td>
                  <input type="text" id="branch" name="accountBank.branch"  value="${(accountBank.branch)!}" style="width:294px; height:44px; line-height:44px; padding-left:4px; border:1px solid #e6e6e6; border-radius:5px;color:#4c4c4c;"  />(可选)
                </td>
              </tr>
              <tr height="60">
                <td></td> 
                <td>
                    <input type="submit" value="前往认证" class="btn_t1" style="display:inline-block;" />
                    <input type="reset" value="取消" class="btn_t1" style="display:inline-block; background:#e6e6e6; color:#4d4d4d; margin-left:35px;"/>
                </td>
              </tr>
            </tbody>
           </table>
           </form>
          </div>      
        </div>
</div>
    <div style="width:100%;clear:both; height:1px;"></div>
  </div>
</div><!-- content end -->     

<#include "/content/common/footer.ftl">

<script type="text/javascript" src="${base}/static/js/common/bankInput.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
    <script type="text/javascript">
		$().ready( function() {
			
			$("#header_wdzh").addClass("hq");<#-- header.ftl 选中样式 -->
			$("#yhkgl").addClass("click_current");<#-- user_center_left.ftl 选中样式 -->
			var $areaSelect = $("#areaSelect");
			
			// 地区选择菜单
			$areaSelect.lSelect({
				url: "${base}/area/ajaxArea.do"// AJAX数据获取url
			});
			var $bankInputForm = $("#bankInputForm");
			$("#account").bankInput();
			
			 // 表单验证
			$bankInputForm.validate({
				rules: {
					
					"user.realName":{
						required: true,
						realName:true,
						minlength:2,
						maxlength:20
					},
					"user.cardId":{
						required: true,
						isIdCardNo:true,
						remote:"${base}/userCenter/checkCardiId.do"
					},
					"accountBank.bankId":{
						required: true
					},
					"accountBank.account":{
						required: true,
						minlength: 12,
						maxlength:26
					},
					"accountBank.branch":{
						minlength: 5,
						maxlength: 50
					}
				},
				messages: {
					
					"user.realName":{
						required: "真实姓名不能为空",
						realName:"真实姓名格式错误",
						minlength: "最少2个字",
						maxlength: "最多20个字"
					},

					"user.cardId":{
						required:"身份证号码不能为空",
						isIdCardNo:"身份证号码格式不对",
						remote:"该身份证号之前已经通过实名认证"
					},
					"accountBank.bankId":{
						required: "请选择开户银行",
					},
					"accountBank.account":{
						required: "请输入银行账号",
						minlength: "银行账号最少10位",
						maxlength: "银行账号最多21位"
					},
					"accountBank.branch":{
						minlength: "最少5个字符",
						maxlength: "最多50个字符"
					}
				},
				errorPlacement: function(error, element) {
				  error.appendTo(element.parent());
				},
				submitHandler: function(form) {
					form.submit();
				}
			});
		});

	</script>
</body>
</html>
