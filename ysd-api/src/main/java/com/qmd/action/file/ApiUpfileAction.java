package com.qmd.action.file;

import com.qmd.action.base.ApiBaseAction;
import com.qmd.bean.file.FileBean;
import com.qmd.mode.user.User;
import com.qmd.service.user.UserInfoService;
import com.qmd.service.user.UserService;
import com.qmd.util.ApiConstantUtil;
import com.qmd.util.ConfigUtil;
import com.qmd.util.ImageUtil;
import com.qmd.util.JsonUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@InterceptorRefs({ 
	@InterceptorRef(value = "qmdDefaultStack") })
@Service("apiUpfileAction")
public class ApiUpfileAction extends ApiBaseAction {

	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(ApiUpfileAction.class);
	
	private File imgFile;// 上传文件
	private String  fileFileName;//文件名称
	private String  fileContentType;
	
	@Resource
	UserService userService;
	
	@Resource
	UserInfoService userInfoService;
	
	/**
	 * 上传图片
	 * @return
	 */
	@Action(value="/api/upfile",results={@Result(type="json")})
	public String ajaxUpfile() throws Exception{
		
		if (imgFile == null) {
			return ajaxJson("M00016", ApiConstantUtil.M00016);
		}
		FileBean bean = null;
		try {
			bean = saveFile();
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJson("S0001", ApiConstantUtil.S0001);
		}
		return ajax(JsonUtil.toJson(bean));
	}

	/**
	 * 上传图片
	 * @return
	 */
	@Action(value="/api/ajaxUpload",results={@Result(type="json")})
	public String ajaxUpload() throws Exception{
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (imgFile == null) {
			return ajaxJson("M00016", ApiConstantUtil.M00016);
		}
		String uploadPath = ImageUtil.copyImageFile(getServletContext(), imgFile);
		if (uploadPath==null) {
			jsonMap.put("error", 1);
			jsonMap.put("message", "请选择正确文件!");
			return ajax(jsonMap);
		}
		
		jsonMap.put("error", 0);
		jsonMap.put("url", ConfigUtil.getConfigUtil().get(
				ConfigUtil.QMD_IMG_BASEURL)
				+ uploadPath);

		return ajax(jsonMap);
		
	}
	/**
	 * 上传头像
	 * @return
	 */
	@Action(value="/api/upHeadImg",results={@Result(type="json")})
	public String ajaxupHeadImg() throws Exception{
		
		if (imgFile == null) {
			return ajaxJson("M00016", ApiConstantUtil.M00016);
		}
		User user = getLoginUser();
		if(user ==null){
			return ajaxJson("E0001", ApiConstantUtil.E0001);
		}
		FileBean bean = null;
		
		String path = "";
		try {
			bean = saveFile();
			
			// 更新数据库
			Date date = new Date();
			
			user.setId(user.getId());
			user.setLitpic(bean.getUrl());
			user.setModifyDate(date);
			userService.updateLitpic(user);
			reloadUser();
			path = bean.getUrl();
			
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJson("S0001", ApiConstantUtil.S0001);
		}
		
		return ajax(JsonUtil.toJson(bean));
	}

	private FileBean saveFile() {
		FileBean bean = new FileBean();

		String uploadPath = ImageUtil.copyImageFile(getServletContext(), imgFile);

		bean.setFilename(fileFileName);
		bean.setUrl(uploadPath);
		bean.setFullUrl(ConfigUtil.getConfigUtil().get(ConfigUtil.QMD_IMG_BASEURL)
				+"/mobile" + uploadPath);
		return bean;
	}
	
	public String getFileFileName() {
		return fileFileName;
	}



	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}


	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

}
