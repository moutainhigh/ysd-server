package net.qmdboss.action.admin;

import net.qmdboss.entity.Agency;
import net.qmdboss.entity.AgencyReady;
import net.qmdboss.entity.User;
import net.qmdboss.service.*;
import net.qmdboss.util.*;
import net.sf.json.JSONException;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.codehaus.jackson.type.TypeReference;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * 服务商
 * @author Xsf
 *
 */
@ParentPackage("admin")
public class AgencyAction extends BaseAdminAction {

	private static final long serialVersionUID = 3488634892926999082L;

	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	@Resource(name = "userInfoServiceImpl")
	private UserInfoService userInfoService;
	
	@Resource(name = "userAccountServiceImpl")
	private UserAccountService userAccountService;
	
	@Resource(name = "agencyServiceImpl")
	private AgencyService agencyService;
	
	@Resource(name = "agencyReadyServiceImpl")
	private AgencyReadyService agencyReadyService;

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	
	private User user;
	
	private Agency agency;
	private AgencyReady agencyReady;//临时审核表
	
	private String areaId;
	private File img;
	private File img1;
	private File img2;
	private File img3;
	
	private File cardPic1;
	private File cardPic2;
	private File privateCharterImg;
	private File privateTaxImg;
	private File privateOrganizationImg;
	private File accountLicenceImg;
	private File signImage;
	
	private List<File> fileList;
	private List<String> fileName;
	private List<NoteImg> voucherImgList;
	
	private List<String> imgName;// 图片名字
	private List<String> imgValue; //图片
	
	private BigDecimal tasteMoney;
	
	
	/**
	 * 平台服务商列表
	 * 
	 * @return
	 */
	public String list() {
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("agencyType", "1");
//		pager = userService.getAgencyUser(pager,map);
		pager = agencyService.findPager(pager);
		return LIST;
	}

	public String toInput() {
		if (id != null && id > 0) {
			user = userService.load(id);
		}
		return INPUT;
	}
	
	public String readyAdd(){
		return "input_ready";
	}
	
	public String readyUpdate(){
		agencyReady = agencyReadyService.load(id);
		return "input_ready";
	}
	
