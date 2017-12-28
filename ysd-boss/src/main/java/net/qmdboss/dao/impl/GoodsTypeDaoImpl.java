package net.qmdboss.dao.impl;

import net.qmdboss.dao.GoodsTypeDao;
import net.qmdboss.entity.Goods;
import net.qmdboss.entity.GoodsType;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Dao实现类 - 商品类型
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX98FDAF6620D898936043F11DC7A029CC
 * ============================================================================
 */

@Repository("goodsTypeDaoImpl")
public class GoodsTypeDaoImpl extends BaseDaoImpl<GoodsType, Integer> implements GoodsTypeDao {
	
	// 关联处理
	@Override
	public void delete(GoodsType goodsType) {
		Set<Goods> goodsSet = goodsType.getGoodsSet();
		int i = 0;
		for (Goods goods : goodsSet) {
			goods.setGoodsType(null);
			goods.setGoodsAttributeValueToNull();
			if(i % 20 == 0) {
				flush();
				clear();
			}
			i ++;
		}
		super.delete(goodsType);
	}

	// 关联处理
	@Override
	public void delete(Integer id) {
		GoodsType goodsType = super.load(id);
		this.delete(goodsType);
	}

	// 关联处理
	@Override
	public void delete(Integer[] ids) {
		for (Integer id : ids) {
			GoodsType goodsType = super.load(id);
			this.delete(goodsType);
		}
	}

}