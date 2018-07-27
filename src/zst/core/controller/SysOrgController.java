package zst.core.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zst.core.entity.ChangeFieldForLog;
import zst.core.entity.ResultBean;
import zst.core.entity.SysOrg;
import zst.core.service.ISysOrgService;
import zst.core.service.ISysOrgUserService;
import zst.core.service.IVmsOrgAssetService;
import zst.core.service.datatree.SysDataHelper;
import zst.extend.common.Constant;
import zst.extend.enums.LogEnum;
import zst.extend.exception.PlatformException;
import zst.extend.page.PageBean;
import zst.extend.util.CollectionUtil;
import zst.extend.util.CommonUtil;
import zst.extend.util.LogUtil;
import zst.extend.util.PageUtil;
import zst.extend.util.PinyinUtil;

/**
 * 组织机构 controller
 * @author: liuyy
 * @date: 2017-6-6 上午10:53:14
 */
@Controller
@RequestMapping("/org")
public class SysOrgController {

	private static final Log logger = LogFactory.getLog(SysOrgController.class);
	
	
	@Resource
	private ISysOrgService sysOrgService;
	
	@Resource
	private ISysOrgUserService orgUserService;
	
	@Resource
	private IVmsOrgAssetService orgAssetService;
	
	@Resource
	private SysDataHelper dataHelper;
	
	/**打开组织机构页面 **/
	@RequestMapping("/openOrgManage")
	public String openOrgManage(Model model, String funtId) throws Exception {
		model.addAttribute("funtId", funtId);
		return "sys/org/treeOrg";
	}
	
	/**
	 * 查询组织机构
	 * @param model
	 * @param sysOrg
	 * @param orgParName
	 * @param orgParLevel
	 * @param funtId
	 * @param rows
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryOrgs")
	public String queryOrgs(Model model, SysOrg sysOrg, Integer funtId, String rows,String page, String containBranch ,HttpServletRequest request) throws Exception {
		try {
			int validStatus = 0;
			List<SysOrg> orgList = null;
			long totalRows = 0L;
			//分为是否包含子组织 ，两种情况
			if (containBranch==null) {
				//查询本节点及其子节点 不勾选
				List<SysOrg> childs = sysOrgService.selectChildsByParId(sysOrg.getParentId());
				List<Integer> currentNode = new ArrayList<Integer>();
				//加入页面左侧点击到的节点
				currentNode.add(sysOrg.getParentId());
				if (CollectionUtil.isNotEmpty(childs)) {
					for (SysOrg org : childs) {
						currentNode.add(org.getOrgId());
					}
				}
				SysOrg queryOrg = new SysOrg();
				queryOrg.setPage(rows, page);
				queryOrg.setStatus(validStatus);//设置查询可用的组织机构
//				queryOrg.setIsVisible(true);
				queryOrg.setChildIds(currentNode);
				queryOrg.setKeywords(sysOrg.getKeywords());
				totalRows = sysOrgService.selectContainCountByObj(queryOrg);
				orgList = sysOrgService.selectContainListByObj(queryOrg);
			} else {
				if (sysOrg.getParentId()!=null) {
					//获取所有可查询的组织
					SysOrg caseOrg = new SysOrg();
					caseOrg.setStatus(validStatus);
//					caseOrg.setIsVisible(true);
					List<SysOrg> allOrgList = sysOrgService.selectListByObj(caseOrg);
					if (CollectionUtil.isNotEmpty(allOrgList)) {
						List<Integer> allChildId = new ArrayList<Integer>();
						allChildId = getAllChildren(allOrgList, sysOrg.getParentId(), allChildId);
						allChildId.add(sysOrg.getParentId());
						SysOrg queryOrg = new SysOrg();
						queryOrg.setPage(rows, page);
						queryOrg.setStatus(validStatus);//设置查询可用的组织机构
						queryOrg.setIsVisible(true);
						queryOrg.setChildIds(allChildId);
						queryOrg.setKeywords(sysOrg.getKeywords());
						totalRows = sysOrgService.selectContainCountByObj(queryOrg);
						orgList = sysOrgService.selectContainListByObj(queryOrg);
					}
				}
			}
			PageBean myPage = new PageBean();
			myPage.setPageSize(rows);
			myPage.setPageNum(page);
			myPage.setTotalRows(totalRows);
			model.addAttribute("list",orgList);
			model.addAttribute("myPage", myPage);
			model.addAttribute("containBranch", containBranch);
			model.addAttribute("parentId", sysOrg.getParentId());
			model.addAttribute("funtId", funtId);
			//处理所用有的组织按钮权限
			Map<Integer, List<String>> map = (Map<Integer, List<String>>) request.getSession().getAttribute(Constant.USER_BUTTON);
			if (map != null) {
				List<String> list = map.get(funtId+"");
				JSONArray fromObject = JSONArray.fromObject(list);
				model.addAttribute(Constant.BUTTON_LIST, fromObject.toString());
			}
			return "sys/org/listOrg";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 采用递归算法获取某个节点下所有的子节点
	 * @param orgList
	 * @param parentId
	 * @param children
	 * @return
	 */
	private List<Integer> getAllChildren(List<SysOrg> orgList, Integer parentId, List<Integer> children) {
		for (SysOrg org : orgList) {
			if (org.getParentId().equals(parentId)) {
				getAllChildren(orgList, org.getOrgId(), children);
				children.add(org.getOrgId());
			}
		}
		return children;
	}
	
