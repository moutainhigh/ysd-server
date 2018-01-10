package net.qmdboss.service;

import net.qmdboss.entity.Setting;

public interface SettingService extends BaseService<Setting, Integer>{

	//ServletContextEvent sce
	public void replaceSetting();
}