	public String readySave(){
		
		String username = user.getUsername();
		
		if (userService.getUserByUsername(username)!=null) {
			addErrorMessages("请重新输入用户名");
			return ERROR;
		}
		
		String random = CommonUtil.getRandomString(8);
		agencyReady.setUrandom(random);//保存随机8位密码
		if(StringUtils.isNotEmpty(agencyReady.getUpassword())){
			agencyReady.setUpassword( MD5.getMD5Str(MD5.getMD5Str(agencyReady.getUpassword())+random));
		}
		//处理省市区
//		if(StringUtils.isEmpty(areaId)){
//			addErrorMessages("请选择平台服务商地址");
//			return ERROR;
//		}
		
		String domain = areaService.get(Integer.parseInt(areaId)).getDomain();
		if(StringUtils.isNotEmpty(domain)){
			String[] areaStore = CommonUtil.splitString(domain);
			agencyReady.setProvince(areaStore[0]);//省
			agencyReady.setCity(areaStore[1]);//市
			agencyReady.setArea(areaStore[2]);//区
			StringBuffer areaSotreBuffer = new StringBuffer();
			if(StringUtils.isNotEmpty(areaStore[0])){
				areaSotreBuffer.append(areaService.get(Integer.parseInt(areaStore[0])).getName());
				if(StringUtils.isNotEmpty(areaStore[1])){
					areaSotreBuffer.append(",").append(areaService.get(Integer.parseInt(areaStore[1])).getName());
					if(StringUtils.isNotEmpty(areaStore[2])){
						areaSotreBuffer.append(",").append(areaService.get(Integer.parseInt(areaStore[2])).getName());
					}
				}
			}
			agencyReady.setAreaStore(areaSotreBuffer.toString());
		}
		if(cardPic1!= null){
			String path = ImageUtil.copyImageFile(getServletContext(), cardPic1);
			agencyReady.setCardPic1(path);
		}
		if(cardPic2!= null){
			String path = ImageUtil.copyImageFile(getServletContext(), cardPic2);
			agencyReady.setCardPic2(path);
		}
		if(privateCharterImg!= null){
			String path = ImageUtil.copyImageFile(getServletContext(), privateCharterImg);
			agencyReady.setPrivateCharterImg(path);
		}
		if(privateTaxImg!= null){
			String path = ImageUtil.copyImageFile(getServletContext(), privateTaxImg);
			agencyReady.setPrivateTaxImg(path);
		}
		if(privateOrganizationImg!= null){
			String path = ImageUtil.copyImageFile(getServletContext(), privateOrganizationImg);
			agencyReady.setPrivateOrganizationImg(path);
		}
		if(accountLicenceImg!= null){
			String path = ImageUtil.copyImageFile(getServletContext(), accountLicenceImg);
			agencyReady.setAccountLicenceImg(path);
		}
		if(signImage!= null){
			String path = ImageUtil.copyImageFile(getServletContext(), signImage);
			agencyReady.setSignImage(path);
		}
		
		agencyReady.setStatus(2);
		agencyReady.setServicePromise(agencyReady.getServicePromise()==null?"":agencyReady.getServicePromise().replace(" ", ""));//去空格
		
		agencyReady.setUusername(user.getUsername());
		agencyReady.setUemail(user.getEmail());
		agencyReady.setContactPhone(user.getPhone());
		if (agencyReady.getTasteRule()==null) {
			agencyReady.setTasteRule(0);
		}
		if (agencyReady.getFlowRule()==null) {
			agencyReady.setFlowRule(0);
		}
		if (agencyReady.getCreditRule()==null) {
			agencyReady.setCreditRule(0);
		}
		if (agencyReady.getPledgeRule()==null) {
			agencyReady.setPledgeRule(0);
		}
		
		agencyReadyService.save(agencyReady);
		redirectUrl = "agency!readyAdd.action";
		return SUCCESS;
	}
	
	public String readyUpate(){
		AgencyReady per = agencyReadyService.get(id);
		
		
		agencyReadyService.update(per);
		redirectUrl = "agency!readyList.action";
		return SUCCESS;
	}
	
	public String readyList(){
		if(agencyReady != null && agencyReady.getStatus()!= null){
			agencyReady.setStatus(agencyReady.getStatus());//查询审核中的服务商
		}else{
			agencyReady = new AgencyReady();
			agencyReady.setStatus(2);
		}
		pager = agencyReadyService.findAgencyReadyPager(pager, agencyReady);
		
		return "ready_list";
	}
	
	//审核信息跳转
	public String agencyReadyCheckTo(){
		agencyReady = agencyReadyService.load(id);
		return "ready_check";
	}

	//审核信息
	public String agencyReadyCheck(){
		if(agencyReady.getStatus()!= null){
			if(agencyReady.getStatus() == 1){
				agencyReady = agencyReadyService.load(id);
				if(agencyReady.getStatus() != 2){
					addErrorMessages("此服务商不在审核中,请核对数据...");
					return ERROR;
				}
				//审核通过，把临时表信息添加到主表中和用户表中
				agencyReady.setTasteMoney(tasteMoney == null ? new BigDecimal(0): tasteMoney);
				agencyReadyService.copyAgency(agencyReady);
			}else if(agencyReady.getStatus() == 0){
				agencyReady = agencyReadyService.load(id);
				agencyReady.setStatus(0);
				agencyReadyService.update(agencyReady);
			}
		}
		redirectUrl = "agency!readyList.action";
		return SUCCESS;
	}
	
//	
//	private void fillUser() {
//		if (user!=null) {
//			user.setTypeId(1);//机构服务商
//			user.setMemberType(1);//企业
//			user.setIsLock(false);
//			user.setIsEnabled(true);
//			//默认认证状态都是成功
//			user.setRealStatus(1);
//			user.setEmailStatus(1);
//			user.setPhoneStatus(1);
//			user.setAddTime(new Date());
//		}
//	}
	
