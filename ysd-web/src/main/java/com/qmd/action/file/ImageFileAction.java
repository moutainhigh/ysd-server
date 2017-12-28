package com.qmd.action.file;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.util.ImageUtil;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Action类 - 文件处理
 */

@Service("imageFileAction")
public class ImageFileAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8343756065215242709L;

//	private static final String[] imageFormatName = new String[] { "jpg",
//			"jpeg", "gif", "bmp", "png" };// 图片格式名称

	private File imageFile;// 上传图片文件
	private String  imageFileFileName;
	private String  imageFileContentType;

	// AJAX文件上传
	@Action(value = "/file/ajaxUploadImage")
	@InputConfig(resultName = "ajaxError")
	public String ajaxUploadImage() {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (imageFile == null) {
			jsonMap.put("error", 1);
			jsonMap.put("message", "请选择上传文件!");
			return ajax(jsonMap);
		}

		String uploadPath = ImageUtil.copyImageFile(getServletContext(),
				imageFile);
		
		if (uploadPath==null) {
			jsonMap.put("error", 1);
			jsonMap.put("message", "请选择正确文件!");
			return ajax(jsonMap);
		}
		
		jsonMap.put("error", 0);

		//String encodeUrl = CommonUtil.encodeUrl(uploadPath);

		jsonMap.put("url", uploadPath);

		return ajax(jsonMap);
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public String getImageFileFileName() {
		return imageFileFileName;
	}

	public void setImageFileFileName(String imageFileFileName) {
		this.imageFileFileName = imageFileFileName;
	}

	public String getImageFileContentType() {
		return imageFileContentType;
	}

	public void setImageFileContentType(String imageFileContentType) {
		this.imageFileContentType = imageFileContentType;
	}

	

}