package zst.extend.util;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 信息输出工具类
 * @desc  输出数据到页面，供页面显示
 * @author: liuyy
 * @date: 2017-6-13 上午9:38:49
 */
public final class PageUtil {

	/**
	 * 简易返回，用于ajax非跳转的页面请求
	 * @param strSuccee 执行是否成功标志,可以自定义
	 * @param strMsg 返回的提示信息
	 * @param strOwer 自定义字段,可为空,可自定义带回页面的值
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	public static void toBeJsonByMap(String strSuccee, String strMsg, Object strOwer, HttpServletResponse response) throws Exception {
		JSONObject jobj = new JSONObject(); 
		jobj.put("success", strSuccee); // 配置success项，固定值
		jobj.put("msg", strMsg); // 配置msg项，固定值
		jobj.put("ower", strOwer); // 配置ower项，自定义项，可根据业务逻辑自行定义

		writeDataToClient(jobj,response);
	}
	
	/**
	 * 将json的JSONObject数据返回到jsp页面，多态
	 * @param jobj
	 * @param response
	 * @throws Exception
	 */
	public static void writeDataToClient(JSONObject jobj, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(jobj.toString()); // 转化为JSOn格式
		response.getWriter().close();
	}
	
	/**
	 * 将json的JSONArray数据返回到jsp页面，多态
	 * @param jobj
	 * @param response
	 * @throws Exception
	 */
	public static void writeDataToClient(JSONArray jobj, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(jobj.toString()); // 转化为JSOn格式
		response.getWriter().close();
	}
}
