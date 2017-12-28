package net.qmdboss.action.admin;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.*;
import net.qmdboss.bean.Setting.*;
import net.qmdboss.entity.Setting;
import net.qmdboss.service.MailService;
import net.qmdboss.service.SettingService;
import net.qmdboss.util.ImageUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 后台Action类 - 系统配置
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXCB0831367FDD7078CE4312A6DBF9737D
 * ============================================================================
 */

@ParentPackage("admin")
public class SettingAction extends BaseAdminAction {

	private static final long serialVersionUID = -6200425957233641240L;

	private Setting setting;
	private String smtpToMail;
	private File logo;
	private File defaultBigGoodsImage;
	private File defaultSmallGoodsImage;
	private File defaultThumbnailGoodsImage;
	private File watermarkImage;
	
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	@Resource(name = "settingServiceImpl")
	private SettingService settingService;
//	@Resource(name = "jobServiceImpl")
//	private JobService jobService;
//	@Resource(name = "cacheServiceImpl")
//	private CacheService cacheService;
	
	// 发送SMTP测试邮件
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "setting.smtpFromMail", message = "发件人邮箱不允许为空!"),
			@RequiredStringValidator(fieldName = "setting.smtpHost", message = "SMTP服务器地址不允许为空!"),
			@RequiredStringValidator(fieldName = "setting.smtpUsername", message = "SMTP用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "setting.smtpPassword", message = "SMTP密码不允许为空!"),
			@RequiredStringValidator(fieldName = "smtpToMail", message = "收件人邮箱不允许为空!")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "setting.smtpPort", message = "SMTP服务器端口不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "setting.smtpPort", min = "0", message = "SMTP端口必须为零正整数!")
		},
		emails = {
			@EmailValidator(fieldName = "setting.smtpFromMail", message = "发件人邮箱格式错误!"),
			@EmailValidator(fieldName = "smtpToMail", message = "收件人邮箱格式错误!")
		}
	)
	
	@InputConfig(resultName = "ajaxError")
	public String ajaxSendSmtpTest() {
		try {
			mailService.sendSmtpTestMail(setting.getSmtpFromMail(), setting.getSmtpHost(), setting.getSmtpPort(), setting.getSmtpUsername(), setting.getSmtpPassword(), smtpToMail);
		} catch (MailAuthenticationException e) {
            e.printStackTrace();
			return ajax(Status.error, "权限验证失败,请检查SMTP用户名、密码!");
		} catch (MailSendException e) {
            e.printStackTrace();
			return ajax(Status.error, "邮件发送失败,请检查发件人邮箱、SMTP服务器地址、端口!");
		} catch (Exception e) {
            e.printStackTrace();
			return ajax(Status.error, "邮件发送失败!");
		}
		return ajax(Status.success, "测试邮件发送成功,请查收邮件!");
	}

	// 编辑
	public String edit() {
		setting = settingService.load(1);
		return INPUT;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "setting.name", message = "网站名称不允许为空!"),
			@RequiredStringValidator(fieldName = "setting.url", message = "网站网址不允许为空!"),
			@RequiredStringValidator(fieldName = "setting.smtpFromMail", message = "发件人邮箱不允许为空!"),
			@RequiredStringValidator(fieldName = "setting.smtpHost", message = "SMTP服务器地址不允许为空!"),
			@RequiredStringValidator(fieldName = "setting.smtpUsername", message = "SMTP用户名不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "setting.isLoginFailureLock", message = "是否开启自动锁定账号功能不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.loginFailureLockCount", message = "连续登录失败最大次数不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.loginFailureLockTime", message = "自动解锁时间不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.smtpPort", message = "SMTP服务器端口不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.watermarkAlpha", message = "水印透明度不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "setting.loginFailureLockCount", min = "1", message = "连续登录失败最大次数必须为正整数!"),
			@IntRangeFieldValidator(fieldName = "setting.loginFailureLockTime", min = "0", message = "自动解锁时间必须为零或正整数!"),
			@IntRangeFieldValidator(fieldName = "setting.smtpPort", min = "0", message = "SMTP端口必须为零或正整数!"),
			@IntRangeFieldValidator(fieldName = "setting.watermarkAlpha", min = "0", max="100", message = "水印透明度取值范围在${min}-${max}之间!")
		},
		emails = {
			@EmailValidator(fieldName = "setting.email", message = "E-mail格式错误!"),
			@EmailValidator(fieldName = "setting.smtpFromMail", message = "发件人邮箱格式错误!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		Setting persistent = settingService.get(id);
		StringBuffer logInfoStringBuffer = new StringBuffer();
		logInfoStringBuffer.append("更新系统设置");
		if (StringUtils.isNotEmpty(setting.getSmtpPassword())) {
			persistent.setSmtpPassword(setting.getSmtpPassword());
		}
//		if (!setting.getIsLoginFailureLock()) {
//			persistent.setLoginFailureLockCount(3);
//			persistent.setLoginFailureLockTime(10);
//		}
		if (logo != null) {
			String shopLogoPath = ImageUtil.copyImageFile(getServletContext(), logo);
			persistent.setLogoPath(shopLogoPath);
		}
		
		if (watermarkImage != null) {
			String watermarkImagePath = ImageUtil.copyImageFile(getServletContext(), watermarkImage);
			persistent.setWatermarkImagePath(watermarkImagePath);
		}
		if(StringUtils.isNotEmpty(setting.getSmtpPassword())){
			persistent.setSmtpPassword(setting.getSmtpPassword());
		}
		BeanUtils.copyProperties(setting, persistent, new String[] {"id", "createDate", "modifyDate","watermarkImagePath","logoPath","smtpPassword"});
//		SettingUtil.updateSetting(persistent);
		settingService.update(persistent);
		
		logInfo = logInfoStringBuffer.toString();
		//更新值
//		settingService.replaceSetting();
//		cacheService.flushAllPageCache(getRequest());
//		jobService.buildIndexHtml();
//		jobService.buildLoginHtml();
//		jobService.buildRegisterAgreementHtml();
//		jobService.buildAdminJs();
//		jobService.buildShopJs();
//		jobService.buildArticleContentHtml();
//		jobService.buildGoodsContentHtml();
		
		redirectUrl = "setting!edit.action";
		return SUCCESS;
	}
	
	// 获取货币种类集合
	public List<CurrencyType> getCurrencyTypeList() {
		return Arrays.asList(CurrencyType.values());
	}
	
	// 获取小数位精确方式集合
	public List<RoundType> getRoundTypeList() {
		return Arrays.asList(RoundType.values());
	}
	
	// 获取库存预占时间点集合
	public List<StoreFreezeTime> getStoreFreezeTimeList() {
		return Arrays.asList(StoreFreezeTime.values());
	}

	// 获取水印位置集合
	public List<WatermarkPosition> getWatermarkPositionList() {
		return Arrays.asList(WatermarkPosition.values());
	}
	
	// 获取积分获取方式集合
	public List<ScoreType> getScoreTypeList() {
		return Arrays.asList(ScoreType.values());
	}
	
	// 获取运算符集合
	public List<Operator> getOperatorList() {
		return Arrays.asList(Operator.values());
	}


	public String getSmtpToMail() {
		return smtpToMail;
	}

	public void setSmtpToMail(String smtpToMail) {
		this.smtpToMail = smtpToMail;
	}

	public File getLogo() {
		return logo;
	}

	public void setLogo(File logo) {
		this.logo = logo;
	}

	public File getDefaultBigGoodsImage() {
		return defaultBigGoodsImage;
	}

	public void setDefaultBigGoodsImage(File defaultBigGoodsImage) {
		this.defaultBigGoodsImage = defaultBigGoodsImage;
	}

	public File getDefaultSmallGoodsImage() {
		return defaultSmallGoodsImage;
	}

	public void setDefaultSmallGoodsImage(File defaultSmallGoodsImage) {
		this.defaultSmallGoodsImage = defaultSmallGoodsImage;
	}

	public File getDefaultThumbnailGoodsImage() {
		return defaultThumbnailGoodsImage;
	}

	public void setDefaultThumbnailGoodsImage(File defaultThumbnailGoodsImage) {
		this.defaultThumbnailGoodsImage = defaultThumbnailGoodsImage;
	}

	public File getWatermarkImage() {
		return watermarkImage;
	}

	public void setWatermarkImage(File watermarkImage) {
		this.watermarkImage = watermarkImage;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	
	// 获取随机数
	public int getRandom() {
		return (int) (Math.random() * 100000);
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

}