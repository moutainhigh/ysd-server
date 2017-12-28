<!DOCTYPE html>
<html>
  <head>
  <meta charset="gb2312" />
  <title>${Application ["qmd.setting.name"]}-借款人-借款标信息填写页</title>

  <meta name="application-name" content="网络P2P借贷" />
   <meta name="msapplication-tooltip" content="网络P2P借贷-上海借贷-杭州借贷" />
   <meta http-equiv="x-ua-compatible" content="ie=7" />
	<meta name="keywords" content="${Application ["qmd.setting.metaKeywords"]}" />
	<meta name="description" content="${Application ["qmd.setting.metaDescription"]}" />
	<meta name="robots" content="all" />
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="${base}/static/js/jquery/jquery-1.8.1.js"></script>
    <script type="text/javascript" src="${base}/static/js/jquery/jquery.lSelect.js"></script>
     <script type="text/javascript" src="${base}/static/js/borrow/commondiv.js"></script>
	<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
    	
    
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
	<!--
	<script type="text/javascript">
	
		$(document).ready(
			function(){
				alert("jquery加载成功");
			}
		);
		
	</script>
	-->
<SCRIPT language=javascript> 
 
 
 $().ready(function() {
 
	if(typeof(KindEditor) != "undefined") {
	KindEditor.ready(function(K) {
		editor = K.create("#editor", {
		 	width:"600px" ,
			height: "350px",
			items: ["source", "|", "undo", "redo", "|", "preview", "print", "template", "cut", "copy", "paste", "plainpaste", "wordpaste", "|","justifyleft", "justifycenter", "justifyright", "justifyfull", "insertorderedlist", "insertunorderedlist", "indent", "outdent", "subscript", "superscript", "clearhtml", "quickformat", "selectall", "|", "fullscreen", "/", "formatblock", "fontname", "fontsize", "|", "forecolor", "hilitecolor", "bold", "italic", "underline", "strikethrough", "lineheight", "removeformat", "|", "image", "flash", "media", "insertfile", "table", "hr", "emoticons", "map", "pagebreak", "link", "unlink"],
			syncType: "form",
			allowFileManager: true,
			uploadJson:"${base}/file/ajaxUpload.do",
			fileManagerJson: "${base}/file/ajaxBrowser.do",
			afterChange: function() {
				this.sync();
			}
		});
	});
}	
})	
<!-- 计算天利息!-->
function commit(obj)
{
	if (parseFloat(obj.value) > 0)
	{
		var realMoney=parseFloat(obj.value);
		var inputValue=parseFloat(obj.value);
		var apr = document.getElementById("apr").value;
		var dateApr = changeTwoDecimal(parseFloat(apr/365));
		document.getElementById("dateapr").value = dateApr;
		document.getElementById("dateapr").innerHTML=dateApr;
	}
}
	<!-- 弹出层!-->
	function setRepayment(objName){
		var ojb = $("#repay"+objName);
    	var oldValue=parseInt(objName);
    	var number = document.getElementById("number").value
    	for(var i=0;i<number;i++){
    		var obj = $("#repay"+i);
    		obj.remove();
    	}
    	
    	var num = changeTwoDecimal(parseFloat(document.getElementById("timeLimit").value/10));
    	document.getElementById("number").value=num
    	if(num==0){
		}else{ 
	    	for(var i=num-1;i>0;i--){
	    		var j=parseFloat(i)+parseFloat(1);
	    		var tet=j*10;
	    		
	    			ojb.after("<tr id='repay"+i+"'><td><input type=\"checkbox\" name=\"testdate\" value=\""+tet+"\"> "+tet+"天&nbsp;<input type=\"text\" id=\"txt"+i+"\" name=\"txt"+i+"\" value=\"0\"  onkeyup=\"value=value.replace(/[^0-9]/g,'')\" />元</td></tr>");
	    	}
	    	openLayer('text','test_con3','');
	    }
    	
		
	}
	
