package com.qmd.service.impl.user;


import com.qmd.dao.agency.AgencyDao;
import com.qmd.dao.borrow.BorrowTenderDaoService;
import com.qmd.dao.user.*;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.admin.Admin;
import com.qmd.mode.user.*;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.UserService;
import com.qmd.util.CommonUtil;
import com.qmd.util.Pager;
import com.qmd.util.bean.ShowBean;
import com.qmd.util.md5.MD5;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
/**
 * Service实现类 - 用户基本信息
 * ============================================================================
 */
@Service("userService")
public class UserServiceImpl  extends BaseServiceImpl<User, Integer>  implements UserService {
	
	@Resource
	UserDao userDao;
	@Resource
	UserInfoDao userInfoDao;
	@Resource
	UserAccountDao userAccountDao;
	@Resource
	AccountBankDao accountBankDao;
	@Resource
	UserAccountDetailDao userAccountDetailDao;
	@Resource
	ListingDao listingDao;
	@Resource
	UserSpreadDetailDao userSpreadDetailDao;
	@Resource
	AgencyDao agencyDao;
	@Resource
	UserAwardDetailDao userAwardDetailDao;
	@Resource
	BorrowTenderDaoService borrowTenderDaoService;
	@Resource
	UserAccountRechargeDao userAccountRechargeDao;
	
	@Resource
	public void setBaseDao(UserDao userDao) {
		super.setBaseDao(userDao);
	}
	
	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}
	public UserAccountDao getUserAccountDao() {
		return userAccountDao;
	}
	public void setUserAccountDao(UserAccountDao userAccountDao) {
		this.userAccountDao = userAccountDao;
	}
	public User getUser(Map<String,Object> map){
		return this.userDao.getUser(map);
	}
	
	public List<User> getUserList(Map<String,Object> map) {
		return this.userDao.getUserList(map);
	}
	
	@Override
	public Pager queryUser(Pager pager,Map<String,Object> map) {
		return this.userDao.queryUser(pager,map);
	}

//	@Override
//	public String buildRecoverKey() {
//		return System.currentTimeMillis() + ConstantUtil.RECOVER_KEY_SEPARATOR + CommonUtil.getUUID() + DigestUtils.md5Hex(String.valueOf(Math.random() * 1000000000));
//	}
	
//	@Override
//	public Date getRecoverKeyBuildDate(String recoverKey) {
//		long time = Long.valueOf(StringUtils.substringBefore(recoverKey, ConstantUtil.RECOVER_KEY_SEPARATOR));
//		return new Date(time);
//	}
//	public void updateUserIdentify(boolean result,User user,UserInfo userInfo,String ip) {
//		updateUserIdentify(false, result, user, userInfo, ip);
//	}
	
	
	
	@Override
	public void addUser(User user,UserInfo userInfo,AccountBank accountBank,String ip,Integer AgencyId){

		this.userDao.addUser(user);

		//用户详细信息添加
		userInfo.setUserId(user.getId());
		userInfo.setAddIp(user.getAddIp());
//		userInfo.setPrivateName(user.getRealName());
		this.userInfoDao.addUserInfo(userInfo);

		//用户账户总表信息添加
		BigDecimal def_account = new BigDecimal(0);
		UserAccount userAccount = new UserAccount();
		userAccount.setUserId(user.getId());
		userAccount.setCreateDate(new Date());
		userAccount.setModifyDate(new Date());
		userAccount.setTotal(def_account);
		userAccount.setAbleMoney(def_account);
		userAccount.setUnableMoney(def_account);
		userAccount.setCollection(def_account);
		userAccount.setInvestorCollectionCapital(def_account);
		userAccount.setInvestorCollectionInterest(def_account);
		userAccount.setBorrowerCollectionCapital(def_account);
		userAccount.setBorrowerCollectionInterest(def_account);
		userAccount.setCashMoney(def_account);
		userAccount.setContinueTotal(def_account);
		this.userAccountDao.addUserAccount(userAccount);
		//
		accountBank.setModifyDate(new Date());
		accountBank.setAddIp(ip);
		accountBank.setCreateDate(new Date());
		accountBank.setUserId(user.getId());
		accountBank.setStatus(1);
		accountBank.setAgencyId(AgencyId);
		this.userDao.addAccountBank(accountBank);
		if(user.getTgOneLevelUserId() != null){
			UserSpreadDetail usd = new UserSpreadDetail();
			usd.setTenderUserId(user.getId());
			usd.setTenderMoney(new BigDecimal(0));
			usd.setTgMoney(new BigDecimal(0));
			usd.setStatusCode(1);
			usd.setTgUserId(user.getTgOneLevelUserId());
			usd.setBorrowId(null);
			usd.setRemark("新注册用户");
			userSpreadDetailDao.baseInsert(usd);
		}
	}
	
	@Override
	public void updateUser(User user, UserInfo userInfo,AccountBank accountBank
			 ) {
		// TODO Auto-generated method stub
		this.userDao.update(user);

		userInfo.setUserId(user.getId());
		userInfo.setModifyDate(user.getModifyDate());
		this.userInfoDao.update(userInfo);

		accountBank.setModifyDate(new Date());

		accountBank.setUserId(user.getId());
		this.accountBankDao.update(accountBank);

	}
	public void updateUserByLoginSuccess(User user){
		this.userDao.updateUserByLoginSuccess(user);
	}
	
