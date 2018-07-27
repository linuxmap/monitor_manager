package zst.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.SysUserAssetMapper;
import zst.core.dao.VmsOrgAssetMapper;
import zst.core.dao.VmsRoleAssetMapper;
import zst.core.entity.VmsOrgAsset;
import zst.core.service.IVmsOrgAssetService;
import zst.extend.util.CollectionUtil;

@Service
public class VmsOrgAssetService implements IVmsOrgAssetService {

	@Resource
	private VmsOrgAssetMapper orgAssetMapper;
	
	@Resource
	private VmsRoleAssetMapper roleAssetMapper;
	
	@Resource
	private SysUserAssetMapper userAssetMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) throws Exception {
		return orgAssetMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(VmsOrgAsset record) throws Exception {
		return orgAssetMapper.insert(record);
	}

	@Override
	public int insertSelective(VmsOrgAsset record) throws Exception {
		return orgAssetMapper.insertSelective(record);
	}

	@Override
	public VmsOrgAsset selectByPrimaryKey(Integer id) throws Exception {
		return orgAssetMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsOrgAsset record) throws Exception {
		return orgAssetMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsOrgAsset record) throws Exception {
		return orgAssetMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<VmsOrgAsset> selectListByObj(VmsOrgAsset record)
			throws Exception {
		return orgAssetMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(VmsOrgAsset record) throws Exception {
		return orgAssetMapper.selectCountByObj(record);
	}

	@Override
	public int insertBatch(List<VmsOrgAsset> list) throws Exception {
		return orgAssetMapper.insertBatch(list);
	}

	@Override
	public int deleteByOrgAssetField(List<VmsOrgAsset> list) throws Exception {
		return orgAssetMapper.deleteByOrgAssetField(list);
	}

	@Override
	public int updateBatchOrgAssetVisible(List<VmsOrgAsset> orgAssetList, Boolean visibleFlag, List<Integer> assetIdList, Integer orgId)
			throws Exception {
		//设置状态
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("visibleflag", visibleFlag);
		map.put("item", orgAssetList);
		if (visibleFlag!=null && !visibleFlag && CollectionUtil.isNotEmpty(assetIdList)) {
			//如果是设置为不可见，则删除 ，角色-设备，用户-设备（+-）表中的关联关系   +-表示flag字段是0还是1无论是要看还是不需要看的都删除关联
			Map<String, Object> orgAssetMap = new HashMap<String, Object>();
			orgAssetMap.put("orgId", orgId);
			orgAssetMap.put("list", assetIdList);
			roleAssetMapper.deleteByOrgAsset(orgAssetMap);
			userAssetMapper.deleteByOrgAsset(orgAssetMap);
		}
		return orgAssetMapper.updateBatchOrgAssetVisible(map);
	}

	@Override
	public int deleteByOrgAssetAllField(List<VmsOrgAsset> list)
			throws Exception {
		return orgAssetMapper.deleteByOrgAssetAllField(list);
	}

	@Override
	public long selectCountByOrg(List<Integer> orgIdList) throws Exception {
		return orgAssetMapper.selectCountByOrg(orgIdList);
	}

}
