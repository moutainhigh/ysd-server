package net.qmdboss.service.impl;

import net.qmdboss.dao.LogDao;
import net.qmdboss.entity.Log;
import net.qmdboss.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Service实现类 - 日志
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX1F694A92A7CFA4B05E87616ADCA7D169
 * ============================================================================
 */

@Service("logServiceImpl")
public class LogServiceImpl extends BaseServiceImpl<Log, Integer> implements LogService {

	@Resource(name = "logDaoImpl")
	public void setBaseDao(LogDao logDao) {
		super.setBaseDao(logDao);
	}

}