package net.qmdboss.service.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.RewardsDao;
import net.qmdboss.dao.UserAccountDao;
import net.qmdboss.dao.UserAccountDetailDao;
import net.qmdboss.entity.Rewards;
import net.qmdboss.entity.User;
import net.qmdboss.entity.UserAccountDetail;
import net.qmdboss.service.AdminService;
import net.qmdboss.service.RewardsService;
import net.qmdboss.service.UserAccountDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("rewardsServiceImpl")
public class RewardsServiceImpl extends BaseServiceImpl<Rewards, Integer> implements RewardsService{

	@Resource(name = "rewardsDaoImpl")
	private RewardsDao rewardsDao;
	@Resource(name = "userAccountDetailDaoImpl")
	private UserAccountDetailDao userAccountDetailDao;
	@Resource(name = "userAccountDaoImpl")
	private UserAccountDao userAccountDao;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	
	@Resource(name = "userAccountDetailServiceImpl")
	private UserAccountDetailService userAccountDetailService;
	
	@Resource(name = "rewardsDaoImpl")
	public void setBaseDao(RewardsDao rewardsDao) {
		super.setBaseDao(rewardsDao);
	}
	
	public Pager findPagerByType(Pager pager ,Rewards re){
		return rewardsDao.findPagerByType(pager,re);
	}
	
	public Pager findPagerByType(Pager pager ,Map<String,Object> map){
		return rewardsDao.findPagerByType(pager,map);
	}
	
	@Override 
	public Integer save(Rewards rewards) {
		
		BigDecimal money = rewards.getMoney();
		String type="";
		if(rewards.getType()==0){//费用扣除
			type="expense_deduction_pending";
		}else if(rewards.getType()==1){//奖励
			type="pending_rewards";
		}
		
		return	rewardsDao.save(rewards);
		
		
	}
	
	public int updateApprove(Integer id,Rewards rewards,String ip,String adminUsername,StringBuffer logInfoStringBuffer) {
		
		Rewards entity = rewardsDao.loadLock(id);
		if (entity.getStatus() != 2) {// 2:待审--返还1：状态不对
			return 2;
		}
		
		rewards.setType(entity.getType());
		
		Integer status = rewards.getStatus();
		entity.setStatus(status);
		
		entity.setVerify(adminUsername);
		entity.setIpVerify(ip);
		entity.setVerifyRemark(rewards.getVerifyRemark());
		rewardsDao.update(entity);
		if(status ==1){//处理金额信息
			Integer type = entity.getType();
			
			String type_d = "";
			if(type ==0){
				type_d="offline_deduct,费用扣除";
			}else if(type ==1){
				type_d="offline_reward,奖励";
			}
			User user = entity.getUser();
			UserAccountDetail userAccountDetail = new UserAccountDetail();
			userAccountDetail.setAddTime(new Date());
			userAccountDetail.setMoney(entity.getMoney());
			userAccountDetail.setType(type_d.split(",")[0].toString());
			userAccountDetail.setOperatorIp(ip);
			userAccountDetail.setUser(user);
			userAccountDetailService.saveUserAccountDetailByDebits(userAccountDetail,type.toString());
			
			logInfoStringBuffer.append("用户名:" + user.getUsername() + ";操作类型:" + type_d.split(",")[1]+"，扣除金额:" + userAccountDetail.getMoney());
			logInfoStringBuffer.append(";备注:"+userAccountDetail.getRemark());
			
		}
		
		return 1;
	}
	
	public List<Rewards> getRewardsList(Map<String,Object> map){
		return rewardsDao.getRewardsList(map);
	}
	
	public int updateVerify(Rewards rewards,StringBuffer logInfoStringBuffer) {
		Rewards per = rewardsDao.loadLock(rewards.getId());
		
		if (per.getStatus() ==null || per.getStatus() != 2) {
			return 1;
		}
		
		rewards.setType(per.getType());
		Integer status = rewards.getStatus();
		per.setStatus(status);
		
		per.setVerify(rewards.getVerify());
		per.setIpVerify(rewards.getIpVerify());
		per.setVerifyRemark(rewards.getVerifyRemark());
		rewardsDao.update(per);
		if(status ==1){//处理金额信息
			Integer type = per.getType();
			String type_d = ",";
			if(type ==0){
				type_d="offline_deduct,费用扣除";
			}else if(type ==1){
				type_d="offline_reward,现金奖励";
			}else if(type ==2){
				type_d="money_into,资金转入";
			}else if(type ==3){
				type_d="offline_reward_hong_bao,红包奖励";
			}else if(type ==4){
				type_d="deduct_reward_hong_bao,红包扣除";
			}else if(type ==5){
				type_d="taste_add,增加体验金";
			}else if(type ==6){
				type_d="taste_sub,扣除体验金";
			}
			User user = per.getUser();
			UserAccountDetail userAccountDetail = new UserAccountDetail();
			userAccountDetail.setAddTime(new Date());
			userAccountDetail.setMoney(per.getMoney());
			userAccountDetail.setType(type_d.split(",")[0].toString());
			userAccountDetail.setOperatorIp(rewards.getIpVerify());
			userAccountDetail.setUser(user);
			userAccountDetail.setRemark(type_d.split(",")[1]+"："+userAccountDetail.getMoney()+"元");
			userAccountDetailService.saveUserAccountDetailByDebits(userAccountDetail,type.toString());
			
			logInfoStringBuffer.append("用户名:" + user.getUsername() + ";操作类型:" + type_d.split(",")[1]+"，扣除金额:" + userAccountDetail.getMoney());
			logInfoStringBuffer.append(";备注:"+userAccountDetail.getRemark());
			
		}
		
		return 0;
	
	}
}
