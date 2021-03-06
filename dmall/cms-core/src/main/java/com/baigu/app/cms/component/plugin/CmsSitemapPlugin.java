package com.baigu.app.cms.component.plugin;

import java.util.List;
import java.util.Map;

import com.baigu.app.cms.core.model.DataCat;
import com.baigu.app.cms.core.model.DataModel;
import com.baigu.app.cms.core.service.IDataCatManager;
import com.baigu.app.cms.core.service.IDataManager;
import org.springframework.stereotype.Component;

import com.baigu.app.base.core.model.SiteMapUrl;
import com.baigu.app.base.core.plugin.IRecreateMapEvent;
import com.baigu.app.base.core.service.ISitemapManager;
import com.baigu.app.cms.core.plugin.IDataDeleteEvent;
import com.baigu.app.cms.core.plugin.IDataSaveEvent;
import com.baigu.framework.plugin.AutoRegisterPlugin;

/**
 * CMS的sitemap生成插件
 * @author kingapex
 *
 */
@Component
public class CmsSitemapPlugin extends AutoRegisterPlugin implements	IDataSaveEvent, IDataDeleteEvent, IRecreateMapEvent {
	
	private ISitemapManager sitemapManager;
	private IDataCatManager dataCatManager;
	private IDataManager dataManager;

	public void register() {

	}

	public void onSave(Map<String, Object> data, DataModel dataModel, int dataSaveType) {
		DataCat cat = this.dataCatManager.get(Integer.valueOf(data.get("cat_id").toString()));
		if (cat.getTositemap() == 1) { // 要加入到Sitemap
			SiteMapUrl url = new SiteMapUrl();
			url.setLoc("/" + cat.getDetail_url() + "-" + cat.getCat_id() + "-" + data.get("id") + ".html");
			url.setLastmod(System.currentTimeMillis());
			this.sitemapManager.addUrl(url);
		}
	}

	public void onDelete(Integer catid, Integer articleid) {
		DataCat cat = this.dataCatManager.get(catid);
		
		//ISitemapManager这个接口暂时没有实现类，会抛出空指针异常，所以暂时注释掉
		//this.sitemapManager.delete("/" + cat.getDetail_url() + "-" + cat.getCat_id() + "-" + articleid + ".html");
	}
	
	public void onRecreateMap() {
		List<DataCat> listCat = this.dataCatManager.listAllChildren(0);
		for (DataCat cat : listCat) {
			if (cat.getTositemap() == 1) {
				List<Map<String, Object>> list = this.dataManager.list(cat.getCat_id());
				for (Map map : list) {
					SiteMapUrl url = new SiteMapUrl();
					url.setLoc("/" + cat.getDetail_url() + "-" + cat.getCat_id() + "-" + map.get("id") + ".html");
					url.setLastmod(System.currentTimeMillis());
					this.sitemapManager.addUrl(url);
				}
			}
		}
	}

	
	public IDataCatManager getDataCatManager() {
		return dataCatManager;
	}

	public void setDataCatManager(IDataCatManager dataCatManager) {
		this.dataCatManager = dataCatManager;
	}

	public IDataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(IDataManager dataManager) {
		this.dataManager = dataManager;
	}

	public ISitemapManager getSitemapManager() {
		return sitemapManager;
	}

	public void setSitemapManager(ISitemapManager sitemapManager) {
		this.sitemapManager = sitemapManager;
	}

}
