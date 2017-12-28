package com.qmd.util;

import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.user.UserAccountDetail;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

@Service("interestCalUtil")
public class InterestCalUtil {
	public static void main(String[] args){
		InterestCalUtil rp=new InterestCalUtil();
		PaymentView ret = rp.payback(100,1000,0.20,3,"1,0:2,0:3,1000",1);
		System.out.println(ret);
		 }
	
	/**
	 * 
	 * @param debt 传入计算的金额
	 * @param borTotal 标总借款额
	 * @param NLL 年利率
	 * @param MONTH 借款天数或月数
	 * @param contType 控制计算类型（按月计算传值为1，按天计算传值为2）
	 * @param borStages 标还款串（每期还款时间，还款金额）
	 * @return
	 */
	 public PaymentView payback(double debt,double borTotal, double NLL,int MONTH,String borStages,int contType){
		 String str[]=borStages.split(":"); 
		 int num =str.length;
		 String dayStr[]=new String[num] ;
		 String moneyStr[]=new  String[num];
		 for(int i=0;i<str.length;i++)  {  
				String str1[]=str[i].split(","); 
				dayStr[i]=str1[0];
				moneyStr[i]=String.valueOf((Double.parseDouble(str1[1])/borTotal)*debt);//按比例计算每期还款金额
		}
		 MonthPaymentDetail[] pd = new MonthPaymentDetail[num];
		 PaymentView pv = new PaymentView();
		 double DLL;
		 if(contType==1){
			 DLL=NLL/1200;//月利率
		 }else{
			 DLL=NLL/1000;//天利率
		 }
		     
		 int month=1;
		  //double debt=100;
		  String[] coll = new String[3];
		  //NumberFormat fn=NumberFormat.getIntegerInstance();
		  DecimalFormat fn = new DecimalFormat("#0.00");
		  String nll=fn.format(NLL*100)+"%";
		  String dll=fn.format(DLL*100)+"%";
		  String debt_fn=fn.format(debt);
		  
		  MonthPaymentDetail paymentDetail;
		  if(contType==1){
			   System.out.println("您总共借款"+debt_fn+";还款方式：等额本息还款；还款时间："+ MONTH +"月"+";年利率"+nll+";月利率"+dll);
			   
			   //等额本息还款的月还款数公式
//			   double X=debt*MLL*(Math.pow((1+MLL), MONTH))/(Math.pow((1+MLL), MONTH)-1);  
//			   String X_fn=fn.format(X);   //格式化小数位数
//			   System.out.println("您的月还款额为："+X_fn);
			   String X_fn = null;
			   double TotalAmount;
			   //分期还款明细
			   double lixiT = 0;
			   double lixiM,benjinM,total;  //月利息，月本金
			   System.out.println("分期还款明细");
			   int i = 0;
			   total=debt;
			   while(debt>=1){
				paymentDetail = new MonthPaymentDetail();
				if(i==0){
					lixiM=debt*DLL*Double.parseDouble(dayStr[i]);//计算每期利息(剩余金额*月利率*月数）
				}else{
					lixiM=debt*DLL*(Double.parseDouble(dayStr[i])-Double.parseDouble(dayStr[i-1]));//计算每期利息(剩余金额*月利率*月数（此月数为获取本次月数-上次月数））
				}
			    benjinM=Double.parseDouble(moneyStr[i]);
			    debt=debt-benjinM;
			    double X= lixiM+benjinM;
			     X_fn=fn.format(X);   //格式化小数位数
				System.out.println("您的本期还款额为："+X_fn);
			    if(debt<1){
			     debt=0;
			    }
			    
			    //输出
			    String lixiM_fn=fn.format(lixiM);
			    String benjinM_fn=fn.format(benjinM);
			    String debt_fn3=fn.format(debt);
			    System.out.println("第"+month+"期    还款金额:"+X_fn+" 本月应还本金(即减少债务的钱):"+benjinM_fn+" 本月还款利息:"+lixiM_fn+" 剩余本金:"+debt_fn3);
			    paymentDetail.setMonthPaymentAmount(X_fn);
			    paymentDetail.setBenjinM(benjinM_fn);
			    paymentDetail.setMonth(String.valueOf(month));
			    paymentDetail.setLixiM(lixiM_fn);
			    paymentDetail.setMonthAmount(debt_fn3);
			    paymentDetail.setDateNum(dayStr[i]);
			    lixiT = lixiT + Double.valueOf(lixiM_fn);
			    pd[i] = paymentDetail;
			    month++;
			    i ++;
			   }
			    TotalAmount=lixiT+total;
			   String lixiT_fn = fn.format(lixiT);
			   System.out.println("总利息为：" + lixiT_fn);
			   coll[0] = X_fn;
			   coll[1] = lixiT_fn;
			   coll[2] = fn.format(TotalAmount);
			   pv.setMonthTotalAmount(X_fn);
			   pv.setTotalAmount(fn.format(TotalAmount));
			   pv.setTotalLiXi(lixiT_fn);
			   pv.setPaymentDetail(pd);
			  }
//		  
//		  //等额本金还款
//		  if(mode==1){
//		   System.out.println("您总共借款"+debt_fn+";还款方式：等额本金还款；还款时间：1年"+";年利率是："+nll+";月利率"+mll);
//		   
//		   System.out.println("分期还款明细");
//		   
//		   double monthPincipal=debt/12;  //每月应还本金
//		   debt=monthPincipal*12;
//		   double accrualM;  //每月还款利息
//		   double tm;  //每月还款金额
//		   
//		   //分期还款明细
//		   while(debt>=1){
//		    accrualM=debt*MLL;
//		    tm=monthPincipal+accrualM;
//		    debt=debt-monthPincipal;
//		    if(debt<1){
//		     debt=0;
//		    }
//		    
//		    //把小数位数格式化成2位
//		    String tm_fn=fn.format(tm);
//		    String monthPincipal_fn=fn.format(monthPincipal);
//		    String accrualM_fn=fn.format(accrualM);
//		    String debt_fn2=fn.format(debt);
//		    
//		    System.out.println("第"+month+"月    还款金额:"+tm_fn+" 本月应还本金:"+monthPincipal_fn+" 本月还款利息:"+accrualM_fn+" 剩余本金:"+debt_fn2);
//		    month++;
//		   }
//		  }
//		  
//		  
		  
		  //等额本息还款
		  if(contType==2){
		   System.out.println("您总共借款"+debt_fn+";还款方式：等额本息还款；还款时间："+ MONTH +"天"+";天利率"+dll);
		   
		   //等额本息还款的月还款数公式
//		   double X=debt*MLL*(Math.pow((1+MLL), MONTH))/(Math.pow((1+MLL), MONTH)-1);  
//		   String X_fn=fn.format(X);   //格式化小数位数
//		   System.out.println("您的月还款额为："+X_fn);
		   String X_fn = null;
		   double TotalAmount;
		   //分期还款明细
		   double lixiT = 0;
		   double lixiM,benjinM,total;  //月利息，月本金
		   System.out.println("分期还款明细");
		   int i = 0;
		   total=debt;
		   while(debt>=1){
			paymentDetail = new MonthPaymentDetail();
			if(i==0){
				lixiM=debt*DLL*Double.parseDouble(dayStr[i]);//计算每期利息(剩余金额*天利率*天数）
			}else{
				lixiM=debt*DLL*(Double.parseDouble(dayStr[i])-Double.parseDouble(dayStr[i-1]));//计算每期利息(剩余金额*天利率*天数（获取本次天数-上次天数））
			}
		    benjinM=Double.parseDouble(moneyStr[i]);
		    debt=debt-benjinM;
		    double X= lixiM+benjinM;
		     X_fn=fn.format(X);   //格式化小数位数
			System.out.println("您的本期还款额为："+X_fn);
		    if(debt<1){
		     debt=0;
		    }
		    
		    //输出
		    String lixiM_fn=fn.format(lixiM);
		    String benjinM_fn=fn.format(benjinM);
		    String debt_fn3=fn.format(debt);
		    System.out.println("第"+month+"期    还款金额:"+X_fn+" 本期应还本金(即减少债务的钱):"+benjinM_fn+" 本期还款利息:"+lixiM_fn+" 剩余本金:"+debt_fn3);
		    paymentDetail.setMonthPaymentAmount(X_fn);
		    paymentDetail.setBenjinM(benjinM_fn);
		    paymentDetail.setMonth(String.valueOf(month));
		    paymentDetail.setLixiM(lixiM_fn);
		    paymentDetail.setMonthAmount(debt_fn3);
		    paymentDetail.setDateNum(dayStr[i]);
		    lixiT = lixiT + Double.valueOf(lixiM_fn);
		    pd[i] = paymentDetail;
		    month++;
		    i ++;
		   }
		    TotalAmount=lixiT+total;
		   String lixiT_fn = fn.format(lixiT);
		   System.out.println("总利息为：" + lixiT_fn);
		   coll[0] = X_fn;
		   coll[1] = lixiT_fn;
		   coll[2] = fn.format(TotalAmount);
		   pv.setMonthTotalAmount(X_fn);
		   pv.setTotalAmount(fn.format(TotalAmount));
		   pv.setTotalLiXi(lixiT_fn);
		   pv.setPaymentDetail(pd);
		  }
		  return pv;
		 }
	 
	 
//	 public PaymentView payback(double debt,double NLL,int MONTH){
//		 MonthPaymentDetail[] pd = new MonthPaymentDetail[MONTH];
//		 PaymentView pv = new PaymentView();
//		 double MLL=NLL/12;   //月利率
//		 int month=1;
//		  //double debt=100;
//		  String[] coll = new String[3];
//		  //NumberFormat fn=NumberFormat.getIntegerInstance();
//		  DecimalFormat fn = new DecimalFormat("#0.00");
//		  String nll=fn.format(NLL*100)+"%";
//		  String mll=fn.format(MLL*100)+"%";
//		  String debt_fn=fn.format(debt);
//		  
//		  int mode=2;
//		  
//		  //等额本金还款
//		  if(mode==1){
//		   System.out.println("您总共借款"+debt_fn+";还款方式：等额本金还款；还款时间：1年"+";年利率是："+nll+";月利率"+mll);
//		   
//		   System.out.println("分期还款明细");
//		   
//		   double monthPincipal=debt/12;  //每月应还本金
//		   debt=monthPincipal*12;
//		   double accrualM;  //每月还款利息
//		   double tm;  //每月还款金额
//		   
//		   //分期还款明细
//		   while(debt>=1){
//		    accrualM=debt*MLL;
//		    tm=monthPincipal+accrualM;
//		    debt=debt-monthPincipal;
//		    if(debt<1){
//		     debt=0;
//		    }
//		    
//		    //把小数位数格式化成2位
//		    String tm_fn=fn.format(tm);
//		    String monthPincipal_fn=fn.format(monthPincipal);
//		    String accrualM_fn=fn.format(accrualM);
//		    String debt_fn2=fn.format(debt);
//		    
//		    System.out.println("第"+month+"月    还款金额:"+tm_fn+" 本月应还本金:"+monthPincipal_fn+" 本月还款利息:"+accrualM_fn+" 剩余本金:"+debt_fn2);
//		    month++;
//		   }
//		  }
//		  
//		  
//		  
//		  MonthPaymentDetail paymentDetail;
//		  //等额本息还款
//		  if(mode==2){
//		   System.out.println("您总共借款"+debt_fn+";还款方式：等额本息还款；还款时间："+ MONTH +"月"+";年利率是："+nll+";月利率"+mll);
//		   
//		   //等额本息还款的月还款数公式
//		   double X=debt*MLL*(Math.pow((1+MLL), MONTH))/(Math.pow((1+MLL), MONTH)-1);  
//		   String X_fn=fn.format(X);   //格式化小数位数
//		   System.out.println("您的月还款额为："+X_fn);
//		   
//		   //分期还款明细
//		   double lixiT = 0;
//		   double lixiM,benjinM;  //月利息，月本金
//		   System.out.println("分期还款明细");
//		   int i = 0;
//		   while(debt>=1){
//			paymentDetail = new MonthPaymentDetail();
//		    lixiM=debt*MLL;
//		    benjinM=X-lixiM;
//		    debt=debt-benjinM;
//		    if(debt<1){
//		     debt=0;
//		    }
//		    
//		    //输出
//		    String lixiM_fn=fn.format(lixiM);
//		    String benjinM_fn=fn.format(benjinM);
//		    String debt_fn3=fn.format(debt);
//		    System.out.println("第"+month+"月    还款金额:"+X_fn+" 本月应还本金(即减少债务的钱):"+benjinM_fn+" 本月还款利息:"+lixiM_fn+" 剩余本金:"+debt_fn3);
//		    paymentDetail.setMonthPaymentAmount(X_fn);
//		    paymentDetail.setBenjinM(benjinM_fn);
//		    paymentDetail.setMonth(String.valueOf(month));
//		    paymentDetail.setLixiM(lixiM_fn);
//		    paymentDetail.setMonthAmount(debt_fn3);
//		    lixiT = lixiT + Double.valueOf(lixiM_fn);
//		    pd[i] = paymentDetail;
//		    month++;
//		    i ++;
//		   }
//		   String lixiT_fn = fn.format(lixiT);
//		   System.out.println("总利息为：" + lixiT_fn);
//		   coll[0] = X_fn;
//		   coll[1] = lixiT_fn;
//		   coll[2] = fn.format(X*MONTH);
//		   pv.setMonthTotalAmount(X_fn);
//		   pv.setTotalAmount(fn.format(X*MONTH));
//		   pv.setTotalLiXi(lixiT_fn);
//		   pv.setPaymentDetail(pd);
//		  }
//		  return pv;
//		 }
//	 
	 /**
		 * 计算奖励 【award=1固定金额分摊奖励，award=2按投标金额比例奖励】
		 * @param borrow
		 * @return
		 */
		 public String reward(Borrow borrow){
			 Double reward;
			 NumberFormat fn=NumberFormat.getInstance();
			 if(borrow.getAward().equals("1")){
					reward =Double.parseDouble(borrow.getPartAccount());
				}else if(borrow.getAward().equals("2")){
					reward = Double.parseDouble(borrow.getAccount())* ((Double.parseDouble(borrow.getFunds())/(double)100));
				}else{
					reward=0.00;
				}
			 //把小数位数格式化成2位
			    String num=fn.format(reward);
			return num;
			 
		 }
		 
