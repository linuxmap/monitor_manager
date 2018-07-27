package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.VmsProduct;

@Repository
public interface VmsProductMapper {
	
    int deleteByPrimaryKey(String productId) throws Exception;

    int insert(VmsProduct record) throws Exception;

    int insertSelective(VmsProduct record) throws Exception;

    VmsProduct selectByPrimaryKey(String productId) throws Exception;

    int updateByPrimaryKeySelective(VmsProduct record) throws Exception;

    int updateByPrimaryKey(VmsProduct record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsProduct> selectListByObj(VmsProduct record) throws Exception;
    
    
    long selectCountByObj(VmsProduct record) throws Exception;
}