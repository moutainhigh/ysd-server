package com.qmd.util;

import com.qmd.util.bean.RaisePayPlan;
import com.qmd.util.bean.RaisePayPlanPeriod;
import com.qmd.util.bean.RaiseTypeBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 卡趣新标公共类
 */

public class RaiseUtil {

	public final static List<RaiseTypeBean> getRaiseTypeBeanList() {
		List<RaiseTypeBean> list = new ArrayList<RaiseTypeBean>();
		list.add(new RaiseTypeBean("01", "房产"));
		list.add(new RaiseTypeBean("02", "汽车"));
		list.add(new RaiseTypeBean("03", "动产"));
		list.add(new RaiseTypeBean("04", "保证"));
		list.add(new RaiseTypeBean("11", "信用"));
		list.add(new RaiseTypeBean("12", "名品"));
		list.add(new RaiseTypeBean("13", "益老"));
		list.add(new RaiseTypeBean("99", "体验"));

		return list;
	}

	public final static String getRaiseTypeNameByCode(String code) {

		if ("01".equals(code)) {
			return "房产";
		} else if ("02".equals(code)) {
			return "汽车";
		} else if ("03".equals(code)) {
			return "动产";
		}  else if ("04".equals(code)) {
			return "保证";
		}  else if ("11".equals(code)) {
			return "信用";
		}  else if ("12".equals(code)) {
			return "名品";
		} else if ("13".equals(code)) {
			return "益老";
		}else if ("99".equals(code)) {
			return "体验";
		}
		return "";

	}

	/**
	 * 根据标类型和业务类型取得标识
	 * 
	 * @param btp
	 *            标类型（7债权流转，8抵押质押，9小贷信用）
	 * @param bbtp
	 *            业务类型（01房产，02汽车，03动产，12奢品,益老贷,99体验）
	 * @return
	 */
	public final static String getSignByBtypeBBtype(int btp, String bbtp) {

		String ret = "none";

		if (btp == 7) {// 债权流转
			if("12".equals(bbtp)){// 奢品
				ret = "flow_luxury";
			} else if ("16".equals(bbtp)) {//随心宝 稳健型
				ret = "flow_sxbao_wjx";
			} else if ("17".equals(bbtp)) {// 随心宝 积极型
				ret = "flow_sxbao_jjx";
			} else if ("18".equals(bbtp)) {// 基金
				ret = "flow_fund";
			}else{
				ret = "flow_all";
			}
		} else if (btp == 8) {// 抵押质押
			if ("01".equals(bbtp)) {// 房产抵押
//				ret = "moon_house";
				ret = "moon_bao";
			} else if ("02".equals(bbtp)) {// 汽车质押
//				ret = "moon_car";
				ret = "moon_bao";
			} else if ("03".equals(bbtp)) {// 动产质押
//				ret = "moon_cargo";
				ret = "moon_bao";
			} else if("04".equals(bbtp)){
				ret = "moon_bao";
			} else if ("13".equals(bbtp)){// 益老贷
				ret = "moon_bao";
			} else if("14".equals(bbtp)){//券贷通
				ret = "moon_securities";
			} else if("15".equals(bbtp)){//转贷宝
				ret = "moon_zhuandaibao";
			}

		} else if (btp == 9) {// 小贷信用
			ret = "credit";
		} else if (btp == 11) {// 灵活宝
			if ("01".equals(bbtp)) {// 房产抵押
				ret = "moon_bao";
			} else if ("02".equals(bbtp)) {// 汽车质押
				ret = "moon_bao";
			} else if ("03".equals(bbtp)) {// 动产质押
				ret = "moon_bao";
			}else if("04".equals(bbtp)){
				ret = "moon_bao";
			}
		}else if(btp == 20){//债权转让
			ret = "zqzr";
		}

		return ret;

	}


