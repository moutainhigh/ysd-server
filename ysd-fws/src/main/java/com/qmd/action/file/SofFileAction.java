package com.qmd.action.file;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.qmd.action.base.BaseAction;
import com.qmd.util.CommonUtil;
import com.qmd.util.ConstantUtil;
import com.qmd.util.ImageUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Action类 - 文件处理
 */

@Service("sSofFileAction")
public class SofFileAction extends BaseAction {

	private static final long serialVersionUID = 6614132059804452558L;
	
	private static final String[] imageFormatName = new String[] {"jpg", "jpeg", "gif", "bmp", "png"};// 图片格式名称

	private Boolean isFileAction = true;// 是否为FileAction
	private File imgFile;// 上传图片文件
	private String imgFileFileName;
	private String imgFileContentType;
	private String path;// 浏览路径
	private String order;// 排序方式

	private String Filedata;
	private String Filename;
	private String newFilename;

	public String getFiledata() {
		return Filedata;
	}

	public void setFiledata(String Filedata) {
		//System.out.println("Filedata"+Filedata);
		this.Filedata = Filedata;
	}

	public String getFilename() {
		//System.out.println(Filename);
		return Filename;
	}

	public void setFilename(String filename) {
		//System.out.println("Filename=" + filename);
		Filename = filename;
	}

	public void setNewFilename(String newFilename) {
		this.newFilename = newFilename;
	}

