package net.qmdboss.action.admin;

import net.qmdboss.entity.Rewards;
import net.qmdboss.entity.User;
import net.qmdboss.service.AdminService;
import net.qmdboss.service.RewardsService;
import net.qmdboss.service.UserAccountDetailService;
import net.qmdboss.service.UserService;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ParentPackage("admin")
public class RewardsAction extends BaseAdminAction{

	private static final long serialVersionUID = -2251767602163338164L;
	private User user;
	private Rewards rewards;
	private String flag;//0：扣除；1：现金奖励；2:数据转入；3：红包奖励；4：红包扣除;5:添加体验金;6:扣除体验金
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	
	@Resource(name = "rewardsServiceImpl")
	private RewardsService rewardsService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "userAccountDetailServiceImpl")
	private UserAccountDetailService userAccountDetailService;
	
	//跳转-费用扣除
	public String deduct(){
		flag = "0";
		return INPUT;
	}
	
	//跳转-奖励
	public String reward(){
		flag = "1";
		return INPUT;
	}
	//跳转-红包奖励
	public String rewardHongbao(){
		flag = "3";
		return INPUT;
	}
	
	//跳转-奖励
	public String moneyInto(){
		flag = "2";
		return INPUT;
	}
	//跳转-扣除奖励
	public String deductHongbao(){
		flag = "4";
		return INPUT;
	}
		
	//跳转-添加体验金
	public String tasteAdd(){
		flag = "5";
		return INPUT;
	}
	//跳转-扣除体验金
	public String tasteSub(){
		flag = "6";
		return INPUT;
	}
	//添加
	public String add(){
		if(user == null){
			addActionError("请输入用户名!");
			return ERROR;
		}
		user = userService.getUserByUsername(user.getUsername());
		rewards.setUser(user);
		rewards.setStatus(2);
		rewards.setOperator(adminService.getLoginAdmin().getUsername());
		rewards.setIpOperator(getRequest().getRemoteAddr());
		rewardsService.save(rewards);
		StringBuffer logInfoStringBuffer = new StringBuffer();
		if(rewards.getType()==0){
			logInfoStringBuffer.append("【扣除】"+user.getUsername()+"费用：");
		}else if(rewards.getType() == 1){
			logInfoStringBuffer.append("【现金奖励】"+user.getUsername()+"：");
		}else if(rewards.getType() == 2){
			logInfoStringBuffer.append("【资金转入】"+user.getUsername()+"：");
		}else if(rewards.getType() == 3){
			logInfoStringBuffer.append("【红包奖励】"+user.getUsername()+"：");
		}else if(rewards.getType() == 4){
			logInfoStringBuffer.append("【红包扣除】"+user.getUsername()+"：");
		}else if(rewards.getType() == 5){
			logInfoStringBuffer.append("【增加体验金】"+user.getUsername()+"：");
		}else if(rewards.getType() == 6){
			logInfoStringBuffer.append("【扣除体验金】"+user.getUsername()+"：");
		}
		logInfoStringBuffer.append(rewards.getMoney()+"元");
		logInfo = logInfoStringBuffer.toString();
		if(rewards.getType()==0){
			redirectUrl="rewards!deduct.action";
		}else if(rewards.getType()==1){
			redirectUrl="rewards!reward.action";
		}else if(rewards.getType()==2){
			redirectUrl="rewards!moneyInto.action";
		}else if(rewards.getType()==3){
			redirectUrl="rewards!rewardHongbao.action";
		}else if(rewards.getType()==4){
			redirectUrl="rewards!deductHongbao.action";
		}else if(rewards.getType()==5){
			redirectUrl="rewards!tasteAdd.action";
		}else if(rewards.getType()==6){
			redirectUrl="rewards!tasteSub.action";
		}
		
		return SUCCESS;
	}
	
	//跳转
	public String verify(){
		rewards =  rewardsService.load(id);
		flag = rewards.getType().toString();
		return "verify";
	}
	
	//审核
	public String doVerify(){
		
		StringBuffer logInfoStringBuffer = new StringBuffer();
		rewards.setId(id);
		rewards.setVerify(adminService.getLoginAdmin().getUsername());
		rewards.setIpVerify(getRequest().getRemoteAddr());
		rewards.setVerifyRemark(rewards.getVerifyRemark());
		
		int r = rewardsService.updateVerify(rewards,logInfoStringBuffer);
		if (r==1) {
			addActionError("状态不正确!");
			return ERROR;
		}
		
		logInfo = logInfoStringBuffer.toString();
		if(rewards.getType()==0){
			redirectUrl="rewards!deductList.action?rewards.type="+rewards.getType()+"&rewards.status=2";
		}else if(rewards.getType()==1){
			redirectUrl="rewards!rewardList.action?rewards.type="+rewards.getType()+"&rewards.status=2";
		}else if(rewards.getType()==2){
			redirectUrl="rewards!moneyIntoList.action?rewards.type="+rewards.getType()+"&rewards.status=2";
		}else if(rewards.getType()==3){
			redirectUrl="rewards!rewardHongbaoList.action?rewards.type="+rewards.getType()+"&rewards.status=2";
		}else if(rewards.getType()==4){
			redirectUrl="rewards!deductHongbaoList.action?rewards.type="+rewards.getType()+"&rewards.status=2";
		}else if(rewards.getType()==5){
			redirectUrl="rewards!tasteAddList.action?rewards.type="+rewards.getType()+"&rewards.status=2";
		}else if(rewards.getType()==6){
			redirectUrl="rewards!tasteSubList.action?rewards.type="+rewards.getType()+"&rewards.status=2";
		}
		
		return SUCCESS;
	}
	
	//费用扣除审核列表
	public String deductList(){
		if(rewards == null){
			rewards = new Rewards();
			rewards.setType(0);
			rewards.setStatus(2);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		map.put("type", rewards.getType());//类型
		map.put("status", rewards.getStatus());//状态
		
		pager = rewardsService.findPagerByType(pager,map);
		return LIST;
	}
	
	//现金奖励 审核列表
	public String rewardList(){
		if(rewards == null){
			rewards = new Rewards();
			rewards.setType(1);
			rewards.setStatus(2);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		map.put("type", rewards.getType());//类型
		map.put("status", rewards.getStatus());//状态
		pager = rewardsService.findPagerByType(pager,map);
		return LIST;
	}
	//红包奖励 审核列表
	public String rewardHongbaoList(){
		if(rewards == null){
			rewards = new Rewards();
			rewards.setType(3);
			rewards.setStatus(2);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		map.put("type", rewards.getType());//类型
		map.put("status", rewards.getStatus());//状态
		pager = rewardsService.findPagerByType(pager,map);
		return LIST;
	}
	//红包扣除 审核列表
	public String deductHongbaoList(){
		if(rewards == null){
			rewards = new Rewards();
			rewards.setType(4);
			rewards.setStatus(2);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		map.put("type", rewards.getType());//类型
		map.put("status", rewards.getStatus());//状态
		pager = rewardsService.findPagerByType(pager,map);
		return LIST;
	}
	
	//资金转入 审核列表
	public String moneyIntoList(){
		if(rewards == null){
			rewards = new Rewards();
			rewards.setType(2);
			rewards.setStatus(2);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		map.put("type", rewards.getType());//类型
		map.put("status", rewards.getStatus());//状态
		pager = rewardsService.findPagerByType(pager,map);
		return LIST;
	}

	//增加体验金 审核列表
	public String tasteAddList(){
		if(rewards == null){
			rewards = new Rewards();
			rewards.setType(5);
			rewards.setStatus(2);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		map.put("type", rewards.getType());//类型
		map.put("status", rewards.getStatus());//状态
		pager = rewardsService.findPagerByType(pager,map);
		return LIST;
	}
	//扣除体验金 审核列表
	public String tasteSubList(){
		if(rewards == null){
			rewards = new Rewards();
			rewards.setType(6);
			rewards.setStatus(2);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		map.put("type", rewards.getType());//类型
		map.put("status", rewards.getStatus());//状态
		pager = rewardsService.findPagerByType(pager,map);
		return LIST;
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Rewards getRewards() {
		return rewards;
	}

	public void setRewards(Rewards rewards) {
		this.rewards = rewards;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
