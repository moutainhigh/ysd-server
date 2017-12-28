package com.qmd.util;

import org.apache.struts2.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * PDF 操作
 * 
 * @author xz
 * 
 */
public class PdfForContract {

	/** 合同生成路径 */
	private final static String PATH = ConfigUtil.getConfigUtil().get(ConfigUtil.QMD_UPLOAD_IMG_DIR)+ConstantUtil.IMAGE_UPLOAD_PATH
			+ ConfigUtil.getConfigUtil().get(ConfigUtil.QMD_IMG_UPLOAD_PDF_PATH);

	/** 历史合同路径（上传后被替换的pdf文件） */
	private final static String PATH_CACHE = ConfigUtil.getConfigUtil().get(ConfigUtil.QMD_UPLOAD_IMG_DIR)+ConstantUtil.IMAGE_UPLOAD_PATH
			+ ConfigUtil.getConfigUtil().get(ConfigUtil.QMD_IMG_BACKUP_PDF_PATH);


	
	
	
	
	/**
	 * pdf文件名命名规则 cb.getContractCode() + "_" + cb.getBorrowId()+ "_" +
	 * cb.getBorrowDetailId() + "_"+ cb.getContractUserId() + "_" +
	 * contract.getSort() （项目编号编号_标id_投标id_用户id_(Contract.sort).pdf）
	 * 
	 * @param typeMap
	 *            gsName//公司名， rootPath//根路径
	 * @param typeMap
	 * @param cb
	 * @param contract
	 * @return pdf存储地址
	 */
//	public static String CreditPdf(Map<String, Object> typeMap,
//			ContractBorrow cb, Contract contract) {
//		PdfReader reader = null;
//		PdfStamper stamp = null;
//		String pathUrl = uploadPath(contract.getCode(), cb.getContractCode())
//				+ cb.getContractCode() + "_" + cb.getBorrowId() + "_"
//				+ cb.getBorrowDetailId() + "_" + cb.getContractUserId() + "_"
//				+ contract.getSort() + ".pdf";// 添加文件名;
//		
//		String path = PATH + pathUrl;// 获得存储路径
//		try {
//			// 读取模版
//			reader = new PdfReader(typeMap.get("rootPath") +"/"+ contract.getSign() + ".pdf");
//			// 生成文件 地址 命名规则 （项目编号编号_标id_投标id_用户id_(Contract.sort).pdf）
//			stamp = new PdfStamper(reader, new FileOutputStream(path));
//
//			// 获取模版值
//			Map<String, String> map = (Map<String, String>) JSONUtil
//					.deserialize(cb.getContractContentJson());
//			// 读取模版所有变量
//			AcroFields form = stamp.getAcroFields();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//			map.put("date", sdf.format(new Date()));
//			map.put("gsName", typeMap.get("gsName").toString());
//			for (Iterator it = form.getFields().keySet().iterator(); it
//					.hasNext();) {
//				String str = (String) it.next();
//				// form.setField(str,"");
//				if (map.get(str) != null) {
//					//表单填写
//					form.setField(str, map.get(str).toString());
//				}
//			}
//
//			stamp.setFormFlattening(true);
//			System.out.println("over");
//			return pathUrl;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				stamp.close();
//			} catch (DocumentException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			reader.close();
//		}
//		return "pdf生成失败";
//	}

