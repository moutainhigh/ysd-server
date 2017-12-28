<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户</title>
<#include "/content/common/meta.ftl">
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
        <div style="  width:100%;background:#eeeeee; height:51px; border-bottom:1px solid #e6e6e6;">
            <a class="hr hre">实名认证</a>
        </div>
        <div style=" padding-top:10px; padding-left:50px; padding-bottom:50px; clear:both;">
          <div class="k_right_h1"><img src="/img/tishi.png"><p>认证提示:</p></div>
          <div style="color:#666666; font-size:14px; padding-left:70px; line-height:25px; margin-bottom:30px;">1.每位投资者有5次免费实名认证机会，你目前还有<font style="color:#fd7c1a;"><#if user.sceneStatus==11||user.sceneStatus==12>0<#else>${5-user.sceneStatus}</#if></font>次机会，请认证核实信息再提交！<br />
2.若实名认证不通过或者超过次数，可联系我们客服申请人工实名，客服电话：400-057-7820
          </div>   
         <table cellspacing="0" cellpadding="0" border="0" width="100%">
         	<form action="${base}/member/identity.do" method="post" >
              <tbody>
              <tr height="20"><td></td><td></td></tr>
              <tr>
                <td style="width:115px; padding-right:20px; text-align:right;color:#666; font-size:14px;">姓名</td>
                <td>
                	<#if user.sceneStatus==11||user.sceneStatus==12>
                	${user.realName}
                	<#else>
                  	<input type="text" style="width:294px; height:44px; line-height:44px; padding-left:4px; border:1px solid #e6e6e6; border-radius:5px;color:#b2b2b2;" placeholder="请输入姓名" name="user.realName">
                	</#if>
                </td>
              </tr>
              <tr height="20"><td></td><td></td></tr>
              <tr>
                <td style="width:115px; padding-right:20px; text-align:right;color:#666; font-size:14px;"><span style="position:relative; top:-7px;">身份证号</span></td>
                <td>
                	<#if user.sceneStatus==11||user.sceneStatus==12>
                	${user.cardId}
                	<#else>
                	<input type="text" style="width:294px; height:44px; line-height:44px; padding-left:4px; border:1px solid #e6e6e6; border-radius:5px; margin-bottom:5px; color:#b2b2b2;" placeholder="请输入身份证号码" name="user.cardId">
                	</#if>
                  
                </td>
              </tr>
              <tr height="60">
                <td></td> 
                <td>
                    <input class="btn_t1" <#if user.sceneStatus==11||user.sceneStatus==12>disabled="disabled" value="实名认证通过" style="width:150px;display:inline-block;background: #bfbfbf;"<#else> style="display:inline-block;"  value="确认提交"</#if> type="submit"/>
                </td>
              </tr>
            </tbody>
            </form>
           </table>
          </div>      
        </div>
    </div>
    <div style="width:100%;clear:both; height:1px;"></div>
  </div>
</div><!-- content end -->

<#include "/content/common/footer.ftl">
<script>
	$('.left_kuang li').last().css('border-bottom','0');
	$('.user_list_qh li').click(function(){
		var i=$(this).index();
		$('.user_list_qh li').removeClass('tzlist_user');
		$(this).addClass('tzlist_user');
		$('.user_div_qh').css('display','none');
		$('.user_div_qh').eq(i).css('display','block');
	});
</script>  
 </body>
</html>