function chk(){
  var obj=document.getElementsByName('testdate');  //选择所有name="date"的对象，返回数组
  //取到对象数组后，我们来循环检测它是不是被选中
  var s='';
  var str;
  var qishu=0;
  var apr = document.getElementById("dateapr").value;
  for(var i=0; i<obj.length; i++){
  	var money;
    if(obj[i].checked) {
    	 qishu ++;
    	alert(document.getElementById("txt"+i));
    	var ojb = document.getElementById("txt"+i);
    	money=obj[i].value+','+ojb.value+';';  //如果选中，将value添加到变量s中
    	s+=money;
    	var total =	parseFloat(ojb.value*apr)+parseFloat(ojb.value);
    	 str += "<tr id='repayList"+i+"'><td>"+qishu+"</td>&nbsp; <td>第"+obj[i].value+"天</td>&nbsp;<td>"+total+"</td>&nbsp;<td>"+ojb.value+"</td>&nbsp;<td>"+ojb.value*apr+"</td></tr>";
    	
    	//$("#test").html("<tr id='repayList"+i+"'><td>"+qishu+"</td>&nbsp; <td>第"+obj[i].value+"天</td>&nbsp;<td>"+total+"</td>&nbsp;<td>"+ojb.value+"</td>&nbsp;<td>"+ojb.value*apr+"</td></tr>");
    	//$(ojb).after("<tr id='repayList"+i+"'><td>"+qishu+"</td>&nbsp; <td>第"+obj[i].value+"天</td>&nbsp;<td>"+total+"</td>&nbsp;<td>"+ojb.value+"</td>&nbsp;<td>"+ojb.value*apr+"</td></tr>");
       // qishu ++; 
        alert("==>"+qishu);
    }
  }
  str = "<table><tr><td>期数</td><td>还款日期</td><td>总额</td><td>本金</td><td>利息</td></tr>"+str+"</table>"
    	$("#test").html(str);
  document.getElementById("repayMes").value=s;
  //那么现在来检测s的值就知道选中的复选框的值了
  alert(s==''?'你还没有选择任何内容！':s);
}


function changeTwoDecimal(x)
{
var f_x = parseFloat(x);
if (isNaN(f_x))
{
alert('您输入的数据不完整');
return false;
}
var f_x = Math.round(x*100)/100;
var s_x = f_x.toString();
var pos_decimal = s_x.indexOf('.');
if (pos_decimal < 0)
{
pos_decimal = s_x.length;
s_x += '.';
}
while (s_x.length <= pos_decimal + 2)
{
s_x += '0';
}
return s_x;
}


    var tabIndex;
 
    function SelectMenu(index) {
 
        for (i = 1; i <= 6; i++) 
        {
            if (i == index)
                continue;
            if (document.getElementById("sub_menu_" + i) && document.getElementById("sub_menu_" + i).style.display != "none")
                document.getElementById("sub_menu_" + i).style.display = "none";
                
            if(i == tabIndex)
                continue;
                
            if (document.getElementById("menu_" + i) && document.getElementById("menu_" + i).className != "mainmenu")
                document.getElementById("menu_" + i).className = "mainmenu";
         }
 
        if (document.getElementById("sub_menu_" + index) && document.getElementById("sub_menu_" + index).style.display != "block")
            document.getElementById("sub_menu_" + index).style.display = "block";
        else if(tabIndex == 0) 
            document.getElementById("sub_menu_1").style.display = "block";
            
        if (document.getElementById("menu_" + index) && document.getElementById("menu_" + index).className != "CurMenu") {
            document.getElementById("menu_" + index).className = "CurMenu";
        }
    }
    
    function HideAllMenu() 
    {
        for (i = 1; i <= 6; i++) {
            if (i == tabIndex)
                continue;
            if (document.getElementById("sub_menu_" + i) && document.getElementById("sub_menu_" + i).style.display == "block")
                document.getElementById("sub_menu_" + i).style.display = "none";
            if (document.getElementById("menu_" + i) && document.getElementById("menu_" + i).className != "mainmenu")
                document.getElementById("menu_" + i).className = "mainmenu";
        }
         if (document.getElementById("sub_menu_" + tabIndex))
            document.getElementById("sub_menu_" + tabIndex).style.display = "block";
         else
            document.getElementById("sub_menu_1").style.display = "block";
        
          if (document.getElementById("menu_" + tabIndex) && document.getElementById("menu_" + tabIndex).className != "CurMenu") 
            document.getElementById("menu_" + tabIndex).className = "CurMenu";
    }
      
    function HideMenu(e, subMenuElementID)
    {
        if(!isMouseToSubMenu(e, subMenuElementID))
        HideAllMenu();
    } 
    
    function HideSubMenu(e, handler)
    {
        if(isMouseLeaveOrEnter(e, handler))
        {
 
        HideAllMenu();
        }
    } 
    
 
    function isMouseLeaveOrEnter(e, handler)
    {
      if (e.type != 'mouseout' && e.type != 'mouseover') return false;
       var reltg = e.relatedTarget ? e.relatedTarget : e.type == 'mouseout' ? e.toElement : e.fromElement;
       while (reltg && reltg != handler)
                 reltg = reltg.parentNode;
       return (reltg != handler);
    }
 
    function isMouseToSubMenu(e, subMenuElementID)
    {
     if (e.type != 'mouseout')
        return false;
     var reltg = e.relatedTarget ? e.relatedTarget : e.toElement;
     while(reltg && reltg.id != subMenuElementID)
        reltg = reltg.parentNode;
       
      return reltg;
    }
    
    function addImage(objName) {
    	var ojb = $("#testtest"+objName);
    	var oldValue=parseInt(objName);
    	oldValue++;
    	for(var num=0;num<oldValue;num++){
    	ojb.after("<tr id='testtest"+oldValue+"'><td>&nbsp;</td><td><input type=\"file\" value=\""+oldValue+"\"  name=\"borImagesFile\">&nbsp;<input type=\"button\" id=\"btn_"+oldValue+"\" value=\""+oldValue+"\" onclick=\"addImage('"+oldValue+"');\"/></td></tr>");
    	}
    	var btnOjb = $("#btn_"+objName);
    	btnOjb.after("<input type=\"button\" onclick=\"deleteImage('"+objName+"')\" value=\"删除\"/>");
    	btnOjb.remove();
    }
    
    function deleteImage(objName) {
    	var ojb = $("#testtest"+objName);
    	ojb.remove();
    }
 
