package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.VmsDeviceLoginMapper;
import zst.core.entity.VmsDeviceLogin;
import zst.core.service.IVmsDeviceLoginService;

@Service
public class VmsDeviceLoginService implements IVmsDeviceLoginService {

	@Resource
	private VmsDeviceLoginMapper deviceLoginMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer deviceloginId) throws Exception {
		return deviceLoginMapper.deleteByPrimaryKey(deviceloginId);
	}

	@Override
	public int insert(VmsDeviceLogin record) throws Exception {
		return deviceLoginMapper.insert(record);
	}

	@Override
	public int insertSelective(VmsDeviceLogin record) throws Exception {
		return deviceLoginMapper.insertSelective(record);
	}

	@Override
	public VmsDeviceLogin selectByPrimaryKey(Integer deviceloginId)
			throws Exception {
		return deviceLoginMapper.selectByPrimaryKey(deviceloginId);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsDeviceLogin record)
			throws Exception {
		return deviceLoginMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsDeviceLogin record) throws Exception {
		return deviceLoginMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<VmsDeviceLogin> selectListByObj(VmsDeviceLogin record)
			throws Exception {
		return deviceLoginMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(VmsDeviceLogin record) throws Exception {
		return deviceLoginMapper.selectCountByObj(record);
	}

}
