package zst.extend.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import zst.core.entity.SysUser;
import zst.extend.common.Constant;

/**
 * 公共工具类
 * @author: liuyy
 * @date: 2017-6-21 下午3:47:28
 */
public final class CommonUtil {
	
	/**
	 * 两个字符串不相等返回true
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public static boolean notEqualString(String oldValue,String newValue) {
		if (oldValue==null && newValue==null) {
			return false;
		} else if (oldValue!=null && oldValue.equals(newValue)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 两个Integer不相等  返回true
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public static boolean notEqualInteger(Integer oldValue,Integer newValue) {
		if (oldValue==null && newValue==null) {
			return false;
		} else if (oldValue!=null && newValue!=null && oldValue.intValue()==newValue.intValue()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 两个Double不相等  返回true
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public static boolean notEqualDouble(Double oldValue,Double newValue) {
		if (oldValue==null && newValue==null) {
			return false;
		} else if (oldValue!=null && newValue!=null && oldValue.intValue()==newValue.intValue()) {
			return false;
		} else {
			return true;
		}
	}
	
	public static String getYMdDate(Date date) {  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String dateString = sdf.format(date);  
	    return dateString;  
	}  
	
	/**
	 * 根据map的value获取key
	 * @param <T>
	 * @param map
	 * @param val
	 * @return
	 */
	public static <T> Object getMapKey(Map<T, T> map,Object val){
		Object obj = null;
		
		Set<Entry<T,T>> set = map.entrySet();
		Iterator<Entry<T, T>> it = set.iterator();
		while(it.hasNext()){
			Entry<T, T> entry = it.next();
			if(entry.getValue().equals(val)){
				obj = entry.getKey();
			}
		}
		
		return obj;
	}
	
	/**
	 * 验证集合是否为空
	 * @param list
	 * @return
	 */
	public static <T> boolean validateList(List<T> list){
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}

	/**
	 * 得到32位的uuid
	 */
	public static String get32UUID() {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}

	/**获取session中User **/
	public static SysUser getSessionUser(HttpServletRequest request) {
		SysUser user = (SysUser) request.getSession().getAttribute(Constant.USER_INFO);
		return user;
	}
	
	/**
	 * 获得本地ip
	 */
	public static String getLocalhostIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * cmd执行ping命令
	 */
	public static String cmdPing(String server, int timeout) {
		StringBuffer resultBuffer = new StringBuffer();
		BufferedReader in = null;
		Runtime r = Runtime.getRuntime();

		String pingCommand = "ping " + server + " -w " + timeout;
		try {
			Process p = r.exec(pingCommand);
			if (p == null) {
				return "ping 操作失败";
			}
			in = new BufferedReader(new InputStreamReader(p.getInputStream(), "gbk"));
			String line = null;
			while ((line = in.readLine()) != null) {
				if(!line.trim().isEmpty()){
					resultBuffer.append(line);
					resultBuffer.append("\n");
				}
			}
		} catch (Exception ex) {
			return "ping 操作失败";
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				return "ping 操作失败";
			}
		}
		return resultBuffer.toString();
	}
	
	/**
	 * cmd执行tracert命令
	 */
	public static String cmdTracert(String server) {
		StringBuffer resultBuffer = new StringBuffer();
		BufferedReader in = null;
		Runtime r = Runtime.getRuntime();

		String pingCommand = "tracert " + server + " -w 1000";
		try {
			Process p = r.exec(pingCommand);
			if (p == null) {
				return "tracert 操作失败";
			}
			in = new BufferedReader(new InputStreamReader(p.getInputStream(), "gbk"));
			String line = null;
			while ((line = in.readLine()) != null) {
				if(!line.trim().isEmpty()){
					resultBuffer.append(line);
					resultBuffer.append("\n");
				}
			}
		} catch (Exception ex) {
			return "tracert 操作失败";
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				return "tracert 操作失败";
			}
		}
		return resultBuffer.toString();
	}

	/** 
     * Clone Object 
     */  
    public static Object cloneObject(Object obj) throws Exception{
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();   
        ObjectOutputStream out = new ObjectOutputStream(byteOut);   
        out.writeObject(obj);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());   
        ObjectInputStream in =new ObjectInputStream(byteIn);  
        return in.readObject();  
    }
    /**获取登录人IP **/
	public static String getIpAddr(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	} 
	
	/**
	 * 求两个list的差集(复杂类型需重写equals方法)
	 * @param list1 
	 * @param list2
	 * @return list1中不包含list2的差集
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List diffList(List list1, List list2) { 
        List list = new ArrayList(Arrays.asList(new Object[list1.size()])); 
        Collections.copy(list, list1); 
        list.removeAll(list2); 
        return list; 
    }
	
	
	/**
	 * 解决路径编码
	 * @param filePath
	 * @return
	 */
	public static String decodeFilePath (String filePath){
		if(filePath != null){
			try {
				filePath = URLDecoder.decode(filePath, "UTF-8");
				return filePath;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