</SCRIPT>
<SCRIPT type=text/javascript>       
</SCRIPT>
 
<script type="text/javascript"> 
 
function $(id)
{
   return document.getElementById(id);
}
var anndelay = 3000;
var anncount = 0;
var annheight = 24;
var annst = 0;
function announcementScroll()
{
 
   if( ! annst)
   {
      $('announcementbody').innerHTML += '<br style="clear: both" />' + $('announcementbody').innerHTML;
     $('announcementbody').scrollTop = 0;
      if($('announcementbody').scrollHeight > annheight * 3)
      {
         annst = setTimeout('announcementScroll()', anndelay);
      }
      else
      {
         $('announcement').onmouseover = $('announcement').onmouseout = null;
      }
      return;
   }
   if(anncount == annheight)
   {
      if($('announcementbody').scrollHeight - annheight <= $('announcementbody').scrollTop)
      {
         $('announcementbody').scrollTop = $('announcementbody').scrollHeight / 2 - annheight;
      }
      anncount = 0;
      annst = setTimeout('announcementScroll()', anndelay);
   }
   else
   {
      $('announcementbody').scrollTop ++ ;
      anncount ++ ;
      annst = setTimeout('announcementScroll()', 10);
   }
   
}
//announcementScroll();
 
</script>
	
	<script type="text/javascript">
		function subForm(){
			alert("输入用户名和密码是：" + $("#username").val() + "/" + $("#password").val());
			$("#loginForm").submit();
		}
		
function changeValidateCode(obj) {
        var timenow = new Date().getTime();
           //每次请求需要一个不同的参数，否则可能会返回同样的验证码
        	//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。
        obj.src="${base}/rand.do?d="+timenow;
	}
	
var daishou = 331044;
	var video_status = 1;
	var scene_status = 1;
	var phone_status = 1;
        var vip_status = 1;
	var crmoney = 0.00;
	var cr = 9255;
	var real_s = 1;
	var danbao_money = 0.00;
	var sxf = 2;
	
		//alert("你的借款信用额度为0.00元,你还能借-9564.67元");
	var danbao = 0;
	var max_account = -9564.67;
	var max_fax =20;
var max_apr =24.8;
 
 var maxdai = 1000000000; var max_account=1000000000; var fastbiao = 1;
 var miaobiao_is = 0;
 var jinbiao = 0;
 
					
