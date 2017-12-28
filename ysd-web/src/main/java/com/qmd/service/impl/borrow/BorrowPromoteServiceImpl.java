package com.qmd.service.impl.borrow;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmd.dao.borrow.BorrowDaoService;
import com.qmd.dao.borrow.BorrowTenderDaoService;
import com.qmd.dao.user.*;
import com.qmd.dao.util.ListingDao;
import com.qmd.mode.borrow.Borrow;
import com.qmd.mode.borrow.BorrowTender;
import com.qmd.mode.user.*;
import com.qmd.mode.util.Hongbao;
import com.qmd.service.borrow.BorrowPromoteService;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.util.*;
import com.qmd.util.bean.HongbaoDetail;
import com.qmd.util.bean.RepaymentInfo;
import com.ysd.util.NewPayUtil;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BorrowServiceImpl 标方法的Service实现类
 */
@Repository("borrowPromoteService")
public class BorrowPromoteServiceImpl extends BaseServiceImpl<BorrowTender,Integer> implements BorrowPromoteService {
	Logger log = Logger.getLogger(BorrowPromoteServiceImpl.class);
	
	private Logger logPledge = Logger.getLogger("userPledgeLog");
	
	@Resource
	BorrowDaoService borrowDaoService;
	@Resource
	UserAccountDetailDao userAccountDetailDao;
	@Resource
	UserAccountDao userAccountDao;
	@Resource
	private ListingDao listingDao;
	@Resource
	BorrowTenderDaoService borrowTenderDaoService;
	@Resource
	UserDao userDao;
	@Resource
	UserAwardDetailDao userAwardDetailDao;
	
	@Resource
	UserHongbaoDao userHongbaoDao;
	
