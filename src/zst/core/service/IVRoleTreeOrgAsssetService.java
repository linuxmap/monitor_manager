package zst.core.service;

import java.util.List;

import zst.core.entity.VRoleTreeOrgAsset;

public interface IVRoleTreeOrgAsssetService {

	/**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VRoleTreeOrgAsset> selectListByObj(VRoleTreeOrgAsset record) throws Exception;
    
    long selectCountByObj(VRoleTreeOrgAsset record) throws Exception;
}