jQuery('#changetoDay').click(function(){
 
			var isday=jQuery('#isday').val();
		
			if(isday==0){
				jQuery('#isday').val('1');
				 
				jQuery('#time_limit_day').show();
				jQuery('#time_limit').hide();
			}else{
				jQuery('#isday').val('0');
				 
				jQuery('#time_limit_day').hide();
				jQuery('#time_limit').show();
			}
 
			jQuery('#borrow_day').toggle('slow');
			
								
			});
 
 
 
 
 
function checkDXB(){
    var frm = document.forms['form1'];
    if(frm.elements['isDXB'].checked){
        frm.elements['pwd'].disabled=false;
    }else{
        frm.elements['pwd'].disabled=true;
        frm.elements['pwd'].value="";
    }
}
    function openRepayPlan() {
    	var num = $("#borrow_timeLimitDay").val();
    	var temp = window.showModalDialog("repayPlan.do?num="+num,window,"status:no;scroll:no;dialogWidth:600px;dialogHeight:400px;");
    	if (temp=='') {
    		return;
    	}
    	
    	$("#borrow_orderList").val(temp);
    	
    	temp = "["+temp+"]";
    	var data=eval("("+temp+")");
    	
    	alert(data);
    	
    	var str="<table>";
    	str+="<tr><td>还款期数</td><td>还款日期</td><td>还款总额</td><td>还款本金</td><td>还款利息</td></tr>";
    	
    	var borrow_apr = $("#borrow_apr").val();
    	var apr = parseFloat(borrow_apr)/100;
    	var borrow_account = $("#borrow_account").val();
    	var account = parseFloat(borrow_account);
    	apr = apr/365;
    	var preday = 0;
    	
    	$.each(data,function(idx,item) {
    		var pday = parseFloat(item.payday)-preday;
    		var interest = account*pday*apr;
    		interest=interest.toFixed(2);
    		var total = parseFloat(interest)+ parseFloat(item.payMoney);
    		total=total.toFixed(2);
    		account = parseFloat(account)-parseFloat(item.payMoney);
    		preday = pday;
			str+="<tr><td>"+(idx+1)+"</td><td>"+item.payday+"</td><td>"+total+"</td><td>"+item.payMoney+"</td><td>"+interest+"</td></tr>";
		})
		str+="</table>";
		
		$("#idplan").html(str);
    }
