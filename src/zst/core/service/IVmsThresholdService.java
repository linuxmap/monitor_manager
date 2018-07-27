package zst.core.service;

import java.util.List;

import zst.core.entity.VmsThreshold;

public interface IVmsThresholdService {

	int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(VmsThreshold record) throws Exception;

    int insertSelective(VmsThreshold record) throws Exception;

    VmsThreshold selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(VmsThreshold record) throws Exception;

    int updateByPrimaryKey(VmsThreshold record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsThreshold> selectListByObj(VmsThreshold record) throws Exception;
    
    /**
     * 查询个数
     * @param record
     * @return
     * @throws Exception
     */
    long selectCountByObj(VmsThreshold record) throws Exception;
}
