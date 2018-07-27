package zst.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zst.core.dao.SysRoleMapper;
import zst.core.dao.SysUserAssetMapper;
import zst.core.dao.SysUserOrgMapper;
import zst.core.dao.SysUserRoleMapper;
import zst.core.dao.VmsOrgAssetMapper;
import zst.core.dao.VmsRoleAssetMapper;
import zst.core.dao.VmsRoleOrgMapper;
import zst.core.entity.ResultBean;
import zst.core.entity.SysRole;
import zst.core.entity.SysUserAsset;
import zst.core.entity.SysUserOrg;
import zst.core.entity.SysUserRole;
import zst.core.entity.VmsOrgAsset;
import zst.core.entity.VmsRoleAsset;
import zst.core.entity.VmsRoleOrg;
import zst.core.service.ISysRoleService;
import zst.extend.exception.PlatformException;
import zst.extend.util.CollectionUtil;

@Service
@Transactional
public class SysRoleService implements ISysRoleService{
	
	@Resource
	private SysRoleMapper sysRoleMapper;
	
	@Resource
	private SysUserOrgMapper userOrgMapper;
	
	@Resource
	private SysUserAssetMapper userAssetMapper;
	
	@Resource
	private VmsRoleOrgMapper roleOrgMapper;
	
	@Resource
	private VmsRoleAssetMapper roleAssetMapper;
	
	@Resource
	private SysUserRoleMapper userRoleMapper;
	
