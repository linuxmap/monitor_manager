package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.VmsDeviceTypeMapper;
import zst.core.entity.VmsDeviceType;
import zst.core.service.IVmsDeviceTypeService;

@Service
public class VmsDeviceTypeService implements IVmsDeviceTypeService {
	
	@Resource
	private VmsDeviceTypeMapper deviceTypeMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) throws Exception {
		return deviceTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(VmsDeviceType record) throws Exception {
		return deviceTypeMapper.insert(record);
	}

	@Override
	public int insertSelective(VmsDeviceType record) throws Exception {
		return deviceTypeMapper.insertSelective(record);
	}

	@Override
	public VmsDeviceType selectByPrimaryKey(Integer id) throws Exception {
		return deviceTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsDeviceType record)
			throws Exception {
		return deviceTypeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsDeviceType record) throws Exception {
		return deviceTypeMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<VmsDeviceType> selectListByObj(VmsDeviceType record)
			throws Exception {
		return deviceTypeMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(VmsDeviceType record) throws Exception {
		return deviceTypeMapper.selectCountByObj(record);
	}

}