	public String getNewFilename() {
		return newFilename;
	}
	
//	if (imageFile == null) {
//		return null;
//	}
//	String formatName = getFormatName(imageFile);
//	if (formatName == null) {
//		throw new IllegalArgumentException("imageFile format error!");
//	}
//	String destImagePath = CommonUtil.getImageUploadRealPath() + "/" + CommonUtil.getUUID() + "." + formatName;
////	File destImageFile = new File(servletContext.getRealPath(destImagePath));
//	File destImageFile = new File(IMG_DIR+destImagePath);
//	File destImageParentFile = destImageFile.getParentFile();
//	if (!destImageParentFile.isDirectory()) {
//		destImageParentFile.mkdirs();
//	}
//	try {
//		FileUtils.copyFile(imageFile, destImageFile);
//		
//		String watermark_f=(String) servletContext.getAttribute("qmd.setting.watermarkImagePath");
//		String watermark_p=(String) servletContext.getAttribute("qmd.setting.watermarkPosition");
//		Integer watermark_a= (Integer) servletContext.getAttribute("qmd.setting.watermarkAlpha");
//		
//		File watermarkFile = new File(IMG_DIR+watermark_f);
//		ImageUtil.addWatermark(destImageFile, destImageFile, watermarkFile, WatermarkPosition.valueOf(watermark_p), watermark_a);
//	} catch (IOException e) {
//		e.printStackTrace();
//		destImagePath = null;
//	}
//	return destImagePath;
	@Action(value="/upload")
	@InputConfig(resultName = "ajaxError")
	public String upload(){
		System.out.println("aaaaaaaaaa");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		ServletContext application = ServletActionContext.getServletContext(); 
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
		fis = new FileInputStream(getFiledata());
		File directory = new File(application.getRealPath("upload"));
		if(!directory.exists()){
			directory.mkdir();
		}
		//System.out.println(directory.getAbsolutePath());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = directory + File.separator + format.format(new Date()) + getFilename();
		setNewFilename(format.format(new Date()) + getFilename());
		//System.out.println(getNewFilename());
		File newFile = new File(fileName);
		if(!newFile.exists()) {
			newFile.createNewFile();
		}
		fos = new FileOutputStream(newFile);
		byte[] buffer = new byte[1024];
		int length;
		while((length = fis.read(buffer)) > 0) {
			fos.write(buffer,0,length);
		}
		}catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		} finally {
			try {
				if (fos != null){
					fos.close();
					fos = null;
				}
				if (fis != null){
					fis.close();
					fis = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		jsonMap.put("SUCCESS", 1);
		return ajax(jsonMap);
	}

	// AJAX文件上传
	@Action(value="/file/sofUpload")
	@InputConfig(resultName = "ajaxError")
	public String ajaxUpload() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		imgFile= new File(getFiledata());
		System.out.println(imgFile);
		if (imgFile == null) {
			jsonMap.put("error", 1);
			jsonMap.put("message", "请选择上传文件!");
			return ajax(jsonMap);
		}
		String uploadPath = ImageUtil.copyImageFile(getServletContext(), imgFile);

//		String encodeUrl = CommonUtil.encodeUrl(uploadPath);
//		setNewFilename(ConstantUtil.WEB_SITE_IMAGE_ENCODE_URL + encodeUrl);
//		jsonMap.put("imgValue", encodeUrl);
//		jsonMap.put("url", ConstantUtil.WEB_SITE_IMAGE_ENCODE_URL + encodeUrl);
		
		jsonMap.put("imgValue",  CommonUtil.encodeUrl(uploadPath));
		jsonMap.put("url", ConstantUtil.WEB_IMG + ConstantUtil.WEB_IMG_CONTEXT_PATH + uploadPath);
		
		
		return ajax(jsonMap);
	}
	
	// AJAX上传文件浏览
	@Action(value="/file/ajaxBrowser")
	@SuppressWarnings("unchecked")
	@InputConfig(resultName = "ajaxError")
	public String ajaxBrowser() throws Exception {
		if (StringUtils.isEmpty(path)) {
			path = "";
		}
		if (StringUtils.isEmpty(order)) {
			order = "name";
		}
		
		String rootPath = getRealPath(ConstantUtil.IMAGE_BROWSE_PATH) + "/";
		String currentPath = rootPath + "/" + path;
//		String currentUrl = getContextPath() + ConstantUtil.IMAGE_BROWSE_PATH + "/" + path;
		String currentUrl = ConstantUtil.WEB_IMG + ConstantUtil.IMAGE_BROWSE_PATH + "/" + path;
		String moveupDirPath = "";
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (path.indexOf("..") >= 0 || !currentPath.endsWith("/")) {
			jsonMap.put("error", 1);
			jsonMap.put("message", "此操作不被允许!");
			return ajax(jsonMap);
		}
		
		File currentPathFile = new File(currentPath);
		if(!currentPathFile.isDirectory()){
			jsonMap.put("error", 1);
			jsonMap.put("message", "当前目录不存在!");
			return ajax(jsonMap);
		}
		
		if (!"".equals(path)) {
			String str = path.substring(0, path.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}

		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if(currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hashtable.put("is_dir", true);
					hashtable.put("has_file", (file.listFiles() != null));
					hashtable.put("filesize", 0L);
					hashtable.put("is_photo", false);
					hashtable.put("filetype", "");
				} else if(file.isFile()){
					String fileExtension = StringUtils.substringAfterLast(fileName, ".").toLowerCase();
					hashtable.put("is_dir", false);
					hashtable.put("has_file", false);
					hashtable.put("filesize", file.length());
					hashtable.put("is_photo", Arrays.<String>asList(imageFormatName).contains(fileExtension));
					hashtable.put("filetype", fileExtension);
				}
				hashtable.put("filename", fileName);
				hashtable.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hashtable);
			}
		}
		
		if ("SIZE".equalsIgnoreCase(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("TYPE".equalsIgnoreCase(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		
		jsonMap.put("moveup_dir_path", moveupDirPath);
		jsonMap.put("current_dir_path", path);
		jsonMap.put("current_url", currentUrl);
		jsonMap.put("total_count", fileList.size());
		jsonMap.put("file_list", fileList);
		return ajax(jsonMap);
	}
	
	@SuppressWarnings("unchecked")
	private class NameComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String)hashA.get("filename")).compareTo((String)hashB.get("filename"));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private class SizeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				if (((Long)hashA.get("filesize")) > ((Long)hashB.get("filesize"))) {
					return 1;
				} else if (((Long)hashA.get("filesize")) < ((Long)hashB.get("filesize"))) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private class TypeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String)hashA.get("filetype")).compareTo((String)hashB.get("filetype"));
			}
		}
	}

	public Boolean getIsFileAction() {
		return isFileAction;
	}

	public void setIsFileAction(Boolean isFileAction) {
		this.isFileAction = isFileAction;
	}

	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	public String getImgFileContentType() {
		return imgFileContentType;
	}

	public void setImgFileContentType(String imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}
	
	

}