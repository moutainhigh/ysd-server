package net.qmdboss.action.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.qmdboss.bean.HongbaoDetail;
import net.qmdboss.entity.Hongbao;
import net.qmdboss.enums.HongbaoType;
import net.qmdboss.service.HongbaoService;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ParentPackage("admin")
public class HongbaoAction extends BaseAdminAction {

	private static final long serialVersionUID = -1143031655020521495L;

	private Hongbao hongbao;

	private String check1;
	private String check2;
	private String check3;

	private Integer num1;
	private Integer num2;
	private Integer num3;

	private List<HongbaoDetail> hbList;

	private List<String> hbMoney;// 红包金额
	private List<Integer> hbEndTime;// 使用有效期
	private List<Integer> hbLimitStart;
	private List<Integer> hbLimitEnd;//@author:zdl 不再用
	private List<String> hbIsPc;//@author:zdl 不再用
	private List<String> hbIsApp;//@author:zdl 不再用
	private List<String> hbIsHfive;//@author:zdl 不再用
	//@author:zdl 新增字段
	private List<Integer> investFullMomey;//投资金额满多少可用
	private List<Integer> investmentStart;//投资金额区间下限
	private List<Integer> investmentEnd;//投资金额区间上线

	@Resource(name = "hongbaoServiceImpl")
	private HongbaoService hongbaoService;

	// 列表
	public String list() {
		pager = hongbaoService.findPager(pager);
		return LIST;
	}

	public String edit() {
		hongbao = hongbaoService.load(id);
		if (StringUtils.isNotEmpty(hongbao.getHongbaoDetail())) {
			hbList = (List<HongbaoDetail>) new Gson().fromJson(hongbao.getHongbaoDetail(), new TypeToken<List<HongbaoDetail>>() {
			}.getType());
		}
		return INPUT;
	}