	//月标
	@Override
	public synchronized int borrowInvestDo(User user,BorrowTender borrowTender,String ip,Integer[] hongbaoId) {
		
		logPledge.debug("【天标开始】"+user.getUsername()+",["+borrowTender.getBorrowId()+"] 可用["+borrowTender.getAbleAmount()+"] 续投["+borrowTender.getContinueAmount()+"]");
		long st = CommonUtil.getDateTimeLong();
		
		
		UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(user.getId());
		userAccount = this.userAccountDao.getForUpdate(userAccount.getId(), userAccount); //账户锁定
		Borrow borrow = this.borrowDaoService.getBorrowById(borrowTender.getBorrowId());
		borrow = this.borrowDaoService.getForUpdate(borrow.getId());//标锁定

		if("16".equals(borrow.getType())){
			
			Map<String, Object> tMap = new HashMap<String, Object>();
			tMap.put("userId", user.getId());
			tMap.put("backStatus", 0);
			tMap.put("noBorrowType", "17");
			List<BorrowTender> btList = borrowTenderDaoService.getTenderDetailByUserid(tMap);
			if(btList.size() > 0){
				return 5;
			}
		}
		
		if (borrow.getStatus() != 1) {
			logPledge.debug("【月标结束】"+user.getUsername()+"投标失败,标状态不对["+borrow.getStatus()+"]");
			return 4;// 标已满
		}
		
		if (Double.parseDouble(borrow.getBalance()) <=0) {
			logPledge.debug("【月标结束】"+user.getUsername()+"投标失败,标已满["+borrow.getBalance()+"]");
			return 1;// 标已满
		}
		
		// 账户可用金额少于投标金额
		if (userAccount.getAbleMoney().doubleValue() < Double.parseDouble(borrowTender.getAbleAmount())) {
			logPledge.debug("【月标结束】"+user.getUsername()+"投标失败,余额不足["+userAccount.getAbleMoney()+"]");
			return 2;// 余额不足
		}
		
		// 续投  无用
		if (userAccount.getContinueTotal().doubleValue() < Double.parseDouble(borrowTender.getContinueAmount())) {
			logPledge.debug("【月标结束】"+user.getUsername()+"投标失败,续投余额不足["+userAccount.getContinueTotal()+"]");
			return 3;// 余额不足
		}
		

		BigDecimal useHongbao = BigDecimal.ZERO;
		String money;//标剩余金额与投标金额比
		if(Double.parseDouble(borrowTender.getMoney()) > Double.parseDouble(borrow.getBalance())){
			money = borrow.getBalance();
		}else{
			money = borrowTender.getMoney();
		}
			if(hongbaoId != null &&  borrow.getAwardScale() != null && borrow.getAwardScale().compareTo(BigDecimal.ZERO) > 0){
			Map<String, Object> hbMap = new HashMap<String, Object>();
			hbMap.put("idArray", hongbaoId);
			useHongbao = userHongbaoDao.getSelectSumMoney(hbMap) ;
			BigDecimal maxHongbao = new BigDecimal( money).multiply(borrow.getAwardScale()).divide(new BigDecimal(100));
			
			if(useHongbao.compareTo(maxHongbao) > 0){
				return 22;
			}
			for(Integer hbId :hongbaoId){
				if(hbId != null && hbId > 0){
					UserHongbao hb = userHongbaoDao.getById(hbId, new UserHongbao());
					if(hb.getStatus().compareTo(0) != 0){
						return 23;//红包已使用或已过期
					}
					if(hb.getUserId().compareTo(user.getId()) != 0){
						return 21;//红包不属于投资人
					}
					hb.setUsedMoney(hb.getMoney());
					hb.setUsedBorrowId(borrowTender.getBorrowId());
					hb.setStatus(1);
					userHongbaoDao.update(hb);
					
					UserAwardDetail uad = new UserAwardDetail();
					uad.setUserId(user.getId());// 用户ID
					uad.setType("use_hongbao");// 类型（同资金记录）
					uad.setMoney(hb.getMoney());// 操作金额
					uad.setAwardMoney(userAccount.getAwardMoney());// 奖励账户
					uad.setSignFlg(-1);
					uad.setRemark("投资"+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+"使用红包");// 备注
					uad.setRelateKey("hongbao_id");
					uad.setReserve1(hb.getHbNo());//红包编号
					userAwardDetailDao.save(uad);
					
				}
			}
		}
		borrowTender.setHongbaoAmount(useHongbao.toString());//使用红包金额
		
		//投标金额大于标剩余金额
		if(Double.parseDouble(borrowTender.getMoney()) > Double.parseDouble(borrow.getBalance())){
			//borrowTender.setMoney(borrow.getBalance());
			borrowTender.setAccount(borrow.getBalance());
			borrowTender.setStatus("2");
			
			//>>>部分投资，计算扣除续投金额，可用金额
			//1,续投金额 大于等于 投资金额  
			if (Double.parseDouble(borrowTender.getContinueAmount()) >= Double.parseDouble(borrowTender.getAccount())) {
				borrowTender.setContinueAmount(borrowTender.getAccount());// 实际投标金额
				borrowTender.setAbleAmount("0");//不适用可用金额
			} else {
				//2,续投金额 小于 投资金额；续投全额，可用金额需变更
//				double ableFact = Double.valueOf(borrowTender.getAccount()) - Double.valueOf(borrowTender.getContinueAmount());
//				borrowTender.setAbleAmount(String.valueOf(ableFact));
				borrowTender.setAbleAmount(CommonUtil.setPriceScale(new BigDecimal(borrowTender.getAccount()).subtract(useHongbao)).toString());
			}
			
		}else{
			borrowTender.setAccount(borrowTender.getMoney());
			borrowTender.setStatus("1");
			borrowTender.setAbleAmount(CommonUtil.setPriceScale(new BigDecimal(borrowTender.getAccount()).subtract(useHongbao)).toString());
		}
		
		RepaymentInfo repaymentInfo = PromoteUtil.promotePlan(Integer.parseInt(borrow
				.getIsday()), Integer.parseInt(borrow.getStyle()),
				Integer.parseInt(borrow.getTimeLimit()), borrow
						.getDivides(),
				new BigDecimal(borrowTender.getAccount()), new BigDecimal(
						borrow.getApr()));
		
		String interest = repaymentInfo.getInterest().toString();
		borrowTender.setInterest(interest);
		borrowTender.setRepaymentAccount(String.valueOf(Double.valueOf(borrowTender.getAccount()) + Double.valueOf(interest)));
		borrowTender.setRepaymentYesaccount("0");
		borrowTender.setWaitAccount(borrowTender.getAccount());
		borrowTender.setRepaymentYesinterest("0");
		borrowTender.setWaitInterest(interest);
		borrowTender.setUserId(user.getId());
		borrowTender.setAddPersion(user.getUsername());
		borrowTender.setOperatorIp(ip);
		this.borrowTenderDaoService.save(borrowTender);
		
		// 续投资金修改及记录
		if (Double.valueOf(borrowTender.getContinueAmount()) > 0) {
			//修改用户续投余额
			userAccount.setContinueTotal(BigDecimal.valueOf(userAccount.getContinueTotal().doubleValue() - Double.parseDouble(borrowTender.getContinueAmount())));
			//修改用户冻结金额
			userAccount.setUnableMoney(BigDecimal.valueOf(userAccount.getUnableMoney().doubleValue() + Double.parseDouble(borrowTender.getContinueAmount())));
			this.userAccountDao.update(userAccount);
			
			//资金记录
			UserAccountDetail userAccountDetail = AccountDetailUtil.fillUserAccountDetail("tender_continued",
					BigDecimal.valueOf(Double.valueOf(borrowTender.getContinueAmount())),
					10000, "续投冻结资金["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+"]", ip, userAccount);
			this.userAccountDetailDao.save(userAccountDetail);
		}
		
		// 可用资金修改及记录
		if (Double.valueOf(borrowTender.getAbleAmount()) > 0) {
			BigDecimal tzMoney = new BigDecimal( borrowTender.getAbleAmount());
			
			//修改用户可用金额
			userAccount.setAbleMoney(userAccount.getAbleMoney().subtract(tzMoney));
			//修改用户冻结金额
			userAccount.setUnableMoney(userAccount.getUnableMoney().add(tzMoney));
			//修改奖励红包可用
			userAccount.setAwardMoney(userAccount.getAwardMoney().subtract(useHongbao));
			this.userAccountDao.update(userAccount);
			
			//资金记录
			UserAccountDetail userAccountDetail = AccountDetailUtil.fillUserAccountDetail("tender",
					tzMoney,
					10000, "投标冻结资金["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+"]", ip, userAccount);
			this.userAccountDetailDao.save(userAccountDetail);
			
		}
		
		//修改标的数据 
		DecimalFormat df = new DecimalFormat("#");
		borrow.setBalance(df.format(Double.valueOf(borrow.getBalance()) - Double.valueOf(borrowTender.getAccount())));
		NumberFormat nf =NumberFormat.getInstance();
		nf.setMaximumFractionDigits(0);
		//nf.setMaximumFractionDigits(2);
		String shc = nf.format((1 - Double.valueOf(borrow.getBalance())/Double.valueOf(borrow.getAccount()))*100);
		//borrow.setSchedule(String.valueOf(1-(Double.valueOf(borrow.getBalance())/Double.valueOf(borrow.getAccount()))));
		if(shc.equals("100")&& Double.valueOf(borrow.getBalance()) != 0){
			borrow.setSchedule("99");
		}else{
			borrow.setSchedule(shc);
		}
		if(Double.valueOf(borrow.getBalance()) == 0){
			borrow.setStatus(5);
		}
		if(borrow.getTenderTimes()==null||"".equals(borrow.getTenderTimes())) {
			borrow.setTenderTimes("0");
		}
		borrow.setTenderTimes(String.valueOf(Integer.parseInt(borrow.getTenderTimes()) + 1));
		this.borrowDaoService.update(borrow);
		
		long ed = CommonUtil.getDateTimeLong();
		logPledge.debug("   投标金额["+borrowTender.getMoney()+"] 续投["+borrowTender.getContinueAmount()+"] 可用["+borrowTender.getAbleAmount()+"] 标余额["+borrow.getBalance()+"]");
		logPledge.debug("【月标结束】"+user.getUsername()+" 时间["+(ed-st)+"]");
		
		//首次投资 奖励红包
		Map<String,Object> detailmap = new HashMap<String,Object>();
		detailmap.put("userId", user.getId());
		List<BorrowTender> borrowTenderList=  borrowTenderDaoService.getTenderDetailByUserid(detailmap);
		if( borrowTenderList != null && borrowTenderList.size() == 1){
			Hongbao hbao = listingDao.getHongbao(2);
			if(hbao.getIsEnabled().compareTo(1) ==0 && hbao.getTotal().compareTo(BigDecimal.ZERO) > 0){
//				Integer day_qixian = Integer.parseInt( listingDao.getKeyValue("tuijian_award2"));//红包有效期
//				hongBaoFirstTenderJiLu(hb, user, day_qixian);
				List<HongbaoDetail> hbDetailList = (List<HongbaoDetail>) new Gson().fromJson(hbao.getHongbaoDetail(),new TypeToken<List<HongbaoDetail>>() {}.getType());
				for (HongbaoDetail hbdetail : hbDetailList) {
				BigDecimal award0 = hbdetail.getMoney();
				UserAccount userAccount0 = userAccountDao.getUserAccountByUserId(user.getId());
				userAccount0.setAwardMoney(userAccount0.getAwardMoney().add(award0));
				userAccountDao.update(userAccount0);

				//添加红包记录
				UserHongbao hb = new UserHongbao();
				hb.setUserId(user.getId());// 用户ID
				hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")+CommonUtil.getRandomString(5));
				hb.setName("首次投资");
				hb.setMoney(award0);
				hb.setUsedMoney(BigDecimal.ZERO);
				hb.setStatus(0);
				Date d = new Date();
				hb.setStartTime(d);
				hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d,hbdetail.getExpDate() )));
				hb.setSource(3);
				hb.setSourceString("首次投资");
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
				uad.setType("award_toubiao_first");// 类型（同资金记录）
				uad.setMoney(award0);// 操作金额
				uad.setSignFlg(1);
				uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
				uad.setRemark("首次投资");// 备注
				uad.setReserve1(hb.getHbNo());
				uad.setRelateKey("hongbao_id");
				userAwardDetailDao.save(uad);
				}
			}
		}
		
		return 0;
	}

