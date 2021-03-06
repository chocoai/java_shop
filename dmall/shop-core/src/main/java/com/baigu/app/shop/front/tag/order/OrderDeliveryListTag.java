package com.baigu.app.shop.front.tag.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baigu.app.shop.core.order.service.IOrderReportManager;
import com.baigu.app.shop.core.order.service.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.baigu.app.base.core.service.IMemberManager;
import com.baigu.app.shop.core.order.model.Delivery;
import com.baigu.app.shop.core.order.service.IOrderManager;
import com.baigu.framework.taglib.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 订单详细页，订单货运日志    whj 
 * @param orderid int型， 订单ID
 *2014-03-04下午8:58:00
 */
@Component
@Scope("prototype")
public class OrderDeliveryListTag extends BaseFreeMarkerTag{
	@Autowired
	private IOrderManager orderManager;
	@Autowired
	private IMemberManager memberManager;
	@Autowired
	private IOrderReportManager orderReportManager;
	private Integer orderid;
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		
		Integer orderid  =(Integer)params.get("orderid");
		if(orderid==null){
			throw new TemplateModelException("必须传递orderid参数");
		}
		List<Delivery> deliveryList  = orderReportManager.getDeliveryList(orderid);
		Map result = new HashMap();
		result.put("deliveryList",deliveryList);			
		result.putAll(OrderStatus.getOrderStatusMap());
		return result;
	}
	
	
}
