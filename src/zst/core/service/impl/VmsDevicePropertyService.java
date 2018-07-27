package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.VmsDevicePropertyMapper;
import zst.core.entity.VmsDeviceProperty;
import zst.core.service.IVmsDevicePropertyService;

@Service
public class VmsDevicePropertyService implements IVmsDevicePropertyService {

	@Resource
	private VmsDevicePropertyMapper devicePropertyMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) throws Exception {
		return devicePropertyMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(VmsDeviceProperty record) throws Exception {
		return devicePropertyMapper.insert(record);
	}

	@Override
	public int insertSelective(VmsDeviceProperty record) throws Exception {
		return devicePropertyMapper.insertSelective(record);
	}

	@Override
	public VmsDeviceProperty selectByPrimaryKey(Integer id) throws Exception {
		return devicePropertyMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsDeviceProperty record)
			throws Exception {
		return devicePropertyMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsDeviceProperty record) throws Exception {
		return devicePropertyMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<VmsDeviceProperty> selectListByObj(VmsDeviceProperty record)
			throws Exception {
		return devicePropertyMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(VmsDeviceProperty record) throws Exception {
		return devicePropertyMapper.selectCountByObj(record);
	}

	@Override
	public int addList(List<VmsDeviceProperty> list) throws Exception {
		return devicePropertyMapper.addList(list);
	}

	@Override
	public int updateList(List<VmsDeviceProperty> list) throws Exception {
		return devicePropertyMapper.updateList(list);
	}

	@Override
	public List<VmsDeviceProperty> selectConbinedListByObj(
			VmsDeviceProperty record) throws Exception {
		return devicePropertyMapper.selectConbinedListByObj(record);
	}

	@Override
	public long selectCombinedCountByObj(VmsDeviceProperty record)
			throws Exception {
		return devicePropertyMapper.selectCombinedCountByObj(record);
	}

	@Override
	public int deleteBatchByIds(List<Integer> list) throws Exception {
		return devicePropertyMapper.deleteBatchByIds(list);
	}

}