	/**
	 * 红包新新的更新方法
	 * @author zdl
	 * @return
	 */
	public String updateNew() {
		Hongbao persistent = hongbaoService.load(id);
		persistent.setIsEnabled(hongbao.getIsEnabled());
		String remark = "";
		BigDecimal money = BigDecimal.ZERO;

		List<HongbaoDetail> hbDetailList = new ArrayList<HongbaoDetail>();
		if (hbMoney != null && hbMoney.size() > 0) {
			for (int i = 0; i < hbMoney.size(); i++) {
				HongbaoDetail hbDetail = new HongbaoDetail();
				//验证：红包金额不能为空
				if (StringUtils.isNotEmpty(hbMoney.get(i))) {
					hbDetail.setMoney(new BigDecimal(hbMoney.get(i)));
					money = money.add(new BigDecimal(hbMoney.get(i)));
				}else{
					addErrorMessages("请输入红包金额!");
					return ERROR;
				}
				//验证：红包使用有效期不能为空
				if (hbEndTime!=null && hbEndTime.size()>0 && hbEndTime.get(i) != null) {
					hbDetail.setExpDate(hbEndTime.get(i));
				}else{
					addErrorMessages("请输入红包使用有效期!");
					return ERROR;
				}
				//验证：可用项目期限
				if (hbLimitStart!=null && hbLimitStart.size()>0 && hbLimitStart.get(i) != null) {
					hbDetail.setLimitStart(hbLimitStart.get(i));
				}else{
					addErrorMessages("请输入可用项目期限起始天数!");
					return ERROR;
				}
				if (hbLimitEnd!=null && hbLimitEnd.size()>0 && hbLimitEnd.get(i) != null) {
					hbDetail.setLimitEnd(hbLimitEnd.get(i));
				}else{
					addErrorMessages("请输入可用项目期限结束天数!");
					return ERROR;
				}
				if(hbLimitStart.get(i) - hbLimitEnd.get(i) >= 0){
					addErrorMessages("可用项目期限结束天数必须大于起始天数!");
					return ERROR;
				}
				//验证：投资金额不为空
				if(investFullMomey!=null && investFullMomey.size()>0 && investFullMomey.get(i)!=null){
					hbDetail.setInvestFullMomey(investFullMomey.get(i));
				}else{
					addErrorMessages("请输入投资金额!");
					return ERROR;
				}
				//非注册红包验证：投资金额区间
				if(persistent.getType() !=null && persistent.getType() != HongbaoType.REGISTER.getType()){
					if(investmentStart!=null && investmentStart.size()>0 && investmentStart.get(i)!=null){
						hbDetail.setInvestmentStart(investmentStart.get(i));
					}else{
						addErrorMessages("请输入投资金额区间下限！");
						return ERROR;
					}
					if(investmentEnd!=null && investmentEnd.size()>0 && investmentEnd.get(i)!=null){
						hbDetail.setInvestmentEnd(investmentEnd.get(i));
					}else{
						addErrorMessages("请输入投资金额区间上限！");
						return ERROR;
					}
					if(investmentStart.get(i) > investmentEnd.get(i)){
						addErrorMessages("投资金额区间的下限不能超过上限！");
						return ERROR;
					}
				}
				hbDetail.setIsApp(1);
				hbDetail.setIsHfive(1);
				hbDetail.setIsPc(1);
				hbDetailList.add(hbDetail);
			}
		}else{
			addErrorMessages("请输入红包信息!");
			return ERROR;
		}
		String str = "";
		str = new Gson().toJson(hbDetailList);

		persistent.setHongbaoDetail(str);
		persistent.setTotal(money);
		persistent.setRemark(remark);
		hongbaoService.update(persistent);
		redirectUrl = "hongbao!list.action";
		return SUCCESS;
	}
	/**
	 * public String update() { Hongbao persistent = hongbaoService.load(id);
	 * persistent.setIsEnabled(hongbao.getIsEnabled()); String remark="";
	 * BigDecimal money = BigDecimal.ZERO; if("on".equals(check3) && num3 !=
	 * null && num3 >0){ persistent.setFiftieth(num3); remark = "50元"+num3+"个,";
	 * money = new BigDecimal(50).multiply(new BigDecimal(num3)); }else {
	 * persistent.setFiftieth(0); remark = "50元0个,"; }
	 * 
	 * if("on".equals(check2) && num2 != null && num2 >0){
	 * persistent.setTwentieth(num2); remark = remark + "20元"+num2+"个,";
	 * money=money.add(new BigDecimal(20).multiply(new BigDecimal(num2))); }else
	 * { persistent.setTwentieth(0); remark = remark + "20元0个,"; }
	 * if("on".equals(check1) && num1 != null && num1 >0){
	 * persistent.setTenth(num1); remark = remark + "10元"+num1+"个";
	 * money=money.add(new BigDecimal(10).multiply(new BigDecimal(num1))); }else
	 * { persistent.setTenth(0); remark = remark + "10元0个"; }
	 * persistent.setTotal(money); persistent.setRemark(remark);
	 * hongbaoService.update(persistent); redirectUrl="hongbao!list.action";
	 * return SUCCESS; }
	 **/
	/**
	 * 旧的更新红包方法
	 * @author zdl 修改
	 * @return
	 */
	public String update() {
		Hongbao persistent = hongbaoService.load(id);
		persistent.setIsEnabled(hongbao.getIsEnabled());
		String remark = "";
		BigDecimal money = BigDecimal.ZERO;

		List<HongbaoDetail> hbDetailList = new ArrayList<HongbaoDetail>();
		if (hbMoney != null && hbMoney.size() > 0) {
			for (int i = 0; i < hbMoney.size(); i++) {
				HongbaoDetail hbDetail = new HongbaoDetail();
				if (StringUtils.isNotEmpty(hbMoney.get(i))) {
					hbDetail.setMoney(new BigDecimal(hbMoney.get(i)));
					money = money.add(new BigDecimal(hbMoney.get(i)));
				}else{
					addErrorMessages("请输入红包金额!");
					return ERROR;
				}
				if (hbEndTime.get(i) != null) {
//					hbDetail.setEndTime(CommonUtil.getDate2String( CommonUtil.date2end(CommonUtil.getString2Date(hbEndTime.get(i), "yyyy-MM-dd")),"yyyy-MM-dd HH:mm:ss"));
					hbDetail.setExpDate(hbEndTime.get(i));
				}else{
					addErrorMessages("请输入红包使用有效期!");
					return ERROR;
				}

				if (hbLimitStart.get(i) != null) {
					hbDetail.setLimitStart(hbLimitStart.get(i));
				}else{
					addErrorMessages("请输入可用项目期限起始天数!");
					return ERROR;
				}
				if (hbLimitEnd.get(i) != null) {
					hbDetail.setLimitEnd(hbLimitEnd.get(i));
				}else{
					addErrorMessages("请输入可用项目期限截止天数!");
					return ERROR;
				}

				if (hbIsPc != null && hbIsPc.size() >i && StringUtils.isNotEmpty(hbIsPc.get(i)) && "on".equals(hbIsPc.get(i))) {
					hbDetail.setIsPc(1);
				}else{
					hbDetail.setIsPc(0);
				}

				if (hbIsApp != null && hbIsApp.size() >i && StringUtils.isNotEmpty(hbIsApp.get(i)) && "on".equals(hbIsApp.get(i))) {
					hbDetail.setIsApp(1);
				}else{
					hbDetail.setIsApp(0);
				}

				if (hbIsHfive != null && hbIsHfive.size() >i && StringUtils.isNotEmpty(hbIsHfive.get(i)) && "on".equals(hbIsHfive.get(i))) {
					hbDetail.setIsHfive(1);
				}else{
					hbDetail.setIsHfive(0);
				}
				if(hbDetail.getMoney()!= null && hbDetail.getExpDate() != null && hbDetail.getLimitStart() != null && hbDetail.getLimitEnd() != null){
					hbDetailList.add(hbDetail);
				}
			}
		}else{
			addErrorMessages("请输入红包信息!");
			return ERROR;
		}
		String str = "";
		str = new Gson().toJson(hbDetailList);

		persistent.setHongbaoDetail(str);
		persistent.setTotal(money);
		persistent.setRemark(remark);
		hongbaoService.update(persistent);
		redirectUrl = "hongbao!list.action";
		return SUCCESS;
	}