//	public void updateProfile(User user){
//		this.userDao.updateProfile(user);
//	}
	
//	public void updateRealName(User user){
//		this.userDao.updateRealName(user);
//	}
	public void updateRealName(User user,String ip){
		
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("sign", "taste_grant_realname");
//		Listing listing_grant = listingDao.findListing(map);
//		if ("1".equals(listing_grant.getKeyValue())) {
//			
//			user.setTasteTimeRealname(CommonUtil.notEmptyInteger(user.getTasteTimeRealname()) + 1);
//			user.setTasteAbleBegin(CommonUtil.date2begin(new Date()));
//			user.setTasteAbleEnd(CommonUtil.date2end(CommonUtil.getMonthAfter_oneDay(new Date(),1)));
//			
//			map = new HashMap<String,Object>();
//			map.put("sign", "taste_grant_money_realname");
//			Listing listing_money = listingDao.findListing(map);
//			BigDecimal money = new BigDecimal(listing_money.getKeyValue());
//			
//			UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(user.getId());
//			userAccount = this.userAccountDao.getForUpdate(userAccount.getId(),
//					userAccount); // 账户锁定
//			
//			userAccount.setTasteMoney(CommonUtil.notEmptyBigDecimal(userAccount.getTasteMoney()).add(money));
//			
//			userAccountDao.update(userAccount);
//			
//			// 资金记录
//			UserAccountDetail userAccountDetail = AccountDetailUtil
//					.fillUserAccountDetail(
//							"taste_grant",
//							money,
//							10000,
//							"实名认证通过发放体验金"+money+"元,有效期从"+CommonUtil.getIntDate(user.getTasteAbleBegin())+"到"+CommonUtil.getIntDate(user.getTasteAbleEnd()),
//							ip,
//							userAccount);
//			userAccountDetailDao.save(userAccountDetail);
//			
//		}
		
		this.userDao.updateRealName(user);
	}
	
