package net.qmdboss.service.impl;

import net.qmdboss.dao.AgencyDao;
import net.qmdboss.entity.Agency;
import net.qmdboss.service.AgencyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("agencyServiceImpl")
public class AgencyServiceImpl  extends BaseServiceImpl<Agency, Integer> implements AgencyService {
	@Resource(name = "agencyDaoImpl")
	private AgencyDao agencyDao;
	
	@Resource(name = "agencyDaoImpl")
	public void setBaseDao(AgencyDao agencyDao) {
		super.setBaseDao(agencyDao);
	}
	
	public List<Agency> ListAgencyForUserid(Integer userid){
		
		return agencyDao.ListAgencyForUserid(userid);
	}

	public void updateAgency(Agency ar){
//		Agency pre = agencyDao.get(ar.getId());
//		pre.setLogo(ar.getLogo());
//		pre.setCompanyName(ar.getCompanyName());
//		pre.setMainBusiness(ar.getMainBusiness());
//		pre.setAreaStore(ar.getAreaStore());
//		pre.setMsDate(new Date());
//		pre.setServicePromise(ar.getServicePromise());
//		pre.setRiskMoney(ar.getRiskMoney());
//		pre.setAddress(ar.getAddress());
//		pre.setCapital(ar.getCapital());
//		pre.setEstablishDate(ar.getEstablishDate());
//		pre.setScope(ar.getScope());
//		pre.setRegistrationNum(ar.getRegistrationNum());
//		pre.setLegalRepresentative(ar.getLegalRepresentative());
//		pre.setEnterprise(ar.getEnterprise());
//		pre.setBusinessStart(ar.getBusinessStart());
//		pre.setBusinessEnd(ar.getBusinessEnd());
//		pre.setActualBusinessAddress(ar.getActualBusinessAddress());
//		pre.setTenureType(ar.getTenureType());
//		pre.setServiceLife(ar.getServiceLife());
//		pre.setFloorSpace(ar.getFloorSpace());
//		pre.setRemark(ar.getRemark());
//		agencyDao.update(pre);
		
		
		
		
	}
}
