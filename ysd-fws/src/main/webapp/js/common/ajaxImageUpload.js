//上传图片 path: 1:idcard 2:index 3:borrow 4:agency
function ajaxFileUploadImageWithRtn(pathStr,file,url,fileUrl,rtnFun) {

	var pic=$("#"+file).val();
	
	/* 检查文件类型 */
	var filename = $("#" + file).val().split(".")[1];
	if (filename == "" || filename == null) {
		alert('上传文件不能为空!','info');
		return false;
	}
	
	var filename2 = filename.toLowerCase();
	
	if (!(/^(.*)(jpg|jpeg|png|gif|bmp)$/.test(filename2))) {
		$("#" + file).val("");
		alert('文件类型不对！','info');
		return false;
	}
	
	if(pathStr == "" || pathStr == null){
		$("#" + file).val("");
		alert('请设置文件路径','info');
	}
	
	if(pathStr == '1'){
		url+="?pathStr=1";
	}else if(pathStr == '2'){
		url+="?pathStr=2";
	}else if(pathStr == '3'){
		url+="?pathStr=3";
	}else if(pathStr == '4'){
		url+="?pathStr=4";
	}else{
		$("#" + file).val("");
		alert('文件路径设置错误','info');
	}
	
	$.ajaxFileUpload( {
		url : url,
		secureuri : false,// 一般设置为false
		fileElementId : file,// 文件上传空间的id属性 可以多个INPUT，ID不能相同 <input
								// type="file" id="file" name="file" />
		dataType : 'json',// 返回值类型 一般设置为json
		success : function(data, status) // 服务器成功响应处理函数
		{
			//alert(data);
			if (data.error == 0) {
				$("#"+fileUrl).val(data.url);
				rtnFun();
			} else {
				alert(data.msg);
			}
		}
	});
	//return true;
}

function viewImage(filename) {
	//window.open(qmd.base+'/index.img?image='+$('#'+filename).val());
	window.open(qmd.img+$('#'+filename).val());
}