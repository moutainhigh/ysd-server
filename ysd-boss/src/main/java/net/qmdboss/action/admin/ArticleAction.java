package net.qmdboss.action.admin;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import net.qmdboss.bean.Pager.Order;
import net.qmdboss.bean.Setting;
import net.qmdboss.entity.Article;
import net.qmdboss.entity.ArticleCategory;
import net.qmdboss.service.ArticleCategoryService;
import net.qmdboss.service.ArticleService;
import net.qmdboss.service.CacheService;
import net.qmdboss.util.ImageUtil;
import net.qmdboss.util.SettingUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * 后台Action类 - 文章
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX8DDD613CBE6FE3988F4CE19EAC8724CB
 * ============================================================================
 */

@ParentPackage("admin")
public class ArticleAction extends BaseAdminAction {

    private static final long serialVersionUID = -6825456589196458406L;

    private Article article;
    private ArticleCategory articleCategory;

    private File img;
    private File imgContentApp;
    private Boolean isCleanImg;//是否清除图片
    @Resource(name = "articleServiceImpl")
    private ArticleService articleService;
    @Resource(name = "articleCategoryServiceImpl")
    private ArticleCategoryService articleCategoryService;
    @Resource(name = "cacheServiceImpl")
    private CacheService cacheService;

    private Integer articleType;//文章分类ID


    // 添加
    public String add() {
        return INPUT;
    }

    // 编辑
    public String edit() {
        article = articleService.load(id);
        return INPUT;
    }

    // 列表
    public String list() {
//		pager.setOrder(Order.desc);
//		pager.setOrderBy("isTop");
//		pager = articleService.findPager(pager);
//		return LIST;

        if(article != null && article.getArticleCategory() != null && article.getArticleCategory().getId() != null){
            articleCategory = articleCategoryService.get(article.getArticleCategory().getId());
            if (articleCategory == null) {
                addActionError("参数错误!");
                return ERROR;
            }
        }else if(articleType != null){
            articleCategory = articleCategoryService.get(articleType);
            if (articleCategory == null) {
                addActionError("参数错误!");
                return ERROR;
            }
        }
        if(StringUtils.isEmpty(pager.getOrderBy())){
            pager.setOrder(Order.asc);
            pager.setOrderBy("orderList");
        }

        pager = articleService.getArticlePager(articleCategory, pager);
        return LIST;
    }

    // 删除
    public String delete() throws Exception {
        StringBuffer logInfoStringBuffer = new StringBuffer("删除文章: ");
        for (Integer id : ids) {
            Article article = articleService.load(id);
            articleService.delete(article);
            logInfoStringBuffer.append(article.getTitle() + " ");
        }
        logInfo = logInfoStringBuffer.toString();

        cacheService.flushArticleListPageCache(getRequest());

        return ajax(Status.success, "删除成功!");
    }

    // 保存
    @Validations(
            requiredStrings = {
                    @RequiredStringValidator(fieldName = "article.title", message = "标题不允许为空!")
                    //  @RequiredStringValidator(fieldName = "article.content", message = "内容不允许为空!")
            }
    )
    @InputConfig(resultName = "error")
    public String save() throws Exception {
        if(img!= null){
            String path = ImageUtil.copyImageFile(getServletContext(), img);
            article.setCoverImg(path);
            ArticleCategory ac = articleCategoryService.load(article.getArticleCategory().getId());
            if(ac != null && !ac.getSign().equals("cooperative_partner")){//除了"合作伙伴"不加水印
                Setting setting = SettingUtil.getSetting();
                File watermarkImageFile = new File(getServletContext().getRealPath(setting.getWatermarkImagePath()));
                File destImageFile = new File(ImageUtil.IMG_DIR+path);
                ImageUtil.addWatermark(destImageFile, destImageFile, watermarkImageFile, setting.getWatermarkPosition(), setting.getWatermarkAlpha());
            }
        }

//		if(imgContentApp!= null){
//			String path = ImageUtil.copyImageFile(getServletContext(), imgContentApp);
//			String str=CommonUtil.WEB_IMG + path;
//			article.setContent(str);
//		}
        article.setContent(article.getPcContent());
        articleService.save(article);
        logInfo = "添加文章: " + article.getTitle();

//		cacheService.flushArticleListPageCache(getRequest());

        redirectUrl = "article!list.action";
        return SUCCESS;
    }

    // 更新
    @Validations(
            requiredStrings = {
                    @RequiredStringValidator(fieldName = "article.title", message = "标题不允许为空!")
                    //	@RequiredStringValidator(fieldName = "article.content", message = "内容不允许为空!")
                    //	@RequiredStringValidator(fieldName = "article.articleCategory.id", message = "文章分类不允许为空!")
            }
    )
    @InputConfig(resultName = "error")
    public String update() throws Exception {
        Article persistent = articleService.load(id);
        if(img!= null){
            String path = ImageUtil.copyImageFile(getServletContext(), img);
            persistent.setCoverImg(path);

            if(!persistent.getArticleCategory().getSign().equals("cooperative_partner")){//除了"合作伙伴"不加水印
                Setting setting = SettingUtil.getSetting();
                File watermarkImageFile = new File(getServletContext().getRealPath(setting.getWatermarkImagePath()));
                File destImageFile = new File(ImageUtil.IMG_DIR+path);
                ImageUtil.addWatermark(destImageFile, destImageFile, watermarkImageFile, setting.getWatermarkPosition(), setting.getWatermarkAlpha());
            }
        }
        if(isCleanImg != null &&isCleanImg){
            persistent.setCoverImg(null);
        }
        BeanUtils.copyProperties(article, persistent, new String[] {"id", "createDate", "modifyDate", "pageCount", "htmlPath","coverImg","hits"});
        persistent.setContent(article.getPcContent());
        articleService.update(persistent);
        logInfo = "编辑文章: " + article.getTitle();

        cacheService.flushArticleListPageCache(getRequest());
        if(articleType != null){
            redirectUrl = "article!list.action?articleType="+articleType;
        }else{
            redirectUrl = "article!list.action";
        }
        return SUCCESS;
    }

    /**
     * 查看文章内容
     * @return
     */
    public String content(){
        article = articleService.load(id);
        return "content";
    }


    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<ArticleCategory> getArticleCategoryTreeList() {
        return articleCategoryService.getArticleCategoryTreeList();
    }

    public File getImg() {
        return img;
    }

    public void setImg(File img) {
        this.img = img;
    }

    public ArticleCategory getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(ArticleCategory articleCategory) {
        this.articleCategory = articleCategory;
    }

    public Integer getArticleType() {
        return articleType;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }

    public Boolean getIsCleanImg() {
        return isCleanImg;
    }

    public void setIsCleanImg(Boolean isCleanImg) {
        this.isCleanImg = isCleanImg;
    }

    public File getImgContentApp() {
        return imgContentApp;
    }

    public void setImgContentApp(File imgContentApp) {
        this.imgContentApp = imgContentApp;
    }

}