/**
	public void hongBaoFirstTenderJiLu(Hongbao hongbao ,User user,Integer day_qixian){
		for(int i=0;i<hongbao.getTenth();i++){
			BigDecimal award0 = new BigDecimal(10);
			UserAccount userAccount0 = userAccountDao.getUserAccountByUserId(user.getTgOneLevelUserId());
			userAccount0.setAwardMoney(userAccount0.getAwardMoney().add(award0));
			userAccountDao.update(userAccount0);

			//添加红包记录
			UserHongbao hb = new UserHongbao();
			hb.setUserId(user.getTgOneLevelUserId());// 用户ID
			hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")+CommonUtil.getRandomString(5));
			hb.setName("首次投资");
			hb.setMoney(award0);
			hb.setUsedMoney(BigDecimal.ZERO);
			hb.setStatus(0);
			Date d = new Date();
			hb.setStartTime(d);
			hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d, day_qixian)));
			hb.setSource(3);
			hb.setSourceString("首次投资");
			hb.setSourceUserId(user.getId());
			hb.setSourceBorrowId(null);
			hb.setUsedBorrowId(null);
			userHongbaoDao.save(hb);
			// 奖励账户资金记录
			UserAwardDetail uad = new UserAwardDetail();
			uad.setUserId(user.getTgOneLevelUserId());// 用户ID
			uad.setType("award_toubiao_first");// 类型（同资金记录）
			uad.setMoney(award0);// 操作金额
			uad.setSignFlg(1);
			uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
			uad.setRemark("首次投资");// 备注
			uad.setReserve1(hb.getHbNo());
			uad.setRelateKey("hongbao_id");
			userAwardDetailDao.save(uad);
		}
		
		for(int i=0;i<hongbao.getTwentieth();i++){
			BigDecimal award0 = new BigDecimal(20);
			UserAccount userAccount0 = userAccountDao.getUserAccountByUserId(user.getTgOneLevelUserId());
			userAccount0.setAwardMoney(userAccount0.getAwardMoney().add(award0));
			userAccountDao.update(userAccount0);

			//添加红包记录
			UserHongbao hb = new UserHongbao();
			hb.setUserId(user.getTgOneLevelUserId());// 用户ID
			hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")+CommonUtil.getRandomString(5));
			hb.setName("首次投资");
			hb.setMoney(award0);
			hb.setUsedMoney(BigDecimal.ZERO);
			hb.setStatus(0);
			Date d = new Date();
			hb.setStartTime(d);
			hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d, day_qixian)));
			hb.setSource(3);
			hb.setSourceString("首次投资");
			hb.setSourceUserId(user.getId());
			hb.setSourceBorrowId(null);
			hb.setUsedBorrowId(null);
			userHongbaoDao.save(hb);
			
			// 奖励账户资金记录
			UserAwardDetail uad = new UserAwardDetail();
			uad.setUserId(user.getTgOneLevelUserId());// 用户ID
			uad.setType("award_toubiao_first");// 类型（同资金记录）
			uad.setMoney(award0);// 操作金额
			uad.setSignFlg(1);
			uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
			uad.setRemark("首次投资");// 备注
			uad.setReserve1(hb.getHbNo());
			uad.setRelateKey("hongbao_id");
			userAwardDetailDao.save(uad);
		}

		for(int i=0;i<hongbao.getFiftieth();i++){
			BigDecimal award0 = new BigDecimal(50);
			UserAccount userAccount0 = userAccountDao.getUserAccountByUserId(user.getTgOneLevelUserId());
			userAccount0.setAwardMoney(userAccount0.getAwardMoney().add(award0));
			userAccountDao.update(userAccount0);

			//添加红包记录
			UserHongbao hb = new UserHongbao();
			hb.setUserId(user.getTgOneLevelUserId());// 用户ID
			hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")+CommonUtil.getRandomString(5));
			hb.setName("首次投资");
			hb.setMoney(award0);
			hb.setUsedMoney(BigDecimal.ZERO);
			hb.setStatus(0);
			Date d = new Date();
			hb.setStartTime(d);
			hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d, day_qixian)));
			hb.setSource(3);
			hb.setSourceString("首次投资");
			hb.setSourceUserId(user.getId());
			hb.setSourceBorrowId(null);
			hb.setUsedBorrowId(null);
			userHongbaoDao.save(hb);
			
			// 奖励账户资金记录
			UserAwardDetail uad = new UserAwardDetail();
			uad.setUserId(user.getTgOneLevelUserId());// 用户ID
			uad.setType("award_toubiao_first");// 类型（同资金记录）
			uad.setMoney(award0);// 操作金额
			uad.setSignFlg(1);
			uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
			uad.setRemark("首次投资");// 备注
			uad.setReserve1(hb.getHbNo());
			uad.setRelateKey("hongbao_id");
			userAwardDetailDao.save(uad);
		}
	}
	**/
	/**
	 * qxw
	 * 投标计算
	 */
	public synchronized int borrowInvestDoHNew(User user,BorrowTender borrowTender,String ip,Integer[] hongbaoId) {
		
		logPledge.debug("【月标开始】"+user.getUsername()+",["+borrowTender.getBorrowId()+"] 可用["+borrowTender.getAbleAmount()+"] 续投["+borrowTender.getContinueAmount()+"]");
		long st = CommonUtil.getDateTimeLong();
		
		UserAccount userAccount = this.userAccountDao.getUserAccountByUserId(user.getId());
		userAccount = this.userAccountDao.getForUpdate(userAccount.getId(), userAccount); //账户锁定
		Borrow borrow = this.borrowDaoService.getBorrowById(borrowTender.getBorrowId());
		borrow = this.borrowDaoService.getForUpdate(borrow.getId());//标锁定
		

		if("16".equals(borrow.getType())){
			
			Map<String, Object> tMap = new HashMap<String, Object>();
			tMap.put("userId", user.getId());
			tMap.put("backStatus", 0);
			tMap.put("noBorrowType", "17");
			List<BorrowTender> btList = borrowTenderDaoService.getTenderDetailByUserid(tMap);
			if(user.getId()>=2777&user.getId()<=2788){
				
			}else if(btList.size() > 0){
				return 5;
			}
		}
		
		if (borrow.getStatus() != 1) {
			logPledge.debug("【月标结束】"+user.getUsername()+"投标失败,标状态不对["+borrow.getStatus()+"]");
			return 4;// 标已满
		}
		
		if (Double.parseDouble(borrow.getBalance()) <=0) {
			logPledge.debug("【月标结束】"+user.getUsername()+"投标失败,标已满["+borrow.getBalance()+"]");
			return 1;// 标已满
		}
		
	
		
		// 续投  无用
		if (userAccount.getContinueTotal().doubleValue() < Double.parseDouble(borrowTender.getContinueAmount())) {
			logPledge.debug("【月标结束】"+user.getUsername()+"投标失败,续投余额不足["+userAccount.getContinueTotal()+"]");
			return 3;// 余额不足
		}
		
		BigDecimal useHongbao = BigDecimal.ZERO;
		String money ;
		if(Double.parseDouble(borrowTender.getMoney()) > Double.parseDouble(borrow.getBalance())){
			money = borrow.getBalance();
		}else{
			money = borrowTender.getMoney();
		}
			if(hongbaoId != null ){
			Map<String, Object> hbMap = new HashMap<String, Object>();
			hbMap.put("idArray", hongbaoId);
			
			BigDecimal maxSumHbInvestFullMomey=userHongbaoDao.getSumHbInvestFullMomey(hbMap);//满的上限,
			useHongbao= userHongbaoDao.getSelectSumMoney(hbMap) ;//用户选中红包的金额
		
			BigDecimal maxHongbao = new BigDecimal( money);
			
			System.out.println("======================="+ useHongbao);
			
			if(maxSumHbInvestFullMomey.compareTo(maxHongbao) > 0){//查询出可用的红包满的上限比 投资满金额高
				return 22;
			}
			for(Integer hbId :hongbaoId){
				if(hbId != null && hbId > 0){
					net.qmdboss.beans.UserHongbao  hb = userHongbaoDao.getNewHbById(hbId);
					if(hb==null){
						return 21;//红包不存在
					}
					if(hb.getStatus().compareTo(0) != 0){
						return 23;//红包已使用或已过期
					}
					if(hb.getUserId().compareTo(user.getId()) != 0){
						return 21;//红包不属于投资人
					}
				}
			}
			logPledge.debug("【使用红包总额】"+useHongbao.toString()+";【红包ID】"+StringUtils.join(hongbaoId,",")+"");
			borrowTender.setHongbaoAmount(useHongbao.toString());//使用红包金额
		}
		logPledge.debug("【投标金额】"+borrowTender.getMoney()+";【剩余金额】"+borrow.getBalance()+"");
		
		// 账户可用金额少于投标金额
		if (userAccount.getAbleMoney().doubleValue()+Double.valueOf(useHongbao.toString()) < Double.parseDouble(borrowTender.getAbleAmount())) {
			logPledge.debug("【月标结束】"+user.getUsername()+"投标失败,余额不足["+userAccount.getAbleMoney()+"]");
			return 2;// 余额不足
		}
		
		//投标金额大于标剩余金额
		if(Double.parseDouble(borrowTender.getMoney()) > Double.parseDouble(borrow.getBalance())){
// zdl 添加
			return 122;//可投金额不足
// zdl 注释掉的			
//			//borrowTender.setMoney(borrow.getBalance());
//			borrowTender.setAccount(borrow.getBalance());
//			borrowTender.setStatus("2");
//			
//			borrowTender.setAbleAmount(CommonUtil.setPriceScale(new BigDecimal(borrowTender.getAccount()).subtract(useHongbao)).toString());
//
//			System.out.println("+++++++++++++="+borrowTender.getAccount()+"----------------------"+borrowTender.getAbleAmount());
			
		}else{
			borrowTender.setAccount(borrowTender.getMoney());
			borrowTender.setStatus("1");
			borrowTender.setAbleAmount(CommonUtil.setPriceScale(new BigDecimal(borrowTender.getAccount()).subtract(useHongbao)).toString());
		}
		logPledge.debug("【投标金额】"+borrowTender.getMoney()+";【去除红包后，可用扣除】"+borrowTender.getAbleAmount()+"");
		//修改实际投标金额`

		RepaymentInfo repaymentInfo = PromoteUtil.promotePlan(Integer.parseInt(borrow
				.getIsday()), Integer.parseInt(borrow.getStyle()),
				Integer.parseInt(borrow.getTimeLimit()), borrow
						.getDivides(),
				new BigDecimal(borrowTender.getAccount()), new BigDecimal(
						borrow.getApr()));
		logPledge.debug("【标分期JSON】"+JsonUtil.toJson(repaymentInfo));
		
		String interest = repaymentInfo.getInterest().toString();
		logPledge.debug("interest="+interest);
		borrowTender.setInterest(interest);
		borrowTender.setRepaymentAccount(String.valueOf(Double.valueOf(borrowTender.getAccount()) + Double.valueOf(interest)));
		logPledge.debug("repaymentAccount="+borrowTender.getRepaymentAccount());
		borrowTender.setRepaymentYesaccount("0");
		borrowTender.setWaitAccount(borrowTender.getAccount());
		borrowTender.setRepaymentYesinterest("0");
		borrowTender.setWaitInterest(interest);
		borrowTender.setUserId(user.getId());
		borrowTender.setAddPersion(user.getUsername());
		borrowTender.setOperatorIp(ip);
		
		logPledge.debug("开始添加投标数据");
		this.borrowTenderDaoService.save(borrowTender);
		logPledge.debug("结束添加投标数据");
		
		if(hongbaoId != null){
			logPledge.debug("开始改变红包状态");
			for(Integer hbId :hongbaoId){
				if(hbId != null && hbId > 0){
					//UserHongbao hb = userHongbaoDao.getById(hbId, new UserHongbao());
					net.qmdboss.beans.UserHongbao  hb = userHongbaoDao.getNewHbById(hbId);
					hb.setUsedMoney(hb.getMoney());
					hb.setUsedBorrowId(borrowTender.getBorrowId());
					hb.setStatus(1);
					userHongbaoDao.updateNewHongbao(hb);
					
					UserAwardDetail uad = new UserAwardDetail();
					uad.setUserId(user.getId());// 用户ID
					uad.setType("use_hongbao");// 类型（同资金记录）
					uad.setMoney(hb.getMoney());// 操作金额
					uad.setAwardMoney(userAccount.getAwardMoney());// 奖励账户
					uad.setSignFlg(-1);
					uad.setRemark("投资"+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+"使用红包");// 备注
					uad.setRelateKey("hongbao_id");
					uad.setReserve1(hb.getHbNo());//红包编号
					userAwardDetailDao.save(uad);
				}
			}
			logPledge.debug("结束改变红包状态");
		}
		// 续投资金修改及记录
		if (Double.valueOf(borrowTender.getContinueAmount()) > 0) {
			//修改用户续投余额
			userAccount.setContinueTotal(BigDecimal.valueOf(userAccount.getContinueTotal().doubleValue() - Double.parseDouble(borrowTender.getContinueAmount())));
			//修改用户冻结金额
			userAccount.setUnableMoney(BigDecimal.valueOf(userAccount.getUnableMoney().doubleValue() + Double.parseDouble(borrowTender.getContinueAmount())));
			this.userAccountDao.update(userAccount);
			
			//资金记录
			UserAccountDetail userAccountDetail = AccountDetailUtil.fillUserAccountDetail("tender_continued",
					BigDecimal.valueOf(Double.valueOf(borrowTender.getContinueAmount())),
					10000, "续投冻结资金["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+"]", ip, userAccount);
			this.userAccountDetailDao.save(userAccountDetail);
		}
		
		// 可用资金修改及记录
		if (Double.valueOf(borrowTender.getAbleAmount()) > 0) {
			BigDecimal tzMoney = new BigDecimal( borrowTender.getAbleAmount());
			
			//修改用户可用金额
			userAccount.setAbleMoney(userAccount.getAbleMoney().subtract(tzMoney));
			//修改用户冻结金额
			userAccount.setUnableMoney(userAccount.getUnableMoney().add(tzMoney));
			//修改奖励红包可用
			userAccount.setAwardMoney(userAccount.getAwardMoney().subtract(useHongbao));
			this.userAccountDao.update(userAccount);
			
			//资金记录
			UserAccountDetail userAccountDetail = AccountDetailUtil.fillUserAccountDetail("tender",
					tzMoney,
					10000, "投标冻结资金["+CommonUtil.fillBorrowUrl(borrow.getId(), borrow.getName())+"]", ip, userAccount);
			this.userAccountDetailDao.save(userAccountDetail);
			
		}
		
		//修改标的数据 
		DecimalFormat df = new DecimalFormat("#");
		borrow.setBalance(df.format(Double.valueOf(borrow.getBalance()) - Double.valueOf(borrowTender.getAccount())));
		NumberFormat nf =NumberFormat.getInstance();
		nf.setMaximumFractionDigits(0);
		//nf.setMaximumFractionDigits(2);
		String shc = nf.format((1 - Double.valueOf(borrow.getBalance())/Double.valueOf(borrow.getAccount()))*100);
		//borrow.setSchedule(String.valueOf(1-(Double.valueOf(borrow.getBalance())/Double.valueOf(borrow.getAccount()))));
		if(shc.equals("100")&& Double.valueOf(borrow.getBalance()) != 0){
			borrow.setSchedule("99");
		}else{
			borrow.setSchedule(shc);
		}
		if(Double.valueOf(borrow.getBalance()) == 0){
			borrow.setStatus(5);
		}
		if(borrow.getTenderTimes()==null||"".equals(borrow.getTenderTimes())) {
			borrow.setTenderTimes("0");
		}
		borrow.setTenderTimes(String.valueOf(Integer.parseInt(borrow.getTenderTimes()) + 1));
		this.borrowDaoService.update(borrow);
		
		long ed = CommonUtil.getDateTimeLong();
		logPledge.debug("   投标金额["+borrowTender.getMoney()+"] 续投["+borrowTender.getContinueAmount()+"] 可用["+borrowTender.getAbleAmount()+"] 标余额["+borrow.getBalance()+"]");
		logPledge.debug("【月标结束】"+user.getUsername()+" 时间["+(ed-st)+"]");
		
	
		
		//首次投资 奖励红包
		Map<String,Object> detailmap = new HashMap<String,Object>();
		detailmap.put("userId", user.getId());
			List<BorrowTender> borrowTenderList=  borrowTenderDaoService.getTenderDetailByUserid(detailmap);
			if( borrowTenderList != null && borrowTenderList.size() == 1){
				Hongbao hbao = listingDao.getHongbao(2);
				if(hbao.getIsEnabled().compareTo(1) ==0 && hbao.getTotal().compareTo(BigDecimal.ZERO) > 0){
					List<HongbaoDetail> hbDetailList = (List<HongbaoDetail>) new Gson().fromJson(hbao.getHongbaoDetail(),new TypeToken<List<HongbaoDetail>>() {}.getType());
					for (HongbaoDetail hbdetail : hbDetailList) {
					BigDecimal award0 = hbdetail.getMoney();
					UserAccount userAccount0 = userAccountDao.getUserAccountByUserId(user.getId());
					userAccount0.setAwardMoney(userAccount0.getAwardMoney().add(award0));
					userAccountDao.update(userAccount0);

					//添加红包记录
					UserHongbao hb = new UserHongbao();
					hb.setUserId(user.getId());// 用户ID
					hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")+CommonUtil.getRandomString(5));
					hb.setName("首次投资");
					hb.setMoney(award0);
					hb.setUsedMoney(BigDecimal.ZERO);
					hb.setStatus(0);
					Date d = new Date();
					hb.setStartTime(d);
					hb.setEndTime(CommonUtil.date2end(CommonUtil.getDateAfter(d,hbdetail.getExpDate() )));
					hb.setSource(3);
					hb.setSourceString("首次投资");
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
					uad.setType("award_toubiao_first");// 类型（同资金记录）
					uad.setMoney(award0);// 操作金额
					uad.setSignFlg(1);
					uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
					uad.setRemark("首次投资");// 备注
					uad.setReserve1(hb.getHbNo());
					uad.setRelateKey("hongbao_id");
					userAwardDetailDao.save(uad);
					}
				}
			}
		return 0;
	}
}
