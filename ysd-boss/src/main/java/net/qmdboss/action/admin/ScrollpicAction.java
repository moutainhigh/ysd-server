package net.qmdboss.action.admin;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import net.qmdboss.entity.Scrollpic;
import net.qmdboss.service.ScrollpicService;
import net.qmdboss.util.ImageUtil;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@ParentPackage("admin")
public class ScrollpicAction extends BaseAdminAction {

	private static final long serialVersionUID = -7358041478830205576L;
	
	private Scrollpic scrollpic;
	private List<Scrollpic> scrollpicList;
	private File file;
	@Resource(name = "scrollpicServiceImpl")
	private ScrollpicService scrollpicService;
	

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		scrollpic = scrollpicService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = scrollpicService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		scrollpicService.delete(ids);
		redirectUrl = "scrollpic!list.action";
		return ajax(Status.success, "删除成功!");
	}

	
	// 保存
		@Validations(
			requiredStrings = { 
				@RequiredStringValidator(fieldName = "scrollpic.name", message = "滚动图片名称不允许为空!"),
				@RequiredStringValidator(fieldName = "scrollpic.url", message = "滚动图片不允许为空!")
			},
			requiredFields = {
				@RequiredFieldValidator(fieldName = "scrollpic.type", message = "类型不能为空！")
			},
			intRangeFields = {
				@IntRangeFieldValidator(fieldName = "scrollpic.orderList", min = "0", message = "排序必须为零或正整数!")
			}
		)
		@InputConfig(resultName = "error")
		public String save() {
			if (file != null) {
				String filePath = ImageUtil.copyImageFile(getServletContext(), file);
				scrollpic.setImg(filePath);
			} else {
				addActionError("滚动图片不能为空");
				return ERROR;
			}
			scrollpicService.save(scrollpic);
			redirectUrl = "scrollpic!list.action";
			return SUCCESS;
		}

		// 更新
		@Validations(
			requiredStrings = { 
				@RequiredStringValidator(fieldName = "scrollpic.name", message = "滚动图片名称不允许为空!"),
				@RequiredStringValidator(fieldName = "scrollpic.url", message = "滚动图片地址不允许为空!")
			}, 
			requiredFields = {
				@RequiredFieldValidator(fieldName = "scrollpic.type", message = "类型不能为空！")
			},
			intRangeFields = {
				@IntRangeFieldValidator(fieldName = "scrollpic.orderList", min = "0", message = "排序必须为零或正整数!")
			}
		)
		@InputConfig(resultName = "error")
		public String update() {
			Scrollpic persistent = scrollpicService.load(id);
			if (file != null) {
				String logoPath = ImageUtil.copyImageFile(getServletContext(), file);
				persistent.setImg(logoPath);
			}
			BeanUtils.copyProperties(scrollpic, persistent, new String[]{"id", "createDate", "modifyDate","img"});
			scrollpicService.update(persistent);
			redirectUrl = "scrollpic!list.action";
			return SUCCESS;
		}

		public Scrollpic getScrollpic() {
			return scrollpic;
		}

		public void setScrollpic(Scrollpic scrollpic) {
			this.scrollpic = scrollpic;
		}

		public List<Scrollpic> getScrollpicList() {
			return scrollpicList;
		}

		public void setScrollpicList(List<Scrollpic> scrollpicList) {
			this.scrollpicList = scrollpicList;
		}

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}

		public ScrollpicService getScrollpicService() {
			return scrollpicService;
		}

		public void setScrollpicService(ScrollpicService scrollpicService) {
			this.scrollpicService = scrollpicService;
		}

	
	
	
	
	
}
