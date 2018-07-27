package zst.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zst.core.dao.SysUserAssetMapper;
import zst.core.dao.SysUserOrgMapper;
import zst.core.dao.SysUserRoleMapper;
import zst.core.entity.ResultBean;
import zst.core.entity.SysUserRole;
import zst.core.service.ISysUserRoleService;
import zst.extend.exception.PlatformException;

/**
 * 用户角色中间信息 Service
 * @author: liuyy
 * @date: 2017-8-7 下午6:17:31
 */
@Service
@Transactional
public class SysUserRoleService implements ISysUserRoleService {

	@Resource
	SysUserRoleMapper sysUserRoleMapper;
	
	@Resource
	private SysUserOrgMapper userOrgMapper;
	
	@Resource
	private SysUserAssetMapper userAssetMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) throws Exception {
		return sysUserRoleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SysUserRole record) throws Exception {
		return sysUserRoleMapper.insert(record);
	}

	@Override
	public int insertSelective(SysUserRole record) throws Exception {
		return sysUserRoleMapper.insertSelective(record);
	}

	@Override
	public SysUserRole selectByPrimaryKey(Integer id) throws Exception {
		return sysUserRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysUserRole record) throws Exception {
		return sysUserRoleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysUserRole record) throws Exception {
		return sysUserRoleMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteByUserId(Integer userId) throws Exception {
		return sysUserRoleMapper.deleteByUserId(userId);
	}

	@Override
	public void saveSelectRole(Integer userId, Integer[] roleIds) throws Exception {
		//删除之前赋予的角色
		deleteByUserId(userId);
		//批量插入当前赋予的角色
		if(roleIds !=null && roleIds.length>0) {
			List<SysUserRole> list = new ArrayList<SysUserRole>();
			SysUserRole userRole = null;
			for (int i = 0; i < roleIds.length; i++) {
				userRole = new SysUserRole();
				userRole.setUserId(userId);
				userRole.setRoleId(roleIds[i]);
				list.add(userRole);
			}
			sysUserRoleMapper.insertBatch(list);
		}
		
	}

	@Override
	public int insertBatch(List<SysUserRole> list) throws Exception {
		return sysUserRoleMapper.insertBatch(list);
	}
	@Override
	public List<SysUserRole> selectByRoleId(Integer roleId) throws Exception {
		return sysUserRoleMapper.selectByRoleId(roleId);
	}

	@Override
	public int deleteByRoleId(Integer roleId) throws Exception {
		return sysUserRoleMapper.deleteByRoleId(roleId);
	}

	@Override
	public List<SysUserRole> selectByUserId(Integer userId) throws Exception {
		return sysUserRoleMapper.selectByUserId(userId);
	}

	@Override
	public ResultBean deleteBatch(Integer[] userIds,Integer roleId) throws Exception {
		ResultBean resultBean = new ResultBean();
		try {
			if (userIds!=null) {
				List<SysUserRole> userRoleList = new ArrayList<SysUserRole>();
				List<Integer> userIdList = new ArrayList<Integer>();
				SysUserRole userRole;
				for (Integer userId : userIds) {
					userRole = new SysUserRole();
					userRole.setUserId(userId);
					userRole.setRoleId(roleId);
					userRoleList.add(userRole);
					
					userIdList.add(userId);
				}
				sysUserRoleMapper.deleteBatch(userRoleList);
				//删除user_org和user_asset表中的数据
				userOrgMapper.deleteByUserIdList(userIdList);
				userAssetMapper.deleteByUserIdList(userIdList);
				resultBean.setFlag(true);
				resultBean.setFeedBack("取消成功!");
			} else {
				resultBean.setFlag(false);
				resultBean.setFeedBack("用户为空");
			}
			return resultBean;
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setFeedBack("取消失败!");
			resultBean.setFlag(false);
			throw new PlatformException(e.getMessage());
		}
	}

	@Override
	public ResultBean saveBatchSelectRole(String userIds, Integer roleId)
			throws Exception {
		ResultBean resultBean = new ResultBean();
		try {
			if (userIds!=null) {
				String[] userIdArray = userIds.split(",");
				List<Integer> idList = new ArrayList<Integer>();
				for (int i = 0; i < userIdArray.length; i++) {
			        idList.add(Integer.valueOf(userIdArray[i]));
			    }
				//删除原有的用户的角色
				sysUserRoleMapper.deleteByUserIdList(idList);
				//插入新的关系
				//批量插入当前赋予的角色
				if(roleId !=null && userIdArray.length>0) {
					List<SysUserRole> list = new ArrayList<SysUserRole>();
					SysUserRole userRole;
					for (Integer userId : idList) {
						userRole = new SysUserRole();
						userRole.setUserId(userId);
						userRole.setRoleId(roleId);
						list.add(userRole);
					}
					sysUserRoleMapper.insertBatch(list );
				}
				resultBean.setFlag(true);
				resultBean.setFeedBack("设置成功!");
			} else {
				resultBean.setFlag(false);
				resultBean.setFeedBack("用户为空");
			}
			return resultBean;
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setFeedBack("取消失败!");
			resultBean.setFlag(false);
			throw new PlatformException(e.getMessage());
		}
		
	}
}
