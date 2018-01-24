package com.qmd.service.impl.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmd.dao.place.IosIdfaDao;
import com.qmd.dao.place.PlaceChilderDao;
import com.qmd.dao.report.LogIdentityDao;
import com.qmd.dao.user.*;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.admin.Admin;
import com.qmd.mode.place.IosIdfa;
import com.qmd.mode.place.PlaceChilder;
import com.qmd.mode.report.LogIdentity;
import com.qmd.mode.user.*;
import com.qmd.mode.util.Hongbao;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.UserService;
import com.qmd.util.*;
import com.qmd.util.bean.HongbaoDetail;
import com.qmd.util.card.QueryCard;
import com.qmd.util.md5.PWDUtil;
import com.qmd.util.redis.CacheUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import javax.annotation.Resource;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service实现类 - 用户基本信息
 * ============================================================================
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements
		UserService {

	@Resource
	UserDao userDao;
	@Resource
	UserInfoDao userInfoDao;
	@Resource
	UserAccountDao userAccountDao;
	@Resource
	private ListingDao listingDao;
	@Resource
	UserAccountDetailDao userAccountDetailDao;
	@Resource
	UserAwardDetailDao userAwardDetailDao;
	@Resource
	UserSpreadDetailDao userSpreadDetailDao;
	@Resource
	LogIdentityDao logIdentityDao;
	@Resource
	UserHongbaoDao userHongbaoDao;

	@Resource
	private PlaceChilderDao placeChilderDao;
	@Resource
	UserAnalysDao userAnalysDao;
	@Resource
	IosIdfaDao iosIdfaDao;

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

	public User getUser(Map<String, Object> map) {
		return this.userDao.getUser(map);
	}

	public List<User> getUserList(Map<String, Object> map) {
		return this.userDao.getUserList(map);
	}

	@Override
	public Pager queryUser(Pager pager, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.userDao.queryUser(pager, map);
	}

	@Override
	public String buildRecoverKey() {
		return System.currentTimeMillis()
				+ ConstantUtil.RECOVER_KEY_SEPARATOR
				+ CommonUtil.getUUID()
				+ DigestUtils
						.md5Hex(String.valueOf(Math.random() * 1000000000));
	}

	@Override
	public Date getRecoverKeyBuildDate(String recoverKey) {
		long time = Long.valueOf(StringUtils.substringBefore(recoverKey,
				ConstantUtil.RECOVER_KEY_SEPARATOR));
		return new Date(time);
	}

	@Override
	public  List<UserAward> checkAwardPeople(Integer id) {
		// TODO Auto-generated method stub
		List<UserAward> list = this.userDao.checkAwardPeople(id);
		return list;
	}

	@Override
	public  List<UserAward> getAwardList() {
		// TODO Auto-generated method stub
		List<UserAward> list = this.userDao.getAwardList();
		return list;
	}

	@Override
	public UserAward getAwardInfo(Integer id) {
		// TODO Auto-generated method stub
		UserAward info = this.userDao.getAwardInfo(id);
		return info;
	}

	@Override
	public boolean checkAwardCode(String id) {
		// TODO Auto-generated method stub
		boolean check = this.userDao.checkAwardCode(id);
		return check;
	}

	@Override
	public void updateAwardInfo(UserAward userAward) {
		// TODO Auto-generated method stub
		this.userDao.updateAwardInfo(userAward);
	}
	
	/**
	 * 给用户发注册红包
	 * @param user
	 * @param userAccount
	 */
	private void sendRegHongbao(User user,UserAccount userAccount){
		Hongbao hbao = listingDao.getHongbao(1);
		if (hbao.getIsEnabled().compareTo(1) == 0 && hbao.getTotal().compareTo(BigDecimal.ZERO) > 0) {
			List<HongbaoDetail> hbDetailList = (List<HongbaoDetail>) new Gson().fromJson(hbao.getHongbaoDetail(),
					new TypeToken<List<HongbaoDetail>>() {
					}.getType());
			for (HongbaoDetail hbdetail : hbDetailList) {
				BigDecimal award0 = hbdetail.getMoney();
				UserAccount userAccount0 = userAccountDao.getForUpdate(userAccount.getId(), userAccount);
				userAccount0.setAwardMoney(userAccount0.getAwardMoney().add(award0));
				userAccountDao.update(userAccount0);

				// 添加红包记录
				net.qmdboss.beans.UserHongbao hb = new net.qmdboss.beans.UserHongbao();
				hb.setUserId(user.getId());
				hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd") + CommonUtil.getRandomString(5));
				hb.setName("用户注册");
				hb.setMoney(award0);
				hb.setUsedMoney(BigDecimal.ZERO);
				hb.setStatus(0);
				Date d = new Date();
				hb.setStartTime(d);
				hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d, hbdetail.getExpDate())));
				hb.setSource(1);
				hb.setSourceString("用户注册");
				hb.setSourceUserId(user.getId());
				hb.setSourceBorrowId(null);
				hb.setUsedBorrowId(null);

				hb.setExpDate(hbdetail.getExpDate());
				hb.setLimitStart(hbdetail.getLimitStart());
				hb.setLimitEnd(hbdetail.getLimitEnd());
				hb.setIsPc(hbdetail.getIsPc());
				hb.setIsApp(hbdetail.getIsApp());
				hb.setIsHfive(hbdetail.getIsHfive());
				hb.setInvestFullMomey(hbdetail.getInvestFullMomey());
				hb.setIsLooked(0);
				userHongbaoDao.saveNewHongbao(hb);
				// 奖励账户资金记录
				UserAwardDetail uad = new UserAwardDetail();
				uad.setUserId(user.getId());// 用户ID
				uad.setType(ConstantUtil.MONEY_LOG_REGISTER_AWARD);// 类型（同资金记录）
				uad.setMoney(award0);// 操作金额
				uad.setSignFlg(1);
				uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
				uad.setRemark("用户注册奖励");// 备注
				uad.setReserve1(hb.getHbNo());
				uad.setRelateKey("hongbao_id");
				userAwardDetailDao.save(uad);
			}
		}
	} 
	
	@Override
	public synchronized int addUser(User user) {

		user.setTgNo(CommonUtil.getRandomNumAndLetter(6));// 随机生成数

		User checkUser = new User();
		checkUser.setTgNo(user.getTgNo());
		List<User> uList = userDao.queryListByObject(checkUser);
		if (uList != null && uList.size() > 0) {
			user.setTgNo(CommonUtil.getRandomNumAndLetter(7));
		}
		
		if(StringUtils.isNotEmpty(user.getPlaceName()) ){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("random", user.getPlaceName());
			List<PlaceChilder> list = placeChilderDao.queryListByMap(map);
			if(list!=null && list.size() >0){
				PlaceChilder pc = list.get(0);
				user.setPlaceChilderId(pc.getId());
				user.setPlaceName(pc.getRandom());
			}
			
		}else {
			if( StringUtils.isNotEmpty(user.getAppType()) && "0".equals(user.getAppType())){ //IOS App Store
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("random", "app store");
				List<PlaceChilder> list = placeChilderDao.queryListByMap(map);
				if(list!=null && list.size() >0){
					PlaceChilder pc = list.get(0);
					user.setPlaceChilderId(pc.getId());
					user.setPlaceName(pc.getRandom());
				}
			}
		}
		user.setEmailStatus(0);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("username",user.getUsername());
		if(this.userDao.getUser(map) != null){
			return 1;
		}
		
		this.userDao.addUser(user);
		
		//
		UserAnalys analys = new UserAnalys();
		analys.setUserId(user.getId());
		analys.setAppType(user.getAppType());
		
		analys.setTgOneLevelUserId(user.getTgOneLevelUserId());
		
		analys.setDeviceToken(user.getDeviceToken());
		analys.setBrowserType(user.getBrowserType());
		analys.setImei(user.getImei());
		
		if(StringUtils.isNotBlank(user.getIdfa())){
			analys.setIdfa(user.getIdfa());
			IosIdfa iosidfa = iosIdfaDao.activate(user.getIdfa(),1);
			if(iosidfa != null){
				user.setPlaceChilderId(iosidfa.getPlaceChilderId());
				user.setPlaceName(iosidfa.getSource());
			}
		}else if(StringUtils.isNotBlank(user.getMac())){
			analys.setMac(user.getMac());
			IosIdfa iosidfa = iosIdfaDao.activate(user.getMac(),2);
			if(iosidfa !=null){
				user.setPlaceChilderId(iosidfa.getPlaceChilderId());
				user.setPlaceName(iosidfa.getSource());
			}
		}
		analys.setPlaceName(user.getPlaceName());
		analys.setPlaceChilderId(user.getPlaceChilderId());
		userAnalysDao.save(analys);
	
		
		//渠道活动推广用户
		if(user.getPlaceChilderId() != null){
			PlaceChilder pc = placeChilderDao.get(user.getPlaceChilderId());
			pc.setRegNum(pc.getRegNum()+1);
			placeChilderDao.update(pc);
		}
		
		

		// 用户详细信息添加
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(user.getId());
		userInfo.setAddIp(user.getAddIp());
		// userInfo.setPrivateName(user.getRealName());
		userInfo.setCreateDate(user.getCreateDate());
		userInfo.setModifyDate(user.getModifyDate());
		this.userInfoDao.addUserInfo(userInfo);

		// 用户账户总表信息添加
		BigDecimal def_account = new BigDecimal(0);
		UserAccount userAccount = new UserAccount();
		userAccount.setUserId(user.getId());
		userAccount.setCreateDate(new Date());
		userAccount.setModifyDate(new Date());
		userAccount.setTotal(def_account);
		userAccount.setAbleMoney(def_account);
		userAccount.setUnableMoney(def_account);
		userAccount.setCollection(def_account);
		userAccount.setAwardMoney(def_account);
		userAccount.setTasteMoney(def_account);
		userAccount.setInvestorCollectionCapital(def_account);
		userAccount.setInvestorCollectionInterest(def_account);
		userAccount.setBorrowerCollectionCapital(def_account);
		userAccount.setBorrowerCollectionInterest(def_account);
		this.userAccountDao.addUserAccount(userAccount);

		if (user.getTgOneLevelUserId() != null) {
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

//		// 注册红包
//		Hongbao hb = listingDao.getHongbao(1);
//		if (hb.getIsEnabled().compareTo(1) == 0
//				&& hb.getTotal().compareTo(BigDecimal.ZERO) > 0) {
//			Integer day_qixian = Integer.parseInt(listingDao
//					.getKeyValue("tuijian_award2"));// 红包有效期
//			hongBaoRegisterJiLu(hb, userAccount, user, day_qixian);
//		}
		/** @author zdl 旧的发注册红包逻辑
		Hongbao hbao = listingDao.getHongbao(1);
		if (hbao.getIsEnabled().compareTo(1) == 0 && hbao.getTotal().compareTo(BigDecimal.ZERO) > 0) {
			List<HongbaoDetail> hbDetailList = (List<HongbaoDetail>) new Gson().fromJson(hbao.getHongbaoDetail(),
					new TypeToken<List<HongbaoDetail>>() {
					}.getType());
			for (HongbaoDetail hbdetail : hbDetailList) {
				BigDecimal award0 = hbdetail.getMoney();
				UserAccount userAccount0 = userAccountDao.getForUpdate(userAccount.getId(), userAccount);
				userAccount0.setAwardMoney(userAccount0.getAwardMoney().add(award0));
				userAccountDao.update(userAccount0);

				// 添加红包记录
				UserHongbao hb = new UserHongbao();
				hb.setUserId(user.getId());
				hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd") + CommonUtil.getRandomString(5));
				hb.setName("用户注册");
				hb.setMoney(award0);
				hb.setUsedMoney(BigDecimal.ZERO);
				hb.setStatus(0);
				Date d = new Date();
				hb.setStartTime(d);
				hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d, hbdetail.getExpDate())));
				hb.setSource(1);
				hb.setSourceString("用户注册");
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
				uad.setUserId(user.getId());// 用户ID
				uad.setType(ConstantUtil.MONEY_LOG_REGISTER_AWARD);// 类型（同资金记录）
				uad.setMoney(award0);// 操作金额
				uad.setSignFlg(1);
				uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
				uad.setRemark("用户注册奖励");// 备注
				uad.setReserve1(hb.getHbNo());
				uad.setRelateKey("hongbao_id");
				userAwardDetailDao.save(uad);
			}
		}
		***/
		//@author zdl 新的发注册红包逻辑
		sendRegHongbao(user, userAccount);
		
		return 0;
	}

	public void hongBaoRegisterJiLu(Hongbao hongbao, UserAccount userAccount,
			User user, Integer day_qixian) {
		for (int i = 0; i < hongbao.getTenth(); i++) {
			BigDecimal award0 = new BigDecimal(10);
			UserAccount userAccount0 = userAccountDao.getForUpdate(
					userAccount.getId(), userAccount);
			userAccount0
					.setAwardMoney(userAccount0.getAwardMoney().add(award0));
			userAccountDao.update(userAccount0);

			// 添加红包记录
			UserHongbao hb = new UserHongbao();
			hb.setUserId(user.getId());
			hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")
					+ CommonUtil.getRandomString(5));
			hb.setName("用户注册");
			hb.setMoney(award0);
			hb.setUsedMoney(BigDecimal.ZERO);
			hb.setStatus(0);
			Date d = new Date();
			hb.setStartTime(d);
			hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d,
					day_qixian)));
			hb.setSource(1);
			hb.setSourceString("用户注册");
			hb.setSourceUserId(user.getId());
			hb.setSourceBorrowId(null);
			hb.setUsedBorrowId(null);
			userHongbaoDao.save(hb);
			// 奖励账户资金记录
			UserAwardDetail uad = new UserAwardDetail();
			uad.setUserId(user.getId());// 用户ID
			uad.setType(ConstantUtil.MONEY_LOG_REGISTER_AWARD);// 类型（同资金记录）
			uad.setMoney(award0);// 操作金额
			uad.setSignFlg(1);
			uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
			uad.setRemark("用户注册奖励");// 备注
			uad.setReserve1(hb.getHbNo());
			uad.setRelateKey("hongbao_id");
			userAwardDetailDao.save(uad);
		}

		for (int i = 0; i < hongbao.getTwentieth(); i++) {
			BigDecimal award0 = new BigDecimal(20);
			UserAccount userAccount0 = userAccountDao.getForUpdate(
					userAccount.getId(), userAccount);
			userAccount0
					.setAwardMoney(userAccount0.getAwardMoney().add(award0));
			userAccountDao.update(userAccount0);

			// 添加红包记录
			UserHongbao hb = new UserHongbao();
			hb.setUserId(user.getId());
			hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")
					+ CommonUtil.getRandomString(5));
			hb.setName("用户注册");
			hb.setMoney(award0);
			hb.setUsedMoney(BigDecimal.ZERO);
			hb.setStatus(0);
			Date d = new Date();
			hb.setStartTime(d);
			hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d,
					day_qixian)));
			hb.setSource(1);
			hb.setSourceString("用户注册");
			hb.setSourceUserId(user.getId());
			hb.setSourceBorrowId(null);
			hb.setUsedBorrowId(null);
			userHongbaoDao.save(hb);

			// 奖励账户资金记录
			UserAwardDetail uad = new UserAwardDetail();
			uad.setUserId(user.getId());// 用户ID
			uad.setType(ConstantUtil.MONEY_LOG_REGISTER_AWARD);// 类型（同资金记录）
			uad.setMoney(award0);// 操作金额
			uad.setSignFlg(1);
			uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
			uad.setRemark("用户注册奖励");// 备注
			uad.setReserve1(hb.getHbNo());
			uad.setRelateKey("hongbao_id");
			userAwardDetailDao.save(uad);
		}

		for (int i = 0; i < hongbao.getFiftieth(); i++) {
			BigDecimal award0 = new BigDecimal(50);
			UserAccount userAccount0 = userAccountDao.getForUpdate(
					userAccount.getId(), userAccount);
			userAccount0
					.setAwardMoney(userAccount0.getAwardMoney().add(award0));
			userAccountDao.update(userAccount0);

			// 添加红包记录
			UserHongbao hb = new UserHongbao();
			hb.setUserId(user.getId());
			hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")
					+ CommonUtil.getRandomString(5));
			hb.setName("用户注册");
			hb.setMoney(award0);
			hb.setUsedMoney(BigDecimal.ZERO);
			hb.setStatus(0);
			Date d = new Date();
			hb.setStartTime(d);
			hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d,
					day_qixian)));
			hb.setSource(1);
			hb.setSourceString("用户注册");
			hb.setSourceUserId(user.getId());
			hb.setSourceBorrowId(null);
			hb.setUsedBorrowId(null);
			userHongbaoDao.save(hb);

			// 奖励账户资金记录
			UserAwardDetail uad = new UserAwardDetail();
			uad.setUserId(user.getId());// 用户ID
			uad.setType(ConstantUtil.MONEY_LOG_REGISTER_AWARD);// 类型（同资金记录）
			uad.setMoney(award0);// 操作金额
			uad.setSignFlg(1);
			uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
			uad.setRemark("用户注册奖励");// 备注
			uad.setReserve1(hb.getHbNo());
			uad.setRelateKey("hongbao_id");
			userAwardDetailDao.save(uad);
		}
	}

	public void updateUserByLoginSuccess(User user) {
		this.userDao.updateUserByLoginSuccess(user);
	}

	public void updateProfile(User user) {
		this.userDao.updateProfile(user);
	}

	public void updateRealName(User user) {
		this.userDao.updateRealName(user);

		chkeakInformation(user, "");
	}

	public void updateLitpic(User user) {
		this.userDao.updateLitpic(user);
	}

	public void addAccountBank(AccountBank accountBank) {
		this.userDao.addAccountBank(accountBank);
	}

	public void updateAccountBank(AccountBank accountBank) {
		this.userDao.updateAccountBank(accountBank);
	}

	public List<AccountBank> queryAccountBank(Integer userId) {
		return this.userDao.queryAccountBank(userId);
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
					Hongbao hbao = listingDao.getHongbao(3);
					if (hbao.getIsEnabled().compareTo(1) == 0 && hbao.getTotal().compareTo(BigDecimal.ZERO) > 0) {
					// 好友实名，推广人获得红包
					List<HongbaoDetail> hbDetailList = (List<HongbaoDetail>) new Gson().fromJson(hbao.getHongbaoDetail(),new TypeToken<List<HongbaoDetail>>() {
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

					// if(user.getEmailStatus()!=null&&user.getEmailStatus()==1){//邮箱通过
					// ---给推广人发放奖励
					/**
					 * String key_t = listingDao.getKeyValue(ConstantUtil.
					 * CAMPAIGN_NAME_EMAIL_TENDER_AWARD); if
					 * (key_t!=null&&Double.valueOf(key_t)> 0 ) { BigDecimal
					 * award_t = new BigDecimal(key_t); UserAccount userAccount0
					 * = userAccountDao.getUserAccountByUserId(user.
					 * getTgOneLevelUserId()); userAccount0 =
					 * userAccountDao.getForUpdate
					 * (userAccount0.getId(),userAccount0); //
					 * userAccount0.setTotal
					 * (userAccount0.getTotal().add(award_t));//增加总额
					 * userAccount0
					 * .setAwardMoney(userAccount0.getAwardMoney().add
					 * (award_t));//增加红包账户资金
					 * userAccountDao.update(userAccount0);
					 * 
					 * //添加红包记录 UserHongbao hb = new UserHongbao();
					 * hb.setUserId(user.getTgOneLevelUserId());
					 * hb.setHbNo(CommonUtil.getDate2String(new Date(),
					 * "yyyyMMdd")+CommonUtil.getRandomString(5));
					 * hb.setName("好友认证通过"); hb.setMoney(award_t);
					 * hb.setUsedMoney(BigDecimal.ZERO); hb.setStatus(0); Date d
					 * = new Date(); hb.setStartTime(d); Integer day_qixian =
					 * Integer.parseInt(
					 * listingDao.getKeyValue("tuijian_award2"));//红包有效期
					 * hb.setEndTime
					 * (CommonUtil.date2end(CommonUtil.getDateAfter(d,
					 * day_qixian))); hb.setSource(4);
					 * hb.setSourceString("好友认证通过");
					 * hb.setSourceUserId(user.getId());
					 * hb.setSourceBorrowId(null); hb.setUsedBorrowId(null);
					 * userHongbaoDao.save(hb);
					 * 
					 * // 奖励账户 资金记录 UserAwardDetail uad = new UserAwardDetail();
					 * uad.setUserId(user.getTgOneLevelUserId());// 用户ID
					 * uad.setType
					 * (ConstantUtil.MONEY_LOG_NAME_EMAIL_TENDER_AWARD);//
					 * 类型（同资金记录） uad.setMoney(award_t);// 操作金额
					 * uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
					 * uad.setSignFlg(1);
					 * uad.setRemark("好友["+user.getUsername()+"]认证通过");// 备注
					 * uad.setRelateKey("user_id");
					 * uad.setRelateTo(user.getId());
					 * uad.setReserve1(hb.getHbNo()); //
					 * uad.setUserAccountDetailId(userAccountDetail1.getId());//
					 * 资金记录ID userAwardDetailDao.save(uad);
					 * 
					 * //修改注册奖励发放状态
					 * user.setTgStatus(1);//推广奖励发送状态【0：没发放（默认），1：已发放】
					 * user.setSumTenderMoney(award_t);//奖励的红包
					 * userDao.update(user); }
					 **/
					// }
				}

			}
		}
	}

	public void hongBaoRealNameJiLu(Hongbao hongbao, User user,
			Integer day_qixian) {
		for (int i = 0; i < hongbao.getTenth(); i++) {
			BigDecimal award0 = new BigDecimal(10);
			UserAccount userAccount0 = userAccountDao
					.getUserAccountByUserId(user.getTgOneLevelUserId());
			userAccount0
					.setAwardMoney(userAccount0.getAwardMoney().add(award0));
			userAccountDao.update(userAccount0);

			// 添加红包记录
			UserHongbao hb = new UserHongbao();
			hb.setUserId(user.getTgOneLevelUserId());// 用户ID
			hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")
					+ CommonUtil.getRandomString(5));
			hb.setName("好友认证通过");
			hb.setMoney(award0);
			hb.setUsedMoney(BigDecimal.ZERO);
			hb.setStatus(0);
			Date d = new Date();
			hb.setStartTime(d);
			hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d,
					day_qixian)));
			hb.setSource(4);
			hb.setSourceString("好友认证通过");
			hb.setSourceUserId(user.getId());
			hb.setSourceBorrowId(null);
			hb.setUsedBorrowId(null);
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

		for (int i = 0; i < hongbao.getTwentieth(); i++) {
			BigDecimal award0 = new BigDecimal(20);
			UserAccount userAccount0 = userAccountDao
					.getUserAccountByUserId(user.getTgOneLevelUserId());
			userAccount0
					.setAwardMoney(userAccount0.getAwardMoney().add(award0));
			userAccountDao.update(userAccount0);

			// 添加红包记录
			UserHongbao hb = new UserHongbao();
			hb.setUserId(user.getTgOneLevelUserId());// 用户ID
			hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")
					+ CommonUtil.getRandomString(5));
			hb.setName("好友认证通过");
			hb.setMoney(award0);
			hb.setUsedMoney(BigDecimal.ZERO);
			hb.setStatus(0);
			Date d = new Date();
			hb.setStartTime(d);
			hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d,
					day_qixian)));
			hb.setSource(4);
			hb.setSourceString("好友认证通过");
			hb.setSourceUserId(user.getId());
			hb.setSourceBorrowId(null);
			hb.setUsedBorrowId(null);
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

		for (int i = 0; i < hongbao.getFiftieth(); i++) {
			BigDecimal award0 = new BigDecimal(50);
			UserAccount userAccount0 = userAccountDao
					.getUserAccountByUserId(user.getTgOneLevelUserId());
			userAccount0
					.setAwardMoney(userAccount0.getAwardMoney().add(award0));
			userAccountDao.update(userAccount0);

			// 添加红包记录
			UserHongbao hb = new UserHongbao();
			hb.setUserId(user.getTgOneLevelUserId());// 用户ID
			hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")
					+ CommonUtil.getRandomString(5));
			hb.setName("好友认证通过");
			hb.setMoney(award0);
			hb.setUsedMoney(BigDecimal.ZERO);
			hb.setStatus(0);
			Date d = new Date();
			hb.setStartTime(d);
			hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d,
					day_qixian)));
			hb.setSource(4);
			hb.setSourceString("好友认证通过");
			hb.setSourceUserId(user.getId());
			hb.setSourceBorrowId(null);
			hb.setUsedBorrowId(null);
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
	}

	public void updatePhone(User user) {
		this.userDao.updatePhone(user);
	}

	public void updatePassowrd(User user) {
		this.userDao.updatePassowrd(user);
	}

	public void deleteAccountBank(Integer id) {
		this.userDao.deleteAccountBank(id);
	}

	public List<Admin> getKefuAdminList() {
		return this.userDao.getKefuAdminList();
	}

	public Boolean isPassword(String username, String password,
			String passwordType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		User user = userDao.getUser(map);
		String random = user.getRandomNum() == null ? "" : user.getRandomNum();
		String pwd = "";
		if (passwordType.equals("0")) {// 登录密码
			pwd = user.getPassword();
		} else if (passwordType.equals("1")) {// 支付密码
			pwd = user.getPayPassword();
		}

		if (StringUtils.isEmpty(pwd) || StringUtils.isEmpty(password)) {
			return false;
		}
		// if(pwd.equals( MD5.getMD5Str(MD5.getMD5Str(password)+random))){
		System.out.println("===>>" + password);
		System.out.println("==pwd=>>" + pwd);
		System.out.println("==p=>>" + PWDUtil.encode(password, random));
		if (pwd.equals(PWDUtil.encode(password, random))) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void updateTenderAuto(User user) {

		User oldUser = userDao.getById(user.getId(), user);
		user.setAutoTenderDate(null);
		boolean dateFlg = false;
		if (user.getAutoTenderStatus() != null
				&& !user.getAutoTenderStatus().equals(
						oldUser.getAutoTenderStatus())) {
			dateFlg = true;
		}
		// if (dateFlg == false &&
		// user.getAutoTenderLimitBegin()!=null&&!user.getAutoTenderLimitBegin().equals(oldUser.getAutoTenderLimitBegin()))
		// {
		// dateFlg = true;
		// }
		// if (dateFlg == false &&
		// user.getAutoTenderLimitEnd()!=null&&!user.getAutoTenderLimitEnd().equals(oldUser.getAutoTenderLimitEnd()))
		// {
		// dateFlg = true;
		// }
		// if (dateFlg == false &&
		// user.getAutoTenderRepayWay()!=null&&!user.getAutoTenderRepayWay().equals(oldUser.getAutoTenderRepayWay()))
		// {
		// dateFlg = true;
		// }
		if (dateFlg) {
			user.setAutoTenderDate(new Date());
		} else {
			user.setAutoTenderDate(null);
		}

		this.userDao.updateTenderAuto(user);
	}

	@Override
	public Integer queryTenderAutoRank(Integer id) {
		return userDao.queryTenderAutoRank(id);
	}

	public List<User> queryByRanking(Map<String, Object> map) {
		return userDao.queryByRanking(map);
	}

	@Override
	public Pager querySpreadListByMap(Pager pager, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userDao.querySpreadListByMap(pager, map);
	}

	/**
	 * 保存手机验证码
	 * 
	 */
	public void updatePhoneCode(User user) {
		userDao.updatePhoneCode(user);
	}

	public int authRealName(User user) {

		// 验证身份证是否重复
		Map<String, Object> qMap = new HashMap<String, Object>();
		qMap.put("cardId", user.getCardId());
		qMap.put("realStatus", 1);
		User qUser = userDao.getUser(qMap);
		if (qUser != null) {
			return 2;
		}
		// 验证身份证是否重复
		qMap = new HashMap<String, Object>();
		qMap.put("cardId", user.getCardId());
		qMap.put("realStatus", 2);
		qUser = userDao.getUser(qMap);
		if (qUser != null) {
			return 2;
		}

		LogIdentity l = new LogIdentity();
		String param = user.getRealName() + "," + user.getCardId();
		String ret = QueryCard.wstester(param);
		l.setReturnXml(ret);
		InputSource source = new InputSource(new StringReader(ret));
		SAXReader reader = new SAXReader();
		Document document = null;
		Boolean flag = false;
		try {
			document = reader.read(source);
			Map<String, String> map = QueryCard.getParseMapFromXMLStream(
					document, "/data/message");

			l.setMsgStatus(map.get("status"));
			l.setMsgValue(map.get("value"));
			l.setMsgQuerySeq(map.get("querySeq"));

			if ("0".equals(map.get("status"))) {
				map = QueryCard.getParseMapFromXMLStream(document,
						"/data/policeCheckInfos/policeCheckInfo/message");
				l.setPoliceStatus(map.get("status"));
				l.setPoliceValue(map.get("value"));

				if ("0".equals(map.get("status"))) {
					map = QueryCard.getParseMapFromXMLStream(document,
							"/data/policeCheckInfos/policeCheckInfo");
					l.setName(map.get("name"));
					l.setIdentitycard(map.get("identitycard"));
					l.setCompStatus(map.get("compStatus"));
					l.setCompResult(map.get("compResult"));
					l.setRegion(map.get("region"));
					l.setBirthday(map.get("birthday"));
					l.setSex(map.get("sex"));
					if ("3".equals(map.get("compStatus"))
							&& "一致".equals(map.get("compResult"))) {
						user.setSceneStatus(11);
						String region = map.get("region");
						String birthday = map.get("birthday");

						user.setAreaStore(region);
						user.setBirthday(CommonUtil.getString2Date(birthday,
								"yyyyMMdd"));
						user.setSex("男".equals(map.get("sex")) ? "1" : "2");
						user.setRealStatus(1);
						this.userDao.updateRealName(user);
						this.userDao.updatePassowrd(user);// 同时设置安全密码
						chkeakInformation(user, "");
						flag = true;
					} else {

						Integer scene = user.getSceneStatus();
						if (scene == null)
							scene = 0;
						scene++;
						user.setSceneStatus(scene);
						this.userDao.updateRealName(user);
					}
				}
			} else {

				Integer scene = user.getSceneStatus();
				if (scene == null)
					scene = 0;
				scene++;
				user.setSceneStatus(scene);
				this.userDao.updateRealName(user);
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}

		logIdentityDao.baseInsert(l);

		return flag ? 0 : 1;// 1 返回不通过
	}

	/**
	 * 新版-我的好友
	 * 
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager queryMyFriendsListByMap(Pager pager, Map<String, Object> map) {
		return userDao.queryMyFriendsListByMap(pager, map);
	}

	@Override
	public void updateToken(User user) {
		userDao.updateToken(user);
	}

	@Override
	public User getByToken(String token) {
		return userDao.getByToken(token);
	}

	@Override
	public void logout(String token) {
		// 删除数据库的token
		if (StringUtils.isNotBlank(token)) {
			User user = userDao.getByToken(token);
			if (user != null) {
				userDao.setNullToken(user.getId());
			}
		}

		// 删除redis里的对象
		CacheUtil.delObjValue("user:"+token);
	}

	@Override
	public void saveToken(User loginUser) {
		
		// 删除redis里原先的token
		if(StringUtils.isNotBlank(loginUser.getToken())){
			CacheUtil.delObjValue("user:"+loginUser.getToken());
		}
		
		// 保存token到数据库里
		Date date = new Date();
		loginUser.setLastTime(date);
		String token = TokenUtil.getToken(loginUser.getId().toString(), date);
		loginUser.setToken(token);
		userDao.updateToken(loginUser);
		
		// 保存token到redis		
		CacheUtil.setObjValue("user:"+token, loginUser);
		System.out.println("1====user:"+token);
		// 过期时间
		CacheUtil.setExpire("user:"+token, Integer.parseInt(ConfigUtil.getConfigUtil().get(ConfigUtil.QMD_REDIS_TOKEN_EXPIRE)));
	}
	
}