	public String update() {
		

		if(StringUtils.isEmpty(areaId)){
			addErrorMessages("请选择平台服务商地址");
			return ERROR;
		}
		//处理省市区
		String domain = areaService.get(Integer.parseInt(areaId)).getDomain();
		if(StringUtils.isNotEmpty(domain)){
			String[] areaStore = CommonUtil.splitString(domain);
			user.setProvince(areaStore[0]);//省
			user.setCity(areaStore[1]);//市
			user.setArea(areaStore[2]);//区
			StringBuffer areaSotreBuffer = new StringBuffer();
			if(StringUtils.isNotEmpty(areaStore[0])){
				areaSotreBuffer.append(areaService.get(Integer.parseInt(areaStore[0])).getName());
				if(StringUtils.isNotEmpty(areaStore[1])){
					areaSotreBuffer.append(",").append(areaService.get(Integer.parseInt(areaStore[1])).getName());
					if(StringUtils.isNotEmpty(areaStore[2])){
						areaSotreBuffer.append(",").append(areaService.get(Integer.parseInt(areaStore[2])).getName());
					}
				}
			}
			user.setAreaStore(areaSotreBuffer.toString());
		}

		
		if (img != null) {
			String filePath = ImageUtil.copyImageFile(getServletContext(), img);
			agency.setLogo(filePath);
		}
		if (img1 != null) {
			String filePath = ImageUtil.copyImageFile(getServletContext(), img1);
			agency.setLogo1(filePath);
		}
		if (img2 != null) {
			String filePath = ImageUtil.copyImageFile(getServletContext(), img2);
			agency.setLogo2(filePath);
		}
		if (img3 != null) {
			String filePath = ImageUtil.copyImageFile(getServletContext(), img3);
			agency.setLogo3(filePath);
		}

		if (cardPic1 != null) {
			String filePath = ImageUtil.copyImageFile(getServletContext(), cardPic1);
			user.setCardPic1(filePath);
		}
		if (cardPic2 != null) {
			String filePath = ImageUtil.copyImageFile(getServletContext(), cardPic2);
			user.setCardPic2(filePath);
		}
		
		
		if(privateCharterImg != null){
			String filePath = ImageUtil.copyImageFile(getServletContext(), privateCharterImg);
			agency.setPrivateCharterImg(filePath);
		}
		if(privateTaxImg != null){
			String filePath = ImageUtil.copyImageFile(getServletContext(), privateTaxImg);
			agency.setPrivateTaxImg(filePath);
		}
		if(privateOrganizationImg != null){
			String filePath = ImageUtil.copyImageFile(getServletContext(), privateOrganizationImg);
			agency.setPrivateOrganizationImg(filePath);
		}
		if(accountLicenceImg != null){
			String filePath = ImageUtil.copyImageFile(getServletContext(), accountLicenceImg);
			agency.setAccountLicenceImg(filePath);
		}
		

		//宣传图
		List<NoteImg> noteImgList = new ArrayList<NoteImg>();
		
//		StringBuffer sb = new StringBuffer();
		if(imgValue != null){
			for(int i=0;i<imgValue.size();i++){
				String imgValue_i = imgValue.get(i);
				if(StringUtils.isNotEmpty(imgValue_i)){
					NoteImg nt = new NoteImg();
//					sb.append(imgValue_i).append(",");
					nt.setName(imgName.get(i));
					nt.setUrl(imgValue_i);
					noteImgList.add(nt);
				}
			}
		}
		if(fileList != null){
			for(int i = 0; i<fileList.size(); i++){
				File file_i = fileList.get(i);
				if( file_i != null){
					NoteImg nt = new NoteImg();
					String filePath = ImageUtil.copyImageFile(getServletContext(), file_i);
//					sb.append(filePath).append(",");
					nt.setName(fileName.get(i));
					nt.setUrl(filePath);
					noteImgList.add(nt);
				}
			}
		}	
		String tempVcs = JsonUtil.listToJson(noteImgList);
		agency.setImg(tempVcs);
		agency.setTasteMoney(tasteMoney == null ? new BigDecimal(0): tasteMoney);
		userService.updateUserAndAgency(id, user, agency);
		redirectUrl = "agency!list.action";
		return SUCCESS;
		
	}
	
