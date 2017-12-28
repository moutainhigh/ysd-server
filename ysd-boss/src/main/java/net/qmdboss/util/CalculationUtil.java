package net.qmdboss.util;

import net.qmdboss.entity.Borrow;
import org.apache.log4j.Logger;

import java.math.BigDecimal;



/**
 * CalculationUtil计算公式类
 * 
 * @author zhanf
 *
 */
public class CalculationUtil {
	Logger log = Logger.getLogger(CalculationUtil.class);
	SettingUtil	settingUtil;
	/**
	 * interest 计算利息
	 * @param borrow
	 * @return lastApr 最终利息
	 */
	public  double interest(Borrow borrow){
		double monInterest =(borrow.getApr()/100)/12;
		double dayInterest = (borrow.getApr()/100)/365;
		double lastApr;
		if(borrow.getIsday().equals(1)){
				lastApr = dayInterest;
		}else{
			lastApr =monInterest;
		}
		log.info("计算利息");
		return lastApr;
		
	}
	
	/**
	 * 
	 * @param borrow
	 * @return allInter 对应金额和期限所得利息
	 */
	public double allInterest(Borrow borrow){
		
		double allInter;
		double apr = interest(borrow);
		int timeLimit = Integer.parseInt(borrow.getTimeLimit());
		double accout =(borrow.getAccount().doubleValue());
		if(borrow.getIsday().equals(1)){
			allInter= apr*Double.parseDouble(borrow.getTimeLimitDay())*accout;
		}else{
			allInter=apr*accout*((timeLimit+1)/2);
		}
		log.info("计算总利息");
		return allInter;
	}
	
	/**
	 * repaymentAccount 应还金额
	 * @param borrow
	 * @param type
	 * @return
	 */
	public BigDecimal repaymentAccount(Borrow borrow ){
		
		log.info("开始计算总金额");
		String payAccount;//总金额
		double frAccount;
		double reward = 0 ;//奖励金额
		double lastApr = allInterest(borrow);//获取到的利息
		String award =borrow.getAward();
		if(award.equals("1")){
			reward = Double.parseDouble(borrow.getPartAccount());
		}else if(award.equals("2")){
			reward = (borrow.getAccount().doubleValue())* (Double.parseDouble(borrow.getFunds())/100);
		}else{
			reward=0;
		}
		frAccount =(borrow.getAccount().doubleValue())+reward+lastApr;
		log.info("总金额计算完成");
		return settingUtil.setPriceScale(BigDecimal.valueOf(frAccount));
	}
	
	
	/**
	 * monthlyPay 每月需还金额
	 * @param borrow
	 * @return
	 */
	public BigDecimal  monthlyPay(Borrow borrow){
		log.info("开始计算每月还款数");
		BigDecimal  payAccount = repaymentAccount(borrow);
		int monthly = Integer.parseInt(borrow.getTimeLimit()) ;
		double monAllPay = (payAccount.doubleValue())/monthly;
		log.info("每月还款数计算结束");
		return settingUtil.setPriceScale(BigDecimal.valueOf(monAllPay));
		
	}
	
	/**
	 * monthlyInterest 每月需还利息
	 * @param borrow
	 * @return
	 */
	public BigDecimal monthlyInterest(Borrow borrow,int i){
		log.info("开始计算每月需还利息");
		double interest1 =interest(borrow);
		double num =(double)i/Integer.parseInt(borrow.getTimeLimit());
	    double monInterest =num*interest1*(borrow.getAccount().doubleValue());
		return settingUtil.setPriceScale(BigDecimal.valueOf(monInterest));
		
	}
	
	/**
	 * monthlyAccount 每月需还本金
	 * @param borrow
	 * @return
	 */
	public BigDecimal monthlyAccount(Borrow borrow,int i){
		log.info("开始计算每月需还本金");
		BigDecimal monAllPay  =borrow.getMonthlyRepayment();
		BigDecimal monAccount1 = settingUtil.sub(monAllPay, monthlyInterest(borrow,i));
		return settingUtil.setPriceScale(monAccount1);
		
	}
	
	/**
	 * 根据金额，月息，期限计算利息
	 */
	public BigDecimal getInterest(BigDecimal amount,double yApr,String limitTime){
		Double am = amount.doubleValue();
		Double apr = Double.valueOf(yApr)/100/12;
		Double limit = Double.valueOf(limitTime);
		Double interest = am*apr*limit;
		return BigDecimal.valueOf(interest);
	}
	
	
	/**
	 * 计算月标真实的年利率（含利息管理费）
	 * @param borrow
	 * @return
	 */
	public static BigDecimal monthRealAprYear(Borrow borrow) {

		double monthInterest = borrow.getApr() / 12 / 100;// 月息

		double totalInterest = 0;// 总利息
		double totalAccount = 0;// 有效投资本金
		BigDecimal totalIncome = BigDecimal.ZERO;
		double month = 0;
		BigDecimal award = BigDecimal.ZERO;// 奖金
		double interest = 0;
		double account = borrow.getAccount().doubleValue();// 借款总额
		
		// 计算奖励金额
		if ("1".equals(borrow.getAward())||"2".equals(borrow.getAward())) {
			// 奖金金额
			award = NumberUtil.setPriceScale(Double.parseDouble(borrow
					.getFunds()) / 100 * account);
		}
		
		// 计算利息收益
		if (borrow.getBorStages() != null
				&& !"".equals(borrow.getBorStages().trim())) {
			String str[] = borrow.getBorStages().split(":");

			for (int i = 0; i < str.length; i++) {

				String str1[] = str[i].split(",");
				// 本期的月数
				month = Double.valueOf(str1[0]) - month;
				// 本期利息
				interest = month * monthInterest * account;
				totalAccount += (account * month);
				month = Double.valueOf(str1[0]);
				account = account - Double.parseDouble(str1[1]);

				// total += Double.parseDouble(str1[1])+interest;
				totalInterest += interest;

//				System.out.println(month + "第" + (i + 1) + "期 值:[" + str[i]
//						+ "] 本金:" + str1[1] + " 利息:" + interest + " 剩余借款:"
//						+ account + " 累计总额:" + totalInterest + "投资总额"
//						+ totalAccount);
			}
		}

		totalIncome = NumberUtil.setPriceScale(totalInterest).add(award);
//		System.out.println("总收益：" + totalIncome);
		return NumberUtil.setPriceScale(
				totalIncome.doubleValue() / totalAccount * 100).multiply(
				BigDecimal.valueOf(12));

	}
	
	public static void main(String[] args) {
		// 月标真实的年利率
		Borrow borrow = new Borrow();
		borrow.setAccount(BigDecimal.valueOf(10000));
		borrow.setApr(21.6);
		borrow.setBorStages("1,3000:2,3000:3,4000:");
		borrow.setAward("1");
		borrow.setFunds("1.3");
		BigDecimal i = monthRealAprYear(borrow);
		
		System.out.println("==="+i);
	}
	

}
