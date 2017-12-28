package net.qmdboss.dao;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.Fangkuan;

import java.util.Map;

public interface FangkuanDao extends BaseDao<Fangkuan, Integer>  {

	public Pager findPager(Pager pager,Map<String,Object> map);
}
