package net.qmdboss.action.admin;

import net.qmdboss.entity.Feedback;
import net.qmdboss.service.FeedbackService;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
/**
 * 
 * @author Xsf
 *
 */
@ParentPackage("admin")
public class FeedbackAction extends BaseAdminAction {
	private static final long serialVersionUID = 5686527201079251565L;
	
	private Feedback feedback;
	
	@Resource(name = "feedbackServiceImpl")
	private FeedbackService  feedbackService;
	
	
	/**
	 * 查询所有反馈列表
	 * @return
	 */
	public String list() {
//		Map<String,Object> map = new HashMap<String, Object>();
		
		
		pager = feedbackService.findPager(pager);

		for(Object o : pager.getResult()){

			Feedback fb = (Feedback)o;
			System.out.print(fb.getId()+","+fb.getUser().getRealName());
		}

		return LIST;
	}

	public String edit(){
		feedback = feedbackService.load(id);
		return INPUT;
	}

	public Feedback getFeedback() {
		return feedback;
	}


	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	
	
	
}
