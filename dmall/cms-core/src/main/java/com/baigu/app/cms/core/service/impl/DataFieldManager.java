package com.baigu.app.cms.core.service.impl;

import java.util.List;

import com.baigu.app.cms.core.model.DataField;
import com.baigu.app.cms.core.model.DataModel;
import com.baigu.app.cms.core.plugin.ArticlePluginBundle;
import com.baigu.app.cms.core.service.IDataFieldManager;
import com.baigu.app.cms.core.service.IDataModelManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baigu.framework.annotation.Log;
import com.baigu.framework.database.IDaoSupport;
import com.baigu.framework.log.LogType;

/**
 * 模型数据字段管理
 * @author DMRain 2016年3月1日 版本改造
 * @version v2.0 改为spring mvc
 * @since v6.0
 */
@Service
public class DataFieldManager implements IDataFieldManager {

	@Autowired
	private IDaoSupport daoSupport;
	
	@Autowired
	private IDataModelManager dataModelManager;
	
	@Autowired
	private ArticlePluginBundle articlePluginBundle;

	/* (non-Javadoc)
	 * @see IDataFieldManager#add(DataField)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	@Log(type=LogType.SETTING,detail="名为${dataField.china_name}的文章模型添加字段")
	public Integer add(DataField dataField) {
		dataField.setAdd_time(System.currentTimeMillis());
		this.daoSupport.insert("es_data_field", dataField);
		Integer fieldid = this.daoSupport.getLastId("es_data_field");
		DataModel datamodel = this.dataModelManager.get(dataField.getModel_id());
		StringBuffer sql = new StringBuffer();
		sql.append("alter table ");
		sql.append(this.getModelTableName(datamodel.getEnglish_name()));
		sql.append(" add ");
		sql.append(this.getFieldTypeSql(dataField));

		this.daoSupport.execute(sql.toString());
		return fieldid;
	}


	/* (non-Javadoc)
	 * @see IDataFieldManager#delete(java.lang.Integer)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	@Log(type=LogType.SETTING,detail="删除文章模型ID为${fieldid}的所有字段")
	public void delete(Integer fieldid) {

		DataField dataField = this.get(fieldid);
		DataModel dataModel = this.dataModelManager.get(dataField.getModel_id());

		// 删除模型中的相应字段
		String sql = "alter table "	+ this.getModelTableName(dataModel.getEnglish_name()) + " drop column " + dataField.getEnglish_name();
		this.daoSupport.execute(sql);

		// 删除字段表里的数据
		sql = "delete from  es_data_field where field_id=?";
		this.daoSupport.execute(sql, fieldid);
	}

	/* (non-Javadoc)
	 * @see IDataFieldManager#edit(DataField)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	@Log(type=LogType.SETTING,detail="修改名为${dataField.china_name}模型的字段信息")
	public void edit(DataField dataField) {
		DataField oldDataField = this.get(dataField.getField_id());
		this.daoSupport.update("es_data_field", dataField, "field_id="	+ dataField.getField_id());
		if (!oldDataField.getEnglish_name().equals(dataField.getEnglish_name())) {// 变更了字段名
			DataModel dataModel = this.dataModelManager.get(oldDataField.getModel_id());
			StringBuffer sql = new StringBuffer();
			sql.append("alter table ");
			sql.append(this.getModelTableName(dataModel.getEnglish_name()));
			sql.append(" change column ");
			sql.append(oldDataField.getEnglish_name());
			sql.append(" ");
			sql.append(this.getFieldTypeSql(dataField));
			this.daoSupport.execute(sql.toString());
		}
	
	}

	/* (non-Javadoc)
	 * @see IDataFieldManager#list(java.lang.Integer)
	 */
	@Override
	public List<DataField> list(Integer modelid) {
		return this.daoSupport.queryForList("select * from es_data_field where model_id = ? order by taxis",	DataField.class, modelid);
	}

	/* (non-Javadoc)
	 * @see IDataFieldManager#get(java.lang.Integer)
	 */
	@Override
	public DataField get(Integer fieldid) {
		String sql = "select * from es_data_field where field_id = ?";
		return this.daoSupport.queryForObject(sql, DataField.class, fieldid);
	}

	/* (non-Javadoc)
	 * @see IDataFieldManager#listByCatId(java.lang.Integer)
	 */
	@Override
	public List<DataField> listByCatId(Integer catid) {
		String sql = "select df.* from es_data_field df, es_data_model dm, es_data_cat c "
				+ "where df.model_id = dm.model_id and dm.model_id = c.model_id and c.cat_id = ? order by df.taxis";

		return this.daoSupport.queryForList(sql, DataField.class, catid);
	}

	/* (non-Javadoc)
	 * @see IDataFieldManager#listIsShow(java.lang.Integer)
	 */
	@Override
	public List<DataField> listIsShow(Integer modelid) {
		return this.daoSupport.queryForList("select * from es_data_field where model_id = ? and is_show = 1 order by taxis", DataField.class, modelid);
	}

	/* (non-Javadoc)
	 * @see IDataFieldManager#saveSort(java.lang.Integer[], java.lang.Integer[])
	 */
	@Override
	public void saveSort(Integer[] ids, Integer[] sorts) {
		String sql;
		for (int i = 0; i < ids.length; i++) {
			sql = "update es_data_field set taxis = ? where field_id = ?";
			this.daoSupport.execute(sql, sorts[i], ids[i]);
		}
	}

	private String getModelTableName(String tbname) {
		tbname = "es_" + tbname;
		return tbname;
	}
	
	/**
	 * 拼装增加字段数据类型及大小sql
	 * 
	 * @param field_name
	 * @param data_type
	 *            1:字符串 2:文本
	 * @param data_size
	 * @return
	 */
	private String getFieldTypeSql(DataField field) {
		return field.getEnglish_name() + " " + this.articlePluginBundle.getFieldPlugin(field).getDataType();
	}
}
