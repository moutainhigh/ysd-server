package net.qmdboss.service.impl;

import net.qmdboss.dao.RoleDao;
import net.qmdboss.entity.Role;
import net.qmdboss.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Service实现类 - 角色
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX5780B32776CB0A6FF3A3530C4BC96D54
 * ============================================================================
 */

@Service("roleServiceImpl")
public class RoleServiceImpl extends BaseServiceImpl<Role, Integer> implements RoleService {
	
	@Resource(name = "roleDaoImpl")
	RoleDao roleDao;

	@Resource(name = "roleDaoImpl")
	public void setBaseDao(RoleDao roleDao) {
		super.setBaseDao(roleDao);
	}

}