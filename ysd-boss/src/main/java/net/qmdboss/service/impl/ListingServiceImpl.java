package net.qmdboss.service.impl;

import net.qmdboss.dao.ListingDao;
import net.qmdboss.entity.Listing;
import net.qmdboss.service.ListingService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Service实现类 - 列表分类
 * ============================================================================
 */

@Service("listingServiceImpl")
public class ListingServiceImpl extends BaseServiceImpl<Listing, Integer> implements ListingService {

	@Resource(name = "listingDaoImpl")
	private ListingDao listingDao;
	
	@Resource(name = "listingDaoImpl")
	public void setBaseDao(ListingDao ListingDao) {
		super.setBaseDao(ListingDao);
	}
	
	@Transactional(readOnly = true)
	public boolean isExistBySign(String sign) {
		return listingDao.isExistBySign(sign);
	}
	
	@Transactional(readOnly = true)
	public boolean isUniqueBySign(String oldSign, String newSign) {
		if (StringUtils.equalsIgnoreCase(oldSign, newSign)) {
			return true;
		} else {
			if (listingDao.isExistBySign(newSign)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	@Transactional(readOnly = true)
	public Listing getListingBySign(String sign) {
		return listingDao.getListingBySign(sign);
	}
	
	@Cacheable(modelId = "listingCaching")
	@Transactional(readOnly = true)
	public List<Listing> getListingTree() {
		return listingDao.getListingTree();
	}
	
	@Cacheable(modelId = "listingCaching")
	public List<Listing> getListingTreeList() {
		List<Listing> allListingList = this.getAllList();
		return recursivListingTreeList(allListingList, null, null);
	}
	
	// 递归父类排序分类树
	private List<Listing> recursivListingTreeList(List<Listing> allListingList, Listing p, List<Listing> temp) {
		if (temp == null) {
			temp = new ArrayList<Listing>();
		}
		for (Listing listing : allListingList) {
			Listing parent = listing.getParent();
			if ((p == null && parent == null) || (listing != null && parent == p)) {
				temp.add(listing);
				if (listing.getChildren() != null && listing.getChildren().size() > 0) {
					recursivListingTreeList(allListingList, listing, temp);
				}
			}
		}
		return temp;
	}
	
	@Cacheable(modelId = "listingCaching")
	@Transactional(readOnly = true)
	public List<Listing> getRootListingList(Integer maxResults) {
		List<Listing> rootListingList = listingDao.getRootListingList(maxResults);
		if (rootListingList != null) {
			for (Listing rootListing : rootListingList) {
				if (!Hibernate.isInitialized(rootListing)) {
					Hibernate.initialize(rootListing);
				}
			}
		}
		return rootListingList;
	}
	
	@Cacheable(modelId = "listingCaching")
	@Transactional(readOnly = true)
	public List<Listing> getParentListingList(Listing Listing, boolean isContainParent, Integer maxResults) {
		List<Listing> parentListingList = listingDao.getParentListingList(Listing, isContainParent, maxResults);
		if (parentListingList != null) {
			for (Listing parentListing : parentListingList) {
				if (!Hibernate.isInitialized(parentListing)) {
					Hibernate.initialize(parentListing);
				}
			}
		}
		return parentListingList;
	}
	
	@Cacheable(modelId = "listingCaching")
	@Transactional(readOnly = true)
	public List<Listing> getChildrenListingList(Listing Listing, boolean isContainChildren, Integer maxResults) {
		List<Listing> childrenListingList = listingDao.getChildrenListingList(Listing, isContainChildren, maxResults);
		if (childrenListingList != null) {
			for (Listing childrenListing : childrenListingList) {
				if (!Hibernate.isInitialized(childrenListing)) {
					Hibernate.initialize(childrenListing);
				}
			}
		}
		return childrenListingList;
	}
	
	@Cacheable(modelId = "listingCaching")
	public List<Listing> getListingPathList(Listing Listing) {
		List<Listing> ListingPathList = new ArrayList<Listing>();
		List<Listing> parentListingList = this.getParentListingList(Listing, true, null);
		if (parentListingList != null) {
			ListingPathList.addAll(parentListingList);
		}
		ListingPathList.add(Listing);
		return ListingPathList;
	}
	
	public List<Listing> getChildListingBySignList(String sign ){
		return listingDao.getChildListingBySignList(sign);
	}

	public String findChildListingByKeyValue(String sign,String keyValue){
		return listingDao.findChildListingByKeyValue(sign, keyValue);
	}
	
	
	
	
	@Override
	@CacheFlush(modelId = "listingFlushing")
	public void delete(Listing Listing) {
		listingDao.delete(Listing);
	}

	@Override
	@CacheFlush(modelId = "listingFlushing")
	public void delete(Integer id) {
		listingDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "listingFlushing")
	public void delete(Integer[] ids) {
		listingDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "listingFlushing")
	public Integer save(Listing Listing) {
		return listingDao.save(Listing);
	}

	@Override
	@CacheFlush(modelId = "listingFlushing")
	public void update(Listing Listing) {
		listingDao.update(Listing);
	}

	@Override
	public String findChildListingByname(String sign, String name) {
		
		return listingDao.findChildListingByKeyValue(sign, name);
	}

	@Override
	public BigDecimal findSumMoneyBySpread(Integer id) {
		
		return listingDao.findSumMoneyBySpread(id);
	}

	

}
