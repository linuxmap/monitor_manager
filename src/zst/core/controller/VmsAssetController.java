package zst.core.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import zst.core.dao.SysUserAssetMapper;
import zst.core.entity.ResultBean;
import zst.core.entity.SysOrg;
import zst.core.entity.VmsAsset;
import zst.core.entity.VmsAssetCameraRelation;
import zst.core.entity.VmsCamera;
import zst.core.entity.VmsDeviceLogin;
import zst.core.entity.VmsDeviceType;
import zst.core.entity.VmsModel;
import zst.core.entity.VmsOrgAsset;
import zst.core.entity.VmsProduct;
import zst.core.entity.VmsVendor;
import zst.core.service.ISysOrgService;
import zst.core.service.IVmsAssetCameraRelationService;
import zst.core.service.IVmsAssetService;
import zst.core.service.IVmsCameraService;
import zst.core.service.IVmsDeviceLoginService;
import zst.core.service.IVmsDeviceTypeService;
import zst.core.service.IVmsModelService;
import zst.core.service.IVmsOrgAssetService;
import zst.core.service.IVmsProductService;
import zst.core.service.IVmsRoleAssetService;
import zst.core.service.IVmsVendorService;
import zst.core.service.datatree.SysDataHelper;
import zst.extend.common.Constant;
import zst.extend.enums.AssetAssociationWayEnum;
import zst.extend.enums.LogEnum;
import zst.extend.exception.PlatformException;
import zst.extend.page.PageBean;
import zst.extend.util.CollectionUtil;
import zst.extend.util.LogUtil;
import zst.extend.util.PageUtil;

@Controller
@RequestMapping("/asset")
public class VmsAssetController {

	private static final Log logger = LogFactory.getLog(VmsAssetController.class);
	
	//含子部门查询标识
	private static final String CONTAINBRANCH = "1";
	
	//含部门查询用户对象的key
	private static final String SEARCH_ASSET_POJO_KEY = "asset";
	//含部门查询组织机构id集合的key
	private static final String SEARCH_ORG_ID_LIST_KEY = "list";
	
	private static final Integer AD_AREA = 6;//AD域
	public static final Integer ENORMOUS_WORK = 5;//重大作业
	
	@Resource
	private ISysOrgService orgService;
	
	@Resource
	private IVmsAssetService assetService;
	
	@Resource
	private IVmsDeviceTypeService deviceTypeService;
	
	@Resource
	private IVmsVendorService vendorService;
	
	@Resource
	private IVmsProductService productService;
	
	@Resource
	private IVmsModelService modelService;
	
	@Resource
	private IVmsCameraService cameraService;
	
	@Resource
	private IVmsDeviceLoginService deviceLoginService;
	
	@Resource
	private IVmsOrgAssetService orgAssetService;
	
	@Resource
	private SysDataHelper dataHelper;
	
	@Resource
	private IVmsAssetCameraRelationService assetCameraRelationService;
	
	@Resource
	private IVmsRoleAssetService roleAssetService;
	
	@Resource
	private SysUserAssetMapper userAssetMapper;
	
