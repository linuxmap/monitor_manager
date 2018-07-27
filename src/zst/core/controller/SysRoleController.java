package zst.core.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import zst.core.entity.ResultBean;
import zst.core.entity.SysFunt;
import zst.core.entity.SysRole;
import zst.core.entity.SysRoleCategory;
import zst.core.entity.SysRoleCategoryFunt;
import zst.core.entity.SysUser;
import zst.core.entity.SysUserRole;
import zst.core.entity.VRoleTreeOrgAsset;
import zst.core.entity.VmsRoleAsset;
import zst.core.entity.VmsRoleOrg;
import zst.core.service.ISysFuntService;
import zst.core.service.ISysRoleCategoryFuntService;
import zst.core.service.ISysRoleCategoryService;
import zst.core.service.ISysRoleService;
import zst.core.service.ISysUserRoleService;
import zst.core.service.ISysUserService;
import zst.core.service.IVRoleTreeOrgAsssetService;
import zst.core.service.IVmsRoleAssetService;
import zst.core.service.IVmsRoleOrgService;
import zst.core.service.datatree.SysDataHelper;
import zst.extend.enums.LogEnum;
import zst.extend.exception.PlatformException;
import zst.extend.page.PageBean;
import zst.extend.util.CollectionUtil;
import zst.extend.util.CommonUtil;
import zst.extend.util.LogUtil;
import zst.extend.util.PageUtil;
/**
 * 用户角色Controller
 * @author xiongyq
 *
 */
@Controller
@RequestMapping("/role")
public class SysRoleController {
	
	private static final Log logger = LogFactory.getLog(SysRoleController.class);
	
	/**
	 * 角色的根节点的id
	 */
	private static final Integer ROLE_ROOT_ID = -1;
	
	/**
	 * 正常的角色
	 */
	public static final Integer VALID_ROLE_STATUS = 1;
	
	@Resource
	private SysDataHelper dataHelper;
	
	@Resource
	private ISysRoleService sRoleService;
	
	@Resource
	private ISysFuntService sysFuntService;
	
	@Resource
	private ISysRoleCategoryFuntService sysRoleFuntService;
	
	@Resource
	private ISysUserRoleService sysUserRoleService;
	
	@Resource
	private SysDataHelper sysDataHelper;
	
	@Resource
	private IVmsRoleOrgService roleOrgService;
	
	@Resource
	private IVmsRoleAssetService roleAssetService;
	
	@Resource
	private ISysUserService sysUserService;
	
	@Resource
	private IVRoleTreeOrgAsssetService vasssetService;//注入此service用于匹配设备和组织的唯一性，设备可能挂在多个组织下，从视图中查询
	
