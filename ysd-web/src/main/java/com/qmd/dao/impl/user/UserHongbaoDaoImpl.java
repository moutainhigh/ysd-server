package com.qmd.dao.impl.user;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.user.UserHongbaoDao;
import com.qmd.mode.user.UserHongbao;
import com.qmd.mode.util.Hongbao;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository("userHongbaoDao")
public class UserHongbaoDaoImpl extends BaseDaoImpl<UserHongbao, Integer> implements UserHongbaoDao {

	public BigDecimal getSelectSumMoney(Map<String, Object> map) {
		BigDecimal sum = (BigDecimal) getSqlSession().selectOne(getClassNameSpace() + ".selectSumMoney", map);
		return sum;
	}
	
	public List<net.qmdboss.beans.UserHongbao> queryHbListByMapNew(Map<String,Object> map){
		
		List<net.qmdboss.beans.UserHongbao> list= this.getSqlSession().selectList("UserHongbao.queryHbListByMapNew",map);
		return list;
	}
	
	public BigDecimal getSumHbInvestFullMomey(Map<String, Object> map) {
		BigDecimal sum = (BigDecimal) getSqlSession().selectOne(getClassNameSpace() + ".getSumHbInvestFullMomey", map);
		return sum;
	}
	
	public net.qmdboss.beans.UserHongbao getNewHbById(Integer userId){
		net.qmdboss.beans.UserHongbao uhb = getSqlSession().selectOne(getClassNameSpace()+".getNewHbById", userId);
		return uhb;
	}
	
	
	public Integer updateNewHongbao(net.qmdboss.beans.UserHongbao userhongbaoN) {
		Object crow = getSqlSession().update(getClassNameSpace()+".updateNewHongbao", userhongbaoN);	
		if(crow!=null){
			return (Integer) crow;
		}
		return null;
	}
	
	@Override
	public Integer saveNewHongbao(net.qmdboss.beans.UserHongbao userhongbaoN) {
		Object crow = getSqlSession().insert(getClassNameSpace()+".saveNewHongbao", userhongbaoN);
		if(crow!=null){
			return (Integer) crow;
		}
		return null;
	}

	@Override
	public Hongbao getSysHongBaoById(Integer id) {
		Hongbao hb = (Hongbao) getSqlSession().selectOne(getClassNameSpace() + ".getSysHongBaoById", id);
		return hb;
	}
}
