package net.qmdboss.service.impl;

import net.qmdboss.dao.RechargeConfigDao;
import net.qmdboss.entity.RechargeConfig;
import net.qmdboss.service.RechargeConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("rechargeConfigServiceImpl")
public class RechargeConfigServiceImpl  extends BaseServiceImpl<RechargeConfig, Integer> implements RechargeConfigService{
	
	@Resource(name = "rechargeConfigDaoImpl")
	private RechargeConfigDao rechargeConfigDao;
	
	@Resource(name = "rechargeConfigDaoImpl")
	public void setBaseDao(RechargeConfigDao rechargeConfigDao) {
		super.setBaseDao(rechargeConfigDao);
	}
	
	@Transactional(readOnly = true)
	public RechargeConfig find(String paymentProductId){
		return this.rechargeConfigDao.find(paymentProductId);
	}
	
	@Transactional(readOnly = true)
	public Boolean isUniqueByProductId(String oldProductId,String newProductId){
		if(StringUtils.equalsIgnoreCase(oldProductId, newProductId)){
			return true;
		}else{
			if(rechargeConfigDao.find(newProductId)!= null){
				return false;
			}else{
				return true;
			}
		}
	}
	
	public List<RechargeConfig> getRechargeConfigList(){
		return rechargeConfigDao.getRechargeConfigList();
	}
}
