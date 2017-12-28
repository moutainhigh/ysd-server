package net.qmdboss.action.admin;

import net.qmdboss.bean.Pager.Order;
import net.qmdboss.entity.PaymentConfig.PaymentFeeType;
import net.qmdboss.entity.RechargeConfig;
import net.qmdboss.service.RechargeConfigService;
import net.qmdboss.util.ImageUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 后台Action - 支付方式
 * @author Xsf
 *
 */
@ParentPackage("admin")
public class RechargeConfigAction  extends BaseAdminAction {

	private static final long serialVersionUID = 7606816665410516381L;
	
	private RechargeConfig rechargeConfig;
	private File file;
	
	@Resource(name = "rechargeConfigServiceImpl")
	private RechargeConfigService rechargeConfigService;
	
	/**
	 * 支付方式列表
	 * @return
	 */
	public String list(){
		if(StringUtils.isEmpty(pager.getOrderBy())){
			pager.setOrderBy("orderList");
			pager.setOrder(Order.asc);
		}
		pager = rechargeConfigService.findPager(pager);
		return LIST;
	}
	
	/*
	 * 是否存在支付方式ID
	 * 
	 */
	public String ajaxPaymentProductId(){
		String oldProductId = getParameter("oldProductId");
		String newProductId = rechargeConfig.getPaymentProductId();
		if(rechargeConfigService.isUniqueByProductId(oldProductId, newProductId)){
			return ajax("true");
		}else{
			return ajax("false");
		}
	}
	
	public String add(){
		return INPUT;
	}
	
	public String edit(){
		rechargeConfig = rechargeConfigService.load(id);
		return INPUT;
	}
	
	public String save(){
		if(rechargeConfigService.find(rechargeConfig.getPaymentProductId())!= null){
			addActionError("产品标识已存在!");
			return "error_ftl";
		}
		if (file != null) {
			String logoPath = ImageUtil.copyImageFile(getServletContext(), file);
			rechargeConfig.setLogoPath(logoPath);
		} 
		
		rechargeConfigService.save(rechargeConfig);
		logInfo = "添加支付方式:"+rechargeConfig.getName();
		redirectUrl="recharge_config!list.action";
		return SUCCESS;
	}
	public String update(){
		RechargeConfig persistent = rechargeConfigService.get(id);
		if(!rechargeConfigService.isUniqueByProductId(persistent.getPaymentProductId(), rechargeConfig.getPaymentProductId())){
			addActionError("支付标识已存在");
			return "error_ftl";
		}
		if (file != null) {
			String logoPath = ImageUtil.copyImageFile(getServletContext(), file);
			persistent.setLogoPath(logoPath);
		}
		BeanUtils.copyProperties(rechargeConfig, persistent, new String[] {"id", "createDate","logoPath"});
		
		rechargeConfigService.update(persistent);
		logInfo = "编辑支付方式:"+rechargeConfig.getName();
		redirectUrl="recharge_config!list.action";
		return SUCCESS;
	}

	
	// 获取支付手续费类型集合
	public List<PaymentFeeType> getPaymentFeeTypeList() {
		return Arrays.asList(PaymentFeeType.values());
	}

	public RechargeConfig getRechargeConfig() {
		return rechargeConfig;
	}

	public void setRechargeConfig(RechargeConfig rechargeConfig) {
		this.rechargeConfig = rechargeConfig;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
