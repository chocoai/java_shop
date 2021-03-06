package com.baigu.app.shop.front.tag.goods.activity;

import java.util.Map;

import com.baigu.app.shop.core.order.model.OrderGift;
import com.baigu.app.shop.core.order.service.IOrderGiftManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baigu.eop.sdk.utils.StaticResourcesUtil;
import com.baigu.framework.taglib.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 订单赠品详细Tag
 * @author DMRain
 * @date 2016-6-14
 * @version 1.0
 */
@Component
public class OrderGiftDetailTag extends BaseFreeMarkerTag{

	@Autowired
	private IOrderGiftManager orderGiftManager;
	
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		Integer gift_id = (Integer) params.get("gift_id");
		Integer order_id = (Integer) params.get("order_id");
		OrderGift gift = this.orderGiftManager.getOrderGift(gift_id, order_id);
		if(gift == null){
			gift = new OrderGift();
		}else{
			gift.setGift_img(StaticResourcesUtil.convertToUrl(gift.getGift_img()));
		}
		return gift;
	}

}
