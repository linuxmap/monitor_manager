package zst.extend.common;

/**
 * 保存系统公用的常量
 * @author: liuyy
 * @date: 2017-5-31 下午2:05:55
 */
public class Constant {
	
	/**session中用户信息的key **/
	public static final String USER_INFO = "userInfo";
	/**session中该用户所拥有的菜单 **/
	public static final String USER_FUNT = "userFunt";
	/**session中该用户所拥有的层级菜单 **/
	public static final String USER_BUTTON = "userButton";
	/**用户初始密码 **/
	public static final String INIT_PWD = "123456";
	/** 分页每页显示的数据条数 */
	public static final String PAGE_SIZE = "10";
	/**用户被锁定时长(分钟) **/
	public static final int LOCK_MINUTE = 10;
	/**是否启用密码重视程度 **/
	public static final boolean AUTH_PWD = true;
	/**cookie有效期(天)**/
	public static final int COOKIE_DAY = 3;
	
	/**session中该用户所拥有的层级菜单,用于页面上获取 **/
	public static final String BUTTON_LIST = "buttonList";

}
