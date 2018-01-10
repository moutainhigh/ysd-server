package net.qmdboss.dao;

import net.qmdboss.DTO.UserHongbaoBossDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.entity.UserHongbao;

import java.util.List;
import java.util.Map;

public interface UserHongbaoDao extends BaseDao<UserHongbao, Integer> {

	public List<UserHongbao> getUserHongbaoList(Map<String ,Object> map);

	public void saveLosts(List<UserHongbao> hbList);
	
	/**
	 * 分页查询用户红包列表
	 * @param pager
	 * @param map
	 * @return
	 * @author zdl
	 */
	public Pager userHongbaoPage(Pager pager ,Map<String,Object> map);

	public List<UserHongbaoBossDTO> findUserHongbaoList(Map<String, Object> map);
}
