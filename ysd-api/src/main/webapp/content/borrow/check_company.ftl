<!DOCTYPE html>
<html>
  <head>
    <title>MyHtml.html</title>
	
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    	<script type="text/javascript" src="${base}/static/js/jquery/jquery-1.8.1.js"></script>
    
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
	<script type="text/javascript"> 
 function change(obj){
 		if($(obj).attr("id")=="qiye"){
			$(obj).parent("li").addClass("dq");
			$("#geren").parent("li").removeClass("dq");
			$("#isqiye").val("1");
			$("#gerenz").hide("");
			$("#qiyeh").show("");
		}else if($(obj).attr("id")=="geren"){
			$(obj).parent("li").addClass("dq");
			$("#qiye").parent("li").removeClass("dq");
			$("#isqiye").val("0");
			$("#qiyeh").hide("");
			$("#gerenz").show("");
		}
		alert("输入用户名和密码是：" + $("#isqiye").val() )
 }
 
</script>
  </head>
  
  <body>

<FORM id="aspnetForm" method="post"  action="addBorCpy.do">
<input type="hidden" name="borCompany.type" id="type" value="${borCompany.type}" />
<input type="hidden" name="borCompany.isqiye" id="isqiye" value="0" />
<DIV id="main2">
<DIV class="content">
<DIV class="height">
</DIV>
<!--贷款状态结束-->
<DIV class="sqdk">
<UL class="tab_menu">
<LI class="dq">
<A href="javascript:;" id="geren" onclick="change(this);">个人申请贷款</A> 
</LI>
<LI>
<A href="javascript:;" id="qiye" onclick="change(this);">企业申请贷款</A> 
</LI>
</UL>
<DIV class="clear">
</DIV>
<DIV class="sq_cont border">
<UL>
<LI>
<P class="no_1 diqu">
<SPAN class="red">*</SPAN> 所在地区：
</P>
<SPAN style="FLOAT: left">
<STYLE> 
.WayArea {
MARGIN: 0px 40px 0px 10px
}
</STYLE>
<DIV id="AreaSelect1_UpdatePanel1" style="color:#FF0000">注意:此类贷款权目前仅限浙江省杭州市,福建省福州市</DIV>
</SPAN>
</LI>
<LI>
<P class="no_1">
贷款用途：
</P>
<P>
<INPUT id="cbEntrepreneurship" type="checkbox" name="borCompany.yongtu" value="创业" /><LABEL for="cbEntrepreneurship">创业</LABEL> <INPUT id="cbPurchase" type="checkbox" name="borCompany.yongtu" value="购房" /><LABEL for="cbPurchase">购房</LABEL> <INPUT id="cbCar" type="checkbox" name="yongtu" value="买车" /><LABEL for="cbCar">买车</LABEL> <INPUT id="cbManagement" type="checkbox" name="yongtu[]" value="经营" /><LABEL for="cbManagement">经营</LABEL> <INPUT id="cbRenovation" type="checkbox" name="yongtu[]" value="装修" /><LABEL for="cbRenovation">装修</LABEL> <INPUT id="cbWedding" type="checkbox" name="borCompany.yongtu" value="结婚" /><LABEL for="cbWedding">结婚</LABEL> <INPUT id="cbTraveling" type="checkbox" name="borCompany.yongtu" value="旅游" /><LABEL for="cbTraveling">旅游</LABEL> <INPUT id="cbShopping" type="checkbox" name="borCompany.yongtu" value="购物" /><LABEL for="cbShopping">购物</LABEL> <INPUT id="cbSchool" type="checkbox" name="yongtu[]" value="求学" /><LABEL for="cbSchool">求学</LABEL> <INPUT id="cbOther" type="checkbox" name="borCompany.yongtu" value="其他" /><LABEL for="cbOther">其他</LABEL> 
</P>
</LI>
<LI>
<P class="no_1">
<SPAN class="red">*</SPAN> 有无抵押：
</P>
<P>
<INPUT id="rbYes" type="radio" name="borCompany.Mortgage" value="有" /><LABEL for="rbYes">有</LABEL> <INPUT id="rbNo" type="radio" name="borCompany.Mortgage" value="无" /><LABEL for="rbNo">无</LABEL> 
</P>
</LI>
<LI>
<P class="no_1"><SPAN class="red">*</SPAN> 抵押物：</P>
<P>
<INPUT id="cbCollateralHouse"  type="checkbox" name="borCompany.diya" value="房产" /><LABEL for="cbCollateralHouse">房产</LABEL> <INPUT id="cbCollateralCar" type="checkbox" name="borCompany.diya" value="汽车" /><LABEL for="cbCollateralCar">汽车</LABEL> <INPUT id="cbCollateralNegotiable" type="checkbox" name="borCompany.diya" value="有价证券" /><LABEL for="cbCollateralNegotiable">有价证券</LABEL> <INPUT id="cbCollateralOther" type="checkbox" name="borCompanydiya" value="其他" /><LABEL for="cbCollateralOther">其他</LABEL>
</P>
</LI>
<LI id="gerenz">
<P class="no_1">
<SPAN class="red">*</SPAN> 个人资料：
</P>
<P>
<SELECT id="ddlAge" name="borCompany.ddlAge"> <OPTION value="您的年龄">您的年龄</OPTION> <OPTION value="22岁以下">22岁以下</OPTION> <OPTION value="22-30岁">22-30岁</OPTION> 
 <OPTION value="30-41岁">30-40岁</OPTION><OPTION value="41-50岁">41-50岁</OPTION> <OPTION value="50岁以上">50岁以上</OPTION></SELECT> - <SELECT id="ddlOccupation" name="ddlOccupation"> <OPTION value="-999">请选择您的职业</OPTION> <OPTION value="财务总监">财务总监</OPTION> <OPTION value="财务主管">财务主管</OPTION> <OPTION value="审计/税务">审计/税务</OPTION> <OPTION value="总账/成本">总账/成本</OPTION> <OPTION value="会计人员">会计人员</OPTION> <OPTION value="人力资源总监">人力资源总监</OPTION> <OPTION value="人力资源经理/主管">人力资源经理/主管</OPTION> <OPTION value="招聘经理/主管">招聘经理/主管</OPTION> <OPTION value="薪资福利经理/主管">薪资福利经理/主管</OPTION> <OPTION value="财务经理">财务经理</OPTION> <OPTION value="培训经理/主管">培训经理/主管</OPTION> <OPTION value="人事专员/助理">人事专员/助理</OPTION> <OPTION value="行长/副行长">行长/副行长</OPTION> <OPTION value="信贷/信用管理">信贷/信用管理</OPTION> <OPTION value="资产管理/评估">资产管理/评估</OPTION> <OPTION value="投融资项目/基金">投融资项目/基金</OPTION> <OPTION value="外汇管理/国际结算">外汇管理/国际结算</OPTION> <OPTION value="风险管理">风险管理</OPTION> <OPTION value="证券/期货">证券/期货</OPTION> <OPTION value="客户经理/主管">客户经理/主管</OPTION> <OPTION value="核保/理赔">核保/理赔</OPTION> <OPTION value="保险代理人/内勤">保险代理人/内勤</OPTION> <OPTION value="银行专员/柜台">银行专员/柜台</OPTION> <OPTION value="精算师">精算师</OPTION> <OPTION value="编辑/记者/文案">编辑/记者/文案</OPTION> <OPTION value="版面设计">版面设计</OPTION> <OPTION value="平面设计">平面设计</OPTION> <OPTION value="装潢/陈列设计">装潢/陈列设计</OPTION> <OPTION value="纺织/服装设计">纺织/服装设计</OPTION> <OPTION value="工艺品/珠宝设计">工艺品/珠宝设计</OPTION> <OPTION value="经理">经理</OPTION> <OPTION value="主任">主任</OPTION> <OPTION value="生物工程">生物工程</OPTION> <OPTION value="药物注册">药物注册</OPTION> <OPTION value="临床研究/协调">临床研究/协调</OPTION> <OPTION value="药物研发/分析工程师">药物研发/分析工程师</OPTION> <OPTION value="化学工程师">化学工程师</OPTION> <OPTION value="环保工程师">环保工程师</OPTION> <OPTION value="化工人员">化工人员</OPTION> <OPTION value="结构/建筑工程师">结构/建筑工程师</OPTION> <OPTION value="土建工程师/建造师">土建工程师/建造师</OPTION> <OPTION value="机电/给排水/暖通工程师">机电/给排水/暖通工程师</OPTION> <OPTION value="工程造价师/预决算">工程造价师/预决算</OPTION> <OPTION value="监理/安全工程师">监理/安全工程师</OPTION> <OPTION value="房地产开发/策划">房地产开发/策划</OPTION> <OPTION value="园林景观/城市规划师">园林景观/城市规划师</OPTION> <OPTION value="物业管理">物业管理</OPTION> <OPTION value="房地产评估/中介/交易">房地产评估/中介/交易</OPTION> <OPTION value="电子/电路工程师">电子/电路工程师</OPTION> <OPTION value="电气工程师">电气工程师</OPTION> <OPTION value="半导体工程师">半导体工程师</OPTION> <OPTION value="测试工程师">测试工程师</OPTION> <OPTION value="自动化工程师">自动化工程师</OPTION> <OPTION value="光学工程师">光学工程师</OPTION> <OPTION value="电力工程师">电力工程师</OPTION> <OPTION value="水利/水电工程师">水利/水电工程师</OPTION> <OPTION value="单片机/DLC/DSP/底层软件开发">单片机/DLC/DSP/底层软件开发</OPTION> <OPTION value="其他工程师">其他工程师</OPTION> <OPTION value="技术人员">技术人员</OPTION> <OPTION value="助理">助理</OPTION> <OPTION value="厂长/副厂长">厂长/副厂长</OPTION> <OPTION value="生产经理/车间主任">生产经理/车间主任</OPTION> <OPTION value="产品开发">产品开发</OPTION> <OPTION value="工业/产品设计">工业/产品设计</OPTION> <OPTION value="生产/计划/调度">生产/计划/调度</OPTION> <OPTION value="质量管理">质量管理</OPTION> <OPTION value="项目管理">项目管理</OPTION> <OPTION value="工程设备管理">工程设备管理</OPTION> <OPTION value="安全/健康/环境管理">安全/健康/环境管理</OPTION> <OPTION value="生产工艺/技术">生产工艺/技术</OPTION> <OPTION value="技工">技工</OPTION> <OPTION value="机械工程师">机械工程师</OPTION> <OPTION value="维修工程师">维修工程师</OPTION> <OPTION value="机械设计/制图">机械设计/制图</OPTION> <OPTION value="机电工程师">机电工程师</OPTION> <OPTION value="模具工程师">模具工程师</OPTION> <OPTION value="精密机械/仪器仪表">精密机械/仪器仪表</OPTION> <OPTION value="船舶工程师">船舶工程师</OPTION> <OPTION value="冲压/注塑/焊接">冲压/注塑/焊接</OPTION> <OPTION value="餐饮/娱乐管理">餐饮/娱乐管理</OPTION> <OPTION value="酒店/宾馆管理">酒店/宾馆管理</OPTION> <OPTION value="服务人员/领班">服务人员/领班</OPTION> <OPTION value="导游/旅行顾问/票务">导游/旅行顾问/票务</OPTION> <OPTION value="营业员/收银员">营业员/收银员</OPTION> <OPTION value="技术总监/首席技术执行官">技术总监/首席技术执行官</OPTION> <OPTION value="技术经理">技术经理</OPTION> <OPTION value="项目经理">项目经理</OPTION> <OPTION value="系统分析工程师">系统分析工程师</OPTION> <OPTION value="ERP应用顾问">ERP应用顾问</OPTION> <OPTION value="数据库工程师/管理员">数据库工程师/管理员</OPTION> <OPTION value="软件开发工程师">软件开发工程师</OPTION> <OPTION value="硬件开发工程师">硬件开发工程师</OPTION> <OPTION value="信息支持/安全维护">信息支持/安全维护</OPTION> <OPTION value="网页设计/编辑">网页设计/编辑</OPTION> <OPTION value="通讯工程师">通讯工程师</OPTION> <OPTION value="多媒体/游戏开发">多媒体/游戏开发</OPTION> <OPTION value="采购经理/主管">采购经理/主管</OPTION> <OPTION value="采购专员/助理">采购专员/助理</OPTION> <OPTION value="贸易经理/主管">贸易经理/主管</OPTION> <OPTION value="市场咨询/调研">市场咨询/调研</OPTION> <OPTION value="创意与策划">创意与策划</OPTION> <OPTION value="客服经理/主管">客服经理/主管</OPTION> <OPTION value="客服专员/助理">客服专员/助理</OPTION> <OPTION value="客户关系管理">客户关系管理</OPTION> <OPTION value="客户咨询/热线支持">客户咨询/热线支持</OPTION> <OPTION value="销售总监">销售总监</OPTION> <OPTION value="销售经理">销售经理</OPTION> <OPTION value="渠道/分销经理">渠道/分销经理</OPTION> <OPTION value="业务拓展经理">业务拓展经理</OPTION> <OPTION value="销售主管/主任">销售主管/主任</OPTION> <OPTION value="售前/售后支持">售前/售后支持</OPTION> <OPTION value="销售工程师">销售工程师</OPTION> <OPTION value="销售代表">销售代表</OPTION> <OPTION value="医药代表">医药代表</OPTION> <OPTION value="销售助理">销售助理</OPTION> <OPTION value="专业顾问">专业顾问</OPTION> <OPTION value="咨询师">咨询师</OPTION> <OPTION value="调研员">调研员</OPTION> <OPTION value="英语翻译">英语翻译</OPTION> <OPTION value="日语翻译">日语翻译</OPTION> <OPTION value="德语翻译">德语翻译</OPTION> <OPTION value="法语翻译">法语翻译</OPTION> <OPTION value="俄语翻译">俄语翻译</OPTION> <OPTION value="西班牙语翻译">西班牙语翻译</OPTION> <OPTION value="其他翻译">其他翻译</OPTION> <OPTION value="董事/总经理/总裁">董事/总经理/总裁</OPTION> <OPTION value="首席执行官/运营官">首席执行官/运营官</OPTION> <OPTION value="营运总监/经理">营运总监/经理</OPTION> <OPTION value="合伙人">合伙人</OPTION> <OPTION value="行政总监">行政总监</OPTION> <OPTION value="行政经理">行政经理</OPTION> <OPTION value="主管/主任">主管/主任</OPTION> <OPTION value="助理/秘书">助理/秘书</OPTION> <OPTION value="前台/文员">前台/文员</OPTION> <OPTION value="物流经理/主管">物流经理/主管</OPTION> <OPTION value="物流专员/助理">物流专员/助理</OPTION> <OPTION value="物料/仓库管理">物料/仓库管理</OPTION> <OPTION value="供应链">供应链</OPTION> <OPTION value="空运/海运操作">空运/海运操作</OPTION> <OPTION value="单证/报关员">单证/报关员</OPTION> <OPTION value="律师/法务">律师/法务</OPTION> <OPTION value="能源/动力">能源/动力</OPTION> <OPTION value="医疗/护理">医疗/护理</OPTION> <OPTION value="科研人员">科研人员</OPTION> <OPTION value="培训/教育">培训/教育</OPTION> <OPTION value="储备干部/培训生">储备干部/培训生</OPTION> <OPTION value="在校学生">在校学生</OPTION> <OPTION value="其他职位">其他职位</OPTION> <OPTION value="其他职位">其他职位</OPTION></SELECT> - <SELECT id="ddlInCome" name="ddlInCome"> <OPTION value="-999">请选择您的收入</OPTION> <OPTION value="1000元以下">1000元以下</OPTION> <OPTION value="2001-3000元">2001-3000元</OPTION> <OPTION value="3001-4000元">3001-4000元</OPTION> <OPTION value="4001-5000元">4001-5000元</OPTION> <OPTION value="5001-8000元">5001-8000元</OPTION> <OPTION value="8001-10000元">8001-10000元</OPTION> <OPTION value="10001-30000元">10001-30000元</OPTION> <OPTION value="30001-50000元">30001-50000元</OPTION> <OPTION value="50000元以上">50000元以上</OPTION> <OPTION value="1001-2000元">1001-2000元</OPTION></SELECT> 
