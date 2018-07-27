package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.SysBaseInfo;

@Repository
public interface SysBaseInfoMapper {
	
    int deleteByPrimaryKey(Integer baseId) throws Exception;

    int insert(SysBaseInfo record) throws Exception;

    int insertSelective(SysBaseInfo record) throws Exception;

    SysBaseInfo selectByPrimaryKey(Integer baseId) throws Exception;

    int updateByPrimaryKeySelective(SysBaseInfo record) throws Exception;

    int updateByPrimaryKey(SysBaseInfo record) throws Exception;
    
    /**
     * 批量查询
     * @param record
     * @return
     * @throws Exception
     */
    List<SysBaseInfo> selectListByObj(SysBaseInfo record) throws Exception;
    
    /**
     * 查询个数
     * @param record
     * @return
     * @throws Exception
     */
    long selectCountByObj(SysBaseInfo record) throws Exception;
}