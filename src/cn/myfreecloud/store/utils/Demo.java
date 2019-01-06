package cn.myfreecloud.store.utils;

import java.io.IOException;
import java.util.ResourceBundle;

public class Demo {

	public static void main(String[] args) throws IOException {
	    //传统的读取配置文件的方式
		/*Properties properties = new Properties();
		properties.load(Demo.class.getClassLoader().getResourceAsStream("merchantInfo.properties"));
		String property = properties.getProperty("p1_MerId");
		System.out.println(property);*/


		//ResourceBundle解析配置文件的专用类

		ResourceBundle bundle = ResourceBundle.getBundle("merchantInfo");
		String string = bundle.getString("p1_MerId");
		System.out.println(string);
		
	}
}
