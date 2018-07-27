package zst.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import zst.core.entity.ResultBean;
import zst.core.entity.SysFunt;
import zst.core.entity.SysRoleCategory;
import zst.core.entity.SysRoleCategoryFunt;
import zst.core.entity.VRoleTreeOrgAsset;
import zst.core.entity.VmsRoleAsset;
import zst.core.entity.VmsRoleOrg;
import zst.core.service.ISysFuntService;
import zst.core.service.ISysRoleCategoryService;
import zst.core.service.ISysRoleCategoryFuntService;
import zst.core.service.IVRoleTreeOrgAsssetService;
import zst.core.service.IVmsRoleAssetService;
import zst.core.service.IVmsRoleOrgService;
import zst.core.service.datatree.SysDataHelper;
import zst.extend.enums.LogEnum;
import zst.extend.exception.PlatformException;
import zst.extend.util.CommonUtil;
import zst.extend.util.LogUtil;
import zst.extend.util.PageUtil;

/**
 * 角色类别的controller
 * @author Ablert
 * @date 2018-1-23 下午4:58:20
 */
@Controller
@RequestMapping("/roleCategory")
public class SysRoleCategoryController {
	
	private static final Log logger = LogFactory.getLog(SysRoleCategoryController.class);
	
	/**
	 * 根节点菜单id
	 */
	private static final Integer FUNT_ROOT_ID = 1;
	
	private static final Integer VALID_CATEGORY_STATUS = 1;
	
	@Resource
	private ISysRoleCategoryService roleCategoryService;
	
	@Resource
	private ISysFuntService sysFuntService;
	
	@Resource
	private ISysRoleCategoryFuntService sysCategoryFuntService;
	
	@Resource
	private SysDataHelper sysDataHelper;
	
	@Resource
	private IVmsRoleOrgService roleOrgService;
	
	@Resource
	private IVmsRoleAssetService roleAssetService;
	
