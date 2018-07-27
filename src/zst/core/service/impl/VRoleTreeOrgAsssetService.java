package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.VRoleTreeOrgAssetMapper;
import zst.core.entity.VRoleTreeOrgAsset;
import zst.core.service.IVRoleTreeOrgAsssetService;

@Service
public class VRoleTreeOrgAsssetService implements IVRoleTreeOrgAsssetService {

	@Resource
	private VRoleTreeOrgAssetMapper roleTreeOrgAssetMapper;
	
	@Override
	public List<VRoleTreeOrgAsset> selectListByObj(VRoleTreeOrgAsset record)
			throws Exception {
		return roleTreeOrgAssetMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(VRoleTreeOrgAsset record) throws Exception {
		return roleTreeOrgAssetMapper.selectCountByObj(record);
	}

}