//	public int receiveTaste(Integer id,String ip) {
//		//0 领取成功。-1 领取失败。1 网站没有开通体验金。2 已经领取过体验金。3 实名未认证，
//		
//		//Map<String,Object> map = new HashMap<String,Object>();
//		//map.put("id", id);
//		//User user = userDao.getUser(map);
//		User user = userDao.getForUpdate(id);
//		if (user.getRealStatus()==null || user.getRealStatus() != 1) {
//			return 3;//3 实名未认证
//		}
//		
////		Map<String, Object> map_grant  = new HashMap<String, Object>();
////		map_grant .put("sign", "taste_grant_realname");
//		String listing_grant = listingDao.getKeyValue("taste_grant_realname");
//		if (!"1".equals(listing_grant)) {
//			return 1;// 体验金没开通
//		}
//		
//		if (user.getTasteTimeRealname()!=null && user.getTasteTimeRealname() > 0) {
//			return 2; //已经领取过体验金
//		}
//		
//		// 发放体验金
//		user.setTasteTimeRealname(CommonUtil.notEmptyInteger(user.getTasteTimeRealname()) + 1);
//		user.setTasteAbleBegin(CommonUtil.date2begin(new Date()));
//		user.setTasteAbleEnd(CommonUtil.date2end(CommonUtil.getMonthAfter_oneDay(new Date(),1)));
//		
//		userDao.update(user);
//		
////		Map<String,Object> map_money = new HashMap<String,Object>();
////		map_money.put("sign", "taste_grant_money_realname");
//		String listing_money = listingDao.getKeyValue("taste_grant_money_realname");
//		BigDecimal money = new BigDecimal(listing_money);
//		
//		UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(user.getId());
//		userAccount = this.userAccountDao.getForUpdate(userAccount.getId(),
//				userAccount); // 账户锁定
//		
//		userAccount.setTasteMoney(CommonUtil.notEmptyBigDecimal(userAccount.getTasteMoney()).add(money));
//		
//		userAccountDao.update(userAccount);
//		
//		// 资金记录
//		UserAccountDetail userAccountDetail = AccountDetailUtil
//				.fillUserAccountDetail(
//						"taste_grant",
//						money,
//						10000,
//						"发放体验金"+money+"元,有效期从"+CommonUtil.getIntDate(user.getTasteAbleBegin())+"到"+CommonUtil.getIntDate(user.getTasteAbleEnd()),
//						ip,
//						userAccount);
//		userAccountDetailDao.save(userAccountDetail);
//		
//		UserTasteDetail userTasteDetail = new UserTasteDetail();
//		userTasteDetail.setUserId(id);
//		userTasteDetail.setMoney(money);
//		userTasteDetail.setAbleBegin(user.getTasteAbleBegin());
//		userTasteDetail.setAbleEnd(user.getTasteAbleEnd());
//		userTasteDetail.setStatusCode(1);
//		userTasteDetail.setReasonCode(1);
//		userTasteDetail.setRemark("实名领取体验金");
//		userTasteDetailDao.baseInsert(userTasteDetail);
//		
//		return 0;
//	}
	
	/**
	 * 激活体验金
	 * @param user 用户
	 * @return 0 可以激活，1 网站没开通体验金，2 已经做过激活，3 体验金状态有效不需激活，4 无体检金，5 待收小于制定金额， 6;//体验金有效时间不正确, 7 未领取体验金
	 */
