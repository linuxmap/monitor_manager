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

import zst.core.dao.SysOrgMapper;
import zst.core.entity.SysOrg;
import zst.core.entity.VmsAsset;
import zst.core.entity.VmsCamera;
import zst.core.entity.VmsDeviceLogin;
import zst.core.entity.VmsDeviceType;
import zst.core.entity.VmsModel;
import zst.core.entity.VmsOrgAsset;
import zst.core.entity.VmsProduct;
import zst.core.entity.VmsVendor;
import zst.core.service.IVmsAssetIntegrationService;
import zst.core.service.IVmsAssetService;
import zst.core.service.IVmsCameraService;
import zst.core.service.IVmsDeviceLoginService;
import zst.core.service.IVmsDeviceTypeService;
import zst.core.service.IVmsModelService;
import zst.core.service.IVmsOrgAssetService;
import zst.core.service.IVmsProductService;
import zst.core.service.IVmsVendorService;
import zst.core.service.datatree.SysDataHelper;
import zst.extend.common.Constant;
import zst.extend.enums.LogEnum;
import zst.extend.exception.PlatformException;
import zst.extend.page.PageBean;
import zst.extend.util.CollectionUtil;
import zst.extend.util.LogUtil;
import zst.extend.util.PageUtil;
import zst.extend.util.PinyinUtil;

/**
 * 处理设备的关系整合Handler
 * @author Ablert
 * @date 2018-1-3 下午2:17:30
 */
@Controller
@RequestMapping("/assetIntegration")
public class VmsAssetIntegrationController {

	private static final Log logger = LogFactory.getLog(VmsAssetIntegrationController.class);
	
	//含子部门查询标识
	private static final String CONTAINBRANCH = "1";
	
	//含部门查询用户对象的key
	private static final String SEARCH_ASSET_POJO_KEY = "asset";
	//含部门查询组织机构id集合的key
	private static final String SEARCH_ORG_ID_LIST_KEY = "list";
	
	private static final Integer AD_AREA = 6;//AD域
	
	private static final int COPY_DATA_FLAG = 2;//拷贝数据标识
	
	@Resource
	private IVmsAssetIntegrationService assetIntegrationService;
	
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
	private SysOrgMapper orgMapper;
	
	/**
	 * 打开设备树形管理页面
	 * @param model
	 * @param funtId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/openAssetManage")
	public String openAssetManage(Model model, String funtId,HttpServletRequest request) throws Exception {
		model.addAttribute("funtId", funtId);
		//处理所用有的组织按钮权限
		Map<Integer, List<String>> map = (Map<Integer, List<String>>) request.getSession().getAttribute(Constant.USER_BUTTON);
		if (map != null) {
			List<String> list = map.get(funtId+"");
			JSONArray fromObject = JSONArray.fromObject(list);
			model.addAttribute(Constant.BUTTON_LIST, fromObject.toString());
		}
		return "capital/assetIntegration/treeOrgAssetIntegration";
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
			List<VmsAsset> vmsVendorList = null;
			long totalRows = 0L;
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
				//查询组织节点下的设备表
				totalRows = assetService.selectMultiCountByObj(vmsAsset);
				if(0<totalRows){
					//查询字典条目集合
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
			model.addAttribute("myPage", myPage);
			model.addAttribute("containBranch",containBranch);//是否勾选子部门标识
			model.addAttribute("funtId",funtId);
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
		return "capital/assetIntegration/listAssetIntegration";
	}
	
    /**
     * 获取某个父节点下面的所有子节点  非AD域
     * @param orgList
     * @param pid
     * @return
     */
    private static List<SysOrg> treeOrgList(List<SysOrg> orgList, Integer pid, List<SysOrg> resultList){
        for (SysOrg org: orgList) {
            if (org.getParentId().intValue() == pid.intValue() && org.getOrgSource()!=null && AD_AREA.intValue()!=org.getOrgSource().intValue()) {
            	resultList.add(org);
            	treeOrgList(orgList, org.getOrgId(),resultList);
            } 
        }
        return resultList;
    }
	
