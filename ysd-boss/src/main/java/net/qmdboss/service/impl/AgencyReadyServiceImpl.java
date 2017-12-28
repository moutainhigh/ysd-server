package net.qmdboss.service.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.*;
import net.qmdboss.entity.*;
import net.qmdboss.service.AgencyReadyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Service("agencyReadyServiceImpl")
public class AgencyReadyServiceImpl  extends BaseServiceImpl<AgencyReady, Integer> implements AgencyReadyService {
	@Resource(name = "agencyReadyDaoImpl")
	private AgencyReadyDao agencyReadyDao;
	
	@Resource(name = "userDaoImpl")
	private UserDao userDao;
	
	@Resource(name = "userInfoDaoImpl")
	private UserInfoDao userInfoDao;

	@Resource(name = "userAccountDaoImpl")
	private UserAccountDao userAccountDao;
	
	@Resource(name = "agencyDaoImpl")
	private AgencyDao agencyDao;
	
	
	@Resource(name = "agencyReadyDaoImpl")
	public void setBaseDao(AgencyReadyDao AgencyReadyDao) {
		super.setBaseDao(agencyReadyDao);
	}
	public Pager findAgencyReadyPager(Pager pager,AgencyReady ad){
		return agencyReadyDao.findAgencyReadyPager(pager,ad);
	}
	

	public void copyAgency(AgencyReady ar){
		User user = new User();
		user.setTypeId(3);//服务商
		user.setMemberType(1);//企业
		user.setUsername(ar.getUusername());
		user.setRealName(ar.getUrealname());
		user.setCardPic1(ar.getCardPic1());
		user.setCardPic2(ar.getCardPic2());
//		user.setCardId(ar.getUcardid());
		user.setEmail(ar.getUemail());
		user.setPhone(ar.getContactPhone());//联系电话
		user.setRandomNum(ar.getUrandom());
		user.setPassword(ar.getUpassword());
//		user.setPayPassword(ar.getUpaypassword());
		user.setAddress(ar.getContactAddress());//联系地址
		user.setProvince(ar.getProvince());
		user.setCity(ar.getCity());
		user.setArea(ar.getArea());
		user.setAreaStore(ar.getAreaStore());
		user.setIsEnabled(true);
		user.setIsLock(false);
		//所有认证都通过
		user.setRealStatus(1);
		user.setEmailStatus(1);
		user.setPhoneStatus(1);
		user.setLastTime(new Date());
		user.setLoginTime(1);
		
		user.setLitpic(ar.getSignImage());//签章
		
		userDao.save(user);
		
		
		UserInfo userInfo = new UserInfo();
		userInfo.setPrivateName(ar.getCompanyName());//【服务商名称】
//		userInfo.setPrivatePlace(ar.getActualBusinessAddress());
//		userInfo.setCompanyAddress(ar.getActualBusinessAddress());
//		userInfo.setRegistration(ar.getRegistrationNum());
		userInfo.setRegistration(ar.getPrivateCharter());//营业执照号码
		userInfo.setPrivateCharterImg(ar.getPrivateCharterImg());//营业执照
		userInfo.setTaxRegistration(ar.getTaxRegistration());//税务登记号
		userInfo.setPrivateTaxImg(ar.getPrivateTaxImg());//税务登记证
		userInfo.setOrganization(ar.getOrganization());//机构代码证号
		userInfo.setPrivateOrganizationImg(ar.getPrivateOrganizationImg());//机构代码证
		
		userInfo.setLinkman(ar.getLinkman());//联系人
		userInfo.setPrivatePhone(ar.getLinkmanMobile());//联系人电话
		userInfo.setRegisteredCapital(ar.getCapital()==null?"0":ar.getCapital().toString());
		userInfo.setUser(user);
		userInfoDao.save(userInfo);
		
		BigDecimal def_account = new BigDecimal(0);
		UserAccount userAccount = new UserAccount();
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
		userAccount.setContinueTotal(def_account);
//		userAccount.setCashMoney(def_account);
//		userAccount.setTasteMoney(def_account);
//		userAccount.setTasteMoneyFrozen(def_account);
//		userAccount.setInvestorCollectionTaste(def_account);
//		userAccount.setBorrowerCollectionTaste(def_account);
//		userAccount.setDirectMoney(def_account);
//		userAccount.setDirectMoneyFrozen(def_account);
//		userAccount.setInvestorCollectionDirect(def_account);
//		userAccount.setBorrowerCollectionDirect(def_account);
		userAccount.setUser(user);
		userAccountDao.save(userAccount);
		
		Agency a = new Agency();
		a.setCompanyName(ar.getCompanyName());
		a.setLinkman(ar.getLinkman());
		a.setLinkmanMobile(ar.getLinkmanMobile());
		a.setServicePromise(ar.getServicePromise());
		a.setMainBusiness(ar.getMainBusiness());
		a.setRemark(ar.getRemark());
		
		a.setPrivateCharter(ar.getPrivateCharter());
		a.setPrivateCharterImg(ar.getPrivateCharterImg());
		a.setTaxRegistration(ar.getTaxRegistration());
		a.setPrivateTaxImg(ar.getPrivateTaxImg());
		a.setOrganization(ar.getOrganization());
		a.setPrivateOrganizationImg(ar.getPrivateOrganizationImg());
		a.setAccountLicenceImg(ar.getAccountLicenceImg());
		a.setEstablishDate(ar.getEstablishDate());
		a.setBusinessStart(ar.getBusinessStart());
		a.setBusinessEnd(ar.getBusinessEnd());
		
		a.setAddress(ar.getAddress());
		a.setCapital(ar.getCapital());
		a.setScope(ar.getScope());
		
		a.setUserid(user.getId());
		a.setOrderList(1000);
		a.setAgencydbid(ar.getAgencydbid());
		
		a.setTasteMoney(ar.getTasteMoney());
		
		a.setTasteRule(ar.getTasteRule()==null?0:ar.getTasteRule());
		a.setFlowRule(ar.getFlowRule()==null?0:ar.getFlowRule());
		a.setCreditRule(ar.getCreditRule()==null?0:ar.getCreditRule());
		a.setPledgeRule(ar.getPledgeRule()==null?0:ar.getPledgeRule());
		
		a.setSignImage(ar.getSignImage());//签章
		
		agencyDao.save(a);
		
		
		userDao.flush();
		user.setAgencyid(a.getId());
		user.setAgencytype(1);
		userDao.update(user);
		
		ar.setStatus(1);
		agencyReadyDao.update(ar);
	}
}
