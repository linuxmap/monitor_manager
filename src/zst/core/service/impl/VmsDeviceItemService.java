package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.VmsDeviceItemMapper;
import zst.core.entity.VmsDeviceItem;
import zst.core.service.IVmsDeviceItemService;

@Service
public class VmsDeviceItemService implements IVmsDeviceItemService {

	@Resource
	private VmsDeviceItemMapper deviceItemMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) throws Exception {
		return deviceItemMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(VmsDeviceItem record) throws Exception {
		return deviceItemMapper.insert(record);
	}

	@Override
	public int insertSelective(VmsDeviceItem record) throws Exception {
		return deviceItemMapper.insertSelective(record);
	}

	@Override
	public VmsDeviceItem selectByPrimaryKey(Integer id) throws Exception {
		return deviceItemMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsDeviceItem record)
			throws Exception {
		return deviceItemMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsDeviceItem record) throws Exception {
		return deviceItemMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<VmsDeviceItem> selectListByObj(VmsDeviceItem record)
			throws Exception {
		return deviceItemMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(VmsDeviceItem record) throws Exception {
		return deviceItemMapper.selectCountByObj(record);
	}

	@Override
	public int deleteBatchByIds(List<Integer> list) throws Exception {
		return deviceItemMapper.deleteBatchByIds(list);
	}

}
