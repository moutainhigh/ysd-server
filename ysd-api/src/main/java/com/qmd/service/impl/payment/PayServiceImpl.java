package com.qmd.service.impl.payment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmd.dao.payment.PaymentDao;
import com.qmd.dao.user.UserAccountDao;
import com.qmd.dao.user.UserAccountDetailDao;
import com.qmd.dao.user.UserAccountRechargeDao;
import com.qmd.dao.user.UserAwardDetailDao;
import com.qmd.dao.user.UserDao;
import com.qmd.dao.user.UserHongbaoDao;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.user.AccountBank;
import com.qmd.mode.user.User;
import com.qmd.mode.user.UserAccount;
import com.qmd.mode.user.UserAccountDetail;
import com.qmd.mode.user.UserAccountRecharge;
import com.qmd.mode.user.UserAwardDetail;
import com.qmd.mode.user.UserHongbao;
import com.qmd.mode.util.Hongbao;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.payment.PayService;
import com.qmd.util.AccountDetailUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;
import com.qmd.util.bean.HongbaoDetail;
import com.ysd.biz.PushForBiz;

@Service("payService")
public class PayServiceImpl  extends BaseServiceImpl<UserAccountRecharge, Integer> implements PayService{

	@Resource
	private UserDao userDao;
	@Resource
	private PaymentDao paymentDao;
	@Resource
	private UserAccountDetailDao userAccountDetailDao;
	@Resource
	private UserAccountDao userAccountDao;
	@Resource
	private UserAccountRechargeDao userAccountRechargeDao;
	@Resource
	private ListingDao listingDao;
	@Resource
	private UserHongbaoDao userHongbaoDao;
	@Resource
	private UserAwardDetailDao userAwardDetailDao;
	
	
	/**
	 * 充值失败
	 */
	public void rechargeError(Map<String,Object> map){
		paymentDao.rechargeStatus(map);
	}
	
