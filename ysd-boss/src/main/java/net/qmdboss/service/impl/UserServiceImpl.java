package net.qmdboss.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.qmdboss.DTO.UserSpreadInviteAwardDTO;
import net.qmdboss.bean.HongbaoDetail;
import net.qmdboss.bean.Pager;
import net.qmdboss.bean.UserListBean;
import net.qmdboss.dao.*;
import net.qmdboss.entity.*;
import net.qmdboss.service.UserService;
import net.qmdboss.util.CommonUtil;
import net.qmdboss.util.ConstantUtil;
import net.qmdboss.util.MD5;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;



@Service("userServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements UserService{

	@Resource(name="userDaoImpl")
	private UserDao userDao;
	@Resource(name="listingDaoImpl")
	private ListingDao listingDao;
	@Resource(name = "userAccountDaoImpl")
	private UserAccountDao userAccountDao;
	@Resource(name = "userAccountDetailDaoImpl")
	private UserAccountDetailDao userAccountDetailDao;
	@Resource(name = "userAwardDetailDaoImpl")
	private UserAwardDetailDao userAwardDetailDao;
	@Resource(name = "userHongbaoDaoImpl")
	UserHongbaoDao userHongbaoDao;
	@Resource(name="agencyDaoImpl")
	private AgencyDao agencyDao;
	@Resource(name="hongbaoDaoImpl")
	private HongbaoDao hongbaoDao;
	
	@Resource(name = "userDaoImpl")
	public void setBaseDao(UserDao userDao) {
		super.setBaseDao(userDao);
	}
	
	@Transactional(readOnly = true)
	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}
	
	@Transactional(readOnly = true)
	public User getUserByEmail(String email,Integer id){
		return userDao.getUserByEmail(email,id);
	}
	
	@Transactional(readOnly = true)
	public User getUserByPhone(String phone,Integer id){
		return userDao.getUserByPhone(phone,id);
	}
	@Transactional(readOnly = true)
	public List<User> getByPhone(String phone){
		return userDao.getByPhone(phone);
	}
	
	@Override
	public Pager getUserPager(Integer verifyType, Pager pager) {
		return this.getUserPager(null, verifyType, pager);
	}
	
	
	/**
	 * 根据审核类型，获取用户分页对象
	 * @param verifyType
	 * @param pager
	 * @return
	 */
	public Pager getUserPager(Integer status,Integer verifyType , Pager pager){
		return userDao.getUserPager(status,verifyType, pager);
	}
	
	public void update(User user,Integer verifyType,HttpServletRequest request){
		if(verifyType==3){//扣 VIP 费用
			BigDecimal vipPrice = new BigDecimal(150);
			UserAccount userAccount = user.getAccount();
			userAccount.setTotal(userAccount.getTotal().subtract(vipPrice));
			userAccount.setAbleMoney(userAccount.getAbleMoney().subtract(vipPrice));			
			userAccountDao.update(userAccount);//修改总表记录
			
			UserAccountDetail userAccountDetail = new UserAccountDetail();
			userAccountDetail.setType("vip");
			userAccountDetail.setTotal(userAccount.getTotal());
			userAccountDetail.setMoney(vipPrice);
			userAccountDetail.setUseMoney(userAccount.getAbleMoney());
			userAccountDetail.setNoUseMoney(userAccount.getUnableMoney());
			userAccountDetail.setCollection(userAccount.getCollection());
			userAccountDetail.setToUser(0);
			userAccountDetail.setRemark("成功扣除VIP费用"+vipPrice);
			userAccountDetail.setAddTime(new Date());
			userAccountDetail.setOperatorIp(request.getLocalAddr());
			userAccountDetail.setUser(user);
			userAccountDetail.setInvestorCollectionCapital(userAccount.getInvestorCollectionCapital());
			userAccountDetail.setInvestorCollectionInterest(userAccount.getInvestorCollectionInterest());
			userAccountDetail.setBorrowerCollectionCapital(userAccount.getBorrowerCollectionCapital());
			userAccountDetail.setBorrowerCollectionInterest(userAccount.getBorrowerCollectionInterest());
			userAccountDetailDao.save(userAccountDetail);//添加资金流水记录
		}
		
		//-------------注册奖励加----------
		
		if(verifyType==1){// 实名认证
			//-----实名认证完成送  X 元红包
				if(user.getTypeId()!=null && user.getTypeId()==0)  {
					//--------给推荐者发放奖励
					chkeakInformation(user);
				}		
		}
		//-------------注册奖励加end----------
		
		userDao.update(user);
	}
	
	/****
	 * 
	 * 判断是否符合满足发放奖励条件
	 * 满足则发放奖励红包
	 * user:被推广人
	 * ****/
	public void chkeakInformation(User user){
		user=userDao.load(user.getId());
		if(user.getTgStatus()!=null&&user.getTgStatus()==0){//推广奖励没发
			if(user.getTgOneLevelUserId()!=null){//存在上级好友
					if(user.getRealStatus()!=null&&user.getRealStatus()==1){//实名已经通过
						
						Hongbao hbao = hongbaoDao.get(3);//好友认证红包奖励
						List<HongbaoDetail> hbDetailList =   (List<HongbaoDetail>) new Gson().fromJson(hbao.getHongbaoDetail(), new TypeToken<List<HongbaoDetail>>() {}.getType());
						
						User user_tg=userDao.load(user.getTgOneLevelUserId());
						for(HongbaoDetail hbdetail:hbDetailList){
							UserAccount userAccount0 = userAccountDao.loadLockTable(user_tg);
							userAccount0.setAwardMoney(userAccount0.getAwardMoney().add(hbdetail.getMoney()));//增加红包账户资金
							userAccountDao.update(userAccount0);
							UserHongbao hb = new UserHongbao();
							hb.setUser(user_tg);
							hb.setHbNo(CommonUtil.getDate2String(new Date(), "yyyyMMdd")+CommonUtil.getRandomString(5));
							hb.setName("好友认证通过");
							hb.setMoney(hbdetail.getMoney());
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
							
							// 奖励账户  资金记录
							UserAwardDetail uad = new UserAwardDetail();
							User u=userDao.load(user.getTgOneLevelUserId());
							uad.setUser(u);// 用户ID
							uad.setType(ConstantUtil.MONEY_LOG_NAME_EMAIL_TENDER_AWARD);// 类型（同资金记录）
							uad.setMoney(hbdetail.getMoney());// 操作金额
							uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
							uad.setSignFlg(1);
							uad.setRemark("好友认证通过");// 备注
							uad.setRelateKey("user_id");
							uad.setRelateTo(user.getId());
							userAwardDetailDao.save(uad);
							
						}
						//修改推广奖励发放状态
						user.setTgStatus(1);//推广奖励发送状态【0：没发放（默认），1：已发放】
						user.setSumTenderMoney(hbao.getTotal());//奖励的红包
						userDao.update(user);
						}
						
					}
				}
				
			}
	
	@Transactional(readOnly = true)
	public List queryCensusUserList(Date date,String username) {

		Integer dateInt = CommonUtil.getIntDate(date);
		Date dateEnd = CommonUtil.date2end(date);

		return userDao.queryCensusUserList(dateInt, dateEnd,username);

	}
	
	@Transactional(readOnly = true)
	public Pager queryCensusUserPage(Date date, Pager pager) {
		Integer dateInt = CommonUtil.getIntDate(date);
		Date dateEnd = CommonUtil.date2end(date);
		return userDao.queryCensusUserPage(dateInt, dateEnd, pager);
	}
	
	

	public void updateUserAndAgency(Integer id,User user,Agency agency){
		User pre_user = userDao.get(id);
		
		if(StringUtils.isNotEmpty(user.getPassword())){
			pre_user.setPassword( MD5.getMD5Str(MD5.getMD5Str(user.getPassword())+pre_user.getRandomNum()));
		}
		pre_user.setUsername(user.getUsername());
		pre_user.setRealName(user.getRealName());
		pre_user.setPhone(user.getPhone());
		pre_user.setEmail(user.getEmail());
		if(user.getCardPic1() != null){
			pre_user.setCardPic1(user.getCardPic1());
		}
		if(user.getCardPic2() != null){
			pre_user.setCardPic2(user.getCardPic2());
		}
		pre_user.setAreaStore(user.getAreaStore());
		pre_user.setAddress(user.getAddress());
		pre_user.setProvince(user.getProvince());
		pre_user.setCity(user.getCity());
		pre_user.setArea(user.getArea());
		userDao.update(pre_user);
		
		Agency pre =agencyDao.load(pre_user.getAgencyid());
//		Agency pre = new Agency();
		if(agency.getImg()!= null){
			pre.setImg(agency.getImg());
		}
		if(agency.getLogo() != null){
			pre.setLogo(agency.getLogo());
		}
		if(agency.getLogo1() != null){
			pre.setLogo1(agency.getLogo1());
		}
		if(agency.getLogo2() != null){
			pre.setLogo2(agency.getLogo2());
		}
		if(agency.getLogo3() != null){
			pre.setLogo3(agency.getLogo3());
		}
		if(agency.getPrivateCharterImg() != null){
			pre.setPrivateCharterImg(agency.getPrivateCharterImg());
		}
		if(agency.getPrivateTaxImg() != null){
			pre.setPrivateTaxImg(agency.getPrivateTaxImg());
		}
		if(agency.getPrivateOrganizationImg() != null){
			pre.setPrivateOrganizationImg(agency.getPrivateOrganizationImg());
		}
		if(agency.getAccountLicenceImg() != null){
			pre.setAccountLicenceImg(agency.getAccountLicenceImg());
		}

		pre.setCompanyName(agency.getCompanyName());
		pre.setLinkman(agency.getLinkman());
		pre.setLinkmanMobile(agency.getLinkmanMobile());
		pre.setServicePromise(StringUtils.deleteWhitespace(agency.getServicePromise()));
		pre.setMainBusiness(agency.getMainBusiness());
		pre.setRemark(agency.getRemark());

		pre.setPrivateCharter(agency.getPrivateCharter());
		pre.setTaxRegistration(agency.getTaxRegistration());
		pre.setOrganization(agency.getOrganization());

		pre.setEstablishDate(agency.getEstablishDate());
		pre.setBusinessStart(agency.getBusinessStart());
		pre.setBusinessEnd(agency.getBusinessEnd());
		
		pre.setAddress(agency.getAddress());
		pre.setCapital(agency.getCapital());
		pre.setScope(agency.getScope());
		
		pre.setIntroduction(agency.getIntroduction());
		pre.setSubpagePhone(agency.getSubpagePhone());
		pre.setSubpageAddress(agency.getSubpageAddress());
		pre.setOrderList(agency.getOrderList());
		pre.setAgencydbid(agency.getAgencydbid());
		pre.setTasteRule(agency.getTasteRule());
		pre.setFlowRule(agency.getFlowRule());
		pre.setCreditRule(agency.getCreditRule());
		pre.setPledgeRule(agency.getPledgeRule());
		pre.setTasteMoney(agency.getTasteMoney());
		agencyDao.update(pre);
	}
	

	public User getUserByAgency(Integer agencyid,Integer agencytype){
		return userDao.getUserByAgency( agencyid, agencytype);
	}

	@Override
	public Pager findPagerByMap(Pager pager, Map<String, Object> map) {
		return userDao.findPagerByMap(pager, map);
	}
	

	public Pager findPager(Pager pager,Map<String,Object> map){
		return userDao.findPager(pager, map);
	}
	

	public Pager findPagerByHsql(Pager pager,Map<String, Object> map){
		return userDao.findPagerByHsql(pager, map);
	}
	
	public List<UserListBean> getListByHsql(Map<String, Object> map){
		return userDao.getListByHsql(map);
	}
	
	
	public Pager findInviteAwardPagerBySql(Pager pager,Map<String, Object> map){
		return userDao.findInviteAwardPagerBySql(pager, map);
	}
	
	public List<UserSpreadInviteAwardDTO> getListInviteAwardBySql(Map<String, Object> map){
		return userDao.getListInviteAwardBySql(map);
	}
}
