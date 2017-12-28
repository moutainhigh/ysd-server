package net.qmdboss.service.impl;

import net.qmdboss.dao.HongbaoDao;
import net.qmdboss.entity.Hongbao;
import net.qmdboss.service.HongbaoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("hongbaoServiceImpl")
public class HongbaoServiceImpl extends BaseServiceImpl<Hongbao, Integer> implements HongbaoService{
	
	@Resource(name="hongbaoDaoImpl")
	private HongbaoDao hongbaoDao;
	
	@Resource(name = "hongbaoDaoImpl")
	public void setBaseDao(HongbaoDao hongbaoDao) {
		super.setBaseDao(hongbaoDao);
	}
	
	
	
	
	
}
