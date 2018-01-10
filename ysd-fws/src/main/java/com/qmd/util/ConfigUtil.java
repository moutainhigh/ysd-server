package com.qmd.util;

import java.util.ResourceBundle;

public class ConfigUtil {

	/** 网址 例:http://127.0.0.1:8080/qmd/ */
	public static final String QMD_BASEURL = "qmd.baseUrl";
	/** 会员后台地址  */
	public static final String QMD_MEMBERURL = "qmd.memberUrl";
	/** 网站静态地址 例:http://127.0.0.1:8080/qmd/static/ */
	public static final String QMD_STATIC_BASEURL = "qmd.static.baseUrl";
	/** 网站内容地址 例:http://127.0.0.1:8080/qmd/content/ */
	public static final String QMD_CONTENT_BASEURL = "qmd.content.baseUrl";
	/** 图片地址 例 :http://127.0.0.1:8080/qmd/img/ */
	public static final String QMD_IMG_BASEURL = "qmd.img.baseUrl";
	/** 图片发布 例 :/qmd/img/ (要与tomcat中的context一致)*/
	public static final String QMD_IMG_CONTEXT_PATH = "qmd.img.context.path";
	/** 跳转的根目录 */
	public static final String QMD_INTERCEPT_SENDREDIRECT = "qmd.intercept.sendRedirect";
	/** 图片地址 例 :http://127.0.0.1:8080 */
	public static final String QMD_SITE_URL = "qmd.site.url";
	/** 网站名称 例:网贷 */
	public static final String QMD_SITE_NAME = "qmd.site.name";
	/** 图片上传保存路径 */
	public static final String QMD_UPLOAD_IMG_DIR = "qmd.upload.img.dir";
	/** 手机图片上传保存路径 */
	public static final String QMD_UPLOAD_IMG_MOBILE = "qmd.upload.img.mobile";
	
	/** 水印图片位置 */
	public static final String QMD_WATERMARK_POSITION = "qmd.watermark.position";
	/** 水印图片 */
	public static final String QMD_WATERMARK_FILE = "qmd.watermark.file";
	/** 水印透明度 */
	public static final String QMD_WATERMARK_ALPHA = "qmd.watermark.alpha";
	/** pdf模版路径  */
	public static final String QMD_STENCIL_PDF_PATH = "qmd.stencil.pdf.path";
	
	/** 身份证上传路径  */
	public static final String QMD_IMG_UPLOAD_IDCARD_PATH = "qmd.img.upload.idcard.path";
	/** 标详情图片路径  */
	public static final String QMD_IMG_UPLOAD_BORROW_PATH = "qmd.img.upload.borrow.path";
	/** 文章图片、主页滚动图片路径 */
	public static final String QMD_IMG_UPLOAD_INDEX_PATH = "qmd.img.upload.index.path";
	/** 服务商图片路径 */
	public static final String QMD_IMG_UPLOAD_AGENCY_PATH = "qmd.img.upload.agency.path";
	/** pdf合同生成路径  */
	public static final String QMD_IMG_UPLOAD_PDF_PATH = "qmd.img.upload.pdf.path";
	/** pdf历史文件目录  */
	public static final String QMD_IMG_BACKUP_PDF_PATH = "qmd.img.backup.pdf.path";
//	/** excel上传路径  */
//	public static final String QMD_DATA_UPLOAD_EXCEL_PATH = "qmd.data.upload.excel.path";
	/** excel缓存目录  */
	public static final String QMD_DATA_CACHE_EXCEL_PATH = "qmd.data.cache.excel.path";
	


	
	public String get(String key) {
		return bundle.getString(key);
	}

	private static ConfigUtil configUtil;
	private static ResourceBundle bundle = ResourceBundle
			.getBundle("qmd");

	public static ConfigUtil getConfigUtil() {
		if (configUtil != null) {
			return configUtil;
		} else {
			configUtil = new ConfigUtil();
			bundle = ResourceBundle.getBundle("qmd");

			return configUtil;
		}
	}
	
	/**
     * 返回部署环境
     * @return
     */
    public static String getRuntimeEnv() {
        String runtimeDev = ConfigUtil.getConfigUtil().get("qmd.runtime.env");
        return runtimeDev;
    }
	
	 /**
     * 是否生产环境
     * @return
     */
    public static boolean isProdEnv() {
        return "prod".equals(getRuntimeEnv());
    }

}
