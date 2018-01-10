package net.qmdboss.service;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.Fangkuan;

import java.util.Map;

public interface FangkuanService extends BaseService<Fangkuan, Integer> {

	public Pager findPager(Pager pager,Map<String,Object> map);
	
	public Integer verify(Fangkuan fangkuan,String ip);
}
