package com.qmd.service.impl.payment;

import com.qmd.dao.payment.PaymentDao;
import com.qmd.dao.user.UserAccountDao;
import com.qmd.dao.user.UserAccountDetailDao;
import com.qmd.dao.user.UserAccountRechargeDao;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.payment.PaymentService;
import com.qmd.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("paymentService")
public class PaymentServiceImpl  extends BaseServiceImpl<UserAccountRecharge, Integer> implements PaymentService{

	@Resource
	private PaymentDao paymentDao;
	@Resource
	private UserAccountDetailDao userAccountDetailDao;
	@Resource
	private UserAccountDao userAccountDao;
	@Resource
	private UserAccountRechargeDao userAccountRechargeDao;
	
	@Resource
	public void setBaseDao(PaymentDao paymentDao) {
		super.setBaseDao(paymentDao);
	}

//	public String getLastPaymentSn(){
//		return paymentDao.getLastPaymentSn();
//	}
	
	
	
	public List<RechargeConfig> getPaymentConfigList(){
		return paymentDao.getPaymentConfigList();
	}
	
	public RechargeConfig getPaymentConfig(Integer id){
		return paymentDao.getPaymentConfig(id);
	}
	
	/**
	 * 添加充值记录
	 * @param userAccountRecharge
	 */
//	public void addUserAccountRecharge(UserAccountRecharge userAccountRecharge){
//		paymentDao.addUserAccountRecharge(userAccountRecharge);
//		
//		RechargeStatus rs = new RechargeStatus();
//		rs.setUserAccountRechargeId(userAccountRecharge.getId());
//		paymentDao.addRechargeStatus(rs);
//	}
	
//	public int rechargeSuccess(Map<String,Object> map,Map<String,Object> detailMap){
//		BigDecimal tranAmt = BigDecimal.valueOf(Double.valueOf(detailMap.get("tranAmt").toString()));	//交易金额
//		BigDecimal feeAmt = new BigDecimal(0);
//		String paymentFee = detailMap.get("feeAmt").toString();
//		if(StringUtils.isNotEmpty(paymentFee)){
//			feeAmt = BigDecimal.valueOf(Double.valueOf(paymentFee));		//商户提取佣金金额
//		}
//		
//		Integer rechargeId = (Integer)map.get("rechargeId");
//		UserAccountRecharge entity = new UserAccountRecharge();
//		entity = userAccountRechargeDao.getForUpdate(rechargeId, entity);
//		if (entity.getStatus().equals(1)) {// 状态为1：已经充值完成
//			return 1;
//		}
//		if (!entity.getStatus().equals(2)) {// 状态为非1：不是充值状态
//			return 2;
//		}
//		
//		//修改总账户记录-充值金额
//		UserAccount userAccount = userAccountDao.getUserAccountByUserId(entity.getUserId());
//		
//		userAccount = userAccountDao.getForUpdate(userAccount.getId(), userAccount);
//		
//		userAccount.setModifyDate(new Date());
//		userAccount.setTotal(userAccount.getTotal().add(tranAmt));
//		userAccount.setAbleMoney(userAccount.getAbleMoney().add(tranAmt));
//		this.userAccountDao.update(userAccount);
//		
//		//添加流水记录-充值金额
//		UserAccountDetail userAccountDetail = AccountDetailUtil.fillUserAccountDetail("recharge", 
//				tranAmt, 10000, "线上充值:"+tranAmt+"元", 
//				detailMap.get("operatorIp").toString(), userAccount);
//		userAccountDetailDao.save(userAccountDetail);
//		
//		if(feeAmt.doubleValue() > 0) {// 手续费大于0,金额变更
//		
//		//修改总账户记录-扣除手续费金额
//		UserAccount userAccount_fee = userAccount;
//		userAccount_fee.setModifyDate(new Date());
//		userAccount_fee.setTotal(userAccount_fee.getTotal().subtract(feeAmt));
//		userAccount_fee.setAbleMoney(userAccount_fee.getAbleMoney().subtract(feeAmt));
//		this.userAccountDao.update(userAccount_fee);
//		
//		//添加流水记录-扣除手续费金额
//		UserAccountDetail userAccountDetail_fee = AccountDetailUtil.fillUserAccountDetail("fee", 
//				feeAmt, 10000, "扣除手续费:"+feeAmt+"元", detailMap.get("operatorIp").toString(), userAccount_fee);
//		
//		userAccountDetailDao.save(userAccountDetail_fee);
//		}
//		
//		
//		paymentDao.rechargeStatus(map);
//		
//		return 0;
//	}
	
