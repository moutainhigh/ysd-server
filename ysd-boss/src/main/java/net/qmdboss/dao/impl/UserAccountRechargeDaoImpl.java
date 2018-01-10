package net.qmdboss.dao.impl;

import net.qmdboss.DTO.UserAccountRechargeDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.UserAccountRechargeDao;
import net.qmdboss.entity.RechargeConfig;
import net.qmdboss.entity.UserAccountRecharge;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository("userAccountRechargeDaoImpl")
public class UserAccountRechargeDaoImpl extends BaseDaoImpl<UserAccountRecharge, Integer> implements UserAccountRechargeDao{
	
	public Pager findPager(Pager pager,Map<String,Object> map){
		Criteria criteria = getSession().createCriteria(UserAccountRecharge.class);
		if(map!= null){
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
			Date startDate=null;
			Date endDate=null;
			try {
				startDate = map.get("startDate")!=null?dateformat.parse(map.get("startDate").toString()):null;
				endDate = map.get("endDate")!=null?dateformat.parse(map.get("endDate").toString()):null;
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//按提交时间或者完成时间查询
			if(startDate!= null && endDate!= null ){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endDate);
				calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
				endDate = calendar.getTime();
				if(map.get("dateType").toString().equals("0") ){
					criteria.add(Restrictions.between("createDate", startDate, endDate));//添加时间
				}else{
					criteria.add(Restrictions.between("rechargeDate", startDate, endDate));//充值完成时间
				}
			}
			
			if(map.get("rechargeInterfaceId")!=null){
				if (map.get("rechargeInterfaceIdEq")==null) {
					criteria.add(Restrictions.eq("rechargeInterface.id", Integer.parseInt(map.get("rechargeInterfaceId").toString())));
				} else if("NOT".equals(map.get("rechargeInterfaceIdEq"))) {
					criteria.add(Restrictions.not(Restrictions.eq("rechargeInterface.id", Integer.parseInt(map.get("rechargeInterfaceId").toString()))));
				}
			}
			if(map.get("status")!=null){
				criteria.add(Restrictions.eq("status", Integer.parseInt(map.get("status").toString())));
			}
			
			if(map.get("moneygt")!=null && map.get("moneylt")==null){
				criteria.add(Restrictions.ge("money", new BigDecimal(map.get("moneygt").toString())));
			}else if(map.get("moneygt")==null && map.get("moneylt")!=null){
				criteria.add(Restrictions.le("money", new BigDecimal(map.get("moneylt").toString())));
			}else if(map.get("moneygt")!=null && map.get("moneylt")!=null){
				criteria.add(Restrictions.between("money", new BigDecimal(map.get("moneygt").toString()),new BigDecimal(map.get("moneylt").toString())));
			}
		}
		
		return super.findPager(pager, criteria);
	}
	