	@Resource
	private ISysRoleCategoryService roleCategoryService;
	
	
	/**
	 * 查询所有角色 查询所有角色，组成角色树，因为涉及到层级
	 * @param model
	 * @param sysRole
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRoleList")
	public String getRoleList(Model model, String funtId) throws Exception {
		try {
			model.addAttribute("funtId", funtId);
			SysRoleCategory roleCategory = new SysRoleCategory();
			//获取角色类型
			int validCategory = 1;
			roleCategory.setStatus(validCategory);
			List<SysRoleCategory> categoryList = roleCategoryService.selectListByObj(roleCategory);
			//用于编辑的角色类型
			List<SysRoleCategory> categoriesForEdit = new ArrayList<SysRoleCategory>();
			//拼接前台
			StringBuilder sel = new StringBuilder();
			if (categoryList!=null && categoryList.size()>0) {
				sel.append("<select name='categoryId' id='categoryId' style='width:150px;height:30px;padding-left:10px;margin-bottom:10px;border-radius:3px;'>");
				for (SysRoleCategory category : categoryList) {
					sel.append("<option value='"+category.getCategoryId()+"'>"+category.getName()+"</option>");
					categoriesForEdit.add(category);
				}
				sel.append("</select>");
			}
			model.addAttribute("categorySel", sel.toString().replace("'", "\""));
			JSONArray categoryJson = JSONArray.fromObject(categoriesForEdit);
			String categoryJ = categoryJson.toString();
			model.addAttribute("categoryJ",categoryJ);
			return "sys/role/roleManager";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 获取角色树
	 * @param response
	 * @param open
	 * @throws Exception
	 */
	@RequestMapping("/queryRoleTree")
	public void queryRoleTree(HttpServletResponse response, String open) throws Exception {
		try {
			//查询所有的角色
			String json = dataHelper.getRoleTree();
			PageUtil.toBeJsonByMap("true", "",json, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 角色范围配置  暂时弃用，作为学习和备忘的案例
	 * @param model
	 * @param frameUrl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toRoleRangeFrame")
	public String toRoleRangeFrame(Model model ,String frameUrl) throws Exception {
		String json = sysDataHelper.getOrgAssetTree();
		//获取已经选择的部门和结构
		List<VmsRoleOrg> roleOrgList = roleOrgService.selectListByObj(null);
		JSONArray roleOrgArray = JSONArray.fromObject(roleOrgList);
		String roleOrgJson = roleOrgArray.toString();
		List<VmsRoleAsset> roleAsset = roleAssetService.selectListByObj(null);
		JSONArray roleAssetArray = JSONArray.fromObject(roleAsset);
		String roleAssetJson = roleAssetArray.toString();
		
		model.addAttribute("zTreeNodes", json);
		model.addAttribute("roleOrgJson", roleOrgJson);
		model.addAttribute("roleAssetJson", roleAssetJson);
		return "sys/role/rolerange";
	}
	
	/**
	 * 异步加载数据权限管理下的 角色管理的设备配置，2018-02-24新增功能  ,并查询该角色下的组织权限
	 * @param model
	 * @param frameUrl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAsyncRoleRangeFrame")
	public String toAsyncRoleRangeFrame(Model model ,Integer roleId) throws Exception {
		ResultBean resultBean = sysDataHelper.getAsyncRootOrgAssetTree(roleId);
		if (resultBean!=null) {
			model.addAttribute("zTreeNodes", resultBean.getFeedBack());
			model.addAttribute("isFirstConfigData", resultBean.isFlag());
		}
		return "sys/role/rolerangeAsyc";
	}
	
	/**
	 * 异步加载ztree树下的子节点
	 * @param response
	 * @param parentId
	 * @param roleId
	 * @throws Exception
	 */
	@RequestMapping("/getAsyncChildrenByParentId")
	public void getAsyncChildrenByParentId(HttpServletResponse response, Integer parentId, Integer roleId) throws Exception {
		try {
			JSONArray jsonArray = sysDataHelper.getAssetAsyncChildrenByParentId(parentId,roleId);
			PageUtil.toBeJsonByMap("true", "",jsonArray, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 用户数据权限个性化配置
	 * @param model
	 * @param frameUrl
	 * @param globalRoleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toPersonalizeDataFrame")
	public String toPersonalizeDataFrame(Model model ,String frameUrl, Integer globalRoleId) throws Exception {
		String json = sysDataHelper.getOrgAssetTree();
		//获取已经选择的部门和结构
		List<VmsRoleOrg> roleOrgList = roleOrgService.selectListByObj(null);
		JSONArray roleOrgArray = JSONArray.fromObject(roleOrgList);
		String roleOrgJson = roleOrgArray.toString();
		List<VmsRoleAsset> roleAsset = roleAssetService.selectListByObj(null);
		JSONArray roleAssetArray = JSONArray.fromObject(roleAsset);
		String roleAssetJson = roleAssetArray.toString();
		
		//获取角色对应的用户
		String zUserTreeNodes = sysDataHelper.getUserJsonByRole(globalRoleId);
		
		model.addAttribute("zTreeNodes", json);//组织设备
		model.addAttribute("roleOrgJson", roleOrgJson);
		model.addAttribute("roleAssetJson", roleAssetJson);
		model.addAttribute("zUserTreeNodes", zUserTreeNodes);
		return "sys/role/personalRange";
	}
	
	/**新增角色 **/
	@RequestMapping("/addRole")
	public void addRole(SysRole sysRole, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SysUser user = CommonUtil.getSessionUser(request);
			sysRole.setCreatorId(user.getUserId());
			Date date = new Date();
			sysRole.setCreateTime(date);
			sysRole.setUpdateTime(date);
			sysRole.setStatus(VALID_ROLE_STATUS);
			//增加根节点时需要做处理
			if (sysRole.getParentId()==null) {
				sysRole.setParentId(ROLE_ROOT_ID);
			}
			sRoleService.insertSelective(sysRole);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.DATAPERMISSION_MODULE, LogEnum.OperatorType.INSERTOPERATION, "添加角色："+sysRole.getRoleName());
			PageUtil.toBeJsonByMap("true",sysRole.getRoleId()+"",ROLE_ROOT_ID,response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","新增失败，请确认！","",response);
		}
	}
	
	/**
	 * 拷贝角色，同时复制关系 Transactional
	 * @param sysRole
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/copyRole")
	public void copyRole(SysRole sysRole, Integer sublingRoleId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SysUser user = CommonUtil.getSessionUser(request);
			sysRole.setCreatorId(user.getUserId());
			Date date = new Date();
			sysRole.setCreateTime(date);
			sysRole.setUpdateTime(date);
			sysRole.setStatus(VALID_ROLE_STATUS);
			//增加根节点时需要做处理
			if (sysRole.getParentId()==null) {
				sysRole.setParentId(ROLE_ROOT_ID);
			}
			ResultBean resultBean = sRoleService.insertSameRole(sysRole, sublingRoleId);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.DATAPERMISSION_MODULE, LogEnum.OperatorType.INSERTOPERATION, "添加角色："+sysRole.getRoleName());
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()),resultBean.getFeedBack(),resultBean.getPrimaryKey(),response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","新增失败，请确认！","",response);
		}
	}
	
	/**
	 * 进入角色人员分配页面
	 */
	@RequestMapping("/toDataPermission")
	public String toDataPermission(Model model,Integer globalRoleId){
		try {
			model.addAttribute("globalRoleId", globalRoleId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "sys/role/dataPermissionUserTree";
	}
	
	/**
	 * 给一个角色批量分配用户
	 * @param userIds
	 * @param roleId
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/batchAllocateUser")
	public void batchAllocateUser(@RequestParam(value = "userIds[]")Integer[] userIds,Integer roleId, HttpServletResponse response, HttpServletRequest request) throws Exception { 
		try{
			SysRole role = sRoleService.selectByPrimaryKey(roleId);
			String name = role!=null ? role.getRoleName() : null;
			if (userIds!=null && userIds.length>0) {
				List<SysUserRole> userRoleList = new ArrayList<SysUserRole>();
				List<Integer> idList = new ArrayList<Integer>();
				SysUserRole userRole;
				for (Integer userId : userIds) {
					userRole = new SysUserRole();
					userRole.setUserId(userId);
					userRole.setRoleId(roleId);
					userRoleList.add(userRole);
					
					idList.add(userId);
				}
				sysUserRoleService.insertBatch(userRoleList);
				
				//日志追加
				StringBuilder userString = new StringBuilder();
				List<SysUser> userList = sysUserService.selectListByIdList(idList);
				if (CollectionUtil.isNotEmpty(userList)) {
					for (int i=0;i<userList.size();i++) {
						userString.append(userList.get(i).getUserName());
						if (i<userList.size()-1) {
							userString.append(",");
						}
					}
				}
				PageUtil.toBeJsonByMap("true","分配成功！","",response);
				//写入日志
				LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.DATAPERMISSION_MODULE, LogEnum.OperatorType.INSERTOPERATION, "给角色："+name+"分配用户："+userString.toString());
			} else {
				PageUtil.toBeJsonByMap("false","分配失败，携带参数为空！","",response);
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","分配失败，请确认！","",response);
		}
	}
	
	/**
	 * 给一个角色批量取消用户关联 Transactional
	 * @param userIds
	 * @param roleId
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/batchCancelAllocateUser")
	public void batchCancelAllocateUser(@RequestParam(value = "userIds[]")Integer[] userIds,Integer roleId, HttpServletResponse response, HttpServletRequest request) throws Exception { 
		ResultBean resultBean = new ResultBean();
		resultBean.setFeedBack("取消失败");
		resultBean.setFlag(false);
		try {
			SysRole role = sRoleService.selectByPrimaryKey(roleId);
			String name = role!=null ? role.getRoleName() : null;
			resultBean = sysUserRoleService.deleteBatch(userIds,roleId);
			
			//日志追加
			StringBuilder userString = new StringBuilder();
			if (userIds!=null && userIds.length>0) {
				List<Integer> idList = Arrays.asList(userIds);
				List<SysUser> userList = sysUserService.selectListByIdList(idList);
				if (CollectionUtil.isNotEmpty(userList)) {
					for (int i=0;i<userList.size();i++) {
						userString.append(userList.get(i).getUserName());
						if (i<userList.size()-1) {
							userString.append(",");
						}
					}
				}
			}
			LogUtil.writeLog(request, LogEnum.LogLevel.DELETE, LogEnum.OperatorModule.DATAPERMISSION_MODULE, LogEnum.OperatorType.PHYSICALDELOPERATION, "给用户："+userString.toString()+"取消角色："+name);
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()), resultBean.getFeedBack(), "",response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()),resultBean.getFeedBack(),"",response);
		}
	}
	
	/**
	 * 进入用户个性化配置页面
	 * @param model
	 * @param userId
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/personalizePermissionConfig")
	public String personalizePermissionConfig(Model model, Integer userId, Integer roleId) throws Exception {
		try {
			String json = sysDataHelper.getPersonalOrgAssetTree(userId, roleId);
			SysUser user = sysUserService.selectByPrimaryKey(userId);
			model.addAttribute("zTreeNodes", json);
			model.addAttribute("userId", userId);
			model.addAttribute("roleId", roleId);
			model.addAttribute("user", user);
			return "sys/role/personalizeRange";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 用户个性化权限配置
	 * @param roleId
	 * @param userId
	 * @param negativeOrg
	 * @param negativeAsset
	 * @param positiveOrg
	 * @param positiveAsset
	 * @param negativeUserOrg
	 * @param negativeUserAsset
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updatePersonalizeRange")
	public void updatePersonalizeRange(Integer roleId,Integer userId,
			@RequestParam(value="negativeOrg[]",required=false)Integer[] negativeOrg,@RequestParam(value="negativeAsset[]",required=false)String[] negativeAsset,
			@RequestParam(value="positiveOrg[]",required=false)Integer[] positiveOrg,@RequestParam(value="positiveAsset[]",required=false)String[] positiveAsset, 
			HttpServletResponse response)throws Exception{
		ResultBean resultBean = new ResultBean();
		resultBean.setFlag(false);
		try {
			resultBean = sRoleService.insertBatchPersonalize(roleId, userId, negativeOrg, negativeAsset, positiveOrg, positiveAsset);
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()),resultBean.getFeedBack(), "", response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false",resultBean.getFeedBack(), "", response);
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 不加事务  userless
	 * 进入角色人员分配页面 
	 * @param model
	 * @param role
	 * @param sysUser
	 * @param selectId  右侧已有的id  fourButton为操作所致
	 * @param flag 参数为0说明是通过查询过来的
	 * @param rows
	 * @param page
	 * @param orgName
	 * @return
	 */
	@RequestMapping("/toAllocateUserRole")
	public String toAllocateUserRole(Model model,SysRole role,SysUser sysUser,String selectId, Integer flag, String rows,String page,String orgName){
		try {
			List<SysUser> selectedUserList = null;
			if("fourButton".equals(selectId)){//没有任何已选中的用户
				
			} else if (null != flag && selectId != null ) {//通过查询过来的
				String[] split = selectId.split(",");
				List<Integer> list = new ArrayList<Integer>();
				if(split!=null && split.length>0){
					for(String s : split){
						if(!"".equals(s)){
							Integer id = Integer.valueOf(s);
							list.add(id);
						}
					}
					if(list.size()>0){
						selectedUserList = sysUserService.selectListByIdList(list);
					}
				}
			} else if (role.getRoleId() !=null) {
				//查询角色名称
				SysRole dbRole = sRoleService.selectByPrimaryKey(role.getRoleId());
				if (dbRole!=null) {
					role.setRoleName(dbRole.getRoleName());
				}
				//已经分配的用户
				selectedUserList = sysUserService.selectListByRoleId(role.getRoleId());
				//用户在前台没有做操作
				if(selectedUserList!=null && selectedUserList.size()>0){
					StringBuilder current = new StringBuilder();
					for(SysUser user : selectedUserList){
						current.append(user.getUserId()).append(",");
					}
					selectId = current.toString().substring(0, current.toString().lastIndexOf(","));
				}
			}
			model.addAttribute("role", role);
			model.addAttribute("selectId", selectId);
			model.addAttribute("selectedUserList", selectedUserList);
			
			sysUser.setPage(rows, page);
			sysUser.setStatus(1);//设置查询可用的用户
			List<SysUser> userList = null;
			long totalRows = sysUserService.selectCountByObj(sysUser);
			if(totalRows > 0) {
				userList = sysUserService.selectListByObj(sysUser);
			}
			
			PageBean myPage = new PageBean();
			myPage.setPageSize(rows);
			myPage.setPageNum(page);
			myPage.setTotalRows(totalRows);
			model.addAttribute("list",userList);
			model.addAttribute("myPage", myPage);
			model.addAttribute("sysUser", sysUser);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "sys/role/allocateUserRole";
	}
	
	
	/**
	 * userless 不加事务
	 * 专门查询用户数据，分页交给前台 ${sys_ctx}/role/queryUserForRole.action data.ower
	 * @param sysUser
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryUserForRole")
	public void queryUserForRole(SysUser sysUser, HttpServletResponse response) throws Exception {
		try {
			sysUser.setStatus(1);//设置查询可用的用户
			List<SysUser> userList = null;
			long totalRows = sysUserService.selectCountByObj(sysUser);
			if(totalRows > 0) {
				userList = sysUserService.selectListByObj(sysUser);
			}
			JSONObject usersJson = JSONObject.fromObject(userList);
			PageUtil.toBeJsonByMap("true","",usersJson,response);
		} catch (Exception e) {
			PageUtil.toBeJsonByMap("false","添加关系失败","",response);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * userless 不加事务
	 * 给角色分配人员 Transactional
	 * @param ids
	 * @param roleId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/allocateRoleUsers")
	public void allocateRoleUsers(String userIds,Integer roleId, HttpServletResponse response) throws Exception {
		try{
			//删除原有角色对应的用户  无用户选择说明该角色下没有人员
			sysUserRoleService.deleteByRoleId(roleId);
			List<SysUserRole> userRoleList = new ArrayList<SysUserRole>();
			if(userIds!=null && userIds.length()>0){
				String[] idArray = userIds.split(",");
				SysUserRole userRole = null;
				for(int i=0; i<idArray.length; i++){
					userRole = new SysUserRole();
					userRole.setRoleId(roleId);
					userRole.setUserId(Integer.valueOf(idArray[i]));
					userRoleList.add(userRole);
				}
				//批量添加到数据库
				sysUserRoleService.insertBatch(userRoleList);
			}
			PageUtil.toBeJsonByMap("true","","",response);
		}catch (Exception e) {
			PageUtil.toBeJsonByMap("false","添加关系失败","",response);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除角色 根据角色roleid  删除角色采用物理删除
	 * @param roleId
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/delByRoleId")
	public void delByRoleId(Integer roleId,HttpServletResponse response, HttpServletRequest request)throws Exception{
		try{
			SysRole role = sRoleService.selectByPrimaryKey(roleId);
			String name = role!=null ? role.getRoleName() : null;
			ResultBean resultBean = sRoleService.deleteLogicalByRoleId(roleId);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.DELETE, LogEnum.OperatorModule.DATAPERMISSION_MODULE, LogEnum.OperatorType.PHYSICALDELOPERATION, "删除角色："+name);
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()),resultBean.getFeedBack(),"",response);
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","删除失败，请确认！","",response);
		}
	}
	
	/**
	 * 修改角色 根据角色roleid
	 * @param sysRole
	 * @param req
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updateRole")
	public void updateRole(SysRole sysRole, HttpServletRequest req,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			//拼接变动
			StringBuilder action = new StringBuilder();
			SysRole oldRole = sRoleService.selectByPrimaryKey(sysRole.getRoleId());
			if (oldRole!=null) { 
				action.append("修改角色："+oldRole.getRoleName());
				if (CommonUtil.notEqualString(oldRole.getRoleName(), sysRole.getRoleName())) {
					action.append("名称由"+oldRole.getRoleName()+"改为："+sysRole.getRoleName());
				}
			}
			
			Date date = new Date();
			sysRole.setUpdateTime(date);
			sRoleService.updateByPrimaryKeySelective(sysRole);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.DATAPERMISSION_MODULE, LogEnum.OperatorType.UPDATEOPERATION,action.toString());
			PageUtil.toBeJsonByMap("true","","",response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","编辑失败，请确认！","",response);
		}
	}
	
	/**
	 * 查询所有角色 不需要分页
	 * @param sysRole
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryAllrole")
	public List<SysRole> queryRole(SysRole sysRole,HttpServletResponse response)throws Exception{
		try {
			sysRole.setStatus(VALID_ROLE_STATUS);//1为有效角色
			return  sRoleService.selectAll(sysRole);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
		
	}
	
	/**
	 * 进入管理端权限的frame
	 * @param model
	 * @param frameUrl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toBackFrame")
	public String toBackFrame(Model model,String frameUrl) throws Exception {
		try {
			Integer id = 1;//根节点菜单id
			//根据root节点获取所有菜单集合
			List<SysFunt> roleMenuList = sysFuntService.selectSubFunt(id);
			model.addAttribute("roleMenuList", roleMenuList);
			
			return "sys/role/roleback";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 进入客户端权限的frame 菜单类型为2 写死在mybatis 此为旧版本，页面样式为列表样式,先保留下来
	 * @param model
	 * @param frameUrl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toClientFrame")
	public String toClientFrame(Model model,String frameUrl)throws Exception{
		try {
			//根据root节点获取所有菜单集合
			List<SysFunt> roleMenuList = sysFuntService.selectClientFunt();
			model.addAttribute("roleMenuList", roleMenuList);
				
			return "sys/role/roleclient";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 进入客户端权限的frame，直接查询客户端的所有权限 此为新版本，页面样式为树形样式
	 * @param model
	 * @param frameUrl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toClientFrameTree")
	public String toClientFrameTree(Model model,String frameUrl)throws Exception{
		try {
			String clienTreeJson = sysDataHelper.getClientFuntTree();
			
			//根据root节点获取所有菜单集合
			List<SysFunt> roleMenuList = sysFuntService.selectClientFunt();
			model.addAttribute("roleMenuList", roleMenuList);
			model.addAttribute("clienTreeJson", clienTreeJson);
			return "sys/role/roleclientTree";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 根据roleFunt树形查询集合
	 * @param model
	 * @param roleFunt
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getFuntByRoleId")
	public void  getFuntByRoleId(Model model,SysRoleCategoryFunt roleFunt,HttpServletResponse response)throws Exception{
		try {
			List<SysRoleCategoryFunt> roleFuntList = sysRoleFuntService.selectListByObj(roleFunt);
			if(roleFuntList!=null&&roleFuntList.size()>0){
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < roleFuntList.size(); i++) {
					sb.append(roleFuntList.get(i).getFuntId()+",");
				}
				String str = sb.toString();
				//除去最后一个逗号
				str = str.subSequence(0, str.length()-1).toString();
				PageUtil.toBeJsonByMap("true", "", str, response);
				
			}else{
				PageUtil.toBeJsonByMap("false", "没有给用户授权", "", response);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
			
		}
		
	}
	
	/**
	 * 根据角色id查找权限范围
	 * @param model
	 * @param roleId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getRangeByRoleId")
	public void getRangeByRoleId(Model model,Integer roleId,HttpServletResponse response) throws Exception {
		try {
			if (roleId != null) {
				String orgstr = null;
				String assetstr = null;
				// 已有组织权限
				VmsRoleOrg roleOrg = new VmsRoleOrg();
				roleOrg.setRoleId(roleId);
				List<VmsRoleOrg> roleOrgList = roleOrgService.selectListByObj(roleOrg);
				if (roleOrgList != null && roleOrgList.size() > 0) {
					StringBuffer sbOrg = new StringBuffer();
					for (int i = 0; i < roleOrgList.size(); i++) {
						sbOrg.append(roleOrgList.get(i).getOrgId() + ",");
					}
					orgstr = sbOrg.toString();
					// 除去最后一个逗号
					orgstr = orgstr.subSequence(0, orgstr.length() - 1).toString();
				}
				// 已有资产权限
				VmsRoleAsset roleAsset = new VmsRoleAsset();
				roleAsset.setRoleId(roleId);
				List<VmsRoleAsset> roleAssetList = roleAssetService.selectListByObj(roleAsset);
				//查询视图，对应的视图
				VRoleTreeOrgAsset vRoleTreeOrgAsset = new VRoleTreeOrgAsset();
				vRoleTreeOrgAsset.setAssetorgvisible(true);//组织可见性
				vRoleTreeOrgAsset.setAssetstatus(0);//组织状态
				vRoleTreeOrgAsset.setDeletetatus(false);//设备正常
				vRoleTreeOrgAsset.setAssetvisible(true);//设备是否删除
				List<VRoleTreeOrgAsset> multiOrgsAssetList = vasssetService.selectListByObj(vRoleTreeOrgAsset);
				if (roleAssetList != null && roleAssetList.size() > 0 && multiOrgsAssetList!=null && multiOrgsAssetList.size()>0) {
					StringBuffer sbAsset = new StringBuffer();
					for(VRoleTreeOrgAsset vAsset : multiOrgsAssetList){
						for (int i = 0; i < roleAssetList.size(); i++) {
							if(roleAssetList.get(i).getOrgId().equals(vAsset.getAssetOrgid()) && roleAssetList.get(i).getAssetId().equals(vAsset.getAssetId())){
								sbAsset.append(vAsset.getAssetUUID() + ",");
								break;
							}
						}
					}
					assetstr = sbAsset.toString();
					// 除去最后一个逗号
					assetstr = assetstr.subSequence(0, assetstr.length() - 1).toString();
				}
				PageUtil.toBeJsonByMap("true", orgstr, assetstr, response);
			} else {
				PageUtil.toBeJsonByMap("false", "没有给用户授权", "", response);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
			
		}
		
	}

	/**
	 * 根据用户的角色和用户_组织设备表 查询 用户所拥有的设备
	 * @param model
	 * @param roleId
	 * @param frameUserId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getRangeByUserIdWithRole")
	public void getRangeByUserIdWithRole(Model model,Integer roleId,Integer frameUserId,HttpServletResponse response) throws Exception {
		try {
			if (roleId != null) {
				String orgstr = null;
				String assetstr = null;
				// 已有组织权限
				VmsRoleOrg roleOrg = new VmsRoleOrg();
				roleOrg.setRoleId(roleId);
				List<VmsRoleOrg> roleOrgList = roleOrgService.selectListByObj(roleOrg);
				if (roleOrgList != null && roleOrgList.size() > 0) {
					StringBuffer sbOrg = new StringBuffer();
					for (int i = 0; i < roleOrgList.size(); i++) {
						sbOrg.append(roleOrgList.get(i).getOrgId() + ",");
					}
					orgstr = sbOrg.toString();
					// 除去最后一个逗号
					orgstr = orgstr.subSequence(0, orgstr.length() - 1).toString();
				}
				// 已有资产权限
				VmsRoleAsset roleAsset = new VmsRoleAsset();
				roleAsset.setRoleId(roleId);
				List<VmsRoleAsset> roleAssetList = roleAssetService.selectListByObj(roleAsset);
				//查询视图，对应的视图
				VRoleTreeOrgAsset vRoleTreeOrgAsset = new VRoleTreeOrgAsset();
				vRoleTreeOrgAsset.setAssetorgvisible(true);//组织可见性
				vRoleTreeOrgAsset.setAssetstatus(0);//组织状态
				vRoleTreeOrgAsset.setDeletetatus(false);//设备正常
				vRoleTreeOrgAsset.setAssetvisible(true);//设备是否删除
				List<VRoleTreeOrgAsset> multiOrgsAssetList = vasssetService.selectListByObj(vRoleTreeOrgAsset);
				if (roleAssetList != null && roleAssetList.size() > 0 && multiOrgsAssetList!=null && multiOrgsAssetList.size()>0) {
					StringBuffer sbAsset = new StringBuffer();
					for(VRoleTreeOrgAsset vAsset : multiOrgsAssetList){
						for (int i = 0; i < roleAssetList.size(); i++) {
							if(roleAssetList.get(i).getOrgId().equals(vAsset.getAssetOrgid()) && roleAssetList.get(i).getAssetId().equals(vAsset.getAssetId())){
								sbAsset.append(vAsset.getAssetUUID() + ",");
								break;
							}
						}
					}
					assetstr = sbAsset.toString();
					// 除去最后一个逗号
					assetstr = assetstr.subSequence(0, assetstr.length() - 1).toString();
				}
				PageUtil.toBeJsonByMap("true", orgstr, assetstr, response);
			} else {
				PageUtil.toBeJsonByMap("false", "没有给用户授权", "", response);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
			
		}
		
	}

	/**
	 * useless
	 * 修改管理端权限
	 * @param roleId
	 * @param funtIds
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updateFuntByRoleId")
	public void updateFuntByRoleId(Integer roleId,@RequestParam(value="funtIds[]")Integer[] funtIds,HttpServletResponse response)throws Exception{
		try {
			//根据roelId删除角色功能关联表中此角色的数据
			if(funtIds.length==1 && funtIds[0].equals("null") ){//没有选中任何功能 .删除所有功能即可
				sysRoleFuntService.deleteByCategoryid(roleId);
				PageUtil.toBeJsonByMap("true", "保存成功!", "", response);
			}
			else{
				sysRoleFuntService.deleteByCategoryid(roleId);
				List<SysRoleCategoryFunt> list = new ArrayList<SysRoleCategoryFunt>();
				if(funtIds!=null&&funtIds.length>0){
					for(int i=0;i<funtIds.length;i++){
						SysRoleCategoryFunt sysFunt = new SysRoleCategoryFunt();
						sysFunt.setFuntId(funtIds[i]);
						sysFunt.setRoleCategoryId(roleId);
						list.add(sysFunt);
					}
					//根据角色id批量添加功能
					sysRoleFuntService.addList(list);
				}
				PageUtil.toBeJsonByMap("true", "保存成功!", "", response);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * useless
	 * 修改服务端和客户端权限
	 * @param roleId
	 * @param funtIds
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updateDoubleFuntByRoleId")
	public void updateDoubleFuntByRoleId(Integer roleId, Integer funtGroup, @RequestParam(value="funtIds[]")Integer[] funtIds,HttpServletResponse response)throws Exception{
		try {
			if (funtGroup == null) {
				PageUtil.toBeJsonByMap("false", "模块标识缺失", "", response);
			} else {
				SysRoleCategoryFunt roleFunt = new SysRoleCategoryFunt();
				roleFunt.setFuntGroup(funtGroup);
				roleFunt.setRoleCategoryId(roleId);
				//根据roelId删除角色功能关联表中此角色的数据
				if (funtIds.length==1 && funtIds[0] == -1 ) {//没有选中任何功能 .删除所有功能即可
					sysRoleFuntService.deleteByCategoryFunt(roleFunt);
					PageUtil.toBeJsonByMap("true", "保存成功,请重新登录", "", response);
				} else {
					sysRoleFuntService.deleteByCategoryFunt(roleFunt);
					List<SysRoleCategoryFunt> list = new ArrayList<SysRoleCategoryFunt>();
					if (funtIds!=null&&funtIds.length>0) {
						for(int i=0;i<funtIds.length;i++){
							SysRoleCategoryFunt sysFunt = new SysRoleCategoryFunt();
							sysFunt.setFuntId(funtIds[i]);
							sysFunt.setRoleCategoryId(roleId);
							sysFunt.setFuntGroup(funtGroup);
							list.add(sysFunt);
						}
						//根据角色id批量添加功能
						sysRoleFuntService.addList(list);
					}
					PageUtil.toBeJsonByMap("true", "保存成功,请重新登录", "", response);
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * useless
	 * 保存角色的管理范围
	 * @param roleId
	 * @param orgIds
	 * @param equIds  string类型的数组，资产和其父节点组织的id拼接而成，assetId_orgId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updateRoleRange")
	public void updateRoleRange(Integer roleId, @RequestParam(value="orgIds[]",required=false)Integer[] orgIds, @RequestParam(value="equIds[]",required=false)String[] equIds, HttpServletResponse response)throws Exception{
		try {
			//删除原来的角色对应的组织和设备关系
			roleOrgService.deleteByRoleid(roleId);
			roleAssetService.deleteByRoleid(roleId);
			//新增最新的角色对应的组织和设备关系
			VmsRoleOrg roleOrg = null;
			List<VmsRoleOrg> roleOrgList = new ArrayList<VmsRoleOrg>();
			if (orgIds!=null && orgIds.length>0) {
				for(Integer orgId : orgIds){
					roleOrg = new VmsRoleOrg();
					roleOrg.setRoleId(roleId);
					roleOrg.setOrgId(orgId);
					roleOrgList.add(roleOrg);
				}
				roleOrgService.insertBatch(roleOrgList);
			}
			
			VmsRoleAsset roleAsset = null;
			String separator = "_";
			List<VmsRoleAsset> roleAssetList = new ArrayList<VmsRoleAsset>();
			if(equIds!=null && equIds.length>0){
				for(String assetId : equIds){
					roleAsset = new VmsRoleAsset();
					String[] assetOrgArray = assetId.split(separator);
					if(assetOrgArray!=null && assetOrgArray.length == 2) {
						roleAsset.setRoleId(roleId);
						roleAsset.setAssetId(Integer.valueOf(assetOrgArray[0]));
						roleAsset.setOrgId(Integer.valueOf(assetOrgArray[1]));
						roleAssetList.add(roleAsset);
					}
				}
				roleAssetService.insertBatch(roleAssetList);
			}
			PageUtil.toBeJsonByMap("true", "保存成功!", "", response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false", "保存失败!", "", response);
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 异步新增角色对应的组织和设备权限
	 * @param roleId
	 * @param orgAddIdArray
	 * @param orgDelIdArray
	 * @param equAddIdArray
	 * @param equDelIdArray
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/asyncInsertRoleRange")
	public void asyncInsertRoleRange(Integer roleId, 
									@RequestParam(value="fullOrgChecked[]",required=false)Integer[] fullOrgChecked,
									@RequestParam(value="allCheckedOrgArray[]",required=false)Integer[] allCheckedOrgArray,
									@RequestParam(value="assetSpecialChecked[]",required=false)String[] assetSpecialChecked,
									HttpServletRequest request, HttpServletResponse response)throws Exception{
		try {
			SysRole role = sRoleService.selectByPrimaryKey(roleId);
			String name = role!=null ? role.getRoleName() : null;
			ResultBean resultBean = new ResultBean();
			resultBean = sRoleService.insertRoleDataPermission(roleId, fullOrgChecked, allCheckedOrgArray, assetSpecialChecked);
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.DATAPERMISSION_MODULE, LogEnum.OperatorType.INSERTOPERATION, "配置角色："+name+"的数据权限");
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()),resultBean.getFeedBack(),"",response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false", "保存失败!", "", response);
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 异步更新角色对应的组织和设备权限
	 * @param roleId
	 * @param orgAddIdArray
	 * @param orgDelIdArray
	 * @param equAddIdArray
	 * @param equDelIdArray
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/asyncUpdateRoleRange")
	public void asyncUpdateRoleRange(Integer roleId, @RequestParam(value="allCheckedAssetOrgArray[]",required=false)Integer[] allCheckedAssetOrgArray,
									@RequestParam(value="orgAddIdArray[]",required=false)Integer[] orgAddIdArray,
									@RequestParam(value="orgDelIdArray[]",required=false)Integer[] orgDelIdArray,
									@RequestParam(value="equAddIdArray[]",required=false)String[] equAddIdArray,
									@RequestParam(value="equDelIdArray[]",required=false)String[] equDelIdArray,
									HttpServletRequest request, HttpServletResponse response)throws Exception{
		try {
			SysRole role = sRoleService.selectByPrimaryKey(roleId);
			String name = role!=null ? role.getRoleName() : null;
			ResultBean resultBean = new ResultBean();
			resultBean = sRoleService.updateRoleDataPermission(roleId,allCheckedAssetOrgArray, orgAddIdArray, orgDelIdArray, equAddIdArray, equDelIdArray);
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.DATAPERMISSION_MODULE, LogEnum.OperatorType.UPDATEOPERATION, "配置角色："+name+"的数据权限");
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()),resultBean.getFeedBack(),"",response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false", "保存失败!", "", response);
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 删除角色下的所有组织和设备权限
	 * @param roleId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/asyncDeleteRoleDataPermission")
	public void asyncDeleteRoleDataPermission(Integer roleId,HttpServletRequest request,HttpServletResponse response)throws Exception{
		try {
			SysRole role = sRoleService.selectByPrimaryKey(roleId);
			String name = role!=null ? role.getRoleName() : null;
			ResultBean resultBean = new ResultBean();
			resultBean = sRoleService.deleteRoleDataPermission(roleId);
			LogUtil.writeLog(request, LogEnum.LogLevel.DELETE, LogEnum.OperatorModule.DATAPERMISSION_MODULE, LogEnum.OperatorType.PHYSICALDELOPERATION, "置空角色："+name+"的数据权限");
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()),resultBean.getFeedBack(),"",response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false", "保存失败!", "", response);
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 查询该角色下是否有用户
	 * @param categoryId
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/checkRelatedRole")
	public void checkRelatedRole(Integer roleId,HttpServletResponse response, HttpServletRequest request) throws Exception{
		try {
			ResultBean resultBean = new ResultBean();
			List<SysUserRole> userRoleList = sysUserRoleService.selectByRoleId(roleId);
			if (CollectionUtil.isNotEmpty(userRoleList)) {
				resultBean.setFlag(false);
			} else {
				resultBean.setFlag(true);
			}
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()),null,"",response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","删除失败，请确认！","",response);
		}
	}
}