	public String edit(){		
		if(id != null){
			user = userService.get(id);
			if(user != null && user.getAgencyid()!= null ){
				agency = agencyService.get(user.getAgencyid());
				
				voucherImgList = new ArrayList<NoteImg>();//轮播图片
				String tmp = agency.getImg();
				if (tmp != null && !"".equals(tmp.trim())) {
					try{
						voucherImgList =  JsonUtil.toObject(tmp, new TypeReference<ArrayList<NoteImg>>(){}); 
					}catch(JSONException je) {
						NoteImg ni = new NoteImg();
						ni.setName("");
						ni.setUrl(tmp);
						voucherImgList.add(ni);
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}else{
				addErrorMessages("参数错误");
				return ERROR;
			}
		}else{
			addErrorMessages("参数错误");
			return ERROR;
		}
		return INPUT;
	}
	public String input(){
		return INPUT;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}


	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public File getImg() {
		return img;
	}

	public void setImg(File img) {
		this.img = img;
	}

	public List<File> getFileList() {
		return fileList;
	}

	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}

	public List<String> getImgValue() {
		return imgValue;
	}

	public void setImgValue(List<String> imgValue) {
		this.imgValue = imgValue;
	}

	public AgencyReady getAgencyReady() {
		return agencyReady;
	}

	public void setAgencyReady(AgencyReady agencyReady) {
		this.agencyReady = agencyReady;
	}

	public File getImg1() {
		return img1;
	}

	public void setImg1(File img1) {
		this.img1 = img1;
	}

	public File getImg2() {
		return img2;
	}

	public void setImg2(File img2) {
		this.img2 = img2;
	}

	public File getImg3() {
		return img3;
	}

	public void setImg3(File img3) {
		this.img3 = img3;
	}

	public File getCardPic1() {
		return cardPic1;
	}

	public void setCardPic1(File cardPic1) {
		this.cardPic1 = cardPic1;
	}

	public File getCardPic2() {
		return cardPic2;
	}

	public void setCardPic2(File cardPic2) {
		this.cardPic2 = cardPic2;
	}

	public File getPrivateCharterImg() {
		return privateCharterImg;
	}

	public void setPrivateCharterImg(File privateCharterImg) {
		this.privateCharterImg = privateCharterImg;
	}

	public File getPrivateTaxImg() {
		return privateTaxImg;
	}

	public void setPrivateTaxImg(File privateTaxImg) {
		this.privateTaxImg = privateTaxImg;
	}

	public File getPrivateOrganizationImg() {
		return privateOrganizationImg;
	}

	public void setPrivateOrganizationImg(File privateOrganizationImg) {
		this.privateOrganizationImg = privateOrganizationImg;
	}

	public File getAccountLicenceImg() {
		return accountLicenceImg;
	}

	public void setAccountLicenceImg(File accountLicenceImg) {
		this.accountLicenceImg = accountLicenceImg;
	}

	public List<String> getFileName() {
		return fileName;
	}

	public void setFileName(List<String> fileName) {
		this.fileName = fileName;
	}

	public List<NoteImg> getVoucherImgList() {
		return voucherImgList;
	}

	public void setVoucherImgList(List<NoteImg> voucherImgList) {
		this.voucherImgList = voucherImgList;
	}

	public List<String> getImgName() {
		return imgName;
	}

	public void setImgName(List<String> imgName) {
		this.imgName = imgName;
	}

	public BigDecimal getTasteMoney() {
		return tasteMoney;
	}

	public void setTasteMoney(BigDecimal tasteMoney) {
		this.tasteMoney = tasteMoney;
	}

	public File getSignImage() {
		return signImage;
	}

	public void setSignImage(File signImage) {
		this.signImage = signImage;
	}
	
}