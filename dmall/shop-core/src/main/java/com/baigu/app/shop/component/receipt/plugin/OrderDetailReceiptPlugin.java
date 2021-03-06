package com.baigu.app.shop.component.receipt.plugin;


import com.baigu.app.shop.component.receipt.service.IReceiptManager;
import com.baigu.app.shop.core.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baigu.app.shop.core.order.plugin.order.IOrderTabShowEvent;
import com.baigu.app.shop.core.order.plugin.order.IShowOrderDetailHtmlEvent;
import com.baigu.eop.processor.core.freemarker.FreeMarkerPaser;
import com.baigu.framework.plugin.AutoRegisterPlugin;
import com.baigu.framework.plugin.IAjaxExecuteEnable;

/**
 * 订单详细页发票信息显示插件
 * @author kingapex
 *2012-2-17下午5:46:26
 *@author LiFenLong 2014-4-14;4.0改版订单信息显示修改为不走异步
 */
@Component
public class OrderDetailReceiptPlugin extends AutoRegisterPlugin implements
IOrderTabShowEvent,IShowOrderDetailHtmlEvent,  IAjaxExecuteEnable{
	
	@Autowired
	private IReceiptManager receiptManager;
	
	

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String onShowOrderDetailHtml(Order order) {
		FreeMarkerPaser freeMarkerPaser =FreeMarkerPaser.getInstance();
		freeMarkerPaser.setClz(this.getClass());
		
//		Receipt receipt = this.receiptManager.getById(order.getReceipt_id());
		
		freeMarkerPaser.putData("order",order);
		freeMarkerPaser.setPageName("receipt_detail");
		return freeMarkerPaser.proessPageContent();
//		
//		FreeMarkerPaser freeMarkerPaser =FreeMarkerPaser.getInstance();
//		int orderId =order.getOrder_id();
//		freeMarkerPaser.putData("orderid",orderId);
//		freeMarkerPaser.setPageName("receipt");
//		//System.out.println(freeMarkerPaser.proessPageContent());
//		return freeMarkerPaser.proessPageContent();
	}

	@Override
	public String getTabName(Order order) {
		
		return "发票";
	}

	@Override
	public int getOrder() {
		
		return 11;
	}

	@Override
	public boolean canBeExecute(Order order) {
		
		return true;
	}

}
