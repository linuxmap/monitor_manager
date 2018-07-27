package zst.extend.enums;

/**
 * 日志的枚举值
 * @author Ablert
 * @date 2018-2-22 上午10:32:33
 */
public class LogEnum {
	
	public static final Integer MANAGER_END = 1;//管理端
	public static final Integer CLIENT_END = 2;//客户端
	
	public static final String MANAGER = "管理端";//管理端
	public static final String CLIENT = "客户端";//客户端
	
	//操作类型 
	public interface OperatorType{
		/**
		 * 新增操作
		 */
		public static final Integer INSERTOPERATION = 1;
		
		/**
		 * 标记删除操作
		 */
		public static final Integer LOGICALDELOPERATION = 2;
		/**
		 * 真实删除
		 */
		public static final Integer PHYSICALDELOPERATION = 3;
		/**
		 * 修改操作
		 */
		public static final Integer UPDATEOPERATION = 4;
		/**
		 * 查询操作
		 */
		public static final Integer QUERYOPERATION = 5;
		/**
		 * 登录操作
		 */
		public static final Integer LOGINOPERATION = 6;
		/**
		 * 云台控制
		 */
		public static final Integer CLOUDCONTROL = 7;
		/**
		 * 操作记录
		 */
		public static final Integer OPERATERECORD = 7;
		
	}
	
	//操作模块
	public interface OperatorModule{
		/**
		 * 登录模块
		 */
		public static final String LOGIN_MODULE = "登录";
		/**
		 * 退出登录
		 */
		public static final String LOGINOUT_MODULE = "退出登录";
		/**组织机构模块 **/
		public static final String ORG_MODULE = "组织机构";
		/**用户管理模块**/
		public static final String USER_MODULE = "用户管理";
		/**菜单管理模块 **/
		public static final String MENU_MODULE = "菜单管理";
		/**数据权限管理模块 **/
		public static final String DATAPERMISSION_MODULE = "数据权限管理";
		/**功能权限管理模块**/
		public static final String FUNCTION_MODULE = "功能权限管理";
		/**系统配置模块 **/
		public static final String SYSCONFIG_MODULE = "系统配置";
		/**告警设置模块 **/
		public static final String ALARM_MODULE = "告警设置";
		/**告警阈值配置模块**/
		public static final String THRESHOLD_MODULE = "告警阈值配置";
		/**设备管理模块 **/
		public static final String ASSET_MANAGE = "设备管理";
		/**资源整合模块 **/
		public static final String ASSET_INTEGERATION = "资源整合";
		/**短信配置模块 **/
		public static final String MESSAGE_CONFIG = "短信配置";
		/**短信发送记录查询导出**/
		public static final String MESSAGE_QUERY = "短信记录查询";
		/**日志查询**/
		public static final String LOG_QUERY = "日志查询";
	}
	
	/**
	 * 日志级别
	 * @author Ablert
	 * @date 2018-2-22 上午10:34:49
	 */
	public interface LogLevel {
		
		/**
		 * 删除
		 */
		public static final Integer DELETE = 1;
		/**
		 * 修改
		 */
		public static final Integer MODIFY = 2;
		/**
		 * 新增
		 */
		public static final Integer ADD = 3;
		/**
		 * 操作记录
		 */
		public static final Integer OPERATE = 4;
		/**
		 * 查询
		 */
		public static final Integer QUERY = 5;
	}
	
}
