package com.baigu.app.shop.front.tag.goods;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.baigu.eop.SystemSetting;
import com.baigu.eop.sdk.utils.StaticResourcesUtil;
import com.baigu.framework.context.webcontext.ThreadContextHolder;
import com.baigu.framework.taglib.BaseFreeMarkerTag;
import com.baigu.framework.util.RequestUtil;
import com.baigu.framework.util.StringUtil;

import freemarker.template.TemplateModelException;
/**
 * 商品sns分享标签
 * @author lina
 *
 */
@Component
@Scope("prototype")
public class GoodsSnsShareTag extends BaseFreeMarkerTag {
	/**
	 * 商品sns分享(必须与商品详细显示标签同时存在)
	 * @param 无
	 * @return 商品信息，类型Map
	 * title 商品标题 String型
	 * url 商品地址 String型
	 * img 商品图片 String型
	 */
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		HttpServletRequest request  = ThreadContextHolder.getHttpRequest();
		Map goods = (Map)request.getAttribute("goods");
		if(goods==null) throw new RuntimeException("参数显示标签必须和商品详细显示标签同时存在");
		String default_img  = (String)goods.get("small");
		if(StringUtil.isEmpty(default_img)){
			String static_server_domain= SystemSetting.getStatic_server_domain();
			default_img =static_server_domain +"/images/no_picture.jpg";
		}else{
//			default_img= UploadUtil.getThumbPath(default_img, "_small");
			default_img=StaticResourcesUtil.convertToUrl(default_img);
		};
		String title  = (String)goods.get("name");
		String url = RequestUtil.getWholeUrl(request);
		title = title.replaceAll("\r", "").replaceAll("\n", "");
		Map data = new HashMap();
		data.put("title",title);
		data.put("url",url);
		data.put("img",default_img);
		return data;
	}
}
