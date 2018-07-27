package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.SysUserOrgMapper;
import zst.core.entity.SysUserOrg;
import zst.core.service.ISysUserOrgAuthorityService;

@Service
public class SysUserOrgAuthorityService implements ISysUserOrgAuthorityService {

	@Resource
	private SysUserOrgMapper userOrgMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) throws Exception {
		return userOrgMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SysUserOrg record) throws Exception {
		return userOrgMapper.insert(record);
	}

	@Override
	public int insertSelective(SysUserOrg record) throws Exception {
		return userOrgMapper.insertSelective(record);
	}

	@Override
	public SysUserOrg selectByPrimaryKey(Integer id) throws Exception {
		return userOrgMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysUserOrg record) throws Exception {
		return userOrgMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysUserOrg record) throws Exception {
		return userOrgMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<SysUserOrg> selectListByObj(SysUserOrg record) throws Exception {
		return userOrgMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(SysUserOrg record) throws Exception {
		return userOrgMapper.selectCountByObj(record);
	}

	@Override
	public int insertBatch(List<SysUserOrg> list) throws Exception {
		return userOrgMapper.insertBatch(list);
	}

	@Override
	public List<SysUserOrg> selectByUserId(Integer userId) throws Exception {
		return userOrgMapper.selectByUserId(userId);
	}

	@Override
	public int deleteBatch(List<SysUserOrg> list) throws Exception {
		return userOrgMapper.deleteBatch(list);
	}

	@Override
	public int deleteByUserId(Integer userId) throws Exception {
		return userOrgMapper.deleteByUserId(userId);
	}

}
