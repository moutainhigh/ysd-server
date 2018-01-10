package com.qmd.service.impl.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmd.dao.user.UserHongbaoDao;
import com.qmd.mode.user.UserHongbao;
import com.qmd.mode.util.Hongbao;
import com.qmd.service.impl.BaseServiceImpl;
import com.qmd.service.user.UserHongbaoService;
import com.qmd.util.bean.HongbaoDetail;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service("userHongbaoService")
public class UserHongbaoServiceImpl extends
BaseServiceImpl<UserHongbao, Integer> implements UserHongbaoService {

	@Resource
	UserHongbaoDao userHongbaoDao;
	
	@Resource
	public void setBaseDao(UserHongbaoDao userHongbaoDao) {
		super.setBaseDao(userHongbaoDao);
	}

	public BigDecimal getSelectSumMoney(Map<String,Object> map){
		return userHongbaoDao.getSelectSumMoney(map);
	}
	
	public List<net.qmdboss.beans.UserHongbao> queryHbListByMapNew(Map<String,Object> map){
		
		return userHongbaoDao.queryHbListByMapNew(map);
	}
	
	@Override
	public BigDecimal getThMoneyCurrentMostInvest(BigDecimal most){
		BigDecimal hbmoney =BigDecimal.ZERO;
		if(most==null || most.compareTo(BigDecimal.ZERO)<=0){
			return hbmoney;
		}
		//土豪红包(id=4)
		Hongbao hbaoth = userHongbaoDao.getSysHongBaoById(4);
		if(hbaoth != null && hbaoth.getIsEnabled().compareTo(1) == 0){//有土豪奖&&开启着
			List<HongbaoDetail> hbDetailList = (List<HongbaoDetail>) new Gson().fromJson(hbaoth.getHongbaoDetail(),new TypeToken<List<HongbaoDetail>>() {}.getType());
			for(HongbaoDetail hbdetail : hbDetailList){
				if(most.compareTo(new BigDecimal(hbdetail.getInvestmentStart())) >= 0 && most.compareTo(new BigDecimal(hbdetail.getInvestmentEnd())) <= 0 ){
					return hbdetail.getMoney();//对应区间红包金额
				}
			}
		}
		return hbmoney;
	}
	
	@Override
	public BigDecimal getJbMoneyMost(){
		BigDecimal hbMoney = BigDecimal.ZERO; 
		//截标红包(id=5)
		Hongbao hbaojibiao = userHongbaoDao.getSysHongBaoById(5);
		if(hbaojibiao != null && hbaojibiao.getIsEnabled().compareTo(1) == 0 ){//有截标奖&&开启着&&有投标详情
			List<HongbaoDetail> hbDetailList = (List<HongbaoDetail>) new Gson().fromJson(hbaojibiao.getHongbaoDetail(),new TypeToken<List<HongbaoDetail>>() {}.getType());
			for(HongbaoDetail hbdetail : hbDetailList){
				if(hbMoney.compareTo(hbdetail.getMoney()) < 0){
					hbMoney = hbdetail.getMoney();
				}
			}
		}
		return hbMoney;
	}
}
