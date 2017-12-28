package net.qmdboss.service.impl;

import net.qmdboss.dao.MemberDao;
import net.qmdboss.dao.OrderDao;
import net.qmdboss.entity.Goods;
import net.qmdboss.entity.Member;
import net.qmdboss.entity.Order;
import net.qmdboss.entity.Order.OrderStatus;
import net.qmdboss.service.MemberService;
import net.qmdboss.util.CommonUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Service实现类 - 会员
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX9A3ACBDD2D3A5E96E54D057769342EAF
 * ============================================================================
 */

@Service("memberServiceImpl")
public class MemberServiceImpl extends BaseServiceImpl<Member, Integer> implements MemberService {

	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	@Resource(name = "orderDaoImpl")
	private OrderDao orderDao;

	@Resource(name = "memberDaoImpl")
	public void setBaseDao(MemberDao memberDao) {
		super.setBaseDao(memberDao);
	}
	
	@Transactional(readOnly = true)
	public boolean isExistByUsername(String username) {
		return memberDao.isExistByUsername(username);
	}
	
	@Transactional(readOnly = true)
	public Member getMemberByUsername(String username) {
		return memberDao.getMemberByUsername(username);
	}
	
	public boolean verifyMember(String username, String password) {
		Member member = getMemberByUsername(username);
		if (member != null && member.getPassword().equals(DigestUtils.md5Hex(password))) {
			return true;
		} else {
			return false;
		}
	}
	
	public String buildPasswordRecoverKey() {
		return System.currentTimeMillis() + Member.PASSWORD_RECOVER_KEY_SEPARATOR + CommonUtil.getUUID() + DigestUtils.md5Hex(String.valueOf(Math.random() * 1000000000));
	}
	
	public Date getPasswordRecoverKeyBuildDate(String passwordRecoverKey) {
		long time = Long.valueOf(StringUtils.substringBefore(passwordRecoverKey, Member.PASSWORD_RECOVER_KEY_SEPARATOR));
		return new Date(time);
	}
	
	@Transactional(readOnly = true)
	public boolean isPurchased(Member member, Goods goods) {
		List<Order> orderList = orderDao.getOrderList(member, OrderStatus.completed);
		for (Order order : orderList) {
			List<String> goodsIdList = order.getGoodsIdList();
			if (goodsIdList != null) {
				for (String goodsId : goodsIdList) {
					if (StringUtils.equals(goods.getId().toString(), goodsId)) {
						return true;
					}
				}
			}
			
		}
		return false;
	}

}