	/**
	public List<UserAccountRecharge> getRechargeList(Map<String,Object> map){
		Criteria criteria = getSession().createCriteria(UserAccountRecharge.class);
		
		if(map!= null){
			Object searchar = map.get("search");
			Object keyword = map.get("keyword");
			if(searchar!= null && !"".equals(searchar.toString()) && keyword != null && !"".equals(keyword.toString())){
				if("user.username".equals(searchar.toString())){
					Object queryMode = map.get("queryMode");
					
					criteria.createAlias("user", "user");
					if(queryMode != null && !"".equals(queryMode.toString()) && "1".equals(queryMode.toString())){
						criteria.add(Restrictions.eq("user.username", keyword.toString() ));//根据用户名查询精确查询
					}else{
						criteria.add(Restrictions.like("user.username", "%"+keyword.toString()+"%"));//根据用户名查询
					}
				}else if("tradeNo".equals(searchar.toString())){
					criteria.add(Restrictions.eq("tradeNo", keyword.toString()));//根据订单号查询
				}
			}
			
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
			Date startDate=null;
			Date endDate=null;
			try {
				startDate = map.get("startDate")!=null?dateformat.parse(map.get("startDate").toString()):null;
				endDate = map.get("endDate")!=null?dateformat.parse(map.get("endDate").toString()):null;
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//按提交时间或者完成时间查询
			if(startDate!= null && endDate!= null ){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endDate);
				calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
				endDate = calendar.getTime();
				if(map.get("dateType").toString().equals("0") ){
					criteria.add(Restrictions.between("createDate", startDate, endDate));//添加时间
				}else{
					criteria.add(Restrictions.between("rechargeDate", startDate, endDate));//充值完成时间
				}
			}
			if(map.get("rechargeInterfaceId")!=null){//接口
				criteria.add(Restrictions.eq("rechargeInterface.id", Integer.parseInt(map.get("rechargeInterfaceId").toString())));
			}
			if(map.get("status")!=null){//充值状态
				criteria.add(Restrictions.eq("status", Integer.parseInt(map.get("status").toString())));
			}
			
			if(map.get("moneygt")!=null && map.get("moneylt")==null){
				criteria.add(Restrictions.ge("money", new BigDecimal(map.get("moneygt").toString())));
			}else if(map.get("moneygt")==null && map.get("moneylt")!=null){
				criteria.add(Restrictions.le("money", new BigDecimal(map.get("moneylt").toString())));
			}else if(map.get("moneygt")!=null && map.get("moneylt")!=null){
				criteria.add(Restrictions.between("money", new BigDecimal(map.get("moneygt").toString()),new BigDecimal(map.get("moneylt").toString())));
			}
		}
		
		return criteria.list();
	}
	**/
	
