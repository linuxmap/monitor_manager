package zst.core.service;

import java.util.List;

import zst.core.entity.VmsDeviceLogin;

public interface IVmsDeviceLoginService {

    int deleteByPrimaryKey(Integer deviceloginId)  throws Exception;

    int insert(VmsDeviceLogin record)  throws Exception;

    int insertSelective(VmsDeviceLogin record)  throws Exception;

    VmsDeviceLogin selectByPrimaryKey(Integer deviceloginId)  throws Exception;

    int updateByPrimaryKeySelective(VmsDeviceLogin record)  throws Exception;

    int updateByPrimaryKey(VmsDeviceLogin record)  throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsDeviceLogin> selectListByObj(VmsDeviceLogin record) throws Exception;
    
    
    long selectCountByObj(VmsDeviceLogin record) throws Exception;
}
