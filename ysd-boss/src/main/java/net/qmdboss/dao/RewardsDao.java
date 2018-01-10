package net.qmdboss.dao;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.Rewards;

import java.util.List;
import java.util.Map;

public interface RewardsDao  extends BaseDao<Rewards, Integer>{
	
	/**
	 * 根据 type 查询分页
	 * @param pager
	 * @param re
	 * @return
	 */
	public Pager findPagerByType(Pager pager ,Rewards re);
	/**
	 * 根据 type 查询分页
	 * @param pager
	 * @param re
	 * @return
	 */
	public Pager findPagerByType(Pager pager ,Map<String,Object> map);

	/**
	 * 获取手动操作资金数据
	 * @param pager
	 * @param re
	 * @return
	 */
	public List<Rewards> getRewardsList(Map<String,Object> map);
}
