package zst.core.service;

import java.util.List;

import zst.core.entity.VmsModel;

public interface IVmsModelService {

    int deleteByPrimaryKey(Integer modelId) throws Exception;

    int insert(VmsModel record) throws Exception;

    int insertSelective(VmsModel record) throws Exception;

    VmsModel selectByPrimaryKey(Integer modelId) throws Exception;

    int updateByPrimaryKeySelective(VmsModel record) throws Exception;

    int updateByPrimaryKey(VmsModel record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsModel> selectListByObj(VmsModel record) throws Exception;
    
    
    long selectCountByObj(VmsModel record) throws Exception;
}
