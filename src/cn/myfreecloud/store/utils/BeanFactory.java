package cn.myfreecloud.store.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BeanFactory {

    //声明文档对象
	private static Document doc;
	static{
		SAXReader saxReader = new SAXReader();
		try {
		    //读取配置文件
			doc = saxReader.read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));
		} catch (DocumentException e) {
			e.printStackTrace();
			//如果配置文件没有正常解析,就直接抛异常了,不要在执行程序了
			throw new RuntimeException(e);
		}
	}

	//主要给我传一个接口的class 我就给你返回该接口的具体实现类对象(Spring的核心IOC[实现将组件间的关系从程序内部提到外部容器（spring的xml）来管理。])

    /**
     * 具体的方法的实现
     * @param clazz 接口的class
     * @param <T>   接口的具体实现类的对象
     * @return
     */
    public static <T>T getInstance(Class<T> clazz){

        // 获取到接口的名字
		String interfaceName = clazz.getSimpleName();

		// 根据你接口的名字 去beans.xml寻找对应bean标签


        // xPath语法,解析xml用的    //bean 表示从匹配选择的当前节点选择文档中的节点,而不考虑他们的位置
        // @name选择属性
		Element beanEle=(Element) doc.selectSingleNode("//bean[@name='"+interfaceName+"']");
		//获取到bean标签 能不能获取的该标签的 className属性.的值

		//就是你配置的具体的实现类全限定名
		String className = beanEle.attribute("className").getValue();
		//通过反射 创建一个具体实现类对象 返回
		try {
		    //获取到这个类
			Class forName = Class.forName(className);
			//反射创建这个类的对象
			final Object newInstance = forName.newInstance();

			if(interfaceName.endsWith("Service")){
				//要求所有的生成的service对象是一个被增强对象
				Object newProxyInstance = Proxy.newProxyInstance(newInstance.getClass().getClassLoader(), newInstance.getClass().getInterfaces(), new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						//想办法获取实现类方法
						/*Class<? extends Object> class1 = newInstance.getClass();
						//获取到实现的方法
						Method method2 = class1.getMethod(method.getName(), method.getParameterTypes());
						if(method2.isAnnotationPresent(Log.class)){
							//加上前置加强
							//开启事务
							
							try {
								//开启事务
								method.invoke(newInstance, args);
								//提交事务
							} catch (Exception e) {
								e.printStackTrace();
								//回滚事务
							}
						}
						return null;*/
						//System.out.println("**************"+method.toGenericString());
						return method.invoke(newInstance, args);
					}
				});


				return (T) newProxyInstance;
			}else{
				return (T) newInstance;
			}
			
		} catch (Exception e) {
			e.printStackTrace();

            //如果反射创建对象失败依旧抛异常停止程序
			throw new RuntimeException(e);
		}
	}
}
