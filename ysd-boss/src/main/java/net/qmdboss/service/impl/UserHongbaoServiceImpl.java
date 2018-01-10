package net.qmdboss.service.impl;

import net.qmdboss.DTO.UserDTO;
import net.qmdboss.DTO.UserHongbaoBossDTO;
import net.qmdboss.bean.Pager;
import net.qmdboss.dao.UserAccountDao;
import net.qmdboss.dao.UserAwardDetailDao;
import net.qmdboss.dao.UserDao;
import net.qmdboss.dao.UserHongbaoDao;
import net.qmdboss.entity.User;
import net.qmdboss.entity.UserAccount;
import net.qmdboss.entity.UserAwardDetail;
import net.qmdboss.entity.UserHongbao;
import net.qmdboss.service.UserHongbaoService;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userHongbaoServiceImpl")
public class UserHongbaoServiceImpl  extends BaseServiceImpl<UserHongbao, Integer> implements UserHongbaoService{
	@Resource(name="userHongbaoDaoImpl")
	private UserHongbaoDao userHongbaoDao;
	
	@Resource(name="userDaoImpl")
	private UserDao userDao;
	@Resource(name = "userAccountDaoImpl")
	private UserAccountDao userAccountDao;
	@Resource(name = "userAwardDetailDaoImpl")
	private UserAwardDetailDao userAwardDetailDao;
	
	@Resource(name = "userHongbaoDaoImpl")
	public void setBaseDao(UserHongbaoDao userHongbaoDao) {
		super.setBaseDao(userHongbaoDao);
	}
	

	public void saveLosts(List<UserHongbao> hbList){
		userHongbaoDao.saveLosts(hbList);
	}
	

	public void verifyHongbao(UserHongbao hongbao){
		
		userHongbaoDao.update(hongbao);
		UserAccount userAccount0 = userAccountDao.loadLockTable(hongbao.getUser());
		userAccount0.setAwardMoney(userAccount0.getAwardMoney().add(hongbao.getMoney()));//增加红包账户资金
		userAccountDao.update(userAccount0);
		// 奖励账户  资金记录
		UserAwardDetail uad = new UserAwardDetail();
		User u=userDao.load(hongbao.getUser().getId());
		uad.setUser(u);// 用户ID
		uad.setType("offline_reward_hong_bao");// 类型（同资金记录）
		uad.setMoney(hongbao.getMoney());// 操作金额
		uad.setAwardMoney(userAccount0.getAwardMoney());// 奖励账户
		uad.setSignFlg(1);
		uad.setRemark(hongbao.getName());// 备注
		uad.setRelateKey("user_id");
		uad.setRelateTo(hongbao.getUser().getId());
		userAwardDetailDao.save(uad);
	}


	@Override
	public Pager userHongbaoPage(Pager pager, Map<String, Object> map) {
		return userHongbaoDao.userHongbaoPage(pager, map);
	}
	
	@Resource(name="sessionFactory")
	SessionFactory factory;
	
	public String savePLHongbao(List<String> userIdList,List<String> hbMoneyList ,List<Integer> hbEndTimeList ,List<Integer> hbLimitMaxMoney ,List<Integer> hbLimitStartList,List<Integer> hbLimitEndList,String name){
		

		Map map=new HashMap();
		String x=userIdList.get(0);
		if(x.length()==11){
			map.put("key", "username");
		}else{
			map.put("key", "ids");
		}
		
		map.put("listA", userIdList);
		List<UserDTO> listU=userDao.selectListByIdsOrUsername(map);

		
		List <String>lll=new ArrayList<String>();
		
		for(int i=0;i<userIdList.size();i++){
			for(int j=0;j<listU.size();j++){
				if(listU.get(j).getUsername().equals(userIdList.get(i))){
					lll.add(listU.get(j).getId().toString());
				}
				
			}
			
		}
		
		userDao.savePL(lll,hbMoneyList, hbEndTimeList , hbLimitMaxMoney , hbLimitStartList, hbLimitEndList, name);
		//userDao.savePL(listU,hbMoneyList, hbEndTimeList , hbLimitMaxMoney , hbLimitStartList, hbLimitEndList, name);
		

		return null;
	}


	
	public List<UserHongbaoBossDTO> findUserHongbaoList(Map<String, Object> map) {
		
		return userHongbaoDao.findUserHongbaoList(map);
	}
	
	
}
