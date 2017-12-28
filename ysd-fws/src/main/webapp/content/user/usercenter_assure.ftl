<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-我的账户</title>
	<#include "/content/common/meta.ftl">
</head>
<body>
 <!-- header -->
<#include "/content/common/user_center_header.ftl">
<div class="user_middle" >
<div class="user_content">
    <div class="user_content_bottom kaqu_dl">
        <div class="kaqu_x0">
            <div class="kaqu_x1">
                <span  class="kaqu_x2"> 
                  <span class="kaqu_x3">              
                   <a href="">乐商贷首页</a>&nbsp;
                   <a>></a>&nbsp;
                   <a href="">投资中心</a>
                  </span>
         	   </span>	
            </div><!--kaqu_x1 end-->
        </div><!--kaqu_x0 end--> 
       <div class="kaqu_ca" style=" border-bottom:1px solid #cccbca;">
          <ul class="kaqu_cb">
              <li class="kaqu_cc">我的乐商贷</li>
              <li class="kaqu_cd"><a>用户名：${loginUser.username}</a></li>
         </ul>
          <ul class="kaqu_cf">
              <li class="kaqu_cj">上次登录时间：${loginUser.lastTime?string("yyyy-MM-dd HH:mm:ss")}</li>
              <li><a>上次登录IP：&nbsp;&nbsp;${loginUser.lastIp}</a></li>
         </ul>
         <div style="clear:both;"></div>
      </div><!--kaqu_ca end-->
      <div style=" clear:both"></div>
      <div class="kaqu_changyonggongneng">
           <div class="kaqu_happy">
                <div class="kaqu_dedeersed"><img src="<@imageUrlDecode imgurl="${agencyDb.logo}"; imgurl>${imgurl}</@imageUrlDecode>" width="235" height="80" /></div>
                <div class="kaqu_shengshidanbaoyouxi">
                  <h3 class="kaqu_padernrer">${loginUserInfo.privateName}</h3>
                  <span class="kaqu_jerderf">欢迎登陆，您是乐商贷的<font>担保服务商</font>！</span>
                </div>
                <div style="clear:both"></div>
                <div style=" clear:both"></div>
                <h3 class="kaqu_sguew">常用功能：</h3>
                <div class="kaqu_kdsedddjeas">
                    <div class="kaqu_zichanpianguanl"><a href="${base}/agency_db/detail.do">账号信息</a></div>
                    <div class="kaqu_zichanpianguanl"><a href="${base}/agency_db/subpageDetail.do">子页面管理</a></div>
                    <div class="kaqu_zichanpianguanl"><a href="${base}/userCenter/toPassword.do">密码管理</a></div>
                </div><!--kaqu_kdsedddjeas end-->
                <ul class="kaqu_herdwere">
                    <li>联系乐商贷</li>
                    <li>联系电话：${Application ["qmd.setting.phone"]}</li>
                    <li class="kaqu_binjiangdizhi">联系地址：${Application ["qmd.setting.address"]}</li>
                </ul>
          </div><!--kaqu_happy end-->
          <div class="kaqu_dashalushnag"><img src="${base}/static/img/stronger.png" width="302" height="431" /></div>
          <div style=" clear:both; padding-bottom:15px;"></div>
      </div><!--kaqu_changyonggongneng end-->
	  
	  
	  
    </div><!--user_content_bottom end-->
   <div style=" background-image:url(${base}/static/img/3_bg.png); background-position:0 bottom; background-repeat:repeat-x; height:9px;"></div>
</div><!--user_content end-->
<div style=" clear:both; padding-bottom:50px;"></div>
</div><!--user_middle end-->
			<div style=" clear:both"></div>
		</div>
		<!--user_content_bottom end-->
		<div style=" background-image:url(${base}/static/img/3_bg.png); background-position:0 bottom; background-repeat:repeat-x; height:9px;"></div>
	</div>
	<!--user_content end-->
	<div style=" clear:both; padding-bottom:50px;"></div>
</div>
<!--user_middle end-->
<div style=" clear:both"></div>

<#include "/content/common/footer.html">

</body>
</html>
