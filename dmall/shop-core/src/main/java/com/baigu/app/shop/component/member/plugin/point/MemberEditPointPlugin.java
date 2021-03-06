package com.baigu.app.shop.component.member.plugin.point;

import javax.servlet.http.HttpServletRequest;

import com.baigu.app.shop.core.member.model.PointHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baigu.app.base.core.model.Member;
import com.baigu.app.base.core.service.IMemberManager;
import com.baigu.app.shop.core.member.plugin.IMemberTabShowEvent;
import com.baigu.app.shop.core.member.service.IPointHistoryManager;
import com.baigu.eop.processor.core.freemarker.FreeMarkerPaser;
import com.baigu.framework.context.webcontext.ThreadContextHolder;
import com.baigu.framework.plugin.AutoRegisterPlugin;
import com.baigu.framework.plugin.IAjaxExecuteEnable;
import com.baigu.framework.util.DateUtil;
import com.baigu.framework.util.JsonMessageUtil;
import com.baigu.framework.util.StringUtil;

/**
 * 后台会员积分插件
 * @author lzf<br/>
 * 2012-4-5下午04:11:58<br/>
 */
@Component
public class MemberEditPointPlugin extends AutoRegisterPlugin implements
IMemberTabShowEvent, IAjaxExecuteEnable {
	
	@Autowired
	private IMemberManager memberManager;
	
	@Autowired
	private IPointHistoryManager pointHistoryManager;

	public boolean canBeExecute(Member member) {
		return true;
	}

	public int getOrder() {
		return 9;
	}

	public String getTabName(Member member) {
		return "他的积分";
	}

	public String onShowMemberDetailHtml(Member member) {
		FreeMarkerPaser freeMarkerPaser =FreeMarkerPaser.getInstance();
		freeMarkerPaser.setClz(this.getClass());
		freeMarkerPaser.putData("memberid",member.getMember_id());		 
		freeMarkerPaser.setPageName("point");
		return freeMarkerPaser.proessPageContent();
	}

	@Transactional(propagation = Propagation.REQUIRED) 
	public String execute() {
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		int point = StringUtil.toInt(request.getParameter("modi_point"),true);
		int memberid  = StringUtil.toInt(request.getParameter("memberid"),true);
		Member member = this.memberManager.get(memberid);
		member.setPoint(member.getPoint() + point);
		PointHistory pointHistory = new PointHistory();
		pointHistory.setMember_id(memberid);
		pointHistory.setOperator("管理员");
		pointHistory.setPoint(point);
		pointHistory.setReason("operator_adjust");
		pointHistory.setTime(DateUtil.getDateline());
		try {
			memberManager.edit(member);
			pointHistoryManager.addPointHistory(pointHistory);
			return JsonMessageUtil.getSuccessJson("会员积分修改成功");
		} catch (Exception e) {
			this.logger.error("会员积分修改失败",e);
			return JsonMessageUtil.getErrorJson("修改失败"); 
		}
	}

}
