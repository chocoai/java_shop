package com.baigu.app.shop.component.promotion.plugin;

import com.baigu.app.shop.core.order.service.promotion.PromotionConditions;
import org.springframework.stereotype.Component;

import com.baigu.app.shop.core.order.plugin.promotion.IPromotionPlugin;
import com.baigu.app.shop.core.order.service.promotion.PromotionType;
import com.baigu.framework.plugin.AutoRegisterPlugin;
@Component
public class EnoughPriceReducePrice extends AutoRegisterPlugin implements
		IPromotionPlugin {

	
	public void register() {

	}

	
	public String[] getConditions() {
		 
		return new String[]{ PromotionConditions.ORDER ,PromotionConditions.MEMBERLV};
	}

	
	public String getMethods() {
		 
		return "reducePrice";
	}

	
	public String getAuthor() {
		return "kingapex";
	}

	
	public String getId() {
		return "enoughPriceReducePrice";
	}

	
	public String getName() {
		return "满就减———购物车中商品总金额大于指定金额,就可立减某金额";
	}

	
	public String getType() {
		return PromotionType.PMTTYPE_ORDER;
	}

	
	public String getVersion() {
		return "1.0";
	}

	
	public void perform(Object... params) {

	}

}
