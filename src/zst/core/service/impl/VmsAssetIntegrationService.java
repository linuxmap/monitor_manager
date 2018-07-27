package zst.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zst.core.dao.SysOrgMapper;
import zst.core.dao.VmsAssetMapper;
import zst.core.dao.VmsOrgAssetMapper;
import zst.core.entity.SysOrg;
import zst.core.entity.VmsAsset;
import zst.core.entity.VmsOrgAsset;
import zst.core.service.IVmsAssetIntegrationService;
import zst.core.service.datatree.SysDataHelper;
import zst.extend.util.CollectionUtil;

@Service
@Transactional
public class VmsAssetIntegrationService implements IVmsAssetIntegrationService {

	private static final Log logger = LogFactory.getLog(SysDataHelper.class);
	
	//定义final变量，防止程序出现魔数
	//表示数据来源的自定义，在vms_organization表中
	private static final Integer COPY_ORGDATA_FLAG = 0;
	//表示复制数据，在vms_org_asset表中
	private static final Integer COPY_ASSETDATA_FLAG = 2;
	
	@Resource
	private SysOrgMapper orgMapper;
	
	@Resource
	private VmsAssetMapper assetMapper;
	
	@Resource
	private VmsOrgAssetMapper orgAssetMapper;
	
	/**
	 * 处理前台携带过来的树的子节点和父节点id，然后对集合进行组装  使用算法计算,保存原来的组织关系
	 * @param nodeIdArray
	 * @param parIdArray
	 * @return
	 */
	@Override
	public Map<Integer,List<SysOrg>> insertHandleParChildRelation(Integer[] nodeIdArray, Integer[] parIdArray, Integer orgParId) {
		if (nodeIdArray==null || parIdArray==null || nodeIdArray.length==0 || parIdArray.length==0 || nodeIdArray.length!=parIdArray.length) {
			//非空和长度一致性验证
			return null;
		} else {
			logger.info("需要整合的组织节点个数为:"+nodeIdArray.length);
		}
		try {
			SysOrg sysOrg = new SysOrg();
			byte haveChild = 1;
			byte haveDevice = 1;
			Date date = new Date();
			List<Integer> nodeIdList = Arrays.asList(nodeIdArray);
			List<Integer> parIdList = Arrays.asList(parIdArray);
			//===============处理组织start
			//获取原有组织的信息
			List<SysOrg> originalOrgList = orgMapper.selectListOrgByIds(nodeIdList);
			//获取父节点组织
			SysOrg parOrg = orgMapper.selectByPrimaryKey(orgParId);
			//获取原有父节点组织的子节点集合
			List<SysOrg> originalChildList = orgMapper.selectChildsByParId(orgParId);
			Integer maxChildOrgEncoding = getMaxOrgEncoding(originalChildList);
			//获取现有的子节点的最大encoding
			if (parOrg==null) {
				logger.info("所要关联的父节点不存在");
				return null;
			} else {
				
			}
			//将组织信息放入map中，key为其主键  存储原来组织
			Map<Integer, SysOrg> originalOrgMap = new HashMap<Integer, SysOrg>();
			if (CollectionUtil.isNotEmpty(originalOrgList)) {
				for (int i=0; i<originalOrgList.size(); i++) {
					originalOrgMap.put(originalOrgList.get(i).getOrgId(), originalOrgList.get(i));
				}
			}
			//处理关系
			Map<Integer,List<SysOrg>> map = new HashMap<Integer, List<SysOrg>>();
			//迭代获取集合关系，返回map，key标识节点的level，从1开始
			getRelationByIterator(nodeIdList, parIdList, map, 0);
			//获取到的map集合的key值为层级，1表示根节点，2表示下一级节点
			if (map!=null && map.size()>0) {
				//使用map存储新增的组织的主键 ，key：原有的主键 value 新增的主键
				Map<Integer,Integer> dbKeyOldNewMap = new HashMap<Integer, Integer>();
				//使用map存储新增的组织的主键 ，key：新主键   value 新的encoding
				Map<Integer,String> dbNewEncodingMap = new HashMap<Integer, String>();
				//获取一级节点
				List<SysOrg> rootOrgList = map.get(1);
				if (CollectionUtil.isNotEmpty(rootOrgList)) {
					SysOrg org = null;
					for (int k=0; k<rootOrgList.size(); k++) {
						//获取根节点
						//原有id
						Integer oldOrgId = rootOrgList.get(k).getOrgId();
						org = originalOrgMap.get(oldOrgId);
						org.setOrgId(null);
						org.setParentId(orgParId);
						org.setOrgSource(COPY_ORGDATA_FLAG);//标识组织为自定义的复制数据
						org.setOrderNum(1);
						org.setCreateTime(date);
						org.setUpdateTime(date);
						//设置组织编码
						if (maxChildOrgEncoding==null) {
							//原有组织没有子节点
							if (k<9) {
								//少于10条记录
								org.setEncoding(parOrg.getEncoding()+0+(k+1));
							} else {
								//大于10条记录
								org.setEncoding(parOrg.getEncoding()+(k+1));
							}
						} else {
							//有组织节点,在原来基础上追加
							org.setEncoding((Integer.valueOf(maxChildOrgEncoding)+(k+1))+"");
						}
						logger.info("插入所关联节点的根节点元素集合");
						orgMapper.insertSelective(org);
						//父节点有child
						sysOrg.setHaveChild(haveChild);
						//存入map中
						dbKeyOldNewMap.put(oldOrgId, org.getOrgId());//key:原来节点的id  value:新主键
						dbNewEncodingMap.put(oldOrgId, org.getEncoding());//key:原来节点的id  value:新根节点的编码encoding
					}
				}
				//获取一级以下节点  判断map的大小即节点层次
				int level = map.size();
				if (level==1) {
					//只是一层就不做操作了
					logger.info("被关联的组织没有层级关系，只有一层");
				} else {
					//定义循环次数
					int loopCount = level-1;
					for (int loopIndex=1; loopIndex<=loopCount; loopIndex++) {
						//循环次数
						List<SysOrg> levelList = map.get(loopIndex+1);
						if (CollectionUtil.isNotEmpty(levelList)) {
							SysOrg orgInner = null;
							for (int loopIndexInner=0; loopIndexInner<levelList.size(); loopIndexInner++) {
								//获取根节点
								//原有id
								Integer oldOrgId = levelList.get(loopIndexInner).getOrgId();
								//原有父id
								Integer oldOrgParId  = levelList.get(loopIndexInner).getParentId();
								//获取其新节点
								//原来的父节点有了新的id
								Integer newOrgParId = dbKeyOldNewMap.get(oldOrgParId);
								orgInner = originalOrgMap.get(oldOrgId);//真身的id
								orgInner.setOrgId(null);
								orgInner.setParentId(newOrgParId);
								orgInner.setOrgSource(COPY_ORGDATA_FLAG);//标识组织为自定义的复制数据
								orgInner.setOrderNum(1);
								orgInner.setCreateTime(date);
								orgInner.setUpdateTime(date);
								//设置组织编码
								if (loopIndexInner<9) {//追加
									//少于10条记录
									orgInner.setEncoding(dbNewEncodingMap.get(oldOrgParId)+0+(loopIndexInner+1));
								} else {
									//大于10条记录
									orgInner.setEncoding(dbNewEncodingMap.get(oldOrgParId)+(loopIndexInner+1));
								}
								logger.info("插入所关联节点的根节点元素集合");
								orgMapper.insertSelective(orgInner);
								//存入map中
								dbKeyOldNewMap.put(oldOrgId, orgInner.getOrgId());
								dbNewEncodingMap.put(oldOrgId, orgInner.getEncoding());
							}
						}
					}
				}
				//=========================处理组织end
				//=========================处理设备start
				//只是增加组织设备关系
				List<VmsAsset> copyAssetList = assetMapper.selectListByOrgList(nodeIdList);
				if (CollectionUtil.isNotEmpty(copyAssetList)) {
					//添加组织设备关系时，需要对组织的haveChild字段设置为1,特生成如下容器
					List<Integer> haveChildSetList = new ArrayList<Integer>();
					List<VmsOrgAsset> orgAssetList = new ArrayList<VmsOrgAsset>();
					VmsOrgAsset orgAsset = null;
					for	(VmsAsset asset : copyAssetList) {
						orgAsset = new VmsOrgAsset();
						orgAsset.setAssetId(asset.getAssetId());
						orgAsset.setOrgId(dbKeyOldNewMap.get(asset.getOrgId()));
						orgAsset.setSourceflag(COPY_ASSETDATA_FLAG);
						orgAsset.setOrdernumber(1);
						orgAsset.setVisibleflag(true);
						orgAssetList.add(orgAsset);
						
						//需要设置haveChild添加到集合中
						haveChildSetList.add(dbKeyOldNewMap.get(asset.getOrgId()));
					}
					orgAssetMapper.insertBatch(orgAssetList);
					sysOrg.setHaveDevice(haveDevice);
					logger.info("批量插入关系数据");
					
					//对有设备的组织进行haveChild字段设置
					if (CollectionUtil.isNotEmpty(haveChildSetList)) {
						orgMapper.updateHaveDeviceByList(haveChildSetList);
					}
				}
			}
			sysOrg.setOrgId(orgParId);
			orgMapper.updateByPrimaryKeySelective(sysOrg);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 迭代算法
	 * 方法注释如下
	 * 1		2		3		4		5		6		7		8		9
	 * -1		-2		1		2		3   	-1		-1		7		3
	 * false   flase   true    true    true    false   false  true     true  false的直接为根节点添加到集合当中，true新组成一个数组继续按照上述方式迭代 false的层级为1
	 * 3		4		5		8		9
	 * 1		2		3		7		3
	 * false  false   true     false    true  false的层级为2
	 * ....
	 * @param nodeIdList
	 * @param parIdList
	 * @param map
	 * @param index
	 * @return
	 */
	public Map<Integer,List<SysOrg>> getRelationByIterator(List<Integer> nodeIdList, List<Integer> parIdList, Map<Integer,List<SysOrg>> map, int level) {
		List<SysOrg> orgList = new ArrayList<SysOrg>();
		List<Integer> newNodeIdList = new ArrayList<Integer>();//存储自带关系的节点id
		List<Integer> newParIdList = new ArrayList<Integer>();//存储自带关系的节点父id
		for (int i=0; i<nodeIdList.size(); i++) {
			if (nodeIdList.contains(parIdList.get(i))) {//如果包含，则需要重新整合关系
				//将自带关系的重新分配为一个数组
				newNodeIdList.add(nodeIdList.get(i));
				newParIdList.add(parIdList.get(i));
			} else {
				//出局
				//是当前节点的所有根节点
				SysOrg org = new SysOrg();
				org.setOrgId(nodeIdList.get(i));
				org.setParentId(parIdList.get(i));
				orgList.add(org);
			}
		}
		level++;
		map.put(level, orgList);//将org集合分别存入到对应的map中，有节点级别
		if (newNodeIdList.size()==0 || nodeIdList.size()==1) {//出口，只要有自带关系，则跳出迭代
			logger.info("结束迭代");
		} else {
			//继续迭代
			getRelationByIterator(newNodeIdList, newParIdList, map, level);
		}
		return map;
	}
	
	
	/**
	 * 获取最大encoding值
	 * @param oldSysOrgs
	 * @return
	 */
	private Integer getMaxOrgEncoding(List<SysOrg> oldSysOrgs) {
		if (CollectionUtil.isNotEmpty(oldSysOrgs)) {
			Integer max = 0;
			String encoding = null;
			Integer IntegerWithoutZero = null;
			for (SysOrg org : oldSysOrgs) {
				encoding = org.getEncoding();
				if (encoding != null) {
					IntegerWithoutZero = Integer.valueOf(encoding);
					if(IntegerWithoutZero > max){
						max = IntegerWithoutZero;
					}
				}
			}
			return max;
		} else {
			return null;
		}
	}

}
