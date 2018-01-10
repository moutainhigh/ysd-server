package net.qmdboss.dao.impl;

import net.qmdboss.dao.ListingDao;
import net.qmdboss.entity.Listing;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Dao实现类 - 列表分类
 * @author xsf
 *
 */

@Repository("listingDaoImpl")
public class ListingDaoImpl extends BaseDaoImpl<Listing, Integer> implements ListingDao {
	
	public boolean isExistBySign(String sign) {
		String hql = "from Listing as listing where lower(listing.sign) = lower(:sign)";
		Listing listing = (Listing) getSession().createQuery(hql).setParameter("sign", sign).uniqueResult();
		if (listing != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public Listing getListingBySign(String sign) {
		String hql = "from Listing as listing where lower(listing.sign) = lower(:sign)";
		Listing listing = (Listing) getSession().createQuery(hql).setParameter("sign", sign).uniqueResult();
		return listing;
	}
	
	@SuppressWarnings("unchecked")
	public List<Listing> getListingTree() {
		String hql = "from Listing as listing where listing.parent is null order by listing.orderList asc";
		List<Listing> listingTreeList = getSession().createQuery(hql).list();
		initializeListingList(listingTreeList);
		return listingTreeList;
	}
	/**
	 * 根据标识Key 获取子级列表
	 * @param sign
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Listing> getChildListingBySignList(String sign){
		Criteria criteria = getSession().createCriteria(Listing.class);
		criteria.createAlias("parent", "parent");
		criteria.add(Restrictions.eq("isEnabled", true));
		criteria.add(Restrictions.eq("parent", getListingBySign(sign)));
		criteria.addOrder(Order.asc("orderList"));
		return criteria.list();
	}

	/**
	 * 根据父级sign 和子级keyValue查询子级name
	 * @param map
	 * @return
	 */
	public String findChildListingByKeyValue(String sign,String keyValue){
		Criteria criteria = getSession().createCriteria(Listing.class);
		String name="";
		if(StringUtils.isNotEmpty(sign)){
			criteria.createAlias("parent", "parent");
			criteria.add(Restrictions.eq("isEnabled", true));
			criteria.add(Restrictions.eq("keyValue", keyValue));
			criteria.add(Restrictions.eq("parent", getListingBySign(sign)));
			
			if(criteria.list()!= null && criteria.list().size()>0){
				name=((Listing)criteria.list().get(0)).getName();
			}
		}else{
			criteria.add(Restrictions.eq("keyValue", keyValue));
			if(criteria.list()!= null && criteria.list().size()>0){
				name=((Listing)criteria.list().get(0)).getName();
			}
		}
		
		return name;
	}
	
	/**
	 * 根据父级sign 和子级name查询子级keyValue
	 * @param map
	 * @return
	 */
	public String findChildListingByname(String sign,String name){
		Criteria criteria = getSession().createCriteria(Listing.class);
		criteria.createAlias("parent", "parent");
		criteria.add(Restrictions.eq("isEnabled", true));
		criteria.add(Restrictions.eq("name", name));
		criteria.add(Restrictions.eq("parent", getListingBySign(sign)));
		String keyValue="";
		if(criteria.list()!= null && criteria.list().size()>0){
			keyValue=((Listing)criteria.list().get(0)).getName();
		}
		return keyValue;
	}
	
	
	// 递归实例化商品分类对象
	private void initializeListingList(List<Listing> listingList) {
		for (Listing listing : listingList) {
			Set<Listing> children = listing.getChildren();
			if (children != null && children.size() > 0) {
				if (!Hibernate.isInitialized(children)) {
					Hibernate.initialize(children);
				}
				initializeListingList(new ArrayList<Listing>(children));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Listing> getRootListingList(Integer maxResults) {
		String hql = "from Listing as listing where listing.parent is null order by listing.orderList asc";
		Query query = getSession().createQuery(hql);
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Listing> getParentListingList(Listing listing, boolean isContainParent, Integer maxResults) {
		Query query = null;
		if (listing != null) {
			if (isContainParent) {
				if (listing.getParent() == null) {
					return null;
				}
				String parentPath = StringUtils.substringBeforeLast(listing.getPath(), Listing.PATH_SEPARATOR);
				String[] ids = parentPath.split(Listing.PATH_SEPARATOR);
				String hql = "from Listing as listing where listing.id in(:ids) order by listing.grade asc, listing.orderList asc";
				query = getSession().createQuery(hql);
				query.setParameterList("ids", ids);
			} else {
				List<Listing> parentListingList = null;
				Listing parent = listing.getParent();
				if (maxResults > 0 && parent != null) {
					parentListingList = new ArrayList<Listing>();
					parentListingList.add(parent);
				}
				return parentListingList;
			}
		} else {
			String hql = "from Listing as listing order by listing.grade asc, listing.orderList asc";
			query = getSession().createQuery(hql);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Listing> getChildrenListingList(Listing listing, boolean isContainChildren, Integer maxResults) {
		Query query = null;
		if (listing != null) {
			if (isContainChildren) {
				String hql = "from Listing as listing where listing.path like :path order by listing.grade asc, listing.orderList asc";
				query = getSession().createQuery(hql);
				query.setParameter("path", listing.getPath() + Listing.PATH_SEPARATOR + "%");
			} else {
				String hql = "from Listing as listing where listing.parent = :listing order by listing.grade asc, listing.orderList asc";
				query = getSession().createQuery(hql);
				query.setParameter("listing", listing);
			}
		} else {
			String hql = "from Listing as listing order by listing.grade asc, listing.orderList asc";
			query = getSession().createQuery(hql);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
	// 自动设置path、grade
	@Override
	public Integer save(Listing listing) {
		listing.setPath(listing.getName());
		listing.setGrade(0);
		super.save(listing);
		fillListing(listing);
		super.update(listing);
		return listing.getId();
	}
	
	// 自动设置path、grade
	@Override
	public void update(Listing listing) {
		fillListing(listing);
		super.update(listing);
		List<Listing> childrenListingList = getChildrenListingList(listing, true, null);
		if (childrenListingList != null) {
			for (int i = 0; i < childrenListingList.size(); i ++) {
				Listing childrenCategory = childrenListingList.get(i);
				fillListing(childrenCategory);
				super.update(childrenCategory);
				if(i % 20 == 0) {
					flush();
					clear();
				}
			}
		}
	}
	
	private void fillListing(Listing listing) {
		Listing parent = listing.getParent();
		if (parent == null) {
			listing.setPath(listing.getId().toString());
		} else {
			listing.setPath(parent.getPath() + Listing.PATH_SEPARATOR + listing.getId());
		}
		listing.setGrade(listing.getPath().split(Listing.PATH_SEPARATOR).length - 1);
	}


	public BigDecimal findSumMoneyBySpread(Integer id) {
	
		String sql="SELECT sum(t.money) FROM fy_user_award_detail as t WHERE t.user_id="+id+" and t.type='tui_detail_award'  ";

		Query query = getSession().createSQLQuery(sql).addScalar("sum(t.money)", Hibernate.BIG_DECIMAL);
		return (BigDecimal) query.uniqueResult();
	}

}