	/**
	 * 专门用于模糊查询的action
	 * @param model
	 * @param sysOrg
	 * @param orgParName
	 * @param orgParLevel
	 * @param rows
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryFuzzyOrgs")
	public String queryFuzzyOrgs(Model model, SysOrg sysOrg, String orgParName, String orgParLevel, String rows,String page,Integer funtId, String containBranch, HttpServletRequest request) throws Exception {
		try {
			//先查父节点
			sysOrg.setPage(rows, page);
			sysOrg.setStatus(0);//设置查询可用的组织机构
			List<SysOrg> orgList = null;
			long totalRows = sysOrgService.selectCountByObj(sysOrg);
			if(totalRows >0) {
				orgList = sysOrgService.selectListByObj(sysOrg);
			}
			PageBean myPage = new PageBean();
			myPage.setPageSize(rows);
			myPage.setPageNum(page);
			myPage.setTotalRows(totalRows);
			model.addAttribute("list",orgList);
			model.addAttribute("myPage", myPage);
			model.addAttribute("parentId", sysOrg.getParentId());
			model.addAttribute("containBranch", containBranch);
			model.addAttribute("funtId", funtId);
			//处理所用有的组织按钮权限
			Map<Integer, List<String>> map = (Map<Integer, List<String>>) request.getSession().getAttribute(Constant.USER_BUTTON);
			if (map != null) {
				List<String> list = map.get(funtId+"");
				JSONArray fromObject = JSONArray.fromObject(list);
				model.addAttribute(Constant.BUTTON_LIST, fromObject.toString());
			}
			return "sys/org/listOrg";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 获取资源整合的组织机构树  非AD域的组织机构   ztree为数组形式
	 * @param response
	 * @param open
	 * @throws Exception
	 */
	@RequestMapping("/queryOrgTreeVisible")
	public void queryOrgTreeVisible(HttpServletResponse response, String open) throws Exception {
		try {
			String json = dataHelper.getOrgTreeVisible();
			PageUtil.toBeJsonByMap("true", "",json, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 进入组织机构管理  将设备关联到 
	 * @param response
	 * @param open
	 * @throws Exception
	 */
	@RequestMapping("/queryOrgTree")
	public void queryOrgTree(HttpServletResponse response, String open) throws Exception {
		try {
			String json = dataHelper.getOrgTree();
			PageUtil.toBeJsonByMap("true", "",json, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 进入用户管理下的组织机构管理
	 * @param response
	 * @param open
	 * @throws Exception
	 */
	@RequestMapping("/queryOrgTreeForUser")
	public void queryOrgTreeForUser(HttpServletResponse response, String open) throws Exception {
		try {
			String json = dataHelper.getOrgTreeForUser();
			PageUtil.toBeJsonByMap("true", "",json, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 进入设备管理下的组织机构管理
	 * @param response
	 * @param open
	 * @throws Exception
	 */
	@RequestMapping("/queryOrgTreeForAsset")
	public void queryOrgTreeForAsset(HttpServletResponse response, String open) throws Exception {
		try {
			String json = dataHelper.getOrgTreeForAsset();
			PageUtil.toBeJsonByMap("true", "",json, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 获取组织、设备树
	 * @param response
	 * @param open
	 * @throws Exception
	 */
	@RequestMapping("/queryOrgEquTree")
	public void queryOrgEquTree(HttpServletResponse response, String open) throws Exception {
		try {
			//设置节点是否展开,默认打开
			boolean openTree = true;
			if(StringUtils.isNotEmpty(open)) {
				openTree = Boolean.parseBoolean(open);
			}
			SysOrg org = new SysOrg();
			org.setStatus(0);//设置查询可用的组织机构
			List<SysOrg> orgList = sysOrgService.selectListOrgTree(org);
			if (orgList.size() > 0) {
				// 遍历list
				JSONArray trees = new JSONArray();
				org = orgList.get(0);
				JSONObject root = new JSONObject();
				root.put("id", org.getOrgId());
				root.put("name", org.getOrgName());
				root.put("level", org.getOrgLevel());
				root.put("open", openTree);
				querySonTree(root, org.getOrgId(), orgList, openTree);
				trees.add(root);
				PageUtil.writeDataToClient(trees,response);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}

	/**
	 * 递归查询是否存在子项
	 * @param fNode
	 * @param id
	 * @param list
	 * @throws Exception
	 */
	private void querySonTree(JSONObject fNode, Integer id, List<SysOrg> list, boolean openTree) throws Exception {
		List<SysOrg> orgList = new ArrayList<SysOrg>();
		for(int i = 0; i < list.size(); i++) {
			SysOrg org = list.get(i);
			if (id.equals(org.getParentId())) {
				orgList.add(org);
			}
		}
		if(orgList.size() > 0) {
			JSONArray childs = new JSONArray();
			for (int i = 0; i < orgList.size(); i++) {
				SysOrg org = orgList.get(i);
				JSONObject node = new JSONObject();
				node.put("id", org.getOrgId());
				node.put("name", org.getOrgName());
				node.put("level", org.getOrgLevel());
				node.put("open", openTree);
				querySonTree(node, org.getOrgId(), list, openTree);
				childs.add(node);
			}
			fNode.put("children", childs);
		}
	}
	
	/**打开组织机构操作窗口 **/
	@RequestMapping("/openOperateOrg")
	public String openOperateOrg(Model model, SysOrg sysOrg, String funtType) throws Exception {
		if("edit".equals(funtType) || "view".equals(funtType) || "copy".equals(funtType)) {
			//编辑时获取父类节点名
			
			sysOrg = sysOrgService.selectByPrimaryKey(sysOrg.getOrgId());
			if("copy".equals(funtType)) {
				sysOrg.setOrgId(null);
				sysOrg.setOrgName(null);
				sysOrg.setOrgId(null);
				sysOrg.setDescription(null);
			}
		}
		String orgParName = null;
		SysOrg orgPar = sysOrgService.selectByPrimaryKey(sysOrg.getParentId());
		if(orgPar != null)//跟节点没有父节点名
			orgParName = orgPar.getOrgName();
		model.addAttribute("sysOrg", sysOrg);
		model.addAttribute("orgParName", orgParName);
		model.addAttribute("funtType", funtType);
		return "sys/org/operateOrg";
	}
	
	/**新增组织机构 **/
	@RequestMapping("/addOrg")
	public void addOrg(SysOrg sysOrg, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			Integer userId = CommonUtil.getSessionUser(request).getUserId();
			ResultBean resultBean = sysOrgService.insertTransactionSelective(sysOrg, userId);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.ORG_MODULE, LogEnum.OperatorType.INSERTOPERATION, "添加组织机构："+sysOrg.getOrgName());
			PageUtil.toBeJsonByMap(String.valueOf(resultBean.isFlag()),resultBean.getFeedBack(),resultBean.getPrimaryKey(),response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","新增失败，请确认！","",response);
		}
	}
	
	/**验证组织机构名是否重复 **/
	@RequestMapping("/validateOrgName")
	public void validateOrgName(SysOrg sysOrg, HttpServletResponse response) throws Exception {
		sysOrg.setStatus(0);
		long total = sysOrgService.selectCountByObj(sysOrg);
		if(total>0) {
			PageUtil.toBeJsonByMap("false","","",response);
			return;
		}
		PageUtil.toBeJsonByMap("true","","",response);
	}
	
	/**
	 * 编辑组织机构
	 * @param sysOrg
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/modifyOrg")
	public void modifyOrg(SysOrg sysOrg, ChangeFieldForLog changeFieldForLog, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			//后台添加首字母
			if(sysOrg.getOrgName()!=null){
				sysOrg.setPinyin(PinyinUtil.getPinYinHeadChar(sysOrg.getOrgName(), 2));
			}
			//如果增加的名称是英文，则按照原来添加的名字赋值，并转成大写
			if (sysOrg.getPinyin()== null || "".equals(sysOrg.getPinyin().trim())) {
				sysOrg.setPinyin(sysOrg.getOrgName().toUpperCase());
			}
			sysOrgService.updateByPrimaryKeySelective(sysOrg);
			//写入日志
			//拼接变动
			StringBuilder action = new StringBuilder();
			action.append("修改组织机构->"+changeFieldForLog.getOldObjName());
			if (CommonUtil.notEqualString(changeFieldForLog.getOldObjName(), sysOrg.getOrgName())) {
				action.append("名称为："+sysOrg.getOrgName()+"~");
			}
			if (CommonUtil.notEqualInteger(changeFieldForLog.getOldLevel(), sysOrg.getZoom())) {
				action.append("级别由"+changeFieldForLog.getOldLevel()+"改为："+sysOrg.getZoom()+"~");
			}
			if (CommonUtil.notEqualDouble(changeFieldForLog.getOldLongitude(), sysOrg.getLongitude())) {
				action.append("经度由"+changeFieldForLog.getOldLongitude()+"改为："+sysOrg.getLongitude()+"~");
			}
			if (CommonUtil.notEqualDouble(changeFieldForLog.getOldLatitude(), sysOrg.getLatitude())) {
				action.append("纬度由"+changeFieldForLog.getOldLatitude()+"改为："+sysOrg.getLatitude()+"~");
			}
			if (CommonUtil.notEqualInteger(changeFieldForLog.getOldOrderNum(), sysOrg.getOrderNum())) {
				action.append("节点排序由"+changeFieldForLog.getOldOrderNum()+"改为："+sysOrg.getOrderNum()+"~");
			}
			if (CommonUtil.notEqualString(changeFieldForLog.getOldDescription(), sysOrg.getDescription())) {
				action.append("备注由"+changeFieldForLog.getOldDescription()+"改为："+sysOrg.getDescription()+"~");
			}
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.ORG_MODULE, LogEnum.OperatorType.UPDATEOPERATION, action.toString());
			PageUtil.toBeJsonByMap("true","","",response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","编辑失败，请确认！","",response);
		}
	}
	
	/**
	 * 批量删除sysOrg(逻辑删除,更新状态) 
	 * @param orgIds
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/deleteBatch")
	public void deleteBatch(@RequestParam(value = "orgIds[]")Integer[] orgIds, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			List<Integer> orgIdList = Arrays.asList(orgIds);
			
			//判断是否存在子节点
			long totalOrg = sysOrgService.selectChildCount(orgIdList);
			if (totalOrg > 0) {
				PageUtil.toBeJsonByMap("false","不能删除非叶子节点，请确认！","",response);
				return;
			}
			//判断是否挂载的有用户
			long totalUser = orgUserService.selectCountByOrg(orgIdList);
			if (totalUser > 0) {
				PageUtil.toBeJsonByMap("false","不能删除存有员工的单位，请确认！","",response);
				return;
			}
			long totalAsset = orgAssetService.selectCountByOrg(orgIdList);
			if (totalAsset > 0) {
				PageUtil.toBeJsonByMap("false","不能删除存有设备的单位，请确认！","",response);
				return;
			}
			//查询删除的组织机构的名称
			List<SysOrg> deletedOrgs = sysOrgService.selectListOrgByIds(orgIdList);
			StringBuilder builder = new StringBuilder();//非线程安全 快
			if (CollectionUtil.isNotEmpty(deletedOrgs)) {
				String separator = "，";
				for (int i=0; i<deletedOrgs.size(); i++) {
					builder.append(deletedOrgs.get(i).getOrgName());
					if (i<deletedOrgs.size()-1) {
						builder.append(separator);
					}
				}
			}
			sysOrgService.deleteBatchByOrgIds(orgIdList);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.DELETE, LogEnum.OperatorModule.ORG_MODULE, LogEnum.OperatorType.LOGICALDELOPERATION, "删除组织机构："+builder.toString());
			PageUtil.toBeJsonByMap("true","删除成功！","",response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","删除失败，请确认！","",response);
		}
	}
	
	/**获取组织机构树(只获取两级) **/
	@RequestMapping("/queryOrgTreeTwo")
	public void queryOrgTreeTwo(HttpServletResponse response, String open) throws Exception {
		try {
			//设置节点是否展开,默认打开
			boolean openTree = true;
			if(StringUtils.isNotEmpty(open)) {
				openTree = Boolean.parseBoolean(open);
			} 
			SysOrg org = new SysOrg();
			org.setStatus(0);//设置查询可用的组织机构
			List<SysOrg> orgList = sysOrgService.selectListOrgTree(org);
			if (orgList.size() > 0) {
				// 遍历list
				JSONArray trees = new JSONArray();
				org = orgList.get(0);
				JSONObject root = new JSONObject();
				root.put("id", org.getOrgId());
				root.put("name", org.getOrgName());
				root.put("level", org.getOrgLevel());
				root.put("open", openTree);
				querySonTreetwo(root, org.getOrgId(), orgList, openTree);
				trees.add(root);
				PageUtil.writeDataToClient(trees,response);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 设置组织结构可见性
	 * @param orgIds
	 * @param visibleOrg
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/setOrgVisible")
	public void setOrgVisible(@RequestParam(value = "orgIds[]")Integer[] orgIds, Integer visibleOrg, HttpServletResponse response, HttpServletRequest request) throws Exception { 
		try{
			if(visibleOrg != null){
				Boolean visibleFlag = visibleOrg==1? true : false;
				//设置组织及其下边设备的可见性
				sysOrgService.updateBatchOrgVisible(orgIds, visibleFlag);
			}
			StringBuilder builder = new StringBuilder();
			if (orgIds!=null && orgIds.length>0) {
				List<Integer> asList = Arrays.asList(orgIds);
				List<SysOrg> orgs = sysOrgService.selectListOrgByIds(asList);
				if (CollectionUtil.isNotEmpty(orgs)) {
					builder.append("将组织机构");
					String separator = "，";
					for (int i=0; i<orgs.size(); i++) {
						builder.append(orgs.get(i).getOrgName());
						if (i<orgs.size()-1) {
							builder.append(separator);
						}
					}
					String visible = visibleOrg==1? "可见" : "不可见";
					builder.append("设置为"+visible);
				}
			}
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.ORG_MODULE, LogEnum.OperatorType.UPDATEOPERATION, builder.toString());
			PageUtil.toBeJsonByMap("true","设置成功！","",response);
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","设置失败，请确认！","",response);
		}
	}
	
	private void querySonTreetwo(JSONObject fNode, Integer id, List<SysOrg> list, boolean openTree) throws Exception {
		List<SysOrg> orgList = new ArrayList<SysOrg>();
		for(int i = 0; i < list.size(); i++) {
			SysOrg org = list.get(i);
			if (id.equals(org.getParentId())) {
				orgList.add(org);
			}
		}
		if(orgList.size() > 0) {
			JSONArray childs = new JSONArray();
			for (int i = 0; i < orgList.size(); i++) {
				SysOrg org = orgList.get(i);
				JSONObject node = new JSONObject();
				node.put("id", org.getOrgId());
				node.put("name", org.getOrgName());
				node.put("level", org.getOrgLevel());
				node.put("open", openTree);
				childs.add(node);
			}
			fNode.put("children", childs);
		}
	}
	
}
