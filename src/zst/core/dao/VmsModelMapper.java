package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.VmsModel;

@Repository
public interface VmsModelMapper {
	
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