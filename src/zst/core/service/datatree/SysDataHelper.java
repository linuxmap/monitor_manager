package zst.core.service.datatree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zst.core.controller.SysRoleController;
import zst.core.dao.SysFuntMapper;
import zst.core.dao.SysOrgMapper;
import zst.core.dao.SysRoleMapper;
import zst.core.dao.SysUserAssetMapper;
import zst.core.dao.SysUserOrgMapper;
import zst.core.dao.SysUserRoleMapper;
import zst.core.dao.VRoleTreeOrgAssetMapper;
import zst.core.dao.VmsRoleAssetMapper;
import zst.core.dao.VmsRoleOrgMapper;
import zst.core.entity.ResultBean;
import zst.core.entity.SysFunt;
import zst.core.entity.SysOrg;
import zst.core.entity.SysRole;
import zst.core.entity.SysUserAsset;
import zst.core.entity.SysUserOrg;
import zst.core.entity.SysUserRole;
import zst.core.entity.VRoleTreeOrgAsset;
import zst.core.entity.VmsRoleAsset;
import zst.core.entity.VmsRoleOrg;
import zst.extend.util.CollectionUtil;
import zst.extend.util.CommonUtil;
import zst.extend.util.StringUtil;

@Service
@Transactional
public class SysDataHelper {
	
	private static final Log logger = LogFactory.getLog(SysDataHelper.class);
	
	//组织的页面展示
	private static final String orgZtreeSlogan = "_";
	//设备的页面展示
	private static final String vassetZtreeSlogan = "__";
	
	//组织机构类型：1组织机构，2区域，4收藏夹
	private static final Integer ORGNIZATION = 1;
	private static final Integer AREA = 2;
	private static final Integer COLLCETION = 4;
	
	//定义组织机构的来源    0 自定义，1 海康，2 大华, 3 宇视，4 讯对讲，5 重大作业，6 AD域
//	private static final Integer SELF_DEFINE = 0;
//	private static final Integer HAIKANG = 1;
//	private static final Integer DAHUA = 2;
//	private static final Integer YUSHI = 3;
//	private static final Integer XUNDUIJIANG = 4;
	public static final Integer ENORMOUS_WORK = 5;
	private static final Integer AD_AREA = 6;
	
	private static final String ORG = "org";//组织机构 AD域
	private static final String DIRECTORY = "directory";//收藏夹
	private static final String ENOWORK = "enormouswork";//重大作业
	
	//可见不可见 11 12 14 21 22 24
	private static final String ONE_ONE = "ztree"+1+orgZtreeSlogan+1;//可见重大作业
	private static final String ONE_TWO = "ztree"+2+orgZtreeSlogan+1;//不可见重大作业
	private static final String ONE_FOUR = "ztree"+1+orgZtreeSlogan+2;//可见组织机构
	private static final String TWO_ONE = "ztree"+2+orgZtreeSlogan+2;//不可见组织机构
	private static final String TWO_TWO = "ztree"+1+orgZtreeSlogan+4;//可见收藏夹
	private static final String TWO_FOUR = "ztree"+2+orgZtreeSlogan+4;//不可见收藏夹
	
	//start用于数据库map作为条件的查询
	private static final String ROLE_ID = "roleid";//角色id
	//private static final String PLUS_ORG = "plusorg";//用户新增的组织权限
	//private static final String MINUS_ORG = "minusorg";//用户减少的组织权限
	//private static final String PLUS_ASSET = "plusasset";//用户新增的设备权限 设备
	//private static final String MINUS_ASSET = "minusasset";//用户减少的设备权限 设备
	//private static final String PLUS_ASSETORG = "plusassetorg";//用户新增的设备权限 部门
	//private static final String MINUS_ASSETORG = "minusassetorg";//用户减少的设备权限 部门
	//end用于数据库map作为条件的查询
	
	@Resource
	private SysOrgMapper orgMapper;
	
	@Resource
	private VRoleTreeOrgAssetMapper roleTreeOrgAssetMapper;
	
	@Resource
	private SysFuntMapper funtMapper;
	
	@Resource
	private SysRoleMapper roleMapper;
	
	@Resource
	private SysUserRoleMapper userRoleMapper;
	
	@Resource
	private VmsRoleOrgMapper roleOrgMapper;
	
	@Resource
	private VmsRoleAssetMapper roleAssetMapper;
	
	@Resource
	private SysUserOrgMapper userOrgMapper;
	
	@Resource
	private SysUserAssetMapper userAssetMapper;
	
	//应客户端需求，将重大作业作为虚拟的节点
	private static final int ENORMOUSWORK_ID = -5;
	private static final String ENORMOUSWORK_NAME = "重大作业(集合)";
	private static final int ENORMOUSWORK_PID = -1;
	