	/**
	 * 添加机构充值记录
	 * @param addUserAccountRechargeAgence
	 */
	public void addUserAccountRechargeAgence(UserAccountRecharge userAccountRecharge){
		paymentDao.addUserAccountRecharge(userAccountRecharge);
		
		
		BigDecimal tranAmt = userAccountRecharge.getMoney();	//交易金额
		
		BigDecimal feeAmt = userAccountRecharge.getFee();

		UserAccountRecharge entity = new UserAccountRecharge();
		entity = userAccountRechargeDao.getForUpdate(userAccountRecharge.getId(), entity);
		
		//修改总账户记录-充值金额
		UserAccount userAccount = userAccountDao.getUserAccountByUserId(entity.getUserId());
		userAccount = userAccountDao.getForUpdate(userAccount.getId(), userAccount);
		userAccount.setModifyDate(new Date());
		userAccount.setTotal(userAccount.getTotal().add(tranAmt));
		if (userAccountRecharge.getRechargeInterfaceId()==3) {// 直投充值
			userAccount.setDirectMoney(CommonUtil.defaultBigDecimal(userAccount.getDirectMoney()).add(tranAmt));
		} else {
			userAccount.setAbleMoney(userAccount.getAbleMoney().add(tranAmt));
		}
		
		this.userAccountDao.update(userAccount);
		
		
		if(feeAmt.doubleValue() > 0) {// 手续费大于0,金额变更
		
		//修改总账户记录-扣除手续费金额
		UserAccount userAccount_fee = userAccount;
		userAccount_fee.setModifyDate(new Date());
		userAccount_fee.setTotal(userAccount_fee.getTotal().subtract(feeAmt));
		userAccount_fee.setAbleMoney(userAccount_fee.getAbleMoney().subtract(feeAmt));
		this.userAccountDao.update(userAccount_fee);
		
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("modifyDate", new Date());
		map.put("status", 1);
		map.put("returned", "");
		map.put("tradeNo", userAccountRecharge.getTradeNo());
		
		paymentDao.rechargeStatus(map);
		
	}
	
	/**
	 * 添加平台服务商还款充值记录
	 * @param addUserAccountRechargeAgence
	 */
	public void addUserAccountRechargeRepaymentByAgency(UserAccountRecharge userAccountRecharge){
		paymentDao.addUserAccountRecharge(userAccountRecharge);
		
		
		BigDecimal tranAmt = userAccountRecharge.getMoney();	//交易金额
		
		BigDecimal feeAmt = userAccountRecharge.getFee();

		UserAccountRecharge entity = new UserAccountRecharge();
		entity = userAccountRechargeDao.getForUpdate(userAccountRecharge.getId(), entity);
		
		//修改总账户记录-充值金额
		UserAccount userAccount = userAccountDao.getUserAccountByUserId(entity.getUserId());
		userAccount = userAccountDao.getForUpdate(userAccount.getId(), userAccount);
		userAccount.setModifyDate(new Date());
		userAccount.setTotal(userAccount.getTotal().add(tranAmt));
		if (userAccountRecharge.getRechargeInterfaceId()==3) {// 直投充值
			userAccount.setDirectMoney(CommonUtil.defaultBigDecimal(userAccount.getDirectMoney()).add(tranAmt));
		} else {
			userAccount.setAbleMoney(userAccount.getAbleMoney().add(tranAmt));
		}
		
		this.userAccountDao.update(userAccount);
		
		
		if(feeAmt.doubleValue() > 0) {// 手续费大于0,金额变更
		
		//修改总账户记录-扣除手续费金额
		UserAccount userAccount_fee = userAccount;
		userAccount_fee.setModifyDate(new Date());
		userAccount_fee.setTotal(userAccount_fee.getTotal().subtract(feeAmt));
		userAccount_fee.setAbleMoney(userAccount_fee.getAbleMoney().subtract(feeAmt));
		this.userAccountDao.update(userAccount_fee);
		
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("modifyDate", new Date());
		map.put("status", 1);
		map.put("returned", "");
		map.put("tradeNo", userAccountRecharge.getTradeNo());
		
		paymentDao.rechargeStatus(map);
		
	}
	
	public UserAccountRecharge getUserAccountRechargeByTradeNo(String tradeNo){
		return paymentDao.getUserAccountRechargeByTradeNo(tradeNo);
	}
	
}
