package com.qmd.dao.impl.user;

import com.qmd.dao.impl.BaseDaoImpl;
import com.qmd.dao.user.UserAwardDetailDao;
import com.qmd.mode.user.UserAwardDetail;
import com.qmd.util.Pager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("userAwardDetailDao")
public class UserAwardDetailDaoImpl extends BaseDaoImpl<UserAwardDetail, Integer> implements UserAwardDetailDao {
	
	public Pager queryHbDetailList(Map<String,Object> map,Integer pageSize,Integer currentPage){
		
		
		
		Pager p=new Pager();
		
		p.setPageSize(pageSize);
		p.setPageNumber(currentPage);
	
		Integer totalSize = (Integer) getSqlSession().selectOne(getClassNameSpace() + ".queryCountHbDetailList", map);
		
		p.setTotalCount(totalSize);
		
		
		map.put("pageStart", (currentPage - 1) * pageSize);
		map.put("pageSize", pageSize);
		
		this.getSqlSession().update(getClassNameSpace()+".updateToLook",map);
		List<UserAwardDetail> list= this.getSqlSession().selectList(getClassNameSpace() +".queryHbDetailList",map);
		p.setResult(list);
		
		return p;
	}
}