	/**
	 * 获取组织机构树  显示所有组织   ztree为数组形式
	 * @return
	 */
	public String getOrgTree(){
		try {
			SysOrg org = new SysOrg();
			//org.setIsVisible(true); //20180321修改
			org.setStatus(0);//设置查询可用的组织机构
			List<SysOrg> orgList = orgMapper.selectListOrgTree(org);
			if(CollectionUtil.isNotEmpty(orgList)){
				for(SysOrg sysorg :orgList){
					sysorg.setOrgLevel(1);//1部门 2资产
					if (sysorg.getOrgSource()!=null && ENORMOUS_WORK.intValue() == sysorg.getOrgSource().intValue()) {
						sysorg.setDescription(orgZtreeSlogan+ENOWORK);//重大作业
						//归结到重大作业节点
						sysorg.setParentId(ENORMOUSWORK_ID);
					} else if((sysorg.getType()!=null && ORGNIZATION.intValue() == sysorg.getType().intValue()) || (sysorg.getType()!=null && AREA.intValue() == sysorg.getType().intValue())) {
						sysorg.setDescription(orgZtreeSlogan+ORG);//组织机构
					} else if(sysorg.getType()!=null && COLLCETION.intValue() == sysorg.getType().intValue()) {
						sysorg.setDescription(orgZtreeSlogan+DIRECTORY);//收藏夹
					}
				}
				//加入重大作业节点
				SysOrg enormousWork = new SysOrg();
				enormousWork.setOrgId(ENORMOUSWORK_ID);
				enormousWork.setParentId(ENORMOUSWORK_PID);
				enormousWork.setOrgName(ENORMOUSWORK_NAME);
				enormousWork.setOrgLevel(1);
				enormousWork.setDescription(orgZtreeSlogan+ENOWORK);
				orgList.add(enormousWork);
				//第二种方式
				JsonConfig cfg = new JsonConfig();
				String[] EXCLUDES = new String[]{"orderNum","createTime","updateTime","creatorId","status","orgCode","haveDevice","haveChild","isVisible","zoom","longitude","latitude","type","orgSource","encoding","pinyin","pagestart","pageend","keywords"};
				cfg.setExcludes(EXCLUDES);
				JSONArray jsonArray = JSONArray.fromObject(orgList,cfg);
				String json1 = jsonArray.toString();
				json1 = json1.replaceAll("orgId", "id").replaceAll("parentId", "pId").replaceAll("orgName", "name").replaceAll("orgLevel", "type").replaceAll("description", "iconSkin");
				return json1;
				//注释掉此种方式  第一种
//				StringBuilder sb = new StringBuilder();
//				for(SysOrg o : orgList){
//					sb.append("{id:'"+o.getOrgId()+"',pId:'"+o.getParentId()+"',name:'"+o.getOrgName()+"'},");
//				}
//				//出去最后一个逗号
//				String str = sb.substring(0, sb.length()-1);
//				StringBuilder strbuff = new StringBuilder();
//				strbuff.append("[");
//				strbuff.append(str);
//				strbuff.append("]");
//				return strbuff.toString();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 1/获取用户管理的组织机构树 ，显示非重大作业    ztree为数组形式  2/用于角色管理下的分配用户
	 * @return
	 */
	public String getOrgTreeForUser(){
		try {
			//虚拟根节点
			SysOrg virtualRoot = new SysOrg();
			virtualRoot.setParentId(Integer.MIN_VALUE);
			virtualRoot.setOrgId(-1);
			virtualRoot.setOrgName("用户组织机构");
			
			
			SysOrg org = new SysOrg();
			//org.setIsVisible(true);
			org.setStatus(0);//设置查询可用的组织机构
			List<SysOrg> allOrgList = orgMapper.selectListOrgTree(org);
			List<SysOrg> orgList = new ArrayList<SysOrg>();
			if(CollectionUtil.isNotEmpty(allOrgList)){
				for(SysOrg sysorg :allOrgList){
					sysorg.setOrgLevel(1);//1部门 2资产
					if (sysorg.getOrgSource()!=null && ENORMOUS_WORK.intValue() == sysorg.getOrgSource().intValue()) {
						sysorg.setDescription(orgZtreeSlogan+ENOWORK);//重大作业
					} else if((sysorg.getType()!=null && ORGNIZATION.intValue() == sysorg.getType().intValue()) || (sysorg.getType()!=null && AREA.intValue() == sysorg.getType().intValue())) {
						sysorg.setDescription(orgZtreeSlogan+ORG);//组织机构
					} else if(sysorg.getType()!=null && COLLCETION.intValue() == sysorg.getType().intValue()) {
						sysorg.setDescription(orgZtreeSlogan+DIRECTORY);//收藏夹
					}
					//显示所有非重大作业
					if (sysorg.getOrgSource()!=null && (ENORMOUS_WORK.intValue() == sysorg.getOrgSource().intValue())) {
						continue;
					} else {
						orgList.add(sysorg);
						
					}
					//添加虚拟根节点到集合
				}
				orgList.add(virtualRoot);
				//第二种方式
				JsonConfig cfg = new JsonConfig();
				String[] EXCLUDES = new String[]{"orderNum","createTime","updateTime","creatorId","status","orgCode","haveDevice","haveChild","isVisible","zoom","longitude","latitude","type","orgSource","encoding","pinyin","pagestart","pageend","keywords"};
				cfg.setExcludes(EXCLUDES);
				JSONArray jsonArray = JSONArray.fromObject(orgList,cfg);
				String json1 = jsonArray.toString();
				json1 = json1.replaceAll("orgId", "id").replaceAll("parentId", "pId").replaceAll("orgName", "name").replaceAll("orgLevel", "type").replaceAll("description", "iconSkin");
				return json1;
				//注释掉此种方式  第一种
//				StringBuilder sb = new StringBuilder();
//				for(SysOrg o : orgList){
//					sb.append("{id:'"+o.getOrgId()+"',pId:'"+o.getParentId()+"',name:'"+o.getOrgName()+"'},");
//				}
//				//出去最后一个逗号
//				String str = sb.substring(0, sb.length()-1);
//				StringBuilder strbuff = new StringBuilder();
//				strbuff.append("[");
//				strbuff.append(str);
//				strbuff.append("]");
//				return strbuff.toString();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 设备管理查询组织 工具类
	 * @return
	 */
	public List<SysOrg> getOrgList() {
		List<SysOrg> orgList = new ArrayList<SysOrg>();
		try {
			SysOrg org = new SysOrg();
			//org.setIsVisible(true);
			org.setStatus(0);//设置查询可用的组织机构
			List<SysOrg> orgAllList = orgMapper.selectListOrgTree(org);
			if (CollectionUtil.isNotEmpty(orgAllList)) {
				for (SysOrg sysOrg : orgAllList) {
					sysOrg.setOrgLevel(1);//1部门 2资产
					if (sysOrg.getOrgSource()!=null && ENORMOUS_WORK.intValue() == sysOrg.getOrgSource().intValue()) {
						sysOrg.setDescription(orgZtreeSlogan+ENOWORK);//重大作业
					} else if((sysOrg.getType()!=null && ORGNIZATION.intValue() == sysOrg.getType().intValue()) || (sysOrg.getType()!=null && AREA.intValue() == sysOrg.getType().intValue())) {
						sysOrg.setDescription(orgZtreeSlogan+ORG);//组织机构
					} else if(sysOrg.getType()!=null && COLLCETION.intValue() == sysOrg.getType().intValue()) {
						sysOrg.setDescription(orgZtreeSlogan+DIRECTORY);//收藏夹
					}
					//显示所有非重大作业和非AD域的组织机构
					if (sysOrg.getOrgSource()!=null && ((AD_AREA.intValue() == sysOrg.getOrgSource().intValue()) || (ENORMOUS_WORK.intValue() == sysOrg.getOrgSource().intValue()))) {
						continue;
					} else {
						orgList.add(sysOrg);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return orgList;
	}
	
	/**
	 * 获取设备管理的组织机构树     ztree为数组形式
	 * @return
	 */
	public String getOrgTreeForAsset(){
		try {
			 List<SysOrg> orgList = getOrgList();
			if (CollectionUtil.isNotEmpty(orgList)) {
				//第二种方式
				JsonConfig cfg = new JsonConfig();
				String[] EXCLUDES = new String[]{"orderNum","createTime","updateTime","creatorId","status","orgCode","haveDevice","haveChild","isVisible","zoom","longitude","latitude","type","orgSource","encoding","pinyin","pagestart","pageend","keywords"};
				cfg.setExcludes(EXCLUDES);
				JSONArray jsonArray = JSONArray.fromObject(orgList,cfg);
				String json1 = jsonArray.toString();
				json1 = json1.replaceAll("orgId", "id").replaceAll("parentId", "pId").replaceAll("orgName", "name").replaceAll("orgLevel", "type").replaceAll("description", "iconSkin");
				return json1;
				//注释掉此种方式  第一种
//				StringBuilder sb = new StringBuilder();
//				for(SysOrg o : orgList){
//					sb.append("{id:'"+o.getOrgId()+"',pId:'"+o.getParentId()+"',name:'"+o.getOrgName()+"'},");
//				}
//				//出去最后一个逗号
//				String str = sb.substring(0, sb.length()-1);
//				StringBuilder strbuff = new StringBuilder();
//				strbuff.append("[");
//				strbuff.append(str);
//				strbuff.append("]");
//				return strbuff.toString();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取资源整合的组织机构树
	 * @return
	 */
	public String getOrgTreeVisible() {
		try {
			SysOrg org = new SysOrg();
			org.setStatus(0);//设置查询可用的组织机构
			List<SysOrg> orgAllList = orgMapper.selectListOrgTree(org);
			List<SysOrg> orgList = new ArrayList<SysOrg>();
			if (CollectionUtil.isNotEmpty(orgAllList)) {
				for (SysOrg sysOrg : orgAllList) {
					if (sysOrg.getOrgSource()!=null && (!(AD_AREA.intValue() == sysOrg.getOrgSource()))) {
						orgList.add(sysOrg);
					}
					//配置图标
					if (sysOrg.getType()!=null) {
						if (sysOrg.getOrgSource()!=null && sysOrg.getIsVisible() && sysOrg.getOrgSource().intValue()==ENORMOUS_WORK.intValue()) {//11
							sysOrg.setDescription(ONE_ONE);//可见重大作业
							//归结到重大作业节点
							sysOrg.setParentId(ENORMOUSWORK_ID);
						} else if (sysOrg.getOrgSource()!=null && !sysOrg.getIsVisible() && sysOrg.getOrgSource().intValue()==ENORMOUS_WORK.intValue()) {//12
							sysOrg.setDescription(ONE_TWO);//不可见重大作业
							//归结到重大作业节点
							sysOrg.setParentId(ENORMOUSWORK_ID);
						} else if (sysOrg.getIsVisible() && (sysOrg.getType().intValue()==ORGNIZATION.intValue() || sysOrg.getType().intValue()==AREA.intValue())) {//14
							sysOrg.setDescription(ONE_FOUR);//可见组织机构
						} else if (!sysOrg.getIsVisible() && (sysOrg.getType().intValue()==ORGNIZATION.intValue() || sysOrg.getType().intValue()==AREA.intValue())) {//21
							sysOrg.setDescription(TWO_ONE);//不可见组织机构
						} else if (sysOrg.getIsVisible() && (sysOrg.getType().intValue()==COLLCETION.intValue())) {//22
							sysOrg.setDescription(TWO_TWO);//可见收藏夹
						} else if (!sysOrg.getIsVisible() && sysOrg.getType().intValue()==COLLCETION.intValue()) {//24
							sysOrg.setDescription(TWO_FOUR);//不可见收藏夹
						}
					}
				}
				//将虚拟根节点添加进去
				SysOrg virtualRoot = new SysOrg();
				virtualRoot.setOrgId(-1);
				virtualRoot.setParentId(Integer.MIN_VALUE);
				virtualRoot.setOrgName("资源整合组织机构");
				orgList.add(virtualRoot);
				
				//加入重大作业节点
				SysOrg enormousWork = new SysOrg();
				enormousWork.setOrgId(ENORMOUSWORK_ID);
				enormousWork.setParentId(ENORMOUSWORK_PID);
				enormousWork.setOrgName(ENORMOUSWORK_NAME);
				enormousWork.setOrgLevel(1);
				enormousWork.setDescription(ONE_ONE);
				orgList.add(enormousWork);
			}
			if(CollectionUtil.isNotEmpty(orgList)){
				JsonConfig cfg = new JsonConfig();
				String[] EXCLUDES = new String[]{"orderNum","createTime","updateTime","creatorId","status","orgCode","haveDevice","haveChild","zoom","longitude","latitude","type","orgSource","encoding","pinyin","pagestart","pageend","keywords"};
				cfg.setExcludes(EXCLUDES);
				JSONArray jsonArray = JSONArray.fromObject(orgList,cfg);
				String json1 = jsonArray.toString();
				json1 = json1.replaceAll("orgId", "id").replaceAll("parentId", "pId").replaceAll("orgName", "name").replaceAll("orgLevel", "type").replace("description", "iconSkin");
				return json1;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 角色的权限范围调用，所有可见的组织机构和设备  
	 * @return
	 */
	public String getOrgAssetTree(){
		try {
			SysOrg org = new SysOrg();
			org.setStatus(0);//未被删除 对应数据库的deleteflag
			org.setIsVisible(true);
			List<SysOrg> orgList = orgMapper.selectListOrgTreeOrderId(org);
			VRoleTreeOrgAsset vRoleTreeOrgAsset = new VRoleTreeOrgAsset();
			vRoleTreeOrgAsset.setAssetorgvisible(true);//组织可见性
			vRoleTreeOrgAsset.setAssetstatus(0);//组织状态
			vRoleTreeOrgAsset.setDeletetatus(false);//设备正常
			vRoleTreeOrgAsset.setAssetvisible(true);//设备是否删除
			List<VRoleTreeOrgAsset> assetList = roleTreeOrgAssetMapper.selectListByObj(vRoleTreeOrgAsset);//查询视图
			if(CollectionUtil.isNotEmpty(orgList)){
				for(SysOrg sysorg :orgList){
					sysorg.setOrgLevel(1);//1部门 2资产
					//分辨组织节点
					if (sysorg.getOrgSource()!=null && ENORMOUS_WORK.intValue() == sysorg.getOrgSource().intValue()) {
						sysorg.setDescription(orgZtreeSlogan+ENOWORK);//重大作业
					} else if((sysorg.getType()!=null && ORGNIZATION.intValue() == sysorg.getType().intValue()) || (sysorg.getType()!=null && AREA.intValue() == sysorg.getType().intValue())) {
						sysorg.setDescription(orgZtreeSlogan+ORG);//组织机构
					} else if(sysorg.getType()!=null && COLLCETION.intValue() == sysorg.getType().intValue()) {
						sysorg.setDescription(orgZtreeSlogan+DIRECTORY);//收藏夹
					}
				}
			}
			
			//形成组织结构树形json1
			JsonConfig cfg = new JsonConfig();
			String[] EXCLUDES = new String[]{"orderNum","createTime","updateTime","creatorId","status","orgCode","haveDevice","haveChild","isVisible","zoom","longitude","latitude","type","encoding","pinyin","pagestart","pageend","keywords"};
			cfg.setExcludes(EXCLUDES);
			JSONArray jsonArray = JSONArray.fromObject(orgList,cfg);
			String json1 = jsonArray.toString();
			json1 = json1.replaceAll("orgId", "id").replaceAll("parentId", "pId").replaceAll("orgName", "name").replaceAll("orgLevel", "type").replaceAll("description", "iconSkin");
			
			//组装设备json2
			if(CollectionUtil.isNotEmpty(assetList)){
				for(VRoleTreeOrgAsset vAsset : assetList){
					vAsset.setZtreeDisplay(vassetZtreeSlogan+vAsset.getDeviceTypeId());
				}
			}
			jsonArray = JSONArray.fromObject(assetList);
			String json2 = jsonArray.toString();
			json2 = json2.replaceAll("assetUUID", "id").replaceAll("assetId", "dbassetId").replaceAll("assetOrgid", "pId").replaceAll("assetName", "name").replace("ztreeDisplay", "iconSkin");
			
			//拼接zTreeNodes
			//json格式为[{}],因而需要去掉前后的[]，而且要加上，分隔符
			if(StringUtil.validateStr(json1)){
				json1 = json1.substring(0,json1.length()-1);
			}
			if(StringUtil.validateStr(json2)){
				json2 = json2.substring(1,json2.length());
			}
			json1 += ","+json2;
			
			return json1;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 异步获取组织-设备ztree树，2018-02-24修改，减少数据交互  并查处该角色的组织权限
	 * @param roleId
	 * @return
	 */
	public ResultBean getAsyncRootOrgAssetTree(Integer roleId) {
		try {
			ResultBean resultBean = new ResultBean();
			//0获取该角色的组织权限
			List<VmsRoleOrg> roleOrgList = roleOrgMapper.selectByRoleId(roleId);
			Set<Integer> orgPermissionSet = new HashSet<Integer>();
			if (CollectionUtil.isNotEmpty(roleOrgList)) {
				for (VmsRoleOrg roleOrg : roleOrgList) {
					orgPermissionSet.add(roleOrg.getOrgId());
				}
			}
			if (orgPermissionSet.size()==0) {
				//进入配置页面该角色没有任何组织权限，即第一次配置，标记下来
				resultBean.setFlag(true);
			}
			//1处理组织
			//Integer rootParentId = -1;//获取根节点，用于异步加载
			SysOrg org = new SysOrg();
			//org.setParentId(rootParentId);///组织机构伪异步加载
			org.setStatus(0);//未被删除 对应数据库的deleteflag
			org.setIsVisible(true);
			List<SysOrg> orgList = orgMapper.selectListOrgTreeOrderId(org);
			if (CollectionUtil.isNotEmpty(orgList)) {
				for (SysOrg sysorg :orgList) {
					sysorg.setOrgLevel(1);//1部门 2资产
					//分辨组织节点
					if (sysorg.getOrgSource()!=null && ENORMOUS_WORK.intValue() == sysorg.getOrgSource().intValue()) {
						sysorg.setDescription(orgZtreeSlogan+ENOWORK);//重大作业
						
						//客户端需求start
						sysorg.setParentId(ENORMOUSWORK_ID);
						//客户端需求end
					} else if((sysorg.getType()!=null && ORGNIZATION.intValue() == sysorg.getType().intValue()) || (sysorg.getType()!=null && AREA.intValue() == sysorg.getType().intValue())) {
						sysorg.setDescription(orgZtreeSlogan+ORG);//组织机构
					} else if(sysorg.getType()!=null && COLLCETION.intValue() == sysorg.getType().intValue()) {
						sysorg.setDescription(orgZtreeSlogan+DIRECTORY);//收藏夹
					}
					//在异步加载时，默认的根节点都设置为父节点，借用org的isVisible属性
					sysorg.setIsVisible(true);
					
					//判断是否有该组织的权限
					if (orgPermissionSet.contains(sysorg.getOrgId())) {
						sysorg.setZtreeCheckFlag(true);
					} else {
						sysorg.setZtreeCheckFlag(false);
					}
				}
				
				//客户端需求start
				//加入重大作业节点
				SysOrg enormousWork = new SysOrg();
				enormousWork.setOrgId(ENORMOUSWORK_ID);
				enormousWork.setParentId(ENORMOUSWORK_PID);
				enormousWork.setOrgName(ENORMOUSWORK_NAME);
//				enormousWork.setOrgLevel(1);
				enormousWork.setDescription(orgZtreeSlogan+ENOWORK);
				orgList.add(enormousWork);
				//客户端需求end
			}
			
			//形成组织结构树形json1
			JsonConfig cfg = new JsonConfig();
			String[] EXCLUDES = new String[]{"orderNum","createTime","updateTime","creatorId","status","orgCode","haveDevice","haveChild","zoom","longitude","latitude","type","encoding","pinyin","pagestart","pageend","keywords"};
			cfg.setExcludes(EXCLUDES);
			JSONArray jsonArray = JSONArray.fromObject(orgList,cfg);
			String json1 = jsonArray.toString();
			json1 = json1.replaceAll("orgId", "id").replaceAll("parentId", "pId").replaceAll("orgName", "name").replaceAll("orgLevel", "type").replaceAll("description", "iconSkin").replace("isVisible", "isParent").replace("ztreeCheckFlag", "checked");
			resultBean.setFeedBack(json1);
			return resultBean;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 根据父节点的id查询下面的组织或者设备
	 * @param parentId 组织的父id
	 * @param roleId
	 * @return
	 */
	public JSONArray getOrgAndAssetAsyncChildrenByParentId(Integer parentId,Integer roleId) {
		JSONArray jsonArray = new JSONArray();
		try {
			Integer orgLevelFlag = 1;//1部门 
			Integer assetLevelFlag = 2;//2资产
			//查询权限
			Set<Integer> roleOrgSet = new HashSet<Integer>();//角色对应的组织
			List<VmsRoleOrg> roleOrgList = roleOrgMapper.selectByRoleId(roleId);
			if (CollectionUtil.isNotEmpty(roleOrgList)) {
				for (VmsRoleOrg roleOrg : roleOrgList) {
					roleOrgSet.add(roleOrg.getOrgId());
				}
			}
			Set<Integer> roleAssetSet = new HashSet<Integer>();//角色对应的组织下的设备
			VmsRoleAsset vmsRoleAsset = new VmsRoleAsset();
			vmsRoleAsset.setRoleId(roleId);
			vmsRoleAsset.setOrgId(parentId);
			List<VmsRoleAsset> roleAssetList = roleAssetMapper.selectListByObj(vmsRoleAsset);
			if (CollectionUtil.isNotEmpty(roleAssetList)) {
				for (VmsRoleAsset roleAsset : roleAssetList) {
					roleAssetSet.add(roleAsset.getAssetId());
				}
			}
			String json1 = null;
			String json2 = null;
			//1封装组织机构的node信息
			SysOrg org = new SysOrg();
			org.setParentId(parentId);
			org.setStatus(0);//未被删除 对应数据库的deleteflag
			org.setIsVisible(true);
			List<SysOrg> orgChildrenList = orgMapper.selectListOrgTreeOrderId(org);
			if (CollectionUtil.isNotEmpty(orgChildrenList)) {
				for (SysOrg sysorg :orgChildrenList) {
					sysorg.setOrgLevel(orgLevelFlag);
					//分辨组织节点
					if (sysorg.getOrgSource()!=null && ENORMOUS_WORK.intValue() == sysorg.getOrgSource().intValue()) {
						sysorg.setDescription(orgZtreeSlogan+ENOWORK);//重大作业
					} else if((sysorg.getType()!=null && ORGNIZATION.intValue() == sysorg.getType().intValue()) || (sysorg.getType()!=null && AREA.intValue() == sysorg.getType().intValue())) {
						sysorg.setDescription(orgZtreeSlogan+ORG);//组织机构
					} else if(sysorg.getType()!=null && COLLCETION.intValue() == sysorg.getType().intValue()) {
						sysorg.setDescription(orgZtreeSlogan+DIRECTORY);//收藏夹
					}
					//在异步加载时，组织节点都为父节点，借用org的isVisible属性
					sysorg.setIsVisible(true);
					//该角色是否应该勾选
					if (roleOrgSet.contains(sysorg.getOrgId())) {
						sysorg.setZtreeCheckFlag(true);
					} else {
						sysorg.setZtreeCheckFlag(false);
					}
				}
				JsonConfig cfg = new JsonConfig();
				String[] EXCLUDES = new String[]{"orderNum","createTime","updateTime","creatorId","status","orgCode","haveDevice","haveChild","zoom","longitude","latitude","type","encoding","pinyin","pagestart","pageend","keywords"};
				cfg.setExcludes(EXCLUDES);
				JSONArray orgJsonArray = JSONArray.fromObject(orgChildrenList,cfg);
				json1 = orgJsonArray.toString();
				json1 = json1.replaceAll("orgId", "id").replaceAll("parentId", "pId").replaceAll("orgName", "name").replaceAll("orgLevel", "type").replaceAll("description", "iconSkin").replace("isVisible", "isParent").replace("ztreeCheckFlag", "checked");
			}
			//2封装设备的node信息
			VRoleTreeOrgAsset roleTreeOrgAsset = new VRoleTreeOrgAsset();
			roleTreeOrgAsset.setAssetorgvisible(true);//组织可见性
			roleTreeOrgAsset.setAssetstatus(0);//组织状态
			roleTreeOrgAsset.setDeletetatus(false);//设备正常
			roleTreeOrgAsset.setAssetvisible(true);//设备是否删除
			roleTreeOrgAsset.setAssetOrgid(parentId);
			List<VRoleTreeOrgAsset> assetList = roleTreeOrgAssetMapper.selectListByObj(roleTreeOrgAsset);
			if (CollectionUtil.isNotEmpty(assetList)) {
				for(VRoleTreeOrgAsset vAsset : assetList){
					vAsset.setType(assetLevelFlag);//数据库中也是这个值
					vAsset.setZtreeDisplay(vassetZtreeSlogan+vAsset.getDeviceTypeId());
					//该角色是否应该勾选
					if (roleAssetSet.contains(vAsset.getAssetId())) {
						vAsset.setZtreeCheckFlag(true);
					} else {
						vAsset.setZtreeCheckFlag(false);
					}
				}
				jsonArray = JSONArray.fromObject(assetList);
				json2 = jsonArray.toString();
				json2 = json2.replaceAll("assetUUID", "id").replaceAll("assetId", "dbassetId").replaceAll("assetOrgid", "pId").replaceAll("assetName", "name").replace("ztreeDisplay", "iconSkin").replace("ztreeCheckFlag", "checked");
			}
			//拼接zTreeNodes
			//json格式为[{}],因而需要去掉前后的[]，而且要加上，分隔符
			//组织和设备都有
			if (StringUtil.validateStr(json1) && StringUtil.validateStr(json2)) {
				json1 = json1.substring(0,json1.length()-1);
				json2 = json2.substring(1,json2.length());
				json1 += ","+json2;
				jsonArray = JSONArray.fromObject(json1);
			} else if (StringUtil.validateStr(json1) && !StringUtil.validateStr(json2)) {
				//只有组织
				jsonArray = JSONArray.fromObject(json1);
			} else if (!StringUtil.validateStr(json1) && StringUtil.validateStr(json2)) {
				//只有设备
				jsonArray = JSONArray.fromObject(json2);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return jsonArray;
	}
	
	/**
	 * 根据父节点的id查询下面的设备
	 * @param parentId 组织的父id
	 * @param roleId
	 * @return
	 */
	public JSONArray getAssetAsyncChildrenByParentId(Integer parentId,Integer roleId) {
		JSONArray jsonArray = new JSONArray();
		try {
			Integer assetLevelFlag = 2;//2资产
			//查询权限
			Set<Integer> roleOrgSet = new HashSet<Integer>();//角色对应的组织
			List<VmsRoleOrg> roleOrgList = roleOrgMapper.selectByRoleId(roleId);
			if (CollectionUtil.isNotEmpty(roleOrgList)) {
				for (VmsRoleOrg roleOrg : roleOrgList) {
					roleOrgSet.add(roleOrg.getOrgId());
				}
			}
			Set<Integer> roleAssetSet = new HashSet<Integer>();//角色对应的组织下的设备
			VmsRoleAsset vmsRoleAsset = new VmsRoleAsset();
			vmsRoleAsset.setRoleId(roleId);
			vmsRoleAsset.setOrgId(parentId);
			List<VmsRoleAsset> roleAssetList = roleAssetMapper.selectListByObj(vmsRoleAsset);
			if (CollectionUtil.isNotEmpty(roleAssetList)) {
				for (VmsRoleAsset roleAsset : roleAssetList) {
					roleAssetSet.add(roleAsset.getAssetId());
				}
			}
			String json2 = null;
			//2封装设备的node信息
			VRoleTreeOrgAsset roleTreeOrgAsset = new VRoleTreeOrgAsset();
			roleTreeOrgAsset.setAssetorgvisible(true);//组织可见性
			roleTreeOrgAsset.setAssetstatus(0);//组织状态
			roleTreeOrgAsset.setDeletetatus(false);//设备正常
			roleTreeOrgAsset.setAssetvisible(true);//设备是否删除
			roleTreeOrgAsset.setAssetOrgid(parentId);
			List<VRoleTreeOrgAsset> assetList = roleTreeOrgAssetMapper.selectListByObj(roleTreeOrgAsset);
			if (CollectionUtil.isNotEmpty(assetList)) {
				for(VRoleTreeOrgAsset vAsset : assetList){
					vAsset.setType(assetLevelFlag);//数据库中也是这个值
					vAsset.setZtreeDisplay(vassetZtreeSlogan+vAsset.getDeviceTypeId());
					//该角色是否应该勾选
					if (roleAssetSet.contains(vAsset.getAssetId())) {
						vAsset.setZtreeCheckFlag(true);
					} else {
						vAsset.setZtreeCheckFlag(false);
					}
				}
				jsonArray = JSONArray.fromObject(assetList);
				json2 = jsonArray.toString();
				json2 = json2.replaceAll("assetUUID", "id").replaceAll("assetId", "dbassetId").replaceAll("assetOrgid", "pId").replaceAll("assetName", "name").replace("ztreeDisplay", "iconSkin").replace("ztreeCheckFlag", "checked");
			}
			//拼接zTreeNodes
			//json格式为[{}],因而需要去掉前后的[]，而且要加上，分隔符
			//组织和设备都有
			if (StringUtil.validateStr(json2)) {
				//只有设备
				jsonArray = JSONArray.fromObject(json2);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return jsonArray;
	}
	
	/**
	 * 用户的权限范围调用
	 * @return
	 */
	public String getPersonalOrgAssetTree(Integer userId, Integer roleId) {
		try {
			SysOrg org = new SysOrg();
			org.setStatus(0);//未被删除 对应数据库的deleteflag
			org.setIsVisible(true);
			List<SysOrg> orgList = orgMapper.selectListOrgTreeOrderId(org);
			VRoleTreeOrgAsset vRoleTreeOrgAsset = new VRoleTreeOrgAsset();
			vRoleTreeOrgAsset.setAssetorgvisible(true);//组织可见性
			vRoleTreeOrgAsset.setAssetstatus(0);//组织状态
			vRoleTreeOrgAsset.setDeletetatus(false);//设备正常
			vRoleTreeOrgAsset.setAssetvisible(true);//设备是否删除
			//查询视图
			List<VRoleTreeOrgAsset> assetList = roleTreeOrgAssetMapper.selectListByObj(vRoleTreeOrgAsset);
			
			//用户仅有一个角色
			List<Integer> userRoleIds = new ArrayList<Integer>();
			userRoleIds.add(roleId);
			
			//查询用户的个性化设置
			List<SysUserOrg> userChangeOrgs = userOrgMapper.selectByUserId(userId);
			List<SysUserAsset> userChangeAssets = userAssetMapper.selectByUserId(userId);
			
			//设置查询条件  TODO plus一般是角色之外，minus一般是角色之内
			List<Integer> plusOrgIds = new ArrayList<Integer>();
			List<Integer> minusOrgIds = new ArrayList<Integer>();
			List<Integer> plusAssetIds = new ArrayList<Integer>();
			List<Integer> plusAssetOrgIds = new ArrayList<Integer>();//设备所属部门
			List<Integer> minusAssetIds = new ArrayList<Integer>();
			List<Integer> minusAssetOrgIds = new ArrayList<Integer>();//设备所属部门
			//筛选出条件
			if (CommonUtil.validateList(userChangeOrgs)) {
				for (SysUserOrg userOrg : userChangeOrgs) {
					if (userOrg.getFlag()) {
						plusOrgIds.add(userOrg.getOrgId());
					} else {
						minusOrgIds.add(userOrg.getOrgId());
					}
				}
			}
			if (CommonUtil.validateList(userChangeAssets)) {
				for (SysUserAsset userAsset : userChangeAssets) {
					if (userAsset.getFlag()) {
						plusAssetIds.add(userAsset.getAssetId());
						plusAssetOrgIds.add(userAsset.getOrgId());
					} else {
						minusAssetIds.add(userAsset.getAssetId());
						minusAssetOrgIds.add(userAsset.getOrgId());
					}
				}
			}
			//封装条件
			Map<String,Object> roleOrgConditionMap = new HashMap<String, Object>();//角色-组织
			Map<String,Object> roleAssetConditionMap = new HashMap<String, Object>();//角色-设备
			//组织放入条件  ，只查角色
			roleOrgConditionMap.put(ROLE_ID, userRoleIds);
			
			//设备放入条件，只查角色
			roleAssetConditionMap.put(ROLE_ID, userRoleIds);
			//获取角色(角色可能为多个)下的部门和设备权限  没有分页
			List<VmsRoleOrg> roleOrgList = roleOrgMapper.selectListByPersonalizeMap(roleOrgConditionMap);
			//存储角色下对应的组织的id的容器
			List<Integer> roleOrgIdList = new ArrayList<Integer>();
			if (CollectionUtil.isNotEmpty(roleOrgList)) {
				for(VmsRoleOrg roleOrg : roleOrgList) {
					roleOrgIdList.add(roleOrg.getOrgId());
				}
			}
			//新增的组织权限
			if (roleOrgList!=null && CollectionUtil.isNotEmpty(plusOrgIds)) {
				VmsRoleOrg plusOrg;
					for (Integer orgId : plusOrgIds) {
						//TODO 已有的就不包含了 加上if判断  未设置主键id
						plusOrg = new VmsRoleOrg();
						//plusOrg.setRoleId(roleId);//作为展示用不到角色id
						plusOrg.setOrgId(orgId);
						plusOrg.setAlreadyChanged(true);
						roleOrgList.add(plusOrg);
					}
			}
			//减少组织处理  剔除页面被取消的设备数据
			List<VmsRoleOrg> showRoleOrg = new ArrayList<VmsRoleOrg>();
			if (CollectionUtil.isNotEmpty(roleOrgList) && CollectionUtil.isNotEmpty(minusOrgIds)) {
				for (int h=0; h<minusOrgIds.size(); h++) {
					for (VmsRoleOrg rg : roleOrgList) {
						if (minusOrgIds.get(h).intValue() == rg.getOrgId().intValue()) {
							showRoleOrg.add(rg);
							break;
						}
					}
				}
				roleOrgList.removeAll(showRoleOrg);
			}
			
			String separator = "_";//设备的分隔符
			
			//查询角色下对应的设备权限  只有一个条件
			List<VmsRoleAsset> roleAssetList = roleAssetMapper.selectListByPersonalizeMap(roleAssetConditionMap);
			//存储角色下对应的设备的id的容器
			List<String> roleAssetIdList = new ArrayList<String>();
			if (CollectionUtil.isNotEmpty(roleAssetList)) {
				for(VmsRoleAsset roleAsset : roleAssetList) {
					roleAssetIdList.add(roleAsset.getAssetId() + separator + roleAsset.getOrgId());
				}
			}
			//新增和减少的设备权限操作
			//增加处理
			if (roleAssetList!=null && CollectionUtil.isNotEmpty(plusAssetIds) && CollectionUtil.isNotEmpty(plusAssetOrgIds) && plusAssetIds.size()==plusAssetOrgIds.size()) {
				VmsRoleAsset plusRoleAsset;
					for (int k=0; k<plusAssetIds.size(); k++) {
						//TODO 已有的就不包含了 加上if判断 未设置主键id
						plusRoleAsset = new VmsRoleAsset();
						//plusRoleAsset.setRoleId(roleId);//作为展示用不到角色id
						plusRoleAsset.setAssetId(plusAssetIds.get(k));
						plusRoleAsset.setOrgId(plusAssetOrgIds.get(k));
						plusRoleAsset.setAlreadyChanged(true);
						roleAssetList.add(plusRoleAsset);
					}
			}
			//减少设备处理  剔除页面被取消的设备数据
			List<VmsRoleAsset> showRoleAsset = new ArrayList<VmsRoleAsset>();
			if (CollectionUtil.isNotEmpty(roleAssetList) && CollectionUtil.isNotEmpty(minusAssetIds) && CollectionUtil.isNotEmpty(minusAssetOrgIds) && minusAssetIds.size()==minusAssetOrgIds.size()) {
				String com = "_";
				for (int h=0; h<minusAssetIds.size(); h++) {
					for (VmsRoleAsset ra : roleAssetList) {
						if ((minusAssetIds.get(h)+com+minusAssetOrgIds.get(h)).equals(ra.getAssetId()+com+ra.getOrgId())) {
							showRoleAsset.add(ra);
							break;
						}
					}
				}
				roleAssetList.removeAll(showRoleAsset);
			}
			
			
			if (CollectionUtil.isNotEmpty(orgList)) {
				for (SysOrg sysorg : orgList) {
					sysorg.setOrgLevel(1);//1部门 2资产
					//分辨组织节点
					if (sysorg.getOrgSource()!=null && ENORMOUS_WORK.intValue() == sysorg.getOrgSource().intValue()) {
						sysorg.setDescription(orgZtreeSlogan+ENOWORK);//重大作业
						
						//客户端需求start
						sysorg.setParentId(ENORMOUSWORK_ID);
						//客户端需求end
					} else if((sysorg.getType()!=null && ORGNIZATION.intValue() == sysorg.getType().intValue()) || (sysorg.getType()!=null && AREA.intValue() == sysorg.getType().intValue())) {
						sysorg.setDescription(orgZtreeSlogan+ORG);//组织机构
					} else if(sysorg.getType()!=null && COLLCETION.intValue() == sysorg.getType().intValue()) {
						sysorg.setDescription(orgZtreeSlogan+DIRECTORY);//收藏夹
					}
					
					//设置是否是角色之外的组织
					if (roleOrgIdList.contains(sysorg.getOrgId())) {
						sysorg.setRoleOutsideFlag(false);
					} else {
						sysorg.setRoleOutsideFlag(true);
					}
					
					//设置是否勾选
					if (CollectionUtil.isNotEmpty(roleOrgList)) {
						for (VmsRoleOrg vmsRoleOrg : roleOrgList) {
							if (vmsRoleOrg.getOrgId().intValue() == sysorg.getOrgId().intValue()) {
								sysorg.setZtreeCheckFlag(true);
								break;
							}
						}
					}
				}
				//客户端需求start
				//加入重大作业节点
				SysOrg enormousWork = new SysOrg();
				enormousWork.setOrgId(ENORMOUSWORK_ID);
				enormousWork.setParentId(ENORMOUSWORK_PID);
				enormousWork.setOrgName(ENORMOUSWORK_NAME);
//				enormousWork.setOrgLevel(1);
				enormousWork.setDescription(orgZtreeSlogan+ENOWORK);
				orgList.add(enormousWork);
				//客户端需求end
			}
			
			//形成组织结构树形json1
			JsonConfig cfg = new JsonConfig();
			String[] EXCLUDES = new String[]{"orderNum","createTime","updateTime","creatorId","status","orgCode","haveDevice","haveChild","isVisible","zoom","longitude","latitude","type","encoding","pinyin","pagestart","pageend","keywords"};
			cfg.setExcludes(EXCLUDES);
			JSONArray jsonArray = JSONArray.fromObject(orgList,cfg);
			String json1 = jsonArray.toString();
			json1 = json1.replaceAll("orgId", "id").replaceAll("parentId", "pId").replaceAll("orgName", "name").
					replaceAll("orgLevel", "type").replaceAll("description", "iconSkin").replace("ztreeCheckFlag", "checked").replaceAll("roleOutsideFlag", "extraFlag");
			
			//组装设备json2
			if(CollectionUtil.isNotEmpty(assetList)){
				for(VRoleTreeOrgAsset vAsset : assetList){
					vAsset.setZtreeDisplay(vassetZtreeSlogan+vAsset.getDeviceTypeId());
					
					//设置设备是否是角色外
					if (roleAssetIdList.contains(vAsset.getAssetUUID())) {
						vAsset.setRoleOutsideFlag(false);
					} else {
						vAsset.setRoleOutsideFlag(true);
					}
					
					//设置ztree勾选树
					if (CollectionUtil.isNotEmpty(roleAssetList)) {
						for (VmsRoleAsset vmsRoleAsset : roleAssetList) {
							if ((vmsRoleAsset.getAssetId()+separator+vmsRoleAsset.getOrgId()).equals(vAsset.getAssetUUID())) {
								vAsset.setZtreeCheckFlag(true);
								break;
							}
						}
					}
				}
			}
			jsonArray = JSONArray.fromObject(assetList);
			String json2 = jsonArray.toString();
			json2 = json2.replaceAll("assetUUID", "id").replaceAll("assetId", "dbassetId").replaceAll("assetOrgid", "pId").
					replaceAll("assetName", "name").replace("ztreeDisplay", "iconSkin").replace("ztreeCheckFlag", "checked").replaceAll("roleOutsideFlag", "extraFlag");
			
			//拼接zTreeNodes
			//json格式为[{}],因而需要去掉前后的[]，而且要加上，分隔符
			if(StringUtil.validateStr(json1)){
				json1 = json1.substring(0,json1.length()-1);
			}
			if(StringUtil.validateStr(json2)){
				json2 = json2.substring(1,json2.length());
			}
			json1 += ","+json2;
			
			return json1;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 获取客户端的菜单树
	 * @return
	 */
	public String getClientFuntTree() {
		try {
			SysFunt sysFunt = new SysFunt();
			sysFunt.setFuntStatus(1);
			sysFunt.setFuntGroup(2);
			List<SysFunt> clientFuntList = funtMapper.selectAll(sysFunt);
			if (CollectionUtil.isNotEmpty(clientFuntList)) {
				//组织客户端菜单树形
				JsonConfig cfg = new JsonConfig();
				String[] EXCLUDES = new String[]{"createTime","updateTime"};
				cfg.setExcludes(EXCLUDES);
				JSONArray jsonArray = JSONArray.fromObject(clientFuntList,cfg);
				String json = jsonArray.toString();
				json = json.replaceAll("id", "id").replaceAll("funtName", "name").replaceAll("funtParId", "pId");
				return json;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据角色查询下面用户,节点父id为角色id
	 * @param roleId
	 * @return
	 */
	public String getUserJsonByRole(Integer roleId) {
		try {
			List<SysUserRole> userRoleList = userRoleMapper.selectByRoleId(roleId);
			if (CollectionUtil.isNotEmpty(userRoleList)) {
				SysRole role = roleMapper.selectByPrimaryKey(roleId);
				if (role!=null) {
					//构造虚拟节点
					SysUserRole virtual = new SysUserRole();
					virtual.setUserId(-1);
					virtual.setpId(Integer.MIN_VALUE);
					virtual.setUserName(role.getRoleName());
					virtual.setRoleId(roleId);
					userRoleList.add(virtual);
				} else {
					return null;
				}
				for (SysUserRole userRole : userRoleList) {
					userRole.setpId(-1);
				}
				//组织角色下的用户树形
				JSONArray jsonArray = JSONArray.fromObject(userRoleList);
				String json = jsonArray.toString();
				json = json.replaceAll("userId", "id").replaceAll("roleId", "roleId").replaceAll("userName", "name");
				return json;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取角色组织树
	 * @return
	 */
	public String getRoleTree() {
		try {
			SysRole role = new SysRole();
			role.setStatus(SysRoleController.VALID_ROLE_STATUS);
			List<SysRole> roleList = roleMapper.selectAll(role);
			if (CollectionUtil.isNotEmpty(roleList)) {
				//组织角色树形
				JSONArray jsonArray = JSONArray.fromObject(roleList);
				String json = jsonArray.toString();
				json = json.replaceAll("roleId", "id").replaceAll("roleName", "name").replaceAll("parentId", "pId").replaceAll("categoryId", "categoryId");//用于节点操作
				return json;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 用户列表操作中使用该方法
	 * @param userId
	 * @return
	 */
	public String getRoleTreeOfUser(Integer userId) {
		try {
			int superRoleId = 1;
			SysRole role = new SysRole();
			role.setStatus(SysRoleController.VALID_ROLE_STATUS);
			List<SysRole> roleList = roleMapper.selectAll(role);
			List<SysRole> allList = new ArrayList<SysRole>();
			if (CollectionUtil.isNotEmpty(roleList)) {
				//去掉超级管理员
				for (SysRole sysRole : roleList) {
					if (sysRole.getRoleId().intValue()!= superRoleId) {
						allList.add(sysRole);
					}
				}
				roleList = allList;
				//获取该用户的角色
				List<SysUserRole> userRoleList = userRoleMapper.selectByUserId(userId);
				//设置选中状态
				for (SysRole sysRole : roleList) {
					if (CollectionUtil.isNotEmpty(userRoleList)) {
						if (sysRole.getRoleId().intValue() == userRoleList.get(0).getRoleId().intValue()) {
							sysRole.setZtreeCheck(true);
							break;
						}
					}
				}
				//组织角色树形
				JSONArray jsonArray = JSONArray.fromObject(roleList);
				String json = jsonArray.toString();
				json = json.replaceAll("roleId", "id").replaceAll("roleName", "name").replaceAll("parentId", "pId").replaceAll("categoryId", "categoryId").replace("ztreeCheck", "checked");//用于节点操作
				return json;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 在设备管理-管理设备操作中-获取组织设备树，点击组织异步加载设备
	 * @return
	 */
	public ResultBean asyncGetOrgTreeForAssociateAsset() {
		try {
			ResultBean resultBean = new ResultBean();
			//1处理组织
			//Integer rootParentId = -1;//获取根节点，用于异步加载
			SysOrg org = new SysOrg();
			//org.setParentId(rootParentId);///组织机构伪异步加载
			org.setStatus(0);//未被删除 对应数据库的deleteflag
			org.setIsVisible(true);
			List<SysOrg> orgList = orgMapper.selectListOrgTreeOrderId(org);
			if (CollectionUtil.isNotEmpty(orgList)) {
				for (SysOrg sysorg :orgList) {
					sysorg.setOrgLevel(1);//1部门 2资产
					//分辨组织节点
					if (sysorg.getOrgSource()!=null && ENORMOUS_WORK.intValue() == sysorg.getOrgSource().intValue()) {
						sysorg.setDescription(orgZtreeSlogan+ENOWORK);//重大作业
					} else if((sysorg.getType()!=null && ORGNIZATION.intValue() == sysorg.getType().intValue()) || (sysorg.getType()!=null && AREA.intValue() == sysorg.getType().intValue())) {
						sysorg.setDescription(orgZtreeSlogan+ORG);//组织机构
					} else if(sysorg.getType()!=null && COLLCETION.intValue() == sysorg.getType().intValue()) {
						sysorg.setDescription(orgZtreeSlogan+DIRECTORY);//收藏夹
					}
					//在异步加载时，默认的根节点都设置为父节点，借用org的isVisible属性
					sysorg.setIsVisible(true);
				}
			}
			
			//形成组织结构树形json1
			JsonConfig cfg = new JsonConfig();
			String[] EXCLUDES = new String[]{"orderNum","createTime","updateTime","creatorId","status","orgCode","haveDevice","haveChild","zoom","longitude","latitude","type","encoding","pinyin","pagestart","pageend","keywords"};
			cfg.setExcludes(EXCLUDES);
			JSONArray jsonArray = JSONArray.fromObject(orgList,cfg);
			String json1 = jsonArray.toString();
			json1 = json1.replaceAll("orgId", "id").replaceAll("parentId", "pId").replaceAll("orgName", "name").replaceAll("orgLevel", "type").replaceAll("description", "iconSkin").replace("isVisible", "isParent").replace("ztreeCheckFlag", "checked");
			resultBean.setFeedBack(json1);
			return resultBean;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 设备管理-关联设备操作-异步展开组织下的设备子节点 
	 * @param parentId
	 * @return
	 */
	public JSONArray asyncGetChildrenByOrgId(Integer parentId) {
		JSONArray jsonArray = new JSONArray();
		try {
			Integer assetLevelFlag = 2;//2资产
			String json2 = null;
			//2封装设备的node信息
			VRoleTreeOrgAsset roleTreeOrgAsset = new VRoleTreeOrgAsset();
			roleTreeOrgAsset.setAssetorgvisible(true);//组织可见性
			roleTreeOrgAsset.setAssetstatus(0);//组织状态
			roleTreeOrgAsset.setDeletetatus(false);//设备正常
			roleTreeOrgAsset.setAssetvisible(true);//设备是否删除
			roleTreeOrgAsset.setAssetOrgid(parentId);
			List<VRoleTreeOrgAsset> assetList = roleTreeOrgAssetMapper.selectListByObj(roleTreeOrgAsset);
			if (CollectionUtil.isNotEmpty(assetList)) {
				for(VRoleTreeOrgAsset vAsset : assetList){
					vAsset.setType(assetLevelFlag);//数据库中也是这个值
					vAsset.setZtreeDisplay(vassetZtreeSlogan+vAsset.getDeviceTypeId());
				}
				jsonArray = JSONArray.fromObject(assetList);
				json2 = jsonArray.toString();
				json2 = json2.replaceAll("assetUUID", "id").replaceAll("assetId", "dbassetId").replaceAll("assetOrgid", "pId").replaceAll("assetName", "name").replace("ztreeDisplay", "iconSkin").replace("ztreeCheckFlag", "checked");
			}
			//拼接zTreeNodes
			//json格式为[{}],因而需要去掉前后的[]，而且要加上，分隔符
			//组织和设备都有
			if (StringUtil.validateStr(json2)) {
				//只有设备
				jsonArray = JSONArray.fromObject(json2);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return jsonArray;
	}
}