	/**
	 * 打开设备树形管理页面
	 * @param model
	 * @param funtId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openAssetManage")
	public String openAssetManage(Model model, String funtId) throws Exception {
		model.addAttribute("funtId", funtId);
		return "capital/asset/treeOrgAsset";
	}
	
	/**
	 * 查询资产集合
	 * @param model
	 * @param vmsAsset
	 * @param vmsCamera
	 * @param rows
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("queryAssets")
	public String queryAssets(Model model, VmsAsset vmsAsset ,VmsCamera vmsCamera, String containBranch, String rows,String page,Integer funtId,HttpServletRequest request) throws PlatformException{
		try {
			long totalRows = 0L;
			List<VmsAsset> vmsVendorList = null;
			vmsAsset.setPage(rows, page);
			vmsAsset.setDeleteflag(false);
			//获取当前组织机构节点
			Integer orgId = vmsAsset.getOrgId();
			if (CONTAINBRANCH.equals(containBranch)) {
				//查询当前节点和当前节点下的子节点
				vmsAsset.setOrgId(null);
				
				Map<String, Object> map = new HashMap<String, Object>();
				//查询子节点  非AD域 非重大作业
				List<SysOrg> orgAllList = dataHelper.getOrgList();
				List<SysOrg> resultList = new ArrayList<SysOrg>();
				resultList = treeOrgList(orgAllList, orgId, resultList);//该节点的所有子部门
				List<Integer> orgIdList = new ArrayList<Integer>();
				if (resultList!=null && resultList.size()>0) {
					for (SysOrg org : resultList) {
						orgIdList.add(org.getOrgId());
					}
				}
				//将自己加入
				orgIdList.add(orgId);
				map.put(SEARCH_ORG_ID_LIST_KEY, orgIdList);
				map.put(SEARCH_ASSET_POJO_KEY, vmsAsset);
				totalRows = assetService.selectMultiCountByMap(map);
				if(0<totalRows){
					vmsVendorList = assetService.selectMultiListByMap(map);
				}
			} else {
				//只查当前节点
				totalRows = assetService.selectMultiCountByObj(vmsAsset);
				if(0<totalRows){
					vmsVendorList = assetService.selectMultiListByObj(vmsAsset);
				}
			}
			
			List<VmsDeviceType> typeList = deviceTypeService.selectListByObj(null);//设备类型
			List<VmsVendor> vendorList = vendorService.selectListByObj(null);//厂商
			//设置分页
			PageBean myPage = PageBean.PageSetParameter(rows,page,totalRows);
			model.addAttribute("list",vmsVendorList);
			//vmsAsset的部门id再还原
			vmsAsset.setOrgId(orgId);
			model.addAttribute("vmsAsset",vmsAsset);
			model.addAttribute("typeList",typeList);
			model.addAttribute("vendorList",vendorList);
			model.addAttribute("containBranch",containBranch);//是否勾选子部门标识
			model.addAttribute("myPage", myPage);
			model.addAttribute("funtId", funtId);
			//处理所用有的组织按钮权限
			Map<Integer, List<String>> map = (Map<Integer, List<String>>) request.getSession().getAttribute(Constant.USER_BUTTON);
			if (map != null) {
				List<String> list = map.get(funtId+"");
				JSONArray fromObject = JSONArray.fromObject(list);
				model.addAttribute(Constant.BUTTON_LIST, fromObject.toString());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
		return "capital/asset/listAsset";
	}
	
    /**
     * 获取某个父节点下面的所有子节点  非AD域和非重大作业
     * @param orgList
     * @param pid
     * @return
     */
    private static List<SysOrg> treeOrgList(List<SysOrg> orgList, Integer pid, List<SysOrg> resultList){
        for (SysOrg org: orgList) {
            if (org.getParentId().intValue() == pid.intValue() && ((org.getOrgSource()!=null && AD_AREA.intValue()!=org.getOrgSource().intValue()) || (org.getOrgSource()!=null && ENORMOUS_WORK.intValue()!=org.getOrgSource().intValue()) )) {
            	resultList.add(org);
            	treeOrgList(orgList, org.getOrgId(),resultList);
            } 
        }
        return resultList;
    }
    
