package zst.core.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import zst.core.entity.ChangeFieldForLog;
import zst.core.entity.SysOrg;
import zst.core.entity.SysOrgUser;
import zst.core.entity.SysUser;
import zst.core.service.ISysOrgService;
import zst.core.service.ISysOrgUserService;
import zst.core.service.ISysUserService;
import zst.core.service.datatree.SysDataHelper;
import zst.extend.common.Constant;
import zst.extend.enums.LogEnum;
import zst.extend.exception.PlatformException;
import zst.extend.page.PageBean;
import zst.extend.util.CollectionUtil;
import zst.extend.util.CommonUtil;
import zst.extend.util.LogUtil;
import zst.extend.util.PageUtil;
import zst.extend.util.SecurityUtil;

/**
 * 用户controller
 * @author: liuyy
 * @date: 2017-6-10 上午11:46:49
 */
@Controller
@RequestMapping("/user")
public class SysUserController {

	private static final Log logger = LogFactory.getLog(SysUserController.class);
	
	//含子部门查询标识
	private static final String CONTAINBRANCH = "1";
	//是否是角色下分配用户标识
	private static final String ALLOCATEFLAG = "1";
	//含部门查询用户对象的key
	private static final String SEARCH_USER_POJO_KEY = "user";
	//含部门查询组织机构id集合的key
	private static final String SEARCH_ORG_ID_LIST_KEY = "list";
	//表单唯一验证token的key
	private static final String TOKEN_SESSION = "token";
	
	@Resource
	private ISysUserService sysUserService;
	
	@Resource
	private ISysOrgUserService sysOrgUserService;
	
	@Resource
	private ISysOrgService sysOrgService;
	