		 /**
		  * 封装插入资金变化的信息
		  * @param type
		  * @param total
		  * @param money
		  * @param useMoney
		  * @param noUseMoney
		  * @param collection
		  * @param toUser
		  * @param operatorer
		  * @param remark
		  * @param operatorIp
		  * @param user
		  * @return
		  */
		 	public UserAccountDetail saveAccountDetail(String type,BigDecimal total,BigDecimal money,BigDecimal useMoney,BigDecimal noUseMoney,
		 			BigDecimal collection,Integer toUser,String operatorer,String remark,String operatorIp,Integer userId){
		 		UserAccountDetail userAccountDetail = new UserAccountDetail();
		 		userAccountDetail.setType(type);
		 		userAccountDetail.setTotal(total);
		 		userAccountDetail.setMoney(money);
		 		userAccountDetail.setUseMoney(useMoney);
		 		userAccountDetail.setNoUseMoney(noUseMoney);
		 		userAccountDetail.setCollection(collection);
		 		userAccountDetail.setToUser(toUser);
		 		userAccountDetail.setOperatorer(operatorer);
		 		userAccountDetail.setRemark(remark);
		 		userAccountDetail.setOperatorIp(operatorIp);
		 		userAccountDetail.setUserId(userId);
		 		return userAccountDetail;
		 		
//		 		userAccountDetailService.save(userAccountDetail	);
		 	}
	 }