//	public int checkTasteActivation(User user) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("sign", "taste_grant_realname");
//		Listing listing_grant = listingDao.findListing(map);
//		if (!"1".equals(listing_grant.getKeyValue())) {
//			return 1;// 体验金没开通
//		}
//		if (user.getTasteTimeRealname()==null || user.getTasteTimeRealname() <1) {
//			return 7;
//		}
//		
//		if (user.getTasteTimeRealname() != null
//				&& user.getTasteTimeRealname() >= 2) {
//			return 2;// 已经做过激活 1 实名认证，2第一次激活，>=2 是已经做过一次激活的，不再激活
//		}
//		
//		if (user.getTasteAbleBegin()==null || user.getTasteAbleEnd()==null) {
//			return 6;//体验金有效时间不正确
//		}
//		
//		Map<String,Object> map1 = new HashMap<String,Object>();
//		map1.put("userId", user.getId());
//		map1.put("statusCode", 1);
//		map1.put("reasonCode", 1);
//		
//		List<UserTasteDetail> utdList = userTasteDetailDao.queryListByMap(map1);
//		if (utdList!=null&utdList.size() > 0) {
//			return 3;// 在有效期内 为空或者在有效期内，不能再激活
//		}
//		
////		Date now = new Date();
////		Integer a = CommonUtil.compareDate(now, user.getTasteAbleBegin());
////		Integer b = CommonUtil.compareDate(now, user.get.getTasteAbleEnd());
////		if (a == null || b == null || (a > 0 && b < 0)) {
////			return 3;// 在有效期内 为空或者在有效期内，不能再激活
////		}
////		UserAccount userAccount = this.userAccountDao
////				.getUserAccountByUserId(user.getId());
//		UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(user.getId());
////		if (userAccount.getTasteMoney() == null
////				|| userAccount.getTasteMoney().doubleValue() <= 0) {
////			return 4;// 体检金为空或者体检金额为0，不能激活
////		}
//
//		map = new HashMap<String, Object>();
//		map.put("sign", "taste_activate_waitmoney");
//		Listing listing_waitmoney = listingDao.findListing(map);
//
//		BigDecimal money_c = new BigDecimal(listing_waitmoney.getKeyValue());
//
//		if (userAccount.getInvestorCollectionCapital() == null
//				|| userAccount.getInvestorCollectionCapital().compareTo(money_c) < 0) {
//			return 5;// 待收本金少于激活本金的条件
//		}
//		
//		return 0;
//	}
	
	/**
	 * 激活体验金
	 * @param id:user_id
	 * @param ip 
	 * @return 0 激活，1 网站没开通体验金，2 已经做过激活，3 体验金状态有效不需激活，4 无体检金，5 待收小于制定金额，6;//体验金有效时间不正确
	 */
//	public int updateTasteActivation(Integer id,String ip) {
//
//		User user = userDao.getById(id);
//
//		int checkValue = checkTasteActivation(user);
//		if (checkValue != 0)
//			return checkValue;
//
//		// 以上条件满足，开始激活体验金
//		user.setTasteTimeRealname(CommonUtil.notEmptyInteger(user
//				.getTasteTimeRealname()) + 1);
//		user.setTasteAbleBegin(CommonUtil.date2begin(new Date()));
//		user.setTasteAbleEnd(CommonUtil.date2end(CommonUtil
//				.getMonthAfter_oneDay(new Date(), 1)));
//
//		this.userDao.updateRealName(user);
//		
//		String listing_money = listingDao.getKeyValue("taste_grant_money_realname");
//		BigDecimal money = new BigDecimal(listing_money);
//		
//		UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(user.getId());
//		userAccount = this.userAccountDao.getForUpdate(userAccount.getId(),
//				userAccount); // 账户锁定
//		
//		userAccount.setTasteMoney(CommonUtil.notEmptyBigDecimal(userAccount.getTasteMoney()).add(money));
//		
//		userAccountDao.update(userAccount);
//		
//		// 资金记录
//		UserAccountDetail userAccountDetail = AccountDetailUtil
//				.fillUserAccountDetail(
//						"taste_grant",
//						money,
//						10000,
//						"激活体验金"+money+"元,有效期从"+CommonUtil.getIntDate(user.getTasteAbleBegin())+"到"+CommonUtil.getIntDate(user.getTasteAbleEnd()),
//						ip,
//						userAccount);
//		userAccountDetailDao.save(userAccountDetail);
//		
//		UserTasteDetail userTasteDetail = new UserTasteDetail();
//		userTasteDetail.setUserId(id);
//		userTasteDetail.setMoney(money);
//		userTasteDetail.setAbleBegin(user.getTasteAbleBegin());
//		userTasteDetail.setAbleEnd(user.getTasteAbleEnd());
//		userTasteDetail.setStatusCode(1);
//		userTasteDetail.setReasonCode(2);
//		userTasteDetail.setRemark("再激活领取体验金");
//		userTasteDetailDao.baseInsert(userTasteDetail);
//
//		return 0;
//	}
	
//	public void updateLitpic(User user){
//		this.userDao.updateLitpic(user);
//	}
	