	@Resource
	private SysDataHelper dataHelper;
	
	
	/**
	 * 打开用户管理页面
	 * @param model
	 * @param funtId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openUserManage")
	public String openUserManage(Model model, String funtId) throws Exception {
		model.addAttribute("funtId", funtId);
		return "sys/user/treeOrgUser";
	}
	
	/**
	 * 查询用户  
	 * @param model
	 * @param sysUser
	 * @param rows
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryUsers")
	public String queryUsers(Model model, SysUser sysUser, String rows,String page, String containBranch,Integer funtId, HttpServletRequest request) throws Exception {
		List<SysUser> userList = null;
		long totalRows = 0L;
		try {
			sysUser.setPage(rows, page);
			sysUser.setStatus(1);//设置查询可用的用户
			//获取当前节点
			Integer orgId = sysUser.getOrgId();
			if (CONTAINBRANCH.equals(containBranch)) {
				//查询当前节点和当前节点下的子节点
				sysUser.setOrgId(null);//mybatis的条件
				List<SysOrg> childOrgList = sysOrgService.selectChildsByParId(orgId);
				List<Integer> orgIdList = new ArrayList<Integer>();
				orgIdList.add(orgId);//将父节点自身加入查询条件
				Map<String, Object> map = new HashMap<String, Object>();
				if (childOrgList!=null && childOrgList.size()>0) {
					SysOrg subOrg = null;
					for (int i=0; i<childOrgList.size(); i++) {
						subOrg = childOrgList.get(i);
						orgIdList.add(subOrg.getOrgId());
					}
				}
				map.put(SEARCH_ORG_ID_LIST_KEY, orgIdList);
				map.put(SEARCH_USER_POJO_KEY, sysUser);
				totalRows = sysUserService.selectCountByMap(map);
				if (totalRows > 0) {
					userList = sysUserService.selectListByMap(map);
				}
			} else {
				//只查询当前节点
				totalRows = sysUserService.selectCountByObj(sysUser);
				if (totalRows > 0) {
					userList = sysUserService.selectListByObj(sysUser);
				}
			}
			PageBean myPage = new PageBean();
			myPage.setPageSize(rows);
			myPage.setPageNum(page);
			myPage.setTotalRows(totalRows);
			//TODO
			//屏蔽admin用户
			if (CollectionUtil.isNotEmpty(userList)) {
				Iterator<SysUser> it = userList.iterator();
				while(it.hasNext()){
				    SysUser x = it.next();
				    if ("admin".equals(x.getLoginName())) {
				    	it.remove();
				    	myPage.setTotalRows(--totalRows);
				    }
				}
			}
			model.addAttribute("list",userList);
			model.addAttribute("myPage", myPage);
			model.addAttribute("containBranch", containBranch);//是否勾选子部门标识
			//user的部门id再还原
			sysUser.setOrgId(orgId);
			model.addAttribute("sysUser", sysUser);
			model.addAttribute("funtId", funtId);
			//处理所用有的组织按钮权限
			Map<Integer, List<String>> map = (Map<Integer, List<String>>) request.getSession().getAttribute(Constant.USER_BUTTON);
			if (map != null) {
				List<String> list = map.get(funtId+"");
				JSONArray fromObject = JSONArray.fromObject(list);
				model.addAttribute(Constant.BUTTON_LIST, fromObject.toString());
			}
			return "sys/user/listUser";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 角色管理下的分配用户
	 * @param model
	 * @param sysUser
	 * @param globalRoleId  角色id
	 * @param allocateFLag  是否是角色下的分配用户
	 * @param containBranch 包含子组织
	 * @param rows
	 * @param page
	 * @return
	 * @throws Exception
	 * 
	 * 
	 * 勾选已分配用户
	 */
	@RequestMapping("/queryUsersForData")
	public String queryUsersForData(Model model, SysUser sysUser,Integer globalRoleId, String allocateFLag, String containBranch, String rows,String page) throws Exception {
		List<SysUser> userList = null;
		long totalRows = 0L;
		try {
			sysUser.setPage(rows, page);
			sysUser.setStatus(1);//设置查询可用的用户
			//获取当前节点
			Integer orgId = sysUser.getOrgId();
			if (CONTAINBRANCH.equals(containBranch)) {//勾选了子部门标识
				//查询当前节点和当前节点下的子节点
				sysUser.setOrgId(null);//mybatis的条件
				List<SysOrg> childOrgList = sysOrgService.selectChildsByParId(orgId);
				List<Integer> orgIdList = new ArrayList<Integer>();
				orgIdList.add(orgId);//将父节点自身加入查询条件
				Map<String, Object> map = new HashMap<String, Object>();
				if (childOrgList!=null && childOrgList.size()>0) {
					SysOrg subOrg = null;
					for (int i=0; i<childOrgList.size(); i++) {
						subOrg = childOrgList.get(i);
						orgIdList.add(subOrg.getOrgId());
					}
				}
				map.put(SEARCH_ORG_ID_LIST_KEY, orgIdList);
				//判断查询已分配用户和非分配用户
				if (ALLOCATEFLAG.equals(allocateFLag)) {
					//查询已分配用户
					sysUser.setGlobalRoleId(globalRoleId);
					sysUser.setOppositeRoleId(null);
					map.put(SEARCH_USER_POJO_KEY, sysUser);
					totalRows = sysUserService.selectCountAllocateByMap(map);
					if (totalRows > 0) {
						userList = sysUserService.selectListAllocateByMap(map);
					}
				} else {
					//查询未分配用户
					sysUser.setGlobalRoleId(null);
					sysUser.setOppositeRoleId(globalRoleId);
					map.put(SEARCH_USER_POJO_KEY, sysUser);
					totalRows = sysUserService.selectCountAllocateByMap(map);
					if (totalRows > 0) {
						userList = sysUserService.selectListAllocateByMap(map);
					}
				}
			} else {
				//只查询当前节点
				//判断查询已分配用户和非分配用户
				if (ALLOCATEFLAG.equals(allocateFLag)) {
					//查询已分配用户
					sysUser.setGlobalRoleId(globalRoleId);
					sysUser.setOppositeRoleId(null);
					totalRows = sysUserService.selectCountByRoleObj(sysUser);
					if (totalRows > 0) {
						userList = sysUserService.selectListByRoleObj(sysUser);
					}
				} else {
					//查询未分配用户
					sysUser.setGlobalRoleId(null);
					sysUser.setOppositeRoleId(globalRoleId);
					totalRows = sysUserService.selectCountByRoleObj(sysUser);
					if (totalRows > 0) {
						userList = sysUserService.selectListByRoleObj(sysUser);
					}
				}
			}
			PageBean myPage = new PageBean();
			myPage.setPageSize(rows);
			myPage.setPageNum(page);
			myPage.setTotalRows(totalRows);
			model.addAttribute("list",userList);
			model.addAttribute("myPage", myPage);
			model.addAttribute("containBranch", containBranch);//是否勾选子部门标识
			model.addAttribute("allocateFLag", allocateFLag);//是否勾选已分配用户标识
			model.addAttribute("globalRoleId", globalRoleId);
			//控制角色人员分配中取消和不取消的样式
			if (ALLOCATEFLAG.equals(allocateFLag)) {
				model.addAttribute("cancelAllocateDis", "display");
				model.addAttribute("allocateDis", "none");
				model.addAttribute("personalizeStyle", "display");
			} else {
				model.addAttribute("allocateDis", "display");
				model.addAttribute("cancelAllocateDis", "none");
				model.addAttribute("personalizeStyle", "none");
			}
			//user的部门id再还原
			sysUser.setOrgId(orgId);
			model.addAttribute("sysUser", sysUser);
			return "sys/role/dataPermissionUserList";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 异步查询用户
	 * @param sysUser
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("queryUsersByName")
	public void queryUsersByName(SysUser sysUser, HttpServletResponse response) throws Exception{
		try {
			List<SysUser> userList = sysUserService.selectListByObj(sysUser);
			JsonConfig cfg = new JsonConfig();
			String[] EXCLUDES = new String[]{"lastLoginTime","lastLoginIp","createTime","updateTime"};
			cfg.setExcludes(EXCLUDES);
			JSONArray jsonArray = JSONArray.fromObject(userList,cfg);
			String json = jsonArray.toString();
			PageUtil.toBeJsonByMap("true","",json,response);
		} catch (Exception e) {
			PageUtil.toBeJsonByMap("false","查询出错","",response);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 打开用户操作窗口
	 * @param model
	 * @param sysUser
	 * @param funtType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openOperateUser")
	public String openOperateUser(Model model, SysUser sysUser, String funtType,HttpServletRequest request) throws Exception {
		try{
			if("edit".equals(funtType) || "view".equals(funtType) || "copy".equals(funtType)) {
				sysUser = sysUserService.selectByPrimaryKey(sysUser.getUserId());
				if("copy".equals(funtType)) {
					sysUser.setUserId(null);
					sysUser.setLoginName(null);
					sysUser.setUserName(null);
					sysUser.setDescription(null);
				}
			} else {
				//添加用户，后台查询组织名称，不采用前后台传输展示数据的方式
				SysOrg org = sysOrgService.selectByPrimaryKey(sysUser.getOrgId());
				if (org!=null) {
					sysUser.setOrgName(org.getOrgName());
				}
				//防止表单重复提交将唯一token存入session
				String token = UUID.randomUUID().toString().replaceAll("-","");
				request.getSession().setAttribute(TOKEN_SESSION,token);
			}
			model.addAttribute("sysUser", sysUser);
			model.addAttribute("funtType", funtType);
			return "sys/user/operateUser";
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 进入修改页面
	 * @param model
	 * @param sysUser
	 * @param funtType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toModifyUser")
	public String toModifyUser(Model model, SysUser sysUser, String funtType,HttpServletRequest request) throws Exception {
		try{
			if("edit".equals(funtType) || "view".equals(funtType) || "copy".equals(funtType)) {
				sysUser = sysUserService.selectByPrimaryKey(sysUser.getUserId());
				if("copy".equals(funtType)) {
					sysUser.setUserId(null);
					sysUser.setLoginName(null);
					sysUser.setUserName(null);
					sysUser.setDescription(null);
				}
			} else {
				//添加用户，后台查询组织名称，不采用前后台传输展示数据的方式
				SysOrg org = sysOrgService.selectByPrimaryKey(sysUser.getOrgId());
				if (org!=null) {
					sysUser.setOrgName(org.getOrgName());
				}
			}
			model.addAttribute("sysUser", sysUser);
			model.addAttribute("funtType", funtType);
			return "sys/user/modifyUser";
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 验证用户名是否重复
	 * @param sysUser
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/validateLoginName")
	public void validateLoginName(SysUser sysUser, HttpServletResponse response) throws Exception {
		sysUser.setStatus(1);
		long total = sysUserService.selectCountByObj(sysUser);
		if(total>0) {
			PageUtil.toBeJsonByMap("false","","",response);
			return;
		}
		PageUtil.toBeJsonByMap("true","","",response);
	}
	
	/**
	 * 新增用户 
	 * @param sysUser
	 * @param sysOrgUser
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addUser")
	public void addUser(SysUser sysUser, SysOrgUser sysOrgUser, String formtoken, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			//判断是否重复提交(获取表单中的值，session中的值,比较是否相等，不相等则重复提交了)
	        String token = request.getSession().getAttribute(TOKEN_SESSION) == null ? "1" : request.getSession().getAttribute(TOKEN_SESSION) + "";
	        if (!token.equals(formtoken)) {//重复提交
	        	PageUtil.toBeJsonByMap("false","您已经提交过啦！","",response);
	        	return;
	        }
	      //将token置为null
	        request.getSession().setAttribute(TOKEN_SESSION, null);
			SysUser sessionUser = CommonUtil.getSessionUser(request);//获取session中user对象
			Date date = new Date();
			//为默认密码md5加密
			String pwd = SecurityUtil.encryptPassword(sysUser.getLoginName()+LoginController.ENCRYPT_SEPARATOR+Constant.INIT_PWD);
			sysUser.setLoginPwd(pwd);
			sysUser.setCreateTime(date);
			sysUser.setUpdateTime(date);
			sysUser.setCreatorId(sessionUser==null ? null :sessionUser.getUserId());
			sysUser.setStatus(1);
			sysUserService.insertSelective(sysUser);//插入用户表
			Integer orgId = sysUser.getOrgId();
			sysOrgUser.setOrgId(orgId);
			sysOrgUser.setUserId(sysUser.getUserId());//使用了mybatis的自增长
			sysOrgUserService.insert(sysOrgUser);//增加用户-部门关联表
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.USER_MODULE, LogEnum.OperatorType.INSERTOPERATION, "添加用户："+sysUser.getUserName());
			PageUtil.toBeJsonByMap("true","","",response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","新增失败，请确认！","",response);
		}
	}
	
	/**
	 * 修改用户
	 * @param sysUser
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/modifyUser")
	public void modifyUser(SysUser sysUser, ChangeFieldForLog changeFieldForLog, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			sysUser.setUpdateTime(new Date());
			sysUserService.updateByPrimaryKeySelective(sysUser);
			SysOrgUser record = new SysOrgUser();
			record.setUserId(sysUser.getUserId());
			record.setOrgId(sysUser.getOrgId());
			sysOrgUserService.updateByPrimaryKey(record);
			//写入日志
			//拼接变动
			StringBuilder action = new StringBuilder();
			action.append("修改用户："+sysUser.getUserName());
			if (CommonUtil.notEqualString(changeFieldForLog.getOldMail(), sysUser.getEmail())) {
				action.append("邮箱由"+changeFieldForLog.getOldMail()+"改为："+sysUser.getEmail()+"~");
			}
			if (CommonUtil.notEqualString(changeFieldForLog.getOldPhone(), sysUser.getPhone())) {
				action.append("手机号由"+changeFieldForLog.getOldPhone()+"改为："+sysUser.getPhone()+"~");
			}
			if (CommonUtil.notEqualString(changeFieldForLog.getOldFixedPhone(), sysUser.getTell())) {
				action.append("座机号由"+changeFieldForLog.getOldFixedPhone()+"改为："+sysUser.getTell()+"~");
			}
			if (CommonUtil.notEqualString(changeFieldForLog.getOldDescription(), sysUser.getDescription())) {
				action.append("备注由"+changeFieldForLog.getOldDescription()+"改为："+sysUser.getDescription()+"~");
			}
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.USER_MODULE, LogEnum.OperatorType.UPDATEOPERATION, action.toString());
			PageUtil.toBeJsonByMap("true","","",response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","编辑失败，请确认！","",response);
		}
	}
	
	/**
	 * 选择单位
	 * @param model
	 * @param orgId
	 * @param orgName
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectOrg")
	public String selectOrg(Model model, String orgId, String orgName, String methodName) throws Exception {
		model.addAttribute("orgId", orgId);
		model.addAttribute("orgName", orgName);
		model.addAttribute("methodName", methodName);
		return "common/selectOrgTree";
	}
	
	/**
	 * 打开列表类型的授权页
	 * @param model
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openAddRole")
	public String openAddRole(Model model, String userId) throws Exception {
		//获取授权的角色
		List<Map<String,String>> myRoleList = sysUserService.selectRoleByLoginName(userId);
		//未授权的角色
		List<Map<String,String>> unRoleList = sysUserService.selectUnRoleByLoginName(userId);
		model.addAttribute("myRoleList", myRoleList);
		model.addAttribute("unRoleList", unRoleList);
		model.addAttribute("userId", userId);
		
		return "sys/user/openAddRole";
	}
	
	/**
	 * 打开树形的类型的授权页
	 * @param model
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openAddRoleTree")
	public String openAddRoleTree(Model model, Integer userId) throws Exception {
		model.addAttribute("userId", userId);
		return "sys/user/openAddRoleTree";
	}
	
	/**
	 * 设置角色
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openSetBatchRoleTree")
	public String openSetBatchRoleTree(Model model,String userIds) throws Exception {
		model.addAttribute("userIds", userIds);
		return "sys/user/openSetRoleTree";
	}
	
	/**
	 * 异步获取角色树
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/asyncLoadRoleTree")
	public void asyncLoadRoleTree(HttpServletResponse response, Integer userId) throws Exception {
		try {
			//查询所有的角色
			String json = dataHelper.getRoleTreeOfUser(userId);
			PageUtil.toBeJsonByMap("true", "",json, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 批量删除user(逻辑删除,更新状态) 
	 * @param userIds
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/deleteBatch")
	public void deleteBatch(@RequestParam(value = "userIds[]")Integer[] userIds, HttpServletResponse response, HttpServletRequest request) throws Exception { 
		try{
			StringBuilder builder = new StringBuilder();//非线程安全 快
			if (userIds!=null && userIds.length>0) {
				List<Integer> asList = Arrays.asList(userIds);
				//查询删除的组织机构的名称
				List<SysUser> deletedUsers = sysUserService.selectListByIdList(asList);
				if (CollectionUtil.isNotEmpty(deletedUsers)) {
					String separator = "，";
					for (int i=0; i<deletedUsers.size(); i++) {
						builder.append(deletedUsers.get(i).getUserName());
						if (i<deletedUsers.size()-1) {
							builder.append(separator);
						}
					}
				}
				sysUserService.deleteBatchByOrgIds(asList);
				sysOrgUserService.deleteBatchByUserIds(asList);
				//写入日志
				LogUtil.writeLog(request, LogEnum.LogLevel.DELETE, LogEnum.OperatorModule.USER_MODULE, LogEnum.OperatorType.LOGICALDELOPERATION, "删除用户："+builder.toString());
			}
			PageUtil.toBeJsonByMap("true","删除成功！","",response);
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","删除失败，请确认！","",response);
		}
	}
	
	/**
	 * 重置密码 
	 * @param user
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/resetPwd")
	public void resetPwd(SysUser user, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try{
			SysUser dbUser = sysUserService.selectByPrimaryKey(user.getUserId());
			if (dbUser!=null) {
				user.setLoginName(dbUser.getLoginName());
			} else {
				PageUtil.toBeJsonByMap("false","未查到用户！","",response);
				return;
			}
			Date date = new Date();
			String pwd = SecurityUtil.encryptPassword(user.getLoginName()+LoginController.ENCRYPT_SEPARATOR+Constant.INIT_PWD);//md5加密
			user.setLoginPwd(pwd);
			user.setUpdateTime(date);
			sysUserService.updateByPrimaryKeySelective(user);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.USER_MODULE, LogEnum.OperatorType.UPDATEOPERATION, "重置用户："+user.getLoginName()+"的密码");
			PageUtil.toBeJsonByMap("true","重置密码成功！","",response);
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","重置密码失败，请确认！","",response);
		}
	}
	
	/**
	 * 打开修改密码页 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openModifyPwd")
	public String openModifyPwd() throws Exception {
		return "sys/user/openModifyPwd";
	}
	
	/**
	 * 修改密码
	 * @param oldPwd
	 * @param newPwd
	 * @param repeatNewPwd
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/modifyPwd")
	public void modifyPwd(String oldPwd,String newPwd,String repeatNewPwd,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			SysUser user = (SysUser) request.getSession().getAttribute(Constant.USER_INFO);//获取session中user
			if(user == null) {
				PageUtil.toBeJsonByMap("false","系统异常,请重新登录！","",response);
				return;
			}
			String loginName = user.getLoginName();
			oldPwd = SecurityUtil.encryptPassword(loginName+LoginController.ENCRYPT_SEPARATOR+oldPwd);
			if(StringUtils.isBlank(oldPwd) || !oldPwd.equals(user.getLoginPwd())) {
				PageUtil.toBeJsonByMap("false","旧密码输入错误，请确认！","",response);
				return;
			}
			if(StringUtils.isBlank(newPwd) || StringUtils.isBlank(repeatNewPwd)) {
				PageUtil.toBeJsonByMap("false","输入的新密码不能为空！，请确认！","",response);
				return;
			}
			newPwd = SecurityUtil.encryptPassword(loginName+LoginController.ENCRYPT_SEPARATOR+newPwd);
			repeatNewPwd = SecurityUtil.encryptPassword(loginName+LoginController.ENCRYPT_SEPARATOR+repeatNewPwd);
			if(!newPwd.equals(repeatNewPwd)) {
				PageUtil.toBeJsonByMap("false","输入的两次密码不一致！，请确认！","",response);
				return;
			}
			Date date = new Date();
			SysUser modifyUser = new SysUser();
			modifyUser.setUserId(user.getUserId());
			modifyUser.setLoginName(user.getLoginName());
			modifyUser.setLoginPwd(newPwd);
			modifyUser.setUpdateTime(date);
			sysUserService.updateByPrimaryKeySelective(modifyUser);
			PageUtil.toBeJsonByMap("true","修改密码成功，请重新登录！","",response);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.USER_MODULE, LogEnum.OperatorType.UPDATEOPERATION, "修改用户："+user.getUserName()+"的密码");
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","修改密码失败，请确认！","",response);
		}
	}

}
