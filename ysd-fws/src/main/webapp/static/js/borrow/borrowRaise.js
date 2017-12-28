/* @since:2013/5/24　	* @author:270029616@qq.com ~~~ ! */

// Ken Popup

$(function(){
	

	// 确定投标
	$("#tenderRaise")
		.click(function(){
			
			var testTime = new Date().getTime();
			
			var pieceAble =0
			var pieceContinue =0;
			var pieceTaste = 0;
			
			if ($("#pieceAble").length>0) {
				pieceAble = $("#pieceAble").val();
	    	}
			
			
			if ($("#pieceTaste").length>0) {
				pieceTaste = $("#pieceTaste").val();
			}
			
			var pieceAll = parseInt(pieceAble)+parseInt(pieceContinue)+parseInt(pieceTaste);
			
			if (pieceAll<=0) {
				alert("请选择投资份数！");
				return false;
			}
			
			$.ajax({
		        type: "get",
		        dataType : "json",
		        url: qmd.base+'/user/checkForUserLogin.do?testTime='+testTime+'&id='+$("#borrow_bid").val()+'&',
		        success: function(data){
		        	if(data==0) {
		        		alert("请登录！")
		        		window.location.href=qmd.base+'/user/login.do?loginRedirectUrl=%2Fborrow%2Fdetail.do%3FbId%3D'+$("#borrow_bid").val();
		        		return;
		        	} else if(data==2) {
		        		alert("请实名认证后，再投资！")
		        		window.location.href=qmd.base+'/userCenter/realIdentify.do';
		        		return;
		        	} else if(data==3) {
		        		alert("对不起，您在"+$('#subjectdays').val()+"日内已进行过投资，不符合“新客专享项目”的投资要求，请您选择其他理财项目！");
		        		return;
		        	}
		        	KP.options.drag = true;
					KP.show("确定投资", 540, 580);
					$(KP.options.content)
						.load(qmd.base+"/borrow/poputInvestRaise.do?bid="+$("#borrow_bid").val()+"&pieceAble="+pieceAble+"&pieceContinue="+pieceContinue+"&pieceTaste="+pieceTaste+"&testTime="+testTime);
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