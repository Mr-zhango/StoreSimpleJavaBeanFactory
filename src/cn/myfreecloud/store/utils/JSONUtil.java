package cn.myfreecloud.store.utils;

import cn.myfreecloud.store.domain.User;
import net.sf.ezmorph.ObjectMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JSONUtil {
	private static  JsonConfig config = new JsonConfig(); 
	static{
		DateValueProcessor dateValueProcessor = new DateValueProcessor("yyyy-MM-dd");
		config.registerJsonValueProcessor(Date.class, dateValueProcessor);
	}
	public static String arr2str(Object[] objects){
		return JSONArray.fromObject(objects,config).toString();
	}
	public static String list2str(List<Object> list){
		return JSONArray.fromObject(list,config).toString();
	}
	public static String bean2str(Object obj){
		return JSONObject.fromObject(obj,config).toString();
	}
	public static String map2str(Map<Object, Object> map){
		return JSONObject.fromObject(map,config).toString();
	}
	public static JSONArray str2JSONArray(String str){
		return JSONArray.fromObject(str);
	}
	public static JSONObject str2JSONObject(String str){
		return JSONObject.fromObject(str);
	}
	private static class Datexxx implements ObjectMorpher{

		@Override
		public Class morphsTo() {
			return null;
		}

		@Override
		public boolean supports(Class arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object morph(Object arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	private static class DateValueProcessor implements JsonValueProcessor{
		public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
		private DateFormat dateFormat;
		public DateValueProcessor(String datePattern) {
			try {
				dateFormat = new SimpleDateFormat(datePattern);
			} catch (Exception e) {
				dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
			} 
		}
		 //processArrayValue:处理值并返回一个合适的JSON值。  
	    @Override  
	    public Object processArrayValue(Object value, JsonConfig jsonConfig) {  
	        return process(value);  
	    }  
	    //processObjectValue:处理值并返回一个合适的JSON值。  
	    @Override  
	    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {  
	        return process(value);  
	    }  
	      
	    private Object process(Object value) {  
	        return dateFormat.format((Date) value);  
	    }  
	}
	public static void main(String[] args) {
		User user = new User();
		//user.setBirthday(new Date());
		user.setName("zhangsan");
		String bean2str = bean2str(user);
		System.out.println(bean2str);
		
		/*System.out.println(bean2str(user));
		String s="{'birthday':'2018-03-02','code':'','email':'','name':'zhangsan','password':'','sex':'','state':0,'telephone':'','uid':'','username':''}";
		JSONObject fromObject = JSONObject.fromObject(s);
		Object bean = JSONObject.toBean(fromObject, User.class);
		System.out.println(bean);*/
	}
	
}
