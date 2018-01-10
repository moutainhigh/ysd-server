/* @since:2013/5/24　	* @author:270029616@qq.com ~~~ ! */

// Ken Popup

$(function(){
	
	// myAccountDrop
	$(".myAccountDrop")
		.hover(
			function(){	$(this).addClass("mAD"); },
			function(){	$(this).removeClass("mAD");	}
		);
	


	// 确定投标
	$("#tenderMoon")
		.click(function(){
			
			var testTime = new Date().getTime();
			
			var pieceAble =0
			var pieceContinue =0;
			var pieceTaste = 0;
			
			if ($("#pieceAble").length>0) {
				pieceAble = $("#pieceAble").val();
	    	}
			
			if ($("#pieceTaste").length>0 && $("#boxTaste").length>0) {
				var ck3 = $("#boxTaste").attr("checked");
				if (ck3==false||ck3!='checked') {
		    	} else {
		    		pieceTaste = $("#pieceTaste").val();
		    	}
			}
			
			var pieceAll = parseInt(pieceAble)+parseInt(pieceContinue)+parseInt(pieceTaste);
			
			if (pieceAll<=0) {
				alert("请选择投资份数！");
				return false;
			}
			
			$.ajax({
		        type: "get",
		        dataType : "json",
		        url: qmd.base+'/user/checkByUserLogin.do?testTime='+testTime+'&',
		        success: function(data){
		        	if(data==false) {
		        		alert("请登录！")
		        		window.location.href=qmd.base+'/user/login.do?loginRedirectUrl=%2Fborrow%2Fdetail.do%3FbId%3D'+$("#borrow_bid").val();
		        		return;
		        	}
		        	KP.options.drag = true;
					KP.show("确定投标", 540, 540);
					$(KP.options.content)
						.load(qmd.base+"/borrow/poputInvestMoon.do?bid="+$("#borrow_bid").val()+"&pieceAble="+pieceAble+"&pieceContinue="+pieceContinue+"&pieceTaste="+pieceTaste+"&testTime="+testTime);
		        },
		        error:function(statusText){
		        	alert("发生错误！");
		        },
		        exception:function(){
		        	alert("发生错误！");
		        }
			});
			
			
		});
	
});