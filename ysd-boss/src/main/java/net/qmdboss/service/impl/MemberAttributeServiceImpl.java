package net.qmdboss.service.impl;

import net.qmdboss.dao.MemberAttributeDao;
import net.qmdboss.entity.MemberAttribute;
import net.qmdboss.service.MemberAttributeService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import javax.annotation.Resource;
import java.util.List;

/**
 * Service实现类 - 会员属性
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX193AD837EEF756A1244E2A1816A337B9
 * ============================================================================
 */

@Service("memberAttributeServiceImpl")
public class MemberAttributeServiceImpl extends BaseServiceImpl<MemberAttribute, Integer> implements MemberAttributeService {

	@Resource(name = "memberAttributeDaoImpl")
	private MemberAttributeDao memberAttributeDao;
	
	@Resource(name = "memberAttributeDaoImpl")
	public void setBaseDao(MemberAttributeDao memberAttributeDao) {
		super.setBaseDao(memberAttributeDao);
	}
	
	@Transactional(readOnly = true)
	public Integer getUnusedPropertyIndex() {
		return memberAttributeDao.getUnusedPropertyIndex();
	}
	
	@Cacheable(modelId = "memberAttributeCaching")
	@Transactional(readOnly = true)
	public List<MemberAttribute> getMemberAttributeList() {
		List<MemberAttribute> memberAttributeList = memberAttributeDao.getMemberAttributeList();
		if (memberAttributeList != null) {
			for (MemberAttribute memberAttribute : memberAttributeList) {
				Hibernate.initialize(memberAttribute);
			}
		}
		return memberAttributeList;
	}

	@Override
	@CacheFlush(modelId = "memberAttributeFlushing")
	public void delete(MemberAttribute memberAttribute) {
		super.delete(memberAttribute);
	}

	@Override
	@CacheFlush(modelId = "memberAttributeFlushing")
	public void delete(Integer id) {
		super.delete(id);
	}

	@Override
	@CacheFlush(modelId = "memberAttributeFlushing")
	public void delete(Integer[] ids) {
		super.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "memberAttributeFlushing")
	public Integer save(MemberAttribute memberAttribute) {
		return super.save(memberAttribute);
	}

	@Override
	@CacheFlush(modelId = "memberAttributeFlushing")
	public void update(MemberAttribute memberAttribute) {
		super.update(memberAttribute);
	}

}