package com.baigu.app.shop.front.tag.decorate;

import java.util.List;
import java.util.Map;

import com.baigu.app.shop.core.decorate.service.IShowCaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.baigu.framework.taglib.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 
 * 橱窗列表获取标签
 * @author    jianghongyan
 * @version   1.0.0,2016年6月20日
 * @since     v6.1
 */
@Component
@Scope("prototype")
public class ShowCaseListTag extends BaseFreeMarkerTag{

	@Autowired
	private IShowCaseManager showCaseManager;
	
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		String flag=params.get("flag").toString();
		List list=this.showCaseManager.getShowCaseByFlag(flag);
		return list;
	}

}
