package zst.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zst.core.dao.SysRoleCategoryFuntMapper;
import zst.core.dao.SysRoleCategoryMapper;
import zst.core.dao.SysRoleMapper;
import zst.core.dao.SysUserRoleMapper;
import zst.core.dao.VmsRoleAssetMapper;
import zst.core.dao.VmsRoleOrgMapper;
import zst.core.entity.ResultBean;
import zst.core.entity.SysRole;
import zst.core.entity.SysRoleCategory;
import zst.core.entity.SysRoleCategoryFunt;
import zst.core.entity.SysUserRole;
import zst.core.service.ISysRoleCategoryService;
import zst.extend.exception.PlatformException;
import zst.extend.util.CollectionUtil;

@Service
@Transactional
public class SysRoleCategoryService implements ISysRoleCategoryService {

	@Resource
	private SysRoleCategoryMapper categoryMapper;
	
	@Resource
	private SysRoleCategoryFuntMapper categoryFuntMapper;
	
	@Resource
	private SysRoleMapper roleMapper;
	
	@Resource
	private SysUserRoleMapper userRoleMapper;
	
	@Resource
	private VmsRoleOrgMapper roleOrgMapper;
	
	@Resource
	private VmsRoleAssetMapper roleAssetMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer categoryId) throws Exception {
		List<SysRole> roleList = roleMapper.selectByCategoryId(categoryId);
		List<Integer> roleIdList = new ArrayList<Integer>();
		if (CollectionUtil.isNotEmpty(roleList)) {
			for (SysRole role : roleList) {
				roleIdList.add(role.getRoleId());
			}
			//删除角色下的组织 、设备的关系
			roleOrgMapper.deleteByRoleIdList(roleIdList);
			roleAssetMapper.deleteByRoleIdList(roleIdList);
		}
		//删除角色类型下的角色
		roleMapper.deleteByCategoryId(categoryId);
		return categoryMapper.deleteByPrimaryKey(categoryId);
	}

	@Override
	public int insert(SysRoleCategory record) throws Exception {
		return categoryMapper.insert(record);
	}

	@Override
	public int insertSelective(SysRoleCategory record) throws Exception {
		return categoryMapper.insertSelective(record);
	}

	@Override
	public SysRoleCategory selectByPrimaryKey(Integer categoryId)
			throws Exception {
		return categoryMapper.selectByPrimaryKey(categoryId);
	}

	@Override
	public int updateByPrimaryKeySelective(SysRoleCategory record)
			throws Exception {
		return categoryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysRoleCategory record) throws Exception {
		return categoryMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<SysRoleCategory> selectListByObj(SysRoleCategory record) throws Exception {
		return categoryMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(SysRoleCategory record) throws Exception {
		return categoryMapper.selectCountByObj(record);
	}

	@Override
	public ResultBean insertSameObjectById(Integer categoryId, String realName) throws Exception {
		ResultBean resultBean = new ResultBean();
		try {
			SysRoleCategory originalObject = categoryMapper.selectByPrimaryKey(categoryId);
			if (originalObject!=null) {
				originalObject.setCategoryId(null);
				originalObject.setName(realName);
				categoryMapper.insertSelective(originalObject);
				//处理角色类型和菜单的权限关系
				List<SysRoleCategoryFunt> roleCategoryFuntList = categoryFuntMapper.selectByCategoryid(categoryId);
				if (CollectionUtil.isNotEmpty(roleCategoryFuntList)) {
					for (SysRoleCategoryFunt categoryFunt : roleCategoryFuntList) {
						categoryFunt.setId(null);
						categoryFunt.setRoleCategoryId(originalObject.getCategoryId());
					}
					categoryFuntMapper.addList(roleCategoryFuntList);
				}
				resultBean.setPrimaryKey(originalObject.getCategoryId());
				resultBean.setFlag(true);
				resultBean.setFeedBack("复制成功!");
			} else {
				resultBean.setFlag(false);
				resultBean.setFeedBack("找不到该角色类型!");
			}
			return resultBean;
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setFeedBack("复制失败!");
			resultBean.setFlag(false);
			throw new PlatformException(e.getMessage());
		}
	}

	@Override
	public ResultBean selectRelatedRole(Integer categoryId) throws Exception {
		ResultBean resultBean = new ResultBean();
		List<SysRole> roleList = roleMapper.selectByCategoryId(categoryId);
		if (CollectionUtil.isEmpty(roleList)) {
			//角色类型下没有角色
			resultBean.setFlag(true);
		} else {
			List<Integer> roleIdList = new ArrayList<Integer>();
			for (SysRole role : roleList) {
				roleIdList.add(role.getRoleId());
			}
			List<SysUserRole> userRoleList = userRoleMapper.selectByRoleIdList(roleIdList);
			if (CollectionUtil.isEmpty(userRoleList)) {
				//角色下没有用户
				resultBean.setFlag(true);
			} else {
				//角色下有用户
				StringBuilder sb = new StringBuilder();
				String separator = ",";
				for (SysRole role : roleList) {
					for (SysUserRole userRole : userRoleList) {
						if (role.getRoleId().intValue() == userRole.getRoleId().intValue()) {
							sb.append(role.getRoleName() + separator);
							break;
						}
					}
				}
				resultBean.setFlag(false);
				resultBean.setFeedBack(sb.toString());
			}
		}
		return resultBean;
	}

	@Override
	public ResultBean updateDoubleFuntByCategoryId(Integer roleCategoryId, Integer funtGroup, Integer[] funtIds) throws Exception {
		ResultBean resultBean = new ResultBean();
		resultBean.setFlag(true);
		resultBean.setFeedBack("保存成功,请重新登录");
		try {
			if (funtGroup == null) {
				resultBean.setFlag(false);
				resultBean.setFeedBack("缺少分组参数");
			} else {
				SysRoleCategoryFunt roleCategoryFunt = new SysRoleCategoryFunt();
				roleCategoryFunt.setFuntGroup(funtGroup);
				roleCategoryFunt.setRoleCategoryId(roleCategoryId);
				//根据roelId删除角色功能关联表中此角色的数据
				if (funtIds.length==1 && funtIds[0] == -1) {//没有选中任何功能 .删除所有功能即可
					categoryFuntMapper.deleteByCategoryFunt(roleCategoryFunt);
				} else {
					categoryFuntMapper.deleteByCategoryFunt(roleCategoryFunt);
					List<SysRoleCategoryFunt> list = new ArrayList<SysRoleCategoryFunt>();
					if(funtIds!=null&&funtIds.length>0){
						for(int i=0;i<funtIds.length;i++){
							SysRoleCategoryFunt sysFunt = new SysRoleCategoryFunt();
							sysFunt.setFuntId(funtIds[i]);
							sysFunt.setRoleCategoryId(roleCategoryId);
							sysFunt.setFuntGroup(funtGroup);
							list.add(sysFunt);
						}
						//根据角色id批量添加功能
						categoryFuntMapper.addList(list);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
		return resultBean;
	}

}
