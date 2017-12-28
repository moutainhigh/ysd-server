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
      <div style="border:1px solid #e6e6e6;background:#fff; height:351px; text-align:center;">
       <img src=" <#if user.litpic>${Application["qmd.img.baseUrl"]}/web${user.litpic}<#else>/img/tou.png</#if>" width="148" height="148" style="border-radius:50%; margin-top:40px;" id="imgView"/>
        <div style="color:#4c4c4c; font-size:18px; padding:10px 0;">更换头像</div>
        <div style="color:#4c4c4c; font-size:14px;">请点击按钮，选择图片并确定即可更换</div>
         <a class="btn_t1" style="display:inline-block;">上传头像</a>
      </div>
    </div>
    <div style="width:100%;clear:both; height:1px;"></div>
  </div>
</div><!-- content end -->



<#include "/content/common/footer.ftl">
<script src="/js/jquery.upload.v2.js"></script>
<script>
	$('.left_kuang li').last().css('border-bottom','0');
	$('.user_list_qh li').click(function(){
		var i=$(this).index();
		$('.user_list_qh li').removeClass('tzlist_user');
		$(this).addClass('tzlist_user');
		$('.user_div_qh').css('display','none');
		$('.user_div_qh').eq(i).css('display','block');
	});
	
	$('.btn_t1').upload({
		action : "${base}/member/ajaxUploadLitpic.do",
		fileName : "imageFile",
		params : {}, 
		accept : ".jpg,.png,.gif,.jpeg", 
		maxNum : 1,
		complete : function(data) {
			data = JSON.parse(data);
			if (data.error == "0") {
				
				$('#imgView').attr('src','${Application["qmd.img.baseUrl"]}/web' + data.url);
				$('#center_header_img_id').attr('src','${Application["qmd.img.baseUrl"]}/web' + data.url);
				alertMessage('头像上传成功！');
			} else 
				alertMessage(data.message);
		}
		
	});
</script>  
 </body>
</html>
