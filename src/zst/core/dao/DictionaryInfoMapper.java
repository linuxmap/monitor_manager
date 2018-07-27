package zst.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zst.core.entity.DictionaryInfo;
import zst.core.entity.SysOrg;

/**
 * 字典类型相关接口
 * @author songxiang
 */
@Repository
public interface DictionaryInfoMapper {

	int deleteByPrimaryKey(Integer id) throws Exception;

    int insertSelective(DictionaryInfo record) throws Exception;

    DictionaryInfo selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(SysOrg record) throws Exception;
    
    long selectCountByObj(DictionaryInfo record) throws Exception;
    
    List<DictionaryInfo> selectListByObj(DictionaryInfo record) throws Exception;
    
    int updateBatchByDictionaryIds(List<String> list) throws Exception;
    
    List<DictionaryInfo> selectAllByObj(DictionaryInfo record) throws Exception;
}