	@Resource
	private IVRoleTreeOrgAsssetService vasssetService;//注入此service用于匹配设备和组织的唯一性，设备可能挂在多个组织下，从视图中查询
	
	
	/**
	 * 新增角色类别
	 * @param roleCategory
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addRoleCategory")
	public void addRoleCategory(SysRoleCategory roleCategory, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			roleCategory.setStatus(VALID_CATEGORY_STATUS);
			roleCategoryService.insertSelective(roleCategory);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.FUNCTION_MODULE, LogEnum.OperatorType.INSERTOPERATION, "添加角色类别："+roleCategory.getName());
			PageUtil.toBeJsonByMap("true",roleCategory.getCategoryId()+"",roleCategory.getLogouttime(),response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","新增失败，请确认！","",response);
		}
	}
	
	/**
	 * 拷贝角色类别
	 * @param copyRoleCategoryId
	 * @param name
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/copyRoleCategory")
	public void copyRoleCategory(Integer copyRoleCategoryId, String realName, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultBean resultBean = new ResultBean();
		resultBean.setFlag(false);
		try {
			resultBean = roleCategoryService.insertSameObjectById(copyRoleCategoryId,realName);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.FUNCTION_MODULE, LogEnum.OperatorType.INSERTOPERATION, "添加角色类别："+realName);
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()),resultBean.getFeedBack(), resultBean.getPrimaryKey(), response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false",resultBean.getFeedBack(), resultBean.getPrimaryKey(), response);
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 查询改角色类型下是否有角色，角色下有用户
	 * @param categoryId
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/checkRelatedRole")
	public void checkRelatedRole(Integer categoryId,HttpServletResponse response, HttpServletRequest request) throws Exception{
		try {
			ResultBean resultBean = roleCategoryService.selectRelatedRole(categoryId);
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()),resultBean.getFeedBack(),"",response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","查询失败，请确认！","",response);
		}
	}
	
	/**
	 * 删除角色 根据角色类别id  包含级联删除,写在了service层
	 * @param categoryId
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/deleteByRoleCategoryId")
	public void deleteByRoleCategoryId(Integer categoryId,HttpServletResponse response, HttpServletRequest request) throws Exception{
		try {
			// TODO
			//根据roleId获取角色功能
			//根据roleId获取关联用户id
			//角色下有用户或者功能,该角色不能删除
			//删除角色 物理删除
			//删除关联表zst_category_funt数据
			SysRoleCategory category = roleCategoryService.selectByPrimaryKey(categoryId);
			String name = category!=null ? category.getName() : null;
			sysCategoryFuntService.deleteByCategoryid(categoryId);
			roleCategoryService.deleteByPrimaryKey(categoryId);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.DELETE, LogEnum.OperatorModule.FUNCTION_MODULE, LogEnum.OperatorType.LOGICALDELOPERATION, "删除角色类别："+name);
			PageUtil.toBeJsonByMap("true","删除成功！","",response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","删除失败，请确认！","",response);
		}
	}
	
	/**
	 * 根据主键修改角色类别
	 * @param roleCategory
	 * @param req
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updateByRoleCategoryId")
	public void updateByRoleCategoryId(SysRoleCategory roleCategory, HttpServletRequest req,HttpServletResponse response)throws Exception{
		try {
			//拼接变动
			StringBuilder action = new StringBuilder();
			SysRoleCategory oldCategory = roleCategoryService.selectByPrimaryKey(roleCategory.getCategoryId());
			if (oldCategory!=null) { 
				action.append("修改角色类别："+oldCategory.getName());
				if (CommonUtil.notEqualString(oldCategory.getName(), roleCategory.getName())) {
					action.append("名称由"+oldCategory.getName()+"改为："+roleCategory.getName());
				}
			}
			roleCategoryService.updateByPrimaryKeySelective(roleCategory);
			//写入日志
			LogUtil.writeLog(req, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.FUNCTION_MODULE, LogEnum.OperatorType.UPDATEOPERATION, action.toString());
			PageUtil.toBeJsonByMap("true","","",response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","编辑失败，请确认！","",response);
		}
	}
	
	/**
	 * 查询所有角色类别以及角色类别对应的权限
	 * @param model
	 * @param roleCategory
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRoleCategoryFuntList")
	public String getRoleCategoryFuntList(Model model,SysRoleCategory roleCategory) throws Exception{
		try {
			//查询所有的角色类别
			roleCategory.setStatus(VALID_CATEGORY_STATUS);
			List<SysRoleCategory> roleCategoryList = roleCategoryService.selectListByObj(roleCategory);
			Integer id = 1;//根节点菜单id
			//根据root节点获取所有菜单集合
			List<SysFunt> roleMenuList = sysFuntService.selectSubFunt(id);
			model.addAttribute("roleMenuList", roleMenuList);
			model.addAttribute("roleCategoryList", roleCategoryList);
			return "sys/roleCategory/roleCategoryManager";
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
	public String toBackFrame(Model model,String frameUrl)throws Exception{
		try {
			//根据root节点获取所有菜单集合
			List<SysFunt> roleMenuList = sysFuntService.selectSubFunt(FUNT_ROOT_ID);
			model.addAttribute("roleMenuList", roleMenuList);
			return "sys/roleCategory/roleCategoryBack";
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
			return "sys/roleCategory/roleCategoryClientTree";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 进入权限范围权限的frame
	 * @param model
	 * @param frameUrl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toRoleRangeFrame")
	public String toRoleRangeFrame(Model model ,String frameUrl)throws Exception{
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
	 * 根据roleFunt树形查询集合
	 * @param model
	 * @param categoryFunt
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getFuntByCategoryId")
	public void  getFuntByCategoryId(Model model,SysRoleCategoryFunt categoryFunt,HttpServletResponse response)throws Exception{
		try {
			List<SysRoleCategoryFunt> categoryFuntList = sysCategoryFuntService.selectListByObj(categoryFunt);
			if (categoryFuntList!=null&&categoryFuntList.size()>0) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < categoryFuntList.size(); i++) {
					sb.append(categoryFuntList.get(i).getFuntId()+",");
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
	public void  getRangeByRoleId(Model model,Integer roleId,HttpServletResponse response)throws Exception{
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
				sysCategoryFuntService.deleteByCategoryid(roleId);
				PageUtil.toBeJsonByMap("true", "保存成功!", "", response);
			}
			else{
				sysCategoryFuntService.deleteByCategoryid(roleId);
				List<SysRoleCategoryFunt> list = new ArrayList<SysRoleCategoryFunt>();
				if(funtIds!=null&&funtIds.length>0){
					for(int i=0;i<funtIds.length;i++){
						SysRoleCategoryFunt sysFunt = new SysRoleCategoryFunt();
						sysFunt.setFuntId(funtIds[i]);
						sysFunt.setRoleCategoryId(roleId);
						list.add(sysFunt);
					}
					//根据角色id批量添加功能
					sysCategoryFuntService.addList(list);
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
	 * 修改服务端和客户端权限
	 * @param roleId
	 * @param funtIds
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updateDoubleFuntByCategoryId")
	public void updateDoubleFuntByCategoryId(Integer roleCategoryId, Integer funtGroup, @RequestParam(value="funtIds[]")Integer[] funtIds,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			ResultBean resultBean = roleCategoryService.updateDoubleFuntByCategoryId(roleCategoryId,funtGroup,funtIds);
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.FUNCTION_MODULE, LogEnum.OperatorType.UPDATEOPERATION, null);
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()), resultBean.getFeedBack(), "", response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
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
			if(orgIds!=null && orgIds.length>0){
				for(Integer orgId : orgIds){
					roleOrg = new VmsRoleOrg();
					roleOrg.setRoleId(roleId);
					roleOrg.setOrgId(orgId);
					roleOrgList.add(roleOrg);
				}
				roleOrgService.insertBatch(roleOrgList);
			}
			
			VmsRoleAsset roleAsset = null;
			List<VmsRoleAsset> roleAssetList = new ArrayList<VmsRoleAsset>();
			if(equIds!=null && equIds.length>0){
				for(String assetId : equIds){
					roleAsset = new VmsRoleAsset();
					String[] assetOrgArray = assetId.split("_");
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
	
}
