package com.baigu.app.shop.front.tag.goods.search;

import java.util.HashMap;
import java.util.Map;

import com.baigu.app.shop.core.goods.service.IGoodsCatManager;
import com.baigu.app.shop.core.goods.service.IGoodsSearchManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.baigu.app.shop.core.goods.model.Cat;
import com.baigu.app.shop.core.goods.plugin.search.GoodsSearchPluginBundle;
import com.baigu.app.shop.core.goods.service.SearchEngineFactory;
import com.baigu.framework.taglib.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 
 * (商品品牌标签) 
 * @author zjp
 * @version v1.0
 * @since v6.2
 * 2017年5月31日 下午6:52:44
 */
@Component
@Scope("prototype")
public class SearchSelectorBrandTag extends BaseFreeMarkerTag {
	@Autowired
	private IGoodsCatManager goodsCatManager ;
	
	@Autowired
	private GoodsSearchPluginBundle goodsSearchPluginBundle;

	@Override
	protected Object exec(Map params) throws TemplateModelException {
		IGoodsSearchManager goodsSearchManager = SearchEngineFactory
				.getSearchEngine();
		Integer cat_id =(Integer)params.get("cat_id");
		
		Cat goodscat  =this.goodsCatManager.getById(cat_id);
		Map selectorMap = new HashMap();
		this.goodsSearchPluginBundle.createSelectorList(selectorMap, goodscat);
		
		return selectorMap;

	}

}
