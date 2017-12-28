package net.qmdboss.action.admin;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import net.qmdboss.entity.Listing;
import net.qmdboss.service.CacheService;
import net.qmdboss.service.ListingService;
import net.qmdboss.util.ImageUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * 后台Action类 - 列表数据分类
 *
 * @author xsf
 */

/**
 * 后台Action类 - 分类列表
 * @author xsf
 *
 */

@ParentPackage("admin")
public class ListingAction extends BaseAdminAction {

	private static final long serialVersionUID = 5997555586236539237L;
	private Integer parentId;
	private Listing listing;
	private File imgFile;// 上传图片文件
	private String flag;//标记
	
	@Resource(name = "listingServiceImpl")
	private ListingService listingService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	
	// 是否已存在标识 ajax验证
	public String checkSign() {
		String oldSign = getParameter("oldValue");
		String newSign = listing.getSign();
		if (listingService.isUniqueBySign(oldSign, newSign)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}

	// 添加
	public String add() {
		return INPUT;
	}

	//添加充值银行
	public String addRechargeBank(){
		return "recharge_bank_input";
	}
	
	// 编辑
	public String edit() {
		listing = listingService.load(id);
		return INPUT;
	}
	// 编辑充值银行
	public String editRechargeBank() {
		listing = listingService.load(id);
		return "recharge_bank_input";
	}
	
	// 列表
	public String list() {
		return LIST;
	}

	//充值银行账户列表
	public String rechargeList(){
		return "bank_list";
	}
	
	
	// 删除
	public String delete() {
		Listing listing = listingService.load(id);
		Set<Listing> childrenListingList = listing.getChildren();
		if (childrenListingList != null && childrenListingList.size() > 0) {
			addActionError("此分类存在下级分类,删除失败!");
			return ERROR;
		}
//		Set<Article> articleSet = Listing.getArticleSet();
//		if (articleSet != null && articleSet.size() > 0) {
//			addActionError("此分类下存在文章,删除失败!");
//			return ERROR;
//		}
		listingService.delete(id);
		logInfo = "删除分类: " + listing.getName();
		
		cacheService.flushListingPageCache(getRequest());
		
		redirectUrl = "listing!list.action";
		return SUCCESS;
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "listing.name", message = "分类名称不允许为空!"),
			@RequiredStringValidator(fieldName = "listing.sign", message = "标识不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "listing.orderList", min = "0", message = "排序必须为零或正整数!")
		},
		regexFields = {
			@RegexFieldValidator(fieldName = "listing.sign", regexExpression = "^\\w+$", message = "标识只允许包含英文、数字和下划线!") 
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (listingService.isExistBySign(listing.getSign())) {
			addActionError("标识已存在!");
			return ERROR;
		}
		if ( parentId != null) {
			Listing parent = listingService.load(parentId);
			listing.setParent(parent);
		} else {
			listing.setParent(null);
		}
		if(imgFile != null){
			String uploadPath = ImageUtil.copyImageFile(getServletContext(), imgFile);
			listing.setLogo(uploadPath);
		}
		
		listingService.save(listing);
		logInfo = "添加分类: " + listing.getName();
		
		cacheService.flushListingPageCache(getRequest());
		if(StringUtils.isNotEmpty(flag)){
			redirectUrl = "listing!rechargeList.action";
		}else{
			redirectUrl = "listing!list.action";
		}
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "listing.name", message = "分类名称不允许为空!"),
			@RequiredStringValidator(fieldName = "listing.sign", message = "标识不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "listing.orderList", min = "0", message = "排序必须为零或正整数!")
		},
		regexFields = {
			@RegexFieldValidator(fieldName = "listing.sign", regexExpression = "^\\w+$", message = "标识只允许包含英文、数字和下划线!") 
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		Listing persistent = listingService.load(id);
		if (!listingService.isUniqueBySign(persistent.getSign(), listing.getSign())) {
			addActionError("标识已存在!");
			return ERROR;
		}

		if(imgFile != null){
			String uploadPath = ImageUtil.copyImageFile(getServletContext(), imgFile);
			persistent.setLogo(uploadPath);
		}
		BeanUtils.copyProperties(listing, persistent, new String[]{"id", "createDate", "modifyDate","logo", "path", "parent", "children"});
		listingService.update(persistent);
		logInfo = "编辑分类: " + listing.getName();
		
		cacheService.flushListingPageCache(getRequest());
		
		if(StringUtils.isNotEmpty(flag)){
			redirectUrl = "listing!rechargeList.action";
		}else{
			redirectUrl = "listing!list.action";
		}
		return SUCCESS;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Listing getListing() {
		return listing;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
	}

	// 获取分类列表树
	public List<Listing> getListingTreeList() {
		return listingService.getListingTreeList();
	}

	// 获取充值银行列表
	public List<Listing> getListingChildrenRechargeBankList(){
		return listingService.getChildListingBySignList("recharge_bank");
	}
	
	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