	public List<UserAccountRechargeDTO> getRechargeList(Map<String,Object> map){
		
		
		Session session = getSession();
		StringBuffer hql = new StringBuffer();
	    hql.append("select a.id, u.username,u.real_name as realName,a.create_date as createDate,a.recharge_date as rechargeDate,a.trade_no as tradeNo,a.type,a.recharge_interface_id as rechargeInterfaceId,a.fee,a.reward,a.status,a.money ");
	    hql.append(" from fy_user_account_recharge as a left join fy_user as u on a.user_id=u.id where 1=1 ");
		
	    
		if(map!= null){
			
		
			Object searchar = map.get("search");
			Object keyword = map.get("keyword");
			if(searchar!= null && !"".equals(searchar.toString()) && keyword != null && !"".equals(keyword.toString())){
				if("user.username".equals(searchar.toString())){
					Object queryMode = map.get("queryMode");
					
					if(queryMode != null && !"".equals(queryMode.toString()) && "1".equals(queryMode.toString())){
						//criteria.add(Restrictions.eq("user.username", keyword.toString() ));//根据用户名查询精确查询
						hql.append(" and u.username="+keyword.toString());
					}else{
						//criteria.add(Restrictions.like("user.username", "%"+keyword.toString()+"%"));//根据用户名查询
						hql.append(" and u.username like '%"+keyword.toString()+"%' ");
					}
					
					
				}else if("tradeNo".equals(searchar.toString())){
					//criteria.add(Restrictions.eq("tradeNo", keyword.toString()));//根据订单号查询
					hql.append(" and a.tradeNo="+keyword.toString());
				}
			}
			
			
			
			
		
			//SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
			String startDate=null;
			String endDate=null;
			
				startDate = map.get("startDate")!=null?map.get("startDate").toString()+" 00:00:00":null;
				endDate = map.get("endDate")!=null?map.get("endDate").toString()+" 23:59:59":null;
				
		
		
			
			/**
			//按提交时间或者完成时间查询
			if(startDate!= null && endDate!= null ){
				//Calendar calendar = Calendar.getInstance();
				//calendar.setTime(endDate);
				//calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
				//endDate = calendar.getTime();
				if(map.get("dateType").toString().equals("0") ){
					criteria.add(Restrictions.between("createDate", startDate, endDate));//添加时间
				}else{
					criteria.add(Restrictions.between("rechargeDate", startDate, endDate));//充值完成时间
				}
			}
			**/
			
				
				if(map.get("dateType").toString().equals("0") ){
					//criteria.add(Restrictions.between("createDate", startDate, endDate));//添加时间
					if(startDate!=null ){
						hql.append(" and a.create_date >= '" +startDate+"'");
					}
					
					if(endDate!=null  ){
						hql.append(" and a.create_date <= '" +endDate+"'");
					}
					
					
				}else{
					//criteria.add(Restrictions.between("rechargeDate", startDate, endDate));//充值完成时间
					if(startDate!=null ){
						hql.append(" and a.recharge_date >= '" +startDate+"'");
					}
					
					if(endDate!=null  ){
						hql.append(" and a.recharge_date <= '" +endDate+"'");
					}
				}
			
			
			
			
			if(map.get("rechargeInterfaceId")!=null){//接口
				//criteria.add(Restrictions.eq("rechargeInterface.id", Integer.parseInt(map.get("rechargeInterfaceId").toString())));
				hql.append(" and a.recharge_interface_id="+Integer.parseInt(map.get("rechargeInterfaceId").toString()));
			}
			if(map.get("status")!=null){//充值状态
				//criteria.add(Restrictions.eq("status", Integer.parseInt(map.get("status").toString())));
				hql.append(" and a.status="+Integer.parseInt(map.get("status").toString()));
			}
			
			
			if(map.get("moneygt")!=null && map.get("moneylt")==null){
				//criteria.add(Restrictions.ge("money", new BigDecimal(map.get("moneygt").toString())));
				hql.append(" and a.money >="+map.get("moneygt"));
			}else if(map.get("moneygt")==null && map.get("moneylt")!=null){
				//criteria.add(Restrictions.le("money", new BigDecimal(map.get("moneylt").toString())));
				hql.append(" and a.money <="+map.get("moneylt"));
			}else if(map.get("moneygt")!=null && map.get("moneylt")!=null){
				//criteria.add(Restrictions.between("money", new BigDecimal(map.get("moneygt").toString()),new BigDecimal(map.get("moneylt").toString())));
				hql.append(" and a.money between "+map.get("moneygt")+" and "+ map.get("moneylt"));
				
			}
			
			
			
			
		
		}
		
		hql.append(" order by a.id desc");
		
		
		Query query = session.createSQLQuery(hql.toString());

		query.setResultTransformer(Transformers.aliasToBean(UserAccountRechargeDTO.class));
		
		
		System.out.println("hql============="+hql.toString());
		
		return query.list();
	}
	
	
	public List<UserAccountRecharge> findUserAccountRechargeList(String username,Integer status, RechargeConfig rechargeInterface,Date startDate,Date endDate){
		Criteria criteria = getSession().createCriteria(UserAccountRecharge.class);
		
		if(StringUtils.isNotEmpty(username)){
			criteria.createAlias("user", "user");
			criteria.add(Restrictions.like("user.username", "%"+username+"%"));
		}
		if(rechargeInterface!=null){
			criteria.add(Restrictions.eq("rechargeInterface", rechargeInterface));
		}
		if(status!=null){
			criteria.add(Restrictions.eq("status", status));
		}
		if(startDate != null && endDate != null){
			criteria.add(Restrictions.between("createDate", startDate, endDate));
		}
		criteria.addOrder(Order.desc("createDate"));
		return criteria.list();
		
	}
	
	public UserAccountRecharge getUserAccountRecharge(String tradeNo){
		String hql = "from UserAccountRecharge as userAccountRecharge where lower(userAccountRecharge.tradeNo) = lower(:tradeNo)";
		return (UserAccountRecharge) getSession().createQuery(hql).setParameter("tradeNo", tradeNo).uniqueResult();
	}
	
	@Override
	public void update(List<UserAccountRecharge> userAccountRechargeList){
		for(int i=0,j=userAccountRechargeList.size(); i<j; i++){
			UserAccountRecharge u = userAccountRechargeList.get(i);
			super.update(u);
			if(i%20 ==0){
				flush();
				clear();
			}
			
		}
	}


