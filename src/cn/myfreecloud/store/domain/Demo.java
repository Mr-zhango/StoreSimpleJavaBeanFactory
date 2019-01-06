package cn.myfreecloud.store.domain;

import net.sf.json.JSONObject;

public class Demo {
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public static void main(String[] args) {
		Demo demo = new Demo();
		demo.setAge(18);
		demo.setName("xiaoming");
		JSONObject fromObject = JSONObject.fromObject(demo);
		String s=fromObject.toString();
		Demo bean = (Demo) JSONObject.toBean(JSONObject.fromObject(s), Demo.class);
		System.out.println(bean);
		
	}
	
	
}
