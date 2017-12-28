package net.qmdboss.service;

import net.qmdboss.bean.Pager;
import net.qmdboss.entity.Rewards;

import java.util.List;
import java.util.Map;

public interface RewardsService extends BaseService<Rewards, Integer>{

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
	 *  审核处理
	 * @param id rewardsId
	 * @param rewards 页面信息
	 * @param ip
	 * @param adminUsername 
	 * @param logInfoStringBuffer 日志
	 * @return 1成功，2状态不正确
	 */
	public int updateApprove(Integer id,Rewards rewards,String ip,String adminUsername,StringBuffer logInfoStringBuffer);
	
		/**
	 * 获取手动操作资金数据
	 * @param pager
	 * @param re
	 * @return
	 */
	public List<Rewards> getRewardsList(Map<String,Object> map);
	
	/**
	 * 审核
	 * @param rewards
	 * @param logInfoStringBuffer
	 * @return 0审核完成，1状态不正确
	 */
	public int updateVerify(Rewards rewards,StringBuffer logInfoStringBuffer);
}
