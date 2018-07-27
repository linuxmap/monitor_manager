package zst.core.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import zst.core.dao.SysUserMapper;
import zst.core.entity.SysUser;
import zst.core.service.ISysUserService;

@Service
public class SysUserService implements ISysUserService {
	
	@Resource
	private SysUserMapper sysUserMapper;

	@Override
	public int deleteByPrimaryKey(Integer userId) {
		return sysUserMapper.deleteByPrimaryKey(userId);
	}

	@Override
	public int insert(SysUser record) {
		return sysUserMapper.insert(record);
	}

	@Override
	public int insertSelective(SysUser record) {
		return sysUserMapper.insertSelective(record);
	}

	@Override
	public SysUser selectByPrimaryKey(Integer userId) {
		return sysUserMapper.selectByPrimaryKey(userId);
	}

	@Override
	public int updateByPrimaryKeySelective(SysUser record) {
		return sysUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysUser record) {
		return sysUserMapper.updateByPrimaryKey(record);
	}

	@Override
	public long selectCountByObj(SysUser record) throws Exception {
		return sysUserMapper.selectCountByObj(record);
	}

	@Override
	public List<SysUser> selectListByObj(SysUser record) throws Exception {
		return sysUserMapper.selectListByObj(record);
	}
	@Override
	public List<SysUser> selectListByLoginName(SysUser record) throws Exception {
		return sysUserMapper.selectListByLoginName(record);
	}

	@Override
	public List<Map<String, String>> selectRoleByLoginName(String loginName) throws Exception {
		return sysUserMapper.selectRoleByLoginName(loginName);
	}

	@Override
	public List<Map<String, String>> selectUnRoleByLoginName(String loginName) throws Exception {
		return sysUserMapper.selectUnRoleByLoginName(loginName);
	}

	@Override
	public int deleteBatchByOrgIds(List<Integer> list) throws Exception {
		return sysUserMapper.updateBatchByUserIds(list);
	}
	
	@Override
	public int selectLoginErr(SysUser user) throws Exception {
		int errTimes = 0;
		List<SysUser> userList = selectListByObj(user);
		if(userList.size()>0)
			errTimes = userList.get(0).getLoginErrorTimes();
		return errTimes;
	}

	@Override
	public List<SysUser> selectListByRoleId(Integer roleId) throws Exception {
		return sysUserMapper.selectListByRoleId(roleId);
	}

	@Override
	public List<SysUser> selectListByIdList(List<Integer> list)
			throws Exception {
		return sysUserMapper.selectListByIdList(list);
	}

	@Override
	public long selectCountByMap(Map<String, Object> map) throws Exception {
		return sysUserMapper.selectCountByMap(map);
	}

	@Override
	public List<SysUser> selectListByMap(Map<String, Object> map)
			throws Exception {
		return sysUserMapper.selectListByMap(map);
	}

	@Override
	public long selectCountAllocateByMap(Map<String, Object> map) throws Exception {
		return sysUserMapper.selectCountAllocateByMap(map);
	}

	@Override
	public List<SysUser> selectListAllocateByMap(Map<String, Object> map)
			throws Exception {
		return sysUserMapper.selectListAllocateByMap(map);
	}

	@Override
	public long selectCountByRoleObj(SysUser record) throws Exception {
		return sysUserMapper.selectCountByRoleObj(record);
	}

	@Override
	public List<SysUser> selectListByRoleObj(SysUser record) throws Exception {
		return sysUserMapper.selectListByRoleObj(record);
	}
	
	@Override
	public void checkUser(String loginName,String loginPwd,String code,String rember,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		
	}
	
}
