package com.qmd.service.impl.payment;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmd.dao.payment.PaymentDao;
import com.qmd.dao.user.*;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.payment.RechargeConfig;
import com.qmd.mode.user.*;
import com.qmd.mode.util.Hongbao;
import com.qmd.payment.BasePaymentProduct;
import com.qmd.payment.UnionPayUtils;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.payment.PaymentService;
import com.qmd.util.AccountDetailUtil;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;
import com.qmd.util.bean.HongbaoDetail;
import com.qmd.util.bean.UnionPayInfo;
import com.qmd.util.rongbaoUtils.RongbaoUtil;
import com.ysd.biz.PushForBiz;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("paymentService")
public class PaymentServiceImpl  extends BaseServiceImpl<UserAccountRecharge, Integer> implements PaymentService{

	@Resource
	UserDao userDao;
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
	public void setBaseDao(PaymentDao paymentDao) {
		super.setBaseDao(paymentDao);
	}

	public String getLastPaymentSn(){
		return paymentDao.getLastPaymentSn();
	}
	
	
	
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
	public void addUserAccountRecharge(UserAccountRecharge userAccountRecharge){
		paymentDao.addUserAccountRecharge(userAccountRecharge);
		
	}
	
	
	/**
	 * 更新充值支付方式id
	 * @param map
	 */
	public void updateRechargeId(Map<String,Object> map){
		paymentDao.updateRechargeId(map);
	}
	
	
	/**
	 * 银行卡签约充值 0.01
	 * @param userAccountRecharge
	 */
	public synchronized  Integer addUserAccountRecharge(UserAccountRecharge userAccountRecharge,AccountBank accountBank,User user){
		//add by yujian
		if(accountBank.getStatus()==null) {
			accountBank.setStatus(0);//签约中
		}
		
		List<AccountBank> accountBankList  = userDao.queryAccountBank(user.getId());
		if(accountBankList.size()>0){
			if(accountBankList.size()==1){
				accountBank.setId(accountBankList.get(0).getId());
				this.userDao.updateAccountBank(accountBank);
			}else{
				return 1;
			}
		}else{
			this.userDao.addAccountBank(accountBank);
		}
		//yujian 充值记录取消
		paymentDao.addUserAccountRecharge(userAccountRecharge);
		userDao.updateRealName(user);
		return 0;
	}
	
	public UserAccountRecharge getUserAccountRechargeByTradeNo(String tradeNo){
		return paymentDao.getUserAccountRechargeByTradeNo(tradeNo);
	}
	
	public BasePaymentProduct getPaymentProduct(Integer id){
		RechargeConfig rechargeConfig = getPaymentConfig(id);
		BasePaymentProduct basePaymentProduct=null;
		try {
			basePaymentProduct = (BasePaymentProduct)Class.forName(rechargeConfig.getClassName()).newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		basePaymentProduct.setId(rechargeConfig.getPaymentProductId());
		basePaymentProduct.setName(rechargeConfig.getName());
		basePaymentProduct.setDescription(rechargeConfig.getDescription());
		basePaymentProduct.setBargainorIdName(rechargeConfig.getBargainorId());
		basePaymentProduct.setBargainorKeyName(rechargeConfig.getBargainorKey());
		basePaymentProduct.setLogoPath(rechargeConfig.getLogoPath());
		return basePaymentProduct;
	}
	
	
	//银联商务支付
    @Deprecated
	public int unionPaymentRecharge(UserAccountRecharge recharge, User user){
		AccountBank accountBank =userDao.queryAccountBank(user.getId()).get(0);
		
		int ret =0;
		//校验是否绑定银行卡成功
		String exCode = "HZJCB_UNPY_SUB_BIND";
		String bindId = accountBank.getBindId();
		String amount = recharge.getMoney().multiply(new BigDecimal(100)).setScale(0).toString();//交易金额，以分为单位 
		String remarks="";
		String  merId= "1000000503"; // 第三方商户平台接入号
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String orderNo = recharge.getTradeNo();
		Date d = new Date();
		String tranDateTime = simpleDateFormat.format(d);
		String timestamp = d.getTime()+"";
		String jsonBody04 ="{\"amount\":\""+amount+"\",\"bindId\":\""+bindId+"\",\"exCode\":\""+exCode+"\",\"merId\":\""+merId+"\",\"orderNo\":\""+orderNo+"\",\"remarks\":\""+remarks+"\",\"\": \""+tranDateTime+"\",\"timestamp\": \""+timestamp+"\"}";
		UnionPayInfo info = UnionPayUtils.unionToPay(jsonBody04);
		if("0000".equals(info.getRetCode()) ){
			if("2".equals(info.getOrderStatus())){ //  0:已接受, 1:处理中,2:处理成功,3:处理失败
				recharge.setStatus(1);
				recharge.setRemark( new Gson().toJson(info));
				paymentDao.addUserAccountRecharge(recharge);
				BigDecimal tranAmt = recharge.getMoney();
				//修改总账户记录-充值金额
				UserAccount userAccount = userAccountDao.getUserAccountByUserId(user.getId());
				userAccount = userAccountDao.getForUpdate(userAccount.getId(), userAccount);
				
				userAccount.setModifyDate(new Date());
				userAccount.setTotal(userAccount.getTotal().add(tranAmt));
				userAccount.setAbleMoney(userAccount.getAbleMoney().add(tranAmt));
				this.userAccountDao.update(userAccount);
				
				//添加流水记录-充值金额
				UserAccountDetail userAccountDetail = AccountDetailUtil.fillUserAccountDetail("recharge", 
						tranAmt, 10000, "线上充值:"+tranAmt+"元", 
						recharge.getIpOperator(), userAccount);
				userAccountDetailDao.save(userAccountDetail);
				
				if(StringUtils.isNotEmpty(user.getDeviceToken())){
					/*try {PushUtil push = new PushUtil();
					String message = "尊敬的"+user.getUsername()+"，您已成功充值:"+tranAmt+"元。立即投资，轻松获得高收益。";
						if(user.getDeviceToken().length() ==44){
							push.pushAndoridUnicast(user.getDeviceToken(), "充值成功", "充值成功", message,"com.rongxun.JingChuBao.Activities.RechargeRecordActivity");
						}else if(user.getDeviceToken().length() ==64){
							push.pushIosUnicast(user.getDeviceToken(), message, "","yhcz");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}*/
                    PushForBiz.pushWhenRecharge(String.valueOf(user.getId()), user.getUsername(), tranAmt);
				}
				ret =1;
			}else if( "0".equals(info.getRetCode())  || "1".equals(info.getRetCode()) ){
				recharge.setStatus(2);
				recharge.setRemark( new Gson().toJson(info));
				paymentDao.addUserAccountRecharge(recharge);
				ret =2;
			}else{
				recharge.setStatus(0);
				recharge.setRemark( new Gson().toJson(info));
				paymentDao.addUserAccountRecharge(recharge);
			}
		}else{
			recharge.setStatus(0);
			recharge.setRemark( new Gson().toJson(info));
			paymentDao.addUserAccountRecharge(recharge);
		}
		return ret;
	}
	

	@Resource
	UserHongbaoDao userHongbaoDao;
	@Resource
	UserAwardDetailDao userAwardDetailDao;
	
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


	public Map postRongbaoPay(UserAccountRecharge userRecharge, User user,AccountBank accountBank) {
		
		
		
		 String res=RongbaoUtil.RongbaoPayAgain(userRecharge, user,accountBank);
		
		 Map map = (Map)JSON.parse(res);

		//请求融宝发送验证码
		

		return map;
		
	}
	
	
	
}