	/**
	 * 进入到设备关联页面
	 * @param model
	 * @param assetIds
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openAssociateAssetOrg")
	public String openAssociateAssetOrg( Model model) throws Exception {
		return "capital/assetIntegration/associateAssetOrg";
	}
	
	/**
	 * 进入到设备及组织关联页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openAssociateAssetOrgMix")
	public String openAssociateAssetOrgMix( Model model) throws Exception {
		return "capital/assetIntegration/associateAssetOrgMix";
	}
	
	/** 
	 * 设备关联到组织  只是增加组织和设备之间的关系  取消设备关联时只是删除对应关系即可
	 * @param assetIds
	 * @param orgId
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/addOrgAsset")
	public void addOrgAsset(String assetIds,Integer orgId, HttpServletResponse response, HttpServletRequest request) throws Exception { 
		try {
			//日志拼接
			SysOrg org = orgMapper.selectByPrimaryKey(orgId);
			String orgName = org!=null ? org.getOrgName() : null;
			StringBuilder assetString = new StringBuilder(); 
			if (assetIds !=null) {
				List<VmsOrgAsset> newOrgAsset = new ArrayList<VmsOrgAsset>();
				String[] assetIdArr = assetIds.split(",");
				if (assetIdArr!=null && assetIdArr.length>0) {
					List<Integer> asList = new ArrayList<Integer>();
					for (int i=0; i<assetIdArr.length; i++) {
						VmsOrgAsset orgAsset = new VmsOrgAsset();
						orgAsset.setAssetId(Integer.parseInt(assetIdArr[i]));
						orgAsset.setOrgId(orgId);
						orgAsset.setSourceflag(COPY_DATA_FLAG);//设置来源为复制数据
						orgAsset.setVisibleflag(true);//默认可见
						orgAsset.setOrdernumber(1);
						newOrgAsset.add(orgAsset);
						
						asList.add(Integer.parseInt(assetIdArr[i]));
					}
					List<VmsAsset> assets = assetService.selectListByIds(asList);
					if (CollectionUtil.isNotEmpty(assets)) {
						String separator = "，";
						for (int i=0; i<assets.size(); i++) {
							assetString.append(assets.get(i).getName());
							if (i<assets.size()-1) {
								assetString.append(separator);
							}
						}
					}
				}
				
				//根据部门和资产id 去重复
				List<VmsOrgAsset> currOrgAssets = orgAssetService.selectListByObj(null);
				List<VmsOrgAsset> orgassetListNoDupAndSort = removeDuplicateUser(currOrgAssets, newOrgAsset);
				if (orgassetListNoDupAndSort!=null && orgassetListNoDupAndSort.size()>0) {
					orgAssetService.insertBatch(orgassetListNoDupAndSort);
					SysOrg sysOrg = new SysOrg();
					sysOrg.setOrgId(orgId);
					byte haveChild = 1;
					sysOrg.setHaveDevice(haveChild);
					orgMapper.updateByPrimaryKeySelective(sysOrg);
					PageUtil.toBeJsonByMap("true","添加成功！","",response);
					//写入日志
					LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.ASSET_INTEGERATION, LogEnum.OperatorType.INSERTOPERATION, "将设备："+assetString.toString()+"关联到组织："+orgName);
				} else {
					PageUtil.toBeJsonByMap("false","无新增关联关系，请确认！","",response);
				}
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","添加失败，请确认！","",response);
		}
	}
	
	/**
	 * 将组织和设备关联Mix  Transactional
	 * @param orgIdsToBeAssociate 投奔者集合
	 * @param orgId  新的父节点
	 * @param response
	 * @param request
	 * @throws Exception
	 * 1、根据投奔者id的集合查询出要复制的组织机构集合orgListCopy
	 * 2、给orgListCopy的每个对象设置新的值，比如主键id为null，parentId，编码等等属性，需要重新设置，完成不重复的复制
	 * 3、将logListCopy下的设备复制一份，然后加入到新的组织中
	 * 4、去重复要不要考虑下呢？
	 */
	@RequestMapping("/addOrgAssetMix")
	public void addOrgAssetMix(String orgIds,String orgParIds, Integer parOrgId, HttpServletResponse response, HttpServletRequest request) throws Exception { 
		String cutChar = ",";//切割前台js的分隔符
		try {
			if (orgIds!=null && orgParIds!=null) {
				String[] ids = orgIds.split(cutChar);
				String[] parIds = orgParIds.split(cutChar);
				if (ids.length>0 && ids.length==parIds.length) {
					Integer[] nodeIdArray = new Integer[ids.length];
					Integer[] parIdArray = new Integer[ids.length];
					for (int i=0; i<ids.length; i++) {
						nodeIdArray[i] = Integer.valueOf(ids[i]);
					}
					for (int j=0; j<parIds.length; j++) {
						parIdArray[j] = Integer.valueOf(parIds[j]);
					}
					assetIntegrationService.insertHandleParChildRelation(nodeIdArray, parIdArray, parOrgId);
				}
				
			}
			//拼接日志
			SysOrg org = orgMapper.selectByPrimaryKey(parOrgId);
			String name = org!=null ? org.getOrgName() : null;
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.ASSET_INTEGERATION, LogEnum.OperatorType.INSERTOPERATION, "将组织和设备关联到组织："+name);
			PageUtil.toBeJsonByMap("true","添加成功","",response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","添加失败，请确认！","",response);
		}
	}
	
