package net.qmdboss.dao.impl;

import net.qmdboss.dao.BrandDao;
import net.qmdboss.entity.Brand;
import net.qmdboss.entity.Goods;
import net.qmdboss.entity.GoodsType;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Dao实现类 - 品牌
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX64B5A05594CB1C3B74C8A9B4F94F5991
 * ============================================================================
 */

@Repository("brandDaoImpl")
public class BrandDaoImpl extends BaseDaoImpl<Brand, Integer> implements BrandDao {
	
	// 关联处理
	@Override
	public void delete(Brand brand) {
		Set<Goods> goodsSet = brand.getGoodsSet();
		if (goodsSet != null) {
			for (Goods goods : goodsSet) {
				goods.setBrand(null);
			}
		}
		
		Set<GoodsType> goodsTypeSet = brand.getGoodsTypeSet();
		if (goodsTypeSet != null) {
			for (GoodsType goodsType : goodsTypeSet) {
				goodsType.getBrandSet().remove(brand);
			}
		}
		
		super.delete(brand);
	}

	// 关联处理
	@Override
	public void delete(Integer id) {
		Brand brand = load(id);
		this.delete(brand);
	}

	// 关联处理
	@Override
	public void delete(Integer[] ids) {
		for (Integer id : ids) {
			Brand brand = load(id);
			this.delete(brand);
		}
	}
	
}