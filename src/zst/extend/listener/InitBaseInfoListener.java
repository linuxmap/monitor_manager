package zst.extend.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import zst.extend.enums.FileEnum;
import zst.extend.util.PropertiesUtil;

/**
 * 启动服务器加载初始化信息，从properties文件中获取数据
 * @author Ablert
 *
 */
public class InitBaseInfoListener implements ServletContextListener {

	private static final Log logger = LogFactory.getLog(InitBaseInfoListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		String propertiesFileName = "sysConfig.properties";
		FileEnum.clientSoftWareName= PropertiesUtil.getPropertiesValue(propertiesFileName, "clientSoftWareName");

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("服务关闭");
	}

}
