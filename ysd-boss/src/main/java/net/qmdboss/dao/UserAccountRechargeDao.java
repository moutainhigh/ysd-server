package net.qmdboss.dao;

import net.qmdboss.DTO.UserAccountRechargeDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.entity.RechargeConfig;
import net.qmdboss.entity.UserAccountRecharge;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserAccountRechargeDao extends BaseDao<UserAccountRecharge, Integer>{

	/**
	 * 获取充值记录 -分页
	 * @return
	 */
	public Pager findPager(Pager pager,Map<String,Object> map);
	
	/**
	 * 获取用户充值记录【仅用于导出数据】
	 * @param map
	 * @return
	 */
	public List<UserAccountRechargeDTO> getRechargeList(Map<String,Object> map);
	
	/**
	 *  获取充值记录
	 *  
	 *  @param status 用户名
	* @param status 状态【0：失败;1：成功;2:后台审核失败】
	 * @param rechargeInterface 充值接口【国付宝，支付宝.为0的表示后台充值】
	 * @param stareDate 查询起始时间
	 * @param endDate 查询截止时间
	 * @return
	 */
	public List<UserAccountRecharge> findUserAccountRechargeList(String username,Integer status, RechargeConfig rechargeInterface,Date startDate,Date endDate);
	
	/**
	 * 用户ID获取充值记录
	 * @param userId 用户ID
	 * @param status 状态
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public List<UserAccountRecharge> getUserAccountRechargeList(Integer userId,Integer status, Date startDate, Date endDate);
		
	/**
	 * 根据订单号查询充值记录
	 * @param tradeNo
	 * @return
	 */
	public UserAccountRecharge getUserAccountRecharge(String tradeNo);


	/**
	 * 批量修改查询记录
	 * @param userAccountRechargeList
	 */
	public void update(List<UserAccountRecharge> userAccountRechargeList);
	
	
	
	/**
	 * 查询所有需要比对的充值记录
	 */
	public Pager findComRechargePager(Pager pager);
	
	/**
	 * 查询所有需要比对的线上充值记录,不包含今天的所有充值数据
	 * @return
	 */
	public List<UserAccountRecharge> getComUserAccountRecharge();
	/**
	 * 获取需要迁移的充值记录
	 * @return
	 */
	public List<UserAccountRecharge> getUserAccountRecharge();
	
	/**
	 * 获取充值总金额
	 * @param uar
	 * @return
	 */
	public BigDecimal getMoneyCount(UserAccountRecharge uar);
	
	

	/**
	 * 2013-07-19增加功能 
	 * 查询昨天以前的充值数据
	 * @param pager
	 * @param type 1:线上，0：线下
	 * @return
	 */
	public Pager findNeedVerifyPagerByType(Pager pager,String type);
}