//	public void addApplyBorrower(ApplyBorrower applyBorrower,User loginUser){
//		applyBorrower.setCreateDate(new Date());
//		applyBorrower.setModifyDate(new Date());
//		applyBorrower.setUpdateDate(new Date());
//		applyBorrower.setUserId(loginUser.getId());
//		this.userDao.addApplyBorrower(applyBorrower);
//		
//		this.userDao.updateTypeId(loginUser);
//	}
	
//	public void addApplyVip(ApplyVip applyVip,User loginUser){
//		applyVip.setCreateDate(new Date());
//		applyVip.setModifyDate(new Date());
//		applyVip.setUpdateDate(new Date());
//		applyVip.setUserId(loginUser.getId());
//		this.userDao.addApplyVip(applyVip);
//		
//		this.userDao.updateVipStatus(loginUser);
//	}
	
	
	public void addAccountBank(AccountBank accountBank){
		this.userDao.addAccountBank(accountBank);
	}
	
	
//	public void updateAccountBank(AccountBank accountBank){
//		this.userDao.updateAccountBank(accountBank);
//	}
	
	
	public List<AccountBank> queryAccountBank(Integer userId){
		return this.userDao.queryAccountBank(userId);
	}
	
	
//	public void updateEmailCheck(User user,String ip){
//		if(user.getEmailStatus()!=null && ConstantUtil.APPLY_STATUS_YES==user.getEmailStatus().intValue()) {
//			user = userDao.getForUpdate(user.getId());
//			Integer oldStatus = user.getEmailStatus();
//			user.setEmailStatus(ConstantUtil.APPLY_STATUS_YES); 
//			if (user.getEmailFreq()==null) {
//				user.setEmailFreq(0);
//			}
//			if (user.getEmailFreq()==0 && oldStatus==2)  {//第一次认证
//				
//				String key0 = listingDao.getKeyValue(ConstantUtil.CAMPAIGN_EMAIL_AWARD);
//				if (StringUtils.isNotEmpty(key0) && Double.valueOf(key0) > 0) {
//					Map<String,Object> mm = new HashMap<String,Object>();
//					mm.put("userId", user.getId());
//					mm.put("type", ConstantUtil.MONEY_LOG_EMAIL_AWARD);
//					Integer cc = userAwardDetailDao.queryCountByMap(mm);
//					if (cc!=null && cc < 1) {
//						BigDecimal award0 = new BigDecimal(key0);
//						UserAccount userAccount0 = userAccountDao.getUserAccountByUserId(user.getId());
//						userAccount0 = userAccountDao.getForUpdate(userAccount0.getId());
//						userAccount0.setTotal(userAccount0.getTotal().add(award0));
//						userAccount0.setAwardMoney(userAccount0.getAwardMoney().add(award0));
//						userAccountDao.update(userAccount0);
//						
//						UserAccountDetail userAccountDetail1 = AccountDetailUtil.fillUserAccountDetail(ConstantUtil.MONEY_LOG_EMAIL_AWARD, 
//								award0,user.getId(), "邮箱认证奖励",ip, userAccount0);
//						this.userAccountDetailDao.save(userAccountDetail1);
//						
//						// 奖励账户资金记录
//						UserAwardDetail uad = new UserAwardDetail();
//						uad.setUserId(user.getId());// 用户ID
//						uad.setType(ConstantUtil.MONEY_LOG_EMAIL_AWARD);// 类型（同资金记录）
//						uad.setMoney(award0);// 操作金额
//						uad.setSignFlg(1);//
//						uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
//						uad.setRemark("邮箱认证奖励");// 备注
//						uad.setUserAccountDetailId(userAccountDetail1.getId());// 资金记录ID
//						userAwardDetailDao.save(uad);
//						
//						int flg = updateFriendAward(user,2,ip);//
//						if (flg ==2) {
//							user.setFriendAwardFreq(1);
//						}
//					
//					}
//				}
//				
//			}
//			
//			user.setEmailFreq(user.getEmailFreq()+1);
//			
//		}
//		
//		this.userDao.updateEmail(user);
//	}
//	public void updateEmail(User user){
//		this.userDao.updateEmail(user);
//	}
	
