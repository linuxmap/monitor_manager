package zst.extend.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * properties文件工具类
 * @author: liuyy
 * @date: 2017-7-24 上午10:05:33
 */
public final class PropertiesUtil {
	
	private static final Log logger = LogFactory.getLog(PropertiesUtil.class);
	/***
	 * 获取指定的Properties文件
	 * @param file properties文件名
	 * @return Properties
	 */
	public static Properties getProperties(String file) {
		Properties props = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("WEB-INF/classes/").getPath();
		try {
			String filePath = CommonUtil.decodeFilePath(path + file);
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			return props;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取properties文件指定key的value
	 * @param file properties文件名
	 * @param key key值
	 * @return value值
	 */
	public static String getPropertiesValue(String file, String key) {
		Properties props = new Properties();
		String path = PropertiesUtil.class.getResource("/").getPath();
		try {
			String filePath = CommonUtil.decodeFilePath(path + file);
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			return props.getProperty(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 设置指定key的value值
	 * @param file properties文件名
	 * @param key key值
	 * @param value value值
	 */
	public static void setProperties(String file, String key, String value) {
		Properties props = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		logger.info("path:"+path);
		try {
			String filePath = CommonUtil.decodeFilePath(path + file);
			logger.info("filePath:"+filePath);
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			if(props.containsKey(key)) {
				props.setProperty(key, value);
				OutputStream out = new FileOutputStream(filePath);
				// 以适合使用 load 方法加载到 Properties 表中的格式，   
	            // 将此 Properties 表中的属性列表（键和元素对）写入输出流   
	            props.store(out, "Update '" + key + "' value");  
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
