package com.xjj;

import com.xjj.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FreeMarkerTests extends BasicUtClass{
	@Autowired
	Configuration configuration; //freeMarker configuration
	
	@Test
	public void saveToFile() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("time", new Date());
		model.put("message", "这是测试的内容。。。");
		model.put("toUserName", "张三");
		model.put("fromUserName", "老许");
		
		Template t = configuration.getTemplate("welcome.ftl"); // freeMarker template
		//File path = new File("C:\\opt\\files\\html\\");
		File path = new File("/opt/files/html/");
		if(!path.exists()){
			path.mkdirs();
		}

		String p = path.getPath() + File.separator;

		String fullPath = p + "welcome.html";
		logger.debug("fullPath: {}", fullPath);

		Writer file = new FileWriter(new File(fullPath));

		t.process(model, file);
	}
	 

}
