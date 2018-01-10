package net.qmdboss.action.admin;

import net.qmdboss.entity.RechargeConfig;
import net.qmdboss.service.RechargeConfigService;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台Action类 - 菜单
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXA30F3B3812CF2657A40E96C41242BF06
 * ============================================================================
 */

@ParentPackage("admin")
public class MenuAction extends BaseAdminAction {

	private static final long serialVersionUID = 6465259795235263493L;

	@Resource(name = "rechargeConfigServiceImpl")
	private RechargeConfigService rechargeConfigService;
	// 商店配置
	public String setting() {
		return "setting";
	}
	
	// 会员
	public String member() {
		return "member";
	}	
	
	// 资金账户
	public String account() {
		return "account";
	}
	
	// 商品
	public String goods() {
		return "goods";
	}
	
	// 页面管理
	public String content() {
		return "content";
	}
	
	// 订单管理
	public String order() {
		return "order";
	}
	// 贷款管理
	public String credit() {
		return "credit";
	}
	// 放款管理
	public String fangkuan() {
		return "fangkuan";
	}
	
	// 数据统计
	public String census() {
		return "census";
	}

	// 服务商
	public String service() {
		return "service";
	}
	
	// 管理员
	public String admin() {
		return "admin";
	}

	// 主体
	public String main() {
		return "main";
	}

	// 头部
	public String header() {
		return "header";
	}

	//获取所有支付方式
	public List<RechargeConfig> getRechargeConfigList(){
		return rechargeConfigService.getRechargeConfigList();
	}
}