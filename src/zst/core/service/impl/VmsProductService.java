package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.VmsProductMapper;
import zst.core.entity.VmsProduct;
import zst.core.service.IVmsProductService;

@Service
public class VmsProductService implements IVmsProductService {

	@Resource
	private VmsProductMapper productMapper;
	
	@Override
	public int deleteByPrimaryKey(String productId) throws Exception {
		return productMapper.deleteByPrimaryKey(productId);
	}

	@Override
	public int insert(VmsProduct record) throws Exception {
		return productMapper.insert(record);
	}

	@Override
	public int insertSelective(VmsProduct record) throws Exception {
		return productMapper.insertSelective(record);
	}

	@Override
	public VmsProduct selectByPrimaryKey(String productId) throws Exception {
		return productMapper.selectByPrimaryKey(productId);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsProduct record) throws Exception {
		return productMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsProduct record) throws Exception {
		return productMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<VmsProduct> selectListByObj(VmsProduct record) throws Exception {
		return productMapper.selectListByObj(record);
	}

	@Override
	public long selectCountByObj(VmsProduct record) throws Exception {
		return productMapper.selectCountByObj(record);
	}

}
