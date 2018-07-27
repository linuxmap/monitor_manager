package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.VmsGroup;

@Repository
public interface VmsGroupMapper {
	
    int deleteByPrimaryKey(Integer groupId) throws Exception;

    int insert(VmsGroup record) throws Exception;

    int insertSelective(VmsGroup record) throws Exception;

    VmsGroup selectByPrimaryKey(Integer groupId) throws Exception;

    int updateByPrimaryKeySelective(VmsGroup record) throws Exception;

    int updateByPrimaryKey(VmsGroup record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<VmsGroup> selectListByObj(VmsGroup record) throws Exception;
    
    /**
     * 查询个数
     * @param record
     * @return
     * @throws Exception
     */
    long selectCountByObj(VmsGroup record) throws Exception;
}