package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.SysUserAssetMapper;
import zst.core.entity.SysUserAsset;
import zst.core.service.ISysUserAssetAuthorityService;

@Service
public class SysUserAssetAuthorityService implements ISysUserAssetAuthorityService {

	@Resource
	private SysUserAssetMapper userAssetMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) throws Exception {
		return userAssetMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SysUserAsset record) throws Exception {
		return userAssetMapper.insert(record);
	}

	@Override
	public int insertSelective(SysUserAsset record) throws Exception {
		return userAssetMapper.insertSelective(record);
	}

	@Override
	public SysUserAsset selectByPrimaryKey(Integer id) {
		return userAssetMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysUserAsset record)
			throws Exception {
		return userAssetMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysUserAsset record) throws Exception {
		return userAssetMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<SysUserAsset> selectListByObj(SysUserAsset record)
			throws Exception {
		return userAssetMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(SysUserAsset record) throws Exception {
		return userAssetMapper.selectCountByObj(record);
	}

	@Override
	public int insertBatch(List<SysUserAsset> list) throws Exception {
		return userAssetMapper.insertBatch(list);
	}

	@Override
	public List<SysUserAsset> selectByUserId(Integer userId) throws Exception {
		return userAssetMapper.selectByUserId(userId);
	}

	@Override
	public int deleteBatch(List<SysUserAsset> list) throws Exception {
		return userAssetMapper.deleteBatch(list);
	}

	@Override
	public int deleteByUserId(Integer userId) throws Exception {
		return userAssetMapper.deleteByUserId(userId);
	}

}