	@Resource
	private VmsOrgAssetMapper orgAssetMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) throws Exception {
		return sysRoleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SysRole record) throws Exception {
		return sysRoleMapper.insert(record);
	}

	/**
	 * 新增角色
	 */
	@Override
	public int insertSelective(SysRole record) throws Exception {
		return sysRoleMapper.insertSelective(record);
	}

	@Override
	public SysRole selectByPrimaryKey(Integer id) throws Exception {
		return sysRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysRole record) throws Exception {
		
		return sysRoleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysRole record) throws Exception {
		
		return sysRoleMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<SysRole> selectAll(SysRole record) throws Exception {
		
		return sysRoleMapper.selectAll(record);
	}

	@Override
	public int deleteByRoleId(Integer roleId) throws Exception {
		
		return sysRoleMapper.deleteByPrimaryKey(roleId);
	}

	@Override
	public ResultBean insertBatchPersonalize(Integer roleId, Integer userId, Integer[] negativeOrg, String[] negativeAsset, Integer[] positiveOrg,
			String[] positiveAsset) throws Exception {
		ResultBean resultBean = new ResultBean();
		try {
			//用户 组织权限
			List<SysUserOrg> userOrgs = new ArrayList<SysUserOrg>();
			SysUserOrg userOrg;
			if (negativeOrg!=null) {
				for (Integer minusOrgId : negativeOrg) {
					userOrg = new SysUserOrg();
					userOrg.setOrgId(minusOrgId);
					userOrg.setUserId(userId);
					userOrg.setFlag(false);
					userOrgs.add(userOrg);
				}
			}
			if (positiveOrg!=null) {
				for (Integer plusOrgId : positiveOrg) {
					userOrg = new SysUserOrg();
					userOrg.setOrgId(plusOrgId);
					userOrg.setUserId(userId);
					userOrg.setFlag(true);
					userOrgs.add(userOrg);
				}
			}
			//用户设备权限
			List<SysUserAsset> userAssets = new ArrayList<SysUserAsset>();
			String separator = "_";
			SysUserAsset userAsset;
			if (negativeAsset!=null) {
				for (String minusAsset : negativeAsset) {
					userAsset = new SysUserAsset();
					String[] split = minusAsset.split(separator);
					userAsset.setUserId(userId);
					userAsset.setAssetId(Integer.valueOf(split[0]));
					userAsset.setOrgId(Integer.valueOf(split[1]));
					userAsset.setFlag(false);
					userAssets.add(userAsset);
				}
			}
			if (positiveAsset!=null) {
				for (String plusAsset : positiveAsset) {
					userAsset = new SysUserAsset();
					plusAsset.split(separator);
					String[] split = plusAsset.split(separator);
					userAsset.setUserId(userId);
					userAsset.setAssetId(Integer.valueOf(split[0]));
					userAsset.setOrgId(Integer.valueOf(split[1]));
					userAsset.setFlag(true);
					userAssets.add(userAsset);
				}
			}
			//DB处理
			//每回更新都会删除用户和组织、设备表中的数据
			userOrgMapper.deleteByUserId(userId);
			userAssetMapper.deleteByUserId(userId);
			if (CollectionUtil.isNotEmpty(userOrgs)) {
				userOrgMapper.insertBatch(userOrgs);
			}
			if (CollectionUtil.isNotEmpty(userAssets)) {
				userAssetMapper.insertBatch(userAssets);
			}
			resultBean.setFlag(true);
			resultBean.setFeedBack("保存成功!");
			return resultBean;
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setFeedBack("保存失败!");
			resultBean.setFlag(false);
			throw new PlatformException(e.getMessage());
		}
	}

	@Override
	public ResultBean insertSameRole(SysRole sysRole, Integer sublingRoleId) throws Exception {
		ResultBean resultBean = new ResultBean();
		resultBean.setFlag(true);
		resultBean.setFeedBack("拷贝成功!");
		try {
			sysRoleMapper.insertSelective(sysRole);
			//返回添加到库中的主键值
			resultBean.setPrimaryKey(sysRole.getRoleId());
			//复制关系 role_org role_asset
			List<VmsRoleOrg> roleOrgList = roleOrgMapper.selectByRoleId(sublingRoleId);
			List<VmsRoleAsset> roleAssetList = roleAssetMapper.selectByRoleId(sublingRoleId);
			if (roleOrgList!=null && roleOrgList.size()>0) {
				for (VmsRoleOrg roleOrg : roleOrgList) {
					roleOrg.setId(null);
					roleOrg.setRoleId(sysRole.getRoleId());
				}
				roleOrgMapper.insertBatch(roleOrgList);
			}
			if (roleAssetList!=null && roleAssetList.size()>0) {
				for (VmsRoleAsset roleAsset : roleAssetList) {
					roleAsset.setId(null);
					roleAsset.setRoleId(sysRole.getRoleId());
				}
				roleAssetMapper.insertBatch(roleAssetList);
			}
			return resultBean;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
		
	}

	@Override
	public ResultBean deleteLogicalByRoleId(Integer roleId) throws Exception {
		ResultBean resultBean = new ResultBean();
		resultBean.setFlag(true);
		resultBean.setFeedBack("删除成功!");
		try {
			SysRole role = sysRoleMapper.selectByPrimaryKey(roleId);
			if (role==null) {
				resultBean.setFeedBack("该角色不存在");
				resultBean.setFlag(false);
				return resultBean;
			}
			List<SysUserRole> userRoleList = new ArrayList<SysUserRole>();
			//根据roleId获取关联用户id
			userRoleList = userRoleMapper.selectByRoleId(roleId);
			//角色下有用户或者功能,该角色不能删除
			if (CollectionUtil.isNotEmpty(userRoleList)) {
				resultBean.setFeedBack("该角色下还有用户，请先取消相关用户的权限");
				resultBean.setFlag(false);
			} else {
				//删除角色 逻辑删除
				SysRole sysRole = new SysRole();
				sysRole.setRoleId(roleId);
				sysRole.setStatus(0);//0为失效角色
				Date date = new Date();
				sysRole.setUpdateTime(date);
				sysRoleMapper.updateByPrimaryKeySelective(sysRole);
				//删除角色下的数据权限
				roleOrgMapper.deleteByRoleId(roleId);
				roleAssetMapper.deleteByRoleId(roleId);
			}
			return resultBean;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}

	@Override
	public ResultBean updateRoleDataPermission(Integer roleId,Integer[] allCheckedAssetOrgArray, Integer[] orgAddIdArray,Integer[] orgDelIdArray, String[] equAddIdArray,String[] equDelIdArray) throws Exception {
		ResultBean resultBean = new ResultBean();
		resultBean.setFlag(true);
		resultBean.setFeedBack("保存成功!");
		try {
			//需要增加的组织
			VmsRoleOrg roleOrg = null;
			if (orgAddIdArray!=null && orgAddIdArray.length>0) {
				List<VmsRoleOrg> roleOrgAddList = new ArrayList<VmsRoleOrg>();
				for (Integer orgAddId : orgAddIdArray) {
					roleOrg = new VmsRoleOrg();
					roleOrg.setRoleId(roleId);
					roleOrg.setOrgId(orgAddId);
					roleOrgAddList.add(roleOrg);
				}
				roleOrgMapper.insertBatch(roleOrgAddList);
			}
			//设备全部勾选
			if (allCheckedAssetOrgArray!=null && allCheckedAssetOrgArray.length>0) {
				List<Integer> orgIdList = new ArrayList<Integer>();
				for (Integer integer : allCheckedAssetOrgArray) {
					orgIdList.add(integer);
				}
				//获取设备全部勾选的组织集合
				List<VmsOrgAsset> orgAssetList = orgAssetMapper.selectByOrgIdList(orgIdList);
				//组织需要分清是否展开 
				List<VmsRoleAsset> roleAssetAllAddList = new ArrayList<VmsRoleAsset>();
				if (CollectionUtil.isNotEmpty(orgAssetList)) {
					VmsRoleAsset vmsRoleAsset = null;
					for (VmsOrgAsset orgAsset : orgAssetList) {
						vmsRoleAsset = new VmsRoleAsset();
						vmsRoleAsset.setRoleId(roleId);
						vmsRoleAsset.setOrgId(orgAsset.getOrgId());
						vmsRoleAsset.setAssetId(orgAsset.getAssetId());
						roleAssetAllAddList.add(vmsRoleAsset);
					}
					roleAssetMapper.insertBatch(roleAssetAllAddList);
				}
			}
			//需要减少的组织
			if (orgDelIdArray!=null && orgDelIdArray.length>0) {
				List<VmsRoleOrg> delRoleOrgList = new ArrayList<VmsRoleOrg>();
				List<VmsRoleAsset> delRoleAssetOrgList = new ArrayList<VmsRoleAsset>();
				VmsRoleAsset roleAsset = null;
				for (Integer orgDelId : orgDelIdArray) {
					roleOrg = new VmsRoleOrg();
					roleOrg.setRoleId(roleId);
					roleOrg.setOrgId(orgDelId);
					delRoleOrgList.add(roleOrg);
					
					roleAsset = new VmsRoleAsset();
					roleAsset.setRoleId(roleId);
					roleAsset.setOrgId(orgDelId);
					delRoleAssetOrgList.add(roleAsset);
				}
				roleOrgMapper.deleteByRoleOrg(delRoleOrgList);
				//删除角色设备关系表 根据组织和角色来删除role_asset中的记录
				roleAssetMapper.deleteByRoleAssetOrg(delRoleAssetOrgList);
			}
			VmsRoleAsset roleAsset = null;
			//需要增加的设备
			String separator = "_";
			if (equAddIdArray!=null && equAddIdArray.length>0) {
				List<VmsRoleAsset> roleAssetAddList = new ArrayList<VmsRoleAsset>();
				for (String equAdd : equAddIdArray) {
					roleAsset = new VmsRoleAsset();
					String[] equAddArray = equAdd.split(separator);
					if (equAddArray!=null && equAddArray.length==2) {
						roleAsset.setRoleId(roleId);
						roleAsset.setAssetId(Integer.valueOf(equAddArray[0]));
						roleAsset.setOrgId(Integer.valueOf(equAddArray[1]));
						roleAssetAddList.add(roleAsset);
					}
				}
				roleAssetMapper.insertBatch(roleAssetAddList);
			}
			//需要减少的设备
			if (equDelIdArray!=null && equDelIdArray.length>0) {
				List<VmsRoleAsset> roleAssetDelList = new ArrayList<VmsRoleAsset>();
				for (String equDel : equDelIdArray) {
					roleAsset = new VmsRoleAsset();
					String[] equDelArray = equDel.split(separator);
					if (equDelArray!=null && equDelArray.length==2) {
						roleAsset.setRoleId(roleId);
						roleAsset.setAssetId(Integer.valueOf(equDelArray[0]));
						roleAsset.setOrgId(Integer.valueOf(equDelArray[1]));
						roleAssetDelList.add(roleAsset);
					}
				}
				roleAssetMapper.deleteByRoleAsset(roleAssetDelList);
			}
			return resultBean;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}

	@Override
	public ResultBean insertRoleDataPermission(Integer roleId,Integer[] fullOrgChecked,Integer[] allCheckedOrgArray,
											String[] assetSpecialChecked) throws Exception {
		ResultBean resultBean = new ResultBean();
		resultBean.setFlag(true);
		resultBean.setFeedBack("保存成功!");
		try {
			//设备节点
			List<VmsRoleAsset> roleAssets = new ArrayList<VmsRoleAsset>();
			VmsRoleAsset roleAsset = null;
			//1组织下所有节点被选中
			if (fullOrgChecked!=null && fullOrgChecked.length>0) {
				List<Integer> orgIdList = new ArrayList<Integer>();
				for(Integer orgId : fullOrgChecked){
					orgIdList.add(orgId);
				}
				List<VmsOrgAsset> orgAssetList = orgAssetMapper.selectByOrgIdList(orgIdList);
				if (CollectionUtil.isNotEmpty(orgAssetList)) {
					for(VmsOrgAsset orgAsset : orgAssetList){
						roleAsset = new VmsRoleAsset();
						roleAsset.setOrgId(orgAsset.getOrgId());
						roleAsset.setAssetId(orgAsset.getAssetId());
						roleAsset.setRoleId(roleId);
						roleAssets.add(roleAsset);
					}
				}
			}
			//2单独部分勾选的设备处理
			if (assetSpecialChecked!=null && assetSpecialChecked.length>0) {
				String separator = "_";
				for(String orgAssetId : assetSpecialChecked){
					String[] orgAssetArray = orgAssetId.split(separator);
					if (orgAssetArray!=null && orgAssetArray.length==2) {
						roleAsset = new VmsRoleAsset();
						roleAsset.setAssetId(Integer.valueOf(orgAssetArray[0]));
						roleAsset.setOrgId(Integer.valueOf(orgAssetArray[1]));
						roleAsset.setRoleId(roleId);
						roleAssets.add(roleAsset);
					}
				}
			}
			roleAssetMapper.insertBatch(roleAssets);
			//3组织的权限
			if (allCheckedOrgArray!=null && allCheckedOrgArray.length>0) {
				List<VmsRoleOrg> roleOrgs = new ArrayList<VmsRoleOrg>();
				VmsRoleOrg roleOrg = null;
				for(Integer orgId : allCheckedOrgArray){
					roleOrg = new VmsRoleOrg();
					roleOrg.setRoleId(roleId);
					roleOrg.setOrgId(orgId);
					roleOrgs.add(roleOrg);
				}
				roleOrgMapper.insertBatch(roleOrgs);
			}
			return resultBean;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}

	@Override
	public ResultBean deleteRoleDataPermission(Integer roleId) throws Exception {
		ResultBean resultBean = new ResultBean();
		resultBean.setFlag(true);
		resultBean.setFeedBack("保存成功!");
		try {
			roleOrgMapper.deleteByRoleId(roleId);
			roleAssetMapper.deleteByRoleId(roleId);
			return resultBean;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}

	

}
