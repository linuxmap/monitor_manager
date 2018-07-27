package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.VmsVendorMapper;
import zst.core.entity.VmsVendor;
import zst.core.service.IVmsVendorService;

@Service
public class VmsVendorService implements IVmsVendorService {

	@Resource
	private VmsVendorMapper vmsVendorMapper;

	@Override
	public int deleteByPrimaryKey(Integer vendorId) throws Exception {
		return vmsVendorMapper.deleteByPrimaryKey(vendorId);
	}

	@Override
	public int insert(VmsVendor record) throws Exception {
		return vmsVendorMapper.insert(record);
	}

	@Override
	public int insertSelective(VmsVendor record) throws Exception {
		return vmsVendorMapper.insertSelective(record);
	}

	@Override
	public VmsVendor selectByPrimaryKey(Integer vendorId) throws Exception {
		return vmsVendorMapper.selectByPrimaryKey(vendorId);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsVendor record) throws Exception {
		return vmsVendorMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsVendor record) throws Exception {
		return vmsVendorMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<VmsVendor> selectListByObj(VmsVendor record) throws Exception {
		return vmsVendorMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(VmsVendor record) throws Exception {
		return vmsVendorMapper.selectCountByObj(record);
	}
	
	

}
