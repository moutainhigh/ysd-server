package net.qmdboss.dao.impl;

import net.qmdboss.dao.SettingDao;
import net.qmdboss.entity.Setting;
import org.springframework.stereotype.Repository;

@Repository("settingDaoImpl")
public class SettingDaoImpl  extends BaseDaoImpl<Setting, Integer> implements SettingDao {

}
