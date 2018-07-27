package zst.core.service;

import java.util.List;

import zst.core.entity.VmsCamera;

public interface IVmsCameraService {

    int deleteByPrimaryKey(Integer cameraId) throws Exception;

    int insert(VmsCamera record) throws Exception;

    int insertSelective(VmsCamera record) throws Exception;

    VmsCamera selectByPrimaryKey(Integer cameraId) throws Exception;

    int updateByPrimaryKeySelective(VmsCamera record) throws Exception;

    int updateByPrimaryKey(VmsCamera record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsCamera> selectListByObj(VmsCamera record) throws Exception;
    
    
    long selectCountByObj(VmsCamera record) throws Exception;
}
