/* @since:2013/5/24　	* @author:270029616@qq.com ~~~ ! */

// Ken Popup

$(function(){
	
	// myAccountDrop
	$(".myAccountDrop")
		.hover(
			function(){	$(this).addClass("mAD"); },
			function(){	$(this).removeClass("mAD");	}
		);
	
	// 资金明细详情
	$(".Funds")
		.click(function(){
			
			var testTime = new Date().getTime();
			
			var dfid=this.id;
			
			$.ajax({
		        type: "get",
		        dataType : "json",
		        url: qmd.base+'/user/checkByUserLogin.do',
		        success: function(data){
		        	if(data==false) {
		        		alert("请登录！");
		        		window.location.href=qmd.base+'/user/login.do?loginRedirectUrl=%2Faccount%2Fdetail.do%3Ftype%3Ddetail';
		        		return;
		        	}
//		        	KP.options.drag = true;
//					KP.show("资金明细详情", 520, 545);
//					$(KP.options.content)
//						.load(qmd.base+"/account/detailFull.do?id="+dfid+"&testTime="+testTime);
		        	
		        	var mydialog = art.dialog({
						lock: true,
						background: '#ccc',
						width: 520,
						height: 545,
						padding: 0,
						title: '资金明细详情'
					});
		        	
		        	$.ajax({
				        type: "get",
				        url: qmd.base+'/account/detailFull.do?id='+dfid+'&testTime='+testTime,
				        success: function(data){
				        	mydialog.content(data);
				        }
		        	});
		        	
		        },
		        error:function(statusText){
		        	alert("发生错误！");
		        },
		        exception:function(){
		        	alert("发生错误！");
		        }
			});
			
			
			
			
		});

	// 确定投标
	$("#tenderFlow")
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
		        url: qmd.base+'/user/checkByUserLogin.do?testTime='+testTime+'&',
		        success: function(data){
		        	if(data==false) {
		        		alert("请登录！")
		        		window.location.href=qmd.base+'/user/login.do?loginRedirectUrl=%2Fborrow%2Fdetail.do%3FbId%3D'+$("#borrow_bid").val();
		        		return;
		        	}
		        	KP.options.drag = true;
					KP.show("确定投资", 540, 540);
					$(KP.options.content)
						.load(qmd.base+"/borrow/poputInvestFlow.do?bid="+$("#borrow_bid").val()+"&pieceAble="+pieceAble+"&pieceContinue="+pieceContinue+"&pieceTaste="+pieceTaste+"&testTime="+testTime);
		        },
		        error:function(statusText){
		        	alert("发生错误！");
		        },
		        exception:function(){
		        	alert("发生错误！");
		        }
			});
			
			
		});
	
	// 设置认购回期
	$("#backPeriod")
		.click(function(){
			
			var testTime = new Date().getTime();
			
			$.ajax({
		        type: "get",
		        dataType : "json",
		        url: qmd.base+'/user/checkForUserInvestor.do?testTime='+testTime+'&lowestMoney='+$("#lowest_money").val(),
		        success: function(data){
		        	if(data.message=='login') {
		        		
		        		art.dialog({
		    				lock: true,
		    				background: '#ccc',
		    				content: document.getElementById('forlogin'),
		    				width: 700,
		    				height: 264,
		    				padding: 0,
		    				title: '登录卡趣网'
		    			});
		        		
		        		//alert("请登录！");
		        		//window.location.href=qmd.base+'/user/login.do?loginRedirectUrl=%2Fborrow%2Fdetail.do%3FbId%3D'+$("#borrow_bid").val();
		        		//alert("请登录！");
		        		return;
		        	}
		        	
		        	if (data.message=='recharge') {
		        		var rechargedialog = art.dialog({
			        		id:'dialog_recharge',
							lock: true,
							background: '#ccc',
							content: document.getElementById('tp1cc'),
							width: 200,
							height:150,
							padding: 0,
							title: '请充值'
						});
		        		return;
		        	}
		        	
		        	var mydialog = art.dialog({
		        		id:'dialog_wander',
						lock: true,
						background: '#ccc',
						width: 400,
						height:350,
						padding: 0,
						title: '投标'
					});
		        	
		        	$.ajax({
				        type: "get",
				        url: qmd.base+'/borrow/poputFlow.do?bId='+$("#borrow_bid").val()+'&testTime='+testTime,
				        success: function(data){
				        	mydialog.content(data);
				        }
		        	});
		        	
		        	//KP.options.drag = true;
					//KP.show("设置认购回期", 560, 550);
					//$(KP.options.content)
					//	.load(qmd.base+"/borrow/wanderPlan.do?bId="+$("#borrow_bid").val()+"&testTime="+testTime);
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