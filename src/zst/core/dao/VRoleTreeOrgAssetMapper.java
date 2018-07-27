package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.VRoleTreeOrgAsset;

@Repository
public interface VRoleTreeOrgAssetMapper {
	
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VRoleTreeOrgAsset> selectListByObj(VRoleTreeOrgAsset record) throws Exception;
    
    long selectCountByObj(VRoleTreeOrgAsset record) throws Exception;
}