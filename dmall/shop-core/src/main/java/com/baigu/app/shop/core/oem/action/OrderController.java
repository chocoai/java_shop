package com.baigu.app.shop.core.oem.action;

import com.baigu.app.shop.core.oem.model.OemOrder;
import com.baigu.app.shop.core.oem.service.IOrderManager;
import com.baigu.framework.action.GridController;
import com.baigu.framework.action.GridJsonResult;
import com.baigu.framework.action.JsonResult;
import com.baigu.framework.util.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * create by xt on 2018-07-26 17:00
 */
@Controller("oemOrderController")
@Scope("prototype")
@RequestMapping("/shop/admin/oem/order")
@SuppressWarnings({"rawtypes", "unchecked"})
public class OrderController extends GridController {

    @Autowired
    private IOrderManager orderManager;

    @RequestMapping(value = "/unshipList")
    public ModelAndView unshipList() {
        ModelAndView view = getGridModelAndView();
        view.setViewName("/shop/admin/oem/order/unship_order_list");
        return view;
    }

    @RequestMapping(value = "/shipList")
    public ModelAndView shipList() {
        ModelAndView view = getGridModelAndView();
        view.setViewName("/shop/admin/oem/order/ship_order_list");
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/setShipped", method = RequestMethod.POST)
    public JsonResult setShipped(Integer[] ids) {
        try {
            if (ids.length > 0) {
                orderManager.setShipped(ids);
            }
            return JsonResultUtil.getSuccessJson("操作成功");
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return JsonResultUtil.getErrorJson("操作失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/list-json")
    public GridJsonResult listJson(String keyword, Integer status) {
        Map<String, Object> where = new HashMap<String, Object>();
        where.put("keyword", keyword);
        where.put("status", status);
        webpage = orderManager.pageSale(where, this.getPage(), this.getPageSize(), this.getSort(), this.getOrder());
        return JsonResultUtil.getGridJson(webpage);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonResult delete(Integer id) {
        try {
            orderManager.delete(id);
            return JsonResultUtil.getSuccessJson("操作成功");
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return JsonResultUtil.getErrorJson("操作失败");
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/shop/admin/oem/order/order_add");
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/addSubmit", method = RequestMethod.POST)
    public JsonResult addSubmit(OemOrder order) {
        try {
            orderManager.add(order);
            return JsonResultUtil.getSuccessJson("操作成功");
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return JsonResultUtil.getErrorJson("操作失败");
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(Integer id) {
        ModelAndView view = new ModelAndView();
        OemOrder order = orderManager.get(id);
        view.addObject("order", order);
        view.setViewName("/shop/admin/oem/order/order_edit");
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public JsonResult editSubmit(OemOrder order) {
        try {
            orderManager.update(order);
            return JsonResultUtil.getSuccessJson("操作成功");
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return JsonResultUtil.getErrorJson("操作失败");
        }
    }
}
