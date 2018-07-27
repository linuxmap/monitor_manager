package zst.core.service;

import java.util.List;

import zst.core.entity.ResultBean;
import zst.core.entity.SysRole;

public interface ISysRoleService {
	
	int deleteByPrimaryKey(Integer roleId) throws Exception;

    int insert(SysRole record) throws Exception;

    int insertSelective(SysRole record) throws Exception;

    SysRole selectByPrimaryKey(Integer roleId) throws Exception;
    
    List<SysRole> selectAll(SysRole record) throws Exception;

    int updateByPrimaryKeySelective(SysRole record) throws Exception;

    int updateByPrimaryKey(SysRole record) throws Exception;

	int deleteByRoleId(Integer roleId) throws Exception;
	
	ResultBean insertBatchPersonalize(Integer roleId,Integer userId,Integer[] negativeOrg, String[] negativeAsset,Integer[] positiveOrg,String[] positiveAsset) throws Exception;

	/**
	 * 更新角色的数据权限，包括角色对应的组织和设备权限
	 * @param roleId
	 * @param orgAddIdArray
	 * @param orgDelIdArray
	 * @param equAddIdArray
	 * @param equDelIdArray
	 * @return
	 * @throws Exception
	 */
	ResultBean updateRoleDataPermission(Integer roleId,Integer[] allCheckedAssetOrgArray, Integer[] orgAddIdArray,Integer[] orgDelIdArray, String[] equAddIdArray,String[] equDelIdArray) throws Exception;
	
	/**
	 * 新增角色的数据权限，包括角色对应的组织和设备权限
	 * @param roleId
	 * @param orgAddIdArray
	 * @param orgDelIdArray
	 * @param equAddIdArray
	 * @param equDelIdArray
	 * @return
	 * @throws Exception
	 */
	ResultBean insertRoleDataPermission(Integer roleId, Integer[] fullOrgChecked,Integer[] allCheckedOrgArray, String[] assetSpecialChecked) throws Exception;
	
	/**
	 * 删除角色下的组织和设备权限
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	ResultBean deleteRoleDataPermission(Integer roleId) throws Exception;
	
	/**
	 * 复制角色
	 * @param sysRole
	 * @param sublingRoleId
	 * @return
	 * @throws Exception
	 */
	ResultBean insertSameRole(SysRole sysRole, Integer sublingRoleId) throws Exception;

	/**
	 * 通过角色id逻辑删除角色
	 * @param roleId
	 * @return
	 */
	ResultBean deleteLogicalByRoleId(Integer roleId) throws Exception;
	
}