	public Hongbao getHongbao() {
		return hongbao;
	}

	public void setHongbao(Hongbao hongbao) {
		this.hongbao = hongbao;
	}

	public String getCheck1() {
		return check1;
	}

	public String getCheck2() {
		return check2;
	}

	public String getCheck3() {
		return check3;
	}

	public Integer getNum1() {
		return num1;
	}

	public Integer getNum2() {
		return num2;
	}

	public Integer getNum3() {
		return num3;
	}

	public void setCheck1(String check1) {
		this.check1 = check1;
	}

	public void setCheck2(String check2) {
		this.check2 = check2;
	}

	public void setCheck3(String check3) {
		this.check3 = check3;
	}

	public void setNum1(Integer num1) {
		this.num1 = num1;
	}

	public void setNum2(Integer num2) {
		this.num2 = num2;
	}

	public void setNum3(Integer num3) {
		this.num3 = num3;
	}

	public List<HongbaoDetail> getHbList() {
		return hbList;
	}

	public void setHbList(List<HongbaoDetail> hbList) {
		this.hbList = hbList;
	}

	public List<String> getHbMoney() {
		return hbMoney;
	}

	public void setHbMoney(List<String> hbMoney) {
		this.hbMoney = hbMoney;
	}

	public List<Integer> getHbEndTime() {
		return hbEndTime;
	}

	public void setHbEndTime(List<Integer> hbEndTime) {
		this.hbEndTime = hbEndTime;
	}

	public List<Integer> getHbLimitStart() {
		return hbLimitStart;
	}

	public void setHbLimitStart(List<Integer> hbLimitStart) {
		this.hbLimitStart = hbLimitStart;
	}

	public List<Integer> getHbLimitEnd() {
		return hbLimitEnd;
	}

	public void setHbLimitEnd(List<Integer> hbLimitEnd) {
		this.hbLimitEnd = hbLimitEnd;
	}

	public List<String> getHbIsPc() {
		return hbIsPc;
	}

	public void setHbIsPc(List<String> hbIsPc) {
		this.hbIsPc = hbIsPc;
	}

	public List<String> getHbIsApp() {
		return hbIsApp;
	}

	public void setHbIsApp(List<String> hbIsApp) {
		this.hbIsApp = hbIsApp;
	}

	public List<String> getHbIsHfive() {
		return hbIsHfive;
	}

	public void setHbIsHfive(List<String> hbIsHfive) {
		this.hbIsHfive = hbIsHfive;
	}

	public HongbaoService getHongbaoService() {
		return hongbaoService;
	}

	public void setHongbaoService(HongbaoService hongbaoService) {
		this.hongbaoService = hongbaoService;
	}

	public List<Integer> getInvestFullMomey() {
		return investFullMomey;
	}

	public void setInvestFullMomey(List<Integer> investFullMomey) {
		this.investFullMomey = investFullMomey;
	}

	public List<Integer> getInvestmentStart() {
		return investmentStart;
	}

	public void setInvestmentStart(List<Integer> investmentStart) {
		this.investmentStart = investmentStart;
	}

	public List<Integer> getInvestmentEnd() {
		return investmentEnd;
	}

	public void setInvestmentEnd(List<Integer> investmentEnd) {
		this.investmentEnd = investmentEnd;
	}

}