	@Override
	public List<UserAccountRecharge> getUserAccountRechargeList(Integer userId,
			Integer status, Date startDate, Date endDate) {
		Criteria criteria = getSession().createCriteria(UserAccountRecharge.class);
		// TODO Auto-generated method stub
		System.out.println("==userId====>>>"+userId);
		if(userId !=null){
			criteria.createAlias("user", "user");
			criteria.add(Restrictions.eq("user.id", userId));
		}
		
		if(status!=null){
			criteria.add(Restrictions.eq("status", status));
		}
		if(startDate != null && endDate != null){
			criteria.add(Restrictions.between("createDate", startDate, endDate));
		}
		criteria.addOrder(Order.desc("createDate"));
		return criteria.list();
	}
	
	public Pager findComRechargePager(Pager pager){
		Criteria criteria = getSession().createCriteria(UserAccountRecharge.class);
		
		criteria.add(Restrictions.or(Restrictions.ne("rechargeInterface.id", 0),Restrictions.and(Restrictions.eq("status", 1), Restrictions.eq("rechargeInterface.id", 0))));
		criteria.createAlias("rechargeStatus", "rs");
		Integer state[] = {0,3};
		criteria.add(Restrictions.in("rs.saveState",state));
		pager.setOrderBy("rs.saveState");
		pager.setOrder(Pager.Order.asc);
		return super.findPager(pager, criteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserAccountRecharge> getComUserAccountRecharge(){
		Criteria criteria = getSession().createCriteria(UserAccountRecharge.class);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MINUTE,0);
		//订单时间为不包含今天的所有线上充值数据
		criteria.add(Restrictions.le("createDate", calendar.getTime()));
		
		criteria.add(Restrictions.ne("rechargeInterface.id", 0));
		
		criteria.createAlias("temporary", "temporary", CriteriaSpecification.LEFT_JOIN);//临时表
		criteria.add(Restrictions.or(Restrictions.eq("status", 1), Restrictions.and(Property.forName("temporary.id").isNotNull(),Restrictions.eq("status",2))));
		
		criteria.createAlias("rechargeStatus", "rs");
		Integer state[] = {0,3};
		criteria.add(Restrictions.in("rs.saveState",state));
		criteria.addOrder(Order.asc("rs.saveState"));
		return criteria.list();
	}
	
	public List<UserAccountRecharge> getUserAccountRecharge(){
		Criteria criteria = getSession().createCriteria(UserAccountRecharge.class);
		criteria.createAlias("rechargeStatus", "rs");
		criteria.add(Restrictions.eq("rs.saveState",3));
		return criteria.list();
	}
	
	public BigDecimal getMoneyCount(UserAccountRecharge uar){
		Criteria criteria = getSession().createCriteria(UserAccountRecharge.class);
		criteria.add(Restrictions.eq("type",uar.getType()));
		criteria.add(Restrictions.eq("status",uar.getStatus()));
		criteria.setProjection(Projections.sum("money"));
		BigDecimal total= (BigDecimal) criteria.uniqueResult();
		return total;
	}
	

	public Pager findNeedVerifyPagerByType(Pager pager,String type){
		Criteria criteria = getSession().createCriteria(UserAccountRecharge.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MINUTE,0);
		//订单时间为昨天以前的数据
		criteria.add(Restrictions.le("createDate", calendar.getTime()));
		if("0".equals(type)){//成功的线下充值
			criteria.add(Restrictions.and(Restrictions.eq("status", 1), Restrictions.eq("rechargeInterface.id", 0)));
		}else if("1".equals(type)){//线上充值
			criteria.add(Restrictions.ne("rechargeInterface.id", 0));
			criteria.createAlias("temporary", "temporary", CriteriaSpecification.LEFT_JOIN);//临时表
			criteria.add(Restrictions.or(Restrictions.eq("status", 1), Restrictions.and(Property.forName("temporary.id").isNotNull(),Restrictions.eq("status",2))));
		}
		
		criteria.createAlias("rechargeStatus", "rs");
		Integer state[] = {0,3};
		criteria.add(Restrictions.in("rs.saveState",state));
		pager.setOrderBy("rs.saveState");
		pager.setOrder(Pager.Order.asc);
		return super.findPager(pager, criteria);
	}
	
}