	/**
	 * 去重
	 * @param orgAssets
	 * @return
	 */
	 private List<VmsOrgAsset> removeDuplicateUser(List<VmsOrgAsset> currOrgAssets,List<VmsOrgAsset> newOrgAssets) {
		 List<VmsOrgAsset> dbList = new ArrayList<VmsOrgAsset>();
		 boolean flag = true;
		 for(VmsOrgAsset new1 : newOrgAssets){
			 flag = true;
			 for(VmsOrgAsset old : currOrgAssets){
				 if((old.getAssetId()+"_"+old.getOrgId()).equals((new1.getAssetId()+"_"+new1.getOrgId())) && new1.getAssetId()!=null && new1.getOrgId()!=null){
					 flag = false;
				 }
			 }
			 if(flag){
				 dbList.add(new1); 
			 }
		 }
		 return dbList;
	}
	 
	/**
	 * 取消组织结构和资产的关联，删除关联表vms_org_asset的记录
	 * @param assetIds
	 * @param orgId
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/cancelAssociation")
	public void cancelAssociation(@RequestParam(value = "assetIds[]")Integer[] assetIds ,Integer orgId, HttpServletResponse response, HttpServletRequest request) throws Exception { 
		try{
			//日志拼接
			SysOrg org = orgMapper.selectByPrimaryKey(orgId);
			String orgName = org!=null ? org.getOrgName() : null;
			StringBuilder assetString = new StringBuilder(); 
			if(assetIds != null && assetIds.length>0){
				List<VmsOrgAsset> assetOrgList = new ArrayList<VmsOrgAsset>();
				VmsOrgAsset orgAsset = null;
				for(Integer assetId : assetIds){
					orgAsset = new VmsOrgAsset();
					orgAsset.setAssetId(assetId);
					orgAsset.setOrgId(orgId);
					orgAsset.setSourceflag(COPY_DATA_FLAG);
					assetOrgList.add(orgAsset);
				}
				orgAssetService.deleteByOrgAssetAllField(assetOrgList);//删除关联表中的关系  不删除原始数据
				
				List<VmsAsset> assets = assetService.selectListByIds(Arrays.asList(assetIds));
				if (CollectionUtil.isNotEmpty(assets)) {
					String separator = "，";
					for (int i=0; i<assets.size(); i++) {
						assetString.append(assets.get(i).getName());
						if (i<assets.size()-1) {
							assetString.append(separator);
						}
					}
				}
				LogUtil.writeLog(request, LogEnum.LogLevel.DELETE, LogEnum.OperatorModule.ASSET_INTEGERATION, LogEnum.OperatorType.PHYSICALDELOPERATION, "取消组织机构："+orgName+"下设备："+assetString.toString()+"的关联");
				PageUtil.toBeJsonByMap("true","取消成功！","",response);
			}else{
				PageUtil.toBeJsonByMap("false","资产参数为空！","",response);
			}
			//写入日志
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","取消失败，请确认！","",response);
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
	public void deleteBatch(@RequestParam(value = "assetIds[]")Integer[] assetIds, HttpServletResponse response, HttpServletRequest request) throws Exception { 
		try{
			List<Integer> assetIdList = Arrays.asList(assetIds);
			assetService.updateBatchByAssetIds(assetIdList);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.DELETE, LogEnum.OperatorModule.ASSET_INTEGERATION, LogEnum.OperatorType.LOGICALDELOPERATION, "批量删除设备");
			PageUtil.toBeJsonByMap("true","删除成功！","",response);
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","删除失败，请确认！","",response);
		}
	}
	
	/**
	 * 打开设备资产操作窗口
	 * @param model
	 * @param vmsAsset
	 * @param name
	 * @param funtType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openOperateAsset")
	public String openOperateAsset(Model model, VmsAsset vmsAsset, String orgName, String funtType,HttpServletRequest request) throws Exception {
		try{
			if("edit".equals(funtType) || "view".equals(funtType) || "copy".equals(funtType)) {
				vmsAsset = assetService.selectByPrimaryKey(vmsAsset.getAssetId());
				orgName = vmsAsset.getName();
				if("copy".equals(funtType)) {
					vmsAsset.setAssetId(null);
				}
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
			model.addAttribute("orgName", orgName);
			model.addAttribute("funtType", funtType);
			return "capital/assetIntegration/operateAssetIntegration";
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 批量设置设备可见性
	 * @param assetIds
	 * @param visibleOrg
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/setAssetVisible")
	public void setAssetVisible(@RequestParam(value = "assetIds[]")Integer[] assetIds,Integer orgId, Integer visibleOrg, HttpServletResponse response, HttpServletRequest request) throws Exception { 
		try{
			if(visibleOrg != null){
				Boolean visibleFlag = visibleOrg==1 ? true : false;
				List<VmsOrgAsset> orgAssets = new ArrayList<VmsOrgAsset>();
				if(assetIds!=null && assetIds.length>0){
					List<Integer> asList = Arrays.asList(assetIds);
					VmsOrgAsset orgAsset = null;
					for(Integer assetId : assetIds){
						orgAsset = new VmsOrgAsset();
						orgAsset.setAssetId(assetId);
						orgAsset.setOrgId(orgId);
						orgAsset.setVisibleflag(visibleFlag);
						orgAssets.add(orgAsset);
					}
					orgAssetService.updateBatchOrgAssetVisible(orgAssets, visibleFlag, asList, orgId);
					
					List<VmsAsset> assets = assetService.selectListByIds(asList);
					StringBuilder builder = new StringBuilder();//非线程安全 快
					if (CollectionUtil.isNotEmpty(assets)) {
						String separator = "，";
						for (int i=0; i<assets.size(); i++) {
							builder.append(assets.get(i).getName());
							if (i<assets.size()-1) {
								builder.append(separator);
							}
						}
					}
					String visible = visibleOrg==1 ? "可见" : "不可见";
					PageUtil.toBeJsonByMap("true","删除成功！","",response);
					//写入日志
					LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.ASSET_INTEGERATION, LogEnum.OperatorType.UPDATEOPERATION, "设置设备："+builder.toString()+"为"+visible);
				}
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","删除失败，请确认！","",response);
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
			//handle asset
			vmsAsset.setPinyin(PinyinUtil.getPinYinHeadChar(vmsAsset.getName(), 0));//大写
			vmsAsset.setOrdernumber(1);//TODO 先默认设置为1
			assetService.insertSelective(vmsAsset);
			//handle asset_org
			if(vmsAsset.getAssetId() != null){//成功插入asset表
				//handle vms_org_asset
				vmsOrgAsset.setAssetId(vmsAsset.getAssetId());
				vmsOrgAsset.setOrgId(vmsAsset.getOrgId());
				orgAssetService.insertSelective(vmsOrgAsset);
				
				//两种情况 1、不需要登录信息的  2、需要登录信息的
				if(vmsAsset.getLoginFlag() == 1){
					if(vmsAsset.getLoginSource() == null){
						//新填写信息 asset_deviceLogin camera
						vmsDeviceLogin.setDeviceloginId(vmsAsset.getAssetId());
						vmsDeviceLogin.setType(0);//TODO
						deviceLoginService.insertSelective(vmsDeviceLogin);
						//handle asset_camera
						vmsCamera.setCameraId(vmsAsset.getAssetId());//camera自带登录设备的id
						vmsCamera.setDeviceloginCameraId(vmsDeviceLogin.getDeviceloginId());
						cameraService.insertSelective(vmsCamera);
					}else if(vmsAsset.getLoginFlag() == 1){
						//判断选择的是哪一个框 
						//直接从下拉框选择资源   camera中设置字段
						vmsCamera.setCameraId(vmsAsset.getAssetId());
						cameraService.insertSelective(vmsCamera);//setDeviceloginCameraId已经从页面携带过来
					}
				}else{
					//不需要登录信息，直接向asset表中插入数据，无额外操作
				}
				
				
			}
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.ASSET_INTEGERATION, LogEnum.OperatorType.INSERTOPERATION, "添加设备："+vmsAsset.getName());
			PageUtil.toBeJsonByMap("true","","",response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","新增失败，请确认！","",response);
		}
	}
	
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