</P>
</LI>
 
<LI id="qiyeh" style="display:none">
<P class="no_1">
<SPAN class="red">*</SPAN> 所属行业：
</P>
<P>
<SELECT id="ddlIndustry" name="borCompany.ddlIndustry"> <OPTION value="-999">请选择您企业的所属行业</OPTION> <OPTION value="农、林、牧、渔业">农、林、牧、渔业</OPTION> <OPTION value="制造业">制造业</OPTION> <OPTION value="电力、燃气及水的生产和供应业">电力、燃气及水的生产和供应业</OPTION> <OPTION value="建筑业">建筑业</OPTION> <OPTION value="交通运输、仓储和邮政业">交通运输、仓储和邮政业</OPTION> <OPTION value="信息传输、计算机服务和软件业">信息传输、计算机服务和软件业</OPTION> <OPTION value="批发和零售业">批发和零售业</OPTION> <OPTION value="金融业">金融业</OPTION> <OPTION value="房地产业">房地产业</OPTION> <OPTION value="采矿业">采矿业</OPTION> <OPTION value="租赁和商务服务业">租赁和商务服务业</OPTION> <OPTION value="科学研究、技术服务和地质勘查业">科学研究、技术服务和地质勘查业</OPTION> <OPTION value="水利、环境和公共设施管理业">水利、环境和公共设施管理业</OPTION> <OPTION value="居民服务和其他服务业">居民服务和其他服务业</OPTION> <OPTION value="教育">教育</OPTION> <OPTION value="卫生、社会保障和社会福利业">卫生、社会保障和社会福利业</OPTION> <OPTION value="文化、体育和娱乐业">文化、体育和娱乐业</OPTION> <OPTION value="公共管理和社会组织">公共管理和社会组织</OPTION> <OPTION value="国际组织">国际组织</OPTION></SELECT> 
</P>
</LI>
 
 
<LI>
<P class="no_1">
<SPAN class="red">*</SPAN> 企业介绍：
</P>
<P>
<SELECT id="ddlTurnover" name="borCompany.ddlTurnover"> <OPTION value="-999">年营业额</OPTION> <OPTION value="100万以下">100万以下</OPTION> <OPTION value="100万-500万">100万-500万</OPTION> <OPTION value="500万-2000万">500万-2000万</OPTION> <OPTION value="2000万-1亿">2000万-1亿</OPTION> <OPTION value="1亿以上">1亿以上</OPTION></SELECT> - <SELECT id="ddlStaff" name="ddlStaff"> <OPTION value="-999">员工人数</OPTION> <OPTION value="20人以下">20人以下</OPTION> <OPTION value="20-49人">20-49人</OPTION> <OPTION value="50-99人">50-99人</OPTION> <OPTION value="100-49人">100-49人</OPTION> <OPTION value="500人以上">500人以上</OPTION></SELECT> 
</P>
</LI>
<LI>
<P class="no_1">
<SPAN class="red">*</SPAN> 企业名称：
</P>
<P>
<INPUT id="companyname" name="borCompany.companyname" value="" /> 
</P>
</LI>
<LI>
<P class="no_1">
贷款说明：
</P>
<P>
<TEXTAREA style="WIDTH: 342px; HEIGHT: 94px" id="txtRemark" name="borCompany.txtRemark"></TEXTAREA> 
</P>
</LI>
<LI>
<P class="no_1">
<SPAN class="red">*</SPAN> 联系人：
</P>
<P>
<INPUT id="txtContact" name="borCompany.txtContact" value="" /> 
</P>
</LI>
<LI>
<P class="no_1">
<SPAN class="red">*</SPAN> 性别：
</P>
<P>
<INPUT id="rbBoy" CHECKED type="radio" name="borCompany.Sex" value="男" /><LABEL for="rbBoy">男</LABEL> <INPUT id="rbGirl" type="radio" name="borCompany.Sex" value="女" /><LABEL for="rbGirl">女</LABEL>
</P>
</LI>
<LI>
<P class="no_1">
<SPAN class="red">*</SPAN> 联系电话：
</P>
<P>
<INPUT id="txtTelephone" name="borCompany.txtTelephone" value="" /> 
</P>
</LI>
<LI>
<P class="no_1">
&nbsp;&nbsp;&nbsp;
</P>
<P>
<INPUT id="btnOK" type="submit" name="btnOK" value="提交申请" /> 
</P>
<DIV class="clear">
</DIV>
</LI>
</UL>
</DIV>
</DIV>
<!--投资流程结束-->
<DIV class="height">
</DIV>
</DIV>
 
</DIV></FORM>
  

  </body>
</html>
