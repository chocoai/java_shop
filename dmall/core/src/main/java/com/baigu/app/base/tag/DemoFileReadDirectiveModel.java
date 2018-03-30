package com.baigu.app.base.tag;

import java.io.IOException;
import java.util.Map;

import com.baigu.framework.util.FileUtil;
import com.baigu.framework.util.StringUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class DemoFileReadDirectiveModel implements TemplateDirectiveModel {

	@Override
	public void execute(Environment arg0, Map params, TemplateModel[] arg2,
			TemplateDirectiveBody arg3) throws TemplateException, IOException {
		 String filename=(String)params.get("filename");
		 String app_apth = StringUtil.getRootPath();
		 String filePath = app_apth+"/docs/tags/demo/"+filename;
		 String content = FileUtil.read(filePath, "UTF-8");
		 
	}

}
