package zst.core.service;

import java.util.List;

import zst.core.entity.VmsDeviceType;

public interface IVmsDeviceTypeService {

	int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(VmsDeviceType record) throws Exception;

    int insertSelective(VmsDeviceType record) throws Exception;

    VmsDeviceType selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(VmsDeviceType record) throws Exception;

    int updateByPrimaryKey(VmsDeviceType record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsDeviceType> selectListByObj(VmsDeviceType record) throws Exception;
    
    /**
     * 查询个数
     * @param record
     * @return
     * @throws Exception
     */
    long selectCountByObj(VmsDeviceType record) throws Exception;
}