	/*
	 *detailMap: 
	 *tranAmt交易金额
	 * */
	public int rechargeSuccess(Map<String,Object> map,Map<String,Object> detailMap){
		BigDecimal tranAmt = BigDecimal.valueOf(Double.valueOf(detailMap.get("tranAmt").toString()));	//交易金额
		BigDecimal feeAmt = new BigDecimal(0);
		String paymentFee = detailMap.get("feeAmt").toString();
		if(StringUtils.isNotEmpty(paymentFee)){
			feeAmt = BigDecimal.valueOf(Double.valueOf(paymentFee));		//商户提取佣金金额
		}
		
		Integer rechargeId = (Integer)map.get("rechargeId");
		UserAccountRecharge entity = new UserAccountRecharge();
		entity = userAccountRechargeDao.getForUpdate(rechargeId, entity);
		if (entity.getStatus().equals(1)) {// 状态为1：已经充值完成
			return 1;
		}
		if (!entity.getStatus().equals(2)) {// 状态为非2：不是充值状态
			return 2;
		}
		
		//修改总账户记录-充值金额
		UserAccount userAccount = userAccountDao.getUserAccountByUserId(entity.getUserId());
		
		userAccount = userAccountDao.getForUpdate(userAccount.getId(), userAccount);
		
		userAccount.setModifyDate(new Date());
		userAccount.setTotal(userAccount.getTotal().add(tranAmt));
		userAccount.setAbleMoney(userAccount.getAbleMoney().add(tranAmt));
		this.userAccountDao.update(userAccount);
		
		//添加流水记录-充值金额
		UserAccountDetail userAccountDetail = AccountDetailUtil.fillUserAccountDetail("recharge", 
				tranAmt, 10000, "线上充值:"+tranAmt+"元", 
				detailMap.get("operatorIp").toString(), userAccount);
		userAccountDetailDao.save(userAccountDetail);
		
		if(feeAmt.doubleValue() > 0) {// 手续费大于0,金额变更
		
		//修改总账户记录-扣除手续费金额
		UserAccount userAccount_fee = userAccount;
		userAccount_fee.setModifyDate(new Date());
		if(StringUtils.isNotEmpty(detailMap.get("feeType").toString())){
			if(detailMap.get("feeType").toString().equals("2")||detailMap.get("feeType").toString().equals("3")){
				userAccount_fee.setTotal(userAccount_fee.getTotal().add(feeAmt));
				userAccount_fee.setAbleMoney(userAccount_fee.getAbleMoney().add(feeAmt));
			}
		}else{
			userAccount_fee.setTotal(userAccount_fee.getTotal().subtract(feeAmt));
			userAccount_fee.setAbleMoney(userAccount_fee.getAbleMoney().subtract(feeAmt));
		}
		
		
		this.userAccountDao.update(userAccount_fee);
		
		//添加流水记录-扣除手续费金额
		if(StringUtils.isNotEmpty(detailMap.get("feeType").toString())){
			if(detailMap.get("feeType").toString().equals("2")||detailMap.get("feeType").toString().equals("3")){
				UserAccountDetail userAccountDetail_fee = AccountDetailUtil.fillUserAccountDetail("recharge_offline_reward", 
						feeAmt, 10000, "增加充值奖励:"+feeAmt+"元", detailMap.get("operatorIp").toString(), userAccount_fee);
				userAccountDetailDao.save(userAccountDetail_fee);
			}	
		}else{
			UserAccountDetail userAccountDetail_fee = AccountDetailUtil.fillUserAccountDetail("fee", 
					feeAmt, 10000, "扣除手续费:"+feeAmt+"元", detailMap.get("operatorIp").toString(), userAccount_fee);
			
			userAccountDetailDao.save(userAccountDetail_fee);
		}
		
			
	}
		
		paymentDao.rechargeStatus(map);
		
		System.out.println("start++++++++++++++++++"+"银行卡签约".equals(entity.getRemark())+"+++++++++++"+entity.getMoney().toString()+"++++++"+entity.getMoney().compareTo(new BigDecimal(0.01)));
		
		//判断充值是否为 签约充值
		if("银行卡签约".equals(entity.getRemark()) ){//&& entity.getMoney().compareTo(new BigDecimal(0.10)) <= 0
			System.out.println(entity.getUserId());
			List<AccountBank> abList = this.userDao.queryAccountBank(entity.getUserId());
			System.out.println(abList.size());
			AccountBank ab = new AccountBank();
			if(abList != null && abList.size()>0){
				ab = abList.get(0);
				//签约成功
				AccountBank bankUpdate = new AccountBank();
				bankUpdate.setId(ab.getId());
				bankUpdate.setStatus(1);
				userDao.updateAccountBank(bankUpdate);
				User user = userDao.getById(entity.getUserId(),new User());
				user.setRealStatus(1);
				userDao.updateRealName(user);
				chkeakInformation(user,"");
				return 3;
			}
			
		}else{
			User user = userDao.getByUserId(userAccount.getUserId());
			try {
			 PushForBiz.pushWhenRecharge(String.valueOf(user.getId()), user.getUsername(), tranAmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("end++");
		return 0;
	}
	
	
	/****
	 * 
	 * 判断是否符合满足发放奖励条件 满足则发放奖励红包 user:被推广人
	 * ****/
	public void chkeakInformation(User user, String ip) {
		user = userDao.getByUserId(user.getId());
		if (user.getTgStatus() != null && user.getTgStatus() == 0) {// 推广奖励没发
			if (user.getTgOneLevelUserId() != null) {// 存在上级好友
				if (user.getRealStatus() != null && user.getRealStatus() == 1) {// 实名已经通过

					// 好友实名，推广人获得红包
					Hongbao hbao = listingDao.getHongbao(3);
					if (hbao.getIsEnabled().compareTo(1) == 0 && hbao.getTotal().compareTo(BigDecimal.ZERO) > 0) {
						List<HongbaoDetail> hbDetailList = (List<HongbaoDetail>) new Gson().fromJson(hbao.getHongbaoDetail(),
								new TypeToken<List<HongbaoDetail>>() {
								}.getType());
						for (HongbaoDetail hbdetail : hbDetailList) {
							BigDecimal award0 = hbdetail.getMoney();
							UserAccount userAccount0 = userAccountDao.getUserAccountByUserId(user.getTgOneLevelUserId());
							userAccount0.setAwardMoney(userAccount0.getAwardMoney().add(award0));
							userAccountDao.update(userAccount0);

							// 添加红包记录
							UserHongbao hb = new UserHongbao();
							hb.setUserId(user.getTgOneLevelUserId());// 用户ID
							hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd") + CommonUtil.getRandomString(5));
							hb.setName("好友认证通过");
							hb.setMoney(award0);
							hb.setUsedMoney(BigDecimal.ZERO);
							hb.setStatus(0);
							Date d = new Date();
							hb.setStartTime(d);
							hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d, hbdetail.getExpDate())));
							hb.setSource(4);
							hb.setSourceString("好友认证通过");
							hb.setSourceUserId(user.getId());
							hb.setSourceBorrowId(null);
							hb.setUsedBorrowId(null);

							hb.setExpDate(hbdetail.getExpDate());
							hb.setLimitStart(hbdetail.getLimitStart());
							hb.setLimitEnd(hbdetail.getLimitEnd());
							hb.setIsPc(hbdetail.getIsPc());
							hb.setIsApp(hbdetail.getIsApp());
							hb.setIsHfive(hbdetail.getIsHfive());
							userHongbaoDao.save(hb);
							// 奖励账户资金记录
							UserAwardDetail uad = new UserAwardDetail();
							uad.setUserId(user.getTgOneLevelUserId());// 用户ID
							uad.setType(ConstantUtil.MONEY_LOG_NAME_EMAIL_TENDER_AWARD);// 类型（同资金记录）
							uad.setMoney(award0);// 操作金额
							uad.setSignFlg(1);
							uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
							uad.setRemark("好友认证通过");// 备注
							uad.setReserve1(hb.getHbNo());
							uad.setRelateKey("hongbao_id");
							userAwardDetailDao.save(uad);

						}

						// 修改推广奖励发放状态
						user.setTgStatus(1);// 推广奖励发送状态【0：没发放（默认），1：已发放】
						userDao.update(user);
					}
				}
			}
		}
	}
}
