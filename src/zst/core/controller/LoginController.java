package zst.core.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import zst.core.dao.SysUserMapper;
import zst.core.entity.SysBaseInfo;
import zst.core.entity.SysFunt;
import zst.core.entity.SysUser;
import zst.core.entity.SysUserRole;
import zst.core.service.ISysBaseService;
import zst.core.service.ISysFuntService;
import zst.core.service.ISysUserRoleService;
import zst.core.service.ISysUserService;
import zst.extend.common.Constant;
import zst.extend.enums.LogEnum;
import zst.extend.exception.PlatformException;
import zst.extend.listener.HttpLoginSessionListener;
import zst.extend.util.CollectionUtil;
import zst.extend.util.CommonUtil;
import zst.extend.util.CookieUtil;
import zst.extend.util.LDAPValidationUtil;
import zst.extend.util.LogUtil;
import zst.extend.util.PageUtil;
import zst.extend.util.SecurityUtil;

/**
 * 登录Controller
 * @author: liuyy
 * @date: 2017-6-13 下午1:59:42
 */
@Controller
public class LoginController {

	private static final Log logger = LogFactory.getLog(LoginController.class);
	
	private static final String ENTRY_FROM = "ad";//来自AD域访问标识 
	
	//AD域直接验证
	private static final String LOGIN_VERIFY_FLAG_SSO = "1";
	
	private static final String FILE_NAME_KEY = "fileNameKey";//文件名在数据库中的key
	
	private static final String FILE_PATH_KEY = "filePathKey";//文件磁盘路径在数据库中的key
	
	private static final String GROUP = "clienSoftWare";//客户端配置信息标识
	
	//以下两个字段用于标识AD域登录或者管理端直接登录
	private static final String LOGINWAYKEY = "LoginWayKey";
	private static final Integer LOGINFROMAD = 0;//ad域登录
	private static final Integer LOGINFROMMANGER = 1;//平台登录
	
	//单点登录的3个参数
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String FROM = "from";
	
	private static final String MODIFY_PASSWORD_SHOW_DB = "modifyPassword";//数据库中修改密码的样式
	private static final String MODIFY_PASSWORD_SHOW = "modifyPasswordShow";//index的jsp页面的修改密码的样式取值
	private static final String DISPLAY = "display";//显示
	
	private static final int VALID_USER_FLAG = 1;//有效用户标识
	
	/**
	 * 加密用的分隔符
	 */
	public static final String ENCRYPT_SEPARATOR = ",";

	@Resource
	ISysUserService sysUserService;
	
	@Resource
	ISysFuntService sysFuntService;
	
	@Resource
	private ISysBaseService baseService;
	
	@Resource
	private ISysUserRoleService userRoleService;
	
	@Resource
	private SysUserMapper userMapper;
	
	
	/**获取用户名、密码的cookie **/
	@RequestMapping("/login/getCookie")
	public void getCookie(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie cokLoginId = CookieUtil.getCookieByName(request, "loginId");
        Cookie cokLoginPwd = CookieUtil.getCookieByName(request, "loginPwd");
        if (cokLoginId != null && cokLoginPwd != null &&  StringUtils.isNotBlank(cokLoginId.getValue()) && StringUtils.isNotBlank(cokLoginPwd.getValue())) {  
        	PageUtil.toBeJsonByMap("true", cokLoginId.getValue(), cokLoginPwd.getValue(), response);
        }
        PageUtil.toBeJsonByMap("false", "", "", response);
	}
	
