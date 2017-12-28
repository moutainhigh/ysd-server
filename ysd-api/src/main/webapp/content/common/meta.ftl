  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <link rel="stylesheet" href="${base}/h5/assets/css/amazeui.min.css">
  <link rel="stylesheet" href="${base}/h5/assets/css/base.css">
  <link rel="stylesheet" href="${base}/h5/assets/css/style.css">
  
  
  <script src="/h5/mobile_js/jquery.min.js"></script>
	  <script>
$().ready(function(){
	var u = navigator.userAgent;
		var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android�ն�
		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios�ն�
		var ua = navigator.userAgent.toLowerCase();  
	    if(ua.match(/MicroMessenger/i)=="micromessenger") {  
	     		return false;
	    } else { 
			if (isiOS) {
				return false;
			} else if (isAndroid) {
				return false;
			}else{
				//window.location.href = "https://www.hzjcb.com/index.do";
			}
	    }
});
</script>
  <script src="/h5/mobile_js/base.js"></script>
  <script src="/h5/mobile_js/qmd.js"></script>
  <script src="/h5/assets/js/amazeui.js"></script>
