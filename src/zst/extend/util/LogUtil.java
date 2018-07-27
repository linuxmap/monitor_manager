package zst.extend.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import zst.core.service.ISysLogService;

/**
 * 日志操作工具类
 * @author: liuyy
 * @date: 2017-8-10 下午3:11:31
 */
public final class LogUtil {

	private static final Log logger = LogFactory.getLog(LogUtil.class);
	
	/**
	 * 写入日志
	 * @param request
	 * @param logLevel 日志级别
	 * @param operateModule 操作模块
	 * @param operateType 操作类型
	 * @param operateAction 操作动作
	 * @throws Exception
	 */
	public static void writeLog(HttpServletRequest request, Integer logLevel,String operateModule,Integer operateType,String operateAction) throws Exception {
		try {
			WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
			ISysLogService sysLogService = (ISysLogService) ctx.getBean("sysLogService");
			if (sysLogService!=null) {
				sysLogService.insertLog(request, logLevel, operateModule, operateType, operateAction);
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
	}
}