//	public void updatePhone(User user){
//		this.userDao.updatePhone(user);
//	}
	
//	public void updateAvatar(User user){
//		this.userDao.updateAvatar(user);
//	}
	
	public void updatePassowrd(User user){
		this.userDao.updatePassowrd(user);
	}
	
	public void deleteAccountBank(Integer id){
		this.userDao.deleteAccountBank(id);
	}
	public List<Admin> getKefuAdminList(){
		return this.userDao.getKefuAdminList();
	}
	
//	public void addOnlineBooking(OnlineBooking onlineBooking){
//		onlineBooking.setCreateDate(new Date());
//		onlineBooking.setModifyDate(new Date());
//		userDao.addOnlineBooking(onlineBooking);
//	}
//	@Override
//	public void updateQuestion(User user) {
//		// TODO Auto-generated method stub
//		this.userDao.updateQuestion(user);
//	}
	
	public Boolean isPassword(String username,String password,String passwordType){
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("username", username);
		map.put("GuanLianDengLu", username);
		User user = userDao.getUser(map);
		String random = user.getRandomNum()==null?"":user.getRandomNum();
		String pwd = "";
		if(passwordType.equals("0")){//登录密码
			pwd = user.getPassword();
		}else if(passwordType.equals("1")){//支付密码
			pwd = user.getPayPassword();
		}
		if(pwd.equals( MD5.getMD5Str(MD5.getMD5Str(password)+random))){
			return true;
		}else{
			return false;
		}
	}
	
	

//	public void loginUserJhCard(User user){
//		this.userDao.updateUserByLoginSuccess(user);
//		user.setTasteAbleBegin(CommonUtil.date2begin(new Date()));
//		user.setTasteAbleEnd(CommonUtil.date2end(CommonUtil
//				.getMonthAfter_oneDay(new Date(), 1)));
//		//注册激活卡号 发放体验金
//		if(StringUtils.isNotEmpty(user.getClubCardNo()) ){
//			
//			// 发放绑定会员卡的奖励 
//			updateBindingcardAward(user);
//			
////			String listing_money = listingDao.getKeyValue("taste_grant_money_jh_card");
//			
//			//激活卡号送体验金
////			String listing_money = listingDao.getKeyValue("taste_grant_money_jh_card");
//			
////			String listing_money = listingDao.getKeyValue("club_card_type_2");//类型2【86开头】
//			//根据卡号类型判断送体验金金额
////			if("88".equals(user.getClubCardNo().substring(0, 2))){
////				listing_money = listingDao.getKeyValue("club_card_type_1");//类型1【88开头】
////			}
//			
///*			
//			String listing_money = "0";
//			String card_no = user.getClubCardNo().substring(0,2);//卡号前缀
//			//查询送体验金标准
//			listing_money = listingDao.getKeyValue("club_card_type_"+card_no);
//			if(StringUtils.isEmpty(listing_money)){
//				listing_money = "0";
//			}
//			
//			
//			BigDecimal money = new BigDecimal(listing_money);
//			
//			UserAccount userAccountjh = this.userAccountDao.getUserAccountByUserId(user.getId());
//			userAccountjh = this.userAccountDao.getForUpdate(userAccountjh.getId(),
//					userAccountjh); // 账户锁定
//			
//			userAccountjh.setTasteMoney(CommonUtil.notEmptyBigDecimal(userAccountjh.getTasteMoney()).add(money));
//			
//			userAccountDao.update(userAccountjh);
//			
//			// 资金记录
//			UserAccountDetail userAccountDetail = AccountDetailUtil
//					.fillUserAccountDetail(
//							"taste_grant",
//							money,
//							10000,
//							"激活卡号送体验金"+money+"元,有效期从"+CommonUtil.getIntDate(user.getTasteAbleBegin())+"到"+CommonUtil.getIntDate(user.getTasteAbleEnd()),
//							user.getAddIp(),
//							userAccountjh);
//			userAccountDetailDao.save(userAccountDetail);
//			
//			
//			UserTasteDetail userTasteDetail = new UserTasteDetail();
//			userTasteDetail.setUserId(user.getId());
//			userTasteDetail.setMoney(money);
//			userTasteDetail.setAbleBegin(user.getTasteAbleBegin());
//			userTasteDetail.setAbleEnd(user.getTasteAbleEnd());
//			userTasteDetail.setStatusCode(1);
//			userTasteDetail.setReasonCode(4);
//			userTasteDetail.setRemark("激活会员卡领取体验金");
//			userTasteDetailDao.baseInsert(userTasteDetail);
//*/		
//		}
//	}
	
	
	
