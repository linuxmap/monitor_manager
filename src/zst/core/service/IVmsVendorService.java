package zst.core.service;

import java.util.List;

import zst.core.entity.VmsVendor;

public interface IVmsVendorService {

	int deleteByPrimaryKey(Integer vendorId) throws Exception;

    int insert(VmsVendor record) throws Exception;

    int insertSelective(VmsVendor record) throws Exception;

    VmsVendor selectByPrimaryKey(Integer vendorId) throws Exception;

    int updateByPrimaryKeySelective(VmsVendor record) throws Exception;

    int updateByPrimaryKey(VmsVendor record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsVendor> selectListByObj(VmsVendor record) throws Exception;
    
    long selectCountByObj(VmsVendor record) throws Exception;
}