	/**
	 * 判断目录是否生成
	 * 
	 * @param code
	 *            合同标识(contract.code)
	 * @return
	 */
	private static String uploadPath(String code, String contractCode) {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		// String date = sdf.format(new Date());
		String path = "/" + code + "/" + contractCode;
		try {
			File pathTest = new File(PATH + path + "/pdfUpload.txt");
			if (!pathTest.getParentFile().exists()) {
				if (!pathTest.getParentFile().mkdirs()) {
					return "/";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "/";
		}
		return path + "/";
	}

	/**
	 * 获取pdf下载路径
	 * @param cb
	 * @return
	 */
	public static String getDownloadPath(String path){
		return PATH + path + "/";
	}
	
	/**
	 * 获取文件名
	 * @param path
	 * @return
	 */
	public static String getPdfFileName(String path){
		return path.substring(path.lastIndexOf("/")+1, path.lastIndexOf("."));
	}
	
	/**
	 * 获取文件路径
	 * @return
	 */
	public static String getPdfFilePath(String contractBorrowFilePath){
		return contractBorrowFilePath.substring(0, contractBorrowFilePath.lastIndexOf("/"));
	}
	/**
	 * 	 复制文件到指定相对目录
	 * @param pathStr	1:原始pdf路径（下载路径） 2:pdf历史文件路径（历史文件备份）
	 * @param file文件
	 * @param path	相对路径
	 * @param fileName
	 */
	public static void copyPdfForUpload(String pathStr,File file,String path,String fileName){
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		String p = null;
		try{
			if(pathStr==null||path==""){
				throw new Exception("PDF-->pathStr NULL!!!");
			}else if(pathStr.equals("1")){
				p=PATH;
			}else if(pathStr.equals("2")){
				p=PATH_CACHE;
			}else{
				throw new Exception("PDF-->pathStr ERROR!!!");
			}
			inputStream = new FileInputStream(file); 
			File pathTest = new File(p + path + "/pdfUpload.txt");
			if (!pathTest.getParentFile().exists()) {
				if (!pathTest.getParentFile().mkdirs()) {
					throw new Exception("文件目录创建失败");
				}
			}
			outputStream = new FileOutputStream(p+path+"/"+fileName);  
		    byte[] buf = new byte[1024];  
		    int length = 0;  
		    while ((length = inputStream.read(buf)) != -1)  
		    {  
		        outputStream.write(buf, 0, length);  
		    }  
		}catch(Exception e){
			e.printStackTrace();
		}finally{
	         try {
	        	 inputStream.close();
	        	 outputStream.flush();
	        	 outputStream.close();
	         } catch (IOException e) {
	        	 e.printStackTrace();
	         } 
		}

		
	}
	
	
	
	public static void main(String[] args) throws JSONException {

//		Object o = JSONUtil
//				.deserialize("{'borrow.businessCode':'${borrow.businessCode}', 'agency.companyName':'${agency.companyName}', 'agencyUser.realName':'<#if agency.realName?exists>${agencyUser.realName}</#if>', 'agencyUser.address':'<#if agency.address?exists>${agencyUser.address}</#if>', 'postalCode':'${postalCode}', 'agencyUser.phone':'<#if agency.address?exists>${agencyUser.phone}</#if>', 'contractParam.financeName':'${contractParam.financeName}', 'contractParam.financeLegalPerson':'<#if contractParam.financeLegalPerson?exists>${contractParam.financeLegalPerson}</#if>', 'contractParam.financeAddress':'<#if contractParam.financeAddress?exists>${contractParam.financeAddress}</#if>', 'contractParam.financePost':'<#if contractParam.financePost?exists>${contractParam.financePost}</#if>', 'contractParam.financePhone':'<#if contractParam.financePhone?exists>${contractParam.financePhone}</#if>', 'user.realName':'${user.realName}', 'user.address':'<#if user.address?exists>${user.address}</#if>', 'user.postalCode':'<#if user.postalCode?exists>${user.postalCode}</#if>', 'user.phone':'<#if user.phone?exists>${user.phone}</#if>', 'contractParam.contractAddress':'<#if contractParam.contractAddress?exists>${contractParam.contractAddress}</#if>', 'tender.createDate?':'<#if tender.createDate?exists>${tender.createDate?string('yyyy'+'年'+'MM'+'月'+'dd'+'日')}</#if>', 'tender.accountBig':'${digitUppercase(tender.account)}', 'tender.account':'${tender.account}', 'contractParam.chargeRate':'${contractParam.chargeRate}', 'tender.contractChargeBig':'${digitUppercase(tender.contractCharge)}', 'tender.contractCharge':'${tender.contractCharge}', 'dateWithin':'7', 'contractParam.chargeBankName':'${contractParam.chargeBankName}', 'contractParam.chargeBankCard':'${contractParam.chargeBankCard}', 'contractParam.chargeBankOpen':'${contractParam.chargeBankOpen}', 'tender.contractChargeBig':'${digitUppercase(tender.contractCharge)}', 'tender.showPenaltyBig':'${digitUppercase(tender.showPenalty)}', 'tender.showPenalty':'${tender.showPenalty}', 'borrow.finalRepayDate':'${borrow.finalRepayDate?string('yyyy'+'年'+'MM'+'月'+'dd'+'日')}', 'borrow.showPenaltyDate':'<#if borrow.showPenaltyDate?exists>${borrow.showPenaltyDate?string('yyyy'+'年'+'MM'+'月'+'dd'+'日')}</#if>' } ");
//		Map map = (Map) o;
		
		File pathTest = new File("E:\\repository.rar");
		if (!pathTest.getParentFile().exists()) {
			System.out.println(1);
		}
//		copyPdfForUpload(pathTest);
		
		
		
		
	}

}