//	public void addOffLine(OffLine offLine){
//		userDao.addOffLine(offLine);
//	}
	
//	public List<OffLine> getOffLineListByUserid(Map<String ,Object> map){
//		return userDao.getOffLineListByUserid(map);
//	}
	
//	public Pager queryOffLineListByUserid(Pager pager,Map<String,Object> map){
//		return userDao.queryOffLineListByUserid(pager, map);
//	}
	
//	public Agency getAgencyByUserid(Integer userid){
//		return userDao.getAgencyByUserid(userid);
//	}
	
//	public void updateAgency(Agency agency){
//		userDao.updateAgency(agency);
//	}
	
	
//	public void addLeague(League league){
//		userDao.addLeague(league);
//	}
	
//	public Pager querySpreadListByMap(Pager pager,Map<String,Object> map){
//		return userDao.querySpreadListByMap(pager, map);
//	}
	
//	@Override
//	public void updateTenderAuto(User user) {
//		
//		User oldUser = userDao.getById(user.getId(), user);
//		user.setAutoTenderDate(null);
//		boolean dateFlg = false;
//		if (user.getAutoTenderStatus()!=null&&!user.getAutoTenderStatus().equals(oldUser.getAutoTenderStatus())) {
//			dateFlg = true;
//		}
//		if (dateFlg == false && user.getAutoTenderLimitBegin()!=null&&!user.getAutoTenderLimitBegin().equals(oldUser.getAutoTenderLimitBegin())) {
//			dateFlg = true;
//		}
//		if (dateFlg == false && user.getAutoTenderLimitEnd()!=null&&!user.getAutoTenderLimitEnd().equals(oldUser.getAutoTenderLimitEnd())) {
//			dateFlg = true;
//		}
//		if (dateFlg == false &&  user.getAutoTenderRepayWay()!=null&&!user.getAutoTenderRepayWay().equals(oldUser.getAutoTenderRepayWay())) {
//			dateFlg = true;
//		}
//		if (dateFlg) {
//			user.setAutoTenderDate(new Date());
//		} else {
//			user.setAutoTenderDate(null);
//		}
//		
//		this.userDao.updateTenderAuto(user);
//	}
	
//	@Override
//	public Integer queryTenderAutoRank(Integer id) {
//		return userDao.queryTenderAutoRank(id);
//	}
	
	/**
	 * 
	 * @param user
	 * @param event 1实名，2邮箱，3投资
	 */
