package zst.extend.listener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

/**
 * 处理用户登录信息的session  防止用户多点登录
 * @author Ablert
 * @date 2018-1-23 上午10:00:28
 */
public class HttpLoginSessionListener implements javax.servlet.http.HttpSessionListener {

	/**
	 * 存储用户登录的sessionid，key为用户主键，value为sessionId 在拦截器使用
	 */
	public static Map<String,String> userMap = new HashMap<String,String>();
	
	/**
	 * 存储用户登录的session，key为用户主键
	 */
	//public static Map<String,HttpSession> sessionMap = new HashMap<String,HttpSession>();
	
	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();
		String sessionId = session.getId();
		Iterator<Entry<String, String>> iterator = userMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			if (entry.getValue().equals(sessionId)) {
				iterator.remove();
			}
		}
	}

}
