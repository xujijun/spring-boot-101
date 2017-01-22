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
import java.util.*;

public class FreeMarkerTests extends BasicUtClass{
	@Autowired
	Configuration configuration; //freeMarker configuration
	
	@Test
	public void saveToFile() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("time", new Date());

		model.put("toUserName", "张三");
		model.put("fromUserName", "老许");

		List<String> strList = new ArrayList<>();
		for(int i=0; i<5; i++){
			strList.add("循环信息"+i);
		}
		model.put("messages", strList);

		List<Map<String,String>> mapList = new ArrayList<>();
		for(int i=0; i<5; i++){
			Map<String,String> m = new HashMap<>();
			m.put("title", "列表信息标题"+i);
			m.put("content", "详细内容"+i);
			mapList.add(m);
		}
		model.put("mapMsg", mapList);

		Template t = configuration.getTemplate("freemarker.ftl"); // freeMarker template
		//File path = new File("C:\\opt\\files\\html\\");
		File path = new File("/opt/files/html/");
		if(!path.exists()){
			path.mkdirs();
		}

		String p = path.getPath() + File.separator;

		String fullPath = p + "freemarker.html";
		logger.debug("fullPath: {}", fullPath);

		Writer file = new FileWriter(new File(fullPath));

		t.process(model, file);
	}
	 

}