//	private int updateFriendAward(User user,int event,String ip) {
//		
//		if (user.getTgOneLevelUserId()==null || user.getTgOneLevelUserId()<1) {// 存在好友
//			return 0;
//		}
//		if (user.getFriendAwardFreq()==null || user.getFriendAwardFreq()!=0) {// 未领取奖励
//			return 0;
//		}
//		if (event !=1 && (user.getRealStatus()==null||user.getRealStatus() !=1)) {//实名
//			return 0;
//		}
//		if (event !=2 && (user.getEmailStatus()==null||user.getEmailStatus() !=1)) {//邮箱已认证
//			return 0;
//		}
//		
//		// 判断是否有
//		Map<String,Object> mm = new HashMap<String,Object>();
//		mm.put("userId", user.getId());
//		mm.put("noBusinessType", "99");
//		Integer c = borrowTenderDaoService.queryTenderDetailList(mm);
//		if (c==null || c<1) {
//			return 0;
//		}
//		String key_t = listingDao.getKeyValue(ConstantUtil.CAMPAIGN_NAME_EMAIL_TENDER_AWARD);
//		if (StringUtils.isEmpty(key_t) || Double.valueOf(key_t) <= 0) {
//			return 0;
//		}
//		
//		BigDecimal award_t = new BigDecimal(key_t);
//		
//		UserAccount userAccount0 = userAccountDao.getUserAccountByUserId(user.getTgOneLevelUserId());
//		userAccount0 = userAccountDao.getForUpdate(userAccount0.getId());
//		userAccount0.setTotal(userAccount0.getTotal().add(award_t));
//		userAccount0.setAwardMoney(userAccount0.getAwardMoney().add(award_t));
//		userAccountDao.update(userAccount0);
//		
//		UserAccountDetail userAccountDetail1 = AccountDetailUtil.fillUserAccountDetail(ConstantUtil.MONEY_LOG_NAME_EMAIL_TENDER_AWARD, 
//				award_t,user.getTgOneLevelUserId(), "好友["+user.getUsername()+"]推广奖励",ip, userAccount0);
//		this.userAccountDetailDao.save(userAccountDetail1);
//		
//		// 奖励账户资金记录
//		UserAwardDetail uad = new UserAwardDetail();
//		uad.setUserId(user.getTgOneLevelUserId());// 用户ID
//		uad.setType(ConstantUtil.MONEY_LOG_NAME_EMAIL_TENDER_AWARD);// 类型（同资金记录）
//		uad.setMoney(award_t);// 操作金额
//		uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
//		uad.setSignFlg(1);
//		uad.setRemark("好友["+user.getUsername()+"]推广奖励");// 备注
//		uad.setRelateKey("user_id");
//		uad.setRelateTo(user.getId());
//		uad.setUserAccountDetailId(userAccountDetail1.getId());// 资金记录ID
//		userAwardDetailDao.save(uad);
//		
//		return event;
//	}
	
	public Integer 	queryCountUserByMap(Map<String,Object> map3) {
		Integer c = userDao.queryCountByMap(map3);
		return c;
	}
	
	public List<ShowBean> queryDirectInfo(String type,Map<String,Object> map) {
		List<ShowBean> list = new ArrayList<ShowBean>();
		ShowBean d = null;
		if ("c".equals(type)) {
			List<UserAccountRecharge> l = userAccountRechargeDao.getUserAccountRechargeListSum(map);
			BigDecimal total = BigDecimal.ZERO;
			if ( l !=null && l.size()>0) {
				for(UserAccountRecharge a:l) {
					d = new ShowBean();
					d.setName(a.getName());
					d.setMoney(CommonUtil.setPriceScale(a.getMoney()));
					total = total.add(a.getMoney());
					list.add(d);
				}
			}
			d = new ShowBean();
			d.setName("合计");
			d.setMoney(CommonUtil.setPriceScale(total));
			list.add(d);
			
		} else if ("t0".equals(type)) {
			Double obj = (Double)userAccountRechargeDao.getByStatementId("AccountCash.selectMoneySumCashing",map);
			d = new ShowBean();
			d.setName("提现中");
			d.setMoney(CommonUtil.setPriceScale2BigDecimal(obj));
			list.add(d);
		} else if ("t1".equals(type)) {
			Double obj = (Double)userAccountRechargeDao.getByStatementId("AccountCash.selectMoneySumCashed",map);
			d = new ShowBean();
			d.setName("提现完成");
			d.setMoney(CommonUtil.setPriceScale2BigDecimal(obj));
			list.add(d);
		}
		
		return list;
	}

	/**
	 * 借款人列表
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager queryJkrlb(Pager pager,Map<String,Object> map){
		return userDao.queryJkrlb(pager, map);
	}
	
}
