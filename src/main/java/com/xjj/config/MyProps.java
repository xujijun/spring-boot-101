package com.xjj.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="myProps") //接收application.yml中的myProps下面的属性
public class MyProps {
	private String simpleProp;
	private List<Map<String, String>> listProp1 = new ArrayList<>(); //接收prop1里面的属性值
	private List<String> listProp2 = new ArrayList<>(); //接收prop2里面的属性值
	
	public String getSimpleProp() {
		return simpleProp;
	}
	
	//String类型的一定需要setter来接收属性值；maps, collections, 和 arrays 不需要
	public void setSimpleProp(String simpleProp) {
		this.simpleProp = simpleProp;
	}
	
	public List<Map<String, String>> getListProp1() {
		return listProp1;
	}
	public List<String> getListProp2() {
		return listProp2;
	}
}
