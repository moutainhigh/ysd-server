package net.qmdboss.dao.impl;

import net.qmdboss.bean.Pager;
import net.qmdboss.dao.RewardsDao;
import net.qmdboss.entity.Rewards;
import net.qmdboss.util.CommonUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository("rewardsDaoImpl")
public class RewardsDaoImpl extends BaseDaoImpl<Rewards, Integer> implements
		RewardsDao {

	public Pager findPagerByType(Pager pager, Rewards re) {

		Criteria criteria = getSession().createCriteria(Rewards.class);
		if (re.getType() != null) {
			criteria.add(Restrictions.eq("type", re.getType()));
		}
		if (re.getStatus() != null) {
			criteria.add(Restrictions.eq("status", re.getStatus()));
		}
		return super.findPager(pager, criteria);
	}

	public Pager findPagerByType(Pager pager, Map<String, Object> map) {

		Criteria criteria = getSession().createCriteria(Rewards.class);
		if (map != null) {
			Date startDate = map.get("startDate") != null ? (Date) map
					.get("startDate") : null;
			Date endDate = map.get("endDate") != null ? (Date) map
					.get("endDate") : null;

			// 按添加时间
			if (startDate != null && endDate != null) {
				endDate = CommonUtil.date2begin(endDate);
				criteria.add(Restrictions.between("createDate", startDate,
						endDate));// 添加时间
			}

			if (map.get("status") != null) {// 操作状态【0:失败；1：成功；2：审核中】
				criteria.add(Restrictions.eq("status",
						Integer.parseInt(map.get("status").toString())));
			}
			if (map.get("type") != null) {// 【0：扣除；1：奖励；2:数据转入】
				criteria.add(Restrictions.eq("type",
						Integer.parseInt(map.get("type").toString())));
			}
		}
		return super.findPager(pager, criteria);
	}

	public List<Rewards> getRewardsList(Map<String, Object> map) {
		Criteria criteria = getSession().createCriteria(Rewards.class);

		if (map != null) {
			Object searchar = map.get("search");
			Object keyword = map.get("keyword");
			if (searchar != null && !"".equals(searchar.toString())
					&& keyword != null && !"".equals(keyword.toString())) {
				if ("user.username".equals(searchar.toString())) {
					criteria.createAlias("user", "user");
					criteria.add(Restrictions.like("user.username", "%"
							+ keyword.toString() + "%"));// 根据用户名查询
				}
			}

			Date startDate = map.get("startDate") != null ? (Date) map
					.get("startDate") : null;
			Date endDate = map.get("endDate") != null ? (Date) map
					.get("endDate") : null;

			// 按提交时间或者完成时间查询
			if (startDate != null && endDate != null) {
				endDate = CommonUtil.date2begin(endDate);
				criteria.add(Restrictions.between("createDate", startDate,
						endDate));// 添加时间

			}
			if (map.get("status") != null) {// 操作状态【0:失败；1：成功；2：审核中】
				criteria.add(Restrictions.eq("status",
						Integer.parseInt(map.get("status").toString())));
			}
			if (map.get("type") != null) {// 【0：扣除；1：奖励；2:数据转入】
				criteria.add(Restrictions.eq("type",
						Integer.parseInt(map.get("type").toString())));
			}

		}
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}
}