	/**
	 * 根据标类型和业务类型取得标识
	 * 
	 * @param btp
	 *            标类型（7债权流转，8抵押质押，9小贷信用）
	 * @param bbtp
	 *            业务类型（01房产，02汽车，03动产，12奢品，99体验）
	 * @param bbtp_sub
	 *            业务子类型（2001企业,2002个人）
	 * @return
	 */
	public final static String getSignByBtypeBBtype(int btp, String bbtp,String bbtp_sub) {

		String ret = "none";

		if (btp == 7) {// 债权流转
			if("12".equals(bbtp)){// 奢品
				ret = "flow_luxury";
			}else if ("16".equals(bbtp)) {//随心宝 稳健型
				ret = "flow_sxbao_wjx";
			} else if ("17".equals(bbtp)) {// 随心宝 积极型
				ret = "flow_sxbao_jjx";
			} else if ("18".equals(bbtp)) {// 随心宝 积极型
				ret = "flow_fund";
			}else{
				ret = "flow_all";
			}
		} else if (btp == 8) {// 抵押质押
			if ("14".equals(bbtp)) {// 券贷通
				ret = "moon_securities";
			} else if ("15".equals(bbtp)) {// 券贷通
				if("2001".equals(bbtp_sub)){//企业（占无合同）
					ret = "moon_zhuandaibao";
				}else if("2002".equals(bbtp_sub)){//个人
					ret = "moon_zhuandaibao";
				}
			}else {//保证
				if("2001".equals(bbtp_sub)){//企业
					ret = "moon_bao";
				}else if("2002".equals(bbtp_sub)){//个人
					ret = "moon_bao_person";
				}
			}
		} else if (btp == 9) {// 小贷信用
			ret = "credit";
		} else if (btp == 11) {// 灵活宝
//			if ("01".equals(bbtp)) {// 房产抵押
//				ret = "moon_house";
//			} else if ("02".equals(bbtp)) {// 汽车质押
//				ret = "moon_car";
//			} else if ("03".equals(bbtp)) {// 动产质押
//				ret = "moon_cargo";
//			}else if("04".equals(bbtp)){// 保证
				if("2001".equals(bbtp_sub)){//企业
					ret = "moon_agile";
				}else if("2002".equals(bbtp_sub)){//个人
					ret = "moon_agile_person";
				}
//			}
		}else if(btp == 20){//债权转让
			ret = "zqzr";
		}

		return ret;

	}
	
	/**
	 * 还款计划
	 * @param catipalTotal 本金
	 * @param apr 利息（天息）
	 * @param period 每期天数
	 * @param divide 总期数
	 * @return
	 */
	public static RaisePayPlan fillRaisePayPlan(BigDecimal catipalTotal,
			BigDecimal apr, int period, int divide) {
		RaisePayPlan plan = new RaisePayPlan();
		plan.setCatipalTotal(catipalTotal);
		plan.setApr(apr);
		plan.setPeriod(period);
		plan.setDivide(divide);
		BigDecimal realApr = apr.divide(new BigDecimal(1000));

		BigDecimal catipalPeriod = CommonUtil.setPriceScale2BigDecimal(catipalTotal.doubleValue()/divide);

		List<RaisePayPlanPeriod> raisePayPlanPeriodList = new ArrayList<RaisePayPlanPeriod>();

		RaisePayPlanPeriod item = null;
		BigDecimal interestTemp = null;
		BigDecimal interestTotal = BigDecimal.ZERO;

		BigDecimal catipalTemp = catipalTotal;
		
		BigDecimal periodBig = new BigDecimal(period);
		

		for (int i = 0; i < divide; i++) {
			item = new RaisePayPlanPeriod();
			item.setCatipalThis(catipalTemp);
			if (i == (divide - 1)) {// 最后一期，剩余全部付
				item.setCatipalPay(catipalTemp);
			} else {
				item.setCatipalPay(catipalPeriod);
			}
			item.setCatipalNext(item.getCatipalThis().subtract(
					item.getCatipalPay()));
			
			interestTemp = catipalTemp.multiply(realApr).multiply(periodBig);
			interestTemp = CommonUtil.setPriceScale(interestTemp);//利息四舍五入
			item.setInterestThis(interestTemp);
			
			interestTotal = interestTotal.add(interestTemp);
			
			catipalTemp = item.getCatipalNext();
			
			raisePayPlanPeriodList.add(item);
		}
		
		plan.setInterestTotal(interestTotal);
		plan.setRaisePayPlanPeriodList(raisePayPlanPeriodList);

		return plan;
	}

}