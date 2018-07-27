package zst.core.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.VmsRoleAssetMapper;
import zst.core.entity.VmsRoleAsset;
import zst.core.service.IVmsRoleAssetService;

@Service
public class VmsRoleAssetService implements IVmsRoleAssetService {

	@Resource
	private VmsRoleAssetMapper roleAssetMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) throws Exception {
		return roleAssetMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(VmsRoleAsset record) throws Exception {
		return roleAssetMapper.insert(record);
	}

	@Override
	public int insertSelective(VmsRoleAsset record) throws Exception {
		return roleAssetMapper.insertSelective(record);
	}

	@Override
	public VmsRoleAsset selectByPrimaryKey(Integer id) throws Exception {
		return roleAssetMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsRoleAsset record)
			throws Exception {
		return roleAssetMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsRoleAsset record) throws Exception {
		return roleAssetMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<VmsRoleAsset> selectListByObj(VmsRoleAsset record)
			throws Exception {
		return roleAssetMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(VmsRoleAsset record) throws Exception {
		return roleAssetMapper.selectCountByObj(record);
	}

	@Override
	public int insertBatch(List<VmsRoleAsset> list) throws Exception {
		return roleAssetMapper.insertBatch(list);
	}

	@Override
	public int deleteByRoleid(Integer roleId) throws Exception {
		return roleAssetMapper.deleteByRoleId(roleId);
	}

	@Override
	public List<VmsRoleAsset> selectByRoleId(Integer roleId) throws Exception {
		return roleAssetMapper.selectByRoleId(roleId);
	}

	@Override
	public List<VmsRoleAsset> selectListByPersonalizeMap(Map<String, Object> map)
			throws Exception {
		return roleAssetMapper.selectListByPersonalizeMap(map);
	}

	@Override
	public long selectCountByMap(Map<String, Object> map) throws Exception {
		return roleAssetMapper.selectCountByMap(map);
	}
}