	/**
	 * 直接登录验证
	 * @param loginName
	 * @param loginPwd
	 * @param code
	 * @param rember
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/login/checkUser")
	public void checkUser(String loginName,String loginPwd,String code,String rember,HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			/**
			 * 先查询用户名是否存在
			 * 	 用户不存在，直接return提示用户名不存在
			 * 	 用户存在但是用户已经失效，直接return提示用户失效
			 * 	 用户存在但是密码错误，保存错误次数和登录时间以及错误信息等，然后return提示密码错误
			 * 	 用户存在，判断用户是否被锁定(密码错误次数大于等于5将会被锁定一定的时间)
			 * 		 用户被锁定，直接return提示用户已锁定稍后再试
			 * 		 用户没有被锁定，初始错误密码次数为0以及登录时间
			 * 	用户存在， 查询用户是否有角色
			 * 		 用户没有角色，直接return提示无权限
			 * 		 用户有角色，将用户的登录信息加入session中
			 */
			if(StringUtils.isBlank(loginName) || StringUtils.isBlank(loginPwd)){
				PageUtil.toBeJsonByMap("false", "用户名或密码不能为空，请确认！", "", response);
				return;
			}
			Date date = new Date();
			//通过用户名查询用户信息
			SysUser user = new SysUser();
			user.setLoginName(loginName);
			user.setStatus(VALID_USER_FLAG);
			List<SysUser> userList = sysUserService.selectListByLoginName(user);
			//用户不存在
			if(userList.size() <= 0) { 
				PageUtil.toBeJsonByMap("false", "用户名不存在，请确认！", "", response);
				return;
			}
			SysUser selectUser = userList.get(0);//通过用户名查询出的用户信息
			//判断用户是否失效
			if(selectUser.getStatus() == 0) {
				PageUtil.toBeJsonByMap("false", "用户名已失效，请确认！", "", response);
				return;
			}
			//密码错误,保存错误次数以及错误信息
			if(!SecurityUtil.encryptPassword(loginName+ENCRYPT_SEPARATOR+loginPwd).equals(userList.get(0).getLoginPwd())) { 
				String errInfo = "密码错误";
				if(Constant.AUTH_PWD){
					SysUser sysUsers = new SysUser();
					if(selectUser.getLoginErrorTimes() >= 0 && selectUser.getLoginErrorTimes() < 4){
						sysUsers.setLoginErrorTimes(selectUser.getLoginErrorTimes() + 1);
						errInfo = "密码错误" + (selectUser.getLoginErrorTimes() + 1) + "次，错误5次将锁定用户";
					}else if(selectUser.getLoginErrorTimes() >= 4){
						sysUsers.setStatus(2);//锁定用户
						sysUsers.setLoginErrorTimes(5);
						errInfo = "用户已锁定，请于" + Constant.LOCK_MINUTE + "分钟后重试";
					}
					//保存用户登录错误信息
					sysUsers.setLastLoginTime(date);
					sysUsers.setUserId(selectUser.getUserId());
					logger.info("checkUser LoginPwd error updateByPrimaryKeySelective sysUsers:" + sysUsers.toLoginString());
					sysUserService.updateByPrimaryKeySelective(sysUsers);
				}
				PageUtil.toBeJsonByMap("false", errInfo, "", response);
				return;
			}
			//判断用户是否被锁定
			if(selectUser.getStatus() == 2) {
				long xMinute = 60*1000*Constant.LOCK_MINUTE;//xx分钟的毫秒数
				long lastLoginTime = selectUser.getLastLoginTime().getTime();
				long currentTime = System.currentTimeMillis();
				if(xMinute+lastLoginTime < currentTime){ //解锁操作
					SysUser sysUsers = new SysUser();
					sysUsers.setStatus(VALID_USER_FLAG);
					sysUsers.setLoginErrorTimes(0);//初始化登陆错误次数为0
					sysUsers.setLastLoginTime(date);
					sysUsers.setUpdateTime(date);
					sysUsers.setUserId(selectUser.getUserId());
					logger.info("checkUser Unlock updateByPrimaryKeySelective sysUsers:" + sysUsers.toLoginString());
					sysUserService.updateByPrimaryKeySelective(selectUser);
				}else{
					int lockTime = (int)Math.floor((xMinute+lastLoginTime-currentTime)/60000);
					if(lockTime==0){
						lockTime = 1;
					}
					PageUtil.toBeJsonByMap("false", "用户已锁定，请于"+lockTime+"分钟后重试", "", response);
					return;
				}
			}
			//根据用户id查询是否有角色
			List<SysUserRole> userRoleList = userRoleService.selectByUserId(selectUser.getUserId());
			if (userRoleList==null || userRoleList.size()==0) {
				//没有权限
				PageUtil.toBeJsonByMap("false", "您无系统权限，请联系管理员。", "", response);
				return;
			}
			saveLoginInfo(selectUser, rember, request, response);
			PageUtil.toBeJsonByMap("true", "", "", response);//把主键id传回页面
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * AD域登录验证
	 * @param loginName
	 * @param loginPwd
	 * @param code
	 * @param rember
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/login/checkADUser")
	public void checkADUser(String loginName,String loginPwd,String code,String rember,HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			if(StringUtils.isBlank(loginName) || StringUtils.isBlank(loginPwd)){
				PageUtil.toBeJsonByMap("false", "用户名或密码不能为空，请确认！", "", response);
				return;
			}
			//AD域验证
			boolean passFlag = LDAPValidationUtil.LdapValidation(loginName, loginPwd);
			if (!passFlag) {
				PageUtil.toBeJsonByMap("false", "用户名不存在或没有权限，请确认！", "", response);
				return;
			}
			//通过用户名和验证方式查询用户信息
			SysUser user = new SysUser();
			user.setLoginName(loginName);
			user.setStatus(VALID_USER_FLAG);
			user.setAuthType(LOGIN_VERIFY_FLAG_SSO);
			List<SysUser> userList = sysUserService.selectListByLoginName(user);
			if (userList!=null &&userList.size()==1) {
				SysUser selectUser = userList.get(0);//通过用户名查询出的用户信息
				if(selectUser.getStatus() == 0) {//判断用户是否失效
					PageUtil.toBeJsonByMap("false", "用户名已失效，请确认！", "", response);
					return;
				}
				// TODO AD域登录加解锁功能
				
				//根据用户id查询是否有角色
				List<SysUserRole> userRoleList = userRoleService.selectByUserId(selectUser.getUserId());
				if (userRoleList==null || userRoleList.size()==0) {
					//没有权限
					PageUtil.toBeJsonByMap("false", "您无系统权限，请联系管理员。", "", response);
					return;
				}
				saveLoginInfo(selectUser, rember, request, response);
				PageUtil.toBeJsonByMap("true", "", "", response);//把主键id传回页面
			} else {
				PageUtil.toBeJsonByMap("false", "账号或密码错误", "", response);//把主键id传回页面
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 保存用户登录信息及cookie、session等
	 * @param user
	 * @param rember
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void saveLoginInfo(SysUser user, String rember, HttpServletRequest request, HttpServletResponse response)throws Exception {
		//是否添加到cookie
		if ("y".equals(rember)) {
			int  loginMaxAge = Constant.COOKIE_DAY*24*60*60;  //定义账户密码的生命周期,单位为秒  
			 CookieUtil.addCookie(response , "loginId" , user.getLoginName() , loginMaxAge); //将姓名加入到cookie中  
             CookieUtil.addCookie(response , "loginPwd" , user.getLoginPwd() , loginMaxAge);   //将密码加入到cookie中  
		}
		
		String ip = CommonUtil.getIpAddr(request);
		SysUser sysUsers = new SysUser();
		sysUsers.setLastLoginIp(ip);
		sysUsers.setLoginErrorTimes(0);//初始化登陆错误次数为0
		sysUsers.setLastLoginTime(new Date());
		sysUsers.setUpdateTime(user.getLastLoginTime());
		sysUsers.setCurrentLoginIp(ip);
		sysUsers.setUserId(user.getUserId());
		logger.info("saveLoginInfo updateByPrimaryKeySelective sysUsers:" + sysUsers.toLoginString());
		sysUserService.updateByPrimaryKeySelective(sysUsers);
		//登录后将最新的sessionid即对应的浏览器标识放入其中  用于拦截器取出数据调用
		if (user.getUserId() != null){
			HttpLoginSessionListener.userMap.put(user.getUserId().toString().toString(), request.getSession().getId());
		}
		request.getSession().setAttribute(Constant.USER_INFO, user);//保存到session
  	}
	
	/**跳转到首页**/
	@RequestMapping("/login")
	public String login(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			//验证是否有修改密码的权限
			SysFunt record = new SysFunt();
			record.setFuntNo(MODIFY_PASSWORD_SHOW_DB);
			List<SysFunt> modifyFuntList = sysFuntService.selectAll(record);
			//登录用户已有的权限
			List<Integer> funcal = new ArrayList<Integer>();
			
			SysUser user = (SysUser) request.getSession().getAttribute(Constant.USER_INFO);
			List<SysFunt> queryFuntList = sysFuntService.selectFunByUserId(user.getUserId());//查询该loginName权限下所有菜单
			request.getSession().setAttribute(Constant.USER_FUNT,queryFuntList);//该用户所拥有的菜单保存到session
			
			//将权限按层级进行处理
			Map<String, List<String>> handleMenuList = handleMenuList(queryFuntList);
			request.getSession().setAttribute(Constant.USER_BUTTON,handleMenuList);
			
			List<SysFunt> firstFuntList = new ArrayList<SysFunt>();//一级菜单list
			Map<Integer,List<SysFunt>> secondFuntMap = new LinkedHashMap<Integer , List<SysFunt>>();//二级菜单map
			Iterator<SysFunt> funtIterator = queryFuntList.iterator();
			while(funtIterator.hasNext()) {
				SysFunt sysFunt = funtIterator.next();
				if(sysFunt == null) 
					continue;
				funcal.add(sysFunt.getId());//将已有权限加进去
				if (CollectionUtil.isNotEmpty(modifyFuntList) && modifyFuntList.size()==1 && sysFunt.getId().intValue() == modifyFuntList.get(0).getId().intValue()) {
					continue;//修改密码不加入权限
				}
				if(null!=sysFunt.getFuntLevel() && sysFunt.getFuntLevel() == 1) {
					firstFuntList.add(sysFunt);//加入到一级菜单集合中
					
					List<SysFunt> secondFuntList = new ArrayList<SysFunt>();//二级菜单集合
					Iterator<SysFunt> funtIterator2 = queryFuntList.iterator();
					while(funtIterator2.hasNext()) {//再次遍历所有菜单集合，找到该一级菜单下所有二级菜单
						SysFunt sysFunt2 = funtIterator2.next();
						if(sysFunt2 == null) 
							continue;
						if(sysFunt.getId().equals(sysFunt2.getFuntParId()) && null!=sysFunt2.getFuntLevel() && sysFunt2.getFuntLevel()==2) {
							secondFuntList.add(sysFunt2);
						}
					}
					secondFuntMap.put(sysFunt.getId(), secondFuntList);//加入到二级节点map中
				}
				
			}
			model.addAttribute("firstFuntList", firstFuntList);
			model.addAttribute("secondFuntMap", secondFuntMap);
			model.addAttribute("user", user);
			//验证是否有权限
			if (CollectionUtil.isNotEmpty(modifyFuntList) && modifyFuntList.size()==1 && funcal.contains(modifyFuntList.get(0).getId())) {
				model.addAttribute(MODIFY_PASSWORD_SHOW, DISPLAY);
			}
			//管理端平台登录标识
			model.addAttribute(LOGINWAYKEY, LOGINFROMMANGER);
			LogUtil.writeLog(request, LogEnum.LogLevel.OPERATE, LogEnum.OperatorModule.LOGIN_MODULE, LogEnum.OperatorType.LOGINOPERATION, user.getLoginName()+"登录管理端");
			return "forward:/index.jsp";
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 从齐鲁石化OA系统进入ZST监控管理平台，此次action不指定携带参数，安全问题
	 * @param attributes
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping("/ssoLoginEntry")
	public String ssoLoginEntry(RedirectAttributes attributes, Model model, String username, String password, String from ,HttpServletRequest request) throws Exception {
		String resultFailView = "error/noSSOAuthority";
		String resultKey = "message";
		if(ENTRY_FROM.equals(from)) {
			//通过用户名查询用户信息
			SysUser user = new SysUser();
			user.setStatus(VALID_USER_FLAG);
			user.setLoginName(username);
			List<SysUser> userList = sysUserService.selectListByObj(user);
			if (userList==null || userList.size()==0) {
				model.addAttribute(resultKey, "用户名不存在，请确认！");
				return resultFailView;
			}
			SysUser selectUser = userList.get(0);//通过用户名查询出的用户信息
			//根据用户id查询是否有角色
			List<SysUserRole> userRoleList = userRoleService.selectByUserId(selectUser.getUserId());
			if (userRoleList==null || userRoleList.size()==0) {
				//没有权限
				model.addAttribute(resultKey, "您无系统权限，请联系管理员。");
				return resultFailView;
			}
			attributes.addFlashAttribute(USERNAME, username);
			attributes.addFlashAttribute(PASSWORD, password);
			attributes.addFlashAttribute(FROM, from);
			return "redirect:/toSsoLogin.action";
		} else {
			model.addAttribute(resultKey, "请走AD域验证进行单点登录");
			return resultFailView;
		}
	}
	
	/**
	 * 同上一个方法
	 * @param attributes
	 * @param model
	 * @param username
	 * @param password
	 * @param from
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ssoLoginAdValidate")
	public String ssoLoginAdValidate(RedirectAttributes attributes, Model model, String username, String password, String from ,HttpServletRequest request) throws Exception {
		String resultFailView = "error/noSSOAuthority";
		String resultKey = "message";
		if (ENTRY_FROM.equals(from)) {
			//通过用户名查询用户信息
			SysUser user = new SysUser();
			user.setStatus(VALID_USER_FLAG);
			user.setLoginName(username);
			List<SysUser> userList = sysUserService.selectListByObj(user);
			if (userList==null || userList.size()==0) {
				model.addAttribute(resultKey, "用户名不存在，请确认！");
				return resultFailView;
			}
			SysUser selectUser = userList.get(0);//通过用户名查询出的用户信息
			//根据用户id查询是否有角色
			List<SysUserRole> userRoleList = userRoleService.selectByUserId(selectUser.getUserId());
			if (userRoleList==null || userRoleList.size()==0) {
				//没有权限
				model.addAttribute(resultKey, "您无系统权限，请联系管理员。");
				return resultFailView;
			}
			attributes.addFlashAttribute(USERNAME, username);
			attributes.addFlashAttribute(PASSWORD, password);
			attributes.addFlashAttribute(FROM, from);
			return "redirect:/toSsoLogin.action";
		} else {
			model.addAttribute(resultKey, "请走AD域验证进行单点登录");
			return resultFailView;
		}
	}
	
	/**
	 * 接收重定向的参数，跳转到相应页面
	 * @param model
	 * @param attributes
	 * @param username
	 * @param password
	 * @param from
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/toSsoLogin")
	public String toSsoLogin(Model model, RedirectAttributes attributes ,HttpServletRequest request) throws Exception {
		String message = null;
		String username = null;
		String password = null;
		String from = null;
		Map<String,String> map = (Map<String, String>) RequestContextUtils.getInputFlashMap(request);
		if (map != null) {
			username = map.get(USERNAME);
			password = map.get(PASSWORD);
			from = map.get(FROM);
		} else {
			message = "不存在登录信息，请检查";
		}
		//是否是单点登录进来
		if (ENTRY_FROM.equals(from)) {
			model.addAttribute(USERNAME, username);
			model.addAttribute(PASSWORD, password);
			model.addAttribute(FROM, from);
			
			//控制单点登录页面的权限 是否有【登录管理端】、【登录客户端】、【下载客户端】这几个权限
			SysUser sysUser = new SysUser();
			sysUser.setStatus(VALID_USER_FLAG);
			sysUser.setLoginName(username);
			List<Integer> groupList = userMapper.selectFuntGroupByUser(sysUser);
			Integer manager = 1;//管理端
			Integer client = 2;//客户端
			 if (CollectionUtil.isNotEmpty(groupList)) {
				 if (groupList.contains(client)) {
					 model.addAttribute("loginclientShow", DISPLAY);
					 model.addAttribute("downloadclientShow", DISPLAY);
				 }
				 if (groupList.contains(manager)) {
					 model.addAttribute("loginbackShow", DISPLAY);
				 }
			 }
			return "forward:/ssoLoginEntry.jsp";//进入单点登录页面
		} else {
			message = "请走AD域验证进行登录";
		}
		String resultFailView = "error/noSSOAuthority";
		String resultKey = "message";
		model.addAttribute(resultKey, message);
		return resultFailView;
	}
	
	/**
	 * 防止地址栏显示URL的参数，安全
	 * @param attributes
	 * @param model
	 * @param username
	 * @param password
	 * @param from
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toSsoLoginManageEnd")
	public String toSsoLoginManageEnd(RedirectAttributes attributes, Model model, String username, String password, String from ,HttpServletRequest request) throws Exception {
		if(ENTRY_FROM.equals(from)) {
			attributes.addFlashAttribute(USERNAME, username);
			attributes.addFlashAttribute(PASSWORD, password);
			attributes.addFlashAttribute(FROM, from);
			return "redirect:/ssoLoginManageEnd.action";
		} else {
			String resultFailView = "error/noSSOAuthority";
			String resultKey = "message";
			model.addAttribute(resultKey, "请走AD域验证进行单点登录");
			return resultFailView;
		}
	}
	
	/**
	 * 单点登录，AD域验证  防止f5刷新
	 * @param model
	 * @param username
	 * @param password
	 * @param from
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/ssoLoginManageEnd")
	public String ssoLoginManageEnd(Model model,RedirectAttributes attributes, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//判断是否登录
		SysUser sysUser = (SysUser) request.getSession().getAttribute(Constant.USER_INFO);
		if (sysUser==null) {
			String resultFailView = "error/noSSOAuthority";
			String resultKey = "message";
			String username = null;
			String password = null;
			String from = null;
			Map<String,String> map = (Map<String, String>) RequestContextUtils.getInputFlashMap(request);
			if (map != null) {
				username = map.get(USERNAME);
				password = map.get(PASSWORD);
				from = map.get(FROM);
			} else {
				model.addAttribute(resultKey, "未携带参数");
				return resultFailView;
			}
			if(ENTRY_FROM.equals(from)) {
				try{
					boolean passFlag = LDAPValidationUtil.LdapValidation(username, password);
					if (!passFlag) { //没有通过AD域验证
						model.addAttribute(resultKey, "没有通过AD域验证");
						return resultFailView;
					}
					//进入平台业务程序 管理端平台验证
					//空值验证
					if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
						model.addAttribute(resultKey, "用户名或密码不能为空，请确认！");
						return resultFailView;
					}
					Date date = new Date();
					//通过用户名查询用户信息
					SysUser user = new SysUser();
					user.setLoginName(username);
					user.setStatus(VALID_USER_FLAG);
					user.setAuthType(LOGIN_VERIFY_FLAG_SSO);
					List<SysUser> userList = sysUserService.selectListByLoginName(user);
					if(userList.size() <= 0) {
						model.addAttribute(resultKey, "用户名不存在或没有权限，请确认！");
						return resultFailView;
					} 
					SysUser selectUser = userList.get(0);//通过用户名查询出的用户信息
					if(selectUser.getStatus() == 0) {//判断用户是否失效
						model.addAttribute(resultKey, "用户名已失效，请确认！");
						return resultFailView;
					}
					if(selectUser.getStatus() == 2) {//判断用户是否被锁定
						long xMinute = 60*1000*Constant.LOCK_MINUTE;//xx分钟的毫秒数
						long lastLoginTime = selectUser.getLastLoginTime().getTime();
						long currentTime = System.currentTimeMillis();
						if(xMinute+lastLoginTime < currentTime){ //解锁操作
							SysUser sysUsers = new SysUser();
							sysUsers.setStatus(VALID_USER_FLAG);
							sysUsers.setLoginErrorTimes(0);//初始化登陆错误次数为0
							sysUsers.setLastLoginTime(date);
							sysUsers.setUpdateTime(date);
							sysUserService.updateByPrimaryKeySelective(sysUsers);
						}else{
							int lockTime = (int)Math.floor((xMinute+lastLoginTime-currentTime)/60000);
							if(lockTime==0){
								lockTime = 1;
							}
							model.addAttribute(resultKey, "用户已锁定，请于"+lockTime+"分钟后重试");
							return resultFailView;
						}
					}
					//将用户放入session中,并更新用户登录信息
					String ip = CommonUtil.getIpAddr(request);
					if ("127.0.0.1".equals(ip)) {
						SysUser sysUsers = new SysUser();
						ip = CommonUtil.getLocalhostIp();
						sysUsers.setLastLoginIp(ip);
						sysUsers.setLoginErrorTimes(0);//初始化登陆错误次数为0
						sysUsers.setLastLoginTime(new Date());
						sysUsers.setUpdateTime(selectUser.getLastLoginTime());
						sysUserService.updateByPrimaryKeySelective(sysUsers);
					}
					//登录后将最新的sessionid即对应的浏览器标识放入其中  用于拦截器取出数据调用
					if (selectUser.getUserId()!=null){
						HttpLoginSessionListener.userMap.put(selectUser.getUserId().toString().toString(),request.getSession().getId());
					}
					request.getSession().setAttribute(Constant.USER_INFO, selectUser);//保存到session
					
					//获取用户的权限和信息
					if (userList != null && userList.size() == 1) {
						//验证是否有修改密码的权限
						SysFunt record = new SysFunt();
						record.setFuntNo(MODIFY_PASSWORD_SHOW_DB);
						List<SysFunt> modifyFuntList = sysFuntService.selectAll(record);
						//登录用户已有的权限
						List<Integer> funcal = new ArrayList<Integer>();
						List<SysFunt> queryFuntList = sysFuntService.selectFunByUserId(userList.get(0).getUserId());//查询该loginName权限下所有菜单
						request.getSession().setAttribute(Constant.USER_FUNT,queryFuntList);//该用户所拥有的菜单保存到session
						
						//将权限按层级进行处理
						Map<String, List<String>> handleMenuList = handleMenuList(queryFuntList);
						request.getSession().setAttribute(Constant.USER_BUTTON,handleMenuList);
						
						List<SysFunt> firstFuntList = new ArrayList<SysFunt>();//一级菜单list
						Map<Integer,List<SysFunt>> secondFuntMap = new LinkedHashMap<Integer , List<SysFunt>>();//二级菜单map
						Iterator<SysFunt> funtIterator = queryFuntList.iterator();
						while(funtIterator.hasNext()) {
							SysFunt sysFunt = funtIterator.next();
							if(sysFunt == null) 
								continue;
							funcal.add(sysFunt.getId());//将已有权限加进去
							if (CollectionUtil.isNotEmpty(modifyFuntList) && modifyFuntList.size()==1 && sysFunt.getId().intValue() == modifyFuntList.get(0).getId().intValue()) {
								continue;//修改密码不加入权限
							}
							if(null!=sysFunt.getFuntLevel() && sysFunt.getFuntLevel() == 1) {
								firstFuntList.add(sysFunt);//加入到一级菜单集合中
								
								List<SysFunt> secondFuntList = new ArrayList<SysFunt>();//二级菜单集合
								Iterator<SysFunt> funtIterator2 = queryFuntList.iterator();
								while(funtIterator2.hasNext()) {//再次遍历所有菜单集合，找到该一级菜单下所有二级菜单
									SysFunt sysFunt2 = funtIterator2.next();
									if(sysFunt2 == null) 
										continue;
									if(sysFunt.getId().equals(sysFunt2.getFuntParId()) && null!=sysFunt2.getFuntLevel() && sysFunt2.getFuntLevel()==2) {
										secondFuntList.add(sysFunt2);
									}
								}
								secondFuntMap.put(sysFunt.getId(), secondFuntList);//加入到二级节点map中
							}
						}
						model.addAttribute("firstFuntList", firstFuntList);
						model.addAttribute("secondFuntMap", secondFuntMap);
						//AD域登录没有修改密码权限 此时没有给修改密码按钮  和 MODIFY_PASSWORD_SHOW
						//验证是否有权限
						if (CollectionUtil.isNotEmpty(modifyFuntList) && modifyFuntList.size()==1 && funcal.contains(modifyFuntList.get(0).getId())) {
							model.addAttribute(MODIFY_PASSWORD_SHOW, DISPLAY);
						}
					}
					
					selectUser.setLoginPwd(password);//给定传过来的密码
					model.addAttribute("user", selectUser);
					//ad域登录标识
					model.addAttribute(LOGINWAYKEY, LOGINFROMAD);
					
					LogUtil.writeLog(request, LogEnum.LogLevel.OPERATE, LogEnum.OperatorModule.LOGIN_MODULE, LogEnum.OperatorType.LOGINOPERATION, selectUser.getLoginName()+"单点登录进入管理端");
					return "forward:/index.jsp";//成功页面
				} catch (Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					throw new PlatformException(e.getMessage());
				}
			} else {
				model.addAttribute(resultKey, "请从AD域验证");
				return resultFailView;
			}
			
		} else {
			//验证是否有修改密码的权限
			SysFunt record = new SysFunt();
			record.setFuntNo(MODIFY_PASSWORD_SHOW_DB);
			List<SysFunt> modifyFuntList = sysFuntService.selectAll(record);
			//登录用户已有的权限
			List<Integer> funcal = new ArrayList<Integer>();
			
			List<SysFunt> queryFuntList = sysFuntService.selectFunByUserId(sysUser.getUserId());//查询该loginName权限下所有菜单
			request.getSession().setAttribute(Constant.USER_FUNT,queryFuntList);//该用户所拥有的菜单保存到session
			
			//将权限按层级进行处理
			Map<String, List<String>> handleMenuList = handleMenuList(queryFuntList);
			request.getSession().setAttribute(Constant.USER_BUTTON,handleMenuList);
			
			List<SysFunt> firstFuntList = new ArrayList<SysFunt>();//一级菜单list
			Map<Integer,List<SysFunt>> secondFuntMap = new LinkedHashMap<Integer , List<SysFunt>>();//二级菜单map
			Iterator<SysFunt> funtIterator = queryFuntList.iterator();
			while(funtIterator.hasNext()) {
				SysFunt sysFunt = funtIterator.next();
				if(sysFunt == null) 
					continue;
				funcal.add(sysFunt.getId());//将已有权限加进去
				if (CollectionUtil.isNotEmpty(modifyFuntList) && modifyFuntList.size()==1 && sysFunt.getId().intValue() == modifyFuntList.get(0).getId().intValue()) {
					continue;//修改密码不加入权限
				}
				if(null!=sysFunt.getFuntLevel() && sysFunt.getFuntLevel() == 1) {
					firstFuntList.add(sysFunt);//加入到一级菜单集合中
					
					List<SysFunt> secondFuntList = new ArrayList<SysFunt>();//二级菜单集合
					Iterator<SysFunt> funtIterator2 = queryFuntList.iterator();
					while(funtIterator2.hasNext()) {//再次遍历所有菜单集合，找到该一级菜单下所有二级菜单
						SysFunt sysFunt2 = funtIterator2.next();
						if(sysFunt2 == null) 
							continue;
						if(sysFunt.getId().equals(sysFunt2.getFuntParId()) && null!=sysFunt2.getFuntLevel() && sysFunt2.getFuntLevel()==2) {
							secondFuntList.add(sysFunt2);
						}
					}
					secondFuntMap.put(sysFunt.getId(), secondFuntList);//加入到二级节点map中
				}
				
			}
			model.addAttribute("firstFuntList", firstFuntList);
			model.addAttribute("secondFuntMap", secondFuntMap);
			model.addAttribute("user", sysUser);
			//验证是否有权限
			if (CollectionUtil.isNotEmpty(modifyFuntList) && modifyFuntList.size()==1 && funcal.contains(modifyFuntList.get(0).getId())) {
				model.addAttribute(MODIFY_PASSWORD_SHOW, DISPLAY);
			}
			//管理端平台登录标识
			model.addAttribute(LOGINWAYKEY, LOGINFROMAD);
			return "forward:/index.jsp";
		}
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loginOut")
	public String loginOut(HttpServletRequest request,HttpServletResponse response) throws Exception {
		CookieUtil.addCookie(response, "loginId", null, 0); // 清除Cookie  
        CookieUtil.addCookie(response, "loginPwd", null, 0); // 清除Cookie  
        SysUser sysUser = (SysUser) request.getSession().getAttribute(Constant.USER_INFO);
        LogUtil.writeLog(request, LogEnum.LogLevel.OPERATE, LogEnum.OperatorModule.LOGINOUT_MODULE, LogEnum.OperatorType.LOGINOPERATION, (sysUser==null ? null : sysUser.getLoginName())+"退出管理端");
        request.getSession().removeAttribute(Constant.USER_INFO);//移除session
        return "redirect:/login.jsp";//跳转到登录页
	}
	
	/**
	 * 权限按钮展示重构，将权限按钮放入到以父id为key，按钮样式名称集合为value的map中
	 * @param queryFuntList
	 * @return
	 */
	private Map<String,List<String>> handleMenuList(List<SysFunt> queryFuntList) {
		//权限展示优化 使用map来进行封装
		Map<String,List<String>> menuButtonsMap = new HashMap<String, List<String>>();
		if (CollectionUtil.isNotEmpty(queryFuntList)) {
			int buttonLevel = 3;//权限按钮level
			int menudLevel = 2;//菜单level
			int managerEnd = 1;//管理端菜单
			//二级菜单
			Map<Integer,SysFunt> idObjMap = new HashMap<Integer, SysFunt>();
			//三级按钮
			List<SysFunt> buttonList = new ArrayList<SysFunt>();
			for (SysFunt funt : queryFuntList) {
				//菜单按钮
				if (menudLevel == funt.getFuntLevel().intValue() && managerEnd==funt.getFuntGroup().intValue()) {
					idObjMap.put(funt.getId(), funt);
				} else if (buttonLevel == funt.getFuntLevel().intValue() && managerEnd==funt.getFuntGroup().intValue()) {
					buttonList.add(funt);
				}
			}
			if (CollectionUtil.isNotEmpty(buttonList)) {
				List<String> buttonStyleList = null;
				for (SysFunt funt : buttonList) {//遍历3级按钮菜单
					if (idObjMap.containsKey(funt.getFuntParId()) && idObjMap.get(funt.getFuntParId())!=null) {
						buttonStyleList = idObjMap.get(funt.getFuntParId()).getSubMenuStyle();
						//将样式存入集合之中
						if (CollectionUtil.isEmpty(buttonStyleList) && funt.getFuntNo()!=null) {
							buttonStyleList = new ArrayList<String>();
							buttonStyleList.add(funt.getFuntNo());
						} else {
							buttonStyleList.add(funt.getFuntNo());
						}
					}
					//重新给二级菜单设置按钮集合
					idObjMap.get(funt.getFuntParId()).setSubMenuStyle(buttonStyleList);
				}
				//组装二级菜单的权限按钮
				//遍历二级菜单
				for (Entry<Integer, SysFunt> entry : idObjMap.entrySet()) {
				      menuButtonsMap.put(entry.getKey()+"", entry.getValue().getSubMenuStyle());
				}
			}
		}
		return menuButtonsMap;
	}
	
	/**获取页面该用户在该菜单下的所有的按钮和操作的权限 **/
	@SuppressWarnings("unchecked")
	@RequestMapping("/getFuntBtnList")
	public void getFuntBtnList(HttpServletRequest request, HttpServletResponse response, Integer funtId) throws Exception {
		net.sf.json.JSONObject btnJson = new net.sf.json.JSONObject();
		try {
			if(funtId == null) {
				PageUtil.writeDataToClient(btnJson,response);
				return;
			}
			
			List<SysFunt> funtList = (List<SysFunt>)WebUtils.getSessionAttribute(request, Constant.USER_FUNT);//获取session中该用户拥有的菜单集合
			
			if(funtList != null && funtList.size()>0 ) {
				Iterator<SysFunt> iterator = funtList.iterator();
				StringBuilder btnString = new StringBuilder();
				while(iterator.hasNext()) {
					SysFunt funt = iterator.next();
					if(funt == null) 
						continue;
					if(funtId.intValue() == funt.getFuntParId().intValue() && null!=funt.getFuntLevel() && funt.getFuntLevel().intValue()==3) {
						btnString.append(funt.getFuntNo()+",");
					}
				}
				if(btnString.length()>0) {
					btnString = btnString.delete(btnString.length()-1, btnString.length());
				}
				btnJson.put("data", btnString.toString());
			}
			PageUtil.writeDataToClient(btnJson, response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.writeDataToClient(btnJson,response);
		}
	}
	
	/**
	 * 下载客户端软件 exe文件
	 * @param fileName
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadClientSoftWare")
	public String downloadClientSoftWare(SysBaseInfo baseInfo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //路径从base数据库表中获取
        String localFilePath = null;
        String localFileName = null;
        //String path ="G:"+ File.separator+"clientSoft"+File.separator;
        baseInfo.setGroup(GROUP);
        List<SysBaseInfo> fileEntryList = baseService.selectListByObj(baseInfo);
        if(fileEntryList != null && fileEntryList.size() == 2){
        	for(SysBaseInfo info : fileEntryList){
        		if(FILE_PATH_KEY.equals(info.getName())){
        			localFilePath = info.getValue();
        		}
        		if(FILE_NAME_KEY.equals(info.getName())){
        			localFileName = info.getValue();
        		}
        	}
        }
        if(localFilePath!=null && localFileName!=null){
        	localFilePath = URLDecoder.decode(localFilePath, "UTF-8");
        } else {
        	logger.error("未配置文件磁盘路径信息");
        	return null;
        }
        response.setCharacterEncoding("utf-8");
        //返回的数据类型
        response.setContentType("application/octet-stream");
        //响应头
        //获取浏览器的类型
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        String nameFile = null;
        if (userAgent != null) {
        	if (userAgent.indexOf("msie") > 0) {
        		nameFile = URLEncoder.encode(localFileName, "UTF-8");//IE
        	} else {
        		nameFile = new String(localFileName.getBytes("utf-8"),"iso-8859-1");//GOOGLE FIREFOX
        	}
        } else {
        	nameFile = new String(localFileName.getBytes("utf-8"),"iso-8859-1");
        }
        response.setHeader("Content-Disposition", "attachment;fileName="+ nameFile);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        byte[] bytes = new byte[2048];
        try {
            File file=new File(localFilePath,localFileName);
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            int length;
            //inputStream.read(bytes)从file中读取数据,-1是读取完的标志
            while ((length = inputStream.read(bytes)) > 0) {
                //写数据
                outputStream.write(bytes, 0, length);
            }
            LogUtil.writeLog(request, LogEnum.LogLevel.OPERATE, LogEnum.OperatorModule.SYSCONFIG_MODULE, LogEnum.OperatorType.OPERATERECORD, "下载客户端软件");
        } catch (FileNotFoundException e) {
        	logger.error("未在服务器中找到文件");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭输入输出流
            if(outputStream!=null) {
                outputStream.close();
            }
            if(inputStream!=null) {
                inputStream.close();
            }
        }
		return null;
	}

}