function check_form(){
   
	var frm = document.forms['form1'];
	 var account = frm.elements['account'].value;
	 var title = frm.elements['name'].value;
	 var style = frm.elements['style'].value;
	 var content = frm.elements['content'].value;
	 var time_limit = frm.elements['time_limit'].value;
	 var award = get_award_value();
	 var part_account = frm.elements['part_account'].value;
	 var funds = frm.elements['funds'].value;
	
	 var valicode = frm.elements['valicode'].value;
	 var most_account = frm.elements['most_account'].value;
	 var use = frm.elements['most_account'].value;
	  var useapr = frm.elements['apr'].value;
	  var apr = frm.elements['apr'].value;
	 var lowest_account = frm.elements['lowest_account'].value;
	
	 var errorMsg = '';
	  if (account.length == 0 ) {
		errorMsg += '- 总金额不能为空' + '\n';
	  }
	
	  if(account<500)
	  {
		  	errorMsg += '-借款金额不在指定的范围内' + '\n';
	  } 
	    if(account>2000000)
	  {
		  	errorMsg += '-借款金额不在指定的范围内' + '\n';  
			alert(errorMsg);
			return false;
	  } 
	
	  if(danbao){//担宝
              if(account > danbao_money){//如果大于担保额度,不允许
                  errorMsg += '- 您当前的贷款金额大于您的可用担保额度！' + '\n';
              }else if(cr < 30 || real_s != '1'){
                  errorMsg += '- 您尚未通过实名认证' + '\n';
                  alert(errorMsg);
                  location.href='/index.php?user&q=code/user/realname';
                  return false;
              }else if(vip_status != 1){
                            errorMsg += '-您不是VIP用户，请先申请VIP！' + '\n';
                            alert(errorMsg);
                            location.href="${base}/vip/index.html";
                            return false;
              }else if(cr <30){
                  errorMsg += '- 您当前的积分小于30分，请上传资料进行认证!' + '\n';
                  alert(errorMsg);
                  location.href='/index.php?user&q=code/attestation';
                  return false;
              }//else if(video_status != '1' && scene_status != '1'){
                 // errorMsg += '- 您未进行视频认证或者现场认证，请先进行相关认证' + '\n';
                  //alert(errorMsg);
                  //location.href='/index.php?user&q=code/user/video_status';
                  //return false;
             // }
	      else if(account>(danbao_money)) errorMsg += '- 借款金额大于担保额度' + '\n';
	  }else if(miaobiao_is){//秒标
	  		//var donjie = parseFloat(apr)*parseFloat(account)/(100*12) + parseFloat(account)/100*sxf;//冻结资金
                        //var donjie = parseFloat(apr)*parseFloat(account)/(100*12);
	  		//if(parseInt(total_zi) < parseInt(donjie)) errorMsg += '- 您的账户总余额小于(利息+管理手续费的金额)' + '\n';
	  }else if(fastbiao){
	  		if(scene_status != '1'){
                          errorMsg += '- 您未进行现场认证，请先进行现场认证' + '\n';
                          alert(errorMsg);
                          location.href='/index.php?user&q=code/user/scene_status';
                          return false;
                        } 
	  		else if(account>2000000) errorMsg += '- 贷款总金额不能大于200万' + '\n';
                        
	  }else if(jinbiao){//净值标:账户总额减去冻结总额减去待还总额减去替人担保金额
       		  if(daishou<account){
			  	 errorMsg += '-您的借出金额为'+daishou+'元,小于您的借入金额，不能发净资产标！' + '\n';
			  }else if(jinMoney < 500){
                          errorMsg += '-您的净资产小于500，不能发净资产标！' + '\n';
              }else if(account>jinMoney){
                          errorMsg += '-借款金额不能大于净资产！' + '\n';
              }else if(phone_status != '1'){
                          errorMsg += '- 您未进行手机认证，请先进行手机认证' + '\n';
                          alert(errorMsg);
                          location.href='/index.php?user&q=code/user/phone_status';
                          return false;
              }
              
          }else{//信用标:
	  		if(real_s != '1'){
                            errorMsg += '-请先通过实名认证！' + '\n';
                            alert(errorMsg);
                            location.href='/index.php?user&q=code/user/realname';
                            return false;
                        }else if(vip_status != 1){
                            errorMsg += '-您不是VIP用户，请先申请VIP！' + '\n';
                            alert(errorMsg);
                            location.href="${base}/vip/index.html";
                            return false;
                        }else if(cr < 30 ){
                            errorMsg += '-您的积分小于30分,请上传资料进行认证！' + '\n';
                            alert(errorMsg);
                            location.href="${base}/index.php?user&q=code/attestation";
                            return false;
                        }
                        ////else if(video_status != '1' && scene_status != '1'){
                          //errorMsg += '- 您未进行视频认证或者现场认证，请先进行相关认证' + '\n';
                          //alert(errorMsg);
                          //location.href='${base}/index.php?user&q=code/user/video_status';
                          //return false;
                      // }
 
			else  if(account>max_account && !czb) errorMsg += '- 借款金额大于可用信用额度' + '\n';
	  }
	
	  if (apr.length == 0 ) {
		errorMsg += '- 利率不能为空' + '\n';
	  }
	 
	  if (time_limit >=1 && time_limit<=6 && apr>23.24) {
		errorMsg += '- 1至6个月的年利率不能超过23.24%' + '\n';
	  }else if (time_limit >6  && apr>25.24) {
		errorMsg += '- 6至12个月的年利率不能超过'+max_fax+'%' + '\n';
	  }
	  
	  if (apr>max_apr) {
		errorMsg += '- 利率不能大于'+max_apr+'%' + '\n';
	  }
	    if (apr<1 ) {
		errorMsg += '- 利率不能小于1%' + '\n';
	  }
	  
	  if (award==1 && (part_account=="" || part_account<5 || part_account>account*0.02)) {
		errorMsg += '- 固定金额分摊奖励不能低于5元,不能高于总标的金额的2%' + '\n';
	  }
	  if (award==2 && (funds =="" || funds<0.1 || funds>6)) {
		errorMsg += '- 投标金额比例奖励0.1%~6% ' + '\n';
	  }
	  if (most_account!=0 && parseInt(most_account)<parseInt(lowest_account)){
		  errorMsg += '- 投标最大金额不能小于最小金额' + '\n';
	  }
	  if (title.length == 0 ) {
		errorMsg += '- 标题不能为空' + '\n';
	  }
	  if (content.length == 0 ) {
		errorMsg += '- 内容不能为空' + '\n';
	  }
	  if (valicode.length == 0 ) {
		errorMsg += '- 验证码不能为空' + '\n';
	  }
 
	
	var awa = "";
	for(var i=0;i<frm.award.length;i++){   
	   if(frm.award[i].checked){
		 awa =  frm.award[i].value;
		}
	} 
 
 
	if(awa==1){
		if (part_account==""){
			errorMsg += '- 固定分摊比例奖励不能为空 ' + '\n';
		}
	}
	if(awa==2){
		if (funds==""){
			errorMsg += '- 投标金额比例奖励不能为空 ' + '\n';
		}
	}
	
	  if (errorMsg.length > 0){
		alert(errorMsg); return false;
	  } else{  
		return true;
	  }
 
}
function get_award_value()
{
    var form1 = document.forms['form1'];
    
    for (i=0; i<form1.award.length; i++)    {
        if (form1.award[i].checked)
        {
           return form1.award[i].value;
        }
    }
}
function change_j(type){
	var frm = document.forms['form1'];
	if (type==0){
                jQuery("#part_account").attr("disabled",true); 
		jQuery("#funds").attr("disabled",true); 
                jQuery("#is_false").attr("disabled",true); 
              document.getElementById("award").value=0; 
                //frm.elements['part_account'].disabled = "disabled";
		//frm.elements['funds'].disabled = "disabled";
		//frm.elements['is_false'].disabled = "disabled";
	}else if (type==1){
                jQuery("#part_account").attr("disabled",false); 
		jQuery("#funds").attr("disabled",true); 
                jQuery("#is_false").attr("disabled",false); 
           document.getElementById("award").value=1;     
		//frm.elements['part_account'].disabled = "";
		//frm.elements['funds'].disabled = "disabled";
		//frm.elements['is_false'].disabled = "";
	}else if (type==2){
            
                jQuery("#part_account").attr("disabled",true); 
		jQuery("#funds").attr("disabled",false); 
                jQuery("#is_false").attr("disabled",false); 
          document.getElementById("award").value=2;          
		//frm.elements['part_account'].disabled = "disabled";
		//frm.elements['funds'].disabled = "";
		//frm.elements['is_false'].disabled = "";
	}
}	
	</script>
  </head>
  
  <body>
   <!-- header -->
	<#include "/content/common/header.ftl">
	<!--content-->
    <div id="main2">
 
