package cn.myfreecloud.store.web;

import cn.myfreecloud.store.constant.Constant;
import cn.myfreecloud.store.domain.Product;
import cn.myfreecloud.store.service.ProductService;
import cn.myfreecloud.store.utils.BeanFactory;
import cn.myfreecloud.store.utils.UUIDUtil;
import cn.myfreecloud.store.utils.UploadUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProductService productService= BeanFactory.getInstance(ProductService.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取表单中提交过来所有参数
			//可以获取map集合  存放所有请求参数
			Map<String, Object> map=new HashMap<>();
			parseRequest(request,map);
			//参数封装成 product对象
			Product product = new Product();
			BeanUtils.populate(product, map);
			product.setPid(UUIDUtil.getId());
			product.setPdate(new Date());
			product.setPflag(Constant.PRODUCT_ON);
			//调用service 保存product
			productService.save(product);
			//操作成功
			//写回1
			response.getWriter().print("1");
		} catch (Exception e) {
			e.printStackTrace();
			//失败写回0
			response.getWriter().print("0");
		}
	}
	/**
	 * 解析请求过来的数据  并且最终将解析好数据 放入map集合  以备你后面的代码使用
	 * @param request
	 * @param map
	 */
	private void parseRequest(HttpServletRequest request, Map<String, Object> map) {
		//1.创建文件磁盘工厂
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		//2.使用文件磁盘工厂创建文件上传对象
		FileUpload fileUpload = new FileUpload(diskFileItemFactory);
		//3.使用文件上传对象的parse方法  去解析请求数据流
		try {
			List<FileItem> lists = fileUpload.parseRequest(request);
			for (FileItem fileItem : lists) {
				//getFieldName代表的是参数名
				String fieldName = fileItem.getFieldName();
				if(fileItem.isFormField()){
					//此项是普通输入框
					//返回参数值
					String value = fileItem.getString("utf-8");
					map.put(fieldName, value);
				}else{
					//获取表单提交过来的文件名
					String fromBD = fileItem.getName();
					//做两个功能 第一个 将上传过来的文件 保存到一个 resources/products目录下
					InputStream in = fileItem.getInputStream();
					//创建一个输入流
					//servletContext提供了一个方法  获取相对路径
					ServletContext sc = this.getServletContext();
					String uploadDir = sc.getRealPath("/resources/products");
					//随机生成一些子目录   /4/a 
					String subDir = UploadUtils.getDir();
					
					//返回的值 e:\\\tomcat家目录\webapps\store\/resources/products/4/a
					String path=uploadDir+subDir;
					//检测随机子目录存在与否
					checkDir(path);
					String realName=UploadUtils.getUUIDName(UploadUtils.getRealName(fromBD));
					FileOutputStream out = new FileOutputStream(path+"/"+realName);
					//两个流对拷
					IOUtils.copy(in, out);
					out.close();
					in.close();
					//第二功能 将参数名和参数值 保存map集合中 
					//参数名就是pimage 对应值就是保存图片的路径
					map.put(fieldName,"resources/products"+subDir+"/"+realName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkDir(String path) {
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
