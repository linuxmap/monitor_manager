package zst.extend.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理器
 * @author: liuyy
 * @date: 2017-5-23 下午4:22:22
 */
public class PlatformExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest req,
			HttpServletResponse rep, Object handle, Exception ex) {
		
		PlatformException platformException = null;
		if(ex instanceof PlatformException){
			platformException = (PlatformException)ex;
		}else{
			platformException = new PlatformException("未知错误");
		}
		//错误信息
		String message = platformException.getMessage();
		ModelAndView modelAndView = new ModelAndView();
		//将错误信息传到页面
		modelAndView.addObject("message", message);
		//指向错误页面
		modelAndView.setViewName("error/exceptionError");
		return modelAndView;
	}

}
