package net.qmdboss.service;

import net.qmdboss.DTO.UserHongbaoBossDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.entity.UserHongbao;

import java.util.List;
import java.util.Map;

public interface UserHongbaoService  extends BaseService<UserHongbao, Integer> {

	public void saveLosts(List<UserHongbao> hbList);
	
	public void verifyHongbao(UserHongbao hongbao);
	/**
	 * 分页查询用户红包列表
	 * @param pager
	 * @param map
	 * @return
	 * @author zdl
	 */
	public Pager userHongbaoPage(Pager pager ,Map<String,Object> map);
	
	//批量保存红包
	public String savePLHongbao(List<String> l,List<String> hbMoneyList ,List<Integer> hbEndTimeList ,List<Integer> hbLimitMaxMoney ,List<Integer> hbLimitStartList,List<Integer> hbLimitEndList,String name);

	/**
	 * 用于导出 红包list
	 * @param map
	 * @return
	 */
	public List<UserHongbaoBossDTO> findUserHongbaoList(Map<String, Object> map);
	
}
