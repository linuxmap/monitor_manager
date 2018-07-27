package zst.extend.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import zst.core.entity.SysUser;
import zst.extend.listener.HttpLoginSessionListener;
import zst.extend.util.CommonUtil;
import zst.extend.util.RequestUtil;

/**
 * 访问请求的拦截器
 * @author songxiang
 */
public class AccessRequestInterceptor extends HandlerInterceptorAdapter{

	private static final Log logger = LogFactory.getLog(AccessRequestInterceptor.class);

	@Override    
    public boolean preHandle(HttpServletRequest request,    
            HttpServletResponse response, Object handler) throws Exception {    
        if ("GET".equalsIgnoreCase(request.getMethod())) {  
            RequestUtil.saveRequest();  
        }  
        //logger.info("==============执行顺序: 1、preHandle================");    
        String requestUri = request.getRequestURI();  
        String contextPath = request.getContextPath();  
        String url = requestUri.substring(contextPath.length());  
        
        SysUser sysUser = CommonUtil.getSessionUser(request);
		if (null==sysUser || null== sysUser.getUserId()) {//TODO 待优化到xml配置中
        	if("/ssoLoginAdValidate.action".equals(url) || "/toSsoLoginAdValidate.action".equals(url) || "/ssoLoginEntry.action".equals(url) || "/toSsoLogin.action".equals(url) || "/toSsoLoginManageEnd.action".equals(url) || "/ssoLoginManageEnd.action".equals(url) || "/downloadClientSoftWare.action".equals(url)){
        		logger.info("单点登录，AD域验证");
        		return true;
        	}
        	//单点登录携带jsessionid
        	if (url!=null && url.startsWith("/toSsoLogin.action", 0)) {
        		logger.info("单点登录，AD域验证");
        		return true;
        	}
        	logger.info("Interceptor：跳转到login页面！");  
            request.getRequestDispatcher("/login.jsp").forward(request, response);  
            return false;  
        } else {
        	//已经登录判断session是否是最新的 TODO 定时清理
        	if ((request.getSession().getId()).equals(HttpLoginSessionListener.userMap.get(sysUser.getUserId().toString()))) {
        		return true;
        	} else {
        		request.setAttribute("forceLoginMsg", "被迫下线，您已在其它地方登录。");
        		request.getRequestDispatcher("/login.jsp").forward(request, response);
        		return false;
        	}
        	//return true;
        }
    }    
    
    /** 
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作    
     * 可在modelAndView中加入数据，比如当前时间 
     */  
    @Override    
    public void postHandle(HttpServletRequest request,    
            HttpServletResponse response, Object handler,    
            ModelAndView modelAndView) throws Exception {     
    	//logger.info("==============执行顺序: 2、postHandle================");    
        /*if(modelAndView != null){  //加入当前时间    
            modelAndView.addObject("var", "测试postHandle");    
        }   */ 
    }    
	    
    /**  
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等   
     *   
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()  
     */    
    @Override    
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)    
            throws Exception {    
    	//logger.info("==============执行顺序: 3、afterCompletion================");
    }   
}