<!--content-->
  <div class="index mt">
  <ul class="cb">
      <p class="c_b"><a href="${base}/">${Application ["qmd.setting.name"]}</a> &gt; <a href="${base}/userCenter/index.do">我的账户</a> &gt <a href="#">资金管理</a> &gt 我要提现</p>
	  </ul>
      <!--left-->
	    <#include "/content/common/user_center_left.ftl">
	         <!--right-->
	         <form name="form1" method="post" action="insertPledge.do"  enctype="multipart/form-data" >
	         <input type="hidden" id="borrow_orderList" name="borrow.orderList" value="" />
	         <div class="c_right">
			     <div class="top_bg"></div>
				 <div class="rightwrap">
				      <div class="rightcon">
				      <h2><strong>我要借款</strong> </h2>
					    
                          <table class="autotou clr cassaddtop" id ="text">
							                                
							  <tbody>
							  <tr>
							  	<td class="label" >标类型：</td>
							  	<td>
							  		<select id="type" class="ddlist" name="borrow.type">
	                                    <@listing_childlist sign="borrowtype"; listingList>
											<#list listingList as listing>
												<option value="${listing.description}" >${listing.name}</a>
												</option>
											</#list>
										</@listing_childlist>
									</select></td></tr>
							  	<tr >
						      		<td  class="label" >借款标题：</td>
						      		<td><input type="text" name="borrow.name" value="" size="50" /></td>
                              	</tr>
                              	<tr >
						      		<td  class="label" >借款人类型：</td>
						      		<td><#if memberType==0>个人<#else> 企业</#if></td>
                              	</tr>
                              
                              	<tr >
						      		<td  class="label" >借款金额：</td>
						      		<td><input type="text" id="borrow_account" name="borrow.account" value="" size="50"  onkeyup="value=value.replace(/[^0-9]/g,'')" /></td>
                              	</tr>
                              	<tr >
						      		<td  class="label" >年化利率：</td>
						      		<td><input type="text" id="borrow_apr" name="borrow.apr" value="" size="10" onKeyUp="value=value.replace(/[^0-9.]/g,'')" />%</td>
                              	</tr>
                              	<tr >
						      		<td  class="label" >最小投资额：</td>
						      		<td><select id="lowestAccount" class="ddlist" name="borrow.lowestAccount">
	                                    	<@listing_childlist sign="borrow_lowest_account"; listingList>
												<#list listingList as listing>
													<option value="${listing.description}" >${listing.name}</option>
												</#list>
											</@listing_childlist>
										</select></td>
                              	</tr>
                              	<tr >
						      		<td  class="label" >最大投资额：</td>
						      		<td>
						      			<select id="mostAccount" class="ddlist" name="borrow.mostAccount">
	                                   		<option value="" selected="selected">请选择</option>
		                                    <@listing_childlist sign="borrow_most_account"; listingList>
												<#list listingList as listing>
													<option value="${listing.description}" >${listing.name}</option>
												</#list>
											</@listing_childlist>
										</select></td>
                              	</tr>
                              	<tr >
						      		<td  class="label" >投资有效期：</td>
						      		<td>
										<select id="validTime" class="ddlist" name="borrow.validTime">
	                                    	<@listing_childlist sign="borrow_valid_time"; listingList>
												<#list listingList as listing>
													<option value="${listing.description}" >${listing.name}</option>
												</#list>
											</@listing_childlist>
										</select></td>
                              	</tr>
                              	<tr >
						      		<td  class="label" >投资奖励：</td>
						      		<td><input type="checkbox" name="borrow.award" value="2"/><input type="text" name="borrow.funds" value="" size="50" />%</td>
                              	</tr>
                              	<tr >
						      		<td  class="label" >借款时长：</td>
						      		<td><input type="text" id="borrow_timeLimitDay" name="borrow.timeLimitDay" value="" size="50" />天<input type="button" value="设置还款计划" onclick="openRepayPlan();"/></td>
                              	</tr>
                              	<tr >
						      		<td  class="label" >还款计划：</td>
						      		<td id="idplan">&nbsp;</td>
                              	</tr>
                              	<tr >
						      		<td  class="label" >是否定向：</td>
						      		<td><input type="checkbox" name="borrow.isDxb" value="1" /><input type="input" name="borrow.pwd" value="" /></td>
                              	</tr>
                              	<tr id="testtest0">
						      		<td  class="label" >图片：</td>
						      		<td><input type="file" name="borImagesFile">&nbsp;<input type="button" id="btn_0" value="新增" onclick="addImage('0');"/></td>
                              	</tr>
                              	<tr>
                                	<td class="label"> 借款详情： </td>
                                    <td >
                                    	<textarea id="editor" name="borrow.content" class="editor"  ></textarea></td>
                                </tr>
							    <tr height=34>
						      		<td ></td>
							  		<td></td>
							  	</tr>
							  	<tr >
						      		<td  class="label" >安全密码：</td>
						      		<td><input type="password" name="safePassword" /></td>
                              	</tr>					
								</tbody>
							</table> 
							
 
								<div class="content">
									<input type="submit" value="确认提交" name="submit" /> 
								</div>
								<div class="foot"></div>
						
					  </div>
				 
				 </div>
				 <div class="bottom_bg"></div>
			 
			 
			 </div>


  </div>
  </form>
 <!--弹出层-->

  <!--footer-->
  <#include "/content/common/foot.ftl">
  <div>	
 <!--帐户信息公开设置 结束-->
<!--帐户信息公开设置 开始-->
  </body>
</html>