    /**
     * 验证该设备下是否有角色关联
     * @param assetIds
     * @param orgId
     * @param response
     * @param request
     * @throws Exception
     */
	@RequestMapping("/checkRelatedAssets")
	public void checkRelatedAssets(@RequestParam(value = "assetIds[]")Integer[] assetIds,Integer orgId, HttpServletResponse response, HttpServletRequest request) throws Exception { 
		try{
			if (assetIds!=null && assetIds.length>0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orgId", orgId);
				map.put("ids", assetIds);
				long count = roleAssetService.selectCountByMap(map);
				long count2 = userAssetMapper.selectCountByMap(map);
				List<Integer> asList = Arrays.asList(assetIds);
				long totalRelation = assetCameraRelationService.selectCountByAsset(asList);
				if (count>0 || count2>0 || totalRelation>0) {
					PageUtil.toBeJsonByMap("false","选择的设备已分配到角色，请在菜单【数据权限管理】->【数据权限】中取消关联或者取消与摄像机的关联。","",response);
				} else {
					PageUtil.toBeJsonByMap("true","删除成功！","",response);
				}
			} else {
				PageUtil.toBeJsonByMap("false","没有携带设备参数","",response);
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","删除失败，请确认！","",response);
		}
	}
	
	/**
	 * 批量修改状态
	 * @param assetIds
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/deleteBatch")
	public void deleteBatch(@RequestParam(value = "assetIds[]")Integer[] assetIds,Integer orgId, HttpServletResponse response, HttpServletRequest request) throws Exception { 
		try{
			if (assetIds!=null && assetIds.length>0) {
				StringBuilder builder = new StringBuilder();
				List<Integer> assetIdList = Arrays.asList(assetIds);
				//查询删除的设备的名称
				List<VmsAsset> deletedAssets = assetService.selectListByIds(assetIdList);
				if (CollectionUtil.isNotEmpty(deletedAssets)) {
					String separator = "，";
					for (int i=0; i<deletedAssets.size(); i++) {
						builder.append(deletedAssets.get(i).getName());
						if (i<deletedAssets.size()-1) {
							builder.append(separator);
						}
					}
				}
				
				assetService.updateBatchByAssetIds(assetIdList);//删除设备单表中的数据
				//删除设备和组织机构关联表的数据
				List<VmsOrgAsset> deleteList = new ArrayList<VmsOrgAsset>();
				if (assetIds!=null && assetIds.length>0) {
					VmsOrgAsset orgAsset = null;
					for (Integer assetId : assetIds) {
						orgAsset = new VmsOrgAsset();
						orgAsset.setAssetId(assetId);
						orgAsset.setOrgId(orgId);
						deleteList.add(orgAsset);
						
						//删除login表和camera表的对应的资产  三个表共用一个主键
						cameraService.deleteByPrimaryKey(assetId);
						deviceLoginService.deleteByPrimaryKey(assetId);
					}
				}
				orgAssetService.deleteByOrgAssetField(deleteList);
				//写入日志
				LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.ASSET_MANAGE, LogEnum.OperatorType.UPDATEOPERATION, "删除设备："+builder.toString());
				PageUtil.toBeJsonByMap("true","删除成功！","",response);
			} else {
				PageUtil.toBeJsonByMap("false","没有携带设备参数","",response);
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","删除失败，请确认！","",response);
		}
	}
	
	/**
	 * 批量设置级别
	 * @param assetIds
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/setLevelBatch")
	public void setLevelBatch(@RequestParam(value = "assetIds[]")Integer[] assetIds, String levelName, HttpServletResponse response, HttpServletRequest request) throws Exception { 
		try{
			if (assetIds != null && assetIds.length>0) {
				//设置等级
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("level", levelName);
				map.put("ids", assetIds);
				assetService.updateBatchAssetLevel(map);//批量修改的少，所以在循环中批量修改
				
				StringBuilder builder = new StringBuilder();
				List<Integer> assetIdList = Arrays.asList(assetIds);
				//查询处理的设备的名称
				List<VmsAsset> deletedAssets = assetService.selectListByIds(assetIdList);
				if (CollectionUtil.isNotEmpty(deletedAssets)) {
					String separator = "，";
					for (int i=0; i<deletedAssets.size(); i++) {
						builder.append(deletedAssets.get(i).getName());
						if (i<deletedAssets.size()-1) {
							builder.append(separator);
						}
					}
				}
				PageUtil.toBeJsonByMap("true","设置成功！","",response);
				//写入日志
				LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.ASSET_MANAGE, LogEnum.OperatorType.UPDATEOPERATION, "设置设备："+builder.toString()+"级别为"+levelName);
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","设置失败，请确认！","",response);
		}
	}
	
	/**
	 * 进入新增资产页面
	 * @param model
	 * @param vmsAsset
	 * @param name
	 * @param funtType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openOperateAsset")
	public String openOperateAsset(Model model, VmsAsset vmsAsset, String funtType,HttpServletRequest request) throws Exception {
		try{
			//查询部门
			SysOrg org = orgService.selectByPrimaryKey(vmsAsset.getOrgId());
			if (org!=null) {
				vmsAsset.setOrgName(org.getOrgName());
			}
			
			List<VmsDeviceType> typeList = deviceTypeService.selectListByObj(null);//设备类型
			JSONArray typeJson = JSONArray.fromObject(typeList);
			String typeJ = typeJson.toString();
			
			List<VmsVendor> vendorList = vendorService.selectListByObj(null);//厂商
			
			List<VmsProduct> productList = productService.selectListByObj(null);//产品
			JSONArray productJson = JSONArray.fromObject(productList);
			String productJ = productJson.toString();
			
			List<VmsModel> modelList = modelService.selectListByObj(null);//型号
			JSONArray modelJson = JSONArray.fromObject(modelList);
			String modelJ = modelJson.toString();
			
			List<VmsDeviceLogin> deviceLoginList = deviceLoginService.selectListByObj(null);//设备登录信息集合
			
			model.addAttribute("deviceLoginList", deviceLoginList);
			model.addAttribute("typeList", typeList);
			model.addAttribute("vendorList", vendorList);
			model.addAttribute("productJ", productJ);
			model.addAttribute("modelJ", modelJ);
			model.addAttribute("typeJ", typeJ);
			
			model.addAttribute("vmsAsset", vmsAsset);
			model.addAttribute("funtType", funtType);
			return "capital/asset/addAsset";
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 查询资产详情
	 * @param model
	 * @param vmsAsset
	 * @param orgName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/detailAsset")
	public String detailAsset(Model model, VmsAsset vmsAsset,String orgName, HttpServletRequest request) throws Exception {
		try{
			List<VmsAsset> assetList = assetService.selectMultiListByObj(vmsAsset);
			if(assetList != null && assetList.size()>0){
				vmsAsset = assetList.get(0);
				model.addAttribute("vmsAsset", vmsAsset);
				SysOrg org = orgService.selectByPrimaryKey(vmsAsset.getOrgId());
				if (org!=null) {
					model.addAttribute("orgName", org.getOrgName());
				}
			}

			List<VmsDeviceType> typeList = deviceTypeService.selectListByObj(null);//设备类型
			JSONArray typeJson = JSONArray.fromObject(typeList);
			String typeJ = typeJson.toString();
			
			List<VmsVendor> vendorList = vendorService.selectListByObj(null);//厂商
			
			List<VmsProduct> productList = productService.selectListByObj(null);//产品
			
			List<VmsModel> modelList = modelService.selectListByObj(null);//型号
			
			List<VmsDeviceLogin> deviceLoginList = deviceLoginService.selectListByObj(null);//设备登录信息集合
			
			//查询camera表格
			VmsCamera vmsCamera = cameraService.selectByPrimaryKey(vmsAsset.getAssetId());
			
			model.addAttribute("deviceLoginList", deviceLoginList);
			model.addAttribute("typeList", typeList);
			model.addAttribute("vendorList", vendorList);
			model.addAttribute("productList", productList);
			model.addAttribute("modelList", modelList);
			model.addAttribute("typeJ", typeJ);
			model.addAttribute("vmsCamera", vmsCamera);
			model.addAttribute("funtType", "view");
			
			//如果该设备本身就是登录平台
			VmsDeviceLogin deviceLoginVO = deviceLoginService.selectByPrimaryKey(vmsAsset.getAssetId());
			if (deviceLoginVO!=null) {
				deviceLoginVO.setConnecttypeLong(Long.valueOf(deviceLoginVO.getConnecttype()));
				model.addAttribute("deviceLoginVO", deviceLoginVO);
				return "capital/asset/detailAssetLogin";
			}
			
			return "capital/asset/detailAsset";
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 编辑资产信息
	 * @param model
	 * @param vmsAsset
	 * @param orgName
	 * @param funtType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openEditAsset")
	public String openEditAsset(Model model, VmsAsset vmsAsset, String orgName, String funtType,HttpServletRequest request) throws Exception {
		try {
			List<VmsAsset> assetList = assetService.selectMultiListByObj(vmsAsset);
			if(assetList != null){
				vmsAsset = assetList.get(0);
				//查询是否需要登录信息
				VmsDeviceType deviceType = deviceTypeService.selectByPrimaryKey(vmsAsset.getDevicetypeId());
				model.addAttribute("deviceType", deviceType);
				if (deviceType!=null && deviceType.getLoginflag()!=null) {
					if (deviceType.getLoginflag().intValue() == 1) {
						//有登录信息  
						model.addAttribute("loginDisplay", "display");
					} else if (deviceType.getLoginflag().intValue() == 0) {
						//没有登录信息
						model.addAttribute("loginDisplay", "none");
					}
				}
				model.addAttribute("vmsAsset", vmsAsset);
				model.addAttribute("orgName", vmsAsset.getOrgName());
			}
			//查出所有的设备类型
			List<VmsDeviceType> typeList = deviceTypeService.selectListByObj(null);
			JSONArray typeJson = JSONArray.fromObject(typeList);
			String typeJ = typeJson.toString();
			//编辑时查出所有的厂商
			List<VmsVendor> vendorList = vendorService.selectListByObj(null);
			//查询所有的产品
			List<VmsProduct> productList = productService.selectListByObj(null);
			JSONArray productJson = JSONArray.fromObject(productList);
			String productJ = productJson.toString();
			if(productList!=null && productList.size()>0){
				List<VmsProduct> currentProductList = new ArrayList<VmsProduct>();
				for(VmsProduct product : productList){
					if(product.getProductId().intValue() == vmsAsset.getProductId().intValue()){
						currentProductList.add(product);
					}
				}
				model.addAttribute("currentProductList", currentProductList);
			}
			//查询所有的型号
			List<VmsModel> modelList = modelService.selectListByObj(null);
			JSONArray modelJson = JSONArray.fromObject(modelList);
			String modelJ = modelJson.toString();
			if(modelList!=null && modelList.size()>0){
				List<VmsModel> currentModelList = new ArrayList<VmsModel>();
				for(VmsModel cModel : modelList){
					if(cModel.getModelId().intValue() == vmsAsset.getModelId().intValue()){
						currentModelList.add(cModel);
					}
				}
				model.addAttribute("currentModelList", currentModelList);
			}
			//查询设备对应的登录信息对象
			List<VmsDeviceLogin> deviceLoginList = deviceLoginService.selectListByObj(null);
			
			//查询camera表格
			VmsCamera vmsCamera = cameraService.selectByPrimaryKey(vmsAsset.getAssetId());
			
			model.addAttribute("deviceLoginList", deviceLoginList);
			model.addAttribute("typeList", typeList);
			model.addAttribute("vendorList", vendorList);
			model.addAttribute("productJ", productJ);
			model.addAttribute("modelJ", modelJ);
			model.addAttribute("typeJ", typeJ);
			model.addAttribute("vmsCamera", vmsCamera);
			model.addAttribute("vmsAsset", vmsAsset);
			model.addAttribute("funtType", funtType);
			
			//如果该设备本身就是登录平台,进入编辑登录平台页面
			VmsDeviceLogin deviceLoginVO = deviceLoginService.selectByPrimaryKey(vmsAsset.getAssetId());
			if (deviceLoginVO!=null) {
				deviceLoginVO.setConnecttypeLong(Long.valueOf(deviceLoginVO.getConnecttype()));
				model.addAttribute("deviceLoginVO", deviceLoginVO);
				return "capital/asset/editAssetLogin";
			}
			
			return "capital/asset/editAsset";
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 进入设备的关联配置页面
	 * @param model
	 * @param assetId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAssociateCamera")
	public String toAssociateCamera(Model model, Integer assetId) throws Exception {
		try {
			ResultBean resultBean = dataHelper.asyncGetOrgTreeForAssociateAsset();
			if (resultBean!=null) {
				model.addAttribute("zTreeNodes", resultBean.getFeedBack());
			}
			//查询已经选择的设备
			VmsAssetCameraRelation acr = new VmsAssetCameraRelation();;
			acr.setAssetId(assetId);
			acr.setType(AssetAssociationWayEnum.ASSET_ASSOCIATION);
			List<VmsAssetCameraRelation> acrList = assetCameraRelationService.selectListByObj(acr);
			List<Integer> cameraIds = new ArrayList<Integer>();
			if (CollectionUtil.isNotEmpty(acrList)){
				for (VmsAssetCameraRelation assetCameraRelation : acrList) {
					cameraIds.add(assetCameraRelation.getCameraId());
				}
				List<VmsAsset> choosedCameras = assetService.selectListByIds(cameraIds);
				model.addAttribute("choosedCameras", choosedCameras);
			}
			model.addAttribute("assetId", assetId);
			return "capital/asset/associateCameraForAsset";
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 设备管理-关联设备操作-异步展开组织下的设备子节点 
	 * @param response
	 * @param parentId
	 * @throws Exception
	 */
	@RequestMapping("/asyncGetChildrenByOrgId")
	public void asyncGetChildrenByOrgId(HttpServletResponse response, Integer parentId) throws Exception {
		try {
			JSONArray jsonArray = dataHelper.asyncGetChildrenByOrgId(parentId);
			PageUtil.toBeJsonByMap("true", "",jsonArray, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 设备管理-关联设备操作-保存设备和摄像机直接的关联关系
	 * @param assetId
	 * @param cameraIdArray
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveAssetCameraRelation")
	public void saveAssetCameraRelation(Integer assetId, @RequestParam(value = "cameraIdArray[]")Integer[] cameraIdArray, HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		try {
			ResultBean resultBean = assetService.updateAssetCameraRelTransaction(assetId, cameraIdArray);
			VmsAsset asset = assetService.selectByPrimaryKey(assetId);
			StringBuilder builder = new StringBuilder();
			String name = asset!=null ? asset.getName() : null;
			if (cameraIdArray!=null && cameraIdArray.length>0) {
				List<Integer> asList = Arrays.asList(cameraIdArray);
				List<VmsAsset> relations = assetService.selectListByIds(asList);
				if (CollectionUtil.isNotEmpty(relations)) {
					StringBuilder relationName = new StringBuilder();
					String separator = "，";
					for (int i=0; i<relations.size(); i++) {
						relationName.append(relations.get(i).getName());
						if (i<relations.size()-1) {
							relationName.append(separator);
						}
					}
					builder.append("给设备："+name+"关联摄像机："+relationName);
				}
			} else {
				builder.append("给设备："+name+"取消设备关联摄像机");
			}
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.ASSET_MANAGE, LogEnum.OperatorType.UPDATEOPERATION, builder.toString());
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()),"保存成功！","",response);
		}catch(Exception e) {
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","操作失败，请确认！","",response);
		}
		
	}
	
	/**
	 * 验证用户名是否重复
	 * @param vmsAsset
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/validateAssetName")
	public void validateAssetName(VmsAsset vmsAsset, HttpServletResponse response) throws Exception {
		vmsAsset.setDeleteflag(false);
		long total = assetService.selectCountByObj(vmsAsset);
		if(total>0) {
			PageUtil.toBeJsonByMap("false","","",response);
			return;
		}
		PageUtil.toBeJsonByMap("true","","",response);
	}
	
	/**
	 * 新增设备,关联camera和device_login
	 * @param vmsAsset
	 * @param vmsCamera
	 * @param vmsDeviceLogin
	 * @param vmsOrgAsset
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addAsset")
	public void addAsset(VmsAsset vmsAsset ,VmsCamera vmsCamera, VmsDeviceLogin vmsDeviceLogin ,VmsOrgAsset vmsOrgAsset, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			ResultBean resultBean = assetService.insertAssetTransaction(vmsAsset, vmsCamera, vmsDeviceLogin, vmsOrgAsset);
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.ASSET_MANAGE, LogEnum.OperatorType.INSERTOPERATION, "添加设备："+vmsAsset.getName());
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()),"","",response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","新增失败，请确认！","",response);
		}
	}
	
	
	/**
	 * 修改资产  只是修改asset和camera的基础信息，对于登录信息需要考虑更好的解决方案
	 * @param vmsAsset
	 * @param vmsCamera
	 * @param vmsDeviceLogin
	 * @param vmsOrgAsset
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/editAsset")
	public void editAsset(VmsAsset vmsAsset ,VmsCamera vmsCamera, VmsDeviceLogin vmsDeviceLogin ,VmsOrgAsset vmsOrgAsset, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			ResultBean resultBean = assetService.updateAssetTransaction(vmsAsset, vmsCamera, vmsDeviceLogin, vmsOrgAsset);
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.ASSET_MANAGE, LogEnum.OperatorType.UPDATEOPERATION, "修改设备："+vmsAsset.getName());
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()),"","",response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","编辑失败，请确认！","",response);
		}
	}
	
	/**
	 * 修改带登录平台的设备
	 * @param vmsAsset
	 * @param vmsCamera
	 * @param vmsDeviceLogin
	 * @param vmsOrgAsset
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/editLoginAsset")
	public void editLoginAsset(VmsAsset vmsAsset ,VmsCamera vmsCamera, VmsDeviceLogin vmsDeviceLogin ,VmsOrgAsset vmsOrgAsset, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			ResultBean resultBean = assetService.updateLoginAssetTransaction(vmsAsset, vmsCamera, vmsDeviceLogin, vmsOrgAsset);
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.ASSET_MANAGE, LogEnum.OperatorType.UPDATEOPERATION, "修改登录设备");
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()),"","",response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","编辑失败，请确认！","",response);
		}
	}
	
	/**批量删除user(逻辑删除,更新状态) **/
/*	@RequestMapping("/deleteBatch")
	public void deleteBatch(@RequestParam(value = "userIds[]")Integer[] userIds, HttpServletResponse response, HttpServletRequest request) throws Exception { 
		try{
			List<Integer> userIdList = Arrays.asList(userIds);
			assetService.deleteBatchByOrgIds(userIdList);
			PageUtil.toBeJsonByMap("true","删除成功！","",response);
			//写入日志
			LogUtil.writeLog(request, ConstType.LogType.OperationLog, ConstType.OperatorModule.SysUserModule, ConstType.OperatorType.UpdateDelOperator, null);
		}catch(Exception e) {
			logger.error(e.getMessage());
			PageUtil.toBeJsonByMap("false","删除失败，请确认！","",response);
		}
	}*/
	
	
	/**选择用户 **/
	@RequestMapping("/selectUser")
	public String selectUser(Model model,String adminTel, String adminName, String adminId, String methodName) throws Exception {
		model.addAttribute("adminName", adminName);
		model.addAttribute("adminId", adminId);
		model.addAttribute("adminTel", adminTel);
		model.addAttribute("methodName", methodName);
		return "common/selectUserTree";
	}

}
